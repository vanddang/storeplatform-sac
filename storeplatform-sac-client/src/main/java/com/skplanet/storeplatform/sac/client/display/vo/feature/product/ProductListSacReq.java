package com.skplanet.storeplatform.sac.client.display.vo.feature.product;

import org.hibernate.validator.constraints.NotBlank;

public class ProductListSacReq {
	@NotBlank
	private String listId;
	private String menuId;
	private String prodGradeCd;
	private String startKey;
	private String prodCharge;
	private Integer count;
	
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getProdGradeCd() {
		return prodGradeCd;
	}
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}
	public String getStartKey() {
		return startKey;
	}
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}
	public String getProdCharge() {
		return prodCharge;
	}
	public void setProdCharge(String prodCharge) {
		this.prodCharge = prodCharge;
	}
	public Integer getCount() {
		return count!=null?count:new Integer(20);
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
