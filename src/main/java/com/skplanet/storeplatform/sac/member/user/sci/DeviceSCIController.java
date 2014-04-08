package com.skplanet.storeplatform.sac.member.user.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
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

	/**
	 * <pre>
	 * 단말 ID 정보(msisdn|uuid|mac) 조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchDeviceIdSacReq
	 * @return SearchDeviceIdSacRes
	 */
	@Override
	@RequestMapping(value = "/searchDeviceId", method = RequestMethod.POST)
	public @ResponseBody
	SearchDeviceIdSacRes searchDeviceId(@RequestBody @Validated SearchDeviceIdSacReq requestVO) {

		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		LOGGER.info("RequestParameter : {}", requestHeader, requestVO);

		DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID,
				requestVO.getDeviceKey(), requestVO.getUserKey());

		SearchDeviceIdSacRes responseVO = new SearchDeviceIdSacRes();
		if (deviceInfo != null && StringUtils.isNotBlank(deviceInfo.getDeviceId())) {
			responseVO.setDeviceId(deviceInfo.getDeviceId());
			responseVO.setDeviceTelecom(deviceInfo.getDeviceTelecom());
		} else {
			throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
		}

		LOGGER.info("ResponseParameter : {}", responseVO);
		return responseVO;
	}

	/**
	 * <pre>
	 * 기기변경 이력 조회.
	 * </pre>
	 * 
	 * @param request
	 *            ChangedDeviceHistorySacReq
	 * @return ChangedDeviceHistorySacRes
	 */
	@Override
	@RequestMapping(value = "/searchChangedDeviceHistory", method = RequestMethod.POST)
	public @ResponseBody
	ChangedDeviceHistorySacRes searchChangedDeviceHistory(@RequestBody @Validated ChangedDeviceHistorySacReq request) {

		// 공통 파라미터 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		LOGGER.info("[DeviceSCIController.searchChangedDeviceHistory] RequestHeader : {}, \nRequestParameter : {}",
				requestHeader, request);
		if (StringUtils.isBlank(request.getDeviceId()) && StringUtils.isBlank(request.getDeviceKey())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId 또는 deviceKey");
		}

		ChangedDeviceHistorySacRes changedDeviceHistoryRes = this.deviceService.searchChangedDeviceHistory(
				requestHeader, request);

		LOGGER.info("[DeviceSCIController.searchChangedDeviceHistory] ResponseParameter : {}", changedDeviceHistoryRes);
		return changedDeviceHistoryRes;

	}
}
