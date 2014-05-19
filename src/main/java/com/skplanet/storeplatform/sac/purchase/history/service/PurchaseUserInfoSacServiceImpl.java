/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.history.sci.PurchaseUserInfoSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScRes;

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

	// @Autowired
	// private PurchaseMemberRepository purchaseMemberRepository;
	// @Autowired
	// private SearchUserSCI searchUserSCI;

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
		// String newUserKey = userInfoScReq.getNewUserKey();
		// String newDeviceKey = "";
		//
		// if (StringUtils.isBlank(userInfoScReq.getDeviceKey())) {
		// newDeviceKey = this.purchaseUserInfoSCI.searchDeviceKey(userInfoScReq);
		// } else {
		// newDeviceKey = userInfoScReq.getNewDeviceKey();
		// }
		// SearchUserDeviceSac searchUserDeviceSac = new SearchUserDeviceSac();
		// searchUserDeviceSac.setUserKey(newUserKey);
		// searchUserDeviceSac.setDeviceKey(newDeviceKey);
		//
		// List<SearchUserDeviceSac> searchUserDeviceSacList = new ArrayList<SearchUserDeviceSac>();
		// searchUserDeviceSacList.add(searchUserDeviceSac);
		//
		// SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();
		// searchUserDeviceSacReq.setSearchUserDeviceReqList(searchUserDeviceSacList);
		//
		// SearchUserDeviceSacRes searchUserDeviceSacRes = null;
		// try {
		// searchUserDeviceSacRes = this.searchUserSCI.searchUserByDeviceKey(searchUserDeviceSacReq);
		// Map<String, UserDeviceInfoSac> userDeviceInfoMap = searchUserDeviceSacRes.getUserDeviceInfo();
		// if (userDeviceInfoMap == null || (userDeviceInfoMap.containsKey(newDeviceKey) == false)) {
		// throw new StorePlatformException("SAC_PUR_4106", newUserKey, newDeviceKey);
		// }
		// } catch (StorePlatformException e) {
		// throw new StorePlatformException("SAC_PUR_4106", newUserKey, newDeviceKey);
		// }

		return this.purchaseUserInfoSCI.updateUserDevice(userInfoScReq);
	}

}
