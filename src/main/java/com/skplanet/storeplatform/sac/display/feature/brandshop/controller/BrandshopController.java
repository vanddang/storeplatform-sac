package com.skplanet.storeplatform.sac.display.feature.brandshop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.brandshop.service.BrandshopService;

/**
 * 
 * Calss 설명(브렌드 샵 관련)
 * 
 * Updated on : 2014. 2. 25. Updated by : 이승훈, 엔텔스.
 */
@Controller
public class BrandshopController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrandshopController.class);

	@Autowired
	private BrandshopService BrandshopService;

	/**
	 * <pre>
	 * 브렌드샵 테마 조회 Input Value Object.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return ThemeThemeZoneSacRes
	 */
	@RequestMapping(value = "/display/feature/brandshop/v1", method = RequestMethod.GET)
	@ResponseBody
	public BrandshopSacRes searchBrandshop(BrandshopSacReq req, SacRequestHeader header) {
		return this.BrandshopService.searchBrandshop(req, header);

	}

	/**
	 * <pre>
	 * 브렌드샵 테마 상품 조회 Input Value Object.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return ThemeThemeZoneSacRes
	 */
	@RequestMapping(value = "/display/feature/brandshop/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BrandshopSacRes searchBrandshopList(BrandshopSacReq req, SacRequestHeader header) {
		return this.BrandshopService.searchBrandshopList(req, header);

	}
}
