/*****************************************************************************
 * SKT TStore Project ::: PARTNER Site :::: Shopping Coupon interface
 *****************************************************************************
 * 1.클래스 개요 :
 * 2.작   성  자 : Kim Hyung Sik
 * 3.작 성 일 자 : 2013. 12. 29.
 * <pre>
 * 4.수 정 일 자 :
 *      . <날짜> : <수정 내용> (성명)
 *      . 2013. 12. 29.  : 최초 생성 (jade)
 * @author Kim Hyung Sik
 * </pre>
 * @version 1.0
 *****************************************************************************/

package com.skplanet.storeplatform.sac.api.except;

import com.skplanet.storeplatform.sac.api.vo.ERROR_DATA;

public class CouponException extends Exception {
	private ERROR_DATA error_data;
	private String request_time;
	private String err_value;

	/**
	 * Exception 시에는 tx_id = null 로와도 무방함.
	 * 
	 * @param error_code
	 * @param error_message
	 * @param err_value
	 * @param request_time
	 */
	public CouponException(String error_code, String error_message, String err_value) {
		this.error_data = new ERROR_DATA();
		this.error_data.setERROR_CODE(error_code);
		this.error_data.setERROR_MSG(error_message);
		this.err_value = err_value;
	}

	@Override
	public String getMessage() {
		return this.error_data.getERROR_MSG();
	}

	public String getErrCode() {
		return this.error_data.getERROR_CODE();
	}

	public ERROR_DATA getError_data() {
		return this.error_data;
	}

	public void setError_data(ERROR_DATA error_data) {
		this.error_data = error_data;
	}

	public String getRequest_time() {
		return this.request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getErr_value() {
		return this.err_value;
	}

	public void setErr_valu(String err_value) {
		this.err_value = err_value;
	}
}
