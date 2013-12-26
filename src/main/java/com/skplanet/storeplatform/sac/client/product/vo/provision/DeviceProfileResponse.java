package com.skplanet.storeplatform.sac.client.product.vo.provision;

import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;

public class DeviceProfileResponse extends CommonVO {
	private CommonResponse commonRes;
	private Device device;

	public CommonResponse getCommonRes() {
		return this.commonRes;
	}

	public void setCommonRes(CommonResponse commonRes) {
		this.commonRes = commonRes;
	}

	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
}
