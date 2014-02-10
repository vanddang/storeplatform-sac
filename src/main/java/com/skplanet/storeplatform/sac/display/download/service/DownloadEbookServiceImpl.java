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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EbookComicGenerator;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

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
				historyListSacReq.setTenantId(downloadEbookReq.getTenantId());
				historyListSacReq.setUserKey(downloadEbookReq.getUserKey());
				historyListSacReq.setDeviceKey(downloadEbookReq.getDeviceKey());
				historyListSacReq.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN);
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

					// 소장
					if (prchsProdId.equals(metaInfo.getStoreProdId())) {
						if (PurchaseConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
							prchsState = "payment";
						} else if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
							prchsState = "gift";
						}
					} else {
						downloadEbookReq.setPrchsDt(prchsDt);
						downloadEbookReq.setDwldExprDt(dwldExprDt);

						// 대여 상품 구매상태 조회
						prchsState = (String) this.commonDAO.queryForObject("Download.getEbookPurchaseState",
								downloadEbookReq);
					}

					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("[getDownloadEbookInfo] prchsState : {}", prchsState);
					this.logger.debug("----------------------------------------------------------------");
				}
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
			}

			Product product = new Product();
			Title title = new Title();
			Source source = new Source();
			Identifier identifier = new Identifier();
			Menu menu = new Menu();
			Book book = new Book();
			Rights rights = new Rights();
			Store store = new Store();
			Play play = new Play();
			Support support = new Support();
			Price price = new Price();
			Distributor distributor = new Distributor();
			Purchase purchase = new Purchase();
			Date date = new Date();

			List<Identifier> identifierList = new ArrayList<Identifier>();
			List<Source> sourceList = new ArrayList<Source>();
			List<Menu> menuList = new ArrayList<Menu>();
			List<Support> supportList = new ArrayList<Support>();

			// 상품 ID 정보
			identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
					DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId()));
			product.setIdentifierList(identifierList);

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
