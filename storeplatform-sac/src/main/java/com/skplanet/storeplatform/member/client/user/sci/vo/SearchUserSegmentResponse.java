package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * Class 설명
 * 
 * Updated on : 2014. 10. 2. Updated by : Rejoice, Burkhan
 */
public class SearchUserSegmentResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;
	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 회원 키. */
	private String userKey;
	/** 회원 등급 코드. */
	private String userGradeCd;
	/** 회원 성별. */
	private String userSex;
	/** 회원 생년월일. */
	private String userBirthDay;
	/** 등록일자. */
	private String entryDay;

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
	 * 회원 키 정보를 리턴한다.
	 * 
	 * @return userKey - 회원키
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 회원 키 정보를 설정한다.
	 * 
	 * @param userKey
	 *            회원 키
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 회원등급 코드 정보를 리턴한다.
	 * 
	 * @return userGradeCd - 회원 등급 코드
	 */
	public String getUserGradeCd() {
		return this.userGradeCd;
	}

	/**
	 * 회원등급 코드 정보를 설정한다.
	 * 
	 * @param userGradeCd
	 *            회원등급 코드
	 */
	public void setUserGradeCd(String userGradeCd) {
		this.userGradeCd = userGradeCd;
	}

	/**
	 * 회원 성별 정보를 리턴한다.
	 * 
	 * @return userSex - 회원 성별
	 */
	public String getUserSex() {
		return this.userSex;
	}

	/**
	 * 회원 성별 정보를 설정한다.
	 * 
	 * @param userSex
	 *            회원 성별
	 */
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	/**
	 * 회원 생년월일을 리턴한다.
	 * 
	 * @return userBirthDay - 회원 생년월일
	 */
	public String getUserBirthDay() {
		return this.userBirthDay;
	}

	/**
	 * 회원 생년월일을 설정한다.
	 * 
	 * @param userBirthDay
	 *            회원 생년월일
	 */
	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	/**
	 * 등록일자 정보를 리턴한다.
	 * 
	 * @return entryDay - 등록일자
	 */
	public String getEntryDay() {
		return this.entryDay;
	}

	/**
	 * 등록일자 정보를 설정한다.
	 * 
	 * @param entryDay
	 *            등록일자
	 */
	public void setEntryDay(String entryDay) {
		this.entryDay = entryDay;
	}

}
