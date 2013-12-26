package com.skplanet.storeplatform.sac.client.product.vo.provision;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class DeviceProfileRequest extends CommonInfo {
	/**
	 * 제조사명
	 */
	private String manufacturer;
	/**
	 * 모델명
	 */
	private String model;

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
