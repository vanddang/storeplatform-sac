/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.common.code;

/**
 * 기기변경 유형코드 정의
 * 
 * Created on : 2014. 2. 12 Created by : wisestone_dinga
 */
public enum DeviceChangeCode {
	/** 사용자선택. */
	STATUS_CD_USER,
	/** 기기변경. */
	STATUS_CD_DEV,
	/** 번호변경. */
	STATUS_CD_NUM, ;

	/** 정의된 코드 아이디. */
	private final String codeID;

	/** 정의된 그룹코드 아이디. */
	private final String groupID = GroupCode.DEVICE_CHANGE.getCode();

	/** 정의된 코드 순번. */
	private final int index;

	/**
	 * Instantiates a new device management code.
	 * 
	 */
	DeviceChangeCode() {
		this.index = this.ordinal() + 1;
		this.codeID = this.groupID + String.format("%02d", this.index);
	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return this.codeID;
	}

}
