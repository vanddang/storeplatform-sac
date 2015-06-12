/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistorySacIn;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EbookComicGenerator;

/**
 * DownloadComic Service 인터페이스(CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
public class DownloadComicServiceImpl implements DownloadComicService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private HistoryInternalSCI historyInternalSCI;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private EbookComicGenerator ebookComicGenerator;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private DisplayCommonService commonService;

    @Autowired
    private DownloadSupportService supportService;

    @Autowired
    private DownloadCommonService downloadCommonService;

	@SuppressWarnings("rawtypes")
	@Override
	public DownloadComicSacRes getDownloadComicInfo(SacRequestHeader header, DownloadComicSacReq comicReq) {

        List<Encryption> encryptionList = new ArrayList<Encryption>();
        StopWatch sw = new StopWatch();
        sw.start();

        MetaInfo dateInfo = (MetaInfo) commonDAO.queryForObject("Download.selectDownloadSystemDate", null);
        String sysDate = dateInfo.getSysDate();
        String reqExpireDate = dateInfo.getExpiredDate();

        String productId = comicReq.getProductId();
        logger.debug("----------------------------------------------------------------");
        logger.debug("[DownloadComicLog] productId : {}", productId);
        logger.debug("[DownloadComicLog] deviceKey : {}", comicReq.getDeviceKey());
        logger.debug("[DownloadComicLog] userKey : {}", comicReq.getUserKey());
        logger.debug("----------------------------------------------------------------");

        setRequest(comicReq, header);
        MetaInfo metaInfo = getComicMetaInfo(comicReq);

        logger.debug("----------------------------------------------------------------");
        logger.debug("[DownloadComicLog] scid : {}", metaInfo.getSubContentsId());
        logger.debug("----------------------------------------------------------------");

        // 암호화된 DL Token extra 필드에서 사용 할 공통 meta 정보
        metaInfo.setSystemId(header.getTenantHeader().getSystemId());
        metaInfo.setTenantId(header.getTenantHeader().getTenantId());
        downloadCommonService.validateVisitPathNm(metaInfo, comicReq.getVisitPathNm(), productId);

        Product product = new Product();
        setProduct(product, metaInfo);

        if (StringUtils.isNotEmpty(comicReq.getDeviceKey()) && StringUtils.isNotEmpty(comicReq.getUserKey())) {
            HistoryListSacInRes historyRes = null;
            boolean purchasePassFlag = true;

            try {
            	List<ProductListSacIn> prodIdList = makeProdIdList(metaInfo);
                HistoryListSacInReq historyReq = makeHistoryListSacInReq(comicReq, sysDate, prodIdList);
                loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq);
                historyRes = historyInternalSCI.searchHistoryList(historyReq);
            } catch (Exception ex) {
                purchasePassFlag = false;
                logger.error("구매내역 조회 연동 중 오류가 발생하였습니다.\n", ex);
            }

            logger.debug("----------------------------------------------------------------");
            logger.debug("[DownloadComicLog] purchasePassFlag : {}", purchasePassFlag);
            logger.debug("[DownloadComicLog] historyRes : {}", historyRes);
            logger.debug("----------------------------------------------------------------");

            if (purchasePassFlag && historyRes != null) {
                logger.debug("----------------------------------------------------------------");
                logger.debug("[DownloadComicLog] 구매건수 : {}", historyRes.getTotalCnt());
                logger.debug("----------------------------------------------------------------");

                String dwldStartDt = null; // 다운로드 시작일시
                String dwldExprDt = null; // 다운로드 만료일시
                String prchsCaseCd = null; // 선물 여부
                String prchsProdId = null; // 구매 상품ID
                String drmYn = null; // DRM 지원여부
                String permitDeviceYn = null; // 단말지원여부

                if (historyRes.getTotalCnt() > 0) {
                    List<Purchase> purchaseList = new ArrayList<Purchase>();

                    for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
                        dwldStartDt = historySacIn.getDwldStartDt();
                        dwldExprDt = historySacIn.getDwldExprDt();
                        prchsCaseCd = historySacIn.getPrchsCaseCd();
                        prchsProdId = historySacIn.getProdId();
                        drmYn = historySacIn.getDrmYn();
                        permitDeviceYn = historySacIn.getPermitDeviceYn();

						String prchsStateCheckedByDbTime = getDownloadPurchaseStateByDbTime(dwldStartDt, dwldExprDt);
						String prchsState = null;

                        // 구매상태 만료여부 확인
                        if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsStateCheckedByDbTime)) {
                            // 구매 및 선물 여부 확인
                            if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
                                prchsState = "payment";
                            } else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
                                prchsState = "gift";
                            }
                        }
                        loggingResponseOfPurchaseHistoryLocalSCI(historySacIn, prchsState);
                        addPurchaseIntoList(purchaseList, historySacIn, prchsState);
                        // 구매상태 만료여부 및 단말 지원여부 확인
                        if (DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsStateCheckedByDbTime) || !"Y".equals(permitDeviceYn)) {
                        	continue;
                        }
                        SearchDeviceIdSacReq deviceReq = null;
                        SearchDeviceIdSacRes deviceRes = null;
                        boolean memberPassFlag = true;

                        try {
                            deviceReq = makeSearchDeviceIdSacReq(comicReq, header);
                            deviceRes = deviceSCI.searchDeviceId(deviceReq);
                        } catch (Exception ex) {
                            memberPassFlag = false;
                            logger.error("단말정보 조회 연동 중 오류가 발생하였습니다.\n", ex);
                        }
                        logger.debug("----------------------------------------------------------------");
                        logger.debug("[DownloadComicLog] memberPassFlag : {}", memberPassFlag);
                        logger.debug("[DownloadComicLog] deviceRes : {}", deviceRes);
                        logger.debug("----------------------------------------------------------------");

                        // MDN 인증여부 확인 (2014.05.22 회원 API 변경에 따른 추가)
						if (!"Y".equals(deviceRes.getAuthYn())) {
							logger.debug("##### [SAC DSP LocalSCI] NOT VALID DEVICE_ID : {}", deviceRes.getDeviceId());
						} else if (memberPassFlag && deviceRes != null) {
                        	setMetaInfo(comicReq, reqExpireDate, metaInfo, prchsProdId, drmYn, historySacIn, prchsState, deviceRes);
                            Encryption encryption = supportService.generateEncryption(metaInfo, prchsProdId);
                            encryptionList.add(encryption);
                            loggingEncResult(encryption);
                        }
                        product.setPurchaseList(purchaseList);
                        if (!encryptionList.isEmpty()) {
                            product.setDl(encryptionList);
                        }
                        break;
                    }
                }
            }
        }

        DownloadComicSacRes comicRes = makeResponse(product);
        sw.stop();
        supportService.logDownloadResult(comicReq.getUserKey(), comicReq.getDeviceKey(), productId, encryptionList, sw.getTime());

        return comicRes;
    }

	private void setRequest(DownloadComicSacReq comicReq, SacRequestHeader header) {
		comicReq.setTenantId(header.getTenantHeader().getTenantId());
        comicReq.setLangCd(header.getTenantHeader().getLangCd());
        comicReq.setDeviceModelCd(header.getDeviceHeader().getModel());
        comicReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
        comicReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
	}

	private void setProduct(Product product, MetaInfo metaInfo) {
		// 상품 ID 정보
        List<Identifier> identifierList = new ArrayList<Identifier>();
        identifierList.add(commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId()));
        identifierList.add(commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId()));
        product.setIdentifierList(identifierList);

        product.setTitle(commonGenerator.generateTitle(metaInfo));
        product.setChnlProdNm(metaInfo.getChnlProdNm());
        product.setProductExplain(metaInfo.getProdBaseDesc());
        product.setProductDetailExplain(metaInfo.getProdDtlDesc());
        product.setSourceList(commonGenerator.generateDownloadSourceList(metaInfo));
        product.setMenuList(commonGenerator.generateMenuList(metaInfo));
        product.setBook(ebookComicGenerator.generateForDownloadBook(metaInfo));
        product.setPrice(commonGenerator.generatePrice(metaInfo));
        product.setRights(commonGenerator.generateRights(metaInfo));
        product.setDistributor(commonGenerator.generateDistributor(metaInfo));
        product.setContributor(ebookComicGenerator.generateComicContributor(metaInfo));
	}

	private void loggingEncResult(Encryption encryption) {
		logger.debug("-----------------------------------------------------------");
		logger.debug("[DownloadComicLog] token : {}", encryption.getToken());
		logger.debug("[DownloadComicLog] keyIdx : {}", encryption.getKeyIndex());
		logger.debug("-----------------------------------------------------------");
	}

	private DownloadComicSacRes makeResponse(Product product) {
		CommonResponse commonResponse = new CommonResponse();
        commonResponse.setTotalCount(1);

        DownloadComicSacRes comicRes = new DownloadComicSacRes();
        comicRes.setProduct(product);
        comicRes.setCommonResponse(commonResponse);
		return comicRes;
	}

	private void setMetaInfo(DownloadComicSacReq comicReq, String reqExpireDate, MetaInfo metaInfo, String prchsProdId, String drmYn,
			HistorySacIn historySacIn, String prchsState, SearchDeviceIdSacRes deviceRes) {
		String deviceId = deviceRes.getDeviceId();
		String deviceIdType = commonService.getDeviceIdType(deviceId);

		metaInfo.setPurchaseId(historySacIn.getPrchsId());
		metaInfo.setPurchaseProdId(historySacIn.getProdId());
		metaInfo.setPurchaseDt(historySacIn.getPrchsDt());
		metaInfo.setPurchaseState(prchsState);
		metaInfo.setPurchaseDwldExprDt(historySacIn.getDwldExprDt());
		metaInfo.setPurchasePrice(Integer.parseInt(historySacIn.getProdAmt()));
		metaInfo.setExpiredDate(reqExpireDate);
		metaInfo.setUseExprDt(historySacIn.getUseExprDt()); // 이용 만료일시
		metaInfo.setUserKey(comicReq.getUserKey());
		metaInfo.setDeviceKey(comicReq.getDeviceKey());
		if (StringUtils.isNotBlank(comicReq.getAdditionalMsisdn())) {
			metaInfo.setDeviceType(DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN);
			metaInfo.setDeviceSubKey(comicReq.getAdditionalMsisdn());
		} else {
			metaInfo.setDeviceType(deviceIdType);
			metaInfo.setDeviceSubKey(deviceId);
		}
		metaInfo.setPurchaseHide(historySacIn.getHidingYn()); // 구매내역 숨김 여부
		metaInfo.setUpdateAlarm(historySacIn.getAlarmYn()); // 업데이트 알람 수신 여부

		// 구매시점 DRM 여부값으로 세팅
		if (StringUtils.isNotEmpty(drmYn)) {
		    metaInfo.setDrmYn(drmYn);
		}
		// 소장, 대여 구분(Store : 소장, Play : 대여)
		if (prchsProdId.equals(metaInfo.getStoreProdId())) {
			metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
		} else {
			metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
		}
	}

	private void addPurchaseIntoList(List<Purchase> purchaseList, HistorySacIn historySacIn, String prchsState) {
		Purchase p = commonGenerator.generatePurchase(historySacIn.getPrchsId(),
													historySacIn.getProdId(),
		            								prchsState,
		            								historySacIn.getPrchsDt(),
		            								historySacIn.getDwldExprDt());
		purchaseList.add(p);
	}

	private MetaInfo getComicMetaInfo(DownloadComicSacReq comicReq) {
        MetaInfo metaInfo = (MetaInfo) commonDAO.queryForObject("Download.selectDownloadComicInfo", comicReq);
        if (metaInfo == null)
            throw new StorePlatformException("SAC_DSP_0009");
		return metaInfo;
	}

	private SearchDeviceIdSacReq makeSearchDeviceIdSacReq(DownloadComicSacReq comicReq, SacRequestHeader header) {
		SearchDeviceIdSacReq deviceReq = new SearchDeviceIdSacReq();
		deviceReq.setUserKey(comicReq.getUserKey());
		deviceReq.setDeviceKey(comicReq.getDeviceKey());
		deviceReq.setTenantId(header.getTenantHeader().getTenantId());

		logger.debug("--------------------------------------------------------------");
		logger.debug("[DownloadComicLog] 단말정보 조회 요청 파라미터");
		logger.debug("--------------------------------------------------------------");
		logger.debug("[DownloadComicLog] userKey : {}", deviceReq.getUserKey());
		logger.debug("[DownloadComicLog] deviceKey : {}", deviceReq.getDeviceKey());
		logger.debug("--------------------------------------------------------------");
		return deviceReq;
	}

	private void loggingResponseOfPurchaseHistoryLocalSCI(HistorySacIn historySacIn, String prchsState) {
		logger.debug("----------------------------------------------------------------");
		logger.debug("[DownloadComicLog] prchsId : {}", historySacIn.getPrchsId());
		logger.debug("[DownloadComicLog] prchsDt : {}", historySacIn.getPrchsDt());
		logger.debug("[DownloadComicLog] useExprDt : {}", historySacIn.getUseExprDt());
		logger.debug("[DownloadComicLog] dwldStartDt : {}", historySacIn.getDwldStartDt());
		logger.debug("[DownloadComicLog] dwldExprDt : {}", historySacIn.getDwldExprDt());
		logger.debug("[DownloadComicLog] prchsCaseCd : {}", historySacIn.getPrchsCaseCd());
		logger.debug("[DownloadComicLog] prchsState : {}", prchsState);
		logger.debug("[DownloadComicLog] prchsProdId : {}", historySacIn.getProdId());
		logger.debug("[DownloadComicLog] prchsPrice : {}", historySacIn.getProdAmt());
		logger.debug("[DownloadComicLog] drmYn : {}", historySacIn.getDrmYn());
		logger.debug("[DownloadComicLog] permitDeviceYn : {}", historySacIn.getPermitDeviceYn());
		logger.debug("----------------------------------------------------------------");
	}

	@SuppressWarnings("rawtypes")
	private String getDownloadPurchaseStateByDbTime(String dwldStartDt, String dwldExprDt) {
		DownloadComicSacReq req = new DownloadComicSacReq();
		req.setDwldStartDt(dwldStartDt);
		req.setDwldExprDt(dwldExprDt);

		HashMap map = (HashMap) commonDAO.queryForObject("Download.getDownloadPurchaseState", req);
		return (String) map.get("PURCHASE_STATE");
	}

	private List<ProductListSacIn> makeProdIdList(MetaInfo metaInfo) {
		List<ProductListSacIn>  productList = new ArrayList<ProductListSacIn>();
		ProductListSacIn productListSacIn = new ProductListSacIn();
		productListSacIn.setProdId(metaInfo.getPartProdId());
		productList.add(productListSacIn);
		return productList;
	}

	private void loggingParamsForPurchaseHistoryLocalSCI(List<ProductListSacIn> productList, HistoryListSacInReq historyReq) {
		logger.debug("----------------------------------------------------------------");
		logger.debug("[DownloadComicLog] 구매내역 조회 요청 파라미터");
		logger.debug("----------------------------------------------------------------");
		logger.debug("[DownloadComicLog] tenantId : {}", historyReq.getTenantId());
		logger.debug("[DownloadComicLog] userKey : {}", historyReq.getUserKey());
		logger.debug("[DownloadComicLog] deviceKey : {}", historyReq.getDeviceKey());
		logger.debug("[DownloadComicLog] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
		logger.debug("[DownloadComicLog] prchsProdType : {}", historyReq.getPrchsProdType());
		logger.debug("[DownloadComicLog] startDt : {}", historyReq.getStartDt());
		logger.debug("[DownloadComicLog] endDt : {}", historyReq.getEndDt());
		logger.debug("[DownloadComicLog] prodId : {}", productList.get(0).getProdId());
		logger.debug("----------------------------------------------------------------");
	}

	private HistoryListSacInReq makeHistoryListSacInReq(DownloadComicSacReq comicReq, String sysDate, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
		historyReq.setTenantId(comicReq.getTenantId());
		historyReq.setUserKey(comicReq.getUserKey());
		historyReq.setDeviceKey(comicReq.getDeviceKey());
		historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
		historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
		historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
		historyReq.setPrchsStatusCd(DisplayConstants.PRCHS_STSTUS_COMPLETE_CD);
		historyReq.setEndDt(sysDate);
		historyReq.setOffset(1);
		historyReq.setCount(1000);
		historyReq.setProductList(productList);
		return historyReq;
	}
}
