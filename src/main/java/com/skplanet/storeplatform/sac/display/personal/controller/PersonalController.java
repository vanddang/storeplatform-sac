/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.personal.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.personal.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.personal.service.PersonalAutoUpdateService;
import com.skplanet.storeplatform.sac.display.personal.service.PersonalUpdateProductListService;
import com.skplanet.storeplatform.sac.display.personal.service.PersonalUpdateProductService;
import com.skplanet.storeplatform.sac.display.personal.service.RecommendNewMemberProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;

/**
 * 개인화 관련 Controller
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스
 */
@Controller
@RequestMapping("/display/personal")
public class PersonalController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonalUpdateProductListService personalUpdateProductListService;

	@Autowired
	private PersonalAutoUpdateService personalAutoUpdateService;

	@Autowired
	private RecommendNewMemberProductService recommendNewMemberProductService;

	@InitBinder("personalUpdateProductReq")
	public void personalUpdateProductReqBinder(WebDataBinder dataBinder) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator springValidatorAdapter = new SpringValidatorAdapter(factory.getValidator());
		dataBinder.setValidator(new PersonalUpdateProductReqValidator(springValidatorAdapter));
	}

	/**
	 * <pre>
	 * 업데이트 대상 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return PersonalUpgradeProductRes
	 */
	@RequestMapping(value = "/update/product/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public PersonalUpdateProductRes searchUpdateProductList(
			@Validated @RequestBody PersonalUpdateProductReq req, SacRequestHeader header) {
		List<String> packageInfoList = Arrays.asList(StringUtils.split(req.getPackageInfo(), "+"));
		if (packageInfoList.size() > DisplayConstants.DP_PERSONAL_UPDATE_PARAM_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "packageInfo",
					DisplayConstants.DP_PERSONAL_UPDATE_PARAM_LIMIT);
		}
		this.log.debug("----------------------------------------------------------------");
		this.log.debug("[searchUpdateProductList] SacRequestHeader\n{}", header.toString());
		this.log.debug("[searchUpdateProductList] PersonalUpdateProductReq\n{}", req.toString());
		this.log.debug("----------------------------------------------------------------");
		return this.personalUpdateProductListService.searchUpdateProductList(req, header,
				packageInfoList);
	}

	/**
	 * <pre>
	 * 자동 Update 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	@RequestMapping(value = "/update/autoUpdate/status/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public PersonalAutoUpdateRes updateAutoUpdateList(@Validated @RequestBody PersonalAutoUpdateReq req,
			SacRequestHeader header) {

		List<String> packageInfoList = Arrays.asList(StringUtils.split(req.getPackageInfo(), "+"));
		if (packageInfoList.size() > DisplayConstants.DP_PERSONAL_UPDATE_PARAM_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "packageInfo",
					DisplayConstants.DP_PERSONAL_UPDATE_PARAM_LIMIT);
		}

		Integer limitCnt = req.getUpdLimitCnt();
		if(limitCnt != null && limitCnt < 0)
			throw new StorePlatformException("SAC_DSP_0006");


		this.log.debug("----------------------------------------------------------------");
		this.log.debug("[updateAutoUpdateList] SacRequestHeader\n{}", header.toString());
		this.log.debug("[updateAutoUpdateList] PersonalAutoUpdateReq\n{}", req.toString());
		this.log.debug("----------------------------------------------------------------");

		return this.personalAutoUpdateService.updateAutoUpdateList(req, header, packageInfoList);
	}

	/**
	 * <pre>
	 * [I03000060] 2.8.5. 신규 가입 고객 추천 상품조회
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return RecommendNewMemberProductRes
	 */
	@RequestMapping(value = "/recommendNewMember/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public RecommendNewMemberProductRes recommendNewMemberProductList(SacRequestHeader header,
			@Validated RecommendNewMemberProductReq req) {
		return this.recommendNewMemberProductService.recommendNewMemberProductList(header, req);
	}

}
