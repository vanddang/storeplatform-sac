/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.service;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.sac.member.user.service.UserExtraInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SearchMbrSellerRequest;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateGiftChargeInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateGiftChargeInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchExtentUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateManagementResponse;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.CreateGiftChargeInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.CreateGiftChargeInfoSacRes;
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
	private SellerSCI sellerSCI;

	@Autowired
	private MemberCommonComponent mcc;

    @Autowired
    private UserExtraInfoService extraInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeㅋㅋㅋ
	 * com.skplanet.storeplatform.sac.member.user.sci.service.UserSCIService#removeSSOCredential(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacReq)
	 */
	@Override
	public RemoveSSOCredentialSacRes removeSSOCredential(SacRequestHeader sacHeader, RemoveSSOCredentialSacReq request) {

		LOGGER.info("{} ssoCredential 초기화", request.getUserKey());
		RemoveSSOCredentialSacRes res = new RemoveSSOCredentialSacRes();

        extraInfoService.modifyExtraInfo(request.getUserKey(), MemberConstants.USER_EXTRA_SYRUP_SSO_CREDENTIAL, "");

		res.setUserKey(request.getUserKey());
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.member.user.sci.service.UserSCIService#createGiftChargeInfo(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.internal.member.user.vo.CreateGiftChargeInfoSacReq)
	 */
	@Override
	public CreateGiftChargeInfoSacRes createGiftChargeInfo(SacRequestHeader sacHeader,
			CreateGiftChargeInfoSacReq request) {
		LOGGER.info("{} 회원 상품권 충전 정보 등록", request.getUserKey());
		CreateGiftChargeInfoSacRes res = new CreateGiftChargeInfoSacRes();

		// 공통 파라미터 셋팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		// 01. 회원 여부 조회
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keySchUserKey.setKeyString(request.getUserKey());
		keySearchList.add(keySchUserKey);
		SearchExtentUserRequest searchExtentUserRequest = new SearchExtentUserRequest();
		searchExtentUserRequest.setCommonRequest(commonRequest);
		searchExtentUserRequest.setKeySearchList(keySearchList);
		searchExtentUserRequest.setUserInfoYn(MemberConstants.USE_Y);
		this.userSCI.searchExtentUser(searchExtentUserRequest);

		// 02. 판매자 회원 여부 조회
		List<KeySearch> sellerKeys = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_INSD_SELLERMBR_NO);
		keySearch.setKeyString(request.getSellerKey());
		sellerKeys.add(keySearch);
		SearchMbrSellerRequest searchMbrSellerRequest = new SearchMbrSellerRequest();
		searchMbrSellerRequest.setCommonRequest(commonRequest);
		if (StringUtil.isNotEmpty(request.getSellerKey())) {
			searchMbrSellerRequest.setKeySearchList(sellerKeys);
		}

		try {
			this.sellerSCI.searchMbrSeller(searchMbrSellerRequest);
		} catch (StorePlatformException e) {
			if (StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				res.setUserKey(""); // 판매자 존재하지 않을 시 빈값 return
				return res;
			}
		}

		// 03. 회원 상품권 충전 정보 등록
		CreateGiftChargeInfoRequest createGiftChargeInfoRequest = new CreateGiftChargeInfoRequest();
		createGiftChargeInfoRequest.setCommonRequest(commonRequest);
		createGiftChargeInfoRequest.setUserKey(request.getUserKey());
		createGiftChargeInfoRequest.setSellerKey(request.getSellerKey());
		createGiftChargeInfoRequest.setBrandName(request.getBrandName());
		createGiftChargeInfoRequest.setBrandId(request.getBrandId());
		createGiftChargeInfoRequest.setChargerId(request.getChargerId());
		createGiftChargeInfoRequest.setChargerName(request.getChargerName());

		CreateGiftChargeInfoResponse createGiftChargeInfoResponse = this.userSCI
				.createGiftChargeInfo(createGiftChargeInfoRequest);
		res.setUserKey(createGiftChargeInfoResponse.getUserKey());

		return res;
	}
}
