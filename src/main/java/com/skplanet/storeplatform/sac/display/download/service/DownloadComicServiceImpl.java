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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductListSac;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.history.service.HistoryListService;

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
	HistoryListService historyListService;

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
			String prchsId = null;
			String prchsDt = null;
			String prchsState = null;

			try {
				// 구매내역 조회를 위한 생성자
				ProductListSac productListSac = new ProductListSac();
				productListSac.setProdId(metaInfo.getPartProdId());

				List<ProductListSac> productList = new ArrayList<ProductListSac>();
				productList.add(productListSac);

				HistoryListSacReq historyListSacReq = new HistoryListSacReq();
				historyListSacReq.setTenantId(downloadComicReq.getTenantId());
				historyListSacReq.setInsdUsermbrNo(downloadComicReq.getUserKey());
				historyListSacReq.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN);
				historyListSacReq.setStartDt("19000101000000");
				historyListSacReq.setEndDt(metaInfo.getSysDate());
				historyListSacReq.setOffset(1);
				historyListSacReq.setCount(1);
				historyListSacReq.setProductList(productList);

				// 구매내역 조회 실행
				HistoryListSacRes historyListSacRes = this.historyListService.searchHistoryList(historyListSacReq);

				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("[getDownloadComicInfo] purchase count : {}", historyListSacRes.getTotalCnt());
				this.logger.debug("----------------------------------------------------------------");

				if (historyListSacRes.getTotalCnt() > 0) {
					prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
					prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
					prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();

					if (PurchaseConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
						prchsState = "payment";
					} else if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
						prchsState = "gift";
					}
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
			Price price = new Price();
			Distributor distributor = new Distributor();
			Purchase purchase = new Purchase();
			Date date = new Date();

			List<Identifier> identifierList = new ArrayList<Identifier>();
			List<Source> sourceList = new ArrayList<Source>();
			List<Menu> menuList = new ArrayList<Menu>();

			// 상품 ID 정보
			identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			identifier.setText(metaInfo.getProdId());
			identifierList.add(identifier);
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(metaInfo.getPartProdId());
			identifierList.add(identifier);
			product.setIdentifierList(identifierList);

			// 상품명 정보
			title.setText(metaInfo.getProdNm());
			product.setTitle(title);

			// 이미지 정보
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getImagePath()));
			source.setUrl(metaInfo.getImagePath());
			sourceList.add(source);
			product.setSourceList(sourceList);

			// 메뉴 정보
			menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
			menu.setId(metaInfo.getTopMenuId());
			menu.setName(metaInfo.getTopMenuNm());
			menuList.add(menu);
			menu = new Menu();
			menu.setId(metaInfo.getMenuId());
			menu.setName(metaInfo.getMenuNm());
			menuList.add(menu);
			product.setMenuList(menuList);

			// 도서 정보
			book.setBookVersion(metaInfo.getProdVer());
			book.setScid(metaInfo.getSubContentsId());
			book.setSize(metaInfo.getFileSize());
			book.setType("DP004302".equals(metaInfo.getBookClsfCd()) ? "serial" : "");
			product.setBook(book);

			// 상품금액 정보
			price.setText(metaInfo.getProdAmt());
			product.setPrice(price);

			// 이용권한 정보
			rights.setGrade(metaInfo.getProdGrdCd());

			// 저작자 정보
			distributor.setName(metaInfo.getExpoSellerNm());
			distributor.setTel(metaInfo.getExpoSellerTelNo());
			distributor.setEmail(metaInfo.getExpoSellerEmail());
			distributor.setRegNo(metaInfo.getSellerMbrNo());
			product.setDistributor(distributor);

			// 구매 정보
			if (StringUtils.isNotEmpty(prchsId)) {
				purchase.setState(prchsState);
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
				identifier.setText(prchsId);
				purchase.setIdentifier(identifier);
				date.setType("date/purchase");
				date.setText(prchsDt);
				purchase.setDate(date);
			} else {
				purchase.setState("");
			}
			product.setPurchase(purchase);

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
