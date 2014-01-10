package com.skplanet.storeplatform.sac.display.search.vo;

import java.util.List;

public class SearchProductDTO {
	private String tenantId;
	private String menuId;
	private List<SearchProductDetailDTO> searchProdinfoList;;
	private List<String> prodList;

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public List<SearchProductDetailDTO> getSearchProdinfoList() {
		return this.searchProdinfoList;
	}

	public void setSearchProdinfoList(List<SearchProductDetailDTO> searchProdinfoList) {
		this.searchProdinfoList = searchProdinfoList;
	}

	public List<String> getProdList() {
		for (SearchProductDetailDTO dto : this.searchProdinfoList) {
			this.prodList.add(dto.getProdId());
		}
		return this.prodList;
	}

	public void setProdList(List<String> prodList) {
		this.prodList = prodList;
	}

}
