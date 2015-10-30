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
 * 기기 관리항목 코드 정의 Class
 * 
 * Created on : 2014. 1. 2 Created by : wisestone_brian, wisestone
 */
public enum DeviceManagementCode {

	/** 기기 관리항목 - OMP DOWNLOADER 설치 여부. */
	DOWNLOADER_YN,
	/** 기기 관리항목 - 대기화면 설정 여부. */
	STANDBY_SCREEN_YN,
	/** 기기 관리항목 - UA 코드. */
	UA_CODE,
	/** 기기 관리항목 - OMP 지원 단말 여부. */
	OMP_YN,
	/** 기기 관리항목 - OS 버전. */
	OS_VERSION,
	/** 기기 관리항목 - 샵클 버전. */
	SC_VERSION,
	/** 기기 관리항목 - 앱 사용통계 사용여부. */
	STATS_YN,
	/** 기기 관리항목 - 도토리 인증일. */
	DOTORI_DT,
	/** 기기 관리항목 - 도토리 인증여부. */
	DOTORI_YN,
	/** 기기 관리항목 - 임베디드 여부. */
	EMBEDDED_YN,
	/** 회원서브상태 - OMD UA코드. */
	OMD_UA_CODE,
	/** 기기 관리항목 - 루팅 여부. */
	ROOTING_YN,
	/** 기기 관리항목 - T Cloud 지원. */
	TCLOUD_YN,

	;

	/** 정의된 코드 아이디. */
	private final String codeID;

	/** 정의된 그룹코드 아이디. */
	private final String groupID = GroupCode.DEVICE_MANAGEMENT.getCode();

	/** 정의된 코드 순번. */
	private final int index;

	/**
	 * Instantiates a new device management code.
	 * 
	 */
	DeviceManagementCode() {
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
