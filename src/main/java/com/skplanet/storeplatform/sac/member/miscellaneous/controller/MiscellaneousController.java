package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceRes;
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
	 */
	@RequestMapping(value = "/getUaCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, @Validated @RequestBody GetUaCodeReq request) {

		// 필수 파라미터 확인. 둘 중 하나는 필수로 입력해야함.
		if (request == null || request.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "msisdn or deviceModel");
		}

		if (request.getMsisdn() != null) {
			Pattern pattern = Pattern.compile("[0-9]{10,11}");
			Matcher matcher = pattern.matcher(request.getMsisdn());
			boolean isMdn = matcher.matches();
			if (!isMdn) {
				throw new StorePlatformException("SAC_MEM_3004");
			}
		}

		GetUaCodeRes response = this.service.getUaCode(requestHeader, request);

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
	 */
	@RequestMapping(value = "/getOpmd/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetOpmdRes getOpmd(SacRequestHeader requestHeader, @Validated @RequestBody GetOpmdReq request) {

		Pattern pattern = Pattern.compile("[0-9]{10,11}");
		Matcher matcher = pattern.matcher(request.getMsisdn());
		boolean isMdn = matcher.matches();
		if (!isMdn) {
			throw new StorePlatformException("SAC_MEM_3004");
		}

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
	 */
	@RequestMapping(value = "/getPhoneAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetPhoneAuthorizationCodeRes getPhoneAutorizationCode(SacRequestHeader requestHeader,
			@Validated @RequestBody GetPhoneAuthorizationCodeReq request) {

		Pattern pattern = Pattern.compile("[0-9]{10,11}");
		Matcher matcher = pattern.matcher(request.getRecvMdn());
		boolean isMdn = matcher.matches();
		if (!isMdn) {
			throw new StorePlatformException("SAC_MEM_3004");
		}

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
	 */
	@RequestMapping(value = "/confirmPhoneAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(SacRequestHeader requestHeader,
			@Validated @RequestBody ConfirmPhoneAuthorizationCodeReq request) {

		ConfirmPhoneAuthorizationCodeRes response = this.service.confirmPhoneAutorizationCode(request);

		return response;

	}

	/**
	 * <pre>
	 * Captcha 문자 발급.
	 * </pre>
	 * 
	 * @return GetCaptchaRes
	 */
	@RequestMapping(value = "/getCaptcha/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetCaptchaRes getCaptcha() {

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
	 */
	@RequestMapping(value = "/confirmCaptcha/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmCaptchaRes confirmCaptcha(@Validated @RequestBody ConfirmCaptchaReq request) {
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
	 */
	@RequestMapping(value = "/getEmailAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(SacRequestHeader requestHeader,
			@Validated @RequestBody GetEmailAuthorizationCodeReq request) {
		GetEmailAuthorizationCodeRes res = this.service.getEmailAuthorizationCode(requestHeader, request);
		return res;

	}

	/**
	 * <pre>
	 * 이메일 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmEmailAuthorizationCodeReq
	 * @return ConfirmEmailAuthorizationCodeRes
	 */
	@RequestMapping(value = "/confirmEmailAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmEmailAuthorizationCodeRes confirmEmailAuthorizationCode(
			@Validated @RequestBody ConfirmEmailAuthorizationCodeReq request) {
		ConfirmEmailAuthorizationCodeRes res = this.service.confirmEmailAuthorizationCode(request);
		return res;
	}

	/**
	 * <pre>
	 * 부가서비스 가입.
	 * </pre>
	 * 
	 * @param request
	 *            CreateAdditionalServiceReq
	 * @return CreateAdditionalServiceRes
	 */
	@RequestMapping(value = "/dev/createAdditionalService/v1")
	@ResponseBody
	public CreateAdditionalServiceRes createAdditionalService(@Validated @RequestBody CreateAdditionalServiceReq request) {

		CreateAdditionalServiceRes response = this.service.createAdditionalService(request);
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
	@RequestMapping(value = "/dev/getAdditionalService/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetAdditionalServiceRes getAdditionalService(@Validated @RequestBody GetAdditionalServiceReq request) {
		GetAdditionalServiceRes response = this.service.getAdditionalService(request);

		return response;
	}

	/**
	 * <pre>
	 * 2.3.8. 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return GetIndividualPolicyRes
	 */
	@RequestMapping(value = "/getIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetIndividualPolicyRes getIndividualPolicy(SacRequestHeader header,
			@RequestBody @Validated GetIndividualPolicyReq req) {
		return this.service.getIndividualPolicy(header, req);
	}

	/**
	 * <pre>
	 * 2.3.9. 사용자별 정책 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return CreateIndividualPolicyRes
	 */
	@RequestMapping(value = "/createIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateIndividualPolicyRes createIndividualPolicy(SacRequestHeader header,
			@RequestBody @Validated CreateIndividualPolicyReq req) {
		return this.service.createIndividualPolicy(header, req);
	}

	/**
	 * <pre>
	 * 2.3.10. 사용자별 정책 삭제.
	 * </pre>
	 * 
	 * @param header
	 * @param req
	 * @return RemoveIndividualPolicyRes
	 */
	@RequestMapping(value = "/removeIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveIndividualPolicyRes removeIndividualPolicy(SacRequestHeader header,
			@RequestBody @Validated RemoveIndividualPolicyReq req) {
		return this.service.removeIndividualPolicy(header, req);
	}
}
