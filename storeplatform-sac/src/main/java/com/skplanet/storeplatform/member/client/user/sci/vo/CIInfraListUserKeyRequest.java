package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * [REQUEST] CI Infra 회원키 리스트 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public class CIInfraListUserKeyRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * 조회구분(신규, 정보변경, 탈퇴).
	 */
	private String searchType;

	/**
	 * 조회일자(YYYYMMDD).
	 */
	private String searchDay;

	/**
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

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
