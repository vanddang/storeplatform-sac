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
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScResponse;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
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
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;

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
	ExistenceSacService existenceSacService;

	@Override
	public DownloadComicSacRes getDownloadComicInfo(SacRequestHeader requestHeader, DownloadComicSacReq downloadComicReq) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVodList Service started!!");
		this.logger.debug("----------------------------------------------------------------");

		DownloadComicSacRes comicRes = new DownloadComicSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String productId = downloadComicReq.getProductId();
		String deviceKey = downloadComicReq.getDeviceKey();
		String userKey = downloadComicReq.getUserKey();

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

			try {
				// 기구매 체크를 위한 생성자
				ExistenceScRequest existenceScRequest = new ExistenceScRequest();
				existenceScRequest.setTenantId(downloadComicReq.getTenantId());
				existenceScRequest.setInsdUsermbrNo(downloadComicReq.getUserKey());
				existenceScRequest.setInsdDeviceId(downloadComicReq.getDeviceKey());

				ExistenceItemSc existenceItemSc = new ExistenceItemSc();
				existenceItemSc.setProdId(downloadComicReq.getProductId());

				List<ExistenceItemSc> list = new ArrayList<ExistenceItemSc>();
				list.add(existenceItemSc);
				existenceScRequest.setExistenceItemSc(list);

				// 기구매 체크 실행
				List<ExistenceScResponse> existenceResponseList = this.existenceSacService
						.searchExistenceList(existenceScRequest);

				if (!existenceResponseList.isEmpty()) {
					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("구매 상품 ({}", existenceResponseList.toString(), ")");
					this.logger.debug("----------------------------------------------------------------");

					prchsId = existenceResponseList.get(0).getPrchsId();
				} else {
					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("미구매 상품");
					this.logger.debug("----------------------------------------------------------------");
				}
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_0001", "구매여부 확인 ", ex);
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

			List<Identifier> identifierList = new ArrayList<Identifier>();
			List<Source> sourceList = new ArrayList<Source>();
			List<Menu> menuList = new ArrayList<Menu>();

			// 상품 ID 정보
			identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			identifier.setText(metaInfo.getProdId());
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
			menu = new Menu();
			menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
			menu.setId(metaInfo.getMetaClsfCd());
			menuList.add(menu);
			product.setMenuList(menuList);

			// 책 정보
			book.setBookVersion(metaInfo.getProdVer());
			book.setScid(metaInfo.getSubContentsId());
			book.setSize(metaInfo.getFileSize());
			product.setBook(book);

			// 소장 대여 정보 (store : 소장, play : 대여)
			if (StringUtils.isNotEmpty(metaInfo.getStoreProdId())) {
				support.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
				support.setText(metaInfo.getStoreDrmYn());
				store.setSupport(support);
				price.setFixedPrice(metaInfo.getStoreProdNetAmt());
				price.setText(metaInfo.getStoreProdAmt());
				store.setPrice(price);
				source = new Source();
				source.setUrl(metaInfo.getStoreProdId());
				store.setSource(source);
				rights.setGrade(metaInfo.getProdGrdCd());
				rights.setStore(store);
			} else {
				support.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
				support.setText(metaInfo.getPlayDrmYn());
				play.setSupport(support);
				price.setFixedPrice(metaInfo.getPlayProdNetAmt());
				price.setText(metaInfo.getPlayProdAmt());
				play.setPrice(price);
				source = new Source();
				source.setUrl(metaInfo.getStoreProdId());
				play.setSource(source);
				rights.setGrade(metaInfo.getProdGrdCd());
				rights.setPlay(play);
			}
			product.setRights(rights);

			// 저작자 정보
			distributor.setName(metaInfo.getExpoSellerNm());
			distributor.setTel(metaInfo.getExpoSellerTelNo());
			distributor.setEmail(metaInfo.getExpoSellerEmail());
			distributor.setRegNo(metaInfo.getSellerMbrNo());
			product.setDistributor(distributor);

			// 구매 정보
			purchase.setPurchaseFlag(StringUtils.isNotEmpty(prchsId) ? "payment" : "nonPayment");
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
			identifier.setText(prchsId);
			purchase.setIdentifier(identifier);
			product.setPurchase(purchase);

			comicRes.setProduct(product);
			commonResponse.setTotalCount(1);
		} else {
			commonResponse.setTotalCount(0);
		}

		comicRes.setCommonResponse(commonResponse);
		return comicRes;
	}
}
