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

// TODO: Auto-generated Javadoc
/**
 * 서브계정 목록 조회 요청 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class SearchSubSellerListRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 서브 계정 부모 판매자키. (필수) */
	private String parentSellerKey;

	/** 로그인시간 정렬 . (ASC/DESC) */
	private String loginSort;

	/**
	 * 로그인시간 정렬 리턴한다.
	 * 
	 * @return loginSort - 로그인시간 정렬 Value Object
	 */
	public String getLoginSort() {
		return this.loginSort;
	}

	/**
	 * 로그인시간 정렬 설정한다.
	 * 
	 * @param loginSort
	 *            로그인시간 정렬 Value Object
	 */
	public void setLoginSort(String loginSort) {
		this.loginSort = loginSort;
	}

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
	 * 서브 판매자 부모 키를 리턴한다.
	 * 
	 * @return parentSellerKey - 서브 판매자 부모 키
	 */
	public String getParentSellerKey() {
		return this.parentSellerKey;
	}

	/**
	 * 서브 판매자 부모 키를 설정한다.
	 * 
	 * @param parentSellerKey
	 *            서브 판매자 부모 키
	 */
	public void setParentSellerKey(String parentSellerKey) {
		this.parentSellerKey = parentSellerKey;
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
