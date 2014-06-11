package com.skplanet.storeplatform.sac.other.sacservice.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SacService extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String serviceCd;

	private String simOperator;

	private String model;

	private boolean active;

	private boolean applied;

	public String getServiceCd() {
		return this.serviceCd;
	}

	public void setServiceCd(String serviceCd) {
		this.serviceCd = serviceCd;
	}

	public String getSimOperator() {
		return this.simOperator;
	}

	public void setSimOperator(String simOperator) {
		this.simOperator = simOperator;
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

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
