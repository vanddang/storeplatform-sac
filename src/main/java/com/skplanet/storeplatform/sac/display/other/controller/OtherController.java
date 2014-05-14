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

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherAIDListReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherAIDListRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPackageListReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPackageListRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTMembershipReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTMembershipRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTMembershipUseStatusRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTagReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTagRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.other.service.OtherAIDListService;
import com.skplanet.storeplatform.sac.display.other.service.OtherArtistService;
import com.skplanet.storeplatform.sac.display.other.service.OtherPackageListService;
import com.skplanet.storeplatform.sac.display.other.service.OtherServiceGroupService;
import com.skplanet.storeplatform.sac.display.other.service.OtherTMembershipService;
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
	private OtherTMembershipService otherTMembershipService;

	@Autowired
	private OtherServiceGroupService otherServiceGroupService;

	@Autowired
	private OtherTagService otherTagService;

	@Autowired
	private OtherArtistService otherArtistService;

	@Autowired
	private OtherPackageListService otherPackageListService;

	@Autowired
	private OtherAIDListService otherAIDListService;

	/**
	 * <pre>
	 * T Membership 할인율 조회
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return OtherTMembershipRes
	 */
	@RequestMapping(value = "/tmembership/get/v1", method = RequestMethod.GET)
	@ResponseBody
	public OtherTMembershipRes searchTMembership(@Validated OtherTMembershipReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchTMembership Controller started!!");
		this.logger.debug("Input Parameters {}", req.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.otherTMembershipService.searchTMembership(req, header);
	}

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
	public OtherServiceGroupSacRes searchServiceGroupList(@Validated OtherServiceGroupSacReq req,
			SacRequestHeader header) {

		if (this.logger.isDebugEnabled()) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("searchServiceGroupList Controller started!!");
			this.logger.debug("Input Parameters {}", req.toString());
			this.logger.debug("----------------------------------------------------------------");
		}
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

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return OtherPakcageListRes
	 */
	@RequestMapping(value = "/package/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public OtherPackageListRes searchProductListByPackageNm(@Validated OtherPackageListReq req, SacRequestHeader header) {
		List<String> packageInfoList = Arrays.asList(StringUtils.split(req.getPackageInfo(), "+"));
		if (packageInfoList.size() > DisplayConstants.DP_UPDATE_PARAM_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "packageInfo", DisplayConstants.DP_UPDATE_PARAM_LIMIT);
		}
		return this.otherPackageListService.searchProductListByPackageNm(req, header, packageInfoList);
	}

	/**
	 * <pre>
	 * 상품 ID 조회(by AID)
	 * </pre>
	 * 
	 * @param OtherAIDListReq
	 *            req
	 * @param SacRequestHeader
	 *            header
	 * @return OtherAIDListRes
	 */
	@RequestMapping(value = "/aid/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public OtherAIDListRes searchProductListByAID(@Validated OtherAIDListReq req, SacRequestHeader header) {
		List<String> aIdList = Arrays.asList(StringUtils.split(req.getAidList(), "+"));
		if (aIdList.size() > DisplayConstants.DP_UPDATE_PARAM_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "list", DisplayConstants.DP_UPDATE_PARAM_LIMIT);
		}
		return this.otherAIDListService.searchProductListByAID(req, header, aIdList);
	}

	/**
	 * <pre>
	 * T맴버십 할인 사용여부
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @return OtherTMembershipUseStatusRes
	 */
	@RequestMapping(value = "/tmemebership/useStatus/get/v1", method = RequestMethod.GET)
	@ResponseBody
	public OtherTMembershipUseStatusRes searchTMembershipUseStatus(SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchTMembershipUseStatus Controller started!!");
		this.logger.debug("----------------------------------------------------------------");

		return this.otherTMembershipService.searchTMembershipUseStatus(header);
	}

}
