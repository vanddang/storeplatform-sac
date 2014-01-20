package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeRes;
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
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetUaCodeReq
	 * @return GetUaCodeRes
	 * @throws Exception
	 *             Exception
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
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetOpmdReq
	 * @return GetOpmdRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/getOpmd/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetOpmdRes getOpmd(SacRequestHeader requestHeader, @Valid @RequestBody GetOpmdReq request) throws Exception {
		GetOpmdRes response = this.service.getOpmd(request);

		return response;
	}

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
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/getPhoneAuthorizationCode/v1", method = RequestMethod.POST)
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
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            ConfirmPhoneAuthorizationCodeReq
	 * @return ConfirmPhoneAuthorizationCodeRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/confirmPhoneAutorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(SacRequestHeader requestHeader,
			@Valid @RequestBody ConfirmPhoneAuthorizationCodeReq request) throws Exception {

		ConfirmPhoneAuthorizationCodeRes response = this.service.confirmPhoneAutorizationCode(request);

		return response;

	}

	/**
	 * <pre>
	 * Captcha 문자 발급.
	 * </pre>
	 * 
	 * @return GetCaptchaRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/dev/getCaptcha/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetCaptchaRes getCaptcha() throws Exception {

		LOGGER.debug("######## Captcha 문자 발급 Controller 시작 ##############");

		GetCaptchaRes res = this.service.getCaptcha();
		LOGGER.debug("######## Captcha 문자 발급 Controller 종료 ##############");
		return res;
	}

	/**
	 * <pre>
	 * Captcha 문자 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmCaptchaReq
	 * @return ConfirmCaptchaRes
	 */
	@RequestMapping(value = "/dev/confirmCaptcha/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmCaptchaRes confirmCaptcha(@RequestBody ConfirmCaptchaReq request) {
		return null;
	}

	/**
	 * <pre>
	 * 이메일 인증 코드 생성.
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(
			@Valid @RequestBody GetEmailAuthorizationCodeReq request) {

		return null;

	}

}
