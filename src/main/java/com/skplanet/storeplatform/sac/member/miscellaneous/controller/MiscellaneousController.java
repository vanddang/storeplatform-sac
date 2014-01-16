package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;

/**
 * 
 * 기타 기능 관련 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Controller
@RequestMapping(value = "/dev/member/miscellaneous")
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
	public GetUaCodeRes getUaCode(GetUaCodeReq request) throws Exception {

		LOGGER.debug("#################  UA 코드 정보 조회 - START ######################");
		if (request.getDeviceModelNo() == null && request.getMsisdn() == null) {
			throw new Exception("필수요청 파라메터 부족.");
		}

		GetUaCodeRes response = new GetUaCodeRes();
		response = this.service.getUaCode(request);

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
	public GetOpmdRes getOpmd(GetOpmdReq request) throws Exception {
		LOGGER.debug("#################  OPMD 모번호 조회 - START ######################");
		if (request.getMsisdn() == null) {
			throw new Exception("필수요청 파라메터 부족.");
		}

		GetOpmdRes response = new GetOpmdRes();
		response = this.service.getOpmd(request);
		LOGGER.debug("#################  OPMD 모번호 조회 -  END #######################");

		return response;
	}

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송. ( 구현중 2014-01-15 )
	 * </pre>
	 * 
	 * @param request
	 * @return GetPhoneAuthorizationCodeRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPhoneAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetPhoneAuthorizationCodeRes getPhoneAutorizationCode(GetPhoneAuthorizationCodeReq request) throws Exception {

		LOGGER.debug("################## 휴대폰 인증 SMS 발송 - START #######################");
		if (request.getUserPhone() == null || request.getUserTelecom() == null || request.getSrcId() == null
				|| request.getTeleSvcId() == null) {
			throw new Exception("필수 요청 파라메터 부족.");
		}
		GetPhoneAuthorizationCodeRes response = new GetPhoneAuthorizationCodeRes();
		response = this.service.getPhoneAuthorizationCode(request);

		LOGGER.debug("################## 휴대폰 인증 SMS 발송 - END #########################");

		return response;

	}

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인. ( 구현중 2014-01-16 )
	 * </pre>
	 * 
	 * @param request
	 * @return ConfirmPhoneAuthorizationCodeRes
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmPhoneAutorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAutorizationCode(ConfirmPhoneAuthorizationCodeReq request)
			throws Exception {

		LOGGER.debug("################## 휴대폰 인증 코드 확인 - START #######################");
		if (request.getUserPhone() == null || request.getPhoneAuthCode() == null || request.getPhoneSign() == null
				|| request.getTimeToLive() == null) {
			throw new Exception("필수 요청 파라메터 부족.");
		}

		String userPhone = this.service.confirmPhoneAutorizationCode(request);

		ConfirmPhoneAuthorizationCodeRes response = new ConfirmPhoneAuthorizationCodeRes();
		response.setUserPhone(userPhone);
		LOGGER.debug("################## 휴대폰 인증 코드 확인 - END #########################");

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
	@RequestMapping(value = "/getCaptcha/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetCaptchaRes getCaptcha() throws Exception {

		GetCaptchaRes res = new GetCaptchaRes();
		LOGGER.debug("######## Captcha 문자 발급 Controller 시작 ##############");
		res = this.service.getCaptcha();
		LOGGER.debug("### >> GetCaptchaRes {} ", res);
		// res.setImageData(imageData);
		// res.setImageSign(imageSign);
		LOGGER.debug("######## Captcha 문자 발급 Controller 종료 ##############");
		return res;
	}
}
