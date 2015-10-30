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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 휴대기기 소유자이력 정보 응답 Value Object
 * 
 * Updated on : 2014. 6. 24 Updated by : wisestone_mikepark.
 */
public class SearchDeviceOwnerResponse extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/** 휴대기기 Key. */
	private String deviceKey;

	/** 사용자 Key. */
	private String userKey;

	/** 인증 날짜. */
	private String authenticationDate;

	/** 휴대기기 사용 여부. */
	private String isUsed; // USE_YN : Y/N

	/** 테이블이름. */
	private String tableName;

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key를 리턴한다.
	 * 
	 * @param userKey
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 휴대기기 Key를 리턴한다.
	 * 
	 * @return deviceKey - 휴대기기 Key
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * 휴대기기 Key를 리턴한다.
	 * 
	 * @param deviceKey
	 *            휴대기기 Key
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * 휴대기기 인증일을 리턴한다.
	 * 
	 * @return authenticationDate - 휴대기기 인증일자
	 */
	public String getAuthenticationDate() {
		return this.authenticationDate;
	}

	/**
	 * 휴대기기 인증일을 설정한다.
	 * 
	 * @param authenticationDate
	 *            휴대기기 인증일자
	 */
	public void setAuthenticationDate(String authenticationDate) {
		this.authenticationDate = authenticationDate;
	}

	/**
	 * 휴대기기 사용여부(Y/N)를 리턴한다.
	 * 
	 * @return isUsed - 휴대기기 사용여부(Y/N)
	 */
	public String getIsUsed() {
		return this.isUsed;
	}

	/**
	 * 휴대기기 사용여부(Y/N)를 설정한다.
	 * 
	 * @param isUsed
	 *            휴대기기 사용여부(Y/N)
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * 테이블이름를 리턴한다.
	 * 
	 * @return tableName - 테이블이름
	 */
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * 테이블이름를 설정한다.
	 * 
	 * @param tableName
	 *            테이블이름
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
