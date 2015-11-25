/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

/**
 * Interface Message ProvisionDeviceProfileResult Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProvisionDeviceProfile extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonRes;
	private Device device;

	/**
	 * @return CommonResponse
	 */
	public CommonResponse getCommonRes() {
		return this.commonRes;
	}

	/**
	 * @param commonRes
	 *            commonRes
	 */
	public void setCommonRes(CommonResponse commonRes) {
		this.commonRes = commonRes;
	}

	/**
	 * @return Device
	 */
	public Device getDevice() {
		return this.device;
	}

	/**
	 * @param device
	 *            device
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

}
