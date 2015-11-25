package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.57. 실명 인증 정보 초기화 [REQUEST].
 * 
 * Updated on : 2015. 4. 09. Updated by : 윤보영, 카레즈.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class InitRealNameReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String userKey;

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

}
