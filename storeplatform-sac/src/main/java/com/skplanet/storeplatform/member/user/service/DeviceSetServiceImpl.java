/**
 * 
 */
package com.skplanet.storeplatform.member.user.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.util.RandomString;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDevicePinRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDevicePinResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDevicePinRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDevicePinResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDevicePinRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDevicePinResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDeviceSetInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDeviceSetInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDevicePinRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDevicePinResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceSetInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceSetInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferDeviceSetInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferDeviceSetInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceSet;
import com.skplanet.storeplatform.member.common.crypto.SHACipher;

/**
 * 휴대기기 설정 기능을 제공하는 ServiceImpl
 * 
 * Updated on : 2014. 10. 30. Updated by : Rejoice, Burkhan
 */
@Service
public class DeviceSetServiceImpl implements DeviceSetService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSetServiceImpl.class);

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

	/**
	 * <pre>
	 * PIN 등록기능을 제공한다.
	 * </pre>
	 * 
	 * @param createDevicePinRequest
	 *            - PIN 등록 요청 Value Object
	 * @return CreateDevicePinResponse - PIN 등록 응답 Value Object
	 */
	@Override
	public CreateDevicePinResponse createDevicePin(CreateDevicePinRequest createDevicePinRequest) {
		LOGGER.debug("### createDevicePinRequest : {}", createDevicePinRequest.toString());

		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();

		List<KeySearch> keySearchList = createDevicePinRequest.getKeySearchList();
		userMbrDeviceSet.setKeySearchList(keySearchList);
		userMbrDeviceSet.setSystemID(createDevicePinRequest.getCommonRequest().getSystemID());

		userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchUserKey", userMbrDeviceSet,
				UserMbrDeviceSet.class);

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		LOGGER.debug("### userMbrDevice : {}", userMbrDeviceSet);

		UserMbrDeviceSet checkUserMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchDeviceSetInfo",
				userMbrDeviceSet, UserMbrDeviceSet.class);

		// 중복 확인
		if (checkUserMbrDeviceSet != null && StringUtils.isNotBlank(checkUserMbrDeviceSet.getPinNo())) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.duplicatedDeviceID", ""));
		}

		CreateDevicePinResponse createDevicePinResponse = new CreateDevicePinResponse();

		SHACipher cipher = new SHACipher();
		try {
			// PIN 번호 암호화
			userMbrDeviceSet.setPinNo(cipher.getHash(createDevicePinRequest.getPinNo()));
			userMbrDeviceSet.setAuthLockYn(Constant.TYPE_YN_N);
			userMbrDeviceSet.setAuthCnt("0");
		} catch (NoSuchAlgorithmException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (UnsupportedEncodingException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (StorePlatformException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		if (checkUserMbrDeviceSet == null) { // 등록
			Integer row = (Integer) this.commonDAO.insert("DeviceSet.createDevicePin", userMbrDeviceSet);
			LOGGER.debug("### row : {}", row);

			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			LOGGER.debug("### setMainDevice row : {}", row);
		} else { // 수정
			Integer row = (Integer) this.commonDAO.insert("DeviceSet.modifyDeviceSet", userMbrDeviceSet);
			LOGGER.debug("### row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			LOGGER.debug("### setMainDevice row : {}", row);
		}

		// 공통 응답
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
				LocaleContextHolder.getLocale()));
		commonResponse.setResultMessage(this.messageSourceAccessor.getMessage("response.ResultMessage.success", null,
				"성공", LocaleContextHolder.getLocale()));

		createDevicePinResponse.setCommonResponse(commonResponse);
		createDevicePinResponse.setDeviceId(userMbrDeviceSet.getDeviceID());
		createDevicePinResponse.setDeviceKey(userMbrDeviceSet.getDeviceKey());
		createDevicePinResponse.setUserKey(userMbrDeviceSet.getUserKey());

		LOGGER.debug("### DB에서 받아온 결과 : {}", createDevicePinResponse);

		return createDevicePinResponse;
	}

	/**
	 * <pre>
	 * PIN 수정기능을 제공한다.
	 * </pre>
	 * 
	 * @param modifyDevicePinRequest
	 *            - PIN 수정 요청 Value Object
	 * @return ModifyDevicePinResponse - PIN 수정 응답 Value Object
	 */
	@Override
	public ModifyDevicePinResponse modifyDevicePin(ModifyDevicePinRequest modifyDevicePinRequest) {
		LOGGER.debug("### modifyDevicePinRequest : {}", modifyDevicePinRequest.toString());

		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();

		List<KeySearch> keySearchList = modifyDevicePinRequest.getKeySearchList();
		userMbrDeviceSet.setKeySearchList(keySearchList);
		userMbrDeviceSet.setSystemID(modifyDevicePinRequest.getCommonRequest().getSystemID());

		userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchUserKey", userMbrDeviceSet,
				UserMbrDeviceSet.class);

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		LOGGER.debug("### userMbrDevice : {}", userMbrDeviceSet);
		String deviceId = userMbrDeviceSet.getDeviceID();
		userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchDeviceSetInfo", userMbrDeviceSet,
				UserMbrDeviceSet.class);

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		ModifyDevicePinResponse modifyDevicePinResponse = new ModifyDevicePinResponse();

		SHACipher cipher = new SHACipher();
		String pinNo = "";
		String newPinNo = "";
		try {
			pinNo = cipher.getHash(modifyDevicePinRequest.getPinNo());
			newPinNo = cipher.getHash(modifyDevicePinRequest.getNewPinNo());
		} catch (NoSuchAlgorithmException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (UnsupportedEncodingException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (StorePlatformException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}
		if (StringUtils.equals(pinNo, userMbrDeviceSet.getPinNo())) {
			userMbrDeviceSet.setPinNo(newPinNo);
			userMbrDeviceSet.setAuthLockYn(Constant.TYPE_YN_N);
			userMbrDeviceSet.setAuthCnt("0");
		} else {
			// 요청한 PIN가 일치하지 않음
			throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
		}

		Integer row = (Integer) this.commonDAO.insert("DeviceSet.modifyDeviceSet", userMbrDeviceSet);
		LOGGER.debug("### row : {}", row);

		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}
		LOGGER.debug("### setMainDevice row : {}", row);

		// 공통 응답
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
				LocaleContextHolder.getLocale()));
		commonResponse.setResultMessage(this.messageSourceAccessor.getMessage("response.ResultMessage.success", null,
				"성공", LocaleContextHolder.getLocale()));

		modifyDevicePinResponse.setCommonResponse(commonResponse);
		modifyDevicePinResponse.setDeviceId(deviceId);
		modifyDevicePinResponse.setDeviceKey(userMbrDeviceSet.getDeviceKey());
		modifyDevicePinResponse.setUserKey(userMbrDeviceSet.getUserKey());

		LOGGER.debug("### DB에서 받아온 결과 : {}", modifyDevicePinResponse);

		return modifyDevicePinResponse;
	}

	/**
	 * <pre>
	 * PIN 번호 초기화 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDevicePinRequest
	 *            - PIN 초기화 요청 Value Object
	 * @return SearchDevicePinResponse - PIN 초기화 응답 Value Object
	 */
	@Override
	public SearchDevicePinResponse searchDevicePin(SearchDevicePinRequest searchDevicePinRequest) {
		LOGGER.debug("### searchDevicePinRequest : {}", searchDevicePinRequest.toString());

		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();

		List<KeySearch> keySearchList = searchDevicePinRequest.getKeySearchList();
		userMbrDeviceSet.setKeySearchList(keySearchList);
		userMbrDeviceSet.setSystemID(searchDevicePinRequest.getCommonRequest().getSystemID());

		userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchUserKey", userMbrDeviceSet,
				UserMbrDeviceSet.class);

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		LOGGER.debug("### userMbrDevice : {}", userMbrDeviceSet);

		String deviceId = userMbrDeviceSet.getDeviceID();
		userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchDeviceSetInfo", userMbrDeviceSet,
				UserMbrDeviceSet.class);

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		SearchDevicePinResponse searchDevicePinResponse = new SearchDevicePinResponse();

		SHACipher cipher = new SHACipher();
		String newPinNo = RandomString.getString(4, RandomString.TYPE_NUMBER);
		try {
			// PIN 번호 설정
			userMbrDeviceSet.setPinNo(cipher.getHash(newPinNo));
			userMbrDeviceSet.setAuthLockYn(Constant.TYPE_YN_N);
			userMbrDeviceSet.setAuthCnt("0");
		} catch (NoSuchAlgorithmException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (UnsupportedEncodingException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (StorePlatformException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		Integer row = (Integer) this.commonDAO.insert("DeviceSet.modifyDeviceSet", userMbrDeviceSet);
		LOGGER.debug("### row : {}", row);

		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}
		LOGGER.debug("### setMainDevice row : {}", row);

		// 공통 응답
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
				LocaleContextHolder.getLocale()));
		commonResponse.setResultMessage(this.messageSourceAccessor.getMessage("response.ResultMessage.success", null,
				"성공", LocaleContextHolder.getLocale()));

		searchDevicePinResponse.setCommonResponse(commonResponse);
		searchDevicePinResponse.setDeviceId(deviceId);
		searchDevicePinResponse.setDeviceKey(userMbrDeviceSet.getDeviceKey());
		searchDevicePinResponse.setUserKey(userMbrDeviceSet.getUserKey());
		searchDevicePinResponse.setPinNo(newPinNo);

		LOGGER.debug("### DB에서 받아온 결과 : {}", searchDevicePinResponse);

		return searchDevicePinResponse;
	}

	/**
	 * <pre>
	 * PIN 확인 기능을 제공한다.
	 * </pre>
	 * 
	 * @param checkDevicePinRequest
	 *            - PIN 확인 요청 Value Object
	 * @return CheckDevicePinResponse - PIN 확인 응답 Value Object
	 */
	@Override
	public CheckDevicePinResponse checkDevicePin(CheckDevicePinRequest checkDevicePinRequest) {
		LOGGER.debug("### checkDevicePinRequest : {}", checkDevicePinRequest.toString());

		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();

		List<KeySearch> keySearchList = checkDevicePinRequest.getKeySearchList();
		userMbrDeviceSet.setKeySearchList(keySearchList);
		userMbrDeviceSet.setSystemID(checkDevicePinRequest.getCommonRequest().getSystemID());

		userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchUserKey", userMbrDeviceSet,
				UserMbrDeviceSet.class);

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		LOGGER.debug("### userMbrDevice : {}", userMbrDeviceSet);

		String deviceId = userMbrDeviceSet.getDeviceID();
		userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchDeviceSetInfo", userMbrDeviceSet,
				UserMbrDeviceSet.class);

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		CheckDevicePinResponse checkDevicePinResponse = new CheckDevicePinResponse();

		SHACipher cipher = new SHACipher();
		String pinNo = "";
		try {
			pinNo = cipher.getHash(checkDevicePinRequest.getPinNo());
		} catch (NoSuchAlgorithmException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (UnsupportedEncodingException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (StorePlatformException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// 인증 실패 횟수
		int authCnt = Integer.parseInt(StringUtils.defaultString(userMbrDeviceSet.getAuthCnt(), "0"));
		int failCnt = 0;
		if (StringUtils.equals(Constant.TYPE_YN_N,
				StringUtils.defaultString(userMbrDeviceSet.getAuthLockYn(), Constant.TYPE_YN_N))
				&& !StringUtils.equals(pinNo, userMbrDeviceSet.getPinNo())) {
			// 실패
			authCnt++;
			failCnt = authCnt;
			if (authCnt == 5) {
				userMbrDeviceSet.setAuthLockYn(Constant.TYPE_YN_Y);
			}
			userMbrDeviceSet.setAuthCnt(String.valueOf(authCnt));
		} else if (StringUtils.equals(pinNo, userMbrDeviceSet.getPinNo())
				&& StringUtils.equals(Constant.TYPE_YN_N,
						StringUtils.defaultString(userMbrDeviceSet.getAuthLockYn(), Constant.TYPE_YN_N))) {
			userMbrDeviceSet.setAuthCnt("0");
		}
		int chRow = (Integer) this.commonDAO.insert("DeviceSet.modifyDeviceSet", userMbrDeviceSet);
		if (chRow <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		// 공통 응답
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
				LocaleContextHolder.getLocale()));
		commonResponse.setResultMessage(this.messageSourceAccessor.getMessage("response.ResultMessage.success", null,
				"성공", LocaleContextHolder.getLocale()));

		checkDevicePinResponse.setCommonResponse(commonResponse);
		checkDevicePinResponse.setDeviceId(deviceId);
		checkDevicePinResponse.setDeviceKey(userMbrDeviceSet.getDeviceKey());
		checkDevicePinResponse.setUserKey(userMbrDeviceSet.getUserKey());
		checkDevicePinResponse.setUserMbrDeviceSet(userMbrDeviceSet);
		checkDevicePinResponse.setFailCnt(String.valueOf(failCnt));
		LOGGER.debug("### DB에서 받아온 결과 : {}", checkDevicePinResponse);

		return checkDevicePinResponse;
	}

	/**
	 * <pre>
	 * 휴대기기 설정 정보 조회 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceSetInfoRequest
	 *            - 휴대기기 설정 정보 조회 요청 Value Object
	 * @return SearchDeviceSetInfoResponse- 휴대기기 설정 정보 조회 응답 Value Object
	 */
	@Override
	public SearchDeviceSetInfoResponse searchDeviceSetInfo(SearchDeviceSetInfoRequest searchDeviceSetInfoRequest) {
		LOGGER.debug("### searchDeviceSetInfoRequest : {}", searchDeviceSetInfoRequest.toString());

		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();

		List<KeySearch> keySearchList = searchDeviceSetInfoRequest.getKeySearchList();
		userMbrDeviceSet.setKeySearchList(keySearchList);
		userMbrDeviceSet.setSystemID(searchDeviceSetInfoRequest.getCommonRequest().getSystemID());

		userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchUserKey", userMbrDeviceSet,
				UserMbrDeviceSet.class);

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		LOGGER.debug("### userMbrDevice : {}", userMbrDeviceSet);

		UserMbrDeviceSet searchUserMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchDeviceSetInfo",
				userMbrDeviceSet, UserMbrDeviceSet.class);

		if (searchUserMbrDeviceSet == null) {
			searchUserMbrDeviceSet = new UserMbrDeviceSet();
			searchUserMbrDeviceSet.setIsPinRetry(Constant.TYPE_YN_Y);
			searchUserMbrDeviceSet.setIsAutoUpdate(Constant.TYPE_YN_Y);
			searchUserMbrDeviceSet.setIsAutoUpdateWifi(Constant.TYPE_YN_Y);
			searchUserMbrDeviceSet.setIsLoginLock(Constant.TYPE_YN_N);
			searchUserMbrDeviceSet.setIsAdultLock(Constant.TYPE_YN_Y);
			searchUserMbrDeviceSet.setIsDownloadWifiOnly(Constant.TYPE_YN_Y);
			searchUserMbrDeviceSet.setIsIcasAuth(Constant.TYPE_YN_N);
		}

		SearchDeviceSetInfoResponse searchDeviceSetInfoResponse = new SearchDeviceSetInfoResponse();

		// 공통 응답
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
				LocaleContextHolder.getLocale()));
		commonResponse.setResultMessage(this.messageSourceAccessor.getMessage("response.ResultMessage.success", null,
				"성공", LocaleContextHolder.getLocale()));
		searchUserMbrDeviceSet
				.setIsPin(searchUserMbrDeviceSet.getPinNo() == null ? Constant.TYPE_YN_N : Constant.TYPE_YN_Y);
		searchDeviceSetInfoResponse.setCommonResponse(commonResponse);
		searchDeviceSetInfoResponse.setUserMbrDeviceSet(searchUserMbrDeviceSet);

		LOGGER.debug("### DB에서 받아온 결과 : {}", searchDeviceSetInfoResponse);

		return searchDeviceSetInfoResponse;
	}

	/**
	 * <pre>
	 * 휴대기기 설정 정보 등록/수정 기능을 제공한다.
	 * </pre>
	 * 
	 * @param modifyDeviceSetInfoRequest
	 *            - 휴대기기 설정 등록/수정 요청 Value Object
	 * @return ModifyDeviceSetInfoResponse - 휴대기기 설정 등록/수정 응답 Value Object
	 */
	@Override
	public ModifyDeviceSetInfoResponse modifyDeviceSetInfo(ModifyDeviceSetInfoRequest modifyDeviceSetInfoRequest) {
		LOGGER.debug("### modifyDeviceSetInfoRequest : {}", modifyDeviceSetInfoRequest.toString());

		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();

		List<KeySearch> keySearchList = modifyDeviceSetInfoRequest.getKeySearchList();
		userMbrDeviceSet.setKeySearchList(keySearchList);
		userMbrDeviceSet.setSystemID(modifyDeviceSetInfoRequest.getCommonRequest().getSystemID());

		userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchUserKey", userMbrDeviceSet,
				UserMbrDeviceSet.class);

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		LOGGER.debug("### userMbrDevice : {}", userMbrDeviceSet);

		UserMbrDeviceSet checkUserMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchDeviceSetInfo",
				userMbrDeviceSet, UserMbrDeviceSet.class);

		ModifyDeviceSetInfoResponse modifyDeviceSetInfoResponse = new ModifyDeviceSetInfoResponse();

		UserMbrDeviceSet modifyUserMbrDeviceSet = modifyDeviceSetInfoRequest.getUserMbrDeviceSet();

		modifyUserMbrDeviceSet.setUserKey(userMbrDeviceSet.getUserKey());
		modifyUserMbrDeviceSet.setDeviceKey(userMbrDeviceSet.getDeviceKey());
		modifyUserMbrDeviceSet.setUserID(userMbrDeviceSet.getUserID());
		modifyUserMbrDeviceSet.setSystemID(modifyDeviceSetInfoRequest.getCommonRequest().getSystemID());

		if (checkUserMbrDeviceSet == null) { // 등록

			// TB_US_USERMBR_DEVICE_SET 테이블 등록시 ICAS_AUTH_YN에 디폴트 값이 없기 때문에
			// 해당 컬럼의 데이터가 NULL일경우에 'N'으로 내려준다.
			if (StringUtils.isBlank(modifyUserMbrDeviceSet.getIsIcasAuth())) {
				modifyUserMbrDeviceSet.setIsIcasAuth(Constant.TYPE_YN_N);
			}

			Integer row = (Integer) this.commonDAO.insert("DeviceSet.createDevicePin", modifyUserMbrDeviceSet);
			LOGGER.debug("### row : {}", row);

			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			LOGGER.debug("### createDevicePin row : {}", row);
		} else { // 수정
			Integer row = this.commonDAO.update("DeviceSet.modifyDeviceSet", modifyUserMbrDeviceSet);
			LOGGER.debug("### row : {}", row);

			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			LOGGER.debug("### modifyDeviceSet row : {}", row);
		}

		// 공통 응답
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
				LocaleContextHolder.getLocale()));
		commonResponse.setResultMessage(this.messageSourceAccessor.getMessage("response.ResultMessage.success", null,
				"성공", LocaleContextHolder.getLocale()));

		modifyDeviceSetInfoResponse.setCommonResponse(commonResponse);
		modifyDeviceSetInfoResponse.setDeviceId(userMbrDeviceSet.getDeviceID());
		modifyDeviceSetInfoResponse.setDeviceKey(userMbrDeviceSet.getDeviceKey());
		modifyDeviceSetInfoResponse.setUserKey(userMbrDeviceSet.getUserKey());

		LOGGER.debug("### DB에서 받아온 결과 : {}", modifyDeviceSetInfoResponse);

		return modifyDeviceSetInfoResponse;
	}

	/**
	 * <pre>
	 * 휴대기기 설정 정보 이관 기능을 제공한다.
	 * </pre>
	 * 
	 * @param transferDeviceSetInfoRequest
	 *            - 휴대기기 설정 정보 이관 요청 Value Object
	 * @return transferDeviceSetInfoResponse - 휴대기기 설정 정보 이관 응답 Value Object
	 */
	@Override
	public TransferDeviceSetInfoResponse transferDeviceSetInfo(TransferDeviceSetInfoRequest transferDeviceSetInfoRequest) {
		LOGGER.debug("### transferDeviceSetInfoRequest : {}", transferDeviceSetInfoRequest.toString());

		String isDormant = StringUtils.isBlank(transferDeviceSetInfoRequest.getIsDormant()) ? Constant.TYPE_YN_N : transferDeviceSetInfoRequest
				.getIsDormant();
		String preIsDormant = StringUtils.isBlank(transferDeviceSetInfoRequest.getPreIsDormant()) ? Constant.TYPE_YN_N : transferDeviceSetInfoRequest
				.getPreIsDormant();

		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
		userMbrDeviceSet.setUserKey(transferDeviceSetInfoRequest.getPreUserKey());
		userMbrDeviceSet.setDeviceKey(transferDeviceSetInfoRequest.getPreDeviceKey());
		userMbrDeviceSet.setSystemID(transferDeviceSetInfoRequest.getCommonRequest().getSystemID());

		// 이전 휴대기기 설정정보 조회
		if (StringUtils.equals(preIsDormant, Constant.TYPE_YN_N)) {
			userMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.onlyDeviceSetInfo", userMbrDeviceSet,
					UserMbrDeviceSet.class);
		} else {
			userMbrDeviceSet = this.idleDAO.queryForObject("DeviceSet.onlyDeviceSetInfo", userMbrDeviceSet,
					UserMbrDeviceSet.class);
		}

		if (userMbrDeviceSet == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		if (userMbrDeviceSet.getUserKey() == null || userMbrDeviceSet.getDeviceKey() == null)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		TransferDeviceSetInfoResponse transferDeviceSetInfoResponse = new TransferDeviceSetInfoResponse();

		// 휴대기기 설정 정보 이관
		if (StringUtils.isNotBlank(userMbrDeviceSet.getPinNo())) {
			UserMbrDeviceSet transferUserMbrDeviceSet = userMbrDeviceSet;

			userMbrDeviceSet.setUserKey(transferDeviceSetInfoRequest.getPreUserKey());
			userMbrDeviceSet.setDeviceKey(transferDeviceSetInfoRequest.getPreDeviceKey());

			// 이전 휴대기기 설정정보 삭제
			int row = 0;
			if (StringUtils.equals(preIsDormant, Constant.TYPE_YN_N)) {
				row = this.commonDAO.delete("DeviceSet.removeDeviceSetInfo", userMbrDeviceSet);
			} else {
				row = this.idleDAO.delete("DeviceSet.removeDeviceSetInfo", userMbrDeviceSet);
			}

			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			LOGGER.debug("### pre removeDeviceSetInfo row : {}", row);

			transferUserMbrDeviceSet.setUserKey(transferDeviceSetInfoRequest.getUserKey());
			transferUserMbrDeviceSet.setDeviceKey(transferDeviceSetInfoRequest.getDeviceKey());
			UserMbrDeviceSet checkUserMbrDeviceSet = null;
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				checkUserMbrDeviceSet = this.commonDAO.queryForObject("DeviceSet.searchDeviceSetInfo",
						transferUserMbrDeviceSet, UserMbrDeviceSet.class);
			} else {
				checkUserMbrDeviceSet = this.idleDAO.queryForObject("DeviceSet.searchDeviceSetInfo",
						transferUserMbrDeviceSet, UserMbrDeviceSet.class);
			}

			// 휴대기기 설정정보 중복 체크
			if (checkUserMbrDeviceSet != null) {
				if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
					row = this.commonDAO.delete("DeviceSet.removeDeviceSetInfo", transferUserMbrDeviceSet);
				} else {
					row = this.idleDAO.delete("DeviceSet.removeDeviceSetInfo", transferUserMbrDeviceSet);
				}

				if (row <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
				LOGGER.debug("### duplicate removeDeviceSetInfo row : {}", row);
			}

			// 휴대기기 설정정보 저장
			if (StringUtils.equals(isDormant, Constant.TYPE_YN_N)) {
				row = (Integer) this.commonDAO.insert("DeviceSet.createDevicePin", transferUserMbrDeviceSet);
			} else {
				row = (Integer) this.idleDAO.insert("DeviceSet.createDevicePin", transferUserMbrDeviceSet);
			}

			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			LOGGER.debug("### createDevicePin row : {}", row);

			transferDeviceSetInfoResponse.setDeviceKey(transferDeviceSetInfoRequest.getDeviceKey());
			transferDeviceSetInfoResponse.setUserKey(transferDeviceSetInfoRequest.getUserKey());
		}

		// 공통 응답
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
				LocaleContextHolder.getLocale()));
		commonResponse.setResultMessage(this.messageSourceAccessor.getMessage("response.ResultMessage.success", null,
				"성공", LocaleContextHolder.getLocale()));

		transferDeviceSetInfoResponse.setCommonResponse(commonResponse);

		LOGGER.debug("### DB에서 받아온 결과 : {}", transferDeviceSetInfoResponse);

		return transferDeviceSetInfoResponse;
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
}
