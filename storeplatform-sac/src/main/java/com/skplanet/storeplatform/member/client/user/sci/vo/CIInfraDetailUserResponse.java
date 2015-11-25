package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * [RESPONSE] CI Infra 회원 상세 정보 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public class CIInfraDetailUserResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/**
	 * Tstore가 관리하는 회원 Identity Key.
	 */
	private String userKey;

	/** CI Infra 제공용 회원 기본정보 Value Object. */
	private CIInfraUserInfo ciInfraUserInfo;

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * <pre>
	 * Tstore가 관리하는 회원 Identity Key 리턴.
	 * </pre>
	 * 
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * <pre>
	 * Tstore가 관리하는 회원 Identity Key 셋팅.
	 * </pre>
	 * 
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * <pre>
	 *  CI Infra 제공용 회원 기본정보 Object 리턴.
	 * </pre>
	 * 
	 * @return ciInfraUserInfo
	 */
	public CIInfraUserInfo getCiInfraUserInfo() {
		return this.ciInfraUserInfo;
	}

	/**
	 * <pre>
	 * CI Infra 제공용 회원 기본정보 Object 셋팅.
	 * </pre>
	 * 
	 * @param ciInfraUserInfo
	 *            CIInfraUserInfo
	 */
	public void setCiInfraUserInfo(CIInfraUserInfo ciInfraUserInfo) {
		this.ciInfraUserInfo = ciInfraUserInfo;
	}
}
