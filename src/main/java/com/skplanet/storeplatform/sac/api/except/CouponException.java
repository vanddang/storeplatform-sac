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
 * 전처리 쿠폰 Exception Value Object.
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
	 * @param errorCode
	 *            errorCode
	 * @param errorMessage
	 *            errorMessage
	 * @param errValue
	 *            errValue
	 */
	public CouponException(String errorCode, String errorMessage, String errValue) {
		this.errorData = new ErrorData();
		this.errorData.setErrorCode(errorCode);
		this.errorData.setErrorMsg(errorMessage);
		this.errValue = errValue;
	}

	/**
	 * @return String
	 */
	@Override
	public String getMessage() {
		return this.errorData.getErrorMsg();
	}

	/**
	 * @return String
	 */
	public String getErrCode() {
		return this.errorData.getErrorCode();
	}

	/**
	 * @return ErrorData
	 */
	public ErrorData getError_data() {
		return this.errorData;
	}

	/**
	 * @param errorData
	 *            errorData
	 */
	public void setError_data(ErrorData errorData) {
		this.errorData = errorData;
	}

	/**
	 * @return String
	 */
	public String getRequest_time() {
		return this.requestTime;
	}

	/**
	 * @param requestTime
	 *            requestTime
	 */
	public void setRequest_time(String requestTime) {
		this.requestTime = requestTime;
	}

	/**
	 * @return String
	 */
	public String getErr_value() {
		return this.errValue;
	}

	/**
	 * @param errValue
	 *            errValue
	 */
	public void setErr_valu(String errValue) {
		this.errValue = errValue;
	}
}
