package com.skplanet.storeplatform.sac.client.display.vo.feature.list;

import org.hibernate.validator.constraints.NotBlank;

public class DisplayListSacReq {
	@NotBlank
	private String listGrpCd;
	private String listId;
	private String menuId;
	private String startKey;
	private Integer count;
	public String getListGrpCd() {
		return listGrpCd;
	}
	public void setListGrpCd(String listGrpCd) {
		this.listGrpCd = listGrpCd;
	}
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
	public String getStartKey() {
		return startKey;
	}
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}
	public Integer getCount() {
		return count!=null?count:new Integer(20);
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
