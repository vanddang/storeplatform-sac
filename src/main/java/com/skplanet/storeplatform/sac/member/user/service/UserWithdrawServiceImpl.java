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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

/**
 * 휴대기기 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
@Transactional
public class UserWithdrawServiceImpl implements UserWithdrawService {

	private static final Logger logger = LoggerFactory.getLogger(UserWithdrawServiceImpl.class);

	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
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

	/**
	 * 
	 * 회원탈퇴
	 * 
	 * @param
	 * @return
	 */
	@Override
	public WithdrawRes withdraw(SacRequestHeader requestHeader, WithdrawReq req) throws Exception {
		String userAuthKey = req.getUserAuthKey();
		String userId = req.getUserId();
		String deviceId = req.getDeviceId();

		/* SC 회원 정보 조회 */
		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		key.setKeyString(userId);
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		if (!StringUtil.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new Exception("[" + schUserRes.getCommonResponse().getResultCode() + "] "
					+ schUserRes.getCommonResponse().getResultMessage());
		}

		/* IDP 회원탈퇴 */
		if (schUserRes.getUserMbr().getImSvcNo() != null) { // 통합회원
			if (MemberConstants.USER_TYPE_ONEID.equals(schUserRes.getUserMbr().getUserType())) {
				// OneId 사용자
				// TXDisagreeUserIDP
			}
		} else {

			if (MemberConstants.USER_TYPE_MOBILE.equals(schUserRes.getUserMbr().getUserType())) {
				// 기기 사용자
				// secedeForWap

			} else if (MemberConstants.USER_TYPE_IDPID.equals(schUserRes.getUserMbr().getUserType())) {
				// IDP 사용자
				// secedeUser
			}

		}

		/* IDP 연동결과 성공이면 SC회원 탈퇴 */
		RemoveUserRequest removeUserRequest = new RemoveUserRequest();
		removeUserRequest.setSecedeReasonCode(""); // 탈퇴사유 코드
		removeUserRequest.setSecedeReasonMessage(""); // 탈퇴사유 내용
		removeUserRequest.setSecedeTypeCode(""); // 탈퇴유형 코드
		removeUserRequest.setUserKey(schUserRes.getUserMbr().getUserKey()); // 사용자 키
		this.userSCI.remove(removeUserRequest);

		// return userKey : WithdrawRes.getUserKey();
		return null;
	}

}
