package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.57. 소셜 계정 삭제 [RESPONSE].
 * 
 * Updated on : 2015. 6. 3. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RemoveSocialAccountSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 key. */
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
