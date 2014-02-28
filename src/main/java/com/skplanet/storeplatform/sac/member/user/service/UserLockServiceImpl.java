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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.SetLoginStatusEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.sac.client.member.vo.user.LockAccountSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.LockAccountSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 회원 계정 잠금 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 2. 10. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class UserLockServiceImpl implements UserLockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserLockServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Override
	public LockAccountSacRes lockAccount(SacRequestHeader sacHeader, LockAccountSacReq req) {

		/**
		 * 미동의 회원 체크및 회원 정보 조회.
		 */
		CheckDuplicationResponse chkDupRes = this.checkDisAgree(sacHeader, req);

		/**
		 * 통합서비스번호 존재 유무로 통합회원인지 기존회원인지 판단한다. (UserType보다 더 신뢰함.) 회원 타입에 따라서 [통합IDP, 기존IDP] 연동처리 한다.
		 */
		LOGGER.info("## 사용자 타입  : {}", chkDupRes.getUserMbr().getUserType());
		if (StringUtils.isNotEmpty(chkDupRes.getUserMbr().getImSvcNo())) {

			LOGGER.info("## ====================================================");
			LOGGER.info("## One ID 통합회원 [{}]", req.getUserId());
			LOGGER.info("## ====================================================");

			/**
			 * 통합IDP 로그인 상태 정보 변경 연동 (cmd = TXSetLoginConditionIDP)
			 */
			SetLoginStatusEcReq setLoginStatusEcReq = new SetLoginStatusEcReq();
			setLoginStatusEcReq.setKey(req.getUserId());
			setLoginStatusEcReq.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_PAUSE);
			this.imIdpSCI.setLoginStatus(setLoginStatusEcReq);

			/**
			 * 회원 계정 잠금
			 */
			this.updateLoginStatus(sacHeader, req.getUserId());

		} else {

			LOGGER.info("## ====================================================");
			LOGGER.info("## 기존 IDP 회원 [{}]", req.getUserId());
			LOGGER.info("## ====================================================");

			/**
			 * 회원 계정 잠금
			 */
			this.updateLoginStatus(sacHeader, req.getUserId());

		}

		/**
		 * 결과 setting.
		 */
		LockAccountSacRes response = new LockAccountSacRes();
		response.setUserId(req.getUserId());

		return response;
	}

	/**
	 * <pre>
	 * 미동의 회원 체크및 회원 정보 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return CheckDuplicationResponse
	 */
	private CheckDuplicationResponse checkDisAgree(SacRequestHeader sacHeader, LockAccountSacReq req) {

		/**
		 * 검색조건 정보 setting.
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySchUserKey.setKeyString(req.getUserId());
		keySearchList.add(keySchUserKey);

		/**
		 * 회원 조회 연동.
		 */
		CheckDuplicationRequest chkDupReq = new CheckDuplicationRequest();
		chkDupReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		chkDupReq.setKeySearchList(keySearchList);
		CheckDuplicationResponse chkDupRes = this.userSCI.checkDuplication(chkDupReq);

		if (StringUtils.equals(chkDupRes.getIsRegistered(), "N")) {

			/* 원아이디 서비스 이용동의 간편 가입 대상 확인 */
			if (chkDupRes.getUserMbr() == null && chkDupRes.getMbrOneID() != null) {

				/**
				 * 미동의 회원은 계정 잠금 할수 없음.
				 */
				throw new StorePlatformException("SAC_MEM_1800", req.getUserId());

			} else {

				/**
				 * 회원 정보 없음.
				 */
				throw new StorePlatformException("SAC_MEM_0003", "userId", req.getUserId());

			}

		}

		return chkDupRes;

	}

	/**
	 * <pre>
	 * 회원 계정 잠금 업데이트.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param userId
	 *            사용자 아이디
	 */
	public void updateLoginStatus(SacRequestHeader sacHeader, String userId) {

		UpdateStatusUserRequest updStatusUserReq = new UpdateStatusUserRequest();

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		key.setKeyString(userId);
		keySearchList.add(key);

		updStatusUserReq.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		updStatusUserReq.setKeySearchList(keySearchList);
		updStatusUserReq.setLoginStatusCode(MemberConstants.USER_LOGIN_STATUS_PAUSE);

		this.userSCI.updateStatus(updStatusUserReq);
		LOGGER.info("## 회원 계정 잠금 DB 설정 완료!!");

	}

}
