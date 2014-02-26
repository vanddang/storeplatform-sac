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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EbookComicGenerator;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;

/**
 * DownloadComic Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
public class DownloadComicServiceImpl implements DownloadComicService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	HistoryInternalSCI historyInternalSCI;

	@Autowired
	CommonMetaInfoGenerator commonMetaInfoGenerator;

	@Autowired
	private EbookComicGenerator ebookComicGenerator;

	@Autowired
	private EncryptionGenerator encryptionGenerator;

	@Autowired
	private DownloadAES128Helper downloadAES128Helper;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private DisplayCommonService commonService;

	@Override
	public DownloadComicSacRes getDownloadComicInfo(SacRequestHeader requestHeader, DownloadComicSacReq comicReq) {
		// 현재일시 및 만료일시 조회
		MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadSystemDate", null);

		String sysDate = metaInfo.getSysDate();
		String reqExpireDate = metaInfo.getExpiredDate();
		metaInfo = null;

		DownloadComicSacRes comicRes = new DownloadComicSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String productId = comicReq.getProductId();
		String deviceKey = comicReq.getDeviceKey();
		String userKey = comicReq.getUserKey();

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[getDownloadComicInfo] productId : {}", productId);
		this.logger.debug("[getDownloadComicInfo] deviceKey : {}", deviceKey);
		this.logger.debug("[getDownloadComicInfo] userKey : {}", userKey);
		this.logger.debug("----------------------------------------------------------------");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(productId)) {
			throw new StorePlatformException("SAC_DSP_0002", "productId", productId);
		}
		if (StringUtils.isEmpty(deviceKey)) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceKey", deviceKey);
		}
		if (StringUtils.isEmpty(userKey)) {
			throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey);
		}

		// 헤더정보 세팅
		comicReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
		comicReq.setLangCd(requestHeader.getTenantHeader().getLangCd());
		comicReq.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		comicReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

		// comic 상품 정보 조회(for download)
		metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadComicInfo", comicReq);

		if (metaInfo != null) {
			Product product = new Product();

			// 상품 ID 정보
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
					DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId()));
			identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
					DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId()));
			product.setIdentifierList(identifierList);

			// 상품명 정보
			product.setTitle(this.commonMetaInfoGenerator.generateTitle(metaInfo));
			product.setChnlProdNm(metaInfo.getChnlProdNm());

			// 상품 설명 정보
			product.setProductExplain(metaInfo.getProdBaseDesc());
			product.setProductDetailExplain(metaInfo.getProdDtlDesc());

			// 이미지 정보
			product.setSourceList(this.commonMetaInfoGenerator.generateDownloadSourceList(metaInfo));

			// 메뉴 정보
			product.setMenuList(this.commonMetaInfoGenerator.generateMenuList(metaInfo));

			// 도서 정보
			product.setBook(this.ebookComicGenerator.generateForDownloadBook(metaInfo));

			// 상품금액 정보
			product.setPrice(this.commonMetaInfoGenerator.generatePrice(metaInfo));

			// 이용권한 정보
			product.setRights(this.commonMetaInfoGenerator.generateRights(metaInfo));

			// 배포자 정보
			product.setDistributor(this.commonMetaInfoGenerator.generateDistributor(metaInfo));

			// 저작자 정보
			product.setContributor(this.ebookComicGenerator.generateComicContributor(metaInfo));

			// 구매내역 조회를 위한 생성자
			ProductListSacIn productListSacIn = null;
			List<ProductListSacIn> productList = null;
			HistoryListSacInReq historyReq = null;
			HistoryListSacInRes historyRes = null;

			try {
				// 구매내역 조회를 위한 생성자
				productListSacIn = new ProductListSacIn();
				productList = new ArrayList<ProductListSacIn>();

				productListSacIn.setProdId(metaInfo.getPartProdId());
				productList.add(productListSacIn);

				historyReq = new HistoryListSacInReq();
				historyReq.setTenantId(comicReq.getTenantId());
				historyReq.setUserKey(comicReq.getUserKey());
				historyReq.setDeviceKey(comicReq.getDeviceKey());
				historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
				historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
				historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
				historyReq.setEndDt(sysDate);
				historyReq.setOffset(1);
				historyReq.setCount(1000);
				historyReq.setProductList(productList);

				// 구매내역 조회 실행
				historyRes = this.historyInternalSCI.searchHistoryList(historyReq);

			} catch (Exception ex) {
				// 구매내역 조회 연동 중 오류가 발생하였습니다.
				throw new StorePlatformException("SAC_DSP_2001", ex);
			}

			String prchsId = null; // 구매ID
			String prchsDt = null; // 구매일시
			String useExprDt = null; // 이용 만료일시
			String dwldExprDt = null; // 다운로드 만료일시
			String prchsCaseCd = null; // 선물 여부
			String prchsState = null; // 구매상태
			String prchsProdId = null; // 구매 상품ID
			String prchsPrice = null; // 구매금액

			if (historyRes.getTotalCnt() > 0) {
				List<Purchase> purchaseList = new ArrayList<Purchase>();
				List<Encryption> encryptionList = new ArrayList<Encryption>();

				for (int i = 0; i < historyRes.getTotalCnt(); i++) {
					prchsId = historyRes.getHistoryList().get(i).getPrchsId();
					prchsDt = historyRes.getHistoryList().get(i).getPrchsDt();
					useExprDt = historyRes.getHistoryList().get(i).getUseExprDt();
					dwldExprDt = historyRes.getHistoryList().get(i).getDwldExprDt();
					prchsCaseCd = historyRes.getHistoryList().get(i).getPrchsCaseCd();
					prchsProdId = historyRes.getHistoryList().get(i).getProdId();
					prchsPrice = historyRes.getHistoryList().get(i).getProdAmt();

					// 구매상태 확인
					comicReq.setPrchsDt(prchsDt);
					comicReq.setDwldExprDt(dwldExprDt);
					prchsState = (String) this.commonDAO.queryForObject("Download.getDownloadPurchaseState", comicReq);

					// 구매상태 만료여부 확인
					if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
						// 구매 및 선물 여부 확인
						if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
							prchsState = "payment";
						} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
							prchsState = "gift";
						}
					}

					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("[getDownloadComicInfo] prchsId : {}", prchsId);
					this.logger.debug("[getDownloadComicInfo] prchsDt : {}", prchsDt);
					this.logger.debug("[getDownloadComicInfo] useExprDt : {}", useExprDt);
					this.logger.debug("[getDownloadComicInfo] dwldExprDt : {}", dwldExprDt);
					this.logger.debug("[getDownloadComicInfo] prchsCaseCd : {}", prchsCaseCd);
					this.logger.debug("[getDownloadComicInfo] prchsState : {}", prchsState);
					this.logger.debug("[getDownloadComicInfo] prchsProdId : {}", prchsProdId);
					this.logger.debug("[getDownloadComicInfo] prchsPrice : {}", prchsPrice);
					this.logger.debug("----------------------------------------------------------------");

					metaInfo.setPurchaseId(prchsId);
					metaInfo.setPurchaseProdId(prchsProdId);
					metaInfo.setPurchaseDt(prchsDt);
					metaInfo.setPurchaseState(prchsState);
					metaInfo.setPurchaseDwldExprDt(dwldExprDt);
					metaInfo.setPurchasePrice(Integer.parseInt(prchsPrice));

					// 구매 정보
					purchaseList.add(this.commonMetaInfoGenerator.generatePurchase(metaInfo));

					// 구매상태 만료 여부 확인
					if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
						String deviceId = null; // Device Id
						String deviceIdType = null; // Device Id 유형
						SearchDeviceIdSacReq deviceReq = null;
						SearchDeviceIdSacRes deviceRes = null;

						try {
							deviceReq = new SearchDeviceIdSacReq();
							deviceReq.setUserKey(comicReq.getUserKey());
							deviceReq.setDeviceKey(comicReq.getDeviceKey());

							// 기기정보 조회
							deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
						} catch (Exception ex) {
							// 단말정보 조회 연동 중 오류가 발생하였습니다.
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
							metaInfo.setBpJoinFileType(DisplayConstants.DP_FORDOWNLOAD_BP_DEFAULT_TYPE);

							// 암호화 정보 (JSON)
							EncryptionContents contents = this.encryptionGenerator.generateEncryptionContents(metaInfo);

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
								this.logger.debug("----------------------------------------------------------------");
								this.logger.debug("[getDownloadEbookInfo] decData : {}", decData);
								this.logger.debug("----------------------------------------------------------------");
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

			comicRes.setProduct(product);
			commonResponse.setTotalCount(1);
		} else {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("[getDownloadComicInfo] 조회된 내역이 없습니다.");
			this.logger.debug("----------------------------------------------------------------");
			commonResponse.setTotalCount(0);
		}

		comicRes.setCommonResponse(commonResponse);
		return comicRes;
	}
}
