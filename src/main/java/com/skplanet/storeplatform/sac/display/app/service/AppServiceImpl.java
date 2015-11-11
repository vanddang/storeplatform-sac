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

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.GradeInfoSac;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetail;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetailParam;
import com.skplanet.storeplatform.sac.display.app.vo.ImageSource;
import com.skplanet.storeplatform.sac.display.cache.service.ProductInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductStats;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductStatsParam;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.service.menu.MenuInfoService;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import com.skplanet.storeplatform.sac.display.common.vo.UpdateHistory;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private ProductInfoManager productInfoManager;

	@Override
	public AppDetailRes searchAppDetail(AppDetailParam param) {

        String userKey = param.getUserKey();

        Map<String, Object> req = Maps.newHashMap();
        req.put("deviceModelCd", param.getDeviceModelCd());
        req.put("userKey", userKey);
        req.put("channelId", param.getChannelId());
        req.put("langCd", param.getLangCd());
        req.put("tenantId", param.getTenantId());

        AppDetail appDetail = this.commonDAO.queryForObject("AppDetail.getAppDetail", param, AppDetail.class);
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
                if (!StringUtils.isEmpty(userKey) && !StringUtils.isEmpty(param.getDeviceKey()) &&
                        !commonService.checkPurchase(param.getTenantId(), userKey, param.getDeviceKey(), param.getChannelId())) {
                    return null;
                } else
                    res.getProduct().setUserPurStatus(DisplayConstants.DP_PURSTAT_RESTRICTED);
            } else
                res.getProduct().setUserPurStatus(DisplayConstants.DP_PURSTAT_RESTRICTED);
        }

        if (!StringUtils.isEmpty(userKey) && !StringUtils.isEmpty(param.getDeviceKey())) {
            res.getProduct().setUserPurStatus(DisplayConstants.DP_PURSTAT_AVAILABLE);
        }

        res.getProduct().setSalesStatus(appDetail.getProdStatusCd());

        // Product Basic info
        product.setIdentifierList(new ArrayList<Identifier>());
        product.getIdentifierList().add(new Identifier("channel", param.getChannelId()));
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

        List<TenantProductInfo> tenantProdInfoList = getTenantProdList(param.getChannelId());
        List<TenantProduct> tenantProductList = Lists.transform(tenantProdInfoList, new Function<TenantProductInfo, TenantProduct>() {
            @Override
            public TenantProduct apply(TenantProductInfo input) {
                TenantProduct tp = new TenantProduct();
                tp.setTenantId(input.getTenantId());
                tp.setMapgProdId(input.getMapgProdId());
                tp.setSalesStatus(input.getProdStatusCd());
                return tp;
            }
        });
        product.setTenantProductList(tenantProductList);

        // Menu
        product.setMenuList(Lists.newArrayList(
                new Menu(appDetail.getTopMenuId(), menuInfoService.getMenuName(appDetail.getTopMenuId(), param.getLangCd()), "topClass"),
                new Menu(appDetail.getMenuId(), appDetail.getMenuNm(), appDetail.getMenuDesc(), null)));

        //tmembership 할인율
        TmembershipDcInfo tmembershipDcInfo = commonService.getTmembershipDcRateForMenu(param.getTenantId(), appDetail.getTopMenuId());
        List<Point> pointList = metaInfoGenerator.generatePoint(tmembershipDcInfo);
        //Tstore멤버십 적립율 정보
        if (StringUtils.isNotEmpty(userKey)) {
            //회원등급 조회
            GradeInfoSac userGradeInfo = commonService.getUserGrade(userKey);
            if (userGradeInfo != null) {
                if (pointList == null) pointList = new ArrayList<Point>();
                String userGrade = userGradeInfo.getUserGradeCd();
                Integer prodAmt = appDetail.getProdAmt();
                MileageInfo mileageInfo = benefitService.getMileageInfo(param.getTenantId(), appDetail.getMenuId(), param.getChannelId(), userKey);
                //benefitService.checkFreeProduct(mileageInfo, prodAmt); TODO 대체

                //무료인 경우 예외처리
                if (prodAmt == null || prodAmt == 0) {
                    if (StringUtils.equals(mileageInfo.getPolicyTargetCd(), DisplayConstants.POLICY_TARGET_CD_CATEGORY)
                            && !supportInApp(appDetail)) {
                        //무료 && 카테고리 (예외상품 아님) && 인앱 미지원
                        // 마일리지 정보를 내려주지 않는다.
                        mileageInfo = new MileageInfo();
                    }
                }
                pointList.addAll(metaInfoGenerator.generateMileage(mileageInfo, userGrade));
            }
        }

        if (pointList.size() > 0)
            product.setPointList(pointList);

        product.setSupportList(Lists.newArrayList(
                new Support("drm", appDetail.getDrmYn()),
                new Support("iab", supportInApp(appDetail) ? "Y" : "N")));

        // Source
        List<Source> sourceList = getImageList(param.getChannelId(), param.getLangCd());
        product.setSourceList(sourceList);

        ProductStats stats = productInfoManager.getProductStats(new ProductStatsParam(param.getChannelId()));
        Accrual accrual = new Accrual();
        accrual.setVoterCount(stats.getParticipantCount());
        accrual.setDownloadCount(stats.getPurchaseCount());
        accrual.setScore(stats.getAverageScore());
        product.setAccrual(accrual);

        Rights rights = new Rights();
        rights.setGrade(appDetail.getProdGrdCd());
        rights.setAgeAllowedFrom(commonService.getAllowedAge(appDetail.getTopMenuId(), appDetail.getProdGrdCd()));
        product.setRights(rights);

        // App
        App app = new App();
        product.setApp(app);
        app.setAid(appDetail.getAid());
        if (appDetail.getVmVer() != null)
            app.setSupportedOs("Android " + appDetail.getVmVer());
        app.setPackageName(appDetail.getApkPkgNm());
        app.setVersionCode(appDetail.getApkVer());
        app.setVersion(appDetail.getApkVerNm());
        app.setSize(appDetail.getFileSize());
        app.setDate(new Date(DisplayConstants.DP_DATE_SALE_REG, appDetail.getSaleStrtDt()));
        app.setDescriptionVideoUrl(appDetail.getDescVideoUrl());
		app.setApkSignedKeyHash(appDetail.getApkSignedKeyHash());

        // App - Provisioning
        app.setIsDeviceSupported(checkAvailability(param.getSdkCd(), param.getOsVersion(), appDetail) ? "Y" : "N");

        // Update History
        History history = new History();
        List<UpdateHistory> updateHistoryList = this.commonService.getUpdateList(param.getChannelId(), 1, 5);
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
        distributor.setSellerWebUrl(appDetail.getExpoSellerWebUrl());
        distributor.setPolicyInfoUrl(appDetail.getPolicyInfoUrl());
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

    private List<TenantProductInfo> getTenantProdList(String chnlId) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("prodId", chnlId);
        req.put("tenantIdList", ServicePropertyManager.getSupportTenantList());

        return commonDAO.queryForList("AppDetail.getTenantProductList", req, TenantProductInfo.class);
    }

    private boolean checkAvailability(Integer userSdkCd, String userOsVer, AppDetail appDetail) {
        if(appDetail.getIsDeviceSupp().equals("N") || StringUtils.isEmpty(appDetail.getVmVer()))
            return false;

        if (userSdkCd != null) {
            int min = appDetail.getSdkMin(),
                max = appDetail.getSdkMax();

            return min <= userSdkCd && userSdkCd <= max;
        }

        if (StringUtils.isNotEmpty(userOsVer)) {
            String simpleOsVer = "_NOT_";
            Matcher matcher = PATTERN_OSVER.matcher(userOsVer);
            if (matcher.matches()) {
                simpleOsVer = StringUtils.defaultString(matcher.group(1));
            }

            return appDetail.getVmVer().contains(simpleOsVer);
        }

        return false;
    }

    public static class TenantProductInfo {
        private String tenantId;
        private String mapgProdId;
        private String prodStatusCd;

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getMapgProdId() {
            return mapgProdId;
        }

        public void setMapgProdId(String mapgProdId) {
            this.mapgProdId = mapgProdId;
        }

        public String getProdStatusCd() {
            return prodStatusCd;
        }

        public void setProdStatusCd(String prodStatusCd) {
            this.prodStatusCd = prodStatusCd;
        }
    }

}
