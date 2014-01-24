package com.skplanet.storeplatform.sac.client.member.vo.seller;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 5.2.23. 판매자 서브계정 ID 중복 체크 [REQUEST]
 * 
 * Updated on : 2014. 1. 23. Updated by : 한서구, 부르칸.
 */
public class DuplicateBySubsellerIdReq extends CommonInfo {

	/**
	 * Default SerialVersion.
	 */
	private static final long serialVersionUID = 1L;

	private String keyString;

	public String getKeyString() {
		return this.keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
}
