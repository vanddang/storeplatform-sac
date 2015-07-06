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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Badge;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 공통 Meta 정보 Generator 구현체.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
@Component
public class CommonMetaInfoGeneratorImpl implements CommonMetaInfoGenerator {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DisplayCommonService commonService;

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
		menu.setDesc(metaInfo.getMenuDesc());

		menuList.add(menu);

		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(metaInfo.getTopMenuId());
		menu.setName(metaInfo.getTopMenuNm());
		menuList.add(menu);

		// 메타 클래스 정보
		if (StringUtils.isNotEmpty(metaInfo.getMetaClsfCd())) {
			menu = new Menu();
			menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
			menu.setId(metaInfo.getMetaClsfCd());
			menuList.add(menu);
		}

		// 장르 정보
		if (StringUtils.isNotEmpty(metaInfo.getGenreCd())) {
			menu = new Menu();
			menu.setType(DisplayConstants.DP_MENU_TYPE_GENRE);
			menu.setId(metaInfo.getGenreCd());
			menuList.add(menu);
		}

		// 장르2 정보
		if (StringUtils.isNotEmpty(metaInfo.getGenreCd2())) {
			menu = new Menu();
			menu.setType(DisplayConstants.DP_MENU_TYPE_SUB_GENRE);
			menu.setId(metaInfo.getGenreCd2());
			menuList.add(menu);
		}

		// 서비스 그룹 코드 클래스 정보
		if (StringUtils.isNotEmpty(metaInfo.getSvcGrpCd())) {
			menu = new Menu();
			menu.setType(DisplayConstants.DP_SVC_GRP_CD_TYPE);
			menu.setName(metaInfo.getSvcGrpNm());
			menu.setId(metaInfo.getSvcGrpCd());
			menuList.add(menu);
		}

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

		if (StringUtils.isEmpty(metaInfo.getImagePath()) && StringUtils.isEmpty(metaInfo.getFilePath()))
			return null;

		Source source = new Source();

		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		if (StringUtils.isNotEmpty(metaInfo.getImagePath())) {
			source = this.generateSource(metaInfo.getImagePath());
		} else {
			source = this.generateSource(metaInfo.getFilePath());
		}
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSource(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Source generateSource(String type, String url) {
		Source source = new Source();

		if (StringUtils.isNotEmpty(url)) {
			source.setMediaType(DisplayCommonUtil.getMimeType(url));
			source.setUrl(url);
		}

		if (StringUtils.isNotEmpty(type)) {
			source.setType(type);
		}

		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSource(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Source generateSource(String type, String url, Long size) {
		Source source = new Source();

		if (StringUtils.isNotEmpty(url)) {
			source.setMediaType(DisplayCommonUtil.getMimeType(url));
			source.setUrl(url);
		}

		if (StringUtils.isNotEmpty(type)) {
			source.setType(type);
		}

		if (size != null) {
			source.setSize(size);
		}

		return source;
	}

	@Override
	public Source generateSource(String type, String url, String ratio) {
		Source source = new Source();

		if (StringUtils.isNotEmpty(url)) {
			source.setMediaType(DisplayCommonUtil.getMimeType(url));
			source.setUrl(url);
		}

		if (StringUtils.isNotEmpty(type)) {
			source.setType(type);
		}

		if (StringUtils.isNotEmpty(ratio)) {
			source.setRatio(ratio);
		}

		return source;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSource(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public Source generateSource(String path) {
		Source source = null;

		if (StringUtils.isNotEmpty(path)) {
			source = new Source();
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setMediaType(DisplayCommonUtil.getMimeType(path));
			source.setUrl(path);
		}
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateBannerSourceList(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public List<Source> generateBannerSourceList(MetaInfo metaInfo) {
		List<Source> sourceList = null;

		if (StringUtils.isEmpty(metaInfo.getScSamplUrl())) {
			if (StringUtils.isNotEmpty(metaInfo.getImagePath())) {
				Source source = new Source();
				sourceList = new ArrayList<Source>();
				source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getImagePath()));
				source.setUrl(metaInfo.getImagePath());
				sourceList.add(source);
			}
		} else {
			Source source = new Source();
			sourceList = new ArrayList<Source>();
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getScSamplUrl()));
			source.setType(DisplayConstants.DP_PREVIEW_LQ);
			source.setUrl(this.commonService.makePreviewUrl(metaInfo.getScSamplUrl()));
			sourceList.add(source);

			source = new Source();
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getSamplUrl()));
			source.setType(DisplayConstants.DP_PREVIEW_HQ);
			source.setUrl(this.commonService.makePreviewUrl(metaInfo.getSamplUrl()));
			sourceList.add(source);
		}

		return sourceList;
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
		if (source != null)
			sourceList.add(source);

		return sourceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSourceList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Source> generateVodSourceList(MetaInfo metaInfo) {
		List<Source> sourceList = new ArrayList<Source>();
		Source source = null;

		if (StringUtils.isNotEmpty(metaInfo.getScSamplUrl())) {
			source = new Source();
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getScSamplUrl()));
			source.setType(DisplayConstants.DP_PREVIEW_LQ);
			source.setUrl(this.commonService.makePreviewUrl(metaInfo.getScSamplUrl()));
			sourceList.add(source);
		}

		if (StringUtils.isNotEmpty(metaInfo.getSamplUrl())) {
			source = new Source();
			source.setType(DisplayConstants.DP_PREVIEW_HQ);
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getSamplUrl()));
			source.setUrl(this.commonService.makePreviewUrl(metaInfo.getSamplUrl()));
			sourceList.add(source);
		}

		return sourceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateDownloadSourceList(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Source> generateDownloadSourceList(MetaInfo metaInfo) {
		Source source = null;
		List<Source> sourceList = new ArrayList<Source>();

		source = this.generateSource(metaInfo.getImagePath());
		sourceList.add(source);

		// DLM 이미지
		if (StringUtils.isNotEmpty(metaInfo.getDlmImagePath())) {
			source = new Source();
			source.setType(DisplayConstants.DP_DLM_SOURCE);
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getDlmImagePath()));
			source.setUrl(metaInfo.getDlmImagePath());
			sourceList.add(source);
		}

		return sourceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSourceList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Source> generateSourceList(String mediaType, String type, String url) {
		List<Source> sourceList = new ArrayList<Source>();
		Source source = new Source();
		source.setMediaType(mediaType);
		source.setType(type);
		source.setUrl(url);
		sourceList.add(source);
		return sourceList;
	}

	private static final Set<String> SET_EBOOK_COMIC = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
			"DP13", "DP14")));

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

		// 영화,TV방송에 대한 allow 설정
		if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(metaInfo.getTopMenuId())
				|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(metaInfo.getTopMenuId())
				|| DisplayConstants.DP_VOD_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			if (StringUtils.isNotEmpty(metaInfo.getDwldAreaLimtYn())) {
				if ("Y".equals(metaInfo.getDwldAreaLimtYn())) {
					rights.setAllow(DisplayConstants.DP_RIGHTS_ALLOW_DOMESTIC);
				}
			}
		}

		// eBook 상품에 대한 allow 설정
		if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			if (StringUtils.isNotEmpty(metaInfo.getChnlClsfCd())) {
				if (DisplayConstants.DP_SUBSCRIPTION_CD.equals(metaInfo.getChnlClsfCd())) {
					rights.setAllow(DisplayConstants.DP_RIGHTS_ALLOW_SUBSCRIPTION);
				}
			}
		}

		rights.setGrade(metaInfo.getProdGrdCd());
		rights.setPlus19Yn(metaInfo.getPlus19Yn());

		// NOTICE
		// MM 상품 & 에피소드 상품이면 => 기간, 가격
		// rights.play.price
		// rights.store.price
		if (StringUtils.isEmpty(metaInfo.getStoreProdId()) && StringUtils.isEmpty(metaInfo.getPlayProdId())
				&& SET_EBOOK_COMIC.contains(metaInfo.getTopMenuId())
				&& DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(metaInfo.getContentsTypeCd())) {

			if (metaInfo.getUnlmtAmt() != null) {
				Price storePrice = new Price(metaInfo.getUnlmtAmt());
				Store store = new Store(null, storePrice, null);
				store.setUsePeriodUnitCd(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE);
				rights.setStore(store);
			}
			if (metaInfo.getPeriodAmt() != null) {
				Price playPrice = new Price(metaInfo.getPeriodAmt());
				Play play = new Play(null, playPrice, DisplayCommonUtil.makeDateUsagePeriod(
						metaInfo.getUsePeriodUnitCd(), metaInfo.getUsePeriodInt(), metaInfo.getUsePeriodNm()));
				play.setUsePeriodUnitCd(metaInfo.getUsePeriodUnitCd());
				rights.setPlay(play);
			}
		}

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
		return this.generatePrice(metaInfo.getProdAmt(), metaInfo.getProdNetAmt());
	}

	@Override
	public Price generatePrice(Integer text, Integer fixedPrice) {
		Price price = new Price();
		price.setText(text);
		price.setFixedPrice(fixedPrice);
		return price;
	}

	@Override
	public Accrual generateAccrual(MetaInfo metaInfo) {

		Accrual accrual = new Accrual();
		accrual.setVoterCount(metaInfo.getPaticpersCnt() != null ? metaInfo.getPaticpersCnt() : 0);
		accrual.setDownloadCount(metaInfo.getPrchsCnt() != null ? metaInfo.getPrchsCnt() : 0);
		accrual.setScore(metaInfo.getAvgEvluScore() != null ? metaInfo.getAvgEvluScore() : 0);

		return accrual;
	}

	@Override
	public Title generateTitle(MetaInfo metaInfo) {
		Title title = new Title();
		if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			title.setText(metaInfo.getCatalogNm());
		} else {
			title.setPrefix(metaInfo.getPrefixTitle());
			title.setText(metaInfo.getProdNm());
			// 시리즈 상품의 경우
			if (StringUtils.isNotEmpty(metaInfo.getMetaClsfCd())) { // Postfix 세팅
				if (DisplayConstants.DP_SERIAL_VOD_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())
						|| DisplayConstants.DP_SERIAL_VOD_LANGUAGE_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())
						|| DisplayConstants.DP_SERIAL_VOD_SKT_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())) {
					title.setPostfix(metaInfo.getChapter() == null ? null : metaInfo.getChapter()
							+ this.commonService.getVodChapterUnit());

				} else if (DisplayConstants.DP_SERIAL_META_CLASS_CD.equals(metaInfo.getMetaClsfCd()) // 채널상품명 + 회차
						|| DisplayConstants.DP_SERIAL_COMIC_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())
						|| DisplayConstants.DP_MAGAZINE_COMIC_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())
						|| DisplayConstants.DP_WEBTOON_COMIC_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())) {
					title.setPostfix(metaInfo.getChapter() == null ? null : metaInfo.getChapter()
							+ StringUtil.trimToEmpty(this.commonService.getEpubChapterUnit(metaInfo.getBookClsfCd())));
				}
			}
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
		// Cid 설정
		if (StringUtils.isNotEmpty(metaInfo.getCid())) {
			this.log.debug("##### Cid Identifier setting");
			identifier = this.generateIdentifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, metaInfo.getCid());
			identifierList.add(identifier);
		}
		// OutsdContentsId 설정
		if (StringUtils.isNotEmpty(metaInfo.getOutsdContentsId())) {
			this.log.debug("##### Cid Identifier setting");
			identifier = this.generateIdentifier(DisplayConstants.DP_OUTSDCONTENTS_IDENTIFIER_CD,
					metaInfo.getOutsdContentsId());
			identifierList.add(identifier);
		}
		// oneSeq 설정
		if (StringUtils.isNotEmpty(metaInfo.getOneSeq())) {
			this.log.debug("##### Cid Identifier setting");
			identifier = this.generateIdentifier(DisplayConstants.DP_OUTSDCONTENTS_ONE_CD, metaInfo.getOneSeq());
			identifierList.add(identifier);
		}
		return identifierList;
	}

	@Override
	public Distributor generateDistributor(MetaInfo metaInfo) {
		Distributor distributor = new Distributor();
		distributor.setIdentifier(metaInfo.getExpoSellerId());
		distributor.setName(metaInfo.getExpoSellerNm());
		distributor.setTel(metaInfo.getExpoSellerTelNo());
		distributor.setEmail(metaInfo.getExpoSellerEmail());
		distributor.setSellerKey(metaInfo.getSellerMbrNo());
		distributor.setCompany(metaInfo.getCompany());
		return distributor;
	}

	@Override
	public Date generateDate(String type, String text) {
		Date date = new Date();
		date.setType(type);
		date.setTextFromDate(DateUtils.parseDate(text));
		return date;
	}

	@Override
	public Date generateDateString(String type, String text) {
		Date date = new Date();
		date.setType(type);
		date.setText(text);
		return date;
	}

	@Override
	public Date generateDateUsagePeriod(String tp, String period, String unit) {
		if (tp == null || tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) {
			return null;
		}

		String unitName;

		if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MINUTE)) {
			unitName = "minute";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_HOUR)) {
			unitName = "hour";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_DAY)) {
			unitName = "day";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MONTH)) {
			unitName = "month";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_YEAR)) {
			unitName = "year";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_DAY)) {
			unitName = "limit/day";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_MONTH)) {
			unitName = "limit/month";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_YEAR)) {
			unitName = "limit/year";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_CALENDAR)) {
			unitName = "calendar";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) {
			return new Date(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, "unlimit");
		} else
			return null;

		return new Date(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, unitName + "/" + period, unit);
	}

	@Override
	public Store generateStore(MetaInfo metaInfo) {
		Store store = new Store();

		ArrayList<Support> supportList = new ArrayList<Support>();
		supportList.add(this.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM, metaInfo.getStoreDrmYn()));
		supportList.add(this.generateSupport(DisplayConstants.DP_DL_STRM_NM, metaInfo.getStoreDlStrmCd()));
		store.setSupportList(supportList);

		// 이용기간단위
		if (StringUtils.isNotEmpty(metaInfo.getStoreUsePeriodUnitCd())) {
			store.setUsePeriodUnitCd(metaInfo.getStoreUsePeriodUnitCd());
		}

		metaInfo.setProdAmt(metaInfo.getStoreProdAmt());
		metaInfo.setProdNetAmt(metaInfo.getStoreProdNetAmt());
		store.setPrice(this.generatePrice(metaInfo));

		// 코믹일 경우 SQL 에서 PLAY_PROD_ID="play"로 리턴한다.
		if (!DisplayConstants.DP_RIGHTS_STORE.equals(metaInfo.getStoreProdId())) {
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
					metaInfo.getStoreProdId()));
			store.setIdentifierList(identifierList);
		}

		return store;
	}

	@Override
	public Play generatePlay(MetaInfo metaInfo) {

		Play play = new Play();

		ArrayList<Support> supportList = new ArrayList<Support>();
		supportList.add(this.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM, metaInfo.getPlayDrmYn()));
		supportList.add(this.generateSupport(DisplayConstants.DP_DL_STRM_NM, metaInfo.getPlayDlStrmCd()));
		play.setSupportList(supportList);

		// 이용기간단위
		if (StringUtils.isNotEmpty(metaInfo.getPlayUsePeriodUnitCd())) {
			play.setUsePeriodUnitCd(metaInfo.getPlayUsePeriodUnitCd());
		}

		metaInfo.setProdAmt(metaInfo.getPlayProdAmt());
		metaInfo.setProdNetAmt(metaInfo.getPlayProdNetAmt());
		play.setPrice(this.generatePrice(metaInfo));

		Date date = new Date();
		date.setType(DisplayConstants.DP_DATE_USAGE_PERIOD);
		date.setText(metaInfo.getUsePeriodNm());
		play.setDate(date);

		// 코믹일 경우 SQL 에서 PLAY_PROD_ID="play"로 리턴한다.
		if (!DisplayConstants.DP_RIGHTS_PLAY.equals(metaInfo.getPlayProdId())) {
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
					metaInfo.getPlayProdId()));
			play.setIdentifierList(identifierList);
		}

		return play;
	}

	@Override
	public Purchase generatePurchase(MetaInfo metaInfo) {
		return this.generatePurchase(metaInfo.getPurchaseId(), metaInfo.getPurchaseProdId(),
				metaInfo.getPurchaseState(), metaInfo.getPurchaseDt(), metaInfo.getPurchaseDwldExprDt());
	}

	@Override
	public Purchase generatePurchase(String prchId, String prodId, String prchState, String prchDt, String dwldExprDt) {
		Purchase purchase = new Purchase();

		// 구매상태 만료 여부 확인
		if (DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchState)) {
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(this.generateIdentifier(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD, prchId));

			purchase.setIdentifierList(identifierList);
		} else {
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(this.generateIdentifier(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD, prchId));
			identifierList.add(this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, prodId));
			purchase.setIdentifierList(identifierList);

			List<Date> dateList = new ArrayList<Date>();
			;

			if (StringUtils.isNotEmpty(prchDt)) {
				dateList.add(this.generateDate(DisplayConstants.DP_DATE_PURCHASE, prchDt));
			}
			if (StringUtils.isNotEmpty(dwldExprDt)) {
				dateList.add(this.generateDate(DisplayConstants.DP_DATE_DOWNLOAD_EXPIRED_NM, dwldExprDt));
			}

			if (!dateList.isEmpty()) {
				purchase.setDateList(dateList);
			}
		}

		purchase.setState(prchState);
		return purchase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSourceList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Source generatePreviewSourceList(MetaInfo metaInfo) {
		Source source = this.generateSource(DisplayConstants.DP_SOURCE_TYPE_PREVIEW, metaInfo.getPreviewImagePath());
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateUrl(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Url generateUrl(MetaInfo metaInfo) {
		Url url = new Url();

		if (StringUtils.isNotEmpty(metaInfo.getTinyUrl())) {
			url.setType(DisplayConstants.DP_EXTERNAL_CLIENT);
			url = this.generateUrl(metaInfo.getTinyUrl());
		} else {
			url.setType(DisplayConstants.DP_EXTERNAL_PORTAL);
			url = this.generateUrl(metaInfo.getWebUrl());
		}
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateUrl(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Url generateUrl(String type, String text) {
		Url url = new Url();

		if (StringUtils.isNotEmpty(text)) {
			url.setText(text);
		}

		if (StringUtils.isNotEmpty(type)) {
			url.setType(type);
		}

		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSource(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public Url generateUrl(String text) {
		Url url = null;

		if (StringUtils.isNotEmpty(text)) {
			url = new Url();
			url.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			url.setText(text);
		}
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSourceList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Url> generateUrlList(MetaInfo metaInfo) {
		List<Url> urlList = new ArrayList<Url>();
		Url url = this.generateUrl(metaInfo);
		urlList.add(url);
		return urlList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateSourceList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Url> generateUrlList(String type, String text) {
		List<Url> urlList = new ArrayList<Url>();
		Url url = new Url();
		url.setType(type);
		url.setText(text);
		urlList.add(url);
		return urlList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generatePoint(com.skplanet.storeplatform
	 * .sac.display.common.vo.TmembershipDcInfo)
	 */
	@Override
	public List<Point> generatePoint(TmembershipDcInfo metaInfo) {
		List<Point> pointList = null;

		if (metaInfo.getNormalDcRate() != null) {
			pointList = new ArrayList<Point>();
			Point point = new Point();
			point.setName(DisplayConstants.DC_RATE_TMEMBERSHIP);
			point.setType(DisplayConstants.DC_RATE_TYPE_NORMAL);
			point.setDiscountRate(metaInfo.getNormalDcRate());
			pointList.add(point);
		}
		if (metaInfo.getFreepassDcRate() != null) {
			if (pointList == null)
				pointList = new ArrayList<Point>();
			Point point = new Point();
			point.setName(DisplayConstants.DC_RATE_TMEMBERSHIP);
			point.setType(DisplayConstants.DC_RATE_TYPE_FREEPASS);
			point.setDiscountRate(metaInfo.getFreepassDcRate());
			pointList.add(point);
		}

		return pointList;
	}

	@Override
	public List<Point> generateMileage(MileageInfo mileageInfo) {

		if (mileageInfo == null)
			return new ArrayList<Point>();

		List<Point> res = new ArrayList<Point>();
		if (mileageInfo.getRateLv1() != null) {
			res.add(new Point(DisplayConstants.POINT_NM_MILEAGE, DisplayConstants.POINT_TP_MILEAGE_LV1, mileageInfo
					.getRateLv1(), null));
		}
		if (mileageInfo.getRateLv2() != null) {
			res.add(new Point(DisplayConstants.POINT_NM_MILEAGE, DisplayConstants.POINT_TP_MILEAGE_LV2, mileageInfo
					.getRateLv2(), null));
		}
		if (mileageInfo.getRateLv3() != null) {
			res.add(new Point(DisplayConstants.POINT_NM_MILEAGE, DisplayConstants.POINT_TP_MILEAGE_LV3, mileageInfo
					.getRateLv3(), null));
		}

		return res;
	}

	/**
	 * 회원 등급별 마일리지 객체 생성
	 * 
	 * @param mileageInfo
	 * @param userGrade
	 * @return
	 */
	@Override
	public List<Point> generateMileage(MileageInfo mileageInfo, String userGrade) {

		if (mileageInfo == null)
			return new ArrayList<Point>();

		List<Point> res = new ArrayList<Point>();
		if (StringUtil.equals(userGrade, DisplayConstants.POINT_TP_MILEAGE_LV1)) {
			res.add(new Point(DisplayConstants.POINT_NM_MILEAGE, DisplayConstants.POINT_TP_MILEAGE_LV1, mileageInfo
					.getRateLv1(), null));
		} else if (StringUtil.equals(userGrade, DisplayConstants.POINT_TP_MILEAGE_LV2)) {
			res.add(new Point(DisplayConstants.POINT_NM_MILEAGE, DisplayConstants.POINT_TP_MILEAGE_LV2, mileageInfo
					.getRateLv2(), null));
		} else if (StringUtil.equals(userGrade, DisplayConstants.POINT_TP_MILEAGE_LV3)) {
			res.add(new Point(DisplayConstants.POINT_NM_MILEAGE, DisplayConstants.POINT_TP_MILEAGE_LV3, mileageInfo
					.getRateLv3(), null));
		}

		return res;
	}

	@Override
	public Badge generateBadge(MetaInfo metaInfo) {
		if (metaInfo.getBadgeCd() == null && metaInfo.getBadgeOptText() == null)
			return null;
		Badge badge = new Badge();
		badge.setCode(metaInfo.getBadgeCd());
		badge.setText(metaInfo.getBadgeOptText());
		return badge;
	}
}
