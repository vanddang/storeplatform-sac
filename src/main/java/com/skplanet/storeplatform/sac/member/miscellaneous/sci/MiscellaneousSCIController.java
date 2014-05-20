/**
 * 
 */
package com.skplanet.storeplatform.sac.member.miscellaneous.sci;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.sci.MiscellaneousSCI;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.IndividualPolicyInfoSac;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyReq.PolicyCode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetIndividualPolicyRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.IndividualPolicyInfo;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.miscellaneous.sci.service.MiscellaneousSCIService;

/**
 * 기타 기능 내부메소드 호출 Controller.
 * 
 * Updated on : 2014. 3. 11. Updated by : 강신완, 부르칸.
 */
@LocalSCI
@RequestMapping(value = "/member/miscellaneous/sci")
public class MiscellaneousSCIController implements MiscellaneousSCI {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousSCIController.class);

	@Autowired
	private MiscellaneousSCIService miscellaneousSCIService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.sci
	 * .MiscellaneousSCI
	 * #getIndividualPolicy(com.skplanet.storeplatform.sac.client
	 * .internal.member.miscellaneous.vo.GetIndividualPolicySacReq)
	 */
	@Override
	@RequestMapping(value = "/getIndividualPolicy", method = RequestMethod.POST)
	@ResponseBody
	public GetIndividualPolicySacRes getIndividualPolicy(@RequestBody @Validated GetIndividualPolicySacReq req) {

		// 헤더 정보 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		List<PolicyCode> codeList = new ArrayList<PolicyCode>();
		for (int i = 0; i < req.getPolicyCodeList().size(); i++) {
			PolicyCode code = new PolicyCode();
			code.setPolicyCode(req.getPolicyCodeList().get(i).getPolicyCode());
			codeList.add(code);
		}

		GetIndividualPolicyReq getIndividualPolicyReq = new GetIndividualPolicyReq();
		getIndividualPolicyReq.setKey(req.getKey());
		getIndividualPolicyReq.setPolicyCodeList(codeList);

		GetIndividualPolicyRes getIndividualPolicyRes = this.miscellaneousSCIService.getIndividualPolicy(requestHeader, getIndividualPolicyReq);

		GetIndividualPolicySacRes sacRes = new GetIndividualPolicySacRes();
		List<IndividualPolicyInfoSac> infoSacList = new ArrayList<IndividualPolicyInfoSac>();

		for (IndividualPolicyInfo policyList : getIndividualPolicyRes.getPolicyList()) {
			IndividualPolicyInfoSac infoSac = new IndividualPolicyInfoSac();
			infoSac.setIsUsed(StringUtil.setTrim(policyList.getIsUsed()));
			infoSac.setKey(StringUtil.setTrim(policyList.getKey()));
			infoSac.setLimitAmount(StringUtil.setTrim(policyList.getLimitAmount()));
			infoSac.setPermissionType(StringUtil.setTrim(policyList.getPermissionType()));
			infoSac.setPolicyCode(StringUtil.setTrim(policyList.getPolicyCode()));
			infoSac.setPreLimitAmount(StringUtil.setTrim(policyList.getPreLimitAmount()));
			infoSac.setValue(StringUtil.setTrim(policyList.getValue()));

			infoSacList.add(infoSac);
		}
		sacRes.setPolicyList(infoSacList);

		LOGGER.info("Response : {}", sacRes.getPolicyList().get(0).getKey());

		return sacRes;

	}

}
