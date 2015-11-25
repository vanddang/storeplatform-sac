package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * CmpxBasicInfoSacRes Value Object
 * 
 * 이용권 기본정보 조회 시 필요한 상품 메타 정보 조회 VO.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식, 지티소프트
 * 
 **/

public class CmpxBasicInfoSacRes extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<CmpxBasicInfo> cmpxBasicInfo;

	/**
	 * @return the cmpxBasicInfo
	 */
	public List<CmpxBasicInfo> getCmpxBasicInfo() {
		return cmpxBasicInfo;
	}

	/**
	 * @param cmpxBasicInfo the cmpxBasicInfo to set
	 */
	public void setCmpxBasicInfo(List<CmpxBasicInfo> cmpxBasicInfo) {
		this.cmpxBasicInfo = cmpxBasicInfo;
	}

	

}
