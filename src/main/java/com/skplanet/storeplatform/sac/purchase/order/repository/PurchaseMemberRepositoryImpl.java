/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.sci.MiscellaneousSCI;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacReq.PolicyCode;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.IndividualPolicyInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;

/**
 * 
 * 구매 시 필요한 회원Part SAC internal I/F repository
 * 
 * Updated on : 2014. 2. 27. Updated by : 이승택, nTels.
 */
@Component
public class PurchaseMemberRepositoryImpl implements PurchaseMemberRepository {
	@Autowired
	private SearchUserSCI searchUserSCI;
	@Autowired
	private DeviceSCI deviceSCI;
	@Autowired
	private MiscellaneousSCI miscellaneousSCI;

	/**
	 * 
	 * <pre>
	 * 회원/기기 키에 해당하는 회원/기기 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param userKey
	 *            내부 회원 고유 키
	 * @param deviceKey
	 *            내부 디바이스 고유 키
	 * @return 회원/기기 정보
	 */
	@Override
	public PurchaseUserDevice searchUserDeviceByKey(String tenantId, String userKey, String deviceKey) {
		SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();
		List<String> deviceKeyList = new ArrayList<String>();
		deviceKeyList.add(deviceKey);
		searchUserDeviceSacReq.setDeviceKeyList(deviceKeyList);

		SearchUserDeviceSacRes searchUserDeviceSacRes = this.searchUserSCI
				.searchUserByDeviceKey(searchUserDeviceSacReq);

		Map<String, UserDeviceInfoSac> userDeviceInfoMap = searchUserDeviceSacRes.getUserDeviceInfo();
		if (userDeviceInfoMap == null) {
			return null;
		}
		UserDeviceInfoSac userDeviceInfoSac = userDeviceInfoMap.get(deviceKey);
		PurchaseUserDevice purchaseUserDevice = new PurchaseUserDevice();
		purchaseUserDevice.setTenantId(tenantId);
		purchaseUserDevice.setUserKey(userKey);
		// purchaseUserDevice.setUserId(userInfo.getUserId());
		// purchaseUserDevice.setUserType(userInfo.getUserType());
		purchaseUserDevice.setUserMainStatus(userDeviceInfoSac.getUserMainStatus());
		purchaseUserDevice.setUserSubStatus(userDeviceInfoSac.getUserSubStatus());
		purchaseUserDevice.setRealName(StringUtils.equals(userDeviceInfoSac.getIsRealName(), PurchaseConstants.USE_Y));
		purchaseUserDevice.setDeviceKey(deviceKey);
		purchaseUserDevice.setDeviceId(userDeviceInfoSac.getDeviceId());
		purchaseUserDevice.setDeviceModelCd(userDeviceInfoSac.getDeviceModelNo());
		purchaseUserDevice.setTelecom(userDeviceInfoSac.getDeviceTelecom());
		purchaseUserDevice.setAge(StringUtils.isNotBlank(userDeviceInfoSac.getUserBirthday()) ? this
				.getCurrDayAge(userDeviceInfoSac.getUserBirthday()) : 0);

		return purchaseUserDevice;
	}

	/**
	 * <pre>
	 * 회원의 결제 관련 정보 조회: 통신과금 이용약관 동의여부, OCB 이용약관 동의여부, OCB 카드번호.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 NO
	 * @param deviceKey
	 *            내부 디바이스 ID
	 * @return SearchUserPayplanetSacRes
	 */
	@Override
	public SearchUserPayplanetSacRes searchUserPayplanet(String userKey, String deviceKey) {
		SearchUserPayplanetSacReq searchUserPayplanetSacReq = new SearchUserPayplanetSacReq();
		searchUserPayplanetSacReq.setUserKey(userKey);
		searchUserPayplanetSacReq.setDeviceKey(deviceKey);

		return this.searchUserSCI.searchUserPayplanet(searchUserPayplanetSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 회원의 비과금단말 / 구매차단 정책 조회.
	 * </pre>
	 * 
	 * @param deviceKey
	 *            내부 디바이스 ID
	 * @param policyCodeList
	 *            정책코드 목록
	 * @return
	 */
	@Override
	public Map<String, IndividualPolicyInfoSac> getPurchaseUserPolicy(String deviceKey, List<String> policyCodeList) {
		List<PolicyCode> policyCodeObjList = new ArrayList<PolicyCode>();
		PolicyCode policyCodeObj = null;
		for (String policyCode : policyCodeList) {
			policyCodeObj = new PolicyCode();
			policyCodeObj.setPolicyCode(policyCode);
			policyCodeObjList.add(policyCodeObj);
		}
		GetIndividualPolicySacReq getIndividualPolicySacReq = new GetIndividualPolicySacReq();
		getIndividualPolicySacReq.setKey(deviceKey);
		getIndividualPolicySacReq.setPolicyCodeList(policyCodeObjList);

		GetIndividualPolicySacRes getIndividualPolicySacRes = this.miscellaneousSCI
				.getIndividualPolicy(getIndividualPolicySacReq);

		Map<String, IndividualPolicyInfoSac> resMap = new HashMap<String, IndividualPolicyInfoSac>();

		List<IndividualPolicyInfoSac> individualPolicyInfoSacList = getIndividualPolicySacRes.getPolicyList();
		for (IndividualPolicyInfoSac individualPolicyInfoSac : individualPolicyInfoSacList) {
			resMap.put(individualPolicyInfoSac.getKey(), individualPolicyInfoSac);
		}

		return resMap;
	}

	/*
	 * 
	 * <pre> 생일 일자 기준으로 나이 계산. </pre>
	 * 
	 * @param birthday 생일
	 * 
	 * @return 나이
	 */
	private int getCurrDayAge(String birthday) {
		String currday = DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyyyMMdd");
		int baseAge = Integer.parseInt(currday.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4)) + 1;
		return (currday.substring(4, 8).compareTo(birthday.substring(4, 8)) > 0 ? baseAge - 1 : baseAge);
	}
}
