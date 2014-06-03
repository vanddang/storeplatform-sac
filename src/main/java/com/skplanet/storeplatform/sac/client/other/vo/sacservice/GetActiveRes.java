package com.skplanet.storeplatform.sac.client.other.vo.sacservice;

public class GetActiveRes {

	private String serviceCd;

	private String operator;

	private boolean isActive;

	public String getServiceCd() {
		return this.serviceCd;
	}

	public void setServiceCd(String serviceCd) {
		this.serviceCd = serviceCd;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
