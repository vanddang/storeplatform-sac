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
import java.util.HashSet;
import java.util.List;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetail;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetailParam;
import com.skplanet.storeplatform.sac.display.app.vo.ImageSource;
import com.skplanet.storeplatform.sac.display.app.vo.ImageSourceReq;
import com.skplanet.storeplatform.sac.display.app.vo.UpdateHistory;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;

/**
 * 앱 상품 상세조회
 * <p/>
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
@Service
public class AppServiceImpl implements AppService {

	private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

	private static final Set<String> SOURCE_LIST_SCREENSHOT_ORIGINAL;
	private static final String[] SOURCE_REQUEST;
    private static final Pattern PATTERN_OSVER = Pattern.compile("Android/(\\d+\\.\\d+)(?:\\.\\d+)?");

	static {
		SOURCE_LIST_SCREENSHOT_ORIGINAL = new HashSet<String>(Arrays.asList("DP000103", "DP000104", "DP000105",
				"DP000106", "DP0001C1", "DP0001C2", "DP0001C3", "DP0001C4"));
		List<String> reqList = new ArrayList<String>();
		reqList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD); // for Thumbnail original
		reqList.addAll(SOURCE_LIST_SCREENSHOT_ORIGINAL);
		SOURCE_REQUEST = reqList.toArray(new String[reqList.size()]);
	}

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	@Override
	public AppDetailRes getAppDetail(AppDetailParam request) {

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
        if(tmembershipDcInfo != null) {
        	List<Point> pointList = null; 
        	
        	if(tmembershipDcInfo.getNormalDcRate() != null) {
        		pointList = new ArrayList<Point>();
		        Point point = new Point();
		        point.setName(DisplayConstants.DC_RATE_TMEMBERSHIP);
		        point.setType(DisplayConstants.DC_RATE_TYPE_NORMAL);
		        point.setDiscountRate(tmembershipDcInfo.getNormalDcRate());
		        pointList.add(point);
        	}
        	if(tmembershipDcInfo.getFreepassDcRate() != null) {
        		if(pointList == null) pointList = new ArrayList<Point>();
        		Point point = new Point();
        		point.setName(DisplayConstants.DC_RATE_TMEMBERSHIP);
        		point.setType(DisplayConstants.DC_RATE_TYPE_FREEPASS);
        		point.setDiscountRate(tmembershipDcInfo.getFreepassDcRate());
        		pointList.add(point);
        	}
	        
        	product.setPointList(pointList);
        }

        product.setSupportList(new ArrayList<Support>());
        product.getSupportList().add(new Support("drm", appDetail.getDrmYn()));

        // Source
        List<ImageSource> imageSourceList = commonDAO.queryForList("AppDetail.getSourceList", new ImageSourceReq(request.getChannelId(), SOURCE_REQUEST, request.getLangCd()), ImageSource.class);
        List<Source> sourceList = new ArrayList<Source>();
        for (ImageSource imgSrc : imageSourceList) {
            Source source = new Source();
            source.setMediaType(DisplayCommonUtil.getMimeType(imgSrc.getFileNm()));
            source.setUrl(imgSrc.getFilePath());
            if(imgSrc.getWidth() != null && imgSrc.getHeight() != null) {
                source.setHeight(imgSrc.getHeight());
                source.setWidth(imgSrc.getWidth());
                source.setOrientation(imgSrc.getWidth() < imgSrc.getHeight() ? "portrait" : "landscape");
            } else {
                source.setOrientation("portrait");
            }

			if (SOURCE_LIST_SCREENSHOT_ORIGINAL.contains(imgSrc.getImgCd()))
				source.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
			else
				source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);

			sourceList.add(source);
		}
		product.setSourceList(sourceList);

		Accrual accrual = new Accrual();
		accrual.setVoterCount(appDetail.getPaticpersCnt());
		accrual.setDownloadCount(appDetail.getDwldCnt());
		accrual.setScore(appDetail.getAvgEvluScore());
		product.setAccrual(accrual);

		Rights rights = new Rights();
		rights.setGrade(appDetail.getProdGrdCd());
		product.setRights(rights);

		// App
		App app = new App();
		product.setApp(app);
		app.setAid(appDetail.getAid());
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
		List<UpdateHistory> updateHistoryList = this.commonDAO.queryForList("AppDetail.getUpdateHistoryList", request,
				UpdateHistory.class);
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

}
