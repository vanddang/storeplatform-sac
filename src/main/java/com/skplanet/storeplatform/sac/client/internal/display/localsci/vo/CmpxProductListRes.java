package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

/**
 * 이용권에 등록된 상품 정보 조회 CmpxProductListRes VO.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식 , 지티소프트
 */
public class CmpxProductListRes {
	private static final long serialVersionUID = 1L;

	List<CmpxProductInfoList> CmpxProductInfoList;

	/**
	 * @return the cmpxProductInfoList
	 */
	public List<CmpxProductInfoList> getCmpxProductInfoList() {
		return CmpxProductInfoList;
	}

	/**
	 * @param cmpxProductInfoList the cmpxProductInfoList to set
	 */
	public void setCmpxProductInfoList(List<CmpxProductInfoList> cmpxProductInfoList) {
		CmpxProductInfoList = cmpxProductInfoList;
	}

	

}
