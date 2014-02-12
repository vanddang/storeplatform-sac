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
import org.apache.xmlbeans.impl.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EbookComicGenerator;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;
import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * DownloadEbook Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
@Transactional
public class DownloadEbookServiceImpl implements DownloadEbookService {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.download.service.DownloadEbookService#getDownloadEbookInfo(com.skplanet
	 * .storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq)
	 */
	@Override
	public DownloadEbookSacRes getDownloadEbookInfo(SacRequestHeader requestHeader, DownloadEbookSacReq downloadEbookReq) {
		// 현재일시 및 만료일시 조회
		MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadSystemDate", null);

		String sysDate = metaInfo.getSysDate();
		String expireDate = metaInfo.getExpiredDate();
		metaInfo = null;

		DownloadEbookSacRes ebookRes = new DownloadEbookSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String idType = downloadEbookReq.getIdType();
		String productId = downloadEbookReq.getProductId();
		String deviceKey = downloadEbookReq.getDeviceKey();
		String userKey = downloadEbookReq.getUserKey();

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[getDownloadEbookInfo] idType : {}", idType);
		this.logger.debug("[getDownloadEbookInfo] productId : {}", productId);
		this.logger.debug("[getDownloadEbookInfo] deviceKey : {}", deviceKey);
		this.logger.debug("[getDownloadEbookInfo] userKey : {}", userKey);
		this.logger.debug("----------------------------------------------------------------");

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

		// 헤더정보 세팅
		downloadEbookReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
		downloadEbookReq.setLangCd(requestHeader.getTenantHeader().getLangCd());
		downloadEbookReq.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		downloadEbookReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

		// ebook 상품 정보 조회(for download)
		metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadEbookInfo", downloadEbookReq);

		if (metaInfo != null) {
			String prchsId = null; // 구매ID
			String prchsDt = null; // 구매일시
			String dwldExprDt = null; // 다운로드 만료일시
			String prchsState = null; // 구매상태
			String prchsProdId = null; // 구매 상품ID

			try {
				// 구매내역 조회를 위한 생성자
				ProductListSacIn productListSacIn = new ProductListSacIn();
				List<ProductListSacIn> productList = new ArrayList<ProductListSacIn>();

				// 소장 상품ID
				productListSacIn.setProdId(metaInfo.getStoreProdId());
				productList.add(productListSacIn);

				// 대여 상품ID
				productListSacIn = new ProductListSacIn();
				productListSacIn.setProdId(metaInfo.getPlayProdId());
				productList.add(productListSacIn);

				HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
				historyListSacReq.setTenantId(downloadEbookReq.getTenantId());
				historyListSacReq.setUserKey(downloadEbookReq.getUserKey());
				historyListSacReq.setDeviceKey(downloadEbookReq.getDeviceKey());
				historyListSacReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_OWN);
				historyListSacReq.setStartDt("19000101000000");
				historyListSacReq.setEndDt(sysDate);
				historyListSacReq.setOffset(1);
				historyListSacReq.setCount(1);
				historyListSacReq.setProductList(productList);

				// 구매내역 조회 실행
				HistoryListSacInRes historyListSacRes = this.historyInternalSCI.searchHistoryList(historyListSacReq);

				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("[getDownloadEbookInfo] purchase count : {}", historyListSacRes.getTotalCnt());
				this.logger.debug("----------------------------------------------------------------");

				if (historyListSacRes != null && historyListSacRes.getTotalCnt() > 0) {
					prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
					prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
					dwldExprDt = historyListSacRes.getHistoryList().get(0).getDwldExprDt();
					prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();
					prchsProdId = historyListSacRes.getHistoryList().get(0).getProdId();

					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("[getDownloadEbookInfo] prchsId : {}", prchsId);
					this.logger.debug("[getDownloadEbookInfo] prchsDt : {}", prchsDt);
					this.logger.debug("[getDownloadEbookInfo] dwldExprDt : {}", dwldExprDt);
					this.logger.debug("[getDownloadEbookInfo] prchsState : {}", prchsState);
					this.logger.debug("[getDownloadEbookInfo] prchsProdId : {}", prchsProdId);
					this.logger.debug("----------------------------------------------------------------");

					// 소장, 대여 구분(Store : 소장, Play : 대여)
					if (prchsProdId.equals(metaInfo.getStoreProdId())) {
						if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
							prchsState = "payment";
						} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
							prchsState = "gift";
						}
					} else {
						downloadEbookReq.setPrchsDt(prchsDt);
						downloadEbookReq.setDwldExprDt(dwldExprDt);

						// 대여 상품 만료여부 조회
						prchsState = (String) this.commonDAO.queryForObject("Download.getEbookPurchaseState",
								downloadEbookReq);
					}

				}
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
			}

			Product product = new Product();

			// 상품 ID 정보
			metaInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
			product.setIdentifierList(this.commonMetaInfoGenerator.generateIdentifierList(metaInfo));

			// 상품명 정보
			product.setTitle(this.commonMetaInfoGenerator.generateTitle(metaInfo));

			// 이미지 정보
			product.setSourceList(this.commonMetaInfoGenerator.generateSourceList(metaInfo));

			// 메뉴 정보
			product.setMenuList(this.commonMetaInfoGenerator.generateMenuList(metaInfo));

			// 도서 정보
			product.setBook(this.ebookComicGenerator.generateForDownloadBook(metaInfo));

			// 이용등급 및 소장/대여 정보
			product.setRights(this.commonMetaInfoGenerator.generateRights(metaInfo));

			// 저작자 정보
			product.setDistributor(this.commonMetaInfoGenerator.generateDistributor(metaInfo));

			// 구매 여부 확인
			if (StringUtils.isNotEmpty(prchsId)) {
				metaInfo.setExpiredDate(expireDate);
				metaInfo.setPurchaseId(prchsId);
				metaInfo.setPurchaseProdId(prchsProdId);
				metaInfo.setPurchaseDt(prchsDt);
				metaInfo.setPurchaseState(prchsState);
				metaInfo.setDwldExprDt(dwldExprDt);
				metaInfo.setUserKey(userKey);
				metaInfo.setDeviceKey(deviceKey);

				// 소장, 대여 구분(Store : 소장, Play : 대여)
				if (prchsProdId.equals(metaInfo.getStoreProdId())) {
					metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
					metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
				} else {
					metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
					metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
				}

				// 인터파크 DRM 타입
				if (StringUtils.isNotEmpty(metaInfo.getBpJoinFileNo())) {
					metaInfo.setBpJoinFileType(DisplayConstants.DP_FORDOWNLOAD_BP_EBOOK_TYPE);
				} else {
					metaInfo.setBpJoinFileType(DisplayConstants.DP_FORDOWNLOAD_BP_DEFAULT_TYPE);
				}

				// 구매 정보
				product.setPurchase(this.commonMetaInfoGenerator.generatePurchase(metaInfo));

				// 암호화 정보
				EncryptionContents contents = this.encryptionGenerator.generateEncryptionContents(metaInfo);

				// JSON 파싱
				MarshallingHelper marshaller = new JacksonMarshallingHelper();
				byte[] jsonData = marshaller.marshal(contents);

				// JSON 암호화
				byte[] encryptByte = this.downloadAES128Helper.encryption(jsonData);

				Encryption encryption = new Encryption();
				encryption.setType(DisplayConstants.DP_FORDOWNLOAD_ENCRYPT_TYPE + "/"
						+ this.downloadAES128Helper.getSAC_RANDOM_NUMBER());

				// JSON 암호화값을 BASE64 Encoding
				Base64Encoder encoder = new Base64Encoder();
				String encryptString = encoder.encode(encryptByte);
				encryption.setText(encryptString);

				product.setEncryption(encryption);
			}

			// 테스트를 위한 복호화 확인
			try {
				Encryption testEn = new Encryption();
				testEn = product.getEncryption();

				byte[] testValue = Base64.decode(testEn.getText().getBytes());
				byte[] dec = this.downloadAES128Helper.decryption(testValue);

				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("Encryption Type : {}", testEn.getType());
				this.logger.debug("Encryption Text : {}", testEn.getText());
				this.logger.debug("Decryption Text : {}", new String(dec, "UTF-8"));
				this.logger.debug("----------------------------------------------------------------");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			ebookRes.setProduct(product);
			commonResponse.setTotalCount(1);
		} else {
			commonResponse.setTotalCount(0);
		}

		ebookRes.setCommonResponse(commonResponse);
		return ebookRes;
	}
}
