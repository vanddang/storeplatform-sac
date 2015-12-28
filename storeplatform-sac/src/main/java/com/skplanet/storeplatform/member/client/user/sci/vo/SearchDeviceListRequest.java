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
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;

/**
 * 사용자 휴대기기 목록조회 요청 Value Object
 * 
 * Updated on : 2014. 1. 6 Updated by : wisestone_brian, wisestone
 */
public class SearchDeviceListRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * <pre>
	 * 검색조건 Value Object List.
	 * INSD_USERMBR_NO : 내부 사용자 키
	 * MBR_ID : 사용자 ID
	 * USERMBR_NO : 통합서비스 키
	 * INTG_SVC_NO : 통합서비스 관리번호
	 * INSD_DEVICE_ID : Device 내부키
	 * DEVICE_ID : Device 고유ID
	 * </pre>
	 */
	private List<KeySearch> keySearchList;

	/** 대표기기여부. */
	private String isMainDevice;

	/** 사용자 Key. */
	private String userKey; // 내부 사용자 id

	/** 휴대기기 auth_yn 여부. */
	private String isUsed;
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
	 * 대표기기여부를 리턴한다.
	 * 
	 * @return isMainDevice - 대표기기여부
	 */
	public String getIsMainDevice() {
		return this.isMainDevice;
	}

	/**
	 * 대표기기여부를 설정한다.
	 * 
	 * @param isMainDevice
	 *            대표기기여부
	 */
	public void setIsMainDevice(String isMainDevice) {
		this.isMainDevice = isMainDevice;
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
	 * 휴대기기 auth_yn 여부를 리턴한다.
	 *
	 * @return isUsed
	 */
	public String getIsUsed() {
		return isUsed;
	}

	/**
	 * 휴대기기 auth_yn 여부를 설정한다.
	 *
	 * @param isUsed
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
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
