package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.CaptchaService;
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
 * Captcha Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Miscellaneous 클래스에서 Captcha 관련 기능 클래스 분리
 */
@Controller
@RequestMapping(value = "/member/miscellaneous")
public class CaptchaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaController.class);

	@Autowired
	private CaptchaService service;

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

		LOGGER.info("Request : {}");
		GetCaptchaRes response = this.service.getCaptcha();
		LOGGER.info("Response : imageSign : {}", response.getImageSign());
		return response;
	}

	/**
	 * <pre>
	 *  Captcha 문자 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmCaptchaReq
	 * @return ConfirmCaptchaRes - 인증 성공:null, 인증 실패:Exception
	 */
	@RequestMapping(value = "/confirmCaptcha/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmCaptchaRes confirmCaptcha(@Validated @RequestBody ConfirmCaptchaReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		ConfirmCaptchaRes response = this.service.confirmCaptcha(request);
		LOGGER.info("Response : SUCC.");
		return response;
	}
}
