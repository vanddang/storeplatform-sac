package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserAuthMethod;

/**
 * [RESPONSE] 변동성 회원 체크.
 * 
 * Updated on : 2014. 3. 11. Updated by : 반범진. 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CheckVariabilityRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 변동성 체크 성공유무(Y/N).
	 */
	private String isVariability;

	/**
	 * 사용자 키.
	 */
	private String userKey;

	/**
	 * 기기 Key.
	 */
	private String deviceKey;

	/**
	 * 변동성 회원 추가 인증 정보.
	 */
	private UserAuthMethod userAuthMethod;

	/**
	 * @return isVariability
	 */
	public String getIsVariability() {
		return this.isVariability;
	}

	/**
	 * @param isVariability
	 *            String
	 */
	public void setIsVariability(String isVariability) {
		this.isVariability = isVariability;
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
	 * @return deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            String
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return userAuthMethod
	 */
	public UserAuthMethod getUserAuthMethod() {
		return this.userAuthMethod;
	}

	/**
	 * @param userAuthMethod
	 *            UserAuthMethod
	 */
	public void setUserAuthMethod(UserAuthMethod userAuthMethod) {
		this.userAuthMethod = userAuthMethod;
	}

}
