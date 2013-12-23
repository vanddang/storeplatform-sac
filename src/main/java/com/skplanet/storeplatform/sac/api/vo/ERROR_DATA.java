/*****************************************************************************
 * SKT TStore Project ::: PARTNER Site :::: Shopping Coupon interface
 *****************************************************************************
 * 1.클래스 개요 :
 * 2.작   성  자 : Sun-taek Lim
 * 3.작 성 일 자 : 2013. 3. 22.
 * <pre>
 * 4.수 정 일 자 :
 *      . <날짜> : <수정 내용> (성명)
 *      . 2013. 3. 22.  : 최초 생성 (Sun-taek Lim)
 * @author Sun-taek Lim
 * </pre>
 * @version 1.0
 *****************************************************************************/

package com.skplanet.storeplatform.sac.api.vo;

import java.io.Serializable;

public class ERROR_DATA implements Serializable {

	/**
	 *
	 */
	// private static final long serialVersionUID = 2864668372166277348L;

	// VARCHAR(4) 에러 코드
	private String ERROR_CODE;
	// VARCHAR(300)　 에러 메시지

	private String ERROR_MSG;

	public String getERROR_CODE() {
		return this.ERROR_CODE;
	}

	public void setERROR_CODE(String error_code) {
		this.ERROR_CODE = error_code;
	}

	public String getERROR_MSG() {
		return this.ERROR_MSG;
	}

	public void setERROR_MSG(String error_msg) {
		this.ERROR_MSG = error_msg;
	}

	@Override
	public String toString() {
		return "ERROR_DATA [ERROR_CODE=" + this.ERROR_CODE + ", ERROR_MSG=" + this.ERROR_MSG + "]";
	}
}
