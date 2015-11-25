package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.ArrayList;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * [RESPONSE] CI Infra 회원키 리스트 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public class CIInfraListUserKeyResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/**
	 * 회원키 리스트.
	 */
	private ArrayList<CIInfraUserInfo> ciInfraUserInfoList;

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
	 * 회원키 리스트 리턴.
	 * </pre>
	 * 
	 * @return ciInfraUserInfoList
	 */
	public ArrayList<CIInfraUserInfo> getCiInfraUserInfoList() {
		return this.ciInfraUserInfoList;
	}

	/**
	 * <pre>
	 * 회원키 리스트 셋팅.
	 * </pre>
	 * 
	 * @param ciInfraUserInfoList
	 *            CIInfraUserInfoList
	 */
	public void setCiInfraUserInfoList(ArrayList<CIInfraUserInfo> ciInfraUserInfoList) {
		this.ciInfraUserInfoList = ciInfraUserInfoList;
	}
}
