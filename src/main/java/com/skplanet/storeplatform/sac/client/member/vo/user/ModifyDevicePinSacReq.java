package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 10. 31. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyDevicePinSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/** 기기Key. */
	private String deviceKey;
	/** 기기 ID. */
	private String deviceId;
	/** PIN 번호. */
	@NotBlank
	private String pinNo;
	/** 변경할 PIN 번호. */
	@NotBlank
	private String newPinNo;

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the pinNo
	 */
	public String getPinNo() {
		return this.pinNo;
	}

	/**
	 * @param pinNo
	 *            the pinNo to set
	 */
	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	/**
	 * @return the newPinNo
	 */
	public String getNewPinNo() {
		return this.newPinNo;
	}

	/**
	 * @param newPinNo
	 *            the newPinNo to set
	 */
	public void setNewPinNo(String newPinNo) {
		this.newPinNo = newPinNo;
	}

}
