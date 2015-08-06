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
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.*;
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
 * DownloadEbook Service 인터페이스(CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
public class DownloadEbookServiceImpl implements DownloadEbookService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
		MetaInfo dateInfo = (MetaInfo) commonDAO.queryForObject("Download.selectDownloadSystemDate", null);
		String sysDate = dateInfo.getSysDate();
		String reqExpireDate = dateInfo.getExpiredDate();

		String idType = ebookReq.getIdType();
		String productId = ebookReq.getProductId();
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadEbookLog] idType : {}", idType);
		log.debug("[DownloadEbookLog] productId : {}", productId);
		log.debug("[DownloadEbookLog] deviceKey : {}", ebookReq.getDeviceKey());
		log.debug("[DownloadEbookLog] userKey : {}", ebookReq.getUserKey());
		log.debug("----------------------------------------------------------------");

		// ID유형 유효값 체크
		if (!"channel".equals(idType) && !"episode".equals(idType)) {
			throw new StorePlatformException("SAC_DSP_0003", "idType", idType);
		}

		setRequest(ebookReq, header);
		MetaInfo metaInfo = getEbookMetaInfo(ebookReq);
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadEbookLog] scid : {}", metaInfo.getSubContentsId());
		log.debug("----------------------------------------------------------------");

		// 암호화된 DL Token extra 필드에서 사용 할 공통 meta 정보
		metaInfo.setSystemId(header.getTenantHeader().getSystemId());
        metaInfo.setTenantId(header.getTenantHeader().getTenantId());
		downloadCommonService.validateVisitPathNm(metaInfo, ebookReq.getVisitPathNm(), productId);

		Product product = new Product();
		setProduct(product, metaInfo);

		if (StringUtils.isNotEmpty(ebookReq.getDeviceKey()) && StringUtils.isNotEmpty(ebookReq.getUserKey())) {
			HistoryListSacInRes historyRes = null;
			boolean purchasePassFlag = true;

			try {
				List<ProductListSacIn> prodIdList = makeProdIdList(metaInfo);
				HistoryListSacInReq historyReq = makeHistoryListSacInReq(ebookReq, prodIdList);
				loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq);
				historyRes = historyInternalSCI.searchHistoryList(historyReq);
			} catch (Exception ex) {
				purchasePassFlag = false;
				log.error("구매내역 조회 연동 중 오류가 발생하였습니다.\n", ex);
			}

			log.debug("----------------------------------------------------------------");
			log.debug("[DownloadEbookLog] purchasePassFlag : {}", purchasePassFlag);
			log.debug("[DownloadEbookLog] historyRes : {}", historyRes);
			log.debug("----------------------------------------------------------------");
			if (purchasePassFlag && historyRes != null) {
				log.debug("----------------------------------------------------------------");
				log.debug("[DownloadEbookLog] 구매건수 : {}", historyRes.getTotalCnt());
				log.debug("----------------------------------------------------------------");
				List<Purchase> purchaseList = new ArrayList<Purchase>();

				for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
                    String permitDeviceYn = historySacIn.getPermitDeviceYn();
                    String prchsProdId = historySacIn.getProdId();
					String prchsState = setPrchsState(historySacIn);

					loggingResponseOfPurchaseHistoryLocalSCI(historySacIn, prchsState);
					if (supportService.resetExprDtOfGift(historySacIn, header, ebookReq.getUserKey(), ebookReq.getDeviceKey(),
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
						SearchDeviceIdSacReq deviceReq = makeSearchDeviceIdSacReq(ebookReq, header);
						deviceRes = deviceSCI.searchDeviceId(deviceReq);
					} catch (Exception ex) {
						log.error("단말정보 조회 연동 중 오류가 발생하였습니다.\n", ex);
					}

					log.debug("----------------------------------------------------------------");
					log.debug("[DownloadEbookLog] deviceRes : {}", deviceRes);
					log.debug("----------------------------------------------------------------");

					if (deviceRes == null || !"Y".equals(deviceRes.getAuthYn()))
						break;

					setMetaInfo(metaInfo, historySacIn, ebookReq, reqExpireDate, prchsProdId, prchsState, deviceRes);
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
		DownloadEbookSacRes ebookRes = makeResponse(product);
        sw.stop();
        supportService.logDownloadResult(ebookReq.getUserKey(), ebookReq.getDeviceKey(), productId, encryptionList, sw.getTime());

        return ebookRes;
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

	private void setRequest(DownloadEbookSacReq ebookReq, SacRequestHeader header) {
		ebookReq.setTenantId(header.getTenantHeader().getTenantId());
		ebookReq.setLangCd(header.getTenantHeader().getLangCd());
		ebookReq.setDeviceModelCd(header.getDeviceHeader().getModel());
		ebookReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		ebookReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
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
		product.setRights(commonGenerator.generateRights(metaInfo));
		product.setDistributor(commonGenerator.generateDistributor(metaInfo));
		product.setContributor(ebookComicGenerator.generateEbookContributor(metaInfo));
	}

	private void loggingEncResult(Encryption encryption) {
		log.debug("-----------------------------------------------------------");
		log.debug("[DownloadEbookLog] token : {}", encryption.getToken());
		log.debug("[DownloadEbookLog] keyIdx : {}", encryption.getKeyIndex());
		log.debug("-----------------------------------------------------------");
	}

	private DownloadEbookSacRes makeResponse(Product product) {
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(1);

		DownloadEbookSacRes ebookRes = new DownloadEbookSacRes();
		ebookRes.setProduct(product);
		ebookRes.setCommonResponse(commonResponse);
		return ebookRes;
	}

	private void setMetaInfo(MetaInfo metaInfo, HistorySacIn historySacIn, DownloadEbookSacReq ebookReq, String reqExpireDate, String prchsProdId,
                             String prchsState, SearchDeviceIdSacRes deviceRes) {
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
		metaInfo.setUserKey(ebookReq.getUserKey());
		metaInfo.setDeviceKey(ebookReq.getDeviceKey());
		if (StringUtils.isNotBlank(ebookReq.getAdditionalMsisdn())) {
			metaInfo.setDeviceType(DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN);
			metaInfo.setDeviceSubKey(ebookReq.getAdditionalMsisdn());
		} else {
			metaInfo.setDeviceType(deviceIdType);
			metaInfo.setDeviceSubKey(deviceId);
		}
		metaInfo.setPurchaseHide(historySacIn.getHidingYn()); // 구매내역 숨김 여부
		metaInfo.setUpdateAlarm(historySacIn.getAlarmYn()); // 업데이트 알람 수신 여부

		// 구매시점 DRM 여부값으로 세팅
        String drmYn = historySacIn.getDrmYn();
        if (StringUtils.isNotEmpty(drmYn)) {
            metaInfo.setDrmYn(drmYn);
            if (drmYn.equals("Y")
					&& !supportService.isTfreemiumPurchase(historySacIn.getPrchsReqPathCd())) {
				// 구매 경로가 Tfreemium 제외하고 호출되도록 수정한다.
				supportService.mapPurchaseDrmInfo(metaInfo);
			}

            metaInfo.setStoreDrmYn(drmYn);
            metaInfo.setPlayDrmYn(drmYn);
        }

		// 소장, 대여 구분(Store : 소장, Play : 대여)
		if (prchsProdId.equals(metaInfo.getStoreProdId())) {
            if (StringUtils.isEmpty(drmYn))
			    metaInfo.setDrmYn(metaInfo.getStoreDrmYn());

			metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
		} else {
            if (StringUtils.isEmpty(drmYn))
			    metaInfo.setDrmYn(metaInfo.getPlayDrmYn());

			metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
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

	private MetaInfo getEbookMetaInfo(DownloadEbookSacReq ebookReq) {
		MetaInfo metaInfo = (MetaInfo) commonDAO.queryForObject("Download.selectDownloadEbookInfo", ebookReq);
		if (metaInfo == null)
			throw new StorePlatformException("SAC_DSP_0009");
		// 단품인 상품만 조회가능 합니다.
		if ("channel".equals(ebookReq.getIdType()) && DisplayConstants.DP_SERIAL_META_CLASS_CD.equals(metaInfo.getMetaClsfCd()))
			throw new StorePlatformException("SAC_DSP_0013");
		return metaInfo;
	}

	private SearchDeviceIdSacReq makeSearchDeviceIdSacReq(DownloadEbookSacReq ebookReq, SacRequestHeader header) {
		SearchDeviceIdSacReq deviceReq;
		deviceReq = new SearchDeviceIdSacReq();
		deviceReq.setUserKey(ebookReq.getUserKey());
		deviceReq.setDeviceKey(ebookReq.getDeviceKey());
		deviceReq.setTenantId(header.getTenantHeader().getTenantId());

		log.debug("--------------------------------------------------------------");
		log.debug("[DownloadEbookLog] 단말정보 조회 요청 파라미터");
		log.debug("--------------------------------------------------------------");
		log.debug("[DownloadEbookLog] userKey : {}", deviceReq.getUserKey());
		log.debug("[DownloadEbookLog] deviceKey : {}", deviceReq.getDeviceKey());
		log.debug("--------------------------------------------------------------");
		return deviceReq;
	}

	private void loggingResponseOfPurchaseHistoryLocalSCI(HistorySacIn historySacIn, String prchsState) {
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadEbookLog] prchsId : {}", historySacIn.getPrchsId());
		log.debug("[DownloadEbookLog] prchsDt : {}", historySacIn.getPrchsDt());
		log.debug("[DownloadEbookLog] useExprDt : {}", historySacIn.getUseExprDt());
		log.debug("[DownloadEbookLog] dwldStartDt : {}", historySacIn.getDwldStartDt());
		log.debug("[DownloadEbookLog] dwldExprDt : {}", historySacIn.getDwldExprDt());
		log.debug("[DownloadEbookLog] prchsCaseCd : {}", historySacIn.getPrchsCaseCd());
		log.debug("[DownloadEbookLog] prchsState : {}", prchsState);
		log.debug("[DownloadEbookLog] prchsProdId : {}", historySacIn.getProdId());
		log.debug("[DownloadEbookLog] prchsPrice : {}", historySacIn.getProdAmt());
		log.debug("[DownloadEbookLog] drmYn : {}", historySacIn.getDrmYn());
		log.debug("[DownloadEbookLog] permitDeviceYn : {}", historySacIn.getPermitDeviceYn());
		log.debug("----------------------------------------------------------------");
	}

	@SuppressWarnings("rawtypes")
	private String getDownloadPurchaseStateByDbTime(HistorySacIn historySacIn) {
		DownloadEbookSacReq req = new DownloadEbookSacReq();
		req.setDwldStartDt(historySacIn.getDwldStartDt());
		req.setDwldExprDt(historySacIn.getDwldExprDt());

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
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadEbookLog] 구매내역 조회 요청 파라미터");
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadEbookLog] tenantId : {}", historyReq.getTenantId());
		log.debug("[DownloadEbookLog] userKey : {}", historyReq.getUserKey());
		log.debug("[DownloadEbookLog] deviceKey : {}", historyReq.getDeviceKey());
		log.debug("[DownloadEbookLog] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
		log.debug("[DownloadEbookLog] prchsProdType : {}", historyReq.getPrchsProdType());
		log.debug("[DownloadEbookLog] startDt : {}", historyReq.getStartDt());
		log.debug("[DownloadEbookLog] endDt : {}", historyReq.getEndDt());
		log.debug("[DownloadEbookLog] prodId[0] : {}", productList.get(0).getProdId());
		log.debug("[DownloadEbookLog] prodId[1] : {}", productList.get(1).getProdId());
		log.debug("----------------------------------------------------------------");
	}

	private HistoryListSacInReq makeHistoryListSacInReq(DownloadEbookSacReq ebookReq, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
		historyReq.setTenantId(ebookReq.getTenantId());
		historyReq.setUserKey(ebookReq.getUserKey());
		historyReq.setDeviceKey(ebookReq.getDeviceKey());
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
}
