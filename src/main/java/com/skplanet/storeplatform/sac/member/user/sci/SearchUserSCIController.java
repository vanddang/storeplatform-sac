/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 2. 12. Updated by : 김다슬, 인크로스.
 */
@LocalSCI
@RequestMapping(value = "/user")
public class SearchUserSCIController implements SearchUserSCI {

	@Autowired
	private UserSearchService userSearchService; // 회원 조회 서비스 인터페이스.

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI#SearchUserByUserKey(com.skplanet
	 * .storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq)
	 */
	@Override
	@RequestMapping(value = "/searchUserByUserKey", method = RequestMethod.POST)
	public SearchUserSacRes SearchUserByUserKey(/* SacRequestHeader requestHeader, */SearchUserSacReq request) {

		SacRequestHeader requestHeader = new SacRequestHeader(); // client-internal에 공통으로 생성되면 삭제 후 Bypass 예정.
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setSystemId("S01-01001");
		tenantHeader.setTenantId("S01");
		requestHeader.setTenantHeader(tenantHeader);

		// TODO 1. 회원 정보 조회 SC API 호출. - this.userSearchService.searchUser
		DetailReq detailRequest = new DetailReq();
		UserInfo userInfo = this.userSearchService.searchUser(detailRequest, requestHeader);

		// TODO 2. 사용자 휴대기기 등록 대수가 1개 이상일 경우 리스트 가져오기.
		// TODO 2-1.
		// if( userInfo.getDeviceCount() > 0 )
		// 디바이스 리스트 조회 SC API 호출. - this.userSearchService.listDevice

		// TODO 2-2.
		// else - 등록기기 없는경우, size=0 인 List 내려주기.

		// TODO 3. 파라미터 셋팅해서 Response.
		SearchUserSacRes searchUserSacRes = new SearchUserSacRes();

		return null;
	}
}
