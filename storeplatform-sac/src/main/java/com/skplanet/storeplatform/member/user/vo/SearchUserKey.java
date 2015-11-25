/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.user.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;

/**
 * KeySearchList를 이용한 사용자 키값 조회 Value Object
 * 
 * Updated on : 2014. 01. 15. Updated by : wisestone_dinga
 */
public class SearchUserKey extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** Tenant ID. */
	private String tenantID;

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

}
