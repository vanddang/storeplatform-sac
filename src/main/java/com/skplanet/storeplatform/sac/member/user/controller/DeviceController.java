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

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRepresentationDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceListSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SupportAomReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SupportAomRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

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
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, @Valid @RequestBody ListDeviceReq req) {

		String userId = StringUtil.nvl(req.getUserId(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), "");
		String isMainDevice = StringUtil.nvl(req.getIsMainDevice(), "");

		if (!StringUtil.equals(userId, "") && StringUtil.equals(isMainDevice, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "isMainDevice");
		}

		if (StringUtil.equals(deviceId, "") && StringUtil.equals(deviceKey, "") && StringUtil.equals(isMainDevice, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "isMainDevice");
		}

		ListDeviceRes res = this.deviceService.listDevice(requestHeader, (ListDeviceReq) ConvertMapperUtils.convertObject(req));

		if (res.getDeviceInfoList() == null) {
			throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
		}
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

		DeviceInfo deviceInfo = req.getDeviceInfo();

		String userAuthKey = StringUtil.nvl(req.getUserAuthKey(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String regMaxCnt = StringUtil.nvl(req.getRegMaxCnt(), "");
		String deviceId = StringUtil.nvl(deviceInfo.getDeviceId(), "");
		String deviceIdType = StringUtil.nvl(deviceInfo.getDeviceIdType(), "");
		String deviceTelecom = StringUtil.nvl(deviceInfo.getDeviceTelecom(), "");
		String deviceModelNo = StringUtil.nvl(deviceInfo.getDeviceModelNo(), "");
		String isPrimary = StringUtil.nvl(deviceInfo.getIsPrimary(), "");

		/* 휴대기기 정보 필수 파라메터 체크 */

		if (StringUtil.equals(userAuthKey, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "userAuthKey");
		}

		if (StringUtil.equals(userKey, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		if (StringUtil.equals(regMaxCnt, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "regMaxCnt");
		}

		if (StringUtil.equals(deviceId, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		if (StringUtil.equals(deviceIdType, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceIdType");
		}

		if (StringUtil.equals(deviceTelecom, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceTelecom");
		}

		if (StringUtil.equals(deviceModelNo, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceModelNo");
		}

		if (StringUtil.equals(isPrimary, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "isPrimary");
		}

		CreateDeviceRes res = this.deviceService.createDevice(requestHeader, req);

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
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceInfo().getDeviceKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceInfo().getDeviceId(), "");

		if (StringUtil.equals(userKey, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		if (StringUtil.equals(deviceKey, "") && StringUtil.equals(deviceId, "")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceKey || deviceId");
		}

		ModifyDeviceRes res = this.deviceService.modifyDevice(requestHeader, req);

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

		LOGGER.info("###### modifyRepresentationDevice Request : {}", req.toString());

		if (StringUtil.nvl(req.getUserKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		if (StringUtil.nvl(req.getDeviceKey(), "").equals("") && StringUtil.nvl(req.getDeviceId(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceKey || deviceId");
		}

		SetMainDeviceRes res = this.deviceService.modifyRepresentationDevice(requestHeader, req);

		LOGGER.info("###### Final modifyRepresentationDevice Respone : {}", res.toString());

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
	public DetailRepresentationDeviceRes detailRepresentationDevice(SacRequestHeader requestHeader, @RequestBody DetailRepresentationDeviceReq req) {

		LOGGER.info("###### Start detailRepresentationDevice Request 가공전 : {}", req.toString());

		if (StringUtil.nvl(req.getUserKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		DetailRepresentationDeviceRes res = this.deviceService.detailRepresentationDeviceRes(requestHeader, req);

		LOGGER.info("###### Fianl detailRepresentationDevice Respone : {}", res.toString());
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

		String userAuthKey = StringUtil.nvl(req.getUserAuthKey(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		int deviceCount = 0;
		for (RemoveDeviceListSacReq id : req.getDeviceIdList()) {
			String deviceId = StringUtil.nvl(id.getDeviceId(), "");
			if (!deviceId.equals("")) {
				deviceCount += 1;
			}
		}

		LOGGER.info("============================================ Start removeDevice Request : {}", req.toString());

		if (userAuthKey.equals("") || userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userAuthKey && userKey");
		} else if (deviceCount == 0) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		req.setUserAuthKey(userAuthKey);
		req.setUserKey(userKey);

		RemoveDeviceRes res = this.deviceService.removeDevice(requestHeader, req);

		LOGGER.info("============================================ Final removeDevice Response : {}", res.toString());
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

		LOGGER.info("============================================ Start getSupportAom Request : {}", req.toString());

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		if ("".equals(userKey) && "".equals(deviceId)) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey or deviceId");
		}
		SupportAomRes res = this.deviceService.getSupportAom(requestHeader, req);

		LOGGER.info("============================================ Final getSupportAom Response : {}", res.toString());
		return res;
	}
}
