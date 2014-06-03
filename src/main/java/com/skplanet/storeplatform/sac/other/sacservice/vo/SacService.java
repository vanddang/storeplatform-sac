package com.skplanet.storeplatform.sac.other.sacservice.vo;

public class SacService {

	private String serviceCd;

	private String operator;

	private boolean active;

	private boolean applied;

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
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isApplied() {
		return this.applied;
	}

	public void setApplied(boolean applied) {
		this.applied = applied;
	}

}
