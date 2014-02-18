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
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeUserEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.DiscardUserEcReq;
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.idp.service.IdpService;

/**
 * 회원탈퇴 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@Service
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
	private DeviceService deviceService;

	@Autowired
	private IdpService idpService; // IDP 연동 클래스

	@Autowired
	private UapsSCI uapsSCI;

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private ImIdpSCI imIdpSCI;

	/**
	 * 
	 * 회원탈퇴
	 * 
	 * @param
	 * @return
	 */
	@Override
	public WithdrawRes withdraw(SacRequestHeader requestHeader, WithdrawReq req) {

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userAuthKey = StringUtil.nvl(req.getUserAuthKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		req.setUserId(userId);
		req.setUserAuthKey(userAuthKey);
		req.setDeviceId(deviceId);

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (!deviceId.equals("")) {
			String opmdMdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			logger.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);
		}

		/* SC 회원 존재 여부 */
		SearchUserResponse schUserRes = this.searchUser(requestHeader, req);
		logger.info("schUserRes ", schUserRes.toString());

		/* Return Value */
		WithdrawRes withdrawRes = new WithdrawRes();

		/* 통합회원 연동 */
		if (schUserRes.getUserMbr().getImSvcNo() != null) {
			this.oneIdUser(requestHeader, schUserRes, req);

			logger.info("oneIdUser Discard Success req : ", req.toString());
			withdrawRes.setUserKey(schUserRes.getUserMbr().getUserKey());
		} else {
			if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				this.idpMobileUser(requestHeader, schUserRes, req);

				logger.info("secedeUser4Wap Success req : ", req.toString());
				withdrawRes.setUserKey(schUserRes.getUserMbr().getUserKey());
			}
			/* IDP 아이디 회원 */
			else if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_IDPID)) {
				this.idpIdUser(requestHeader, schUserRes, req);

				logger.info("secedeUser Success req ", req.toString());
				withdrawRes.setUserKey(schUserRes.getUserMbr().getUserKey());
			}
		}

		/* SC Remove */
		RemoveUserRequest scReq = new RemoveUserRequest();
		scReq.setCommonRequest(commonRequest);
		scReq.setUserKey(schUserRes.getUserKey());
		scReq.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
		scReq.setSecedeReasonMessage("");
		this.userSCI.remove(scReq);

		/* 게임센터 연동 */
		GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
		gameCenterSacReq.setUserKey(schUserRes.getUserKey());
		if (!deviceId.equals("")) {
			gameCenterSacReq.setDeviceId(req.getDeviceId());
		}
		gameCenterSacReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
		gameCenterSacReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
		gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_USER_SECEDE);
		this.deviceService.insertGameCenterIF(gameCenterSacReq);

		return withdrawRes;

	}

	/**
	 * 입력된 Parameter로 회원존재여부 체크 한다. deviceId or userId && userKey
	 */
	@Override
	public SearchUserResponse searchUser(SacRequestHeader requestHeader, WithdrawReq req) {

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userAuthKey = StringUtil.nvl(req.getUserAuthKey(), "");
		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		logger.info("###### 회원정보조회 SearchUser Request : {}", req.toString());

		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		/* userId & userAuthKey || userId 로 회원정보 조회 */
		if (!userId.equals("") && !userAuthKey.equals("")) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
			key.setKeyString(userId);
			keySearchList.add(key);
			schUserReq.setKeySearchList(keySearchList);

		} else if (!deviceId.equals("")) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			key.setKeyString(deviceId);
			keySearchList.add(key);
			schUserReq.setKeySearchList(keySearchList);
		}

		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		logger.info("###### SearchUser.deviceId req : {}", schUserReq.toString());

		return schUserRes;

	}

	/**
	 * IMIDP 연동(통합회원)
	 */
	@Override
	public void oneIdUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req) {
		DiscardUserEcReq ecReq = new DiscardUserEcReq();
		ecReq.setKey(schUserRes.getUserMbr().getImSvcNo());
		ecReq.setKeyType("1");
		ecReq.setUserAuthKey(req.getUserAuthKey());

		this.imIdpSCI.discardUser(ecReq);

	}

	/**
	 * IDP 모바일 회원(무선)
	 */
	@Override
	public void idpMobileUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req) {
		SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
		ecReq.setUserMdn(req.getDeviceId());

		this.idpSCI.secedeForWap(ecReq);

	}

	/**
	 * IDP 아이디 회원
	 */
	@Override
	public void idpIdUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req) {
		SecedeUserEcReq ecReq = new SecedeUserEcReq();
		ecReq.setKey(schUserRes.getUserMbr().getUserID());
		ecReq.setUserAuthKey(req.getUserAuthKey());
		ecReq.setKeyType("1");

		this.idpSCI.secedeUser(ecReq);

	}

}
