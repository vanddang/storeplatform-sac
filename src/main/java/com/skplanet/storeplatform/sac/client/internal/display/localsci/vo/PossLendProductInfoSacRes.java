package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

/**
 * PossLendProductInfoSacRes Value Object
 * 
 * 소장/대여 상품 정보 조회 VO
 * 
 * Updated on : 2014. 4. 15. Updated by : 홍지호, 엔텔스
 */
public class PossLendProductInfoSacRes {
	List<PossLendProductInfo> possLendProductInfoList;

	/**
	 * @return the possLendProductInfoList
	 */
	public List<PossLendProductInfo> getPossLendProductInfoList() {
		return this.possLendProductInfoList;
	}

	/**
	 * @param possLendProductInfoList
	 *            the possLendProductInfoList to set
	 */
	public void setPossLendProductInfoList(List<PossLendProductInfo> possLendProductInfoList) {
		this.possLendProductInfoList = possLendProductInfoList;
	}

}
