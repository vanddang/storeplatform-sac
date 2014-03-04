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
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSac;
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

		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add(userKey);

		SearchUserSacReq userReq = new SearchUserSacReq();
		userReq.setUserKeyList(userKeyList);
		Map<String, UserInfoSac> userMap = this.searchUserSCI.searchUserByUserKey(userReq).getUserInfo();

		UserInfoSac userInfo = userMap.get(userKey);
		if (userInfo == null) {
			return null;
		}

		SearchDeviceIdSacReq deviceReq = new SearchDeviceIdSacReq();
		deviceReq.setUserKey(userKey);
		deviceReq.setDeviceKey(deviceKey);
		SearchDeviceIdSacRes deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
		if (deviceRes == null) {
			return null;
		}

		PurchaseUserDevice purchaseUserDevice = new PurchaseUserDevice();
		purchaseUserDevice.setTenantId(tenantId);
		purchaseUserDevice.setUserKey(userKey);
		purchaseUserDevice.setUserId(userInfo.getUserId());
		purchaseUserDevice.setUserMainStatus(userInfo.getUserMainStatus());
		purchaseUserDevice.setUserSubStatus(userInfo.getUserSubStatus());
		purchaseUserDevice.setUserType(userInfo.getUserType());
		purchaseUserDevice.setDeviceKey(deviceKey);
		purchaseUserDevice.setDeviceId(deviceRes.getDeviceId());
		purchaseUserDevice.setDeviceModelCd("SHV-E210S"); // TAKTODO:: DUMMY
		purchaseUserDevice.setTelecom("SKT"); // TAKTODO:: DUMMY
		purchaseUserDevice.setAge(20); // TAKTODO:: DUMMY

		return purchaseUserDevice;
	}

}
