/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.seller.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.seller.sci.vo.BatCmsSellerMbr;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.Document;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginInfo;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.PWReminder;
import com.skplanet.storeplatform.member.client.seller.sci.vo.PWReminderAll;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.ResetPasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.ResetPasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAgreementListSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchAgreementListSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchIDSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchProviderRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchProviderResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListAllRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListAllResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchPwdHintListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerListResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerUpgrade;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAccountSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAgreementSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateAgreementSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateLoginInfoResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateNewPasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateNewPasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdatePasswordSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateRealNameSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpgradeSellerResponse;
import com.skplanet.storeplatform.member.common.code.MainStateCode;
import com.skplanet.storeplatform.member.common.code.SellerCategoryCode;
import com.skplanet.storeplatform.member.common.code.SellerChangeCode;
import com.skplanet.storeplatform.member.common.code.SellerClassCode;
import com.skplanet.storeplatform.member.common.code.SubStateCode;
import com.skplanet.storeplatform.member.common.crypto.SHACipher;
import com.skplanet.storeplatform.member.common.vo.ExistLimitWordMemberID;
import com.skplanet.storeplatform.member.seller.vo.SellerMbrRetrievePWD;

/**
 * 판매자 기능 implementation.
 * 
 * Updated on : 2015. 11. 27. Updated by : 최진호, 보고지티.
 */
@Service
public class SellerServiceImpl implements SellerService {
	/*
	 * Logger 는 반드시 "private static final" 로 선언한다.
	 */
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SellerServiceImpl.class);
	/*
	 * 데이터 베이스에서 데이터를 조회할 별도 DAO 객체를 생성하지 않고, CommonDAO 를 사용한다.
	 */
	/** The common dao. */
	@Autowired
	@Qualifier("scMember")
	private CommonDAO commonDAO;

	/** The message source accessor. */
	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * <pre>
	 * 판매자회원 가입을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createSellerRequest
	 *            판매자회원 가입 요청 Value Object
	 * @return createSellerResponse - 판매자회원 가입 응답 Value Object
	 */
	@Override
	public CreateSellerResponse createSeller(CreateSellerRequest createSellerRequest) {
		// TO DO.
		// ID 있나없나 확인.
		// ACTION 1. 회원 정보 insert.
		CreateSellerResponse createSellerResponse = new CreateSellerResponse();
		Integer row = 0;
		String tempKey = "";

		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setTenantID(createSellerRequest.getCommonRequest().getTenantID());
		sellerMbr.setSellerID(createSellerRequest.getSellerMbr().getSellerID());

		// import com.omp.batch.commons.util.CipherAES;
		// wd 테이블에서도 검색해본다.
		SellerMbr checkSellerMbrWd = (SellerMbr) this.commonDAO.queryForObject("Seller.checkSellerMbrWd", sellerMbr);

		if (checkSellerMbrWd != null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.duplicatedMemberID", ""));
			// TB_US_SELLERMBR_WD 6개월이내 중복

		}

		ExistLimitWordMemberID existLimitWordMemberID = this.commonDAO.queryForObject("Seller.checkExistLimitWord",
				sellerMbr, ExistLimitWordMemberID.class);
		LOGGER.debug("### existLimitWordMemberID : {}", existLimitWordMemberID);

		// ACTION 1-1. userID가 존재하는 경우 Fail 반환
		if (existLimitWordMemberID != null && existLimitWordMemberID.getExistWordID() != null
				&& existLimitWordMemberID.getExistWordID().length() > 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.duplicatedMemberID", ""));
		} else
		// ACTION 1-2. userID가 제한된 단어인 경우 Fail 반환
		if (existLimitWordMemberID != null && existLimitWordMemberID.getLimitWordID() != null
				&& existLimitWordMemberID.getLimitWordID().length() > 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.restrictedWordMemberID", ""));
		} else {
			// 내부 사용자키 셋팅
			int seq = this.commonDAO.queryForObject("Seller.getSellerSequence", null, Integer.class);
			tempKey = "SE" + Utils.getLocalDateTime() + Utils.leftPadStringWithChar(Integer.toString(seq), 7, '0');

			sellerMbr = null;
			sellerMbr = createSellerRequest.getSellerMbr();
			sellerMbr.setSellerKey(tempKey); // INSD_SELLERMBR_NO,
			// sellerMbr.setRegDate(Utils.getLocalDateTimeinYYYYMMDD()); // ENTRY_DAY <<- 왜 SYSDATE 안했지?

			if (createSellerRequest.getMbrAuth() != null) {
				sellerMbr.setIsRealName(createSellerRequest.getMbrAuth().getIsRealName());
			}

			row = (Integer) this.commonDAO.insert("Seller.createSeller", sellerMbr);
			LOGGER.debug("### row : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			// password
			MbrPwd mbrPwd = new MbrPwd();
			mbrPwd.setMemberKey(tempKey);
			mbrPwd.setEncType("S");

			SHACipher cipher = new SHACipher();

			try {
				mbrPwd.setMemberPW(cipher.getHash(createSellerRequest.getMbrPwd().getMemberPW()));

			} catch (NoSuchAlgorithmException ex) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
			} catch (UnsupportedEncodingException ex) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
			} catch (StorePlatformException ex) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			row = (Integer) this.commonDAO.insert("Seller.insertPassword", mbrPwd);
			LOGGER.debug("### row 0: {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			// check 보안질문
			if (createSellerRequest.getPWReminderList() != null) {
				List<PWReminder> pWReminderList = createSellerRequest.getPWReminderList();
				for (int i = 0; i < pWReminderList.size(); i++) {
					PWReminder pWReminder = pWReminderList.get(i);
					pWReminder.setSellerKey(tempKey);
					row = this.commonDAO.update("Seller.insertPWReminder", pWReminder);
					LOGGER.debug("###  1 row : {}", row);
					if (row == 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
				}
			}

			// check 실명인증
			if (createSellerRequest.getMbrAuth() != null) {
				MbrAuth mbrAuth = null;
				mbrAuth = createSellerRequest.getMbrAuth();
				mbrAuth.setMemberKey(tempKey);
				row = (Integer) this.commonDAO.insert("Seller.updateOwnRealName", mbrAuth);
				LOGGER.debug("### 2  row : {}", row);
				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}
			//
			// // check 이용약관
			// if (createSellerRequest.getMbrClauseAgreeList() != null) {
			// List<MbrClauseAgree> mbrClauseAgreeList = createSellerRequest.getMbrClauseAgreeList();
			// for (int i = 0; i < mbrClauseAgreeList.size(); i++) {
			// MbrClauseAgree mbrClauseAgree = mbrClauseAgreeList.get(i);
			// mbrClauseAgree.setMemberKey(tempKey);
			// row = this.commonDAO.update("Seller.updateAgreement", mbrClauseAgree);
			// LOGGER.debug("###  3 row : {}", row);
			// if (row == 0) {
			// throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			// }
			// }
			// }

			// 계정승인시 CMS 테이블 INSERT 로 변경 - 2014-07-04
			// 가입이 성공하면 cms 테이블에 insert 한다.
			// BatCmsSellerMbr batCmsSellerMbr = new BatCmsSellerMbr();
			// batCmsSellerMbr.setSellerKey(tempKey);
			// batCmsSellerMbr.setResultCode("PD003701");
			// batCmsSellerMbr.setStatusType("INSERT");
			//
			// row = this.commonDAO.update("Seller.insertBatCmsSellerMbr", batCmsSellerMbr);
			// LOGGER.debug("### 2  row : {}", row);
			// if (row == 0) {
			// throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			// }

		}

		// ACTION 2
		createSellerResponse.setSellerKey(tempKey);
		createSellerResponse.setSellerID(createSellerRequest.getSellerMbr().getSellerID());
		createSellerResponse.setSellerMainStatus(createSellerRequest.getSellerMbr().getSellerMainStatus());
		createSellerResponse.setSellerSubStatus(createSellerRequest.getSellerMbr().getSellerSubStatus());
		createSellerResponse.setLoginStatusCode(createSellerRequest.getSellerMbr().getLoginStatusCode());
		createSellerResponse.setStopStatusCode(createSellerRequest.getSellerMbr().getStopStatusCode());

		return createSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자회원 로그인을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param loginSellerRequest
	 *            판매자회원 로그인 요청 Value Object
	 * @return loginSellerResponse - 판매자회원 로그인 응답 Value Object
	 */
	@Override
	public LoginSellerResponse updateLoginSeller(LoginSellerRequest loginSellerRequest) {

		// TO DO.
		// ACTION 1. ID가 없는 경우, FAIL 처리
		// ACTION 2. login Success : 회원 PW정보 update.
		// ACTION 3. login Fail : 회원 PW정보 update.

		LoginSellerResponse loginSellerResponse = new LoginSellerResponse();
		Integer row = 0;

		SellerMbrRetrievePWD sellerMbrRetrievePWD = new SellerMbrRetrievePWD();
		sellerMbrRetrievePWD.setSellerID(loginSellerRequest.getSellerID());
		sellerMbrRetrievePWD.setSellerPW(loginSellerRequest.getSellerPW());

		// 서브계정인지 id 가 있는지 확인한다. ( 서브인경우 부모의 상태값으로 로그인 처리되어야한다. )
		String isRegisteredParent = null;
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerID(loginSellerRequest.getSellerID());
		isRegisteredParent = this.commonDAO.queryForObject("Seller.isRegisteredParent", sellerMbr, String.class);

		if (isRegisteredParent != null) {
			// 부모키로 조회해서 상태값을 가지고오고 , 또한 정상인 것을 조회해온다.
			// 그리고 패스워드 정보는 서브 계정 키로 조회해서 정보를 가지고온다.
			sellerMbrRetrievePWD.setParentSellerKey(isRegisteredParent);
			sellerMbrRetrievePWD = this.commonDAO.queryForObject("Seller.getSellerMbrRetrieveParentPWD",
					sellerMbrRetrievePWD, SellerMbrRetrievePWD.class);
		} else {
			sellerMbrRetrievePWD = this.commonDAO.queryForObject("Seller.getSellerMbrRetrievePWD",
					sellerMbrRetrievePWD, SellerMbrRetrievePWD.class);
		}
		// ACTION 1. ID가 없는 경우, FAIL 처리
		if (sellerMbrRetrievePWD == null) {
			loginSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.unknownMemberID",
					"response.ResultMessage.unknownMemberID"));
			loginSellerResponse.setLoginFailCount(0);
			return loginSellerResponse;
		}

		// ID가 있는 경우 내부 판매자 키 설정
		MbrPwd mbrPwd = new MbrPwd();
		mbrPwd.setTenantID(loginSellerRequest.getCommonRequest().getTenantID());

		mbrPwd.setMemberID(loginSellerRequest.getSellerID());
		mbrPwd.setMemberPW(loginSellerRequest.getSellerPW());

		SHACipher cipher = new SHACipher();

		LOGGER.debug(">>>> >>> SellerServiceImpl login : {}", mbrPwd);

		boolean loginSuccess;
		try {
			loginSuccess = cipher.compare(loginSellerRequest.getSellerPW(), sellerMbrRetrievePWD.getPwd());
			LOGGER.debug(">>>> >>> SellerServiceImpl login   pws1: {}", loginSellerRequest.getSellerPW());
			LOGGER.debug(">>>> >>> SellerServiceImpl login cipher.getHash   pws1: {}",
					cipher.getHash(loginSellerRequest.getSellerPW()));
			LOGGER.debug(">>>> >>> SellerServiceImpl login   getMD5: {}",
					cipher.getMD5(loginSellerRequest.getSellerPW()));
			LOGGER.debug(">>>> >>> SellerServiceImpl login  db getEncType: {}", sellerMbrRetrievePWD.getEncType());
			LOGGER.debug(">>>> >>> SellerServiceImpl login db pwd: {}", sellerMbrRetrievePWD.getPwd());

			if ("M".equals(sellerMbrRetrievePWD.getEncType())) {
				// 서브 판매자
				loginSuccess = sellerMbrRetrievePWD.getPwd().equals(cipher.getMD5(loginSellerRequest.getSellerPW()));
			} else {
				// 일반 판매자.
				loginSuccess = cipher.compare(loginSellerRequest.getSellerPW(), sellerMbrRetrievePWD.getPwd());
			}

		} catch (NoSuchAlgorithmException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.fail", ""), ex);
		} catch (UnsupportedEncodingException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.fail", ""), ex);
		} catch (IOException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.fail", ""), ex);
		} catch (StorePlatformException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.fail", ""));
		}

		// if (!sellerMbrRetrievePWD.getLoginStatusCode().equals(Constant.LOGIN_STATUS_CODE) // 상태가 정상이 아닐 경우 US010201

		// (id / pass 먼저 확인

		if (loginSuccess) {

			// 가가입 상태 및 정상이 아니라면
			if (!sellerMbrRetrievePWD.getStopStatusCode().equals(Constant.STOP_STATUS_CODE)
					|| !sellerMbrRetrievePWD.getSellerMainStatus().equals(MainStateCode.NORMAL.getCode())) {

				loginSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.fail",
						"response.ResultMessage.fail"));
				loginSellerResponse.setLoginFailCount(sellerMbrRetrievePWD.getFailCnt());
				loginSellerResponse.setIsLoginSuccess(Constant.TYPE_YN_Y);
				loginSellerResponse.setSellerKey(sellerMbrRetrievePWD.getSellerKey());
				loginSellerResponse.setSellerID(sellerMbrRetrievePWD.getSellerID());
				loginSellerResponse.setSellerClass(sellerMbrRetrievePWD.getSellerClass());
				loginSellerResponse.setSellerCategory(sellerMbrRetrievePWD.getSellerCategory());
				loginSellerResponse.setSellerMainStatus(sellerMbrRetrievePWD.getSellerMainStatus());
				loginSellerResponse.setSellerSubStatus(sellerMbrRetrievePWD.getSellerSubStatus());
				loginSellerResponse.setLoginStatusCode(sellerMbrRetrievePWD.getLoginStatusCode());
				loginSellerResponse.setStopStatusCode(sellerMbrRetrievePWD.getStopStatusCode());

			} else { // 정상적인 로그인인경우

				loginSellerResponse.setSellerKey(sellerMbrRetrievePWD.getSellerKey());
				loginSellerResponse.setSubSellerKey(sellerMbrRetrievePWD.getSubSellerKey());
				loginSellerResponse.setSellerClass(sellerMbrRetrievePWD.getSellerClass());
				loginSellerResponse.setSellerCategory(sellerMbrRetrievePWD.getSellerCategory());
				loginSellerResponse.setSellerMainStatus(sellerMbrRetrievePWD.getSellerMainStatus());
				loginSellerResponse.setSellerSubStatus(sellerMbrRetrievePWD.getSellerSubStatus());
				loginSellerResponse.setLoginStatusCode(sellerMbrRetrievePWD.getLoginStatusCode());
				loginSellerResponse.setStopStatusCode(sellerMbrRetrievePWD.getStopStatusCode());
				loginSellerResponse.setSellerID(sellerMbrRetrievePWD.getSellerID());
				loginSellerResponse.setLoginFailCount(sellerMbrRetrievePWD.getFailCnt());
				loginSellerResponse.setIsLoginSuccess(Constant.TYPE_YN_Y);
				LOGGER.debug("### updateLoginSuccess row : {}", row);

			}

			// 서브계정인경우 부모로 조회해서 리턴해야한다.
			if (isRegisteredParent != null) {
				loginSellerResponse.setIsSubSeller(Constant.TYPE_YN_Y);
				mbrPwd.setMemberKey(sellerMbrRetrievePWD.getSubSellerKey());

			} else {
				loginSellerResponse.setIsSubSeller(Constant.TYPE_YN_N);
				mbrPwd.setMemberKey(sellerMbrRetrievePWD.getSellerKey());
			}

			// id pass 맞으므로 count = 0 설정
			if (sellerMbrRetrievePWD.getLoginStatusCode().equals(Constant.LOGIN_STATUS_CODE)) {
				loginSellerResponse.setLoginFailCount(0);
				row = this.commonDAO.update("Seller.updateLoginSuccess", mbrPwd);
				LOGGER.debug("### updateLoginSuccess row : {}", row);
				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}

		} else { // 비번이 틀린경우

			if (isRegisteredParent != null) {
				mbrPwd.setMemberKey(sellerMbrRetrievePWD.getSubSellerKey());
			} else {
				mbrPwd.setMemberKey(sellerMbrRetrievePWD.getSellerKey());
			}

			row = this.commonDAO.update("Seller.updateLoginFail", mbrPwd);
			LOGGER.debug("### updateLoginFail/Success row : {}", row);
			loginSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.fail",
					"response.ResultMessage.fail"));
			loginSellerResponse.setLoginFailCount(sellerMbrRetrievePWD.getFailCnt() + 1);
			loginSellerResponse.setIsLoginSuccess(Constant.TYPE_YN_N);

		}

		return loginSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자회원 기본정보 조회를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchSellerRequest
	 *            판매자회원 기본정보 조회 요청 Value Object
	 * @return searchSellerResponse - 판매자회원 기본정보 조회 응답 Value Object
	 */
	@Override
	public SearchSellerResponse searchSeller(SearchSellerRequest searchSellerRequest) {

		SearchSellerResponse searchSellerResponse = (SearchSellerResponse) this.commonDAO.queryForObject(
				"Seller.searchSeller", searchSellerRequest);
		return searchSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자회원 기본정보만(판매자테이블) 조회를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchMbrSellerRequest
	 *            판매자회원 기본정보만(판매자테이블) 조회 요청 Value Object
	 * @return searchMbrSellerResponse - 판매자회원 기본정보만 조회 응답 Value Object
	 */
	@Override
	public SearchMbrSellerResponse searchMbrSeller(SearchMbrSellerRequest searchMbrSellerRequest) {

		List<SellerMbr> sellerMbrKeyList = (List<SellerMbr>) this.commonDAO.queryForList("Seller.searchMbrSeller",
				searchMbrSellerRequest);

		List<SellerMbr> sellerMbrList = new ArrayList<SellerMbr>();
		Map sellerMbrListMap = new HashMap();
		String keyString = "";

		int iCase = 0;
		if (searchMbrSellerRequest.getKeySearchList().get(0).getKeyType().equals("INSD_SELLERMBR_NO"))
			iCase = 1;
		else if (searchMbrSellerRequest.getKeySearchList().get(0).getKeyType().equals("SELLERMBR_ID"))
			iCase = 2;
		else if (searchMbrSellerRequest.getKeySearchList().get(0).getKeyType().equals("BIZ_REG_NO"))
			iCase = 3;

		if (sellerMbrKeyList != null && sellerMbrKeyList.size() != 0) {
			switch (iCase) {
			case 1:
				keyString = sellerMbrKeyList.get(0).getSellerKey();
				break;
			case 2:
				keyString = sellerMbrKeyList.get(0).getSellerID();
				break;
			case 3:
				keyString = sellerMbrKeyList.get(0).getSellerBizNumber();
				break;

			default:
				break;
			}
		} else {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		for (int i = 0; i < sellerMbrKeyList.size(); i++) {
			switch (iCase) {
			case 1:
				// 기존 검색어와 같지 않으면
				if (!keyString.equals(sellerMbrKeyList.get(i).getSellerKey())) {
					// 기존 검색어와 다르다면 map 에 add
					sellerMbrListMap.put(keyString, sellerMbrList);
					sellerMbrList = new ArrayList<SellerMbr>();
				}
				sellerMbrList.add(sellerMbrKeyList.get(i));
				keyString = sellerMbrKeyList.get(i).getSellerKey();
				break;
			case 2:
				// 기존 검색어와 같지 않으면
				if (!keyString.equals(sellerMbrKeyList.get(i).getSellerID())) {
					// 기존 검색어와 다르다면 map 에 add
					sellerMbrListMap.put(keyString, sellerMbrList);
					sellerMbrList = new ArrayList<SellerMbr>();
				}
				sellerMbrList.add(sellerMbrKeyList.get(i));
				keyString = sellerMbrKeyList.get(i).getSellerID();
				break;
			case 3:
				// 기존 검색어와 같지 않으면
				if (!keyString.equals(sellerMbrKeyList.get(i).getSellerBizNumber())) {
					// 기존 검색어와 다르다면 map 에 add
					sellerMbrListMap.put(keyString, sellerMbrList);
					sellerMbrList = new ArrayList<SellerMbr>();
				}
				sellerMbrList.add(sellerMbrKeyList.get(i));
				keyString = sellerMbrKeyList.get(i).getSellerBizNumber();
				break;

			default:
				break;
			}
		}
		sellerMbrListMap.put(keyString, sellerMbrList);

		SearchMbrSellerResponse searchMbrSellerResponse = new SearchMbrSellerResponse();
		searchMbrSellerResponse.setSellerMbrListMap(sellerMbrListMap);

		return searchMbrSellerResponse;

	}

	/**
	 * <pre>
	 * 판매자회원 정산정보 조회를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAccountSellerRequest
	 *            판매자회원 정산정보 조회 요청 Value Object
	 * @return searchAccountSellerResponse - 판매자회원 정산정보 조회 응답 Value Object
	 */
	@Override
	public SearchAccountSellerResponse searchAccountSeller(SearchAccountSellerRequest searchAccountSellerRequest) {

		// US010201 정상 정상
		// US010202 탈퇴 자의탈퇴/직권탈퇴
		// US010203 가가입 가가입
		// US010204 일시정지 로그인제한/직권중지/7일이용정지/30일이용정지/영구이용정지
		// US010205 전환

		// US010301 정상
		// US010302 탈퇴신청
		// US010303 탈퇴완료
		// US010304 가입승인 만료
		// US010305 가입승인 대기
		// US010306 이메일변경승인대기
		// US010307 로그인 제한
		// US010308 직권중지
		// US010309 7일이용정지 7일이용정지
		// US010310 30일이용정지 30일이용정지
		// US010311 영구이용정지 영구이용정지
		// US010312 전환신청
		// US010313 전환재신청
		// US010314 전환거절
		/** 회원서브상태 - 전환신청. */
		// CHANGE_REQUEST,
		/** 회원서브상태 - 전환재신청. */
		// CHANGE_REAPPLY,
		// MainStateCode
		// SubStateCode

		SellerMbr sellerMbr = (SellerMbr) this.commonDAO.queryForObject("Seller.searchAccountSellerCheck",
				searchAccountSellerRequest.getSellerKey());

		if (sellerMbr == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.unknownMemberID", ""));
		}

		LOGGER.debug(">>>> >>> SellerServiceImpl searchAccountSeller getSellerSubStatus() : {}",
				sellerMbr.getSellerSubStatus() + "  = " + SubStateCode.CHANGE_REQUEST.getCode());

		/*
		 * Modified by Brian Ahn, Date : 2014.06.10
		 */

		// 2014.06.05

		// if (sellerMbr.getSellerSubStatus().equals(SubStateCode.CHANGE_REQUEST.getCode()) ||
		// sellerMbr.getSellerSubStatus().equals(SubStateCode.CHANGE_REAPPLY.getCode()) ||
		// sellerMbr.getSellerSubStatus().equals(SubStateCode.ACCT_AGREE_STANDBY.getCode()) ||
		// sellerMbr.getSellerSubStatus().equals(SubStateCode.CHANGE_CANCEL.getCode()) ||
		// sellerMbr.getSellerSubStatus().equals(SubStateCode.JOIN_REJECTED.getCode())) { // 유료회원은 무조건
		// searchAccountSellerUpgrade if (sellerMbr.getSellerCategory().equals(SellerCategoryCode.PAY_SELLER.getCode()))
		// { // 유료회원은 정산태이블에서 조회 SearchAccountSellerResponse searchAccountSellerResponse = (SearchAccountSellerResponse)
		// this.commonDAO .queryForObject("Seller.searchAccountSellerUpgrade", searchAccountSellerRequest); return
		// searchAccountSellerResponse; } // 무료회원은 아래 logic. // US010313 개발자유형전환 거절 US010315 정산정보승인 거절 // 전환신청 중이거나 ,
		// 전환재신청 중일때만 전환 신청 테이블에서 조회 // TB_US_SELLERMBR_CHANGED_APLC 에서 조회해온다. SearchAccountSellerResponse
		// searchAccountSellerResponse = (SearchAccountSellerResponse) this.commonDAO
		// .queryForObject("Seller.searchAccountSellerStandBy", searchAccountSellerRequest); return
		// searchAccountSellerResponse; } else { // 전환신청 전이거나 , 전환신청이 완료된경우 는 정산태이블에서 조회 SearchAccountSellerResponse
		// searchAccountSellerResponse = (SearchAccountSellerResponse) this.commonDAO
		// .queryForObject("Seller.searchAccountSeller", searchAccountSellerRequest); return
		// searchAccountSellerResponse; }

		SearchAccountSellerResponse searchAccountSellerResponse = null;
		// 유료회원은 무조건 searchAccountSellerUpgrade
		if (sellerMbr.getSellerCategory().equals(SellerCategoryCode.PAY_SELLER.getCode())) {
			// 유료회원은 정산태이블에서 조회
			searchAccountSellerResponse = (SearchAccountSellerResponse) this.commonDAO.queryForObject(
					"Seller.searchAccountSellerUpgrade", searchAccountSellerRequest);
		} else if (sellerMbr.getSellerCategory().equals(SellerCategoryCode.FREE_SELLER.getCode())) {
			// if (sellerMbr.getSellerSubStatus().equals(SubStateCode.ACCT_AGREE_STANDBY.getCode())) {
			searchAccountSellerResponse = (SearchAccountSellerResponse) this.commonDAO.queryForObject(
					"Seller.searchAccountSellerStandBy", searchAccountSellerRequest);
			// }
		}

		return searchAccountSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자회원 기본정보 수정을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateSellerRequest
	 *            판매자회원 기본정보 수정 요청 Value Object
	 * @return updateSellerResponse - 판매자회원 기본정보 수정 응답 Value Object
	 */
	@Override
	public UpdateSellerResponse updateSeller(UpdateSellerRequest updateSellerRequest) {

		UpdateSellerResponse updateSellerResponse = new UpdateSellerResponse();
		Integer row = 0;

		SellerMbr sellerMbr = null;
		sellerMbr = updateSellerRequest.getSellerMbr();
		sellerMbr.setSellerKey(updateSellerRequest.getSellerMbr().getSellerKey());

		/*
		 * NODATA 처리 루틴 : Added by Brian Ahn, 2014.01.21
		 * 
		 * Utils.setDataMapping(sellerMbr);
		 */

		String isRegistered = null;
		isRegistered = this.commonDAO.queryForObject("Seller.isRegisteredID", sellerMbr, String.class);

		if (isRegistered != null) {

			// 이력 테이블 먼저 저장 한다.
			row = this.commonDAO.update("Seller.updateSellerHistory", sellerMbr);
			LOGGER.debug("### row History : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			// if (updateSellerRequest.getMbrAuth() != null) {
			// sellerMbr.setIsRealName("Y");
			// }
			//
			// if (updateSellerRequest.getMbrLglAgent() != null) {
			// sellerMbr.setIsParent("Y");
			// }

			row = this.commonDAO.update("Seller.updateSeller", sellerMbr);
			LOGGER.debug("### row : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			// check 법정대리인
			if (updateSellerRequest.getMbrLglAgent() != null) {
				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				mbrLglAgent = updateSellerRequest.getMbrLglAgent();
				mbrLglAgent.setTenantID(updateSellerRequest.getCommonRequest().getTenantID());
				mbrLglAgent.setMemberKey(updateSellerRequest.getSellerMbr().getSellerKey());

				/*
				 * NODATA 처리 루틴 : Added by Brian Ahn, 2014.01.21
				 * 
				 * Utils.setDataMapping(mbrLglAgent);
				 */
				row = (Integer) this.commonDAO.insert("Seller.updateAgentRealName", mbrLglAgent);
				LOGGER.debug("### 1 row : {}", row);
				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}

			// check 실명인증
			if (updateSellerRequest.getMbrAuth() != null) {
				MbrAuth mbrAuth = new MbrAuth();
				mbrAuth = updateSellerRequest.getMbrAuth();
				mbrAuth.setMemberKey(updateSellerRequest.getSellerMbr().getSellerKey());
				mbrAuth.setTenantID(updateSellerRequest.getCommonRequest().getTenantID());

				/*
				 * NODATA 처리 루틴 : Added by Brian Ahn, 2014.01.21
				 * 
				 * Utils.setDataMapping(mbrAuth);
				 */
				row = (Integer) this.commonDAO.insert("Seller.updateOwnRealName", mbrAuth);
				LOGGER.debug("### 2  row : {}", row);
				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}

			// check 보안질문
			if (updateSellerRequest.getPWReminderList() != null) {
				// 보안질문 삭제 후
				this.commonDAO.delete("Seller.removePWReminder", sellerMbr);
				LOGGER.debug("###  PWR del row : {}", row);
				// 보안질문 등록
				List<PWReminder> pWReminderList = updateSellerRequest.getPWReminderList();
				for (int i = 0; i < pWReminderList.size(); i++) {
					PWReminder pWReminder = pWReminderList.get(i);
					pWReminder.setSellerKey(updateSellerRequest.getSellerMbr().getSellerKey());
					row = this.commonDAO.update("Seller.insertPWReminder", pWReminder);
					LOGGER.debug("###  1 row : {}", row);
					if (row == 0) {
						throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					}
				}
			}

			BatCmsSellerMbr batCmsSellerMbr = new BatCmsSellerMbr();
			batCmsSellerMbr.setSellerKey(updateSellerRequest.getSellerMbr().getSellerKey());
			batCmsSellerMbr.setResultCode("PD003701");
			batCmsSellerMbr.setStatusType("UPDATE");

			row = this.commonDAO.update("Seller.insertBatCmsSellerMbr", batCmsSellerMbr);
			LOGGER.debug("### 2  row : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

		} else {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		updateSellerResponse.setSellerID(isRegistered);
		updateSellerResponse.setSellerKey(updateSellerRequest.getSellerMbr().getSellerKey());
		updateSellerResponse.setSellerMainStatus(updateSellerRequest.getSellerMbr().getSellerMainStatus());
		updateSellerResponse.setSellerSubStatus(updateSellerRequest.getSellerMbr().getSellerSubStatus());

		return updateSellerResponse;

	}

	/**
	 * <pre>
	 * 판매자회원 정산정보 수정을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateAccountSellerRequest
	 *            판매자회원 정산정보 수정 요청 Value Object
	 * @return updateAccountSellerResponse - 판매자회원 정산정보 수정 응답 Value Object
	 */
	@Override
	public UpdateAccountSellerResponse updateAccountSeller(UpdateAccountSellerRequest updateAccountSellerRequest) {

		// 전환상태 확인 // 전환신청이 완료된상태에서만 수정이 가능해야한다.체크하는로직 추가
		SellerMbr sellerMbr = (SellerMbr) this.commonDAO.queryForObject("Seller.searchAccountSellerCheck",
				updateAccountSellerRequest.getSellerKey());

		if (sellerMbr == null || sellerMbr.getSellerSubStatus().equals(SubStateCode.CHANGE_REQUEST.getCode())
				|| sellerMbr.getSellerSubStatus().equals(SubStateCode.CHANGE_REAPPLY.getCode())) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}
		LOGGER.debug(">>>> >>> SellerServiceImpl updateAccountSeller getSellerSubStatus() : {}",
				sellerMbr.getSellerSubStatus());

		// >>>>>>>>>>>>>>>> 판매자 업데이트 기본 테이블에서 히스토리 테이블에 백업
		Integer row = this.commonDAO.update("Seller.updateSellerHistory", updateAccountSellerRequest.getSellerMbr());
		LOGGER.debug("### row History : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		row = this.commonDAO.update("Seller.updateAccountSellerMbr", updateAccountSellerRequest.getSellerMbr());
		LOGGER.debug("### updateAccountSellerMbr row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		Integer row1 = 0;
		// >>>>>>>>>>>>>>>>정산 테이블 기존 row 종료일 (enddate) 수정
		if (StringUtils.equals(SellerCategoryCode.PAY_SELLER.getCode(), sellerMbr.getSellerCategory())) {
			row = this.commonDAO.update("Seller.updateAccountSellerAccountHistory",
					updateAccountSellerRequest.getSellerAccount());
			LOGGER.debug("### updateAccountSellerAccountHistory History row : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			// 마지막 row 를 셀렉트 insert 를 하고
			row1 = this.commonDAO.update("Seller.insertAccountSellerAccount",
					updateAccountSellerRequest.getSellerAccount());
		}

		// update 하는 동작으로 처리한다. 만약 업데이트 할 row 가 없으면 insert 하게된다.
		// 판매자 정산 업데이트 무조건 insert 하는데 현재 날짜 StartDate 값이 있으면 update 없으면 insert
		row1 = this.commonDAO
				.update("Seller.updateAccountSellerAccount", updateAccountSellerRequest.getSellerAccount());
		LOGGER.debug("### updateAccountSellerAccount row : {}", row1);
		if (row1 == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// 판매자 정산률 멀티미디어 업데이트 >> 있으면 update 없다면 insert List END_DT // TO_DATE('29991231235959','YYYYMMDDHH24MISS')

		// if (updateAccountSellerRequest.getExtraRightList() != null) {
		// for (int i = 0; i < updateAccountSellerRequest.getExtraRightList().size(); i++) {
		// ExtraRight extraRight = updateAccountSellerRequest.getExtraRightList().get(i);
		// extraRight.setSellerKey(updateAccountSellerRequest.getSellerKey());
		// // >>>>>>>>>>>>>>>>판매자 정산률 멀티미디어 기존 row 종료일 (enddate) 수정
		// row = this.commonDAO.update("Seller.updateAccountSellerExtraRightHistory", extraRight);
		// LOGGER.debug("### updateAccountSellerExtraRight History row : {}", row);
		//
		// Integer row2 = this.commonDAO.update("Seller.updateAccountSellerExtraRight", extraRight);
		// LOGGER.debug("### updateAccountSellerExtraRight row : {}", row2);
		// if (row2 == 0) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		// }
		// }
		// }

		// 판매자 서류문서 업데이트 >> 있으면 update 없다면 insert
		if (updateAccountSellerRequest.getDocumentList() != null) {
			for (int i = 0; i < updateAccountSellerRequest.getDocumentList().size(); i++) {
				Document document = updateAccountSellerRequest.getDocumentList().get(i);
				document.setSellerKey(updateAccountSellerRequest.getSellerKey());
				Integer row3 = this.commonDAO.update("Seller.updateAccountSellerDocument", document);
				LOGGER.debug("### updateAccountSellerDocument row : {}", row3);

				if (row3 == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}
		}

		BatCmsSellerMbr batCmsSellerMbr = new BatCmsSellerMbr();
		batCmsSellerMbr.setSellerKey(updateAccountSellerRequest.getSellerMbr().getSellerKey());
		batCmsSellerMbr.setResultCode("PD003701");
		batCmsSellerMbr.setStatusType("UPDATE");

		row = this.commonDAO.update("Seller.insertBatCmsSellerMbr", batCmsSellerMbr);
		LOGGER.debug("### 2  row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		UpdateAccountSellerResponse updateAccountSellerResponse = new UpdateAccountSellerResponse();
		updateAccountSellerResponse.setSellerID(sellerMbr.getSellerID());
		updateAccountSellerResponse.setSellerKey(updateAccountSellerRequest.getSellerKey());
		return updateAccountSellerResponse;

	}

	/**
	 * <pre>
	 * 판매자회원 ID 찾기를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchIDSellerRequest
	 *            판매자회원 ID 찾기 요청 Value Object
	 * @return searchIDSellerResponse - 판매자회원 ID 찾기 응답 Value Object
	 */
	@Override
	public SearchIDSellerResponse searchIDSeller(SearchIDSellerRequest searchIDSellerRequest) {

		SearchIDSellerResponse searchIDSellerResponse = new SearchIDSellerResponse();
		searchIDSellerResponse.setSellerMbr((List<SellerMbr>) this.commonDAO.queryForList("Seller.searchIDSeller",
				searchIDSellerRequest));
		if (searchIDSellerResponse.getSellerMbr() == null || searchIDSellerResponse.getSellerMbr().size() == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		return searchIDSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자회원 상태를 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateStatusSellerRequest
	 *            판매자회원 상태 변경 요청 Value Object
	 * @return updateStatusSellerResponse - 판매자회원 상태 변경 응답 Value Object
	 */
	@Override
	public UpdateStatusSellerResponse updateStatusSeller(UpdateStatusSellerRequest updateStatusSellerRequest) {
		// TO DO.
		// ID 있나없나 확인.
		// ACTION 1. 회원 속성의 Main, Sub 상태정보를 update.
		// ACTION 2. 회원 이력 테이블 insert.

		UpdateStatusSellerResponse updateStatusSellerResponse = new UpdateStatusSellerResponse();
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerID(updateStatusSellerRequest.getSellerID());

		String isRegistered = null;
		isRegistered = this.commonDAO.queryForObject("Seller.isRegistered", sellerMbr, String.class);
		LOGGER.debug("### updateStatusSeller isRegistered row : {}", isRegistered);

		if (isRegistered != null) {
			sellerMbr = new SellerMbr();
			sellerMbr.setSellerKey(isRegistered);
			sellerMbr.setSellerMainStatus(updateStatusSellerRequest.getSellerMainStatus());
			sellerMbr.setSellerSubStatus(updateStatusSellerRequest.getSellerSubStatus());
			sellerMbr.setStopStatusCode(updateStatusSellerRequest.getStopStatusCode());
			sellerMbr.setLoginStatusCode(updateStatusSellerRequest.getLoginStatusCode());

			if (updateStatusSellerRequest.getIsNewEntry() != null)
				sellerMbr.setRegDate("Y");

			// 이력 테이블 저장후 수정

			// >>>>>>>>>>>>>>>> 판매자 업데이트 기본 테이블에서 히스토리 테이블에 백업
			Integer row = this.commonDAO.update("Seller.updateSellerHistory", sellerMbr);
			LOGGER.debug("### row History : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			row = this.commonDAO.update("Seller.updateStatus", sellerMbr);
			LOGGER.debug("### updateStatus row : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			} else if (updateStatusSellerRequest.getLoginStatusCode().equals(Constant.LOGIN_STATUS_CODE)) {

				MbrPwd mbrPwd = new MbrPwd();
				mbrPwd.setMemberKey(isRegistered);
				// 로그인 상태값이 10인경우 (잠금해지로 판단하기때문에 fail count = 0 셋팅

				row = this.commonDAO.update("Seller.updateLoginSuccess", mbrPwd);
				LOGGER.debug("### updateLoginSuccess row : {}", row);
				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}

			BatCmsSellerMbr batCmsSellerMbr = new BatCmsSellerMbr();
			batCmsSellerMbr.setSellerKey(isRegistered);
			// 가입승인 처리시 CMS - INSERT
			if (updateStatusSellerRequest.getIsNewEntry() != null) {
				batCmsSellerMbr.setStatusType("INSERT");
			} else { // 상태 변경시 CMS - UPDATE
				batCmsSellerMbr.setStatusType("UPDATE");
			}
			batCmsSellerMbr.setResultCode("PD003701");

			row = this.commonDAO.update("Seller.insertBatCmsSellerMbr", batCmsSellerMbr);
			LOGGER.debug("### 2  row : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

		} else {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		return updateStatusSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자회원 ID/EMAIL 존재여부를 확인하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param checkDuplicationSellerRequest
	 *            판매자회원 ID/EMAIL 존재여부 확인 요청 Value Object
	 * @return checkDuplicationSellerResponse - 판매자회원 ID/EMAIL 존재여부 확인 응답 Value Object
	 */
	@Override
	public CheckDuplicationSellerResponse checkDuplicationSeller(
			CheckDuplicationSellerRequest checkDuplicationSellerRequest) {
		// USER_KEY 내부 사용자 키
		// USER_ID 사용자 ID
		// IDP_KEY 통합서비스 키
		// DEVICE_KEY 내부 기기 키
		// DEVICE_ID 기기 ID
		// EMAIL 이메일
		// PHONE_NUMBER 연락처
		// 조회 타입은 api key 명 과 같아야한다.

		CheckDuplicationSellerResponse checkDuplicationSellerResponse = (CheckDuplicationSellerResponse) this.commonDAO
				.queryForObject("Seller.checkDuplicationSeller", checkDuplicationSellerRequest);
		// wd 테이블에서도 검색해본다.
		if (checkDuplicationSellerResponse == null) {
			for (int i = 0; i < checkDuplicationSellerRequest.getKeySearchList().size(); i++) {
				if (checkDuplicationSellerRequest.getKeySearchList().get(i).getKeyType().equals("SELLERMBR_ID")) {
					checkDuplicationSellerResponse = (CheckDuplicationSellerResponse) this.commonDAO.queryForObject(
							"Seller.checkDuplicationSellerWd", checkDuplicationSellerRequest);
				}
			}
		}

		if (checkDuplicationSellerResponse == null) {
			checkDuplicationSellerResponse = new CheckDuplicationSellerResponse();
			checkDuplicationSellerResponse.setIsRegistered(Constant.TYPE_YN_N);
		}

		return checkDuplicationSellerResponse;

	}

	/**
	 * <pre>
	 * 판매자회원 비밀번호 보안질문을 확인하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param checkPasswordReminderSellerRequest
	 *            판매자회원 비밀번호 보안질문 확인 요청 Value Object
	 * @return checkPasswordReminderSellerResponse - 판매자회원 비밀번호 보안질문 확인 응답 Value Object
	 */

	@Override
	public CheckPasswordReminderSellerResponse checkPasswordReminderSeller(
			CheckPasswordReminderSellerRequest checkPasswordReminderSellerRequest) {

		CheckPasswordReminderSellerResponse checkPasswordReminderSellerResponse = new CheckPasswordReminderSellerResponse();
		for (int i = 0; i < checkPasswordReminderSellerRequest.getPWReminderList().size(); i++) {
			PWReminder pWReminder;
			pWReminder = checkPasswordReminderSellerRequest.getPWReminderList().get(i);
			if (pWReminder.getSellerID() == null) {
				pWReminder.setSellerID(checkPasswordReminderSellerRequest.getSellerID());
			}
			checkPasswordReminderSellerResponse = (CheckPasswordReminderSellerResponse) this.commonDAO.queryForObject(
					"Seller.checkPasswordReminderSeller", pWReminder);

			if (checkPasswordReminderSellerResponse == null
					|| checkPasswordReminderSellerResponse.getIsCorrect() == null
					|| !checkPasswordReminderSellerResponse.getIsCorrect().equals(Constant.TYPE_YN_Y)) {

				checkPasswordReminderSellerResponse = new CheckPasswordReminderSellerResponse();
				checkPasswordReminderSellerResponse.setIsCorrect(Constant.TYPE_YN_N);
				// checkPasswordReminderSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.fail",
				// "response.ResultMessage.fail"));

				return checkPasswordReminderSellerResponse;
			}
		}
		return checkPasswordReminderSellerResponse;

	}

	/**
	 * <pre>
	 * 판매자회원 비밀번호를 초기화하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param resetPasswordSellerRequest
	 *            비밀번호 초기화 요청 Value Object
	 * @return resetPasswordSellerResponse - 비밀번호 초기화 응답 Value Object
	 */
	@Override
	public ResetPasswordSellerResponse updateResetPasswordSeller(ResetPasswordSellerRequest resetPasswordSellerRequest) {
		// 해당 id 의 비밀번호를 셋팅하고 insert 한 비밀번호를 리턴한다.
		MbrPwd mbrPwd = resetPasswordSellerRequest.getMbrPwd();

		String initPw = Utils.getInitPassword();
		SHACipher cipher = new SHACipher();

		try {
			mbrPwd.setMemberPW(cipher.getHash(initPw)); // < 초기화 패스워드 셋팅한다.

		} catch (NoSuchAlgorithmException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (UnsupportedEncodingException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (StorePlatformException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// mbrPwd.setMemberPW("rest passward"); // < 초기화 패스워드 셋팅한다.

		Integer row = this.commonDAO.update("Seller.resetPasswordSeller", mbrPwd);
		LOGGER.debug("### updateStatus row : {}", row);
		if (row == 1) {
			ResetPasswordSellerResponse resetPasswordSellerResponse = new ResetPasswordSellerResponse();
			resetPasswordSellerResponse.setSellerPW(initPw);
			return resetPasswordSellerResponse;
		} else {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}
	}

	/**
	 * <pre>
	 * 판매자회원 비밀번호를 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updatePasswordSellerRequest
	 *            비밀번호 변경 Value Object
	 * @return updatePasswordSellerResponse - 비밀번호 변경 응답 Value Object
	 */
	@Override
	public UpdatePasswordSellerResponse updatePasswordSeller(UpdatePasswordSellerRequest updatePasswordSellerRequest) {

		MbrPwd mbrPwd = updatePasswordSellerRequest.getMbrPwd();

		SHACipher cipher = new SHACipher();

		try {
			mbrPwd.setMemberPW(cipher.getHash(mbrPwd.getMemberPW()));
			mbrPwd.setOldPW(cipher.getHash(mbrPwd.getOldPW()));

		} catch (NoSuchAlgorithmException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (UnsupportedEncodingException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (StorePlatformException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		Integer row = this.commonDAO.update("Seller.updatePasswordSeller", mbrPwd);
		LOGGER.debug("### updateStatus row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		} else {

			String isRegistered = null;
			isRegistered = this.commonDAO.queryForObject("Seller.isRegistered", updatePasswordSellerRequest.getMbrPwd()
					.getMemberID(), String.class);

			UpdatePasswordSellerResponse updatePasswordSellerResponse = new UpdatePasswordSellerResponse();
			updatePasswordSellerResponse.setSellerKey(isRegistered);
			return updatePasswordSellerResponse;
		}

	}

	/**
	 * <pre>
	 * 판매자회원 새로운 비밀번호를 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateNewPasswordSellerRequest
	 *            새로운 비밀번호 변경 Value Object
	 * @return updateNewPasswordSellerResponse - 비밀번호 변경 응답 Value Object
	 */
	@Override
	public UpdateNewPasswordSellerResponse updateNewPasswordSeller(
			UpdateNewPasswordSellerRequest updateNewPasswordSellerRequest) {

		MbrPwd mbrPwd = updateNewPasswordSellerRequest.getMbrPwd();

		SHACipher cipher = new SHACipher();

		try {
			mbrPwd.setMemberPW(cipher.getHash(mbrPwd.getMemberPW()));
			// mbrPwd.setOldPW(cipher.getHash(mbrPwd.getOldPW()));

		} catch (NoSuchAlgorithmException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (UnsupportedEncodingException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
		} catch (StorePlatformException ex) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		Integer row = this.commonDAO.update("Seller.updatePasswordSeller", mbrPwd);
		LOGGER.debug("### updateStatus row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		} else {

			String isRegistered = null;
			isRegistered = this.commonDAO.queryForObject("Seller.isRegistered", updateNewPasswordSellerRequest
					.getMbrPwd().getMemberID(), String.class);

			UpdateNewPasswordSellerResponse updateNewPasswordSellerResponse = new UpdateNewPasswordSellerResponse();
			updateNewPasswordSellerResponse.setSellerKey(isRegistered);
			return updateNewPasswordSellerResponse;
		}

	}

	//
	// /**
	// * <pre>
	// * 판매자회원 약관동의 정보를 등록 또는 수정하는 기능을 제공한다.
	// * </pre>
	// *
	// * @param updateAgreementSellerRequest
	// * 약관동의 정보 수정 요청 Value Object
	// * @return updateAgreementSellerResponse - 약관동의 정보 수정 응답 Value Object
	// */
	// @Override
	// public UpdateAgreementSellerResponse updateAgreementSeller(UpdateAgreementSellerRequest
	// updateAgreementSellerRequest) {
	//
	// LOGGER.debug("\n\n\n\n\n");
	// LOGGER.debug("==================================================================================");
	// LOGGER.debug("서비스 - 판매자 약관동의 등록/수정");
	// LOGGER.debug("==================================================================================\n\n\n\n\n");
	//
	// LOGGER.debug("### 받은 데이터 : {}", updateAgreementSellerRequest);
	//
	// // 요청
	// List<MbrClauseAgree> mbrClauseAgreeList = updateAgreementSellerRequest.getMbrClauseAgreeList();
	//
	// Integer row = 0;
	// for (int i = 0; i < mbrClauseAgreeList.size(); i++) {
	// MbrClauseAgree mbrClauseAgree = mbrClauseAgreeList.get(i);
	// mbrClauseAgree.setMemberKey(updateAgreementSellerRequest.getSellerKey());
	// Integer rows = this.commonDAO.update("Seller.updateAgreement", mbrClauseAgree);
	// LOGGER.debug("### row : {}", row);
	// row = row + rows;
	// }
	// if (row == 0) {
	// return null;
	// }
	// List<MbrClauseAgree> agreementCount = (List) this.commonDAO.queryForList("Seller.getClauseAgreeList",
	// updateAgreementSellerRequest.getSellerKey());
	//
	// UpdateAgreementSellerResponse updateAgreementSellerResponse = new UpdateAgreementSellerResponse();
	// updateAgreementSellerResponse.setSellerKey(updateAgreementSellerRequest.getSellerKey());
	// updateAgreementSellerResponse.setAgreementCount(agreementCount.size());
	//
	// return updateAgreementSellerResponse;
	// }

	//
	// /**
	// * <pre>
	// * 판매자 약관동의 목록 조회.
	// * </pre>
	// *
	// * @param searchAgreementListSellerRequest
	// * 판매자 약관동의 목록 조회 요청 Value Object
	// * @return searchAgreementListSeller - 판매자 약관동의 목록 조회 응답 Value Object
	// */
	// @Override
	// public SearchAgreementListSellerResponse searchAgreementListSeller(
	// SearchAgreementListSellerRequest searchAgreementListSellerRequest) {
	//
	// LOGGER.debug("\n\n\n\n\n");
	// LOGGER.debug("==================================================================================");
	// LOGGER.debug("서비스 - 판매자 약관동의 목록 조회");
	// LOGGER.debug("==================================================================================\n\n\n\n\n");
	//
	// LOGGER.debug("### 받은 데이터 : {}", searchAgreementListSellerRequest);
	//
	// SearchAgreementListSellerResponse searchAgreementListSellerResponse;
	// searchAgreementListSellerResponse = this.commonDAO.queryForObject("Seller.searchAgreementListSeller",
	// searchAgreementListSellerRequest, SearchAgreementListSellerResponse.class);
	//
	// return searchAgreementListSellerResponse;
	// }

	/**
	 * <pre>
	 * 판매자회원 실명인증 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateRealNameSellerRequest
	 *            실명인증 정보 수정 요청 Value Object
	 * @return updateRealNameSellerResponse - 실명인증 정보 수정 응답 Value Object
	 */
	@Override
	public UpdateRealNameSellerResponse updateRealNameSeller(UpdateRealNameSellerRequest updateRealNameSellerRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 실명인증 정보 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 : {}", updateRealNameSellerRequest);
		LOGGER.debug("### 받은 데이터 : {}", updateRealNameSellerRequest.getMbrAuth());
		LOGGER.debug("### 받은 데이터 : {}", updateRealNameSellerRequest.getMbrLglAgent());

		Integer row = (Integer) this.commonDAO.insert("Seller.updateRealName", updateRealNameSellerRequest);
		LOGGER.debug("### row : {}", row);

		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		} else {
			SellerMbr sellerMbr = new SellerMbr();
			sellerMbr.setSellerKey(updateRealNameSellerRequest.getSellerKey());

			if (updateRealNameSellerRequest.getIsOwn().equals(Constant.REAL_NAME_TYPE_OWN)) {
				sellerMbr.setIsRealName(updateRealNameSellerRequest.getIsRealName());
			} else if (updateRealNameSellerRequest.getIsOwn().equals(Constant.REAL_NAME_TYPE_PARANT)) {
				sellerMbr.setIsParent(updateRealNameSellerRequest.getIsRealName());
			}

			// 이력 테이블 먼저 저장 한다.
			row = this.commonDAO.update("Seller.updateSellerHistory", sellerMbr);
			LOGGER.debug("### row History : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			row = this.commonDAO.update("Seller.updateSeller", sellerMbr);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		}

		UpdateRealNameSellerResponse updateRealNameSellerResponse = new UpdateRealNameSellerResponse();
		updateRealNameSellerResponse.setSellerKey(updateRealNameSellerRequest.getSellerKey());

		return updateRealNameSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자회원 전환신청을 등록하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param upgradeSellerRequest
	 *            전환신청 요청 Value Object
	 * @return upgradeSellerResponse - 전환신청 응답 Value Object
	 */
	@Override
	public UpgradeSellerResponse updateUpgradeSeller(UpgradeSellerRequest upgradeSellerRequest) {

		// 히스토리 백업
		Integer row = this.commonDAO.update("Seller.updateSellerHistory", upgradeSellerRequest.getSellerMbr());
		LOGGER.debug("### row History : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// 판매자 업데이트 기본 테이블에서 수정된것이 없다면 오류로 판단함 // 히스토리 테이블에 백업을 해야 하는가?
		row = this.commonDAO.update("Seller.upgradeSellerMbr", upgradeSellerRequest.getSellerMbr());
		LOGGER.debug("### updateStatus row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// 전환신청 코드 작성 (전환 유형코드)

		SellerUpgrade sellerUpgrade = upgradeSellerRequest.getSellerUpgrade();

		SellerMbr sellerMbr = (SellerMbr) this.commonDAO.queryForObject("Seller.searchSellerMbr",
				upgradeSellerRequest.getSellerKey());

		// sellerMbr.getIsDomestic(); // 국내 ('Y'), 해외 ('N')
		// sellerMbr.getSellerClass(); // 판매자 구분 ex) US010101(개인) , US010102(개인사업자) , US010103(법인사업자) SellerClassCode
		// sellerMbr.getSellerCategory(); // 판매자 분류 ex) US011301(무료 ), US011302(유료) , US011303(BP)
		// sellerUpgrade.getSellerClassTo(); // 바뀔 판매자 구분 코드 US010101(개인) ,US010102(개인사업자) ,US010103(법인사업자)

		// row getSellerClass : US010102 개인사업자
		// row getSellerCategory : US011302 유료
		// row getSellerClassTo : US010103 법인사업자.
		// US001503 US001506 ㅡ이게 리턴되어야한다.

		// LOGGER.debug("### updateStatus row getSellerClass : {}", sellerMbr.getSellerClass());
		// LOGGER.debug("### updateStatus row getSellerCategory : {}", sellerMbr.getSellerCategory());
		// LOGGER.debug("### updateStatus row getSellerClassTo : {}", sellerUpgrade.getSellerClassTo());

		sellerUpgrade.setChangedCd("");

		if (sellerMbr.getIsDomestic() != null && sellerMbr.getIsDomestic().equals(Constant.TYPE_YN_N)) { // 현재 해외 개발자
			sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_OVERSEA_TO_PAY.getCode()); // 해외개발자전환
			// 2014.06.05 수정 시작
			// } else if (sellerMbr.getSellerClass().equals(SellerClassCode.PRIVATE_SELLER.getCode()) // 현재 개인 개발자 이고
			// 무료인경우
			// && sellerMbr.getSellerCategory().equals(SellerCategoryCode.FREE_SELLER.getCode())) {
		} else if (sellerMbr.getSellerClass().equals(SellerClassCode.PRIVATE_SELLER.getCode()) // 현재 개인 개발자 이고 무료인경우
				&& sellerMbr.getSellerSubStatus().equals(SubStateCode.MEMBER_FREE.getCode())) {
			// 2014.06.05 수정 끝
			if (sellerUpgrade.getSellerClassTo().equals(SellerClassCode.PRIVATE_SELLER.getCode())) {
				sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_FREE_TO_PAY.getCode()); // 무료개발자 유료개인개발자로 전환
			} else if (sellerUpgrade.getSellerClassTo().equals(SellerClassCode.PRIVATE_BUSINESS_SELLER.getCode())) {
				sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_FRRE_TO_BUSINESS.getCode()); // 유료 개인사업자로 전환
			} else if (sellerUpgrade.getSellerClassTo().equals(SellerClassCode.CORPORATION_SELLER.getCode())) {
				sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_FREE_TO_CORP.getCode()); // 유료 법인 사업자로 전환
			}
		} else if (sellerMbr.getSellerClass().equals(SellerClassCode.PRIVATE_SELLER.getCode())) { // 현재 개인 개발자 이고 유료인경우
			// && sellerMbr.getSellerCategory().equals(SellerCategoryCode.PAY_SELLER.getCode())) { // 2014.06.05 수정
			if (sellerUpgrade.getSellerClassTo().equals(SellerClassCode.PRIVATE_BUSINESS_SELLER.getCode())) {
				sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_PAY_TO_BUSINESS.getCode()); // 유료개발자 유료개인사업자 전환
			} else if (sellerUpgrade.getSellerClassTo().equals(SellerClassCode.CORPORATION_SELLER.getCode())) {
				sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_PAY_TO_CORP.getCode()); // 유료 법인 사업자로 전환
			}
			// ( 현재 유료/무료 개인 사업자 + 무료/유료 법인사업자에서 ) > 유료법인사업자로 전환
		} else if (sellerMbr.getSellerClass().equals(SellerClassCode.PRIVATE_BUSINESS_SELLER.getCode())
				|| sellerMbr.getSellerClass().equals(SellerClassCode.CORPORATION_SELLER.getCode())) {
			if (sellerUpgrade.getSellerClassTo().equals(SellerClassCode.CORPORATION_SELLER.getCode())) {
				sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_BUSINESS_TO_CORP.getCode()); // 유료사업자 유료법인사업자 전환
			}
		}

		// LOGGER.debug("### updateStatus row sellerUpgrade.getChangedCd() 1: {}", sellerUpgrade.getChangedCd());

		// 서브 상태가 US010305 정산정보승인 대기 라면
		if (sellerMbr.getIsDomestic() != null && sellerMbr.getIsDomestic().equals(Constant.TYPE_YN_N)) {
			sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_OVERSEA_TO_PAY.getCode()); // 해외개발자전환
		} else {
			if (upgradeSellerRequest.getSellerMbr().getSellerSubStatus()
					.equals(SubStateCode.ACCT_AGREE_STANDBY.getCode())) {
				// 개인이라면
				if (sellerUpgrade.getSellerClassTo().equals(SellerClassCode.PRIVATE_SELLER.getCode())) {
					sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_FREE_TO_PAY.getCode());
				} else if (sellerUpgrade.getSellerClassTo().equals(SellerClassCode.PRIVATE_BUSINESS_SELLER.getCode())) {
					sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_FRRE_TO_BUSINESS.getCode());
				} else if (sellerUpgrade.getSellerClassTo().equals(SellerClassCode.CORPORATION_SELLER.getCode())) {
					sellerUpgrade.setChangedCd(SellerChangeCode.CHANGE_FREE_TO_CORP.getCode());
				}
			}
		}

		// 전환 신청 코드 셋팅이 안된경우
		if (sellerUpgrade.getChangedCd().equals("")) {
			UpgradeSellerResponse upgradeSellerResponse = new UpgradeSellerResponse();
			upgradeSellerResponse.setSellerID(upgradeSellerRequest.getSellerMbr().getSellerID());
			upgradeSellerResponse.setSellerKey(upgradeSellerRequest.getSellerKey());
			// upgradeSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.wrongKeyType",
			// "response.ResultMessage.wrongKeyType",
			// "전환유형코드 생성오류(DB SellerClass,DB SellerCategory,SellerClassTo)"));
			// return upgradeSellerResponse;

			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			// 전환유형코드 생성오류(DB SellerClass,DB SellerCategory,SellerClassTo)

		}

		// LOGGER.debug("### updateStatus row sellerUpgrade.getChangedCd() 2: {}", sellerUpgrade.getChangedCd());

		// 판매자 전환 테이블 전환 신청id 가 추가되어야하고 insert 되어야한다. 전환신청 id 는 seq 를 사용한다.
		Integer row1 = this.commonDAO.update("Seller.upgradeSellerChanged", sellerUpgrade);
		LOGGER.debug("### updateStatus row1 : {}", row1);
		if (row1 <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		// // 판매자 정산률 멀티미디어 업데이트 >> 있으면 update 없다면 insert List END_DT // TO_DATE('29991231235959','YYYYMMDDHH24MISS')
		// if (upgradeSellerRequest.getExtraRightList() != null) {
		// for (int i = 0; i < upgradeSellerRequest.getExtraRightList().size(); i++) {
		// ExtraRight extraRight = upgradeSellerRequest.getExtraRightList().get(i);
		// extraRight.setSellerKey(upgradeSellerRequest.getSellerKey());
		// Integer row2 = this.commonDAO.update("Seller.updateAccountSellerExtraRight", extraRight);
		// LOGGER.debug("### updateStatus row2 : {}", row2);
		// if (row2 <= 0) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		// }
		// }
		// }

		// 판매자 서류문서 업데이트 >> 있으면 update 없다면 insert
		if (upgradeSellerRequest.getDocumentList() != null) {
			for (int i = 0; i < upgradeSellerRequest.getDocumentList().size(); i++) {
				Document document = upgradeSellerRequest.getDocumentList().get(i);
				document.setSellerKey(upgradeSellerRequest.getSellerKey());
				Integer row3 = this.commonDAO.update("Seller.updateAccountSellerDocument", document);
				LOGGER.debug("### updateStatus row3 : {}", row3);
				if (row3 <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}
		}

		BatCmsSellerMbr batCmsSellerMbr = new BatCmsSellerMbr();
		batCmsSellerMbr.setSellerKey(upgradeSellerRequest.getSellerKey());
		batCmsSellerMbr.setResultCode("PD003701");
		batCmsSellerMbr.setStatusType("UPDATE");

		row = this.commonDAO.update("Seller.insertBatCmsSellerMbr", batCmsSellerMbr);
		LOGGER.debug("### 2  row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		UpgradeSellerResponse upgradeSellerResponse = new UpgradeSellerResponse();
		upgradeSellerResponse.setSellerID(upgradeSellerRequest.getSellerMbr().getSellerID());
		upgradeSellerResponse.setSellerKey(upgradeSellerRequest.getSellerKey());
		return upgradeSellerResponse;

	}

	/**
	 * <pre>
	 * 판매자회원 탈퇴를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeSellerRequest
	 *            판매자회원 탈퇴 요청 Value Object
	 * @return removeSellerResponse - 판매자회원 탈퇴 응답 Value Object
	 */
	@Override
	public RemoveSellerResponse removeSeller(RemoveSellerRequest removeSellerRequest) {
		// TO DO.
		// ID 있나없나 확인.
		// ACTION 1. 회원 속성의 탈퇴정보를 update.
		// ACTION 2. 회원 이력 테이블 insert.

		RemoveSellerResponse removeSellerResponse = new RemoveSellerResponse();

		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey(removeSellerRequest.getSellerKey());

		String isRegistered = null;
		// 탈퇴 신청이거나 탈퇴 완료인경우에는 탈퇴신청이 되지 않도록 검색
		isRegistered = this.commonDAO.queryForObject("Seller.isRegisteredRemoveKey", sellerMbr, String.class);

		if (isRegistered != null) {
			// ACTION 1
			sellerMbr = new SellerMbr();
			sellerMbr.setSellerKey(removeSellerRequest.getSellerKey());
			sellerMbr.setSecedeReasonCode(removeSellerRequest.getSecedeReasonCode());
			sellerMbr.setSecedeReasonMessage(removeSellerRequest.getSecedeReasonMessage());
			sellerMbr.setSellerMainStatus(MainStateCode.SECEDE.getCode()); // 탈퇴 MAIN CODE US010202
			sellerMbr.setSellerSubStatus(SubStateCode.WITHDRAW_REQUEST.getCode()); // 탈퇴신청 SUB CODE US010302
			sellerMbr.setSecedePathCd(removeSellerRequest.getCommonRequest().getSystemID());

			// 판매자 이력 저장후
			// 이력 테이블 먼저 저장 한다.
			Integer row = this.commonDAO.update("Seller.updateSellerHistory", sellerMbr);
			LOGGER.debug("### row History : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}

			// 판매자 상태 탈퇴신청으로 변경
			row = this.commonDAO.update("Seller.removeSeller", sellerMbr);
			LOGGER.debug("### removeUser row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}

			// 2개의 테이블로 복사한다.
			// row = this.commonDAO.update("Seller.insertSellerWd", sellerMbr);
			// LOGGER.debug("### insertSellerWd row : {}", row);

			// row = this.commonDAO.update("Seller.insertSellerPrsnStWd", sellerMbr);
			// LOGGER.debug("### insertSellerPrsnStWd row : {}", row);

			sellerMbr.setParentSellerKey(removeSellerRequest.getSellerKey());
			sellerMbr.setSellerSubStatus(SubStateCode.WITHDRAW.getCode()); // 탈퇴완료 상태로 변경
			sellerMbr.setSellerKey(null);

			// 판매자 서브 계정의 이력도 저장한다.
			row = this.commonDAO.update("Seller.updateSubSellerHistory", sellerMbr);
			LOGGER.debug("### row sub History : {}", row);

			// 서브계정 삭제
			row = this.commonDAO.delete("Seller.removeSubSeller", sellerMbr);
			LOGGER.debug("### removeUser row : {}", row);

			BatCmsSellerMbr batCmsSellerMbr = new BatCmsSellerMbr();
			batCmsSellerMbr.setSellerKey(removeSellerRequest.getSellerKey());
			batCmsSellerMbr.setResultCode("PD003701");
			batCmsSellerMbr.setStatusType("DELETE");

			row = this.commonDAO.update("Seller.insertBatCmsSellerMbr", batCmsSellerMbr);
			LOGGER.debug("### 2  row : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

			// 판매자 회원 서류 삭제
			File docDir = new File(Constant.SELLER_DOCUMENT_PATH + removeSellerRequest.getSellerKey());

			try {
				if (docDir.exists() && docDir.isDirectory()) {
					FileUtils.deleteDirectory(docDir);
					LOGGER.info("{} : Delete seller document directory success {}  ",
							removeSellerRequest.getSellerKey(), docDir.getPath());
				}
			} catch (IOException e) {
				LOGGER.info("{} :  Delete seller document directory fail  {}  ", removeSellerRequest.getSellerKey(),
						docDir.getPath());
			}

		} else {
			// FAIL 처리
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		return removeSellerResponse;
	}

	/**
	 * <pre>
	 * 서브계정 등록을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param createSubSellerRequest
	 *            서브계정 등록 요청 Value Object
	 * @return createSubSellerResponse - 서브계정 등록 응답 Value Object
	 */
	@Override
	public CreateSubSellerResponse createSubSeller(CreateSubSellerRequest createSubSellerRequest) {
		// TO DO.
		// ID 있나없나 확인.
		// ACTION 1. 회원 정보 insert.

		CreateSubSellerResponse createSubSellerResponse = new CreateSubSellerResponse();
		Integer row = 0;

		// 신규 등록이면
		if (createSubSellerRequest.getIsNew().equals(Constant.TYPE_YN_Y)) {

			SellerMbr checkSellerMbrWd = new SellerMbr();
			checkSellerMbrWd.setSellerID(createSubSellerRequest.getSellerMbr().getSellerID());
			// wd 테이블에서도 검색해본다.
			checkSellerMbrWd = (SellerMbr) this.commonDAO.queryForObject("Seller.checkSellerMbrWd", checkSellerMbrWd);
			if (checkSellerMbrWd != null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.duplicatedMemberID", ""));
				// TB_US_SELLERMBR_WD 6개월이내 중복
			}

			checkSellerMbrWd = new SellerMbr();
			checkSellerMbrWd.setSellerID(createSubSellerRequest.getSellerMbr().getSellerID());
			ExistLimitWordMemberID existLimitWordMemberID = this.commonDAO.queryForObject("Seller.checkExistLimitWord",
					checkSellerMbrWd, ExistLimitWordMemberID.class);

			// ACTION 1-1. userID가 존재하는 경우 Fail 반환
			if (existLimitWordMemberID != null && existLimitWordMemberID.getExistWordID() != null
					&& existLimitWordMemberID.getExistWordID().length() > 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.duplicatedMemberID", ""));

			}

			// 기본정보 조회 후 등록 // 기본정보 가 없다면 오류처리
			SellerMbr parentSellerMbr = (SellerMbr) this.commonDAO.queryForObject("Seller.searchSubSellerMbr",
					createSubSellerRequest.getSellerMbr());
			if (parentSellerMbr == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
				// 부모 판매자 없음
			}

			int seq = this.commonDAO.queryForObject("Seller.getSellerSequence", null, Integer.class);
			String tempKey = "SS" + Utils.getLocalDateTime()
					+ Utils.leftPadStringWithChar(Integer.toString(seq), 7, '0');

			SellerMbr sellerMbr = createSubSellerRequest.getSellerMbr();
			sellerMbr.setSellerKey(tempKey);
			sellerMbr.setSellerClass(parentSellerMbr.getSellerClass());
			sellerMbr.setSellerCategory(parentSellerMbr.getSellerCategory());
			sellerMbr.setSellerMainStatus(parentSellerMbr.getSellerMainStatus());
			sellerMbr.setSellerSubStatus(parentSellerMbr.getSellerSubStatus());
			sellerMbr.setLoginStatusCode(parentSellerMbr.getLoginStatusCode());
			sellerMbr.setStopStatusCode(parentSellerMbr.getStopStatusCode());
			row = (Integer) this.commonDAO.insert("Seller.createSubSeller", sellerMbr);
			LOGGER.debug("### row : {}", row);

			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
			if (createSubSellerRequest.getMbrPwd() != null) {
				MbrPwd mbrPwd = createSubSellerRequest.getMbrPwd();
				mbrPwd.setMemberKey(tempKey);

				SHACipher cipher = new SHACipher();
				try {
					// mbrPwd.setMemberPW(cipher.getHash(mbrPwd.getMemberPW()));
					mbrPwd.setMemberPW(cipher.getMD5(mbrPwd.getMemberPW()));
					mbrPwd.setEncType("M");
				} catch (NoSuchAlgorithmException ex) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
				} catch (UnsupportedEncodingException ex) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
				} catch (StorePlatformException ex) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}

				row = (Integer) this.commonDAO.insert("Seller.insertPassword", mbrPwd);
				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}
			}
			createSubSellerResponse.setSellerKey(tempKey);
		} else {
			// 수정이라면 // 수정처리 // 이력을 저장해야 한다.
			// 비밀번호가 있다면 체크해야한다

			if (createSubSellerRequest.getMbrPwd() != null) {
				MbrPwd mbrPwd = createSubSellerRequest.getMbrPwd();
				SHACipher cipher = new SHACipher();
				try {
					// mbrPwd.setMemberPW(cipher.getHash(mbrPwd.getMemberPW()));
					mbrPwd.setMemberPW(cipher.getMD5(mbrPwd.getMemberPW()));
					// mbrPwd.setOldPW(cipher.getHash(mbrPwd.getOldPW()));
					mbrPwd.setMemberID(createSubSellerRequest.getSellerMbr().getSellerID());
					mbrPwd.setMemberKey(createSubSellerRequest.getSellerMbr().getSellerKey());
					mbrPwd.setEncType("M");

				} catch (NoSuchAlgorithmException ex) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
				} catch (UnsupportedEncodingException ex) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""), ex);
				} catch (StorePlatformException ex) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				}

				row = this.commonDAO.update("Seller.updatePasswordSellerKey", mbrPwd);

				if (row == 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
					// MbrPwd update row = 0
				}
			}

			// 이력 테이블 먼저 저장 한다.
			row = this.commonDAO.update("Seller.updateSellerHistory", createSubSellerRequest.getSellerMbr());
			LOGGER.debug("### row History : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				// sellerMbr HIS update row = 0
			}

			if (createSubSellerRequest.getSellerMbr().getSellerID() != null
					&& createSubSellerRequest.getSellerMbr().getSellerID().length() > 0) {
				createSubSellerRequest.getSellerMbr().setSellerID(null);
			}

			row = this.commonDAO.update("Seller.updateSeller", createSubSellerRequest.getSellerMbr());
			LOGGER.debug("### row : {}", row);
			if (row == 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
				// sellerMbr update row = 0
			}
			createSubSellerResponse.setSellerKey(createSubSellerRequest.getSellerMbr().getSellerKey());
		}

		return createSubSellerResponse;
	}

	/**
	 * <pre>
	 * 서브계정 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchSubListSellerRequest
	 *            서브계정 목록 조회 요청 Value Object
	 * @return searchSubListSellerRequest - 서브계정 목록 조회 응답 Value Object
	 */
	@Override
	public SearchSubSellerListResponse searchSubSellerList(SearchSubSellerListRequest searchSubListSellerRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("판매자 서비스 - 서브계정 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchSubListSellerRequest : {}", searchSubListSellerRequest);

		SearchSubSellerListResponse searchSubSellerListResponse;

		if (searchSubListSellerRequest.getLoginSort() == null) {
			searchSubListSellerRequest.setLoginSort("");
		}

		searchSubSellerListResponse = this.commonDAO.queryForObject("Seller.searchSubSellerList",
				searchSubListSellerRequest, SearchSubSellerListResponse.class);

		return searchSubSellerListResponse;

	}

	/**
	 * <pre>
	 * 서브계정 상세정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchSubSellerRequest
	 *            서브계정 상세정보 조회 요청 Value Object
	 * @return searchSubSellerResponse - 서브계정 상세정보 조회 응답 Value Object
	 */
	@Override
	public SearchSubSellerResponse searchSubSeller(SearchSubSellerRequest searchSubSellerRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("판매자 서비스 - 서브계정 상세 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchSubSellerRequest : {}", searchSubSellerRequest.toString());

		// 조회
		SearchSubSellerResponse searchSubSellerResponse = this.commonDAO.queryForObject("Seller.searchSubSeller",
				searchSubSellerRequest, SearchSubSellerResponse.class);

		return searchSubSellerResponse;
	}

	/**
	 * <pre>
	 * 서브계정 삭제를 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeSubSellerRequest
	 *            서브계정 삭제 요청 Value Object
	 * @return removeSubSellerResponse - 서브계정 삭제 응답 Value Object
	 */
	@Override
	public RemoveSubSellerResponse removeSubSeller(RemoveSubSellerRequest removeSubSellerRequest) {
		// TO DO.
		// ID 있나없나 확인.
		// ACTION 1. 회원 삭제. BOLTER 필드 업데이트
		// ACTION 2. 회원 탈퇴 테이블 insert. - 나중에 배치 작업
		// ACTION 2. 회원 이력 테이블 insert.

		LOGGER.debug(">>>> >>> SellerServiceImpl removeSubSeller : {}", removeSubSellerRequest);

		Integer row = 0;
		String isRegistered = null;
		int deletedNumber = 0;

		SellerMbr sellerMbr = null;
		RemoveSubSellerResponse removeSubSellerResponse = new RemoveSubSellerResponse();

		List<String> removeSubSellerKey = removeSubSellerRequest.getSellerKeyList();
		int sizeList = removeSubSellerKey.size();
		for (int i = 0; i < sizeList; i++) {
			// ID 있나없나 확인
			sellerMbr = new SellerMbr();
			sellerMbr.setParentSellerKey(removeSubSellerRequest.getParentSellerKey());
			sellerMbr.setSellerKey(removeSubSellerKey.get(i));
			isRegistered = this.commonDAO.queryForObject("Seller.isSubRegisteredKey", sellerMbr, String.class);
			if (isRegistered == null) {

				throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));

			}
		}

		for (int i = 0; i < sizeList; i++) {
			// ID 삭제 - BOLTER날짜 업데이트
			sellerMbr = new SellerMbr();
			sellerMbr.setParentSellerKey(removeSubSellerRequest.getParentSellerKey());
			sellerMbr.setSellerKey(removeSubSellerKey.get(i));

			// 이력 테이블 먼저 저장 한다.
			row = this.commonDAO.update("Seller.updateSellerHistory", sellerMbr);
			LOGGER.debug("### row History : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}

			row = this.commonDAO.delete("Seller.removeSubSeller", sellerMbr);
			LOGGER.debug("### removeUser row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));

			} else {
				deletedNumber = deletedNumber + row;
			}
		}
		removeSubSellerResponse.setDeletedNumber(deletedNumber);
		return removeSubSellerResponse;

	}

	/**
	 * <pre>
	 * 비밀번호 힌트 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchPwdHintListRequest
	 *            비밀번호 힌트 목록 조회 요청 Value Object
	 * @return searchPwdHintListResponse - 비밀번호 힌트 목록 조회 응답 Value Object
	 */
	@Override
	public SearchPwdHintListResponse searchPwdHintList(SearchPwdHintListRequest searchPwdHintListRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("판매자 서비스 - 비밀번호 힌트 목록 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchPwdHintList : {}", searchPwdHintListRequest);

		SearchPwdHintListResponse searchPwdHintListResponse;

		if (searchPwdHintListRequest.getQuestionID() == null) {
			SearchPwdHintListRequest searchPwdHintList = searchPwdHintListRequest;
			searchPwdHintList.setQuestionID("return");
			searchPwdHintListResponse = this.commonDAO.queryForObject("Seller.searchPwdHintList",
					searchPwdHintListRequest, SearchPwdHintListResponse.class);
		} else {
			searchPwdHintListResponse = this.commonDAO.queryForObject("Seller.searchPwdHintList",
					searchPwdHintListRequest, SearchPwdHintListResponse.class);
		}

		if (searchPwdHintListResponse == null || searchPwdHintListResponse.getPWReminderList() == null
				|| searchPwdHintListResponse.getPWReminderList().size() == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		return searchPwdHintListResponse;
	}

	/**
	 * <pre>
	 * 로그인 정보 등록/수정을 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateLoginInfoRequest
	 *            로그인 정보 등록/수정 요청 Value Object
	 * @return updateLoginInfoResponse - 로그인 정보 등록/수정 응답 Value Object
	 */
	@Override
	public UpdateLoginInfoResponse updateLoginInfo(UpdateLoginInfoRequest updateLoginInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("판매자 서비스 - 로그인 정보 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updateLoginInfo : {}", updateLoginInfoRequest);

		LoginInfo loginInfo = updateLoginInfoRequest.getLoginInfo();
		Integer row = this.commonDAO.update("Seller.updateLoginInfo", loginInfo);
		LOGGER.debug("### row : {}", row);
		if (row <= 0) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}
		UpdateLoginInfoResponse updateLoginInfoResponse = new UpdateLoginInfoResponse();
		updateLoginInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return updateLoginInfoResponse;
	}

	/**
	 * <pre>
	 * 로그인 정보를 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchLoginInfoRequest
	 *            로그인 정보 조회 요청 Value Object
	 * @return searchLoginInfoResponse - 로그인 정보 조회 응답 Value Object
	 */
	@Override
	public SearchLoginInfoResponse searchLoginInfo(SearchLoginInfoRequest searchLoginInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("판매자 서비스 - 로그인 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchLoginInfoRequest : {}", searchLoginInfoRequest);

		SearchLoginInfoResponse searchLoginInfoResponse = this.commonDAO.queryForObject("Seller.searchLoginInfo",
				searchLoginInfoRequest, SearchLoginInfoResponse.class);

		if (searchLoginInfoResponse == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		} else {
			/** 2014-09-01 (추가) - 서브계정으로 요청시, 서브계정여부 & 부모키 리턴 추가. */
			String upSellerKey = (String) this.commonDAO.queryForObject("Seller.isRegisteredParentKey",
					searchLoginInfoResponse.getLoginInfo().getSellerKey());
			if (upSellerKey != null) {
				LoginInfo loginInfo = searchLoginInfoResponse.getLoginInfo();
				// 부계정 여부 확인.
				loginInfo.setIsSubSeller(Constant.TYPE_YN_Y);
				// 부계정일 경우 - 부모 판매자키 설정.
				loginInfo.setParentSellerKey(upSellerKey);
				searchLoginInfoResponse.setLoginInfo(loginInfo);
			}
		}

		return searchLoginInfoResponse;
	}

	/**
	 * <pre>
	 * 로그인 정보 삭제 처리하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param removeLoginInfoRequest
	 *            로그인 정보 삭제 요청 Value Object
	 * @return removeLoginInfoResponse - 로그인 정보 삭제 응답 Value Object
	 */
	@Override
	public RemoveLoginInfoResponse removeLoginInfo(RemoveLoginInfoRequest removeLoginInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("판매자 서비스 - 로그인 정보 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### removeLoginInfoRequest : {}", removeLoginInfoRequest);

		LoginInfo loginInfo = removeLoginInfoRequest.getLoginInfo();
		// Integer row = this.commonDAO.delete("Seller.removeLoginInfo", loginInfo);
		Integer row = this.commonDAO.update("Seller.removeLoginInfo", loginInfo);

		LOGGER.debug("### row : {}", row);
		if (row <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
		}
		RemoveLoginInfoResponse removeLoginInfoResponse = new RemoveLoginInfoResponse();
		removeLoginInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return removeLoginInfoResponse;
	}

	/**
	 * <pre>
	 * 전체 비밀번호 힌트 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchPwdHintListAllRequest
	 *            전체 비밀번호 힌트 목록 조회 요청 Value Object
	 * @return searchPwdHintListAllResponse - 전체 비밀번호 힌트 목록 조회 응답 Value Object
	 */
	@Override
	public SearchPwdHintListAllResponse searchPwdHintListAll(SearchPwdHintListAllRequest searchPwdHintListAllRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("판매자 서비스 - 전체 비밀번호 힌트 목록 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchPwdHintList : {}", searchPwdHintListAllRequest);

		SearchPwdHintListAllResponse searchPwdHintListAllResponse = new SearchPwdHintListAllResponse();

		List<PWReminderAll> pWReminderAllList;
		pWReminderAllList = (List<PWReminderAll>) this.commonDAO.queryForList("Seller.searchPasswordReminderListAll",
				searchPwdHintListAllRequest);

		searchPwdHintListAllResponse.setPWReminderAllList(pWReminderAllList);

		if (searchPwdHintListAllResponse == null || searchPwdHintListAllResponse.getPWReminderAllList() == null
				|| searchPwdHintListAllResponse.getPWReminderAllList().size() == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		return searchPwdHintListAllResponse;
	}

	/**
	 * <pre>
	 * 판매자 약관동의 등록/수정.
	 * </pre>
	 * 
	 * @param updateAgreementSellerRequest
	 *            판매자 약관동의 등록/수정 요청 Value Object
	 * @return UpdateAgreementSellerResponse - 판매자 약관동의 등록/수정 응답 Value Object
	 */
	@Override
	public UpdateAgreementSellerResponse updateAgreementSeller(UpdateAgreementSellerRequest updateAgreementSellerRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("판매자 서비스 - 판매자 약관동의 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### updateRightRequest : {}", updateAgreementSellerRequest);
		Integer row = 0;
		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey(updateAgreementSellerRequest.getSellerKey());

		String isRegistered = null;
		isRegistered = this.commonDAO.queryForObject("Seller.isRegisteredKey", sellerMbr, String.class);
		LOGGER.debug("### isRegisteredKey : {}", isRegistered);

		// ACTION 1. 존재하지 않는 회원키는 에러코드 반환
		if (isRegistered == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		UpdateAgreementSellerResponse updateAgreementSellerResponse = new UpdateAgreementSellerResponse();
		updateAgreementSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));

		// 요청
		List<MbrClauseAgree> mbrClauseAgreeList = updateAgreementSellerRequest.getMbrClauseAgreeList();
		for (int i = 0; i < mbrClauseAgreeList.size(); i++) {
			MbrClauseAgree mbrClauseAgree = mbrClauseAgreeList.get(i);
			mbrClauseAgree.setMemberKey(updateAgreementSellerRequest.getSellerKey());

			row = this.commonDAO.update("Seller.updateAgreementSeller", mbrClauseAgree);
			LOGGER.debug("### row : {}", row);
			if (row <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		}

		BatCmsSellerMbr batCmsSellerMbr = new BatCmsSellerMbr();
		batCmsSellerMbr.setSellerKey(isRegistered);
		// CMS - UPDATE
		batCmsSellerMbr.setStatusType("UPDATE");
		batCmsSellerMbr.setResultCode("PD003701");

		row = this.commonDAO.update("Seller.insertBatCmsSellerMbr", batCmsSellerMbr);
		LOGGER.debug("### 2  row : {}", row);
		if (row == 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
		}

		updateAgreementSellerResponse.setSellerKey(updateAgreementSellerRequest.getSellerKey());
		return updateAgreementSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자 약관동의 목록 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchAgreementListSellerRequest
	 *            판매자 약관동의 목록 요청 Value Object
	 * @return SearchAgreementListSellerResponse - 판매자 약관동의 목록 응답 Value Object
	 */
	@Override
	public SearchAgreementListSellerResponse searchAgreementListSeller(
			SearchAgreementListSellerRequest searchAgreementListSellerRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 판매자 약관동의 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchAgreementListRequest : {}", searchAgreementListSellerRequest);

		SellerMbr sellerMbr = new SellerMbr();
		sellerMbr.setSellerKey(searchAgreementListSellerRequest.getSellerKey());

		String isRegistered = null;
		isRegistered = this.commonDAO.queryForObject("Seller.isRegisteredKey", sellerMbr, String.class);
		LOGGER.debug("### isRegisteredKey : {}", isRegistered);

		// ACTION 1. 존재하지 않는 회원키는 에러코드 반환
		if (isRegistered == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		SearchAgreementListSellerResponse searchAgreementListSellerResponse;
		searchAgreementListSellerResponse = this.commonDAO.queryForObject("Seller.searchAgreementListSeller",
				searchAgreementListSellerRequest, SearchAgreementListSellerResponse.class);

		if (searchAgreementListSellerResponse == null
				|| searchAgreementListSellerResponse.getMbrClauseAgreeList() == null
				|| searchAgreementListSellerResponse.getMbrClauseAgreeList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
		}

		searchAgreementListSellerResponse.setSellerKey(searchAgreementListSellerRequest.getSellerKey());
		searchAgreementListSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
				"response.ResultMessage.success"));
		return searchAgreementListSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자회원 제공자 정보 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchProviderRequest
	 *            판매자회원 제공자 정보 조회 요청 Value Object
	 * @return SearchProviderResponse - 판매자회원 제공자 정보 조회 응답 Value Object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SearchProviderResponse searchProviderInfo(SearchProviderRequest searchProviderRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("서비스 - 판매자회원 제공자 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### searchProviderRequest : {}", searchProviderRequest);

		String categoryCd = searchProviderRequest.getCategoryCd();

		// locaSCI에서는 categoryCd 유효성검사가 없음. 강제로 고정
		if (StringUtil.isEmpty(categoryCd))
			categoryCd = "DE120204";

		if (categoryCd.equals("DP18"))
			categoryCd = "DE120201"; // 방송
		else if (categoryCd.equals("DP17"))
			categoryCd = "DE120202"; // 영화
		else if (categoryCd.equals("DP14"))
			categoryCd = "DE120203"; // 만화
		else if (categoryCd.equals("DP13"))
			categoryCd = "DE120204"; // 이북
		else if (categoryCd.equals("DP26"))
			categoryCd = "DE120206"; // 웹툰
		else if (categoryCd.equals("DP28"))
			categoryCd = "US011002"; // 쇼핑
		else if (categoryCd.equals("DP30"))
			categoryCd = ""; // 앱
		else if (categoryCd.equals("DP01"))
			categoryCd = ""; // 게임

		searchProviderRequest.setCategoryCd(categoryCd);

		// 판매자가 등록된 카테고리 목록이 있으면 해당 카테고리의 제공자 정보 조회
		SearchProviderResponse searchProviderResponse = new SearchProviderResponse();
		searchProviderResponse = this.commonDAO.queryForObject("Seller.searchProviderInfo", searchProviderRequest,
				SearchProviderResponse.class);
		LOGGER.debug("### providerInfo : {}", searchProviderResponse);

		if (searchProviderResponse != null) {
			searchProviderResponse.setSellerKey(searchProviderRequest.getSellerKey());
			searchProviderResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
					"response.ResultMessage.success"));
		}

		return searchProviderResponse;
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
}
