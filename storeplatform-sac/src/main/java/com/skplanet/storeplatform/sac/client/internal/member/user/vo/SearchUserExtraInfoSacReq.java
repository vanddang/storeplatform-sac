package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.11. 회원 부가속성 정보 조회 [REQUEST].
 * 
 * Updated on : 2014. 10. 29. Updated by : Rejoice, Burkhan
 */
public class SearchUserExtraInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 회원 키 리스트. */
	@javax.validation.constraints.NotNull.List(value = { @NotNull })
	private List<String> userKeyList;
	/** 부가정보 코드 리스트. */
	private List<String> extraProfileList;

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
	 * @return the extraProfileList
	 */
	public List<String> getExtraProfileList() {
		return this.extraProfileList;
	}

	/**
	 * @param extraProfileList
	 *            the extraProfileList to set
	 */
	public void setExtraProfileList(List<String> extraProfileList) {
		this.extraProfileList = extraProfileList;
	}
}
