package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class NotifyPaymentRes extends CommonInfo {
	private static final long serialVersionUID = 201401231L;

	private String code; // 결제알림 처리 결과
	private String msg; // 결제알림 처리 메세지

	/**
	 */
	public NotifyPaymentRes() {
	}

	/**
	 * 
	 * @param code
	 *            code
	 * @param msg
	 *            msg
	 */
	public NotifyPaymentRes(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

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

}
