package com.skplanet.storeplatform.sac.client.member.vo.user;

import javax.validation.constraints.Max;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.44. 휴대기기 PIN 등록 [REQUEST].
 * 
 * Updated on : 2014. 10. 31. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateDevicePinSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 기기Key. */
	@NotBlank
	private String deviceKey;
	/** 회원 Key. */
	@NotBlank
	private String userKey;
	/** PIN 번호. */
	@NotBlank
	@Length(min=4, max=4)
	private String pinNo;

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
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
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

}
