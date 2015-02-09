package com.skplanet.storeplatform.sac.client.display.vo.feature.outproduct.list;

public class OutProductListSacReq {
	private String listId;
	private Integer startKey;
	private Integer count;

	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public Integer getStartKey() {
		return startKey;
	}
	public void setStartKey(Integer startKey) {
		this.startKey = startKey;
	}
	public Integer getCount() {
		return count!=null?count:new Integer(20);
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
