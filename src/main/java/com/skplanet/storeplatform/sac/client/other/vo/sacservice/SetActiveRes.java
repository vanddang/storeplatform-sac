package com.skplanet.storeplatform.sac.client.other.vo.sacservice;

public class SetActiveRes {

	private String serviceCd;

	private String operator;

	private boolean isApplied;

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

	public boolean isApplied() {
		return isApplied;
	}

	public void setApplied(boolean isApplied) {
		this.isApplied = isApplied;
	}

}
