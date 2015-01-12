/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.controller;

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

import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraDetailUserSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraDetailUserSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraListUserKeySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraListUserKeySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraSearchUserInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraSearchUserInfoSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.CIInfraService;

/**
 * CI Infra 컨트롤러
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
@Controller
public class CIInfraController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClauseController.class);

	@Autowired
	private CIInfraService ciInfraService;

	/**
	 * <pre>
	 * 회원키 조회.
	 * </pre>
	 * 
	 * @param req
	 *            CIInfraSearchUserInfoSacReq
	 * @return CIInfraSearchUserInfoSacRes
	 */
	@RequestMapping(value = "/member/ciinfra/searchUserInfo/v1", method = RequestMethod.GET)
	@ResponseBody
	public CIInfraSearchUserInfoSacRes searchUserInfo(SacRequestHeader requestHeader,
			@Validated CIInfraSearchUserInfoSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		CIInfraSearchUserInfoSacRes res = this.ciInfraService.searchUserInfo(requestHeader, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 회원키 리스트 조회.
	 * </pre>
	 * 
	 * @param req
	 *            CIInfraListUserKeySacReq
	 * @return CIInfraListUserKeySacRes
	 */
	@RequestMapping(value = "/member/ciinfra/listUserKey/v1", method = RequestMethod.GET)
	@ResponseBody
	public CIInfraListUserKeySacRes listUserKey(SacRequestHeader requestHeader, @Validated CIInfraListUserKeySacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		CIInfraListUserKeySacRes res = this.ciInfraService.listUserKey(requestHeader, req);
		LOGGER.info("Response : {}", res.getUserInfoList().size());
		return res;
	}

	/**
	 * <pre>
	 * 회원 정보 상세 조회.
	 * </pre>
	 * 
	 * @param req
	 *            CIInfraDetailUserSacReq
	 * @return CIInfraDetailUserSacRes
	 */
	@RequestMapping(value = "/member/ciinfra/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public CIInfraDetailUserSacRes detail(SacRequestHeader requestHeader,
			@Valid @RequestBody CIInfraDetailUserSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		CIInfraDetailUserSacRes res = this.ciInfraService.detail(requestHeader, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}
}
