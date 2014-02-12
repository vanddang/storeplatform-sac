/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceMdnSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceMdnSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * 단말 정보 조회 내부메소드 호출 Controller.
 * 
 * Updated on : 2014. 2. 11. Updated by : 김다슬, 인크로스.
 */
@LocalSCI
@RequestMapping(value = "/user")
public class DeviceSCIController implements DeviceSCI {

	@Autowired
	private DeviceService deviceService; // 휴대기기 관련 인터페이스.

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.sci.DeviceSCI#getDeviceMdn(com.skplanet.storeplatform.sac
	 * .client.internal.member.vo.GetMdnReq)
	 */

	@Override
	@RequestMapping(value = "/searchDeviceMdn", method = RequestMethod.POST)
	public SearchDeviceMdnSacRes searchDeviceMdn(
	/* SacRequestHeader requestHeader, */@Validated SearchDeviceMdnSacReq requestVO) {

		SacRequestHeader requestHeader = new SacRequestHeader(); // client-internal에 공통으로 생성되면 삭제 후 Bypass 예정.
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setSystemId("S01-01001");
		tenantHeader.setTenantId("S01");
		requestHeader.setTenantHeader(tenantHeader);

		DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID,
				requestVO.getDeviceKey(), requestVO.getUserKey());

		SearchDeviceMdnSacRes responseVO = new SearchDeviceMdnSacRes();
		responseVO.setMsisdn(deviceInfo.getDeviceId());

		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.client.internal.member.sci.DeviceSCI#searchChangedDeviceList(com.skplanet.
	 * storeplatform.sac.client.internal.member.vo.ChangedDeviceReq)
	 */
	@Override
	public ChangedDeviceSacRes searchChangedDeviceList(ChangedDeviceSacReq requestVO) {
		// TODO Auto-generated method stub
		return null;
	}

}
