package com.skplanet.storeplatform.sac.client.display.vo.device;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 상품 ID에 대한 단말 Provisioning Request Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class DeviceProductProvisioningReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 상품 유형.
	 */
	@NotBlank
	private String productType;
	/**
	 * 상품 Id list.(episode ID만 가능)
	 */
	@NotBlank
	private String list;
	/**
	 * 단말 모델명.
	 */
	@NotBlank
	private String deviceModelNo;

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return this.productType;
	}

	/**
	 * @param productType
	 *            the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the list
	 */
	public String getList() {
		return this.list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(String list) {
		this.list = list;
	}

	/**
	 * @return the deviceModelNo
	 */
	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	/**
	 * @param deviceModelNo
	 *            the deviceModelNo to set
	 */
	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

}
