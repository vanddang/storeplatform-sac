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

import com.skplanet.storeplatform.sac.display.download.vo.SearchDownloadAppResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsEcReq;
import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacRes;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Component;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.download.vo.AppDeltaUpdate;
import com.skplanet.storeplatform.sac.display.download.vo.AppDeltaUpdateParam;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.other.common.constant.OtherConstants;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceService;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 1. 21. Updated by : 이석희, 인크로스.
 */
@Service
public class DownloadAppServiceImpl implements DownloadAppService {
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
	private AppInfoGenerator appInfoGenerator;

	@Autowired
	private DeviceSCI deviceSCI;

    @Autowired
	private UapsSCI uapsSCI;

    @Autowired
    private DownloadSupportService supportService;

    @Autowired
    private SacServiceService sacServiceDataService;

    @Autowired
    private DownloadCommonService downloadCommonService;

	@Override
	public SearchDownloadAppResult searchDownloadApp(SacRequestHeader requestheader, DownloadAppSacReq downloadAppSacReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		String osVersion = DisplayCommonUtil.extractOsVer(deviceHeader.getOs());
		downloadAppSacReq.setTenantId(tenantHeader.getTenantId());
		downloadAppSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadAppSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		downloadAppSacReq.setLangCd(tenantHeader.getLangCd());
		downloadAppSacReq.setOsVersion(osVersion); // OS Version
		downloadAppSacReq.setLcdSize(deviceHeader.getResolution()); // LCD SIZE
		downloadAppSacReq.setImageCd(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);

		String filteredBy = downloadAppSacReq.getFilteredBy();
		String productId = downloadAppSacReq.getProductId();
		String deviceKey = downloadAppSacReq.getDeviceKey();
		String userKey = downloadAppSacReq.getUserKey();
		String packageName = downloadAppSacReq.getPackageName();
		List<Identifier> identifierList = null;
		boolean tingMemberFlag = false;

        StopWatch sw = new StopWatch();
        sw.start();

		// 파라미터 체크
		if ("package".equals(filteredBy)) {
			productId = (String) commonDAO.queryForObject("Download.getProductIdForPackageName", downloadAppSacReq);
			downloadAppSacReq.setProductId(productId);

			if (StringUtils.isEmpty(productId)) {
				throw new StorePlatformException("SAC_DSP_0005", packageName);
			}
		}

		if (StringUtils.isEmpty(osVersion)) {
			throw new StorePlatformException("SAC_DSP_0024");
		}

		Map chkSupportOs = (Map) commonDAO.queryForObject("Download.selectSupportOsVersion", downloadAppSacReq);

		if (chkSupportOs != null) {
			if ("N".equals(chkSupportOs.get("VM_VER"))) {
				throw new StorePlatformException("SAC_DSP_0023");
			}
		} else {
			log.debug("About Check OS Provisioning Not Found Product Info");
			throw new StorePlatformException("SAC_DSP_0009");
		}

		MetaInfo downloadSystemDate = commonDAO.queryForObject("Download.selectDownloadSystemDate", "",	MetaInfo.class);
		String sysDate = downloadSystemDate.getSysDate();
		String reqExpireDate = downloadSystemDate.getExpiredDate();
		String purchaseDt = downloadSystemDate.getPurchaseDt();

		Product product = new Product();
		Component component = new Component();
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadAppServiceImpl] productId : {}", productId);
		log.debug("[DownloadAppServiceImpl] deviceKey : {}", deviceKey);
		log.debug("[DownloadAppServiceImpl] userKey : {}", userKey);
		log.debug("----------------------------------------------------------------");

		// 다운로드 앱 상품 조회
		MetaInfo metaInfo = commonDAO.queryForObject("Download.getDownloadAppInfo", downloadAppSacReq, MetaInfo.class);

		if (metaInfo == null)  {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		List<Encryption> encryptionList = new ArrayList<Encryption>();

		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadAppServiceImpl] scid : {}", metaInfo.getSubContentsId());
		log.debug("----------------------------------------------------------------");
		identifierList = new ArrayList<Identifier>();

		doBunchProdProvisioning(downloadAppSacReq, metaInfo);
		validateParentBunchProd(downloadAppSacReq, metaInfo);

		/**
		 * 암호화된 DL Token extra 필드에서 사용 할 공통 meta 정보
		 */
		metaInfo.setSystemId(tenantHeader.getSystemId());
        metaInfo.setTenantId(tenantHeader.getTenantId());
		downloadCommonService.validateVisitPathNm(metaInfo, downloadAppSacReq.getVisitPathNm(), productId);
		metaInfo.setDwldTypeCd(downloadAppSacReq.getDwldTypeCd());

        /**
         * 업데이트 알람 수신 여부가 SAC구매에서 테넌트로 옮겨짐.
         * For D/L API에 파마리터로 현재 상태의 알람 수신 여부를 받도록 변경
         */
        metaInfo.setUpdateAlarm(StringUtils.defaultString(downloadAppSacReq.getUpdateAlarmYn(), "Y"));


		if (StringUtils.isNotEmpty(deviceKey) && StringUtils.isNotEmpty(userKey)) {
			HistoryListSacInRes historyRes = null;
			boolean purchaseFlag = true;

			try {
				List<ProductListSacIn> prodIdList = makeProdIdList(metaInfo);
				HistoryListSacInReq historyReq = makeHistoryListSacInReq(downloadAppSacReq, sysDate, prodIdList);
				loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq);
				historyRes = historyInternalSCI.searchHistoryList(historyReq);
			} catch (Exception ex) {
				purchaseFlag = false;
				log.error("구매내역 조회 연동 중 오류가 발생하였습니다.\n", ex);
				// throw new StorePlatformException("SAC_DSP_2001", ex);
			}

			log.debug("---------------------------------------------------------------------");
			log.debug("[DownloadAppServiceImpl] purchaseFlag :{}", purchaseFlag);
			log.debug("[DownloadAppServiceImpl] historyRes :{}", historyRes);
			if (purchaseFlag && historyRes != null) {
				log.debug("[DownloadAppServiceImpl] 구매건수 :{}", historyRes.getTotalCnt());
				log.debug("---------------------------------------------------------------------");

				String useExprDt = null; // 이용 만료일시
				String dwldStartDt = null; // 다운로드 시작일시
				String dwldExprDt = null; // 다운로드 만료일시
				String prchsCaseCd = null; // 선물 여부
				String prchsProdId = null; // 구매 상품ID
				String permitDeviceYn = null; // 단말 지원여부
				String purchaseHide = null; // 구매내역 숨김 여부

				if (historyRes.getTotalCnt() > 0) {
					List<Purchase> purchaseList = new ArrayList<Purchase>();

					for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
						useExprDt = historySacIn.getUseExprDt();
						dwldStartDt = historySacIn.getDwldStartDt();
						dwldExprDt = historySacIn.getDwldExprDt();
						prchsCaseCd = historySacIn.getPrchsCaseCd();
						prchsProdId = historySacIn.getProdId();
						permitDeviceYn = historySacIn.getPermitDeviceYn();
						purchaseHide = historySacIn.getHidingYn();

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
						metaInfo.setDrmYn(historySacIn.getDrmYn());
						// 구매 정보
						purchaseList.add(commonGenerator.generatePurchase(metaInfo));

						/************************************************************************************************
						 * 구매 정보에 따른 암호화 시작
						 ************************************************************************************************/
						// 구매상태 만료 여부 확인
						if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsStateCheckedByDbTime) && permitDeviceYn.equals("Y")) {
							SearchDeviceIdSacReq deviceReq = null;
							SearchDeviceIdSacRes deviceRes = null;
							boolean memberFlag = true;

							try {
								deviceReq = new SearchDeviceIdSacReq();
								deviceReq.setUserKey(downloadAppSacReq.getUserKey());
								deviceReq.setDeviceKey(downloadAppSacReq.getDeviceKey());
                                deviceReq.setTenantId(tenantHeader.getTenantId());

								log.debug("----------------------------------------------------------------");
								log.debug("*******************회원 단말 정보 조회 파라미터*********************");
								log.debug("[DownloadAppServiceImpl] userKey : {}", deviceReq.getUserKey());
								log.debug("[DownloadAppServiceImpl] deviceKey : {}", deviceReq.getDeviceKey());
								log.debug("----------------------------------------------------------------");

								// 기기정보 조회
								deviceRes = deviceSCI.searchDeviceId(deviceReq);

							} catch (Exception ex) {
								memberFlag = false;
								log.error("단말정보 조회 연동 중 오류가 발생하였습니다.\n", ex);
								// 예외 무시
							}

							log.debug("----------------------------------------------------------------");
							log.debug("[DownloadAppServiceImpl] memberFlag	:	{}", memberFlag);
							log.debug("[DownloadAppServiceImpl] deviceRes	:	{}", deviceRes);
							log.debug("----------------------------------------------------------------");

							if (memberFlag && deviceRes != null) {
								// MDN 인증여부 확인 (2014.05.22 회원 API 변경에 따른 추가)
								if ("Y".equals(deviceRes.getAuthYn())) {
									String deviceId = deviceRes.getDeviceId();
									String deviceTelecom = deviceRes.getDeviceTelecom();
									String deviceIdType = commonService.getDeviceIdType(deviceId);

									metaInfo.setExpiredDate(reqExpireDate);
									metaInfo.setUseExprDt(useExprDt);
									metaInfo.setUserKey(userKey);
									metaInfo.setDeviceKey(deviceKey);
									metaInfo.setDeviceType(deviceIdType);
									metaInfo.setDeviceSubKey(deviceId);
									metaInfo.setPurchaseHide(purchaseHide);

									// 단말의 통신사가 SKT 일때만 적용
									if (DisplayConstants.DP_TELECOM_TYPE_CD_SKT.equals(deviceTelecom)) {
										// Top Menu 가 DP08(어학/교육) 이고, deviceId 유형이 mdn일때 PacketFee 는 halfPaid
										if (DisplayConstants.DP_LANG_EDU_TOP_MENU_ID
												.equals(metaInfo.getTopMenuId())
												&& deviceIdType.equals(DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN)) {

											try {
												UapsEcReq uapsEcReq = new UapsEcReq();
												uapsEcReq.setDeviceId(deviceId);
												uapsEcReq.setType("mdn");
												log.debug("----------------------------------------------------------------");
												log.debug("********************UAPS 정보 조회************************");
												log.debug("[DownloadAppServiceImpl] DeviceId : {}",	uapsEcReq.getDeviceId());
												log.debug("[DownloadAppServiceImpl] Type : {}",uapsEcReq.getType());
												log.debug("----------------------------------------------------------------");

												UserEcRes uapsEcRes = uapsSCI.getMappingInfo(uapsEcReq);

												for (int k = 0; k < uapsEcRes.getServiceCD().length; k++) {
													log.debug("[DownloadAppServiceImpl] serviceCd	:{}", uapsEcRes.getServiceCD()[k]);
													if (DisplayConstants.DP_DEVICE_SERVICE_TYPE_TING
															.equals(uapsEcRes.getServiceCD()[k])) {
														metaInfo.setProdClsfCd(DisplayConstants.DP_PACKETFEE_TYPE_HALFPAID);

														tingMemberFlag = true;
													}
												}

											} catch (Exception e) {
												log.error("UAPS 조회 연동 중 오류가 발생하였습니다.\n", e);
												// 예외 무시
											}
										}
									}

									// 암호화 정보 (JSON)
									genenateMetaForAppDeltaUpdate(metaInfo, downloadAppSacReq.getApkVerCd());

                                    // Push 강제 업그레이드인 경우
                                    generateMetaForPushForceUpgrade(metaInfo, downloadAppSacReq.getPacketFreeYn());

                                    Encryption encryption = supportService.generateEncryption(metaInfo, prchsProdId);
                                    encryptionList.add(encryption);

									log.debug("-------------------------------------------------------------");
									log.debug("[DownloadAppServiceImpl] token : {}", encryption.getToken());
									log.debug("[DownloadAppServiceImpl] keyIdx : {}", encryption.getKeyIndex());
									log.debug("-------------------------------------------------------------");
								} else {
									log.debug("##### [SAC DSP LocalSCI] userKey : {}", deviceReq.getUserKey());
									log.debug("##### [SAC DSP LocalSCI] deviceKey : {}", deviceReq.getDeviceKey());
									log.debug("##### [SAC DSP LocalSCI] NOT VALID DEVICE_ID : {}", deviceRes.getDeviceId());
								}
							}
							product.setPurchaseList(purchaseList);

							// 암호화 정보
							if (!encryptionList.isEmpty()) {
								product.setDl(encryptionList);
							}
							break;
						}
					}

				} else {
					/**
					 * 구매내역이 존재하지 않는 경우 예외적으로 다운로드 허용
					 * 예) 앱가이드, 스마트청구서, T 통화 도우미
					 */
					if ( isProdWithoutPrchsHis(tenantHeader.getTenantId(), productId) ) {
						makeDefaultMetaWithoutPrchsHis(metaInfo, downloadAppSacReq, purchaseDt, reqExpireDate);

						Encryption encryption = supportService.generateEncryption(metaInfo, productId);
						encryptionList.add(encryption);
						product.setDl(encryptionList);
					}

				}
			}
		}

		/************************************************************************************************
		 * Seed App 정보
		 ************************************************************************************************/

		component.setIdentifierList(appInfoGenerator.generateComponentIdentifierList(metaInfo));
		component.setGameCenterVerCd(StringUtils.defaultString(metaInfo.getGameCentrVerCd()));
		component.setUseYn(metaInfo.getSeedUseYn());
		component.setCaseRefCd(metaInfo.getSeedCaseRefCd());
        component.setBunchMessage(metaInfo.getBnchDwldMsg());

		/************************************************************************************************
		 * 상품 정보
		 ************************************************************************************************/

		identifierList.add(commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD,metaInfo.getProdId()));
		identifierList.add(commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,metaInfo.getProdId()));
		product.setIdentifierList(identifierList); // 상품 Id
		product.setTitle(commonGenerator.generateTitle(metaInfo)); // 상품명
		product.setSourceList(commonGenerator.generateSourceList(metaInfo)); // 상품 이미지정보
		List<Support> supportList = new ArrayList<Support>();
		supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM, metaInfo.getDrmYn()));
		product.setSupportList(supportList);
		product.setMenuList(commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
		product.setApp(appInfoGenerator.generateApp(metaInfo)); // App 상세정보
		product.setRights(commonGenerator.generateRights(metaInfo)); // 권한
		product.setDistributor(commonGenerator.generateDistributor(metaInfo)); // 판매자 정보
        product.setPacketFee(makePacketFee(metaInfo, tingMemberFlag, downloadAppSacReq.getPacketFreeYn()));
		product.setPlatClsfCd(metaInfo.getPlatClsfCd());
		product.setPrice(commonGenerator.generatePrice(metaInfo)); // 상품금액 정보

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(1);

		DownloadAppSacRes response = new DownloadAppSacRes();
		response.setCommonResponse(commonResponse);
		response.setComponent(component);
		response.setProduct(product);

        sw.stop();
        supportService.logDownloadResult(userKey, deviceKey, productId, encryptionList, sw.getTime());

		return new SearchDownloadAppResult(response, metaInfo.getAid(), metaInfo.getProdId(), CollectionUtils.isNotEmpty(encryptionList));
	}

	private void loggingResponseOfPurchaseHistoryLocalSCI(HistorySacIn historySacIn, String prchsState) {
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadAppServiceImpl] prchsId : {}", historySacIn.getPrchsId());
		log.debug("[DownloadAppServiceImpl] prchsDt : {}", historySacIn.getPrchsDt());
		log.debug("[DownloadAppServiceImpl] useExprDt : {}", historySacIn.getUseExprDt());
		log.debug("[DownloadAppServiceImpl] dwldStartDt : {}", historySacIn.getDwldStartDt());
		log.debug("[DownloadAppServiceImpl] dwldExprDt : {}", historySacIn.getDwldExprDt());
		log.debug("[DownloadAppServiceImpl] prchsCaseCd : {}", historySacIn.getPrchsCaseCd());
		log.debug("[DownloadAppServiceImpl] prchsState : {}", prchsState);
		log.debug("[DownloadAppServiceImpl] prchsProdId : {}", historySacIn.getProdId());
		log.debug("[DownloadAppServiceImpl] prchsPrice : {}", historySacIn.getProdAmt());
		log.debug("----------------------------------------------------------------");
	}

	@SuppressWarnings("rawtypes")
	private String getDownloadPurchaseStateByDbTime(String dwldStartDt, String dwldExprDt) {
		DownloadAppSacReq req = new DownloadAppSacReq();
		req.setDwldStartDt(dwldStartDt);
		req.setDwldExprDt(dwldExprDt);

		HashMap map = (HashMap) commonDAO.queryForObject("Download.getDownloadPurchaseState", req);
		return (String) map.get("PURCHASE_STATE");
	}

	private List<ProductListSacIn> makeProdIdList(MetaInfo metaInfo) {
		List<ProductListSacIn>  productList = new ArrayList<ProductListSacIn>();
		ProductListSacIn productListSacIn = new ProductListSacIn();
		productListSacIn.setProdId(metaInfo.getProdId());
		productList.add(productListSacIn);
		return productList;
	}

	private void loggingParamsForPurchaseHistoryLocalSCI(List<ProductListSacIn> productList, HistoryListSacInReq historyReq) {
		log.debug("----------------------------------------------------------------");
		log.debug("********************	구매 요청 파라미터	***************************");
		log.debug("[DownloadAppServiceImpl] tenantId : {}", historyReq.getTenantId());
		log.debug("[DownloadAppServiceImpl] userKey : {}", historyReq.getUserKey());
		log.debug("[DownloadAppServiceImpl] deviceKey : {}", historyReq.getDeviceKey());
		log.debug("[DownloadAppServiceImpl] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
		log.debug("[DownloadAppServiceImpl] prchsProdtype : {}", historyReq.getPrchsProdType());
		log.debug("[DownloadAppServiceImpl] startDt : {}", historyReq.getStartDt());
		log.debug("[DownloadAppServiceImpl] endDt : {}", historyReq.getEndDt());
		log.debug("[DownloadAppServiceImpl] offset : {}", historyReq.getOffset());
		log.debug("[DownloadAppServiceImpl] count : {}", historyReq.getCount());
		log.debug("[DownloadAppServiceImpl] prodId : {}", productList.get(0).getProdId());
		log.debug("----------------------------------------------------------------");
	}

	private HistoryListSacInReq makeHistoryListSacInReq(DownloadAppSacReq downloadAppSacReq, String sysDate, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
		historyReq.setTenantId(downloadAppSacReq.getTenantId());
		historyReq.setUserKey(downloadAppSacReq.getUserKey());
		historyReq.setDeviceKey(downloadAppSacReq.getDeviceKey());
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

    private void generateMetaForPushForceUpgrade(MetaInfo metaInfo, String packetFreeYn) {
        // Push 강제 업그레이드인 경우 packetFee 값을 free로 설정
        if (StringUtils.isNotBlank(packetFreeYn) && "Y".equals(packetFreeYn))
            metaInfo.setProdClsfCd(DisplayConstants.DP_PACKETFEE_TYPE_FREE);
    }

    private String makePacketFee(MetaInfo metaInfo, boolean tingMemberFlag, String packetFreeYn) {

        // Push 강제 업그레이드인 경우 packetFee 값을 free로 설정
        if (StringUtils.isNotBlank(packetFreeYn) && "Y".equals(packetFreeYn)) {
            return DisplayConstants.DP_PACKETFEE_TYPE_FREE;
        }

        if (tingMemberFlag) {
            /**
             * ting 요금제 가입자가 어학/교육 카테고리를 다운받을때는
             * packetFee 값이 'paid'로 내려가야함.
             * dl에 암호화된 token에 packetFee는 'half'로 내려가야함.
             */
            return DisplayConstants.DP_PACKETFEE_TYPE_PAID;
        }

        return metaInfo.getProdClsfCd();
    }

	private void doBunchProdProvisioning(DownloadAppSacReq downloadAppSacReq, MetaInfo metaInfo) {

		if (StringUtils.isBlank(metaInfo.getBnchProdId())) return;


		downloadAppSacReq.setBnchProdId(metaInfo.getBnchProdId());

		if ( (Integer)commonDAO.queryForObject("Download.getBunchIdProvisioning", downloadAppSacReq) > 0 ) return;

		metaInfo.setBnchProdId(null);
		metaInfo.setBnchDwldMsg(null);
	}

	private void validateParentBunchProd(DownloadAppSacReq downloadAppSacReq, MetaInfo metaInfo) {

		if (StringUtils.isBlank(downloadAppSacReq.getParentBunchId())) return;

		if ( (Integer)commonDAO.queryForObject("Download.getValidateParentBunchId", downloadAppSacReq) <= 0 ) return;

		metaInfo.setParentBunchId(downloadAppSacReq.getParentBunchId());
	}

	private boolean isActiveAppDeltaUpdate() {

		SacService sacService = new SacService();
		sacService.setServiceCd(OtherConstants.SAC_SERVICE_APPDELTA);

		return sacServiceDataService.getServiceActive(sacService).isActive();
	}

	private void genenateMetaForAppDeltaUpdate(MetaInfo metaInfo, Integer preApkVer) {

		if (preApkVer == null || preApkVer <= 0) return;

		if ( !isActiveAppDeltaUpdate() ) return;

		AppDeltaUpdateParam param = new AppDeltaUpdateParam();
		param.setProdId(metaInfo.getProdId());
		param.setSubContentsId(metaInfo.getSubContentsId());
		param.setApkVer(Integer.parseInt(metaInfo.getApkVer()));
		param.setPreApkVer(preApkVer);

		AppDeltaUpdate appDeltaUpdate = commonDAO.queryForObject("Download.getDownloadAppDeltaUpdate", param, AppDeltaUpdate.class);
		if( appDeltaUpdate == null ) return;

		metaInfo.setDeltaType("delta");
		metaInfo.setDeltaFileSize(appDeltaUpdate.getDeltaFileSize());
		metaInfo.setDeltaFilePath(appDeltaUpdate.getDeltaFilePath());
	}

	private void makeDeviceSubKey(MetaInfo metaInfo, final String userKey, final String deviceKey) {

		SearchDeviceIdSacReq deviceReq = new SearchDeviceIdSacReq();
		deviceReq.setUserKey(userKey);
		deviceReq.setDeviceKey(deviceKey);

		try {
			SearchDeviceIdSacRes deviceRes = deviceSCI.searchDeviceId(deviceReq);

			if ( deviceRes != null ) {
				metaInfo.setDeviceType(commonService.getDeviceIdType(deviceRes.getDeviceId()));
				metaInfo.setDeviceSubKey(deviceRes.getDeviceId());
			}

		} catch (Exception ex) {
			log.error("단말정보 조회 연동 중 오류가 발생하였습니다.\n", ex);
			// 예외 무시
		}
	}

	private void makeDefaultMetaWithoutPrchsHis(MetaInfo metaInfo, final DownloadAppSacReq sacReq, final String purchaseDt, final String expiredDate) {

		metaInfo.setExpiredDate(expiredDate);
		metaInfo.setPurchaseId("FREE1000000000000000");
		metaInfo.setPurchaseProdId(sacReq.getProductId());
		metaInfo.setPurchaseDt(purchaseDt);
		metaInfo.setUserKey(sacReq.getUserKey());
		metaInfo.setUseExprDt("99991231235959");
		metaInfo.setDeviceKey(sacReq.getDeviceKey());

		makeDeviceSubKey(metaInfo, sacReq.getUserKey(), sacReq.getDeviceKey());

		metaInfo.setPurchaseHide("N");
		metaInfo.setUpdateAlarm("Y");
	}

	private boolean isProdWithoutPrchsHis(final String tenantId, final String prodId) {

		Map<String, Object> req = new HashMap<String, Object>();
		req.put("tenantId", tenantId);
		req.put("prodId", prodId);

		if ( commonDAO.queryForObject("Download.getDwldPolicy", req, Integer.class) <= 0 )
			return false;
		else
			return true;
	}

}

