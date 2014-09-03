package com.skplanet.storeplatform.sac.display.feature.theme.controller;

import com.skplanet.storeplatform.sac.client.display.vo.theme.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.theme.service.EbookComicThemeService;
import com.skplanet.storeplatform.sac.display.feature.theme.service.ThemeEpubService;
import com.skplanet.storeplatform.sac.display.feature.theme.service.ThemeThemeZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public ThemeThemeZoneListSacRes searchThemeThemeZoneList(@Validated ThemeThemeZoneListSacReq req,
			SacRequestHeader header) {
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
	@RequestMapping(value = "/display/feature/theme/themeZone/v1", method = RequestMethod.GET)
	@ResponseBody
	public ThemeThemeZoneSacRes searchThemeThemeZone(ThemeThemeZoneSacReq req, SacRequestHeader header) {
		return this.themeThemeZoneService.searchThemeThemeZone(req, header);
	}
}
