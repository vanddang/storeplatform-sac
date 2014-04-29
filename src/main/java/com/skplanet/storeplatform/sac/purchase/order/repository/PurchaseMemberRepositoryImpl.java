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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.sci.MiscellaneousSCI;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacReq.PolicyCode;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.GetIndividualPolicySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.IndividualPolicyInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSac;
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
	private SellerSearchSCI sellerSearchSCI;
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

		SearchUserDeviceSac searchUserDeviceSac = new SearchUserDeviceSac();
		searchUserDeviceSac.setUserKey(userKey);
		searchUserDeviceSac.setDeviceKey(deviceKey);

		List<SearchUserDeviceSac> searchUserDeviceSacList = new ArrayList<SearchUserDeviceSac>();
		searchUserDeviceSacList.add(searchUserDeviceSac);

		SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();
		searchUserDeviceSacReq.setSearchUserDeviceReqList(searchUserDeviceSacList);

		SearchUserDeviceSacRes searchUserDeviceSacRes = null;
		try {
			searchUserDeviceSacRes = this.searchUserSCI.searchUserByDeviceKey(searchUserDeviceSacReq);
		} catch (StorePlatformException e) {
			if (StringUtils.equals(e.getCode(), PurchaseConstants.SACINNER_MEMBER_RESULT_NOTFOUND)) {
				return null;
			} else {
				throw e;
			}
		}

		Map<String, UserDeviceInfoSac> userDeviceInfoMap = searchUserDeviceSacRes.getUserDeviceInfo();
		if (userDeviceInfoMap == null || (userDeviceInfoMap.containsKey(deviceKey) == false)) {
			return null;
		}
		UserDeviceInfoSac userDeviceInfoSac = userDeviceInfoMap.get(deviceKey);
		PurchaseUserDevice purchaseUserDevice = new PurchaseUserDevice();
		purchaseUserDevice.setTenantId(tenantId);
		purchaseUserDevice.setUserKey(userKey);
		purchaseUserDevice.setUserName(userDeviceInfoSac.getUserName());
		purchaseUserDevice.setUserId("test"); // TAKTODO:: 회원Part에 응답값으로 추가
		// purchaseUserDevice.setUserId(userInfo.getUserId());
		// purchaseUserDevice.setUserType(userInfo.getUserType());
		purchaseUserDevice.setUserMainStatus(userDeviceInfoSac.getUserMainStatus());
		purchaseUserDevice.setUserSubStatus(userDeviceInfoSac.getUserSubStatus());
		purchaseUserDevice.setRealName(StringUtils.equals(userDeviceInfoSac.getIsRealName(), PurchaseConstants.USE_Y));
		purchaseUserDevice.setDeviceKey(deviceKey);
		purchaseUserDevice.setDeviceId(userDeviceInfoSac.getDeviceId());
		purchaseUserDevice.setDeviceModelCd(userDeviceInfoSac.getDeviceModelNo());
		purchaseUserDevice.setTelecom(userDeviceInfoSac.getDeviceTelecom());
		// 연령체크 안함: 생년월일도 * 문자 포함으로 확인불가
		// purchaseUserDevice.setAge(StringUtils.isNotBlank(userDeviceInfoSac.getUserBirthday()) ? this
		// .getCurrDayAge(userDeviceInfoSac.getUserBirthday()) : 0);

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

		GetIndividualPolicySacRes getIndividualPolicySacRes = null;
		try {
			getIndividualPolicySacRes = this.miscellaneousSCI.getIndividualPolicy(getIndividualPolicySacReq);
		} catch (StorePlatformException e) {
			if (StringUtils.equals(e.getCode(), PurchaseConstants.SACINNER_MEMBER_RESULT_NOTFOUND)) {
				return null;
			} else {
				throw e;
			}
		}

		Map<String, IndividualPolicyInfoSac> resMap = new HashMap<String, IndividualPolicyInfoSac>();

		List<IndividualPolicyInfoSac> individualPolicyInfoSacList = getIndividualPolicySacRes.getPolicyList();
		for (IndividualPolicyInfoSac individualPolicyInfoSac : individualPolicyInfoSacList) {
			resMap.put(individualPolicyInfoSac.getPolicyCode(), individualPolicyInfoSac);
		}

		return resMap;
	}

	/**
	 * 
	 * <pre>
	 * 판매자 회원 정보 조회.
	 * </pre>
	 * 
	 * @param sellerKey
	 *            판매자 내부 회원 번호
	 * @return 판매자 정보
	 */
	@Override
	public SellerMbrSac searchSellerInfo(String sellerKey) {
		SellerMbrSac sellerMbrSac = new SellerMbrSac();
		sellerMbrSac.setSellerKey(sellerKey);
		List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
		sellerMbrSacList.add(sellerMbrSac);

		DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
		detailInformationSacReq.setSellerMbrSacList(sellerMbrSacList);

		DetailInformationSacRes detailInformationSacRes = this.sellerSearchSCI
				.detailInformation(detailInformationSacReq);
		Map<String, List<SellerMbrSac>> sellerMap = detailInformationSacRes.getSellerMbrListMap();
		List<SellerMbrSac> sellerList = sellerMap.get(sellerKey);
		if (CollectionUtils.isNotEmpty(sellerList)) {
			return sellerList.get(0);
		} else {
			return null;
		}
	}

	/*
	 * 
	 * <pre> 생일 일자 기준으로 나이 계산. </pre>
	 * 
	 * @param birthday 생일
	 * 
	 * @return 나이
	 */
	// private int getCurrDayAge(String birthday) {
	// String currday = DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyyyMMdd");
	// int baseAge = Integer.parseInt(currday.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4)) + 1;
	// return (currday.substring(4, 8).compareTo(birthday.substring(4, 8)) > 0 ? baseAge - 1 : baseAge);
	// }
}
