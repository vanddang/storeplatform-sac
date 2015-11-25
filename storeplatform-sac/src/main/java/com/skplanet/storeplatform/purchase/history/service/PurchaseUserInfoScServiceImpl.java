/**
 * 
 */
package com.skplanet.storeplatform.purchase.history.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.UserInfoScRes;

/**
 * 구매내역 회원정보 변경.
 * 
 * Updated on : 2014. 2. 26. Updated by : 조용진, NTELS.
 */
@Service
public class PurchaseUserInfoScServiceImpl implements PurchaseUserInfoScService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 구매내역 회원정보 변경.
	 * 
	 * @param userInfoScReq
	 *            구매내역요청
	 * @return UserInfoScRes
	 */
	@Override
	public UserInfoScRes updateUserDevice(UserInfoScReq userInfoScReq) {
		UserInfoScRes userInfoScRes = new UserInfoScRes();

		int count = 0;

		// 구매 업데이트
		count += this.commonDAO.update("PurchaseUserInfo.updatePurchase", userInfoScReq);
		this.logger.debug("PurchaseUserInfo.updatePurchase          : {}", count);
		// 구매상세(수신자) 업데이트
		count += this.commonDAO.update("PurchaseUserInfo.updatePurchaseDetail", userInfoScReq);
		this.logger.debug("PurchaseUserInfo.updatePurchaseDetail    : {}", count);
		// 구매상세(발신자) 업데이트
		count += this.commonDAO.update("PurchaseUserInfo.updatePurchaseSend", userInfoScReq);
		this.logger.debug("PurchaseUserInfo.updatePurchaseSend      : {}", count);
		// 결제내역 업데이트
		count += this.commonDAO.update("PurchaseUserInfo.updatePayment", userInfoScReq);
		this.logger.debug("PurchaseUserInfo.updatePurchase          : {}", count);
		// 자동결제 업데이트
		count += this.commonDAO.update("PurchaseUserInfo.updateAutoPaymentCancel", userInfoScReq);
		this.logger.debug("PurchaseUserInfo.updateAutoPaymentCancel : {}", count);
		// 구매내역전송(보류)
		count += this.commonDAO.update("PurchaseUserInfo.updateTransfer", userInfoScReq);
		this.logger.debug("PurchaseUserInfo.updateTransfer          : {}", count);
		// 마일리지 업데이트
		count += this.commonDAO.update("PurchaseUserInfo.updateMileage", userInfoScReq);
		this.logger.debug("PurchaseUserInfo.updateMileage          : {}", count);

		userInfoScRes.setCount(count);
		return userInfoScRes;
	}

	/**
	 * 구매내역 회원정보 변경.
	 * 
	 * @param userInfoScReq
	 *            구매내역요청
	 * @return String
	 */
	@Override
	public String searchDeviceKey(UserInfoScReq userInfoScReq) {

		return (String) this.commonDAO.queryForObject("PurchaseUserInfo.searchDeviceKey", userInfoScReq);
	}

}
