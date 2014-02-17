/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;

/**
 * 회원정보 조회 내부메소드 호출 Controller.
 * 
 * Updated on : 2014. 2. 12. Updated by : 김다슬, 인크로스.
 */
@LocalSCI
public class SearchUserSCIController implements SearchUserSCI {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchUserSCIController.class);

	@Autowired
	private UserSearchService userSearchService; // 회원 조회 서비스 인터페이스.

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserInternalSCI#searchUserByUserKey(com.
	 * skplanet .storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq)
	 */
	@Override
	public SearchUserSacRes searchUserByUserKey(SearchUserSacReq request) {

		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		LOGGER.info(
				"[SearchUserInternalSCIController.searchUserByUserKey] RequestHeader : {}, \nRequestParameter : {}",
				requestHeader, request);

		/* 1. 회원 정보 조회 SC API 호출. */
		DetailReq detailRequest = new DetailReq();
		DetailRes userDetail = this.userSearchService.detail(requestHeader, detailRequest);
		LOGGER.info("[SearchUserInternalSCIController.searchUserByUserKey] SC UserDetailInfo Response : {}", userDetail);

		/* 2. 사용자 휴대기기 등록 대수가 1개 이상일 경우 리스트 가져오기. */
		// 등록기기 없는경우, size=0 인 List 내려주기.
		List<DeviceInfo> deviceList = userDetail.getDeviceInfoList();
		List<String> deviceIdList = new ArrayList<String>();

		// MDN 정보 리스트 셋팅
		for (int i = 0; i < deviceList.size(); i++) {
			deviceIdList.set(i, deviceList.get(i).getDeviceId());
		}

		/* 3. 파라미터 셋팅해서 Response. */
		SearchUserSacRes searchUserSacRes = new SearchUserSacRes();
		searchUserSacRes.setDeviceId(deviceIdList);
		searchUserSacRes.setUserId(userDetail.getUserInfo().getUserId());
		searchUserSacRes.setUserType(userDetail.getUserInfo().getUserType());
		searchUserSacRes.setUserMainStatus(userDetail.getUserInfo().getUserMainStatus());
		searchUserSacRes.setUserSubStatus(userDetail.getUserInfo().getUserSubStatus());
		LOGGER.info("[SearchUserInternalSCIController.searchUserByUserKey] SAC UserInfo Response : {}",
				searchUserSacRes);

		return searchUserSacRes;
	}
}
