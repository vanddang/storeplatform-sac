/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * 사용자회원 비밀번호 변경 요청 Value Object
 * 
 * Updated on : 2016. 1. 14. Updated by : 최진호, 보고지티.
 */
public class UpdatePasswordUserRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 비밀번호 타입 (IDP:US011502, OneId:US011503) */
	private String userPwType;

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

	/**
	 * 사용자 비밀번호 정보 Value Object를 리턴한다.
	 * 
	 * @return mbrPwd - 사용자 비밀번호 정보 Value Object
	 */
	public MbrPwd getMbrPwd() {
		return this.mbrPwd;
	}

	/**
	 * 사용자 비밀번호 정보 Value Object를 설정한다.
	 * 
	 * @param mbrPwd
	 *            사용자 비밀번호 정보 Value Object
	 */
	public void setMbrPwd(MbrPwd mbrPwd) {
		this.mbrPwd = mbrPwd;
	}

	/** 사용자 비밀번호 정보 Value Object. */
	private MbrPwd mbrPwd;

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
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 비밀번호 타입 (IDP:US011502, OneId:US011503) 를 리턴한다.
	 *
	 * @return userPwType - 사용자 salt값 (One Id 유저인경우)
	 */
	public String getUserPwType() {
		return userPwType;
	}

	/**
	 * 비밀번호 타입 (IDP:US011502, OneId:US011503)를 설정한다.
	 *
	 * @param userPwType
	 *            사용자 salt값 (One Id 유저인경우)
	 */
	public void setUserPwType(String userPwType) { this.userPwType = userPwType; }

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.framework.core.common.vo.CommonInfo#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
