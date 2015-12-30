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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;
import com.skplanet.storeplatform.sac.member.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 휴대기기관련 Controller.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@RequestMapping(value = "/member/user")
@Controller
public class DeviceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private UserService userService;

	/**
	 * 휴대기기 목록조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ListDeviceReq
	 * @return ListDeviceRes
	 */
	@RequestMapping(value = "/listDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, @Valid @RequestBody ListDeviceRemoveDeviceIdReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		// userId, userKey 조회시 isMainDevice는 필수
		if (StringUtils.isEmpty(req.getDeviceKey())){
			if (StringUtils.isEmpty(req.getIsMainDevice())) {
				throw new StorePlatformException("SAC_MEM_0001", "isMainDevice");
			}
		// deviceKey로 조회시 userKey 필수
		}else{
			if (StringUtils.isEmpty(req.getUserKey())) {
				throw new StorePlatformException("SAC_MEM_0001", "userKey");
			}
		}

		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setUserId(req.getUserId());
		listDeviceReq.setUserKey(req.getUserKey());
		listDeviceReq.setDeviceKey(req.getDeviceKey());
		listDeviceReq.setIsMainDevice(req.getIsMainDevice());

		ListDeviceRes res = this.deviceService.listDevice(requestHeader, listDeviceReq);

		if (res.getDeviceInfoList() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
		}

		LOGGER.info("Response : {}, device count : {}", res.getUserKey(), res.getDeviceInfoList().size());

		return res;
	}

	/**
	 * 휴대기기 등록.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateDeviceReq
	 * @return CreateDeviceRes
	 */
	@RequestMapping(value = "/createDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateDeviceRes createDevice(SacRequestHeader requestHeader, @Valid @RequestBody CreateDeviceReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtil.isBlank(req.getUserKey())) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		if (StringUtil.isBlank(req.getRegMaxCnt())) {
			throw new StorePlatformException("SAC_MEM_0001", "regMaxCnt");
		}

		if (StringUtil.isBlank(req.getDeviceInfo().getDeviceId())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		if (StringUtil.isBlank(req.getDeviceInfo().getDeviceIdType())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceIdType");
		}

		if (StringUtil.isBlank(req.getDeviceInfo().getDeviceTelecom())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceTelecom");
		}

		if (StringUtil.isBlank(req.getDeviceInfo().getDeviceModelNo())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceModelNo");
		}

		if (StringUtil.isBlank(req.getDeviceInfo().getIsPrimary())) {
			throw new StorePlatformException("SAC_MEM_0001", "isPrimary");
		}

		// onebrand에서 mdn 컬럼이 추가되어, deviceIdType이 msisdn인 경우 deviceId를 mdn 필드로 셋팅한다.
		if(StringUtils.equals(req.getDeviceInfo().getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)){
			req.getDeviceInfo().setMdn(req.getDeviceInfo().getDeviceId());
			req.getDeviceInfo().setDeviceId("");
		}

		CreateDeviceRes res = this.deviceService.regDevice(requestHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;
	}

	/**
	 * 휴대기기 수정.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ModifyDeviceReq
	 * @return ModifyDeviceRes
	 */
	@RequestMapping(value = "/modifyDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyDeviceRes modifyDevice(SacRequestHeader requestHeader, @Valid @RequestBody ModifyDeviceReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtil.isBlank(req.getUserKey())) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		/* deviceId, deviceKey 둘중하나는 필수 */
		if (StringUtil.isBlank(req.getDeviceInfo().getDeviceKey())
				&& StringUtil.isBlank(req.getDeviceInfo().getDeviceId())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceKey || deviceId");
		}

		// deviceIdType = msisdn인경우 mdn필드에 셋팅
		if(StringUtils.equals(req.getDeviceInfo().getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)){
			req.getDeviceInfo().setMdn(req.getDeviceInfo().getDeviceId());
			req.getDeviceInfo().setDeviceId("");
		}

		ModifyDeviceRes res = this.deviceService.modDevice(requestHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;
	}

	/**
	 * 휴대기기 대표단말 설정.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            SetMainDeviceReq
	 * @return SetMainDeviceRes
	 */
	@RequestMapping(value = "/modifyRepresentationDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public SetMainDeviceRes modifyRepresentationDevice(SacRequestHeader requestHeader, @RequestBody SetMainDeviceReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtil.nvl(req.getUserKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		if (StringUtil.nvl(req.getDeviceKey(), "").equals("") && StringUtil.nvl(req.getDeviceId(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceKey || deviceId");
		}

		SetMainDeviceRes res = this.deviceService.modRepresentationDevice(requestHeader, req);

		LOGGER.info("Response : {}", res.getDeviceKey());

		return res;
	}

	/**
	 * 휴대기기 대표단말 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            DetailRepresentationDeviceReq
	 * @return DetailRepresentationDeviceRes
	 */
	@RequestMapping(value = "/detailRepresentationDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailRepresentationDeviceRes detailRepresentationDevice(SacRequestHeader requestHeader,
			@RequestBody DetailRepresentationDeviceReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtil.nvl(req.getUserKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		DetailRepresentationDeviceRes res = this.deviceService.detailRepresentationDeviceRes(requestHeader, req);

		LOGGER.info("Response : {}", res.getDeviceInfo().getDeviceKey());

		return res;
	}

	/**
	 * 휴대기기 단말 삭제.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            RemoveDeviceReq
	 * @return RemoveDeviceRes
	 */
	@RequestMapping(value = "/removeDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveDeviceRes removeDevice(SacRequestHeader requestHeader, @RequestBody RemoveDeviceReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		int deviceCount = 0;
		for (RemoveDeviceListSacReq id : req.getDeviceIdList()) {
			if (StringUtils.isNotBlank(id.getDeviceId())) {
				deviceCount += 1;
			}
		}

		if (StringUtils.isEmpty(req.getUserKey())) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		} else if (deviceCount == 0) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		RemoveDeviceRes res = this.deviceService.remDevice(requestHeader, req);

		LOGGER.info("Response : {}", res.getDeviceKeyList().get(0).getDeviceKey());

		return res;
	}

	/**
	 * 단말 AOM 지원여부 확인.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            RemoveDeviceReq
	 * @return RemoveDeviceRes
	 */
	@RequestMapping(value = "/getSupportAom/v1", method = RequestMethod.POST)
	@ResponseBody
	public SupportAomRes getSupportAom(SacRequestHeader requestHeader, @RequestBody SupportAomReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		if ("".equals(userKey) && "".equals(deviceId)) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey or deviceId");
		}
		SupportAomRes res = this.deviceService.getSupportAom(requestHeader, req);

		LOGGER.info("Response : {}, {}", req.getDeviceId(), res.getIsAomSupport());

		return res;
	}

    /**
     * 휴대기기 단말 삭제v2.
     *
     * @param requestHeader
     *            SacRequestHeader
     * @param req
     *            RemoveDeviceReq
     * @return RemoveDeviceRes
     */
    @RequestMapping(value = "/removeDevice/v2", method = RequestMethod.POST)
    @ResponseBody
    public RemoveDeviceRes removeDeviceV2(SacRequestHeader requestHeader, @RequestBody RemoveDeviceReq req) {
        LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

        int deviceCount = 0;
        for (RemoveDeviceKeyListSacReq deviceKeyList : req.getDeviceKeyList()) {
            if (StringUtils.isNotBlank(deviceKeyList.getDeviceKey())) {
                deviceCount += 1;
            }
        }

        if (StringUtils.isEmpty(req.getUserKey())) {
            throw new StorePlatformException("SAC_MEM_0001", "userKey");
        } else if (deviceCount == 0) {
            throw new StorePlatformException("SAC_MEM_0001", "deviceKey");
        }

        RemoveDeviceRes res = this.deviceService.remDeviceV2(requestHeader, req);

        LOGGER.info("Response : {}", res.getDeviceKeyList().get(0).getDeviceKey());

        return res;
    }
}
