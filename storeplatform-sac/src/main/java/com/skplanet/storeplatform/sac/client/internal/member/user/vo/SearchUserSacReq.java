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
 * [REQUEST] UserKey를 이용한 회원정보 조회.
 *
 * Updated on : 2014. 2. 12. Updated by : 김다슬, 인크로스.
 * Updated on : 2016. 1. 25. Updated by : 윤보영, 카레즈.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 회원 키.
	 */
	@javax.validation.constraints.NotNull.List(value = { @NotNull })
	private List<String> userKeyList;

	/** tenant Id (타파트 작업 후 삭제 필요) */
    @Deprecated
	private String tenantId;

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

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
