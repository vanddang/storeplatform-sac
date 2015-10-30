package com.skplanet.storeplatform.purchase.history.sci;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.PaymentListSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentListScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScReq;
import com.skplanet.storeplatform.purchase.history.service.PaymentSearchService;

/**
 * 결제내역 Controller
 * 
 * Updated on : 2014. 01. 21. Updated by : 조용진, 엔텔스.
 */
@LocalSCI
public class PaymentListSCIController implements PaymentListSCI {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentSearchService paymentSearchService;

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
		this.logger.debug("PRCHS,PaymentListSCIController,SC,REQ,{}", paymentScReq);
		return this.paymentSearchService.searchPaymentList(paymentScReq);
	}

}
