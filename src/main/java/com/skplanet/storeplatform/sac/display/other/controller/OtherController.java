/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTagReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTagRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.other.service.OtherArtistService;
import com.skplanet.storeplatform.sac.display.other.service.OtherServiceGroupService;
import com.skplanet.storeplatform.sac.display.other.service.OtherTagService;

/**
 * 기타 카테고리 Controller
 * 
 * Updated on : 2014. 1. 28. Updated by : 이승훈, 엔텔스.
 */
@Controller
@RequestMapping("/display/other")
public class OtherController {
	private transient Logger logger = LoggerFactory.getLogger(OtherController.class);

	@Autowired
	private OtherServiceGroupService otherServiceGroupService;

	@Autowired
	private OtherTagService otherTagService;

	@Autowired
	private OtherArtistService otherArtistService;

	/**
	 * <pre>
	 * 기타 카테고리 상품서비스군 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return OtherServiceGroupSacRes
	 */
	@RequestMapping(value = "/serviceGroup/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public OtherServiceGroupSacRes searchServiceGroupList(OtherServiceGroupSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchServiceGroupList Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.otherServiceGroupService.searchServiceGroupList(req, header);
	}

	/**
	 * <pre>
	 * Tag 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	@RequestMapping(value = "/tagList/v1", method = RequestMethod.GET)
	@ResponseBody
	public OtherTagRes searchTagList(@Validated OtherTagReq req, SacRequestHeader header) {
		return this.otherTagService.searchTagList(req, header);
	}

	/**
	 * <pre>
	 * 아티스트별 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	@RequestMapping(value = "/artist/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public OtherArtistRes searchArtistDetail(@Validated OtherArtistReq req, SacRequestHeader header) {
		return this.otherArtistService.searchArtistDetail(req, header);
	}

}
