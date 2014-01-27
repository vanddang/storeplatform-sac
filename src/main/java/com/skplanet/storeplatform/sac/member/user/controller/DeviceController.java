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
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SetMainDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SupportAomReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SupportAomRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.ConvertMapperUtil;
import com.skplanet.storeplatform.sac.member.common.HeaderInfo;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * 휴대기기관련 Controller
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@RequestMapping(value = "/member/user")
@Controller
public class DeviceController {

	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	private HeaderInfo headerInfo;

	@Autowired
	private DeviceService deviceService;

	/**
	 * 휴대기기 목록조회
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ListDeviceReq
	 * @return ListDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/listDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public ListDeviceRes listDevice(SacRequestHeader requestHeader, @Valid @RequestBody ListDeviceReq req)
			throws Exception {

		String userId = StringUtil.nvl(req.getUserId(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), "");
		String isMainDevice = StringUtil.nvl(req.getIsMainDevice(), "");

		if (!userId.equals("") && isMainDevice.equals("")) {
			logger.info(":::: 1");
			throw new Exception("isMainDevice 필수 파라미터 입니다.");
		}

		if (deviceId.equals("") && deviceKey.equals("") && isMainDevice.equals("")) {
			throw new Exception("isMainDevice 필수 파라미터 입니다.");
		}

		ListDeviceRes res = this.deviceService.listDevice(requestHeader,
				(ListDeviceReq) ConvertMapperUtil.convertObject(req));

		return res;
	}

	/**
	 * 휴대기기 등록
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            CreateDeviceReq
	 * @return CreateDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/createDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateDeviceRes createDevice(SacRequestHeader requestHeader, @Valid @RequestBody CreateDeviceReq req)
			throws Exception {

		/* 휴대기기 정보 필수 파라메터 체크 */
		DeviceInfo deviceInfo = req.getDeviceInfo();
		if (StringUtil.nvl(deviceInfo.getDeviceId(), "").equals("")) {
			throw new Exception("deviceId 필수 파라미터 입니다.");
		}

		if (StringUtil.nvl(deviceInfo.getDeviceIdType(), "").equals("")) {
			throw new Exception("deviceIdType 필수 파라미터 입니다.");
		}

		if (StringUtil.nvl(deviceInfo.getDeviceTelecom(), "").equals("")) {
			throw new Exception("deviceTelecom 필수 파라미터 입니다.");
		}

		if (StringUtil.nvl(deviceInfo.getIsPrimary(), "").equals("")) {
			throw new Exception("isPrimary 필수 파라미터 입니다.");
		}

		if (StringUtil.nvl(deviceInfo.getIsAuthenticated(), "").equals("")) {
			throw new Exception("isAuthenticate 필수 파라미터 입니다.");
		}

		if (StringUtil.nvl(deviceInfo.getIsUsed(), "").equals("")) {
			throw new Exception("isUsed 필수 파라미터 입니다.");
		}

		if (StringUtil.nvl(deviceInfo.getAuthenticationDate(), "").equals("")) {
			throw new Exception("authenticationDate 필수 파라미터 입니다.");
		}

		CreateDeviceRes res = this.deviceService.createDevice(requestHeader, req);

		return res;
	}

	/**
	 * 휴대기기 수정
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            ModifyDeviceReq
	 * @return ModifyDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/modifyDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyDeviceRes modifyDevice(SacRequestHeader requestHeader, @Valid @RequestBody ModifyDeviceReq req)
			throws Exception {

		if (StringUtil.nvl(req.getDeviceInfo().getDeviceKey(), "").equals("")) {
			throw new Exception("deviceKey 필수 파라미터 입니다.");
		}

		ModifyDeviceRes res = this.deviceService.modifyDevice(requestHeader, req);

		return res;
	}

	/**
	 * 휴대기기 대표단말 설정
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            SetMainDeviceReq
	 * @return SetMainDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/modifyRepresentationDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public SetMainDeviceRes modifyRepresentationDevice(SacRequestHeader requestHeader, @RequestBody SetMainDeviceReq req)
			throws Exception {

		String userKey = StringUtil.nvl(req.getUserKey(), ""); // 사용자 Key
		String deviceKey = StringUtil.nvl(req.getDeviceKey(), ""); // 기기 Key

		logger.info("###### modifyRepresentationDevice Request : {}", req.toString());
		if (userKey.equals("") && deviceKey.equals("")) {
			throw new RuntimeException("필수요청 파라메터 부족");
		}

		SetMainDeviceRes res = this.deviceService.modifyRepresentationDevice(requestHeader, req);

		logger.info("###### Final modifyRepresentationDevice Respone : {}", res.toString());

		return res;
	}

	/**
	 * 휴대기기 대표단말 조회
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            DetailRepresentationDeviceReq
	 * @return DetailRepresentationDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/detailRepresentationDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailRepresentationDeviceRes detailRepresentationDevice(SacRequestHeader requestHeader,
			@RequestBody DetailRepresentationDeviceReq req) throws Exception {

		logger.info("###### Start detailRepresentationDevice Request 가공전 : {}", req.toString());

		String userId = StringUtil.nvl(req.getUserId(), ""); // 사용자 ID
		String userKey = StringUtil.nvl(req.getUserKey(), ""); // 사용자 Key

		if (userId.equals("") && userKey.equals("")) {
			throw new RuntimeException("필수요청 파라메터 부족");
		}

		DetailRepresentationDeviceRes res = this.deviceService.detailRepresentationDeviceRes(requestHeader, req);

		logger.info("###### Fianl detailRepresentationDevice Respone : {}", res.toString());
		return res;
	}

	/**
	 * 휴대기기 단말 삭제
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            RemoveDeviceReq
	 * @return RemoveDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/removeDevice/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveDeviceRes removeDevice(SacRequestHeader requestHeader, @RequestBody RemoveDeviceReq req)
			throws Exception {

		String userAuthKey = StringUtil.nvl(req.getUserAuthKey(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		logger.info("###### Start removeDevice Request : {}", req.toString());

		if (userAuthKey.equals("")) {
			throw new Exception("필수요청 파라메터 부족 userAuthKey");
		} else if (userKey.equals("")) {
			throw new Exception("필수요청 파라메터 부족 userKey");
		} else if (deviceId.equals("")) {
			throw new Exception("필수요청 파라메터 부족 deviceId");
		}

		req.setUserAuthKey(userAuthKey);
		req.setUserKey(userKey);
		req.setDeviceId(deviceId);

		RemoveDeviceRes res = this.deviceService.removeDevice(requestHeader, req);

		logger.info("###### Final removeDevice Response : {}", res.toString());
		return res;
	}

	/**
	 * 단말 AOM 지원여부 확인
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            RemoveDeviceReq
	 * @return RemoveDeviceRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/getSupportAom/v1", method = RequestMethod.GET)
	@ResponseBody
	public SupportAomRes getSupportAom(SacRequestHeader requestHeader, SupportAomReq req) throws Exception {

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		if (userKey.equals("") || deviceId.equals("")) {
			throw new Exception("필수요청 파라메터 부족");
		}

		logger.info("###### Start getSupportAom Request : {}", req.toString());

		req.setUserKey(userKey);
		req.setDeviceId(deviceId);

		SupportAomRes res = this.deviceService.getSupportAom(requestHeader, req);

		logger.info("###### Final getSupportAom Response : {}", res.toString());
		return res;
	}
}
