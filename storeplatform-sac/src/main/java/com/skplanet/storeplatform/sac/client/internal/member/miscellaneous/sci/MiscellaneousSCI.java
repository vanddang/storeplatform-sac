/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.sci;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacRes;

/**
 * 기타 기능 내부 메소드 호출 Interface.
 * 
 * Updated on : 2014. 3. 11. Updated by : 강신완, 부르칸.
 */
@SCI
@RequestMapping(value = "/member/miscellaneous/sci")
public interface MiscellaneousSCI {

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
	@RequestMapping(value = "/getIndividualPolicy", method = RequestMethod.POST)
	@ResponseBody
	public GetIndividualPolicySacRes getIndividualPolicy(@RequestBody @Validated GetIndividualPolicySacReq req);

}
