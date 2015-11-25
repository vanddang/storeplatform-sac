package com.skplanet.storeplatform.sac.client.example.header;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class NetworkRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String type;
	private String operator;
	private String simOperator;

	public NetworkRes() {
		super();
	}

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOperator() {
		return this.operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getSimOperator() {
		return this.simOperator;
	}
	public void setSimOperator(String simOperator) {
		this.simOperator = simOperator;
	}

}