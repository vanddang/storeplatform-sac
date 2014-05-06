/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import java.util.ArrayList;
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
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.OcbInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchExtentReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchUserDevice;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchUserDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserInfoByDeviceKey;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
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

	/**
	 * <pre>
	 * userKey 목록을 이용하여 회원정보 목록조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchUserSacReq
	 * @return SearchUserSacRes
	 */
	@Override
	@RequestMapping(value = "/searchUserByUserKey", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserSacRes searchUserByUserKey(@RequestBody @Validated SearchUserSacReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		// 헤더 정보 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		SearchUserSacRes searchUserSacRes = this.userSearchService.searchUserByUserKey(requestHeader, request);
		LOGGER.info("Response : user count : {}", searchUserSacRes.getUserInfo().size());
		return searchUserSacRes;
	}

	/**
	 * <pre>
	 * deviceId를 이용한 회원정보 조회.
	 * </pre>
	 * 
	 * @param request
	 *            UserInfoSacReq
	 * @return UserInfoSacRes
	 */
	@Override
	@RequestMapping(value = "/searchUserBydeviceId", method = RequestMethod.POST)
	@ResponseBody
	public UserInfoSacRes searchUserBydeviceId(@RequestBody @Validated UserInfoSacReq request) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		DetailReq detailReq = new DetailReq();
		detailReq.setDeviceId(request.getDeviceId());
		SearchExtentReq searchExtentReq = new SearchExtentReq();
		searchExtentReq.setDeviceInfoYn(MemberConstants.USE_Y);
		searchExtentReq.setUserInfoYn(MemberConstants.USE_Y);
		detailReq.setSearchExtent(searchExtentReq);

		// 사용자 회원 기본정보 조회 SAC 내부 메서드 호출
		DetailRes detailRes = this.userSearchService.detail(requestHeader, detailReq);

		UserInfoSacRes response = new UserInfoSacRes();
		response.setUserKey(detailRes.getUserKey());
		LOGGER.debug("detailRes.getDeviceInfoList() : {}", detailRes.getDeviceInfoList());
		response.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
		response.setUserMainStatus(detailRes.getUserInfo().getUserMainStatus());
		response.setUserSubStatus(detailRes.getUserInfo().getUserSubStatus());
		response.setLoginStatus(detailRes.getUserInfo().getLoginStatusCode());

		LOGGER.info("Response : {}", response.getUserKey());
		return response;
	}

	/**
	 * <pre>
	 * 결제페이지 노출 정보 조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchUserPayplanetSacReq
	 * @return SearchUserPayplanetSacRes
	 */
	@Override
	@RequestMapping(value = "/searchUserPayplanet", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserPayplanetSacRes searchUserPayplanet(@RequestBody @Validated SearchUserPayplanetSacReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		// 헤더 정보 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		String deviceKey = StringUtil.nvl(request.getDeviceKey(), "");
		String userKey = StringUtil.nvl(request.getUserKey(), "");

		// 회원정보조회 deviceKey로 userKey세팅
		DetailReq detailReq = new DetailReq();
		if (!userKey.equals("")) {
			detailReq.setUserKey(request.getUserKey());
		} else if (!deviceKey.equals("")) {
			detailReq.setDeviceKey(request.getDeviceKey());
		}
		DetailRes detailRes = this.userSearchService.searchUser(detailReq, requestHeader);

		request.setUserKey(detailRes.getUserKey());

		// Store 약관동의목록 조회 US010609 = POLICY_AGREEMENT_CLAUSE_COMMUNICATION_CHARGE
		String skpAgreementYn = "N";
		try {
			for (Agreement agree : detailRes.getAgreementList()) {
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

		// OCB이용약관 동의여부 searchOneId
		String ocbAgreementYn = "N";
		try {
			ocbAgreementYn = StringUtil.setTrimYn(detailRes.getUserInfo().getIsMemberPoint());
		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				ocbAgreementYn = "N";
			} else if (ex.getErrorInfo().getCode().equals(MemberConstants.SAC_ERROR_NO_ONEID)) {
				ocbAgreementYn = "N";
			}
			LOGGER.debug("====== OneId Response : {}", ex.getCode());
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

		SearchUserPayplanetSacRes payplanetSacRes = new SearchUserPayplanetSacRes();
		payplanetSacRes.setSkpAgreementYn(skpAgreementYn);
		payplanetSacRes.setOcbCardNumber(StringUtil.setTrim(ocbCardNumber));
		payplanetSacRes.setOcbAgreementYn(ocbAgreementYn);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(payplanetSacRes));

		return payplanetSacRes;
	}

	/**
	 * <pre>
	 * userKey, deviceKey 목록을 이용하여 회원정보 목록조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchUserDeviceSacReq
	 * @return SearchUserDeviceSacRes
	 */
	@Override
	@RequestMapping(value = "/searchUserByDeviceKey", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserDeviceSacRes searchUserByDeviceKey(@RequestBody @Validated SearchUserDeviceSacReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		// 헤더 정보 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		// Respone 를 가지고 오기 위한 devceKeyList Setting
		List<String> deviceKeyList = new ArrayList<String>();

		// Request 를 보내기 위한 세팅
		List<SearchUserDevice> schUserDeviceList = new ArrayList<SearchUserDevice>();

		for (SearchUserDeviceSac schUserDevice : request.getSearchUserDeviceReqList()) {
			String deviceKey = schUserDevice.getDeviceKey();
			SearchUserDevice schUser = new SearchUserDevice();
			schUser.setDeviceKey(schUserDevice.getDeviceKey());
			schUser.setUserKey(schUserDevice.getUserKey());

			schUserDeviceList.add(schUser);
			deviceKeyList.add(deviceKey);
		}

		SearchUserDeviceReq searchUserDeviceReq = new SearchUserDeviceReq();
		searchUserDeviceReq.setSearchUserDeviceReqList(schUserDeviceList);

		Map<String, UserInfoByDeviceKey> userInfoMap = this.userSearchService.searchUserByDeviceKey(requestHeader,
				searchUserDeviceReq);

		Map<String, UserDeviceInfoSac> resMap = new HashMap<String, UserDeviceInfoSac>();
		UserDeviceInfoSac userDeviceInfoSac;

		for (int i = 0; i < deviceKeyList.size(); i++) {
			if (userInfoMap.get(deviceKeyList.get(i)) != null) {
				userDeviceInfoSac = new UserDeviceInfoSac();
				userDeviceInfoSac.setDeviceId(userInfoMap.get(deviceKeyList.get(i)).getDeviceId());
				userDeviceInfoSac.setDeviceModelNo(userInfoMap.get(deviceKeyList.get(i)).getDeviceModelNo());
				userDeviceInfoSac.setDeviceTelecom(userInfoMap.get(deviceKeyList.get(i)).getDeviceTelecom());
				userDeviceInfoSac.setIsRealName(userInfoMap.get(deviceKeyList.get(i)).getIsRealName());
				userDeviceInfoSac.setUserMainStatus(userInfoMap.get(deviceKeyList.get(i)).getUserMainStatus());
				userDeviceInfoSac.setUserSubStatus(userInfoMap.get(deviceKeyList.get(i)).getUserSubStatus());

				if (userInfoMap.get(deviceKeyList.get(i)).getIsRealName().equals("Y")) {
					String birthday = StringUtil.nvl(userInfoMap.get(deviceKeyList.get(i)).getAuthBirthday(), "");
					String name = StringUtil.nvl(userInfoMap.get(deviceKeyList.get(i)).getAuthName(), "");

					if (birthday.equals("")) {
						userDeviceInfoSac.setUserBirthday(userInfoMap.get(deviceKeyList.get(i)).getUserBirthday());
					} else {
						userDeviceInfoSac.setUserBirthday(birthday);
					}

					if (name.equals("")) {
						userDeviceInfoSac.setUserName(userInfoMap.get(deviceKeyList.get(i)).getUserName());
					} else {
						userDeviceInfoSac.setUserName(name);
					}
				} else {
					userDeviceInfoSac.setUserBirthday(userInfoMap.get(deviceKeyList.get(i)).getUserBirthday());
					userDeviceInfoSac.setUserName(userInfoMap.get(deviceKeyList.get(i)).getUserName());
				}

				resMap.put(deviceKeyList.get(i), userDeviceInfoSac);
			}
		}

		SearchUserDeviceSacRes searchUserDeviceSacRes = new SearchUserDeviceSacRes();
		searchUserDeviceSacRes.setUserDeviceInfo(resMap);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(searchUserDeviceSacRes));

		return searchUserDeviceSacRes;
	}
}
