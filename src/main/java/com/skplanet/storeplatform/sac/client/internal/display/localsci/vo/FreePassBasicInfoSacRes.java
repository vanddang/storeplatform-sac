package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * FreePassBasicInfoSacRes Value Object
 * 
 * 정액권 구매 내역 조회 시 필요한 상품 메타 정보 조회 VO.
 * 
 * Updated on : 2014. 6. 09. Updated by : 김형식, 지티소프트
 * 
 **/

public class FreePassBasicInfoSacRes extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<FreePassBasicInfo> freePassBasicInfo;

	/**
	 * @return the freePassBasicInfo
	 */
	public List<FreePassBasicInfo> getFreePassBasicInfo() {
		return this.freePassBasicInfo;
	}

	/**
	 * @param freePassBasicInfo
	 *            the freePassBasicInfo to set
	 */
	public void setFreePassBasicInfo(List<FreePassBasicInfo> freePassBasicInfo) {
		this.freePassBasicInfo = freePassBasicInfo;
	}

}
