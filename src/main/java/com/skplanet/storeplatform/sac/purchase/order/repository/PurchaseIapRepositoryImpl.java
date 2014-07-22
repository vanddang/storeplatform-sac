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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.iap.sci.IapPurchaseSCI;
import com.skplanet.storeplatform.external.client.iap.vo.InquiryBillingAmtEcReq;
import com.skplanet.storeplatform.external.client.iap.vo.InquiryBillingAmtEcRes;

/**
 * 
 * IAP Repository
 * 
 * Updated on : 2014. 7. 21. Updated by : 이승택, nTels.
 */
@Component
public class PurchaseIapRepositoryImpl implements PurchaseIapRepository {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IapPurchaseSCI iapPurchaseSCI;

	/**
	 * 
	 * <pre>
	 * IAP SKT후불 결제 금액 조회.
	 * </pre>
	 * 
	 * @param mdn
	 *            조회할 MDN
	 * @param svcMangNo
	 *            조회할 SKT서비스관리번호
	 * @param queryMonth
	 *            조회할 월 (YYYYMM)
	 * @return IAP SKT후불 결제 금액
	 */
	@Override
	public int inquiryBillingAmt(String mdn, String svcMangNo, String queryMonth) {
		InquiryBillingAmtEcReq inquiryBillingAmtEcReq = new InquiryBillingAmtEcReq();
		inquiryBillingAmtEcReq.setMdn(mdn);
		inquiryBillingAmtEcReq.setSvcMngNum(svcMangNo);
		inquiryBillingAmtEcReq.setQueryMonths(queryMonth);

		this.logger.info("PRCHS,ORDER,SAC,POLICY,IAP,INQUIRY,REQ,{}",
				ReflectionToStringBuilder.toString(inquiryBillingAmtEcReq, ToStringStyle.SHORT_PREFIX_STYLE));

		InquiryBillingAmtEcRes inquiryBillingAmtEcRes = this.iapPurchaseSCI.inquiryBillingAmt(inquiryBillingAmtEcReq);

		this.logger.info("PRCHS,ORDER,SAC,POLICY,IAP,INQUIRY,RES,{}",
				ReflectionToStringBuilder.toString(inquiryBillingAmtEcRes, ToStringStyle.SHORT_PREFIX_STYLE));

		return inquiryBillingAmtEcRes.getBillingAmt();
	}
}
