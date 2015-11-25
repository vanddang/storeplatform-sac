package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;

/**
 * 조건별 사용자 정보 조회 [REQUEST].
 * 
 * Updated on : 2014. 7. 7. Updated by : Rejoice, Burkhan
 */
public class SearchExtentUserRequest extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * <pre>
	 * 검색조건 Value Object List
	 * INSD_USERMBR_NO : 내부 사용자 키
	 * MBR_ID : 사용자 ID
	 * USERMBR_NO : 통합서비스 키
	 * INTG_SVC_NO : 통합서비스 관리번호
	 * INSD_DEVICE_ID : Device 내부키
	 * DEVICE_ID : Device 고유ID.
	 * </pre>
	 */
	private List<KeySearch> keySearchList;

	/** 사용자 정보. */
	private String userInfoYn;

	/** 약관동의정보. */
	private String agreementInfoYn;

	/** 실명인증정보. */
	private String mbrAuthInfoYn;

	/** 법정대리인정보. */
	private String mbrLglAgentInfoYn;

	/** 사용자 징계 정보. */
	private String mbrPnshInfoYn;

	/** 사용자 등급 정보. */
	private String gradeInfoYn;

	/**
	 * 휴면계정조회유무.
	 */
	private String isDormant;

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
	 * 검색 조건을 리턴한다.
	 * 
	 * @return keySearch - 검색 조건 Value Object
	 */
	public List<KeySearch> getKeySearchList() {
		return this.keySearchList;
	}

	/**
	 * 검색조건을 설정한다.
	 * 
	 * @param keySearchList
	 *            the new key search list
	 */
	public void setKeySearchList(List<KeySearch> keySearchList) {
		this.keySearchList = keySearchList;
	}

	/**
	 * 사용자정보 조회 조건을 리턴한다.
	 * 
	 * @return the userInfoYn
	 */
	public String getUserInfoYn() {
		return this.userInfoYn;
	}

	/**
	 * 사용자정보 조회 조건을 설정한다.
	 * 
	 * @param userInfoYn
	 *            the userInfoYn to set
	 */
	public void setUserInfoYn(String userInfoYn) {
		this.userInfoYn = userInfoYn;
	}

	/**
	 * 약관정보 조회 조건을 리턴한다.
	 * 
	 * @return the agreementInfoYn
	 */
	public String getAgreementInfoYn() {
		return this.agreementInfoYn;
	}

	/**
	 * 약관정보 조회 조건을 설정한다.
	 * 
	 * @param agreementInfoYn
	 *            the agreementInfoYn to set
	 */
	public void setAgreementInfoYn(String agreementInfoYn) {
		this.agreementInfoYn = agreementInfoYn;
	}

	/**
	 * 실명인증정보 조회 조건을 리턴한다.
	 * 
	 * @return the mbrAuthInfoYn
	 */
	public String getMbrAuthInfoYn() {
		return this.mbrAuthInfoYn;
	}

	/**
	 * 실명인증정보 조회 조건을 설정한다.
	 * 
	 * @param mbrAuthInfoYn
	 *            the mbrAuthInfoYn to set
	 */
	public void setMbrAuthInfoYn(String mbrAuthInfoYn) {
		this.mbrAuthInfoYn = mbrAuthInfoYn;
	}

	/**
	 * 법정대리인정보 조회 조건을 리턴한다.
	 * 
	 * @return the mbrLglAgentInfoYn
	 */
	public String getMbrLglAgentInfoYn() {
		return this.mbrLglAgentInfoYn;
	}

	/**
	 * 법정대리인정보 조회 조건을 설정한다.
	 * 
	 * @param mbrLglAgentInfoYn
	 *            the mbrLglAgentInfoYn to set
	 */
	public void setMbrLglAgentInfoYn(String mbrLglAgentInfoYn) {
		this.mbrLglAgentInfoYn = mbrLglAgentInfoYn;
	}

	/**
	 * 징계정보 조회 조건을 리턴한다.
	 * 
	 * @return the mbrPnshInfoYn
	 */
	public String getMbrPnshInfoYn() {
		return this.mbrPnshInfoYn;
	}

	/**
	 * 징계정보 조회 조건을 설정한다.
	 * 
	 * @param mbrPnshInfoYn
	 *            the mbrPnshInfoYn to set
	 */
	public void setMbrPnshInfoYn(String mbrPnshInfoYn) {
		this.mbrPnshInfoYn = mbrPnshInfoYn;
	}

	/**
	 * 사용자 등급정보 조회 조건을 리턴한다.
	 * 
	 * @return the gradeInfoYn
	 */
	public String getGradeInfoYn() {
		return this.gradeInfoYn;
	}

	/**
	 * 사용자 등급정보 조회 조건을 설정한다.
	 * 
	 * @param gradeInfoYn
	 *            the gradeInfoYn to set
	 */
	public void setGradeInfoYn(String gradeInfoYn) {
		this.gradeInfoYn = gradeInfoYn;
	}

	/**
	 * @return isDormant
	 */
	public String getIsDormant() {
		return this.isDormant;
	}

	/**
	 * @param isDormant
	 *            String
	 */
	public void setIsDormant(String isDormant) {
		this.isDormant = isDormant;
	}

}
