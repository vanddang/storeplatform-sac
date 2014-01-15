/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateAgreementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateAgreementResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceReq;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

/**
 * 휴대기기 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

	private static final String SYSTEMID = "S001";
	private static final String TENANTID = "S01";
	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
		commonRequest.setSystemID(SYSTEMID);
		commonRequest.setTenantID(TENANTID);
	}

	@Autowired
	MemberCommonComponent commService; // 회원 공통 서비스

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Autowired
	private IDPService idpService; // IDP 연동 클래스

	@Autowired
	private ImIDPService imIdpService; // 통합 IDP 연동 클래스

	@Autowired
	private IDPRepository idpRepository;

	@Autowired
	private UAPSSCI uapsSCI;

	@Value("#{propertiesForSac['idp.im.request.operation']}")
	public String IDP_OPERATION_MODE;

	@Override
	public CreateDeviceRes createDevice(HeaderVo headerVo, CreateDeviceReq req)
			throws Exception {

		logger.info("######################## DeviceServiceImpl createDevice start ############################");

		String userKey = req.getUserKey();
		String deviceId = req.getDeviceInfo().getDeviceId();

		/* 회원 정보 조회 */
		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		key.setKeyString(userKey);
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		if (!StringUtil.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new Exception("["	+ schUserRes.getCommonResponse().getResultCode() + "] " + schUserRes.getCommonResponse().getResultMessage());
		}

		if (schUserRes.getUserMbr().getDeviceCount() != null
				&& Integer.parseInt(schUserRes.getUserMbr().getDeviceCount()) >= req.getRegMaxCnt()) {
			throw new Exception("등록 가능한 단말개수가 초과되었습니다.");
		}

		/* 기등록된 회원의 휴대기기 정보 처리 */
		this.insertDeviceInfo(userKey, req.getDeviceInfo());

		/* sc회원 컴포넌트 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setIsMainDevice("N");
		listDeviceReq.setUserKey(userKey);
		listDeviceReq.setDeviceId(deviceId);
		ListDeviceRes listDeviceRes = this.listDevice(headerVo, listDeviceReq);

		List<DeviceInfo> deviceInfoList = listDeviceRes.getDeviceInfoList();
		StringBuffer sbUserPhone = new StringBuffer();
		if (deviceInfoList.size() > 0) {
			for (DeviceInfo deviceInfo : deviceInfoList) {
				sbUserPhone.append(deviceInfo.getDeviceId());
				sbUserPhone.append(",");
				sbUserPhone.append(deviceInfo.getImMngNum());
				sbUserPhone.append(",");
				sbUserPhone.append(deviceInfo.getUacd());
				sbUserPhone.append(",");
				sbUserPhone.append(deviceInfo.getDeviceTelecom());
				sbUserPhone.append("|");
			}
		}

		/* IDP 휴대기기 정보 등록 요청 */
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("user_auth_key", req.getUserAuthKey());

		UserMbr userMbr = schUserRes.getUserMbr();
		if (userMbr.getUserSex() != null) {
			param.put("user_sex", userMbr.getUserSex());
		}
		if (userMbr.getUserBirthDay() != null) {
			param.put("user_birthday", userMbr.getUserBirthDay());
		}
		if (userMbr.getUserZip() != null) {
			param.put("user_zipcode", userMbr.getUserZip());
		}
		if (userMbr.getUserAddress() != null) {
			param.put("user_address", userMbr.getUserAddress());
		}
		if (userMbr.getUserDetailAddress() != null) {
			param.put("user_address2", userMbr.getUserDetailAddress());
		}
		if (userMbr.getUserPhone() != null) {
			param.put("user_tel", userMbr.getUserPhone());
		}
		param.put("is_foreign", (userMbr.getUserPhoneCountry().equals("82") ? "N" : "Y"));

		if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_ONEID)) { // 통합회원

			param.put("cmd", "TXUpdateAdditionalUserInfoIDP");
			param.put("key_type", "1"); // IM 통합서비스 번호
			param.put("key", schUserRes.getUserMbr().getImSvcNo());
			param.put("operation_mode", this.IDP_OPERATION_MODE);
			param.put("user_mdn", sbUserPhone.toString());
			param.put("user_mdn_auth_key", this.idpRepository.makePhoneAuthKey(sbUserPhone.toString()));

			Date dtCur = new Date();
			param.put("modify_req_date", new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(dtCur));
			param.put("modify_req_time", new SimpleDateFormat("HHmmss",	Locale.KOREA).format(dtCur));

		} else {

			param.put("key_type", "2");
			param.put("key", schUserRes.getUserMbr().getImMbrNo());
			param.put("user_phone", sbUserPhone.toString());
			param.put("phone_auth_key",	this.idpRepository.makePhoneAuthKey(sbUserPhone.toString()));
			IDPReceiverM idpReceiver = this.idpService.modifyProfile(param);

			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new Exception("["	+ idpReceiver.getResponseHeader().getResult() + "] " + idpReceiver.getResponseHeader().getResult_text());
			}

		}

		logger.info("######################## DeviceServiceImpl createDevice start ############################");

		return null;
	}

	@Override
	public ListDeviceRes listDevice(HeaderVo headerVo, ListDeviceReq req)
			throws Exception {

		logger.info("######################## DeviceServiceImpl listDevice start ############################");

		String userKey = req.getUserKey();

		SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
		schDeviceListReq.setUserKey(userKey);
		schDeviceListReq.setIsMainDevice(req.getIsMainDevice());
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		if (req.getDeviceId() != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			key.setKeyString(req.getDeviceId());
		} else if (req.getUserId() != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
			key.setKeyString(req.getUserId());
		} else if (req.getUserKey() != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			key.setKeyString(req.getUserKey());
		}

		keySearchList.add(key);
		schDeviceListReq.setKeySearchList(keySearchList);
		schDeviceListReq.setCommonRequest(commonRequest);
		
		logger.info("###### schDeviceListReq : " + schDeviceListReq);
		logger.info("###### schDeviceListReq.getKeySearchList() : " + schDeviceListReq.getKeySearchList());
		logger.info("###### schDeviceListReq.getCommonRequest() : " + schDeviceListReq.getCommonRequest());
		
		/* 사용자 휴대기기 목록 조회 */
		SearchDeviceListResponse schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);
		if (!schDeviceListRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("["	+ schDeviceListRes.getCommonResponse().getResultCode() + "]" + schDeviceListRes.getCommonResponse().getResultMessage());
		}

		ListDeviceRes res = new ListDeviceRes();

		if (schDeviceListRes.getUserMbrDevice() != null
				&& schDeviceListRes.getUserMbrDevice().size() > 0) {

			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {
				deviceInfoList.add(this.getConverterDeviceInfo(userMbrDevice));
			}
			res.setDeviceInfoList(deviceInfoList);

		}

		logger.info("######################## DeviceServiceImpl listDevice end ############################");

		return res;
	}

	@Override
	public void insertDeviceInfo(String userKey, DeviceInfo deviceInfo)
			throws Exception {

		logger.info("######################## DeviceServiceImpl preRegMemberDeviceRegist start ############################");

		/* 1. 휴대기기 정보 등록 요청 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setIsNew("Y");
		createDeviceReq.setUserKey(userKey);
		createDeviceReq.setUserMbrDevice(this.getConverterUserMbrDeviceInfo(deviceInfo));

		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		/* 2.휴대기기 정보 등록완료 */
		if (StringUtil.equals(createDeviceRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

			/* 3. 구매이력 이관 여부 확인 [기존 회원 key, 신규 회원 key] */
			if (false) {

				/* 4. 구매이관 대상인 경우 구매이력 이관요청 */

				/* 5. 약관 이관 처리 */
				SearchAgreementListRequest schAgreeListReq = new SearchAgreementListRequest();
				schAgreeListReq.setCommonRequest(commonRequest);
				schAgreeListReq.setUserKey("기존 회원 key");
				SearchAgreementListResponse schAgreeListRes = this.userSCI.searchAgreementList(schAgreeListReq);
				if (schAgreeListRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
					UpdateAgreementRequest updAgreeReq = new UpdateAgreementRequest();
					updAgreeReq.setCommonRequest(commonRequest);
					updAgreeReq.setUserKey("신규 회원 key");
					updAgreeReq.setMbrClauseAgreeList(schAgreeListRes.getMbrClauseAgreeList());
					UpdateAgreementResponse updAgreeRes = this.userSCI.updateAgreement(updAgreeReq);

					if (!updAgreeRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
						throw new Exception("약관저장실패 [" + updAgreeRes.getCommonResponse().getResultCode() + "]" + updAgreeRes.getCommonResponse().getResultMessage());
					}
				} else {
					throw new Exception("약관조회실패 [" + schAgreeListRes.getCommonResponse().getResultCode()	+ "]" + schAgreeListRes.getCommonResponse().getResultMessage());
				}

				/* 6. 통합회원인 경우 무선회원 해지 */
				SearchUserRequest schUserReq = new SearchUserRequest();
				schUserReq.setCommonRequest(commonRequest);
				List<KeySearch> keySearchList = new ArrayList<KeySearch>();
				KeySearch key = new KeySearch();
				key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
				key.setKeyString(userKey);
				keySearchList.add(key);
				schUserReq.setKeySearchList(keySearchList);
				SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

				if (!schUserRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
					throw new Exception("["	+ schUserRes.getCommonResponse().getResultCode() + "] "	+ schUserRes.getCommonResponse().getResultMessage());
				}
				
				/*	통합 회원 아이디에 휴대기기 등록 시 IDP에 무선 회원 해지 요청 - 자동 해지 안된다고 함	*/
				if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_ONEID)) {
					IDPReceiverM idpReceiver = this.idpService.authForWap(deviceInfo.getDeviceId());
					if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
						
						idpReceiver = this.idpService.secedeUser4Wap(deviceInfo.getDeviceId()); 
						if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) { 
							throw new Exception("IDP secedeForWap fail mdn : [" + deviceInfo.getDeviceId() + "] result code : [" + idpReceiver.getResponseHeader().getResult() + "]"); 
						}
					}
				}

			}
				
		} else {
			throw new Exception("["	+ createDeviceRes.getCommonResponse().getResultCode() + "] " + createDeviceRes.getCommonResponse().getResultMessage());
		}

		
		logger.info("######################## DeviceServiceImpl preRegMemberDeviceRegist end ############################");
	}

	@Override
	public void mergeDeviceInfo(DeviceInfo req) throws Exception {

		logger.info("################ mergeDeviceInfo start ##################");
		
		String deviceId = req.getDeviceId();
		
		if (deviceId == null) {
			throw new Exception("deviceId is null 기기정보 수정 불가");
		}

		/* 기기정보 조회 */
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		key.setKeyString(deviceId);
		keySearchList.add(key);

		schDeviceReq.setCommonRequest(commonRequest);
		schDeviceReq.setUserKey(req.getUserKey());
		schDeviceReq.setKeySearchList(keySearchList);

		logger.info(" param : {}" + schDeviceReq.toString());

		SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(schDeviceReq);
		UserMbrDevice userMbrDevice = schDeviceRes.getUserMbrDevice();

		if (!schDeviceRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("["	+ schDeviceRes.getCommonResponse().getResultCode() + "] " + schDeviceRes.getCommonResponse().getResultMessage());
		}

		/* 기기정보 필드 */
		String deviceModelNo = req.getDeviceModelNo(); // 단말모델코드
		String nativeId = req.getNativeId(); // nativeId(imei)
		String deviceAccount = req.getDeviceAccount(); // gmailAddr
		String imMngNum = req.getImMngNum(); // SKT 서비스 관리번호
		String deviceTelecom = req.getDeviceTelecom(); // 통신사코드
		String deviceNickName = req.getDeviceNickName(); // 휴대폰닉네임
		String isPrimary = req.getIsPrimary(); // 대표폰 여부
		String isRecvSms = req.getIsRecvSms(); // sms 수신여부

		/* 부가 기기정보 필드 */
		String rooting = req.getRooting(); // rooting 여부
		String osVer = req.getOsVer() == null ? req.getOsVerOrg() : req.getOsVer(); // OS버젼,OS오리지날버젼
		String scVer = req.getScVer(); // SC버젼
		String uacd = req.getUacd(); // uacd
		String dotoriAuthDate = req.getDotoriAuthDate(); // 도토리인증일
		String dotoriAuthYn = req.getDotoriAuthYn(); // 도토리인증여부

		/* 파라메터 기기 정보와 SC콤포넌트 기기 정보 비교 */
		if (deviceId != null && !deviceId.equals(userMbrDevice.getDeviceID())) {
			
			userMbrDevice.setDeviceID(deviceId);
			logger.info("[deviceId] {} -> {}",	userMbrDevice.getDeviceID(), deviceId);
			
		} else if (deviceModelNo != null
				&& !deviceModelNo.equals(userMbrDevice.getDeviceModelNo())) {
			
			if (MemberConstants.DEVICE_TELECOM_SKT.equals(userMbrDevice.getDeviceTelecom())) {
				
				IDPReceiverM idpReceiver = this.idpService.deviceCompare(deviceId);
				if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(),	IDPConstants.IDP_RES_CODE_OK)) {
					String idpModelId = idpReceiver.getResponseBody().getModel_id();
					
					if (idpModelId != null && !idpModelId.equals("")) {
						// 특정 단말 모델 임시 변경 처리 2013.05.02 watermin
                        if ("SSNU".equals(idpModelId)) { // SHW-M200K -> SHW-M200S
                        	idpModelId = "SSNL";
                        } else if ("SP05".equals(idpModelId)) { // SHW-M420K -> SHW-M420S
                        	idpModelId = "SSO0";
                        }
                        
                        uacd = idpModelId;
					}
				}
			}
			userMbrDevice.setDeviceModelNo(deviceModelNo);
			logger.info("[deviceModelNo] {} -> {}",	userMbrDevice.getDeviceModelNo(), deviceModelNo);
			
		} else if (nativeId != null) {
			
			if (MemberConstants.DEVICE_TELECOM_SKT.equals(userMbrDevice.getDeviceTelecom())) {
				
				if (!nativeId.equals(userMbrDevice.getNativeID())) {
					userMbrDevice.setNativeID(nativeId);
					
					logger.info("[nativeId] {} -> {}", userMbrDevice.getNativeID(),	nativeId);
				}
				
				// 자번호 여부
				boolean isOpmd = StringUtils.substring(deviceId, 0, 3).equals("989");
	            
				if ("Y".equals(rooting) && !isOpmd) {
					Map<String, String> mapIcas = null;
					if (!commService.getMappingInfo(deviceId, "11").getMvnoCD().equals("0")) { //MVNO
						mapIcas = commService.getMvService(deviceId);
					} else {
						mapIcas = commService.getCustomer(deviceId);
					}
					
					if (mapIcas.get("RESULT_CODE").equals("0")) {
						if (!mapIcas.get("IMEI_NUM").equals(nativeId)) {
							throw new Exception("로그인에 실패하였습니다.(오류코드 4204).");
						}
					} else if (mapIcas.get("RESULT_CODE").equals("3162")) {
						throw new Exception("휴대폰 번호에 등록된 단말 정보가 일치하지 않아 T store 를 이용할 수 없습니다. T store 를 종료합니다.");
					}
				}
			} else { //타사
				
				if (userMbrDevice.getNativeID() == null || userMbrDevice.getNativeID().equals("")) {
					userMbrDevice.setNativeID(nativeId);
					logger.info("[nativeId] {} -> {}", userMbrDevice.getNativeID(),	nativeId);
				} else {
					if (rooting.equals("Y") && !nativeId.equals(userMbrDevice.getNativeID())) {
						throw new Exception("로그인에 실패하였습니다.(오류코드 4204)");
					}
				}
			}
			
			
		} else if (deviceAccount != null
				&& !deviceAccount.equals(userMbrDevice.getDeviceAccount())) {
			
			userMbrDevice.setDeviceAccount(deviceAccount);
			logger.info("[deviceAccount] {} -> {}",	userMbrDevice.getDeviceAccount(), deviceAccount);
			
		} else if (imMngNum != null
				&& !imMngNum.equals(userMbrDevice.getImMngNum())) {
			
			userMbrDevice.setImMngNum(imMngNum);
			logger.info("[imMngNum] {} -> {}", userMbrDevice.getImMngNum(),	imMngNum);
			
		} else if (deviceTelecom != null
				&& !deviceTelecom.equals(userMbrDevice.getDeviceTelecom())) {
			
			userMbrDevice.setDeviceTelecom(deviceTelecom);
			logger.info("[deviceTelecom] {} -> {}",	userMbrDevice.getDeviceTelecom(), deviceTelecom);
			
		} else if (deviceNickName != null
				&& !deviceNickName.equals(userMbrDevice.getDeviceNickName())) {
			
			userMbrDevice.setDeviceNickName(deviceNickName);
			logger.info("[deviceNickName] {} -> {}", userMbrDevice.getDeviceNickName(), deviceNickName);
			
		} else if (isPrimary != null
				&& !isPrimary.equals(userMbrDevice.getIsPrimary())) {
			
			userMbrDevice.setIsPrimary(isPrimary);
			logger.info("[isPrimary] {} -> {}", userMbrDevice.getIsPrimary(), isPrimary);
			
		} else if (isRecvSms != null
				&& !isRecvSms.equals(userMbrDevice.getIsRecvSMS())) {
			
			userMbrDevice.setIsRecvSMS(isRecvSms);
			logger.info("[isRecvSms] {} -> {}", userMbrDevice.getIsRecvSMS(), isRecvSms);
			
		}

		/* 휴대기기 부가 정보 비교 */
		List<UserMbrDeviceDetail> deviceExtraList = userMbrDevice.getUserMbrDeviceDetail();
		if (deviceExtraList.size() > 0) {

			List<UserMbrDeviceDetail> modDeviceExtraList = new ArrayList<UserMbrDeviceDetail>();

			for (UserMbrDeviceDetail extraInfo : deviceExtraList) {

				if (rooting != null
						&& extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_ROOTING_YN)
						&& !rooting.equals(extraInfo.getExtraProfileValue())) {
					
					extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
					extraInfo.setExtraProfileValue(rooting);
					logger.info("[rooting] {} -> {}", extraInfo.getExtraProfileValue(), rooting);
					
				} else if (osVer != null
						&& extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_OSVERSION)
						&& !osVer.equals(extraInfo.getExtraProfileValue())) {
					
					extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_OSVERSION);
					extraInfo.setExtraProfileValue(osVer);
					logger.info("[osVer] {} -> {}",	extraInfo.getExtraProfileValue(), osVer);
					
				} else if (scVer != null
						&& extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_SCVERSION)
						&& !scVer.equals(extraInfo.getExtraProfileValue())) {
					
					extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_SCVERSION);
					extraInfo.setExtraProfileValue(scVer);
					logger.info("[scVer] {} -> {}", extraInfo.getExtraProfileValue(), scVer);
					
				} else if (uacd != null
						&& extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_UACD)
						&& !uacd.equals(extraInfo.getExtraProfileValue())) {
					
					extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
					extraInfo.setExtraProfileValue(uacd);
					logger.info("[uacd] {} -> {}", extraInfo.getExtraProfileValue(), uacd);
					
				} else if (dotoriAuthDate != null
						&& extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE)
						&& !dotoriAuthDate.equals(extraInfo.getExtraProfileValue())) {
					
					extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE);
					extraInfo.setExtraProfileValue(dotoriAuthDate);
					logger.info("[dotoriAuthDate] {} -> {}", extraInfo.getExtraProfileValue(), dotoriAuthDate);
					
				} else if (dotoriAuthYn != null
						&& extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_YN)
						&& !dotoriAuthYn.equals(extraInfo.getExtraProfileValue())) {
					
					extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_DODORYAUTH_YN);
					extraInfo.setExtraProfileValue(dotoriAuthYn);
					logger.info("[dotoriAuthYn] {} -> {}", extraInfo.getExtraProfileValue(), dotoriAuthYn);
					
				}

				modDeviceExtraList.add(extraInfo);
			}

			userMbrDevice.setUserMbrDeviceDetail(modDeviceExtraList);
		}

		/* 기기정보 업데이트 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setUserKey(schDeviceRes.getUserKey());
		createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		if (!createDeviceRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			
			throw new Exception("휴대기기정보 업데이트 실패 ["
					+ createDeviceRes.getCommonResponse().getResultCode() + "]"
					+ createDeviceRes.getCommonResponse().getResultMessage());
			
		}

		logger.info("################ mergeDeviceInfo end ##################");

	}

	/**
	 * 
	 * SC회원콤포넌트 휴대기기 정보 -> SAC 휴대기기 정보
	 * 
	 * @param userMbrDevice
	 * @return
	 */
	public DeviceInfo getConverterDeviceInfo(UserMbrDevice userMbrDevice) {
		DeviceInfo deviceInfo = new DeviceInfo();

		deviceInfo.setDeviceAccount(userMbrDevice.getDeviceAccount());
		deviceInfo.setTenantId(userMbrDevice.getTenantID());
		deviceInfo.setDeviceId(userMbrDevice.getDeviceID());
		deviceInfo.setDeviceModelNo(userMbrDevice.getDeviceModelNo());
		deviceInfo.setDeviceNickName(userMbrDevice.getDeviceNickName());
		deviceInfo.setDeviceTelecom(userMbrDevice.getDeviceTelecom());
		deviceInfo.setImMngNum(userMbrDevice.getImMngNum());
		deviceInfo.setIsPrimary(userMbrDevice.getIsPrimary());
		deviceInfo.setIsRecvSms(userMbrDevice.getIsRecvSMS());
		deviceInfo.setNativeId(userMbrDevice.getNativeID());
		deviceInfo.setJoinId(userMbrDevice.getJoinId());
		deviceInfo.setIsAuthenticated(userMbrDevice.getIsAuthenticated());
		deviceInfo.setAuthenticationDate(userMbrDevice.getAuthenticationDate());
		if (userMbrDevice.getUserMbrDeviceDetail() != null) {
			deviceInfo.setUserDeviceExtraInfo(this.getConverterDeviceInfoDetailList(userMbrDevice.getUserMbrDeviceDetail()));	
		}
		
		return deviceInfo;
	}

	/**
	 * 
	 * SC회원콤포넌트 휴대기기 부가서비스 리스트정보 -> SAC 휴대기기 부가서비스 리스트정보
	 * 
	 * @param list
	 * @return
	 */
	public List<DeviceExtraInfo> getConverterDeviceInfoDetailList(
			List<UserMbrDeviceDetail> list) {

		List<DeviceExtraInfo> deviceExtraInfoList = null;
		DeviceExtraInfo deviceExtraInfo = null;

		if (list.size() > 0) {
			deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
			deviceExtraInfo = new DeviceExtraInfo();
		}

		for (UserMbrDeviceDetail deviceDetail : list) {

			String extraProfile = deviceDetail.getExtraProfile();

			if (extraProfile.equals(MemberConstants.DEVICE_EXTRA_OMPDOWNLOADER_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_STANDBYSCREEN_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_UACD)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_OMPSUPPORT_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_OSVERSION)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_SCVERSION)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_APPSTATISTICS_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_EMBEDDED_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_OMPUACE)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_ROOTING_YN)) {

				deviceExtraInfo.setExtraProfile(extraProfile);
				deviceExtraInfo.setExtraProfileValue(deviceDetail.getExtraProfileValue());

				deviceExtraInfoList.add(deviceExtraInfo);
			}
		}

		return deviceExtraInfoList;

	}

	/**
	 * 
	 * SAC 휴대기기 정보 --> SC회원콤포넌트 휴대기기 정보
	 * 
	 * @param deviceInfo
	 * @return
	 */
	public UserMbrDevice getConverterUserMbrDeviceInfo(DeviceInfo deviceInfo) {

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setDeviceAccount(deviceInfo.getDeviceAccount());
		userMbrDevice.setTenantID(deviceInfo.getTenantId());
		userMbrDevice.setDeviceID(deviceInfo.getDeviceId());
		userMbrDevice.setDeviceModelNo(deviceInfo.getDeviceModelNo());
		userMbrDevice.setDeviceNickName(deviceInfo.getDeviceNickName());
		userMbrDevice.setDeviceTelecom(deviceInfo.getDeviceTelecom());
		userMbrDevice.setImMngNum(deviceInfo.getImMngNum());
		userMbrDevice.setIsPrimary(deviceInfo.getIsPrimary());
		userMbrDevice.setIsRecvSMS(deviceInfo.getIsRecvSms());
		userMbrDevice.setNativeID(deviceInfo.getNativeId());
		userMbrDevice.setJoinId(deviceInfo.getJoinId());
		userMbrDevice.setIsAuthenticated(deviceInfo.getIsAuthenticated());
		userMbrDevice.setAuthenticationDate(deviceInfo.getAuthenticationDate());
		
		if (deviceInfo.getUserDeviceExtraInfo() != null) {
			userMbrDevice.setUserMbrDeviceDetail(this.getConverterUserMbrDeviceDetailList(deviceInfo.getUserDeviceExtraInfo()));	
		}
		
		return userMbrDevice;

	}

	/**
	 * 
	 * SAC 휴대기기 부가서비스 리스트정보 --> SC회원콤포넌트 휴대기기 부가서비스 리스트정보
	 * 
	 * @param list
	 * @return
	 */
	public List<UserMbrDeviceDetail> getConverterUserMbrDeviceDetailList(
			List<DeviceExtraInfo> list) {

		List<UserMbrDeviceDetail> userMbrDeviceDetailList = null;
		UserMbrDeviceDetail userMbrDeviceDetail = null;
		if (list.size() > 0) {
			userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
			userMbrDeviceDetail = new UserMbrDeviceDetail();
		}

		for (DeviceExtraInfo extraInfo : list) {

			String extraProfile = extraInfo.getExtraProfile();

			if (extraProfile.equals(MemberConstants.DEVICE_EXTRA_OMPDOWNLOADER_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_STANDBYSCREEN_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_UACD)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_OMPSUPPORT_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_OSVERSION)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_SCVERSION)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_APPSTATISTICS_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_EMBEDDED_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_OMPUACE)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_ROOTING_YN)) {

				userMbrDeviceDetail.setExtraProfile(extraProfile);
				userMbrDeviceDetail.setExtraProfileValue(extraInfo.getExtraProfileValue());

				userMbrDeviceDetailList.add(userMbrDeviceDetail);
			}
		}

		return userMbrDeviceDetailList;

	}

	/**
	 * 
	 * 휴대기기 대표단말 설정
	 * 
	 * @param SetMainDeviceRequest
	 * @return
	 */
	@Override
	public DeviceInfo modifyRepresentationDevice(HeaderVo headerVo, SetMainDeviceRequest req) throws Exception {
		req.setCommonRequest(commonRequest);
		SetMainDeviceResponse scRes = this.deviceSCI.setMainDevice(req);
		DeviceInfo res = new DeviceInfo();

		if (!scRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("result_code : [" + scRes.getCommonResponse().getResultCode() + "] result_message : ["
					+ scRes.getCommonResponse().getResultMessage() + "]");
		} else {
			res.setDeviceKey(req.getDeviceKey());
		}

		return res;
	}

	/**
	 * 
	 * 휴대기기 삭제
	 * 
	 * @param SetMainDeviceRequest
	 * @return
	 */
	@Override
	public List<DeviceInfo> removeDevice(HeaderVo headerVo, RemoveDeviceReq req) throws Exception {

		logger.info("######################## DeviceServiceImpl 휴대기기 삭제 start ############################");

		String userKey = req.getUserKey();
		String deviceKey = req.getDeviceKey();

		/* SC 회원 정보 조회 */
		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		key.setKeyString(userKey);
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		if (!StringUtil.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new Exception("[" + schUserRes.getCommonResponse().getResultCode() + "] "
					+ schUserRes.getCommonResponse().getResultMessage());
		}

		/* SC 회원 컴포넌트 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setDeviceId(req.getDeviceId());
		listDeviceReq.setUserKey(userKey);
		ListDeviceRes listDeviceRes = this.listDevice(headerVo, listDeviceReq);

		List<DeviceInfo> deviceInfoList = listDeviceRes.getDeviceInfoList();
		List<DeviceInfo> deviceModifyList = new ArrayList<DeviceInfo>();
		DeviceInfo info = new DeviceInfo();

		if (deviceInfoList.size() > 0) {
			for (DeviceInfo deviceInfo : deviceInfoList) {

				/* 삭제요청한 휴대기기를 제외하고 리스트로 담는다. */
				if (!req.getDeviceId().equals(deviceInfo.getDeviceId())) {
					info = deviceInfo;
					deviceModifyList.add(info);
				}

				logger.info("###### 삭제요청 디바이스 : " + req.getDeviceId());
				logger.info("###### 삭제요청제외 디바이스 : " + info.getDeviceId());
			}

		}

		/* IDP 휴대기기 정보 등록 요청 */
		if (schUserRes.getUserMbr().getImSvcNo() != null) { // 통합회원
			// TXUpdateAdditionalUserInfoIDP
		} else {
			// modifyProfile
		}

		/* SC 휴대기기 정보 삭제 */

		/* 월정액 탈퇴 */

		/* 게임센터 연동 처리 */

		logger.info("######################## DeviceServiceImpl 휴대기기 삭제 start ############################");

		return deviceModifyList;
	}
}
