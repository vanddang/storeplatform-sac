/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.api.except;

import com.skplanet.storeplatform.sac.api.vo.ErrorData;

/**
 * <pre>
 * 전처리 쿠폰 Exception Value Object
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class CouponException extends RuntimeException {
	private ErrorData errorData;
	private String requestTime;
	private String errValue;

	/**
	 * Exception 시에는 tx_id = null 로와도 무방함.
	 * 
	 * @param error_code
	 * @param error_message
	 * @param err_value
	 * @param request_time
	 */
	public CouponException(String error_code, String error_message, String err_value) {
		this.errorData = new ErrorData();
		this.errorData.setErrorCode(error_code);
		this.errorData.setErrorMsg(error_message);
		this.errValue = err_value;
	}

	@Override
	public String getMessage() {
		return this.errorData.getErrorMsg();
	}

	public String getErrCode() {
		return this.errorData.getErrorCode();
	}

	public ErrorData getError_data() {
		return this.errorData;
	}

	public void setError_data(ErrorData error_data) {
		this.errorData = error_data;
	}

	public String getRequest_time() {
		return this.requestTime;
	}

	public void setRequest_time(String request_time) {
		this.requestTime = request_time;
	}

	public String getErr_value() {
		return this.errValue;
	}

	public void setErr_valu(String err_value) {
		this.errValue = err_value;
	}
}
