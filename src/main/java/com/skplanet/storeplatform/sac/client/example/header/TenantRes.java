package com.skplanet.storeplatform.sac.client.example.header;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class TenantRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;

	private String systemId;

	public TenantRes() {
		super();
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}