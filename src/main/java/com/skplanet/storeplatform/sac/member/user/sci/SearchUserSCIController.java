/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSac;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchUserReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserInfoByUserKey;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;

/**
 * 회원정보 조회 내부메소드 호출 Controller.
 * 
 * Updated on : 2014. 2. 12. Updated by : 김다슬, 인크로스.
 */
@LocalSCI
@RequestMapping(value = "/member/user/sci")
public class SearchUserSCIController implements SearchUserSCI {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchUserSCIController.class);

	@Autowired
	private UserSearchService userSearchService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI#searchUserByUserKey(com.skplanet
	 * .storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq)
	 */
	@Override
	@RequestMapping(value = "/searchUserByUserKey", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserSacRes searchUserByUserKey(@RequestBody @Validated SearchUserSacReq request) {

		// 헤더 정보 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		LOGGER.info("[SearchUserSCIController.searchUserByUserKey] RequestHeader : {}, \nRequestParameter : {}",
				requestHeader, request);

		List<String> userKeyList = request.getUserKeyList();
		SearchUserReq searchUserReq = new SearchUserReq();
		searchUserReq.setUserKeyList(userKeyList);

		Map<String, UserInfoByUserKey> userInfoMap = this.userSearchService.searchUserByUserKey(requestHeader,
				searchUserReq);

		Map<String, UserInfoSac> resMap = new HashMap<String, UserInfoSac>();
		UserInfoSac userInfoSac;

		for (int i = 0; i < userKeyList.size(); i++) {
			if (userInfoMap.get(userKeyList.get(i)) != null) {
				userInfoSac = new UserInfoSac();
				// userInfoSac.setUserKey(userInfoMap.get(userKeyList.get(i)).getUserKey());
				userInfoSac.setUserId(userInfoMap.get(userKeyList.get(i)).getUserId());
				userInfoSac.setUserMainStatus(userInfoMap.get(userKeyList.get(i)).getUserMainStatus());
				userInfoSac.setUserSubStatus(userInfoMap.get(userKeyList.get(i)).getUserSubStatus());
				userInfoSac.setUserType(userInfoMap.get(userKeyList.get(i)).getUserType());
				// 등록기기(deviceIdList) 없는경우, SC 회원에서 size=0인 List로 내려주기로함.
				userInfoSac.setDeviceIdList(userInfoMap.get(userKeyList.get(i)).getDeviceIdList());

				resMap.put(userKeyList.get(i), userInfoSac);
			}
		}

		SearchUserSacRes searchUserSacRes = new SearchUserSacRes();
		searchUserSacRes.setUserInfo(resMap);

		return searchUserSacRes;
	}
}
