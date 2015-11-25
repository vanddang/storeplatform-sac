/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;

/**
 * 판매자 실명인증 정보 수정 요청 Value Object.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class UpdateRealNameSellerRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자 키. */
	private String sellerKey;

	/** 인증 여부 Y/N. isOwn=OWN 이면 실명인증여부 값이 되고, isOwn=parent 이면 법정대리인 여부 값으로 저장됨 */
	private String isRealName;

	/** 실명인증 대상 본인 : OWN 법정대리인 : PARENT. */
	private String isOwn;

	/** 법정대리인 Value Object. */
	private MbrLglAgent mbrLglAgent;

	/** 실명인증 Value Object. */
	private MbrAuth mbrAuth;

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
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 키
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * 판매자 키를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자 키
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * 실명인증 Value Object를 리턴한다.
	 * 
	 * @return mbrAuth - 실명인증 Value Object
	 */
	public MbrAuth getMbrAuth() {
		return this.mbrAuth;
	}

	/**
	 * 실명인증 Value Object를 설정한다.
	 * 
	 * @param mbrAuth
	 *            실명인증 Value Object
	 */
	public void setMbrAuth(MbrAuth mbrAuth) {
		this.mbrAuth = mbrAuth;
	}

	/**
	 * 실명인증 대상을 리턴한다.
	 * 
	 * @return isOwn - 실명인증 대상
	 */
	public String getIsOwn() {
		return this.isOwn;
	}

	/**
	 * 실명인증 대상을 설정한다.
	 * 
	 * @param isOwn
	 *            실명인증 대상
	 */
	public void setIsOwn(String isOwn) {
		this.isOwn = isOwn;
	}

	/**
	 * 법정대리인 Value Object를 리턴한다.
	 * 
	 * @return mbrLglAgent - 법정대리인 Value Object
	 */
	public MbrLglAgent getMbrLglAgent() {
		return this.mbrLglAgent;
	}

	/**
	 * 법정대리인 Value Object를 설정한다.
	 * 
	 * @param mbrLglAgent
	 *            법정대리인 Value Object
	 */
	public void setMbrLglAgent(MbrLglAgent mbrLglAgent) {
		this.mbrLglAgent = mbrLglAgent;
	}

	/**
	 * 실명인증 여부를 리턴한다.
	 * 
	 * @return isRealName - 실명인증 여부
	 */
	public String getIsRealName() {
		return this.isRealName;
	}

	/**
	 * 실명인증 여부를 설정한다.
	 * 
	 * @param isRealName
	 *            실명인증 여부
	 */
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
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
