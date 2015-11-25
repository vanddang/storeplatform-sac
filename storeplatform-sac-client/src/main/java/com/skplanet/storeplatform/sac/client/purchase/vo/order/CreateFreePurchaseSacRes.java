package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CreateFreePurchaseSacRes extends CommonInfo {
	private static final long serialVersionUID = 201401031L;

	private String resultType; // 결과 타입: payment-결제Page 요청 진행, free-무료구매 완료
	private String prchsId; // 구매ID

	/**
	 * @return the resultType
	 */
	public String getResultType() {
		return this.resultType;
	}

	/**
	 * @param resultType
	 *            the resultType to set
	 */
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

}
