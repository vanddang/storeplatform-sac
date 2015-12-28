package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.JoinSupServiceRequestEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinSupServiceRequestEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.ServiceSubscriptionCheckEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.ServiceSubscriptionCheckEcRes;
import com.skplanet.storeplatform.external.client.inicis.sci.InicisSCI;
import com.skplanet.storeplatform.external.client.inicis.vo.InicisAuthAccountEcReq;
import com.skplanet.storeplatform.external.client.inicis.vo.InicisAuthAccountEcRes;
import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.UafmapEcRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.client.member.vo.user.PinAuthorizationCheckReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.PinAuthorizationCheckRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuth;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 기타 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Service
public class MiscellaneousServiceImpl implements MiscellaneousService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousServiceImpl.class);

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트

	@Autowired
	private UserSCI userSCI; // 회원 Component 사용자 기능 Interface.
	@Autowired
	private DeviceSCI deviceSCI; // 회원 Component 휴대기기 기능 Interface.

	@Autowired
	private IdpSCI idpSCI; // IDP 연동 Interface.
	@Autowired
	private ImIdpSCI imIdpSCI; // IMIDP 연동 Interface.
	@Autowired
	private UapsSCI uapsSCI; // UAPS 연동 Interface.
	@Autowired
	private MessageSCI messageSCI; // 메시지전송 기능 Interface.
	@Autowired
	private InicisSCI inicisSCI; // 이니시스 연동 Interface.
	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor; // Message Properties

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	@Value("#{propertiesForSac['sms.auth.cnt']}")
	private int smsAuthCnt;

	@Value("#{propertiesForSac['email.auth.url']}")
	private String mobileEmailAuthUrl;

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * </pre>
	 * 
	 * @param req
	 *            GetOpmdReq
	 * @return GetOpmdRes
	 */
	@Override
	public GetOpmdRes getOpmd(GetOpmdReq req) {
		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(this.commonComponent.getOpmdMdnInfo(req.getMsisdn()));
		return res;
	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            GetUaCodeReq
	 * @return GetUaCodeRes
	 */
	@Override
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, GetUaCodeReq req) {
		String deviceModelNo = req.getDeviceModelNo();
		String msisdn = req.getMsisdn();
		String userKey = null;

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = this.commonComponent.getSCCommonRequest(requestHeader);

		GetUaCodeRes response = new GetUaCodeRes();
		/* 파라미터로 MSISDN만 넘어온 경우 */
		if (StringUtils.isNotBlank(msisdn) && StringUtils.isBlank(deviceModelNo)) {
			SearchUserRequest searchUserRequest = new SearchUserRequest();
			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();

			/* SC 공통헤더 Request 파라미터 셋팅 */
			searchUserRequest.setCommonRequest(commonRequest);
			searchDeviceRequest.setCommonRequest(commonRequest);

			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch keySearch = new KeySearch();
			keySearch.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			keySearch.setKeyString(msisdn);
			keySearchList.add(keySearch);
			searchUserRequest.setKeySearchList(keySearchList);

			/* deviceId(msisdn)로 userKey 조회 - SC 회원 "회원 기본 정보 조회" */
			SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserResponse == null || searchUserResponse.getUserMbr() == null) {
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", msisdn);
			} else {
				userKey = searchUserResponse.getUserMbr().getUserKey();
				LOGGER.debug("[MiscellaneousService.getUaCode] SC Response : {}", searchUserResponse.getUserMbr());
			}

			searchDeviceRequest.setKeySearchList(keySearchList);
			searchDeviceRequest.setUserKey(userKey);

			/* deviceId와 userKey로 deviceModelNo 조회 */
			SearchDeviceResponse searchDeviceResult = this.deviceSCI.searchDevice(searchDeviceRequest);

			/* deviceModelNo 조회 결과 확인 */
			if (searchDeviceResult != null
					&& StringUtils.isNotBlank(searchDeviceResult.getUserMbrDevice().getDeviceModelNo())) {
				deviceModelNo = searchDeviceResult.getUserMbrDevice().getDeviceModelNo();
			} else {
				throw new StorePlatformException("SAC_MEM_3402", "msisdn", msisdn);
			}
		}

		if (StringUtils.isNotBlank(deviceModelNo)) { // deviceModelNo 가 파라미터로 들어온 경우
			// DB 접속(TB_CM_DEVICE) - UaCode 조회
			String uaCode = this.commonDao.queryForObject("Miscellaneous.getUaCode", deviceModelNo, String.class);
			if (StringUtils.isNotBlank(uaCode)) {
				response.setUaCd(uaCode);
			} else {
				throw new StorePlatformException("SAC_MEM_3401", "deviceModelNo", deviceModelNo);
			}
		}
		return response;
	}

	/**
	 * <pre>
	 * 부가서비스 가입.
	 * </pre>
	 * 
	 * @param request
	 *            CreateAdditionalServiceReq
	 * @return CreateAdditionalServiceRes
	 */
	@Override
	public CreateAdditionalServiceRes regAdditionalService(CreateAdditionalServiceReq request) {

		CreateAdditionalServiceRes response = new CreateAdditionalServiceRes();

		JoinSupServiceRequestEcReq joinSupServiceEcReq = new JoinSupServiceRequestEcReq();
		joinSupServiceEcReq.setSvcCode(request.getSvcCode());
		joinSupServiceEcReq.setUserMdn(request.getMsisdn());
		joinSupServiceEcReq.setUserSvcMngNum(request.getSvcMngNum());

		/* FDS LOG START */
		final String deviceId = joinSupServiceEcReq.getUserMdn();
		final String serviceCode = joinSupServiceEcReq.getSvcCode();

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_MEM_0003").device_id(deviceId).service_code(serviceCode);
				;
			}
		});
		/* FDS LOG END */

		JoinSupServiceRequestEcRes joinSupServiceEcRes = this.idpSCI.joinSupServiceRequest(joinSupServiceEcReq);

		response.setSvcCode(serviceCode); // 부가서비스 코드
		response.setMsisdn(joinSupServiceEcRes.getUserMdn()); // 사용자 휴대폰번호

		return response;
	}

	/**
	 * <pre>
	 * 부가서비스 가입 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetAdditionalServiceReq
	 * @return GetAdditionalServiceRes
	 */
	@Override
	public GetAdditionalServiceRes getAdditionalService(GetAdditionalServiceReq request) {

		ServiceSubscriptionCheckEcReq serviceSubscriptionCheckEcReq = new ServiceSubscriptionCheckEcReq();
		serviceSubscriptionCheckEcReq.setUserMdn(request.getMsisdn());
		serviceSubscriptionCheckEcReq.setSvcCode(request.getSvcCode());
		ServiceSubscriptionCheckEcRes serviceSubscriptionCheckEcRes = this.idpSCI
				.serviceSubscriptionCheck(serviceSubscriptionCheckEcReq);

		LOGGER.debug("[MiscellaneousService.getAdditionalService] SAC<-IDP Response {}", serviceSubscriptionCheckEcRes);

		GetAdditionalServiceRes response = new GetAdditionalServiceRes();
		response.setMsisdn(serviceSubscriptionCheckEcRes.getUserMdn());
		response.setSvcCode(serviceSubscriptionCheckEcRes.getSvcCode());
		response.setSvcJoinResult(serviceSubscriptionCheckEcRes.getSvcResult());
		LOGGER.debug("## Response {}", response);

		return response;
	}

	/**
	 * <pre>
	 * 단말 모델코드 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetModelCodeReq
	 * @return GetModelCodeRes
	 */
	@Override
	public GetModelCodeRes getModelCode(GetModelCodeReq request) {
		GetModelCodeRes response = new GetModelCodeRes();
		String uaCd = request.getUaCd();
		String msisdn = request.getMsisdn();
		String errorKey = "uaCd";
		String errorValue = uaCd;

		if (StringUtils.isNotBlank(msisdn) && StringUtils.isBlank(uaCd)) {
			errorKey = "msisdn";
			errorValue = msisdn;
			UapsEcReq uapsReq = new UapsEcReq();
			uapsReq.setDeviceId(request.getMsisdn());
			uapsReq.setType("mdn");

			UafmapEcRes uapsRes = this.uapsSCI.getDeviceInfo(uapsReq);
			if (uapsRes != null) {
				if (StringUtils.isNotBlank(uapsRes.getDeviceModel())) {
					uaCd = uapsRes.getDeviceModel();
				} else {
					// else uaCd가 존재하지 않을 경우 9999로 셋팅. - 비정상 데이터.
					LOGGER.info("## uaCd is not exists, Set uaCd=\"9999\"");
					uaCd = "9999";
				}
			}
		}

		// uaCd로 PhoneInfo 테이블 조회.
		Device device = this.commonComponent.getPhoneInfoByUacd(uaCd);

		if (device != null && StringUtils.isNotBlank(device.getDeviceModelCd())) {
			response.setDeviceModelNo(device.getDeviceModelCd());
		} else {
			throw new StorePlatformException("SAC_MEM_3402", errorKey, errorValue);
		}
		LOGGER.debug("## Response : {}", response);

		return response;
	}

	/**
	 * <pre>
	 * 결제 계좌 정보 인증.
	 * </pre>
	 * 
	 * @param request
	 *            AuthorizeAccountReq
	 * @return AuthorizeAccountRes
	 */
	@Override
	public AuthorizeAccountRes authorizeAccount(AuthorizeAccountReq request) {

		// 1. EC (Inicis 연동) 파라미터 전달, 결제계좌 정보 인증 요청
		InicisAuthAccountEcReq inicisAuthAccountEcReq = new InicisAuthAccountEcReq();
		inicisAuthAccountEcReq.setAcctNm(request.getBankAcctName());
		inicisAuthAccountEcReq.setBankCd(request.getBankCode());
		inicisAuthAccountEcReq.setAcctNo(request.getBankAccount());

		InicisAuthAccountEcRes inicisAuthAccountEcRes = this.inicisSCI.authAccount(inicisAuthAccountEcReq);
		LOGGER.debug("## 계좌인증 성공. Inicis ResultCode : {}", inicisAuthAccountEcRes.getResultCode());

		return new AuthorizeAccountRes();

	}

	/**
	 * <pre>
	 * 2.1.60.	휴대기기 PIN 번호 인증 여부 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            PinAuthorizationCheckReq
	 * @return PinAuthorizationCheckRes
	 */
	@Override
	public PinAuthorizationCheckRes pinAuthorizationCheck(SacRequestHeader header, PinAuthorizationCheckReq req) {
		String tenantId = header.getTenantHeader().getTenantId();
		String systemId = header.getTenantHeader().getSystemId();
		String deviceId = req.getDeviceId();

		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setTenantId(tenantId);
		serviceAuthInfo.setSystemId(systemId);
		serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_PIN);
		serviceAuthInfo.setAuthMdn(deviceId);
		serviceAuthInfo.setAuthValue(req.getPhoneSign());
		serviceAuthInfo.setTimeToLive(req.getTimeToLive());

		/** pin 인증 signature 확인 */
		Object authCntObj = this.commonDao.queryForObject("Miscellaneous.searchPinAuthorizationCheckCnt",
				serviceAuthInfo);

		PinAuthorizationCheckRes res = new PinAuthorizationCheckRes();
		res.setDeviceId(deviceId);
		if (authCntObj != null && Integer.parseInt(authCntObj.toString()) > 0) {
			res.setComptYn(MemberConstants.USE_Y); // 인증번호 확인 성공
		} else {
			res.setComptYn(MemberConstants.USE_N); // 인증번호 확인 실패
		}

		return res;
	}
}
