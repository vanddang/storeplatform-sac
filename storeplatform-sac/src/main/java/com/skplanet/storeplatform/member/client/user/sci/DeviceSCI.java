/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ReviveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ReviveUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAllDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAllDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOwnerRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOwnerResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchOrderDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchOrderDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateDeviceManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateDeviceManagementResponse;

/**
 * 휴대기기 기능을 제공하는 Interface
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@SCI
public interface DeviceSCI {

	/**
	 * <pre>
	 * 사용자의 등록된 휴대기기 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceListRequest
	 *            휴대기기 목록 조회 요청 Value Object
	 * @return searchDeviceListResponse - 휴대기기 목록 조회 응답 Value Object
	 */
	public SearchDeviceListResponse searchDeviceList(SearchDeviceListRequest searchDeviceListRequest);

	/**
	 * <pre>
	 * 휴대기기를 등록 또는 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createDeviceRequest
	 *            휴대기기 등록 요청 Value Object
	 * @return createDeviceResponse - 휴대기기 등록 응답 Value Object
	 */
	public CreateDeviceResponse createDevice(CreateDeviceRequest createDeviceRequest);

	/**
	 * <pre>
	 * 등록된 휴대기기를 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeDeviceRequest
	 *            휴대기기 삭제 요청 Value Object
	 * @return removeDeviceResponse - 휴대기기 삭제 응답 Value Object
	 */
	public RemoveDeviceResponse removeDevice(RemoveDeviceRequest removeDeviceRequest);

	/**
	 * <pre>
	 * 회원의 등록된 휴대기기 상세정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceRequest
	 *            휴대기기 조회 요청 Value Object
	 * @return searchDeviceResponse - 휴대기기 조회 응답 Value Object
	 */
	public SearchDeviceResponse searchDevice(SearchDeviceRequest searchDeviceRequest);

	/**
	 * <pre>
	 * 회원의 등록된 휴대기기중 한대를 대표단말로 설정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param setMainDeviceRequest
	 *            휴대기기 대표단말 설정 요청 Value Object
	 * @return setMainDeviceResponse - 휴대기기 대표단말 설정 응답 Value Object
	 */
	public SetMainDeviceResponse setMainDevice(SetMainDeviceRequest setMainDeviceRequest);

	/**
	 * <pre>
	 * 변동성 대상 체크.
	 * </pre>
	 * 
	 * @param checkSaveNSyncRequest
	 *            변동성 대상 체크 요청 Value Object
	 * @return CheckSaveNSyncResponse - 변동성 대상 체크 응답 Value Object
	 */
	public CheckSaveNSyncResponse checkSaveNSync(CheckSaveNSyncRequest checkSaveNSyncRequest);

	/**
	 * <pre>
	 * 회원 복구.
	 * </pre>
	 * 
	 * @param reviveUserRequest
	 *            회원 복구 요청 Value Object
	 * @return ReviveUserResponse - 회원 복구 응답 Value Object
	 */
	public ReviveUserResponse reviveUser(ReviveUserRequest reviveUserRequest);

	/**
	 * <pre>
	 * 회원의 이전 휴대기기 상세정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAllDeviceRequest
	 *            이전 휴대기기 조회 요청 Value Object
	 * @return searchOldDeviceResponse - 이전 휴대기기 조회 응답 Value Object
	 */
	public SearchAllDeviceResponse searchAllDevice(SearchAllDeviceRequest searchAllDeviceRequest);

	/**
	 * <pre>
	 * 휴대기기의 소유자이력 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceOwnerRequest
	 *            휴대기기 소유자이력 조회 요청 Value Object
	 * @return searchDeviceOwnerListResponse - 휴대기기 소유자이력 조회 응답 Value Object
	 */
	public SearchDeviceOwnerResponse searchDeviceOwner(SearchDeviceOwnerRequest searchDeviceOwnerRequest);

	/**
	 * <pre>
	 * 등록된 단말 정보 조회 기능을 제공한다(탈퇴 포함).
	 * </pre>
	 * 
	 * @param searchOrderDeviceRequest
	 *            - 등록된 단말 정보 조회 요청 Value Object
	 * @return SearchOrderDeviceResponse - 등록된 단말 정보 조회 응답 Value Object
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
	 * 단말 정보 수정 기능을 제공한다.
	 * </pre>
	 *
	 * @param modifyDeviceRequest
	 *            - 단말 수정 요청 Value Object
	 * @return ModifyDeviceResponse - 단말 수정 응답 Value Object
	 */
	public ModifyDeviceResponse modifyDevice(ModifyDeviceRequest modifyDeviceRequest);

}
