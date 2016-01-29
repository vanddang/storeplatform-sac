package com.skplanet.storeplatform.sac.client.member.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 각 통신사 주요 정보
 * 
 * Updated on : 2016. 1. 28. Updated by : 반범진.
 */
public class DeviceTelecomInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 각 통신사 서비스관리번호
	 */
	private String svcMangNum;

	/**
	 * @return String : svcMangNum
	 */
	public String getSvcMangNum() {
		return this.svcMangNum;
	}

	/**
	 * @param svcMangNum
	 *            String : the svcMangNum to set
	 */
	public void setSvcMangNum(String svcMangNum) {
		this.svcMangNum = svcMangNum;
	}

}
