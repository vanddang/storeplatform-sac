package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CheckPurchaseReq extends CommonInfo {
	private static final long serialVersionUID = 201312021L;

	private String tenantId;
	private String mbrNo;
	private String deviceNo;
	private String prchsId;
	private List<String> prodIdList;

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getMbrNo() {
		return this.mbrNo;
	}

	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	public String getDeviceNo() {
		return this.deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getPrchsId() {
		return this.prchsId;
	}

	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	public List<String> getProdIdList() {
		return this.prodIdList;
	}

	public void setProdIdList(List<String> prodIdList) {
		this.prodIdList = prodIdList;
	}

}
