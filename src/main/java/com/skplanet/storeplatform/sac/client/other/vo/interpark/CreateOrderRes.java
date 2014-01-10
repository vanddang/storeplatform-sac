package com.skplanet.storeplatform.sac.client.other.vo.interpark;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CreateOrderRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	public CreateOrderRes() {
	}

	public CreateOrderRes(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	private String resultStatus;

	public String getResultStatus() {
		return this.resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

}
