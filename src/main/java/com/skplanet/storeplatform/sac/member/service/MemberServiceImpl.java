///*
// * Copyright (c) 2013 SK planet.
// * All right reserved.
// *
// * This software is the confidential and proprietary information of SK planet.
// * You shall not disclose such Confidential Information and
// * shall use it only in accordance with the terms of the license agreement
// * you entered into with SK planet.
// */
//package com.skplanet.storeplatform.sac.member.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.skplanet.storeplatform.sac.client.user.vo.MyPagePurchaseVO;
//import com.skplanet.storeplatform.sc.client.sci.PaymentSCI;
//import com.skplanet.storeplatform.sc.client.sci.PurchaseSCI;
//import com.skplanet.storeplatform.sc.client.sci.UserSCI;
//import com.skplanet.storeplatform.sc.client.vo.PaymentVO;
//import com.skplanet.storeplatform.sc.client.vo.PurchaseVO;
//import com.skplanet.storeplatform.sc.client.vo.UserSearchVO;
//
///**
// * Member Service 인터페이스(CoreStoreBusiness) 구현체
// * 
// * Updated on : 2013-09-01 Updated by : $$Id$$.
// */
//@Component
//public class MemberServiceImpl implements MemberService {
//	private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private UserSCI userSCI;
//
//	@Autowired
//	private PurchaseSCI purchaseSCI;
//
//	@Autowired
//	private PaymentSCI paymentSCI;
//
//	/**
//	 * MyPage 구매/결제 이력 상세 조회.
//	 * 
//	 * @param myPagePurchaseVO
//	 *            MyPage 구매/결제 이력
//	 * @return MyPage 구매/결제 이력 상세
//	 */
//	@Override
//	public MyPagePurchaseVO searchMypagePurchase(MyPagePurchaseVO myPagePurchaseVO) {
//		MyPagePurchaseVO resultVO = new MyPagePurchaseVO();
//
//		this.log.debug("myPagePurchaseVO.getId()  : {}", myPagePurchaseVO.getId());
//		this.log.debug("myPagePurchaseVO.getNum() : {}", myPagePurchaseVO.getNum());
//
//		UserSearchVO paramUserVO = new UserSearchVO();
//		paramUserVO.setId(myPagePurchaseVO.getId());
//
//		UserSearchVO userVO = this.userSCI.detail(paramUserVO);
//		if (userVO != null) {
//			resultVO.setUserId(Integer.toString(myPagePurchaseVO.getId()));
//		}
//
//		PurchaseVO paramPurchaseVO = new PurchaseVO();
//		paramPurchaseVO.setPid(myPagePurchaseVO.getPid());
//
//		PurchaseVO purchaseVO = this.purchaseSCI.searchDetail(paramPurchaseVO);
//		if (purchaseVO != null) {
//			resultVO.setPurchaseId(myPagePurchaseVO.getPid());
//		}
//
//		PaymentVO paramPaymentVO = new PaymentVO();
//		paramPaymentVO.setIdentifier(myPagePurchaseVO.getPid());
//
//		PaymentVO paymentVO = this.paymentSCI.searchDetail(paramPaymentVO);
//		if (paymentVO != null) {
//			resultVO.setPayId(myPagePurchaseVO.getPid());
//		}
//
//		resultVO.setId(myPagePurchaseVO.getId());
//		resultVO.setNum(myPagePurchaseVO.getNum());
//
//		resultVO.setPurchaseId(myPagePurchaseVO.getPurchaseId());
//
//		return resultVO;
//	}
// }
