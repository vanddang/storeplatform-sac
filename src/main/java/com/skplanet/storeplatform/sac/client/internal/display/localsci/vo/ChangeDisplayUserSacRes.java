package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ChangeDisplayUserSacRes Value Object
 * 
 * 회원 ID, Key 변경에 따른 전시 테이블 회원 ID, Key 업데이트 응답 VO
 * 
 * Updated on : 2014. 2. 13. Updated by : 김현일, 인크로스
 */
public class ChangeDisplayUserSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String resultStatus;

	/**
	 * @return String
	 */
	public String getResultStatus() {
		return this.resultStatus;
	}

	/**
	 * @param resultStatus
	 *            resultStatus
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

}
