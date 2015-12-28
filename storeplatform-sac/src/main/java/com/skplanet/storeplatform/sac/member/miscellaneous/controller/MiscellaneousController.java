package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;
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
 * 
 * 기타 기능 관련 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Controller
@RequestMapping(value = "/member/miscellaneous")
public class MiscellaneousController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousController.class);

	@Autowired
	private MiscellaneousService service;

	/**
	 * <pre>
	 * 부가서비스 가입.
	 * </pre>
	 * 
	 * @param request
	 *            CreateAdditionalServiceReq
	 * @return CreateAdditionalServiceRes
	 */
	@RequestMapping(value = "/createAdditionalService/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateAdditionalServiceRes createAdditionalService(@Validated @RequestBody CreateAdditionalServiceReq request) {
		LOGGER.info("Request : {}", ConvertMapperUtils.defaultConvertObjectToJson(request));

		if (!ValidationCheckUtils.isMdn(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}
		CreateAdditionalServiceRes response = this.service.regAdditionalService(request);
		LOGGER.info("Response : msisdn : {}", response.getMsisdn());
		return response;
	}

	/**
	 * <pre>
	 * 부가서비스 가입 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetAdditionalServiceReq
	 * @return GetAdditionalServiceRes
	 */
	@RequestMapping(value = "/getAdditionalService/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetAdditionalServiceRes getAdditionalService(@Validated @RequestBody GetAdditionalServiceReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.defaultConvertObjectToJson(request));

		if (!ValidationCheckUtils.isMdn(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}
		GetAdditionalServiceRes response = this.service.getAdditionalService(request);
		LOGGER.info("Response : join result : {}", response.getSvcJoinResult());
		return response;
	}

	/**
	 * <pre>
	 * 결제 계좌 정보 인증.
	 * </pre>
	 * 
	 * @param request
	 *            AuthorizeAccountReq
	 * @return AuthorizeAccountRes
	 */
	@RequestMapping(value = "/authorizeAccount/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeAccountRes authorizeAccount(@RequestBody @Validated AuthorizeAccountReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		AuthorizeAccountRes response = this.service.authorizeAccount(request);

		LOGGER.info("Response : SUCC.");
		return response;
	}


}
