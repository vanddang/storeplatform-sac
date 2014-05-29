/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.sci.PurchaseUserInfoSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 구매내역 회원정보 변경.
 * 
 * Updated on : 2014. 2. 26. Updated by : 조용진, NTELS.
 */
@Service
public class PurchaseUserInfoSacServiceImpl implements PurchaseUserInfoSacService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseUserInfoSCI purchaseUserInfoSCI;

	@Autowired
	private SearchUserSCI searchUserSCI;

	/**
	 * 구매내역 회원정보 변경.
	 * 
	 * @param userInfoScReq
	 *            구매내역요청
	 * @return UserInfoScRes
	 */
	@Override
	public UserInfoScRes updateUserDevice(UserInfoScReq userInfoScReq) {

		// String tenantId = userInfoScReq.getTenantId();
		String newUserKey = userInfoScReq.getNewUserKey();
		String newDeviceKey = "";

		if (StringUtils.isBlank(userInfoScReq.getDeviceKey())) {
			newDeviceKey = this.purchaseUserInfoSCI.searchDeviceKey(userInfoScReq);

			if (StringUtils.isBlank(newDeviceKey)) {
				UserInfoScRes userInfoScRes = new UserInfoScRes();
				userInfoScRes.setCount(0);
				return userInfoScRes;
			}
		} else {
			newDeviceKey = userInfoScReq.getNewDeviceKey();
		}
		SearchUserDeviceSac searchUserDeviceSac = new SearchUserDeviceSac();
		searchUserDeviceSac.setUserKey(newUserKey);
		searchUserDeviceSac.setDeviceKey(newDeviceKey);

		List<SearchUserDeviceSac> searchUserDeviceSacList = new ArrayList<SearchUserDeviceSac>();
		searchUserDeviceSacList.add(searchUserDeviceSac);

		SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();
		searchUserDeviceSacReq.setSearchUserDeviceReqList(searchUserDeviceSacList);

		SearchUserDeviceSacRes searchUserDeviceSacRes = null;
		try {
			searchUserDeviceSacRes = this.searchUserSCI.searchUserByDeviceKey(searchUserDeviceSacReq);
			Map<String, UserDeviceInfoSac> userDeviceInfoMap = searchUserDeviceSacRes.getUserDeviceInfo();
			if (userDeviceInfoMap == null || (userDeviceInfoMap.containsKey(newDeviceKey) == false)) {
				throw new StorePlatformException("SAC_PUR_4106", newUserKey, newDeviceKey);
			}
		} catch (StorePlatformException e) {
			if (StringUtils.equals(e.getCode(), PurchaseConstants.SACINNER_MEMBER_RESULT_NOTFOUND)) {
				throw new StorePlatformException("SAC_PUR_4106", newUserKey, newDeviceKey);
			} else {
				throw e;
			}
		}

		return this.purchaseUserInfoSCI.updateUserDevice(userInfoScReq);
	}

}
