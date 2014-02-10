/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 공통 Meta 정보 Generator 구현체.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
@Component
public class CommonMetaInfoGeneratorImpl implements CommonMetaInfoGenerator {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifier(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Identifier generateIdentifier(String type, String text) {
		Identifier identifier = new Identifier();
		identifier.setType(type);
		identifier.setText(text);

		return identifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifier(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Identifier generateIdentifier(MetaInfo metaInfo) {
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		Identifier identifier = null;
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier = this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getProdId());
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)
				&& DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			identifier = this.generateIdentifier(DisplayConstants.DP_CATALOG_IDENTIFIER_CD, metaInfo.getCatalogId());
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier = this.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
		}
		return identifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateMenuList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Menu> generateMenuList(MetaInfo metaInfo) {
		Menu menu = new Menu();
		List<Menu> menuList = new ArrayList<Menu>();

		menu.setId(metaInfo.getMenuId());
		menu.setName(metaInfo.getMenuNm());
		menuList.add(menu);

		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(metaInfo.getTopMenuId());
		menu.setName(metaInfo.getTopMenuNm());
		menuList.add(menu);

		menu = new Menu();
		menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
		menu.setId(metaInfo.getMetaClsfCd());
		menuList.add(menu);
		return menuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSource(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Source generateSource(MetaInfo metaInfo) {
		Source source = new Source();
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		if (StringUtils.isNotEmpty(metaInfo.getImagePath())) {
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getImagePath()));
			source.setUrl(metaInfo.getImagePath());
			source.setSize(metaInfo.getImageSize());
		} else {
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getFilePath()));
			source.setUrl(metaInfo.getFilePath());
			source.setSize(metaInfo.getFileSize());
		}
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSourceList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Source> generateSourceList(MetaInfo metaInfo) {
		List<Source> sourceList = new ArrayList<Source>();
		Source source = this.generateSource(metaInfo);
		sourceList.add(source);
		return sourceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateRights(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Rights generateRights(MetaInfo metaInfo) {
		Rights rights = new Rights();
		rights.setGrade(metaInfo.getProdGrdCd());

		// 소장 정보
		if (StringUtils.isNotEmpty(metaInfo.getStoreProdId())) {
			rights.setStore(this.generateStore(metaInfo));
		}
		// 대여 정보
		if (StringUtils.isNotEmpty(metaInfo.getPlayProdId())) {
			rights.setPlay(this.generatePlay(metaInfo));
		}

		return rights;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSupport(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Support generateSupport(String type, String text) {
		Support support = new Support();
		support.setType(type);
		support.setText(text);
		return support;
	}

	@Override
	public Map<String, Object> generateSupportList(MetaInfo metaInfo) {
		return null;
	}

	@Override
	public Price generatePrice(MetaInfo metaInfo) {
		Price price = new Price();
		price.setText(metaInfo.getProdAmt());
		price.setFixedPrice(metaInfo.getProdNetAmt());
		return price;
	}

	@Override
	public Accrual generateAccrual(MetaInfo metaInfo) {
		Accrual accrual = new Accrual();
		accrual.setVoterCount(metaInfo.getPaticpersCnt());
		accrual.setDownloadCount(metaInfo.getPrchsCnt());
		accrual.setScore(metaInfo.getAvgEvluScore());

		return accrual;
	}

	@Override
	public Title generateTitle(MetaInfo metaInfo) {
		Title title = new Title();
		if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			title.setText(metaInfo.getCatalogNm());
		} else {
			title.setPrefix(metaInfo.getVodTitlNm());
			title.setText(metaInfo.getProdNm());
		}
		return title;
	}

	@Override
	public List<Identifier> generateIdentifierList(MetaInfo metaInfo) {
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		Identifier identifier = null;
		List<Identifier> identifierList = new ArrayList<Identifier>();

		this.log.debug("##### generateSpecificProductIdentifierList contentsTypeCd : {}", contentsTypeCd);
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Episode ID 기준검색일 경우
			this.log.debug("##### Episode & Channel Identifier setting");
			identifier = this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId());
			identifierList.add(identifier);

			if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
				if (metaInfo.getCatalogId() != null) {
					identifier = this.generateIdentifier(DisplayConstants.DP_CATALOG_IDENTIFIER_CD,
							metaInfo.getCatalogId());
					identifierList.add(identifier);
				}
			} else {
				if (metaInfo.getProdId() != null) {
					identifier = this.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD,
							metaInfo.getProdId());
					identifierList.add(identifier);
				}
			}

			// music 의 경우 songId
			if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
				identifier = this.generateIdentifier(DisplayConstants.DP_SONG_IDENTIFIER_CD,
						metaInfo.getOutsdContentsId());
				identifierList.add(identifier);
			}

		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd) // Catalog ID 기준 검색일 경우
				&& DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			this.log.debug("##### Catalog & Episode Identifier setting");
			identifier = this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId());
			identifierList.add(identifier);
			identifier = this.generateIdentifier(DisplayConstants.DP_CATALOG_IDENTIFIER_CD, metaInfo.getCatalogId());
			identifierList.add(identifier);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Channel ID 기준 검색일 경우
			this.log.debug("##### Channel Identifier setting");
			identifier = this.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
			identifierList.add(identifier);
		}
		return identifierList;
	}

	@Override
	public Distributor generateDistributor(MetaInfo metaInfo) {
		Distributor distributor = new Distributor();
		distributor.setName(metaInfo.getExpoSellerNm());
		distributor.setTel(metaInfo.getExpoSellerTelNo());
		distributor.setEmail(metaInfo.getExpoSellerEmail());
		distributor.setSellerKey(metaInfo.getSellerMbrNo());
		return distributor;
	}

	@Override
	public Date generateDate(String type, String text) {
		Date date = new Date();
		date.setType(type);
		date.setText(DateUtils.parseDate(text));
		return date;
	}

	@Override
	public Store generateStore(MetaInfo metaInfo) {
		Store store = new Store();

		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList
				.add(this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getStoreProdId()));
		store.setIdentifierList(identifierList);

		ArrayList<Support> supportList = new ArrayList<Support>();
		supportList.add(this.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM, metaInfo.getStoreDrmYn()));
		store.setSupportList(supportList);

		metaInfo.setProdAmt(metaInfo.getStoreProdAmt());
		metaInfo.setProdNetAmt(metaInfo.getStoreProdNetAmt());
		store.setPrice(this.generatePrice(metaInfo));

		return store;
	}

	@Override
	public Play generatePlay(MetaInfo metaInfo) {
		Play play = new Play();

		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList
				.add(this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPlayProdId()));
		play.setIdentifierList(identifierList);

		ArrayList<Support> supportList = new ArrayList<Support>();
		supportList.add(this.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM, metaInfo.getPlayDrmYn()));
		play.setSupportList(supportList);

		metaInfo.setProdAmt(metaInfo.getStoreProdAmt());
		metaInfo.setProdNetAmt(metaInfo.getStoreProdNetAmt());
		play.setPrice(this.generatePrice(metaInfo));

		play.setDate(this.generateDate(DisplayConstants.DP_DATE_USAGE_PERIOD, metaInfo.getUsePeriodNm()));

		return play;
	}

	@Override
	public Purchase generatePurchase(MetaInfo metaInfo) {
		Purchase purchase = new Purchase();

		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList
				.add(this.generateIdentifier(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD, metaInfo.getPurchaseId()));
		identifierList.add(this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
				metaInfo.getPurchaseProdId()));
		purchase.setIdentifierList(identifierList);

		purchase.setDate(this.generateDate(DisplayConstants.DP_SHOPPING_PURCHASE_TYPE_NM, metaInfo.getPurchaseDt()));

		return purchase;
	}
}
