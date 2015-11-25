package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

/**
 * PaymentInfoSacRes Value Object
 * 
 * 결제 시 필요한 상품 메타 정보 조회 VO
 * 
 * Updated on : 2014. 2. 27. Updated by : 홍지호, 엔텔스
 */
public class PaymentInfoSacRes {

    private List<PaymentInfo> paymentInfoList;

    public PaymentInfoSacRes(List<PaymentInfo> paymentInfoList) {
        this.paymentInfoList = paymentInfoList;
    }

    /**
	 * @return the paymentInfoList
	 */
	public List<PaymentInfo> getPaymentInfoList() {
		return this.paymentInfoList;
	}

}
