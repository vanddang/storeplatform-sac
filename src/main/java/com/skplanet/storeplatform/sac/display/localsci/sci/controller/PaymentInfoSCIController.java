package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PaymentInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.PaymentInfoService;

/**
 * 
 * PaymentInfoSCIController Controller
 * 
 * 결제 시 필요한 상품 메타 정보 조회
 * 
 * Updated on : 2014. 2. 27. Updated by : 홍지호, 엔텔스
 */
@LocalSCI
public class PaymentInfoSCIController implements PaymentInfoSCI {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentInfoService paymentInfoService;

	/**
	 * <pre>
	 * 결제 시 필요한 상품 메타 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PaymentInfoSacRes 상품 메타 정보 리스트
	 */
	@Override
	public PaymentInfoSacRes searchPaymentInfo(PaymentInfoSacReq req) {
		return this.paymentInfoService.searchPaymentInfo(req);
	}

}
