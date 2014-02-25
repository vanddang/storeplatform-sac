/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST]userKey 목록을 이용하여 회원정보 목록조회.
 * 
 * Updated on : 2014. 2. 24. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserReq extends CommonInfo {
	private static final long serialVersionUID = 1L;
	/**
	 * 회원 키.
	 */
	private List<String> userKeyList;

	/**
	 * @return the userKeyList
	 */
	public List<String> getUserKeyList() {
		return this.userKeyList;
	}

	/**
	 * @param userKeyList
	 *            the userKeyList to set
	 */
	public void setUserKeyList(List<String> userKeyList) {
		this.userKeyList = userKeyList;
	}
}
