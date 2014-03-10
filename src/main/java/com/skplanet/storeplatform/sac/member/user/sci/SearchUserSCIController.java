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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSac;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.OcbInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchUserDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchUserReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserInfoByDeviceKey;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserInfoByUserKey;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.service.UserOcbService;
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

	@Autowired
	private UserOcbService userOcbService;

	@Autowired
	private MemberCommonComponent mcc;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI
	 * #searchUserByUserKey(com.skplanet
	 * .storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq)
	 */
	@Override
	@RequestMapping(value = "/searchUserByUserKey", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserSacRes searchUserByUserKey(@RequestBody @Validated SearchUserSacReq request) {

		// 헤더 정보 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		LOGGER.info("[SearchUserSCIController.searchUserByUserKey] RequestHeader : {}, \nRequestParameter : {}", requestHeader, request);

		List<String> userKeyList = request.getUserKeyList();
		SearchUserReq searchUserReq = new SearchUserReq();
		searchUserReq.setUserKeyList(userKeyList);

		Map<String, UserInfoByUserKey> userInfoMap = this.userSearchService.searchUserByUserKey(requestHeader, searchUserReq);

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

		LOGGER.info("[SearchUserSCIController.searchUserByUserKey] ResponseParameter : {}", searchUserSacRes);
		return searchUserSacRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI
	 * #searchUserPayplanet(com.skplanet
	 * .storeplatform.sac.client.internal.member
	 * .user.vo.SearchUserPayplanetSacReq)
	 */
	@Override
	@RequestMapping(value = "/searchUserPayplanet", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserPayplanetSacRes searchUserPayplanet(@RequestBody @Validated SearchUserPayplanetSacReq request) {

		// 헤더 정보 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		LOGGER.info("[SearchUserSCIController.searchUserPayplanet] RequestHeader : {}, \nRequestParameter : {}", requestHeader, request);

		String deviceKey = StringUtil.nvl(request.getDeviceKey(), "");
		String userKey = StringUtil.nvl(request.getUserKey(), "");

		// 회원기본정보조회 deviceKey로 userKey세팅
		UserInfo info = new UserInfo();
		if (!userKey.equals("")) {
			info = this.mcc.getUserBaseInfo("userKey", request.getUserKey(), requestHeader);
		} else if (!deviceKey.equals("")) {
			info = this.mcc.getUserBaseInfo("deviceKey", request.getDeviceKey(), requestHeader);
		}

		request.setUserKey(info.getUserKey());

		// Store 약관동의목록 조회 US010609 = POLICY_AGREEMENT_CLAUSE_COMMUNICATION_CHARGE
		ListTermsAgreementSacReq agreementReq = new ListTermsAgreementSacReq();
		agreementReq.setUserKey(request.getUserKey());

		String skpAgreementYn = "N";
		try {
			ListTermsAgreementSacRes agreementRes = this.userSearchService.listTermsAgreement(requestHeader, agreementReq);

			for (Agreement agree : agreementRes.getAgreementList()) {
				if (agree.getExtraAgreementId().equals(MemberConstants.POLICY_AGREEMENT_CLAUSE_COMMUNICATION_CHARGE)) {
					if (agree.getIsExtraAgreement().equals("Y")) {
						skpAgreementYn = "Y";
					} else {
						skpAgreementYn = "N";
					}
				}
			}
		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				skpAgreementYn = "N";
			}
		}

		// OCB 카드번호
		GetOcbInformationReq ocbReq = new GetOcbInformationReq();
		ocbReq.setUserKey(request.getUserKey());

		String ocbCardNumber = "";
		try {
			GetOcbInformationRes ocbRes = this.userOcbService.getOcbInformation(requestHeader, ocbReq);
			for (OcbInfo ocb : ocbRes.getOcbInfoList()) {
				ocbCardNumber = ocb.getCardNumber();
			}

		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				ocbCardNumber = "";
			}
		}

		// OCB이용약관 동의여부 searchOneId
		MbrOneidSacReq oneIdReq = new MbrOneidSacReq();
		oneIdReq.setUserKey(request.getUserKey());

		String ocbAgreementYn = "N";
		try {
			MbrOneidSacRes oneIdRes = this.userSearchService.searchUserOneId(requestHeader, oneIdReq);
			ocbAgreementYn = oneIdRes.getIsMemberPoint();
		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				ocbAgreementYn = "N";
			} else if (ex.getErrorInfo().getCode().equals(MemberConstants.SAC_ERROR_NO_ONEID)) {
				ocbAgreementYn = "N";
			}
			LOGGER.info("====== OneId Response : {}", ex.getCode());
		}

		SearchUserPayplanetSacRes payplanetSacRes = new SearchUserPayplanetSacRes();
		payplanetSacRes.setSkpAgreementYn(skpAgreementYn);
		payplanetSacRes.setOcbCardNumber(ocbCardNumber);
		payplanetSacRes.setOcbAgreementYn(ocbAgreementYn);

		return payplanetSacRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI
	 * #searchUserPayplanet(com.skplanet
	 * .storeplatform.sac.client.internal.member
	 * .user.vo.SearchUserPayplanetSacReq)
	 */
	@Override
	@RequestMapping(value = "/searchUserByDeviceKey", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserDeviceSacRes searchUserByDeviceKey(@RequestBody @Validated SearchUserDeviceSacReq request) {

		// 헤더 정보 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		LOGGER.info("[SearchUserSCIController.searchUserByDeviceKey] RequestHeader : {}, \nRequestParameter : {}", requestHeader, request);

		List<String> deviceKeyList = request.getDeviceKeyList();
		SearchUserDeviceReq searchUserDeviceReq = new SearchUserDeviceReq();
		searchUserDeviceReq.setDeviceKeyList(deviceKeyList);

		Map<String, UserInfoByDeviceKey> userInfoMap = this.userSearchService.searchUserByDeviceKey(requestHeader, searchUserDeviceReq);

		Map<String, UserDeviceInfoSac> resMap = new HashMap<String, UserDeviceInfoSac>();
		UserDeviceInfoSac userDeviceInfoSac;

		for (int i = 0; i < deviceKeyList.size(); i++) {
			if (userInfoMap.get(deviceKeyList.get(i)) != null) {
				userDeviceInfoSac = new UserDeviceInfoSac();
				userDeviceInfoSac.setDeviceId(userInfoMap.get(deviceKeyList.get(i)).getDeviceId());
				userDeviceInfoSac.setDeviceModelNo(userInfoMap.get(deviceKeyList.get(i)).getDeviceModelNo());
				userDeviceInfoSac.setDeviceTelecom(userInfoMap.get(deviceKeyList.get(i)).getDeviceTelecom());
				userDeviceInfoSac.setUserBirthday(userInfoMap.get(deviceKeyList.get(i)).getUserBirthday());
				userDeviceInfoSac.setUserName(userInfoMap.get(deviceKeyList.get(i)).getUserName());
				userDeviceInfoSac.setIsRealName(userInfoMap.get(deviceKeyList.get(i)).getIsRealName());

				resMap.put(deviceKeyList.get(i), userDeviceInfoSac);
			}
		}

		SearchUserDeviceSacRes searchUserDeviceSacRes = new SearchUserDeviceSacRes();
		searchUserDeviceSacRes.setUserDeviceInfo(resMap);

		LOGGER.info("[SearchUserSCIController.searchUserByDeviceKey] ResponseParameter : {}", searchUserDeviceSacRes.toString());

		return searchUserDeviceSacRes;
	}
}
