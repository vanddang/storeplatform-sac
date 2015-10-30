/**
 * 
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;

/**
 * 휴대기기 설정 등록/수정 요청 Value Object.
 * 
 * Updated on : 2014. 10. 30. Updated by : Rejoice, Burkhan
 */
public class ModifyDeviceSetInfoRequest extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 조건 객체. */
	private List<KeySearch> keySearchList;
	/** 휴대기기 설정 정보. */
	private UserMbrDeviceSet userMbrDeviceSet;

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
	 * @return the keySearchList
	 */
	public List<KeySearch> getKeySearchList() {
		return this.keySearchList;
	}

	/**
	 * @param keySearchList
	 *            the keySearchList to set
	 */
	public void setKeySearchList(List<KeySearch> keySearchList) {
		this.keySearchList = keySearchList;
	}

	/**
	 * @return the userMbrDeviceSet
	 */
	public UserMbrDeviceSet getUserMbrDeviceSet() {
		return this.userMbrDeviceSet;
	}

	/**
	 * @param userMbrDeviceSet
	 *            the userMbrDeviceSet to set
	 */
	public void setUserMbrDeviceSet(UserMbrDeviceSet userMbrDeviceSet) {
		this.userMbrDeviceSet = userMbrDeviceSet;
	}

}
