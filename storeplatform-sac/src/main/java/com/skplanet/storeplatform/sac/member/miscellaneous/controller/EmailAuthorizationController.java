package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.EmailAuthorizationService;
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
 * 이메일 인증 기능 관련 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 Email인증 관련 기능 클래스 분리
 */
@Controller
@RequestMapping(value = "/member/miscellaneous")
public class EmailAuthorizationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailAuthorizationController.class);

	@Autowired
	private EmailAuthorizationService service;

	/**
	 * <pre>
	 * 이메일 인증 코드 생성.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetEmailAuthorizationCodeReq
	 * @return GetEmailAuthorizationCodeRes
	 */
	@RequestMapping(value = "/getEmailAuthorizationCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(SacRequestHeader requestHeader,
			@Validated @RequestBody GetEmailAuthorizationCodeReq request) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		GetEmailAuthorizationCodeRes response = this.service.getEmailAuthorizationCode(requestHeader, request);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;

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

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		ConfirmEmailAuthorizationCodeRes response = this.service.confirmEmailAuthorizationCode(request);
		LOGGER.info("Response : {}", response.getUserKey());
		return response;
	}

	/**
	 * <pre>
	 * 2.3.19. 이메일 인증 URL 생성.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            GetEmailAuthorizationUrlSacReq
	 * @return GetEmailAuthorizationUrlSacRes
	 */
	@RequestMapping(value = "/getEmailAuthorizationUrl/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetEmailAuthorizationUrlSacRes getEmailAuthorizationUrl(SacRequestHeader header,
			@RequestBody @Validated GetEmailAuthorizationUrlSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		GetEmailAuthorizationUrlSacRes res = this.service.getEmailAuthorizationUrl(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 2.3.19. 이메일 인증 URL 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ConfirmEmailAuthorizationUrlSacReq
	 * @return ConfirmEmailAuthorizationUrlSacRes
	 */
	@RequestMapping(value = "/confirmEmailAuthorizationUrl/v1", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmEmailAuthorizationUrlSacRes confirmEmailAuthorizationUrl(SacRequestHeader header,
			@RequestBody @Validated ConfirmEmailAuthorizationUrlSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		ConfirmEmailAuthorizationUrlSacRes res = this.service.confirmEmailAuthorizationUrl(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

}
