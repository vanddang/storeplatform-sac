/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.user.sci;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSapUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSapUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSocialAccountSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSocialAccountSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserExtraInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserExtraInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserGradeSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacRes;

/**
 * 회원 정보 조회 내부 메소드 호출 Interface.
 * 
 * Updated on : 2014. 2. 12. Updated by : 김다슬, 인크로스.
 */
@SCI
@RequestMapping(value = "/member/user/sci")
public interface SearchUserSCI {

	/**
	 * <pre>
	 * userKey 목록을 이용하여 회원정보 목록조회. 
	 * Updated on : 2014. 2. 12. Updated by : 김다슬, 인크로스.
	 * </pre>
	 * 
	 * @param request
	 *            SearchUserSacReq
	 * @return SearchUserSacRes
	 */
	@RequestMapping(value = "/searchUserByUserKey", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserSacRes searchUserByUserKey(@RequestBody @Validated SearchUserSacReq request);

	/**
	 * <pre>
	 * userKey, tenantId 목록을 이용하여 회원정보 목록조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchSapUserSacReq
	 * @return SearchSapUserSacReq
	 */
	@RequestMapping(value = "/searchSapUserByUserKey", method = RequestMethod.POST)
	@ResponseBody
	public SearchSapUserSacRes searchSapUserByUserKey(@RequestBody @Validated SearchSapUserSacReq request);

	/**
	 * <pre>
	 * deviceId를 이용한 회원정보 조회.
	 * Updated on : 2014. 4. 18. Updated by : 김다슬, 인크로스.
	 * </pre>
	 * 
	 * @param request
	 *            UserInfoSacReq
	 * @return UserInfoSacRes
	 */
	@RequestMapping(value = "/searchUserBydeviceId", method = RequestMethod.POST)
	@ResponseBody
	public UserInfoSacRes searchUserBydeviceId(@RequestBody @Validated UserInfoSacReq request);

	/**
	 * <pre>
	 * userKey, deviceKey 이용하여 회원 결제페이지 노출 정보 조회
	 * </pre>
	 * 
	 * @param request
	 *            SearchUserPayplanetSacReq
	 * @return SearchUserPayplanetSacRes
	 */
	@RequestMapping(value = "/searchUserPayplanet", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserPayplanetSacRes searchUserPayplanet(@RequestBody @Validated SearchUserPayplanetSacReq request);

	/**
	 * <pre>
	 * deviceKey 목록 이용하여 회원정보 디바이스정보 조회
	 * </pre>
	 * 
	 * @param request
	 *            SearchUserDeviceSacReq
	 * @return SearchUserDeviceSacRes
	 */
	@RequestMapping(value = "/searchUserByDeviceKey", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserDeviceSacRes searchUserByDeviceKey(@RequestBody @Validated SearchUserDeviceSacReq request);

	/**
	 * <pre>
	 * deviceId, orderDt 이용하여 최근 회원정보(탈퇴포함) 조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchOrderUserByDeviceIdSacReq
	 * @return SearchOrderUserByDeviceIdSacRes
	 */
	@RequestMapping(value = "/searchOrderUserByDeviceId", method = RequestMethod.POST)
	@ResponseBody
	public SearchOrderUserByDeviceIdSacRes searchOrderUserByDeviceId(
			@RequestBody @Validated SearchOrderUserByDeviceIdSacReq request);

	/**
	 * <pre>
	 * UserKey를 이용하여 회원마일리지 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SearchUserMileageSacReq
	 * @return SearchUserMileageSacRes
	 */
	@RequestMapping(value = "/searchUserGrade", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserGradeSacRes searchUserGrade(@RequestBody @Validated SearchUserGradeSacReq req);

	/**
	 * <pre>
	 * 2.1.10.	 회원 segment 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SearchUserSegmentSacReq
	 * @return SearchUserSegmentSacRes
	 */
	@RequestMapping(value = "/searchUserSegment", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserSegmentSacRes searchUserSegment(@RequestBody @Validated SearchUserSegmentSacReq req);

	/**
	 * <pre>
	 * 2.1.11. 회원 부가속성 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SearchUserExtraInfoSacReq
	 * @return SearchUserExtraInfoSacRes
	 */
	@RequestMapping(value = "/searchUserExtraInfo", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserExtraInfoSacRes searchUserExtraInfo(@RequestBody @Validated SearchUserExtraInfoSacReq req);

	/**
	 * <pre>
	 * 2.1.14. 소셜계정 등록 회원 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SearchSocialAccountSacReq
	 * @return SearchSocialAccountSacRes
	 */
	@RequestMapping(value = "/searchSocialAccount", method = RequestMethod.POST)
	@ResponseBody
	public SearchSocialAccountSacRes searchSocialAccount(@RequestBody @Validated SearchSocialAccountSacReq req);
}
