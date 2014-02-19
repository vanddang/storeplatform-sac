/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchExtentReq;
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
	private UserSearchService userSearchService; // 회원 조회 서비스 인터페이스.

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI#searchUserByUserKey(com.
	 * skplanet .storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq)
	 */
	@Override
	@RequestMapping(value = "/searchUserByUserKey", method = RequestMethod.POST)
	public SearchUserSacRes searchUserByUserKey(SearchUserSacReq request) {

		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		LOGGER.info("[SearchUserSCIController.searchUserByUserKey] RequestHeader : {}, \nRequestParameter : {}",
				requestHeader, request);

		// 회원정보 조회 범위 설정.
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn("Y");
		searchExtent.setDeviceInfoYn("Y");

		DetailReq detailRequest = new DetailReq();
		detailRequest.setUserKey(request.getUserKey());
		detailRequest.setSearchExtent(searchExtent);

		// 회원 정보 조회 SC API 호출.
		DetailRes userDetail = this.userSearchService.detail(requestHeader, detailRequest);
		LOGGER.info("[SearchUserSCIController.searchUserByUserKey] SC UserDetailInfo Response : {}", userDetail);

		SearchUserSacRes searchUserSacRes = new SearchUserSacRes();

		// 회원 정보가 존재할 경우
		if (userDetail != null && userDetail.getUserInfo() != null) {

			List<DeviceInfo> deviceList = userDetail.getDeviceInfoList();
			List<String> deviceIdList = new ArrayList<String>();

			// if (deviceList == null) { // 등록기기 없는경우, size=0 인 List 내려주기.
			// deviceList = new ArrayList<DeviceInfo>();
			// } else {
			// Setting deviceId List.
			for (int i = 0; i < deviceList.size(); i++) {
				deviceIdList.add(deviceList.get(i).getDeviceId());
			}
			// }

			/* 3. 파라미터 셋팅해서 Response. */
			searchUserSacRes.setDeviceId(deviceIdList);
			searchUserSacRes.setUserId(userDetail.getUserInfo().getUserId());
			searchUserSacRes.setUserType(userDetail.getUserInfo().getUserType());
			searchUserSacRes.setUserMainStatus(userDetail.getUserInfo().getUserMainStatus());
			searchUserSacRes.setUserSubStatus(userDetail.getUserInfo().getUserSubStatus());
			LOGGER.info("[SearchUserSCIController.searchUserByUserKey] SAC UserInfo Response : {}", searchUserSacRes);
		} else {
			throw new StorePlatformException("SAC_MEM_0003", "userKey", request.getUserKey());
		}

		return searchUserSacRes;
	}
}
