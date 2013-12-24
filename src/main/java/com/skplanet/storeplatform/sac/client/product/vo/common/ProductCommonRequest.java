package com.skplanet.storeplatform.sac.client.product.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;

public class ProductCommonRequest extends CommonVO {
	private static final long serialVersionUID = 1L;
	private String menuId; // 메뉴ID

	private String tenantId; // 테넌트ID

	private String systemId; // 시스템ID

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
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
