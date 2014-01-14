package com.skplanet.storeplatform.sac.member.miscellaneous.dummy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetPhoneAuthorizationCodeRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
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
	@RequestMapping(value = "/getPhoneAuthorizationCode/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/confirmPhoneAuthorizationCode/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/getEmailAuthorizationCode/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/confirmEmailAuthorizationCode/v1", method = RequestMethod.GET)
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
	@RequestMapping(value = "/getIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetIndividualPolicyRes getIndividualPolicy() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.8. 사용자별 정책 조회     #################");
		LOGGER.info("####################################################");

		GetIndividualPolicyRes response = new GetIndividualPolicyRes();

		response.setPolicyCode("restrictMdn");
		response.setKey("01088902431");
		response.setValue("Y");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.9. 사용자별 정책 등록/수정.
	 * </pre>
	 * 
	 * @return CreateIndividualPolicyRes
	 */
	@RequestMapping(value = "/createIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateIndividualPolicyRes createIndividualPolicy() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.9. 사용자별 정책 등록/수정#################");
		LOGGER.info("####################################################");

		CreateIndividualPolicyRes response = new CreateIndividualPolicyRes();
		response.setPolicyCode("restrictMdn");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.10. 사용자별 정책 삭제.
	 * </pre>
	 * 
	 * @return RemoveIndividualPolicyRes
	 */
	@RequestMapping(value = "/removeIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveIndividualPolicyRes removeIndividualPolicy() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.10. 사용자별 정책 삭제    #################");
		LOGGER.info("####################################################");

		RemoveIndividualPolicyRes response = new RemoveIndividualPolicyRes();
		response.setPolicyCode("restrictEmail");

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
	@RequestMapping(value = "/getUaCode/v1", method = RequestMethod.POST)
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
	@RequestMapping(value = "/getOpmd/v1", method = RequestMethod.POST)
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
	 * @return
	 */
	/**
	 * <pre>
	 * 5.3.16. Captcha 문자 발급.
	 * </pre>
	 * 
	 * @return GetCaptchaRes
	 */
	@RequestMapping(value = "/getCaptcha/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetCaptchaRes getCaptcha() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.16. Captcha 문자 발급     ################");
		LOGGER.info("####################################################");

		GetCaptchaRes response = new GetCaptchaRes();
		response.setImageData(""); // captcha 문자 이미지를 String 형태로 반환
		response.setImageSign("N1w0EfuCzwfCNjxPdvSHYjUjTtszi47I7rkpbeV0");

		return response;
	}

	/**
	 * <pre>
	 * 5.3.16. Captcha 문자 확인.
	 * </pre>
	 * 
	 * @return ConfirmCaptchaRes
	 */
	@RequestMapping(value = "/confirmCaptcha/v1", method = RequestMethod.GET)
	@ResponseBody
	public ConfirmCaptchaRes confirmCaptcha() {

		LOGGER.info("####################################################");
		LOGGER.info("####### 5.3.16. Captcha 문자 확인     ################");
		LOGGER.info("####################################################");

		ConfirmCaptchaRes response = new ConfirmCaptchaRes();
		return response;
	}
}
