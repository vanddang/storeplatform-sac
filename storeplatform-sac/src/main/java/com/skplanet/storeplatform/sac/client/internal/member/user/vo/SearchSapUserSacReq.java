/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] UserKey, TenantId를 이용한 회원정보 조회.
 * 
 * Updated on : 2015. 4. 13. Updated by : 반범진.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchSapUserSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 회원 키, 테넌트 아이디.
	 */
	@javax.validation.constraints.NotNull.List(value = { @NotNull })
	private List<SearchSapUserInfoSac> userKeyList;

	/**
	 * @return userKeyList
	 */
	public List<SearchSapUserInfoSac> getUserKeyList() {
		return this.userKeyList;
	}

	/**
	 * @param userKeyList
	 *            String
	 */
	public void setUserKeyList(List<SearchSapUserInfoSac> userKeyList) {
		this.userKeyList = userKeyList;
	}

}
