/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.user.sci;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.*;
import com.skplanet.storeplatform.member.user.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;

/**
 * 휴대기기 기능을 제공하는 Controller
 * 
 * Updated on : 2013. 12. 26. Updated by : wisestone_mikepark
 */
@LocalSCI
public class DeviceSCIController implements DeviceSCI {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSCIController.class);

	@Autowired
	private DeviceService device;

	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * <pre>
	 * 휴대기기를 등록 또는 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createDeviceRequest
	 *            휴대기기 등록 요청 Value Object
	 * @return CreateDeviceResponse - 휴대기기 등록 응답 Value Object
	 */
	@Override
	public CreateDeviceResponse createDevice(CreateDeviceRequest createDeviceRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("DeviceSCIController 휴대기기 컨트롤러 - 휴대기기 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 createDeviceRequest : {}", createDeviceRequest);

		CreateDeviceResponse createDeviceResponse = null;

		// 입력 파라미터가 없음
		if (createDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (createDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라메터 체크
		if (createDeviceRequest.getUserMbrDevice() == null
				|| createDeviceRequest.getUserKey() == null
				|| createDeviceRequest.getUserMbrDevice().getDeviceModelNo() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			createDeviceResponse = this.device.createDevice(createDeviceRequest);
			if (createDeviceResponse == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.fail", ""));
			}
		} catch (StorePlatformException ex) {
			throw ex;
		}

		return createDeviceResponse;
	}

	/**
	 * 등록된 휴대기기 목록 조회.
	 * 
	 * @param searchDeviceListRequest
	 *            사용자 휴대기기 목록조회 요청 Value Object
	 * @return SearchDeviceListResponse - 사용자 휴대기기 목록조회 응답 Value Object
	 */
	@Override
	public SearchDeviceListResponse searchDeviceList(SearchDeviceListRequest searchDeviceListRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListResponse searchDeviceListResponse = null;

		// 입력 파라미터가 없음
		if (searchDeviceListRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchDeviceListRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, keySearch
		if (searchDeviceListRequest.getKeySearchList() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터, isMainDevice
		if (searchDeviceListRequest.getIsMainDevice() == null
				|| searchDeviceListRequest.getIsMainDevice().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터, keySearchList
		if (searchDeviceListRequest.getKeySearchList() == null
				|| searchDeviceListRequest.getKeySearchList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 in List, keySearch
		List<KeySearch> keySearchList = searchDeviceListRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {

			// 필수 파라미터
			if (keySearch.getKeyType() == null || keySearch.getKeyType().length() <= 0
					|| keySearch.getKeyString() == null || keySearch.getKeyString().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 잘못된 키값
			if (!keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_USER_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_SVC_MANG_NO)
                    && !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_MDN)) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			}
		}

		try {

			searchDeviceListResponse = this.device.searchDeviceList(searchDeviceListRequest);

			if (searchDeviceListResponse != null) {
				if (searchDeviceListResponse.getUserMbrDevice() != null) {
					List<UserMbrDevice> userMbrDeviceList = searchDeviceListResponse.getUserMbrDevice();
					if (userMbrDeviceList.size() == 0)
						throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
				}
				searchDeviceListResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return searchDeviceListResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}
	}

	/**
	 * <pre>
	 * 등록된 휴대기기를 삭제하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeDeviceRequest
	 *            휴대기기 삭제 요청 Value Object
	 * @return RemoveDeviceResponse - 휴대기기 삭제 응답 Value Object
	 */
	@Override
	public RemoveDeviceResponse removeDevice(RemoveDeviceRequest removeDeviceRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("휴대기기 컨트롤러 - 휴대기기 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 removeDeviceRequest : {}", removeDeviceRequest);

		// TLog
		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SC_MEM_0008");
			}
		});

		RemoveDeviceResponse removeDeviceResponse = null;

		// 입력 파라미터가 없음
		if (removeDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removeDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자키 없음
		if (removeDeviceRequest.getUserKey() == null || removeDeviceRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		// 수정 또는 추가할 기기 없음
		if (removeDeviceRequest.getDeviceKey() == null || removeDeviceRequest.getDeviceKey().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
		}

		// TLog
		final String insdusermbrno = removeDeviceRequest.getUserKey();

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.insd_usermbr_no(insdusermbrno);
			}
		});

		try {
			removeDeviceResponse = this.device.removeDevice(removeDeviceRequest);

			if (removeDeviceResponse == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.fail", ""));
			}
		} catch (StorePlatformException ex) {
			throw ex;
		}
		
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

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("디바이스 컨트롤러 - 휴대기기 개별 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### client에서 받은값 : {}", searchDeviceRequest);

		SearchDeviceResponse searchDeviceResponse = null;

		// 입력 파라미터가 없음
		if (searchDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, keySearch
		if (searchDeviceRequest.getKeySearchList() == null || searchDeviceRequest.getKeySearchList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 in List, keySearch
		List<KeySearch> keySearchList = searchDeviceRequest.getKeySearchList();
		for (KeySearch keySearch : keySearchList) {

			// 필수 파라미터
			if (keySearch.getKeyType() == null || keySearch.getKeyType().length() <= 0
					|| keySearch.getKeyString() == null || keySearch.getKeyString().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}

			// 잘못된 키값
			if (!keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_KEY)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_DEVICE_ID)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_SVC_MANG_NO)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_MDN)
                    && !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_AUTHORIZE_SVC_MANG_NO)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_AUTHORIZE_MDN)
					&& !keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_AUTHORIZE_DEVICE_ID)) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			}
		}

		// 검색 조건에 서비스 관리번호가 포함되었는지 여부
//		boolean isSvcMangNoExist = false;
//		for (KeySearch keySearch : keySearchList) {
//			if (keySearch.getKeyType().equalsIgnoreCase(Constant.SEARCH_TYPE_SVC_MANG_NO)) {
//				isSvcMangNoExist = true;
//			}
//		}

		// 검색 조건에 서비스 관리번호가 존재하는 경우 userKey가 필수아님, 서비스 관리번호로만 조회
//		if (isSvcMangNoExist) {
//			// 사용자키 없음
//			if (searchDeviceRequest.getUserKey() == null || searchDeviceRequest.getUserKey().length() <= 0) {
//				throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
//			}
//		}

		try {

			searchDeviceResponse = this.device.searchDevice(searchDeviceRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchDeviceResponse;
	}

	/**
	 * <pre>
	 * 회원의 등록된 휴대기기중 한대를 대표단말로 설정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param setMainDeviceRequest
	 *            휴대기기 대표단말 설정 요청 Value Object
	 * @return SetMainDeviceResponse - 휴대기기 대표단말 설정 응답 Value Object
	 */
	@Override
	public SetMainDeviceResponse setMainDevice(SetMainDeviceRequest setMainDeviceRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("디바이스 컨트롤러 - 휴대기기 대표단말 설정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 컨트롤러에서 받은값 : {}", setMainDeviceRequest);

		SetMainDeviceResponse setMainDeviceResponse;

		// 입력 파라미터가 없음
		if (setMainDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (setMainDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자키 없음
		if (setMainDeviceRequest.getUserKey() == null || setMainDeviceRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		// 필수 파라미터 없음, deviceKey
		if (setMainDeviceRequest.getDeviceKey() == null || setMainDeviceRequest.getDeviceKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			setMainDeviceResponse = this.device.updateMainDevice(setMainDeviceRequest);

			if (setMainDeviceResponse == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.fail", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return setMainDeviceResponse;

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

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("디바이스 컨트롤러 - 변동성 대상 체크");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### client에서 받은값 : {}", checkSaveNSyncRequest);

		CheckSaveNSyncResponse checkSaveNSyncResponse = null;

		// 입력 파라미터가 없음
		if (checkSaveNSyncRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (checkSaveNSyncRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, deviceID
		if (checkSaveNSyncRequest.getDeviceID() == null || checkSaveNSyncRequest.getDeviceID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			checkSaveNSyncResponse = this.device.checkSaveNSync(checkSaveNSyncRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

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
	public ReviveUserResponse reviveUser(ReviveUserRequest reviveUserRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("디바이스 컨트롤러 - 회원복구");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### client에서 받은값 : {}", reviveUserRequest);

		ReviveUserResponse reviveUserResponse = null;

		// 입력 파라미터가 없음
		if (reviveUserRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (reviveUserRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (reviveUserRequest.getCommonRequest().getTenantID() == null
				|| reviveUserRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, mbrNo, userKey
		if (reviveUserRequest.getImMbrNo() == null || reviveUserRequest.getImMbrNo().length() <= 0 // mbrNo
				|| reviveUserRequest.getUserKey() == null || reviveUserRequest.getUserKey().length() <= 0 // userKey
		) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			reviveUserResponse = this.device.updateReviveUser(reviveUserRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("디바이스 컨트롤러 - 이전 휴대기기 개별 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### client에서 받은값 : {}", searchAllDeviceRequest);

		SearchAllDeviceResponse searchAllDeviceResponse = null;

		// 입력 파라미터가 없음
		if (searchAllDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchAllDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 사용자키 없음
		if (searchAllDeviceRequest.getUserKey() == null || searchAllDeviceRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 수정 또는 추가할 기기 없음
		if (searchAllDeviceRequest.getDeviceKey() == null || searchAllDeviceRequest.getDeviceKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchAllDeviceResponse = this.device.searchAllDevice(searchAllDeviceRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

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

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 휴대기기 소유자이력 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceOwnerResponse searchDeviceOwnerResponse = null;

		// 입력 파라미터가 없음
		if (searchDeviceOwnerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchDeviceOwnerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, Device ID,  mdn 둘 중 하나.
		if ((searchDeviceOwnerRequest.getDeviceID() == null || searchDeviceOwnerRequest.getDeviceID().length() <= 0)
				&& (searchDeviceOwnerRequest.getMdn() == null || searchDeviceOwnerRequest.getMdn().length() <= 0)) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound",""));
		}

		try {

			searchDeviceOwnerResponse = this.device.searchDeviceOwner(searchDeviceOwnerRequest);

			if (searchDeviceOwnerResponse != null) {
				searchDeviceOwnerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return searchDeviceOwnerResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}

	}

	/**
	 * <pre>
	 * 등록된 단말 정보 조회 기능을 제공 한다(탈퇴 포함).
	 * </pre>
	 * 
	 * @param searchOrderDeviceRequest
	 *            - 등록된 단말 정보 조회 요청 Value Object
	 * @return SearchOrderDeviceResponse - 등록된 단말 정보 조회 응답 Value Object
	 */
	@Override
	public SearchOrderDeviceResponse searchOrderDevice(SearchOrderDeviceRequest searchOrderDeviceRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 등록된 단말 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchOrderDeviceResponse searchOrderDeviceResponse = null;

		// 입력 파라미터가 없음
		if (searchOrderDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchOrderDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, deviceKey, userKey
		if (searchOrderDeviceRequest.getDeviceKey() == null || searchOrderDeviceRequest.getDeviceKey().length() <= 0
				|| searchOrderDeviceRequest.getUserKey() == null || searchOrderDeviceRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			// device, device_wd 조회
			searchOrderDeviceResponse = this.device.searchOrderDevice(searchOrderDeviceRequest);

			if (searchOrderDeviceResponse != null) {
				searchOrderDeviceResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return searchOrderDeviceResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}

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
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 등록된 단말 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateDeviceManagementResponse updateDeviceManagementResponse = null;

		// 입력 파라미터가 없음
		if (updateDeviceManagementRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateDeviceManagementRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터, deviceKey, userKey
		if (updateDeviceManagementRequest.getDeviceKey() == null
				|| updateDeviceManagementRequest.getDeviceKey().length() <= 0
				|| updateDeviceManagementRequest.getUserKey() == null
				|| updateDeviceManagementRequest.getUserKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			updateDeviceManagementResponse = this.device.updateDeviceManagement(updateDeviceManagementRequest);

			if (updateDeviceManagementResponse != null) {
				updateDeviceManagementResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return updateDeviceManagementResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}
	}

	@Override
	public ModifyDeviceResponse modifyDevice(ModifyDeviceRequest modifyDeviceRequest) {
		ModifyDeviceResponse modifyDeviceResponse = null;

		// 입력 파라미터가 없음
		if (modifyDeviceRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (modifyDeviceRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 필수 파라미터 userKey
		if (modifyDeviceRequest.getUserKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// deviceId, mdn 수정시에는 deviceKey가 필수 파라메터
		if(modifyDeviceRequest.isUpdDeviceId() && modifyDeviceRequest.getUserMbrDevice().getDeviceKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		if(modifyDeviceRequest.getUserMbrDevice().getDeviceKey() == null
				&& modifyDeviceRequest.getUserMbrDevice().getDeviceID() == null
				&& modifyDeviceRequest.getUserMbrDevice().getMdn() == null){
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			modifyDeviceResponse = this.device.modifyDevice(modifyDeviceRequest);

			if (modifyDeviceResponse != null) {
				modifyDeviceResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return modifyDeviceResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}
	}

}
