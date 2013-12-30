/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.user.vo.MyPagePurchase;
import com.skplanet.storeplatform.sc.client.sci.PaymentSCI;
import com.skplanet.storeplatform.sc.client.sci.PurchaseSCI;
import com.skplanet.storeplatform.sc.client.sci.UserSCI;
import com.skplanet.storeplatform.sc.client.vo.Payment;
import com.skplanet.storeplatform.sc.client.vo.Purchase;
import com.skplanet.storeplatform.sc.client.vo.UserSearch;

/**
 * Member Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013-09-01 Updated by : $$Id$$.
 */
@Component
public class MemberServiceImpl implements MemberService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private PurchaseSCI purchaseSCI;

	@Autowired
	private PaymentSCI paymentSCI;

	/**
	 * MyPage 구매/결제 이력 상세 조회.
	 * 
	 * @param myPagePurchaseVO
	 *            MyPage 구매/결제 이력
	 * @return MyPage 구매/결제 이력 상세
	 */
	@Override
	public MyPagePurchase searchMypagePurchase(MyPagePurchase myPagePurchaseVO) {
		MyPagePurchase resultVO = new MyPagePurchase();

		this.logger.debug("myPagePurchaseVO.getId()  : {}", myPagePurchaseVO.getId());
		this.logger.debug("myPagePurchaseVO.getNum() : {}", myPagePurchaseVO.getNum());

		UserSearch paramUserVO = new UserSearch();
		paramUserVO.setId(myPagePurchaseVO.getId());

		UserSearch userVO = this.userSCI.detail(paramUserVO);
		if (userVO != null) {
			resultVO.setUserId(Integer.toString(myPagePurchaseVO.getId()));
		}

		Purchase paramPurchaseVO = new Purchase();
		paramPurchaseVO.setPid(myPagePurchaseVO.getPid());

		Purchase purchaseVO = this.purchaseSCI.searchDetail(paramPurchaseVO);
		if (purchaseVO != null) {
			resultVO.setPurchaseId(myPagePurchaseVO.getPid());
		}

		Payment paramPaymentVO = new Payment();
		paramPaymentVO.setIdentifier(myPagePurchaseVO.getPid());

		Payment paymentVO = this.paymentSCI.searchDetail(paramPaymentVO);
		if (paymentVO != null) {
			resultVO.setPayId(myPagePurchaseVO.getPid());
		}

		resultVO.setId(myPagePurchaseVO.getId());
		resultVO.setNum(myPagePurchaseVO.getNum());

		resultVO.setPurchaseId(myPagePurchaseVO.getPurchaseId());

		return resultVO;
	}

}
