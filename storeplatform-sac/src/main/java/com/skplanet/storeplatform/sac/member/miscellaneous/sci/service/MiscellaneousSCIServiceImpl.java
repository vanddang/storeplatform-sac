/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.miscellaneous.sci.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyResponse;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.IndividualPolicyInfo;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 기타 기능 내부메소드 호출 Service 구현체.
 * 
 * Updated on : 2014. 3. 11. Updated by : 강신완, 부르칸.
 */
@Service
public class MiscellaneousSCIServiceImpl implements MiscellaneousSCIService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousSCIServiceImpl.class);

	@Autowired
	private UserSCI userSCI;

	/**
	 * <pre>
	 * 2.3.8. 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            GetIndividualPolicyReq
	 * @return GetIndividualPolicyRes
	 */
	@Override
	public GetIndividualPolicyRes getIndividualPolicy(SacRequestHeader header, GetIndividualPolicyReq req) {

		LOGGER.debug("###### MiscellaneousServiceImpl.createIndividualPolicy [START] ######");

		/** 1. SC회원[UserSCI] Req 생성 및 주입 시작. */
		SearchPolicyRequest policyRequest = new SearchPolicyRequest();
		List<String> codeList = new ArrayList<String>();
		for (int i = 0; i < req.getPolicyCodeList().size(); i++) {
			codeList.add(req.getPolicyCodeList().get(i).getPolicyCode());
		}

		LOGGER.debug("==>>[SC] codeList.toString() : {}", codeList.toString());

		policyRequest.setLimitPolicyCodeList(codeList);
		policyRequest.setLimitPolicyKey(req.getKey());

		/** 2. 공통 파라미터 생성 및 주입. */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		policyRequest.setCommonRequest(commonRequest);

		LOGGER.debug("==>>[SC] SearchPolicyRequest.toString() : {}", policyRequest.toString());

		/** 3. SC회원[searchPolicyList] Call. */
		SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);

		/** 4. SC회원 Call 결과 값으로 Response 생성 및 주입. */
		GetIndividualPolicyRes res = new GetIndividualPolicyRes();
		List<IndividualPolicyInfo> policyInfos = null;
		IndividualPolicyInfo policyInfo = null;
		if (policyResponse.getLimitTargetList().size() > 0) {
			policyInfos = new ArrayList<IndividualPolicyInfo>();
			for (int i = 0; i < policyResponse.getLimitTargetList().size(); i++) {
				policyInfo = new IndividualPolicyInfo();
				policyInfo.setKey(policyResponse.getLimitTargetList().get(i).getLimitPolicyKey());
				policyInfo.setPolicyCode(policyResponse.getLimitTargetList().get(i).getLimitPolicyCode());
				policyInfo.setValue(policyResponse.getLimitTargetList().get(i).getPolicyApplyValue());
				policyInfo.setLimitAmount(policyResponse.getLimitTargetList().get(i).getLimitAmount());
				policyInfo.setPreLimitAmount(policyResponse.getLimitTargetList().get(i).getPreLimitAmount());
				policyInfo.setPermissionType(policyResponse.getLimitTargetList().get(i).getPermissionType());
				policyInfo.setIsUsed(policyResponse.getLimitTargetList().get(i).getIsUsed());
				policyInfos.add(policyInfo);
				LOGGER.debug("==>>[SAC] IndividualPolicyInfo[{}].toString() : {}", i, policyInfo.toString());
			}
		}
		res.setPolicyList(policyInfos);

		LOGGER.debug("==>>[SAC] GetIndividualPolicyRes.toString() : {}", res.toString());

		return res;
	}
}
