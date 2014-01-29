package com.skplanet.storeplatform.sac.display.banner.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.BannerExplain;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.banner.vo.Banner;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

//import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner;

@Service
@Transactional
public class BannerServceImpl implements BannerService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	private static final List<Object> listEntry = new ArrayList<Object>();

	// 배너 규격 정의
	static {
		Map<String, String> mapEntry;

		mapEntry = new HashMap<String, String>();
		mapEntry.put("BNR_TYPE", DisplayConstants.DP_BANNER_PRODUCT_CD);
		mapEntry.put("TYPE", "product");
		mapEntry.put("DESC_NAME", "id");
		listEntry.add(mapEntry);

		mapEntry = new HashMap<String, String>();
		mapEntry.put("BNR_TYPE", DisplayConstants.DP_BANNER_URL_NEW_CD);
		mapEntry.put("TYPE", "externalUrl");
		mapEntry.put("DESC_NAME", "url");
		mapEntry.put("EXTERNAL", "external");
		listEntry.add(mapEntry);

		mapEntry = new HashMap<String, String>();
		mapEntry.put("BNR_TYPE", DisplayConstants.DP_BANNER_CATEGORY_CD);
		mapEntry.put("TYPE", "category");
		listEntry.add(mapEntry);

		mapEntry = new HashMap<String, String>();
		mapEntry.put("BNR_TYPE", DisplayConstants.DP_BANNER_ADMIN_RECOMM_CD);
		mapEntry.put("TYPE", "themeZone");
		mapEntry.put("BASE", "/category/themeZone");
		mapEntry.put("DESC_NAME", "id");
		listEntry.add(mapEntry);

		mapEntry = new HashMap<String, String>();
		mapEntry.put("BNR_TYPE", DisplayConstants.DP_BANNER_BRANDSHOP_LIST_CD);
		mapEntry.put("TYPE", "brandShopCategory");
		mapEntry.put("BASE", "/category/brandShop");
		listEntry.add(mapEntry);

		mapEntry = new HashMap<String, String>();
		mapEntry.put("BNR_TYPE", DisplayConstants.DP_BANNER_SPECIFIC_BRANDSHOP_CD);
		mapEntry.put("TYPE", "brandShop");
		mapEntry.put("BASE", "http://www.tstore.co.kr/miscellaneous/category/brandShop");
		mapEntry.put("DESC_NAME", "id");
		listEntry.add(mapEntry);

		mapEntry = new HashMap<String, String>();
		mapEntry.put("BNR_TYPE", DisplayConstants.DP_BANNER_INTERNAL_URL_CD);
		mapEntry.put("TYPE", "url");
		mapEntry.put("DESC_NAME", "url");
		listEntry.add(mapEntry);

		mapEntry = new HashMap<String, String>();
		mapEntry.put("BNR_TYPE", DisplayConstants.DP_BANNER_SITUATIONAL_RECOMM_CD);
		mapEntry.put("TYPE", "themeRecomm");
		mapEntry.put("BASE", "/category/themeRecomm");
		mapEntry.put("DESC_NAME", "id");
		listEntry.add(mapEntry);
	}

	@Override
	public BannerRes searchBannerList(BannerReq bannerReq, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {
		BannerRes bannerRes = new BannerRes();
		CommonResponse commonResponse = new CommonResponse();

		// 헤더 값 설정
		bannerReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
		bannerReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
		bannerReq.setLangCd(requestHeader.getTenantHeader().getLangCd());
		bannerReq.setRsltnSize(requestHeader.getDeviceHeader().getDpi());
		bannerReq.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());

		// Test 값 설정
		// 헤더
		// bannerReq.setTenantId("S01");
		// bannerReq.setSystemId("test01");
		// bannerReq.setDeviceModelCd("SHV-E210S");
		// bannerReq.setRsltnSize("PI000101");
		// 파라미터
		// bannerReq.setBnrGrpId("MBI000000004");
		// bannerReq.setBnrTopCd("DP000504");
		// bannerReq.setProdClsf("ALL");
		// bannerReq.setResvExpoYn("N");
		// bannerReq.setImgCaseCd("A");
		// bannerReq.setOffset(1);
		// bannerReq.setCount(20);

		// 필수 파라미터 체크
		if (bannerReq.getBnrGrpId() == null || bannerReq.getBnrTopCd() == null) {
			bannerRes.setCommonResponse(commonResponse);
			return bannerRes;
		}

		// 파라미터 기본 값 설정
		bannerReq.setProdClsf(bannerReq.getProdClsf() == null ? "ALL" : bannerReq.getProdClsf().toUpperCase());
		bannerReq.setResvExpoYn(bannerReq.getResvExpoYn() == null ? "N" : bannerReq.getResvExpoYn().toUpperCase());
		bannerReq.setOffset(bannerReq.getOffset() <= 0 ? 1 : bannerReq.getOffset());
		bannerReq.setCount(bannerReq.getCount() <= 0 ? 20 : bannerReq.getCount());

		// 객체 선언
		List<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner> bannerList = new ArrayList<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner>();
		com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner banner;
		List<Identifier> identifierList;
		Identifier identifier;
		Title title;
		Title titleName;
		List<Menu> menuList;
		Menu menu;
		List<Source> sourceList;
		Source source;
		Music music;
		Preview preview;
		BannerExplain bannerExplain;

		// 배너 리스트 조회
		this.log.debug("배너 리스트 조회");
		List<Banner> resultList = this.commonDAO.queryForList("Banner.searchBannerList", bannerReq, Banner.class);

		if (resultList != null && resultList.size() > 0) {
			this.log.debug("배너 리스트 - 총 건수 : " + resultList.get(0).getTotalCount());
			commonResponse.setTotalCount(resultList.get(0).getTotalCount());
		}

		Banner resultInfo = null;
		Banner product = null;

		// 배너 값 설정
		Iterator<Banner> resultIterator = resultList.iterator();
		while (resultIterator.hasNext()) {

			banner = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner();
			resultInfo = resultIterator.next();

			// 상품모바일배너의 경우 상품 메타 조회 및 설정
			if (resultInfo.getBnrTypeCd().equals(DisplayConstants.DP_BANNER_PRODUCT_CD)) {
				bannerReq.setProdId(resultInfo.getProdId());
				resultInfo.setBnrInfo(resultInfo.getProdId());// 상품 메타 정보가 없을 경우 대비

				// 상품 메타 정보 조회
				this.log.debug("상품 메타 조회");
				product = this.commonDAO.queryForObject("Banner.searchProductInfo", bannerReq, Banner.class);
				if (product != null) {
					// 상품 메타 값 설정
					resultInfo.setTopMenuId(product.getTopMenuId());
					resultInfo.setMenuId(product.getMenuId());
					resultInfo.setTopMenuNm(product.getTopMenuNm());
					resultInfo.setMenuNm(product.getMenuNm());
					resultInfo.setMetaClsfCd(product.getMetaClsfCd());
					resultInfo.setScSamplUrl(product.getScSamplUrl());
					resultInfo.setSamplUrl(product.getSamplUrl());
					resultInfo.setProdCase(product.getProdCase());
					resultInfo.setOutsdContentsId(product.getOutsdContentsId());
					resultInfo.setFileSize(product.getFileSize());
					resultInfo.setFileSizeH(product.getFileSizeH());
					resultInfo.setBnrInfo(product.getProdId());
				} else {
					this.log.debug("상품 메타 - 데이터 없음");
				}
			}

			// 배너 규격 매핑
			Iterator<Object> entryIterator = listEntry.iterator();
			while (entryIterator.hasNext()) {
				@SuppressWarnings("unchecked")
				Map<String, String> entry = (Map<String, String>) entryIterator.next();
				if (entry.get("BNR_TYPE").equals(resultInfo.getBnrTypeCd())) {
					resultInfo.setType(entry.get("TYPE"));
					resultInfo.setDescName(entry.get("DESC_NAME"));
					resultInfo.setExternal(entry.get("EXTERNAL"));
					resultInfo.setBase(entry.get("BASE"));
				}
			}

			// 샵클 BEST 앱/컨텐츠 일 경우 배너 명 설정
			if (bannerReq.getBnrGrpId().equals(DisplayConstants.DP_BANNER_MOBILE_CLIENT_CD)) {
				resultInfo.setBnrNm(resultInfo.getBnrTitle());
			}

			this.log.debug("Respons 값 설정 - 배너 타입 : " + resultInfo.getBnrTypeCd());

			// sizeType 설정
			banner.setSizeType(resultInfo.getImgCaseCd());

			// type 설정
			banner.setType(resultInfo.getType());

			// base 설정
			banner.setBase(resultInfo.getBase());
			if (resultInfo.getBase() != null) {
				banner.setBase(resultInfo.getBase());
			}

			// Identifier 설정
			identifierList = new ArrayList<Identifier>();
			identifier = new Identifier();
			identifier.setType("banner");
			identifier.setText(resultInfo.getBnrSeq());
			identifierList.add(identifier);

			// Title 설정
			title = new Title();
			title.setText(resultInfo.getBnrNm());

			// TitleName, ThemeInfo 설정(테마일 경우에만 해당)
			// 테마 수정 필요(DB 설계가 안됨)
			titleName = new Title();
			if (bannerReq.getBnrTopCd().equals(DisplayConstants.DP_COMIC_THEME_BANNER_CD)
					|| bannerReq.getBnrTopCd().equals(DisplayConstants.DP_EBOOK_THEME_BANNER_CD)) {
				titleName.setText(resultInfo.getBnrNm());
				banner.setTitleName(titleName);
				banner.setThemeInfo(resultInfo.getBnrInfo());
			}

			// menu 설정
			menuList = new ArrayList<Menu>();
			menu = new Menu();
			menu.setId(resultInfo.getMenuId());
			menu.setName(resultInfo.getMenuNm());
			if (menu.getId() != null || menu.getName() != null) {// 메뉴 값이 없을 경우 메뉴 리스트에 추가 안함
				menuList.add(menu);
			}

			// bannerExplain 설정
			bannerExplain = new BannerExplain();
			if (resultInfo.getDescName().equals("id")) {
				bannerExplain.setId(resultInfo.getBnrInfo());
				banner.setBannerExplain(bannerExplain);
			} else if (resultInfo.getDescName().equals("url")) {
				bannerExplain.setUrl(resultInfo.getBnrInfo());
				banner.setBannerExplain(bannerExplain);
			}

			// Source 설정
			sourceList = new ArrayList<Source>();
			source = new Source();
			source.setUrl(resultInfo.getImgPath() + "" + resultInfo.getImgNm());
			source.setMediaType(DisplayCommonUtil.getMimeType(resultInfo.getImgNm()));
			sourceList.add(source);
			this.log.debug("4");

			// 상품모바일배너 전용(BnrTypeCd : DP010303)
			if (resultInfo.getBnrTypeCd().equals(DisplayConstants.DP_BANNER_PRODUCT_CD)) {

				// Top menu 설정
				menu = new Menu();
				menu.setId(resultInfo.getTopMenuId());
				menu.setName(resultInfo.getTopMenuNm());
				menu.setType("topClass");
				menuList.add(menu);

				// MetaclsfCd 설정
				menu = new Menu();
				menu.setId(resultInfo.getMetaClsfCd());
				menu.setType("metaClass");
				menuList.add(menu);

				// music 상품일 경우 미리듣기 정보
				if (resultInfo.getTopMenuId() != null
						&& resultInfo.getTopMenuId().equals(DisplayConstants.DP_MUSIC_TOP_MENU_ID)) {
					music = new Music();
					identifier = new Identifier();
					identifier.setType("downloadId");
					identifier.setText(resultInfo.getOutsdContentsId());
					music.setIdentifier(identifier);
					this.log.debug("5");

					sourceList = new ArrayList<Source>();
					source = new Source();
					source.setType("audio/mp3-128");
					source.setSize(resultInfo.getFileSize());
					sourceList.add(source);
					source = new Source();
					source.setType("audio/mp3-192");
					source.setSize(resultInfo.getFileSizeH());
					sourceList.add(source);
					music.setSourceList(sourceList);
					banner.setMusic(music);
				}
				// VOD 상품일 경우 미리보기 정보
				if (resultInfo.getTopMenuId() != null
						&& (resultInfo.getTopMenuId().equals(DisplayConstants.DP_MOVIE_TOP_MENU_ID) || resultInfo
								.getTopMenuId().equals(DisplayConstants.DP_TV_TOP_MENU_ID))) {
					preview = new Preview();

					sourceList = new ArrayList<Source>();
					source = new Source();
					source.setType("video/x-freeview-lq");
					source.setUrl(resultInfo.getScSamplUrl());
					sourceList.add(source);
					source = new Source();
					source.setType("video/x-freeview-hq");
					source.setUrl(resultInfo.getSamplUrl());
					sourceList.add(source);
					preview.setSourceList(sourceList);
					banner.setPreview(preview);
				}
			}

			banner.setTitle(title);
			banner.setMenuList(menuList);
			banner.setSourceList(sourceList);
			banner.setIdentifier(identifierList);
			bannerList.add(banner);
		}
		bannerRes.setBannerList(bannerList);
		bannerRes.setCommonResponse(commonResponse);
		return bannerRes;
	}
}
