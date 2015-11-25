/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.UserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.CreateGiftChargeInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.CreateGiftChargeInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.sci.service.UserSCIService;

/**
 * 회원 내부메소드 호출 Controller.
 * 
 * Updated on : 2015. 9. 22. Updated by : 반범진.
 */
@LocalSCI
@RequestMapping(value = "/member/user/sci")
public class UserSCIController implements UserSCI {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSCIController.class);

	@Autowired
	private UserSCIService userSCIService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.user.sci.UserSCI#removeSSOCredential(com.skplanet.storeplatform
	 * .sac.client.internal.member.user.vo.RemoveSSOCredentialSacReq)
	 */
	@Override
	@RequestMapping(value = "/removeSSOCredential", method = RequestMethod.POST)
	@ResponseBody
	public RemoveSSOCredentialSacRes removeSSOCredential(@RequestBody @Validated RemoveSSOCredentialSacReq request) {
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		RemoveSSOCredentialSacRes response = this.userSCIService.removeSSOCredential(requestHeader, request);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.client.internal.member.user.sci.UserSCI#createGiftChargeInfo(com.skplanet.
	 * storeplatform.sac.client.internal.member.user.vo.CreateGiftChargeInfoSacReq)
	 */
	@Override
	@RequestMapping(value = "/createGiftChargeInfo", method = RequestMethod.POST)
	@ResponseBody
	public CreateGiftChargeInfoSacRes createGiftChargeInfo(@RequestBody @Validated CreateGiftChargeInfoSacReq request) {
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		CreateGiftChargeInfoSacRes response = this.userSCIService.createGiftChargeInfo(requestHeader, request);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}

}
