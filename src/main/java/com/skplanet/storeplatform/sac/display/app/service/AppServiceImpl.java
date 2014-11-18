/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.GradeInfoSac;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetail;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetailParam;
import com.skplanet.storeplatform.sac.display.app.vo.ImageSource;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import com.skplanet.storeplatform.sac.display.common.vo.UpdateHistory;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * 앱 상품 상세조회
 * <p/>
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
@Service
public class AppServiceImpl implements AppService {

	private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

	private static final Set<String> IMG_CODE_PREVIEW;
	private static final Map<String, String> IMG_CODE_LANDSCAPE_REFERENCE;
	private static final List<String> IMG_CODE_REQUEST;
    private static final Pattern PATTERN_OSVER = Pattern.compile("Android/(\\d+\\.\\d+)(?:\\.\\d+)?");

	static {
		IMG_CODE_PREVIEW = new LinkedHashSet<String>(Arrays.asList("DP000103,DP000104,DP000105,DP000106,DP0001C1,DP0001C2,DP0001C3,DP0001C4".split(",")));
        IMG_CODE_LANDSCAPE_REFERENCE = new HashMap<String, String>();
        String[] landscapeRef = "DP0001D7,DP0001D8,DP0001D9,DP0001E0,DP0001E1,DP0001E2,DP0001E3,DP0001E4".split(",");
        int i = 0;
        for (String imgCd : IMG_CODE_PREVIEW) {
            IMG_CODE_LANDSCAPE_REFERENCE.put(landscapeRef[i++], imgCd);
        }

		List<String> reqList = new ArrayList<String>();
		reqList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD); // for Thumbnail original
		reqList.addAll(IMG_CODE_PREVIEW);
		reqList.addAll(IMG_CODE_LANDSCAPE_REFERENCE.keySet());
		IMG_CODE_REQUEST = reqList;
	}

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	@Autowired
    private MemberBenefitService benefitService;
	
    @Autowired
    private CommonMetaInfoGenerator metaInfoGenerator;

	@Override
	public AppDetailRes searchAppDetail(AppDetailParam request) {
        logger.info("channelId={},userKey={},deviceKey={},deviceModel={}",
                request.getChannelId(), request.getUserKey(), request.getDeviceKey(), request.getDeviceModelCd());

		AppDetail appDetail = this.commonDAO.queryForObject("AppDetail.getAppDetail", request, AppDetail.class);
		if (appDetail == null)
			return null;

		AppDetailRes res = new AppDetailRes();
        Product product = new Product();
        res.setProduct(product);

        if (!appDetail.getProdStatusCd().equals(DisplayConstants.DP_SALE_STAT_ING)) {
            // 04, 09, 10의 경우 구매이력이 없으면 상품 없음을 표시한다.
            if (DisplayConstants.DP_SALE_STAT_PAUSED.equals(appDetail.getProdStatusCd()) ||
                    DisplayConstants.DP_SALE_STAT_RESTRIC_DN.equals(appDetail.getProdStatusCd()) ||
                    DisplayConstants.DP_SALE_STAT_DROP_REQ_DN.equals(appDetail.getProdStatusCd())) {
                if (!StringUtils.isEmpty(request.getUserKey()) && !StringUtils.isEmpty(request.getDeviceKey()) &&
                        !commonService.checkPurchase(request.getTenantId(), request.getUserKey(), request.getDeviceKey(), request.getChannelId())) {
                    return null;
                }
                else
                    res.getProduct().setSalesStatus(DisplayConstants.DP_PURSTAT_RESTRICTED);
            }
            else
                res.getProduct().setUserPurStatus(DisplayConstants.DP_PURSTAT_RESTRICTED);
        }

        if (!StringUtils.isEmpty(request.getUserKey()) && !StringUtils.isEmpty(request.getDeviceKey())) {
            res.getProduct().setUserPurStatus(DisplayConstants.DP_PURSTAT_AVAILABLE);
        }

        res.getProduct().setSalesStatus(appDetail.getProdStatusCd());

        // Product Basic info
        product.setIdentifierList(new ArrayList<Identifier>());
        product.getIdentifierList().add(new Identifier("channel", request.getChannelId()));
        product.setPacketFee(appDetail.getProdGbn());

		product.setTitle(new Title(appDetail.getProdNm()));
		Price price = new Price();
		price.setFixedPrice(appDetail.getFixedAmt());
        price.setText(appDetail.getProdAmt());
		product.setPrice(price);
        product.setProductExplain(appDetail.getProdBaseDesc());
        product.setProductDetailExplain(appDetail.getProdDtlDesc());

        product.setSvcGrpCd(appDetail.getSvcGrpCd());

        product.setLikeYn(appDetail.getLikeYn());

        // Menu
        List<MenuItem> menuList = commonService.getMenuItemList(request.getChannelId(), request.getLangCd());
        product.setMenuList(new ArrayList<Menu>());
        String topMenuId = "";
        for (MenuItem mi : menuList) {
            Menu menu = new Menu();
            menu.setId(mi.getMenuId());
            menu.setName(mi.getMenuNm());
            if(mi.isInfrMenu()) {
            	topMenuId = mi.getMenuId();
                menu.setType("topClass");
            } else
                menu.setDesc(mi.getMenuDesc());

			product.getMenuList().add(menu);
		}

        //tmembership 할인율
        TmembershipDcInfo tmembershipDcInfo = commonService.getTmembershipDcRateForMenu(request.getTenantId(), topMenuId);
        List<Point> pointList = metaInfoGenerator.generatePoint(tmembershipDcInfo);
        //Tstore멤버십 적립율 정보
        if (StringUtils.isNotEmpty(request.getUserKey())) {
        	//회원등급 조회
        	GradeInfoSac userGradeInfo = commonService.getUserGrade(request.getUserKey());
        	if(userGradeInfo != null) {
        		if(pointList == null) pointList = new ArrayList<Point>();
	        	String userGrade = userGradeInfo.getUserGradeCd();
	        	Integer prodAmt = appDetail.getProdAmt();
	        	MileageInfo mileageInfo = benefitService.getMileageInfo(request.getTenantId(), topMenuId, request.getChannelId(), prodAmt);
	        	
	        	//무료인 경우 예외처리
	        	if(prodAmt == null || prodAmt == 0) {
	        		if(StringUtils.equals(mileageInfo.getPolicyTargetCd(), DisplayConstants.POLICY_TARGET_CD_CATEGORY)
	        				&& !supportInApp(appDetail)) {
	        			//무료 && 카테고리 (예외상품 아님) && 인앱 미지원
	        			// 마일리지 정보를 내려주지 않는다.
	        			mileageInfo = new MileageInfo(); 
	        		}
	        	}
	        	pointList.addAll(metaInfoGenerator.generateMileage(mileageInfo, userGrade));
        	}
        }
        if(pointList.size() > 0) product.setPointList(pointList);
        
        
        product.setSupportList(new ArrayList<Support>());
        product.getSupportList().add(new Support("drm", appDetail.getDrmYn()));
        product.getSupportList().add(new Support("iab", supportInApp(appDetail) ? "Y" : "N"));

        // Source
        List<Source> sourceList = getImageList(request.getChannelId(), request.getLangCd());
		product.setSourceList(sourceList);

		Accrual accrual = new Accrual();
		accrual.setVoterCount(appDetail.getPaticpersCnt());
		accrual.setDownloadCount(appDetail.getDwldCnt());
		accrual.setScore(appDetail.getAvgEvluScore());
		product.setAccrual(accrual);

		Rights rights = new Rights();
		rights.setGrade(appDetail.getProdGrdCd());
        rights.setAgeAllowedFrom(commonService.getAllowedAge(appDetail.getTopMenuId(), appDetail.getProdGrdCd()));
		product.setRights(rights);

		// App
		App app = new App();
		product.setApp(app);
		app.setAid(appDetail.getAid());
        if(appDetail.getVmVer() != null)
		    app.setSupportedOs("Android " + appDetail.getVmVer());
		app.setPackageName(appDetail.getApkPkgNm());
		app.setVersionCode(appDetail.getApkVer());
		app.setVersion(appDetail.getApkVerNm());
		app.setSize(appDetail.getFileSize());
        app.setDate(new Date(DisplayConstants.DP_DATE_SALE_REG, appDetail.getSaleStrtDt()));
        app.setDescriptionVideoUrl(appDetail.getDescVideoUrl());

        // App - Provisioning
        String simpleOsVer = "_NOT_";
        Matcher matcher = PATTERN_OSVER.matcher(request.getOsVersion());
        if (matcher.matches()) {
            simpleOsVer = matcher.group(1);
        }
        app.setIsDeviceSupported(appDetail.getVmVer() != null &&
                request.getOsVersion() != null &&
                appDetail.getVmVer().contains(simpleOsVer != null ? simpleOsVer : "") &&
                appDetail.getIsDeviceSupp().equals("Y") ? "Y" : "N");

		// Update History
		History history = new History();
		List<UpdateHistory> updateHistoryList = this.commonService.getUpdateList(request.getChannelId(), 1, 5);
		List<Update> updateList = new ArrayList<Update>();
		for (UpdateHistory uh : updateHistoryList) {
			Update update = new Update();

			update.setUpdateExplain(uh.getUpdtText());
			update.setDate(new Date(DisplayConstants.DP_DATE_REG, uh.getProdUpdDt()));

			updateList.add(update);
		}
		app.setHistory(history);
		history.setUpdate(updateList);

		// Distributor
		Distributor distributor = new Distributor();
		distributor.setSellerKey(appDetail.getSellerMbrNo());
		distributor.setName(appDetail.getExpoSellerNm());
		distributor.setTel(appDetail.getExpoSellerTelno());
		distributor.setEmail(appDetail.getExpoSellerEmail());
		product.setDistributor(distributor);

		res.setProduct(product);
		return res;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param appDetail
	 * @return
	 */
	private boolean supportInApp(AppDetail appDetail) {
		return StringUtils.isNotEmpty(appDetail.getPartParentClsfCd());
	}

    private List<Source> getImageList(String channelId, String langCd) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("channelId", channelId);
        req.put("langCd", langCd);
        req.put("imgCds", IMG_CODE_REQUEST);

        List<ImageSource> imageSourceList = commonDAO.queryForList("AppDetail.getSourceList", req, ImageSource.class);

        // 가로 크기 이미지 판단
        Set<String> landscapeImgSet = new HashSet<String>();
        for (ImageSource imgSrc : imageSourceList) {
            String orgImgCd = IMG_CODE_LANDSCAPE_REFERENCE.get(imgSrc.getImgCd());
            if(orgImgCd != null)
                landscapeImgSet.add(orgImgCd);
        }

        List<Source> sourceList = new ArrayList<Source>();
        for (ImageSource imgSrc : imageSourceList) {

            Source source = new Source();
            source.setMediaType(DisplayCommonUtil.getMimeType(imgSrc.getFileNm()));
            source.setUrl(imgSrc.getFilePath());

            if (IMG_CODE_PREVIEW.contains(imgSrc.getImgCd())) {
                source.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
            }
            else if(imgSrc.getImgCd().equals(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD)) {
                source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
            }
            else
                continue;

            if(landscapeImgSet.contains(imgSrc.getImgCd())) {
                source.setOrientation("landscape");
            }
            else {
                if(imgSrc.getWidth() != null && imgSrc.getHeight() != null) {
                    source.setHeight(imgSrc.getHeight());
                    source.setWidth(imgSrc.getWidth());

                    if(imgSrc.getWidth().equals(imgSrc.getHeight()))
                        source.setOrientation("square");
                    else
                        source.setOrientation(imgSrc.getWidth() < imgSrc.getHeight() ? "portrait" : "landscape");
                } else {
                    source.setOrientation("portrait");
                }
            }

            sourceList.add(source);
        }

        return sourceList;
    }

}
