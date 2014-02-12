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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.MemberPoint;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMemberPointRequest;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;

/**
 * 회원 OCB 정보 서비스 (CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 2. 12. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class UserOcbServiceImpl implements UserOcbService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserModifyServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private UserSCI userSCI;

	@Override
	public CreateOcbInformationRes createOcbInformation(SacRequestHeader sacHeader, CreateOcbInformationReq req) {

		UpdateMemberPointRequest updateMemberPointRequest = new UpdateMemberPointRequest();
		updateMemberPointRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		MemberPoint memberPoint = new MemberPoint();
		memberPoint.setUserKey(req.getUserKey()); // 사용자 Key
		memberPoint.setAuthMethodCode(req.getAuthMethodCode()); // 인증방법 코드.
		memberPoint.setCardNumber(req.getCardNumber()); // 카드 번호.
		memberPoint.setEndDate(req.getEndDate()); // 사용종료 일시.
		memberPoint.setIsUsed(req.getIsUsed()); // 사용여부 (Y/N).
		memberPoint.setRegDate(req.getRegDate()); // 등록일시.
		memberPoint.setRegID(req.getRegId()); // 등록 ID.
		memberPoint.setStartDate(req.getStartDate()); // 사용시작 일시.
		memberPoint.setUpdateDate(req.getUpdateDate()); // 수정일시.
		memberPoint.setUpdateID(req.getUpdateId()); // 수정 ID.
		updateMemberPointRequest.setMemberPoint(memberPoint);

		/**
		 * OCB 등록/수정 요청.
		 */
		this.userSCI.updateMemberPoint(updateMemberPointRequest);

		/**
		 * 결과 setting.
		 */
		CreateOcbInformationRes response = new CreateOcbInformationRes();
		response.setUserKey(req.getUserKey());

		return response;
	}

	@Override
	public RemoveOcbInformationRes removeOcbInformation(SacRequestHeader sacHeader, RemoveOcbInformationReq req) {

		RemoveOcbInformationRes response = new RemoveOcbInformationRes();

		return response;
	}

	@Override
	public GetOcbInformationRes getOcbInformation(SacRequestHeader sacHeader, GetOcbInformationReq req) {

		GetOcbInformationRes response = new GetOcbInformationRes();

		return response;
	}

}
