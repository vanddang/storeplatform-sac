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

import com.skplanet.storeplatform.member.client.user.sci.vo.*;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
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

		boolean isDeviceRequest = false;
		List<KeySearch> keySearchList = searchDeviceListRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {
			if (keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_KEY)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_DEVICE_ID)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_MDN)
					|| keySearch.getKeyType().equals(Constant.SEARCH_TYPE_SVC_MANG_NO)) {
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

//		CommonRequest commonRequest = new CommonRequest();
//		commonRequest.setSystemID(userMbr.getUserID());
//		searchDeviceListRequest.setCommonRequest(commonRequest);

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
		String preUserKey = null; // 이전사용자 휴대기기 설정정보가 존재하는경우 이관처리를 위한 userKey
		String preDeviceKey = null; // 이전사용자 휴대기기 설정정보가 존재하는경우 이관처리를 위한 deviceKey
		UserMbr createUserMbr = null; // 휴대기기 등록/수정 요청한 회원정보
		UserMbrDevice mobileUserMbrDevice = null; // 아이디 전환 되는 모바일 회원의 정보
		List<UserMbrDevice> userMbrDeviceList = null; // 본인 정보 처리용 휴대기기 리스트(auth_yn = N)
		List<UserMbrDevice> ownerUserMbrDeviceList = null; // 타인 정보 처리용 휴대기기 리스트(auth_yn = Y)

		// 정상회원 유무 체크
		UserMbr userMbr = new UserMbr();
		userMbr.setUserKey(userKey);
		createUserMbr = this.commonDAO.queryForObject("User.getUserDetail", userMbr, UserMbr.class);
		if(createUserMbr == null){
			throw new StorePlatformException(this.getMessage("response.ResultCode.memberKeyNotFound", ""));
		}

		// 단말 등록/수정 요청 회원은 휴면계정일 수 없다.
		if (StringUtils.equals(createUserMbr.getIsDormant(), Constant.TYPE_YN_Y)) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.sleepUserError", ""));
		}

		// 모바일 회원 전환 케이스 확인
		if(StringUtils.isNotBlank(createDeviceRequest.getUserMbrDevice().getSvcMangNum())){
			SearchDeviceResponse searchDeviceResponse = this.commonDAO.queryForObject("Device.searchDeviceOrderBySvcMangNo", createDeviceRequest.getUserMbrDevice().getSvcMangNum(), SearchDeviceResponse.class);
			if(searchDeviceResponse != null && searchDeviceResponse.getUserMbrDevice() != null){
				mobileUserMbrDevice = searchDeviceResponse.getUserMbrDevice();
			}
			String previousIsDormant = Constant.TYPE_YN_N;
			if(mobileUserMbrDevice == null){
				searchDeviceResponse = this.idleDAO.queryForObject("Device.searchDeviceOrderBySvcMangNo", createDeviceRequest.getUserMbrDevice().getSvcMangNum(), SearchDeviceResponse.class);
				if(searchDeviceResponse != null && searchDeviceResponse.getUserMbrDevice() != null){
					mobileUserMbrDevice = searchDeviceResponse.getUserMbrDevice();
					previousIsDormant = Constant.TYPE_YN_Y;
				}
			}
			if(mobileUserMbrDevice != null && !StringUtils.equals(Constant.USER_TYPE_MOBILE, createUserMbr.getUserType())){
				// 모바일 회원 탈퇴전 구매내역 이관 및 MQ 연동에 필요한 정보 셋팅
				createDeviceResponse.setPreviousUserKey(mobileUserMbrDevice.getUserKey());
				createDeviceResponse.setPreviousDeviceKey(mobileUserMbrDevice.getDeviceKey());
				createDeviceResponse.setPreviousIsDormant(previousIsDormant);
				createDeviceResponse.setPreviousUserID(mobileUserMbrDevice.getUserID());

				// 모바일 회원의 프로필 이미지 경로
				List<MbrMangItemPtcr> mbrMangItemPtcrList = null;
				if (StringUtils.equals(previousIsDormant, Constant.TYPE_YN_N)) {
					mbrMangItemPtcrList = this.commonDAO.queryForList("User.getManagementItemList", mobileUserMbrDevice.getUserKey(), MbrMangItemPtcr.class);
				} else {
					mbrMangItemPtcrList = this.idleDAO.queryForList("User.getManagementItemList", mobileUserMbrDevice.getUserKey(), MbrMangItemPtcr.class);
				}
				if (mbrMangItemPtcrList != null && mbrMangItemPtcrList.size() > 0) {
					for(MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList){
						if(StringUtils.equals(mbrMangItemPtcr.getExtraProfile(), "US010912")){
							createDeviceResponse.setPreviousProfileImgPath(mbrMangItemPtcr.getExtraProfileValue());
						}
					}
				}

				// 모바일 회원 전환이력 저장
				UserkeyTrack userkeyTrack = new UserkeyTrack();
				userkeyTrack.setPreUserKey(mobileUserMbrDevice.getUserKey());
				userkeyTrack.setAfterUserKey(createUserMbr.getUserKey());
				userkeyTrack.setRegID(createUserMbr.getUserID());
				this.commonDAO.update("Device.insertUserkeyTrack", userkeyTrack);

				// sms수신여부, 가입채널, PUSH수신동의 이관
				createDeviceRequest.getUserMbrDevice().setIsRecvSMS(mobileUserMbrDevice.getIsRecvSMS());
				createDeviceRequest.getUserMbrDevice().setJoinId(mobileUserMbrDevice.getJoinId());
				for(UserMbrDeviceDetail userMbrDeviceDetail : mobileUserMbrDevice.getUserMbrDeviceDetail()){
					if(StringUtils.equals(userMbrDeviceDetail.getExtraProfile(), MemberConstants.DEVICE_EXTRA_PUSH_YN)){
						createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail().add(userMbrDeviceDetail);
						break;
					}
				}
			}
		}

		/** 타인 정보 처리 start */
		LOGGER.info("타인 정보 처리 start");
		// device_id 존재 체크
		if (StringUtils.isNotBlank(createDeviceRequest.getUserMbrDevice().getDeviceID())) {
			ownerUserMbrDeviceList = this.doSearchDevice(Constant.SEARCH_TYPE_DEVICE_ID, createDeviceRequest.getUserMbrDevice().getDeviceID(), userKey, Constant.TYPE_YN_N, Constant.TYPE_YN_Y);
			if(ownerUserMbrDeviceList != null && ownerUserMbrDeviceList.size() > 0){
				for(UserMbrDevice userMbrDevice : ownerUserMbrDeviceList){
					// device_id로 다른회원이 존재하면 auth_yn = 'N' 처리
					LOGGER.info("{} : deviceId로 등록된 회원이 존재 -> {}", userKey, userMbrDevice.getUserKey(), createDeviceRequest.getUserMbrDevice().getDeviceID(), userMbrDevice.getUserKey());
					String isDormant = StringUtils.isBlank(userMbrDevice.getIsDormant()) ? Constant.TYPE_YN_N : userMbrDevice.getIsDormant(); // 휴면 회원 유무

					// 회원정보/휴대기기 설정정보 조회
					UserMbr preUserMbr = new UserMbr();
					preUserMbr.setUserKey(userMbrDevice.getUserKey());
					UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
					userMbrDeviceSet.setUserKey(userMbrDevice.getUserKey());
					userMbrDeviceSet.setDeviceKey(userMbrDevice.getDeviceKey());
					UserMbrDeviceSet searchUserMbrDeviceSet = null;
					if(StringUtils.equals(isDormant, Constant.TYPE_YN_N)){
						preUserMbr = this.commonDAO.queryForObject("User.getUserDetail", preUserMbr, UserMbr.class);
						searchUserMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchDeviceSetInfo", userMbrDeviceSet, UserMbrDeviceSet.class);
					}else{
						preUserMbr = this.idleDAO.queryForObject("User.getUserDetail", preUserMbr, UserMbr.class);
						searchUserMbrDeviceSet = this.idleDAO.queryForObject("DeviceSet.searchDeviceSetInfo", userMbrDeviceSet, UserMbrDeviceSet.class);
					}

					if (searchUserMbrDeviceSet != null) {
						preUserKey = searchUserMbrDeviceSet.getUserKey();
						preDeviceKey = searchUserMbrDeviceSet.getDeviceKey();
					}

					if (StringUtils.equals(Constant.USER_TYPE_MOBILE, preUserMbr.getUserType())) {// 모바일 회원
						LOGGER.info("{} : {} {} {} 모바일 회원 탈퇴", userKey, preUserMbr.getUserKey(), userMbrDevice.getDeviceKey(), userMbrDevice.getMdn());
						// 탈퇴처리
						int row = this.doRemoveMobileUser(userMbrDevice, isDormant);
						if (row < 1)
							throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
					} else { // 아이디 회원
						LOGGER.info("{} : {} {} {} 휴대기기 invalid 처리", userKey, preUserMbr.getUserKey(), userMbrDevice.getDeviceKey(), userMbrDevice.getMdn());
						int row = this.doInvalidDevice(userMbrDevice, isDormant);
						if (row < 1)
							throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
					}
				}
			}
		}

		// MDN 존재 체크
		if (StringUtils.isNotBlank(createDeviceRequest.getUserMbrDevice().getMdn())) {
			ownerUserMbrDeviceList = this.doSearchDevice(Constant.SEARCH_TYPE_MDN, createDeviceRequest.getUserMbrDevice().getMdn(), userKey, Constant.TYPE_YN_N, Constant.TYPE_YN_Y);

			if(ownerUserMbrDeviceList != null && ownerUserMbrDeviceList.size() > 0){
				for(UserMbrDevice userMbrDevice : ownerUserMbrDeviceList){
					// device_id가 존재하는 mdn으로 다른회원이 존재하면 mdn, imei, sim null 처리
					if (StringUtils.isNotBlank(userMbrDevice.getDeviceID())) {
						LOGGER.info("{} : {} 회원이 {} MDN으로 등록된 회원이 존재", userKey, userMbrDevice.getUserKey(), createDeviceRequest.getUserMbrDevice().getMdn());
						LOGGER.info("{} : {} 회원 mdn, nativeId, deviceSimNm 초기화 처리", userKey, userMbrDevice.getUserKey());
						String isDormant = StringUtils.isBlank(userMbrDevice.getIsDormant()) ? Constant.TYPE_YN_N : userMbrDevice.getIsDormant(); // 휴면 회원 유무
						UserMbrDevice updateMbrDevice = new UserMbrDevice();
						updateMbrDevice.setMdn("");
						updateMbrDevice.setDeviceSimNm("");
						updateMbrDevice.setNativeID("");
						updateMbrDevice.setUserMbrDeviceDetail(createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail());
						this.updateDeviceInfo(createDeviceRequest.getCommonRequest().getSystemID(), userMbrDevice, updateMbrDevice, isDormant);

						// tb_us_ousermbr_device_set 테이블의 실명인증일자, mdn을 null 처리
						UserMbrDeviceSet modifyUserMbrDeviceSet = new UserMbrDeviceSet();
						modifyUserMbrDeviceSet.setUserKey(updateMbrDevice.getUserKey());
						modifyUserMbrDeviceSet.setDeviceKey(updateMbrDevice.getDeviceKey());
						modifyUserMbrDeviceSet.setUserID(updateMbrDevice.getUserID());
						modifyUserMbrDeviceSet.setRealNameDate("");
						modifyUserMbrDeviceSet.setRealNameMdn("");
						if(StringUtils.equals(isDormant, Constant.TYPE_YN_N)){
							//this.commonDAO.update("DeviceSet.modifyDeviceSet", modifyUserMbrDeviceSet); // TODO. 아직 테이블 정의 안됨
						}else{
							//this.idleDAO.update("DeviceSet.modifyDeviceSet", modifyUserMbrDeviceSet);  // TODO. 아직 테이블 정의 안됨
						}
					}
				}
			}
		}

		// 서비스 관리 번호 체크
		if(StringUtils.isNotBlank(createDeviceRequest.getUserMbrDevice().getSvcMangNum())){
			ownerUserMbrDeviceList = this.doSearchDevice(Constant.SEARCH_TYPE_SVC_MANG_NO, createDeviceRequest.getUserMbrDevice().getSvcMangNum(), userKey, Constant.TYPE_YN_N, Constant.TYPE_YN_Y);
			if(ownerUserMbrDeviceList != null && ownerUserMbrDeviceList.size() > 0){
				for(UserMbrDevice userMbrDevice : ownerUserMbrDeviceList){
					LOGGER.info("{} : {} 회원이 {} 서비스관리번호로 존재", userKey, userMbrDevice.getUserKey(), createDeviceRequest.getUserMbrDevice().getSvcMangNum());
					String isDormant = StringUtils.isBlank(userMbrDevice.getIsDormant()) ? Constant.TYPE_YN_N : userMbrDevice.getIsDormant(); // 휴면 회원 유무

					// 회원 정보 조회
					UserMbr preUserMbr = new UserMbr();
					preUserMbr.setUserKey(userMbrDevice.getUserKey());
					if(StringUtils.equals(isDormant, Constant.TYPE_YN_N)){
						preUserMbr = this.commonDAO.queryForObject("User.getUserDetail", preUserMbr, UserMbr.class);
					}else{
						preUserMbr = this.idleDAO.queryForObject("User.getUserDetail", preUserMbr, UserMbr.class);
					}

					if (StringUtils.isBlank(userMbrDevice.getDeviceID())) { // device_id가 없는 경우 auth_yn
						// 휴대기기 설정정보 이관처리를 위한 회원키 조회
						UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
						userMbrDeviceSet.setUserKey(userMbrDevice.getUserKey());
						userMbrDeviceSet.setDeviceKey(userMbrDevice.getDeviceKey());
						UserMbrDeviceSet searchUserMbrDeviceSet = null;
						if(StringUtils.equals(isDormant, Constant.TYPE_YN_N)){
							searchUserMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchDeviceSetInfo", userMbrDeviceSet, UserMbrDeviceSet.class);
						}else{
							searchUserMbrDeviceSet = this.idleDAO.queryForObject("DeviceSet.searchDeviceSetInfo", userMbrDeviceSet, UserMbrDeviceSet.class);
						}

						if (searchUserMbrDeviceSet != null) {
							preUserKey = searchUserMbrDeviceSet.getUserKey();
							preDeviceKey = searchUserMbrDeviceSet.getDeviceKey();
						}

						if (StringUtils.equals(Constant.USER_TYPE_MOBILE, preUserMbr.getUserType())) { // 모바일 회원
							LOGGER.info("{} : {} {} {} 모바일 회원 탈퇴(device_id 없음)", userKey, preUserMbr.getUserKey(), userMbrDevice.getDeviceKey(), userMbrDevice.getMdn());
							int row = this.doRemoveMobileUser(userMbrDevice, isDormant);
							if (row < 1)
								throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
						} else { // 아이디 회원
							LOGGER.info("{} : {} {} {} 휴대기기 invalid 처리(device_id 없음)", userKey, preUserMbr.getUserKey(), userMbrDevice.getDeviceKey(), userMbrDevice.getMdn());
							int row = this.doInvalidDevice(userMbrDevice, isDormant);
							if (row < 1)
								throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
						}
					} else {
						// device_id가 존재하고 ID 회원인 경우 svc_no, mno_cd 널 처리
						if (!StringUtils.equals(Constant.USER_TYPE_MOBILE, preUserMbr.getUserType())) {
							LOGGER.info("{} : {} 아이디 회원으로 {} deviceId존재", userKey, userMbrDevice.getUserID(), userMbrDevice.getDeviceID());
							LOGGER.info("{} : {} 아이디 회원 svcMangNo, deviceTelecom 초기화 처리", userKey, userMbrDevice.getUserID());
							UserMbrDevice updateMbrDevice = new UserMbrDevice();
							updateMbrDevice.setSvcMangNum("");
							updateMbrDevice.setDeviceTelecom("");
							updateMbrDevice.setUserMbrDeviceDetail(createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail());
							this.updateDeviceInfo(createDeviceRequest.getCommonRequest().getSystemID(), userMbrDevice, updateMbrDevice, isDormant);

							// tb_us_ousermbr_device_set 테이블의 실명인증일자, mdn을 null 처리
							UserMbrDeviceSet modifyUserMbrDeviceSet = new UserMbrDeviceSet();
							modifyUserMbrDeviceSet.setUserKey(updateMbrDevice.getUserKey());
							modifyUserMbrDeviceSet.setDeviceKey(updateMbrDevice.getDeviceKey());
							modifyUserMbrDeviceSet.setUserID(updateMbrDevice.getUserID());
							modifyUserMbrDeviceSet.setRealNameDate("");
							modifyUserMbrDeviceSet.setRealNameMdn("");
							if(StringUtils.equals(isDormant, Constant.TYPE_YN_N)){
								//this.commonDAO.update("DeviceSet.modifyDeviceSet", modifyUserMbrDeviceSet); // TODO. 아직 테이블 정의 안됨
							}else{
								//this.idleDAO.update("DeviceSet.modifyDeviceSet", modifyUserMbrDeviceSet); // TODO. 아직 테이블 정의 안됨
							}
						}
					}
				}
			}
		}
		LOGGER.info("타인 정보 처리 end");
		/** 타인 정보 처리 end */

		/** 본인 정보 처리 start */
		LOGGER.info("본인 정보 처리 start");

		// device_id 존재 체크
		if(StringUtils.isNotBlank(createDeviceRequest.getUserMbrDevice().getDeviceID())){
			userMbrDeviceList = this.doSearchDevice(Constant.SEARCH_TYPE_DEVICE_ID, createDeviceRequest.getUserMbrDevice().getDeviceID(), userKey, Constant.TYPE_YN_Y, Constant.TYPE_YN_N);
		}

		if(userMbrDeviceList == null || userMbrDeviceList.size() == 0){
			// svc_mang_no 조회
			if(StringUtils.isNotBlank(createDeviceRequest.getUserMbrDevice().getSvcMangNum())){
				userMbrDeviceList = this.doSearchDevice(Constant.SEARCH_TYPE_SVC_MANG_NO, createDeviceRequest.getUserMbrDevice().getSvcMangNum(), userKey, Constant.TYPE_YN_Y,  Constant.TYPE_YN_N);
			}
			if(userMbrDeviceList == null || userMbrDeviceList.size() == 0){
				LOGGER.info("{} {} {} 신규 단말 등록", userKey, createDeviceRequest.getUserMbrDevice().getDeviceID(), createDeviceRequest.getUserMbrDevice().getMdn());

				// 단말 등록 TLog
				new TLogUtil().set(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id("TL_SC_MEM_0003");
					}
				});

				// 아이디 회원에 대표기기 등록요청인 경우 기존 등록된 대표기기 N처리
				if (!StringUtils.equals(Constant.USER_TYPE_MOBILE, createUserMbr.getUserType())
						&& createDeviceRequest.getUserMbrDevice().getIsPrimary().equals(Constant.TYPE_YN_Y)){
					this.doCancelMainDevice(createUserMbr);
				}

				createDeviceRequest.getUserMbrDevice().setUserID(createUserMbr.getUserID());
				deviceKey = this.doInsertDevice(createDeviceRequest);
				if (deviceKey == null)
					throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));

				final String tlogDeviceID = createDeviceRequest.getUserMbrDevice().getDeviceID();
				final String tlogDeviceKey = deviceKey;
				final String tlogUserKey = createDeviceRequest.getUserKey();
				final String tlogUserID = createUserMbr.getUserID();
				final String tlogImSvcNo = createDeviceRequest.getUserMbrDevice().getSvcMangNum();
				final String tlogDeviceTelecom = createDeviceRequest.getUserMbrDevice().getDeviceTelecom();
				final String tlogImei = createDeviceRequest.getUserMbrDevice().getNativeID();
				final String tlogDeviceModelNo = createDeviceRequest.getUserMbrDevice().getDeviceModelNo();
				final String tlogSystemID = createDeviceRequest.getCommonRequest().getSystemID();
				final String tlogMdn = createDeviceRequest.getUserMbrDevice().getMdn(); // TODO. TLOG 필드 추가 필요
				String isSKTelecom = "N";
				if (createDeviceRequest.getUserMbrDevice().getDeviceTelecom() != null
						&& createDeviceRequest.getUserMbrDevice().getDeviceTelecom().equals(MemberConstants.DEVICE_TELECOM_SKT)) {
					isSKTelecom = "Y";
				}
				final String tlogCompanyOwnPhoneYn = isSKTelecom;
				new TLogUtil().set(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.insd_usermbr_no(tlogUserKey).device_id(tlogDeviceID).svc_mng_no(tlogImSvcNo)
								.company_own_phone_yn(tlogCompanyOwnPhoneYn).mno_type(tlogDeviceTelecom).system_id(tlogSystemID)
								.imei(tlogImei).phone_model(tlogDeviceModelNo).insd_device_id(tlogDeviceKey).mbr_id(tlogUserID);
					}
				});
				for(UserMbrDeviceDetail userMbrDeviceDetail : createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail()){
					if(StringUtils.equals(userMbrDeviceDetail.getExtraProfile(), MemberConstants.DEVICE_EXTRA_OSVERSION)){
						final String tlogOsVersion = userMbrDeviceDetail.getExtraProfileValue();
						new TLogUtil().set(new ShuttleSetter() {
							@Override
							public void customize(TLogSentinelShuttle shuttle) {
								shuttle.os_version(tlogOsVersion);
							}
						});
						break;
					}
				}
			}else{
				for(UserMbrDevice userMbrDevice : userMbrDeviceList){
					LOGGER.info("{} req device_id 가 null이 아니고 db의 device_id와 다른 경우 update", userKey);
					if(StringUtils.equals(userMbrDevice.getIsUsed(), Constant.TYPE_YN_Y)){
						this.updateDeviceInfo(createDeviceRequest.getCommonRequest().getSystemID(), userMbrDevice, createDeviceRequest.getUserMbrDevice(), Constant.TYPE_YN_N);
						LOGGER.info("{} {} OneStore 재설치 ", userKey, userMbrDevice.getDeviceKey());
						// mno_cd, svc_mang_no중 하나라도 변경되면 tb_us_ousermbr_device_set 테이블의 실명인증일자, mdn을 null 처리
						if(!StringUtils.equals(userMbrDevice.getDeviceTelecom(), createDeviceRequest.getUserMbrDevice().getDeviceTelecom())
								|| !StringUtils.equals(userMbrDevice.getSvcMangNum(), createDeviceRequest.getUserMbrDevice().getSvcMangNum())) {
							UserMbrDeviceSet modifyUserMbrDeviceSet = new UserMbrDeviceSet();
							modifyUserMbrDeviceSet.setUserKey(userMbrDevice.getUserKey());
							modifyUserMbrDeviceSet.setDeviceKey(userMbrDevice.getDeviceKey());
							modifyUserMbrDeviceSet.setUserID(userMbrDevice.getUserID());
							modifyUserMbrDeviceSet.setRealNameDate("");
							modifyUserMbrDeviceSet.setRealNameMdn("");
							//this.commonDAO.update("DeviceSet.modifyDeviceSet", modifyUserMbrDeviceSet); // TODO. 아직 테이블 정의 안됨
						}
					}else{
						this.doActivateDevice(userMbrDevice, createDeviceRequest); // auth_yn = 'Y'처리
					}
					deviceKey = userMbrDevice.getDeviceKey();
				}
			}
		} else {
			for(UserMbrDevice userMbrDevice : userMbrDeviceList) {
				LOGGER.info("{} : deviceId {} 동일 정보 존재", userKey, createDeviceRequest.getUserMbrDevice().getDeviceID());
				if(StringUtils.equals(userMbrDevice.getIsUsed(), Constant.TYPE_YN_Y)){
					if(StringUtils.isBlank(userMbrDevice.getMdn()) && StringUtils.isBlank(createDeviceRequest.getUserMbrDevice().getMdn())){
						// usim이 제거된 상태에서는 휴대기기 업데이트를 안한다.
						LOGGER.info("{}, {} : usim 제거 상태", userKey, userMbrDevice.getDeviceID());
					}else if(StringUtils.isNotBlank(userMbrDevice.getMdn()) && StringUtils.isBlank(createDeviceRequest.getUserMbrDevice().getMdn())){
						// usim 제거 후 접속했을때 초기화 처리
						LOGGER.info("{}, {} : usim 제거 후 접속", userKey, userMbrDevice.getDeviceID());
						UserMbrDevice updateMbrDevice = new UserMbrDevice();
						updateMbrDevice.setNativeID("");
						updateMbrDevice.setMdn("");
						updateMbrDevice.setDeviceSimNm("");
						this.updateDeviceInfo(createDeviceRequest.getCommonRequest().getSystemID(), userMbrDevice, updateMbrDevice, Constant.TYPE_YN_N);
					}else{
						if(StringUtils.isBlank(userMbrDevice.getMdn()) && StringUtils.isNotBlank(createDeviceRequest.getUserMbrDevice().getMdn())){
							LOGGER.info("{}, {}, {} usim 삽입 후 접속", userKey, userMbrDevice.getDeviceID(), userMbrDevice.getMdn());
						}else{
							LOGGER.info("{}, {}, {} 정상 인증처리", userKey, userMbrDevice.getDeviceID(), userMbrDevice.getMdn());
						}
						this.updateDeviceInfo(createDeviceRequest.getCommonRequest().getSystemID(), userMbrDevice, createDeviceRequest.getUserMbrDevice(), Constant.TYPE_YN_N);
						// mno_cd, svc_mang_no중 하나라도 변경되면 tb_us_ousermbr_device_set 테이블의 실명인증일자, mdn을 null 처리
						if(!StringUtils.equals(userMbrDevice.getDeviceTelecom(), createDeviceRequest.getUserMbrDevice().getDeviceTelecom())
								|| !StringUtils.equals(userMbrDevice.getSvcMangNum(), createDeviceRequest.getUserMbrDevice().getSvcMangNum())) {
							UserMbrDeviceSet modifyUserMbrDeviceSet = new UserMbrDeviceSet();
							modifyUserMbrDeviceSet.setUserKey(userMbrDevice.getUserKey());
							modifyUserMbrDeviceSet.setDeviceKey(userMbrDevice.getDeviceKey());
							modifyUserMbrDeviceSet.setUserID(userMbrDevice.getUserID());
							modifyUserMbrDeviceSet.setRealNameDate("");
							modifyUserMbrDeviceSet.setRealNameMdn("");
							//this.commonDAO.update("DeviceSet.modifyDeviceSet", modifyUserMbrDeviceSet); //TODO. 아직 테이블 정리 안됨
						}
					}
				}else{
					this.doActivateDevice(userMbrDevice, createDeviceRequest);
				}
				deviceKey = userMbrDevice.getDeviceKey();
			}
		}
		LOGGER.info("본인 정보 처리 end");
		/** 본인 정보 처리 end */

		createDeviceResponse.setUserKey(userKey);
		createDeviceResponse.setDeviceKey(deviceKey);
		createDeviceResponse.setPreUserKey(preUserKey);
		createDeviceResponse.setPreDeviceKey(preDeviceKey);

		// 모바일 회원 전환 tlog
		if(mobileUserMbrDevice != null && !StringUtils.equals(Constant.USER_TYPE_MOBILE, createUserMbr.getUserType())){
			final String tlogUserKey = createDeviceRequest.getUserKey();
			final String tlogDeviceId = createDeviceRequest.getUserMbrDevice().getDeviceID();
			final String tlogMdn = createDeviceRequest.getUserMbrDevice().getMdn(); // TODO. mdn 필드 추가할건지 확인 필요.
			final String tlogUserKeyPre = mobileUserMbrDevice.getUserKey();
			final String tlogUserKeyPost = createDeviceRequest.getUserKey();
			final String tlogMbrIdPre = mobileUserMbrDevice.getUserID();
			final String tlogMbrIdPost = createUserMbr.getUserID();

			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SC_MEM_0005");
					shuttle.insd_usermbr_no(tlogUserKey);
					shuttle.device_id(tlogDeviceId);
					shuttle.mbr_id(tlogMbrIdPost);
					shuttle.insd_usermbr_no_pre(tlogUserKeyPre);
					shuttle.insd_usermbr_no_post(tlogUserKeyPost);
					shuttle.mbr_id_pre(tlogMbrIdPre);
					shuttle.mbr_id_post(tlogMbrIdPost);
					shuttle.result_code("SUCC");
				}
			});
		}
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
		// ACTION 6. 휴대기기 설정정보 초기화

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
		String userKey = removeDeviceRequest.getUserKey();
		String lastDeviceKey = "";
		List<String> deviceKeyList = removeDeviceRequest.getDeviceKey();
		int sizeList = deviceKeyList.size();

		// ACTION 1. 단말 존재여부 확인 : deviceKey, userKey로 단말 조회
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

			dao.delete("Device.removeDeviceExtraProfile", userMbrDeviceDetail);
			LOGGER.debug("### Device.removeDeviceExtraProfile row : {}", row);

			// ACTION 5. 휴대기기 삭제 카운트 증가
			int delDeviceCount = removeDeviceResponse.getDelDeviceCount();
			delDeviceCount = delDeviceCount + 1;
			removeDeviceResponse.setDelDeviceCount(delDeviceCount);

			// ACTION 6. 휴대기기 설정정보 초기화
			UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
			userMbrDeviceSet.setUserKey(userKey);
			userMbrDeviceSet.setDeviceKey(deviceKeyList.get(i));
			userMbrDeviceSet.setPinNo("");
			userMbrDeviceSet.setAuthCnt("0");
			userMbrDeviceSet.setAuthLockYn("N");
//			this.commonDAO.delete("DeviceSet.modifyDeviceSet", userMbrDeviceSet); // TODO. 아직 테이블 정의 안됨
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
	 * @param userMbr
	 *            사용자 Value Object
	 * @return row - 결과값 1:성공, 0:실패
	 */
	private int doCancelMainDevice(UserMbr userMbr) {
		int row = 0;
		UserMbrDevice mainDevice = this.commonDAO.queryForObject("Device.findMainDevice", userMbr, UserMbrDevice.class);

		if (mainDevice != null) {
			// 휴대기기 이력 테이블 insert.
			row = this.commonDAO.update("Device.insertUpdateDeviceHistory", mainDevice);
			// 휴대기기 속성의 REP_DEVICE_YN = N
			row = this.commonDAO.update("Device.unsetDeviceYn", mainDevice);
		}
		return row;
	}

	/**
	 * <pre>
	 * 기기등록, 부가속성 등록 시키는 기능.
	 * </pre>
	 *
	 * @param createDeviceRequest
	 *            사용자 Value Object
	 * @return tempKey - Device Key
	 */
	private String doInsertDevice(CreateDeviceRequest createDeviceRequest) {
		int seq = this.commonDAO.queryForObject("Device.getUserSequence", null, Integer.class);
		String tempKey = "DE" + Utils.getLocalDateTime() + Utils.leftPadStringWithChar(Integer.toString(seq), 7, '0');

		UserMbrDevice userMbrDevice = createDeviceRequest.getUserMbrDevice();
		userMbrDevice.setDeviceKey(tempKey);
		this.commonDAO.insert("Device.createDevice", userMbrDevice);

		if (createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail() != null) {
			for (UserMbrDeviceDetail userMbrDeviceDetail : createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail()) {
				userMbrDeviceDetail.setUserKey(createDeviceRequest.getUserKey());
				userMbrDeviceDetail.setDeviceKey(tempKey);
				userMbrDeviceDetail.setSystemID(createDeviceRequest.getCommonRequest().getSystemID());
				userMbrDeviceDetail.setRegID(createDeviceRequest.getUserMbrDevice().getUserID());
				this.commonDAO.update("Device.updateDeviceDetail", userMbrDeviceDetail);
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
	 */
	private void doActivateDevice(UserMbrDevice userMbrDevice, CreateDeviceRequest createDeviceRequest) {
		// 휴대기기 이력 테이블 insert.
		this.commonDAO.update("Device.insertUpdateDeviceHistory", userMbrDevice);

		// auth_yn = Y
		createDeviceRequest.getUserMbrDevice().setDeviceKey(userMbrDevice.getDeviceKey());
		this.commonDAO.update("Device.doActivateDevice", createDeviceRequest.getUserMbrDevice());

		if (createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail() != null) {
			for (UserMbrDeviceDetail userMbrDeviceDetail : createDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail()) {
				userMbrDeviceDetail.setUserKey(createDeviceRequest.getUserKey());
				userMbrDeviceDetail.setDeviceKey(userMbrDevice.getDeviceKey());
				userMbrDeviceDetail.setSystemID(createDeviceRequest.getCommonRequest().getSystemID());
				userMbrDeviceDetail.setRegID(userMbrDevice.getUserID());
				this.commonDAO.update("Device.updateDeviceDetail", userMbrDeviceDetail);
			}
		}
	}

	/**
	 * <pre>
	 * 아이디 회원의 휴대기기를 auth_yn = N 처리.
	 * </pre>
	 *
	 * @param userMbrDevice
	 *            사용자 Value Object
	 * @param isDormant
	 *            휴면계정 유무
	 * @return row - 결과값 1:성공, 0:실패
	 */
	private int doInvalidDevice(UserMbrDevice userMbrDevice, String isDormant) {
		int row = 0;
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			// 휴대기기 이력 테이블 insert.
			row = dao.update("Device.insertUpdateDeviceHistory", userMbrDevice);
			if (row <= 0) {
				return row;
			}
		}

		// 휴대기기 삭제
		row = dao.update("Device.removeDevice", userMbrDevice);
		if (row <= 0) {
			return row;
		}

		// 휴대기기 부가속성 삭제
		UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setUserKey(userMbrDevice.getUserKey());
		userMbrDeviceDetail.setDeviceKey(userMbrDevice.getDeviceKey());
		row = dao.delete("removeDeviceExtraProfile", userMbrDeviceDetail);
		if (row <= 0) {
			row = 1;
		}

		// 휴대기기 설정정보 초기화
		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
		userMbrDeviceSet.setUserKey(userMbrDevice.getUserKey());
		userMbrDeviceSet.setDeviceKey(userMbrDevice.getDeviceKey());
		userMbrDeviceSet.setPinNo("");
		userMbrDeviceSet.setAuthCnt("");
		userMbrDeviceSet.setAuthLockYn("N");
		// TODO. 테스트를 위해 주석처리
		//this.commonDAO.delete("DeviceSet.modifyDeviceSet", userMbrDeviceSet);

		// 대표기기가 invalid된 경우 최신 등록 기기를 대표기기로 등록한다.
		if (StringUtils.equals(userMbrDevice.getIsPrimary(), Constant.TYPE_YN_Y)) {
			UserMbrDevice userMbrDeviceOrderByUpdDt = this.commonDAO.queryForObject("Device.searchDeviceOrderbyUpdDt", userMbrDevice, UserMbrDevice.class);
			if(userMbrDeviceOrderByUpdDt != null){
				row = dao.update("Device.insertUpdateDeviceHistory", userMbrDeviceOrderByUpdDt);
				if (row <= 0) {
					return row;
				}
				userMbrDeviceOrderByUpdDt.setIsPrimary(Constant.TYPE_YN_Y);
				row = dao.update("Device.updateDevice", userMbrDeviceOrderByUpdDt);
				if (row <= 0) {
					return row;
				}
			}
		}

		return row;
	}

	/**
	 * <pre>
	 * 무선회원을 탈퇴하는 기능.
	 * </pre>
	 *
	 * @param userMbrDevice
	 *            무선회원 휴대기기 Value Object
	 * @param isDormant
	 *            휴면계정유무
	 * @return row - 결과값 1:성공, 0:실패
	 */
	private int doRemoveMobileUser(UserMbrDevice userMbrDevice, String isDormant) {
		int row = 0;
		CommonDAO dao = null;
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			dao = this.commonDAO;
		} else {
			dao = this.idleDAO;
		}

		UserMbr userMbr = new UserMbr();
		userMbr.setUserKey(userMbrDevice.getUserKey());

		// 회원정보 변경전 이력저장
		if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
			row = dao.update("User.insertUpdateStatusHistory", userMbr);
			if (row <= 0) {
				return row;
			}
		}

		// 회원 탈퇴
		userMbr.setSecedeTypeCode(WithdrawClassCode.CHANGE_ID.getCode());
		userMbr.setUserMainStatus(MainStateCode.SECEDE.getCode());
		userMbr.setUserSubStatus(SubStateCode.WITHDRAW.getCode());
		userMbr.setSecedeDate(Utils.getLocalDateTimeinYYYYMMDD());
		row = dao.update("Device.removeOwner", userMbr);
		if (row <= 0) {
			return row;
		}

		// 휴대기기 변경전 이력 저장
		row = dao.update("Device.insertUpdateDeviceHistory", userMbrDevice);
		if (row <= 0) {
			return row;
		}

		// 휴대기기 auth_yn=N
		row = dao.update("Device.removeDevice", userMbrDevice);
		if (row <= 0) {
			return row;
		}

		// 휴대기기 부가속성 삭제
		UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setUserKey(userMbrDevice.getUserKey());
		userMbrDeviceDetail.setDeviceKey(userMbrDevice.getDeviceKey());
		row = dao.update("Device.removeDeviceExtraProfile", userMbrDevice);
		if (row <= 0) {
			row = 1;
		}

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
		this.doActivateDevice(tempDevice, createDeviceRequest);

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
	 * 타인의 휴대기기 목록 조회.
	 * </pre>
	 *
	 * @param keyType
	 * @param keyString
	 * @param userKey
	 * @param isMine
	 * @param authYn
	 * @return List<UserMbrDevice>
	 */
	private List<UserMbrDevice> doSearchDevice(String keyType, String keyString, String userKey, String isMine, String authYn) {
		SearchDeviceListRequest searchDeviceListRequest = new SearchDeviceListRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(keyType);
		key.setKeyString(keyString);
		keySearchList.add(key);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setUserKey(userKey);
		searchDeviceListRequest.setIsMine(isMine);
		searchDeviceListRequest.setIsUsed(authYn);
		List<UserMbrDevice> userMbrDeviceList = this.commonDAO.queryForList("Device.searchDeviceList3", searchDeviceListRequest, UserMbrDevice.class);
		List<UserMbrDevice> userMbrDormantDeviceList = this.idleDAO.queryForList("Device.searchDeviceList3", searchDeviceListRequest, UserMbrDevice.class);
		if ((userMbrDeviceList != null && userMbrDeviceList.size() > 0)
				|| (userMbrDormantDeviceList != null && userMbrDormantDeviceList.size() > 0)) {
			// 휴면DB에 존재하는 휴대기기정보 합치기
			if (userMbrDormantDeviceList != null && userMbrDormantDeviceList.size() > 0) {
				if (userMbrDeviceList == null)
					userMbrDeviceList = new ArrayList<UserMbrDevice>();
				for (UserMbrDevice userMbrDevice : userMbrDormantDeviceList) {
					userMbrDevice.setIsDormant(Constant.TYPE_YN_Y);
					userMbrDeviceList.add(userMbrDevice);
				}
			}
		}
		// 본인 단말 정보 조회(device_id or svc_mang_no) 결과가 2건이상인 경우 에러처리
		if(StringUtils.equals(isMine, Constant.TYPE_YN_Y) && userMbrDeviceList.size() > 1){
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// 휴대기기 부가속성 조회
		for(UserMbrDevice userMbrDevice : userMbrDeviceList){
			if(StringUtils.isBlank(userMbrDevice.getIsDormant()) || StringUtils.equals(userMbrDevice.getIsDormant(), Constant.TYPE_YN_N)){
				UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
				userMbrDeviceDetail.setUserKey(userMbrDevice.getUserKey());
				userMbrDeviceDetail.setDeviceKey(userMbrDevice.getDeviceKey());
				userMbrDevice.setUserMbrDeviceDetail(this.commonDAO.queryForList("Device.getExtraProfile2", userMbrDeviceDetail, UserMbrDeviceDetail.class));
			}else{
				UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
				userMbrDeviceDetail.setUserKey(userMbrDevice.getUserKey());
				userMbrDeviceDetail.setDeviceKey(userMbrDevice.getDeviceKey());
				userMbrDevice.setUserMbrDeviceDetail(this.idleDAO.queryForList("Device.getExtraProfile2", userMbrDeviceDetail, UserMbrDeviceDetail.class));
			}
		}
		return userMbrDeviceList;
	}

	/**
	 * <pre>
	 * 휴대기기 정보업데이트.
	 * request와 DB의 정보를 비교하여 다른 경우만 처리한다.
	 * </pre>
	 *
	 * @param systemID 시스템 ID
	 * @param userMbrDevice DB 휴대기기 정보
	 * @param userMbrDeviceForReq request 휴대기기 정보
	 * @param isDormant 휴면계정 유무
	 */
	private void updateDeviceInfo(String systemID, UserMbrDevice userMbrDevice, UserMbrDevice userMbrDeviceForReq, String isDormant){
		StringBuffer logBuf = new StringBuffer();
		String chgCaseCd = userMbrDeviceForReq.getChangeCaseCode() == null ? MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT :  userMbrDeviceForReq.getChangeCaseCode();

		if(userMbrDeviceForReq.getDeviceTelecom() != null && !StringUtils.equals(userMbrDevice.getDeviceTelecom(), userMbrDeviceForReq.getDeviceTelecom())){
			logBuf.append("[mno_cd]").append(userMbrDevice.getDeviceTelecom()).append("->").append(userMbrDeviceForReq.getDeviceTelecom());
		}
		if(userMbrDeviceForReq.getIsRecvSMS() != null && !StringUtils.equals(userMbrDevice.getIsRecvSMS(), userMbrDeviceForReq.getIsRecvSMS())){
			logBuf.append("[sms_recv_yn]").append(userMbrDevice.getIsRecvSMS()).append("->").append(userMbrDeviceForReq.getIsRecvSMS());
		}
		if(userMbrDeviceForReq.getDeviceSimNm() != null && !StringUtils.equals(userMbrDevice.getDeviceSimNm(), userMbrDeviceForReq.getDeviceSimNm())){
			logBuf.append("[device_sim_nm]").append(userMbrDevice.getDeviceSimNm()).append("->").append(userMbrDeviceForReq.getDeviceSimNm());
		}
		if(userMbrDeviceForReq.getSvcMangNum() != null && !StringUtils.equals(userMbrDevice.getSvcMangNum(), userMbrDeviceForReq.getSvcMangNum())){
			logBuf.append("[svc_mang_no]").append(userMbrDevice.getSvcMangNum()).append("->").append(userMbrDeviceForReq.getSvcMangNum());
		}
		if(userMbrDeviceForReq.getDeviceAccount() != null && !StringUtils.equals(userMbrDevice.getDeviceAccount(), userMbrDeviceForReq.getDeviceAccount())){
			logBuf.append("[device_acct]").append(userMbrDevice.getDeviceAccount()).append("->").append(userMbrDeviceForReq.getDeviceAccount());
		}

		// TODO. 휴대기기 이력 변경 코드 우선순위 처리함.
		if(userMbrDeviceForReq.getDeviceID() != null && !StringUtils.equals(userMbrDevice.getDeviceID(), userMbrDeviceForReq.getDeviceID())){
			chgCaseCd = MemberConstants.DEVICE_CHANGE_TYPE_DEVICEID_CHANGE;
			logBuf.append("[device_id]").append(userMbrDevice.getDeviceID()).append("->").append(userMbrDeviceForReq.getDeviceID());
		}
		if(userMbrDeviceForReq.getNativeID() != null && !StringUtils.equals(userMbrDevice.getNativeID(), userMbrDeviceForReq.getNativeID())) {
			if(StringUtils.isNotBlank(userMbrDeviceForReq.getNativeID())){
				chgCaseCd = MemberConstants.DEVICE_CHANGE_TYPE_IMEI_CHANGE;
			}
			logBuf.append("[device_natv_id]").append(userMbrDevice.getNativeID()).append("->").append(userMbrDeviceForReq.getNativeID());
		}
		if(userMbrDeviceForReq.getDeviceModelNo() != null && !StringUtils.equals(userMbrDevice.getDeviceModelNo(), userMbrDeviceForReq.getDeviceModelNo())){
			chgCaseCd = MemberConstants.DEVICE_CHANGE_TYPE_MODEL_CHANGE;
			logBuf.append("[device_model_no]").append(userMbrDevice.getDeviceModelNo()).append("->").append(userMbrDeviceForReq.getDeviceModelNo());
		}
		if(userMbrDeviceForReq.getMdn() != null && !StringUtils.equals(userMbrDevice.getMdn(), userMbrDeviceForReq.getMdn())){
			if(StringUtils.isNotBlank(userMbrDeviceForReq.getMdn())){
				chgCaseCd = MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_CHANGE;
			}
			logBuf.append("[mdn]").append(userMbrDevice.getMdn()).append("->").append(userMbrDeviceForReq.getMdn());
		}

		if(logBuf.length() > 0){
			userMbrDeviceForReq.setUserKey(userMbrDevice.getUserKey());
			userMbrDeviceForReq.setDeviceKey(userMbrDevice.getDeviceKey());
			userMbrDeviceForReq.setChangeCaseCode(chgCaseCd);
			if(StringUtils.equals(isDormant, Constant.TYPE_YN_Y)){
				this.idleDAO.update("Device.updateDevice", userMbrDeviceForReq);
			}else{
				this.commonDAO.update("Device.insertUpdateDeviceHistoryCode", userMbrDeviceForReq);
				this.commonDAO.update("Device.updateDevice", userMbrDeviceForReq);
			}
		}
		// 휴대기기 부가속성
		if (userMbrDeviceForReq.getUserMbrDeviceDetail() != null) {
			for (UserMbrDeviceDetail userMbrDeviceDetailForReq : userMbrDeviceForReq.getUserMbrDeviceDetail()) {
				for(UserMbrDeviceDetail userMbrDeviceDetailForDb : userMbrDevice.getUserMbrDeviceDetail()){
					if(StringUtils.equals(userMbrDeviceDetailForReq.getExtraProfile(), userMbrDeviceDetailForDb.getExtraProfile())){
						if(!StringUtils.equals(userMbrDeviceDetailForReq.getExtraProfileValue(), userMbrDeviceDetailForDb.getExtraProfileValue())){
							logBuf.append("[").append(userMbrDeviceDetailForReq.getExtraProfile()).append("]").append(userMbrDeviceDetailForDb.getExtraProfileValue()).append("->").append(userMbrDeviceDetailForReq.getExtraProfileValue());
							userMbrDeviceDetailForReq.setRegID(userMbrDevice.getUserID());
							userMbrDeviceDetailForReq.setUserKey(userMbrDevice.getUserKey());
							userMbrDeviceDetailForReq.setDeviceKey(userMbrDevice.getDeviceKey());
							userMbrDeviceDetailForReq.setSystemID(systemID);
							if(StringUtils.equals(isDormant, Constant.TYPE_YN_Y)){
								this.idleDAO.update("Device.updateDeviceDetail", userMbrDeviceDetailForReq);
							}else{
								this.commonDAO.update("Device.updateDeviceDetail", userMbrDeviceDetailForReq);
							}
						}
					}
				}
			}
		}
		if(logBuf.length() > 0){
			LOGGER.info("{} : 휴대기기 수정정보 : {}", userMbrDevice.getUserKey(), logBuf.toString());
		}else{
			LOGGER.info("{} : 수정된 휴대기기 정보 없음", userMbrDevice.getUserKey());
		}
	}

	@Override
	public ModifyDeviceResponse modifyDevice(ModifyDeviceRequest modifyDeviceRequest) {
		StringBuffer logBuf = new StringBuffer();
		String chgCaseCd = modifyDeviceRequest.getUserMbrDevice().getChangeCaseCode() == null ? MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT :  modifyDeviceRequest.getUserMbrDevice().getChangeCaseCode();
		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		if (StringUtils.isNotBlank(modifyDeviceRequest.getUserMbrDevice().getDeviceKey())) {
			keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
			keySearch.setKeyString(modifyDeviceRequest.getUserMbrDevice().getDeviceKey());
		} else {
			if (StringUtils.isNotBlank(modifyDeviceRequest.getUserMbrDevice().getDeviceID())) {
				keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
				keySearch.setKeyString(modifyDeviceRequest.getUserMbrDevice().getDeviceID());
			} else {
				keySearch.setKeyType(Constant.SEARCH_TYPE_MDN);
				keySearch.setKeyString(modifyDeviceRequest.getUserMbrDevice().getMdn());
			}
		}
		keySearchList.add(keySearch);
		searchDeviceRequest.setKeySearchList(keySearchList);
		searchDeviceRequest.setUserKey(modifyDeviceRequest.getUserKey());
		SearchDeviceResponse searchDeviceResponse = this.commonDAO.queryForObject("Device.searchDevice", searchDeviceRequest,
				SearchDeviceResponse.class);
		if (searchDeviceResponse == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		UserMbrDevice updateMbrDevice = new UserMbrDevice();
		updateMbrDevice.setUserKey(modifyDeviceRequest.getUserKey());
		updateMbrDevice.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());

		// 통신사
		if (modifyDeviceRequest.getUserMbrDevice().getDeviceTelecom() != null) {
			logBuf.append("[mno_cd]").append(searchDeviceResponse.getUserMbrDevice().getDeviceTelecom()).append("->").append(modifyDeviceRequest.getUserMbrDevice().getDeviceTelecom());
			updateMbrDevice.setDeviceTelecom(modifyDeviceRequest.getUserMbrDevice().getDeviceTelecom());
		}

		// 단말모델
		if (modifyDeviceRequest.getUserMbrDevice().getDeviceModelNo() != null) {
			logBuf.append("[device_model_cd]").append(searchDeviceResponse.getUserMbrDevice().getDeviceModelNo()).append("->").append(modifyDeviceRequest.getUserMbrDevice().getDeviceModelNo());
			updateMbrDevice.setDeviceModelNo(modifyDeviceRequest.getUserMbrDevice().getDeviceModelNo());
		}

		// 기기계정(gmail)
		if (modifyDeviceRequest.getUserMbrDevice().getDeviceAccount() != null) {
			logBuf.append("[device_acct]").append(searchDeviceResponse.getUserMbrDevice().getDeviceAccount()).append("->").append(modifyDeviceRequest.getUserMbrDevice().getDeviceAccount());
			updateMbrDevice.setDeviceAccount(modifyDeviceRequest.getUserMbrDevice().getDeviceAccount());
		}

		// 가입채널코드
		if (modifyDeviceRequest.getUserMbrDevice().getJoinId() != null) {
			logBuf.append("[entry_chnl_cd]").append(searchDeviceResponse.getUserMbrDevice().getJoinId()).append("->").append(modifyDeviceRequest.getUserMbrDevice().getJoinId());
			updateMbrDevice.setJoinId(modifyDeviceRequest.getUserMbrDevice().getJoinId());
		}

		// SMS 수신 여부
		if (modifyDeviceRequest.getUserMbrDevice().getIsRecvSMS() != null) {
			logBuf.append("[sms_recv_yn]").append(searchDeviceResponse.getUserMbrDevice().getIsRecvSMS()).append("->").append(modifyDeviceRequest.getUserMbrDevice().getIsRecvSMS());
			updateMbrDevice.setIsRecvSMS(modifyDeviceRequest.getUserMbrDevice().getIsRecvSMS());
		}

		// 기기 고유ID(imei)
		if (modifyDeviceRequest.getUserMbrDevice().getNativeID() != null) {
			logBuf.append("[device_natv_id]").append(searchDeviceResponse.getUserMbrDevice().getNativeID()).append("->").append(modifyDeviceRequest.getUserMbrDevice().getNativeID());
			chgCaseCd = MemberConstants.DEVICE_CHANGE_TYPE_IMEI_CHANGE;
			updateMbrDevice.setNativeID(modifyDeviceRequest.getUserMbrDevice().getNativeID());
		}

		// 서비스관리번호
		if (modifyDeviceRequest.getUserMbrDevice().getSvcMangNum() != null) {
			logBuf.append("[svc_mang_no]").append(searchDeviceResponse.getUserMbrDevice().getSvcMangNum()).append("->").append(modifyDeviceRequest.getUserMbrDevice().getSvcMangNum());
			updateMbrDevice.setSvcMangNum(modifyDeviceRequest.getUserMbrDevice().getSvcMangNum());
		}

		updateMbrDevice.setChangeCaseCode(chgCaseCd);
		this.commonDAO.update("Device.insertUpdateDeviceHistory", updateMbrDevice);
		this.commonDAO.update("Device.updateDevice", updateMbrDevice);

		// 휴대기기 부가속성
		if (modifyDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail() != null) {
			for (UserMbrDeviceDetail userMbrDeviceDetailForReq : modifyDeviceRequest.getUserMbrDevice().getUserMbrDeviceDetail()) {
				for(UserMbrDeviceDetail userMbrDeviceDetailForDb : searchDeviceResponse.getUserMbrDevice().getUserMbrDeviceDetail()){
					if(StringUtils.equals(userMbrDeviceDetailForReq.getExtraProfile(), userMbrDeviceDetailForDb.getExtraProfile())){
						logBuf.append("[").append(userMbrDeviceDetailForReq.getExtraProfile()).append("]").append(userMbrDeviceDetailForDb.getExtraProfileValue()).append("->").append(userMbrDeviceDetailForReq.getExtraProfileValue());
						userMbrDeviceDetailForReq.setUserKey(userMbrDeviceDetailForDb.getUserKey());
						userMbrDeviceDetailForReq.setDeviceKey(userMbrDeviceDetailForDb.getDeviceKey());
						userMbrDeviceDetailForReq.setSystemID(modifyDeviceRequest.getCommonRequest().getSystemID());
						userMbrDeviceDetailForReq.setRegID(searchDeviceResponse.getUserMbrDevice().getUserID());
						this.commonDAO.update("Device.updateDeviceDetail", userMbrDeviceDetailForReq);
					}
				}
			}
		}

		LOGGER.info("휴대기기 수정정보 : {}", logBuf.toString());

		ModifyDeviceResponse modifyDeviceResponse = new ModifyDeviceResponse();
		modifyDeviceResponse.setUserKey(modifyDeviceRequest.getUserKey());
		modifyDeviceResponse.setDeviceKey(searchDeviceResponse.getUserMbrDevice().getDeviceKey());
		return modifyDeviceResponse;
	}

}
