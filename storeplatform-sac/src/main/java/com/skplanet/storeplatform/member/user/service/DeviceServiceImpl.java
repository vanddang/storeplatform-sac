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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceSet;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserkeyTrack;
import com.skplanet.storeplatform.member.common.code.DeviceChangeCode;
import com.skplanet.storeplatform.member.common.code.DeviceManagementCode;
import com.skplanet.storeplatform.member.common.code.MainStateCode;
import com.skplanet.storeplatform.member.common.code.SubStateCode;
import com.skplanet.storeplatform.member.common.code.UserTypeCode;
import com.skplanet.storeplatform.member.common.code.WithdrawClassCode;

/**
 * 회원 휴대기기 서비스 Implementation
 * 
 * Updated on : 2013. 11. 29. Updated by : 황정택, 와이즈스톤.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	/*
	 * Logger 는 반드시 "private static final" 로 선언한다.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);

	/*
	 * 데이터 베이스에서 데이터를 조회할 별도 DAO 객체를 생성하지 않고, CommonDAO 를 사용한다.
	 */
	@Autowired
	@Qualifier("scMember")
	private CommonDAO commonDAO;

	/** idle dao. */
	@Autowired
	@Qualifier("scIdleMember")
	private CommonDAO idleDAO;

	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	@Autowired
	private UserService userService;

	/**
	 * <pre>
	 * 사용자의 등록된 휴대기기 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceListRequest
	 *            휴대기기 목록 조회 요청 Value Object
	 * @return SearchDeviceListResponse - 휴대기기 목록 조회 응답 Value Object
	 */
	@Override
	public SearchDeviceListResponse searchDeviceList(SearchDeviceListRequest searchDeviceListRequest) {

		LOGGER.debug("### searchDeviceListRequest : {}", searchDeviceListRequest.toString());
		CommonDAO dao = this.commonDAO;
		UserMbr userMbr = null;
		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID(searchDeviceListRequest.getCommonRequest().getTenantID());

		boolean isDeviceRequest = false;
		List<KeySearch> keySearchList = searchDeviceListRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {
			if (keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_KEY)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_ID)) {
				isDeviceRequest = true;
			}
		}

		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			userMbr = dao.queryForObject("Device.searchDeviceListD", searchDeviceListRequest, UserMbr.class);
		} else {
			userMbr = dao.queryForObject("Device.searchDeviceList", searchDeviceListRequest, UserMbr.class);
		}

		// 휴면DB 조회
		if (userMbr == null) {
			dao = this.idleDAO;
			if (isDeviceRequest) {
				userMbr = dao.queryForObject("Device.searchDeviceListD", searchDeviceListRequest, UserMbr.class);
			} else {
				userMbr = dao.queryForObject("Device.searchDeviceList", searchDeviceListRequest, UserMbr.class);
			}
		}

		if (userMbr == null || userMbr.getUserID() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		commonRequest.setSystemID(userMbr.getUserID());
		searchDeviceListRequest.setCommonRequest(commonRequest);

		SearchDeviceListResponse searchDeviceListResponse;

		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			searchDeviceListResponse = dao.queryForObject("Device.searchDeviceList2D", searchDeviceListRequest,
					SearchDeviceListResponse.class);
		} else {
			searchDeviceListResponse = dao.queryForObject("Device.searchDeviceList2", searchDeviceListRequest,
					SearchDeviceListResponse.class);
		}

		if (searchDeviceListResponse.getUserMbrDevice() == null) {
			return null;
		}
		return searchDeviceListResponse;

	}

	/**
	 * <pre>
	 * 휴대기기를 등록 또는 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createDeviceRequest
	 *            the 휴대기기 등록 요청 Value Object
	 * @return createDeviceResponse - the 휴대기기 등록 응답 Value Object
	 */
	@Override
	public CreateDeviceResponse createDevice(CreateDeviceRequest createDeviceRequest) {

		CreateDeviceResponse createDeviceResponse = new CreateDeviceResponse();
		String userKey = createDeviceRequest.getUserKey();
		String deviceKey = null; // 휴대기기 신규등록시 생성되는 deviceKey
		String previousUserKey = null; // 아이디 전환되는 모바일 회원의 userKey
		String previousDeviceKey = null; // 아이디 전환되는 모바일 회원의 deviceKey
		String preUserKey = null; // 이전사용자 휴대기기 설정정보가 존재하는경우 이관처리를 위한 userKey
		String preDeviceKey = null; // 이전사용자 휴대기기 설정정보가 존재하는경우 이관처리를 위한 deviceKey
		SearchDeviceResponse searchDeviceResponse = null;

		/** 타인 정보 처리 start */
		if (StringUtils.isNotBlank(createDeviceRequest.getUserMbrDevice().getDeviceID())) {

			// device_id 존재 체크
			searchDeviceResponse = this.doSearchDevice(Constant.SEARCH_TYPE_DEVICE_ID, createDeviceRequest
					.getUserMbrDevice().getDeviceID());

			if (searchDeviceResponse != null && !StringUtils.equals(userKey, searchDeviceResponse.getUserKey())) {

				// device_id로 다른회원이 존재하면 auth_yn = 'N' 처리
				UserMbr preUserMbr = new UserMbr();
				preUserMbr.setUserKey(searchDeviceResponse.getUserKey());
				preUserMbr = this.commonDAO.queryForObject("User.getUserDetail", preUserMbr, UserMbr.class);

				// 휴대기기 설정정보 이관처리를 위한 회원키 조회
				UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
				userMbrDeviceSet.setUserKey(searchDeviceResponse.getUserKey());
				userMbrDeviceSet.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());
				UserMbrDeviceSet searchUserMbrDeviceSet = this.commonDAO.queryForObject(
						"DeviceSet.searchDeviceSetInfo", userMbrDeviceSet, UserMbrDeviceSet.class);

				if (searchUserMbrDeviceSet != null) {
					preUserKey = searchUserMbrDeviceSet.getUserKey();
					preDeviceKey = searchUserMbrDeviceSet.getDeviceKey();
				}

				if (Constant.USER_TYPE_MOBILE.equals(preUserMbr.getUserType())) {// 모바일 회원

					// 탈퇴처리
					int tempKey = this.doRemoveUser(preUserMbr, Constant.TYPE_YN_N);
					if (tempKey < 1)
						throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound",
								""));
					previousUserKey = searchDeviceResponse.getUserKey();
					previousDeviceKey = searchDeviceResponse.getUserMbrDevice().getDeviceKey();

				} else { // 아이디 회원

					// 휴대기기 invalid 처리
					int tempKey = this.doDeactivateDevice(searchDeviceResponse.getUserMbrDevice(), Constant.TYPE_YN_N);
					if (tempKey < 1)
						throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound",
								""));
				}
			}

			// MDN 존재 체크
			searchDeviceResponse = this.doSearchDevice(Constant.SEARCH_TYPE_MDN, createDeviceRequest.getUserMbrDevice()
					.getMdn());

			if (searchDeviceResponse != null && !StringUtils.equals(userKey, searchDeviceResponse.getUserKey())) {
				// 다른 회원이 존재하는 경우 mdn, imei, sim 널 처리
				this.commonDAO.update("Device.insertUpdateDeviceHistory", searchDeviceResponse.getUserMbrDevice());
				UserMbrDevice updateMbrDevice = new UserMbrDevice();
				updateMbrDevice.setUserKey(searchDeviceResponse.getUserKey());
				updateMbrDevice.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());
				updateMbrDevice.setMdn("");
				updateMbrDevice.setDeviceSimMn("");
				updateMbrDevice.setNativeID("");
				this.commonDAO.update("Device.updateDevice", updateMbrDevice);
			}

		}

		// 서비스 관리 번호 체크
		searchDeviceResponse = this.doSearchDevice(Constant.SEARCH_TYPE_SVC_MANG_NO, createDeviceRequest
				.getUserMbrDevice().getSvcMangNum());

		if (searchDeviceResponse != null) {

			UserMbr preUserMbr = new UserMbr();
			preUserMbr.setUserKey(searchDeviceResponse.getUserKey());
			preUserMbr = this.commonDAO.queryForObject("User.getUserDetail", preUserMbr, UserMbr.class);

			if (StringUtils.isBlank(createDeviceRequest.getUserMbrDevice().getDeviceID())) { // device_id가 없는 경우 auth_yn

				// 휴대기기 설정정보 이관처리를 위한 회원키 조회
				UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
				userMbrDeviceSet.setUserKey(searchDeviceResponse.getUserKey());
				userMbrDeviceSet.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());
				UserMbrDeviceSet searchUserMbrDeviceSet = this.commonDAO.queryForObject(
						"DeviceSet.searchDeviceSetInfo", userMbrDeviceSet, UserMbrDeviceSet.class);

				if (searchUserMbrDeviceSet != null) {
					preUserKey = searchUserMbrDeviceSet.getUserKey();
					preDeviceKey = searchUserMbrDeviceSet.getDeviceKey();
				}

				if (Constant.USER_TYPE_MOBILE.equals(preUserMbr.getUserType())) {// 모바일 회원

					// 탈퇴처리
					int tempKey = this.doRemoveUser(preUserMbr, Constant.TYPE_YN_N);
					if (tempKey < 1)
						throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound",
								""));

					previousUserKey = searchDeviceResponse.getUserKey();
					previousDeviceKey = searchDeviceResponse.getUserMbrDevice().getDeviceKey();

				} else { // 아이디 회원

					// 휴대기기 invalid 처리
					int tempKey = this.doDeactivateDevice(searchDeviceResponse.getUserMbrDevice(), Constant.TYPE_YN_N);
					if (tempKey < 1)
						throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound",
								""));
				}
			} else {
				// device_id가 존재하고 ID 회원인 경우 svc_no, mno_cd 널 처리
				if (!Constant.USER_TYPE_MOBILE.equals(preUserMbr.getUserType())) {
					this.commonDAO.update("Device.insertUpdateDeviceHistory", searchDeviceResponse.getUserMbrDevice());
					UserMbrDevice updateMbrDevice = new UserMbrDevice();
					updateMbrDevice.setUserKey(searchDeviceResponse.getUserKey());
					updateMbrDevice.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());
					updateMbrDevice.setSvcMangNum("");
					updateMbrDevice.setDeviceTelecom("");
					this.commonDAO.update("Device.updateDevice", updateMbrDevice);
				}
			}
		}
		/** 타인 정보 처리 end */

		/** 본인 정보 처리 start */
		// device_id 존재 체크
		searchDeviceResponse = this.doSearchDevice(Constant.SEARCH_TYPE_DEVICE_ID, createDeviceRequest
				.getUserMbrDevice().getDeviceID());

		if (searchDeviceResponse == null) {

			// svc_mang_no 조회
			searchDeviceResponse = this.doSearchDevice(Constant.SEARCH_TYPE_SVC_MANG_NO, createDeviceRequest
					.getUserMbrDevice().getSvcMangNum());

			if (searchDeviceResponse == null) {

				UserMbr userMbr = new UserMbr();
				userMbr.setUserKey(userKey);
				userMbr = this.commonDAO.queryForObject("User.getUserDetail", userMbr, UserMbr.class);

				// 신규단말정보insert
				if (StringUtils.equals(userMbr.getIsDormant(), Constant.TYPE_YN_Y)) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.sleepUserError", ""));
				}

				// 대표기기 등록요청인 경우 기존 등록된 대표기기 N처리
				if (createDeviceRequest.getUserMbrDevice().getIsPrimary().equals(Constant.TYPE_YN_Y)) {
					int tempKey = this.doCancelMainDevice(userMbr, Constant.TYPE_YN_N);

					if (tempKey < 1)
						throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound",
								""));
				}

				deviceKey = this.doInsertDevice(createDeviceRequest, Constant.TYPE_YN_N);
				if (deviceKey == null)
					throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
			} else {
				// req device_id 가 널이 아니고 db의 device_id와 다른 경우 update
				// svc_no 기준으로 다른 정보들을 req 정보로 update
				// 단말 정보 update
				// TODO. 요기가 초기화 되는 구간인가
				if (!StringUtils.equals(createDeviceRequest.getUserMbrDevice().getDeviceID(), searchDeviceResponse
						.getUserMbrDevice().getSvcMangNum())) {
					UserMbrDevice updateMbrDevice = new UserMbrDevice();
					updateMbrDevice.setUserKey(userKey);
					updateMbrDevice.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());
					updateMbrDevice.setMdn(createDeviceRequest.getUserMbrDevice().getMdn());
					updateMbrDevice.setDeviceSimMn(createDeviceRequest.getUserMbrDevice().getDeviceSimMn());
					updateMbrDevice.setNativeID(createDeviceRequest.getUserMbrDevice().getNativeID());
					updateMbrDevice.setDeviceTelecom(createDeviceRequest.getUserMbrDevice().getDeviceTelecom());
					updateMbrDevice.setSvcMangNum(createDeviceRequest.getUserMbrDevice().getSvcMangNum());
					this.commonDAO.update("Device.updateDevice", updateMbrDevice);
					// TODO. 추가 업데이트 할 데이터 확인 필요
				}
			}
		} else if (StringUtils.equals(userKey, searchDeviceResponse.getUserKey())) {
			// 단말 정보 update
			UserMbrDevice updateMbrDevice = new UserMbrDevice();
			updateMbrDevice.setUserKey(userKey);
			updateMbrDevice.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());
			updateMbrDevice.setMdn(createDeviceRequest.getUserMbrDevice().getMdn());
			updateMbrDevice.setDeviceSimMn(createDeviceRequest.getUserMbrDevice().getDeviceSimMn());
			updateMbrDevice.setNativeID(createDeviceRequest.getUserMbrDevice().getNativeID());
			updateMbrDevice.setDeviceTelecom(createDeviceRequest.getUserMbrDevice().getDeviceTelecom());
			updateMbrDevice.setSvcMangNum(createDeviceRequest.getUserMbrDevice().getSvcMangNum());
			this.commonDAO.update("Device.updateDevice", updateMbrDevice);
			// TODO. 추가 업데이트 할 데이터 확인 필요
		}
		/** 본인 정보 처리 end */

		createDeviceResponse.setUserKey(userKey);
		createDeviceResponse.setDeviceKey(deviceKey);
		createDeviceResponse.setPreviousUserKey(previousUserKey);
		createDeviceResponse.setPreviousDeviceKey(previousDeviceKey);
		createDeviceResponse.setPreUserKey(preUserKey);
		createDeviceResponse.setPreDeviceKey(preDeviceKey);
		createDeviceResponse.setPreMbrNo(null);

		return createDeviceResponse;
	}

	/**
	 * <pre>
	 * 등록된 휴대기기를 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeDeviceRequest
	 *            the 휴대기기 삭제 요청 Value Object
	 * @return removeDeviceResponse - the 휴대기기 삭제 응답 Value Object
	 */
	@Override
	public RemoveDeviceResponse removeDevice(RemoveDeviceRequest removeDeviceRequest) {

		// TO DO.
		// ACTION 1. 단말 존재여부 확인 : tenantID, deviceKey, userKey로 단말 조회
		// ACTION 2. 휴대기기 이력 테이블 insert.
		// ACTION 3. 휴대기기 속성의 USE_YN, AUTH_YN 을 N 으로 update.
		// ACTION 4. 휴대기기 부가정보 삭제
		// ACTION 5. 휴대기기 삭제 카운트 증가
		// ACTION 6-1. 회원 이력 테이블 insert.
		// ACTION 6-2. 휴대기기 보유 카운트 감소
		// ACTION 6-3. 휴대기기 설정정보 초기화

		LOGGER.debug(">>>> >>> DeviceServiceImpl removeDevice : {}", removeDeviceRequest);
		String isDormant = StringUtils.isBlank(removeDeviceRequest.getIsDormant()) ? Constant.TYPE_YN_N : removeDeviceRequest
				.getIsDormant();
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}

		RemoveDeviceResponse removeDeviceResponse = new RemoveDeviceResponse();
		removeDeviceResponse.setDelDeviceCount(0);
		Integer row = 0;
		String tenantID = removeDeviceRequest.getCommonRequest().getTenantID();
		String userKey = removeDeviceRequest.getUserKey();
		String lastDeviceKey = "";
		List<String> deviceKeyList = removeDeviceRequest.getDeviceKey();
		int sizeList = deviceKeyList.size();

		// ACTION 1. 단말 존재여부 확인 : tenantID, deviceKey, userKey로 단말 조회
		for (int i = 0; i < sizeList; i++) {
			UserMbrDevice userMbrDevice = new UserMbrDevice();
			userMbrDevice.setUserKey(userKey);
			userMbrDevice.setDeviceKey(deviceKeyList.get(i));
			String isRegistered = null;

			isRegistered = dao.queryForObject("Device.isRegisteredKey", userMbrDevice, String.class);
			LOGGER.debug("### removeDeviceIMPL isRegisteredKey : {}", isRegistered);

			if (isRegistered == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.deviceKeyNotFound", ""));
			}
		}

		for (int i = 0; i < deviceKeyList.size(); i++) {

			UserMbrDevice userMbrDevice = new UserMbrDevice();
			userMbrDevice.setUserKey(userKey);
			userMbrDevice.setDeviceKey(deviceKeyList.get(i));
			userMbrDevice.setIsUsed(Constant.TYPE_YN_N);

			// ACTION 2. 휴대기기 이력 테이블 insert.
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				row = dao.update("Device.insertUpdateDeviceHistory", userMbrDevice); // ok
				if (row <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
				}
				LOGGER.debug("### insertUpdateDeviceHistory row : {}", row);
			}

			// ACTION 3. 휴대기기 속성의 USE_YN, AUTH_YN 을 N 으로 update.
			row = dao.update("Device.removeDevice", userMbrDevice);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
			}
			LOGGER.debug("### removeDevice row : {}", row);

			// ACTION 4. 휴대기기 부가속성정보 삭제
			UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setUserKey(userKey);
			userMbrDeviceDetail.setDeviceKey(deviceKeyList.get(i));
			// userMbrDeviceDetail.setExtraProfile(DeviceManagementCode.DOTORI_YN.getCode()); // 도토리 인증여부 CD_ID
			// userMbrDeviceDetail.setExtraProfileValue(Constant.TYPE_YN_N);

			// row = dao.update("Device.removeDotori", userMbrDeviceDetail);
			dao.delete("removeDeviceExtraProfile", userMbrDeviceDetail);
			// 예외처리 삭제 : 토토리 관리항목이 없는 경우가 있음.
			// if (row <= 0) {
			// throw new Exception("Unknown Error.");
			// }
			LOGGER.debug("### removeDotori row : {}", row);

			// ACTION 5. 휴대기기 삭제 카운트 증가
			int delDeviceCount = removeDeviceResponse.getDelDeviceCount();
			delDeviceCount = delDeviceCount + 1;
			removeDeviceResponse.setDelDeviceCount(delDeviceCount);

			// ACTION 6-1. 회원 이력 테이블 insert.
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				UserMbr usermbr = new UserMbr();
				usermbr.setUserKey(removeDeviceRequest.getUserKey());
				usermbr.setTenantID(removeDeviceRequest.getCommonRequest().getTenantID());
				row = dao.update("User.insertUpdateStatusHistory", usermbr);
				LOGGER.debug("### insertUpdateStatusHistory row : {}", row);
				if (row <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
				}
			}

			// ACTION 6-2. 휴대기기 보유 카운트 감소
			row = dao.update("Device.updateCountDevice2", userMbrDevice);
			LOGGER.debug("### Device.updateCountMinus row : {}", row);
			lastDeviceKey = userMbrDevice.getDeviceKey();

			// ACTION 6-3. 휴대기기 설정정보 초기화
			UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
			userMbrDeviceSet.setTenantID(tenantID);
			userMbrDeviceSet.setUserKey(userKey);
			userMbrDeviceSet.setDeviceKey(deviceKeyList.get(i));
			userMbrDeviceSet.setPinNo("");
			userMbrDeviceSet.setAuthCnt("0");
			userMbrDeviceSet.setAuthLockYn("N");
			this.commonDAO.delete("DeviceSet.modifyDeviceSet", userMbrDeviceSet);
		}

		// TLog
		final String insdusermbrno = userKey;
		final String insddeviceid = lastDeviceKey;

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.insd_usermbr_no(insdusermbrno);
				shuttle.insd_device_id(insddeviceid);
			}
		});

		removeDeviceResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return removeDeviceResponse;
	}

	/**
	 * <pre>
	 * 회원의 등록된 휴대기기 상세정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceRequest
	 *            휴대기기 조회 요청 Value Object
	 * @return SearchDeviceResponse - 휴대기기 조회 응답 Value Object
	 */
	@Override
	public SearchDeviceResponse searchDevice(SearchDeviceRequest searchDeviceRequest) {

		LOGGER.debug("### searchDeviceRequest : {}", searchDeviceRequest.toString());

		SearchDeviceResponse searchDeviceResponse;
		CommonDAO dao = this.commonDAO;
		String isDormant = Constant.TYPE_YN_N;

		// deviceKey 또는 deviceID로 조회한 경우
		if (searchDeviceRequest.getUserKey() != null) {
			searchDeviceResponse = dao.queryForObject("Device.searchDevice", searchDeviceRequest,
					SearchDeviceResponse.class);

			if (searchDeviceResponse == null) { // 휴면DB 조회
				dao = this.idleDAO;
				searchDeviceResponse = dao.queryForObject("Device.searchDevice", searchDeviceRequest,
						SearchDeviceResponse.class);
				isDormant = Constant.TYPE_YN_Y;
			}
		} else {
			// 검색 조건에 서비스 관리번호가 포함되었는지 여부
			List<KeySearch> keySearchList = searchDeviceRequest.getKeySearchList();
			boolean isSvcMangNoExist = false;
			for (KeySearch keySearch : keySearchList) {
				if (keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_SVC_MANG_NO)) {
					isSvcMangNoExist = true;
				}
			}

			if (isSvcMangNoExist) {
				// svc_mang_no로 조회한 경우
				searchDeviceResponse = dao.queryForObject("Device.searchDeviceSvcMangNo", searchDeviceRequest,
						SearchDeviceResponse.class);

				if (searchDeviceResponse == null) { // 휴면DB 조회
					dao = this.idleDAO;
					searchDeviceResponse = dao.queryForObject("Device.searchDeviceSvcMangNo", searchDeviceRequest,
							SearchDeviceResponse.class);
					isDormant = Constant.TYPE_YN_Y;
				}
			} else {
				// device id, no userkey로 조회한 경우
				searchDeviceResponse = dao.queryForObject("Device.searchDeviceIDOnly", searchDeviceRequest,
						SearchDeviceResponse.class);

				if (searchDeviceResponse == null) { // 휴면DB 조회
					dao = this.idleDAO;
					searchDeviceResponse = dao.queryForObject("Device.searchDeviceIDOnly", searchDeviceRequest,
							SearchDeviceResponse.class);
					isDormant = Constant.TYPE_YN_Y;
				}
			}
		}

		if (searchDeviceResponse == null || searchDeviceResponse.getUserMbrDevice() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchDeviceResponse.getUserMbrDevice().setIsDormant(isDormant);

		LOGGER.debug("### searchDeviceResponse : {}", searchDeviceResponse.toString());
		searchDeviceResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchDeviceResponse;
	}

	/**
	 * <pre>
	 * 휴대기기 대표단말 설정.
	 * 
	 * @param setMainDeviceRequest
	 *            휴대기기 대표단말 설정 요청 Value Object
	 * @return SetMainDeviceResponse - 휴대기기 대표단말 설정 응답 Value Object
	 * </pre>
	 */
	@Override
	public SetMainDeviceResponse updateMainDevice(SetMainDeviceRequest setMainDeviceRequest) {

		// TO DO.
		// ACTION 1. 해당 휴대기기의 존재여부 확인 : tenantID, deviceKey, userKey로 단말 조회
		// ACTION 2. 모든 휴대기기의 대표단말 해지
		// ACTION 2-1. 휴대기기 전체목록 조회
		// ACTION 2-1-1. 휴대기기 이력 테이블 insert.
		// ACTION 2-1-2. 휴대기기 속성의 REP_DEVICE_YN = N
		// ACTION 3. 해당 휴대기기의 대표단말 설정
		// ACTION 3-1. 휴대기기 이력 테이블 insert.
		// ACTION 3-2. 휴대기기 속성의 REP_DEVICE_YN = Y

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("디바이스 서비스 - 휴대기기 대표단말 설정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updateMainDevice : {}", setMainDeviceRequest.toString());

		SetMainDeviceResponse setMainDeviceResponse = new SetMainDeviceResponse();

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setUserKey(setMainDeviceRequest.getUserKey());
		userMbrDevice.setDeviceKey(setMainDeviceRequest.getDeviceKey());
		String isRegistered = null;

		// ACTION 1. 해당 휴대기기의 존재여부 확인 : tenantID, deviceKey, userKey로 단말 조회
		isRegistered = this.commonDAO.queryForObject("Device.isRegisteredKey", userMbrDevice, String.class);
		LOGGER.debug("### removeDeviceIMPL isRegisteredKey : {}", isRegistered);

		if (isRegistered == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.deviceKeyNotFound", ""));
		}

		// ACTION 2. 모든 휴대기기의 대표단말 해지
		// ACTION 2-1. 휴대기기 전체목록 조회

		SearchDeviceListRequest searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(setMainDeviceRequest.getCommonRequest());
		searchDeviceListRequest.setIsMainDevice(Constant.TYPE_YN_N);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		keySearch.setKeyString(setMainDeviceRequest.getUserKey());
		keySearchList.add(keySearch);
		searchDeviceListRequest.setKeySearchList(keySearchList);

		LOGGER.debug("### searchDeviceListRequest : {}", searchDeviceListRequest.toString());

		UserMbr userMbr = null;
		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID(searchDeviceListRequest.getCommonRequest().getTenantID());

		boolean isDeviceRequest = false;
		List<KeySearch> keySearchList2 = searchDeviceListRequest.getKeySearchList();
		for (KeySearch keySearch2 : keySearchList2) {
			if (keySearch2.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_KEY)
					|| keySearch2.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_ID)) {
				isDeviceRequest = true;
			}
		}

		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			userMbr = this.commonDAO.queryForObject("Device.searchDeviceListD", searchDeviceListRequest, UserMbr.class);
		} else {
			userMbr = this.commonDAO.queryForObject("Device.searchDeviceList", searchDeviceListRequest, UserMbr.class);
		}

		if (userMbr == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbr.getUserID() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		commonRequest.setSystemID(userMbr.getUserID());
		searchDeviceListRequest.setCommonRequest(commonRequest);

		SearchDeviceListResponse searchDeviceListResponse = null;

		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			searchDeviceListResponse = this.commonDAO.queryForObject("Device.searchDeviceList2D",
					searchDeviceListRequest, SearchDeviceListResponse.class);
		} else {
			searchDeviceListResponse = this.commonDAO.queryForObject("Device.searchDeviceList2",
					searchDeviceListRequest, SearchDeviceListResponse.class);
		}

		LOGGER.debug("### searchDeviceListResponse : {}", searchDeviceListResponse);

		List<UserMbrDevice> userMbrDeviceList = searchDeviceListResponse.getUserMbrDevice();
		for (UserMbrDevice device : userMbrDeviceList) {
			if (device.getIsPrimary() != null && device.getIsPrimary().equals(Constant.TYPE_YN_Y)) {

				// ACTION 2-1-1. 휴대기기 이력 테이블 insert.
				int row = this.commonDAO.update("Device.insertUpdateDeviceHistory", device); // ok
				if (row <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
				}
				LOGGER.debug("### insertUpdateDeviceHistory row : {}", row);

				// ACTION 2-1-2. 휴대기기 속성의 REP_DEVICE_YN = N
				row = this.commonDAO.update("Device.unsetDeviceYn", device);
				if (row <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
				}
				LOGGER.debug("### Device.unsetDeviceYn row : {}", row);
			}

		}

		// ACTION 3. 해당 휴대기기의 대표단말 설정
		// ACTION 3-1. 휴대기기 이력 테이블 insert.
		UserMbrDevice device = new UserMbrDevice();
		device.setUserKey(setMainDeviceRequest.getUserKey());
		device.setDeviceKey(setMainDeviceRequest.getDeviceKey());
		int row = this.commonDAO.update("Device.insertUpdateDeviceHistory", device); // ok
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
		}
		LOGGER.debug("### insertUpdateDeviceHistory row : {}", row);

		// ACTION 3-2. 휴대기기 속성의 REP_DEVICE_YN = Y
		row = this.commonDAO.update("Device.setMainDevice", setMainDeviceRequest);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
		}
		LOGGER.debug("### setMainDevice row : {}", row);

		// 공통 응답
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
				LocaleContextHolder.getLocale()));
		commonResponse.setResultMessage(this.messageSourceAccessor.getMessage("response.ResultMessage.success", null,
				"성공", LocaleContextHolder.getLocale()));

		// 성공 설정
		setMainDeviceResponse.setCommonResponse(commonResponse);
		setMainDeviceResponse.setUserKey(setMainDeviceRequest.getUserKey());
		setMainDeviceResponse.setDeviceKey(setMainDeviceRequest.getDeviceKey());

		LOGGER.debug("### DB에서 받아온 결과 : {}", setMainDeviceResponse);

		return setMainDeviceResponse;

	}

	/**
	 * <pre>
	 * 사용자의 현재 Main Device 를 off 시키는 기능.
	 * </pre>
	 * 
	 * @param ownerUserMbr
	 *            사용자 Value Object
	 * @param isDormant
	 *            휴면계정유무
	 * @return row - 결과값 1:성공, 0:실패
	 */
	private int doCancelMainDevice(UserMbr ownerUserMbr, String isDormant) {
		// check for isMainDevice
		// 만일 mainDevice 면 나머지 단말 아닌걸로 세팅..
		LOGGER.debug("### doCancelMainDevice : {}", ownerUserMbr);
		int row = 0;
		CommonDAO dao = null;
		UserMbrDevice oldMain = null;

		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}

		oldMain = dao.queryForObject("Device.findMainDevice", ownerUserMbr, UserMbrDevice.class);
		LOGGER.debug("### oldMain : {}", oldMain);

		if (oldMain != null) {
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				// 휴대기기 이력 테이블 insert.
				row = this.commonDAO.update("Device.insertUpdateDeviceHistory", oldMain); // ok
				LOGGER.debug("### insertUpdateDeviceHistory row : {}", row);
			}

			// 휴대기기 속성의 REP_DEVICE_YN = N
			row = dao.update("Device.unsetDeviceYn", oldMain);
			LOGGER.debug("### Device.unsetDeviceYn row : {}", row);
		} else
			return 1;

		return row;
	}

	/**
	 * <pre>
	 * 기기등록, 부가속성 등록, 사용자의 기기카운트++ 시키는 기능.
	 * </pre>
	 * 
	 * @param createDeviceRequest
	 *            사용자 Value Object
	 * @param isDormant
	 *            휴면계정유무
	 * @return tempKey - Device Key
	 */
	private String doInsertDevice(CreateDeviceRequest createDeviceRequest, String isDormant) {
		LOGGER.debug("### doInsertDevice : {}", createDeviceRequest);
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}

		int row = 0;
		int seq = this.commonDAO.queryForObject("Device.getUserSequence", null, Integer.class);
		String tempKey = "DE" + Utils.getLocalDateTime() + Utils.leftPadStringWithChar(Integer.toString(seq), 7, '0');
		LOGGER.debug("### tempKey : {}", tempKey);

		UserMbrDevice userMbrDevice = createDeviceRequest.getUserMbrDevice();
		userMbrDevice.setUserKey(createDeviceRequest.getUserKey());
		userMbrDevice.setDeviceKey(tempKey);
		userMbrDevice.setIsUsed(Constant.TYPE_YN_Y);
		LOGGER.debug("### createDeviceIMPL userMbrDevice : {}", userMbrDevice.toString());
		row = (Integer) dao.insert("Device.createDevice", userMbrDevice);
		LOGGER.debug("### createDevice row : {}", row);
		if (row == 0) {
			return null;
		}

		// device count++
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			UserMbr deviceUsermbr = new UserMbr();
			deviceUsermbr.setTenantID(createDeviceRequest.getCommonRequest().getTenantID());
			deviceUsermbr.setUserKey(createDeviceRequest.getUserKey());
			row = dao.update("User.insertUpdateStatusHistory", deviceUsermbr);
			LOGGER.debug("### insertUpdateStatusHistory row : {}", row);
		}
		row = dao.update("Device.updateCountDevice", createDeviceRequest);
		LOGGER.debug("### createDevice updateCountPlus : {}", row);

		// 2014.09.01. vanddang 휴대기기 부가속성에 userId를 넣기 위해 조회
		UserMbr userMbr = new UserMbr(); // 임시VO
		userMbr.setTenantID(createDeviceRequest.getCommonRequest().getTenantID());
		userMbr.setUserKey(createDeviceRequest.getUserKey());
		UserMbr ownerUserMbr = dao.queryForObject("Device.retrieveUserInfo", userMbr, UserMbr.class);

		// check 부가속성
		if (createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail() != null) {
			List<UserMbrDeviceDetail> userMbrDeviceDetailList = createDeviceRequest.getUserMbrDevice()
					.getUserMbrDeviceDetail();
			LOGGER.debug("### userMbrDeviceDetailList size : {}", userMbrDeviceDetailList.size());
			for (int i = 0; i < userMbrDeviceDetailList.size(); i++) {
				UserMbrDeviceDetail record = userMbrDeviceDetailList.get(i);
				record.setUserKey(createDeviceRequest.getUserKey());
				record.setDeviceKey(tempKey);

				// 2014.09.01. vanddang. 등록ID, 업데이트 ID, 처리채널 저장 추가
				record.setSystemID(createDeviceRequest.getCommonRequest().getSystemID());
				record.setRegID(ownerUserMbr.getUserID());

				try {
					row = dao.update("Device.updateDeviceDetail", record);
				} catch (Exception e) {
					row = 1;
				}

				LOGGER.debug("### updateDeviceDetail : {}", row);
				if (row == 0) {
					return null;
				}
			}
		}

		return tempKey;
	}

	/**
	 * <pre>
	 * 사용자없는 기기속성을 초기화하는 기능.
	 * </pre>
	 * 
	 * @param cancelMbrDevice
	 *            사용자 Value Object
	 * @param isDormant
	 *            휴면계정 유무
	 * @return row - 결과값 1:성공, 0:실패
	 */
	private int doCancelDevice(UserMbrDevice cancelMbrDevice, String isDormant) {
		LOGGER.debug("### doCancelDevice : {}", cancelMbrDevice);
		int row = 0;
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}
		if (cancelMbrDevice != null) {

			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				// 휴대기기 이력 테이블 insert.
				row = dao.update("Device.insertUpdateDeviceHistory", cancelMbrDevice); // ok
				LOGGER.debug("### insertUpdateDeviceHistory row : {}", row);
				if (row <= 0) {
					return row;
				}
			}

			// 휴대기기 속성의 USE_YN = N
			row = dao.update("Device.doCancelDevice", cancelMbrDevice);
			LOGGER.debug("### Device.doCancelDevice row : {}", row);
			if (row <= 0) {
				return row;
			}

			UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setUserKey(cancelMbrDevice.getUserKey());
			userMbrDeviceDetail.setDeviceKey(cancelMbrDevice.getDeviceKey());
			userMbrDeviceDetail.setExtraProfile(DeviceManagementCode.DOTORI_YN.getCode()); // 도토리 인증여부 CD_ID
			userMbrDeviceDetail.setExtraProfileValue(Constant.TYPE_YN_N);

			LOGGER.debug("### userMbrDeviceDetail  : {}", userMbrDeviceDetail);

			row = dao.update("Device.removeDotori", userMbrDeviceDetail);
			if (row <= 0) {
				row = 1;
			}
		}

		return row;
	}

	/**
	 * <pre>
	 * USE_YN을 Y로 초기화하는 기능.
	 * </pre>
	 * 
	 * @param userMbrDevice
	 *            사용자 Value Object
	 * @param createDeviceRequest
	 *            기기 Request Value Object
	 * @param isDormant
	 *            휴면계정유무
	 * @return row - 결과값 1:성공, 0:실패
	 */
	private int doActivateDevice(UserMbrDevice userMbrDevice, CreateDeviceRequest createDeviceRequest, String isDormant) {
		LOGGER.debug("### doActivateDevice : {}", userMbrDevice);
		int row = 0;
		int row1 = 0;
		String tempKey = "";
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}

		if (userMbrDevice != null) {
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				// 휴대기기 이력 테이블 insert.
				row = dao.update("Device.insertUpdateDeviceHistory", userMbrDevice); // ok
				LOGGER.debug("### insertUpdateDeviceHistory row : {}", row);
			}

			// 휴대기기 속성의 REP_DEVICE_YN = N
			row = dao.update("Device.doActivateDevice", userMbrDevice);
			LOGGER.debug("### Device.doActivateDevice row : {}", row);

			tempKey = userMbrDevice.getDeviceKey();
		}

		// 2014.09.01. vanddang 휴대기기 부가속성에 userId를 넣기 위해 조회
		UserMbr userMbr = new UserMbr(); // 임시VO
		userMbr.setTenantID(createDeviceRequest.getCommonRequest().getTenantID());
		userMbr.setUserKey(createDeviceRequest.getUserKey());
		UserMbr ownerUserMbr = dao.queryForObject("Device.retrieveUserInfo", userMbr, UserMbr.class);

		// check 부가속성
		if (createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail() != null) {
			List<UserMbrDeviceDetail> userMbrDeviceDetailList = createDeviceRequest.getUserMbrDevice()
					.getUserMbrDeviceDetail();
			LOGGER.debug("### userMbrDeviceDetailList size : {}", userMbrDeviceDetailList.size());
			for (int i = 0; i < userMbrDeviceDetailList.size(); i++) {
				UserMbrDeviceDetail record = userMbrDeviceDetailList.get(i);
				record.setUserKey(createDeviceRequest.getUserKey());
				record.setDeviceKey(tempKey);

				// 2014.09.01. vanddang. 등록ID, 업데이트 ID, 처리채널 저장 추가
				record.setSystemID(createDeviceRequest.getCommonRequest().getSystemID());
				record.setRegID(ownerUserMbr.getUserID());

				try {
					row1 = dao.update("Device.updateDeviceDetail", record);
				} catch (Exception e) {
					row1 = 1;
				}

				LOGGER.debug("### updateDeviceDetail : {}", row1);
			}
		}

		return row;
	}

	/**
	 * <pre>
	 * USE_YN을 N로 초기화하는 기능.
	 * </pre>
	 * 
	 * @param userMbrDevice
	 *            사용자 Value Object
	 * @param isDormant
	 *            휴면계정 유무
	 * @return row - 결과값 1:성공, 0:실패
	 */
	private int doDeactivateDevice(UserMbrDevice userMbrDevice, String isDormant) {
		LOGGER.debug("### doDeactivateDevice : {}", userMbrDevice);
		int row = 0;
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}
		if (userMbrDevice != null) {
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				// 휴대기기 이력 테이블 insert.
				row = dao.update("Device.insertUpdateDeviceHistory", userMbrDevice); // ok
				LOGGER.debug("### insertUpdateDeviceHistory row : {}", row);
				if (row <= 0) {
					return row;
				}
			}

			// 휴대기기 속성의 REP_DEVICE_YN = N
			LOGGER.debug("### userMbrDevice row ..: {}", userMbrDevice);
			row = dao.update("Device.removeDevice", userMbrDevice);
			LOGGER.debug("### Device.removeDevice row : {}", row);
			if (row <= 0) {
				return row;
			}

			// ACTION 6-2. 휴대기기 보유 카운트 감소
			UserMbr deviceUsermbr = new UserMbr();
			deviceUsermbr.setUserKey(userMbrDevice.getUserKey());
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				row = dao.update("User.insertUpdateStatusHistory", deviceUsermbr);
			}
			LOGGER.debug("### insertUpdateStatusHistory row : {}", row);
			row = dao.update("Device.updateCountDevice2", userMbrDevice);
			LOGGER.debug("### createDevice updateCountMinus : {}", row);

			UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setUserKey(userMbrDevice.getUserKey());
			userMbrDeviceDetail.setDeviceKey(userMbrDevice.getDeviceKey());
			// userMbrDeviceDetail.setExtraProfile(DeviceManagementCode.DOTORI_YN.getCode()); // 도토리 인증여부 CD_ID
			// userMbrDeviceDetail.setExtraProfileValue(Constant.TYPE_YN_N);

			row = dao.delete("removeDeviceExtraProfile", userMbrDeviceDetail);
			LOGGER.debug("### removeDotori row : {}", row);
			if (row <= 0) {
				row = 1;
			}

			// ACTION 6-3. 휴대기기 설정정보 초기화
			UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
			userMbrDeviceSet.setUserKey(userMbrDevice.getUserKey());
			userMbrDeviceSet.setDeviceKey(userMbrDevice.getDeviceKey());
			userMbrDeviceSet.setPinNo("");
			userMbrDeviceSet.setAuthCnt("");
			userMbrDeviceSet.setAuthLockYn("N");
			this.commonDAO.delete("DeviceSet.modifyDeviceSet", userMbrDeviceSet);
		}

		return row;
	}

	/**
	 * <pre>
	 * 무선회원을 탈퇴하는 기능.
	 * </pre>
	 * 
	 * @param userMbr
	 *            사용자 Value Object
	 * @param isDormant
	 *            휴면계정유무
	 * @return row - 결과값 1:성공, 0:실패
	 */
	private int doRemoveUser(UserMbr userMbr, String isDormant) {
		LOGGER.debug("### doRemoveUser : {}", userMbr);
		int row = 0;
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}
		// TO DO.
		// ACTION 2. 회원 이력 테이블 insert.
		// ACTION 3. 회원 속성 탈퇴정보 update.(휴대기기 보유 카운트 초기화)

		// 아래는 배치로 이동
		// - ACTION 4. 회원 개인식별 탈퇴 정보 저장
		// - ACTION 5. 회원 탈퇴 정보 저장
		// - ACTION 6. 휴대기기 전체 목록 삭제

		// ACTION 6-1. 휴대기기 전체 목록 조회
		// ACTION 6-2. 휴대기기 삭제 요청
		// ACTION 6-2-1. 휴대기기 이력 테이블 insert.
		// ACTION 6-2-2. 휴대기기 속성의 USE_YN, AUTH_YN 을 N 으로 update.
		// ACTION 6-2-3. 휴대기기 부가속성의 도토리사용여부를 N 으로 업데이트.(US005610 -> N)

		// 아래는 배치로 이동
		// - ACTION 7. 회원 정보 삭제

		// ACTION 2. 회원 이력 테이블 insert.
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			row = dao.update("User.insertUpdateStatusHistory", userMbr);
			LOGGER.debug("### insertUpdateStatusHistory row : {}", row);
			if (row <= 0) {
				return row;
			}
		}

		LOGGER.debug("### doRemoveUser : 1");
		// ACTION 3. 회원 속성 탈퇴정보 update.(휴대기기 보유 카운트 초기화)
		userMbr.setSecedeTypeCode(WithdrawClassCode.CHANGE_ID.getCode());
		userMbr.setUserMainStatus(MainStateCode.SECEDE.getCode()); // 탈퇴 MAIN CODE
		userMbr.setUserSubStatus(SubStateCode.WITHDRAW.getCode()); // 탈퇴완료 SUB CODE
		userMbr.setSecedeDate(Utils.getLocalDateTimeinYYYYMMDD());
		// old source delete user
		LOGGER.debug("### doRemoveUser : 2");
		// ACTION 6. 휴대기기 전체 목록 삭제
		List<KeySearch> keySearchList;
		KeySearch keySearch;
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		keySearch.setKeyString(userMbr.getUserKey());
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);
		LOGGER.debug("### doRemoveUser : 3");
		SearchDeviceListRequest searchDeviceListRequest;
		searchDeviceListRequest = new SearchDeviceListRequest();
		CommonRequest commonRequest;
		commonRequest = new CommonRequest();
		commonRequest.setTenantID(userMbr.getTenantID());
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice(Constant.TYPE_YN_N);

		// ACTION 6-1. 휴대기기 전체 목록 조회
		UserMbr userMbr2 = null;
		CommonRequest commonRequest2 = new CommonRequest();

		commonRequest2.setTenantID(searchDeviceListRequest.getCommonRequest().getTenantID());
		LOGGER.debug("### doRemoveUser : 4");
		boolean isDeviceRequest = false;
		List<KeySearch> keySearchList2 = searchDeviceListRequest.getKeySearchList();
		for (KeySearch keySearch2 : keySearchList2) {
			if (keySearch2.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_KEY)
					|| keySearch2.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_ID)) {
				isDeviceRequest = true;
			}
		}

		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			userMbr2 = dao.queryForObject("Device.searchDeviceListD", searchDeviceListRequest, UserMbr.class);
		} else {
			userMbr2 = dao.queryForObject("Device.searchDeviceList", searchDeviceListRequest, UserMbr.class);
		}
		LOGGER.debug("### doRemoveUser : 5");
		commonRequest2.setSystemID(userMbr2.getUserID());
		searchDeviceListRequest.setCommonRequest(commonRequest2);

		SearchDeviceListResponse searchDeviceListResponse = null;

		// 휴대기기 정보로 조회
		if (isDeviceRequest) {
			LOGGER.debug("### doRemoveUser : 5a");
			searchDeviceListResponse = dao.queryForObject("Device.searchDeviceList2D", searchDeviceListRequest,
					SearchDeviceListResponse.class);
		} else {
			LOGGER.debug("### doRemoveUser : 5b");
			searchDeviceListResponse = dao.queryForObject("Device.searchDeviceList2", searchDeviceListRequest,
					SearchDeviceListResponse.class);
		}
		LOGGER.debug("### doRemoveUser : 6");
		LOGGER.debug("### searchDeviceListResponse : {}", searchDeviceListResponse);

		row = dao.update("Device.removeOwner", userMbr);
		LOGGER.debug("### removeUser row : {}", row);

		List<UserMbrDevice> userMbrDeviceList = searchDeviceListResponse.getUserMbrDevice();
		List<String> deviceKeyList = new ArrayList<String>();
		if (userMbrDeviceList != null && userMbrDeviceList.size() > 0) {
			for (UserMbrDevice userMbrDevice : userMbrDeviceList) {
				if (userMbrDevice.getDeviceKey() != null)
					deviceKeyList.add(userMbrDevice.getDeviceKey());
			}
		}
		LOGGER.debug("### doRemoveUser : 7");
		// ACTION 6-2. 휴대기기 삭제 요청
		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
		removeDeviceRequest.setCommonRequest(commonRequest);
		removeDeviceRequest.setUserKey(userMbr.getUserKey());
		removeDeviceRequest.setDeviceKey(deviceKeyList);

		for (int i = 0; i < deviceKeyList.size(); i++) {

			UserMbrDevice userMbrDevice = new UserMbrDevice();
			userMbrDevice.setUserKey(userMbr.getUserKey());
			userMbrDevice.setDeviceKey(deviceKeyList.get(i));
			userMbrDevice.setIsUsed(Constant.TYPE_YN_N);

			// ACTION 6-2-1. 휴대기기 이력 테이블 insert.
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				row = dao.update("Device.insertUpdateDeviceHistory", userMbrDevice); // ok
				if (row <= 0) {
					return row;
				}
				LOGGER.debug("### insertUpdateDeviceHistory row : {}", row);

			}

			// ACTION 6-2-2. 휴대기기 속성의 USE_YN, AUTH_YN 을 N 으로 update.
			row = dao.update("Device.removeDevice", userMbrDevice);
			if (row <= 0) {
				return row;
			}
			LOGGER.debug("### removeDevice row : {}", row);

			// ACTION 6-2-3. 휴대기기 부가속성의 도토리사용여부를 N 으로 업데이트.(US005610 -> N)
			UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setUserKey(userMbr.getUserKey());
			userMbrDeviceDetail.setDeviceKey(deviceKeyList.get(i));
			userMbrDeviceDetail.setExtraProfile(DeviceManagementCode.DOTORI_YN.getCode()); // 도토리 인증여부 CD_ID
			userMbrDeviceDetail.setExtraProfileValue(Constant.TYPE_YN_N);

			row = dao.update("Device.removeDotori", userMbrDeviceDetail);
			// 예외처리 삭제 : 토토리 관리항목이 없는 경우가 있음.
			// if (row <= 0) {
			// throw new Exception("Unknown Error.");
			// }
			LOGGER.debug("### removeDotori row : {}", row);
			if (row <= 0) {
				row = 1;
			}
		}
		LOGGER.debug("### end doRemoveUser");
		return row;
	}

	/**
	 * <pre>
	 * 에러 메시지.
	 * </pre>
	 * 
	 * @param resultCode
	 *            에러코드
	 * @param resultMessage
	 *            에러메세지
	 * @return CommonResponse 결과 메세지
	 */
	private CommonResponse getErrorResponse(String resultCode, String resultMessage) {
		CommonResponse commonResponse;
		commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.getMessage(resultCode, ""));
		commonResponse.setResultMessage(this.getMessage(resultMessage, ""));
		return commonResponse;
	}

	/**
	 * <pre>
	 *  메시지 프로퍼티에서 메시지 참조.
	 * </pre>
	 * 
	 * @param code
	 *            fail 코드
	 * @param fail
	 *            에러메세지
	 * @return String 결과 메세지
	 */
	private String getMessage(String code, String fail) {
		String msg = this.messageSourceAccessor.getMessage(code, null, fail, LocaleContextHolder.getLocale());
		LOGGER.debug(msg);
		return msg;
	}

	/**
	 * <pre>
	 * 변동성 대상 체크.
	 * </pre>
	 * 
	 * @param checkSaveNSyncRequest
	 *            변동성 대상 체크 요청 Value Object
	 * @return CheckSaveNSyncResponse - 변동성 대상 체크 응답 Value Object
	 */
	@Override
	public CheckSaveNSyncResponse checkSaveNSync(CheckSaveNSyncRequest checkSaveNSyncRequest) {

		LOGGER.debug("### checkSaveNSyncRequest : {}", checkSaveNSyncRequest);

		CheckSaveNSyncResponse checkSaveNSyncResponse = null;

		checkSaveNSyncResponse = this.commonDAO.queryForObject("Device.checkRealSaveNSync", checkSaveNSyncRequest,
				CheckSaveNSyncResponse.class);

		if (checkSaveNSyncResponse == null) {
			checkSaveNSyncResponse = new CheckSaveNSyncResponse();
			checkSaveNSyncResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
					"response.ResultMessage.success"));
			checkSaveNSyncResponse.setIsSaveNSync(Constant.TYPE_YN_N);

			return checkSaveNSyncResponse;
		}

		checkSaveNSyncResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		if (checkSaveNSyncResponse.getChangeCaseCode() == null) {
			checkSaveNSyncResponse.setIsSaveNSync(Constant.TYPE_YN_N);
			return checkSaveNSyncResponse;
		}

		if (checkSaveNSyncResponse.getIsActive().equals(MainStateCode.SECEDE.getCode()) == true
				&& checkSaveNSyncResponse.getUserSubStatus().equals(SubStateCode.CHANGE_MANAGEMENT.getCode()) == false) {
			checkSaveNSyncResponse.setIsSaveNSync(Constant.TYPE_YN_N);
			return checkSaveNSyncResponse;
		}

		checkSaveNSyncResponse.setIsSaveNSync(Constant.TYPE_YN_Y);
		if (checkSaveNSyncResponse.getIsActive().equals(MainStateCode.SECEDE.getCode()))
			checkSaveNSyncResponse.setIsActive(Constant.TYPE_YN_N);
		else
			checkSaveNSyncResponse.setIsActive(Constant.TYPE_YN_Y);
		return checkSaveNSyncResponse;
	}

	/**
	 * <pre>
	 * 회원복구 요청.
	 * </pre>
	 * 
	 * @param reviveUserRequest
	 *            회원복구 요청 Value Object
	 * @return ReviveUserResponse - 회원복구 응답 Value Object
	 */
	@Override
	public ReviveUserResponse updateReviveUser(ReviveUserRequest reviveUserRequest) {

		// ACTION 1. 서브상태가 변동처리대상인지 여부 확인 : INSD_USERMBR_NO로 조회.
		// ACTION 2. 변동성처리대상 회원의 이력 저장.
		// ACTION 3. 해당 회원의 메인/서브 상태를 정상/정상으로 변경.

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("디바이스 서비스 - 회원복구");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### reviveUserRequest : {}", reviveUserRequest);

		ReviveUserResponse reviveUserResponse = new ReviveUserResponse();
		String isRegistered = null;
		UserMbr previousUserMbr = null;

		// ACTION 1. 서브상태가 변동처리대상인지 여부 확인 : INSD_USERMBR_NO로 조회.
		previousUserMbr = this.commonDAO.queryForObject("Device.isSubCodeChangeManagement", reviveUserRequest,
				UserMbr.class);
		if (previousUserMbr != null) {
			isRegistered = previousUserMbr.getUserKey();
		} else {
			isRegistered = null;
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}
		// isRegistered = this.commonDAO.queryForObject("Device.isSubCodeChangeManagement", reviveUserRequest,
		// String.class);
		LOGGER.debug("### updateReviveUser isRegisteredKey : {}", isRegistered);

		// if (isRegistered == null) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		// }

		// ACTION 2. 변동성처리대상 회원의 이력 저장.
		UserMbr usermbr = new UserMbr();
		usermbr.setTenantID(reviveUserRequest.getCommonRequest().getTenantID());
		usermbr.setUserKey(reviveUserRequest.getUserKey());
		usermbr.setImMbrNo(reviveUserRequest.getImMbrNo());

		int row = 0;
		row = this.commonDAO.update("User.insertUpdateStatusHistory", usermbr);
		LOGGER.debug("### updateuser - insertUpdateStatusHistory row : {}", row);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// ACTION 3. 해당 회원의 메인/서브 상태를 정상/정상으로 변경.
		usermbr.setUserMainStatus(MainStateCode.NORMAL.getCode()); // 메인:정상(US010201)
		usermbr.setUserSubStatus(SubStateCode.NORMAL.getCode()); // 서브:정상(US010301)
		usermbr.setSecedeDate(""); // 탈퇴일자 초기화
		row = (Integer) this.commonDAO.insert("User.updateUser", usermbr);

		LOGGER.debug("### row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// 기존재했었던 휴대기기 정보를 살린다.
		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		createDeviceRequest.setCommonRequest(reviveUserRequest.getCommonRequest());
		createDeviceRequest.setUserKey(reviveUserRequest.getUserKey());

		UserMbrDevice tempDevice = new UserMbrDevice();
		tempDevice.setDeviceID(reviveUserRequest.getDeviceID());
		tempDevice.setDeviceTelecom(reviveUserRequest.getDeviceTelecom());
		tempDevice.setUserKey(reviveUserRequest.getUserKey());
		tempDevice.setDeviceKey(reviveUserRequest.getDeviceKey());
		createDeviceRequest.setUserMbrDevice(tempDevice);
		int updateRowCnt = this.doActivateDevice(tempDevice, createDeviceRequest, Constant.TYPE_YN_N);
		if (updateRowCnt < 1)
			throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));

		// device count++
		row = this.commonDAO.update("User.insertUpdateStatusHistory", tempDevice);
		LOGGER.debug("### insertUpdateStatusHistory row : {}", row);

		row = this.commonDAO.update("Device.updateCountDevice", createDeviceRequest);
		LOGGER.debug("### createDevice updateCountPlus : {}", row);

		// 공통 응답
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
				LocaleContextHolder.getLocale()));
		commonResponse.setResultMessage(this.messageSourceAccessor.getMessage("response.ResultMessage.success", null,
				"성공", LocaleContextHolder.getLocale()));

		// 성공 설정
		reviveUserResponse.setCommonResponse(commonResponse);
		return reviveUserResponse;
	}

	/**
	 * <pre>
	 * 회원의 이전 휴대기기 상세정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAllDeviceRequest
	 *            이전 휴대기기 조회 요청 Value Object
	 * @return SearchAllDeviceResponse - 이전 휴대기기 조회 응답 Value Object
	 */
	@Override
	public SearchAllDeviceResponse searchAllDevice(SearchAllDeviceRequest searchAllDeviceRequest) {

		LOGGER.debug("### searchOldDeviceRequest : {}", searchAllDeviceRequest.toString());

		SearchAllDeviceResponse searchAllDeviceResponse;

		searchAllDeviceResponse = this.commonDAO.queryForObject("Device.searchAllDevice", searchAllDeviceRequest,
				SearchAllDeviceResponse.class);

		if (searchAllDeviceResponse == null || searchAllDeviceResponse.getUserMbrDevice() == null) {
			searchAllDeviceResponse = this.idleDAO.queryForObject("Device.searchAllDevice", searchAllDeviceRequest,
					SearchAllDeviceResponse.class);
		}

		if (searchAllDeviceResponse == null || searchAllDeviceResponse.getUserMbrDevice() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		LOGGER.debug("### searchOldDeviceResponse : {}", searchAllDeviceResponse.toString());
		searchAllDeviceResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchAllDeviceResponse;
	}

	/**
	 * <pre>
	 * 휴대기기의 소유자이력을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceOwnerRequest
	 *            휴대기기 소유자이력 조회 요청 Value Object
	 * @return searchDeviceOwnerResponse - 휴대기기 소유자이력 조회 응답 Value Object
	 */
	@Override
	public SearchDeviceOwnerResponse searchDeviceOwner(SearchDeviceOwnerRequest searchDeviceOwnerRequest) {

		LOGGER.debug("### searchDeviceOwnerListRequest : {}", searchDeviceOwnerRequest.toString());
		searchDeviceOwnerRequest.setIsDormant(Constant.TYPE_YN_N);
		SearchDeviceOwnerResponse searchDeviceOwnerResponse = this.commonDAO.queryForObject("Device.searchDeviceOwner",
				searchDeviceOwnerRequest, SearchDeviceOwnerResponse.class);

		if (searchDeviceOwnerResponse == null || searchDeviceOwnerResponse.getUserKey() == null) {
			searchDeviceOwnerRequest.setIsDormant(Constant.TYPE_YN_Y);
			searchDeviceOwnerResponse = this.idleDAO.queryForObject("Device.searchDeviceOwner",
					searchDeviceOwnerRequest, SearchDeviceOwnerResponse.class);
		}

		if (searchDeviceOwnerResponse == null || searchDeviceOwnerResponse.getUserKey() == null) {
			searchDeviceOwnerResponse = this.commonDAO.queryForObject("Device.searchDeviceOwnerHis",
					searchDeviceOwnerRequest, SearchDeviceOwnerResponse.class);
		}

		if (searchDeviceOwnerResponse == null || searchDeviceOwnerResponse.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		return searchDeviceOwnerResponse;

	}

	/**
	 * <pre>
	 * 등록단말 정보 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchOrderDeviceRequest
	 *            - 등록단말 정보 조회 요청 Value Object
	 * @return SearchOrderDeviceResponse - 등록단말 정보 조회 응답 Value Object
	 */
	@Override
	public SearchOrderDeviceResponse searchOrderDevice(SearchOrderDeviceRequest searchOrderDeviceRequest) {
		LOGGER.debug("### searchOrderDeviceRequest : {}", searchOrderDeviceRequest.toString());

		SearchOrderDeviceResponse searchOrderDeviceResponse = this.commonDAO.queryForObject("Device.searchOrderDevice",
				searchOrderDeviceRequest, SearchOrderDeviceResponse.class);

		if (searchOrderDeviceResponse == null || searchOrderDeviceResponse.getDeviceId() == null) {
			searchOrderDeviceResponse = this.idleDAO.queryForObject("Device.searchOrderDevice",
					searchOrderDeviceRequest, SearchOrderDeviceResponse.class);
		}

		if (searchOrderDeviceResponse == null || searchOrderDeviceResponse.getDeviceId() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		return searchOrderDeviceResponse;
	}

	/**
	 * <pre>
	 * 단말 부가속성 정보 등록/수정 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateDeviceManagementRequest
	 *            - 단말 부가속성 정보 등록/수정 요청 Value Object
	 * @return UpdateDeviceManagementResponse - 단말 부가속성 정보 등록/수정 응답 Value Object
	 */
	@Override
	public UpdateDeviceManagementResponse updateDeviceManagement(
			UpdateDeviceManagementRequest updateDeviceManagementRequest) {
		LOGGER.debug("### searchOrderDeviceRequest : {}", updateDeviceManagementRequest.toString());

		String isDormant = StringUtils.isBlank(updateDeviceManagementRequest.getIsDormant()) ? Constant.TYPE_YN_N : updateDeviceManagementRequest
				.getIsDormant();
		UpdateDeviceManagementResponse updateDeviceManagementResponse = new UpdateDeviceManagementResponse();

		Integer row = 0;
		// check 부가속성
		if (updateDeviceManagementRequest.getUserMbrDeviceDetail() != null) {
			List<UserMbrDeviceDetail> userMbrDeviceDetailList = updateDeviceManagementRequest.getUserMbrDeviceDetail();
			LOGGER.debug("### userMbrDeviceDetailList size : {}", userMbrDeviceDetailList.size());
			for (int i = 0; i < userMbrDeviceDetailList.size(); i++) {
				UserMbrDeviceDetail record = userMbrDeviceDetailList.get(i);
				record.setSystemID(updateDeviceManagementRequest.getCommonRequest().getSystemID());
				record.setUserKey(updateDeviceManagementRequest.getUserKey());
				record.setDeviceKey(updateDeviceManagementRequest.getDeviceKey());
				try {
					if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
						row = this.commonDAO.update("Device.updateDeviceDetail", record);
					} else {
						row = this.idleDAO.update("Device.updateDeviceDetail", record);
					}
				} catch (Exception e) {
					row = 1;
				}

				LOGGER.debug("### updateDeviceDetail : {}", row);
				if (row == 0) {
					return null;
				}
			}
		}

		updateDeviceManagementResponse.setDeviceKey(updateDeviceManagementRequest.getDeviceKey());
		updateDeviceManagementResponse.setUserKey(updateDeviceManagementRequest.getUserKey());
		return updateDeviceManagementResponse;
	}

	/**
	 * <pre>
	 * 휴대기기 정보 조회.
	 * </pre>
	 *
	 * @param keyType
	 * @param keyString
	 * @return SearchDeviceResponse
	 */
	private SearchDeviceResponse doSearchDevice(String keyType, String keyString) {
		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(keyType);
		key.setKeyString(keyString);
		keySearchList.add(key);
		searchDeviceRequest.setKeySearchList(keySearchList);
		SearchDeviceResponse searchDeviceResponse = this.commonDAO.queryForObject("Device.searchDevice",
				searchDeviceRequest, SearchDeviceResponse.class);

		return searchDeviceResponse;
	}
}
