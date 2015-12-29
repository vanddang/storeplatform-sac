package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountRes;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.UserAccountService;
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
 * 결제 계좌 기능 관련 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 결제 계좌 관련 기능 클래스 분리
 */
@Controller
@RequestMapping(value = "/member/miscellaneous")
public class UserAccountController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountController.class);

	@Autowired
	private UserAccountService userAccountService;

	/**
	 * <pre>
	 * 결제 계좌 정보 인증.
	 * </pre>
	 * 
	 * @param request
	 *            AuthorizeAccountReq
	 * @return AuthorizeAccountRes
	 */
	@RequestMapping(value = "/authorizeAccount/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeAccountRes authorizeAccount(@RequestBody @Validated AuthorizeAccountReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		AuthorizeAccountRes response = this.userAccountService.authorizeAccount(request);

		LOGGER.info("Response : SUCC.");
		return response;
	}


}
