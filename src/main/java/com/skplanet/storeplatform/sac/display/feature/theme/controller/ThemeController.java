package com.skplanet.storeplatform.sac.display.feature.theme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.theme.BrandShopThemeReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.BrandShopThemeRes;
import com.skplanet.storeplatform.sac.client.display.vo.theme.EbookComicThemeReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.EbookComicThemeRes;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeZoneReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeZoneRes;
import com.skplanet.storeplatform.sac.display.feature.theme.service.BrandShopThemeService;
import com.skplanet.storeplatform.sac.display.feature.theme.service.EbookComicThemeService;
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

	@RequestMapping(value = "/display/feature/theme/ebookComicThemeList/v1", method = RequestMethod.GET)
	@ResponseBody
	public EbookComicThemeRes ebookComicThemeList(EbookComicThemeReq ebookComicThemeReq) {
		return this.ebookComicThemeService.searchEbookComicThemeList(ebookComicThemeReq);
	}

	@RequestMapping(value = "/display/feature/theme/ThemeZoneList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ThemeZoneRes ThemeZoneList(ThemeZoneReq themeZoneReq) {
		return this.themeZoneService.searchThemeZoneList(themeZoneReq);
	}

	@RequestMapping(value = "/display/feature/theme/brandShopThemeList/v1", method = RequestMethod.GET)
	@ResponseBody
	public BrandShopThemeRes brandShopThemeList(BrandShopThemeReq brandShopThemeReq) {
		return this.brandShopThemeService.searchBrandShopThemeList(brandShopThemeReq);
	}

}
