package com.skplanet.storeplatform.sac.display.feature.theme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.theme.BrandShopThemeReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.BrandShopThemeRes;
import com.skplanet.storeplatform.sac.client.display.vo.theme.EbookComicThemeReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.EbookComicThemeRes;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeEpubSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeEpubSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeZoneReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeZoneRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.theme.service.BrandShopThemeService;
import com.skplanet.storeplatform.sac.display.feature.theme.service.EbookComicThemeService;
import com.skplanet.storeplatform.sac.display.feature.theme.service.ThemeEpubService;
import com.skplanet.storeplatform.sac.display.feature.theme.service.ThemeThemeZoneService;
import com.skplanet.storeplatform.sac.display.feature.theme.service.ThemeZoneService;

/**
 * 
 * Calss 설명(BEST 앱, BEST 컨텐츠, BEST 다운로드 관련)
 * 
 * Updated on : 2014. 1. 3. Updated by : 이석희, 인크로스
 */
@Controller
public class ThemeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThemeController.class);

	@Autowired
	private EbookComicThemeService ebookComicThemeService;

	@Autowired
	private ThemeZoneService themeZoneService;

	@Autowired
	private BrandShopThemeService brandShopThemeService;

	@Autowired
	private ThemeEpubService themeEpubService;

	@Autowired
	private ThemeThemeZoneService themeThemeZoneService;

	/**
	 * 
	 * <pre>
	 * Ebook/Comic 테마 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ebookComicThemeReq
	 *            ebookComicThemeReq
	 * @return EbookComicThemeRes
	 */
	@RequestMapping(value = "/display/feature/theme/ebookComicThemeList/v1", method = RequestMethod.GET)
	@ResponseBody
	public EbookComicThemeRes ebookComicThemeList(EbookComicThemeReq ebookComicThemeReq) {
		return this.ebookComicThemeService.searchEbookComicThemeList(ebookComicThemeReq);
	}

	/**
	 * 
	 * <pre>
	 * 테마존 테마 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param themeZoneReq
	 *            themeZoneReq
	 * @return ThemeZoneRes
	 */
	@RequestMapping(value = "/display/feature/theme/ThemeZoneList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ThemeZoneRes themeZoneList(ThemeZoneReq themeZoneReq) {
		return this.themeZoneService.searchThemeZoneList(themeZoneReq);
	}

	/**
	 * 
	 * <pre>
	 * 브랜드샵 테마 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param brandShopThemeReq
	 *            brandShopThemeReq
	 * @return BrandShopThemeRes
	 */
	@RequestMapping(value = "/display/feature/theme/brandShopThemeList/v1", method = RequestMethod.GET)
	@ResponseBody
	public BrandShopThemeRes brandShopThemeList(BrandShopThemeReq brandShopThemeReq) {
		return this.brandShopThemeService.searchBrandShopThemeList(brandShopThemeReq);
	}

	/**
	 * <pre>
	 * ebook/코믹 테마상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return ThemeEpubSacRes
	 */
	@RequestMapping(value = "/display/feature/theme/epub/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public ThemeEpubSacRes searchThemeEpubList(ThemeEpubSacReq req, SacRequestHeader header) {
		return this.themeEpubService.searchThemeEpubList(req, header);

	}

	/**
	 * <pre>
	 * 테마존 테마 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return ThemeThemeZoneSacRes
	 */
	@RequestMapping(value = "/display/feature/theme/themeZone/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public ThemeThemeZoneSacRes searchThemeThemeZoneList(@Validated ThemeThemeZoneSacReq req, SacRequestHeader header) {
		return this.themeThemeZoneService.searchThemeThemeZoneList(req, header);

	}

	/**
	 * 
	 * <pre>
	 * 테마존 테마 리스트 조회.
	 * </pre>
	 * 
	 * @param themeZoneReq
	 *            themeZoneReq
	 * @return ThemeZoneRes
	 */
	// @RequestMapping(value = "/display/feature/theme/themeZone/v1", method = RequestMethod.GET)
	// @ResponseBody
	// public ThemeThemeZoneSacRes searchThemeThemeZone(ThemeThemeZoneSacReq req, SacRequestHeader header) {
	// return this.themeThemeZoneService.searchThemeThemeZone(req, header);
	// }
}
