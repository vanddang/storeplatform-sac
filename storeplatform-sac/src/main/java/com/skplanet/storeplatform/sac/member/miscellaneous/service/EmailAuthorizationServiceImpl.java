package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailV2Res;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchExtentReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuth;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 
 * 이메일 인증 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Miscellaneous 클래스에서 Email인증 관련 기능 클래스 분리
 */
@Service
public class EmailAuthorizationServiceImpl implements EmailAuthorizationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailAuthorizationServiceImpl.class);

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트
	@Autowired
	private UserSCI userSCI; // 회원 Component 사용자 기능 Interface.
	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	@Value("#{propertiesForSac['email.auth.url']}")
	private String mobileEmailAuthUrl;


	/**
	 * <pre>
	 * 이메일 인증 코드 생성.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetEmailAuthorizationCodeReq
	 * @return GetEmailAuthorizationCodeRes
	 */
	@Override
	public GetEmailAuthorizationCodeRes getEmailAuthorizationCode(SacRequestHeader sacRequestHeader,
			GetEmailAuthorizationCodeReq request) {

		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		String systemId = sacRequestHeader.getTenantHeader().getSystemId();

		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthEmail(request.getUserEmail());
		serviceAuthReq.setMbrNo(request.getUserKey());
		serviceAuthReq.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_EMAIL);

		// 1. 기존 인증코드 발급 여부 및 인증 여부 확인
		ServiceAuth authYnInfo = this.commonDao.queryForObject("ServiceAuth.searchEmailAuthYn", serviceAuthReq,
				ServiceAuth.class);

		// 2. 이메일 인증 코드 생성 - GUID 수준의 난수
		String authCode = UUID.randomUUID().toString().replace("-", "");
		LOGGER.debug("## authCode : {}", authCode);

		// 3. DB에 저장(TB_CM_SVC_AUTH)
		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setAuthValue(authCode);
		serviceAuthInfo.setAuthEmail(request.getUserEmail());

		if (authYnInfo == null) {
			serviceAuthInfo.setMbrNo(request.getUserKey());
			serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_EMAIL);
			serviceAuthInfo.setAuthSign("EmailAuthorization"); // 의미 없음. DB에 AUTH_SIGN 이 "NOT NULL"로 정의되어있음.

			this.commonDao.insert("ServiceAuth.createServiceAuthCode", serviceAuthInfo);
			LOGGER.debug("인증코드 신규발급. authCode : {}", authCode);
		} else {
			// 미인증 상태의 인증코드 존재 - 신규 발급 코드로 업데이트.
			serviceAuthInfo.setAuthSeq(authYnInfo.getAuthSeq());
			this.commonDao.update("ServiceAuth.updateServiceAuthCode", serviceAuthInfo);
		}

		// 4. 인증코드 Response
		GetEmailAuthorizationCodeRes response = new GetEmailAuthorizationCodeRes();
		response.setEmailAuthCode(authCode);

		return response;
	}

	/**
	 * <pre>
	 * 이메일 인증 코드 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmEmailAuthorizationCodeReq
	 * @return ConfirmEmailAuthorizationCodeRes
	 */
	@Override
	public ConfirmEmailAuthorizationCodeRes confirmEmailAuthorizationCode(ConfirmEmailAuthorizationCodeReq request) {

		// 1. 인증 코드로 DB 확인하여 , 회원 key, 회원 email 조회
		String authValue = request.getEmailAuthCode();
		String timeToLive = request.getTimeToLive();

		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthValue(authValue);
		serviceAuthReq.setTimeToLive(timeToLive);
		serviceAuthReq.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_EMAIL);
		ServiceAuth serviceAuthInfo = this.commonDao.queryForObject("ServiceAuth.searchEmailAuthInfo",
				serviceAuthReq, ServiceAuth.class);

		/** 2. 인증코드 정보가 존재할 경우, 인증 처리 */
		if (serviceAuthInfo != null) {

			if (MemberConstants.USE_Y.equals(serviceAuthInfo.getAuthComptYn())) { // 기존 인증된 코드일 경우
				throw new StorePlatformException("SAC_MEM_3001");
			}

			/** timeToLive 값이 존재 할 경우 인증코드 유효기간 검사 */
			if (StringUtils.isNotBlank(timeToLive) && StringUtils.isNotBlank(serviceAuthInfo.getCurrDt())
					&& (Double.parseDouble(serviceAuthInfo.getCurrDt()) < 0)) {
				throw new StorePlatformException("SAC_MEM_3002");
			}

			String authSeq = serviceAuthInfo.getAuthSeq();
			this.commonDao.update("ServiceAuth.updateServiceAuthYn", authSeq);
			LOGGER.debug("## 인증 완료.");
		} else {
			throw new StorePlatformException("SAC_MEM_3003"); // 해당 인증코드가 DB Table에 존재하지 않음.
		}
		ConfirmEmailAuthorizationCodeRes response = new ConfirmEmailAuthorizationCodeRes();
		response.setUserEmail(serviceAuthInfo.getAuthEmail());
		response.setUserKey(serviceAuthInfo.getMbrNo());

		return response;
	}

	/**
	 * <pre>
	 * 2.3.19. 이메일 인증 URL 생성.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            GetEmailAuthorizationUrlSacReq
	 * @return GetEmailAuthorizationUrlSacRes
	 */
	@Override
	public GetEmailAuthorizationUrlSacRes getEmailAuthorizationUrl(SacRequestHeader header,
			GetEmailAuthorizationUrlSacReq req) {

		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthEmail(req.getUserEmail());
		serviceAuthReq.setMbrNo(req.getUserKey());
		serviceAuthReq.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_EMAIL_AUTH_URL);

		// 1. 기존 인증코드 발급 여부 및 인증 여부 확인
		ServiceAuth authYnInfo = this.commonDao.queryForObject("ServiceAuth.searchEmailAuthYn", serviceAuthReq,
				ServiceAuth.class);

		// 2. 이메일 인증 코드 생성 - 모바일 웹 인증 URL + GUID 수준의 난수
		String authValue = UUID.randomUUID().toString().replace("-", "");
		String emailAuthUrl = this.mobileEmailAuthUrl + authValue;
		LOGGER.debug("## authCode : {}", emailAuthUrl);

		// 3. DB에 저장(TB_CM_SVC_AUTH)
		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setAuthValue(authValue);
		serviceAuthInfo.setAuthEmail(req.getUserEmail());

		if (authYnInfo == null) {
			serviceAuthInfo.setMbrNo(req.getUserKey());
			serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_EMAIL_AUTH_URL);
			serviceAuthInfo.setAuthSign("EmailAuthorizationUrl"); // 의미 없음. DB에 AUTH_SIGN 이 "NOT NULL"로 정의되어있음.

			this.commonDao.insert("ServiceAuth.createServiceAuthCode", serviceAuthInfo);
			LOGGER.debug("이메일 변경 인증코드 신규발급. authCode : {}", emailAuthUrl);
		} else {
			// 미인증 상태의 인증코드 존재 - 신규 발급 코드로 업데이트.
			serviceAuthInfo.setAuthSeq(authYnInfo.getAuthSeq());
			this.commonDao.update("ServiceAuth.updateServiceAuthCode", serviceAuthInfo);
		}

		// 4. 인증코드 Response
		GetEmailAuthorizationUrlSacRes response = new GetEmailAuthorizationUrlSacRes();
		response.setEmailAuthUrl(emailAuthUrl);

		return response;
	}

	/**
	 * <pre>
	 * 2.3.19. 이메일 인증 URL 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ConfirmEmailAuthorizationUrlSacReq
	 * @return ConfirmEmailAuthorizationUrlSacRes
	 */
	@Override
	public ConfirmEmailAuthorizationUrlSacRes confirmEmailAuthorizationUrl(SacRequestHeader header,
			ConfirmEmailAuthorizationUrlSacReq req) {
		// 1. 인증 코드로 DB 확인하여 , 회원 key, 회원 email 조회
		String authValue = req.getEmailAuthCode();
		String timeToLive = req.getTimeToLive();

		ServiceAuth serviceAuthReq = new ServiceAuth();
		serviceAuthReq.setAuthValue(authValue);
		serviceAuthReq.setTimeToLive(timeToLive);
		serviceAuthReq.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_EMAIL_AUTH_URL);
		ServiceAuth serviceAuthInfo = this.commonDao.queryForObject("ServiceAuth.searchEmailAuthUrlInfo",
				serviceAuthReq, ServiceAuth.class);

		/** 2. 인증코드 정보가 존재할 경우, 인증 처리 */
		if (serviceAuthInfo != null) {

			if (MemberConstants.USE_Y.equals(serviceAuthInfo.getAuthComptYn())) { // 기존 인증된 코드일 경우
				throw new StorePlatformException("SAC_MEM_3001");
			}

			/** timeToLive 값이 존재 할 경우 인증코드 유효기간 검사 */
			if (StringUtils.isNotBlank(timeToLive) && StringUtils.isNotBlank(serviceAuthInfo.getCurrDt())
					&& (Double.parseDouble(serviceAuthInfo.getCurrDt()) < 0)) {
				throw new StorePlatformException("SAC_MEM_3002");
			}

			// 회원 정보 조회 => 회원 정보 변경
			DetailReq detailReq = new DetailReq();
			detailReq.setUserKey(serviceAuthInfo.getMbrNo());
			SearchExtentReq searchExtent = new SearchExtentReq();
			searchExtent.setUserInfoYn(MemberConstants.USE_Y);
			detailReq.setSearchExtent(searchExtent);
			DetailV2Res detailRes = this.userSearchService.detailV2(header, detailReq);

			// 인증 확인 이메일과 사용자 업데이트 이메일이 동일 할경우
			// 1. 사용자 이메일 정보를 업데이트 이메일정보로
			// 2. 사용자 업데이트 이메일 정보를 "" 변경 처리
			if (StringUtils.equals(StringUtils.defaultString(detailRes.getUserInfo().getUserUpdEmail()),
					serviceAuthInfo.getAuthEmail())) {

				UpdateUserRequest updateUserRequest = new UpdateUserRequest();
				updateUserRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
				UserMbr userMbr = new UserMbr();
				userMbr.setUserKey(serviceAuthInfo.getMbrNo());
				userMbr.setUserEmail(detailRes.getUserInfo().getUserUpdEmail());
				userMbr.setUserUpdEmail("");
				updateUserRequest.setUserMbr(userMbr);
				this.userSCI.updateUser(updateUserRequest);

			} else {
				// 인증 이메일정보 != 사용자 업데이트 이메일
				throw new StorePlatformException("SAC_MEM_3006");
			}

			String authSeq = serviceAuthInfo.getAuthSeq();
			this.commonDao.update("ServiceAuth.updateServiceAuthYn", authSeq);
			LOGGER.debug("## 인증 완료.");
		} else {
			throw new StorePlatformException("SAC_MEM_3003"); // 해당 인증코드가 DB Table에 존재하지 않음.
		}

		ConfirmEmailAuthorizationUrlSacRes response = new ConfirmEmailAuthorizationUrlSacRes();
		response.setUserEmail(serviceAuthInfo.getAuthEmail());
		response.setUserKey(serviceAuthInfo.getMbrNo());

		return response;
	}

}
