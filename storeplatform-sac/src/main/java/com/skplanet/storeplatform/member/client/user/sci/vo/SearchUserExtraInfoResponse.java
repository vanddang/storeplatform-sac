package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.util.Map;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * Class 설명
 * 
 * Updated on : 2014. 10. 30. Updated by : Rejoice, Burkhan
 */
public class SearchUserExtraInfoResponse extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	private Map searchUserExtraInfoMap;

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * @return the searchUserExtraInfoMap
	 */
	public Map getSearchUserExtraInfoMap() {
		return this.searchUserExtraInfoMap;
	}

	/**
	 * @param searchUserExtraInfoMap
	 *            the searchUserExtraInfoMap to set
	 */
	public void setSearchUserExtraInfoMap(Map searchUserExtraInfoMap) {
		this.searchUserExtraInfoMap = searchUserExtraInfoMap;
	}

}
