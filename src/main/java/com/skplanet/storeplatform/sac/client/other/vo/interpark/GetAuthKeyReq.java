package com.skplanet.storeplatform.sac.client.other.vo.interpark;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class GetAuthKeyReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String type;
	private String revOrdNo;
	private String ebFileNo;

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRevOrdNo() {
		return this.revOrdNo;
	}
	public void setRevOrdNo(String revOrdNo) {
		this.revOrdNo = revOrdNo;
	}
	public String getEbFileNo() {
		return this.ebFileNo;
	}
	public void setEbFileNo(String ebFileNo) {
		this.ebFileNo = ebFileNo;
	}

}
