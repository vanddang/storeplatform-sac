package com.skplanet.storeplatform.sac.client.other.vo.sacservice;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetActiveReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String serviceCd;

	private String simOperator;

	private String model;

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

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
