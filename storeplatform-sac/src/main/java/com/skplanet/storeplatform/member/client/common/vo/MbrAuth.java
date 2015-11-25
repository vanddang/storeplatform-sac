/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.common.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * 실명인증 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class MbrAuth extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 실명인증 여부 (Y/N). */
	private String isRealName;

	/** Sequence number. */
	private String sequence;

	/** CI. */
	private String ci; // CI

	/** DI. */
	private String di; // DI

	/** 인증방법코드. */
	private String realNameMethod; // AUTH_MTD_CD 인증방법코드

	/** 통신사 코드. */
	private String telecom; // MNO_CD 통신사 코드

	/** 무선 전화번호. */
	private String phone; // WILS_TEL_NO 무선 전화번호

	/** 생년월일. */
	private String birthDay; // BIRTH 생년월일 DB 에 없음

	/** 성별. */
	private String sex; // SEX

	/** 회원명. */
	private String name; // MBR_NM 회원명

	/** 인증 요청 채널 코드. */
	private String realNameSite; // SYSTEM_ID 실명인증사이트 코드(인증 요청 채널 코드)

	/** 인증일시.ex (YYYYMMDDHH24MISS) ("20130122162853") 2013-01-22 16:28:53. */
	private String realNameDate; // AUTH_DT 인증일시

	/** 수정일시. */
	private String updateDate; // UPD_DT 수정일시

	/**
	 * 회원구분코드.<br>
	 * 사용자:'US010801'<br>
	 * 판매자:'US010802'<br>
	 */
	private String memberCategory; // MBR_CLSF_CD 회원구분코드 (user:'US010801' , seller:'US010802')

	/** Tenant ID. */
	private String tenantID; // TENANT_ID 테넌트 아이디

	/** 내부 회원 키. */
	private String memberKey; // INSD_SELLERMBR_NO

	/** 내국인 여부. */
	private String isDomestic; // 내국인인지여부

	/**
	 * 내국인 여부를 리턴한다.
	 * 
	 * @return isDomestic - 내국인 여부
	 */
	public String getIsDomestic() {
		return this.isDomestic;
	}

	/**
	 * 내국인 여부를 설정한다.
	 * 
	 * @param isDomestic
	 *            내국인 여부
	 */
	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}

	/**
	 * 실명인증 여부 (Y/N)를 리턴한다.
	 * 
	 * @return isRealName - 실명인증 여부 (Y/N)
	 */
	public String getIsRealName() {
		return this.isRealName;
	}

	/**
	 * 실명인증 여부 (Y/N)를 설정한다.
	 * 
	 * @param isRealName
	 *            실명인증 여부 (Y/N)
	 */
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	/**
	 * Sequence number를 리턴한다.
	 * 
	 * @return sequence - Sequence number
	 */
	public String getSequence() {
		return this.sequence;
	}

	/**
	 * Sequence number를 설정한다.
	 * 
	 * @param sequence
	 *            Sequence number
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * 인증방법코드를 리턴한다.
	 * 
	 * @return realNameMethod - 인증방법코드
	 */
	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	/**
	 * 인증방법코드를 설정한다.
	 * 
	 * @param realNameMethod
	 *            인증방법코드
	 */
	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	/**
	 * 인증 요청 채널 코드를 리턴한다.
	 * 
	 * @return realNameSite - 인증 요청 채널 코드
	 */
	public String getRealNameSite() {
		return this.realNameSite;
	}

	/**
	 * 인증 요청 채널 코드를 설정한다.
	 * 
	 * @param realNameSite
	 *            인증 요청 채널 코드
	 */
	public void setRealNameSite(String realNameSite) {
		this.realNameSite = realNameSite;
	}

	/**
	 * 인증일시를 리턴한다.
	 * 
	 * @return realNameDate - 인증일시
	 */
	public String getRealNameDate() {
		return this.realNameDate;
	}

	/**
	 * 인증일시를 설정한다.
	 * 
	 * @param realNameDate
	 *            인증일시
	 */
	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	/**
	 * 수정일시를 리턴한다.
	 * 
	 * @return updateDate - 수정일시
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 수정일시를 설정한다.
	 * 
	 * @param updateDate
	 *            수정일시
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 회원구분코드를 리턴한다.
	 * 
	 * @return memberCategory - 회원구분코드
	 */
	public String getMemberCategory() {
		return this.memberCategory;
	}

	/**
	 * 회원구분코드를 설정한다.
	 * 
	 * @param memberCategory
	 *            회원구분코드
	 */
	public void setMemberCategory(String memberCategory) {
		this.memberCategory = memberCategory;
	}

	/**
	 * Tenant ID를 리턴한다.
	 * 
	 * @return tenantID - Tenant ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * Tenant ID를 설정한다.
	 * 
	 * @param tenantID
	 *            Tenant ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * CI를 리턴한다.
	 * 
	 * @return ci - CI
	 */
	public String getCi() {
		return this.ci;
	}

	/**
	 * CI를 설정한다.
	 * 
	 * @param ci
	 *            CI
	 */
	public void setCi(String ci) {
		this.ci = ci;
	}

	/**
	 * DI를 리턴한다.
	 * 
	 * @return di - DI
	 */
	public String getDi() {
		return this.di;
	}

	/**
	 * DI를 설정한다.
	 * 
	 * @param di
	 *            DI
	 */
	public void setDi(String di) {
		this.di = di;
	}

	/**
	 * 통신사 코드를 리턴한다.
	 * 
	 * @return telecom - 통신사 코드
	 */
	public String getTelecom() {
		return this.telecom;
	}

	/**
	 * 통신사 코드를 설정한다.
	 * 
	 * @param telecom
	 *            통신사 코드
	 */
	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}

	/**
	 * 무선 전화번호를 리턴한다.
	 * 
	 * @return phone - 무선 전화번호
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 무선 전화번호를 설정한다.
	 * 
	 * @param phone
	 *            무선 전화번호
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 성별을 리턴한다.
	 * 
	 * @return sex - 성별
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * 성별을 설정한다.
	 * 
	 * @param sex
	 *            성별
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 회원명을 리턴한다.
	 * 
	 * @return name - 회원명
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 회원명을 설정한다.
	 * 
	 * @param name
	 *            회원명
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 내부 회원 키를 리턴한다.
	 * 
	 * @return memberKey - 내부 회원 키
	 */
	public String getMemberKey() {
		return this.memberKey;
	}

	/**
	 * 내부 회원 키를 설정한다.
	 * 
	 * @param memberKey
	 *            내부 회원 키
	 */
	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
	}

	/**
	 * 생년월일을 리턴한다.
	 * 
	 * @return birthDay - 생년월일
	 */
	public String getBirthDay() {
		return this.birthDay;
	}

	/**
	 * 생년월일을 설정한다.
	 * 
	 * @param birthDay
	 *            생년월일
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

}
