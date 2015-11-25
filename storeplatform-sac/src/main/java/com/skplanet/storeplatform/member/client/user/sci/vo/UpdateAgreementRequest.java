/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;

/**
 * 사용자 약관동의 등록/수정 요청 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : 황정택, 와이즈스톤.
 */
public class UpdateAgreementRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 사용자 Key. */
	private String userKey;

	/**
	 * 휴면계정유무.
	 */
	private String isDormant;

	/** 약관동의 항목 Value Object List. */
	private List<MbrClauseAgree> mbrClauseAgreeList;

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
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 설정한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 사용자 약관동의 항목 Value Object List를 리턴한다.
	 * 
	 * @return mbrClauseAgreeList - 사용자 약관동의 항목 Value Object List
	 */
	public List<MbrClauseAgree> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	/**
	 * 사용자 약관동의 항목 Value Object List를 설정한다.
	 * 
	 * @param mbrClauseAgreeList
	 *            사용자 약관동의 항목 Value Object List
	 */
	public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
		this.mbrClauseAgreeList = mbrClauseAgreeList;
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
