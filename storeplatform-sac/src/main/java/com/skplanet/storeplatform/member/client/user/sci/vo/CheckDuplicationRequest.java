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
 * 사용자 존재여부 확인 요청 Value Object.
 * 
 * Updated on : 2014. 1. 2 Updated by : wisestone_brian, wisestone
 */
public class CheckDuplicationRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * <pre>
	 * 검색조건 Value Object List.
	 * 
	 * INSD_USERMBR_NO : 내부 사용자 키
	 * MBR_ID : 사용자 ID
	 * USERMBR_NO : 통합서비스 키
	 * INTG_SVC_NO : 통합서비스 관리번호
	 * INSD_DEVICE_ID : Device 내부키
	 * DEVICE_ID : Device 고유ID
	 * EMAIL_ADDR : 회원 EMAIL
	 * </pre>
	 */
	private List<KeySearch> keySearchList;

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
	 *            검색 조건 Value Object
	 */
	public void setKeySearchList(List<KeySearch> keySearchList) {
		this.keySearchList = keySearchList;
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
