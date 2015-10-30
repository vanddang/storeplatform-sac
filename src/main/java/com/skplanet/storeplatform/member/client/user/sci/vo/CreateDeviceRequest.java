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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 사용자 휴대기기기 등록/수정 요청 Value Object
 * 
 * Updated on : 2014. 1. 3 Updated by : wisestone_brian, wisestone
 */
public class CreateDeviceRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

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

	/** 등록여부. */
	private String isNew;

	/** 사용자 Key. */
	private String userKey;

	/** 사용자 휴대기기 Value Object. */
	private UserMbrDevice userMbrDevice;

	/**
	 * 사용자 휴대기기 Value Object를 리턴한다.
	 * 
	 * @return userMbrDevice - 사용자 휴대기기 Value Object
	 */
	public UserMbrDevice getUserMbrDevice() {
		return this.userMbrDevice;
	}

	/**
	 * 사용자 휴대기기 Value Object를 설정한다.
	 * 
	 * @param userMbrDevice
	 *            사용자 휴대기기 Value Object
	 */
	public void setUserMbrDevice(UserMbrDevice userMbrDevice) {
		this.userMbrDevice = userMbrDevice;
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

	/**
	 * 휴대기기 등록 여부(Y/N)를 리턴한다.
	 * 
	 * @return isNew - 휴대기기 등록 여부(Y/N)
	 */
	public String getIsNew() {
		return this.isNew;
	}

	/**
	 * 휴대기기 등록 여부를 설정한다. Example : Y/N
	 * 
	 * @param isNew
	 *            휴대기기 등록 여부(Y/N)
	 */
	public void setIsNew(String isNew) {
		this.isNew = isNew;
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
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
