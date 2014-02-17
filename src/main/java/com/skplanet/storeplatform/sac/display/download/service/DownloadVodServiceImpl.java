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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceInternalSCI;
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
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

//import org.apache.commons.lang3.StringUtils;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
@Transactional
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
	private DeviceInternalSCI deviceSCI;

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

		Product product = null;

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

				String prchsId = null;
				String prchsDt = null;
				String dwldExprDt = null;
				String prchsState = null;
				String prchsProdId = null;

				try {
					// 구매내역 조회를 위한 생성자
					ProductListSacIn productListSacIn = new ProductListSacIn();
					List<ProductListSacIn> productList = new ArrayList<ProductListSacIn>();

					productListSacIn.setProdId(metaInfo.getStoreProdId());
					productList.add(productListSacIn);

					productListSacIn = new ProductListSacIn();
					productListSacIn.setProdId(metaInfo.getPlayProdId());
					productList.add(productListSacIn);

					HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
					historyListSacReq.setTenantId(downloadVodSacReq.getTenantId());
					historyListSacReq.setUserKey(downloadVodSacReq.getUserKey());
					historyListSacReq.setDeviceKey(downloadVodSacReq.getDeviceKey());
					historyListSacReq.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN);
					historyListSacReq.setStartDt("19000101000000");
					historyListSacReq.setEndDt(metaInfo.getSysDate());
					historyListSacReq.setOffset(1);
					historyListSacReq.setCount(1);
					historyListSacReq.setProductList(productList);

					// 구매내역 조회 실행
					HistoryListSacInRes historyListSacRes = this.historyInternalSCI
							.searchHistoryList(historyListSacReq);

					if (historyListSacRes.getTotalCnt() > 0) {
						prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
						prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
						dwldExprDt = historyListSacRes.getHistoryList().get(0).getDwldExprDt();
						prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();
						prchsProdId = historyListSacRes.getHistoryList().get(0).getProdId();

						// 소장
						if (prchsProdId.equals(metaInfo.getStoreProdId())) {
							if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
								prchsState = "payment";
							} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
								prchsState = "gift";
							}
						} else {
							downloadVodSacReq.setPrchsDt(prchsDt);
							downloadVodSacReq.setDwldExprDt(dwldExprDt);

							// 대여 상품 구매상태 조회
							prchsState = (String) this.commonDAO.queryForObject("Download.getEbookPurchaseState",
									downloadVodSacReq);
						}
					}
				} catch (Exception ex) {
					throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
				}

				/************************************************************************************************
				 * 상품 정보
				 ************************************************************************************************/
				product = new Product();
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

				// 구매 정보
				if (StringUtils.isNotEmpty(prchsId)) {
					/*
					 * 단말 정보 조회
					 */
					SearchDeviceIdSacReq request = new SearchDeviceIdSacReq();
					request.setUserKey("US201402110557052730002230");
					request.setDeviceKey("DE201402120409541480001552");
					SearchDeviceIdSacRes result = this.deviceSCI.searchDeviceId(request);
					String deviceId = result.getDeviceId(); // Device Id
					String deviceIdType = this.commonService.getDeviceIdType(deviceId); // Device Id 유형

					metaInfo.setPurchaseId(prchsId);
					metaInfo.setPurchaseDt(prchsDt);
					metaInfo.setPurchaseState(prchsState);
					metaInfo.setPurchaseProdId(prchsProdId);

					metaInfo.setExpiredDate(downloadSystemDate.getExpiredDate());
					// metaInfo.setDwldExprDt(dwldExprDt);
					metaInfo.setBpJoinFileType(DisplayConstants.DP_FORDOWNLOAD_BP_DEFAULT_TYPE);

					metaInfo.setUserKey(downloadVodSacReq.getUserKey());
					metaInfo.setDeviceKey(downloadVodSacReq.getDeviceKey());
					metaInfo.setDeviceType(deviceIdType);
					metaInfo.setDeviceSubKey(deviceId);

					product.setPurchase(this.commonGenerator.generatePurchase(metaInfo));

					if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(metaInfo.getPurchaseState())) {
						// 소장, 대여 구분(Store : 소장, Play : 대여)
						if (prchsProdId.equals(metaInfo.getStoreProdId())) {
							metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
							metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
						} else {
							metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
							metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
						}

						// 암호화 정보
						EncryptionContents contents = this.encryptionGenerator.generateEncryptionContents(metaInfo);

						// JSON 파싱
						MarshallingHelper marshaller = new JacksonMarshallingHelper();
						byte[] jsonData = marshaller.marshal(contents);

						// JSON 암호화
						byte[] encryptByte = this.downloadAES128Helper.encryption(jsonData);
						String encryptString = this.downloadAES128Helper.toHexString(encryptByte);

						Encryption encryption = new Encryption();
						encryption.setDigest(DisplayConstants.DP_FORDOWNLOAD_ENCRYPT_DIGEST);
						encryption.setKeyIndex(String.valueOf(this.downloadAES128Helper.getSAC_RANDOM_NUMBER()));
						encryption.setToken(encryptString);
						product.setEncryption(encryption);

						product.setEncryption(encryption);
					}

				}

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
