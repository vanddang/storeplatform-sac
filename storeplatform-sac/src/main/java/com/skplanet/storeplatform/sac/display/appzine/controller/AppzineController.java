package com.skplanet.storeplatform.sac.display.appzine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
 * Appzine 조회 Controller.
 * 
 * Updated on : 2015. 8. 3. Updated by : 이태희.
 */
@Controller
@RequestMapping("/display/appzine")
public class AppzineController {
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
	 *            requestVO
	 * @param requestHeader
	 *            requestHeader
	 * @return AppzineVolListSacRes
	 */
	@RequestMapping(value = "/vol/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public AppzineVolListSacRes searchAappzineVolList(@Validated AppzineVolListSacReq requestVO,
			SacRequestHeader requestHeader) {
		return this.appzineVolListService.searchAppzineVolList(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * Appzine 상세정보 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            requestVO
	 * @param requestHeader
	 *            requestHeader
	 * @return AppzineDetailSacRes
	 */
	@RequestMapping(value = "/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public AppzineDetailSacRes searchAappzineDetail(@Validated AppzineDetailSacReq requestVO,
			SacRequestHeader requestHeader) {
		return this.appzineDetailService.searchAppzineDetail(requestVO, requestHeader);
	}

	/**
	 * <pre>
	 * Appzine 앱 목록 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            requestVO
	 * @param requestHeader
	 *            requestHeader
	 * @return AppzineAppListSacRes
	 */
	@RequestMapping(value = "/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public AppzineAppListSacRes searchAappzineAppList(@Validated AppzineAppListSacReq requestVO,
			SacRequestHeader requestHeader) {
		return this.appzineAppListService.searchAppzineAppList(requestVO, requestHeader);
	}
}
