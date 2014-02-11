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
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EbookComicGenerator;

/**
 * DownloadComic Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
@Transactional
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

	@Override
	public DownloadComicSacRes getDownloadComicInfo(SacRequestHeader requestHeader, DownloadComicSacReq downloadComicReq) {
		DownloadComicSacRes comicRes = new DownloadComicSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String productId = downloadComicReq.getProductId();
		String deviceKey = downloadComicReq.getDeviceKey();
		String userKey = downloadComicReq.getUserKey();

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
		downloadComicReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
		downloadComicReq.setLangCd(requestHeader.getTenantHeader().getLangCd());
		downloadComicReq.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		downloadComicReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

		// comic 상품 정보 조회(for download)
		MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadComicInfo",
				downloadComicReq);

		if (metaInfo != null) {
			String prchsId = null; // 구매ID
			String prchsDt = null; // 구매일시
			String prchsState = null; // 구매상태
			String prchsProdId = null; // 구매 상품ID

			try {
				// 구매내역 조회를 위한 생성자
				ProductListSacIn productListSacIn = new ProductListSacIn();
				List<ProductListSacIn> productList = new ArrayList<ProductListSacIn>();

				productListSacIn.setProdId(metaInfo.getPartProdId());
				productList.add(productListSacIn);

				HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
				historyListSacReq.setTenantId(downloadComicReq.getTenantId());
				historyListSacReq.setUserKey(downloadComicReq.getUserKey());
				historyListSacReq.setDeviceKey(downloadComicReq.getDeviceKey());
				historyListSacReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_OWN);
				historyListSacReq.setStartDt("19000101000000");
				historyListSacReq.setEndDt(metaInfo.getSysDate());
				historyListSacReq.setOffset(1);
				historyListSacReq.setCount(1);
				historyListSacReq.setProductList(productList);

				// 구매내역 조회 실행
				HistoryListSacInRes historyListSacRes = this.historyInternalSCI.searchHistoryList(historyListSacReq);

				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("[getDownloadComicInfo] purchase count : {}", historyListSacRes.getTotalCnt());
				this.logger.debug("----------------------------------------------------------------");

				if (historyListSacRes.getTotalCnt() > 0) {
					prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
					prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
					prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();
					prchsProdId = historyListSacRes.getHistoryList().get(0).getProdId();

					if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
						prchsState = "payment";
					} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
						prchsState = "gift";
					}

					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("[getDownloadComicInfo] prchsId : {}", prchsId);
					this.logger.debug("[getDownloadComicInfo] prchsDt : {}", prchsDt);
					this.logger.debug("[getDownloadComicInfo] prchsState : {}", prchsState);
					this.logger.debug("[getDownloadComicInfo] prchsProdId : {}", prchsProdId);
					this.logger.debug("----------------------------------------------------------------");
				}
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
			}

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

			// 이미지 정보
			product.setSourceList(this.commonMetaInfoGenerator.generateSourceList(metaInfo));

			// 메뉴 정보
			product.setMenuList(this.commonMetaInfoGenerator.generateMenuList(metaInfo));

			// 도서 정보
			product.setBook(this.ebookComicGenerator.generateForDownloadBook(metaInfo));

			// 상품금액 정보
			product.setPrice(this.commonMetaInfoGenerator.generatePrice(metaInfo));

			// 이용권한 정보
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
