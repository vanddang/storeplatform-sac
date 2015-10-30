package com.skplanet.storeplatform.sac.client.internal.member.user.sci;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UpdateLimitChargeYnSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UpdateLimitChargeYnSacRes;

/**
 * 단말 정보 조회 내부 메소드 호출 Interface.
 * 
 * Updated on : 2014. 2. 11. Updated by : 김다슬, 인크로스.
 */
@SCI
@RequestMapping(value = "/member/user/sci")
public interface DeviceSCI {

	/**
	 * <pre>
	 * 단말 ID 정보(msisdn|uuid|mac) 조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchDeviceIdSacReq
	 * @return SearchDeviceIdSacRes
	 */
	@ResponseBody
	@RequestMapping(value = "/searchDeviceId", method = RequestMethod.POST)
	public SearchDeviceIdSacRes searchDeviceId(@RequestBody @Validated SearchDeviceIdSacReq request);

	/**
	 * <pre>
	 * 기기변경 이력 조회.
	 * </pre>
	 * 
	 * @param request
	 *            ChangedDeviceHistorySacReq
	 * @return ChangedDeviceHistorySacRes
	 */
	@ResponseBody
	@RequestMapping(value = "/searchChangedDeviceHistory", method = RequestMethod.POST)
	public ChangedDeviceHistorySacRes searchChangedDeviceHistory(
			@RequestBody @Validated ChangedDeviceHistorySacReq request);

	/**
	 * <pre>
	 * 2.1.9.	등록 단말 MDN 정보 조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchOrderDeviceIdSacReq
	 * @return SearchOrderDeviceIdSacRes
	 */
	@ResponseBody
	@RequestMapping(value = "/searchOrderDeviceId", method = RequestMethod.POST)
	public SearchOrderDeviceIdSacRes searchOrderDeviceId(@RequestBody @Validated SearchOrderDeviceIdSacReq request);

	/**
	 * <pre>
	 * 2.1.13. 회원 한도 요금제 사용여부 업데이트.
	 * </pre>
	 * 
	 * @param request
	 *            UpdateLimitChargeYnSacReq
	 * @return UpdateLimitChargeYnSacRes
	 */
	@ResponseBody
	@RequestMapping(value = "/updateLimitChargeYn", method = RequestMethod.POST)
	public UpdateLimitChargeYnSacRes updateLimitChargeYn(@RequestBody @Validated UpdateLimitChargeYnSacReq request);
}
