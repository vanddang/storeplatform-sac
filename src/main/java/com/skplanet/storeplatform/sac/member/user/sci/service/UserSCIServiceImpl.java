/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateManagementResponse;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 회원 내부메소드 호출 Service 구현체.
 * 
 * Updated on : 2015. 9. 22. Updated by : 반범진.
 */
@Service
public class UserSCIServiceImpl implements UserSCIService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSCIServiceImpl.class);

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private MemberCommonComponent mcc;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.sci.service.UserSCIService#removeSSOCredential(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacReq)
	 */
	@Override
	public RemoveSSOCredentialSacRes removeSSOCredential(SacRequestHeader sacHeader, RemoveSSOCredentialSacReq request) {
		RemoveSSOCredentialSacRes res = new RemoveSSOCredentialSacRes();

		UpdateManagementRequest updateManagementRequest = new UpdateManagementRequest();
		List<MbrMangItemPtcr> ptcrList = new ArrayList<MbrMangItemPtcr>();
		MbrMangItemPtcr ptcr = new MbrMangItemPtcr();
		ptcr.setExtraProfile(MemberConstants.USER_EXTRA_SYRUP_SSO_CREDENTIAL);
		ptcr.setExtraProfileValue("");
		ptcrList.add(ptcr);
		updateManagementRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		updateManagementRequest.setUserKey(request.getUserKey());
		updateManagementRequest.setMbrMangItemPtcr(ptcrList);
		UpdateManagementResponse updateManagementResponse = this.userSCI.updateManagement(updateManagementRequest);

		res.setUserKey(updateManagementResponse.getUserKey());
		return res;
	}

}
