package com.skplanet.storeplatform.sac.client.other.vo.sacservice;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetActiveReqList extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private List<GetActiveReq> serviceList;

	public List<GetActiveReq> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<GetActiveReq> serviceList) {
		this.serviceList = serviceList;
	}

}
