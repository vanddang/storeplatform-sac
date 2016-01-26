/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 배송지 정보 삭제
 * 
 * Updated on : 2015. 10. 05. Updated by : 최진호, Bogogt.
 */
public class RemoveDeliveryInfoSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 고유키. */
	private String userKey = "";

    public RemoveDeliveryInfoSacRes() {}

    public RemoveDeliveryInfoSacRes(String userKey) {
        this.userKey = userKey;
    }

    /**
	 * 사용자 키를 리턴한다.
	 * 
	 * @return userKey - 사용자 키
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 키를 셋팅한다.
	 * 
	 * @param userKey
	 *            사용자 키
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}


}
