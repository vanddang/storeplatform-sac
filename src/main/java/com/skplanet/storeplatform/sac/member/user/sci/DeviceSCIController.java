package com.skplanet.storeplatform.sac.member.user.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class DeviceSCIController implements DeviceSCI {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSCIController.class);

	@Autowired
	private DeviceService deviceService; // 휴대기기 관련 SAC 내부 인터페이스.

	@Autowired
	private com.skplanet.storeplatform.member.client.user.sci.DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스.

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.sci.DeviceInternalSCI#getDeviceMdn(com.skplanet.storeplatform
	 * .sac .client.internal.member.vo.GetMdnReq)
	 */

	@Override
	public @ResponseBody
	SearchDeviceIdSacRes searchDeviceId(@Validated SearchDeviceIdSacReq requestVO) {

		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		LOGGER.info("[DeviceInternalSCIController.searchDeviceId] RequestHeader : {}, \nRequestParameter : {}",
				requestHeader, requestVO);

		DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID,
				requestVO.getDeviceKey(), requestVO.getUserKey());

		LOGGER.info("[DeviceInternalSCIController.searchDeviceId] SearchDevice Info : {}", deviceInfo);

		SearchDeviceIdSacRes responseVO = new SearchDeviceIdSacRes();
		responseVO.setDeviceId(deviceInfo.getDeviceId());

		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceInternalSCI#searchChangedDeviceHistoryList
	 * (com.skplanet .storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq)
	 */
	@Override
	public ChangedDeviceHistorySacRes searchChangedDeviceHistoryList(ChangedDeviceHistorySacReq request) {
		// TODO 1. SC 회원 기기변경이력 조회 기능 호출.
		// ChangedDeviceListRes changedDeviceList = this.deviceSCI.searchChangedDeviceList(request);
		// changedDeviceList.get~

		// ChangedDeviceHistorySacRes changedDeviceHistorySacRes = new ChangedDeviceHistorySacRes();
		// changedDeviceHistorySacRes.setDeviceKey(deviceKey);
		// changedDeviceHistorySacRes.setIsChanged(isChanged);

		// return changedDeviceHistorySacRes;
		return null;
	}

}
