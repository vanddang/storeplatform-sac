package com.skplanet.storeplatform.sac.member.user.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchChangedDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchChangedDeviceResponse;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * 단말 정보 조회 내부메소드 호출 Controller.
 * 
 * Updated on : 2014. 2. 11. Updated by : 김다슬, 인크로스.
 */
@LocalSCI
@RequestMapping(value = "/member/user/sci")
public class DeviceSCIController implements DeviceSCI {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSCIController.class);

	@Autowired
	private DeviceService deviceService; // 휴대기기 관련 SAC 내부 인터페이스.

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 회원정보 관련 기능 인터페이스.

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.client.internal.member.sci.DeviceSCI#getDeviceMdn(com.skplanet.storeplatform
	 * .sac .client.internal.member.vo.GetMdnReq)
	 */

	@Override
	@RequestMapping(value = "/searchDeviceId", method = RequestMethod.POST)
	public @ResponseBody
	SearchDeviceIdSacRes searchDeviceId(@Validated SearchDeviceIdSacReq requestVO) {

		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		LOGGER.info("[DeviceSCIController.searchDeviceId] RequestHeader : {}, \nRequestParameter : {}", requestHeader,
				requestVO);

		DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID,
				requestVO.getDeviceKey(), requestVO.getUserKey());

		LOGGER.info("[DeviceSCIController.searchDeviceId] SearchDevice Info : {}", deviceInfo);

		SearchDeviceIdSacRes responseVO = new SearchDeviceIdSacRes();
		if (deviceInfo != null && deviceInfo.getDeviceId() != null) {
			responseVO.setDeviceId(deviceInfo.getDeviceId());
		} else {
			throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
		}
		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI#searchChangedDeviceHistoryList
	 * (com.skplanet .storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq)
	 */
	@Override
	@RequestMapping(value = "/searchChangedDeviceHistory", method = RequestMethod.POST)
	public ChangedDeviceHistorySacRes searchChangedDeviceHistory(ChangedDeviceHistorySacReq request) {

		String deviceId = request.getDeviceId();
		String deviceKey = request.getDeviceKey();

		String errorValue = "userKey 또는 ";

		// 필수파라미터 미입력.
		if ((deviceId == null || "".equals(deviceId)) && (deviceKey == null || "".equals(deviceKey))) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId 또는 deviceKey");
		} else if (deviceId != null || "".equals(deviceId)) {
			errorValue = StringUtil.capitalize(errorValue + "deviceKey");
		} else {
			errorValue = StringUtil.capitalize(errorValue + "deviceId");
		}

		// 공통 파라미터 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		// SC 회원 기기변경이력 조회 기능 호출.
		SearchChangedDeviceRequest searchChangedDeviceRequest = new SearchChangedDeviceRequest();
		searchChangedDeviceRequest.setUserKey(request.getUserKey());
		searchChangedDeviceRequest.setDeviceKey(request.getDeviceKey());
		searchChangedDeviceRequest.setDeviceID(request.getDeviceId());
		searchChangedDeviceRequest.setCommonRequest(commonRequest);

		LOGGER.info("[DeviceSCIController.searchChangedDeviceHistory] SC Request userSCI.searchChangedDevice : {}",
				searchChangedDeviceRequest);
		SearchChangedDeviceResponse searchChangedDeviceResponse = this.userSCI
				.searchChangedDevice(searchChangedDeviceRequest);

		ChangedDeviceHistorySacRes changedDeviceHistorySacRes = new ChangedDeviceHistorySacRes();

		if (searchChangedDeviceResponse != null && searchChangedDeviceResponse.getChangedDeviceLog() != null
				&& searchChangedDeviceResponse.getChangedDeviceLog().getDeviceKey() != null) {
			LOGGER.info(
					"[DeviceSCIController.searchChangedDeviceHistory] SC Response userSCI.searchChangedDevice : {}",
					searchChangedDeviceResponse.getChangedDeviceLog());
			changedDeviceHistorySacRes.setDeviceKey(searchChangedDeviceResponse.getChangedDeviceLog().getDeviceKey());
			changedDeviceHistorySacRes.setIsChanged(searchChangedDeviceResponse.getChangedDeviceLog().getIsChanged());
		} else {
			// Response DeviceKey값이 null일 경우, UserKey or DeviceId or DeviceKey 불량
			throw new StorePlatformException("SAC_MEM_0003", errorValue, "오류");
		}
		LOGGER.info("[DeviceSCIController.searchChangedDeviceHistory] SAC Response : {}", changedDeviceHistorySacRes);
		return changedDeviceHistorySacRes;

	}
}
