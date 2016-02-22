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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DownloadComic Service 인터페이스(CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
public class DownloadComicServiceImpl implements DownloadComicService {
	private final Logger log = LoggerFactory.getLogger(getClass());

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
        log.debug("----------------------------------------------------------------");
        log.debug("[DownloadComicLog] productId : {}", productId);
        log.debug("[DownloadComicLog] deviceKey : {}", comicReq.getDeviceKey());
        log.debug("[DownloadComicLog] userKey : {}", comicReq.getUserKey());
        log.debug("----------------------------------------------------------------");

        setRequest(comicReq, header);
        MetaInfo metaInfo = getComicMetaInfo(comicReq);

        log.debug("----------------------------------------------------------------");
        log.debug("[DownloadComicLog] scid : {}", metaInfo.getSubContentsId());
        log.debug("----------------------------------------------------------------");

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
                HistoryListSacInReq historyReq = makeHistoryListSacInReq(comicReq, prodIdList);
                loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq);
                historyRes = historyInternalSCI.searchHistoryList(historyReq);
            } catch (Exception ex) {
                purchasePassFlag = false;
                log.error("구매내역 조회 연동 중 오류가 발생하였습니다.\n", ex);
            }

            log.debug("----------------------------------------------------------------");
            log.debug("[DownloadComicLog] purchasePassFlag : {}", purchasePassFlag);
            log.debug("[DownloadComicLog] historyRes : {}", historyRes);
            log.debug("----------------------------------------------------------------");
            if (purchasePassFlag && historyRes != null) {
                log.debug("----------------------------------------------------------------");
                log.debug("[DownloadComicLog] 구매건수 : {}", historyRes.getTotalCnt());
                log.debug("----------------------------------------------------------------");

                List<Purchase> purchaseList = new ArrayList<Purchase>();


                for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
                    String prchsProdId = historySacIn.getProdId();
                    String permitDeviceYn = historySacIn.getPermitDeviceYn();
					String prchsState = setPrchsState(historySacIn);

                    loggingResponseOfPurchaseHistoryLocalSCI(historySacIn, prchsState);
					if (supportService.resetExprDtOfGift(historySacIn, header, comicReq.getUserKey(), comicReq.getDeviceKey(),
							prchsProdId, sysDate, prchsState)) {
						prchsState = setPrchsState(historySacIn); // 선물인경우 만료기한이 update 되었을 수 있어 만료여부 다시 체크
					}
					addPurchaseIntoList(purchaseList, historySacIn, prchsState);
                    // 구매상태 만료여부 및 단말 지원여부 확인
                    if (DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState) || !"Y".equals(permitDeviceYn)) {
                    	continue;
                    }

                    SearchDeviceIdSacRes deviceRes = null;
                    try {
						SearchDeviceIdSacReq deviceReq = makeSearchDeviceIdSacReq(comicReq, header);
                        deviceRes = deviceSCI.searchDeviceId(deviceReq);
                    } catch (Exception ex) {
                        log.error("단말정보 조회 연동 중 오류가 발생하였습니다.\n{}", ex);
                    }
                    log.debug("----------------------------------------------------------------");
                    log.debug("[DownloadComicLog] deviceRes : {}", deviceRes);
                    log.debug("----------------------------------------------------------------");

					if (deviceRes == null || !"Y".equals(deviceRes.getAuthYn()))
						break;

					setMetaInfo(comicReq, reqExpireDate, metaInfo, prchsProdId, historySacIn, prchsState, deviceRes);
					Encryption encryption = supportService.generateEncryption(metaInfo, prchsProdId);
					encryptionList.add(encryption);
					loggingEncResult(encryption);

					// 구매 정보
                    product.setPurchaseList(purchaseList);

					// 암호화 정보
                    if (!encryptionList.isEmpty()) {
                        product.setDl(encryptionList);
                    }
                    break;
                }
            }
        }

        DownloadComicSacRes comicRes = makeResponse(product);
        sw.stop();
        supportService.logDownloadResult(comicReq.getUserKey(), comicReq.getDeviceKey(), productId, encryptionList, sw.getTime());

        return comicRes;
    }

	private String setPrchsState(HistorySacIn historySacIn) {
		String prchsState = getDownloadPurchaseStateByDbTime(historySacIn);

		// 구매상태 만료여부 확인
		if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
		    // 구매 및 선물 여부 확인
		    if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(historySacIn.getPrchsCaseCd())) {
		        prchsState = "payment";
		    } else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(historySacIn.getPrchsCaseCd())) {
		        prchsState = "gift";
		    }
		}
		return prchsState;
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
        product.setVerticalYn(metaInfo.getVerticalYn());
	}

	private void loggingEncResult(Encryption encryption) {
		log.debug("-----------------------------------------------------------");
		log.debug("[DownloadComicLog] token : {}", encryption.getToken());
		log.debug("[DownloadComicLog] keyIdx : {}", encryption.getKeyIndex());
		log.debug("-----------------------------------------------------------");
	}

	private DownloadComicSacRes makeResponse(Product product) {
		CommonResponse commonResponse = new CommonResponse();
        commonResponse.setTotalCount(1);

        DownloadComicSacRes comicRes = new DownloadComicSacRes();
        comicRes.setProduct(product);
        comicRes.setCommonResponse(commonResponse);
		return comicRes;
	}

	private void setMetaInfo(DownloadComicSacReq comicReq, String reqExpireDate, MetaInfo metaInfo, String prchsProdId,
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
		if (StringUtils.isNotEmpty(historySacIn.getDrmYn())) {
		    metaInfo.setDrmYn(historySacIn.getDrmYn());

            if ("Y".equals(historySacIn.getDrmYn())
					&& !supportService.isTfreemiumPurchase(historySacIn.getPrchsReqPathCd()) ) {
				// 구매 경로가 Tfreemium 제외하고 호출되도록 수정한다.
				supportService.mapPurchaseDrmInfo(metaInfo);
			}
		}
        else {
            // 소장, 대여 구분(Store : 소장, Play : 대여)
            if (prchsProdId.equals(metaInfo.getStoreProdId())) {
                metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
            } else {
                metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
            }
        }

		/**
		 * 컨텐츠 DRM Key setting.
		 */
		metaInfo.setDrmKey(this.setDrmKey(comicReq.getUserType(), metaInfo.getDrmYn(), deviceRes.getMdn(), comicReq.getUserKey()));
	}

	private void addPurchaseIntoList(List<Purchase> purchaseList, HistorySacIn historySacIn, String prchsState) {
		Purchase p = commonGenerator.generatePurchase(prchsState, historySacIn);
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

		log.debug("--------------------------------------------------------------");
		log.debug("[DownloadComicLog] 단말정보 조회 요청 파라미터");
		log.debug("--------------------------------------------------------------");
		log.debug("[DownloadComicLog] userKey : {}", deviceReq.getUserKey());
		log.debug("[DownloadComicLog] deviceKey : {}", deviceReq.getDeviceKey());
		log.debug("--------------------------------------------------------------");
		return deviceReq;
	}

	private void loggingResponseOfPurchaseHistoryLocalSCI(HistorySacIn historySacIn, String prchsState) {
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadComicLog] prchsId : {}", historySacIn.getPrchsId());
		log.debug("[DownloadComicLog] prchsDt : {}", historySacIn.getPrchsDt());
		log.debug("[DownloadComicLog] useExprDt : {}", historySacIn.getUseExprDt());
		log.debug("[DownloadComicLog] dwldStartDt : {}", historySacIn.getDwldStartDt());
		log.debug("[DownloadComicLog] dwldExprDt : {}", historySacIn.getDwldExprDt());
		log.debug("[DownloadComicLog] prchsCaseCd : {}", historySacIn.getPrchsCaseCd());
		log.debug("[DownloadComicLog] prchsState : {}", prchsState);
		log.debug("[DownloadComicLog] prchsProdId : {}", historySacIn.getProdId());
		log.debug("[DownloadComicLog] prchsPrice : {}", historySacIn.getProdAmt());
		log.debug("[DownloadComicLog] drmYn : {}", historySacIn.getDrmYn());
		log.debug("[DownloadComicLog] permitDeviceYn : {}", historySacIn.getPermitDeviceYn());
		log.debug("----------------------------------------------------------------");
	}

	@SuppressWarnings("rawtypes")
	private String getDownloadPurchaseStateByDbTime(HistorySacIn historySacIn) {
		DownloadComicSacReq req = new DownloadComicSacReq();
		req.setDwldStartDt(historySacIn.getDwldStartDt());
		req.setDwldExprDt(historySacIn.getDwldExprDt());

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
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadComicLog] 구매내역 조회 요청 파라미터");
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadComicLog] tenantId : {}", historyReq.getTenantId());
		log.debug("[DownloadComicLog] userKey : {}", historyReq.getUserKey());
		log.debug("[DownloadComicLog] deviceKey : {}", historyReq.getDeviceKey());
		log.debug("[DownloadComicLog] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
		log.debug("[DownloadComicLog] prchsProdType : {}", historyReq.getPrchsProdType());
		log.debug("[DownloadComicLog] startDt : {}", historyReq.getStartDt());
		log.debug("[DownloadComicLog] endDt : {}", historyReq.getEndDt());
		log.debug("[DownloadComicLog] prodId : {}", productList.get(0).getProdId());
		log.debug("----------------------------------------------------------------");
	}

	private HistoryListSacInReq makeHistoryListSacInReq(DownloadComicSacReq comicReq, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
		historyReq.setTenantId(comicReq.getTenantId());
		historyReq.setUserKey(comicReq.getUserKey());
		historyReq.setDeviceKey(comicReq.getDeviceKey());
		historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
		historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
		historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
		historyReq.setPrchsStatusCd(DisplayConstants.PRCHS_STSTUS_COMPLETE_CD);
		historyReq.setEndDt("20991231235959");
		historyReq.setOffset(1);
		historyReq.setCount(1000);
		historyReq.setProductList(productList);
		return historyReq;
	}

	/**
	 * drmKey 값 셋팅 (기존 MDN을 DRM키로 사용하였으나 MDN이 없는 Wi-Fi 전용 단말을 위해 DRM키 정책을 만든다.)
	 * @param userType
	 * @param applyDrm
	 * @param mdn
	 * @param userKey
	 * @return
	 */
	private String setDrmKey(String userType, String applyDrm, String mdn, String userKey) {

		/**
		 * userType 이 null 이면 drmKey = 세팅하지않음.
		 */
		if(StringUtils.isBlank(userType)) {
			return null;
		}

		/**
		 * applyDrm 값이 "Y" 이면서, 모바일 회원일 경우 drmKey = mdn.
		 */
		if(StringUtils.equals(applyDrm, "Y") && StringUtils.equals(userType, "US011501")) {
			return mdn;
		}

		/**
		 *  applyDrm 값이 "Y" 이면서, IDP 회원이거나 OneId 회원이거나 기타 등등  drmKey = userKey.
		 */
		if(StringUtils.equals(applyDrm, "Y") && !StringUtils.equals(userType, "US011501")) {
			return userKey;
		}

		/**
		 * 위에 걸리는 항목 없으면 drmKey를 내리지 않는다.
		 */
		return null;

	}
}
