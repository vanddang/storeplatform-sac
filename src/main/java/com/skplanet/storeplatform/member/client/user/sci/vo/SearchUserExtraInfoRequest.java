package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 멀티 회원 부가속성 정보 조회 요청 Value Object.
 * 
 * Updated on : 2014. 10. 30. Updated by : Rejoice, Burkhan
 */
public class SearchUserExtraInfoRequest extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 Key List. */
	private List<String> userKeyList;

	/** 부가속성 코드 List. */
	private List<String> extraProfileList;

	/**
	 * @return the commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @param commonRequest
	 *            the commonRequest to set
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

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
