/**
 * 
 */
package com.skplanet.storeplatform.sac.member.miscellaneous.sci;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.common.vo.LimitTarget;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyResponse;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.sci.MiscellaneousSCI;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.IndividualPolicyInfoSac;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.LimitTargetService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 기타 기능 내부메소드 호출 Controller.
 * 
 * Updated on : 2014. 3. 11. Updated by : 강신완, 부르칸.
 * Updated on : 2016. 2. 12. Updated by : 윤보영, 카레즈.
 */
@LocalSCI
@RequestMapping(value = "/member/miscellaneous/sci")
public class MiscellaneousSCIController implements MiscellaneousSCI {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousSCIController.class);

    @Autowired
    private LimitTargetService limitTargetService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.sci .MiscellaneousSCI
	 * #getIndividualPolicy(com.skplanet.storeplatform.sac.client
	 * .internal.member.miscellaneous.vo.GetIndividualPolicySacReq)
	 */
	@Override
	@RequestMapping(value = "/getIndividualPolicy", method = RequestMethod.POST)
	@ResponseBody
	public GetIndividualPolicySacRes getIndividualPolicy(@RequestBody @Validated GetIndividualPolicySacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		List<String> codeList = new ArrayList<String>();
		for (int i = 0; i < req.getPolicyCodeList().size(); i++) {
            if(StringUtils.isNotEmpty(req.getPolicyCodeList().get(i).getPolicyCode())){
                codeList.add(req.getPolicyCodeList().get(i).getPolicyCode());
            }
		}

        SearchPolicyRequest policyRequest = new SearchPolicyRequest();
        policyRequest.setUserKey(req.getUserKey());
        policyRequest.setDeviceKey(req.getDeviceKey());
        policyRequest.setLimitPolicyCodeList(codeList);

        SearchPolicyResponse res = this.limitTargetService.searchPolicyListByKey(policyRequest);

		GetIndividualPolicySacRes sacRes = new GetIndividualPolicySacRes();
		List<IndividualPolicyInfoSac> infoSacList = new ArrayList<IndividualPolicyInfoSac>();

		for (LimitTarget policyList : res.getLimitTargetList()) {
            IndividualPolicyInfoSac infoSac = new IndividualPolicyInfoSac();
            infoSac.setPolicyCode(StringUtils.trimToEmpty(policyList.getLimitPolicyCode()));
            infoSac.setPolicyValue(StringUtils.trimToEmpty(policyList.getPolicyApplyValue()));
            infoSac.setDeviceTelecom(StringUtils.trimToEmpty(policyList.getMnoCd()));
			infoSacList.add(infoSac);
		}
		sacRes.setPolicyCodeList(infoSacList);

        LOGGER.info("Response : {}", sacRes);

		return sacRes;

	}

}
