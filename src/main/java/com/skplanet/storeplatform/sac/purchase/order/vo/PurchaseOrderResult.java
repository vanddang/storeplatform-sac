package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class PurchaseOrderResult extends CommonInfo {
	private static final long serialVersionUID = 201401221L;

	private String resultCd;
	private String resultMsg;

	/**
	 */
	public PurchaseOrderResult() {
	}

	/**
	 */
	public PurchaseOrderResult(String resultCd, String resultMsg) {
		this.resultCd = resultCd;
		this.resultMsg = resultMsg;
	}

	/**
	 * @return the resultCd
	 */
	public String getResultCd() {
		return this.resultCd;
	}

	/**
	 * @param resultCd
	 *            the resultCd to set
	 */
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return this.resultMsg;
	}

	/**
	 * @param resultMsg
	 *            the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}
