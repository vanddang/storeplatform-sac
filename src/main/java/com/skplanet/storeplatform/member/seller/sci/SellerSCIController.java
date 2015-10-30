/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.seller.sci;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckDuplicationSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CheckPasswordReminderSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSubSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LoginSellerResponse;
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
import com.skplanet.storeplatform.member.seller.service.SellerService;

/**
 * 판매자 기능을 제공하는 Controller.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@LocalSCI
public class SellerSCIController implements SellerSCI {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSCIController.class);

	@Autowired
	private SellerService service;

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
		CreateSellerResponse createSellerResponse;

		// 입력 파라미터가 없음
		if (createSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		LOGGER.debug("### create서버에서 찍음 데이터 : {}", createSellerRequest.toString());

		// 공통 파라미터 없음
		if (createSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (createSellerRequest.getCommonRequest().getTenantID() == null
				|| createSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 (검색 keyType , keyString 없음)
		if (createSellerRequest.getSellerMbr().getSellerID() == null
				|| createSellerRequest.getSellerMbr().getSellerID().length() <= 0
				|| createSellerRequest.getMbrPwd().getMemberPW() == null
				|| createSellerRequest.getMbrPwd().getMemberPW().length() <= 0
				|| createSellerRequest.getSellerMbr().getSellerMainStatus() == null
				|| createSellerRequest.getSellerMbr().getSellerMainStatus().length() <= 0
				|| createSellerRequest.getSellerMbr().getSellerCategory() == null
				|| createSellerRequest.getSellerMbr().getSellerCategory().length() <= 0
				|| createSellerRequest.getSellerMbr().getSellerClass() == null
				|| createSellerRequest.getSellerMbr().getSellerClass().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 실명인증 필수 파라미터 확인 (user:'US010801' , seller:'US010802')
		if (createSellerRequest.getMbrAuth() != null) {
			if (createSellerRequest.getMbrAuth().getIsRealName() == null
					|| createSellerRequest.getMbrAuth().getIsRealName().length() <= 0
					|| createSellerRequest.getMbrAuth().getMemberCategory() == null
					|| createSellerRequest.getMbrAuth().getMemberCategory().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
				// "MbrAuth (IsRealName,MemberCategory)"
			}
		}

		// 비밀번호 보안질문 필수 파라미터 확인
		if (createSellerRequest.getPWReminderList() != null) {

			for (int i = 0; i < createSellerRequest.getPWReminderList().size(); i++) {
				if (createSellerRequest.getPWReminderList().get(i).getQuestionID() == null
						|| createSellerRequest.getPWReminderList().get(i).getQuestionID().length() <= 0
						|| createSellerRequest.getPWReminderList().get(i).getAnswerString() == null
						|| createSellerRequest.getPWReminderList().get(i).getAnswerString().length() <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
					// PWReminderList( QuestionID ,AnswerString)
				}
			}

		}

		try {
			createSellerResponse = this.service.createSeller(createSellerRequest);
			if (createSellerResponse != null) {
				if (createSellerResponse.getCommonResponse() == null) {
					createSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
							"response.ResultMessage.success"));
				}
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
	public LoginSellerResponse loginSeller(LoginSellerRequest loginSellerRequest) {

		LoginSellerResponse loginSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		if (loginSellerRequest.getSellerID() == null || loginSellerRequest.getSellerID().length() <= 0) {
			loginSellerResponse = new LoginSellerResponse();
			loginSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.memberKeyNotFound",
					"response.ResultMessage.memberKeyNotFound"));
			return loginSellerResponse;
		}
		if (loginSellerRequest.getSellerPW() == null || loginSellerRequest.getSellerPW().length() <= 0) {
			loginSellerResponse = new LoginSellerResponse();
			loginSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.memberKeyNotFound",
					"response.ResultMessage.memberKeyNotFound"));
			return loginSellerResponse;
		}

		try {
			loginSellerResponse = this.service.updateLoginSeller(loginSellerRequest);

			if (loginSellerResponse != null) {

				if (loginSellerResponse.getCommonResponse() == null) {
					loginSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
							"response.ResultMessage.success"));
				}

			} else {
				loginSellerResponse = new LoginSellerResponse();
				loginSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.fail",
						"response.ResultMessage.fail"));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// loginSellerResponse = new LoginSellerResponse();
		// commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.unknownErr", null,
		// "9991", LocaleContextHolder.getLocale()));
		// commonResponse.setResultMessage(ex.toString());
		// loginSellerResponse.setCommonResponse(commonResponse);
		// }

		return loginSellerResponse;
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

		SearchMbrSellerResponse searchMbrSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (searchMbrSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchMbrSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchMbrSellerRequest.getCommonRequest().getTenantID() == null
				|| searchMbrSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 (검색 keyType , keyString 없음)
		if (searchMbrSellerRequest.getKeySearchList() == null || searchMbrSellerRequest.getKeySearchList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 잘못된 키 값 (INSD_SELLERMBR_NO ,SELLERMBR_ID ,EMAIL ,WILS_TEL_NO) 만 허용
		for (int i = 0; i < searchMbrSellerRequest.getKeySearchList().size(); i++) {
			if (searchMbrSellerRequest.getKeySearchList().get(i).getKeyType().equals("INSD_SELLERMBR_NO")
					|| searchMbrSellerRequest.getKeySearchList().get(i).getKeyType().equals("SELLERMBR_ID")
					|| searchMbrSellerRequest.getKeySearchList().get(i).getKeyType().equals("EMAIL")
					|| searchMbrSellerRequest.getKeySearchList().get(i).getKeyType().equals("BIZ_REG_NO")
					|| searchMbrSellerRequest.getKeySearchList().get(i).getKeyType().equals("WILS_TEL_NO")) {
				;
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			}
		}

		try {
			searchMbrSellerResponse = this.service.searchMbrSeller(searchMbrSellerRequest);
			if (searchMbrSellerResponse != null) {
				searchMbrSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

		return searchMbrSellerResponse;

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

		SearchSellerResponse searchSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (searchSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchSellerRequest.getCommonRequest().getTenantID() == null
				|| searchSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 (검색 keyType , keyString 없음)
		if (searchSellerRequest.getKeySearchList() == null || searchSellerRequest.getKeySearchList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 잘못된 키 값 (INSD_SELLERMBR_NO ,SELLERMBR_ID ,EMAIL ,WILS_TEL_NO) 만 허용
		for (int i = 0; i < searchSellerRequest.getKeySearchList().size(); i++) {
			if (searchSellerRequest.getKeySearchList().get(i).getKeyType().equals("INSD_SELLERMBR_NO")
					|| searchSellerRequest.getKeySearchList().get(i).getKeyType().equals("SELLERMBR_ID")
					|| searchSellerRequest.getKeySearchList().get(i).getKeyType().equals("EMAIL")
					|| searchSellerRequest.getKeySearchList().get(i).getKeyType().equals("BIZ_REG_NO")
					|| searchSellerRequest.getKeySearchList().get(i).getKeyType().equals("WILS_TEL_NO")) {
				;
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			}
		}

		try {
			searchSellerResponse = this.service.searchSeller(searchSellerRequest);
			if (searchSellerResponse != null) {
				searchSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

		return searchSellerResponse;

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

		SearchAccountSellerResponse searchAccountSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (searchAccountSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchAccountSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchAccountSellerRequest.getCommonRequest().getTenantID() == null
				|| searchAccountSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey
		if (searchAccountSellerRequest.getSellerKey() == null
				|| searchAccountSellerRequest.getSellerKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			searchAccountSellerResponse = this.service.searchAccountSeller(searchAccountSellerRequest);

			if (searchAccountSellerResponse != null) {
				searchAccountSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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

		UpdateSellerResponse updateSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (updateSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateSellerRequest.getCommonRequest().getTenantID() == null
				|| updateSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey
		if (updateSellerRequest.getSellerMbr().getSellerKey() == null
				|| updateSellerRequest.getSellerMbr().getSellerKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		if (updateSellerRequest.getMbrLglAgent() != null) { // 필수 파라미터 없음, ci, 법정대리인 인증방법코드
			if (updateSellerRequest.getMbrLglAgent().getParentName() == null
					|| updateSellerRequest.getMbrLglAgent().getParentName().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
		}

		/*
		 * // 실명인증 데이터 if (updateSellerRequest.getMbrAuth() != null) { // 필수 파라미터 없음, ci if
		 * (updateSellerRequest.getMbrAuth().getCi() == null || updateSellerRequest.getMbrAuth().getCi().length() <= 0)
		 * { throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", "")); //
		 * MbrAuth(Ci) } }
		 */

		// 비밀번호 보안질문 필수 파라미터 확인
		if (updateSellerRequest.getPWReminderList() != null) {

			for (int i = 0; i < updateSellerRequest.getPWReminderList().size(); i++) {
				if (updateSellerRequest.getPWReminderList().get(i).getQuestionID() == null
						|| updateSellerRequest.getPWReminderList().get(i).getQuestionID().length() <= 0
						|| updateSellerRequest.getPWReminderList().get(i).getAnswerString() == null
						|| updateSellerRequest.getPWReminderList().get(i).getAnswerString().length() <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
					// PWReminderList( QuestionID ,AnswerString)
				}
			}

		}

		try {
			updateSellerResponse = this.service.updateSeller(updateSellerRequest);

			if (updateSellerResponse != null) {
				updateSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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

		UpdateAccountSellerResponse updateAccountSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (updateAccountSellerRequest == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateAccountSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateAccountSellerRequest.getCommonRequest().getTenantID() == null
				|| updateAccountSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey ,sellerAccount
		if (updateAccountSellerRequest.getSellerKey() == null
				|| updateAccountSellerRequest.getSellerKey().length() <= 0
				|| updateAccountSellerRequest.getSellerAccount() == null
				|| updateAccountSellerRequest.getSellerMbr().getSellerKey() == null
				|| updateAccountSellerRequest.getSellerMbr().getSellerKey().length() <= 0
				|| updateAccountSellerRequest.getSellerAccount().getSellerKey() == null
				|| updateAccountSellerRequest.getSellerAccount().getSellerKey().length() <= 0) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// SellerKey,SellerAccount(SellerKey),SellerMbr(SellerKey)

		}

		// DOC 필수 파라미터 확인 & 개인판매자 BSS 연동 삭제로 옵션변경(2015-05-27)
		// if (updateAccountSellerRequest.getDocumentList() != null
		// && updateAccountSellerRequest.getDocumentList().size() > 0) {
		// for (int i = 0; i < updateAccountSellerRequest.getDocumentList().size(); i++) {
		// if (updateAccountSellerRequest.getDocumentList().get(i).getDocumentCode() == null
		// || updateAccountSellerRequest.getDocumentList().get(i).getDocumentCode().length() <= 0
		// || updateAccountSellerRequest.getDocumentList().get(i).getIsUsed() == null
		// || updateAccountSellerRequest.getDocumentList().get(i).getIsUsed().length() <= 0) {
		//
		// throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		// // DocumentList (DocumentCode,IsUsed)
		//
		// }
		// }
		// }

		try {
			updateAccountSellerResponse = this.service.updateAccountSeller(updateAccountSellerRequest);

			if (updateAccountSellerResponse != null) {
				updateAccountSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }
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

		SearchIDSellerResponse searchIDSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (searchIDSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchIDSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchIDSellerRequest.getCommonRequest().getTenantID() == null
				|| searchIDSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey ,sellerAccount
		if ((searchIDSellerRequest.getSellerEmail() == null || searchIDSellerRequest.getSellerEmail().length() <= 0)
				&& (searchIDSellerRequest.getSellerCompany() == null || searchIDSellerRequest.getSellerCompany()
						.length() <= 0)
				&& (searchIDSellerRequest.getSellerBizNumber() == null || searchIDSellerRequest.getSellerBizNumber()
						.length() <= 0)
				&& (searchIDSellerRequest.getSellerPhone() == null || searchIDSellerRequest.getSellerPhone().length() <= 0)) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			searchIDSellerResponse = this.service.searchIDSeller(searchIDSellerRequest);

			if (searchIDSellerResponse != null) {
				searchIDSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {

				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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

		UpdateStatusSellerResponse updateStatusSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (updateStatusSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateStatusSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateStatusSellerRequest.getCommonRequest().getTenantID() == null
				|| updateStatusSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 ( 없음)
		if (updateStatusSellerRequest.getSellerID() == null || updateStatusSellerRequest.getSellerID().equals("")) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			updateStatusSellerResponse = this.service.updateStatusSeller(updateStatusSellerRequest);

			if (updateStatusSellerResponse != null) {
				updateStatusSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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

		CheckDuplicationSellerResponse checkDuplicationSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (checkDuplicationSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (checkDuplicationSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (checkDuplicationSellerRequest.getCommonRequest().getTenantID() == null
				|| checkDuplicationSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 (검색 keyType , keyString 없음)
		if (checkDuplicationSellerRequest.getKeySearchList() == null
				|| checkDuplicationSellerRequest.getKeySearchList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 잘못된 키 값 (INSD_SELLERMBR_NO ,SELLERMBR_ID ,EMAIL ,WILS_TEL_NO) 만 허용
		for (int i = 0; i < checkDuplicationSellerRequest.getKeySearchList().size(); i++) {
			if (checkDuplicationSellerRequest.getKeySearchList().get(i).getKeyType().equals("INSD_SELLERMBR_NO")
					|| checkDuplicationSellerRequest.getKeySearchList().get(i).getKeyType().equals("SELLERMBR_ID")
					|| checkDuplicationSellerRequest.getKeySearchList().get(i).getKeyType().equals("EMAIL")
					|| checkDuplicationSellerRequest.getKeySearchList().get(i).getKeyType().equals("WILS_TEL_NO")) {
				;
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
			}
		}

		try {
			checkDuplicationSellerResponse = this.service.checkDuplicationSeller(checkDuplicationSellerRequest);
			if (checkDuplicationSellerResponse != null) {
				checkDuplicationSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				// checkDuplicationSellerResponse = new CheckDuplicationSellerResponse();
				// checkDuplicationSellerResponse.setIsRegistered(Constant.TYPE_YN_N);
				// checkDuplicationSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.fail",
				// "response.ResultMessage.fail"));
				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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

		CheckPasswordReminderSellerResponse checkPasswordReminderSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (checkPasswordReminderSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (checkPasswordReminderSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (checkPasswordReminderSellerRequest.getCommonRequest().getTenantID() == null
				|| checkPasswordReminderSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 (PWReminderList)
		if (checkPasswordReminderSellerRequest.getPWReminderList() == null
				|| checkPasswordReminderSellerRequest.getSellerID() == null
				|| checkPasswordReminderSellerRequest.getSellerID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// PWReminderList,SellerID
		}

		// 필수 파라미터 없음 (PWReminderList)
		for (int i = 0; i < checkPasswordReminderSellerRequest.getPWReminderList().size(); i++) {
			if (checkPasswordReminderSellerRequest.getPWReminderList().get(i).getQuestionID() == null
					|| checkPasswordReminderSellerRequest.getPWReminderList().get(i).getQuestionID().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
				// PWReminderList(QuestionID)

			}
		}

		// 필수 파라미터 중복 체크 (PWReminderList)
		for (int i = 0; i < checkPasswordReminderSellerRequest.getPWReminderList().size(); i++) {
			if ((checkPasswordReminderSellerRequest.getPWReminderList().get(i).getQuestionMessage() != null && checkPasswordReminderSellerRequest
					.getPWReminderList().get(i).getQuestionMessage().length() > 0)
					&& (checkPasswordReminderSellerRequest.getPWReminderList().get(i).getAnswerString() != null && checkPasswordReminderSellerRequest
							.getPWReminderList().get(i).getAnswerString().length() > 0)) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
				// PWReminderList(QuestionMessage or AnswerString) one input
			}
		}

		try {
			checkPasswordReminderSellerResponse = this.service
					.checkPasswordReminderSeller(checkPasswordReminderSellerRequest);

			if (checkPasswordReminderSellerResponse != null) {
				if (checkPasswordReminderSellerResponse.getCommonResponse() == null) {
					checkPasswordReminderSellerResponse.setCommonResponse(this.getErrorResponse(
							"response.ResultCode.success", "response.ResultMessage.success"));
				}
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
	public ResetPasswordSellerResponse resetPasswordSeller(ResetPasswordSellerRequest resetPasswordSellerRequest) {

		ResetPasswordSellerResponse resetPasswordSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (resetPasswordSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (resetPasswordSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (resetPasswordSellerRequest.getCommonRequest().getTenantID() == null
				|| resetPasswordSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 (검색 keyType , keyString 없음)
		if (resetPasswordSellerRequest.getMbrPwd().getMemberID() == null
				|| resetPasswordSellerRequest.getMbrPwd().getMemberID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// MbrPwd(MemberID)
		}

		try {
			resetPasswordSellerResponse = this.service.updateResetPasswordSeller(resetPasswordSellerRequest);

			if (resetPasswordSellerResponse != null) {
				resetPasswordSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }
		return resetPasswordSellerResponse;

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

		UpdatePasswordSellerResponse updatePasswordSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (updatePasswordSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updatePasswordSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updatePasswordSellerRequest.getCommonRequest().getTenantID() == null
				|| updatePasswordSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 ( 없음)
		if (updatePasswordSellerRequest.getMbrPwd().getMemberID() == null
				|| updatePasswordSellerRequest.getMbrPwd().getMemberID().length() <= 0
				|| updatePasswordSellerRequest.getMbrPwd().getOldPW() == null
				|| updatePasswordSellerRequest.getMbrPwd().getOldPW().length() <= 0
				|| updatePasswordSellerRequest.getMbrPwd().getMemberPW() == null
				|| updatePasswordSellerRequest.getMbrPwd().getMemberPW().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			updatePasswordSellerResponse = this.service.updatePasswordSeller(updatePasswordSellerRequest);

			if (updatePasswordSellerResponse != null) {
				updatePasswordSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }
		return updatePasswordSellerResponse;
	}

	/**
	 * <pre>
	 * 판매자회원 새로운 비밀번호를 변경하는 기능을 제공한다.
	 * </pre>
	 * 
	 * @param updateNewPasswordSellerRequest
	 *            새로운 비밀번호 변경 Value Object
	 * @return updateNewPasswordSellerResponse - 새로운 비밀번호 변경 응답 Value Object
	 */
	@Override
	public UpdateNewPasswordSellerResponse updateNewPasswordSeller(
			UpdateNewPasswordSellerRequest updateNewPasswordSellerRequest) {

		UpdateNewPasswordSellerResponse updateNewPasswordSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (updateNewPasswordSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateNewPasswordSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateNewPasswordSellerRequest.getCommonRequest().getTenantID() == null
				|| updateNewPasswordSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 ( 없음)
		if (updateNewPasswordSellerRequest.getMbrPwd().getMemberID() == null
				|| updateNewPasswordSellerRequest.getMbrPwd().getMemberID().length() <= 0
				|| updateNewPasswordSellerRequest.getMbrPwd().getMemberPW() == null
				|| updateNewPasswordSellerRequest.getMbrPwd().getMemberPW().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			updateNewPasswordSellerResponse = this.service.updateNewPasswordSeller(updateNewPasswordSellerRequest);

			if (updateNewPasswordSellerResponse != null) {
				updateNewPasswordSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }
		return updateNewPasswordSellerResponse;
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
	// LOGGER.debug("컨트롤러 - 약관동의 등록/수정");
	// LOGGER.debug("==================================================================================\n\n\n\n\n");
	//
	// LOGGER.debug("### 받은 데이터 : {}", updateAgreementSellerRequest);
	//
	// UpdateAgreementSellerResponse updateAgreementSellerResponse = new UpdateAgreementSellerResponse();
	// CommonResponse commonResponse = new CommonResponse();
	//
	// // 입력 파라미터가 없음
	// if (updateAgreementSellerRequest == null) {
	// updateAgreementSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.inputNotFound",
	// "response.ResultMessage.inputNotFound"));
	// return updateAgreementSellerResponse;
	// }
	//
	// // 공통 파라미터 없음
	// if (updateAgreementSellerRequest.getCommonRequest() == null) {
	// updateAgreementSellerResponse = new UpdateAgreementSellerResponse();
	// updateAgreementSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.commonNotFound",
	// "response.ResultMessage.commonNotFound"));
	// return updateAgreementSellerResponse;
	// }
	//
	// // 필수 파라미터 없음, sellerKey
	// if (updateAgreementSellerRequest.getSellerKey() == null
	// || updateAgreementSellerRequest.getSellerKey().length() <= 0) {
	// updateAgreementSellerResponse.setCommonResponse(this.getErrorResponse(
	// "response.ResultCode.mandatoryNotFound", "response.ResultMessage.mandatoryNotFound"));
	// return updateAgreementSellerResponse;
	// }
	//
	// try {
	// updateAgreementSellerResponse = this.service.updateAgreementSeller(updateAgreementSellerRequest);
	// if (updateAgreementSellerResponse != null) {
	// updateAgreementSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
	// "response.ResultMessage.success"));
	// } else {
	// updateAgreementSellerResponse = new UpdateAgreementSellerResponse();
	// updateAgreementSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.fail",
	// "response.ResultMessage.fail"));
	// return updateAgreementSellerResponse;
	// }
	//
	// } catch (Exception ex) {
	// updateAgreementSellerResponse = new UpdateAgreementSellerResponse();
	// commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.unknownErr", null,
	// "9991", LocaleContextHolder.getLocale()));
	// commonResponse.setResultMessage(ex.toString());
	// updateAgreementSellerResponse.setCommonResponse(commonResponse);
	// }
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
	// LOGGER.debug("컨트롤러 - 판매자 약관동의 목록 조회");
	// LOGGER.debug("==================================================================================\n\n\n\n\n");
	//
	// LOGGER.debug("### 받은 데이터 : {}", searchAgreementListSellerRequest);
	//
	// SearchAgreementListSellerResponse searchAgreementListSellerResponse = new SearchAgreementListSellerResponse();
	// CommonResponse commonResponse = new CommonResponse();
	//
	// // 입력 파라미터가 없음
	// if (searchAgreementListSellerRequest == null) {
	// searchAgreementListSellerResponse.setCommonResponse(this.getErrorResponse(
	// "response.ResultCode.inputNotFound", "response.ResultMessage.inputNotFound"));
	// return searchAgreementListSellerResponse;
	// }
	//
	// // 공통 파라미터 없음
	// if (searchAgreementListSellerRequest.getCommonRequest() == null) {
	// searchAgreementListSellerResponse.setCommonResponse(this.getErrorResponse(
	// "response.ResultCode.commonNotFound", "response.ResultMessage.commonNotFound"));
	// return searchAgreementListSellerResponse;
	// }
	//
	// // 필수 파라미터 없음, sellerKey
	// if (searchAgreementListSellerRequest.getSellerKey() == null
	// || searchAgreementListSellerRequest.getSellerKey().length() <= 0) {
	// searchAgreementListSellerResponse.setCommonResponse(this.getErrorResponse(
	// "response.ResultCode.mandatoryNotFound", "response.ResultMessage.mandatoryNotFound"));
	// return searchAgreementListSellerResponse;
	// }
	//
	// try {
	//
	// searchAgreementListSellerResponse = this.service
	// .searchAgreementListSeller(searchAgreementListSellerRequest);
	// if (searchAgreementListSellerResponse != null) {
	// searchAgreementListSellerResponse.setCommonResponse(this.getErrorResponse(
	// "response.ResultCode.success", "response.ResultMessage.success"));
	// } else {
	// searchAgreementListSellerResponse = new SearchAgreementListSellerResponse();
	// searchAgreementListSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.fail",
	// "response.ResultMessage.fail"));
	// }
	// } catch (Exception ex) {
	// searchAgreementListSellerResponse = new SearchAgreementListSellerResponse();
	// commonResponse.setResultCode(this.messageSourceAccessor.getMessage("response.ResultCode.unknownErr", null,
	// "9991", LocaleContextHolder.getLocale()));
	// commonResponse.setResultMessage(ex.toString());
	// searchAgreementListSellerResponse.setCommonResponse(commonResponse);
	// return searchAgreementListSellerResponse;
	// }
	// return searchAgreementListSellerResponse;
	//
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
		LOGGER.debug("컨트롤러 - 판매자 실명인증 정보 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 : {}", updateRealNameSellerRequest);

		UpdateRealNameSellerResponse updateRealNameSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();
		// 입력 파라미터가 없음
		if (updateRealNameSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateRealNameSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateRealNameSellerRequest.getCommonRequest().getTenantID() == null
				|| updateRealNameSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey, isOwn
		if (updateRealNameSellerRequest.getSellerKey() == null
				|| updateRealNameSellerRequest.getSellerKey().length() <= 0
				|| updateRealNameSellerRequest.getIsOwn() == null
				|| updateRealNameSellerRequest.getIsOwn().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 실명인증 대상이 본인인 경우
		if (updateRealNameSellerRequest.getIsOwn().equalsIgnoreCase(Constant.REAL_NAME_TYPE_OWN)) {
			// 실명인증 데이터
			if (updateRealNameSellerRequest.getMbrAuth() == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
				// MbrAuth
			}
			/*
			 * // 필수 파라미터 없음, ci if (updateRealNameSellerRequest.getMbrAuth().getCi() == null ||
			 * updateRealNameSellerRequest.getMbrAuth().getCi().length() <= 0) {
			 * 
			 * throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", "")); //
			 * MbrAuth(Ci)
			 * 
			 * }
			 */
		}

		// 실명인증 대상이 법정대리인(부모)인 경우
		if (updateRealNameSellerRequest.getIsOwn().equalsIgnoreCase(Constant.REAL_NAME_TYPE_PARANT)) {
			// 실명인증 데이터
			if (updateRealNameSellerRequest.getMbrLglAgent() == null) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
				// MbrLglAgent
			}

			if (updateRealNameSellerRequest.getMbrLglAgent() != null) { // 필수 파라미터 없음, ci, 법정대리인 인증방법코드
				if (updateRealNameSellerRequest.getMbrLglAgent().getParentName() == null
						|| updateRealNameSellerRequest.getMbrLglAgent().getParentName().length() <= 0) {
					throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
				}
			}

			//
			// // 필수 파라미터 없음, ci, 법정대리인 인증방법코드
			// if (updateRealNameSellerRequest.getMbrLglAgent().getParentRealNameMethod() == null
			// || updateRealNameSellerRequest.getMbrLglAgent().getParentRealNameMethod().length() <= 0) {
			// throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// // MbrLglAgent (ParentRealNameMethod)
			//
			// }
		}

		try {
			updateRealNameSellerResponse = this.service.updateRealNameSeller(updateRealNameSellerRequest);

			if (updateRealNameSellerResponse != null) {
				updateRealNameSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {

				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
	public UpgradeSellerResponse upgradeSeller(UpgradeSellerRequest upgradeSellerRequest) {

		UpgradeSellerResponse upgradeSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (upgradeSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (upgradeSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (upgradeSellerRequest.getCommonRequest().getTenantID() == null
				|| upgradeSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey
		if (upgradeSellerRequest.getSellerKey() == null || upgradeSellerRequest.getSellerKey().length() <= 0
				|| upgradeSellerRequest.getSellerMbr().getSellerKey() == null
				|| upgradeSellerRequest.getSellerMbr().getSellerKey().length() <= 0
				|| upgradeSellerRequest.getSellerUpgrade().getBankAccount() == null
				|| upgradeSellerRequest.getSellerUpgrade().getBankAccount().length() <= 0
				|| upgradeSellerRequest.getSellerUpgrade().getSellerKey() == null
				|| upgradeSellerRequest.getSellerUpgrade().getSellerKey().length() <= 0
				|| upgradeSellerRequest.getSellerUpgrade().getSellerClassTo() == null
				|| upgradeSellerRequest.getSellerUpgrade().getSellerClassTo().length() <= 0) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// SellerKey,SellerMbr(SellerKey),SellerUpgrade(BankAccount,SellerKey,SellerClassTo)
		}

		// DOC 필수 파라미터 확인
		if (upgradeSellerRequest.getDocumentList() != null && upgradeSellerRequest.getDocumentList().size() > 0) {
			for (int i = 0; i < upgradeSellerRequest.getDocumentList().size(); i++) {
				if (upgradeSellerRequest.getDocumentList().get(i).getDocumentCode() == null
						|| upgradeSellerRequest.getDocumentList().get(i).getDocumentCode().length() <= 0
						|| upgradeSellerRequest.getDocumentList().get(i).getIsUsed() == null
						|| upgradeSellerRequest.getDocumentList().get(i).getIsUsed().length() <= 0) {

					throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
					// DocumentList (DocumentCode,IsUsed)

				}
			}
		}

		try {
			upgradeSellerResponse = this.service.updateUpgradeSeller(upgradeSellerRequest);
			if (upgradeSellerResponse != null) {
				if (upgradeSellerResponse.getCommonResponse() == null) {
					upgradeSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
							"response.ResultMessage.success"));
				}
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));

			}
		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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

		RemoveSellerResponse removeSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (removeSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removeSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (removeSellerRequest.getCommonRequest().getTenantID() == null
				|| removeSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음 (검색 keyType , keyString 없음)
		if (removeSellerRequest.getSellerKey() == null || removeSellerRequest.getSellerKey().length() <= 0
				|| removeSellerRequest.getSecedeReasonCode() == null
				|| removeSellerRequest.getSecedeReasonCode().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			removeSellerResponse = this.service.removeSeller(removeSellerRequest);
			if (removeSellerResponse != null) {
				if (removeSellerResponse.getCommonResponse() == null) {
					removeSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
							"response.ResultMessage.success"));
				}
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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

		CreateSubSellerResponse createSubSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (createSubSellerRequest == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (createSubSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (createSubSellerRequest.getCommonRequest().getTenantID() == null
				|| createSubSellerRequest.getCommonRequest().getTenantID().length() <= 0) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음
		if (createSubSellerRequest.getSellerMbr() == null || createSubSellerRequest.getIsNew() == null
				|| createSubSellerRequest.getIsNew().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// SellerMbr(SellerID) ,IsNew
		}

		// 필수 파라미터 없음
		if (createSubSellerRequest.getSellerMbr().getParentSellerKey() == null
				|| createSubSellerRequest.getSellerMbr().getParentSellerKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// SellerMbr(ParentSellerKey,SellerID)
		}

		// 필수 파라미터 없음 MbrPwd
		if (createSubSellerRequest.getMbrPwd() != null
				&& (createSubSellerRequest.getMbrPwd().getMemberPW() == null || createSubSellerRequest.getMbrPwd()
						.getMemberPW().length() <= 0)) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// MbrPwd(MemberPW)

		}

		// 필수 파라미터 없음 수정인경우
		if (createSubSellerRequest.getMbrPwd() == null && createSubSellerRequest.getIsNew().equals(Constant.TYPE_YN_Y)) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// IsNew = Y > MbrPwd input
		}

		// 필수 파라미터 없음 수정인경우
		if (createSubSellerRequest.getIsNew().equals(Constant.TYPE_YN_N)
				&& (createSubSellerRequest.getSellerMbr().getSellerKey() == null || createSubSellerRequest
						.getSellerMbr().getSellerKey().length() <= 0)) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// IsNew = N > SellerKey input
		} else if ((createSubSellerRequest.getSellerMbr().getSellerID() == null || createSubSellerRequest
				.getSellerMbr().getSellerID().length() <= 0)
				&& createSubSellerRequest.getIsNew().equals(Constant.TYPE_YN_Y)) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 없음 비밀번호 수정인경우
		if (createSubSellerRequest.getIsNew().equals(Constant.TYPE_YN_N)
				&& createSubSellerRequest.getMbrPwd() != null
				&& (createSubSellerRequest.getMbrPwd().getMemberPW() == null || createSubSellerRequest.getMbrPwd()
						.getMemberPW().length() <= 0)) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// MbrPwd(OldPW,MemberPW)

		}

		try {
			createSubSellerResponse = this.service.createSubSeller(createSubSellerRequest);

			if (createSubSellerResponse != null) {
				if (createSubSellerResponse.getCommonResponse() == null) {
					createSubSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
							"response.ResultMessage.success"));
				}
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("판매자 컨트롤러 - 서브계정 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 : {}", searchSubListSellerRequest);

		SearchSubSellerListResponse searchSubSellerListResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (searchSubListSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchSubListSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchSubListSellerRequest.getCommonRequest().getTenantID() == null
				|| searchSubListSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey
		if (searchSubListSellerRequest.getParentSellerKey() == null
				|| searchSubListSellerRequest.getParentSellerKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// ParentSellerKey
		}

		try {

			searchSubSellerListResponse = this.service.searchSubSellerList(searchSubListSellerRequest);

			if (searchSubSellerListResponse != null) {
				searchSubSellerListResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return searchSubSellerListResponse;

			} else {

				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("판매자 컨트롤러 - 서브계정 상세 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 : {}", searchSubSellerRequest);

		SearchSubSellerResponse searchSubSellerResponse;

		// CommonResponse commonResponse = new CommonResponse();
		// 입력 파라미터가 없음
		if (searchSubSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchSubSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchSubSellerRequest.getCommonRequest().getTenantID() == null
				|| searchSubSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey
		if (searchSubSellerRequest.getSellerKey() == null || searchSubSellerRequest.getSellerKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchSubSellerResponse = this.service.searchSubSeller(searchSubSellerRequest);

			if (searchSubSellerResponse != null) {
				searchSubSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }
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

		RemoveSubSellerResponse removeSubSellerResponse;
		// CommonResponse commonResponse = new CommonResponse();

		// 입력 파라미터가 없음
		if (removeSubSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removeSubSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (removeSubSellerRequest.getCommonRequest().getTenantID() == null
				|| removeSubSellerRequest.getCommonRequest().getTenantID().length() <= 0) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, ParentSellerKey
		if (removeSubSellerRequest.getParentSellerKey() == null
				|| removeSubSellerRequest.getParentSellerKey().length() <= 0
				|| removeSubSellerRequest.getSellerKeyList() == null
				|| removeSubSellerRequest.getSellerKeyList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			// ParentSellerKey 부모키,SellerKeyList
		}

		try {
			removeSubSellerResponse = this.service.removeSubSeller(removeSubSellerRequest);
			if (removeSubSellerResponse != null) {
				if (removeSubSellerResponse.getCommonResponse() == null) {
					removeSubSellerResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
							"response.ResultMessage.success"));
				}
			} else {

				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }
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
		LOGGER.debug("판매자 컨트롤러 - 비밀번호 힌트 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 : {}", searchPwdHintListRequest);

		SearchPwdHintListResponse searchPwdHintListResponse;

		// 입력 파라미터가 없음
		if (searchPwdHintListRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchPwdHintListRequest.getCommonRequest() == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));

		}

		// 테넌트 아이디 없음
		if (searchPwdHintListRequest.getCommonRequest().getTenantID() == null
				|| searchPwdHintListRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey
		if (searchPwdHintListRequest.getSellerKey() == null || searchPwdHintListRequest.getSellerKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// LanguageCode 없음
		if (searchPwdHintListRequest.getLanguageCode() == null
				|| searchPwdHintListRequest.getLanguageCode().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			searchPwdHintListResponse = this.service.searchPwdHintList(searchPwdHintListRequest);

			if (searchPwdHintListResponse != null) {
				if (searchPwdHintListResponse.getCommonResponse() == null) {
					searchPwdHintListResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
							"response.ResultMessage.success"));
				}
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("판매자 컨트롤러 - 로그인 정보 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("updateFlurryRequest : {}", updateLoginInfoRequest);

		UpdateLoginInfoResponse updateLoginInfoResponse = null;

		// 입력 파라미터가 없음
		if (updateLoginInfoRequest == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateLoginInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateLoginInfoRequest.getCommonRequest().getTenantID() == null
				|| updateLoginInfoRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, LoginInfo
		if (updateLoginInfoRequest.getLoginInfo() == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 없음, LoginInfo
		if (updateLoginInfoRequest.getLoginInfo().getSellerKey() == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			updateLoginInfoResponse = this.service.updateLoginInfo(updateLoginInfoRequest);

			if (updateLoginInfoResponse != null) {
				updateLoginInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));

			} else {

				throw new StorePlatformException(this.getMessage("response.ResultCode.insertOrUpdateError", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }
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
		LOGGER.debug("판매자 컨트롤러 - 로그인 정보 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("updateFlurryRequest : {}", searchLoginInfoRequest);

		SearchLoginInfoResponse searchLoginInfoResponse;

		// 입력 파라미터가 없음
		if (searchLoginInfoRequest == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchLoginInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchLoginInfoRequest.getCommonRequest().getTenantID() == null
				|| searchLoginInfoRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, sellerKey
		if (searchLoginInfoRequest.getSessionKey() == null || searchLoginInfoRequest.getSessionKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			searchLoginInfoResponse = this.service.searchLoginInfo(searchLoginInfoRequest);

			if (searchLoginInfoResponse != null) {
				searchLoginInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("판매자 컨트롤러 - 로그인 정보 삭제");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("removeLoginInfoRequest : {}", removeLoginInfoRequest);

		RemoveLoginInfoResponse removeLoginInfoResponse;

		// 입력 파라미터가 없음
		if (removeLoginInfoRequest == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (removeLoginInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (removeLoginInfoRequest.getCommonRequest().getTenantID() == null
				|| removeLoginInfoRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, LoginInfo
		if (removeLoginInfoRequest.getLoginInfo() == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 필수 파라미터 없음, LoginInfo
		if (removeLoginInfoRequest.getLoginInfo().getSellerKey() == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			removeLoginInfoResponse = this.service.removeLoginInfo(removeLoginInfoRequest);

			if (removeLoginInfoResponse != null) {
				removeLoginInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
			} else {

				throw new StorePlatformException(this.getMessage("response.ResultCode.removeError", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("판매자 컨트롤러 - 비밀번호 힌트 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 : {}", searchPwdHintListAllRequest);

		SearchPwdHintListAllResponse searchPwdHintListAllResponse;

		// 입력 파라미터가 없음
		if (searchPwdHintListAllRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchPwdHintListAllRequest.getCommonRequest() == null) {

			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));

		}

		// 테넌트 아이디 없음
		if (searchPwdHintListAllRequest.getCommonRequest().getTenantID() == null
				|| searchPwdHintListAllRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// LanguageCode 없음
		if (searchPwdHintListAllRequest.getLanguageCode() == null
				|| searchPwdHintListAllRequest.getLanguageCode().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {
			searchPwdHintListAllResponse = this.service.searchPwdHintListAll(searchPwdHintListAllRequest);

			if (searchPwdHintListAllResponse != null) {
				if (searchPwdHintListAllResponse.getCommonResponse() == null) {
					searchPwdHintListAllResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
							"response.ResultMessage.success"));
				}
			} else {
				throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));
			}

		} catch (StorePlatformException ex) {
			throw ex;
		}
		// catch (Exception ex) {
		// throw new StorePlatformException(this.getMessage("response.ResultCode.unknownErr", ""), ex);
		// }

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
		LOGGER.debug("판매자자 컨트롤러 - 약관동의 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		LOGGER.debug("### 받은 데이터 updateAgreementRequest : {}", updateAgreementSellerRequest);

		UpdateAgreementSellerResponse updateAgreementSellerResponse = null;

		// 입력 파라미터가 없음
		if (updateAgreementSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (updateAgreementSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (updateAgreementSellerRequest.getCommonRequest().getTenantID() == null
				|| updateAgreementSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터 없음, userKey
		if (updateAgreementSellerRequest.getSellerKey() == null
				|| updateAgreementSellerRequest.getSellerKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 수정 또는 추가할 약관 없음
		if (updateAgreementSellerRequest.getMbrClauseAgreeList() == null
				|| updateAgreementSellerRequest.getMbrClauseAgreeList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.editInputItemNotFound", ""));
		}

		// 필수 파라미터 없음, mbrClauseAgreeList
		List<MbrClauseAgree> mbrClauseAgreeList = updateAgreementSellerRequest.getMbrClauseAgreeList();
		for (MbrClauseAgree mbrClauseAgree : mbrClauseAgreeList) {
			if (mbrClauseAgree.getExtraAgreementID() == null || mbrClauseAgree.getExtraAgreementID().length() <= 0
					|| mbrClauseAgree.getIsExtraAgreement() == null
					|| mbrClauseAgree.getIsExtraAgreement().length() <= 0) {
				throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
			}
		}

		try {

			updateAgreementSellerResponse = this.service.updateAgreementSeller(updateAgreementSellerRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

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
		LOGGER.debug("컨트롤러 - 사용자 약관동의 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchAgreementListSellerResponse searchAgreementListSellerResponse = null;

		// 입력 파라미터가 없음
		if (searchAgreementListSellerRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchAgreementListSellerRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchAgreementListSellerRequest.getCommonRequest().getTenantID() == null
				|| searchAgreementListSellerRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 사용자키 없음
		if (searchAgreementListSellerRequest.getSellerKey() == null
				|| searchAgreementListSellerRequest.getSellerKey().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.userKeyNotFound", ""));
		}

		try {

			searchAgreementListSellerResponse = this.service
					.searchAgreementListSeller(searchAgreementListSellerRequest);

		} catch (StorePlatformException ex) {
			throw ex;
		}

		return searchAgreementListSellerResponse;
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
		LOGGER.debug(code);
		LOGGER.debug(msg);
		return msg;
	}

}
