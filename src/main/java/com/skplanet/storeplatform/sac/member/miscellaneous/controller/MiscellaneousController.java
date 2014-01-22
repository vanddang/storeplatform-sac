package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.RemoveIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.RemoveIndividualPolicyRes;
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
	@RequestMapping(value = "/confirmPhoneAuthorizationCode/v1", method = RequestMethod.POST)
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

		GetCaptchaRes res = this.service.getCaptcha();
		return res;
	}

	/**
	 * <pre>
	 * Captcha 문자 인증.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmCaptchaReq
	 * @return ConfirmCaptchaRes - 인증 성공:null, 인증 실패:Exception
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/dev/confirmCaptcha/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmCaptchaRes confirmCaptcha(@RequestBody ConfirmCaptchaReq request) throws Exception {
		ConfirmCaptchaRes res = this.service.confirmCaptcha(request);
		return res;
	}

	/**
	 * <pre>
	 * 이메일 인증 코드 생성.
	 * </pre>
	 * 
	 * @param request
	 *            GetEmailAuthorizationCodeReq
	 * @return GetEmailAuthorizationCodeRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/dev/getEmailAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(
			@Valid @RequestBody GetEmailAuthorizationCodeReq request) throws Exception {
		GetEmailAuthorizationCodeRes res = this.service.getEmailAuthorizationCode(request);
		return res;

	}

	/**
	 * <pre>
	 * 5.3.8. 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return GetIndividualPolicyRes
	 */
	@RequestMapping(value = "/getIndividualPolicy/v1", method = RequestMethod.POST)
	public @ResponseBody
	GetIndividualPolicyRes getIndividualPolicy(SacRequestHeader header,
			@RequestBody @Validated GetIndividualPolicyReq req) {
		return this.service.getIndividualPolicy(header, req);
	}

	/**
	 * <pre>
	 * 5.3.9. 사용자별 정책 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return CreateIndividualPolicyRes
	 */
	@RequestMapping(value = "/createIndividualPolicy/v1", method = RequestMethod.POST)
	public @ResponseBody
	CreateIndividualPolicyRes createIndividualPolicy(SacRequestHeader header,
			@RequestBody @Validated CreateIndividualPolicyReq req) {
		return this.service.createIndividualPolicy(header, req);
	}

	/**
	 * <pre>
	 * 5.3.10. 사용자별 정책 삭제.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return RemoveIndividualPolicyRes
	 */
	@RequestMapping(value = "/removeIndividualPolicy/v1", method = RequestMethod.POST)
	public @ResponseBody
	RemoveIndividualPolicyRes removeIndividualPolicy(SacRequestHeader header,
			@RequestBody @Validated RemoveIndividualPolicyReq req) {
		return this.service.removeIndividualPolicy(header, req);
	}
}
