/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.vod.controller;


import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.vod.service.VodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * VOD Controller
 *
 * Updated on : 2014-01-09
 * Updated by : 임근대, SK플래닛.
 */
@Controller
@RequestMapping("/display/vod")
public class VodController {
	private static final Logger logger = LoggerFactory.getLogger(VodController.class);

	@Autowired
	private VodService vodService;

    /**
     * [I03000040] VOD 상품 상세 조회
     * @param header SAC 헤더
     * @param req   Parameter
     * @return
     */
	@RequestMapping(value = "/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public VodDetailRes searchVodDetail(SacRequestHeader header, @Validated @RequestBody VodDetailReq req) {
		//logger.debug("header={}, req={}", new String[]{ header.toString(), req.toString() });
        req.setLangCd(header.getTenantHeader().getLangCd());
        req.setTenantId(header.getTenantHeader().getTenantId());
        req.setDeviceModel(header.getDeviceHeader().getModel());

        logger.debug("req={}", req);
		return this.vodService.searchVod(req, false);

	}

    /**
     * [I03000159] VOD 상품 상세 조회 (V2)
     * @param header SAC 헤더
     * @param req   Parameter
     * @return
     */
	@RequestMapping(value = "/detail/v2", method = RequestMethod.POST)
	@ResponseBody
	public VodDetailRes searchVodDetailV2(SacRequestHeader header, @Validated @RequestBody VodDetailReq req) {
		//logger.debug("header={}, req={}", new String[]{ header.toString(), req.toString() });
        req.setLangCd(header.getTenantHeader().getLangCd());
        req.setTenantId(header.getTenantHeader().getTenantId());
        req.setDeviceModel(header.getDeviceHeader().getModel());

        logger.debug("req={}", req);
		return this.vodService.searchVod(req, true);

	}
	
	
    /**
     * [I03000189] 2.6.7 VOD 상품 상세 조회 (V3)
     * @param header SAC 헤더
     * @param req   Parameter
     * @return
     */
	@RequestMapping(value = "/detail/v3", method = RequestMethod.POST)
	@ResponseBody
	public VodDetailRes searchVodDetailV3(SacRequestHeader header, @Validated @RequestBody VodDetailReq req) {
        req.setLangCd(header.getTenantHeader().getLangCd());
        req.setTenantId(header.getTenantHeader().getTenantId());
        req.setDeviceModel(header.getDeviceHeader().getModel());
		return this.vodService.searchVodV3(req, true);
	}	

}
