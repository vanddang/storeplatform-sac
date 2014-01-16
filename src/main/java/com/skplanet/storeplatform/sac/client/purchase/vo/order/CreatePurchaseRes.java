package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CreatePurchaseRes extends CommonInfo {
	private static final long serialVersionUID = 201401031L;

	private String resultType; // 결과 타입: payment-결제Page 요청 진행, free-무료구매 완료
	private String paymentPageUrl; // 결제Page URL
	private String paymentPageParam; // 결제Page 요청 파라미터

	public String getResultType() {
		return this.resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getPaymentPageUrl() {
		return this.paymentPageUrl;
	}

	public void setPaymentPageUrl(String paymentPageUrl) {
		this.paymentPageUrl = paymentPageUrl;
	}

	public String getPaymentPageParam() {
		return this.paymentPageParam;
	}

	public void setPaymentPageParam(String paymentPageParam) {
		this.paymentPageParam = paymentPageParam;
	}
}
