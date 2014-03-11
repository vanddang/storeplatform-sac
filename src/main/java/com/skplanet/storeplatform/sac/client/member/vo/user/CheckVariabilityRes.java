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
	 * 로그인 성공여부(Y/N).
	 */
	private String isLoginSuccess;

	/**
	 * 변동성 회원 추가 인증 정보
	 */
	private UserAuthMethod userAuthMethod;

	/**
	 * @return isLoginSuccess
	 */
	public String getIsLoginSuccess() {
		return this.isLoginSuccess;
	}

	/**
	 * @param isLoginSuccess
	 *            String
	 */
	public void setIsLoginSuccess(String isLoginSuccess) {
		this.isLoginSuccess = isLoginSuccess;
	}

	/**
	 * @return String
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
