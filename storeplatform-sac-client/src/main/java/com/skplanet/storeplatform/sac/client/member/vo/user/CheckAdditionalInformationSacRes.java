package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceKeyInfo;

/**
 * [RESPONSE] 회원의 부가 정보(소셜 계정 아이디) 중복 체크
 * 
 * Updated on : 2015. 4. 9. Updated by : 반범진.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CheckAdditionalInformationSacRes extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용 여부.
	 */
	private String useYn;

	/**
	 * 사용자 Key.
	 */
	private String userKey;

	/**
	 * 테넌트 아이디.
	 */
	private String tenantId;

	/**
	 * 기기 리스트.
	 */
	private List<DeviceKeyInfo> deviceKeyList;

	/**
	 * @return useYn
	 */
	public String getUseYn() {
		return this.useYn;
	}

	/**
	 * @param useYn
	 *            String
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            String
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return deviceKeyList
	 */
	public List<DeviceKeyInfo> getDeviceKeyList() {
		return this.deviceKeyList;
	}

	/**
	 * @param deviceKeyList
	 *            List<DeviceKeyInfo>
	 */
	public void setDeviceKeyList(List<DeviceKeyInfo> deviceKeyList) {
		this.deviceKeyList = deviceKeyList;
	}

}
