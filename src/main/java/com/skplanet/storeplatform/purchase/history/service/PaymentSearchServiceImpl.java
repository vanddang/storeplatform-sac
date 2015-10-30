/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentListScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScRes;

/**
 * 구매내역 Implements
 * 
 * Updated on : 2014-01-21 Updated by : 조용진, 엔텔스.
 */
@Service
public class PaymentSearchServiceImpl implements PaymentSearchService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 
	 * <pre>
	 * 결제내역조회.
	 * </pre>
	 * 
	 * @param paymentScReq
	 *            결제내역 조회조건
	 * @return List<PaymentListScRes> 결제내역 조회결과
	 */
	@Override
	public List<PaymentListScRes> searchPaymentList(PaymentScReq paymentScReq) {

		List<PaymentScRes> list = (List<PaymentScRes>) this.commonDAO.queryForList("Payment.searchPaymentList",
				paymentScReq);

		PaymentListScRes paymentListScRes = new PaymentListScRes();
		List<PaymentListScRes> resList = new ArrayList<PaymentListScRes>();
		List<PaymentScRes> outList = new ArrayList<PaymentScRes>();
		PaymentScRes paymentScRes = new PaymentScRes();
		String tempId = "";
		for (int i = 0; i < list.size(); i++) {

			if (i == 0) {
				// 최초row 값 셋팅
				tempId = list.get(i).getPrchsId();
				paymentListScRes.setPrchsId(list.get(i).getPrchsId());

				paymentScRes.setPaymentAmt(list.get(i).getPaymentAmt());
				paymentScRes.setPaymentMtdCd(list.get(i).getPaymentMtdCd());
				outList.add(paymentScRes);
			} else {
				paymentScRes = new PaymentScRes();
				// tempId와 list 구매아이디가 같다면 결재내역에 결재수단 추가
				if (tempId.equals(list.get(i).getPrchsId())) {

					paymentScRes.setPaymentAmt(list.get(i).getPaymentAmt());
					paymentScRes.setPaymentMtdCd(list.get(i).getPaymentMtdCd());
					outList.add(paymentScRes);
				} else {
					// tempId와 list 구매아이디가 틀리면 tempId 셋팅
					tempId = list.get(i).getPrchsId();

					// 구매아이디가 틀릴경우 결재내역을 셋팅한다.
					paymentListScRes.setPaymentList(outList);
					resList.add(paymentListScRes);
					// outList 초기화
					outList = new ArrayList<PaymentScRes>();
					// paymentListScRes 초기화
					paymentListScRes = new PaymentListScRes();

					// 결재내역에 결재수단 추가
					paymentListScRes.setPrchsId(tempId);
					paymentScRes.setPaymentAmt(list.get(i).getPaymentAmt());
					paymentScRes.setPaymentMtdCd(list.get(i).getPaymentMtdCd());

					outList.add(paymentScRes);
				}
			}
		}
		// 마지막 row 셋팅
		paymentListScRes.setPaymentList(outList);
		resList.add(paymentListScRes);
		return resList;
	}

}
