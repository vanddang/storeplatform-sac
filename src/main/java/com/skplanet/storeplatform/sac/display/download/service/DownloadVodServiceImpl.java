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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
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
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;
import com.skplanet.storeplatform.sac.display.response.VodGenerator;

//import org.apache.commons.lang3.StringUtils;

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
	HistoryInternalSCI historyInternalSCI;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private VodGenerator vodGenerator;
	@Autowired
	private EncryptionGenerator encryptionGenerator;
	@Autowired
	private DownloadAES128Helper downloadAES128Helper;
	@Autowired
	private DeviceSCI deviceSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadVodService#DownloadVodService(com.skplanet
	 * .storeplatform.sac.client.product.vo.DownloadVodReqVO)
	 */
	@Override
	public DownloadVodSacRes searchDownloadVod(SacRequestHeader requestheader, DownloadVodSacReq downloadVodSacReq) {
		TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		MetaInfo downloadSystemDate = this.commonDAO.queryForObject("Download.selectDownloadSystemDate", "",
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

		this.log.info("----------------------------------------------------------------");
		this.log.info("[DownloadVodServiceImpl] idType : {}", idType);
		this.log.info("[DownloadVodServiceImpl] productId : {}", productId);
		this.log.info("[DownloadVodServiceImpl] deviceKey : {}", deviceKey);
		this.log.info("[DownloadVodServiceImpl] userKey : {}", userKey);
		this.log.info("----------------------------------------------------------------");

		// 다운로드 Vod 상품 조회
		MetaInfo metaInfo = this.commonDAO.queryForObject("Download.getDownloadVodInfo", downloadVodSacReq,
				MetaInfo.class);

		Product product = new Product();

		if (metaInfo != null) {
			if (DisplayConstants.DP_CHANNEL_IDENTIFIER_CD.equals(idType)) {
				if (DisplayConstants.DP_SERIAL_VOD_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())) {
					throw new StorePlatformException("SAC_DSP_0013");
				}
			}

			this.log.info("----------------------------------------------------------------");
			this.log.info("[DownloadVodServiceImpl] NORMAL scid : {}", metaInfo.getNmSubContsId());
			this.log.info("[DownloadVodServiceImpl] SD scid : {}", metaInfo.getSdSubContsId());
			this.log.info("[DownloadVodServiceImpl] HD scid : {}", metaInfo.getHdSubContsId());
			this.log.info("[DownloadVodServiceImpl] CID : {}", metaInfo.getCid());
			this.log.info("----------------------------------------------------------------");

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

					this.log.info("----------------------------------------------------------------");
					this.log.info("********************	구매 요청 파라미터	***************************");
					this.log.info("[DownloadVodServiceImpl] tenantId : {}", historyReq.getTenantId());
					this.log.info("[DownloadVodServiceImpl] userKey : {}", historyReq.getUserKey());
					this.log.info("[DownloadVodServiceImpl] deviceKey : {}", historyReq.getDeviceKey());
					this.log.info("[DownloadVodServiceImpl] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
					this.log.info("[DownloadVodServiceImpl] prchsProdtype : {}", historyReq.getPrchsProdType());
					this.log.info("[DownloadVodServiceImpl] startDt : {}", historyReq.getStartDt());
					this.log.info("[DownloadVodServiceImpl] endDt : {}", historyReq.getEndDt());
					this.log.info("[DownloadVodServiceImpl] offset : {}", historyReq.getOffset());
					this.log.info("[DownloadVodServiceImpl] count : {}", historyReq.getCount());
					this.log.info("[DownloadVodServiceImpl] store prodId : {}", productList.get(0).getProdId());
					this.log.info("[DownloadVodServiceImpl] play prodId : {}", productList.get(1).getProdId());
					this.log.info("----------------------------------------------------------------");

					// 구매내역 조회 실행
					this.log.info("##### [SAC DSP LocalSCI] SAC Purchase Start : historyInternalSCI.searchHistoryList");
					long start = System.currentTimeMillis();
					historyRes = this.historyInternalSCI.searchHistoryList(historyReq);
					this.log.info("##### [SAC DSP LocalSCI] SAC Purchase End : historyInternalSCI.searchHistoryList");
					long end = System.currentTimeMillis();
					this.log.info(
							"##### [SAC DSP LocalSCI] SAC Purchase historyInternalSCI.searchHistoryList takes {} ms",
							(end - start));

				} catch (Exception ex) {
					purchaseFlag = false;
					this.log.info("[DownloadVodServiceImpl] Purchase History Search Exception : {}");
					this.log.error("구매내역 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
					// throw new StorePlatformException("SAC_DSP_2001", ex);
				}

				this.log.info("---------------------------------------------------------------------");
				this.log.info("[DownloadVodServiceImpl] purchaseFlag :{}", purchaseFlag);
				this.log.info("[DownloadVodServiceImpl] historyRes :{}", historyRes);

				if (purchaseFlag && historyRes != null) {
					this.log.info("[DownloadVodServiceImpl] 구매건수 :{}", historyRes.getTotalCnt());
					this.log.info("---------------------------------------------------------------------");

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
					
					if (historyRes.getTotalCnt() > 0) {
						List<Purchase> purchaseList = new ArrayList<Purchase>();
						List<Encryption> encryptionList = new ArrayList<Encryption>();

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

							// 구매상태 확인
							downloadVodSacReq.setPrchsDt(prchsDt);
							downloadVodSacReq.setDwldStartDt(dwldStartDt);
							downloadVodSacReq.setDwldExprDt(dwldExprDt);
							// prchsState = (String) this.commonDAO.queryForObject("Download.getDownloadPurchaseState",
							// downloadVodSacReq);

							prchsState = (String) ((HashMap) this.commonDAO.queryForObject(
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

							this.log.info("----------------------------------------------------------------");
							this.log.info("[DownloadVodServiceImpl] prchsId : {}", prchsId);
							this.log.info("[DownloadVodServiceImpl] prchsDt : {}", prchsDt);
							this.log.info("[DownloadVodServiceImpl] useExprDt : {}", useExprDt);
							this.log.info("[DownloadVodServiceImpl] dwldStartDt : {}", dwldStartDt);
							this.log.info("[DownloadVodServiceImpl] dwldExprDt : {}", dwldExprDt);
							this.log.info("[DownloadVodServiceImpl] prchsCaseCd : {}", prchsCaseCd);
							this.log.info("[DownloadVodServiceImpl] prchsState : {}", prchsState);
							this.log.info("[DownloadVodServiceImpl] prchsProdId : {}", prchsProdId);
							this.log.info("[DownloadVodServiceImpl] prchsPrice : {}", puchsPrice);
							this.log.info("----------------------------------------------------------------");

							metaInfo.setPurchaseId(prchsId);
							metaInfo.setPurchaseProdId(prchsProdId);
							metaInfo.setPurchaseDt(prchsDt);
							metaInfo.setPurchaseState(prchsState);
							metaInfo.setPurchaseDwldExprDt(dwldExprDt);
							metaInfo.setPurchasePrice(Integer.parseInt(puchsPrice));

							// 구매 정보
							purchaseList.add(this.commonGenerator.generatePurchase(metaInfo));

							/************************************************************************************************
							 * 구매 정보에 따른 암호화 시작
							 ************************************************************************************************/
							this.log.info("----------------------------------------------------------------");
							this.log.info("[DownloadVodServiceImpl] prchsState	:	{}", prchsState);
							this.log.info("----------------------------------------------------------------");

							// 구매상태 만료 여부 확인
							if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)
									&& permitDeviceYn.equals("Y")) {
								this.log.info("----------------------------  start set Purchase Info  ------------------------------------");
								String deviceId = null; // Device Id
								String deviceIdType = null; // Device Id 유형
								SearchDeviceIdSacReq deviceReq = null;
								SearchDeviceIdSacRes deviceRes = null;
								boolean memberFlag = true;

								try {
									deviceReq = new SearchDeviceIdSacReq();
									deviceReq.setUserKey(downloadVodSacReq.getUserKey());
									deviceReq.setDeviceKey(downloadVodSacReq.getDeviceKey());
									this.log.info("----------------------------------------------------------------");
									this.log.info("*******************회원 단말 정보 조회 파라미터*********************");
									this.log.info("[DownloadVodServiceImpl] userKey : {}", deviceReq.getUserKey());
									this.log.info("[DownloadVodServiceImpl] deviceKey : {}", deviceReq.getDeviceKey());
									this.log.info("----------------------------------------------------------------");

									// 기기정보 조회
									this.log.info("##### [SAC DSP LocalSCI] SAC Member Start : deviceSCI.searchDeviceId");
									long start = System.currentTimeMillis();
									deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
									this.log.info("##### [SAC DSP LocalSCI] SAC Member End : deviceSCI.searchDeviceId");
									long end = System.currentTimeMillis();
									this.log.info(
											"##### [SAC DSP LocalSCI] SAC Member deviceSCI.searchDeviceId takes {} ms",
											(end - start));
									this.log.info("---------------------------------------------------------------");
									this.log.info("[DownloadVodServiceImpl] deviceRes.getDeviceId{} : "
											+ deviceRes.getDeviceId());
									this.log.info("---------------------------------------------------------------");
								} catch (Exception ex) {
									memberFlag = false;
									this.log.info("[DownloadVodServiceImpl] Device Search Exception : {}");
									this.log.error("단말정보 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
									// throw new StorePlatformException("SAC_DSP_1001", ex);
								}

								this.log.info("----------------------------------------------------------------");
								this.log.info("[DownloadVodServiceImpl] memberFlag	:	{}", memberFlag);
								this.log.info("[DownloadVodServiceImpl] deviceRes	:	{}", deviceRes);
								this.log.info("----------------------------------------------------------------");

								if (memberFlag && deviceRes != null) {
									// MDN 인증여부 확인 (2014.05.22 회원 API 변경에 따른 추가)
									if ("Y".equals(deviceRes.getAuthYn())) {
										this.log.info("----------------------------------------------------------------");
										this.log.info("[DownloadVodServiceImpl] Start Encription");

										deviceId = deviceRes.getDeviceId();
										this.log.info("[DownloadVodServiceImpl] deviceId	: {}", deviceId);
										deviceIdType = this.commonService.getDeviceIdType(deviceId);
										this.log.info("[DownloadVodServiceImpl] deviceIdType	:	{}", deviceIdType);
										this.log.info("[DownloadVodServiceImpl] reqExpireDate	:	{}", reqExpireDate);
										this.log.info("[DownloadVodServiceImpl] useExprDt	:	{}", useExprDt);
										this.log.info("[DownloadVodServiceImpl] userKey	:	{}", userKey);
										this.log.info("[DownloadVodServiceImpl] deviceKey	:	{}", deviceKey);

										metaInfo.setExpiredDate(reqExpireDate);
										metaInfo.setUseExprDt(useExprDt);
										metaInfo.setUserKey(userKey);
										metaInfo.setDeviceKey(deviceKey);
										metaInfo.setDeviceType(deviceIdType);
										metaInfo.setDeviceSubKey(deviceId);

										// 2014.07.01. kdlim. 구매 내역 drmYn 값이 정확하지 않아 상품정보 drmYn으로 변경
										// 단, T Freemium을 통한 구매건의 경우는 무조건 DRM적용이므로 아래의 조건을 예외처리 해야함.
										//-	"prchsReqPathCd": "OR0004xx",
										//-	OR000413, OR000420 2개 코드가 T Freemium을 통한 구매건임.
										if(StringUtils.equals(DisplayConstants.PRCHS_REQ_PATH_TFREEMIUM1_CD, prchsReqPathCd) 
												|| StringUtils.equals(DisplayConstants.PRCHS_REQ_PATH_TFREEMIUM2_CD, prchsReqPathCd)) {
											metaInfo.setDrmYn("Y");
											metaInfo.setStoreDrmYn("Y");
											metaInfo.setPlayDrmYn("Y");
										}
										else {

											// 소장, 대여 구분(Store : 소장, Play : 대여)
											if (prchsProdId.equals(metaInfo.getStoreProdId())) {
												metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
												metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
											} else {
												metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
												metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
											}
										}

										
										/*
										if (StringUtils.isNotEmpty(drmYn)) {
											metaInfo.setStoreDrmYn(drmYn);
											metaInfo.setPlayDrmYn(drmYn);
										}
										*/

										
										this.log.info("DownloadVodServiceImpl prchsReqPathCd={}, StoreProdId={}, PlayDrmYn={}, DrmYn={}", prchsReqPathCd, metaInfo.getStoreProdId(), metaInfo.getPlayDrmYn(), metaInfo.getDrmYn());

										// 암호화 정보 (JSON)
										EncryptionContents contents = this.encryptionGenerator
												.generateEncryptionContents(metaInfo);

										this.log.info("[DownloadVodServiceImpl] contents	:	{}", contents);

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
										encryption.setKeyIndex(String.valueOf(this.downloadAES128Helper
												.getSacRandomNo()));
										encryption.setToken(encryptString);
										encryptionList.add(encryption);

										this.log.info("-------------------------------------------------------------");
										this.log.info("[DownloadVodServiceImpl] token : {}", encryption.getToken());
										this.log.info("[DownloadVodServiceImpl] keyIdx : {}", encryption.getKeyIndex());
										this.log.info("--------------------------------------------------------------");
										// JSON 복호화
										// byte[] decryptString = this.downloadAES128Helper.convertBytes(encryptString);
										// byte[] decrypt = this.downloadAES128Helper.decryption(decryptString);
										//
										// try {
										// String decData = new String(decrypt, "UTF-8");
										// this.log.debug("----------------------------------------------------------------");
										// this.log.debug("[DownloadVodServiceImpl] decData : {}", decData);
										// System.out.println("decData	:	" + decData);
										// this.log.debug("----------------------------------------------------------------");
										// } catch (UnsupportedEncodingException e) {
										// e.printStackTrace();
										// }
									} else {
										this.log.info("##### [SAC DSP LocalSCI] userKey : {}", deviceReq.getUserKey());
										this.log.info("##### [SAC DSP LocalSCI] deviceKey : {}",
												deviceReq.getDeviceKey());
										this.log.info("##### [SAC DSP LocalSCI] NOT VALID DEVICE_ID : "
												+ deviceRes.getDeviceId());
									}
								}
								// 구매 정보
								product.setPurchaseList(purchaseList);
								this.log.info("----------------------------------------------------------------");
								// 암호화 정보
								if (!encryptionList.isEmpty()) {
									this.log.info("[DownloadVodServiceImpl]	setDl : {}");
									product.setDl(encryptionList);
								}

								this.log.debug("[DownloadVodServiceImpl] End Encription");
								this.log.info("----------------------------------------------------------------");
								this.log.info("----------------------------  end set Purchase Info  ------------------------------------");
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
			Identifier identifier = new Identifier();

			identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD,
					metaInfo.getProdId());
			identifierList.add(identifier);

			metaInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

			identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
					metaInfo.getEspdProdId());
			identifierList.add(identifier);

			// CID
			identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD,
					metaInfo.getCid());
			identifierList.add(identifier);

			product.setIdentifierList(identifierList); // 상품 ID
			// product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
			supportList = new ArrayList<Support>();
			supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HDCP_SUPPORT_NM,
					metaInfo.getHdcpYn()));
			supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM,
					metaInfo.getHdvYn()));
			supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_BTV_SUPPORT_NM, "Y"));
			supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_DOLBY_NM,
					metaInfo.getDolbySprtYn()));
			product.setSupportList(supportList);
			product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명
			product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
			product.setSourceList(this.commonGenerator.generateSourceList(metaInfo)); // 상품 이미지정보
			product.setVod(this.vodGenerator.generateVod(metaInfo)); // VOD 정보
			product.setRights(this.commonGenerator.generateRights(metaInfo)); // 이용등급 및 소장/대여 정보
			product.setDistributor(this.commonGenerator.generateDistributor(metaInfo)); // 판매자 정보

			commonResponse.setTotalCount(1);
		} else {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}
}
