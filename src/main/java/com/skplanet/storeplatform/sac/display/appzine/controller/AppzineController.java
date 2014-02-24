package com.skplanet.storeplatform.sac.display.appzine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineVolListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineVolListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.appzine.service.AppzineAppListService;
import com.skplanet.storeplatform.sac.display.appzine.service.AppzineDetailService;
import com.skplanet.storeplatform.sac.display.appzine.service.AppzineVolListService;

/**
 * 
 * Calss 설명(Appzine)
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
@Controller
@RequestMapping("/display/appzine")
public class AppzineController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppzineVolListService appzineVolListService;

	@Autowired
	private AppzineDetailService appzineDetailService;

	@Autowired
	private AppzineAppListService appzineAppListService;

	/**
	 * <pre>
	 * Appzine 회차별 목록 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppzineVolListSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppzineVolListSacRes
	 */
	@RequestMapping(value = "/vol/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public AppzineVolListSacRes searchAappzineVolList(AppzineVolListSacReq requestVO, SacRequestHeader requestHeader) {

		this.logger.debug("AppzineVolListService.searchAappzineVolList start !!");

		return this.appzineVolListService.searchAppzineVolList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * Appzine 상세정보 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppzineDetailSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppzineDetailSacRes
	 */
	@RequestMapping(value = "/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public AppzineDetailSacRes searchAappzineDetail(AppzineDetailSacReq requestVO, SacRequestHeader requestHeader) {

		this.logger.debug("AppzineDetailService.searchAappzineDetail start !!");

		return this.appzineDetailService.searchAppzineDetail(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * Appzine 앱 목록 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppzineAppListSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppzineAppListSacRes
	 */
	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public AppzineAppListSacRes searchAappzineAppList(AppzineAppListSacReq requestVO, SacRequestHeader requestHeader) {

		this.logger.debug("AppzineAppistService.searchAappzineAppList start !!");

		return this.appzineAppListService.searchAppzineAppList(requestVO, requestHeader);
	}

}
