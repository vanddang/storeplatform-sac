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

		if (!userId.equals("") && isMainDevice.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "isMainDevice");
		}

		if (deviceId.equals("") && deviceKey.equals("") && isMainDevice.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "isMainDevice");
		}

		ListDeviceRes res = this.deviceService.listDevice(requestHeader, (ListDeviceReq) ConvertMapperUtils.convertObject(req));

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

		/* 휴대기기 정보 필수 파라메터 체크 */
		DeviceInfo deviceInfo = req.getDeviceInfo();
		if (StringUtil.nvl(deviceInfo.getDeviceId(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		if (StringUtil.nvl(deviceInfo.getDeviceIdType(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceIdType");
		}

		if (StringUtil.nvl(deviceInfo.getDeviceTelecom(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceTelecom");
		}

		if (StringUtil.nvl(deviceInfo.getIsPrimary(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "isPrimary");
		}

		if (StringUtil.nvl(deviceInfo.getIsAuthenticated(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "isAuthenticate");
		}

		if (StringUtil.nvl(deviceInfo.getIsUsed(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "isUsed");
		}

		if (StringUtil.nvl(deviceInfo.getAuthenticationDate(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "authenticationDate");
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

		if (StringUtil.nvl(req.getDeviceInfo().getDeviceKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceKey");
		}

		/* imei값은 수정 불가이므로 빈값처리 */
		DeviceInfo deviceInfo = req.getDeviceInfo();
		deviceInfo.setNativeId("");
		req.setDeviceInfo(deviceInfo);

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

		String userKey = StringUtil.nvl(req.getUserKey(), ""); // 사용자 Key
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), ""); // 기기 Key
		String deviceId = StringUtil.nvl(req.getDeviceId(), ""); // 기기 Id

		LOGGER.info("###### modifyRepresentationDevice Request : {}", req.toString());

		if (userKey.equals("") && deviceKey.equals("") && deviceId.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", req.toString());
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

		String userKey = StringUtil.nvl(req.getUserKey(), ""); // 사용자 Key

		if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", req.toString());
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
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		LOGGER.info("============================================ Start removeDevice Request : {}", req.toString());

		if (userAuthKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", req.toString());
		} else if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", req.toString());
		} else if (deviceId.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", req.toString());
		}

		req.setUserAuthKey(userAuthKey);
		req.setUserKey(userKey);
		req.setDeviceId(deviceId);

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

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		if (userKey.equals("") && deviceId.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", req.toString());
		}

		LOGGER.info("============================================ Start getSupportAom Request : {}", req.toString());

		req.setUserKey(userKey);
		req.setDeviceId(deviceId);

		SupportAomRes res = this.deviceService.getSupportAom(requestHeader, req);

		LOGGER.info("============================================ Final getSupportAom Response : {}", res.toString());
		return res;
	}
}
