/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * SAC [RESPONSE] 기기변경 이력조회.
 * 
 * Updated on : 2014. 2. 21. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ChangedDeviceHistoryRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 기기 변경 여부 (Y/N)
	 */
	private String isChanged;

	/**
	 * 기기 키.
	 */
	private String deviceKey;

	/**
	 * @return the isChanged
	 */
	public String getIsChanged() {
		return this.isChanged;
	}

	/**
	 * @param isChanged
	 *            the isChanged to set
	 */
	public void setIsChanged(String isChanged) {
		this.isChanged = isChanged;
	}

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

}
