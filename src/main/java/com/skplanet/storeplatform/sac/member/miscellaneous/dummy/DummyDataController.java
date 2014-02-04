package com.skplanet.storeplatform.sac.member.miscellaneous.dummy;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmRealNameAuthorizationRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetEmailAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetModelCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetModelCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.IndividualPolicyInfo;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.RemoveIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ResendSmsForRealNameAuthorizationRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.SendSmsForRealNameAuthorizationRes;

/**
 * 
 * 기타 서비스 Dummy Controller
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@Controller
@RequestMapping(value = "/member/miscellaneous")
public class DummyDataController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DummyDataController.class);

	/**
	 * <pre>
	 * 5.3.1. 실명 인증용 휴대폰 인증 SMS 발송.
	 * </pre>
	 * 
	 * @return SendSmsForRealNameAuthorizationRes
	 */
	@RequestMapping(value = "/sendSmsForRealNameAuthorization/v1", method = RequestMethod.POST)
	@ResponseBody
	public SendSmsForRealNameAuthorizationRes sendSmsForRealNameAuthorization() {
		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.1. 실명 인증용 휴대폰 인증 SMS 발송 ########");
		LOGGER.info("####################################################");

		SendSmsForRealNameAuthorizationRes response = new SendSmsForRealNameAuthorizationRes();
		response.setKmcSmsAuth("IW102158844420091030165015");
		response.setCheckParam1("");
		response.setCheckParam2("");
		response.setCheckParam3("");
		return response;
	}

	/**
	 * <pre>
	 * 5.3.2. 실명 인증용 휴대폰 인증 코드 확인.
	 * </pre>
	 * 
	 * @param ConfirmRealNameAuthorizationReq
	 * @return ConfirmRealNameAuthorizationRes
	 */
	@RequestMapping(value = "/confirmRealNameAuthorization/v1", method = RequestMethod.GET)
	@ResponseBody
	public ConfirmRealNameAuthorizationRes confirmRealNameAuthorization() {
		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.2. 실명 인증용 휴대폰 인증 코드 확인 ########");
		LOGGER.info("####################################################");

		ConfirmRealNameAuthorizationRes response = new ConfirmRealNameAuthorizationRes();
		response.setUserCI("skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1B9A3z5zrVHettHzKa5dpJA==");
		response.setUserDI("skpone000012902JAyEAPgMkfp3WL1 caEThzSSWjsOXQCfONIbAJgSFvitpjcQ=");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.3. 실명 인증용 휴대폰 인증 SMS 재발송.
	 * </pre>
	 * 
	 * @param ResendSmsForRealNameAuthorizationReq
	 * @return ResendSmsForRealNameAuthorizationRes
	 */
	@RequestMapping(value = "/resendSmsForRealNameAuthorization/v1", method = RequestMethod.GET)
	@ResponseBody
	public ResendSmsForRealNameAuthorizationRes resendSmsForRealNameAuthorization() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.3. 실명 인증용 휴대폰 인증 SMS 재발송  ######");
		LOGGER.info("####################################################");

		ResendSmsForRealNameAuthorizationRes response = new ResendSmsForRealNameAuthorizationRes();
		response.setCheckParam1("");
		response.setCheckParam2("");
		response.setCheckParam3("");
		return response;
	}

	/**
	 * <pre>
	 * 5.3.4. 휴대폰 인증 SMS 발송.
	 * </pre>
	 * 
	 * @param GetPhoneAuthorizationCodeReq
	 * @return GetPhoneAuthorizationCodeRes
	 */
	// @RequestMapping(value = "/getPhoneAuthorizationCode/v1", method = RequestMethod.POST) //주석 2014-01-17
	@ResponseBody
	public GetPhoneAuthorizationCodeRes getPhoneAuthorizationCode() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.4. 휴대폰 인증 SMS 발송   #################");
		LOGGER.info("####################################################");

		GetPhoneAuthorizationCodeRes response = new GetPhoneAuthorizationCodeRes();
		response.setPhoneSign("N1w0EfuCzwfCNjxPdvSHYjUjTtszi47I7rkpbeV0");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.5. 휴대폰 인증 코드 확인.
	 * </pre>
	 * 
	 * @return ConfirmPhoneAuthorizationCodeRes
	 */
	// @RequestMapping(value = "/confirmPhoneAuthorizationCode/v1", method = RequestMethod.POST) //주석 2014-01-17
	@ResponseBody
	public ConfirmPhoneAuthorizationCodeRes confirmPhoneAuthorizationCode() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.5. 휴대폰 인증 코드 확인  #################");
		LOGGER.info("####################################################");

		ConfirmPhoneAuthorizationCodeRes response = new ConfirmPhoneAuthorizationCodeRes();
		response.setUserPhone("01012340009");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.6. 이메일 인증 코드 생성.
	 * </pre>
	 * 
	 * @return GetEmailAuthorizationCodeRes
	 */
	// @RequestMapping(value = "/getEmailAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.6. 이메일 인증 코드 생성  #################");
		LOGGER.info("####################################################");

		GetEmailAuthorizationCodeRes response = new GetEmailAuthorizationCodeRes();
		response.setEmailAuthCode("a7852b590b880930036fdf6fe26e9bec5ee18c3d3438be0ad530aa5e4914251b");
		return response;
	}

	/**
	 * <pre>
	 * 5.3.7. 이메일 인증 코드 확인.
	 * </pre>
	 * 
	 * @return ConfirmEmailAuthorizationCodeRes
	 */
	// @RequestMapping(value = "/confirmEmailAuthorizationCode/v1", method = RequestMethod.GET)
	@ResponseBody
	public ConfirmEmailAuthorizationCodeRes confirmEmailAuthorizationCode() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.7. 이메일 인증 코드 확인  #################");
		LOGGER.info("####################################################");

		ConfirmEmailAuthorizationCodeRes response = new ConfirmEmailAuthorizationCodeRes();
		response.setUserKey("IW102158844420091030165015");
		response.setUserEmail("tstore2013@nate.com");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.8. 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @return GetIndividualPolicyRes
	 */
	// @RequestMapping(value = "/getIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetIndividualPolicyRes getIndividualPolicy() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.8. 사용자별 정책 조회     #################");
		LOGGER.info("####################################################");

		GetIndividualPolicyRes response = new GetIndividualPolicyRes();
		List<IndividualPolicyInfo> policyInfos = new ArrayList<IndividualPolicyInfo>();
		IndividualPolicyInfo policyInfo = null;
		for (int i = 0; i < 3; i++) {
			policyInfo = new IndividualPolicyInfo();
			policyInfo.setKey("010231" + i);
			policyInfo.setPolicyCode("restrictMdn_0" + i);
			policyInfo.setValue("test_0" + i);
			policyInfos.add(policyInfo);
		}
		response.setPolicyList(policyInfos);
		return response;
	}

	/**
	 * <pre>
	 * 5.3.9. 사용자별 정책 등록/수정.
	 * </pre>
	 * 
	 * @return CreateIndividualPolicyRes
	 */
	// @RequestMapping(value = "/createIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateIndividualPolicyRes createIndividualPolicy() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.9. 사용자별 정책 등록/수정#################");
		LOGGER.info("####################################################");

		CreateIndividualPolicyRes response = new CreateIndividualPolicyRes();
		response.setPolicyCode("restrictMdn");
		response.setKey("a@a.com");
		response.setValue("test");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.10. 사용자별 정책 삭제.
	 * </pre>
	 * 
	 * @return RemoveIndividualPolicyRes
	 */
	// @RequestMapping(value = "/removeIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveIndividualPolicyRes removeIndividualPolicy() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.10. 사용자별 정책 삭제    #################");
		LOGGER.info("####################################################");

		RemoveIndividualPolicyRes response = new RemoveIndividualPolicyRes();
		response.setPolicyCode("restrictEmail");
		response.setKey("010123");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.11. UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param GetUaCodeReq
	 * @param GetUaCodeReq
	 * @return GetUaCodeRes
	 */
	// @RequestMapping(value = "/getUaCode/v1", method = RequestMethod.POST)//주석 2014-01-17
	@ResponseBody
	public GetUaCodeRes getUaCode() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.11. UA 코드 정보 조회     #################");
		LOGGER.info("####################################################");

		GetUaCodeRes response = new GetUaCodeRes();
		response.setUaCd("SSI8");
		return response;
	}

	/**
	 * <pre>
	 * 5.3.12. OPMD 모회선 번호 조회.
	 * </pre>
	 * 
	 * @param GetOpmdReq
	 * @return GetOpmdRes
	 */
	// @RequestMapping(value = "/getOpmd/v1", method = RequestMethod.POST) //주석 2014-01-17
	@ResponseBody
	public GetOpmdRes getOpmd() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.12. OPMD 모회선 번호 조회 ################");
		LOGGER.info("####################################################");

		GetOpmdRes response = new GetOpmdRes();
		response.setMsisdn("01023451102");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.13. 부가서비스 가입.
	 * </pre>
	 * 
	 * @return CreateAdditionalServiceRes
	 */
	@RequestMapping(value = "/createAdditionalService/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateAdditionalServiceRes createAdditionalService() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.13. 부가서비스 가입        ################");
		LOGGER.info("####################################################");

		CreateAdditionalServiceRes response = new CreateAdditionalServiceRes();
		response.setDeviceId("0101231234");
		response.setSvcCode("NA00004184");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.14. 부가서비스 가입 조회.
	 * </pre>
	 * 
	 * @return GetAdditionalServiceRes
	 */
	@RequestMapping(value = "/getAdditionalService/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetAdditionalServiceRes getAdditionalService() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.14. 부가서비스 가입 조회   ################");
		LOGGER.info("####################################################");

		GetAdditionalServiceRes response = new GetAdditionalServiceRes();
		response.setDeviceId("0101231234");
		response.setSvcJoinResult("NA00004184=T");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.15. 결제 계좌 정보 인증.
	 * </pre>
	 * 
	 * @return AuthorizeAccountRes
	 */
	@RequestMapping(value = "/authorizeAccount/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeAccountRes authorizeAccount() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.15. 결제 계좌 정보 인증    ################");
		LOGGER.info("####################################################");

		AuthorizeAccountRes response = new AuthorizeAccountRes();

		return response;
	}

	/**
	 * <pre>
	 * 5.3.16. Captcha 문자 발급.
	 * </pre>
	 * 
	 * @return GetCaptchaRes
	 */
	// @RequestMapping(value = "/getCaptcha/v1", method = RequestMethod.GET) //주석 2014-01-21
	@ResponseBody
	public GetCaptchaRes getCaptcha() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.16. Captcha 문자 발급     ################");
		LOGGER.info("####################################################");

		GetCaptchaRes response = new GetCaptchaRes();
		response.setImageData("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");
		response.setImageSign("e940982c743208d282bec5f705669d705b73c489");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.16. Captcha 문자 확인.
	 * </pre>
	 * 
	 * @return ConfirmCaptchaRes
	 */
	// @RequestMapping(value = "/confirmCaptcha/v1", method = RequestMethod.GET) //주석 2014-01-22
	@ResponseBody
	public ConfirmCaptchaRes confirmCaptcha() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.17. Captcha 문자 확인     ################");
		LOGGER.info("####################################################");

		ConfirmCaptchaRes response = new ConfirmCaptchaRes();
		return response;
	}

	/**
	 * <pre>
	 * 단말 모델코드 조회.
	 * </pre>
	 * 
	 * @param req
	 *            GetModelCodeReq
	 * @return GetModelCodeRes
	 */
	@RequestMapping(value = "/getModelCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetModelCodeRes getModelCode(@Validated @RequestBody GetModelCodeReq req) {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.18. 단말 모델코드 조회     ################");
		LOGGER.info("####################################################");
		LOGGER.info("[SAC] Request : {}", req);
		GetModelCodeRes response = new GetModelCodeRes();
		response.setDeviceModelNo("LG-SH810");
		return response;
	}

}
