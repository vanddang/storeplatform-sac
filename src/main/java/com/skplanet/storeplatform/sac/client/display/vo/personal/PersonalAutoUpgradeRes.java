package com.skplanet.storeplatform.sac.client.display.vo.personal;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;

/**
 * 특정 단말 조회 Response Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class PersonalAutoUpgradeRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse;
	private Device device;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
}
