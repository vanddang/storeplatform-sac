/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningRes;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommandSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommandSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProductProvisioningService;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProfileService;
import com.skplanet.storeplatform.sac.display.openapi.service.DownloadBestService;
import com.skplanet.storeplatform.sac.display.openapi.service.NewAppRecommandService;

/**
 * Open API 관련 Controller
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Controller
@RequestMapping("/display/openapi")
public class OpenApiController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DeviceProfileService deviceProfileService;

	@Autowired
	private DeviceProductProvisioningService deviceProductProvisioningService;

	@Autowired
	private DownloadBestService downloadBestService;

	@Autowired
	private NewAppRecommandService newAppRecommandService;

	/**
	 * <pre>
	 * App 목록 요청 - TBD.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProfileRes
	 */
	@RequestMapping(value = "/sellerApp/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceProfileRes searchSellerAppList(DeviceProfileReq req, SacRequestHeader header) {
		return this.deviceProfileService.searchDeviceProfile(req, header);
	}

	/**
	 * <pre>
	 * App 상세 정보 요청
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	@RequestMapping(value = "/sellerApp/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public DeviceProductProvisioningRes getSellerAppDetail(DeviceProductProvisioningReq req, SacRequestHeader header) {
		return this.deviceProductProvisioningService.searchProductProvisioning(req, header);
	}

	/**
	 * 
	 * <pre>
	 * Download Best 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadBestSacReq
	 *            downloadBestSacReq
	 * @return DownloadBestSacRes
	 */
	@RequestMapping(value = "/bestDownload/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadBestSacRes downloadBest(SacRequestHeader requestheader,
			@RequestBody DownloadBestSacReq downloadBestSacReq) {
		return this.downloadBestService.searchDownloadBestList(requestheader, downloadBestSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 신규 앱 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param newAppRecommandSacReq
	 *            newAppRecommandSacReq
	 * @return NewAppRecommandSacRes
	 */
	@RequestMapping(value = "/newAppRecommand/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public NewAppRecommandSacRes newAppRecommand(SacRequestHeader requestheader,
			@RequestBody NewAppRecommandSacReq newAppRecommandSacReq) {
		return this.newAppRecommandService.searchNewAppRecommandList(requestheader, newAppRecommandSacReq);
	}

}
