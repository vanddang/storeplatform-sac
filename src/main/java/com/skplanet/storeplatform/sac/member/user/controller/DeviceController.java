package com.skplanet.storeplatform.sac.member.user.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceRequest;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.member.common.HeaderInfo;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * 휴대기기관련 Controller
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@RequestMapping(value = "/dev/member/user")
@Controller
public class DeviceController {

	private static final Logger logger = LoggerFactory.getLogger(UserJoinController.class);

	@Autowired
	private HeaderInfo headerInfo;

	@Autowired
	private DeviceService deviceService;

	/**
	 * 휴대기기 목록조회
	 * 
	 * @param headers
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listDevice/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListDeviceRes listDevice(@RequestHeader Map<String, Object> headers, @RequestBody ListDeviceReq req)
			throws Exception {

		String userId = StringUtil.nvl(req.getUserId(), ""); // 사용자 ID
		String userKey = StringUtil.nvl(req.getUserKey(), ""); // 사용자 Key
		String deviceId = StringUtil.nvl(req.getDeviceId(), ""); // 기기ID(mdn,uuid)
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), ""); // 기기 Key

		if (userId.equals("") && userKey.equals("") && deviceId.equals("") && deviceKey.equals("")) {
			throw new Exception("필수요청 파라메터 부족");
		}

		/* Header 정보 세팅 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		ListDeviceRes res = this.deviceService.listDevice(headerVo, req);

		return res;
	}

	/**
	 * 휴대기기 등록
	 * 
	 * @param headers
	 * @param req
	 * @return
	 * @throws Exception
	 */
	/* @RequestMapping(value = "/createDevice/v1", method = RequestMethod.POST) */
	@ResponseBody
	public CreateDeviceRes createDevice(@RequestHeader Map<String, Object> headers, @RequestBody CreateDeviceReq req)
			throws Exception {

		/* Header 정보 세팅 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		CreateDeviceRes res = this.deviceService.createDevice(headerVo, req);

		return res;
	}

	/**
	 * 휴대기기 대표단말 설정
	 * 
	 * @param headers
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifyRepresentationDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public DeviceInfo modifyRepresentationDevice(@RequestHeader Map<String, Object> headers,
			@RequestBody SetMainDeviceRequest req) throws Exception {

		/* Header 정보 세팅 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		String userKey = StringUtil.nvl(req.getUserKey(), ""); // 사용자 Key
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), ""); // 기기 Key

		if (userKey.equals("") || deviceKey.equals("")) {
			throw new Exception("필수요청 파라메터 부족");
		}

		DeviceInfo res = this.deviceService.modifyRepresentationDevice(headerVo, req);

		return res;
	}

	/**
	 * 휴대기기 대표단말 조회
	 * 
	 * @param headers
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailRepresentationDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public ListDeviceRes detailRepresentationDevice(@RequestHeader Map<String, Object> headers,
			@RequestBody ListDeviceReq req) throws Exception {

		/* Header 정보 세팅 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		String userId = StringUtil.nvl(req.getUserId(), ""); // 사용자 ID
		String userKey = StringUtil.nvl(req.getUserKey(), ""); // 사용자 Key
		String isMainDevice = StringUtil.nvl(req.getIsMainDevice(), ""); // 대표단말 조회설정

		if (userId.equals("") || userKey.equals("") || isMainDevice.equals("")) {
			throw new Exception("필수요청 파라메터 부족");
		} else if ("".equals(isMainDevice) || "N".equals(isMainDevice)) {
			throw new Exception("대표단말 조회값이 없거나 'N' 입니다.");
		}

		ListDeviceRes res = this.deviceService.listDevice(headerVo, req);

		return res;
	}
}
