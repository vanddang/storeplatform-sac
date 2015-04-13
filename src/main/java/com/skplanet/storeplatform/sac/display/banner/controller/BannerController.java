package com.skplanet.storeplatform.sac.display.banner.controller;

import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerProdMapgSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.banner.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 배너 리스트 조회
 * 
 * Updated on : 2014. 02. 21. Updated by : 이태희.
 */
@Controller
@RequestMapping("/display/banner")
public class BannerController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BannerService bannerService;

	/**
	 * <pre>
	 * 배너 리스트 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param bannerReq
	 *            bannerReq
	 * @return BannerSacRes
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BannerSacRes searchBannerList(SacRequestHeader header, @Validated BannerSacReq bannerReq) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[searchBannerList] SacRequestHeader\n{}", header.toString());
		this.logger.debug("[searchBannerList] BannerSacReq\n{}", bannerReq.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.bannerService.searchBannerList(header, bannerReq, null);
	}

    @RequestMapping(value = "/prodmapg/v1", method = RequestMethod.GET)
    @ResponseBody
    public BannerSacRes searchBannerProdMapgList(SacRequestHeader header, @Validated BannerProdMapgSacReq bannerProdMapgReq) {

        return this.bannerService.searchBannerProdMapgList(header, bannerProdMapgReq);
    }
}
