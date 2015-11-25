package com.skplanet.storeplatform.sac.client.other.vo.sacservice;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GetActiveResList extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private List<GetActiveRes> serviceList;

	public GetActiveResList() {
	}
	
	public GetActiveResList(List<GetActiveRes> serviceList) {
		this.serviceList = serviceList;
	}

	public List<GetActiveRes> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<GetActiveRes> serviceList) {
		this.serviceList = serviceList;
	}

}
