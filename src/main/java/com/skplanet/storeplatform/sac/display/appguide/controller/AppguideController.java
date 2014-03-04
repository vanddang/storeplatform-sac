package com.skplanet.storeplatform.sac.display.appguide.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideApprankingSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideApprankingSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.appguide.service.AppguideApprankingService;
import com.skplanet.storeplatform.sac.display.appguide.service.AppguideIsfService;
import com.skplanet.storeplatform.sac.display.appguide.service.AppguideVersionService;

/**
 * 
 * Class 설명(App Guide 컨트롤러)
 * 
 * Updated on : 2014. 02. 21. Updated by : 윤주영, SK 플래닛.
 */
@Controller
@RequestMapping("/display/appguide")
public class AppguideController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppguideIsfService appguideIsfService;

	@Autowired
	private AppguideVersionService appguideVersionService;

	@Autowired
	private AppguideApprankingService appguideApprankingService;

	/**
	 * <pre>
	 * 개인화 추천(앱가이드).
	 * </pre>
	 * 
	 * @param AppguideSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppguideSacRes
	 */
	@RequestMapping(value = "/isf/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public AppguideSacRes searchIsfRecommendList(@RequestBody AppguideSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException {

		this.logger.debug(this.getClass().getName() + ".searchIsfRecommendList start !!");
		this.logger.debug("request {}", requestVO);
		if (StringUtils.equals("dummy", requestVO.getFilteredBy()))
			return this.appguideIsfService.searchDummyIsfRecommendList(requestVO, requestHeader);
		else
			return this.appguideIsfService.searchIsfRecommendList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 버전정보 조회.
	 * </pre>
	 * 
	 * @param AppguideSacReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return AppguideSacRes
	 */
	@RequestMapping(value = "/version/get/v1", method = RequestMethod.POST)
	@ResponseBody
	public AppguideSacRes searchVersion(@RequestBody AppguideSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException {

		this.logger.debug(this.getClass().getName() + ".searchVersion start !!");
		this.logger.debug("request {}", requestVO);
		return this.appguideVersionService.searchVersion(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * 앱랭킹  상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return AppguideApprankingRes
	 */
	@RequestMapping(value = "/appranking/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public AppguideApprankingSacRes searchVodList(@Validated AppguideApprankingSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchApprankingList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("Input Parameters {}", header.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.appguideApprankingService.searchApprankingList(req, header);
	}

}
