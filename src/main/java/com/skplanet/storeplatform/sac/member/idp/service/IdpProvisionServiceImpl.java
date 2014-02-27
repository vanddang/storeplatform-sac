/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.idp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.LimitTarget;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyResponse;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.ChangedDeviceLog;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateChangedDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDCDRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.DCDInfo;
import com.skplanet.storeplatform.member.client.user.sci.vo.NonMbrSegment;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateNonMbrSegmentRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyKeyRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyKeyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserMbrSegmentRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrSegment;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacReq;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import com.skplanet.storeplatform.sac.member.idp.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * IDP에서 전달되는 Provisioning 처리를 위한 인터페이스 구현체.
 * 
 * Updated on : 2014. 2. 27. Updated by : 반범진, 지티소프트.
 */
@Service
public class IdpProvisionServiceImpl implements IdpProvisionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdpServiceImpl.class);

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private DeviceService deviceService;

	/*
	 * 
	 * <pre> 회선 변경 정보 Provisioning (번호변경) - CMD : changeMobileNumber </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return IDP Provisioning 처리 결과
	 */
	@Override
	public String executeChangeMobileNumber(HashMap<String, String> map) {

		// <provisionInterface> CMD[ changeMobileNumber] REQUEST URL [
		// http://stg.tstore.co.kr/userpoc/IF/IDPSubsProv.omp?cmd=changeMobileNumber&mdn=01071295269&model_id=SSNT&be_mdn=01071295269&svc_mng_num=7213811004]
		// svcMngNum [ 7213811004] mdn [ 01071295269] modelId [ SSNT]
		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String beMdn = StringUtil.nvl(map.get("be_mdn"), "");
		String uacd = StringUtil.nvl(map.get("model_id"), ""); // uacd
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");

		String userKey = null;
		String deviceKey = null;
		String modelCd = null;

		String result = null;
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		try {

			/* 휴대기기 정보조회 - 서비스 관리 번호 */
			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
			searchDeviceRequest.setCommonRequest(commonRequest);

			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch key = new KeySearch();
			key.setKeyType(MemberConstants.KEY_TYPE_SVC_MANG_NO);
			key.setKeyString(svcMngNum);
			keySearchList.add(key);

			searchDeviceRequest.setKeySearchList(keySearchList);
			SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);
			beMdn = schDeviceRes.getUserMbrDevice().getDeviceID();
			userKey = schDeviceRes.getUserMbrDevice().getUserKey();
			deviceKey = schDeviceRes.getUserMbrDevice().getDeviceKey();

			/* 단말 정보 조회 */
			Device device = this.mcc.getPhoneInfoByUacd(uacd);

			if (device == null) {

				uacd = MemberConstants.NOT_SUPPORT_HP_UACODE;
				modelCd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;

			} else {

				modelCd = device.getDeviceModelCd();
				if (StringUtil.equals(device.getVerifyDvcYn(), "Y")) { // 타겟 단말인 경우

					/* 테스트 단말여부 확인 */
					String isTestModel = "N";
					List<String> limitPolicyCodeList = new ArrayList<String>();
					limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_TESTER);
					SearchPolicyRequest policyRequest = new SearchPolicyRequest();
					policyRequest.setCommonRequest(commonRequest);
					policyRequest.setLimitPolicyKey(mdn);
					policyRequest.setLimitPolicyCodeList(limitPolicyCodeList);
					SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);
					for (LimitTarget limitTarget : policyResponse.getLimitTargetList()) {
						if (limitTarget.getPolicyApplyValue().equals(mdn)) {
							isTestModel = "Y";
							break;
						}
					}

					if (!StringUtil.equals(isTestModel, "Y")) { // 일반 사용자인 경우 - 미지원 휴대폰으로
						uacd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
					}

				}
			}

			/* 휴대기기 수정 요청 */
			CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();

			UserMbrDevice userMbrDevice = new UserMbrDevice();
			userMbrDevice.setUserKey(userKey);
			userMbrDevice.setDeviceID(mdn);
			userMbrDevice.setDeviceKey(deviceKey);
			userMbrDevice.setDeviceModelNo(modelCd);
			userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_CHANGE); // 휴대기기 변경 유형코드 : 번호변경

			List<UserMbrDeviceDetail> userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
			UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
			userMbrDeviceDetail.setExtraProfileValue(uacd);
			userMbrDeviceDetail.setTenantID(tenantId);
			userMbrDeviceDetail.setUserKey(userKey);
			userMbrDeviceDetail.setDeviceKey(deviceKey);
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
			userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);

			createDeviceReq.setCommonRequest(commonRequest);
			createDeviceReq.setUserKey(userKey);
			createDeviceReq.setIsNew("N");
			createDeviceReq.setUserMbrDevice(userMbrDevice);

			this.deviceSCI.createDevice(createDeviceReq);

			/* 게임센터 연동 */
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(userKey);
			gameCenterSacReq.setDeviceId(mdn);
			gameCenterSacReq.setPreDeviceId(beMdn);
			gameCenterSacReq.setSystemId(systemId);
			gameCenterSacReq.setTenantId(tenantId);
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_CHANGE);
			this.deviceService.insertGameCenterIF(gameCenterSacReq);

			/* 구매한도/선물수신한도 mdn 변경 */
			UpdatePolicyKeyRequest updPolicyKeyReq = new UpdatePolicyKeyRequest();
			updPolicyKeyReq.setCommonRequest(commonRequest);
			updPolicyKeyReq.setOldLimitPolicyKey(beMdn);
			updPolicyKeyReq.setNewLimitPolicyKey(mdn);
			UpdatePolicyKeyResponse updPolicyKeyRes = this.userSCI.updatePolicyKey(updPolicyKeyReq);

			LOGGER.info("::: 구매/선물수신한도 변경 카운트 : {}", updPolicyKeyRes.getUpdateCount());

			result = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;

		} catch (StorePlatformException ex) {

			LOGGER.info("::: Exception : {}", ex.getErrorInfo().getCode());
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {

				/* skt 서비스 관리번호로 조회결과 없는경우 device_id로 조회하여 정보 업데이트 */
				LOGGER.info("<changeMobileNumber> NOT EXIST MEMBER. SVC_MGMT_NUM[ {} ]", svcMngNum);

				try {

					/* 이전 deviceId로 휴대기기 조회 */
					SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
					searchDeviceRequest.setCommonRequest(commonRequest);

					List<KeySearch> keySearchList = new ArrayList<KeySearch>();
					KeySearch key = new KeySearch();
					key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
					key.setKeyString(beMdn);
					keySearchList.add(key);

					searchDeviceRequest.setKeySearchList(keySearchList);

					LOGGER.info("::: searchDeviceRequest : {}", searchDeviceRequest.getCommonRequest().toString());
					LOGGER.info("::: searchDeviceRequest : {}", searchDeviceRequest.getKeySearchList().toString());
					SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);

					beMdn = schDeviceRes.getUserMbrDevice().getDeviceID();
					userKey = schDeviceRes.getUserMbrDevice().getUserKey();
					deviceKey = schDeviceRes.getUserMbrDevice().getDeviceKey();

					/* 단말 정보 조회 */
					Device device = this.mcc.getPhoneInfoByUacd(uacd);

					if (device == null) {
						uacd = MemberConstants.NOT_SUPPORT_HP_UACODE;
						modelCd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
					} else {
						modelCd = device.getDeviceModelCd();
					}

					/* 휴대기기 정보 업데이트 */
					CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
					UserMbrDevice userMbrDevice = new UserMbrDevice();
					userMbrDevice.setUserKey(userKey);
					userMbrDevice.setDeviceID(mdn);
					userMbrDevice.setDeviceKey(deviceKey);
					userMbrDevice.setDeviceModelNo(modelCd);
					userMbrDevice.setSvcMangNum(svcMngNum);
					userMbrDevice.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_SKT);
					userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_CHANGE); // 휴대기기 변경 유형코드 :
																										// 번호변경

					List<UserMbrDeviceDetail> userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
					UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
					userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
					userMbrDeviceDetail.setExtraProfileValue(uacd);
					userMbrDeviceDetail.setTenantID(tenantId);
					userMbrDeviceDetail.setUserKey(userKey);
					userMbrDeviceDetail.setDeviceKey(deviceKey);
					userMbrDeviceDetailList.add(userMbrDeviceDetail);
					userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);

					createDeviceReq.setCommonRequest(commonRequest);
					createDeviceReq.setUserKey(userKey);
					createDeviceReq.setIsNew("N");
					createDeviceReq.setUserMbrDevice(userMbrDevice);

					this.deviceSCI.createDevice(createDeviceReq);

					result = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;

				} catch (StorePlatformException e) {
					if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
						result = IdpConstants.IDP_RESPONSE_NO_DATA;
					} else {
						result = IdpConstants.IDP_RESPONSE_FAIL_CODE;
					}
				}

			} else {

				result = IdpConstants.IDP_RESPONSE_FAIL_CODE;
			}

		} finally {

			LOGGER.info("::: result : {}", result);

			/* IDP로그 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_CHANGE);
			changeDeviceLog.setDeviceID(mdn);
			changeDeviceLog.setMessageIDP(requestUrl);
			if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData(beMdn);

				/* 번호 변경 DCD 연동 */
				DCDInfo dcdInfo = new DCDInfo();
				dcdInfo.setRegChannel(systemId);
				dcdInfo.setTenantID(tenantId);
				dcdInfo.setEntryClass(IdpConstants.DCD_ENTRY_CHANGE_NUMBER); // 번호변경
				dcdInfo.setServiceNumber(svcMngNum);
				dcdInfo.setDeviceID(null);
				dcdInfo.setRegDeviceID(mdn);
				dcdInfo.setPriorityClass("0");
				dcdInfo.setProductID(null);
				this.insertDceInfo(commonRequest, dcdInfo);

			} else if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_NO_DATA)) {
				changeDeviceLog.setPreData("FAIL");
			} else {
				changeDeviceLog.setPreData("ERROR");
			}

			changeDeviceLog.setSvcMangNum(svcMngNum);
			changeDeviceLog.setTenantID(tenantId);
			changeDeviceLog.setUserKey(userKey == null ? "-" : userKey);
			changeDeviceLog.setDeviceKey(deviceKey);
			// changeDeviceLog.setDeviceCode(deviceCode);
			// changeDeviceLog.setIsChanged(isChanged);
			this.insertIdpLog(commonRequest, changeDeviceLog);
		}

		return result;
	}

	/**
	 * <pre>
	 * IDP 로그 저장.
	 * </pre>
	 * 
	 * @param commonRequest
	 *            CommonRequest
	 * @param changeDeviceLog
	 *            ChangedDeviceLog
	 */
	public void insertIdpLog(CommonRequest commonRequest, ChangedDeviceLog changeDeviceLog) {
		CreateChangedDeviceRequest createChangeDeviceReq = new CreateChangedDeviceRequest();
		createChangeDeviceReq.setCommonRequest(commonRequest);
		createChangeDeviceReq.setChangedDeviceLog(changeDeviceLog);

		this.userSCI.createChangedDevice(createChangeDeviceReq);
	}

	/**
	 * <pre>
	 * DCD 정보 저장.
	 * </pre>
	 * 
	 * @param commonRequest
	 *            CommonRequest
	 * @param dcdInfo
	 *            DCDInfo
	 */
	public void insertDceInfo(CommonRequest commonRequest, DCDInfo dcdInfo) {
		dcdInfo.setRegChannel(commonRequest.getSystemID());
		CreateDCDRequest createDcdReq = new CreateDCDRequest();
		createDcdReq.setCommonRequest(commonRequest);
		createDcdReq.setDCDInfo(dcdInfo);
		this.userSCI.createDCD(createDcdReq);
	}

	/*
	 * 
	 * <pre> 회선 변경 정보 Provisioning (기기변경) - CMD : changeMobileID </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return IDP Provisioning 처리 결과
	 */
	@Override
	public String executeChangeMobileID(HashMap<String, String> map) {

		// <provisionInterface> CMD[ changeMobileID] REQUEST URL [
		// http://sbeta.itopping.co.kr/userpoc/IF/IDPSubsProv.omp?cmd=changeMobileID&svc_mng_num=7035516765&mdn=01049545098&be_mdn=01049545098&model_id=SSOG&min=1049545098]
		// svcMngNum [ 7035516765] mdn [ 01049545098] modelId [ SSOG]
		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String uacd = StringUtil.nvl(map.get("model_id"), ""); // uacd
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");
		String beforeV4SprtYn = null; // 이전 단말 DCD 지원여부
		String v4SprtYn = null; // 변경될 단말 DCD 지원여부
		String preData = null;

		String userKey = null;
		String deviceKey = null;
		String modelCd = null;
		String result = null;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		try {

			/* 휴대기기 정보조회 - 서비스 관리 번호 */
			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
			searchDeviceRequest.setCommonRequest(commonRequest);

			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch key = new KeySearch();
			key.setKeyType(MemberConstants.KEY_TYPE_SVC_MANG_NO);
			key.setKeyString(svcMngNum);
			keySearchList.add(key);

			searchDeviceRequest.setKeySearchList(keySearchList);
			SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);
			userKey = schDeviceRes.getUserMbrDevice().getUserKey();
			deviceKey = schDeviceRes.getUserMbrDevice().getDeviceKey();

			/* 이전 단말의 DCD 지원여부 조회 */
			Device beforeDevice = this.mcc.getPhoneInfo(schDeviceRes.getUserMbrDevice().getDeviceModelNo());
			beforeV4SprtYn = beforeDevice.getItoppV4SprtYn() == null ? "N" : beforeDevice.getItoppV4SprtYn();
			preData = beforeDevice.getDeviceModelCd();

			/* 변경될 단말의 정보 조회 */
			Device device = this.mcc.getPhoneInfoByUacd(uacd);

			if (device == null) {
				LOGGER.info(
						"<idpChangeMobile> NOT SUPPORT DEVICE.(기기변경 대상 단말이 존재하지 않음- 미지원 휴대폰) mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}",
						mdn, null, uacd, svcMngNum);
				uacd = MemberConstants.NOT_SUPPORT_HP_UACODE;
				modelCd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
				v4SprtYn = "N"; // V4 무조건 해지

			} else {

				modelCd = device.getDeviceModelCd();
				v4SprtYn = device.getItoppV4SprtYn() == null ? "N" : device.getItoppV4SprtYn(); // DCD 연동 지원여부

				if (StringUtil.equals(device.getVerifyDvcYn(), "Y")) { // 타겟 단말인 경우

					/* 테스트 단말여부 확인 */
					String isTestModel = "N";

					/* 사용자 제한정책 조회(테스트 폰 리스트) */
					SearchPolicyRequest policyRequest = new SearchPolicyRequest();

					List<String> limitPolicyCodeList = new ArrayList<String>();
					limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_TESTER);

					policyRequest.setCommonRequest(commonRequest);
					policyRequest.setLimitPolicyKey(mdn);
					policyRequest.setLimitPolicyCodeList(limitPolicyCodeList);
					SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);

					for (LimitTarget limitTarget : policyResponse.getLimitTargetList()) {
						if (limitTarget.getPolicyApplyValue().equals(mdn)) {
							isTestModel = "Y";
							break;
						}
					}

					if (StringUtil.equals(isTestModel, "Y")) {
						LOGGER.info("<idpChangeMobile> 단말 테스터이고 타겟 단말 mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}", mdn,
								device.getDeviceModelCd(), uacd, svcMngNum);
					} else {
						LOGGER.info(
								"<idpChangeMobile> NOT SUPPORT DEVICE.(기기변경 대상 단말이 존재하지 않음- 미지원 휴대폰) mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}",
								mdn, device.getDeviceModelCd(), uacd, svcMngNum);
						uacd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD; // 일반 사용자인 경우 - 미지원 휴대폰으로
						v4SprtYn = "N"; // V4 무조건 해지
					}

				}
			}

			/* 휴대기기 수정 요청 */
			CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();

			UserMbrDevice userMbrDevice = new UserMbrDevice();
			userMbrDevice.setUserKey(userKey);
			userMbrDevice.setDeviceID(mdn);
			userMbrDevice.setDeviceKey(deviceKey);
			userMbrDevice.setDeviceModelNo(modelCd);
			userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_MODEL_CHANGE); // 휴대기기 변경 유형코드 : 기기변경

			List<UserMbrDeviceDetail> userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
			UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
			userMbrDeviceDetail.setExtraProfileValue(uacd);
			userMbrDeviceDetail.setTenantID(tenantId);
			userMbrDeviceDetail.setUserKey(userKey);
			userMbrDeviceDetail.setDeviceKey(deviceKey);
			userMbrDeviceDetailList.add(userMbrDeviceDetail);
			userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);

			createDeviceReq.setCommonRequest(commonRequest);
			createDeviceReq.setUserKey(userKey);
			createDeviceReq.setIsNew("N");
			createDeviceReq.setUserMbrDevice(userMbrDevice);

			this.deviceSCI.createDevice(createDeviceReq);

			/* 게임센터 연동 */
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(userKey);
			gameCenterSacReq.setDeviceId(mdn);
			gameCenterSacReq.setSystemId(systemId);
			gameCenterSacReq.setTenantId(tenantId);
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_INSERT);
			this.deviceService.insertGameCenterIF(gameCenterSacReq);

			/* DCD 연동 */
			DCDInfo dcdInfo = new DCDInfo();
			if (StringUtil.equals(beforeV4SprtYn, "Y") && StringUtil.equals(v4SprtYn, "N")) {

				LOGGER.info("<idpChangeMobile> V4지원 -> V4미지원 기변. mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}", mdn,
						device.getDeviceModelCd(), uacd, svcMngNum);

				/* 기존에 구매했던 DCD 상품 조회 */

				/* 기존번호 상품별로 DCD 단말해지 */

				dcdInfo.setEntryClass(IdpConstants.DCD_ENTRY_SECEDE); // 해지
				dcdInfo.setProductID("A000Z00001");

			} else if (StringUtil.equals(beforeV4SprtYn, "Y") && StringUtil.equals(v4SprtYn, "Y")) {

				LOGGER.info("<idpChangeMobile> V4지원 -> V4지원 기변. mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}", mdn,
						device.getDeviceModelCd(), uacd, svcMngNum);

				dcdInfo.setEntryClass(IdpConstants.DCD_ENTRY_CHANGE_MODEL); // 기기변경
				dcdInfo.setProductID(null);

			} else if (StringUtil.equals(beforeV4SprtYn, "N") && StringUtil.equals(v4SprtYn, "Y")) {

				LOGGER.info("<idpChangeMobile> V4미지원 -> V4지원 기변. mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}", mdn,
						device.getDeviceModelCd(), uacd, svcMngNum);

				dcdInfo.setEntryClass(IdpConstants.DCD_ENTRY_JOIN); // 가입
				dcdInfo.setProductID("A000Z00001");

				/* 기존에 구입했던 DCD 상품조회 */

				/* 상품 DCD 가입처리 */

			} else if (StringUtil.equals(beforeV4SprtYn, "N") && StringUtil.equals(v4SprtYn, "N")) {

				LOGGER.info("<idpChangeMobile> V4미지원 -> V4미지원 기변 시 DCD 한번더 해지 처리. mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}", mdn,
						device.getDeviceModelCd(), uacd, svcMngNum);

				/* 기존에 구매했던 DCD 상품 조회 */

				/* 기존번호 상품별로 DCD 단말해지 */

				dcdInfo.setEntryClass(IdpConstants.DCD_ENTRY_SECEDE); // 해지
				dcdInfo.setProductID("A000Z00001");

			}

			dcdInfo.setRegChannel(systemId);
			dcdInfo.setTenantID(tenantId);
			dcdInfo.setServiceNumber(svcMngNum);
			dcdInfo.setDeviceID(mdn);
			dcdInfo.setRegDeviceID(null);
			dcdInfo.setPriorityClass("0");
			this.insertDceInfo(commonRequest, dcdInfo);

			result = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;

		} catch (StorePlatformException ex) {

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {

				result = IdpConstants.IDP_RESPONSE_NO_DATA;

			} else {

				result = IdpConstants.IDP_RESPONSE_FAIL_CODE;

			}

		} finally {

			/* IDP로그 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_MODEL_CHANGE);
			changeDeviceLog.setDeviceID(mdn);
			changeDeviceLog.setMessageIDP(requestUrl);
			if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData(preData);
			} else if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_NO_DATA)) {
				changeDeviceLog.setPreData("FAIL");
			} else {
				changeDeviceLog.setPreData("ERROR");
			}
			if (userKey == null) {
				changeDeviceLog.setUserKey("-");
			} else {
				changeDeviceLog.setUserKey(userKey);
			}
			changeDeviceLog.setSvcMangNum(svcMngNum);
			changeDeviceLog.setTenantID(tenantId);
			changeDeviceLog.setDeviceKey(deviceKey);
			// changeDeviceLog.setDeviceCode(deviceCode);
			// changeDeviceLog.setIsChanged(isChanged);
			this.insertIdpLog(commonRequest, changeDeviceLog);
		}

		return result;
	}

	/*
	 * 
	 * <pre> 회선 변경 정보 Provisioning (번호해지) - CMD : secedeMobileNumber </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return IDP Provisioning 처리 결과
	 */
	@Override
	public String executeSecedeMobileNumber(HashMap<String, String> map) {

		// <provisionInterface> CMD[ secedeMobileNumber] REQUEST URL [
		// http://sbeta.itopping.co.kr/userpoc/IF/IDPSubsProv.omp?cmd=secedeMobileNumber&svc_mng_num=7049931033&mdn=01090130995&be_mdn=01090130995&svc_rsn_cd=Z21Z&min=1090130995]
		// svcMngNum [ 7049931033] mdn [ 01090130995] modelId [ null]
		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");

		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");

		String userKey = null;
		String deviceKey = null;
		String result = null;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		try {

			/* 휴대기기 정보조회 - 서비스 관리 번호 */
			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
			searchDeviceRequest.setCommonRequest(commonRequest);

			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch key = new KeySearch();
			key.setKeyType(MemberConstants.KEY_TYPE_SVC_MANG_NO);
			key.setKeyString(svcMngNum);
			keySearchList.add(key);

			searchDeviceRequest.setKeySearchList(keySearchList);
			SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);
			userKey = schDeviceRes.getUserKey();
			deviceKey = schDeviceRes.getUserMbrDevice().getDeviceKey();

			/* 휴대기기 삭제 요청 */
			List<String> removeKeyList = new ArrayList<String>();
			removeKeyList.add(deviceKey);

			RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
			removeDeviceReq.setCommonRequest(commonRequest);
			removeDeviceReq.setUserKey(userKey);
			removeDeviceReq.setDeviceKey(removeKeyList);
			this.deviceSCI.removeDevice(removeDeviceReq);

			/* 게임센터 연동 */
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(userKey);
			gameCenterSacReq.setDeviceId(mdn);
			gameCenterSacReq.setSystemId(systemId);
			gameCenterSacReq.setTenantId(tenantId);
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_USER_SECEDE);

			this.deviceService.insertGameCenterIF(gameCenterSacReq);

			result = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;

		} catch (StorePlatformException ex) {

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {

				result = IdpConstants.IDP_RESPONSE_NO_DATA;

			} else {

				result = IdpConstants.IDP_RESPONSE_FAIL_CODE;
			}

		} finally {

			/* IDP로그 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_SECEDE);
			changeDeviceLog.setDeviceID(mdn);
			changeDeviceLog.setMessageIDP(requestUrl);
			if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData("");
			} else if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_NO_DATA)) {
				changeDeviceLog.setPreData("FAIL");
			} else {
				changeDeviceLog.setPreData("ERROR");
			}
			if (userKey == null) {
				changeDeviceLog.setUserKey("-");
			} else {
				changeDeviceLog.setUserKey(userKey);
			}
			changeDeviceLog.setSvcMangNum(svcMngNum);
			changeDeviceLog.setTenantID(tenantId);
			changeDeviceLog.setDeviceKey(deviceKey);
			// changeDeviceLog.setDeviceCode(deviceCode);
			// changeDeviceLog.setIsChanged(isChanged);
			this.insertIdpLog(commonRequest, changeDeviceLog);
		}
		return result;
	}

	/*
	 * 
	 * <pre> 가입 승인 만료 정보 Provisioning (유선, 통합 회원) - CMD : joinComplete. </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public String executeJoinComplete(HashMap<String, String> map) {

		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");
		String mbrNo = StringUtil.nvl(map.get("user_key"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");
		String userKey = null;
		String result = null;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		try {

			/* 회원 정보 조회 */
			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch key = new KeySearch();
			key.setKeyType(MemberConstants.KEY_TYPE_USERMBR_NO);
			key.setKeyString(mbrNo);
			keySearchList.add(key);

			SearchUserRequest schUserReq = new SearchUserRequest();
			schUserReq.setCommonRequest(commonRequest);
			schUserReq.setKeySearchList(keySearchList);
			SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

			userKey = schUserRes.getUserKey();

			/* 회원 탈퇴 처리 */
			RemoveUserRequest removeUserReq = new RemoveUserRequest();
			removeUserReq.setCommonRequest(commonRequest);
			removeUserReq.setUserKey(schUserRes.getUserMbr().getUserKey());
			removeUserReq.setSecedeTypeCode(MemberConstants.USER_WITHDRAW_CLASS_JOIN_AGREE_EXPIRED); // 가입승인만료
			removeUserReq.setSecedeReasonCode("US010409"); // 기타
			removeUserReq.setSecedeReasonMessage("가입승인만료");

			this.userSCI.remove(removeUserReq);

			/* 게임센터 연동 */
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(schUserRes.getUserKey());
			gameCenterSacReq.setSystemId(systemId);
			gameCenterSacReq.setTenantId(tenantId);
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_USER_SECEDE);
			this.deviceService.insertGameCenterIF(gameCenterSacReq);

			result = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;

		} catch (StorePlatformException ex) {

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					|| StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				result = IdpConstants.IDP_RESPONSE_NO_DATA;
			} else {
				result = IdpConstants.IDP_RESPONSE_FAIL_CODE;
			}

		} finally {
			/* IDP로그 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_EMAIL_JOIN_COMPLETE);
			changeDeviceLog.setDeviceID(mdn);
			changeDeviceLog.setMessageIDP(requestUrl);
			if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData("");
			} else if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_NO_DATA)) {
				changeDeviceLog.setPreData("FAIL");
			} else {
				changeDeviceLog.setPreData("ERROR");
			}
			changeDeviceLog.setSvcMangNum(svcMngNum);
			changeDeviceLog.setTenantID(tenantId);
			if (userKey == null) {
				changeDeviceLog.setUserKey("-");
			} else {
				changeDeviceLog.setUserKey(userKey);
			}
			// changeDeviceLog.setDeviceKey(deviceKey);
			// changeDeviceLog.setDeviceCode(deviceCode);
			// changeDeviceLog.setIsChanged(isChanged);
			this.insertIdpLog(commonRequest, changeDeviceLog);
		}

		return result;
	}

	/*
	 * 
	 * <pre> 프로파일 변경 Provisioning (유선, 통합 회원) - CMD : adjustWiredProfile. </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public String executeAdjustWiredProfile(HashMap<String, String> map) {
		// <provisionInterface> CMD[ adjustWiredProfile] REQUEST URL [
		// http://sbeta.itopping.co.kr/userpoc/IF/IDPSubsProv.omp?cmd=adjustWiredProfile&user_address=%EC%9D%B8%EC%B2%9C+%EA%B3%84%EC%96%91%EA%B5%AC+%EB%8F%99%EC%96%91%EB%8F%99&user_address2=595-3+%ED%95%9C%EB%AB%BC%EC%9C%84%EB%84%88%EC%8A%A4%EB%B9%8C+102%EB%8F%99+402%ED%98%B8&is_foreign=N&user_sex=F&user_key=IF1023112421620111107130852&user_id=newjungyb&user_birthday=19880903&user_zipcode=407340]
		// svcMngNum [ null] mdn [ null] modelId [ null]
		// userpoc_idp2.log:2013-01-24 16:06:11,769 [ajp-9209-3] INFO
		// (com.omp.market.intf.idp.action.IdpOmpSubscriptAction:132) - <provisionInterface> CMD[ adjustWiredProfile]
		// REQUEST URL [
		// http://sbeta.itopping.co.kr/userpoc/IF/IDPSubsProv.omp?cmd=adjustWiredProfile&user_key=IF1023548360620120726182243&user_id=expertman99&is_rname_auth=N&is_foreign=N]
		// svcMngNum [ null] mdn [ null] modelId [ null]

		// cmd=adjustWiredProfile&is_foreign=N&user_sex=F&user_key=IF1023112421620111107130852&user_id=newjungyb&user_birthday=19880903&user_email=vanddang1234@gadf.com&is_rname_auth=Y&user_name=반범진&user_social_number=8312101
		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String imIntSvcNo = StringUtil.nvl(map.get("im_int_svc_no"), "");
		String imMbrNo = StringUtil.nvl(map.get("user_key"), "");
		String userKey = null;
		String result = null;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
		commonRequest.setSystemID(StringUtil.nvl(map.get("systemID"), ""));

		try {

			if (!StringUtil.equals(imMbrNo, "")) {

				/* 회원 정보 조회 */
				List<KeySearch> keySearchList = new ArrayList<KeySearch>();
				KeySearch key = new KeySearch();
				key.setKeyType(MemberConstants.KEY_TYPE_USERMBR_NO);
				key.setKeyString(imMbrNo);
				keySearchList.add(key);
				SearchUserRequest schUserReq = new SearchUserRequest();
				schUserReq.setCommonRequest(commonRequest);
				schUserReq.setKeySearchList(keySearchList);
				SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

				userKey = schUserRes.getUserKey();

				/* 변경정보 셋팅 */
				UserMbr userMbr = new UserMbr();
				userMbr.setUserKey(userKey);

				String userPhone = StringUtil.nvl(map.get("user_phone"), "");
				String userId = StringUtil.nvl(map.get("user_id"), "");
				String userSex = StringUtil.nvl(map.get("user_sex"), "");
				String userBirthday = StringUtil.nvl(map.get("user_birthday"), "");
				String userEmail = StringUtil.nvl(map.get("user_email"), "");
				String isRnameAuth = StringUtil.nvl(map.get("is_rname_auth"), "");
				String userName = StringUtil.nvl(map.get("user_name"), "");
				String userSocialNumber = StringUtil.nvl(map.get("user_social_number"), "");

				if (!StringUtil.equals(userId, "")) {
					userMbr.setUserID(userId);
				}

				if (!StringUtil.equals(userSex, "")) {
					userMbr.setUserSex(userSex);
				}

				if (!StringUtil.equals(userBirthday, "")) {
					userMbr.setUserBirthDay(userBirthday);
				}

				/* 실명인증이 추가된 경우만 처리 */
				if (StringUtil.equals(isRnameAuth, "Y")) {

					if (!StringUtil.equals(userName, "") && !StringUtil.equals(userSocialNumber, "")) {

						userMbr.setIsRealName(isRnameAuth);
						userMbr.setUserName(userName);

					}
				}

				if (!StringUtil.equals(userEmail, "")) {
					userMbr.setUserEmail(userEmail);
				}

				LOGGER.info(":::: adjustWiredProfile : {}", userMbr.toString());
				if (StringUtil.equals(imIntSvcNo, "")) { // 통합회원이 아닌경우
					/* 회원정보 변경 요청 */
					UpdateUserRequest updateUserRequest = new UpdateUserRequest();
					updateUserRequest.setUserMbr(userMbr);
					updateUserRequest.setCommonRequest(commonRequest);
					this.userSCI.updateUser(updateUserRequest);
				}

				/* 휴대기기 목록 조회 */
				SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
				schDeviceListReq.setUserKey(userKey);
				schDeviceListReq.setIsMainDevice("N");
				key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
				key.setKeyString(userKey);
				keySearchList.add(key);
				schDeviceListReq.setKeySearchList(keySearchList);
				schDeviceListReq.setCommonRequest(commonRequest);

				/* 휴대기기 기기 정보 변경 */
				if (!StringUtil.equals(userPhone, "")) {

					/* 사용자 휴대기기 목록 조회 */
					SearchDeviceListResponse schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);

					if (userPhone.indexOf("|") > -1) { // 처리단말이 여러개

						String userPhones[] = userPhone.split("\\|");
						List<String> idpUserPhoneList = new ArrayList<String>();

						for (int i = 0; i < userPhones.length; i++) {

							String phoneInfo[] = userPhones[i].split(",");

							/* 휴대기기 번호 */
							String deviceId = phoneInfo[0];
							LOGGER.info("deviceId : {}", deviceId);
							idpUserPhoneList.add(i, deviceId);

							/* SKT 통합관리번호 */
							String svcMangNum = phoneInfo[1];
							LOGGER.info("svcMangNum : {}", svcMangNum);

							/* uacd */
							String uaCd = phoneInfo[2];

							/* 단말코드 조회 */
							Device device = this.mcc.getPhoneInfoByUacd(uaCd);
							String deviceModelNo = null;
							if (device == null) {
								deviceModelNo = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
								uaCd = MemberConstants.NOT_SUPPORT_HP_UACODE;
							} else {
								deviceModelNo = device.getDeviceModelCd();
							}

							LOGGER.info("uaCd : {}", uaCd);

							/* 통신사명 */
							String deviceTelecom = phoneInfo[3];
							LOGGER.info("deviceTelecom : {}", deviceTelecom);

							LOGGER.info("deviceModelCd : {}", deviceModelNo);

							for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {

								/* 동일한 deviceId인 경우 수정처리 */
								if (userMbrDevice.getDeviceID().equals(deviceId)) {

									LOGGER.info("modify deviceId : {}", deviceId);

									UserMbrDevice modifyDevice = new UserMbrDevice();
									modifyDevice.setUserKey(userKey);
									modifyDevice.setDeviceKey(userMbrDevice.getDeviceKey());
									modifyDevice.setDeviceID(deviceId);
									modifyDevice.setDeviceModelNo(deviceModelNo);
									modifyDevice.setDeviceTelecom(this.mcc.convertDeviceTelecomCode(deviceTelecom));
									modifyDevice.setSvcMangNum(svcMangNum);

									List<UserMbrDeviceDetail> modifyDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
									UserMbrDeviceDetail modifyDeviceDetail = new UserMbrDeviceDetail();
									modifyDeviceDetail.setUserKey(userKey);
									modifyDeviceDetail.setDeviceKey(userMbrDevice.getDeviceKey());
									modifyDeviceDetail.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
									modifyDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
									modifyDeviceDetail.setExtraProfileValue(uaCd);
									modifyDeviceDetailList.add(modifyDeviceDetail);
									modifyDevice.setUserMbrDeviceDetail(modifyDeviceDetailList);

									CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
									createDeviceReq.setCommonRequest(commonRequest);
									createDeviceReq.setUserKey(userKey);
									createDeviceReq.setIsNew("N");
									createDeviceReq.setUserMbrDevice(modifyDevice);

									this.deviceSCI.createDevice(createDeviceReq);

								}
							}
						}

						/* user_phone에 존재하지 않은 휴대기기 정보 삭제 처리 */
						List<String> removeKeyList = new ArrayList<String>();
						for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {

							String removeYn = "Y";
							for (String deviceIdStr : idpUserPhoneList) {

								if (userMbrDevice.getDeviceID().equals(deviceIdStr)) {
									removeYn = "N";
								} else {
									LOGGER.info("delete deviceId : {}", userMbrDevice.getDeviceID());
								}

							}

							if (removeYn.equals("Y")) {
								removeKeyList.add(userMbrDevice.getDeviceKey());
							}

						}

						if (removeKeyList.size() > 0) {
							RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
							removeDeviceReq.setCommonRequest(commonRequest);
							removeDeviceReq.setUserKey(userKey);
							removeDeviceReq.setDeviceKey(removeKeyList);
							this.deviceSCI.removeDevice(removeDeviceReq);
						}

					} else { // 처리단말 한개

						String phoneInfo[] = userPhone.split(",");

						/* 휴대기기 번호 */
						String deviceId = phoneInfo[0];
						LOGGER.info("deviceId : {}", deviceId);

						/* SKT 통합관리번호 */
						String svcMangNum = phoneInfo[1];
						LOGGER.info("svcMangNum : {}", svcMangNum);

						/* uacd */
						String uaCd = phoneInfo[2];

						/* 단말코드 조회 */
						Device device = this.mcc.getPhoneInfoByUacd(uaCd);
						String deviceModelNo = null;
						if (device == null) {
							uaCd = MemberConstants.NOT_SUPPORT_HP_UACODE;
							deviceModelNo = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
						} else {
							deviceModelNo = device.getDeviceModelCd();
						}

						LOGGER.info("uaCd : {}", uaCd);

						/* 통신사명 */
						String deviceTelecom = phoneInfo[3];
						LOGGER.info("deviceTelecom : {}", deviceTelecom);

						LOGGER.info("deviceModelCd : {}", deviceModelNo);

						for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {

							/* 동일한 deviceId인 경우 수정처리 */
							if (userMbrDevice.getDeviceID().equals(deviceId)) {

								LOGGER.info("modify deviceId : {}", deviceId);

								UserMbrDevice modifyDevice = new UserMbrDevice();
								modifyDevice.setUserKey(userKey);
								modifyDevice.setDeviceKey(userMbrDevice.getDeviceKey());
								modifyDevice.setDeviceID(deviceId);
								modifyDevice.setDeviceModelNo(deviceModelNo);
								modifyDevice.setDeviceTelecom(this.mcc.convertDeviceTelecomCode(deviceTelecom));
								modifyDevice.setSvcMangNum(svcMangNum);

								List<UserMbrDeviceDetail> modifyDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
								UserMbrDeviceDetail modifyDeviceDetail = new UserMbrDeviceDetail();
								modifyDeviceDetail.setUserKey(userKey);
								modifyDeviceDetail.setDeviceKey(userMbrDevice.getDeviceKey());
								modifyDeviceDetail.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
								modifyDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
								modifyDeviceDetail.setExtraProfileValue(uaCd);
								modifyDeviceDetailList.add(modifyDeviceDetail);
								modifyDevice.setUserMbrDeviceDetail(modifyDeviceDetailList);

								CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
								createDeviceReq.setCommonRequest(commonRequest);
								createDeviceReq.setUserKey(userKey);
								createDeviceReq.setIsNew("N");
								createDeviceReq.setUserMbrDevice(modifyDevice);

								this.deviceSCI.createDevice(createDeviceReq);

							}
						}

						/* user_phone에 존재하지 않은 휴대기기 정보 삭제 처리 */
						List<String> removeKeyList = new ArrayList<String>();
						for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {

							String removeYn = "Y";

							if (StringUtil.equals(userMbrDevice.getDeviceID(), deviceId)) {
								removeYn = "N";
							} else {
								LOGGER.info("delete deviceId : {}", userMbrDevice.getDeviceID());
							}

							if (StringUtil.equals(removeYn, "Y")) {
								removeKeyList.add(userMbrDevice.getDeviceKey());
							}

						}

						if (removeKeyList.size() > 0) {
							RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
							removeDeviceReq.setCommonRequest(commonRequest);
							removeDeviceReq.setUserKey(userKey);
							removeDeviceReq.setDeviceKey(removeKeyList);
							this.deviceSCI.removeDevice(removeDeviceReq);
						}
					}
				}

			}

			result = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;

		} catch (StorePlatformException ex) {

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					|| StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				result = IdpConstants.IDP_RESPONSE_NO_DATA;
			} else {
				result = IdpConstants.IDP_RESPONSE_FAIL_CODE;
			}

		} finally {
			/* IDP로그 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_MODIFY_PROFILE);
			changeDeviceLog.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
			if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData("");
			} else if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_NO_DATA)) {
				changeDeviceLog.setPreData("FAIL");
			} else if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_FAIL_CODE)) {
				changeDeviceLog.setPreData("ERROR");
			}
			if (userKey == null) {
				changeDeviceLog.setUserKey("-");
			} else {
				changeDeviceLog.setUserKey(userKey);
			}
			changeDeviceLog.setMessageIDP(requestUrl);
			changeDeviceLog.setSvcMangNum("-");
			// changeDeviceLog.setDeviceID(mdn);
			// changeDeviceLog.setDeviceKey(deviceKey);
			// changeDeviceLog.setDeviceCode(deviceCode);
			// changeDeviceLog.setIsChanged(isChanged);
			this.insertIdpLog(commonRequest, changeDeviceLog);
		}

		return result;
	}

	/*
	 * 
	 * <pre> 부가서비스 가입 Provisioning - CMD : ecgJoinedTStore. </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public String executeEcgJoinedTStore(HashMap<String, String> map) {

		// <provisionInterface> CMD[ ecgJoinedTStore] REQUEST URL [
		// http://stg.tstore.co.kr/userpoc/IF/IDPSubsProv.omp?cmd=ecgJoinedTStore&svc_mng_num=7240323788&mdn=01021489123&min=1021489123]
		// svcMngNum [ 7240323788] mdn [ 01021489123] modelId [ null]
		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String min = StringUtil.nvl(map.get("min"), "");
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");

		String userKey = null;
		String result = null;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
		commonRequest.setSystemID(StringUtil.nvl(map.get("systemID"), ""));

		try {

			/* 휴대기기 정보조회 - 서비스 관리 번호 */
			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
			searchDeviceRequest.setCommonRequest(commonRequest);

			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch key = new KeySearch();
			key.setKeyType(MemberConstants.KEY_TYPE_SVC_MANG_NO);
			key.setKeyString(svcMngNum);
			keySearchList.add(key);

			searchDeviceRequest.setKeySearchList(keySearchList);
			SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);
			userKey = schDeviceRes.getUserKey();

			/* 유통망 추천앱 스케줄 저장 */
			UpdateUserMbrSegmentRequest req = new UpdateUserMbrSegmentRequest();
			req.setCommonRequest(commonRequest);
			UserMbrSegment userMbrSegment = new UserMbrSegment();
			userMbrSegment.setDeviceID(mdn);
			userMbrSegment.setSvcMangNum(svcMngNum);
			userMbrSegment.setUserKey(userKey);
			userMbrSegment.setEcgNumber(min);
			req.setUserMbrSegment(userMbrSegment);
			this.userSCI.updateUserMbrSegment(req);

			result = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;

		} catch (StorePlatformException ex) {

			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {

				result = IdpConstants.IDP_RESPONSE_NO_DATA;

			} else {

				result = IdpConstants.IDP_RESPONSE_FAIL_CODE;

			}
		} finally {
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_JOIN_ECG);
			changeDeviceLog.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
			if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData("");
			} else if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_NO_DATA)) {

				changeDeviceLog.setPreData("");
				/* 비회원인 경우 후 성공처리 */
				UpdateNonMbrSegmentRequest req = new UpdateNonMbrSegmentRequest();
				req.setCommonRequest(commonRequest);
				NonMbrSegment nonMbrSegment = new NonMbrSegment();
				nonMbrSegment.setDeviceID(mdn);
				nonMbrSegment.setSvcMangNum(svcMngNum);
				req.setNonMbrSegment(nonMbrSegment);
				this.userSCI.updateNonMbrSegment(req);

				result = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;

			} else if (StringUtil.equals(result, IdpConstants.IDP_RESPONSE_FAIL_CODE)) {
				changeDeviceLog.setPreData("ERROR");
			}
			if (userKey == null) {
				changeDeviceLog.setUserKey("-");
			} else {
				changeDeviceLog.setUserKey(userKey);
			}
			changeDeviceLog.setMessageIDP(requestUrl);
			changeDeviceLog.setSvcMangNum(svcMngNum);
			changeDeviceLog.setDeviceID(mdn);
			// changeDeviceLog.setDeviceKey(deviceKey);
			// changeDeviceLog.setDeviceCode(deviceCode);
			// changeDeviceLog.setIsChanged(isChanged);

			this.insertIdpLog(commonRequest, changeDeviceLog);
		}

		return result;
	}

	/*
	 * 
	 * <pre> 부가서비스 해지 Provisioning - CMD : ecgScededTStore. </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public String executeEcgScededTStore(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return IdpConstants.IDP_RESPONSE_SUCCESS_CODE;
	}
}
