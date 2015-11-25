package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] CI Infra 회원키 리스트 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public class CIInfraListUserKeySacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 조회구분(신규, 정보변경, 탈퇴).
	 */
	private String searchType;

	/**
	 * 조회일자(YYYYMMDD).
	 */
	private String searchDay;

	/**
	 * <pre>
	 * 조회구분 리턴.
	 * </pre>
	 * 
	 * @return searchType
	 */
	public String getSearchType() {
		return this.searchType;
	}

	/**
	 * <pre>
	 * 조회구분 셋팅.
	 * </pre>
	 * 
	 * @param searchType
	 *            String
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * <pre>
	 * 조회일자 리턴.
	 * </pre>
	 * 
	 * @return searchDay
	 */
	public String getSearchDay() {
		return this.searchDay;
	}

	/**
	 * <pre>
	 * 조회일자 셋팅.
	 * </pre>
	 * 
	 * @param searchDay
	 *            String
	 */
	public void setSearchDay(String searchDay) {
		this.searchDay = searchDay;
	}

}
