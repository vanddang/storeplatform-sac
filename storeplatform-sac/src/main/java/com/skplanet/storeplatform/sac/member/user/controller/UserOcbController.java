/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.skplanet.storeplatform.sac.member.domain.shared.UserOcb;
import com.skplanet.storeplatform.sac.member.user.service.OcbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.common.OcbInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserOcbService;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 회원 OCB 정보 Controller
 * 
 * Updated on : 2014. 2. 12. Updated by : 심대진, 다모아 솔루션.
 */
@Controller
public class UserOcbController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserOcbController.class);

	@Autowired
	private UserOcbService svc;

    @Autowired
    private OcbService ocbService;

	/**
	 * <pre>
	 * [I01000029] 회원 OCB 정보 등록/수정.
	 * </pre>
	 * @param req Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createOcbInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateOcbInformationRes createOcbInformation(@Validated @RequestBody CreateOcbInformationReq req) {

		LOGGER.debug("########################################");
		LOGGER.debug("##### 2.1.29 회원 OCB 정보 등록/수정 #####");
		LOGGER.debug("########################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * 회원 OCB 정보 등록/수정 Biz
		 */
//		CreateOcbInformationRes res = this.svc.regOcbInformation(sacHeader, req);
        ocbService.merge(req.getUserKey(), req.getCardNumber(), req.getAuthMethodCode(), req.getRegId());

        CreateOcbInformationRes res = new CreateOcbInformationRes(req.getUserKey());
        LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * <pre>
	 * [I01000030] 회원 OCB 정보 삭제.
	 * </pre>
	 * 
	 * @param req Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/removeOcbInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveOcbInformationRes removeOcbInformation(@Validated @RequestBody RemoveOcbInformationReq req) {

		LOGGER.debug("###################################");
		LOGGER.debug("##### 2.1.29 회원 OCB 정보 삭제 #####");
		LOGGER.debug("###################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * 회원 OCB 정보 삭제 Biz
		 */
        ocbService.delete(req.getUserKey(), req.getCardNumber());
//		RemoveOcbInformationRes res = this.svc.remOcbInformation(sacHeader, req);
        RemoveOcbInformationRes res = new RemoveOcbInformationRes(req.getUserKey());

        LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * <pre>
	 * [I01000031] 회원 OCB 정보 조회.
	 * </pre>
	 * @param req Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/getOcbInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetOcbInformationRes getOcbInformation(@Validated @RequestBody GetOcbInformationReq req) {

		LOGGER.debug("###################################");
		LOGGER.debug("##### 2.1.29 회원 OCB 정보 조회 #####");
		LOGGER.debug("###################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

        List<UserOcb> list = ocbService.find(req.getUserKey());
        List<OcbInfo> transform = Lists.transform(list, new Function<UserOcb, OcbInfo>() {
            @Override
            public OcbInfo apply(UserOcb input) {
                return input.convertToOcbInfo();
            }
        });

        /**
		 * 회원 OCB 정보 조회 Biz
		 */
//		GetOcbInformationRes res = this.svc.getOcbInformation(sacHeader, req);
//
//		if (res.getOcbInfoList().size() > 0) {
//
//			for (OcbInfo info : res.getOcbInfoList()) {
//
//				LOGGER.info("Response : {}", info.getUserKey());
//
//			}
//
//		}

		return new GetOcbInformationRes(transform);
	}

}
