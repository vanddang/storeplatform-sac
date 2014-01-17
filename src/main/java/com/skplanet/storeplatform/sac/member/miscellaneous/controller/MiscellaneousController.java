package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;

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
	 * UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param request
	 * @return GetUaCodeRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUaCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, @RequestBody GetUaCodeReq request) throws Exception {

		LOGGER.debug("#################  UA 코드 정보 조회 - START ######################");

		GetUaCodeRes response = this.service.getUaCode(requestHeader, request);

		LOGGER.debug("#################  UA 코드 정보 조회 -  END #######################");
		return response;
	}

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * </pre>
	 * 
	 * @param msisdn
	 * @return GetOpmdRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOpmd/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetOpmdRes getOpmd(SacRequestHeader requestHeader, @Valid @RequestBody GetOpmdReq request,
			BindingResult errors) throws Exception {
		GetOpmdRes response = this.service.getOpmd(request);

		return response;
	}

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송.
	 * </pre>
	 * 
	 * @param request
	 * @return GetPhoneAuthorizationCodeRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/dev/getPhoneAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetPhoneAuthorizationCodeRes getPhoneAutorizationCode(SacRequestHeader requestHeader,
			@Valid @RequestBody GetPhoneAuthorizationCodeReq request) throws Exception {

		GetPhoneAuthorizationCodeRes response = this.service.getPhoneAuthorizationCode(requestHeader, request);

		return response;

	}

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 * @return ConfirmPhoneAuthorizationCodeRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/dev/confirmPhoneAutorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(SacRequestHeader requestHeader,
			@Valid @RequestBody ConfirmPhoneAuthorizationCodeReq request) throws Exception {

		ConfirmPhoneAuthorizationCodeRes response = this.service.confirmPhoneAutorizationCode(request);

		return response;

	}

	/**
	 * <pre>
	 * Captcha 문자 발급. - 기능 잠시 보류. ( 휴대폰 인증 부터 개발 예정 2014-01-14)
	 * </pre>
	 * 
	 * @return GetCaptchaRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/dev/getCaptcha/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetCaptchaRes getCaptcha() throws Exception {

		LOGGER.debug("######## Captcha 문자 발급 Controller 시작 ##############");

		GetCaptchaRes res = this.service.getCaptcha();
		LOGGER.debug("######## Captcha 문자 발급 Controller 종료 ##############");
		return res;
	}
}
