package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
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
	private static final Logger logger = LoggerFactory.getLogger(MiscellaneousController.class);

	@Autowired
	private MiscellaneousService service;

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUaCode/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetUaCodeRes getUaCode(GetUaCodeReq request) throws Exception {

		logger.debug("#################  UA 코드 정보 조회 - START ######################");
		GetUaCodeRes response = new GetUaCodeRes();
		response = this.service.getUaCode(request);

		logger.debug("#################  UA 코드 정보 조회 -  END #######################");
		return response;
	}

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * </pre>
	 * 
	 * @param msisdn
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOpmd/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetOpmdRes getOpmd(GetOpmdReq req) throws Exception {
		logger.debug("#################  OPMD 모번호 조회 - START ######################");
		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(this.service.getOpmd(req).getMsisdn());
		logger.debug("#################  OPMD 모번호 조회 -  END #######################");

		return res;
	}

	/**
	 * <pre>
	 * Captcha 문자 발급.
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCaptcha/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetCaptchaRes getCaptcha() throws Exception {

		GetCaptchaRes res = new GetCaptchaRes();
		logger.debug("######## Captcha 문자 발급 Controller 시작 ##############");
		res = this.service.getCaptcha();
		logger.debug("### >> GetCaptchaRes {} ", res);
		// res.setImageData(imageData);
		// res.setImageSign(imageSign);
		logger.debug("######## Captcha 문자 발급 Controller 종료 ##############");
		return res;
	}
}
