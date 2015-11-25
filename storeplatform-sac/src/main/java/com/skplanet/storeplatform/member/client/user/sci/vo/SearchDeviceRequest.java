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

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;

/**
 * 사용자 휴대기기 개별 조회 요청 Value Object
 * 
 * Updated on : 2014. 1. 6 Updated by : wisestone_brian, wisestone
 */
public class SearchDeviceRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * 사용자 key값.
	 */
	private String userKey;

	/**
	 * <pre>
	 * 검색조건 Value Object List.
	 * INSD_DEVICE_ID : Device 내부키
	 * DEVICE_ID : Device 고유ID.
	 * SVC_MANG_NO : 서비스 관리 번호
	 * </pre>
	 */
	private List<KeySearch> keySearchList;

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

}
