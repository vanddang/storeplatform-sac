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
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
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

		DownloadVodSacRes response = new DownloadVodSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String idType = downloadVodSacReq.getIdType();
		String productId = downloadVodSacReq.getProductId();
		String deviceKey = downloadVodSacReq.getDeviceKey();
		String userKey = downloadVodSacReq.getUserKey();

		List<Support> supportList = null;

		Product product = new Product();

		if (downloadVodSacReq.getDummy() == null) {

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(idType)) {
				throw new StorePlatformException("SAC_DSP_0002", "idType", idType);
			}
			if (StringUtils.isEmpty(productId)) {
				throw new StorePlatformException("SAC_DSP_0002", "productId", productId);
			}
			if (StringUtils.isEmpty(deviceKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "deviceKey", deviceKey);
			}
			if (StringUtils.isEmpty(userKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey);
			}

			// ID유형 유효값 체크
			if (!"channel".equals(idType) && !"episode".equals(idType)) {
				throw new StorePlatformException("SAC_DSP_0003", "idType", idType);
			}

			// 다운로드 Vod 상품 조회
			MetaInfo metaInfo = this.commonDAO.queryForObject("Download.getDownloadVodInfo", downloadVodSacReq,
					MetaInfo.class);

			product = new Product();

			if (metaInfo != null) {

				// 구매내역 조회를 위한 생성자
				ProductListSacIn productListSacIn = null;
				List<ProductListSacIn> productList = null;
				HistoryListSacInReq historyReq = null;
				HistoryListSacInRes historyRes = null;

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
					historyReq.setEndDt(sysDate);
					historyReq.setOffset(1);
					historyReq.setCount(productList.size());
					historyReq.setProductList(productList);

					// 구매내역 조회 실행
					historyRes = this.historyInternalSCI.searchHistoryList(historyReq);

				} catch (Exception ex) {
					throw new StorePlatformException("SAC_DSP_2001", ex);
				}

				String prchsId = null; // 구매ID
				String prchsDt = null; // 구매일시
				String useExprDt = null; // 이용 만료일시
				String dwldExprDt = null; // 다운로드 만료일시
				String prchsCaseCd = null; // 선물 여부
				String prchsState = null; // 구매상태
				String prchsProdId = null; // 구매 상품ID
				String puchsPrice = null; // 구매 상품금액

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

						// 구매상태 확인
						downloadVodSacReq.setPrchsDt(prchsDt);
						downloadVodSacReq.setDwldExprDt(dwldExprDt);
						prchsState = (String) this.commonDAO.queryForObject("Download.getDownloadPurchaseState",
								downloadVodSacReq);

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
						this.log.debug("[getDownloadVodkInfo] prchsId : {}", prchsId);
						this.log.debug("[getDownloadVodkInfo] prchsDt : {}", prchsDt);
						this.log.debug("[getDownloadVodkInfo] useExprDt : {}", useExprDt);
						this.log.debug("[getDownloadVodkInfo] dwldExprDt : {}", dwldExprDt);
						this.log.debug("[getDownloadVodkInfo] prchsCaseCd : {}", prchsCaseCd);
						this.log.debug("[getDownloadVodkInfo] prchsState : {}", prchsState);
						this.log.debug("[getDownloadVodkInfo] prchsProdId : {}", prchsProdId);
						this.log.debug("----------------------------------------------------------------");

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
						// 구매상태 만료 여부 확인
						if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
							String deviceId = null; // Device Id
							String deviceIdType = null; // Device Id 유형
							SearchDeviceIdSacReq deviceReq = null;
							SearchDeviceIdSacRes deviceRes = null;

							try {
								deviceReq = new SearchDeviceIdSacReq();
								deviceReq.setUserKey(downloadVodSacReq.getUserKey());
								deviceReq.setDeviceKey(downloadVodSacReq.getDeviceKey());

								// 기기정보 조회
								deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
							} catch (Exception ex) {
								throw new StorePlatformException("SAC_DSP_1001", ex);
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

								// 소장, 대여 구분(Store : 소장, Play : 대여)
								if (prchsProdId.equals(metaInfo.getStoreProdId())) {
									metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
									metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
								} else {
									metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
									metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
								}

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
								encryption.setDigest(DisplayConstants.DP_FORDOWNLOAD_ENCRYPT_DIGEST);
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
					product.setPurchaseList(purchaseList);

					// 암호화 정보
					if (!encryptionList.isEmpty()) {
						product.setDl(encryptionList);
					}
				}

				/************************************************************************************************
				 * 상품 정보
				 ************************************************************************************************/

				metaInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
				product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
				supportList = new ArrayList<Support>();
				supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HDCP_SUPPORT_NM,
						metaInfo.getHdcpYn()));
				supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM,
						metaInfo.getHdvYn()));
				supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_BTV_SUPPORT_NM,
						metaInfo.getBtvYn()));
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
				commonResponse.setTotalCount(0);
			}

		}

		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}
}
