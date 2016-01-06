package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.member.client.common.vo.*;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 사용자별 정책 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 사용자별 정책 관련 기능 클래스 분리
 */
@Service
public class IndividualPolicyServiceImpl implements IndividualPolicyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(IndividualPolicyServiceImpl.class);

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트

	@Autowired
	private UserSCI userSCI; // 회원 Component 사용자 기능 Interface.

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

		LOGGER.debug("###### IndividualPolicyService.createIndividualPolicy [START] ######");

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
		policyRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		LOGGER.debug("==>>[SC] SearchPolicyRequest.toString() : {}", policyRequest.toString());

		/** 3. SC회원[searchPolicyList] Call. */
		SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);

		/** 4. SC회원 Call 결과 값으로 Response 생성 및 주입. */
		GetIndividualPolicyRes res = new GetIndividualPolicyRes();
		List<IndividualPolicyInfo> policyInfos = null;
		IndividualPolicyInfo policyInfo;
		List<LimitTarget> limitTargetList = policyResponse.getLimitTargetList();
		if (limitTargetList.size() > 0) {
			policyInfos = new ArrayList<IndividualPolicyInfo>();
			int idx = 0;
			for(LimitTarget limitTarget : limitTargetList) {
				policyInfo = new IndividualPolicyInfo();
				policyInfo.setKey(limitTarget.getLimitPolicyKey());
				policyInfo.setPolicyCode(limitTarget.getLimitPolicyCode());
				policyInfo.setValue(limitTarget.getPolicyApplyValue());
				policyInfo.setLimitAmount(limitTarget.getLimitAmount());
				policyInfo.setPreLimitAmount(limitTarget.getPreLimitAmount());
				policyInfo.setPermissionType(limitTarget.getPermissionType());
				policyInfo.setIsUsed(limitTarget.getIsUsed());
				policyInfos.add(policyInfo);
				LOGGER.debug("==>>[SAC] IndividualPolicyInfo[{}].toString() : {}", (idx++), policyInfo.toString());
			}
		}
		res.setPolicyList(policyInfos);

		LOGGER.debug("==>>[SAC] GetIndividualPolicyRes.toString() : {}", res.toString());

		return res;
	}

	/**
	 * <pre>
	 * 2.3.9. 사용자별 정책 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateIndividualPolicyReq
	 * @return CreateIndividualPolicyRes
	 */
	@Override
	public CreateIndividualPolicyRes regIndividualPolicy(SacRequestHeader header, CreateIndividualPolicyReq req) {

		LOGGER.debug("###### IndividualPolicyService.createIndividualPolicy [START] ######");

		/** 1. SC회원[UserSCI] Req 생성 및 주입 시작. */
		UpdatePolicyRequest updatePolicyRequest = new UpdatePolicyRequest();
		List<LimitTarget> limitTargets = new ArrayList<LimitTarget>();
		LimitTarget limitTarget = new LimitTarget();
		limitTarget.setLimitPolicyCode(req.getPolicyCode());
		limitTarget.setLimitPolicyKey(req.getKey());
		limitTarget.setPolicyApplyValue(req.getValue());
		limitTarget.setRegID(req.getRegId());
		limitTarget.setLimitAmount(req.getLimitAmount());
		limitTarget.setPreLimitAmount(req.getPreLimitAmount());
		limitTarget.setPermissionType(req.getPermissionType());
		limitTarget.setIsUsed(req.getIsUsed());

		limitTargets.add(limitTarget);
		updatePolicyRequest.setLimitTargetList(limitTargets);

		LOGGER.debug("==>>[SC] LimitTarget.toString() : {}", limitTarget.toString());

		/** 2. 공통 파라미터 생성 및 주입. */

		updatePolicyRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));

		LOGGER.debug("==>>[SC] UpdatePolicyRequest.toString() : {}", updatePolicyRequest.toString());

		/** 3. SC회원[updatePolicy] Call. */
		UpdatePolicyResponse updatePolicyResponse = this.userSCI.updatePolicy(updatePolicyRequest);

		/** 4. SC회원 Call 결과 값으로 Response 생성 및 주입. */
		CreateIndividualPolicyRes res = new CreateIndividualPolicyRes();

		if (updatePolicyResponse.getLimitTargetList().size() > 0) {
			LimitTarget limitTarget1 = updatePolicyResponse.getLimitTargetList().get(0);
			res.setKey(limitTarget1.getLimitPolicyKey());
			res.setPolicyCode(limitTarget1.getLimitPolicyCode());
			res.setValue(limitTarget1.getPolicyApplyValue());
			res.setIsUsed(limitTarget1.getIsUsed());
			res.setLimitAmount(limitTarget1.getLimitAmount());
			res.setPermissionType(limitTarget1.getPermissionType());
			res.setPreLimitAmount(limitTarget1.getPreLimitAmount());
		}

		LOGGER.debug("==>>[SAC] CreateIndividualPolicyRes.toString() : {}", res.toString());
		LOGGER.debug("###### IndividualPolicyService.createIndividualPolicy [START] ######");
		return res;
	}

	/**
	 * <pre>
	 * 2.3.10. 사용자별 정책 삭제.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            RemoveIndividualPolicyReq
	 * @return RemoveIndividualPolicyRes
	 */
	@Override
	public RemoveIndividualPolicyRes remIndividualPolicy(SacRequestHeader header, RemoveIndividualPolicyReq req) {

		LOGGER.debug("###### removeIndividualPolicy [START] ######");

		/** 1. SC회원[UserSCI] Req 생성 및 주입 시작. */
		RemovePolicyRequest removePolicyRequest = new RemovePolicyRequest();
		List<LimitTarget> limitTargetList = new ArrayList<LimitTarget>();
		LimitTarget limitTarget = new LimitTarget();
		limitTarget.setLimitPolicyCode(req.getPolicyCode());
		limitTarget.setLimitPolicyKey(req.getKey());

		limitTargetList.add(limitTarget);

		LOGGER.debug("==>>[SC] limitTarget.toString() : {}", limitTarget.toString());

		/** 2. 공통 파라미터 생성 및 주입. */
		removePolicyRequest.setCommonRequest(this.commonComponent.getSCCommonRequest(header));
		removePolicyRequest.setLimitTargetList(limitTargetList);

		LOGGER.debug("==>>[SC] RemovePolicyRequest.toString() : {}", removePolicyRequest.toString());

		/** 3. SC회원[updatePolicy] Call. */
		RemovePolicyResponse removePolicyResponse = this.userSCI.removePolicy(removePolicyRequest);

		/** 4. SC회원 Call 결과 값으로 Response 생성 및 주입. */
		RemoveIndividualPolicyRes res = new RemoveIndividualPolicyRes();
		res.setPolicyCode(removePolicyResponse.getLimitPolicyCodeList().get(0));
		res.setKey(req.getKey());

		LOGGER.debug("==>>[SAC] RemoveIndividualPolicyRes.toString() : {}", res.toString());
		LOGGER.debug("###### removeIndividualPolicy [END] ######");
		return res;
	}
}
