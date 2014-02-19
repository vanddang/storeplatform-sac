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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.MemberPoint;
import com.skplanet.storeplatform.member.client.common.vo.RemoveMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchMemberPointResponse;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMemberPointRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMemberPointResponse;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.OcbInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

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
		memberPoint.setAuthMethodCode(req.getAuthMethodCode()); // 인증방법 코드
		memberPoint.setCardNumber(req.getCardNumber()); // 카드 번호
		memberPoint.setStartDate(DateUtil.getToday("yyyyMMddHHmmss")); // 사용시작 일시
		memberPoint.setEndDate("99991231115959"); // 사용종료 일시
		updateMemberPointRequest.setMemberPoint(memberPoint);

		/**
		 * OCB 등록/수정 요청.
		 */
		UpdateMemberPointResponse updateMemberPointResponse = this.userSCI.updateMemberPoint(updateMemberPointRequest);

		/**
		 * 결과 setting.
		 */
		CreateOcbInformationRes response = new CreateOcbInformationRes();
		response.setUserKey(updateMemberPointResponse.getUserKey());

		return response;
	}

	@Override
	public RemoveOcbInformationRes removeOcbInformation(SacRequestHeader sacHeader, RemoveOcbInformationReq req) {

		/**
		 * OCB 조회 요청.
		 */
		SearchMemberPointResponse searchMemberPointResponse = null;
		try {
			searchMemberPointResponse = this.searchMemberPointList(sacHeader, req.getUserKey());
		} catch (StorePlatformException spe) {
			if (StringUtils.equals(spe.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw new StorePlatformException("SAC_MEM_1701", spe);
			}
		}

		/**
		 * 유효한 카드번호를 삭제 하는것인지 체크 한다.
		 */
		for (MemberPoint dbOcbInfo : searchMemberPointResponse.getMemberPointList()) {
			LOGGER.info("### >> 비교 DB  cardNumber : {}", dbOcbInfo.getCardNumber());
			LOGGER.info("### >> 비교 REQ cardNumber : {}", req.getCardNumber());
			if (!StringUtils.equals(dbOcbInfo.getCardNumber(), req.getCardNumber())) {
				throw new StorePlatformException("SAC_MEM_1700", req.getCardNumber());
			}
		}

		RemoveMemberPointRequest removeMemberPointRequest = new RemoveMemberPointRequest();
		removeMemberPointRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));

		List<MemberPoint> memberPointList = new ArrayList<MemberPoint>();
		MemberPoint memberPoint = new MemberPoint();
		memberPoint.setUserKey(req.getUserKey()); // 사용자 Key
		memberPoint.setCardNumber(req.getCardNumber()); // 카드번호

		memberPointList.add(memberPoint);
		removeMemberPointRequest.setMemberPointList(memberPointList);

		/**
		 * OCB 삭제 요청.
		 */
		this.userSCI.removeMemberPoint(removeMemberPointRequest);

		/**
		 * 결과 setting.
		 */
		RemoveOcbInformationRes response = new RemoveOcbInformationRes();
		response.setUserKey(req.getUserKey());

		return response;
	}

	@Override
	public GetOcbInformationRes getOcbInformation(SacRequestHeader sacHeader, GetOcbInformationReq req) {

		/**
		 * OCB 조회 요청.
		 */
		SearchMemberPointResponse searchMemberPointResponse = this.searchMemberPointList(sacHeader, req.getUserKey());

		/**
		 * 결과 setting.
		 */
		List<OcbInfo> ocbInfoList = new ArrayList<OcbInfo>();
		for (MemberPoint dbOcbInfo : searchMemberPointResponse.getMemberPointList()) {
			OcbInfo ocbInfo = new OcbInfo();
			ocbInfo.setUserKey(dbOcbInfo.getUserKey()); // 사용자 Key
			ocbInfo.setAuthMethodCode(dbOcbInfo.getAuthMethodCode()); // 인증방법 코드
			ocbInfo.setCardNumber(dbOcbInfo.getCardNumber()); // 카드 번호
			ocbInfo.setStartDate(dbOcbInfo.getStartDate()); // 사용시작 일시
			ocbInfo.setEndDate(dbOcbInfo.getEndDate()); // 사용종료 일시
			ocbInfo.setIsUsed(dbOcbInfo.getIsUsed()); // 사용여부 (Y/N)
			ocbInfo.setRegDate(dbOcbInfo.getRegDate()); // 사용여부 (Y/N)
			ocbInfoList.add(ocbInfo);
		}

		GetOcbInformationRes response = new GetOcbInformationRes();
		response.setOcbInfoList(ocbInfoList);

		return response;
	}

	private SearchMemberPointResponse searchMemberPointList(SacRequestHeader sacHeader, String userKey) {

		SearchMemberPointRequest searchMemberPointRequest = new SearchMemberPointRequest();
		searchMemberPointRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		searchMemberPointRequest.setUserKey(userKey);

		/**
		 * OCB 조회 요청.
		 */
		SearchMemberPointResponse searchMemberPointResponse = this.userSCI.searchMemberPointList(searchMemberPointRequest);
		LOGGER.info("### searchMemberPointResponse : {}", searchMemberPointResponse.getMemberPointList());

		return searchMemberPointResponse;

	}
}
