package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;

/**
 * 
 * PaymentInfoService Service
 * 
 * 결제 시 필요한 상품 메타 정보 조회 서비스.
 * 
 * Updated on : 2014. 2. 27. Updated by : 홍지호, 엔텔스
 */
public interface PaymentInfoService {

	/**
	 * <pre>
	 * 결제 시 필요한 상품 메타 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PaymentInfoSacRes 상품 메타 정보 리스트
	 */
	PaymentInfoSacRes searchPaymentInfo(PaymentInfoSacReq req);
}
