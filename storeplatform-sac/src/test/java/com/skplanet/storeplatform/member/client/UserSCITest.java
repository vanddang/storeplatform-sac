/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.vo.*;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.*;
import com.skplanet.storeplatform.member.common.code.MainStateCode;
import com.skplanet.storeplatform.member.common.code.SubStateCode;
import com.skplanet.storeplatform.member.common.code.UserTypeCode;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 * 사용자 API 테스트.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/member/spring-test/context-test.xml" })
@Transactional
@TransactionConfiguration
public class UserSCITest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSCITest.class);

	/** The user sci. */
	@Autowired
	private UserSCI userSCI;

	/** The message source accessor. */
	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 정상 사용자의 내부 사용자키.
	 */
	private static final String NORMAL_INSD_USERMBR_NO = "US201401241728143880000637";

	/**
	 * 휴대기기가 2대 이상인 내부 사용자 키.
	 */
	private static final String MULTI_DEVICE_INSD_USERMBR_NO = "IF1023002708420090928145937";

	/**
	 * 정상 사용자의 내부 IDP 키.
	 */
	private static final String NORMAL_USERMBR_NO = "IW1023130025020100311135733";

	/**
	 * 회원은 아니고 미동의 테이블에는 존재하는 사용자 아이디.
	 */
	private static final String NOT_USER_IN_ONEID_TB_MBR_ID = "ljs345";

	/**
	 * 회원은 아니고 미동의 테이블에는 존재하는 사용자 통합서비스 관리번호.
	 */
	private static final String NOT_USER_IN_ONEID_TB_INTG_SVC_NO = "900000083517";

	/**
	 * 정상 사용자의 통합 서비스관리 번호.
	 */
	private static final String NORMAL_INTG_SVC_NO = "100000003303";

	/**
	 * 정상 사용자의 내부 휴대기기 키.
	 */
	private static final String NORMAL_INSD_DEVICE_ID = "01033615111";

	/**
	 * 정상 사용자의 내부 휴대기기 아이디.
	 */
	private static final String NORMAL_DEVICE_ID = "01033615111";

	/**
	 * 정상 사용자의 내부 휴대기기 아이디.
	 */
	private static final String NORMAL_MBR_ID = "hibis1";

	/**
	 * 징계정보가 존재하는 사용자의 내부 사용자키.
	 */
	private static final String HAS_PNSH_INSD_USERMBR_NO = "US201401271640101810000952";

	/**
	 * <pre>
	 * 사용자회원 가입.
	 * 실명인증 내국인 여부.
	 * 내국인여부 필드 추가 되는지 확인.
	 * </pre>
	 */
	@Test
	public void createUserIsRealNameDomestic() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 가입");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateUserRequest createUserRequest;
		CreateUserResponse createUserResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 사용자 가입정보
		UserMbr userMbr = new UserMbr();
		userMbr.setImSvcNo("0102034492"); // INTG_SVC_NO (통합사용자회원번호)
		userMbr.setImMbrNo("IF12345679"); // USERMBR_NO (사용자회원번호)
		userMbr.setUserName("aaa");
		userMbr.setUserID("ID9");
		userMbr.setUserType("123");
		userMbr.setUserMainStatus("111");
		userMbr.setUserSubStatus("222");
//		userMbr.setLoginStatusCode("10"); // LOGIN_STATUS_CD(로그인 상태 코드)
//		userMbr.setStopStatusCode("80"); // OFAUTH_STOP_STATUS_CD(직권중지 상태 코드)

		// 약관동의 정보
		List<MbrClauseAgree> mbrClauseAgreeList;
		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		MbrClauseAgree mbrClauseAgree;
		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US010608");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US010609");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US010610");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 사용자 부가정보
		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr;

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010901");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010902");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010903");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010904");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010905");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010906");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010907");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 실명인증 정보
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");
		mbrAuth.setIsDomestic(Constant.TYPE_YN_Y);

		// 법정대리인 정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US005302");

		// 요청 필수
		createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(commonRequest);
		createUserRequest.setUserMbr(userMbr); // 사용자 가입정보
		createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList); // 약관동의 정보
		createUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList); // 사용자 부가정보
		createUserRequest.setMbrAuth(mbrAuth); // 실명인증 정보
		createUserRequest.setMbrLglAgent(mbrLglAgent); // 법정대리인 정보

		LOGGER.debug("### 넘긴 데이터 : {}", createUserRequest.toString());

		createUserResponse = this.userSCI.create(createUserRequest);
		CommonResponse commonResponse = createUserResponse.getCommonResponse();

		// 응답
		assertThat(createUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(createUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				createUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > userID
		assertNotNull(createUserResponse.getUserID());
		// 응답 필수 > userKey
		assertNotNull(createUserResponse.getUserKey());
		// 응답 필수 > userMainStatus
		assertNotNull(createUserResponse.getUserMainStatus());
		// 응답 필수 > userSubStatus
		assertNotNull(createUserResponse.getUserSubStatus());

		LOGGER.debug("### 받은 데이터 1: {}", createUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

	}

	/**
	 * <pre>
	 * 사용자회원 가입.
	 * 법정대리인의 내국인 여부.
	 * 내국인여부 필드 추가 되는지 확인.
	 * </pre>
	 */
	@Test
	public void createUserisLglAgentDomestic() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 가입");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateUserRequest createUserRequest;
		CreateUserResponse createUserResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 사용자 가입정보
		UserMbr userMbr = new UserMbr();
		userMbr.setImSvcNo("0102034492"); // INTG_SVC_NO (통합사용자회원번호)
		userMbr.setImMbrNo("IF12345679"); // USERMBR_NO (사용자회원번호)
		userMbr.setUserName("aaa");
		userMbr.setUserID("ID9");
		userMbr.setUserType("123");
		userMbr.setUserMainStatus("111");
		userMbr.setUserSubStatus("222");
//		userMbr.setLoginStatusCode("10"); // LOGIN_STATUS_CD(로그인 상태 코드)
//		userMbr.setStopStatusCode("80"); // OFAUTH_STOP_STATUS_CD(직권중지 상태 코드)

		// 약관동의 정보
		List<MbrClauseAgree> mbrClauseAgreeList;
		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		MbrClauseAgree mbrClauseAgree;
		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 사용자 부가정보
		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US004803");
		mbrMangItemPtcr.setExtraProfileValue("100006017147");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 실명인증 정보
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");

		// 법정대리인 정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US005302");
		mbrLglAgent.setIsDomestic(Constant.TYPE_YN_N);

		// 요청 필수
		createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(commonRequest);
		createUserRequest.setUserMbr(userMbr); // 사용자 가입정보
		createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList); // 약관동의 정보
		createUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList); // 사용자 부가정보
		createUserRequest.setMbrAuth(mbrAuth); // 실명인증 정보
		createUserRequest.setMbrLglAgent(mbrLglAgent); // 법정대리인 정보

		LOGGER.debug("### 넘긴 데이터 : {}", createUserRequest.toString());

		createUserResponse = this.userSCI.create(createUserRequest);
		CommonResponse commonResponse = createUserResponse.getCommonResponse();

		// 응답
		assertThat(createUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(createUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				createUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > userID
		assertNotNull(createUserResponse.getUserID());
		// 응답 필수 > userKey
		assertNotNull(createUserResponse.getUserKey());
		// 응답 필수 > userMainStatus
		assertNotNull(createUserResponse.getUserMainStatus());
		// 응답 필수 > userSubStatus
		assertNotNull(createUserResponse.getUserSubStatus());

		LOGGER.debug("### 받은 데이터 1: {}", createUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

	}

	/**
	 * <pre>
	 * 사용자회원 가입.
	 * </pre>
	 */
	@Test
	public void createUserIDOK() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 가입");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateUserRequest createUserRequest;
		CreateUserResponse createUserResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("S01-001");

		// 사용자 가입정보
		UserMbr userMbr = new UserMbr();
		userMbr.setImSvcNo("01020544923242"); // INTG_SVC_NO (통합사용자회원번호)
		userMbr.setImMbrNo("IF123456793241212463"); // USERMBR_NO (사용자회원번호)
		userMbr.setUserName("박상규5");
		userMbr.setUserID("wisestone4");
		userMbr.setUserType("US011502");
		userMbr.setUserMainStatus("US010201");
		userMbr.setUserSubStatus("US010301");
//		userMbr.setLoginStatusCode("10"); // LOGIN_STATUS_CD(로그인 상태 코드)
//		userMbr.setStopStatusCode("80"); // OFAUTH_STOP_STATUS_CD(직권중지 상태 코드)

		// 약관동의 정보
		List<MbrClauseAgree> mbrClauseAgreeList;
		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		MbrClauseAgree mbrClauseAgree;
		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 사용자 부가정보
		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010901");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 실명인증 정보
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("박상규");
		mbrAuth.setCi("cici");

		// 법정대리인 정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US005302");

		// 요청 필수
		createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(commonRequest);
		createUserRequest.setUserMbr(userMbr); // 사용자 가입정보
		createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList); // 약관동의 정보
		createUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList); // 사용자 부가정보
		createUserRequest.setMbrAuth(mbrAuth); // 실명인증 정보
		createUserRequest.setMbrLglAgent(mbrLglAgent); // 법정대리인 정보

		LOGGER.debug("### 넘긴 데이터 : {}", createUserRequest.toString());

		createUserResponse = this.userSCI.create(createUserRequest);
		CommonResponse commonResponse = createUserResponse.getCommonResponse();

		// 응답
		assertThat(createUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(createUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				createUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > userID
		assertNotNull(createUserResponse.getUserID());
		// 응답 필수 > userKey
		assertNotNull(createUserResponse.getUserKey());
		// 응답 필수 > userMainStatus
		assertNotNull(createUserResponse.getUserMainStatus());
		// 응답 필수 > userSubStatus
		assertNotNull(createUserResponse.getUserSubStatus());

		LOGGER.debug("### 받은 데이터 1: {}", createUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

	}

	/**
	 * <pre>
	 * 사용자회원 가입.
	 * 아이디가 중복으로 상태 변경
	 * ggissggiss013
	 * </pre>
	 */
	@Test
	public void createUserIDOKErrorTest() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 가입");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateUserRequest createUserRequest;
		CreateUserResponse createUserResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 사용자 가입정보
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID("ggissggiss013");
		userMbr.setUserType("1");
		userMbr.setImMbrNo("IF123456793241212462");
		userMbr.setUserMainStatus(MainStateCode.NORMAL.getCode());
		userMbr.setUserSubStatus("US010305");
		// userMbr.setLoginStatusCode("10"); // LOGIN_STATUS_CD(로그인 상태 코드)
		// userMbr.setStopStatusCode("80"); // OFAUTH_STOP_STATUS_CD(직권중지 상태 코드)

		// 약관동의 정보
		List<MbrClauseAgree> mbrClauseAgreeList;
		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		MbrClauseAgree mbrClauseAgree;
		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 사용자 부가정보
		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US004803");
		mbrMangItemPtcr.setExtraProfileValue("100006017147");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 실명인증 정보
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");

		// 법정대리인 정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US005302");

		// 요청 필수
		createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(commonRequest);
		createUserRequest.setUserMbr(userMbr); // 사용자 가입정보
		createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList); // 약관동의 정보
		createUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList); // 사용자 부가정보
		createUserRequest.setMbrAuth(mbrAuth); // 실명인증 정보
		createUserRequest.setMbrLglAgent(mbrLglAgent); // 법정대리인 정보

		LOGGER.debug("### 넘긴 데이터 : {}", createUserRequest.toString());

		try {

			createUserResponse = this.userSCI.create(createUserRequest);
			LOGGER.debug("### createUserResponse {}", createUserResponse);

		} catch (StorePlatformException e) {

			LOGGER.debug("### e {}", e);
			LOGGER.debug("### e.getMessage() {}", e.getMessage());
			assertSame(e.getMessage(), this.getMessage("response.ResultCode.duplicatedMemberID", ""));

		} catch (Exception e) {

			LOGGER.debug("### e {}", e);

		}

	}

	/**
	 * <pre>
	 * 사용자회원 가입.
	 * 모바일 사용자 회원가입
	 * </pre>
	 */
	@Test
	public void createUserIDMobile() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 가입");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateUserRequest createUserRequest;
		CreateUserResponse createUserResponse;

		//

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("S01-001");

		// 사용자 가입정보
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID("0501234567");
		userMbr.setImMbrNo("IF123456793241212462");
		userMbr.setUserType(UserTypeCode.MOBILE_USER.getCode()); // 모바일 회원
		userMbr.setUserMainStatus(MainStateCode.NORMAL.getCode());
		userMbr.setUserSubStatus(SubStateCode.NORMAL.getCode());
//		userMbr.setLoginStatusCode("10"); // LOGIN_STATUS_CD(로그인 상태 코드)
//		userMbr.setStopStatusCode("80"); // OFAUTH_STOP_STATUS_CD(직권중지 상태 코드)

		// 약관동의 정보
		List<MbrClauseAgree> mbrClauseAgreeList;
		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		MbrClauseAgree mbrClauseAgree;
		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 사용자 부가정보
		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US004803");
		mbrMangItemPtcr.setExtraProfileValue("100006017147");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 실명인증 정보
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("박상규");
		mbrAuth.setCi("cici");

		// 법정대리인 정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US005302");

		// 요청 필수
		createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(commonRequest);
		createUserRequest.setUserMbr(userMbr); // 사용자 가입정보
		createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList); // 약관동의 정보
		createUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList); // 사용자 부가정보
		createUserRequest.setMbrAuth(mbrAuth); // 실명인증 정보
		createUserRequest.setMbrLglAgent(mbrLglAgent); // 법정대리인 정보

		LOGGER.debug("### 넘긴 데이터 : {}", createUserRequest.toString());

		createUserResponse = this.userSCI.create(createUserRequest);
		CommonResponse commonResponse = createUserResponse.getCommonResponse();

		// 응답
		assertThat(createUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(createUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				createUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > userID
		assertNotNull(createUserResponse.getUserID());
		// 응답 필수 > userKey
		assertNotNull(createUserResponse.getUserKey());
		// 응답 필수 > userMainStatus
		assertNotNull(createUserResponse.getUserMainStatus());
		// 응답 필수 > userSubStatus
		assertNotNull(createUserResponse.getUserSubStatus());

		LOGGER.debug("### 받은 데이터 1: {}", createUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

	}

	/**
	 * <pre>
	 * 사용자회원 가입.
	 * 중복 아이디 테스트
	 * UserID : ainb137
	 * 메인상태 : 정상
	 * </pre>
	 */
	@Test
	public void createUserIDDuplicated() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 가입");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateUserRequest createUserRequest;
		CreateUserResponse createUserResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 사용자 가입정보
		UserMbr userMbr = new UserMbr();
		userMbr.setImSvcNo("0102034491"); // INTG_SVC_NO (통합사용자회원번호)
		userMbr.setImMbrNo("IF12345678"); // USERMBR_NO (사용자회원번호)
		userMbr.setUserName("aaa");
		userMbr.setUserID("ainb137"); // 중복 아이디, 정상사용자
		userMbr.setUserType("123");
		userMbr.setUserMainStatus("111");
		userMbr.setUserSubStatus("222");
//		userMbr.setLoginStatusCode("1"); // LOGIN_STATUS_CD(로그인 상태 코드)
//		userMbr.setStopStatusCode("8"); // OFAUTH_STOP_STATUS_CD(직권중지 상태 코드)

		// 약관동의 정보
		List<MbrClauseAgree> mbrClauseAgreeList;
		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		MbrClauseAgree mbrClauseAgree;
		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 사용자 부가정보
		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US004803");
		mbrMangItemPtcr.setExtraProfileValue("100006017147");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 실명인증 정보
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");

		// 법정대리인 정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US005302");

		// 요청 필수
		createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(commonRequest);
		createUserRequest.setUserMbr(userMbr); // 사용자 가입정보
		createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList); // 약관동의 정보
		createUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList); // 사용자 부가정보
		createUserRequest.setMbrAuth(mbrAuth); // 실명인증 정보
		createUserRequest.setMbrLglAgent(mbrLglAgent); // 법정대리인 정보

		LOGGER.debug("### 넘긴 데이터 : {}", createUserRequest.toString());

		try {

			createUserResponse = this.userSCI.create(createUserRequest);
			LOGGER.debug("### createUserResponse {}", createUserResponse);

		} catch (StorePlatformException e) {

			LOGGER.debug("### e {}", e);
			LOGGER.debug("### e.getMessage() {}", e.getMessage());
			assertSame(e.getMessage(), this.getMessage("response.ResultCode.duplicatedMemberID", ""));

		} catch (Exception e) {

			LOGGER.debug("### e {}", e);

		}

	}

	/**
	 * <pre>
	 * 사용자회원 가입.
	 * 중복 아이디 체크
	 * </pre>
	 */
	@Test
	public void createUserIDLimited() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 가입");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateUserRequest createUserRequest;
		CreateUserResponse createUserResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 사용자 가입정보
		UserMbr userMbr = new UserMbr();
		userMbr.setImSvcNo("0102034491"); // INTG_SVC_NO (통합사용자회원번호)
		userMbr.setImMbrNo("IF12345678"); // USERMBR_NO (사용자회원번호)
		userMbr.setUserName("aaa");
		userMbr.setUserID("mdfelgon"); // 제한 단어
		userMbr.setUserType("123");
		userMbr.setUserMainStatus("111");
		userMbr.setUserSubStatus("222");
//		userMbr.setLoginStatusCode("1"); // LOGIN_STATUS_CD(로그인 상태 코드)
//		userMbr.setStopStatusCode("8"); // OFAUTH_STOP_STATUS_CD(직권중지 상태 코드)

		// 약관동의 정보
		List<MbrClauseAgree> mbrClauseAgreeList;
		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		MbrClauseAgree mbrClauseAgree;
		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 사용자 부가정보
		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US004803");
		mbrMangItemPtcr.setExtraProfileValue("100006017147");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 실명인증 정보
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");

		// 법정대리인 정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US005302");

		// 요청 필수
		createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(commonRequest);
		createUserRequest.setUserMbr(userMbr); // 사용자 가입정보
		createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList); // 약관동의 정보
		createUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList); // 사용자 부가정보
		createUserRequest.setMbrAuth(mbrAuth); // 실명인증 정보
		createUserRequest.setMbrLglAgent(mbrLglAgent); // 법정대리인 정보

		LOGGER.debug("### 넘긴 데이터 : {}", createUserRequest.toString());

		try {

			createUserResponse = this.userSCI.create(createUserRequest);
			LOGGER.debug("### createUserResponse {}", createUserResponse);

		} catch (StorePlatformException e) {

			LOGGER.debug("### e {}", e);
			LOGGER.debug("### e.getMessage() {}", e.getMessage());
			assertSame(e.getMessage(), this.getMessage("response.ResultCode.restrictedWordMemberID", ""));

		} catch (Exception e) {

			LOGGER.debug("### e {}", e);

		}

	}

	/**
	 * <pre>
	 * 로그인상태코드, 직권중지코드가 없는 경우 일괄 정상코드 설정.
	 * 로그인 상태코드 : 10
	 * 직권중지 상태코드 : 80
	 * userID : tlaeowlsuser1xx
	 * userPW : abcd1234
	 * isOneID: Y
	 * isSuccess: Y
	 * isMobile: N
	 * </pre>
	 */
	@Test
	public void createUserIDLoginStopStatusCode() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 가입");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateUserRequest createUserRequest;
		CreateUserResponse createUserResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 사용자 가입정보
		UserMbr userMbr = new UserMbr();
		userMbr.setUserID("tlaeowlsuser1xx");
		userMbr.setUserMainStatus(MainStateCode.NORMAL.getCode());
		userMbr.setUserSubStatus("US010305");
		userMbr.setImMbrNo("IF123456712");
		userMbr.setUserType(UserTypeCode.MOBILE_USER.getCode());
		// userMbr.setLoginStatusCode("10"); // LOGIN_STATUS_CD(로그인 상태 코드)
		// userMbr.setStopStatusCode("80"); // OFAUTH_STOP_STATUS_CD(직권중지 상태 코드)

		// 약관동의 정보
		List<MbrClauseAgree> mbrClauseAgreeList;
		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		MbrClauseAgree mbrClauseAgree;
		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 사용자 부가정보
		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US004803");
		mbrMangItemPtcr.setExtraProfileValue("100006017147");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 실명인증 정보
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");

		// 법정대리인 정보
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US005302");

		// 요청 필수
		createUserRequest = new CreateUserRequest();
		createUserRequest.setCommonRequest(commonRequest);
		createUserRequest.setUserMbr(userMbr); // 사용자 가입정보
		createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList); // 약관동의 정보
		createUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList); // 사용자 부가정보
		createUserRequest.setMbrAuth(mbrAuth); // 실명인증 정보
		createUserRequest.setMbrLglAgent(mbrLglAgent); // 법정대리인 정보

		LOGGER.debug("### 넘긴 데이터 : {}", createUserRequest.toString());

		createUserResponse = this.userSCI.create(createUserRequest);
		CommonResponse commonResponse = createUserResponse.getCommonResponse();

		// 응답
		assertThat(createUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(createUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 에러 : 제한단어인 사용자 ID
		assertSame(
				createUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### 받은 데이터 1: {}", createUserResponse.toString());
		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

	}

	/**
	 * <pre>
	 * 사용자회원 로그인.
	 * OneID이용 로그인 시도
	 * OneID + isSuccess : Y이므로 성공을 리턴함.
	 * UserID : wap_4236(탈퇴 회원으로 변경됨), 2014-01-21
	 * UserID : ainb137
	 * </pre>
	 */
	@Test
	public void loginUserOneIDSuccess() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 로그인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LoginUserResponse loginUserResponse;
		LoginUserRequest loginUserRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 요청 필수
		loginUserRequest = new LoginUserRequest();
		loginUserRequest.setUserID("khkim67_0921_003"); // 상용에 존재하는 정상 사용자.
		loginUserRequest.setUserID("ainb137");
		loginUserRequest.setUserPW("");
		loginUserRequest.setIsOneID(Constant.TYPE_YN_Y);
		loginUserRequest.setIsSuccess(Constant.TYPE_YN_Y);
		loginUserRequest.setIsMobile(Constant.TYPE_YN_N);

		loginUserRequest.setIpAddress("127.0.0.1");

		loginUserRequest.setCommonRequest(commonRequest);
		loginUserRequest.setIsAutoLogin(Constant.TYPE_YN_N);
		loginUserRequest.setLoginReason("fg/success");

		LOGGER.debug("### 넘긴 데이터 : {}", loginUserRequest.toString());

		// 요청
		loginUserResponse = this.userSCI.updateLoginUser(loginUserRequest);
		CommonResponse commonResponse = loginUserResponse.getCommonResponse();

		// 응답
		assertThat(loginUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(loginUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(loginUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(loginUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				loginUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > userMainStatus
		assertNotNull(loginUserResponse.getUserMainStatus());
		// 응답 필수 > userSubStatus
		assertNotNull(loginUserResponse.getUserSubStatus());
		// 응답 필수 > loginFailCount
//		assertTrue(loginUserResponse.getLoginFailCount() >= 0);
		// 응답 필수 > isLoginSuccess
		assertNotNull(loginUserResponse.getIsLoginSuccess());
//		// 응답 필수 > loginStatusCode
//		assertNotNull(loginUserResponse.getLoginStatusCode());
//		// 응답 필수 > stopStatusCode
//		assertNotNull(loginUserResponse.getStopStatusCode());

		LOGGER.debug("### 받은 데이터 1: {}", loginUserResponse.toString());
		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());
	}

	/**
	 * <pre>
	 * 사용자회원 로그인.
	 * 로그인 히스토리 기록
	 * </pre>
	 */
	@Test
	public void loginUserWriteLoginHistory() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 로그인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LoginUserResponse loginUserResponse;
		LoginUserRequest loginUserRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 요청 필수
		loginUserRequest = new LoginUserRequest();
		loginUserRequest.setUserID("ainb137");
		loginUserRequest.setUserPW("");
		loginUserRequest.setIsOneID(Constant.TYPE_YN_Y);
		loginUserRequest.setIsSuccess(Constant.TYPE_YN_Y);
		loginUserRequest.setIsMobile(Constant.TYPE_YN_N);
		loginUserRequest.setCommonRequest(commonRequest);
		loginUserRequest.setIpAddress("1.1.1.1");
		loginUserRequest.setScVersion("1.0");
		loginUserRequest.setIsAutoLogin("N");

		LOGGER.debug("### 넘긴 데이터 : {}", loginUserRequest.toString());

		// 요청
		loginUserResponse = this.userSCI.updateLoginUser(loginUserRequest);
		CommonResponse commonResponse = loginUserResponse.getCommonResponse();

		// 응답
		assertThat(loginUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(loginUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(loginUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(loginUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				loginUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// // 응답 필수 > userKey
		// assertNotNull(loginUserResponse.getUserKey());
		// 응답 필수 > userMainStatus
		assertNotNull(loginUserResponse.getUserMainStatus());
		// 응답 필수 > userSubStatus
		assertNotNull(loginUserResponse.getUserSubStatus());
		// 응답 필수 > loginFailCount
//		assertTrue(loginUserResponse.getLoginFailCount() >= 0);
		// 응답 필수 > isLoginSuccess
		assertNotNull(loginUserResponse.getIsLoginSuccess());
		// 응답 필수 > loginStatusCode
//		assertNotNull(loginUserResponse.getLoginStatusCode());
		// 응답 필수 > stopStatusCode
//		assertNotNull(loginUserResponse.getStopStatusCode());

		LOGGER.debug("### 받은 데이터 1: {}", loginUserResponse.toString());
		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());
	}

	/**
	 * <pre>
	 * 사용자회원 로그인.
	 * 존재하지 않는 사용자 아이디로 로그인 요청하여 실패 반환
	 * </pre>
	 */
	@Test
	public void loginUserOneIDFail() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 로그인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LoginUserResponse loginUserResponse;
		LoginUserRequest loginUserRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 요청 필수
		loginUserRequest = new LoginUserRequest();
		loginUserRequest.setUserID("wap_4236111"); // watermin0927
		loginUserRequest.setIsOneID(Constant.TYPE_YN_Y);
		loginUserRequest.setIsSuccess(Constant.TYPE_YN_N);
		loginUserRequest.setIsMobile(Constant.TYPE_YN_N);
		loginUserRequest.setCommonRequest(commonRequest);

		LOGGER.debug("### 넘긴 데이터 : {}", loginUserRequest.toString());

		// 요청
		try {

			loginUserResponse = this.userSCI.updateLoginUser(loginUserRequest);
			LOGGER.debug("### loginUserResponse {}", loginUserResponse);

		} catch (StorePlatformException e) {

			LOGGER.debug("### e {}", e);
			LOGGER.debug("### e.getMessage() {}", e.getMessage());
			assertSame(e.getMessage(), this.getMessage("response.ResultCode.userKeyNotFound", ""));

		} catch (Exception e) {

			LOGGER.debug("### e {}", e);

		}

	}

	/**
	 * <pre>
	 * 사용자회원 로그인.
	 * OneID가 아니어서 ID/PW를 회원DB에서 조회하여 성공
	 * 
	 * 추가한 테스트 아이디/패스워드
	 * qatft001 : IW1023914553820110323105511
	 * PWD : 1111
	 * </pre>
	 */
	@Test
	public void loginUserOneIDN() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 로그인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LoginUserResponse loginUserResponse;
		LoginUserRequest loginUserRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 요청 필수
		loginUserRequest = new LoginUserRequest();
		loginUserRequest.setUserID("qatft001");
		loginUserRequest.setUserPW("1111");
		loginUserRequest.setIsOneID(Constant.TYPE_YN_N);
		loginUserRequest.setIsSuccess(Constant.TYPE_YN_Y);
		loginUserRequest.setIsMobile(Constant.TYPE_YN_N);
		loginUserRequest.setIsAutoLogin("N");
		loginUserRequest.setCommonRequest(commonRequest);

		LOGGER.debug("### 넘긴 데이터 : {}", loginUserRequest.toString());

		// 요청
		loginUserResponse = this.userSCI.updateLoginUser(loginUserRequest);
		CommonResponse commonResponse = loginUserResponse.getCommonResponse();

		// 응답
		assertThat(loginUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(loginUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(loginUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(loginUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				loginUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > isLoginSuccess : N
		assertNotNull(loginUserResponse.getIsLoginSuccess());
		// 응답 필수 > loginStatusCode
//		assertNotNull(loginUserResponse.getLoginStatusCode());
		// 응답 필수 > stopStatusCode
//		assertNotNull(loginUserResponse.getStopStatusCode());

		LOGGER.debug("### 받은 데이터 1: {}", loginUserResponse.toString());
		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());
	}

	/**
	 * <pre>
	 * 사용자회원 로그인.
	 * 로그인상태코드 설정 기능 추가
	 * </pre>
	 */
	@Test
	public void loginUserOneIDUpdateLoginStatusCode() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 로그인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LoginUserResponse loginUserResponse;
		LoginUserRequest loginUserRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 요청 필수
		loginUserRequest = new LoginUserRequest();
		loginUserRequest.setUserID("qatft001");
		loginUserRequest.setUserPW("1111");
		loginUserRequest.setIsOneID(Constant.TYPE_YN_Y);
		loginUserRequest.setIsSuccess(Constant.TYPE_YN_Y);
		loginUserRequest.setIsMobile(Constant.TYPE_YN_N);
		loginUserRequest.setCommonRequest(commonRequest);
		loginUserRequest.setLoginStatusCode("30");
		loginUserRequest.setIsAutoLogin("N");

		LOGGER.debug("### 넘긴 데이터 : {}", loginUserRequest.toString());

		// 요청
		loginUserResponse = this.userSCI.updateLoginUser(loginUserRequest);
		CommonResponse commonResponse = loginUserResponse.getCommonResponse();

		// 응답
		assertThat(loginUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(loginUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(loginUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(loginUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				loginUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > isLoginSuccess : N
		assertNotNull(loginUserResponse.getIsLoginSuccess());
		// 응답 필수 > loginStatusCode
//		assertNotNull(loginUserResponse.getLoginStatusCode());
		// 응답 필수 > stopStatusCode
//		assertNotNull(loginUserResponse.getStopStatusCode());

		LOGGER.debug("### 받은 데이터 1: {}", loginUserResponse.toString());
		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());
	}

	/**
	 * <pre>
	 * 사용자회원 로그인.
	 * 탈퇴한 사용자 아이디 : tc_one_test08
	 * </pre>
	 */
	@Test
	public void loginUserRemovedID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 로그인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LoginUserRequest loginUserRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 요청 필수
		loginUserRequest = new LoginUserRequest();
		loginUserRequest.setUserID("tc_one_test08");
		loginUserRequest.setUserPW("");
		loginUserRequest.setIsOneID(Constant.TYPE_YN_N);
		loginUserRequest.setIsMobile(Constant.TYPE_YN_N);
		loginUserRequest.setCommonRequest(commonRequest);

		LOGGER.debug("### 넘긴 데이터 : {}", loginUserRequest.toString());

		try {

			LoginUserResponse loginUserResponse;
			loginUserResponse = this.userSCI.updateLoginUser(loginUserRequest);
			LOGGER.debug("### loginUserResponse {}", loginUserResponse);

		} catch (StorePlatformException e) {

			LOGGER.debug("### e {}", e);
			LOGGER.debug("### e.getMessage() {}", e.getMessage());
			assertSame(e.getMessage(), this.getMessage("response.ResultCode.userKeyNotFound", ""));

		} catch (Exception e) {

			LOGGER.debug("### e {}", e);

		}

	}

	/**
	 * <pre>
	 * 사용자회원 로그인.
	 * 모바일 회원이며 패스워드가 존재하지 않으나 로그인은 성공함.
	 * </pre>
	 */
	@Test
	public void loginUserMobileUser() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 로그인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LoginUserResponse loginUserResponse;
		LoginUserRequest loginUserRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("W");

		// 요청 필수
		loginUserRequest = new LoginUserRequest();
		loginUserRequest.setUserID(NORMAL_DEVICE_ID);
		loginUserRequest.setUserID("01020977773"); // dup
		loginUserRequest.setUserPW("");
		loginUserRequest.setIsOneID(Constant.TYPE_YN_Y);
		loginUserRequest.setIsSuccess(Constant.TYPE_YN_Y);
		loginUserRequest.setIsMobile(Constant.TYPE_YN_Y);

		loginUserRequest.setScVersion("2.0");
		loginUserRequest.setIsAutoLogin("N");

		loginUserRequest.setCommonRequest(commonRequest);
		loginUserRequest.setIsAutoLogin("1"); // 'Y', 'N' 이외는 mandatoryNotFound(9993)
		loginUserRequest.setIsAutoLogin(Constant.TYPE_YN_N);

		LOGGER.debug("### 넘긴 데이터 : {}", loginUserRequest.toString());

		// 요청
		loginUserResponse = this.userSCI.updateLoginUser(loginUserRequest);
		CommonResponse commonResponse = loginUserResponse.getCommonResponse();

		// 응답
		assertThat(loginUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(loginUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(loginUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(loginUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				loginUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > isLoginSuccess : N
		assertNotNull(loginUserResponse.getIsLoginSuccess());
		// 응답 필수 > loginStatusCode
//		assertNotNull(loginUserResponse.getLoginStatusCode());
		// 응답 필수 > stopStatusCode
//		assertNotNull(loginUserResponse.getStopStatusCode());

		LOGGER.debug("### 받은 데이터 1: {}", loginUserResponse.toString());
		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());
//		LOGGER.debug("### 받은 데이터 3: {}", loginUserResponse.getLoginFailCount());
	}

	/**
	 * <pre>
	 * 사용자회원 기본정보 수정.
	 * </pre>
	 */
	// @Ignore
	@Test
	public void testUpdateUser() {

		// vo 생성
		UpdateUserRequest updateUserRequest = new UpdateUserRequest();
		UserMbr userMbr = new UserMbr();
		CommonRequest commonRequest = new CommonRequest();

		// 값셋팅
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("S001");

		List<MbrClauseAgree> mbrClauseAgreeList;
		MbrClauseAgree mbrClauseAgree;

		userMbr.setUserKey("US201403211302461930004672"); // ID: skp_test1
		// userMbr.setUserKey("IM142100006600755201304050800"); // 상용 userKey
		// userMbr.setUserName("bbb");
		// userMbr.setUserType("123");
		// userMbr.setUserMainStatus("111a");
		// userMbr.setUserSubStatus("222");
		// userMbr.setImSvcNo("100000003280");
		userMbr.setImMbrNo("IF12345679-");
		userMbr.setUserPhone("0101234567");
		userMbr.setIsMemberPoint("N");

		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		// mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		// mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr;

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010904");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		// mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010905");
		mbrMangItemPtcr.setExtraProfileValue("N");
		// mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010906");
		mbrMangItemPtcr.setExtraProfileValue("N");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");

		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US010901");

		// 최종 vo 에 값 셋팅
		updateUserRequest.setUserMbr(userMbr);
		updateUserRequest.setCommonRequest(commonRequest);
		// updateUserRequest.setMbrClauseAgree(mbrClauseAgreeList);
		updateUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList);
		updateUserRequest.setMbrAuth(mbrAuth);
		updateUserRequest.setMbrLglAgent(mbrLglAgent);

		LOGGER.debug("### 넘긴 데이터 : {}", updateUserRequest.toString());

		UpdateUserResponse updateUserResponse = this.userSCI.updateUser(updateUserRequest);
		CommonResponse commonResponse = updateUserResponse.getCommonResponse();

		LOGGER.debug("### 받은 데이터 1: {}", updateUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

		assertThat(updateUserResponse, notNullValue());

		assertThat(updateUserResponse.getUserKey(), notNullValue());
	}

	/**
	 * <pre>
	 * 사용자회원 기본정보 수정.
	 * </pre>
	 */
	// @Ignore
	@Test
	public void testUpdateUserOGG() {

		// vo 생성
		UpdateUserRequest updateUserRequest = new UpdateUserRequest();
		UserMbr userMbr = new UserMbr();
		CommonRequest commonRequest = new CommonRequest();

		// 값셋팅
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("S001");

		List<MbrClauseAgree> mbrClauseAgreeList;
		MbrClauseAgree mbrClauseAgree;

		userMbr.setUserKey("US201403211302461930004672"); // ID: skp_test1
		// userMbr.setUserKey("IM142100006600755201304050800"); // 상용 userKey
		// userMbr.setUserName("bbb");
		// userMbr.setUserType("123");
		// userMbr.setUserMainStatus("111a");
		// userMbr.setUserSubStatus("222");
		// userMbr.setImSvcNo("100000003280");
		userMbr.setImMbrNo("IF12345679-");
		userMbr.setUserPhone("0101234567");
		userMbr.setIsMemberPoint("N");

		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		// mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		// mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		List<MbrMangItemPtcr> mbrMangItemPtcrList;
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr;

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010904");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		// mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010905");
		mbrMangItemPtcr.setExtraProfileValue("N");
		// mbrMangItemPtcrList.add(mbrMangItemPtcr);

		mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010906");
		mbrMangItemPtcr.setExtraProfileValue("N");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");

		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US010901");

		// 최종 vo 에 값 셋팅
		updateUserRequest.setUserMbr(userMbr);
		updateUserRequest.setCommonRequest(commonRequest);
		// updateUserRequest.setMbrClauseAgree(mbrClauseAgreeList);
		updateUserRequest.setMbrMangItemPtcrList(mbrMangItemPtcrList);
		updateUserRequest.setMbrAuth(mbrAuth);
		updateUserRequest.setMbrLglAgent(mbrLglAgent);

		LOGGER.debug("### 넘긴 데이터 : {}", updateUserRequest.toString());

		UpdateUserResponse updateUserResponse = this.userSCI.updateUser(updateUserRequest);
		CommonResponse commonResponse = updateUserResponse.getCommonResponse();

		LOGGER.debug("### 받은 데이터 1: {}", updateUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

		assertThat(updateUserResponse, notNullValue());

		assertThat(updateUserResponse.getUserKey(), notNullValue());
	}

	/**
	 * <pre>
	 * 사용자회원 상태정보 수정.
	 * </pre>
	 */
	@Test
	public void updateStatusMBRID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 상태정보 수정.");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateStatusUserRequest updateStatusUserRequest;
		UpdateStatusUserResponse updateStatusUserResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_ID);
		keySearch.setKeyString("shop_20017421"); // 상용, 정상상태인 사용자
		keySearch.setKeyString(NORMAL_MBR_ID);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		updateStatusUserRequest = new UpdateStatusUserRequest();
		updateStatusUserRequest.setUserMainStatus("sss");
		updateStatusUserRequest.setUserSubStatus("ssaa");
		updateStatusUserRequest.setCommonRequest(commonRequest);
		updateStatusUserRequest.setKeySearchList(keySearchList);
		updateStatusUserRequest.setLoginStatusCode("10");
		updateStatusUserRequest.setStopStatusCode("80");

		LOGGER.debug("### 넘긴 데이터 : {}", updateStatusUserRequest.toString());

		// 요청
		updateStatusUserResponse = this.userSCI.updateStatus(updateStatusUserRequest);
		CommonResponse commonResponse = updateStatusUserResponse.getCommonResponse();

		LOGGER.debug("### 받은 데이터 1: {}", updateStatusUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

		// 응답
		assertThat(updateStatusUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateStatusUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateStatusUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateStatusUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateStatusUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * 사용자회원 상태정보 수정.
	 * </pre>
	 */
	@Test
	public void updateStatusInsdUsermbrNO() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 상태정보 수정.");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateStatusUserRequest updateStatusUserRequest;
		UpdateStatusUserResponse updateStatusUserResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		keySearch.setKeyString("IF102100001520090625170021"); // 회원이지만 휴대기기가 없는 UserKey
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		updateStatusUserRequest = new UpdateStatusUserRequest();
		updateStatusUserRequest.setUserMainStatus("sss");
		updateStatusUserRequest.setUserSubStatus("ssaa");
		updateStatusUserRequest.setCommonRequest(commonRequest);
		updateStatusUserRequest.setKeySearchList(keySearchList);

		LOGGER.debug("### 넘긴 데이터 : {}", updateStatusUserRequest.toString());

		// 요청
		updateStatusUserResponse = this.userSCI.updateStatus(updateStatusUserRequest);
		CommonResponse commonResponse = updateStatusUserResponse.getCommonResponse();

		// 응답
		assertThat(updateStatusUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateStatusUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateStatusUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateStatusUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateStatusUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### 받은 데이터 1: {}", updateStatusUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

	}

	/**
	 * <pre>
	 * 사용자회원 상태정보 수정.
	 * </pre>
	 */
	@Test
	public void updateStatusUsermbrNo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 상태정보 수정.");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateStatusUserRequest updateStatusUserRequest;
		UpdateStatusUserResponse updateStatusUserResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_IDP_KEY);
		keySearch.setKeyString(NORMAL_USERMBR_NO);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		updateStatusUserRequest = new UpdateStatusUserRequest();
		updateStatusUserRequest.setUserMainStatus("sss");
		updateStatusUserRequest.setUserSubStatus("ssaa");
		updateStatusUserRequest.setCommonRequest(commonRequest);
		updateStatusUserRequest.setKeySearchList(keySearchList);

		LOGGER.debug("### 넘긴 데이터 : {}", updateStatusUserRequest.toString());

		// 요청
		updateStatusUserResponse = this.userSCI.updateStatus(updateStatusUserRequest);
		CommonResponse commonResponse = updateStatusUserResponse.getCommonResponse();

		// 응답
		assertThat(updateStatusUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateStatusUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateStatusUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateStatusUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateStatusUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### 받은 데이터 1: {}", updateStatusUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());
	}

	/**
	 * <pre>
	 * 사용자회원 상태정보 수정.
	 * </pre>
	 */
	@Test
	public void updateStatusIntgSvcNo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 상태정보 수정.");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateStatusUserRequest updateStatusUserRequest;
		UpdateStatusUserResponse updateStatusUserResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_ONEID_KEY);
		keySearch.setKeyString(NORMAL_INTG_SVC_NO);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		updateStatusUserRequest = new UpdateStatusUserRequest();
		updateStatusUserRequest.setUserMainStatus("sss");
		updateStatusUserRequest.setUserSubStatus("ssaa");
		updateStatusUserRequest.setCommonRequest(commonRequest);
		updateStatusUserRequest.setKeySearchList(keySearchList);

		LOGGER.debug("### 넘긴 데이터 : {}", updateStatusUserRequest.toString());

		// 요청
		updateStatusUserResponse = this.userSCI.updateStatus(updateStatusUserRequest);
		CommonResponse commonResponse = updateStatusUserResponse.getCommonResponse();

		// 응답
		assertThat(updateStatusUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateStatusUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateStatusUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateStatusUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateStatusUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### 받은 데이터 1: {}", updateStatusUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());
	}

	/**
	 * <pre>
	 * 사용자회원 상태정보 수정.
	 * </pre>
	 */
	@Test
	public void updateStatusInsdDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 상태정보 수정.");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateStatusUserRequest updateStatusUserRequest;
		UpdateStatusUserResponse updateStatusUserResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString(NORMAL_INSD_DEVICE_ID);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		updateStatusUserRequest = new UpdateStatusUserRequest();
		updateStatusUserRequest.setUserMainStatus("sss");
		updateStatusUserRequest.setUserSubStatus("ssaa");
		updateStatusUserRequest.setCommonRequest(commonRequest);
		updateStatusUserRequest.setKeySearchList(keySearchList);

		LOGGER.debug("### 넘긴 데이터 : {}", updateStatusUserRequest.toString());

		// 요청
		updateStatusUserResponse = this.userSCI.updateStatus(updateStatusUserRequest);
		CommonResponse commonResponse = updateStatusUserResponse.getCommonResponse();

		// 응답
		assertThat(updateStatusUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateStatusUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateStatusUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateStatusUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateStatusUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### 받은 데이터 1: {}", updateStatusUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

	}

	/**
	 * <pre>
	 * 사용자회원 상태정보 수정.
	 * </pre>
	 */
	@Test
	public void updateStatusDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 상태정보 수정.");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateStatusUserRequest updateStatusUserRequest;
		UpdateStatusUserResponse updateStatusUserResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		// keySearch.setKeyString(NORMAL_DEVICE_ID);
		keySearch.setKeyString("01020977773");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		updateStatusUserRequest = new UpdateStatusUserRequest();
		updateStatusUserRequest.setUserMainStatus("sss");
		updateStatusUserRequest.setUserSubStatus("ssaa");
		updateStatusUserRequest.setCommonRequest(commonRequest);
		updateStatusUserRequest.setKeySearchList(keySearchList);

		LOGGER.debug("### 넘긴 데이터 : {}", updateStatusUserRequest.toString());

		// 요청
		updateStatusUserResponse = this.userSCI.updateStatus(updateStatusUserRequest);
		CommonResponse commonResponse = updateStatusUserResponse.getCommonResponse();

		// 응답
		assertThat(updateStatusUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateStatusUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateStatusUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateStatusUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateStatusUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### 받은 데이터 1: {}", updateStatusUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

	}

	/**
	 * <pre>
	 * 사용자회원 탈퇴.
	 * IW1023914553820110323105511
	 * 휴대기기가 1대인 사용자
	 * </pre>
	 */
	@Test
	public void removeUser() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원 탈퇴.");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemoveUserRequest removeUserRequest;
		RemoveUserResponse removeUserResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		removeUserRequest = new RemoveUserRequest();
		removeUserRequest.setUserKey(MULTI_DEVICE_INSD_USERMBR_NO);
		// removeUserRequest.setUserKey("IW1314481420140626192708");
		removeUserRequest.setSecedeReasonMessage("test...");
		removeUserRequest.setCommonRequest(commonRequest);

		LOGGER.debug("### 넘긴 데이터 : {}", removeUserRequest.toString());

		// 요청
		removeUserResponse = this.userSCI.remove(removeUserRequest);
		CommonResponse commonResponse = removeUserResponse.getCommonResponse();

		// 응답
		assertThat(removeUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(removeUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(removeUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(removeUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				removeUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### 받은 데이터 1: {}", removeUserResponse.toString());

		LOGGER.debug("### 받은 데이터 2: {}", commonResponse.toString());

		assertThat(removeUserResponse, notNullValue());
	}

	/**
	 * <pre>
	 * 가입여부 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void checkDuplicationMbrID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_ID);
		keySearch.setKeyString("shop_20017421"); // 상용, 정상상태인 사용자
		keySearch.setKeyString("itachi201402270925@yopmail.com");

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		// 응답 필수 > userID
		assertNotNull(checkDuplicationResponse.getUserID());
		// 응답 필수 > userMbr
		assertNotNull(checkDuplicationResponse.getUserMbr());

		LOGGER.debug("checkDuplicationResponse : {}", checkDuplicationResponse);

	}

	/**
	 * <pre>
	 * 가입여부 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void checkDuplicationInsdUsermbrNo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		keySearch.setKeyString(NORMAL_INSD_USERMBR_NO); // 회원이지만 휴대기기가 없는 UserKey
		// keySearch.setKeyString("US201402051557349950001648");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		// 응답 필수 > userID
		assertNotNull(checkDuplicationResponse.getUserID());
		// 응답 필수 > userMbr
		assertNotNull(checkDuplicationResponse.getUserMbr());

	}

	/**
	 * <pre>
	 * 가입여부 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void checkDuplicationIntgSvcNo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_ONEID_KEY);
		keySearch.setKeyString(NORMAL_INTG_SVC_NO);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		// 응답 필수 > userID
		assertNotNull(checkDuplicationResponse.getUserID());
		// 응답 필수 > userMbr
		assertNotNull(checkDuplicationResponse.getUserMbr());

	}

	/**
	 * <pre>
	 * 가입여부 조회.
	 * SC회원은 아니지만 미동의 테이블에는 존재하는 경우
	 * 통합서비스 관리번호로 조회
	 * </pre>
	 */
	@Test
	public void checkDuplicationTbUsUsermbrOneIDIntgSvcNo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_ONEID_KEY);
		keySearch.setKeyString(NOT_USER_IN_ONEID_TB_INTG_SVC_NO); // 테이블에 테스트로 들어간 통합서비스 관리번호(회원X, OneID테이블O)
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		// 응답 필수 > mbrOneID
		assertNotNull(checkDuplicationResponse.getMbrOneID());
		LOGGER.debug("### getMbrOneID: {}", checkDuplicationResponse.getMbrOneID());

	}

	/**
	 * <pre>
	 * 가입여부 조회.
	 * SC회원은 아니지만 미동의 테이블에는 존재하는 경우
	 * UserID로 조회
	 * </pre>
	 */
	@Test
	public void checkDuplicationTbUsUsermbrOneIDUserID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_ID);
		keySearch.setKeyString(NOT_USER_IN_ONEID_TB_MBR_ID);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		// 응답 필수 > mbrOneID
		assertNotNull(checkDuplicationResponse.getMbrOneID());
		LOGGER.debug("### getMbrOneID: {}", checkDuplicationResponse.getMbrOneID());

	}

	/**
	 * <pre>
	 * 가입여부 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void checkDuplicationUsermbrNo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_IDP_KEY);
		keySearch.setKeyString(NORMAL_USERMBR_NO);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		// 응답 필수 > userID
		assertNotNull(checkDuplicationResponse.getUserID());
		// 응답 필수 > userMbr
		assertNotNull(checkDuplicationResponse.getUserMbr());

	}

	/**
	 * <pre>
	 * 가입여부 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void checkDuplicationInsdDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString(NORMAL_DEVICE_ID);
		keySearch.setKeyString("DE201402061618022440000982");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		// 응답 필수 > userID
		// assertNotNull(checkDuplicationResponse.getUserID());
		// 응답 필수 > userMbr
		// assertNotNull(checkDuplicationResponse.getUserMbr());

		LOGGER.debug("### checkDuplicationResponse : {}", checkDuplicationResponse);
		LOGGER.debug("### getUserMbr : {}", checkDuplicationResponse.getUserMbr());
		LOGGER.debug("### getMbrOneID : {}", checkDuplicationResponse.getMbrOneID());

	}

	/**
	 * <pre>
	 * 가입여부 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void checkDuplicationDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		keySearch.setKeyString(NORMAL_DEVICE_ID);
		keySearch.setKeyString("01020977773");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		// 응답 필수 > userID
		assertNotNull(checkDuplicationResponse.getUserID());
		// 응답 필수 > userMbr
		assertNotNull(checkDuplicationResponse.getUserMbr());

	}

	/**
	 * <pre>
	 * 탈퇴한 사용자로 가입여부 조회를 시도한 경우.
	 * MBR_ID : totokim
	 * 예상 결과
	 * isRegistered : N
	 * userMbr : 없음
	 * mbrOneID : 없음
	 * 가입여부 조회.
	 * </pre>
	 */
	@Test
	public void checkDuplicationRemovedUser() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_ID);
		keySearch.setKeyString("totokim");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		LOGGER.debug("### isRegistered : {}", checkDuplicationResponse.getIsRegistered());
		LOGGER.debug("### userMbr : {}", checkDuplicationResponse.getUserMbr());
		LOGGER.debug("### mbrOneID : {}", checkDuplicationResponse.getMbrOneID());

	}

	/**
	 * <pre>
	 * 이메일 주소로 가입여부 조회.
	 * EMAIL_ADDR : dream00101@yopmail.com
	 * 예상 결과
	 * isRegistered : N
	 * userMbr : 없음
	 * mbrOneID : 없음
	 * 
	 * 기타이메일
	 * fore_20130319@yopmail.com
	 * test201@yopmail.com
	 * nnn@yopmail.com
	 * hahaha@yopmail.com
	 * tatata@yopmail.com
	 * </pre>
	 */
	@Test
	public void checkDuplicationByEmail() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ID/EMAIL 존재여부 확인(SEARCH_TYPE_USER_ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDuplicationResponse checkDuplicationResponse;
		CheckDuplicationRequest checkDuplicationRequest;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("존재하는 사용자ID를 검색한 경우");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_EMAIL);
		keySearch.setKeyString("dream00101@yopmail.com");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDuplicationRequest = new CheckDuplicationRequest();
		checkDuplicationRequest.setCommonRequest(commonRequest);
		checkDuplicationRequest.setKeySearchList(keySearchList);

		// 조회
		checkDuplicationResponse = this.userSCI.checkDuplication(checkDuplicationRequest);

		// 응답
		assertThat(checkDuplicationResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDuplicationResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDuplicationResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDuplicationResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDuplicationResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > isRegistered
		assertThat(checkDuplicationResponse.getIsRegistered(), notNullValue());
		// 응답필수 > isRegistered : Y or N
		assertTrue(checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_Y)
				|| checkDuplicationResponse.getIsRegistered().equalsIgnoreCase(Constant.TYPE_YN_N));

		LOGGER.debug("### isRegistered : {}", checkDuplicationResponse.getIsRegistered());
		LOGGER.debug("### userMbr : {}", checkDuplicationResponse.getUserMbr());
		LOGGER.debug("### mbrOneID : {}", checkDuplicationResponse.getMbrOneID());

	}

	/**
	 * <pre>
	 * 사용자 약관동의 등록/수정
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void updateAgreement() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 약관동의 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateAgreementRequest updateAgreementRequest;
		UpdateAgreementResponse updateRightResponse;
		MbrClauseAgree mbrClauseAgree;
		List<MbrClauseAgree> mbrClauseAgreeList;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003507");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003508");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_N);
		mbrClauseAgreeList.add(mbrClauseAgree);

		mbrClauseAgree = new MbrClauseAgree();
		mbrClauseAgree.setExtraAgreementID("US003510");
		mbrClauseAgree.setIsExtraAgreement(Constant.TYPE_YN_Y);
		mbrClauseAgree.setIsMandatory(Constant.TYPE_YN_Y);
		mbrClauseAgree.setExtraAgreementVersion("0.1");
		mbrClauseAgreeList.add(mbrClauseAgree);

		// 요청 필수
		updateAgreementRequest = new UpdateAgreementRequest();
		updateAgreementRequest.setCommonRequest(commonRequest);
		updateAgreementRequest.setUserKey("IW1425032792720130227164608");
		updateAgreementRequest.setMbrClauseAgreeList(mbrClauseAgreeList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateAgreementRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateAgreementRequest.getMbrClauseAgreeList());

		// 요청
		updateRightResponse = this.userSCI.updateAgreement(updateAgreementRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateRightResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateRightResponse.getCommonResponse());

		// 응답
		assertThat(updateRightResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateRightResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateRightResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateRightResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateRightResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * <pre>
	 * 실명인증 정보 수정.
	 * 내국인여부 수정되는지 확인.
	 * 실명인증 대상 : 본인
	 * </pre>
	 */
	@Test
	public void updateRealNameOwnIsDomestic() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 실명인증 정보 수정-본인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateRealNameRequest updateRealNameRequest;
		UpdateRealNameResponse updateRealNameResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 실명인증 대상 설정 : 본인용
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");
		mbrAuth.setRealNameDate("20141212010110");
		mbrAuth.setIsDomestic("1"); // 에러발생
		mbrAuth.setIsDomestic(" "); // 공백은 성공
		mbrAuth.setIsDomestic(Constant.TYPE_YN_N); // N, Y는 성공
		mbrAuth.setIsDomestic(Constant.TYPE_YN_Y);

		// 요청 필수
		updateRealNameRequest = new UpdateRealNameRequest();
		updateRealNameRequest.setIsOwn(Constant.REAL_NAME_TYPE_OWN);
		updateRealNameRequest.setUserKey("IW1425591006120130725110022");
		updateRealNameRequest.setUserMbrAuth(mbrAuth);
		updateRealNameRequest.setCommonRequest(commonRequest);

		// 수정요청 결과
		updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);

		// 응답
		assertThat(updateRealNameResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateRealNameResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateRealNameResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateRealNameResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				updateRealNameResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * 실명인증 정보를 수정 테스트.
	 * 실명인증 대상 : 본인
	 * </pre>
	 */
	@Test
	public void updateRealNameOwn() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 실명인증 정보 수정-본인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateRealNameRequest updateRealNameRequest;
		UpdateRealNameResponse updateRealNameResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("S01-01002");

		// 실명인증 대상 설정 : 본인용
		MbrAuth mbrAuth = new MbrAuth();
		mbrAuth.setName("이름이름");
		mbrAuth.setCi("cici");
		mbrAuth.setTelecom("US001201");
		mbrAuth.setRealNameDate("20141212010110");

		// 요청 필수
		updateRealNameRequest = new UpdateRealNameRequest();
		updateRealNameRequest.setIsOwn(Constant.REAL_NAME_TYPE_OWN);
		updateRealNameRequest.setUserKey("IW1425591006120130725110022");
		updateRealNameRequest.setUserMbrAuth(mbrAuth);
		updateRealNameRequest.setCommonRequest(commonRequest);

		// 수정요청 결과
		updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);

		// 응답
		assertThat(updateRealNameResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateRealNameResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateRealNameResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateRealNameResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				updateRealNameResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * 실명인증 정보를 수정 테스트.
	 * 실명인증 대상 : 법정대리인
	 * 내국인여부 필드 추가 되는지 확인.
	 * </pre>
	 */
	@Test
	public void updateRealNameParnertIsDomestic() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 실명인증 정보 수정-법정대리인(부모)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateRealNameRequest updateRealNameRequest;
		UpdateRealNameResponse updateRealNameResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 실명인증 대상 설정 : 법정대리인용
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentRealNameMethod("US005301");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setIsDomestic(Constant.TYPE_YN_Y);

		// 요청 필수
		updateRealNameRequest = new UpdateRealNameRequest();
		updateRealNameRequest.setIsOwn(Constant.REAL_NAME_TYPE_PARANT);
		updateRealNameRequest.setUserKey("IW1425591006120130725110022");
		updateRealNameRequest.setCommonRequest(commonRequest);
		updateRealNameRequest.setMbrLglAgent(mbrLglAgent);

		// 요청 결과
		updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);

		// 응답
		assertThat(updateRealNameResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateRealNameResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateRealNameResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateRealNameResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				updateRealNameResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * 실명인증 정보를 수정 테스트
	 * 실명인증 대상 : 법정대리인
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void updateRealNameParnet() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 실명인증 정보 수정-법정대리인(부모)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateRealNameRequest updateRealNameRequest;
		UpdateRealNameResponse updateRealNameResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 실명인증 대상 설정 : 법정대리인용
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		mbrLglAgent.setParentCI("부모ci");
		mbrLglAgent.setParentName("1");
		mbrLglAgent.setParentRealNameMethod("US005301");

		// 요청 필수
		updateRealNameRequest = new UpdateRealNameRequest();
		updateRealNameRequest.setIsOwn(Constant.REAL_NAME_TYPE_PARANT);
		updateRealNameRequest.setUserKey("IW1425591006120130725110022");
		updateRealNameRequest.setCommonRequest(commonRequest);
		updateRealNameRequest.setMbrLglAgent(mbrLglAgent);

		// 요청 결과
		updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);

		// 응답
		assertThat(updateRealNameResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateRealNameResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateRealNameResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateRealNameResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				updateRealNameResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void searchUserUserKey() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		// keySearch.setKeyString("IM142100006017147201303201413");
		// keySearch.setKeyString("IW1023857942220110414141217");
		// keySearch.setKeyString("IF110000000290820120718221834");
		// keySearch.setKeyString("IF102100001520090625170021"); // 회원이지만 휴대기기가 없는 UserKey
		keySearch.setKeyString(NORMAL_INSD_USERMBR_NO);
		keySearch.setKeyString("IW1023469860420110208195136"); // 법정대리인 정보는 존재하나 lgl_agent_auth_yn이 N인 사용자
		keySearch.setKeyString("IW1023744580120120731105635"); // 실명인증 정보는 존재하나 realnm_auth_yn이 N인 사용자
		keySearch.setKeyString("IW1023943854420120326104446"); // 내국인 여부가 N으로 설정된 사용자.
		keySearch.setKeyString("US201402051557349950001648");

		keySearch.setKeyString(NORMAL_INSD_USERMBR_NO);

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		// 응답
		assertThat(searchUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보, searchUserResponse.userMbr : {}", searchUserResponse.getUserMbr());
		LOGGER.debug("사용자 키, searchUserResponse.userKey : {}", searchUserResponse.getUserKey());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : {}", searchUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : {}", searchUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(법정대리인) 사용자키, searchUserResponse.getMbrLglAgent.getMemberKey : {}", searchUserResponse
				.getMbrLglAgent().getMemberKey());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : {}", searchUserResponse.getMbrAuth());

		List<MbrMangItemPtcr> a = searchUserResponse.getMbrMangItemPtcrList();

		LOGGER.debug("부가정보 size  : {}", a.size());
		for (int i = 0; i < a.size(); i++)
			LOGGER.debug("index : {}, 부가정보 : {}", i, a.get(i));

		List<MbrClauseAgree> b = searchUserResponse.getMbrClauseAgreeList();

		LOGGER.debug("약관동의 size  : {}", b.size());
		for (int i = 0; i < b.size(); i++)
			LOGGER.debug("index : {}, 약관동의 : {}", i, b.get(i));

		// 응답 필수
		assertNotNull(searchUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchUserResponse.getUserMbr().getTenantID());

		// 응답 필수 > 징계정보
		assertNotNull(searchUserResponse.getUserMbrPnsh());
		assertNotNull(searchUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchUserResponse.getMbrLglAgent());
		assertNotNull(searchUserResponse.getMbrLglAgent().getIsParent());
		if (searchUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchUserResponse.getMbrAuth());
		assertNotNull(searchUserResponse.getMbrAuth().getIsRealName());
		if (searchUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchUserResponse.getMbrAuth().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}

		LOGGER.debug("전체 단말 Count : {}", searchUserResponse.getTotalDeviceCount());

	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회(존재하지 않는 사용자 키)
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void searchUserUserKeyNon() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		keySearch.setKeyString("존재하지 않는 사용자 키");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과

		try {

			searchUserResponse = this.userSCI.searchUser(searchUserRequest);
			LOGGER.debug("### searchUserResponse {}", searchUserResponse);

		} catch (StorePlatformException e) {

			LOGGER.debug("### e {}", e);
			LOGGER.debug("### e.getMessage() {}", e.getMessage());
			assertSame(e.getMessage(), this.getMessage("response.ResultCode.userKeyNotFound", ""));

		} catch (Exception e) {

			LOGGER.debug("### e {}", e);

		}

	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회(존재하는 사용자  아이디)
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void searchUserUserID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_ID);
		keySearch.setKeyString("ux_oneidtest03");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		// 응답
		assertThat(searchUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보 , searchUserResponse.userMbr : {}", searchUserResponse.getUserMbr());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : {}", searchUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : {}", searchUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : {}", searchUserResponse.getMbrAuth());
		LOGGER.debug("약관동의 : {}", searchUserResponse.getMbrClauseAgreeList());

		// 응답 필수
		assertNotNull(searchUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchUserResponse.getUserMbr().getTenantID());

		// 응답 필수 > 징계정보
		assertNotNull(searchUserResponse.getUserMbrPnsh());
		assertNotNull(searchUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchUserResponse.getMbrLglAgent());
		assertNotNull(searchUserResponse.getMbrLglAgent().getIsParent());
		if (searchUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchUserResponse.getMbrAuth());
		assertNotNull(searchUserResponse.getMbrAuth().getIsRealName());
		if (searchUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchUserResponse.getMbrAuth().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}
	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회(통합서비스 키).
	 * USERMBR_NO : IW1023857942220110414141217(정상상태 체크를 안해서 이전테스트는 성공 현재는 실패 -> 아래로 바꿈)
	 * USERMBR_NO : IW1023663604420110414142459
	 * </pre>
	 */
	@Test
	public void searchUserIdpKey() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_IDP_KEY);
		keySearch.setKeyString(NORMAL_USERMBR_NO);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		// 응답
		assertThat(searchUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보 , searchUserResponse.userMbr : {}", searchUserResponse.getUserMbr());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : {}", searchUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : {}", searchUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : {}", searchUserResponse.getMbrAuth());
		LOGGER.debug("약관동의 : {}", searchUserResponse.getMbrClauseAgreeList());

		// 응답 필수
		assertNotNull(searchUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchUserResponse.getUserMbr().getTenantID());

		// 응답 필수 > 징계정보
		assertNotNull(searchUserResponse.getUserMbrPnsh());
		assertNotNull(searchUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchUserResponse.getMbrLglAgent());
		assertNotNull(searchUserResponse.getMbrLglAgent().getIsParent());
		if (searchUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchUserResponse.getMbrAuth());
		assertNotNull(searchUserResponse.getMbrAuth().getIsRealName());
		if (searchUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}
	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회(OneID 통합 서비스 관리번호).
	 * INTG_SVC_NO : 100000003280(정상상태 체크를 안해서 이전테스트는 성공 현재는 실패 -> 아래로 바꿈)
	 * INTG_SVC_NO : 100000003925
	 * </pre>
	 */
	@Test
	public void searchUserOneIDKey() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_ONEID_KEY);
		keySearch.setKeyString(NORMAL_INTG_SVC_NO);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		// 응답
		assertThat(searchUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보 , searchUserResponse.userMbr : {}", searchUserResponse.getUserMbr());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : {}", searchUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : {}", searchUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : {}", searchUserResponse.getMbrAuth());
		LOGGER.debug("약관동의 : {}", searchUserResponse.getMbrClauseAgreeList());

		// 응답 필수
		assertNotNull(searchUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchUserResponse.getUserMbr().getTenantID());

		// 응답 필수 > 징계정보
		assertNotNull(searchUserResponse.getUserMbrPnsh());
		assertNotNull(searchUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchUserResponse.getMbrLglAgent());
		assertNotNull(searchUserResponse.getMbrLglAgent().getIsParent());
		if (searchUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchUserResponse.getMbrAuth());
		assertNotNull(searchUserResponse.getMbrAuth().getIsRealName());
		if (searchUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}
	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회(Device 내부키)
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void searchUserInsdDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회(Device 내부키)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString(NORMAL_INSD_DEVICE_ID);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		// 응답
		assertThat(searchUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보 , searchUserResponse.userMbr : {}", searchUserResponse.getUserMbr());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : {}", searchUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : {}", searchUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : {}", searchUserResponse.getMbrAuth());
		LOGGER.debug("약관동의 : {}", searchUserResponse.getMbrClauseAgreeList());

		// 응답 필수 > userMbr
		assertNotNull(searchUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchUserResponse.getUserMbr().getTenantID());

		// 응답 필수 > 징계정보
		assertNotNull(searchUserResponse.getUserMbrPnsh());
		assertNotNull(searchUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchUserResponse.getMbrLglAgent());
		assertNotNull(searchUserResponse.getMbrLglAgent().getIsParent());
		if (searchUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchUserResponse.getMbrAuth());
		assertNotNull(searchUserResponse.getMbrAuth().getIsRealName());
		if (searchUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}
	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회(Device 고유ID)
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void searchUserDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회(Device 고유ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		keySearch.setKeyString(NORMAL_DEVICE_ID);
		keySearch.setKeyString("01020977773"); // 강신완 과장님 요청, device_id auth_yn='Y'인 사용자 여러명
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		// 응답
		assertThat(searchUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보 , searchUserResponse.userMbr : {}", searchUserResponse.getUserMbr());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : {}", searchUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : {}", searchUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : {}", searchUserResponse.getMbrAuth());
		LOGGER.debug("약관동의 : {}", searchUserResponse.getMbrClauseAgreeList());
		LOGGER.debug("약관동의 : {}", searchUserResponse);

		// 응답 필수
		assertNotNull(searchUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchUserResponse.getUserMbr().getTenantID());

		// 응답 필수 > 징계정보
		assertNotNull(searchUserResponse.getUserMbrPnsh());
		assertNotNull(searchUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchUserResponse.getMbrLglAgent());
		assertNotNull(searchUserResponse.getMbrLglAgent().getIsParent());
		if (searchUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchUserResponse.getMbrAuth());
		assertNotNull(searchUserResponse.getMbrAuth().getIsRealName());
		if (searchUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}

		LOGGER.debug("전체 단말 Count : {}", searchUserResponse.getTotalDeviceCount());

	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회(Device 고유ID).
	 * 강산완 과장님 요청
	 * //device_id : 01076771470(계속 변경되어 테스트 어려움.)
	 * 01092255726
	 * 동일한 MDN으로 탈퇴 후 재가입시 이전에 탈퇴한 정보를 내려줌.
	 * 메인 상태를 정상으로 설정하지 않아서 발생했던 문제.
	 * </pre>
	 */
	@Test
	public void searchUserDeviceIDRemovedDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회(Device 고유ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		keySearch.setKeyString(NORMAL_INSD_DEVICE_ID);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		// 응답
		assertThat(searchUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보 , searchUserResponse.userMbr : {}", searchUserResponse.getUserMbr());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : {}", searchUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : {}", searchUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : {}", searchUserResponse.getMbrAuth());
		LOGGER.debug("약관동의 : {}", searchUserResponse.getMbrClauseAgreeList());
		LOGGER.debug("약관동의 : {}", searchUserResponse);

		// 응답 필수
		assertNotNull(searchUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchUserResponse.getUserMbr().getTenantID());

		// 응답 필수 > 징계정보
		assertNotNull(searchUserResponse.getUserMbrPnsh());
		assertNotNull(searchUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchUserResponse.getMbrLglAgent());
		assertNotNull(searchUserResponse.getMbrLglAgent().getIsParent());
		if (searchUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchUserResponse.getMbrAuth());
		assertNotNull(searchUserResponse.getMbrAuth().getIsRealName());
		if (searchUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}
	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회(Device 고유ID).
	 * 강산완 과장님 요청(사용자 정보 일부 필드 값 안내려옴)
	 * isParent, isRealName
	 * </pre>
	 */
	@Test
	public void searchUserIsParentIsRealName() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회(Device 고유ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		keySearch.setKeyString(NORMAL_INSD_DEVICE_ID);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		// 응답
		assertThat(searchUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보 , searchUserResponse.userMbr : {}", searchUserResponse.getUserMbr());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : {}", searchUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : {}", searchUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : {}", searchUserResponse.getMbrAuth());
		LOGGER.debug("약관동의 : {}", searchUserResponse.getMbrClauseAgreeList());
		LOGGER.debug("약관동의 : {}", searchUserResponse);

		// 응답 필수
		assertNotNull(searchUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchUserResponse.getUserMbr().getTenantID());
		// 응답 필수 > isRealName
		assertNotNull(searchUserResponse.getUserMbr().getIsRealName());
		LOGGER.debug("isRealName : {}", searchUserResponse.getUserMbr().getIsRealName());
		// 응답 필수 > isParent
		assertNotNull(searchUserResponse.getUserMbr().getIsParent());
		LOGGER.debug("isParent : {}", searchUserResponse.getUserMbr().getIsParent());

		// 응답 필수 > 징계정보
		assertNotNull(searchUserResponse.getUserMbrPnsh());
		assertNotNull(searchUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchUserResponse.getMbrLglAgent());
		assertNotNull(searchUserResponse.getMbrLglAgent().getIsParent());
		if (searchUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchUserResponse.getMbrAuth());
		assertNotNull(searchUserResponse.getMbrAuth().getIsRealName());
		if (searchUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}
	}

	/**
	 * <pre>
	 * 사용자 기본정보 조회.
	 * 징계 정보가 존재하는지 확인.
	 * </pre>
	 */
	@Test
	public void searchUserHasPunishment() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회(Device 고유ID)");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserRequest searchUserRequest;
		SearchUserResponse searchUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		keySearch.setKeyString(HAS_PNSH_INSD_USERMBR_NO);
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchUserRequest = new SearchUserRequest();
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		// 요청 결과
		searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		// 응답
		assertThat(searchUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보 , searchUserResponse.userMbr : {}", searchUserResponse.getUserMbr());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : {}", searchUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : {}", searchUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : {}", searchUserResponse.getMbrAuth());
		LOGGER.debug("약관동의 : {}", searchUserResponse.getMbrClauseAgreeList());
		LOGGER.debug("약관동의 : {}", searchUserResponse);

		// 응답 필수
		assertNotNull(searchUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchUserResponse.getUserMbr().getTenantID());
		// 응답 필수 > isRealName
		assertNotNull(searchUserResponse.getUserMbr().getIsRealName());
		LOGGER.debug("isRealName : {}", searchUserResponse.getUserMbr().getIsRealName());
		// 응답 필수 > isParent
		assertNotNull(searchUserResponse.getUserMbr().getIsParent());
		LOGGER.debug("isParent : {}", searchUserResponse.getUserMbr().getIsParent());

		// 응답 필수 > 징계정보
		assertNotNull(searchUserResponse.getUserMbrPnsh());
		assertNotNull(searchUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchUserResponse.getMbrLglAgent());
		assertNotNull(searchUserResponse.getMbrLglAgent().getIsParent());
		if (searchUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchUserResponse.getMbrAuth());
		assertNotNull(searchUserResponse.getMbrAuth().getIsRealName());
		if (searchUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchUserResponse.getMbrAuth().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}
	}

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 변경.
	 * memberID : shop_4110
	 * oldPW : 1111
	 * memberPW : new_4321
	 * </pre>
	 */
	@Test
	public void updatePasswordUser() {

		UpdatePasswordUserRequest updatePasswordUserRequest = new UpdatePasswordUserRequest();

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		updatePasswordUserRequest.setCommonRequest(commonRequest);

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID("sacagreeuser012405");
		mbrPwd.setOldPW("test111");
		mbrPwd.setOldPW(""); // 이전 비밀번호를 설정하지 않으면 무조건 비밀번호 변경됨.
		mbrPwd.setMemberPW("new_432w1");
		mbrPwd.setPwRegDate("20140206155700");

		updatePasswordUserRequest.setMbrPwd(mbrPwd);

		UpdatePasswordUserResponse updatePasswordUserResponse = this.userSCI
				.updatePasswordUser(updatePasswordUserRequest);

		// 응답
		assertThat(updatePasswordUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(updatePasswordUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updatePasswordUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updatePasswordUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updatePasswordUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userKey
		assertThat(updatePasswordUserResponse.getUserKey(), notNullValue());

		if (updatePasswordUserResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", updatePasswordUserResponse.getCommonResponse().toString());
		if (updatePasswordUserResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", updatePasswordUserResponse.toString());

	}

	/**
	 * <pre>
	 * 비밀번호가 없는 사용자의 비밀번호 변경.
	 * memberID : c-kec9149
	 * oldPW : 
	 * memberPW : new_4321
	 * </pre>
	 */
	@Test
	// 비밀번호가 없는 사용자는 존재하지 않아서 삭제, 2014-03-17
	@Ignore
	public void rupdatePasswordUserNoPassword() {

		UpdatePasswordUserRequest updatePasswordUserRequest = new UpdatePasswordUserRequest();

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		updatePasswordUserRequest.setCommonRequest(commonRequest);

		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID("shop_7842");
		mbrPwd.setOldPW("ZJLx7VbE");
		mbrPwd.setMemberPW("new_432w1");
		mbrPwd.setPwRegDate("20140206155700");

		updatePasswordUserRequest.setMbrPwd(mbrPwd);

		UpdatePasswordUserResponse updatePasswordUserResponse = this.userSCI
				.updatePasswordUser(updatePasswordUserRequest);

		// 응답
		assertThat(updatePasswordUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(updatePasswordUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updatePasswordUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updatePasswordUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updatePasswordUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userKey
		assertThat(updatePasswordUserResponse.getUserKey(), notNullValue());

		if (updatePasswordUserResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", updatePasswordUserResponse.getCommonResponse().toString());
		if (updatePasswordUserResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", updatePasswordUserResponse.toString());

	}

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 초기화. 
	 * 존재하는 사용자에 대해서 패스워드 요청
	 * memberID : shop_4110
	 * </pre>
	 */
	@Test
	public void resetPasswordUser() {

		ResetPasswordUserRequest resetPasswordUserRequest = new ResetPasswordUserRequest();

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		resetPasswordUserRequest.setCommonRequest(commonRequest);
		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID("shop_7842"); // 존재하는 사용자
		resetPasswordUserRequest.setMbrPwd(mbrPwd);

		ResetPasswordUserResponse resetPasswordUserResponse = this.userSCI
				.updateResetPasswordUser(resetPasswordUserRequest);

		// 응답
		assertThat(resetPasswordUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(resetPasswordUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(resetPasswordUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(resetPasswordUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				resetPasswordUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		if (resetPasswordUserResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", resetPasswordUserResponse.getCommonResponse().toString());
		if (resetPasswordUserResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", resetPasswordUserResponse.toString());

		assertThat(resetPasswordUserRequest, notNullValue());
	}

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 초기화. 
	 * 존재하지 않는 사용자에 대해서 패스워드 초기화를 요청한 경우
	 * </pre>
	 */
	@Test
	public void resetPasswordUserNotFoundUser() {

		ResetPasswordUserRequest resetPasswordUserRequest = new ResetPasswordUserRequest();

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		resetPasswordUserRequest.setCommonRequest(commonRequest);
		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID("biz_7908");
		resetPasswordUserRequest.setMbrPwd(mbrPwd);

		ResetPasswordUserResponse resetPasswordUserResponse;

		try {

			resetPasswordUserResponse = this.userSCI.updateResetPasswordUser(resetPasswordUserRequest);
			LOGGER.debug("### resetPasswordUserResponse {}", resetPasswordUserResponse);

		} catch (StorePlatformException e) {

			LOGGER.debug("### e {}", e);
			LOGGER.debug("### e.getMessage() {}", e.getMessage());
			assertSame(e.getMessage(), this.getMessage("response.ResultCode.userKeyNotFound", ""));

		} catch (Exception e) {

			LOGGER.debug("### e {}", e);

		}

	}

	/**
	 * <pre>
	 * 사용자 회원 비밀번호 초기화. 
	 * 패스워드가 없는 경우 패스워드 설정
	 * </pre>
	 */
	@Test
	public void resetPasswordPasswordNotFound() {

		ResetPasswordUserRequest resetPasswordUserRequest = new ResetPasswordUserRequest();

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		resetPasswordUserRequest.setCommonRequest(commonRequest);
		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setMemberID("azsx098"); // 존재하지만 패스워드가 없는 경우
		resetPasswordUserRequest.setMbrPwd(mbrPwd);

		ResetPasswordUserResponse resetPasswordUserResponse = this.userSCI
				.updateResetPasswordUser(resetPasswordUserRequest);

		// 응답
		assertThat(resetPasswordUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(resetPasswordUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(resetPasswordUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(resetPasswordUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				resetPasswordUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		if (resetPasswordUserResponse.getCommonResponse() != null)
			LOGGER.info("### 서버에서 내려받은 값 1: {}", resetPasswordUserResponse.getCommonResponse().toString());
		if (resetPasswordUserResponse != null)
			LOGGER.info("### 서버에서 내려받은 값 2: {}", resetPasswordUserResponse.toString());

		assertThat(resetPasswordUserRequest, notNullValue());
	}

	/**
	 * <pre>
	 * 사용자 부가정보 목록 조회.
	 * </pre>
	 */
	@Test
	public void searchManagementList() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 부가정보 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchManagementListRequest searchManagementListRequest;
		SearchManagementListResponse searchManagementListResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchManagementListRequest = new SearchManagementListRequest();
		searchManagementListRequest.setCommonRequest(commonRequest);
		searchManagementListRequest.setUserKey("IM142100006017147201303201413");

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchManagementListRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchManagementListRequest.getCommonRequest());

		// 요청
		searchManagementListResponse = this.userSCI.searchManagementList(searchManagementListRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchManagementListResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchManagementListResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchManagementListResponse.getMbrMangItemPtcrList());

		// 응답
		assertThat(searchManagementListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchManagementListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchManagementListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchManagementListResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchManagementListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userKey
		assertThat(searchManagementListResponse.getUserKey(), notNullValue());
		// 응답필수 > mbrMangItemPtcrList
		assertThat(searchManagementListResponse.getMbrMangItemPtcrList(), notNullValue());
		// 응답 필수 > mbrMangItemPtcrList(부가정보)
		List<MbrMangItemPtcr> mbrMangItemPtcrList = searchManagementListResponse.getMbrMangItemPtcrList();
		for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
			// 프로파일 키
			assertNotNull(mbrMangItemPtcr.getExtraProfile());
			// 프로파일 값
			assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
		}

	}

	/**
	 * <pre>
	 * 사용자 약관동의 목록 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void searchAgreementList() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 약관동의 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchAgreementListRequest searchAgreementListRequest;
		SearchAgreementListResponse searchAgreementListResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchAgreementListRequest = new SearchAgreementListRequest();
		searchAgreementListRequest.setCommonRequest(commonRequest);
		searchAgreementListRequest.setUserKey("IM142100006017147201303201413");

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchAgreementListRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchAgreementListRequest.getCommonRequest());

		// 요청
		searchAgreementListResponse = this.userSCI.searchAgreementList(searchAgreementListRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchAgreementListResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchAgreementListResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchAgreementListResponse.getMbrClauseAgreeList());

		// 응답
		assertThat(searchAgreementListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchAgreementListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchAgreementListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchAgreementListResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchAgreementListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userKey
		assertThat(searchAgreementListResponse.getUserKey(), notNullValue());
		// 응답필수 > 약관동의 목록
		assertThat(searchAgreementListResponse.getMbrClauseAgreeList(), notNullValue());
		// 응답 필수 > 약관동의 목록
		List<MbrClauseAgree> mbrClauseAgreeList = searchAgreementListResponse.getMbrClauseAgreeList();
		for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
			// 약관동의 ID
			assertNotNull(mbrClauseAgree.getExtraAgreementID());
			// 약관동의여부
			assertNotNull(mbrClauseAgree.getIsExtraAgreement());
		}

	}

	/**
	 * <pre>
	 * 사용자 부가정보 등록/수정.
	 * </pre>
	 */
	@Test
	public void updateManagement() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 부가정보 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateManagementRequest updateManagementRequest;
		UpdateManagementResponse updateManagementResponse;
		List<MbrMangItemPtcr> mbrMangItemPtcrList;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US010906");
		mbrMangItemPtcr.setExtraProfileValue("Y");
		mbrMangItemPtcr.setRegID("testid");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 요청 필수
		updateManagementRequest = new UpdateManagementRequest();
		updateManagementRequest.setCommonRequest(commonRequest);
		updateManagementRequest.setUserKey("US201403211302461930004672");
		updateManagementRequest.setMbrMangItemPtcr(mbrMangItemPtcrList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateManagementRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateManagementRequest.getCommonRequest());

		// 요청
		updateManagementResponse = this.userSCI.updateManagement(updateManagementRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateManagementResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateManagementResponse.getCommonResponse());

		// 응답
		assertThat(updateManagementResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateManagementResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateManagementResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateManagementResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateManagementResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userKey
		assertThat(updateManagementResponse.getUserKey(), notNullValue());

	}

	/**
	 * <pre>
	 * 사용자 부가정보 삭제
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void removeManagement() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 부가정보 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemoveManagementRequest removeManagementRequest;
		RemoveManagementResponse removeManagementResponse;
		List<MbrMangItemPtcr> mbrMangItemPtcrList;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		mbrMangItemPtcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr mbrMangItemPtcr = new MbrMangItemPtcr();
		mbrMangItemPtcr.setExtraProfile("US004803");
		mbrMangItemPtcr.setExtraProfileValue("100006017147");
		mbrMangItemPtcrList.add(mbrMangItemPtcr);

		// 요청 필수
		removeManagementRequest = new RemoveManagementRequest();
		removeManagementRequest.setCommonRequest(commonRequest);
		removeManagementRequest.setUserKey("IM142100006017147201303201413");
		removeManagementRequest.setMbrMangItemPtcr(mbrMangItemPtcrList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", removeManagementRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", removeManagementRequest.getCommonRequest());

		// 요청
		removeManagementResponse = this.userSCI.removeManagement(removeManagementRequest);

		// 응답
		assertThat(removeManagementResponse, notNullValue());
		// 응답 > 공통
		assertThat(removeManagementResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(removeManagementResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(removeManagementResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				removeManagementResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userKey
		assertThat(removeManagementResponse.getUserKey(), notNullValue());

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", removeManagementResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", removeManagementResponse.getCommonResponse());

	}

	/**
	 * <pre>
	 * 사용자 부가정보 목록 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void testsearchPolicyList() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - policy 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchPolicyRequest searchPolicyRequest;
		SearchPolicyResponse searchPolicyResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchPolicyRequest = new SearchPolicyRequest();
		searchPolicyRequest.setCommonRequest(commonRequest);
		searchPolicyRequest.setLimitPolicyKey("010123456");
		List<String> codeList;
		codeList = new ArrayList<String>();
		codeList.add("9");
		codeList.add("4");
		codeList.add("8");
		searchPolicyRequest.setLimitPolicyCodeList(codeList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchPolicyRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchPolicyRequest.getCommonRequest());

		// 요청
		searchPolicyResponse = this.userSCI.searchPolicyList(searchPolicyRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchPolicyResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchPolicyResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchPolicyResponse.getLimitTargetList());
		if (searchPolicyResponse.getLimitTargetList() != null) {
			for (LimitTarget policyCode : searchPolicyResponse.getLimitTargetList()) {
				LOGGER.debug("응답 , policyCode : {}", policyCode.getLimitPolicyCode());
				LOGGER.debug("응답 , policyCode : {}", policyCode.getLimitPolicyKey());
				//LOGGER.debug("응답 , tenantID : {}", policyCode.getTenantID());
			}
		}

		// 응답
		assertThat(searchPolicyResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchPolicyResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchPolicyResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchPolicyResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchPolicyResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > mbrMangItemPtcrList
		assertThat(searchPolicyResponse.getLimitTargetList(), notNullValue());
		// 응답 필수 > mbrMangItemPtcrList(부가정보)
		List<LimitTarget> limitTargetList = searchPolicyResponse.getLimitTargetList();
		for (LimitTarget limitTarget : limitTargetList) {
			// 프로파일 키
			assertNotNull(limitTarget.getLimitTargetNo());
			// 프로파일 값
			assertNotNull(limitTarget.getLimitPolicyKey());
		}
	}

	/**
	 * <pre>
	 * 사용자 부가정보 목록 조회
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void testsearchOCBList() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ocb 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchMemberPointRequest searchMemberPointRequest;
		SearchMemberPointResponse searchMemberPointResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchMemberPointRequest = new SearchMemberPointRequest();
		searchMemberPointRequest.setCommonRequest(commonRequest);
		searchMemberPointRequest.setUserKey("IF1023511101420120615164319");

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchMemberPointRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchMemberPointRequest.getCommonRequest());

		// 요청
		searchMemberPointResponse = this.userSCI.searchMemberPointList(searchMemberPointRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchMemberPointResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchMemberPointResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchMemberPointResponse.getMemberPointList());

		// 응답
		assertThat(searchMemberPointResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchMemberPointResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchMemberPointResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchMemberPointResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchMemberPointResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > mbrMangItemPtcrList
		assertThat(searchMemberPointResponse.getMemberPointList(), notNullValue());
		// 응답 필수 > mbrMangItemPtcrList(부가정보)
		List<MemberPoint> memberPointList = searchMemberPointResponse.getMemberPointList();
		for (MemberPoint memberPoint : memberPointList) {
			// 프로파일 키
			assertNotNull(memberPoint.getAuthMethodCode());
			// 프로파일 값
			assertNotNull(memberPoint.getCardNumber());
		}
	}

	/**
	 * <pre>
	 * 사용자 부가정보 등록/수정
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void testupdatePolicy() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - Policy 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePolicyRequest updatePolicyRequest;
		UpdatePolicyResponse updatePolicyResponse;
		List<LimitTarget> limitTargetList;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		limitTargetList = new ArrayList<LimitTarget>();
		LimitTarget limitTarget = new LimitTarget();
		limitTarget.setLimitPolicyCode("9");
		limitTarget.setLimitPolicyKey("010123456");
		limitTarget.setPolicyApplyValue("IMSI2");
		limitTarget.setLimitAmount("800");
		limitTarget.setRegID("SC회원");
		limitTargetList.add(limitTarget);

		limitTarget = new LimitTarget();
		limitTarget.setLimitPolicyCode("5");
		limitTarget.setLimitPolicyKey("12345");
		limitTarget.setRegID("a");
		// limitTargetList.add(limitTarget);

		// 요청 필수
		updatePolicyRequest = new UpdatePolicyRequest();
		updatePolicyRequest.setCommonRequest(commonRequest);
		updatePolicyRequest.setLimitTargetList(limitTargetList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updatePolicyRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updatePolicyRequest.getCommonRequest());

		// 요청
		updatePolicyResponse = this.userSCI.updatePolicy(updatePolicyRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updatePolicyResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updatePolicyResponse.getCommonResponse());

		// 응답
		assertThat(updatePolicyResponse, notNullValue());
		// 응답 > 공통
		assertThat(updatePolicyResponse.getCommonResponse(), notNullValue());
		assertThat(updatePolicyResponse.getLimitTargetList(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updatePolicyResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updatePolicyResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updatePolicyResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		if (updatePolicyResponse.getLimitTargetList() != null) {
			for (LimitTarget policyCode : updatePolicyResponse.getLimitTargetList()) {
				LOGGER.debug("응답 , policyCode : {}", policyCode.getLimitPolicyCode());
				LOGGER.debug("응답 , policyKey : {}", policyCode.getLimitPolicyKey());
				//LOGGER.debug("응답 , tenantID : {}", policyCode.getTenantID());
			}
		}
	}

	/**
	 * <pre>
	 * 제한대상 수정.
	 * PMT_TYPE, USE_YN, LIMT_AMT, PRE_LIMT_AMT
	 * 중복 업데이트 및 새정책 추가
	 * </pre>
	 */
	@Test
	public void updatePolicy2() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - Policy 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePolicyRequest updatePolicyRequest;
		UpdatePolicyResponse updatePolicyResponse;
		List<LimitTarget> limitTargetList;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		limitTargetList = new ArrayList<LimitTarget>();
		LimitTarget limitTarget = new LimitTarget();
		limitTarget.setLimitPolicyCode("9"); // 9중복.
		limitTarget.setLimitPolicyCode("US011707"); // 8새로운것.
		limitTarget.setLimitPolicyKey("010123456");
		limitTarget.setPolicyApplyValue("IMSI2");
		limitTarget.setRegID("SC회원");

		// PMT_TYPE 추가(2014.02.27)
		limitTarget.setPermissionType("1");

		// USE_YN 추가(2014.02.27)
		limitTarget.setIsUsed("Y");

		// LIMT_AMT 추가(2014.02.27)
		limitTarget.setLimitAmount("300");

		limitTargetList.add(limitTarget);

		limitTarget = new LimitTarget();
		limitTarget.setLimitPolicyCode("5");
		limitTarget.setLimitPolicyKey("12345");
		limitTarget.setRegID("a");
		// limitTargetList.add(limitTarget);

		// 요청 필수
		updatePolicyRequest = new UpdatePolicyRequest();
		updatePolicyRequest.setCommonRequest(commonRequest);
		updatePolicyRequest.setLimitTargetList(limitTargetList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updatePolicyRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updatePolicyRequest.getCommonRequest());

		// 요청
		updatePolicyResponse = this.userSCI.updatePolicy(updatePolicyRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updatePolicyResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updatePolicyResponse.getCommonResponse());

		// 응답
		assertThat(updatePolicyResponse, notNullValue());
		// 응답 > 공통
		assertThat(updatePolicyResponse.getCommonResponse(), notNullValue());
		assertThat(updatePolicyResponse.getLimitTargetList(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updatePolicyResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updatePolicyResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updatePolicyResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		if (updatePolicyResponse.getLimitTargetList() != null) {
			for (LimitTarget policyCode : updatePolicyResponse.getLimitTargetList()) {
				LOGGER.debug("응답 , policyCode : {}", policyCode.getLimitPolicyCode());
				LOGGER.debug("응답 , policyKey : {}", policyCode.getLimitPolicyKey());
				//LOGGER.debug("응답 , tenantID : {}", policyCode.getTenantID());
			}
		}
	}

	/**
	 * <pre>
	 * 사용자 부가정보 등록/수정
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void testupdateOCB() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - OCB 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateMemberPointRequest updateMemberPointRequest;
		UpdateMemberPointResponse updateMemberPointResponse;
		MemberPoint memberPoint;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		memberPoint = new MemberPoint();
		memberPoint.setUserKey("IF1023511101420120615164319");
		memberPoint.setAuthMethodCode("OR003401");
		memberPoint.setStartDate("20130101");
		memberPoint.setIsUsed(Constant.TYPE_YN_Y);
		memberPoint.setRegID("ADMIN");
		memberPoint.setCardNumber("3f7df530b17a0ac38d817d87a5f994855565bae540877207012e46c2eb9af4da");

		// 요청 필수
		updateMemberPointRequest = new UpdateMemberPointRequest();
		updateMemberPointRequest.setCommonRequest(commonRequest);
		updateMemberPointRequest.setMemberPoint(memberPoint);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateMemberPointRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateMemberPointRequest.getCommonRequest());

		// 요청
		updateMemberPointResponse = this.userSCI.updateMemberPoint(updateMemberPointRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateMemberPointResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateMemberPointResponse.getCommonResponse());

		// 응답
		assertThat(updateMemberPointResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateMemberPointResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateMemberPointResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateMemberPointResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateMemberPointResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * <pre>
	 * 사용자 부가정보 삭제
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void testremoveOCB() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ocb 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemoveMemberPointRequest removeMemberPointRequest;
		RemoveMemberPointResponse removeMemberPointResponse;
		List<MemberPoint> memberPointList;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		memberPointList = new ArrayList<MemberPoint>();
		MemberPoint memberPoint = new MemberPoint();
		memberPoint.setUserKey("IF1023511101420120615164319");
		// memberPoint.setAuthMethodCode("OR003401");
		// memberPoint.setIsUsed(Constant.TYPE_YN_N);
		memberPoint.setStartDate("20121112165225");
		memberPoint.setCardNumber("bef57ed35002a93aa146ff6aa07ce7245565bae540877207012e46c2eb9af4db");
		memberPointList.add(memberPoint);

		// 요청 필수
		removeMemberPointRequest = new RemoveMemberPointRequest();
		removeMemberPointRequest.setCommonRequest(commonRequest);
		removeMemberPointRequest.setMemberPointList(memberPointList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", removeMemberPointRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", removeMemberPointRequest.getCommonRequest());

		// 요청
		try {

			removeMemberPointResponse = this.userSCI.removeMemberPoint(removeMemberPointRequest);
			LOGGER.debug("### removeMemberPointResponse {}", removeMemberPointResponse);

		} catch (StorePlatformException e) {

			LOGGER.debug("### e {}", e);
			LOGGER.debug("### e.getMessage() {}", e.getMessage());
			assertSame(e.getMessage(), this.getMessage("response.ResultCode.removeError", ""));

		} catch (Exception e) {

			LOGGER.debug("### e {}", e);

		}

	}

	/**
	 * <pre>
	 * 사용자 부가정보 삭제
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void testremovePolicy() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ocb 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemovePolicyRequest removePolicyRequest;
		RemovePolicyResponse removePolicyResponse;
		List<LimitTarget> limitTargetList;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		limitTargetList = new ArrayList<LimitTarget>();
		LimitTarget limitTarget = new LimitTarget();
		limitTarget.setLimitPolicyCode("4");
		limitTarget.setLimitPolicyKey("53");

		limitTargetList.add(limitTarget);

		// 요청 필수
		removePolicyRequest = new RemovePolicyRequest();
		removePolicyRequest.setCommonRequest(commonRequest);
		removePolicyRequest.setLimitTargetList(limitTargetList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", removePolicyRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", removePolicyRequest.getCommonRequest());

		// 요청
		removePolicyResponse = this.userSCI.removePolicy(removePolicyRequest);

		// 응답
		assertThat(removePolicyResponse, notNullValue());
		// 응답 > 공통
		assertThat(removePolicyResponse.getCommonResponse(), notNullValue());
		assertThat(removePolicyResponse.getLimitPolicyCodeList(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(removePolicyResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(removePolicyResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				removePolicyResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", removePolicyResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", removePolicyResponse.getCommonResponse());
		if (removePolicyResponse.getLimitPolicyCodeList() != null) {
			for (String policyCode : removePolicyResponse.getLimitPolicyCodeList()) {
				LOGGER.debug("응답 , policyCode : {}", policyCode);
			}
		}

	}

	/**
	 * <pre>
	 * 사용자 부가정보 등록/수정
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void updateOneID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - oneid 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateMbrOneIDRequest updateMbrOneIDRequest;
		UpdateMbrOneIDResponse updateMbrOneIDResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		MbrOneID mbrOneID = new MbrOneID();
		// mbrOneID.setUserKey("IF1023511101420120615164319");
		mbrOneID.setLoginStatusCode("10");
		mbrOneID.setIntgSvcNumber("100001111242"); // 없으면 추가 존재하면 수정
		mbrOneID.setIsMemberPoint("Y");
		mbrOneID.setIsCi("N");

		// 요청 필수
		updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
		updateMbrOneIDRequest.setCommonRequest(commonRequest);
		updateMbrOneIDRequest.setMbrOneID(mbrOneID);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateMbrOneIDRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateMbrOneIDRequest.getCommonRequest());

		// 요청
		//FIXME:
		//updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);
		updateMbrOneIDResponse = null;

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateMbrOneIDResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateMbrOneIDResponse.getCommonResponse());

		// 응답
		assertThat(updateMbrOneIDResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateMbrOneIDResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateMbrOneIDResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateMbrOneIDResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateMbrOneIDResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * 미동의 사이트 조회.
	 * </pre>
	 */
	@Test
	public void searchAgreeSite() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 미동의 사이트 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchAgreeSiteRequest searchAgreeSiteRequest;
		SearchAgreeSiteResponse searchAgreeSiteResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchAgreeSiteRequest = new SearchAgreeSiteRequest();
		searchAgreeSiteRequest.setCommonRequest(commonRequest);
		searchAgreeSiteRequest.setImSvcNo("100000101376");

		// 요청
		searchAgreeSiteResponse = this.userSCI.searchAgreeSite(searchAgreeSiteRequest);

		// 응답
		assertThat(searchAgreeSiteResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchAgreeSiteResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchAgreeSiteResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchAgreeSiteResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchAgreeSiteResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### searchAgreeSiteResponse : {}", searchAgreeSiteResponse);
		LOGGER.debug("### mbrOneID : {}", searchAgreeSiteResponse.getMbrOneID());
	}

	/**
	 * <pre>
	 * 사용자키 변환추적 조회.
	 * </pre>
	 */
	@Test
	public void searchUserkeyTrack() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자키 변환추적 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserkeyTrackRequest searchUserkeyTrackRequest;
		SearchUserkeyTrackResponse searchUserkeyTrackResponse;

		// 요청 필수
		searchUserkeyTrackRequest = new SearchUserkeyTrackRequest();
		searchUserkeyTrackRequest.setDeviceID("01049033306");

		// 요청
		searchUserkeyTrackResponse = this.userSCI.searchUserkeyTrack(searchUserkeyTrackRequest);

		// 응답
		assertThat(searchUserkeyTrackResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserkeyTrackResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserkeyTrackResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserkeyTrackResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserkeyTrackResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### searchUserkeyTrackResponse : {}", searchUserkeyTrackResponse);
		LOGGER.debug("### UserkeyTrackD : {}", searchUserkeyTrackResponse.getUserkeyTrack());
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
	 * 사용자회원_ONEID 삭제.
	 * </pre>
	 */
	@Test
	public void removeMbrOneID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자회원_ONEID 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemoveMbrOneIDRequest removeMbrOneIDRequest;
		RemoveMbrOneIDResponse removeMbrOneIDResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		removeMbrOneIDRequest = new RemoveMbrOneIDRequest();
		removeMbrOneIDRequest.setCommonRequest(commonRequest);
		removeMbrOneIDRequest.setImSvcNo("1000011112");

		// 요청
		removeMbrOneIDResponse = this.userSCI.removeMbrOneID(removeMbrOneIDRequest);

		// 응답
		assertThat(removeMbrOneIDResponse, notNullValue());
		// 응답 > 공통
		assertThat(removeMbrOneIDResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(removeMbrOneIDResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(removeMbrOneIDResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				removeMbrOneIDResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("### removeMbrOneIDResponse : {}", removeMbrOneIDResponse);

	}

	/**
	 * <pre>
	 * GameCenter 등록/수정
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void updateUserMbrSegment() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - updateUserMbrSegment 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateUserMbrSegmentRequest updateUserMbrSegmentRequest;
		UpdateUserMbrSegmentResponse updateUserMbrSegmentResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		UserMbrSegment userMbrSegment = new UserMbrSegment();
		userMbrSegment.setSvcMangNum("211122531");
		userMbrSegment.setUserKey("100001111242"); // 없으면 추가 존재하면 수정
		userMbrSegment.setEcgNumber("100001111243");
		userMbrSegment.setDeviceID("010123456"); // 없으면 추가 존재하면 수정

		// 요청 필수
		updateUserMbrSegmentRequest = new UpdateUserMbrSegmentRequest();
		updateUserMbrSegmentRequest.setCommonRequest(commonRequest);
		updateUserMbrSegmentRequest.setUserMbrSegment(userMbrSegment);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateUserMbrSegmentRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateUserMbrSegmentRequest.getCommonRequest());

		// 요청
		updateUserMbrSegmentResponse = this.userSCI.updateUserMbrSegment(updateUserMbrSegmentRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateUserMbrSegmentResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateUserMbrSegmentResponse.getCommonResponse());

		// 응답
		assertThat(updateUserMbrSegmentResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateUserMbrSegmentResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateUserMbrSegmentResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateUserMbrSegmentResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateUserMbrSegmentResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * GameCenter 등록/수정
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void updateNonMbrSegment() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - updateNonMbrSegment 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateNonMbrSegmentRequest updateNonMbrSegmentRequest;
		UpdateNonMbrSegmentResponse updateNonMbrSegmentResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		NonMbrSegment nonMbrSegment = new NonMbrSegment();
		nonMbrSegment.setSvcMangNum("211122531");
		nonMbrSegment.setUserKey("100001111242"); // 없으면 추가 존재하면 수정
		nonMbrSegment.setDeviceID("010123456"); // 없으면 추가 존재하면 수정

		// 요청 필수
		updateNonMbrSegmentRequest = new UpdateNonMbrSegmentRequest();
		updateNonMbrSegmentRequest.setCommonRequest(commonRequest);
		updateNonMbrSegmentRequest.setNonMbrSegment(nonMbrSegment);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateNonMbrSegmentRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updateNonMbrSegmentRequest.getCommonRequest());

		// 요청
		updateNonMbrSegmentResponse = this.userSCI.updateNonMbrSegment(updateNonMbrSegmentRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateNonMbrSegmentResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updateNonMbrSegmentResponse.getCommonResponse());

		// 응답
		assertThat(updateNonMbrSegmentResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateNonMbrSegmentResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateNonMbrSegmentResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateNonMbrSegmentResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateNonMbrSegmentResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * ChangedDevice 조회 성공
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void testsearchChangedDeviceY() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ChangedDevice 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchChangedDeviceRequest searchChangedDeviceRequest;
		SearchChangedDeviceResponse searchChangedDeviceResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchChangedDeviceRequest = new SearchChangedDeviceRequest();
		searchChangedDeviceRequest.setCommonRequest(commonRequest);
		searchChangedDeviceRequest.setUserKey("IW1023078476020100318205733");
		searchChangedDeviceRequest.setDeviceKey("01064468445");
		searchChangedDeviceRequest.setDeviceID("01064468445");

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchChangedDeviceRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchChangedDeviceRequest.getCommonRequest());

		// 요청
		searchChangedDeviceResponse = this.userSCI.searchChangedDevice(searchChangedDeviceRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchChangedDeviceResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchChangedDeviceResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchChangedDeviceResponse.getChangedDeviceLog());

		// 응답
		assertThat(searchChangedDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchChangedDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchChangedDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchChangedDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchChangedDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > mbrMangItemPtcrList
		assertThat(searchChangedDeviceResponse.getChangedDeviceLog(), notNullValue());
	}

	/**
	 * <pre>
	 * ChangedDevice 조회 실패
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void testsearchChangedDeviceN() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ChangedDevice 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchChangedDeviceRequest searchChangedDeviceRequest;
		SearchChangedDeviceResponse searchChangedDeviceResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchChangedDeviceRequest = new SearchChangedDeviceRequest();
		searchChangedDeviceRequest.setCommonRequest(commonRequest);
		searchChangedDeviceRequest.setUserKey("IW1023169573020110701115002");
		searchChangedDeviceRequest.setDeviceID("01023624159");

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchChangedDeviceRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchChangedDeviceRequest.getCommonRequest());

		// 요청
		searchChangedDeviceResponse = this.userSCI.searchChangedDevice(searchChangedDeviceRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchChangedDeviceResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchChangedDeviceResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchChangedDeviceResponse.getChangedDeviceLog());

		// 응답
		assertThat(searchChangedDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchChangedDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchChangedDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchChangedDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchChangedDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > mbrMangItemPtcrList
		assertThat(searchChangedDeviceResponse.getChangedDeviceLog(), notNullValue());
	}

	/**
	 * <pre>
	 * ChangedDevice 조회 실패
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void testsearchChangedDeviceNULL() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - ChangedDevice 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchChangedDeviceRequest searchChangedDeviceRequest;
		SearchChangedDeviceResponse searchChangedDeviceResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchChangedDeviceRequest = new SearchChangedDeviceRequest();
		searchChangedDeviceRequest.setCommonRequest(commonRequest);
		searchChangedDeviceRequest.setUserKey("IW1023078476020100318205733--");
		searchChangedDeviceRequest.setDeviceID("01064468445");

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchChangedDeviceRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchChangedDeviceRequest.getCommonRequest());

		// 요청
		searchChangedDeviceResponse = this.userSCI.searchChangedDevice(searchChangedDeviceRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchChangedDeviceResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchChangedDeviceResponse.getCommonResponse());
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchChangedDeviceResponse.getChangedDeviceLog());

		// 응답
		assertThat(searchChangedDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchChangedDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchChangedDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchChangedDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchChangedDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > mbrMangItemPtcrList
		assertThat(searchChangedDeviceResponse.getChangedDeviceLog(), notNullValue());
	}

	/**
	 * <pre>
	 * 이메일 사용자 정보 조회.
	 * </pre>
	 */
	@Test
	public void searchUserEmail() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 이메일 사용자 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserEmailResponse searchUserEmailResponse;
		SearchUserEmailRequest searchUserEmailRequest;
		CommonRequest commonRequest;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchUserEmailRequest = new SearchUserEmailRequest();
		searchUserEmailRequest.setCommonRequest(commonRequest);
		searchUserEmailRequest.setUserEmail("test@test.com"); // 존재하지 않는 이메일
		searchUserEmailRequest.setUserEmail("shoutjun@naver.com"); // 회원정보가 3개인 사용자(탈퇴 아닌 경우)

		// 조회
		searchUserEmailResponse = this.userSCI.searchUserEmail(searchUserEmailRequest);

		// 응답
		assertThat(searchUserEmailResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserEmailResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserEmailResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserEmailResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserEmailResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		List<UserMbr> userMbrList = searchUserEmailResponse.getUserMbrList();
		if (userMbrList == null || userMbrList.size() <= 0) {
			return;
		}

		for (UserMbr userMbr : userMbrList) {
			assertThat(userMbr.getUserKey(), notNullValue());
			assertThat(userMbr.getUserID(), notNullValue());
		}

	}

	/**
	 * <pre>
	 * 기기변경 정보 등록
	 * </pre>
	 * 
	 * .
	 */
	@Test
	public void createChangedDevice() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 기기변경 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateChangedDeviceRequest createChangedDeviceRequest;
		CreateChangedDeviceResponse createChangedDeviceResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 부가정보
		ChangedDeviceLog changedDeviceLog = new ChangedDeviceLog();
		changedDeviceLog.setUserKey("100001111242"); // 없으면 추가 존재하면 수정
		changedDeviceLog.setDeviceID("010123456"); // 없으면 추가 존재하면 수정
		changedDeviceLog.setMessageIDP("http://www.daume.net");
		changedDeviceLog.setChangeCaseCode("US002101");
		changedDeviceLog.setSvcMangNum("1234664");
		changedDeviceLog.setPreData("none");

		// 요청 필수
		createChangedDeviceRequest = new CreateChangedDeviceRequest();
		createChangedDeviceRequest.setCommonRequest(commonRequest);
		createChangedDeviceRequest.setChangedDeviceLog(changedDeviceLog);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", createChangedDeviceRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", createChangedDeviceRequest.getCommonRequest());

		// 요청
		createChangedDeviceResponse = this.userSCI.createChangedDevice(createChangedDeviceRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", createChangedDeviceResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", createChangedDeviceResponse.getCommonResponse());

		// 응답
		assertThat(createChangedDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createChangedDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createChangedDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createChangedDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				createChangedDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * 단말 OS별 누적 가입자수 조회.
	 * </pre>
	 */
	@Test
	public void searchDeviceOSNumber() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 단말 OS별 누적 가입자수 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceOSNumberResponse searchDeviceOSNumberResponse;
		SearchDeviceOSNumberRequest searchDeviceOSNumberRequest;
		CommonRequest commonRequest;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchDeviceOSNumberRequest = new SearchDeviceOSNumberRequest();
		searchDeviceOSNumberRequest.setCommonRequest(commonRequest);

		// 조회
		searchDeviceOSNumberResponse = this.userSCI.searchDeviceOSNumber(searchDeviceOSNumberRequest);

		// 응답
		assertThat(searchDeviceOSNumberResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceOSNumberResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceOSNumberResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceOSNumberResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceOSNumberResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		Map<String, List<DeviceSystemStats>> deviceSystemStatsMap = searchDeviceOSNumberResponse
				.getDeviceSystemStatsMap();
		if (deviceSystemStatsMap == null || deviceSystemStatsMap.isEmpty()) {
			return;
		}

		Iterator it = deviceSystemStatsMap.values().iterator();
		while (it.hasNext()) {
			List<DeviceSystemStats> deviceSystemStats = (List<DeviceSystemStats>) it.next();
			LOGGER.debug("ModelName : {}", deviceSystemStats.get(0).getModelName());
			for (DeviceSystemStats deviceSystemStatsInMap : deviceSystemStats) {
				LOGGER.debug("Os Version, Entry Count : {}, {}", deviceSystemStatsInMap.getOsVersion(),
						deviceSystemStatsInMap.getEntryCount());

				assertThat(deviceSystemStatsInMap.getTenantID(), notNullValue());
				assertThat(deviceSystemStatsInMap.getRegDate(), notNullValue());
				assertThat(deviceSystemStatsInMap.getModelName(), notNullValue());
				assertThat(deviceSystemStatsInMap.getOsVersion(), notNullValue());
				assertThat(deviceSystemStatsInMap.getEntryCount(), notNullValue());
			}
			LOGGER.debug("ModelName : {}\n", deviceSystemStats.get(0).getModelName());
		}

	}

	/**
	 * <pre>
	 * 복수 사용자 상태정보 조회.
	 * </pre>
	 */
	@Test
	public void searchMbrUser() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 복수 사용자 상태정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchMbrUserRequest searchMbrUserRequest;
		SearchMbrUserResponse searchMbrUserResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // null check

		// 조건 설정
		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add("IW1023857942220110414141217");
		userKeyList.add("IW1023350238820110701120455");
		userKeyList.add("IW1023867899220101223133728");
		userKeyList.add("IW1023284651220101007215215");
		userKeyList.add("IW1023241890420101029171500");

		userKeyList.add("IF1023002708420090928145937"); // 기기가 5개인 사용자
		userKeyList.add("IF1023492984720130319135856"); // 기기가 없는 사용자
		userKeyList.add("US201402071242230500001968"); // 기기가 9개인 사용자

		// 요청 필수
		searchMbrUserRequest = new SearchMbrUserRequest();
		searchMbrUserRequest.setCommonRequest(commonRequest);
		searchMbrUserRequest.setUserKeyList(userKeyList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchMbrUserRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchMbrUserRequest.getCommonRequest());

		// 요청
		searchMbrUserResponse = this.userSCI.searchMbrUser(searchMbrUserRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchMbrUserResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchMbrUserResponse.getCommonResponse());

		// 응답
		assertThat(searchMbrUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchMbrUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchMbrUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchMbrUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchMbrUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		for (String userKey : userKeyList) {
			Map<String, UserMbrStatus> userMbrStatusMap = searchMbrUserResponse.getUserMbrStatusMap();
			UserMbrStatus userMbrStatus = userMbrStatusMap.get(userKey);

			LOGGER.debug("-> userMbrStatus {} : {}", userKey, userMbrStatus);
		}
	}

	/**
	 * <pre>
	 * 사용자 실명인증 조회.
	 * </pre>
	 * 
	 */
	@Test
	public void searchRealName() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 실명인증 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchRealNameRequest searchRealNameRequest;
		SearchRealNameResponse searchRealNameResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchRealNameRequest = new SearchRealNameRequest();
		searchRealNameRequest.setCommonRequest(commonRequest);
		searchRealNameRequest.setUserKey("IW1023943854420120326104446"); // 내국인 여부가 N으로 설정된 사용자.
		searchRealNameRequest.setUserKey("US201401231555153430000447");
		// searchRealNameRequest.setUserKey("IM142100006600755201304050800"); // 상용

		// 요청 결과
		searchRealNameResponse = this.userSCI.searchRealName(searchRealNameRequest);

		// 응답
		assertThat(searchRealNameResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchRealNameResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchRealNameResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchRealNameResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchRealNameResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("searchUserResponse : {}", searchRealNameResponse);
		LOGGER.debug("searchUserResponse.getMbrAuth : {}", searchRealNameResponse.getMbrAuth());

		if (searchRealNameResponse.getMbrAuth() != null) {
			// 응답 필수 > userKey
			assertNotNull(searchRealNameResponse.getUserKey());
			// 응답 필수 > 실명인증(본인)
			assertNotNull(searchRealNameResponse.getMbrAuth());
			assertNotNull(searchRealNameResponse.getMbrAuth().getIsRealName());
			if (searchRealNameResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
				// AUTH_SEQ, sequence
				assertNotNull(searchRealNameResponse.getMbrAuth().getSequence());
			}
		}
	}

	/**
	 * <pre>
	 * 사용자 실명인증 조회.
	 * </pre>
	 * 
	 */
	@Test
	public void searchRealNameWD() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 실명인증 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchRealNameRequest searchRealNameRequest;
		SearchRealNameResponse searchRealNameResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchRealNameRequest = new SearchRealNameRequest();
		searchRealNameRequest.setCommonRequest(commonRequest);
		searchRealNameRequest.setUserKey("IM142100012605952201309111135");

		// 요청 결과
		searchRealNameResponse = this.userSCI.searchRealName(searchRealNameRequest);

		// 응답
		assertThat(searchRealNameResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchRealNameResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchRealNameResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchRealNameResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchRealNameResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("searchUserResponse : {}", searchRealNameResponse);
		LOGGER.debug("searchUserResponse.getMbrAuth : {}", searchRealNameResponse.getMbrAuth());

		if (searchRealNameResponse.getMbrAuth() != null) {
			// 응답 필수 > userKey
			assertNotNull(searchRealNameResponse.getUserKey());
			// 응답 필수 > 실명인증(본인)
			assertNotNull(searchRealNameResponse.getMbrAuth());
			assertNotNull(searchRealNameResponse.getMbrAuth().getIsRealName());
			if (searchRealNameResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
				// AUTH_SEQ, sequence
				assertNotNull(searchRealNameResponse.getMbrAuth().getSequence());
			}
		}
	}

	/**
	 * <pre>
	 * 제한정책 키 수정.
	 * </pre>
	 */
	@Test
	public void updatePolicyKey() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - Policy 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePolicyKeyRequest updatePolicyKeyRequest;
		UpdatePolicyKeyResponse updatePolicyKeyResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		updatePolicyKeyRequest = new UpdatePolicyKeyRequest();
		updatePolicyKeyRequest.setCommonRequest(commonRequest);
		updatePolicyKeyRequest.setNewLimitPolicyKey("010123457");
		updatePolicyKeyRequest.setOldLimitPolicyKey("010123456");

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updatePolicyKeyRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updatePolicyKeyRequest.getCommonRequest());

		// 요청
		updatePolicyKeyResponse = this.userSCI.updatePolicyKey(updatePolicyKeyRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updatePolicyKeyResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updatePolicyKeyResponse.getCommonResponse());

		// 응답
		assertThat(updatePolicyKeyResponse, notNullValue());
		// 응답 > 공통
		assertThat(updatePolicyKeyResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updatePolicyKeyResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updatePolicyKeyResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updatePolicyKeyResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * <pre>
	 * 제한정책 Value 수정.
	 * </pre>
	 */
	@Test
	public void updatePolicyValue() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - Policy value등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdatePolicyValueRequest updatePolicyValueRequest;
		UpdatePolicyValueResponse updatePolicyValueResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		updatePolicyValueRequest = new UpdatePolicyValueRequest();
		updatePolicyValueRequest.setCommonRequest(commonRequest);
		updatePolicyValueRequest.setNewApplyValue("IMSI3");
		updatePolicyValueRequest.setOldApplyValue("IMSI");

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", updatePolicyValueRequest);

		// 요청
		updatePolicyValueResponse = this.userSCI.updatePolicyValue(updatePolicyValueRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updatePolicyValueResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", updatePolicyValueResponse.getCommonResponse());

		// 응답
		assertThat(updatePolicyValueResponse, notNullValue());
		// 응답 > 공통
		assertThat(updatePolicyValueResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updatePolicyValueResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updatePolicyValueResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updatePolicyValueResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * <pre>
	 * 복수 사용자 기기정보 조회.
	 * </pre>
	 */
	@Test
	public void searchMbrDevice() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 복수 사용자 기기정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchMbrDeviceRequest searchMbrDeviceRequest;
		SearchMbrDeviceResponse searchMbrDeviceResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // null check

		// 조건 설정
		List<UserDeviceKey> userDeviceKeyList = new ArrayList<UserDeviceKey>();
		UserDeviceKey userDeviceKey;

		// userDeviceKey = new UserDeviceKey();
		// userDeviceKey.setUserKey("IW1023857942220110414141217");
		// userDeviceKey.setDeviceKey("01031241569");
		// userDeviceKeyList.add(userDeviceKey);
		//
		// userDeviceKey = new UserDeviceKey();
		// userDeviceKey.setUserKey("US201403180943390260004549");
		// userDeviceKey.setDeviceKey("DE201403191817502460003265");
		// userDeviceKeyList.add(userDeviceKey);
		//
		// userDeviceKey = new UserDeviceKey();
		// userDeviceKey.setUserKey("IW1023241890420101029171500");
		// userDeviceKey.setDeviceKey("01033442774");
		// userDeviceKeyList.add(userDeviceKey);

		userDeviceKey = new UserDeviceKey();
		userDeviceKey.setUserKey("IW1023857942220110414141217");
		userDeviceKey.setDeviceKey("01031241569");
		userDeviceKeyList.add(userDeviceKey);
		//
		// userDeviceKey = new UserDeviceKey();
		// userDeviceKey.setUserKey("US201403180943390260004549");
		// userDeviceKey.setDeviceKey("DE201403191918060060003271");
		// userDeviceKeyList.add(userDeviceKey);

		// 요청 필수
		searchMbrDeviceRequest = new SearchMbrDeviceRequest();
		searchMbrDeviceRequest.setCommonRequest(commonRequest);
		searchMbrDeviceRequest.setDeviceKeyList(userDeviceKeyList);

		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchMbrDeviceRequest);
		LOGGER.debug("### 테스트에서 넘긴 데이터 : {}", searchMbrDeviceRequest.getCommonRequest());

		// 요청
		searchMbrDeviceResponse = this.userSCI.searchMbrDevice(searchMbrDeviceRequest);

		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchMbrDeviceResponse);
		LOGGER.debug("### 컨트롤러로부터 받은 데이터 : {}", searchMbrDeviceResponse.getCommonResponse());

		// 응답
		assertThat(searchMbrDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchMbrDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchMbrDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchMbrDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchMbrDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		for (UserDeviceKey deviceKey : userDeviceKeyList) {
			Map<String, DeviceMbrStatus> deviceMbrStatusMap = searchMbrDeviceResponse.getDeviceMbrStatusMap();
			DeviceMbrStatus userMbrStatus = deviceMbrStatusMap.get(deviceKey.getDeviceKey());

			LOGGER.debug("### userMbrStatus {} : {}", deviceKey, userMbrStatus);
		}
	}

	/**
	 * <pre>
	 * 조건별 사용자정보 조회.
	 * </pre>
	 */
	@Test
	public void searchExtentUser() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchExtentUserRequest searchExtentUserRequest;
		SearchExtentUserResponse searchExtentUserResponse;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		// keySearch.setKeyString("IM142100006017147201303201413");
		// keySearch.setKeyString("IW1023857942220110414141217");
		// keySearch.setKeyString("IF110000000290820120718221834");
		// keySearch.setKeyString("IF102100001520090625170021"); // 회원이지만 휴대기기가 없는 UserKey
		keySearch.setKeyString(NORMAL_INSD_USERMBR_NO);
		keySearch.setKeyString("IW1023469860420110208195136"); // 법정대리인 정보는 존재하나 lgl_agent_auth_yn이 N인 사용자
		keySearch.setKeyString("IW1023744580120120731105635"); // 실명인증 정보는 존재하나 realnm_auth_yn이 N인 사용자
		keySearch.setKeyString("IW1023943854420120326104446"); // 내국인 여부가 N으로 설정된 사용자.

		keySearch.setKeyString(NORMAL_INSD_USERMBR_NO);
		keySearch.setKeyString("IM190000008406220140408181722");

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchExtentUserRequest = new SearchExtentUserRequest();
		searchExtentUserRequest.setKeySearchList(keySearchList);
		searchExtentUserRequest.setCommonRequest(commonRequest);

		// 조회 조건
		searchExtentUserRequest.setUserInfoYn("Y");
		searchExtentUserRequest.setAgreementInfoYn("Y");
		searchExtentUserRequest.setMbrAuthInfoYn("Y");
		searchExtentUserRequest.setMbrLglAgentInfoYn("Y");
		searchExtentUserRequest.setMbrPnshInfoYn("Y");
		searchExtentUserRequest.setGradeInfoYn("Y");

		// 요청 결과
		searchExtentUserResponse = this.userSCI.searchExtentUser(searchExtentUserRequest);

		// 응답
		assertThat(searchExtentUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchExtentUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchExtentUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchExtentUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchExtentUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 정보, searchUserResponse.userMbr : \n{}", searchExtentUserResponse.getUserMbr());
		LOGGER.debug("사용자 키, searchUserResponse.userKey : \n{}", searchExtentUserResponse.getUserKey());
		LOGGER.debug("징계 정보, searchUserResponse.getUserMbrPnsh : \n{}", searchExtentUserResponse.getUserMbrPnsh());
		LOGGER.debug("실명인증(법정대리인), searchUserResponse.getMbrLglAgent : \n{}", searchExtentUserResponse.getMbrLglAgent());
		LOGGER.debug("실명인증(법정대리인) 사용자키, searchUserResponse.getMbrLglAgent.getMemberKey : \n{}",
				searchExtentUserResponse.getMbrLglAgent().getMemberKey());
		LOGGER.debug("실명인증(본인), searchUserResponse.getMbrAuth : \n{}", searchExtentUserResponse.getMbrAuth());
		LOGGER.debug("사용자 등급정보, searchExtentUserResponse.getGrade() : \n{}", searchExtentUserResponse.getGrade());

		List<MbrMangItemPtcr> a = searchExtentUserResponse.getMbrMangItemPtcrList();

		LOGGER.debug("부가정보 size  : \n{}", a.size());
		for (int i = 0; i < a.size(); i++)
			LOGGER.debug("index : {}, 부가정보 : {}", i, a.get(i));

		List<MbrClauseAgree> b = searchExtentUserResponse.getMbrClauseAgreeList();

		LOGGER.debug("약관동의 size  : \n{}", b.size());
		for (int i = 0; i < b.size(); i++)
			LOGGER.debug("index : {}, 약관동의 : {}", i, b.get(i));

		// 응답 필수
		assertNotNull(searchExtentUserResponse.getUserMbr());
		// 응답 필수 > userKey
		assertNotNull(searchExtentUserResponse.getUserMbr().getUserKey());
		// 응답 필수 > tenantID
		assertNotNull(searchExtentUserResponse.getUserMbr().getTenantID());

		// 응답 필수 > 징계정보
		assertNotNull(searchExtentUserResponse.getUserMbrPnsh());
		assertNotNull(searchExtentUserResponse.getUserMbrPnsh().getIsRestricted());
		if (searchExtentUserResponse.getUserMbrPnsh().getIsRestricted().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// userKey
			assertNotNull(searchExtentUserResponse.getUserMbrPnsh().getUserKey());
			// tenantID
			assertNotNull(searchExtentUserResponse.getUserMbrPnsh().getTenantID());
			// 징계시작일
			assertNotNull(searchExtentUserResponse.getUserMbrPnsh().getRestrictStartDate());
			// 징계종료일
			assertNotNull(searchExtentUserResponse.getUserMbrPnsh().getRestrictEndDate());
		}

		// 응답 필수 > 실명인증(법정대리인)
		assertNotNull(searchExtentUserResponse.getMbrLglAgent());
		assertNotNull(searchExtentUserResponse.getMbrLglAgent().getIsParent());
		if (searchExtentUserResponse.getMbrLglAgent().getIsParent().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// sequence, 순번
			assertNotNull(searchExtentUserResponse.getMbrLglAgent().getSequence());
			// parentRealNameMethod, 법정대리인 인증방법코드
			assertNotNull(searchExtentUserResponse.getMbrLglAgent().getParentRealNameMethod());
			// parentRealNameDate, REG_DT, 입력 날짜
			assertNotNull(searchExtentUserResponse.getMbrLglAgent().getParentRealNameDate());
		}

		// 응답 필수 > 실명인증(본인)
		assertNotNull(searchExtentUserResponse.getMbrAuth());
		assertNotNull(searchExtentUserResponse.getMbrAuth().getIsRealName());
		if (searchExtentUserResponse.getMbrAuth().getIsRealName().equalsIgnoreCase(Constant.TYPE_YN_Y)) {
			// AUTH_SEQ, sequence
			assertNotNull(searchExtentUserResponse.getMbrAuth().getSequence());
		}

		// 응답 필수 > 사용자 관리항목
		if (searchExtentUserResponse.getMbrMangItemPtcrList() != null) {
			List<MbrMangItemPtcr> mbrMangItemPtcrList = searchExtentUserResponse.getMbrMangItemPtcrList();
			for (MbrMangItemPtcr mbrMangItemPtcr : mbrMangItemPtcrList) {
				// 프로파일 키
				assertNotNull(mbrMangItemPtcr.getExtraProfile());
				// 프로파일 값
				assertNotNull(mbrMangItemPtcr.getExtraProfileValue());
			}
		}

		// 응답 필수 > 약관동의
		if (searchExtentUserResponse.getMbrClauseAgreeList() != null) {
			List<MbrClauseAgree> mbrClauseAgreeList = searchExtentUserResponse.getMbrClauseAgreeList();
			for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
				// 약관동의 ID
				assertNotNull(mbrClauseAgree.getExtraAgreementID());
				// 약관동의여부
				assertNotNull(mbrClauseAgree.getIsExtraAgreement());
			}
		}

		// 응답 필수 > 사용자 회원 등급
		assertNotNull(searchExtentUserResponse.getGrade());
		assertNotNull(searchExtentUserResponse.getGrade().getUserGradeCd());

		LOGGER.debug("전체 단말 Count : {}", searchExtentUserResponse.getTotalDeviceCount());

	}

	/**
	 * <pre>
	 * 회원 segment 정보 조회.
	 * </pre>
	 */
	@Test
	public void searchUserSegment() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 사용자 기본정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchUserSegmentRequest searchUserSegmentRequest;
		SearchUserSegmentResponse searchUserSegmentResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchUserSegmentRequest = new SearchUserSegmentRequest();
		searchUserSegmentRequest.setCommonRequest(commonRequest);

		searchUserSegmentRequest.setUserKey("IM120000055047520140418151040");
		searchUserSegmentRequest.setDeviceKey("DE201404181510510015040242");

		// 요청 결과
		searchUserSegmentResponse = this.userSCI.searchUserSegment(searchUserSegmentRequest);

		// 응답
		assertThat(searchUserSegmentResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchUserSegmentResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchUserSegmentResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchUserSegmentResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchUserSegmentResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.debug("사용자 키, searchUserSegmentResponse.userKey : \n{}", searchUserSegmentResponse.getUserKey());
		LOGGER.debug("사용자 회원등급, searchUserSegmentResponse.getUserMbrPnsh : \n{}",
				searchUserSegmentResponse.getUserGradeCd());
		LOGGER.debug("사용자 생년월일, searchUserSegmentResponse.getMbrLglAgent : \n{}",
				searchUserSegmentResponse.getUserBirthDay());
		LOGGER.debug("사용자 가입일, searchUserSegmentResponse.getMbrLglAgent.getMemberKey : \n{}",
				searchUserSegmentResponse.getEntryDay());
		LOGGER.debug("사용자 성별, searchUserSegmentResponse.getMbrAuth : \n{}", searchUserSegmentResponse.getUserSex());

		// 응답 필수 > userKey
		assertNotNull(searchUserSegmentResponse.getUserKey());
		// 응답 필수 > userGradeCd
		assertNotNull(searchUserSegmentResponse.getUserGradeCd());
		// 응답 필수 > userBirthDay
		assertNotNull(searchUserSegmentResponse.getUserBirthDay());
		// 응답 필수 > entryDay
		assertNotNull(searchUserSegmentResponse.getEntryDay());
		// 응답 필수 > isChanged
//		assertNotNull(searchUserSegmentResponse.getIsChanged());

		// 응답 필수 > 사용자 회원 등급
		// assertNotNull(searchExtentUserResponse.getGrade());
		// assertNotNull(searchExtentUserResponse.getGrade().getUserGradeCd());

	}

	/**
	 * <pre>
	 * 회원 가입여부 리스트 조회.
	 * </pre>
	 */
	@Test
	public void existList() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 회원 가입여부 리스트 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		ExistListRequest existListRequest;
		ExistListResponse existListResponse;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		existListRequest = new ExistListRequest();
		existListRequest.setCommonRequest(commonRequest);

		List<String> list = new ArrayList<String>();
		list.add("01088870008");
		list.add("01088870006");
		list.add("01088870005");
		list.add("01088870004");
		list.add("01088870003");
		list.add("01088870002");
		list.add("01088870001");
		list.add("01088870018");
		list.add("01088870028");
		list.add("01088870038");
		existListRequest.setDeviceIdList(list);

		// 요청 결과
		existListResponse = this.userSCI.existList(existListRequest);

		// 응답
		assertThat(existListResponse, notNullValue());
		// 응답 > 공통
		assertThat(existListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(existListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(existListResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				existListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
		LOGGER.debug("요청 사이즈 : {}", existListRequest.getDeviceIdList().size());
		LOGGER.debug("응답 사이즈 : {}", existListResponse.getDeviceIdList().size());
		for (UserMbrDevice userMbrDevice : existListResponse.getDeviceIdList()) {
			LOGGER.debug("deivceId : {}, userKey : {}", userMbrDevice.getDeviceID(), userMbrDevice.getUserKey());
		}

	}
}
