/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.user.sci.CIInfraSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraDetailUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraDetailUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraListUserKeyRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraListUserKeyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraSearchUserInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraSearchUserInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraUserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraDetailUserSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraDetailUserSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraListUserKeySacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraListUserKeySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraSearchUserInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraSearchUserInfoSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraUserInfoSac;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * CI Infra API 인터페이스 구현 클래스
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
@Service
public class CIInfraServiceImpl implements CIInfraService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CIInfraServiceImpl.class);

	@Autowired
	private CIInfraSCI ciInfraSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.CIInfraService#searchUserInfo(com.skplanet.storeplatform.sac
	 * .common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraSearchUserInfoSacReq)
	 */
	@Override
	public CIInfraSearchUserInfoSacRes searchUserInfo(SacRequestHeader requestHeader,
			CIInfraSearchUserInfoSacReq request) {

		/* 공통 헤더 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/* SC 회원 연동 */
		CIInfraSearchUserInfoRequest ciInfraSearchUserInfoRequest = new CIInfraSearchUserInfoRequest();
		ciInfraSearchUserInfoRequest.setCommonRequest(commonRequest);
		ciInfraSearchUserInfoRequest.setImSvcNo(request.getImSvcNo());
		ciInfraSearchUserInfoRequest.setTrxNo(request.getTrxNo());
		CIInfraSearchUserInfoResponse ciInfraSearchUserInfoResponse = this.ciInfraSCI
				.searchUserInfo(ciInfraSearchUserInfoRequest);

		/* response 셋팅 */
		CIInfraSearchUserInfoSacRes response = new CIInfraSearchUserInfoSacRes();
		response.setTrxNo(request.getTrxNo());
		response.setUserId(ciInfraSearchUserInfoResponse.getCiInfraUserInfo().getUserId());
		response.setImSvcNo(ciInfraSearchUserInfoResponse.getCiInfraUserInfo().getImSvcNo());
		response.setImMbrNo(ciInfraSearchUserInfoResponse.getCiInfraUserInfo().getImMbrNo());
		response.setUserKey(ciInfraSearchUserInfoResponse.getUserKey());

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.CIInfraService#listUserKey(com.skplanet.storeplatform.sac.
	 * common.header.vo.SacRequestHeader, com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraListUserKeySacReq)
	 */
	@Override
	public CIInfraListUserKeySacRes listUserKey(SacRequestHeader requestHeader, CIInfraListUserKeySacReq request) {

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/* SC 회원 연동 */
		CIInfraListUserKeyRequest ciInfraListUserKeyRequest = new CIInfraListUserKeyRequest();
		ciInfraListUserKeyRequest.setCommonRequest(commonRequest);
		ciInfraListUserKeyRequest.setSearchType(request.getSearchType());
		ciInfraListUserKeyRequest.setSearchDay(request.getSearchDay());
		CIInfraListUserKeyResponse ciInfraListUserKeyResponse = this.ciInfraSCI.listUserKey(ciInfraListUserKeyRequest);

		/* response 셋팅 */
		CIInfraListUserKeySacRes response = new CIInfraListUserKeySacRes();
		ArrayList<CIInfraUserInfo> ciInfraUserInfoList = ciInfraListUserKeyResponse.getCiInfraUserInfoList();
		ArrayList<CIInfraUserInfoSac> ciInfraUserInfoListSac = new ArrayList<CIInfraUserInfoSac>();
		for (CIInfraUserInfo info : ciInfraUserInfoList) {
			CIInfraUserInfoSac infoSac = new CIInfraUserInfoSac();
			infoSac.setImMbrNo(info.getImMbrNo());
			infoSac.setUserKey(info.getUserKey());
			infoSac.setUserType(info.getUserType());
			infoSac.setLastTime(info.getLastTime());
			ciInfraUserInfoListSac.add(infoSac);
		}
		response.setUserInfoList(ciInfraUserInfoListSac);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.service.CIInfraService#detail(com.skplanet.storeplatform.sac.common
	 * .header.vo.SacRequestHeader, com.skplanet.storeplatform.sac.client.member.vo.user.CIInfraDetailUserSacReq)
	 */
	@Override
	public CIInfraDetailUserSacRes detail(SacRequestHeader requestHeader, CIInfraDetailUserSacReq request) {

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/* SC 회원 연동 */
		CIInfraDetailUserRequest ciInfraDetailUserRequest = new CIInfraDetailUserRequest();
		ciInfraDetailUserRequest.setCommonRequest(commonRequest);
		ciInfraDetailUserRequest.setKeyType(request.getKeyType());
		ciInfraDetailUserRequest.setKey(request.getKey());
		CIInfraDetailUserResponse ciInfraDetailUserResponse = this.ciInfraSCI.detail(ciInfraDetailUserRequest);

		/* response 셋팅 */
		CIInfraDetailUserSacRes response = new CIInfraDetailUserSacRes();
		response.setCi(ciInfraDetailUserResponse.getCiInfraUserInfo().getCi());
		response.setImMbrNo(ciInfraDetailUserResponse.getCiInfraUserInfo().getImMbrNo());
		response.setUserKey(ciInfraDetailUserResponse.getCiInfraUserInfo().getUserKey());
		response.setUserId(ciInfraDetailUserResponse.getCiInfraUserInfo().getUserId());
		response.setMdn(ciInfraDetailUserResponse.getCiInfraUserInfo().getMdn());
		response.setName(ciInfraDetailUserResponse.getCiInfraUserInfo().getName());
		response.setBirth(ciInfraDetailUserResponse.getCiInfraUserInfo().getBirth());
		response.setSex(ciInfraDetailUserResponse.getCiInfraUserInfo().getSex());
		response.setPhone(ciInfraDetailUserResponse.getCiInfraUserInfo().getPhone());
		response.setDi(ciInfraDetailUserResponse.getCiInfraUserInfo().getDi());
		response.setLastTime(ciInfraDetailUserResponse.getCiInfraUserInfo().getLastTime());

		return response;
	}

}
