package com.skplanet.storeplatform.sac.client.display.vo.feature.list;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.DisplayList;


public class DisplayListSacRes {
	private String startKey;
	private String hasNext; // Y/N
	private Integer count;
	private List<DisplayList> lists;
	public String getStartKey() {
		return startKey;
	}
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}
	public String getHasNext() {
		return hasNext;
	}
	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<DisplayList> getLists() {
		return lists;
	}
	public void setLists(List<DisplayList> lists) {
		this.lists = lists;
	}
}
