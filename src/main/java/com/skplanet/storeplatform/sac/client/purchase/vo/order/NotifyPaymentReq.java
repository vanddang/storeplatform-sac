package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class NotifyPaymentReq extends CommonInfo {
	private static final long serialVersionUID = 201401231L;

	private String code; // 결제요청 결과코드
	private String msg; // 결제요청 결과메세지
	private String tid; // Pay Planet 거래번호
	private String orderId; // 가맹점 주문번호: 구매ID
	private String amtPurchase; // 최종결제금액 (실패시 0)
	private String mctSpareParam; // 가맹점 임의 파라미터
	private String gwBillkey; // GW사에서 발급한 월자동결제 처리용 빌키
	private String noCoupon; // 결제에 사용된 쿠폰번호
	private String paymethodInfo; // 결제수단 별 결제금액 수단코드:결제금액;수단코드:결제금액;...

	/**
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the tid
	 */
	public String getTid() {
		return this.tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return this.orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the amtPurchase
	 */
	public String getAmtPurchase() {
		return this.amtPurchase;
	}

	/**
	 * @param amtPurchase
	 *            the amtPurchase to set
	 */
	public void setAmtPurchase(String amtPurchase) {
		this.amtPurchase = amtPurchase;
	}

	/**
	 * @return the mctSpareParam
	 */
	public String getMctSpareParam() {
		return this.mctSpareParam;
	}

	/**
	 * @param mctSpareParam
	 *            the mctSpareParam to set
	 */
	public void setMctSpareParam(String mctSpareParam) {
		this.mctSpareParam = mctSpareParam;
	}

	/**
	 * @return the gwBillkey
	 */
	public String getGwBillkey() {
		return this.gwBillkey;
	}

	/**
	 * @param gwBillkey
	 *            the gwBillkey to set
	 */
	public void setGwBillkey(String gwBillkey) {
		this.gwBillkey = gwBillkey;
	}

	/**
	 * @return the noCoupon
	 */
	public String getNoCoupon() {
		return this.noCoupon;
	}

	/**
	 * @param noCoupon
	 *            the noCoupon to set
	 */
	public void setNoCoupon(String noCoupon) {
		this.noCoupon = noCoupon;
	}

	/**
	 * @return the paymethodInfo
	 */
	public String getPaymethodInfo() {
		return this.paymethodInfo;
	}

	/**
	 * @param paymethodInfo
	 *            the paymethodInfo to set
	 */
	public void setPaymethodInfo(String paymethodInfo) {
		this.paymethodInfo = paymethodInfo;
	}

}
