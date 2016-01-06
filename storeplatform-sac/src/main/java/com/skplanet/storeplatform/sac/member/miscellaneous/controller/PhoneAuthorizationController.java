package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.PhoneAuthorizationService;
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
 * 휴대폰 인증 기능 관련 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 Captcha 관련 기능 클래스 분리
 */
@Controller
@RequestMapping(value = "/member/miscellaneous")
public class PhoneAuthorizationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PhoneAuthorizationController.class);

	@Autowired
	private PhoneAuthorizationService service;


	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetPhoneAuthorizationCodeReq
	 * @return GetPhoneAuthorizationCodeRes
	 */
	@RequestMapping(value = "/getPhoneAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode(SacRequestHeader requestHeader,
			@Validated @RequestBody GetPhoneAuthorizationCodeReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		if (!ValidationCheckUtils.isMdn(request.getRecvMdn())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}
		GetPhoneAuthorizationCodeRes response = this.service.getPhoneAuthorizationCode(requestHeader, request);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;

	}

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            ConfirmPhoneAuthorizationCodeReq
	 * @return ConfirmPhoneAuthorizationCodeRes
	 */
	@RequestMapping(value = "/confirmPhoneAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAuthorizationCode(SacRequestHeader requestHeader,
			@Validated @RequestBody ConfirmPhoneAuthorizationCodeReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		if (!ValidationCheckUtils.isMdn(request.getUserPhone())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}
		ConfirmPhoneAuthorizationCodeRes response = this.service.confirmPhoneAutorizationCode(requestHeader, request);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;

	}

	/**
	 * <pre>
	 * 2.3.17.휴대폰 인증 여부 확인.
	 * </pre>
	 *
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            ConfirmPhoneAuthorizationCheckReq
	 * @return ConfirmPhoneAuthorizationCheckRes
	 */
	@RequestMapping(value = "/confirmPhoneAuthorizationCheck/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmPhoneAuthorizationCheckRes confirmPhoneAuthorizationCheck(SacRequestHeader header,
			@RequestBody @Validated ConfirmPhoneAuthorizationCheckReq request) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		if (!ValidationCheckUtils.isMdn(request.getUserPhone())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}

		ConfirmPhoneAuthorizationCheckRes response = this.service.confirmPhoneAutorizationCheck(header, request);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}

}
