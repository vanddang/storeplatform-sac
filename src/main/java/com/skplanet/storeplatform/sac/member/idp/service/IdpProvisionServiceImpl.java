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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.LimitTarget;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyResponse;
import com.skplanet.storeplatform.member.client.common.vo.UpdatePolicyRequest;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.ChangedDeviceLog;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateChangedDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.NonMbrSegment;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeActivateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeActivateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateNonMbrSegmentRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserMbrSegmentRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrSegment;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceAmqpSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceAmqpSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveMemberAmqpSacReq;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberCommonInternalComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import com.skplanet.storeplatform.sac.member.idp.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningResult;
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
	private MemberCommonInternalComponent mcic;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	@Resource(name = "memberModDeviceAmqpTemplate")
	private AmqpTemplate memberModDeviceAmqpTemplate;

	@Autowired
	@Resource(name = "memberDelDeviceAmqpTemplate")
	private AmqpTemplate memberDelDeviceAmqpTemplate;

	@Autowired
	@Resource(name = "memberRetireAmqpTemplate")
	private AmqpTemplate memberRetireAmqpTemplate;

	/*
	 * 
	 * <pre> 회선 변경 정보 Provisioning (번호변경) - CMD : changeMobileNumber </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return IDP Provisioning 처리 결과
	 */
	@Override
	public String changeMobileNumber(HashMap<String, String> map) {

		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String beMdn = StringUtil.nvl(map.get("be_mdn"), "");
		String uacd = StringUtil.nvl(map.get("model_id"), ""); // uacd
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");
		String deviceTelecom = MemberConstants.DEVICE_TELECOM_SKT;
		String deviceNickName = null;
		String userKey = null;
		String deviceKey = null;
		String modelCd = null;
		String resultCode = null;
		String resultText = null;
		String isDormant = null;
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		try {

			if (StringUtil.equals(mdn, beMdn)) {

				LOGGER.info("::: 변경mdn과 이전mdn이 동일한 경우 mdn : {}, beMdn : {}, svcMngNum : {}", mdn, beMdn, svcMngNum);

				/* deviceId로 휴대기기 조회 */
				SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
				searchDeviceRequest.setCommonRequest(commonRequest);

				List<KeySearch> keySearchList = new ArrayList<KeySearch>();
				KeySearch key = new KeySearch();
				key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
				key.setKeyString(mdn);
				keySearchList.add(key);

				searchDeviceRequest.setKeySearchList(keySearchList);
				SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);

				beMdn = schDeviceRes.getUserMbrDevice().getDeviceID();
				userKey = schDeviceRes.getUserMbrDevice().getUserKey();
				deviceKey = schDeviceRes.getUserMbrDevice().getDeviceKey();
				isDormant = schDeviceRes.getUserMbrDevice().getIsDormant();
				LOGGER.info("휴면계정유무 : {}", isDormant);

				/* 단말 정보 조회 */
				if (StringUtils.isNotBlank(uacd)) {
					Device device = this.mcc.getPhoneInfoByUacd(uacd);

					if (device == null) {
						LOGGER.info("<changeMobileNumber> NOT SUPPORT DEVICE. mdn : {}, uacd : {}, svc_mng_num : {}",
								mdn, uacd, svcMngNum);
						uacd = MemberConstants.NOT_SUPPORT_HP_UACODE;
						modelCd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
						deviceNickName = MemberConstants.NOT_SUPPORT_HP_MODEL_NM;
					} else {
						modelCd = device.getDeviceModelCd();
						// 저장된 정보가 미지원단말인경우 디폴트 모델명을 닉네임으로 셋팅
						if (StringUtils.equals(schDeviceRes.getUserMbrDevice().getDeviceNickName(),
								MemberConstants.NOT_SUPPORT_HP_MODEL_NM)
								&& StringUtils.equals(schDeviceRes.getUserMbrDevice().getDeviceModelNo(),
										MemberConstants.NOT_SUPPORT_HP_MODEL_CD)) {
							deviceNickName = device.getModelNm();
						}
					}
				}

				/* 휴대기기 정보 업데이트 */
				CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
				UserMbrDevice userMbrDevice = new UserMbrDevice();
				userMbrDevice.setUserKey(userKey);
				userMbrDevice.setDeviceID(mdn);
				userMbrDevice.setDeviceKey(deviceKey);
				userMbrDevice.setDeviceModelNo(modelCd);
				userMbrDevice.setSvcMangNum(svcMngNum);
				userMbrDevice.setDeviceTelecom(deviceTelecom);
				userMbrDevice.setDeviceNickName(deviceNickName);
				userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_CHANGE);

				if (StringUtils.isNotBlank(uacd)) {
					List<UserMbrDeviceDetail> userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
					UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
					userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
					userMbrDeviceDetail.setExtraProfileValue(uacd);
					userMbrDeviceDetail.setTenantID(tenantId);
					userMbrDeviceDetail.setUserKey(userKey);
					userMbrDeviceDetail.setDeviceKey(deviceKey);
					userMbrDeviceDetailList.add(userMbrDeviceDetail);
					userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);
				}

				createDeviceReq.setCommonRequest(commonRequest);
				createDeviceReq.setUserKey(userKey);
				createDeviceReq.setIsNew("N");
				createDeviceReq.setUserMbrDevice(userMbrDevice);

				this.deviceSCI.createDevice(createDeviceReq);

			} else {

				LOGGER.info("::: MDN이 번호변경된 경우 mdn : {}, beMdn : {}, svcMngNum : {}", mdn, beMdn, svcMngNum);

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
				isDormant = schDeviceRes.getUserMbrDevice().getIsDormant();
				LOGGER.info("휴면계정유무 : {}", isDormant);

				/* 단말 정보 조회 */
				if (StringUtils.isNotBlank(uacd)) {
					Device device = this.mcc.getPhoneInfoByUacd(uacd);

					if (device == null) {
						LOGGER.info("<changeMobileNumber> NOT SUPPORT DEVICE. mdn : {}, uacd : {}, svc_mng_num : {}",
								mdn, uacd, svcMngNum);
						uacd = MemberConstants.NOT_SUPPORT_HP_UACODE;
						modelCd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
						deviceNickName = MemberConstants.NOT_SUPPORT_HP_MODEL_NM;
					} else {

						modelCd = device.getDeviceModelCd();
						// 저장된 정보가 미지원단말인경우 디폴트 모델명을 닉네임으로 셋팅
						if (StringUtils.equals(schDeviceRes.getUserMbrDevice().getDeviceNickName(),
								MemberConstants.NOT_SUPPORT_HP_MODEL_NM)
								&& StringUtils.equals(schDeviceRes.getUserMbrDevice().getDeviceModelNo(),
										MemberConstants.NOT_SUPPORT_HP_MODEL_CD)) {
							deviceNickName = device.getModelNm();
						}
						if (StringUtil.equals(device.getVerifyDvcYn(), "Y")) { // 타겟 단말인 경우

							/* 테스트 단말여부 확인 */
							String isTestModel = "N";
							List<String> limitPolicyCodeList = new ArrayList<String>();
							limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_TESTER);
							SearchPolicyRequest policyRequest = new SearchPolicyRequest();
							policyRequest.setCommonRequest(commonRequest);
							policyRequest.setLimitPolicyKey(mdn);
							policyRequest.setLimitPolicyCodeList(limitPolicyCodeList);
							SearchPolicyResponse policyResponse = null;

							try {
								policyResponse = this.userSCI.searchPolicyList(policyRequest);
							} catch (StorePlatformException e) {
								// ignore
							}

							if (policyResponse != null) {
								for (LimitTarget limitTarget : policyResponse.getLimitTargetList()) {
									if (limitTarget.getLimitPolicyKey().equals(mdn)) {
										isTestModel = "Y";
										break;
									}
								}
							}

							if (!StringUtil.equals(isTestModel, "Y")) { // 일반 사용자인 경우 - 미지원 휴대폰으로
								LOGGER.info(
										"<changeMobileNumber> NOT SUPPORT DEVICE.(타겟단말이고 단말테스터가 아닌경우- 미지원 휴대폰) mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}",
										mdn, modelCd, uacd, svcMngNum);
								uacd = MemberConstants.NOT_SUPPORT_HP_UACODE;
								modelCd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
								deviceNickName = MemberConstants.NOT_SUPPORT_HP_MODEL_NM;
							}

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
				userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_CHANGE);
				userMbrDevice.setDeviceTelecom(deviceTelecom);
				userMbrDevice.setDeviceNickName(deviceNickName);

				if (StringUtils.isNotBlank(uacd)) {
					List<UserMbrDeviceDetail> userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
					UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
					userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
					userMbrDeviceDetail.setExtraProfileValue(uacd);
					userMbrDeviceDetail.setTenantID(tenantId);
					userMbrDeviceDetail.setUserKey(userKey);
					userMbrDeviceDetail.setDeviceKey(deviceKey);
					userMbrDeviceDetailList.add(userMbrDeviceDetail);
					userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);
				}

				createDeviceReq.setCommonRequest(commonRequest);
				createDeviceReq.setUserKey(userKey);
				createDeviceReq.setIsNew("N");
				createDeviceReq.setUserMbrDevice(userMbrDevice);

				this.deviceSCI.createDevice(createDeviceReq);

				/* MQ 연동 */
				ModifyDeviceAmqpSacReq mqInfo = new ModifyDeviceAmqpSacReq();
				try {
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					mqInfo.setUserKey(userKey);
					mqInfo.setOldUserKey(userKey);
					mqInfo.setDeviceKey(deviceKey);
					mqInfo.setOldDeviceKey(deviceKey);
					mqInfo.setDeviceId(mdn);
					mqInfo.setOldDeviceId(beMdn);
					mqInfo.setMnoCd(MemberConstants.DEVICE_TELECOM_SKT);
					mqInfo.setOldMnoCd(MemberConstants.DEVICE_TELECOM_SKT);
					mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_CHANGE);
					this.memberModDeviceAmqpTemplate.convertAndSend(mqInfo);

				} catch (AmqpException ex) {
					LOGGER.info("MQ process fail {}", mqInfo);
				}

				/* 사용자제한정책 mdn 변경(as-is 구매한도/선물수신한도 / 단말테스터 변경용) */
				// UpdatePolicyKeyRequest updPolicyKeyReq = new UpdatePolicyKeyRequest();
				// updPolicyKeyReq.setCommonRequest(commonRequest);
				// updPolicyKeyReq.setOldLimitPolicyKey(beMdn);
				// updPolicyKeyReq.setNewLimitPolicyKey(mdn);
				// UpdatePolicyKeyResponse updPolicyKeyRes = this.userSCI.updatePolicyKey(updPolicyKeyReq);
				//
				// UpdatePolicyValueRequest updPolicyValueReq = new UpdatePolicyValueRequest();
				// updPolicyValueReq.setCommonRequest(commonRequest);
				// updPolicyValueReq.setOldApplyValue(beMdn);
				// updPolicyValueReq.setNewApplyValue(mdn);
				// UpdatePolicyValueResponse updPolicyValueRes = this.userSCI.updatePolicyValue(updPolicyValueReq);
				// LOGGER.info("::: 사용자제한정책 mdn 변경 카운트 policyKey : {},  policyValue : {}",
				// updPolicyKeyRes.getUpdateCount(), updPolicyValueRes.getUpdateCount());
				this.transferLimitTargetInfo(tenantId, systemId, map.get("cmd"), mdn, beMdn);

			}

			resultCode = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;
			resultText = IdpConstants.IDP_RESPONSE_SUCCESS_MSG;

		} catch (StorePlatformException ex) {
			LOGGER.error(ex.getMessage(), ex);

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					|| StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				resultCode = IdpConstants.IDP_RESPONSE_NO_DATA;
				resultText = IdpConstants.IDP_RESPONSE_NO_DATA_MSG;
			} else {
				resultCode = IdpConstants.IDP_RESPONSE_FAIL_CODE;
				resultText = IdpConstants.IDP_RESPONSE_FAIL_MSG;
			}

		} finally {

			/* 휴대기기 변경 히스토리 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_CHANGE);
			changeDeviceLog.setDeviceID(mdn);
			changeDeviceLog.setMessageIDP(requestUrl);
			if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData(beMdn);
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_NO_DATA)) {
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

			try {
				this.insertChangedDeviceHis(commonRequest, changeDeviceLog);
			} catch (StorePlatformException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}

		/* TLog 남김(SAC 회원 device_ID변경) */
		final String fdsLogUserKey = userKey;
		final String fdsLogBeMdn = beMdn;
		final String fdsLogMdn = mdn;
		final String fdsLogSvcMngNum = svcMngNum;
		final String fdsLogDeviceKey = deviceKey;
		final String fdsSystemId = systemId;
		final String fdsResult = resultCode;
		final String fdsResultMsg = resultText;

		if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0002").result_code("SUCC").result_message("")
							.insd_usermbr_no(fdsLogUserKey).insd_device_id(fdsLogDeviceKey).device_id(fdsLogMdn)
							.device_id_pre(fdsLogBeMdn).device_id_post(fdsLogMdn).svc_mng_no(fdsLogSvcMngNum)
							.insd_device_id_pre(fdsLogDeviceKey).insd_device_id_post(fdsLogDeviceKey)
							.request_system_id(fdsSystemId).exception_log("");
				}
			});
		} else {
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0002").result_code(fdsResult).result_message(fdsResultMsg)
							.insd_usermbr_no(fdsLogUserKey).insd_device_id(fdsLogDeviceKey).device_id(fdsLogMdn)
							.device_id_pre(fdsLogBeMdn).device_id_post(fdsLogMdn).svc_mng_no(fdsLogSvcMngNum)
							.insd_device_id_pre(fdsLogDeviceKey).insd_device_id_post(fdsLogDeviceKey)
							.request_system_id(fdsSystemId).exception_log("");
				}
			});
		}

		return resultCode;
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
	public void insertChangedDeviceHis(CommonRequest commonRequest, ChangedDeviceLog changeDeviceLog) {
		CreateChangedDeviceRequest createChangeDeviceReq = new CreateChangedDeviceRequest();
		createChangeDeviceReq.setCommonRequest(commonRequest);
		createChangeDeviceReq.setChangedDeviceLog(changeDeviceLog);

		this.userSCI.createChangedDevice(createChangeDeviceReq);
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
	public String changeMobileID(HashMap<String, String> map) {

		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String uacd = StringUtil.nvl(map.get("model_id"), ""); // uacd
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");
		String deviceTelecom = MemberConstants.DEVICE_TELECOM_SKT;
		String deviceNickName = null;
		String preData = null;

		String userKey = null;
		String deviceKey = null;
		String modelCd = null;
		String resultCode = null;
		String isDormant = null;

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
			isDormant = schDeviceRes.getUserMbrDevice().getIsDormant();
			LOGGER.info("휴면계정유무 : {}", isDormant);

			Device device = this.mcc.getPhoneInfoByUacd(uacd);

			if (device == null) {
				LOGGER.info(
						"<changeMobileID> NOT SUPPORT DEVICE.(기기변경 대상 단말이 존재하지 않음- 미지원 휴대폰) mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}",
						mdn, null, uacd, svcMngNum);
				uacd = MemberConstants.NOT_SUPPORT_HP_UACODE;
				modelCd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
				deviceNickName = MemberConstants.NOT_SUPPORT_HP_MODEL_NM;

			} else {

				modelCd = device.getDeviceModelCd();
				// 저장된 정보가 미지원단말인경우 디폴트 모델명을 닉네임으로 셋팅
				if (StringUtils.equals(schDeviceRes.getUserMbrDevice().getDeviceNickName(),
						MemberConstants.NOT_SUPPORT_HP_MODEL_NM)
						&& StringUtils.equals(schDeviceRes.getUserMbrDevice().getDeviceModelNo(),
								MemberConstants.NOT_SUPPORT_HP_MODEL_CD)) {
					deviceNickName = device.getModelNm();
				}

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
					SearchPolicyResponse policyResponse = null;

					try {
						policyResponse = this.userSCI.searchPolicyList(policyRequest);
					} catch (StorePlatformException e) {
						// ignore
					}

					if (policyResponse != null) {
						for (LimitTarget limitTarget : policyResponse.getLimitTargetList()) {
							if (limitTarget.getLimitPolicyKey().equals(mdn)) {
								isTestModel = "Y";
								break;
							}
						}
					}

					if (StringUtil.equals(isTestModel, "Y")) {
						LOGGER.info(
								"<changeMobileID> 단말 테스터이고 타겟 단말 mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}",
								mdn, modelCd, uacd, svcMngNum);
					} else {
						LOGGER.info(
								"<changeMobileID> NOT SUPPORT DEVICE.(타겟단말이고 단말테스터가 아닌경우- 미지원 휴대폰) mdn : {}, model_cd : {}, uacd : {}, svc_mng_num : {}",
								mdn, modelCd, uacd, svcMngNum);
						// 일반 사용자인 경우 - 미지원 휴대폰으로
						uacd = MemberConstants.NOT_SUPPORT_HP_UACODE;
						modelCd = MemberConstants.NOT_SUPPORT_HP_MODEL_CD;
						deviceNickName = MemberConstants.NOT_SUPPORT_HP_MODEL_NM;
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
			userMbrDevice.setDeviceTelecom(deviceTelecom);
			userMbrDevice.setDeviceNickName(deviceNickName);
			/*
			 * 기기변경시 nativeId 가 변경되어 변동성 확인 API에서 지메일까지 불일치 하면 회원이 탈퇴처리 되므로 기기변경프로비져닝에서 변경된 기기의 nativeId를 업데이트 한다.
			 */
			userMbrDevice.setNativeID(this.deviceService.getIcasImei(mdn));
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

			/* MQ 연동 */
			ModifyDeviceAmqpSacReq mqInfo = new ModifyDeviceAmqpSacReq();
			try {
				mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
				mqInfo.setUserKey(userKey);
				// mqInfo.setOldUserKey(userKey);
				mqInfo.setDeviceKey(deviceKey);
				// mqInfo.setOldDeviceKey(deviceKey);
				mqInfo.setDeviceId(mdn);
				// mqInfo.setOldDeviceId(mdn);
				mqInfo.setMnoCd(MemberConstants.DEVICE_TELECOM_SKT);
				mqInfo.setOldMnoCd(MemberConstants.DEVICE_TELECOM_SKT);
				mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_INSERT);
				this.memberModDeviceAmqpTemplate.convertAndSend(mqInfo);
			} catch (AmqpException ex) {
				LOGGER.info("MQ process fail {}", mqInfo);
			}

			resultCode = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;

		} catch (StorePlatformException ex) {

			LOGGER.error(ex.getMessage(), ex);

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					|| StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				resultCode = IdpConstants.IDP_RESPONSE_NO_DATA;
			} else {
				resultCode = IdpConstants.IDP_RESPONSE_FAIL_CODE;
			}

		} finally {
			/* 휴대기기 변경 히스토리 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_MODEL_CHANGE);
			changeDeviceLog.setDeviceID(mdn);
			changeDeviceLog.setMessageIDP(requestUrl);
			if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData(preData);
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_NO_DATA)) {
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
			try {
				this.insertChangedDeviceHis(commonRequest, changeDeviceLog);
			} catch (StorePlatformException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}

		return resultCode;
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
	public String secedeMobileNumber(HashMap<String, String> map) {

		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");
		String svcRsnCd = StringUtil.nvl(map.get("svc_rsn_cd"), ""); // 해지사유
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");

		String userKey = null;
		String deviceKey = null;
		String resultCode = null;
		String changeCaseCode = null;
		String isDormant = null;

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
			isDormant = schDeviceRes.getUserMbrDevice().getIsDormant();
			LOGGER.info("휴면계정유무 : {}", isDormant);

			SearchUserRequest schUserReq = new SearchUserRequest();
			schUserReq.setCommonRequest(commonRequest);
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			key.setKeyString(schDeviceRes.getUserKey());
			keySearchList.add(key);
			schUserReq.setKeySearchList(keySearchList);

			SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

			/********************
			 * 무선 회원인 경우.
			 ********************/
			String resultLogStr = "";
			if (StringUtil.equals(schUserRes.getUserMbr().getUserType(), MemberConstants.USER_TYPE_MOBILE)) {

				if (StringUtil.equals(svcRsnCd, "M1NC")) { // 명의변경
					changeCaseCode = MemberConstants.DEVICE_CHANGE_TYPE_NAME_CHANGE;
				} else {
					changeCaseCode = MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_SECEDE;
				}

				/********************************
				 * 번호이동당일해지 || 번호이동해지
				 ********************************/
				if (StringUtil.equals(svcRsnCd, "Z222") || StringUtil.equals(svcRsnCd, "Z261")) {

					changeCaseCode = MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_MOVE;

					/* 휴대기기 수정 요청 */
					CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
					UserMbrDevice userMbrDevice = new UserMbrDevice();
					userMbrDevice.setUserKey(userKey);
					userMbrDevice.setDeviceID(mdn);
					userMbrDevice.setDeviceKey(deviceKey);
					userMbrDevice.setChangeCaseCode(changeCaseCode); // 휴대기기 변경 유형코드 : 번호이동

					createDeviceReq.setCommonRequest(commonRequest);
					createDeviceReq.setUserKey(userKey);
					createDeviceReq.setIsNew("N");
					createDeviceReq.setUserMbrDevice(userMbrDevice);
					this.deviceSCI.createDevice(createDeviceReq);

					/* 휴대기기 삭제 요청 */
					List<String> removeKeyList = new ArrayList<String>();
					removeKeyList.add(deviceKey);
					RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
					removeDeviceReq.setCommonRequest(commonRequest);
					removeDeviceReq.setUserKey(userKey);
					removeDeviceReq.setDeviceKey(removeKeyList);
					removeDeviceReq.setIsDormant(isDormant);
					this.deviceSCI.removeDevice(removeDeviceReq);

					/* 회원상태 업데이트 */
					UpdateStatusUserRequest updStatusUserReq = new UpdateStatusUserRequest();
					key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
					key.setKeyString(userKey);
					keySearchList.add(key);
					updStatusUserReq.setCommonRequest(commonRequest);
					updStatusUserReq.setKeySearchList(keySearchList);
					updStatusUserReq.setUserMainStatus(MemberConstants.MAIN_STATUS_SECEDE); // 탈퇴
					updStatusUserReq.setUserSubStatus(MemberConstants.SUB_STATUS_CHANGE_USER); // 변동성 대상
					updStatusUserReq.setIsDormant(isDormant);
					this.userSCI.updateStatus(updStatusUserReq);

					resultLogStr = "변동성대상처리";

				} else {

					/* 회원 탈퇴 */
					RemoveUserRequest scReq = new RemoveUserRequest();
					scReq.setCommonRequest(commonRequest);
					scReq.setUserKey(userKey);
					scReq.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
					scReq.setSecedeReasonMessage("");
					scReq.setIsDormant(isDormant);
					this.userSCI.remove(scReq);

					resultLogStr = "회원탈퇴";

				}

				/* MQ 연동(회원탈퇴) */
				RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
				try {

					mqInfo.setUserId(schUserRes.getUserMbr().getUserID());
					mqInfo.setUserKey(userKey);
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					mqInfo.setDeviceId(mdn);

					List<MbrMangItemPtcr> list = schUserRes.getMbrMangItemPtcrList();
					for (int i = 0; i < list.size(); i++) {
						MbrMangItemPtcr extraInfo = list.get(i);
						if (StringUtils.equals(MemberConstants.USER_EXTRA_PROFILEIMGPATH, extraInfo.getExtraProfile())) {
							mqInfo.setProfileImgPath(extraInfo.getExtraProfileValue());
						}
					}

					this.memberRetireAmqpTemplate.convertAndSend(mqInfo);
				} catch (AmqpException ex) {
					LOGGER.info("MQ process fail {}", mqInfo);
				}

			} else {

				/**************************
				 * MDN이 ID에 붙은 경우
				 *************************/
				if (StringUtil.equals(svcRsnCd, "M1NC")) { // 명의변경
					changeCaseCode = MemberConstants.DEVICE_CHANGE_TYPE_NAME_CHANGE;
				} else {
					changeCaseCode = MemberConstants.DEVICE_CHANGE_TYPE_NUMBER_SECEDE;
				}

				/* 휴대기기 삭제 요청 */
				List<String> removeKeyList = new ArrayList<String>();
				removeKeyList.add(deviceKey);

				RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
				removeDeviceReq.setCommonRequest(commonRequest);
				removeDeviceReq.setUserKey(userKey);
				removeDeviceReq.setDeviceKey(removeKeyList);
				removeDeviceReq.setIsDormant(isDormant);
				this.deviceSCI.removeDevice(removeDeviceReq);

				/* MQ 연동(휴대기기 삭제) */
				RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();
				try {
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					mqInfo.setUserKey(userKey);
					mqInfo.setDeviceKey(deviceKey);
					mqInfo.setDeviceId(mdn);
					mqInfo.setSvcMangNo(svcMngNum);
					mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);

					this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);
				} catch (AmqpException ex) {
					LOGGER.info("MQ process fail {}", mqInfo);

				}

				resultLogStr = "휴대기기삭제";

			}

			LOGGER.info("{},결과:{},Type:{},svcRsnCd:{},changeCaseCode:{}", mdn, resultLogStr, schUserRes.getUserMbr()
					.getUserType(), svcRsnCd, changeCaseCode);

			this.transferLimitTargetInfo(tenantId, systemId, map.get("cmd"), mdn, null);

			resultCode = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;
		} catch (StorePlatformException ex) {

			LOGGER.error(ex.getMessage(), ex);

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					|| StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				resultCode = IdpConstants.IDP_RESPONSE_NO_DATA;
			} else {
				resultCode = IdpConstants.IDP_RESPONSE_FAIL_CODE;
			}

		} finally {

			/* 휴대기기 변경 히스토리 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(changeCaseCode);
			changeDeviceLog.setDeviceID(mdn);
			changeDeviceLog.setMessageIDP(requestUrl);
			if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData("");
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_NO_DATA)) {
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
			try {
				this.insertChangedDeviceHis(commonRequest, changeDeviceLog);
			} catch (StorePlatformException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}

		}

		return resultCode;
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
	public String joinComplete(HashMap<String, String> map) {

		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");
		String mbrNo = StringUtil.nvl(map.get("user_key"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");
		String userKey = null;
		String resultCode = null;
		String isDormant = null;

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
			isDormant = schUserRes.getUserMbr().getIsDormant();
			LOGGER.info("휴면계정유무 : {}", isDormant);

			/* 회원 탈퇴 처리 */
			RemoveUserRequest removeUserReq = new RemoveUserRequest();
			removeUserReq.setCommonRequest(commonRequest);
			removeUserReq.setUserKey(userKey);
			removeUserReq.setSecedeTypeCode(MemberConstants.USER_WITHDRAW_CLASS_JOIN_AGREE_EXPIRED); // 가입승인만료
			removeUserReq.setSecedeReasonCode(MemberConstants.WITHDRAW_REASON_OTHER); // 기타
			removeUserReq.setSecedeReasonMessage("가입승인만료");
			removeUserReq.setIsDormant(isDormant);
			this.userSCI.remove(removeUserReq);

			/* MQ 연동 */
			RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
			try {

				mqInfo.setUserId(schUserRes.getUserMbr().getUserID());
				mqInfo.setUserKey(userKey);
				mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
				this.memberRetireAmqpTemplate.convertAndSend(mqInfo);
			} catch (AmqpException ex) {
				LOGGER.info("MQ process fail {}", mqInfo);
			}
			resultCode = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;
		} catch (StorePlatformException ex) {

			LOGGER.error(ex.getMessage(), ex);

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					|| StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				resultCode = IdpConstants.IDP_RESPONSE_NO_DATA;
			} else {
				resultCode = IdpConstants.IDP_RESPONSE_FAIL_CODE;
			}

		} finally {
			/* 휴대기기 변경 히스토리 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_EMAIL_JOIN_COMPLETE);
			changeDeviceLog.setDeviceID(mdn);
			changeDeviceLog.setMessageIDP(requestUrl);
			if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData("");
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_NO_DATA)) {
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
			try {
				this.insertChangedDeviceHis(commonRequest, changeDeviceLog);
			} catch (StorePlatformException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}

		return resultCode;
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
	public String adjustWiredProfile(HashMap<String, String> map) {

		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String imIntSvcNo = StringUtil.nvl(map.get("im_int_svc_no"), "");
		String imMbrNo = StringUtil.nvl(map.get("user_key"), "");
		String userKey = null;
		String resultCode = null;
		String isDormant = null;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
		commonRequest.setSystemID(StringUtil.nvl(map.get("systemID"), ""));

		try {

			if (StringUtil.isNotBlank(imMbrNo)) {

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
				isDormant = schUserRes.getUserMbr().getIsDormant();
				LOGGER.info("휴면계정유무 : {}", isDormant);

				/* 변경정보 셋팅 */
				UserMbr userMbr = new UserMbr();
				userMbr.setUserKey(userKey);

				String userPhone = map.get("user_phone");
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

				if (StringUtil.equals(imIntSvcNo, "")) { // 통합회원이 아닌경우
					/* 회원정보 변경 요청 */
					UpdateUserRequest updateUserRequest = new UpdateUserRequest();
					updateUserRequest.setUserMbr(userMbr);
					updateUserRequest.setCommonRequest(commonRequest);
					updateUserRequest.setIsDormant(isDormant);
					this.userSCI.updateUser(updateUserRequest);
				}

				/* 휴대기기 기기 정보 변경 */
				if (userPhone != null && !StringUtils.equals(userPhone, "")) {

					/* 사용자 휴대기기 목록 조회 */
					SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
					schDeviceListReq.setUserKey(userKey);
					schDeviceListReq.setIsMainDevice("N");
					key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
					key.setKeyString(userKey);
					keySearchList.add(key);
					schDeviceListReq.setKeySearchList(keySearchList);
					schDeviceListReq.setCommonRequest(commonRequest);
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
								LOGGER.info(
										"<executeAdjustWiredProfile> NOT SUPPORT DEVICE. mdn : {}, uacd : {}, svc_mng_num : {}",
										deviceId, uaCd, svcMangNum);
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
									if (device == null) {
										modifyDevice.setDeviceNickName(MemberConstants.NOT_SUPPORT_HP_MODEL_NM);
									} else {
										modifyDevice.setDeviceTelecom(this.mcc.convertDeviceTelecomCode(deviceTelecom));
										// 저장된 정보가 미지원단말인경우 디폴트 모델명을 닉네임으로 셋팅
										if (StringUtils.equals(userMbrDevice.getDeviceNickName(),
												MemberConstants.NOT_SUPPORT_HP_MODEL_NM)
												&& StringUtils.equals(userMbrDevice.getDeviceModelNo(),
														MemberConstants.NOT_SUPPORT_HP_MODEL_CD)) {
											modifyDevice.setDeviceNickName(device.getModelNm());
										}
									}

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

								/* mdn 삭제 MQ 연동 */
								RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();
								try {
									mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
									mqInfo.setUserKey(userMbrDevice.getUserKey());
									mqInfo.setDeviceKey(userMbrDevice.getDeviceKey());
									mqInfo.setDeviceId(userMbrDevice.getDeviceID());
									mqInfo.setSvcMangNo(userMbrDevice.getSvcMangNum());
									mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);
									this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);
								} catch (AmqpException ex) {
									LOGGER.info("MQ process fail {}", mqInfo);
								}
							}

						}

						if (removeKeyList.size() > 0) {
							RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
							removeDeviceReq.setCommonRequest(commonRequest);
							removeDeviceReq.setUserKey(userKey);
							removeDeviceReq.setDeviceKey(removeKeyList);
							removeDeviceReq.setIsDormant(isDormant);
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
							LOGGER.info(
									"<executeAdjustWiredProfile> NOT SUPPORT DEVICE. mdn : {}, uacd : {}, svc_mng_num : {}",
									deviceId, uaCd, svcMangNum);
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
								if (device == null) {
									modifyDevice.setDeviceNickName(MemberConstants.NOT_SUPPORT_HP_MODEL_NM);
								} else {
									modifyDevice.setDeviceTelecom(this.mcc.convertDeviceTelecomCode(deviceTelecom));
									// 저장된 정보가 미지원단말인경우 디폴트 모델명을 닉네임으로 셋팅
									if (StringUtils.equals(userMbrDevice.getDeviceNickName(),
											MemberConstants.NOT_SUPPORT_HP_MODEL_NM)
											&& StringUtils.equals(userMbrDevice.getDeviceModelNo(),
													MemberConstants.NOT_SUPPORT_HP_MODEL_CD)) {
										modifyDevice.setDeviceNickName(device.getModelNm());
									}
								}
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

								/* mdn 삭제 MQ 연동 */
								RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();
								try {
									mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
									mqInfo.setUserKey(userMbrDevice.getUserKey());
									mqInfo.setDeviceKey(userMbrDevice.getDeviceKey());
									mqInfo.setDeviceId(userMbrDevice.getDeviceID());
									mqInfo.setSvcMangNo(userMbrDevice.getSvcMangNum());
									mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);
									this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);
								} catch (AmqpException ex) {
									LOGGER.info("MQ process fail {}", mqInfo);
								}
							}

						}

						if (removeKeyList.size() > 0) {
							RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
							removeDeviceReq.setCommonRequest(commonRequest);
							removeDeviceReq.setUserKey(userKey);
							removeDeviceReq.setDeviceKey(removeKeyList);
							removeDeviceReq.setIsDormant(isDormant);
							this.deviceSCI.removeDevice(removeDeviceReq);
						}
					}
				} else if (StringUtils.equals(userPhone, "")) {

					// user_phone이 빈값으로 내려온 경우 기존IDP계정/통합계정에 MDN이 모두 삭제 된 것으로 판단하여 휴대기기 삭제처리.
					// ex) AAA 아이디, 111MDN 등록되어 있을 때 , BBB계정에 111MDN 등록시 AAA계정정보로 user_phone이 빈값이 내려옴.
					// 휴대기기 등록시 AAA아이디의 111MDN은 invalid 처리를 하기 때문에 정상적인 경우라면 휴대기기 삭제처리할 MDN이 없어서 아무 처리도 안하게 됨.
					// 단순 보청처리용으로 추가되었음.

					/* 사용자 휴대기기 목록 조회 */
					SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
					schDeviceListReq.setUserKey(userKey);
					schDeviceListReq.setIsMainDevice("N");
					key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
					key.setKeyString(userKey);
					keySearchList.add(key);
					schDeviceListReq.setKeySearchList(keySearchList);
					schDeviceListReq.setCommonRequest(commonRequest);
					try {
						SearchDeviceListResponse schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);
						if (schDeviceListRes != null && schDeviceListRes.getUserMbrDevice().size() > 0) {
							List<String> removeKeyList = new ArrayList<String>();
							for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {

								LOGGER.info("delete deviceId : {}", userMbrDevice.getDeviceID());
								removeKeyList.add(userMbrDevice.getDeviceKey());

								/* mdn 삭제 MQ 연동 */
								RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();
								try {
									mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
									mqInfo.setUserKey(userMbrDevice.getUserKey());
									mqInfo.setDeviceKey(userMbrDevice.getDeviceKey());
									mqInfo.setDeviceId(userMbrDevice.getDeviceID());
									mqInfo.setSvcMangNo(userMbrDevice.getSvcMangNum());
									mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);
									this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);
								} catch (AmqpException ex) {
									LOGGER.info("MQ process fail {}", mqInfo);
								}
							}

							RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
							removeDeviceReq.setCommonRequest(commonRequest);
							removeDeviceReq.setUserKey(userKey);
							removeDeviceReq.setDeviceKey(removeKeyList);
							removeDeviceReq.setIsDormant(isDormant);
							this.deviceSCI.removeDevice(removeDeviceReq);
						}
					} catch (StorePlatformException e) {
						if (!StringUtil.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
							throw e;
						}
					}
				}
				resultCode = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;
			} else {
				resultCode = IdpConstants.IDP_RESPONSE_NO_DATA;
			}

		} catch (StorePlatformException ex) {

			LOGGER.error(ex.getMessage(), ex);

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					|| StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				resultCode = IdpConstants.IDP_RESPONSE_NO_DATA;
			} else {
				resultCode = IdpConstants.IDP_RESPONSE_FAIL_CODE;
			}

		} finally {
			/* 휴대기기 변경 히스토리 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_MODIFY_PROFILE);
			changeDeviceLog.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
			if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData("");
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_NO_DATA)) {
				changeDeviceLog.setPreData("FAIL");
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_FAIL_CODE)) {
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
			try {
				this.insertChangedDeviceHis(commonRequest, changeDeviceLog);
			} catch (StorePlatformException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}

		return resultCode;
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
	public String ecgJoinedTStore(HashMap<String, String> map) {

		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String min = StringUtil.nvl(map.get("min"), "");
		String svcMngNum = StringUtil.nvl(map.get("svc_mng_num"), "");

		String userKey = null;
		String resultCode = null;
		String isDormant = null;

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
			isDormant = schDeviceRes.getUserMbrDevice().getIsDormant();
			LOGGER.info("휴면계정유무 : {}", isDormant);

			if (StringUtils.equals(isDormant, MemberConstants.USE_N)) {
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
			}

			resultCode = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;
		} catch (StorePlatformException ex) {

			LOGGER.error(ex.getMessage(), ex);

			if (StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					|| StringUtil.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				resultCode = IdpConstants.IDP_RESPONSE_NO_DATA;
			} else {
				resultCode = IdpConstants.IDP_RESPONSE_FAIL_CODE;
			}

		} finally {
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_JOIN_ECG);
			changeDeviceLog.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
			if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData("");
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_NO_DATA)) {

				changeDeviceLog.setPreData("");
				/* 비회원인 경우 후 성공처리 */
				UpdateNonMbrSegmentRequest req = new UpdateNonMbrSegmentRequest();
				req.setCommonRequest(commonRequest);
				NonMbrSegment nonMbrSegment = new NonMbrSegment();
				nonMbrSegment.setDeviceID(mdn);
				nonMbrSegment.setSvcMangNum(svcMngNum);
				req.setNonMbrSegment(nonMbrSegment);
				this.userSCI.updateNonMbrSegment(req);

				resultCode = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_FAIL_CODE)) {
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
			try {
				this.insertChangedDeviceHis(commonRequest, changeDeviceLog);
			} catch (StorePlatformException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}

		return resultCode;
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
	public String ecgScededTStore(HashMap<String, String> map) {
		return IdpConstants.IDP_RESPONSE_SUCCESS_CODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.idp.service.IdpProvisionService#checkDeactivateStatusForSP(java.util.HashMap
	 * )
	 */
	@Override
	public ProvisioningResult checkDeactivateStatusForSP(HashMap<String, String> map) {

		String resultCode = null;
		String resultText = null;
		String userKey = null;
		String deactivateStatus = null; // Y : 분리보관 N : 정상사용 S : 분리보관 예정
		String isDormant = "";
		String keyType = map.get("key_type");
		String key = map.get("key");
		String requestUrl = StringUtil.nvl(map.get("requestUrl"), "");

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
		commonRequest.setSystemID(StringUtil.nvl(map.get("systemID"), ""));

		try {

			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch keySearch = new KeySearch();

			if (StringUtils.equals(keyType, "1")) { // One아이디
				keySearch.setKeyType(MemberConstants.KEY_TYPE_INTG_SVC_NO);
			} else if (StringUtils.equals(keyType, "2") || StringUtils.equals(keyType, "3")) { // IDP아이디, 모바일 회원
				keySearch.setKeyType(MemberConstants.KEY_TYPE_USERMBR_NO);
			} else {
				ProvisioningResult result = new ProvisioningResult();
				result.setCmd(map.get("cmd"));
				result.setResult(IdpConstants.IDP_RESPONSE_FAIL_CODE);
				result.setResultText(IdpConstants.IDP_RESPONSE_FAIL_MSG);
				return result;
			}
			keySearch.setKeyString(key);
			keySearchList.add(keySearch);
			SearchUserRequest searchUserRequest = new SearchUserRequest();
			searchUserRequest.setCommonRequest(commonRequest);
			searchUserRequest.setKeySearchList(keySearchList);
			SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);
			isDormant = searchUserResponse.getUserMbr().getIsDormant();
			userKey = searchUserResponse.getUserMbr().getUserKey();

			if (StringUtils.equals(isDormant, MemberConstants.USE_N)) {
				deactivateStatus = "N";

				// 휴면계정 분리보관 예정 대상 조회
				SearchDeActivateUserRequest searchDeActivateUserRequest = new SearchDeActivateUserRequest();
				searchDeActivateUserRequest.setCommonRequest(commonRequest);
				searchDeActivateUserRequest.setUserKey(userKey);
				searchDeActivateUserRequest.setWeekAgo("4"); // 휴면계정 전환 4주전 대상이 IDP 분리보관 예정 연동 대상
				try {
					SearchDeActivateUserResponse searchDeActivateUserResponse = this.userSCI
							.searchDeActivateUser(searchDeActivateUserRequest);
					if (searchDeActivateUserResponse.getUserMbr() != null) {
						deactivateStatus = "S";
					}
				} catch (StorePlatformException e) {
					if (!StringUtil.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
						ProvisioningResult result = new ProvisioningResult();
						result.setCmd(map.get("cmd"));
						result.setResult(IdpConstants.IDP_RESPONSE_FAIL_CODE);
						result.setResultText(IdpConstants.IDP_RESPONSE_FAIL_MSG);
						return result;
					}
				}

			} else if (StringUtils.equals(isDormant, MemberConstants.USE_Y)) {
				deactivateStatus = "Y";
			}

			resultCode = IdpConstants.IDP_RESPONSE_SUCCESS_CODE;
			resultText = IdpConstants.IDP_RESPONSE_SUCCESS_MSG;

		} catch (StorePlatformException e) {
			LOGGER.error(e.getMessage(), e);

			if (StringUtil.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)
					|| StringUtil.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_USERKEY)) {
				resultCode = IdpConstants.IDP_RESPONSE_NO_DATA;
				resultText = IdpConstants.IDP_RESPONSE_NO_DATA_MSG;
			} else {
				resultCode = IdpConstants.IDP_RESPONSE_FAIL_CODE;
				resultText = IdpConstants.IDP_RESPONSE_FAIL_MSG;
			}
		} finally {
			/* 휴대기기 변경 히스토리 저장 */
			ChangedDeviceLog changeDeviceLog = new ChangedDeviceLog();
			changeDeviceLog.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_MODIFY_PROFILE);
			changeDeviceLog.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
			if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_SUCCESS_CODE)) {
				changeDeviceLog.setPreData("");
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_NO_DATA)) {
				changeDeviceLog.setPreData("FAIL");
			} else if (StringUtil.equals(resultCode, IdpConstants.IDP_RESPONSE_FAIL_CODE)) {
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
			try {
				this.insertChangedDeviceHis(commonRequest, changeDeviceLog);
			} catch (StorePlatformException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}

		ProvisioningResult result = new ProvisioningResult();
		result.setCmd(map.get("cmd"));
		result.setResult(resultCode);
		result.setResultText(resultText);
		result.setDeactivateStatus(deactivateStatus);
		return result;

	}

	public void transferLimitTargetInfo(String tenantId, String systemId, String cmd, String mdn, String beMdn) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		if (StringUtils.equals(cmd, "changeMobileNumber")) {
			UpdatePolicyRequest updatePolicyRequest = null;
			List<LimitTarget> limitTargetList = null;
			LimitTarget limitTarget = null;
			List<String> limitPolicyCodeList = new ArrayList<String>();
			limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_SERVICE_STOP_TSTORE);
			limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_SERVICE_STOP_NATECA);
			SearchPolicyRequest policyRequest = new SearchPolicyRequest();
			policyRequest.setCommonRequest(commonRequest);
			policyRequest.setLimitPolicyKey(beMdn);
			policyRequest.setLimitPolicyCodeList(limitPolicyCodeList);
			try {
				SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);

				// 신규 MDN으로 이미 차단이력이 존재하면, 기존 차단이력 종료처리 및 해지처리
				limitPolicyCodeList = new ArrayList<String>();
				limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_SERVICE_STOP_TSTORE);
				limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_SERVICE_STOP_NATECA);
				policyRequest = new SearchPolicyRequest();
				policyRequest.setCommonRequest(commonRequest);
				policyRequest.setLimitPolicyKey(mdn);
				policyRequest.setLimitPolicyCodeList(limitPolicyCodeList);
				try {
					SearchPolicyResponse policyResponseByMdn = this.userSCI.searchPolicyList(policyRequest);
					for (LimitTarget limitTargetInfo : policyResponseByMdn.getLimitTargetList()) {
						if (StringUtils.equals(limitTargetInfo.getIsUsed(), MemberConstants.USE_Y)) {
							String regId = limitTargetInfo.getRegID();
							if (regId != null && regId.indexOf("|") > 0) {
								regId = regId.substring(0, regId.indexOf("|"));
							}
							// 신규 MDN 차단이력 종료처리
							LOGGER.info("{} 기등록 결제차단 해지처리", mdn);
							updatePolicyRequest = new UpdatePolicyRequest();
							limitTargetList = new ArrayList<LimitTarget>();
							limitTarget = new LimitTarget();
							limitTarget.setLimitTargetNo(limitTargetInfo.getLimitTargetNo());
							limitTarget.setUpdateID(regId + "|번호 변경");
							limitTarget.setLimitPolicyCode(limitTargetInfo.getLimitPolicyCode());
							limitTarget.setLimitPolicyKey(limitTargetInfo.getLimitPolicyKey());
							limitTarget.setRegID(limitTargetInfo.getRegID());
							limitTargetList.add(limitTarget);
							updatePolicyRequest.setCommonRequest(commonRequest);
							updatePolicyRequest.setLimitTargetList(limitTargetList);
							this.userSCI.updatePolicyHistory(updatePolicyRequest);

							String svcCdNm = StringUtils.equals(limitTargetInfo.getLimitPolicyCode(),
									MemberConstants.USER_LIMIT_POLICY_SERVICE_STOP_TSTORE) ? "단품" : "부분";

							// 신규 MDN 서비스제한 해지 처리
							updatePolicyRequest = new UpdatePolicyRequest();
							limitTargetList = new ArrayList<LimitTarget>();
							limitTarget = new LimitTarget();
							limitTarget.setRegID(regId + "|번호 변경");
							limitTarget.setLimitPolicyCode(limitTargetInfo.getLimitPolicyCode());
							limitTarget.setLimitPolicyKey(limitTargetInfo.getLimitPolicyKey());
							limitTarget.setPolicyApplyValue(beMdn + " -> " + mdn + " 번호 변경에 의해 " + beMdn + "의 "
									+ svcCdNm + " 결제 차단을 해지");
							limitTarget.setIsUsed(MemberConstants.USE_N);
							limitTarget.setPermissionType("2");
							limitTarget.setEndDate(DateUtil.getToday("yyyyMMddHHmmss"));
							limitTarget.setLineMangStatus("US013401");
							limitTargetList.add(limitTarget);
							updatePolicyRequest.setCommonRequest(commonRequest);
							updatePolicyRequest.setLimitTargetList(limitTargetList);
							this.userSCI.insertPolicy(updatePolicyRequest);
						}
					}
				} catch (StorePlatformException e) {
					// ignore
				}

				for (LimitTarget info : policyResponse.getLimitTargetList()) {
					if (StringUtils.equals(info.getIsUsed(), MemberConstants.USE_Y)) {
						LOGGER.info("{} -> {} 결제차단 승계처리", beMdn, mdn);
						String regId = info.getRegID();
						if (regId != null && regId.indexOf("|") > 0) {
							regId = regId.substring(0, regId.indexOf("|"));
						}
						// 이전 MDN 서비스제한 종료처리
						updatePolicyRequest = new UpdatePolicyRequest();
						limitTargetList = new ArrayList<LimitTarget>();
						limitTarget = new LimitTarget();
						limitTarget.setLimitTargetNo(info.getLimitTargetNo());
						limitTarget.setUpdateID(regId + "|번호 변경");
						limitTarget.setLimitPolicyCode(info.getLimitPolicyCode());
						limitTarget.setLimitPolicyKey(info.getLimitPolicyKey());
						limitTarget.setRegID(info.getRegID());
						limitTargetList.add(limitTarget);
						updatePolicyRequest.setCommonRequest(commonRequest);
						updatePolicyRequest.setLimitTargetList(limitTargetList);
						this.userSCI.updatePolicyHistory(updatePolicyRequest);

						String svcCdNm = StringUtils.equals(info.getLimitPolicyCode(),
								MemberConstants.USER_LIMIT_POLICY_SERVICE_STOP_TSTORE) ? "단품" : "부분";

						// 이전 MDN 서비스제한 해지 처리
						updatePolicyRequest = new UpdatePolicyRequest();
						limitTargetList = new ArrayList<LimitTarget>();
						limitTarget = new LimitTarget();
						limitTarget.setRegID(regId + "|번호 변경");
						limitTarget.setLimitPolicyCode(info.getLimitPolicyCode());
						limitTarget.setLimitPolicyKey(beMdn);
						limitTarget.setPolicyApplyValue(beMdn + " -> " + mdn + " 번호 변경에 의해 " + beMdn + "의 " + svcCdNm
								+ " 결제 차단을 해지");
						limitTarget.setIsUsed(MemberConstants.USE_N);
						limitTarget.setPermissionType("2");
						limitTarget.setEndDate(DateUtil.getToday("yyyyMMddHHmmss"));
						limitTarget.setLineMangStatus("US013401");
						limitTargetList.add(limitTarget);
						updatePolicyRequest.setCommonRequest(commonRequest);
						updatePolicyRequest.setLimitTargetList(limitTargetList);
						this.userSCI.insertPolicy(updatePolicyRequest);

						// 신규 MDN 서비스제한 차단 처리
						updatePolicyRequest = new UpdatePolicyRequest();
						limitTargetList = new ArrayList<LimitTarget>();
						limitTarget = new LimitTarget();
						limitTarget.setRegID(regId + "|번호 변경");
						limitTarget.setLimitPolicyCode(info.getLimitPolicyCode());
						limitTarget.setLimitPolicyKey(mdn);
						limitTarget
								.setPolicyApplyValue(beMdn + " 결제 차단을 해지하고, " + mdn + "가 " + svcCdNm + " 결제 차단을 승계함");
						limitTarget.setIsUsed(MemberConstants.USE_Y);
						limitTarget.setPermissionType("1");
						limitTarget.setEndDate("99991231235959");
						limitTarget.setLineMangStatus("US013401");
						limitTargetList.add(limitTarget);
						updatePolicyRequest.setCommonRequest(commonRequest);
						updatePolicyRequest.setLimitTargetList(limitTargetList);
						this.userSCI.insertPolicy(updatePolicyRequest);
					}
				}
			} catch (StorePlatformException e) {
				// ignore
			}
		} else if (StringUtils.equals(cmd, "secedeMobileNumber")) {
			UpdatePolicyRequest updatePolicyRequest = null;
			List<LimitTarget> limitTargetList = null;
			LimitTarget limitTarget = null;
			List<String> limitPolicyCodeList = new ArrayList<String>();
			limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_SERVICE_STOP_TSTORE);
			limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_SERVICE_STOP_NATECA);
			SearchPolicyRequest policyRequest = new SearchPolicyRequest();
			policyRequest.setCommonRequest(commonRequest);
			policyRequest.setLimitPolicyKey(mdn);
			policyRequest.setLimitPolicyCodeList(limitPolicyCodeList);
			try {
				SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);
				for (LimitTarget info : policyResponse.getLimitTargetList()) {
					if (StringUtils.equals(info.getIsUsed(), MemberConstants.USE_Y)) {
						LOGGER.info("{} 결제차단 해지처리", mdn);
						String regId = info.getRegID();
						if (regId != null && regId.indexOf("|") > 0) {
							regId = regId.substring(0, regId.indexOf("|"));
						}
						// 해지 MDN 서비스제한 종료처리
						updatePolicyRequest = new UpdatePolicyRequest();
						limitTargetList = new ArrayList<LimitTarget>();
						limitTarget = new LimitTarget();
						limitTarget.setLimitTargetNo(info.getLimitTargetNo());
						limitTarget.setUpdateID(regId + "|회선 해지");
						limitTarget.setLimitPolicyCode(info.getLimitPolicyCode());
						limitTarget.setLimitPolicyKey(info.getLimitPolicyKey());
						limitTarget.setRegID(info.getRegID());
						limitTargetList.add(limitTarget);
						updatePolicyRequest.setCommonRequest(commonRequest);
						updatePolicyRequest.setLimitTargetList(limitTargetList);
						this.userSCI.updatePolicyHistory(updatePolicyRequest);

						// 해지 MDN 서비스제한 해지 처리
						updatePolicyRequest = new UpdatePolicyRequest();
						limitTargetList = new ArrayList<LimitTarget>();
						limitTarget = new LimitTarget();
						limitTarget.setRegID(regId + "|회선 해지");
						limitTarget.setLimitPolicyCode(info.getLimitPolicyCode());
						limitTarget.setLimitPolicyKey(mdn);
						String svcCdNm = StringUtils.equals(info.getLimitPolicyCode(),
								MemberConstants.USER_LIMIT_POLICY_SERVICE_STOP_TSTORE) ? "단품" : "부분";
						limitTarget.setPolicyApplyValue(mdn + " 회선 해지에 따라 " + svcCdNm + " 결제 차단을 해지");
						limitTarget.setIsUsed(MemberConstants.USE_N);
						limitTarget.setPermissionType("2");
						limitTarget.setEndDate("99991231235959");
						limitTarget.setLineMangStatus("US013401");
						limitTargetList.add(limitTarget);
						updatePolicyRequest.setCommonRequest(commonRequest);
						updatePolicyRequest.setLimitTargetList(limitTargetList);
						this.userSCI.insertPolicy(updatePolicyRequest);
					}
				}
			} catch (StorePlatformException e) {
				// ignore
			}
		}
	}
}
