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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Component;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;

//import org.apache.commons.lang3.StringUtils;

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
	HistoryInternalSCI historyInternalSCI;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private AppInfoGenerator appInfoGenerator;
	@Autowired
	private EncryptionGenerator encryptionGenerator;
	@Autowired
	private DownloadAES128Helper downloadAES128Helper;
	@Autowired
	private DeviceSCI deviceSCI;
	@Autowired
	private UapsSCI uapsSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadAppService#DownloadAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.downloadAppSacReqVO)
	 */
	@Override
	public DownloadAppSacRes searchDownloadApp(SacRequestHeader requestheader, DownloadAppSacReq downloadAppSacReq) {
		TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		MetaInfo downloadSystemDate = this.commonDAO.queryForObject("Download.selectDownloadSystemDate", "",
				MetaInfo.class);

		// OS VERSION 가공
		String[] temp = deviceHeader.getOs().trim().split("/");

		String osVersion = temp[1];
		String sysDate = downloadSystemDate.getSysDate();
		String reqExpireDate = downloadSystemDate.getExpiredDate();
		String osVersionOrginal = osVersion;
		String[] osVersionTemp = StringUtils.split(osVersionOrginal, ".");
		if (osVersionTemp.length == 3) {
			osVersion = osVersionTemp[0] + "." + osVersionTemp[1];
		}

		downloadAppSacReq.setTenantId(tanantHeader.getTenantId());
		downloadAppSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadAppSacReq.setLangCd(tanantHeader.getLangCd());
		downloadAppSacReq.setOsVersion(osVersion); // OS Version
		downloadAppSacReq.setLcdSize(deviceHeader.getResolution()); // LCD SIZE
		downloadAppSacReq.setImageCd(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);

		DownloadAppSacRes response = new DownloadAppSacRes();
		CommonResponse commonResponse = new CommonResponse();
		String filteredBy = downloadAppSacReq.getFilteredBy();
		String productId = downloadAppSacReq.getProductId();
		String deviceKey = downloadAppSacReq.getDeviceKey();
		String userKey = downloadAppSacReq.getUserKey();
		String packageName = downloadAppSacReq.getPackageName();

		List<Identifier> identifierList = null;

		Product product = null;
		Component component = null;

		if (downloadAppSacReq.getDummy() == null) {

			// dummy 호출이 아닐때
			if (StringUtils.isEmpty(filteredBy)) {
				throw new StorePlatformException("SAC_DSP_0002", "filteredBy", filteredBy);
			}

			if (StringUtils.isEmpty(deviceKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "deviceKey", deviceKey);
			}

			if (StringUtils.isEmpty(userKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "userKey", productId);
			}

			// 조회 유효값 체크
			if (!"package".equals(filteredBy) && !"id".equals(filteredBy)) {
				throw new StorePlatformException("SAC_DSP_0002", "filteredBy", filteredBy);
			}

			// 파라미터 체크
			if ("package".equals(filteredBy)) {
				if (StringUtils.isEmpty(packageName)) {
					throw new StorePlatformException("SAC_DSP_0002", "packageName", packageName);
				}

				productId = (String) this.commonDAO.queryForObject("Download.getProductIdForPackageName",
						downloadAppSacReq);
				downloadAppSacReq.setProductId(productId);

				if (StringUtils.isEmpty(productId)) {
					throw new StorePlatformException("SAC_DSP_0005", packageName);
				}
			} else {
				if (StringUtils.isEmpty(productId)) {
					throw new StorePlatformException("SAC_DSP_0002", "productId", productId);
				}
			}

			// 다운로드 앱 상품 조회
			MetaInfo metaInfo = this.commonDAO.queryForObject("Download.getDownloadAppInfo", downloadAppSacReq,
					MetaInfo.class);

			if (metaInfo != null) {
				identifierList = new ArrayList<Identifier>();

				product = new Product();

				// 구매내역 조회를 위한 생성자
				ProductListSacIn productListSacIn = null;
				List<ProductListSacIn> productList = null;
				HistoryListSacInReq historyReq = null;
				HistoryListSacInRes historyRes = null;

				try {
					productListSacIn = new ProductListSacIn();
					productList = new ArrayList<ProductListSacIn>();

					productListSacIn.setProdId(metaInfo.getProdId());
					productList.add(productListSacIn);

					historyReq = new HistoryListSacInReq();
					historyReq.setTenantId(downloadAppSacReq.getTenantId());
					historyReq.setUserKey(downloadAppSacReq.getUserKey());
					historyReq.setDeviceKey(downloadAppSacReq.getDeviceKey());
					historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
					historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
					historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
					historyReq.setEndDt(sysDate);
					historyReq.setOffset(1);
					historyReq.setCount(productList.size());
					historyReq.setProductList(productList);

					// 구매내역 조회 실행
					historyRes = this.historyInternalSCI.searchHistoryList(historyReq);

				} catch (Exception ex) {
					throw new StorePlatformException("SAC_DSP_1001", ex);
				}

				String prchsId = null; // 구매ID
				String prchsDt = null; // 구매일시
				String useExprDt = null; // 이용 만료일시
				String dwldExprDt = null; // 다운로드 만료일시
				String prchsCaseCd = null; // 선물 여부
				String prchsState = null; // 구매상태
				String prchsProdId = null; // 구매 상품ID
				String puchsPrice = null; // 구매 상품금액
				String drmYn = null; // 구매상품 Drm여부

				if (historyRes != null && historyRes.getTotalCnt() > 0) {
					List<Purchase> purchaseList = new ArrayList<Purchase>();
					List<Encryption> encryptionList = new ArrayList<Encryption>();

					for (int i = 0; i < historyRes.getTotalCnt(); i++) {
						prchsId = historyRes.getHistoryList().get(i).getPrchsId();
						prchsDt = historyRes.getHistoryList().get(i).getPrchsDt();
						useExprDt = historyRes.getHistoryList().get(i).getUseExprDt();
						dwldExprDt = historyRes.getHistoryList().get(i).getDwldExprDt();
						prchsCaseCd = historyRes.getHistoryList().get(i).getPrchsCaseCd();
						prchsProdId = historyRes.getHistoryList().get(i).getProdId();
						puchsPrice = historyRes.getHistoryList().get(i).getProdAmt();
						drmYn = historyRes.getHistoryList().get(i).getDrmYn();

						// 구매상태 확인
						downloadAppSacReq.setPrchsDt(prchsDt);
						downloadAppSacReq.setDwldExprDt(dwldExprDt);
						prchsState = (String) this.commonDAO.queryForObject("Download.getDownloadPurchaseState",
								downloadAppSacReq);

						// 구매상태 만료여부 확인
						if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
							// 구매 및 선물 여부 확인
							if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
								prchsState = "payment";
							} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
								prchsState = "gift";
							}
						}

						this.log.debug("----------------------------------------------------------------");
						this.log.debug("[getDownloadAppInfo] prchsId : {}", prchsId);
						this.log.debug("[getDownloadAppInfo] prchsDt : {}", prchsDt);
						this.log.debug("[getDownloadAppInfo] useExprDt : {}", useExprDt);
						this.log.debug("[getDownloadAppInfo] dwldExprDt : {}", dwldExprDt);
						this.log.debug("[getDownloadAppInfo] prchsCaseCd : {}", prchsCaseCd);
						this.log.debug("[getDownloadAppInfo] prchsState : {}", prchsState);
						this.log.debug("[getDownloadAppInfo] prchsProdId : {}", prchsProdId);
						this.log.debug("[getDownloadAppInfo] puchsPrice : {}", puchsPrice);
						this.log.debug("----------------------------------------------------------------");

						metaInfo.setPurchaseId(prchsId);
						metaInfo.setPurchaseProdId(prchsProdId);
						metaInfo.setPurchaseDt(prchsDt);
						metaInfo.setPurchaseState(prchsState);
						metaInfo.setPurchaseDwldExprDt(dwldExprDt);
						metaInfo.setPurchasePrice(Integer.parseInt(puchsPrice));
						metaInfo.setDrmYn(drmYn);

						/************************************************************************************************
						 * 구매 정보에 따른 암호화 시작
						 ************************************************************************************************/
						// 구매상태 만료 여부 확인
						if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
							String deviceId = null; // Device Id
							String deviceIdType = null; // Device Id 유형
							SearchDeviceIdSacReq deviceReq = null;
							SearchDeviceIdSacRes deviceRes = null;

							try {
								deviceReq = new SearchDeviceIdSacReq();
								deviceReq.setUserKey(downloadAppSacReq.getUserKey());
								deviceReq.setDeviceKey(downloadAppSacReq.getDeviceKey());

								// 기기정보 조회
								deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
							} catch (Exception ex) {
								throw new StorePlatformException("SAC_DSP_2001", ex);
							}

							if (deviceRes != null) {
								deviceId = deviceRes.getDeviceId();
								deviceIdType = this.commonService.getDeviceIdType(deviceId);

								metaInfo.setExpiredDate(reqExpireDate);
								metaInfo.setUseExprDt(useExprDt);
								metaInfo.setUserKey(userKey);
								metaInfo.setDeviceKey(deviceKey);
								metaInfo.setDeviceType(deviceIdType);
								metaInfo.setDeviceSubKey(deviceId);

								// if (deviceIdType.equals(DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN)) {
								// UapsEcReq uapsEcReq = new UapsEcReq();
								// UserEcRes uapsEcRes = new UserEcRes();
								// uapsEcReq.setDeviceId(deviceId);
								// uapsEcReq.setType("mdn");
								//
								// uapsEcRes = this.uapsSCI.getMappingInfo(uapsEcReq);
								//
								// this.log.debug("########################################################");
								// this.log.debug("serviceCd length	:	" + uapsEcRes.getServiceCD().length);
								// for (int k = 0; k < uapsEcRes.getServiceCD().length; k++) {
								// this.log.debug("Service Cd value :  " + uapsEcRes.getServiceCD()[i]);
								// if ("NA00002125".equals(uapsEcRes.getServiceCD()[i])) {
								// this.log.debug("Ting 요금제 가입한 휴대폰 입니다");
								// }
								// }
								// this.log.debug("########################################################");
								//
								// }

								// 암호화 정보 (JSON)
								EncryptionContents contents = this.encryptionGenerator
										.generateEncryptionContents(metaInfo);

								// JSON 파싱
								MarshallingHelper marshaller = new JacksonMarshallingHelper();
								byte[] jsonData = marshaller.marshal(contents);

								// JSON 암호화
								byte[] encryptByte = this.downloadAES128Helper.encryption(jsonData);
								String encryptString = this.downloadAES128Helper.toHexString(encryptByte);

								// 암호화 정보 (AES-128)
								Encryption encryption = new Encryption();
								encryption.setProductId(prchsProdId);
								byte[] digest = this.downloadAES128Helper.getDigest(jsonData);
								encryption.setDigest(this.downloadAES128Helper.toHexString(digest));
								encryption.setKeyIndex(String.valueOf(this.downloadAES128Helper.getSacRandomNo()));
								encryption.setToken(encryptString);
								encryptionList.add(encryption);

								// JSON 복호화
								byte[] decryptString = this.downloadAES128Helper.convertBytes(encryptString);
								byte[] decrypt = this.downloadAES128Helper.decryption(decryptString);

								try {
									String decData = new String(decrypt, "UTF-8");
									this.log.debug("----------------------------------------------------------------");
									this.log.debug("[getDownloadVodInfo] decData : {}", decData);
									this.log.debug("----------------------------------------------------------------");
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
							}
						}
					}

					// 구매 정보
					purchaseList.add(this.commonGenerator.generatePurchase(metaInfo));
					product.setPurchaseList(purchaseList);

					// 암호화 정보
					if (!encryptionList.isEmpty()) {
						product.setDl(encryptionList);
					}
				}

				/************************************************************************************************
				 * Seed App 정보
				 ************************************************************************************************/
				component = new Component();

				component.setIdentifierList(this.appInfoGenerator.generateComponentIdentifierList(metaInfo));
				component.setGameCenterVerCd(StringUtils.isNotEmpty(metaInfo.getGameCentrVerCd()) ? metaInfo
						.getGameCentrVerCd() : "");
				component.setUseYn(metaInfo.getSeedUseYn());
				component.setCaseRefCd(metaInfo.getSeedCaseRefCd());

				/************************************************************************************************
				 * 상품 정보
				 ************************************************************************************************/

				identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
						metaInfo.getProdId()));
				product.setIdentifierList(identifierList); // 상품 Id
				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명
				product.setSourceList(this.commonGenerator.generateSourceList(metaInfo)); // 상품 이미지정보
				List<Support> supportList = new ArrayList<Support>();
				supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM,
						metaInfo.getDrmYn()));
				product.setSupportList(supportList);
				product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
				product.setApp(this.appInfoGenerator.generateApp(metaInfo)); // App 상세정보
				product.setRights(this.commonGenerator.generateRights(metaInfo)); // 권한
				product.setDistributor(this.commonGenerator.generateDistributor(metaInfo)); // 판매자 정보
				product.setPacketFee(metaInfo.getProdClsfCd());
				product.setPlatClsfCd(metaInfo.getPlatClsfCd());

				commonResponse.setTotalCount(1);
			} else {
				commonResponse.setTotalCount(0);
			}

		}
		response.setCommonResponse(commonResponse);
		response.setComponent(component);
		response.setProduct(product);
		return response;
	}
}
