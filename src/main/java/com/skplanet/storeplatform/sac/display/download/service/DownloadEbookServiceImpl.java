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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
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
		MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadEbookInfo",
				downloadEbookReq);

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
				historyListSacReq.setEndDt(metaInfo.getSysDate());
				historyListSacReq.setOffset(1);
				historyListSacReq.setCount(1);
				historyListSacReq.setProductList(productList);

				// 구매내역 조회 실행
				HistoryListSacInRes historyListSacRes = this.historyInternalSCI.searchHistoryList(historyListSacReq);

				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("[getDownloadEbookInfo] purchase count : {}", historyListSacRes.getTotalCnt());
				this.logger.debug("----------------------------------------------------------------");

				if (historyListSacRes.getTotalCnt() > 0) {
					prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
					prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
					dwldExprDt = historyListSacRes.getHistoryList().get(0).getDwldExprDt();
					prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();
					prchsProdId = historyListSacRes.getHistoryList().get(0).getProdId();

					if (prchsProdId.equals(metaInfo.getStoreProdId())) {
						// 소장
						if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
							prchsState = "payment";
						} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
							prchsState = "gift";
						}
					} else {
						// 대여
						downloadEbookReq.setPrchsDt(prchsDt);
						downloadEbookReq.setDwldExprDt(dwldExprDt);

						// 대여 상품 만료여부 조회
						prchsState = (String) this.commonDAO.queryForObject("Download.getEbookPurchaseState",
								downloadEbookReq);
					}

					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("[getDownloadEbookInfo] prchsId : {}", prchsId);
					this.logger.debug("[getDownloadEbookInfo] prchsDt : {}", prchsDt);
					this.logger.debug("[getDownloadEbookInfo] dwldExprDt : {}", dwldExprDt);
					this.logger.debug("[getDownloadEbookInfo] prchsState : {}", prchsState);
					this.logger.debug("[getDownloadEbookInfo] prchsProdId : {}", prchsProdId);
					this.logger.debug("----------------------------------------------------------------");
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

			// 구매 정보
			if (StringUtils.isNotEmpty(prchsId)) {
				metaInfo.setPurchaseId(prchsId);
				metaInfo.setPurchaseProdId(prchsProdId);
				metaInfo.setPurchaseDt(prchsDt);
				metaInfo.setPurchaseState(prchsState);

				product.setPurchase(this.commonMetaInfoGenerator.generatePurchase(metaInfo));

				// 암호화 정보
				EncryptionContents contents = this.encryptionGenerator.generateEncryptionContents(metaInfo);
				// contents를 JSON 형태로 파싱
				String jsonContents = "";

				byte[] encryptByte = this.downloadAES128Helper.encryption(jsonContents.getBytes());

				Encryption encryption = new Encryption();
				encryption.setText("AES128/" + DisplayConstants.DP_FORDOWNLOAD_ENCRYPT_KEY);
				encryption.setText(new String(encryptByte));
				product.setEncryption(encryption);
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
