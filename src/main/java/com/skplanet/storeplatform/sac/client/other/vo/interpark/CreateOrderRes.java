package com.skplanet.storeplatform.sac.client.other.vo.interpark;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * CreateOrderRes Value Object
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
public class CreateOrderRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 생성자.
	 */
	public CreateOrderRes() {
	}

	/**
	 * 생성자.
	 * 
	 * @param resultStatus
	 *            resultStatus
	 */
	public CreateOrderRes(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	private String resultStatus;

	/**
	 * @return String
	 */
	public String getResultStatus() {
		return this.resultStatus;
	}

	/**
	 * @param resultStatus
	 *            resultStatus
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

}
