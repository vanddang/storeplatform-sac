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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
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

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
	public DownloadVodSacRes searchDownloadVod(SacRequestHeader requestheader, DownloadVodSacReq downloadVodSacReq) {

        List<Encryption> encryptionList = new ArrayList<Encryption>();
        StopWatch sw = new StopWatch();
        sw.start();

        TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		MetaInfo downloadSystemDate = commonDAO.queryForObject("Download.selectDownloadSystemDate", "",
				MetaInfo.class);

		String sysDate = downloadSystemDate.getSysDate();
		String reqExpireDate = downloadSystemDate.getExpiredDate();
		downloadVodSacReq.setTenantId(tanantHeader.getTenantId());
		downloadVodSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadVodSacReq.setLangCd(tanantHeader.getLangCd());
		downloadVodSacReq.setImageCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		downloadVodSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		DownloadVodSacRes response = new DownloadVodSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String idType = downloadVodSacReq.getIdType();
		String productId = downloadVodSacReq.getProductId();
		String deviceKey = downloadVodSacReq.getDeviceKey();
		String userKey = downloadVodSacReq.getUserKey();

		List<Support> supportList = null;

		List<Identifier> identifierList = null;

		// ID유형 유효값 체크
		if (!DisplayConstants.DP_CHANNEL_IDENTIFIER_CD.equals(idType)
				&& !DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(idType)) {
			throw new StorePlatformException("SAC_DSP_0003", "idType", idType);
		}

		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] idType : {}", idType);
		log.debug("[DownloadVodServiceImpl] productId : {}", productId);
		log.debug("[DownloadVodServiceImpl] deviceKey : {}", deviceKey);
		log.debug("[DownloadVodServiceImpl] userKey : {}", userKey);
		log.debug("----------------------------------------------------------------");

		// 다운로드 Vod 상품 조회
		MetaInfo metaInfo = commonDAO.queryForObject("Download.getDownloadVodInfo", downloadVodSacReq,
				MetaInfo.class);

		Product product = new Product();

		if (metaInfo != null) {
			if (DisplayConstants.DP_CHANNEL_IDENTIFIER_CD.equals(idType)) {
				if (DisplayConstants.DP_SERIAL_VOD_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())) {
					throw new StorePlatformException("SAC_DSP_0013");
				}
			}

			log.debug("----------------------------------------------------------------");
			log.debug("[DownloadVodServiceImpl] NORMAL scid : {}", metaInfo.getNmSubContsId());
			log.debug("[DownloadVodServiceImpl] SD scid : {}", metaInfo.getSdSubContsId());
			log.debug("[DownloadVodServiceImpl] HD scid : {}", metaInfo.getHdSubContsId());
			log.debug("[DownloadVodServiceImpl] CID : {}", metaInfo.getCid());
			log.debug("----------------------------------------------------------------");

			/*
			 * 암호화된 DL Token extra 필드에서 사용 할 공통 meta 정보
			 */
            metaInfo.setSystemId(tanantHeader.getSystemId());


			if (StringUtils.isNotEmpty(deviceKey) && StringUtils.isNotEmpty(userKey)) {
				// 구매내역 조회를 위한 생성자
				ProductListSacIn productListSacIn = null;
				List<ProductListSacIn> productList = null;
				HistoryListSacInReq historyReq = null;
				HistoryListSacInRes historyRes = null;
				boolean purchaseFlag = true;

				try {
					productListSacIn = new ProductListSacIn();
					productList = new ArrayList<ProductListSacIn>();

					// 소장 상품ID
					productListSacIn.setProdId(metaInfo.getStoreProdId());
					productList.add(productListSacIn);

					// 대여 상품ID
					productListSacIn = new ProductListSacIn();
					productListSacIn.setProdId(metaInfo.getPlayProdId());
					productList.add(productListSacIn);

					historyReq = new HistoryListSacInReq();
					historyReq.setTenantId(downloadVodSacReq.getTenantId());
					historyReq.setUserKey(downloadVodSacReq.getUserKey());
					historyReq.setDeviceKey(downloadVodSacReq.getDeviceKey());
					historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
					historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
					historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
					historyReq.setPrchsStatusCd(DisplayConstants.PRCHS_STSTUS_COMPLETE_CD);
					historyReq.setEndDt(sysDate);
					historyReq.setOffset(1);
					historyReq.setCount(1000);
					historyReq.setProductList(productList);

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

					// 구매내역 조회 실행
					historyRes = historyInternalSCI.searchHistoryList(historyReq);


				} catch (Exception ex) {
					purchaseFlag = false;
					log.error("구매내역 조회 연동 중 오류가 발생하였습니다. \n", ex);
				}

				log.debug("---------------------------------------------------------------------");
				log.debug("[DownloadVodServiceImpl] purchaseFlag :{}", purchaseFlag);
				log.debug("[DownloadVodServiceImpl] historyRes :{}", historyRes);
				log.debug("---------------------------------------------------------------------");

				if (purchaseFlag && historyRes != null) {
					log.debug("---------------------------------------------------------------------");
					log.debug("[DownloadVodServiceImpl] 구매건수 :{}", historyRes.getTotalCnt());
					log.debug("---------------------------------------------------------------------");

					String prchsId = null; // 구매ID
					String prchsDt = null; // 구매일시
					String useExprDt = null; // 이용 만료일시
					String dwldStartDt = null; // 다운로드 시작일시
					String dwldExprDt = null; // 다운로드 만료일시
					String prchsCaseCd = null; // 선물 여부
					String prchsState = null; // 구매상태
					String prchsProdId = null; // 구매 상품ID
					String puchsPrice = null; // 구매 상품금액
					String drmYn = null; // 구매상품 Drm여부
					String permitDeviceYn = null; // 단말 지원여부
					String prchsReqPathCd = null; //구매 경로
					String purchaseHide = null; // 구매내역 숨김 여부
					String updateAlarm = null; // 업데이트 알람 수신 여부
					String useFixrateProdId = null; // 정액권 상품ID

					if (historyRes.getTotalCnt() > 0) {
						List<Purchase> purchaseList = new ArrayList<Purchase>();

						//for (int i = 0; i < historyRes.getTotalCnt(); i++) {
						for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
							prchsId = historySacIn.getPrchsId();
							prchsDt = historySacIn.getPrchsDt();
							useExprDt = historySacIn.getUseExprDt();
							dwldStartDt = historySacIn.getDwldStartDt();
							dwldExprDt = historySacIn.getDwldExprDt();
							prchsCaseCd = historySacIn.getPrchsCaseCd();
							prchsProdId = historySacIn.getProdId();
							puchsPrice = historySacIn.getProdAmt();
							drmYn = historySacIn.getDrmYn();
							permitDeviceYn = historySacIn.getPermitDeviceYn();
							prchsReqPathCd = historySacIn.getPrchsReqPathCd();
							purchaseHide = historySacIn.getHidingYn();
							updateAlarm = historySacIn.getAlarmYn();
							useFixrateProdId = historySacIn.getUseFixrateProdId();

							// 구매상태 확인
							downloadVodSacReq.setPrchsDt(prchsDt);
							downloadVodSacReq.setDwldStartDt(dwldStartDt);
							downloadVodSacReq.setDwldExprDt(dwldExprDt);

							prchsState = (String) ((HashMap) commonDAO.queryForObject(
									"Download.getDownloadPurchaseState", downloadVodSacReq)).get("PURCHASE_STATE");

							// 구매상태 만료여부 확인
							if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
								// 구매 및 선물 여부 확인
								if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
									prchsState = "payment";
								} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
									prchsState = "gift";
								}
							}

							log.debug("----------------------------------------------------------------");
							log.debug("[DownloadVodServiceImpl] prchsId : {}", prchsId);
							log.debug("[DownloadVodServiceImpl] prchsDt : {}", prchsDt);
							log.debug("[DownloadVodServiceImpl] useExprDt : {}", useExprDt);
							log.debug("[DownloadVodServiceImpl] dwldStartDt : {}", dwldStartDt);
							log.debug("[DownloadVodServiceImpl] dwldExprDt : {}", dwldExprDt);
							log.debug("[DownloadVodServiceImpl] prchsCaseCd : {}", prchsCaseCd);
							log.debug("[DownloadVodServiceImpl] prchsState : {}", prchsState);
							log.debug("[DownloadVodServiceImpl] prchsProdId : {}", prchsProdId);
							log.debug("[DownloadVodServiceImpl] prchsPrice : {}", puchsPrice);
							log.debug("----------------------------------------------------------------");

							metaInfo.setPurchaseId(prchsId);
							metaInfo.setPurchaseProdId(prchsProdId);
							metaInfo.setPurchaseDt(prchsDt);
							metaInfo.setPurchaseState(prchsState);
							metaInfo.setPurchaseDwldExprDt(dwldExprDt);
							metaInfo.setPurchasePrice(Integer.parseInt(puchsPrice));

							// 구매 정보
							purchaseList.add(commonGenerator.generatePurchase(metaInfo));

							/************************************************************************************************
							 * 구매 정보에 따른 암호화 시작
							 ************************************************************************************************/
							log.debug("----------------------------------------------------------------");
							log.debug("[DownloadVodServiceImpl] prchsState	:	{}", prchsState);
							log.debug("----------------------------------------------------------------");

							// 구매상태 만료 여부 확인
							if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)
									&& permitDeviceYn.equals("Y")) {

								String deviceId = null; // Device Id
								String deviceIdType = null; // Device Id 유형
								SearchDeviceIdSacReq deviceReq = null;
								SearchDeviceIdSacRes deviceRes = null;
								boolean memberFlag = true;

								try {
									deviceReq = new SearchDeviceIdSacReq();
									deviceReq.setUserKey(downloadVodSacReq.getUserKey());
									deviceReq.setDeviceKey(downloadVodSacReq.getDeviceKey());
									log.debug("----------------------------------------------------------------");
									log.debug("[DownloadVodServiceImpl] 단말정보 조회 요청 파라미터");
									log.debug("[DownloadVodServiceImpl] userKey : {}", deviceReq.getUserKey());
									log.debug("[DownloadVodServiceImpl] deviceKey : {}", deviceReq.getDeviceKey());
									log.debug("----------------------------------------------------------------");

									// 기기정보 조회
									deviceRes = deviceSCI.searchDeviceId(deviceReq);

								} catch (Exception ex) {
									memberFlag = false;
									log.error("단말정보 조회 연동 중 오류가 발생하였습니다. \n", ex);
								}

								log.debug("----------------------------------------------------------------");
								log.debug("[DownloadVodServiceImpl] memberFlag	:	{}", memberFlag);
								log.debug("[DownloadVodServiceImpl] deviceRes	:	{}", deviceRes);
								log.debug("----------------------------------------------------------------");

								if (memberFlag && deviceRes != null) {
									// MDN 인증여부 확인 (2014.05.22 회원 API 변경에 따른 추가)
									if ("Y".equals(deviceRes.getAuthYn())) {

										deviceId = deviceRes.getDeviceId();
										deviceIdType = commonService.getDeviceIdType(deviceId);

										metaInfo.setExpiredDate(reqExpireDate);
										metaInfo.setUseExprDt(useExprDt);
										metaInfo.setUserKey(userKey);
										metaInfo.setDeviceKey(deviceKey);
										if (StringUtils.isNotBlank(downloadVodSacReq.getAdditionalMsisdn())) {
                                        	metaInfo.setDeviceType(DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN);
                                        	metaInfo.setDeviceSubKey(downloadVodSacReq.getAdditionalMsisdn());
                                        } else {
                                        	metaInfo.setDeviceType(deviceIdType);
                                        	metaInfo.setDeviceSubKey(deviceId);
                                        }
										metaInfo.setPurchaseHide(purchaseHide);
										metaInfo.setUpdateAlarm(updateAlarm);

										//PROD_CHRG
										mapProdChrg(metaInfo, prchsProdId);
										//DRM_YN
										mapDrmYn(metaInfo, historySacIn);

										log.debug("DownloadVodServiceImpl ProdChrg={}, prchsReqPathCd={}, StoreProdId={}, PlayDrmYn={}, DrmYn={}",
												metaInfo.getProdChrg(), prchsReqPathCd, metaInfo.getStoreProdId(), metaInfo.getPlayDrmYn(), metaInfo.getDrmYn());

										// 암호화 정보 (JSON)
                                        Encryption encryption = supportService.generateEncryption(metaInfo, prchsProdId);
										encryptionList.add(encryption);

										log.debug("-------------------------------------------------------------");
										log.debug("[DownloadVodServiceImpl] token : {}", encryption.getToken());
										log.debug("[DownloadVodServiceImpl] keyIdx : {}", encryption.getKeyIndex());
										log.debug("--------------------------------------------------------------");

									} else {
										log.debug("##### [SAC DSP LocalSCI] userKey : {}", deviceReq.getUserKey());
										log.debug("##### [SAC DSP LocalSCI] deviceKey : {}", deviceReq.getDeviceKey());
										log.debug("##### [SAC DSP LocalSCI] NOT VALID DEVICE_ID : {}", deviceRes.getDeviceId());
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

			/************************************************************************************************
			 * 상품 정보
			 ************************************************************************************************/

			identifierList = new ArrayList<Identifier>();
			Identifier identifier;

			identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD,
					metaInfo.getProdId());
			identifierList.add(identifier);

			metaInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

			identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
					metaInfo.getEspdProdId());
			identifierList.add(identifier);

			// CID
			identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD,
					metaInfo.getCid());
			identifierList.add(identifier);

			product.setIdentifierList(identifierList); // 상품 ID
			// product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
			supportList = new ArrayList<Support>();
			supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_HDCP_SUPPORT_NM,
					metaInfo.getHdcpYn()));
			supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM,
					metaInfo.getHdvYn()));
			supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_BTV_SUPPORT_NM, "Y"));
			supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_DOLBY_NM,
					metaInfo.getDolbySprtYn()));
			product.setSupportList(supportList);
			product.setTitle(commonGenerator.generateTitle(metaInfo)); // 상품명
			product.setMenuList(commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
			product.setSourceList(commonGenerator.generateSourceList(metaInfo)); // 상품 이미지정보
			product.setVod(vodGenerator.generateVod(metaInfo)); // VOD 정보
			product.setRights(commonGenerator.generateRights(metaInfo)); // 이용등급 및 소장/대여 정보
			product.setDistributor(commonGenerator.generateDistributor(metaInfo)); // 판매자 정보

			commonResponse.setTotalCount(1);
		} else {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		response.setCommonResponse(commonResponse);
		response.setProduct(product);

        sw.stop();
        supportService.logDownloadResult(userKey, deviceKey, productId, encryptionList, sw.getTime());

		return response;
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
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param metaInfo
	 * @param prchsProdId
	 * @param prchsReqPathCd
	 * @param useFixrateProdId
	 */
	private void mapDrmYn(MetaInfo metaInfo, HistorySacIn historySacIn) {
		String prchsProdId = historySacIn.getProdId();
		String prchsReqPathCd = historySacIn.getPrchsReqPathCd();
		String useFixrateProdId = historySacIn.getUseFixrateProdId();

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
				if (prchsProdId.equals(metaInfo.getStoreProdId())) {
					metaInfo.setStoreDrmYn(fixrateProd.getStoreDrmYn());
					metaInfo.setDrmYn(fixrateProd.getStoreDrmYn());
				} else {
					metaInfo.setPlayDrmYn(fixrateProd.getPlayDrmYn());
					metaInfo.setDrmYn(fixrateProd.getPlayDrmYn());
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
		}
	}
}
