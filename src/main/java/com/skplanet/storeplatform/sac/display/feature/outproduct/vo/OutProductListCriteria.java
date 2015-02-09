package com.skplanet.storeplatform.sac.display.feature.outproduct.vo;


public class OutProductListCriteria {
	private static final int DEFAULT_LIST_COUNT = 20;
	private static final int MIN_RANK = -99999999;
	private String tenantId;
	private String listId;
	private String stdDt; // YYYYMM DDHH24MISS
	private Integer lastRank;
	private Integer count;

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getStdDt() {
		return stdDt;
	}
	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}
	public Integer getLastRank() {
		if(lastRank==null)
			return MIN_RANK;
		return lastRank;
	}
	public void setLastRank(Integer lastRank) {
		this.lastRank = lastRank;
	}
	public Integer getCount() {
		if(count==null)
			return DEFAULT_LIST_COUNT;
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}


}
