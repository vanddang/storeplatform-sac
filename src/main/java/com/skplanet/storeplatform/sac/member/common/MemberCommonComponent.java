/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.icas.sci.IcasSCI;
import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerCardEcReq;
import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerCardEcRes;
import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerEcReq;
import com.skplanet.storeplatform.external.client.icas.vo.GetCustomerEcRes;
import com.skplanet.storeplatform.external.client.icas.vo.GetMvnoEcReq;
import com.skplanet.storeplatform.external.client.icas.vo.GetMvnoEcRes;
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsEcReq;
import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.repository.MemberCommonRepository;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;

/**
 * 공통 기능을 임시로 정의해서 사용한다.
 * 
 * Updated on : 2014. 1. 7. Updated by : 심대진, 다모아 솔루션.
 */
@Component
public class MemberCommonComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberCommonComponent.class);

	@Autowired
	private MemberCommonRepository repository;

	@Autowired
	private MiscellaneousService miscellaneousService;

	@Autowired
	private UapsSCI uapsSCI;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private IcasSCI icasSCI;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private SellerSCI sellerSCI;

	@Autowired
	private UserSearchService userSearchService;

	@Value("#{propertiesForSac['idp.mobile.user.auth.key']}")
	public String fixedMobileUserAuthKey;

	/**
	 * <pre>
	 * 모번호 조회
	 * 989로 시작하는 MSISDN이 들어오면 모번호를 조회해서 반환하고, 
	 * 989로 시작하지 않는경우 파라미터로 받은 MSISDN을 그대로 반환.
	 * </pre>
	 * 
	 * @param msisdn
	 *            msisdn
	 * @return String
	 */
	public String getOpmdMdnInfo(String msisdn) { // 2014. 01. 09. 김다슬, 인크로스. 수정

		GetOpmdReq req = new GetOpmdReq();
		req.setMsisdn(msisdn);
		return this.miscellaneousService.getOpmd(req).getMsisdn();
	}

	/**
	 * <pre>
	 * SKT 이용정지회원 여부 조회.
	 * </pre>
	 * 
	 * @param deviceId
	 *            기기 ID
	 * @param deviceIdType
	 *            기기 ID 타입
	 * @return SKT 이용 정지 여부
	 */
	public String getIsSktPause(String deviceId, String deviceIdType) {

		UapsEcReq uapsReq = new UapsEcReq();
		uapsReq.setDeviceId(deviceId);

		try {

			UserEcRes userRes = this.getMappingInfo(deviceId, "mdn");
			LOGGER.debug("## >> UAPS Info : {}", userRes);
			return userRes.getPauseYN();

		} catch (StorePlatformException spe) {

			LOGGER.debug("## >> SKT 이용정지 여부 조회 실패로 이용정지여부 Skip....... ");
			LOGGER.debug("## Error Code : {}", spe.getErrorInfo().getCode());
			LOGGER.debug("## Error Msg  : {}", spe.getErrorInfo().getMessage());
			return "";

		}

	}

	/**
	 * <pre>
	 * deviceModelNo로 UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelNo
	 *            String
	 * @return String
	 * @throws Exception
	 *             Exception
	 */
	public String getUaCode(String tenantId, String deviceModelNo) { // 2014. 01. 14. 김다슬, 인크로스. 추가
		GetUaCodeReq request = new GetUaCodeReq();
		request.setDeviceModelNo(deviceModelNo);

		SacRequestHeader requestHeader = new SacRequestHeader();
		requestHeader.getTenantHeader().setTenantId(tenantId);
		return this.miscellaneousService.getUaCode(requestHeader, request).getUaCd();
	}

	/**
	 * <pre>
	 * 필수 약관 동의 목록 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            tenantId
	 * @return List<ClauseDTO>
	 */
	public List<Clause> getMandAgreeList(String tenantId) {
		return this.repository.searchMandatoryAgreeList(tenantId);
	}

	/**
	 * <pre>
	 * 약관 목록 조회.
	 * </pre>
	 * 
	 * @return List<ClauseDTO>
	 */
	public List<Clause> getListClause() {
		return this.repository.searchClauseList();
	}

	/**
	 * <pre>
	 * 약관 목록 상세 조회.
	 * </pre>
	 * 
	 * @return List<ClauseDTO>
	 */
	public List<Clause> getDetailClauseList(String clauseItemCd) {
		return this.repository.searchDetailClauseList(clauseItemCd);
	}

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            deviceModelCd
	 * @return DeviceHeader
	 */
	public Device getPhoneInfo(String deviceModelCd) {
		return this.repository.searchPhoneInfo(deviceModelCd);
	}

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param uacd
	 *            uacd
	 * @return Device
	 */
	public Device getPhoneInfoByUacd(String uacd) {
		return this.repository.searchPhoneInfoByUacd(uacd);
	}

	/**
	 * <pre>
	 * 고객정보조회
	 * 모번호 조회및 단말 정보 조회(USPS 정보와 서비스 관리번호 UA_CD 값이 같이 들어와야함.)
	 * </pre>
	 * 
	 * @param pReqParam
	 *            pReqParam
	 * @param type
	 *            type
	 * @return UserEcRes
	 */
	public UserEcRes getMappingInfo(String pReqParam, String type) {
		UapsEcReq uapsReq = new UapsEcReq();
		uapsReq.setDeviceId(pReqParam);
		uapsReq.setType(type);
		return this.uapsSCI.getMappingInfo(uapsReq);
	}

	/**
	 * <pre>
	 * 휴대기기 등록 서브 모듈.
	 * SC회원콤포넌트에 휴대기기를 등록, 기등록된 회원의 휴대기기인 경우 구매이관처리, 약관이관, 통합회원인 경우 IDP에 무선회원 해지 요청.
	 * </pre>
	 * 
	 * @param systemId
	 *            String
	 * @param tenantId
	 *            String
	 * @param userKey
	 *            String
	 * @param deviceInfo
	 *            DeviceInfo
	 * @return deviceKey
	 */
	public String insertDeviceInfo(String systemId, String tenantId, String userKey, DeviceInfo deviceInfo) {
		return this.deviceService.insertDeviceInfo(systemId, tenantId, userKey, deviceInfo);
	}

	/**
	 * <pre>
	 * 회원 정보 조회.
	 * </pre>
	 * 
	 * @param keyType
	 *            검색 조건 타입 (userKey, userId, deviceKey, deviceId)
	 * @param keyValue
	 *            검색 조건 값
	 * @param sacHeader
	 *            공통 헤더
	 * @return UserInfo Value Object
	 */
	public UserInfo getUserBaseInfo(String keyType, String keyValue, SacRequestHeader sacHeader) {
		LOGGER.debug("###### getUserBaseInfo Req : {}, {}, {}", keyType, keyValue, sacHeader.getTenantHeader().toString());
		LOGGER.debug("============================================ getUserBaseInfo Req : {}, {}, {}", keyType, keyValue, sacHeader.getTenantHeader()
				.toString());

		DetailReq req = new DetailReq();
		if ("userKey".equals(keyType)) {
			req.setUserKey(keyValue);
		} else if ("userId".equals(keyType)) {
			req.setUserId(keyValue);
		} else if ("deviceKey".equals(keyType)) {
			req.setDeviceKey(keyValue);
		} else if ("deviceId".equals(keyType)) {
			req.setDeviceId(keyValue);
		}

		DetailRes detailRes = this.userSearchService.searchUser(req, sacHeader);
		UserInfo userInfo = detailRes.getUserInfo();

		return userInfo;
	}

	/**
	 * <pre>
	 * 회원 부가 정보 조회.
	 * test userKey - "IF1023002708420090928145937"
	 * </pre>
	 * 
	 * @return UserExtraInfoRes
	 * @throws Exception
	 */
	public UserExtraInfoRes getUserExtraInfo(String userKey, SacRequestHeader sacHeader) {

		DetailReq req = new DetailReq();
		UserExtraInfoRes res = new UserExtraInfoRes();
		req.setUserKey(userKey);

		res = this.userSearchService.listUserExtra(req, sacHeader);

		return res;
	}

	/**
	 * <pre>
	 * 약관동의 목록 조회.
	 * test userKey - "IF1023002708420090928145937"
	 * </pre>
	 * 
	 * @param keyType
	 *            검색 조건 타입
	 * @param keyValue
	 *            검색 조건 값
	 * @param systemId
	 *            시스템 아이디
	 * @param tenantId
	 *            테넌트 아이디
	 * @return UserExtraInfo
	 * @throws Exception
	 *             Exception
	 */
	public SearchAgreementRes getSearchAgreement(String userKey, SacRequestHeader sacHeader) {

		DetailReq req = new DetailReq();
		SearchAgreementRes res = new SearchAgreementRes();
		req.setUserKey(userKey);

		DetailRes detailRes = this.userSearchService.searchUser(req, sacHeader);
		res.setAgreementList(detailRes.getAgreementList());

		return res;
	}

	/**
	 * <pre>
	 * ICAS 연동 getCustomer.
	 * </pre>
	 * 
	 * @param msisdn
	 *            String
	 * @return GetCustomerEcRes
	 * @throws Exception
	 *             Exception
	 */
	public GetCustomerEcRes getCustomer(String msisdn) {
		GetCustomerEcReq req = new GetCustomerEcReq();
		req.setDeviceId(msisdn);
		return this.icasSCI.getCustomer(req);
	}

	/**
	 * <pre>
	 * ICAS 연동 getCustomerCard.
	 * </pre>
	 * 
	 * @param msisdn
	 *            String
	 * @return GetCustomerCardEcRes
	 * @throws Exception
	 *             Exception
	 */
	public GetCustomerCardEcRes getCustomerCard(String msisdn) {
		GetCustomerCardEcReq req = new GetCustomerCardEcReq();
		req.setDeviceId(msisdn);
		return this.icasSCI.getCustomerCard(req);
	}

	/**
	 * <pre>
	 * ICAS 연동 getMvService.
	 * </pre>
	 * 
	 * @param msisdn
	 *            String
	 * @return Map<String, String>
	 * @throws Exception
	 *             Exception
	 */
	public GetMvnoEcRes getMvService(String msisdn) {
		GetMvnoEcReq req = new GetMvnoEcReq();
		return this.icasSCI.getMvService(req);
	}

	/**
	 * <pre>
	 * 휴대기기 등록시에 기본정보 setting.
	 * </pre>
	 * 
	 * @param model
	 *            단말 모델
	 * @param deviceTelecom
	 *            이동통신사
	 * @param deviceId
	 *            (msisdn or uuid or mac value)
	 * @param deviceIdType
	 *            (msisdn or uuid or mac type)
	 * @return MajorDeviceInfo
	 * 
	 */
	public MajorDeviceInfo getDeviceBaseInfo(String model, String deviceTelecom, String deviceId, String deviceIdType) {

		MajorDeviceInfo majorDeviceInfo = new MajorDeviceInfo();

		/**
		 * 폰정보 조회후 단말 정보 세팅.
		 */
		Device deviceDTO = this.getPhoneInfo(model);
		if (deviceDTO == null) {

			/**
			 * 미지원 단말 setting
			 */
			LOGGER.debug("## 미지원 단말 세팅.");
			majorDeviceInfo.setUacd(MemberConstants.NOT_SUPPORT_HP_UACODE); // UA 코드
			majorDeviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_NSH); // 이동 통신사
			majorDeviceInfo.setDeviceModelNo(MemberConstants.NOT_SUPPORT_HP_MODEL_CD); // 기기 모델 번호
			majorDeviceInfo.setDeviceNickName(MemberConstants.NOT_SUPPORT_HP_MODEL_NM); // 기기명

		} else {

			majorDeviceInfo.setUacd(deviceDTO.getUaCd()); // UA 코드
			majorDeviceInfo.setDeviceModelNo(deviceDTO.getDeviceModelCd()); // 기기 모델 번호
			majorDeviceInfo.setDeviceNickName(deviceDTO.getModelNm()); // 기기명

			/**
			 * 미지원 단말 예외처리. (UA 코드 여부에 따라 이동통신사 코드 변경)
			 */
			if (StringUtils.equals(deviceDTO.getUaCd(), MemberConstants.NOT_SUPPORT_HP_UACODE)) {
				majorDeviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_NSH); // 이동 통신사
			} else {
				majorDeviceInfo.setDeviceTelecom(deviceTelecom); // 이동 통신사
			}

			/**
			 * UUID 일때 이동통신사코드가 IOS가 아니면 로그찍는다. (테넌트에서 잘못 올려준 데이타.) [[ AS-IS 로직은 하드코딩 했었음... IOS 이북 보관함 지원 uuid ]]
			 */
			if (StringUtils.equals(deviceIdType, MemberConstants.DEVICE_ID_TYPE_UUID)) {
				if (!StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_IOS)) {
					LOGGER.warn("###############################################################################");
					LOGGER.warn("##### UUID 일때는 무조건 이동통신사 코드를 IOS로 줘야 한다. AI-IS 로직 반영.... #####");
					LOGGER.warn("###############################################################################");
				}
			}

		}

		/**
		 * SKT 가입자일 경우 처리
		 */
		if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_SKT)) {

			/**
			 * MDN 일경우만 UAPS 연동 하여 (SKT 서비스가입번호를 세팅한다.)
			 */
			if (StringUtils.equals(deviceIdType, MemberConstants.DEVICE_ID_TYPE_MSISDN)) {

				UserEcRes userRes = this.getMappingInfo(deviceId, "mdn");
				LOGGER.debug("## UAPS 연동 결과 toString() : {}", userRes);
				LOGGER.debug("## UAPS 연동 SKT 서비스 관리번호 : {}", userRes.getSvcMngNum());
				majorDeviceInfo.setSvcMangNum(userRes.getSvcMngNum());

			}

			/**
			 * OMD 단말 setting.
			 */
			if (this.repository.searchOmdCount(model) > 0) {

				LOGGER.debug("## OMD 단말 setting.");
				majorDeviceInfo.setOmdUacd(model);

			}

		}

		LOGGER.debug("## 단말 주요정보 : {}", majorDeviceInfo);

		return majorDeviceInfo;

	}

	/**
	 * <pre>
	 * 이동통신사 Converting.
	 * </pre>
	 * 
	 * @param deviceTelecom
	 *            이동통신사
	 * @return 이동통신사 변환
	 */
	public String convertDeviceTelecom(String deviceTelecom) {
		if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_SKT)) {
			return MemberConstants.NM_DEVICE_TELECOM_SKT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_KT)) {
			return MemberConstants.NM_DEVICE_TELECOM_KT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_LGT)) {
			return MemberConstants.NM_DEVICE_TELECOM_LGT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_OMD)) {
			return MemberConstants.NM_DEVICE_TELECOM_OMD;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_NSH)) {
			return MemberConstants.NM_DEVICE_TELECOM_NSH;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_NON)) {
			return MemberConstants.NM_DEVICE_TELECOM_NON;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_IOS)) {
			return MemberConstants.NM_DEVICE_TELECOM_IOS;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.DEVICE_TELECOM_SKM)) {
			return MemberConstants.NM_DEVICE_TELECOM_SKM;
		} else {
			throw new StorePlatformException("SAC_MEM_1103", deviceTelecom);
		}
	}

	/**
	 * <pre>
	 * 이동통신사 코드 Converting.
	 * </pre>
	 * 
	 * @param deviceTelecom
	 *            이동통신사
	 * @return String 이동통신사 변환
	 */
	public String convertDeviceTelecomCode(String deviceTelecom) {
		if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_SKT)) {
			return MemberConstants.DEVICE_TELECOM_SKT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_KT)) {
			return MemberConstants.DEVICE_TELECOM_KT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_LGT)) {
			return MemberConstants.DEVICE_TELECOM_LGT;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_OMD)) {
			return MemberConstants.DEVICE_TELECOM_OMD;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_NSH)) {
			return MemberConstants.DEVICE_TELECOM_NSH;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_NON)) {
			return MemberConstants.DEVICE_TELECOM_NON;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_IOS)) {
			return MemberConstants.DEVICE_TELECOM_IOS;
		} else if (StringUtils.equals(deviceTelecom, MemberConstants.NM_DEVICE_TELECOM_SKM)) {
			return MemberConstants.DEVICE_TELECOM_SKM;
		} else {
			throw new StorePlatformException("SAC_MEM_1103", deviceTelecom);
		}
	}

	/**
	 * <pre>
	 * SC 공통 헤더 셋팅.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @return CommonRequest
	 */
	public CommonRequest getSCCommonRequest(SacRequestHeader header) {
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		LOGGER.debug("==>>[SC] CommonRequest.toString() : {}", commonRequest.toString());
		return commonRequest;
	}

	/**
	 * <pre>
	 * 판매자 SessionKey 조회.
	 * </pre>
	 * 
	 * @param commonRequest
	 *            CommonRequest
	 * @param sessionKey
	 *            String
	 * @param sellerKey
	 *            String
	 */
	public void checkSessionKey(CommonRequest commonRequest, String sessionKey, String sellerKey) {
		SearchLoginInfoRequest req = new SearchLoginInfoRequest();
		req.setCommonRequest(commonRequest);
		req.setSessionKey(sessionKey);
		SearchLoginInfoResponse res = this.sellerSCI.searchLoginInfo(req);

		if (!StringUtils.equals(sellerKey, res.getLoginInfo().getSellerKey())) {
			// 인증키가 유효하지 않습니다.
			throw new StorePlatformException("SAC_MEM_2002");
		}
		// TODO 만료로직 주석
		// Double expireDate = Double.parseDouble(res.getLoginInfo().getExpireDate());
		// Double sysdate = Double.parseDouble(this.getExpirationTime(0));
		// if (expireDate < sysdate) {
		// // 인증 만료시간 오버
		// throw new StorePlatformException("SAC_MEM_2003");
		// }
	}

	/**
	 * <pre>
	 * 판매자 회원 정보 조회.
	 * </pre>
	 * 
	 * @param commonRequest
	 *            CommonRequest
	 * @param keyType
	 *            : 검색조건
	 * @param key
	 *            : 검색값
	 * @return SearchSellerResponse
	 */
	public SearchSellerResponse getSearchSeller(CommonRequest commonRequest, String keyType, String key) {
		SearchSellerRequest searchSellerRequest = new SearchSellerRequest();
		searchSellerRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(keyType);
		keySearch.setKeyString(key);
		keySearchList.add(keySearch);
		searchSellerRequest.setKeySearchList(keySearchList);

		return this.sellerSCI.searchSeller(searchSellerRequest);
	}

	/**
	 * <pre>
	 * 인증 만료 시간 연장 .
	 * </pre>
	 * 
	 * @param hour
	 *            : 연장할 시간
	 * @return String : 현재시간 + 연장시간
	 */
	public String getExpirationTime(int hour) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, hour);
		return sdf.format(cal.getTime());
	}

	/**
	 * <pre>
	 * IDP 연동 여부 판단.
	 * 
	 * MDN 로그인시에만 fixedMobileUserAuthKey 내려감.
	 * 
	 * </pre>
	 * 
	 * @param userAuthkey
	 *            요청 userAuthKey
	 * @return boolean (true 면 연동한다.)
	 */
	public boolean isIdpConnect(String userAuthkey) {

		if (StringUtils.equals(userAuthkey, this.fixedMobileUserAuthKey)) {
			LOGGER.debug("## >> userAuthKey 가 Fixed 값과 같으면 IDP 연동 하지 않는다.");
			return false;
		} else {
			return true;
		}

	}
}
