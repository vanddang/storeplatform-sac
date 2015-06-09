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

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacRes;
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
 * DownloadEbook Service 인터페이스(CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
public class DownloadEbookServiceImpl implements DownloadEbookService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private HistoryInternalSCI historyInternalSCI;

	@Autowired
	private CommonMetaInfoGenerator commonMetaInfoGenerator;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.download.service.DownloadEbookService#getDownloadEbookInfo(com.skplanet
	 * .storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public DownloadEbookSacRes getDownloadEbookInfo(SacRequestHeader header, DownloadEbookSacReq ebookReq) {

        List<Encryption> encryptionList = new ArrayList<Encryption>();

        StopWatch sw = new StopWatch();
        sw.start();

        // 현재일시 및 요청만료일시 조회
		MetaInfo metaInfo = (MetaInfo) commonDAO.queryForObject("Download.selectDownloadSystemDate", null);

		String sysDate = metaInfo.getSysDate();
		String reqExpireDate = metaInfo.getExpiredDate();
		metaInfo = null;

		String idType = ebookReq.getIdType();
		String productId = ebookReq.getProductId();
		String deviceKey = ebookReq.getDeviceKey();
		String userKey = ebookReq.getUserKey();

		logger.debug("----------------------------------------------------------------");
		logger.debug("[DownloadEbookLog] idType : {}", idType);
		logger.debug("[DownloadEbookLog] productId : {}", productId);
		logger.debug("[DownloadEbookLog] deviceKey : {}", deviceKey);
		logger.debug("[DownloadEbookLog] userKey : {}", userKey);
		logger.debug("----------------------------------------------------------------");

		// ID유형 유효값 체크
		if (!"channel".equals(idType) && !"episode".equals(idType)) {
			throw new StorePlatformException("SAC_DSP_0003", "idType", idType);
		}

		// 헤더정보 세팅
		ebookReq.setTenantId(header.getTenantHeader().getTenantId());
		ebookReq.setLangCd(header.getTenantHeader().getLangCd());
		ebookReq.setDeviceModelCd(header.getDeviceHeader().getModel());
		ebookReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		ebookReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

		// ebook 상품 정보 조회(for download)
		metaInfo = (MetaInfo) commonDAO.queryForObject("Download.selectDownloadEbookInfo", ebookReq);

		if (metaInfo == null) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		if ("channel".equals(idType) && DisplayConstants.DP_SERIAL_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())) {
			// 단품인 상품만 조회가능 합니다.
			throw new StorePlatformException("SAC_DSP_0013");
		}

		logger.debug("----------------------------------------------------------------");
		logger.debug("[DownloadEbookLog] scid : {}", metaInfo.getSubContentsId());
		logger.debug("----------------------------------------------------------------");

		/*
		 * 암호화된 DL Token extra 필드에서 사용 할 공통 meta 정보
		 */
		metaInfo.setSystemId(header.getTenantHeader().getSystemId());
        metaInfo.setTenantId(header.getTenantHeader().getTenantId());
		downloadCommonService.validateVisitPathNm(metaInfo, ebookReq.getVisitPathNm(), productId);

		Product product = new Product();

		// 상품 ID 정보
		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(commonMetaInfoGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId()));
		identifierList.add(commonMetaInfoGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId()));

		product.setIdentifierList(identifierList);
		product.setTitle(commonMetaInfoGenerator.generateTitle(metaInfo));
		product.setChnlProdNm(metaInfo.getChnlProdNm());
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		product.setSourceList(commonMetaInfoGenerator.generateDownloadSourceList(metaInfo));
		product.setMenuList(commonMetaInfoGenerator.generateMenuList(metaInfo));
		product.setBook(ebookComicGenerator.generateForDownloadBook(metaInfo));
		product.setRights(commonMetaInfoGenerator.generateRights(metaInfo));
		product.setDistributor(commonMetaInfoGenerator.generateDistributor(metaInfo));
		product.setContributor(ebookComicGenerator.generateEbookContributor(metaInfo));

		if (StringUtils.isNotEmpty(deviceKey) && StringUtils.isNotEmpty(userKey)) {
			HistoryListSacInRes historyRes = null;
			boolean purchasePassFlag = true;

			try {
				List<ProductListSacIn> prodIdList = makeProdIdList(metaInfo);
				HistoryListSacInReq historyReq = makeHistoryListSacInReq(ebookReq, sysDate, prodIdList);
				loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq);
				historyRes = historyInternalSCI.searchHistoryList(historyReq);
			} catch (Exception ex) {
				purchasePassFlag = false;
				logger.error("구매내역 조회 연동 중 오류가 발생하였습니다.\n", ex);
			}

			logger.debug("----------------------------------------------------------------");
			logger.debug("[DownloadEbookLog] purchasePassFlag : {}", purchasePassFlag);
			logger.debug("[DownloadEbookLog] historyRes : {}", historyRes);
			logger.debug("----------------------------------------------------------------");

			if (purchasePassFlag && historyRes != null) {
				logger.debug("----------------------------------------------------------------");
				logger.debug("[DownloadEbookLog] 구매건수 : {}", historyRes.getTotalCnt());
				logger.debug("----------------------------------------------------------------");

				String useExprDt = null; // 이용 만료일시
				String dwldStartDt = null; // 다운로드 시작일시
				String dwldExprDt = null; // 다운로드 만료일시
				String prchsCaseCd = null; // 선물 여부
				String prchsProdId = null; // 구매 상품ID
				String drmYn = null; // DRM 지원여부
				String permitDeviceYn = null; // 단말지원여부
				String purchaseHide = null; // 구매내역 숨김 여부
				String updateAlarm = null; // 업데이트 알람 수신 여부

				if (historyRes.getTotalCnt() > 0) {
					List<Purchase> purchaseList = new ArrayList<Purchase>();

					for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
						useExprDt = historySacIn.getUseExprDt();
						dwldStartDt = historySacIn.getDwldStartDt();
						dwldExprDt = historySacIn.getDwldExprDt();
						prchsCaseCd = historySacIn.getPrchsCaseCd();
						prchsProdId = historySacIn.getProdId();
						drmYn = historySacIn.getDrmYn();
						permitDeviceYn = historySacIn.getPermitDeviceYn();
						purchaseHide = historySacIn.getHidingYn();
						updateAlarm = historySacIn.getAlarmYn();

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

						metaInfo.setPurchaseId(historySacIn.getPrchsId());
						metaInfo.setPurchaseProdId(historySacIn.getProdId());
						metaInfo.setPurchaseDt(historySacIn.getPrchsDt());
						metaInfo.setPurchaseState(prchsState);
						metaInfo.setPurchaseDwldExprDt(historySacIn.getDwldExprDt());
						metaInfo.setPurchasePrice(Integer.parseInt(historySacIn.getProdAmt()));

						// 구매 정보
						purchaseList.add(commonMetaInfoGenerator.generatePurchase(metaInfo));

						// 구매상태 만료여부 및 단말 지원여부 확인
						if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsStateCheckedByDbTime) && "Y".equals(permitDeviceYn)) {
							String deviceId = null; // Device Id
							String deviceIdType = null; // Device Id 유형
							SearchDeviceIdSacReq deviceReq = null;
							SearchDeviceIdSacRes deviceRes = null;
							boolean memberPassFlag = true;

							try {
								deviceReq = new SearchDeviceIdSacReq();
								deviceReq.setUserKey(ebookReq.getUserKey());
								deviceReq.setDeviceKey(ebookReq.getDeviceKey());
                                deviceReq.setTenantId(header.getTenantHeader().getTenantId());

								logger.debug("--------------------------------------------------------------");
								logger.debug("[DownloadEbookLog] 단말정보 조회 요청 파라미터");
								logger.debug("--------------------------------------------------------------");
								logger.debug("[DownloadEbookLog] userKey : {}", deviceReq.getUserKey());
								logger.debug("[DownloadEbookLog] deviceKey : {}", deviceReq.getDeviceKey());
								logger.debug("--------------------------------------------------------------");

								deviceRes = deviceSCI.searchDeviceId(deviceReq);
							} catch (Exception ex) {
								memberPassFlag = false;
								logger.error("단말정보 조회 연동 중 오류가 발생하였습니다.\n", ex);
							}

							logger.debug("----------------------------------------------------------------");
							logger.debug("[DownloadEbookLog] memberPassFlag : {}", memberPassFlag);
							logger.debug("[DownloadEbookLog] deviceRes : {}", deviceRes);
							logger.debug("----------------------------------------------------------------");

							if (memberPassFlag && deviceRes != null) {
								// MDN 인증여부 확인 (2014.05.22 회원 API 변경에 따른 추가)
								if ("Y".equals(deviceRes.getAuthYn())) {
									deviceId = deviceRes.getDeviceId();
									deviceIdType = commonService.getDeviceIdType(deviceId);

									metaInfo.setExpiredDate(reqExpireDate);
									metaInfo.setUseExprDt(useExprDt);
									metaInfo.setUserKey(userKey);
									metaInfo.setDeviceKey(deviceKey);
									if (StringUtils.isNotBlank(ebookReq.getAdditionalMsisdn())) {
                                    	metaInfo.setDeviceType(DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN);
                                    	metaInfo.setDeviceSubKey(ebookReq.getAdditionalMsisdn());
                                    } else {
                                    	metaInfo.setDeviceType(deviceIdType);
                                    	metaInfo.setDeviceSubKey(deviceId);
                                    }
									metaInfo.setPurchaseHide(purchaseHide);
									metaInfo.setUpdateAlarm(updateAlarm);

									// 구매시점 DRM 여부값으로 세팅
									if (StringUtils.isNotEmpty(drmYn)) {
										metaInfo.setStoreDrmYn(drmYn);
										metaInfo.setPlayDrmYn(drmYn);
									}

									// 소장, 대여 구분(Store : 소장, Play : 대여)
									if (prchsProdId.equals(metaInfo.getStoreProdId())) {
										metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
										metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
									} else {
										metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
										metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
									}

									// 암호화 정보 (JSON)
                                    Encryption encryption = supportService.generateEncryption(metaInfo, prchsProdId);
									encryptionList.add(encryption);

									logger.debug("-----------------------------------------------------------");
									logger.debug("[DownloadEbookLog] token : {}", encryption.getToken());
									logger.debug("[DownloadEbookLog] keyIdx : {}", encryption.getKeyIndex());
									logger.debug("-----------------------------------------------------------");
								} else {
									logger.debug("##### [SAC DSP LocalSCI] userKey : {}", deviceReq.getUserKey());
									logger.debug("##### [SAC DSP LocalSCI] deviceKey : {}",	deviceReq.getDeviceKey());
									logger.debug("##### [SAC DSP LocalSCI] NOT VALID DEVICE_ID : {} ", deviceRes.getDeviceId());
								}
							}
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
			}
		}

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(1);

		DownloadEbookSacRes ebookRes = new DownloadEbookSacRes();
		ebookRes.setProduct(product);
		ebookRes.setCommonResponse(commonResponse);

        sw.stop();
        supportService.logDownloadResult(userKey, deviceKey, productId, encryptionList, sw.getTime());

        return ebookRes;
	}

	private void loggingResponseOfPurchaseHistoryLocalSCI(HistorySacIn historySacIn, String prchsState) {
		logger.debug("----------------------------------------------------------------");
		logger.debug("[DownloadEbookLog] prchsId : {}", historySacIn.getPrchsId());
		logger.debug("[DownloadEbookLog] prchsDt : {}", historySacIn.getPrchsDt());
		logger.debug("[DownloadEbookLog] useExprDt : {}", historySacIn.getUseExprDt());
		logger.debug("[DownloadEbookLog] dwldStartDt : {}", historySacIn.getDwldStartDt());
		logger.debug("[DownloadEbookLog] dwldExprDt : {}", historySacIn.getDwldExprDt());
		logger.debug("[DownloadEbookLog] prchsCaseCd : {}", historySacIn.getPrchsCaseCd());
		logger.debug("[DownloadEbookLog] prchsState : {}", prchsState);
		logger.debug("[DownloadEbookLog] prchsProdId : {}", historySacIn.getProdId());
		logger.debug("[DownloadEbookLog] prchsPrice : {}", historySacIn.getProdAmt());
		logger.debug("[DownloadEbookLog] drmYn : {}", historySacIn.getDrmYn());
		logger.debug("[DownloadEbookLog] permitDeviceYn : {}", historySacIn.getPermitDeviceYn());
		logger.debug("----------------------------------------------------------------");
	}

	@SuppressWarnings("rawtypes")
	private String getDownloadPurchaseStateByDbTime(String dwldStartDt, String dwldExprDt) {
		DownloadEbookSacReq req = new DownloadEbookSacReq();
		req.setDwldStartDt(dwldStartDt);
		req.setDwldExprDt(dwldExprDt);

		HashMap map = (HashMap) commonDAO.queryForObject("Download.getDownloadPurchaseState", req);
		return (String) map.get("PURCHASE_STATE");
	}

	private List<ProductListSacIn> makeProdIdList(MetaInfo metaInfo) {
		List<ProductListSacIn>  productList = new ArrayList<ProductListSacIn>();

		// 소장 상품ID
		ProductListSacIn storeProduct = new ProductListSacIn();
		storeProduct.setProdId(metaInfo.getStoreProdId());
		productList.add(storeProduct);

		// 대여 상품ID
		ProductListSacIn playProduct = new ProductListSacIn();
		playProduct.setProdId(metaInfo.getPlayProdId());
		productList.add(playProduct);
		return productList;
	}

	private void loggingParamsForPurchaseHistoryLocalSCI(List<ProductListSacIn> productList, HistoryListSacInReq historyReq) {
		logger.debug("----------------------------------------------------------------");
		logger.debug("[DownloadEbookLog] 구매내역 조회 요청 파라미터");
		logger.debug("----------------------------------------------------------------");
		logger.debug("[DownloadEbookLog] tenantId : {}", historyReq.getTenantId());
		logger.debug("[DownloadEbookLog] userKey : {}", historyReq.getUserKey());
		logger.debug("[DownloadEbookLog] deviceKey : {}", historyReq.getDeviceKey());
		logger.debug("[DownloadEbookLog] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
		logger.debug("[DownloadEbookLog] prchsProdType : {}", historyReq.getPrchsProdType());
		logger.debug("[DownloadEbookLog] startDt : {}", historyReq.getStartDt());
		logger.debug("[DownloadEbookLog] endDt : {}", historyReq.getEndDt());
		logger.debug("[DownloadEbookLog] prodId[0] : {}", productList.get(0).getProdId());
		logger.debug("[DownloadEbookLog] prodId[1] : {}", productList.get(1).getProdId());
		logger.debug("----------------------------------------------------------------");
	}

	private HistoryListSacInReq makeHistoryListSacInReq(DownloadEbookSacReq ebookReq, String sysDate, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
		historyReq.setTenantId(ebookReq.getTenantId());
		historyReq.setUserKey(ebookReq.getUserKey());
		historyReq.setDeviceKey(ebookReq.getDeviceKey());
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
