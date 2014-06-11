package com.skplanet.storeplatform.sac.client.other.vo.sacservice;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SetActiveReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String serviceCd;

	private String simOperator;

	private String model;

	private boolean isActive;

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
		return this.isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
