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
 * 검색 조건 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class KeySearch extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * 검색 조건 타입 <br>
	 * INSD_USERMBR_NO : 내부 사용자 키 <br>
	 * MBR_ID : 사용자 ID <br>
	 * INSD_SELLERMBR_NO : 내부 판매자 키 <br>
	 * SELLERMBR_ID : 판매자 ID <br>
	 * INTG_SVC_NO : 통합서비스 키 <br>
	 * USERMBR_NO : IDP 사용자 키 <br>
	 * INSD_DEVICE_ID : 내부 기기 키 <br>
	 * DEVICE_ID : 기기 ID <br>
	 * EMAIL_ADDR : 사용자 이메일 <br>
	 * EMAIL : 판매자 이메일 <br>
	 * TEL_NO : 사용자 연락처 <br>
	 * WILS_TEL_NO : 판매자 연락처.
	 */
	private String keyType;

	/** 검색 조건 값. */
	private String keyString;

	/**
	 * 검색 조건 타입을 리턴한다.
	 * 
	 * @return keyType - 검색 조건 타입
	 */
	public String getKeyType() {
		return this.keyType;
	}

	/**
	 * 검색 조건 타입을 설정한다.
	 * 
	 * @param keyType
	 *            검색 조건 타입
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	/**
	 * 검색 조건 값을 리턴한다.
	 * 
	 * @return keyString - 검색 조건 값
	 */
	public String getKeyString() {
		return this.keyString;
	}

	/**
	 * 검색 조건 값을 설정한다.
	 * 
	 * @param keyString
	 *            검색 조건 값
	 */
	public void setKeyString(String keyString) {
		this.keyString = keyString;
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
