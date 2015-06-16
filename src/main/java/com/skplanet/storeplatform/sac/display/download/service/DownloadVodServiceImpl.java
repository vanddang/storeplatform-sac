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
import java.util.Map;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacRes;
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
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.VodGenerator;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
public class DownloadVodServiceImpl implements DownloadVodService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

    @Autowired
	private DisplayCommonService commonService;

    @Autowired
	private HistoryInternalSCI historyInternalSCI;

    @Autowired
	private CommonMetaInfoGenerator commonGenerator;

    @Autowired
	private VodGenerator vodGenerator;

    @Autowired
	private DeviceSCI deviceSCI;

    @Autowired
    private DownloadSupportService supportService;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadVodService#DownloadVodService(com.skplanet
	 * .storeplatform.sac.client.product.vo.DownloadVodReqVO)
	 */
	@Override
	public DownloadVodSacRes searchDownloadVod(SacRequestHeader requestheader, DownloadVodSacReq downloadVodSacReq, boolean supportFhdVideo) {

        List<Encryption> encryptionList = new ArrayList<Encryption>();
        StopWatch sw = new StopWatch();
        sw.start();

        TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		MetaInfo downloadSystemDate = commonDAO.queryForObject("Download.selectDownloadSystemDate", "", MetaInfo.class);

//		String sysDate = downloadSystemDate.getSysDate();
		String reqExpireDate = downloadSystemDate.getExpiredDate();
		setRequest(downloadVodSacReq, tenantHeader, deviceHeader);

		String idType = downloadVodSacReq.getIdType();
		String productId = downloadVodSacReq.getProductId();
		// ID유형 유효값 체크
		if (!DisplayConstants.DP_CHANNEL_IDENTIFIER_CD.equals(idType)
				&& !DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(idType)) {
			throw new StorePlatformException("SAC_DSP_0003", "idType", idType);
		}

		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] idType : {}", idType);
		log.debug("[DownloadVodServiceImpl] productId : {}", productId);
		log.debug("[DownloadVodServiceImpl] deviceKey : {}", downloadVodSacReq.getDeviceKey());
		log.debug("[DownloadVodServiceImpl] userKey : {}", downloadVodSacReq.getUserKey());
		log.debug("----------------------------------------------------------------");

		MetaInfo metaInfo = getVodMetaInfo(downloadVodSacReq);
		Product product = new Product();

		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] NORMAL scid : {}", metaInfo.getNmSubContsId());
		log.debug("[DownloadVodServiceImpl] SD scid : {}", metaInfo.getSdSubContsId());
		log.debug("[DownloadVodServiceImpl] HD scid : {}", metaInfo.getHdSubContsId());
		log.debug("[DownloadVodServiceImpl] CID : {}", metaInfo.getCid());
		log.debug("----------------------------------------------------------------");

		if (StringUtils.isNotEmpty(downloadVodSacReq.getDeviceKey()) && StringUtils.isNotEmpty(downloadVodSacReq.getUserKey())) {
			HistoryListSacInRes historyRes = null;
			boolean purchaseFlag = true;

			try {
				List<ProductListSacIn> prodIdList = makeProdIdList(metaInfo);
				HistoryListSacInReq historyReq = makeHistoryListSacInReq(downloadVodSacReq, prodIdList);
				loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq);
				historyRes = historyInternalSCI.searchHistoryList(historyReq);
			} catch (Exception ex) {
				purchaseFlag = false;
				log.debug("[DownloadVodServiceImpl] Purchase History Search Exception : {}");
				log.error("구매내역 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
				// throw new StorePlatformException("SAC_DSP_2001", ex);
			}

			log.debug("---------------------------------------------------------------------");
			log.debug("[DownloadVodServiceImpl] purchaseFlag :{}", purchaseFlag);
			log.debug("[DownloadVodServiceImpl] historyRes :{}", historyRes);

			if (purchaseFlag && historyRes != null) {
				log.debug("[DownloadVodServiceImpl] 구매건수 :{}", historyRes.getTotalCnt());
				log.debug("---------------------------------------------------------------------");

				String dwldStartDt = null; // 다운로드 시작일시
				String dwldExprDt = null; // 다운로드 만료일시
				String prchsCaseCd = null; // 선물 여부
				String permitDeviceYn = null; // 단말 지원여부

				if (historyRes.getTotalCnt() > 0) {
					List<Purchase> purchaseList = new ArrayList<Purchase>();

					for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
						dwldStartDt = historySacIn.getDwldStartDt();
						dwldExprDt = historySacIn.getDwldExprDt();
						prchsCaseCd = historySacIn.getPrchsCaseCd();
						permitDeviceYn = historySacIn.getPermitDeviceYn();

						String prchsStateCheckedByDbTime = getDownloadPurchaseStateByDbTime(dwldStartDt, dwldExprDt);
						String prchsState = DisplayConstants.PRCHS_STATE_TYPE_EXPIRED;

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
						/************************************************************************************************
						 * 구매 정보에 따른 암호화 시작
						 ************************************************************************************************/
						log.debug("----------------------------------------------------------------");
						log.debug("[DownloadVodServiceImpl] prchsState	:	{}", prchsState);
						log.debug("----------------------------------------------------------------");

						// 구매상태 만료 여부 확인
						if (DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsStateCheckedByDbTime) || !permitDeviceYn.equals("Y")) {
							continue;
						}
						log.debug("----------------------------  start set Purchase Info  ------------------------------------");
						SearchDeviceIdSacReq deviceReq = null;
						SearchDeviceIdSacRes deviceRes = new SearchDeviceIdSacRes();
						boolean memberFlag = true;

						try {
							deviceReq = makeSearchDeviceIdSacReq(downloadVodSacReq, tenantHeader);
							deviceRes = deviceSCI.searchDeviceId(deviceReq);
						} catch (Exception ex) {
							memberFlag = false;
							log.debug("[DownloadVodServiceImpl] Device Search Exception : {}");
							log.error("단말정보 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
							// throw new StorePlatformException("SAC_DSP_1001", ex);
						}

						log.debug("----------------------------------------------------------------");
						log.debug("[DownloadVodServiceImpl] memberFlag	:	{}", memberFlag);
						log.debug("[DownloadVodServiceImpl] deviceRes	:	{}", deviceRes);
						log.debug("----------------------------------------------------------------");

						// MDN 인증여부 확인 (2014.05.22 회원 API 변경에 따른 추가)
						if (!"Y".equals(deviceRes.getAuthYn())) {
							log.debug("##### [SAC DSP LocalSCI] NOT VALID DEVICE_ID : {}", deviceRes.getDeviceId());
						} else if (memberFlag && deviceRes != null) {
							setMetaInfo(metaInfo, historySacIn, downloadVodSacReq, tenantHeader, reqExpireDate, prchsState, deviceRes);
                            Encryption encryption = supportService.generateEncryption(metaInfo, historySacIn.getProdId(), supportFhdVideo);
							encryptionList.add(encryption);
							loggingEncResult(encryption);
						}
						// 구매 정보
						product.setPurchaseList(purchaseList);
						log.debug("----------------------------------------------------------------");
						// 암호화 정보
						if (!encryptionList.isEmpty()) {
							log.debug("[DownloadVodServiceImpl]	setDl : {}");
							product.setDl(encryptionList);
						}

						log.debug("----------------------------  end set Purchase Info  ------------------------------------");

						break;

					}
				}
			}
		}

		setProduct(product, metaInfo, supportFhdVideo);
		DownloadVodSacRes response = makeResponse(product);
        sw.stop();
        supportService.logDownloadResult(downloadVodSacReq.getUserKey(), downloadVodSacReq.getDeviceKey(), productId, encryptionList, sw.getTime());

		return response;
	}

	private void setRequest(DownloadVodSacReq downloadVodSacReq, TenantHeader tenantHeader, DeviceHeader deviceHeader) {
		downloadVodSacReq.setTenantId(tenantHeader.getTenantId());
		downloadVodSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadVodSacReq.setLangCd(tenantHeader.getLangCd());
		downloadVodSacReq.setImageCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		downloadVodSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
	}

	private void setProduct(Product product, MetaInfo metaInfo, boolean supportFhdVideo) {
		List<Identifier>  identifierList = new ArrayList<Identifier>();
		Identifier identifier;

		identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
		identifierList.add(identifier);

		metaInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

		identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getEspdProdId());
		identifierList.add(identifier);

		// CID
		identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, metaInfo.getCid());
		identifierList.add(identifier);

		product.setIdentifierList(identifierList); // 상품 ID
		List<Support>  supportList = new ArrayList<Support>();
		supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_HDCP_SUPPORT_NM, metaInfo.getHdcpYn()));
		supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM, metaInfo.getHdvYn()));
		supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_BTV_SUPPORT_NM, "Y"));
		supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_DOLBY_NM, metaInfo.getDolbySprtYn()));
		product.setSupportList(supportList);
		product.setTitle(commonGenerator.generateTitle(metaInfo)); // 상품명
		product.setMenuList(commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
		product.setSourceList(commonGenerator.generateSourceList(metaInfo)); // 상품 이미지정보
		product.setVod(vodGenerator.generateVod(metaInfo, supportFhdVideo)); // VOD 정보
		product.setRights(commonGenerator.generateRights(metaInfo)); // 이용등급 및 소장/대여 정보
		product.setDistributor(commonGenerator.generateDistributor(metaInfo)); // 판매자 정보

        // 보안을 위해 물리파일 경로는 API응답에서 삭제
        if (product.getVod() != null) {
            List<VideoInfo> videoInfoList = product.getVod().getVideoInfoList();
            for (VideoInfo info : videoInfoList) {
                info.setFilePath(null);
            }
        }
	}

	private void loggingEncResult(Encryption encryption) {
		log.debug("-------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] token : {}", encryption.getToken());
		log.debug("[DownloadVodServiceImpl] keyIdx : {}", encryption.getKeyIndex());
		log.debug("--------------------------------------------------------------");
	}

	private DownloadVodSacRes makeResponse(Product product) {
		CommonResponse commonResponse = new CommonResponse();
        commonResponse.setTotalCount(1);

        DownloadVodSacRes response = new DownloadVodSacRes();
		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}

	private void setMetaInfo(MetaInfo metaInfo, HistorySacIn historySacIn, DownloadVodSacReq downloadVodSacReq, TenantHeader tenantHeader,
			String reqExpireDate, String prchsState, SearchDeviceIdSacRes deviceRes) {
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
		metaInfo.setUserKey(downloadVodSacReq.getUserKey());
		metaInfo.setDeviceKey(downloadVodSacReq.getDeviceKey());
		metaInfo.setDeviceType(deviceIdType);
		metaInfo.setDeviceSubKey(deviceId);
		metaInfo.setPurchaseHide(historySacIn.getHidingYn()); // 구매내역 숨김 여부
		metaInfo.setUpdateAlarm(historySacIn.getAlarmYn()); // 업데이트 알람 수신 여부

		mapProdChrg(metaInfo, historySacIn.getProdId()); // 구매 상품ID
		mapDrmYn(metaInfo, historySacIn);

		metaInfo.setSystemId(tenantHeader.getSystemId());
		metaInfo.setTenantId(tenantHeader.getTenantId());
	}

	private void addPurchaseIntoList(List<Purchase> purchaseList, HistorySacIn historySacIn, String prchsState) {
		Purchase p = commonGenerator.generatePurchase(historySacIn.getPrchsId(),
													historySacIn.getProdId(),
		            								prchsState,
		            								historySacIn.getPrchsDt(),
		            								historySacIn.getDwldExprDt());
		purchaseList.add(p);
	}

	private MetaInfo getVodMetaInfo(DownloadVodSacReq req) {
		MetaInfo metaInfo = commonDAO.queryForObject("Download.getDownloadVodInfo", req, MetaInfo.class);
		if (metaInfo == null)
			throw new StorePlatformException("SAC_DSP_0009");
		if (DisplayConstants.DP_CHANNEL_IDENTIFIER_CD.equals(req.getIdType()) && DisplayConstants.DP_SERIAL_VOD_META_CLASS_CD.equals(metaInfo.getMetaClsfCd()))
				throw new StorePlatformException("SAC_DSP_0013");
		return metaInfo;
	}

	private SearchDeviceIdSacReq makeSearchDeviceIdSacReq(DownloadVodSacReq downloadVodSacReq, TenantHeader tenantHeader) {
		SearchDeviceIdSacReq deviceReq = new SearchDeviceIdSacReq();
		deviceReq.setUserKey(downloadVodSacReq.getUserKey());
		deviceReq.setDeviceKey(downloadVodSacReq.getDeviceKey());
		deviceReq.setTenantId(tenantHeader.getTenantId());
		log.debug("----------------------------------------------------------------");
		log.debug("*******************회원 단말 정보 조회 파라미터*********************");
		log.debug("[DownloadVodServiceImpl] userKey : {}", deviceReq.getUserKey());
		log.debug("[DownloadVodServiceImpl] deviceKey : {}", deviceReq.getDeviceKey());
		log.debug("----------------------------------------------------------------");
		return deviceReq;
	}

	private void loggingResponseOfPurchaseHistoryLocalSCI(HistorySacIn historySacIn, String prchsState) {
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] prchsId : {}", historySacIn.getPrchsId());
		log.debug("[DownloadVodServiceImpl] prchsDt : {}", historySacIn.getPrchsDt());
		log.debug("[DownloadVodServiceImpl] useExprDt : {}", historySacIn.getUseExprDt());
		log.debug("[DownloadVodServiceImpl] dwldStartDt : {}", historySacIn.getDwldStartDt());
		log.debug("[DownloadVodServiceImpl] dwldExprDt : {}", historySacIn.getDwldExprDt());
		log.debug("[DownloadVodServiceImpl] prchsCaseCd : {}", historySacIn.getPrchsCaseCd());
		log.debug("[DownloadVodServiceImpl] prchsState : {}", prchsState);
		log.debug("[DownloadVodServiceImpl] prchsProdId : {}", historySacIn.getProdId());
		log.debug("[DownloadVodServiceImpl] prchsPrice : {}", historySacIn.getProdAmt());
		log.debug("----------------------------------------------------------------");
	}

	@SuppressWarnings("rawtypes")
	private String getDownloadPurchaseStateByDbTime(String dwldStartDt, String dwldExprDt) {
		DownloadVodSacReq req = new DownloadVodSacReq();
		req.setDwldStartDt(dwldStartDt);
		req.setDwldExprDt(dwldExprDt);

		HashMap map = (HashMap) commonDAO.queryForObject("Download.getDownloadPurchaseState", req);
		return (String) map.get("PURCHASE_STATE");
	}

	private List<ProductListSacIn> makeProdIdList(MetaInfo metaInfo) {
		List<ProductListSacIn> productList = new ArrayList<ProductListSacIn>();

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

	private HistoryListSacInReq makeHistoryListSacInReq(DownloadVodSacReq downloadVodSacReq, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
		historyReq.setTenantId(downloadVodSacReq.getTenantId());
		historyReq.setUserKey(downloadVodSacReq.getUserKey());
		historyReq.setDeviceKey(downloadVodSacReq.getDeviceKey());
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

	private void loggingParamsForPurchaseHistoryLocalSCI(List<ProductListSacIn> productList, HistoryListSacInReq historyReq) {
		log.debug("----------------------------------------------------------------");
		log.debug("********************	구매 요청 파라미터	***************************");
		log.debug("[DownloadVodServiceImpl] tenantId : {}", historyReq.getTenantId());
		log.debug("[DownloadVodServiceImpl] userKey : {}", historyReq.getUserKey());
		log.debug("[DownloadVodServiceImpl] deviceKey : {}", historyReq.getDeviceKey());
		log.debug("[DownloadVodServiceImpl] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
		log.debug("[DownloadVodServiceImpl] prchsProdtype : {}", historyReq.getPrchsProdType());
		log.debug("[DownloadVodServiceImpl] startDt : {}", historyReq.getStartDt());
		log.debug("[DownloadVodServiceImpl] endDt : {}", historyReq.getEndDt());
		log.debug("[DownloadVodServiceImpl] offset : {}", historyReq.getOffset());
		log.debug("[DownloadVodServiceImpl] count : {}", historyReq.getCount());
		log.debug("[DownloadVodServiceImpl] store prodId : {}", productList.get(0).getProdId());
		log.debug("[DownloadVodServiceImpl] play prodId : {}", productList.get(1).getProdId());
		log.debug("----------------------------------------------------------------");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param metaInfo
	 * @param prchsProdId
	 */
	private void mapProdChrg(MetaInfo metaInfo, String prchsProdId) {
		if (StringUtils.equals(prchsProdId, metaInfo.getStoreProdId())) {
			metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
		} else {
			metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
		}
	}

	/**
	 * DRM 여부 설정
	 * @param metaInfo
	 * @param historySacIn
	 */
	private void mapDrmYn(MetaInfo metaInfo, HistorySacIn historySacIn) {

		String prchsProdId = "";
		String prchsReqPathCd = "";
		String useFixrateProdId = "";

		if (historySacIn != null) {
			prchsProdId = historySacIn.getProdId();
			prchsReqPathCd = historySacIn.getPrchsReqPathCd();
			useFixrateProdId = historySacIn.getUseFixrateProdId();
		}

		// 2014.07.01. kdlim. 구매 내역 drmYn 값이 정확하지 않아 상품정보 drmYn으로 변경
		// 단, T Freemium을 통한 구매건의 경우는 무조건 DRM적용이므로 아래의 조건을 예외처리 해야함.
		//-	"prchsReqPathCd": "OR0004xx",
		//-	OR000413, OR000420 2개 코드가 T Freemium을 통한 구매건임.
		if(StringUtils.equals(DisplayConstants.PRCHS_REQ_PATH_TFREEMIUM1_CD, prchsReqPathCd)
				|| StringUtils.equals(DisplayConstants.PRCHS_REQ_PATH_TFREEMIUM2_CD, prchsReqPathCd)) {
			metaInfo.setDrmYn("Y");
			metaInfo.setStoreDrmYn("Y");
			metaInfo.setPlayDrmYn("Y");
		} else {

			if(StringUtils.isNotEmpty(useFixrateProdId)) {
				Map<String, String> paramFixrateProd = new HashMap<String, String>();
				paramFixrateProd.put("fixrateProdId", useFixrateProdId);
				paramFixrateProd.put("prodId", metaInfo.getEspdProdId());

				MetaInfo fixrateProd = (MetaInfo) commonDAO.queryForObject("Download.selectFixrateProdInfo", paramFixrateProd);

				// 정액권 상품의 DRM_YN / 소장, 대여 구분(Store : 소장, Play : 대여)
				if(fixrateProd != null) {
					if (prchsProdId.equals(metaInfo.getStoreProdId())) {
						metaInfo.setStoreDrmYn(fixrateProd.getStoreDrmYn());
						metaInfo.setDrmYn(fixrateProd.getStoreDrmYn());
					} else {
						metaInfo.setPlayDrmYn(fixrateProd.getPlayDrmYn());
						metaInfo.setDrmYn(fixrateProd.getPlayDrmYn());
					}
				}

			} else {
				//정액권 상품이 아닌 경우 상품의 DRM_YN 을 리턴
				// 소장, 대여 구분(Store : 소장, Play : 대여)
				if (StringUtils.equals(prchsProdId, metaInfo.getStoreProdId())) {
					metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
				} else {
					metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
				}
			}
			//상품/정액권의 DRM_YN 설정 값이 없을 경우 구매 DRM_YN 을 참조
			if(StringUtils.isEmpty(metaInfo.getDrmYn())
					&& (historySacIn != null && StringUtils.isNotEmpty(historySacIn.getDrmYn()))) {
				metaInfo.setDrmYn(historySacIn.getDrmYn());
			}
		}
	}
}
