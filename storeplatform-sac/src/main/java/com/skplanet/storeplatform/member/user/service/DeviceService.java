/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.user.service;

import com.skplanet.storeplatform.member.client.user.sci.vo.*;

/**
 * 회원 휴대기기 서비스
 * 
 * Updated on : 2013. 11. 29. Updated by : 황정택, 와이즈스톤.
 */
public interface DeviceService {

	/**
	 * <pre>
	 * 사용자의 등록된 휴대기기 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceListRequest
	 *            휴대기기 목록 조회 요청 Value Object
	 * @return SearchDeviceListResponse - 휴대기기 목록 조회 응답 Value Object
	 */
	SearchDeviceListResponse searchDeviceList(SearchDeviceListRequest searchDeviceListRequest);

	/**
	 * <pre>
	 * 휴대기기를 등록 또는 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createDeviceRequest
	 *            휴대기기 등록 요청 Value Object
	 * @return CreateDeviceResponse - 휴대기기 등록 응답 Value Object
	 */
	CreateDeviceResponse createDevice(CreateDeviceRequest createDeviceRequest);

	/**
	 * <pre>
	 * 등록된 휴대기기를 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeDeviceRequest
	 *            휴대기기 삭제 요청 Value Object
	 * @return RemoveDeviceResponse - 휴대기기 삭제 응답 Value Object
	 */
	RemoveDeviceResponse removeDevice(RemoveDeviceRequest removeDeviceRequest);

	/**
	 * <pre>
	 * 회원의 등록된 휴대기기 상세정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceRequest
	 *            휴대기기 조회 요청 Value Object
	 * @return SearchDeviceResponse - 휴대기기 조회 응답 Value Object
	 */
	SearchDeviceResponse searchDevice(SearchDeviceRequest searchDeviceRequest);

	/**
	 * <pre>
	 * 회원의 등록된 휴대기기중 한대를 대표단말로 설정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param setMainDeviceRequest
	 *            휴대기기 대표단말 설정 요청 Value Object
	 * @return SetMainDeviceResponse - 휴대기기 대표단말 설정 응답 Value Object
	 */
	SetMainDeviceResponse updateMainDevice(SetMainDeviceRequest setMainDeviceRequest);

	/**
	 * <pre>
	 * 변동성 대상 체크.
	 * </pre>
	 * 
	 * @param checkSaveNSyncRequest
	 *            변동성 대상 체크 요청 Value Object
	 * @return CheckSaveNSyncResponse - 변동성 대상 체크 응답 Value Object
	 */
	CheckSaveNSyncResponse checkSaveNSync(CheckSaveNSyncRequest checkSaveNSyncRequest);

	/**
	 * <pre>
	 * 회원복구 요청.
	 * </pre>
	 * 
	 * @param reviveUserRequest
	 *            회원복구 요청 Value Object
	 * @return ReviveUserResponse - 회원복구 응답 Value Object
	 */
	public ReviveUserResponse updateReviveUser(ReviveUserRequest reviveUserRequest);

	/**
	 * <pre>
	 * 회원의 이전 휴대기기 상세정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAllDeviceRequest
	 *            이전 휴대기기 조회 요청 Value Object
	 * @return SearchAllDeviceResponse - 이전 휴대기기 조회 응답 Value Object
	 */
	SearchAllDeviceResponse searchAllDevice(SearchAllDeviceRequest searchAllDeviceRequest);

	/**
	 * <pre>
	 * 휴대기기의 소유자이력을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceOwnerRequest
	 *            휴대기기 소유자이력 조회 요청 Value Object
	 * @return searchDeviceOwnerResponse - 휴대기기 소유자이력 조회 응답 Value Object
	 */
	public SearchDeviceOwnerResponse searchDeviceOwner(SearchDeviceOwnerRequest searchDeviceOwnerRequest);

	/**
	 * <pre>
	 * 등록 단말 정보 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchOrderDeviceRequest
	 *            - 등록단말 정보 조회 요청 Value Object
	 * @return SearchOrderDeviceResponse - 등록단말 정보 조회 응답 Value Object
	 */
	public SearchOrderDeviceResponse searchOrderDevice(SearchOrderDeviceRequest searchOrderDeviceRequest);

	/**
	 * <pre>
	 * 단말 부가속성 정보 등록/수정 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateDeviceManagementRequest
	 *            - 단말 부가속성 정보 등록/수정 요청 Value Object
	 * @return UpdateDeviceManagementResponse - 단말 부가속성 정보 등록/수정 응답 Value Object
	 */
	public UpdateDeviceManagementResponse updateDeviceManagement(
			UpdateDeviceManagementRequest updateDeviceManagementRequest);

	/**
	 * <pre>
	 * 단말 수정 기능을 제공한다.
	 * </pre>
	 *
	 * @param modifyDeviceRequest
	 *            - 단말 수정 요청 Value Object
	 * @return ModifyDeviceResponse - 단말 수정 응답 Value Object
	 */
	public ModifyDeviceResponse modifyDevice(ModifyDeviceRequest modifyDeviceRequest);

	/**
	 * <pre>
	 * Save & Sync 인증시 단말 수정 기능을 제공한다.
	 * </pre>
	 *
	 * @param modifyDeviceRequest
	 *            - 단말 수정 요청 Value Object
	 * @return ModifyDeviceResponse - 단말 수정 응답 Value Object
	 */
	public ModifyDeviceResponse modifySaveNSyncDevice(ModifyDeviceRequest modifyDeviceRequest);

}
