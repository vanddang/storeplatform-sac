package com.skplanet.storeplatform.sac.display.feature.list.vo;


import com.skplanet.storeplatform.sac.client.display.vo.feature.list.DisplayListSacReq;

public class DisplayListCriteria {

	private static final int DEFAULT_LIST_COUNT = 20;
	private static final int MIN_EXPO_ORD = -99999999;

	private String tenantId;
	private String listGrpCd;
	private String listId;
	private String filterYn;
	private Integer lastExpoOrd;
	private Integer lastExpoOrdSub;
	private Integer count;

	public DisplayListCriteria() {}

	public DisplayListCriteria(DisplayListSacReq requestVO, String tenantId) {
		this.tenantId = tenantId;
		listGrpCd = requestVO.getListGrpCd();
		listId = requestVO.getListId();
		filterYn = "Y";

		setupOrders(requestVO);
		setCount(requestVO);
	}

	public DisplayListCriteria(String tenantId, String listId, String filterYn, Integer count) {
		this.tenantId = tenantId;
		this.listId = listId;
		this.filterYn = filterYn;
		this.count = count;
		lastExpoOrd    = MIN_EXPO_ORD;
		lastExpoOrdSub = MIN_EXPO_ORD;
	}

	private void setupOrders(DisplayListSacReq req) {
		String startKey = req.getStartKey();

		if(startKey==null || startKey.isEmpty()){
			lastExpoOrd    = MIN_EXPO_ORD;
			lastExpoOrdSub = MIN_EXPO_ORD;
		}else{
			String[] keys = startKey.split("/");
			lastExpoOrd    = Integer.valueOf(keys[0]);
			lastExpoOrdSub = Integer.valueOf(keys[1]);
		}
	}

	private void setCount(DisplayListSacReq req) {
		count = req.getCount()==null?DEFAULT_LIST_COUNT:req.getCount();
		count++; //To set "hasNext"
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

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

	public String getFilterYn() {
		return filterYn;
	}

	public void setFilterYn(String filterYn) {
		this.filterYn = filterYn;
	}

	public Integer getLastExpoOrd() {
		return lastExpoOrd;
	}

	public void setLastExpoOrd(Integer lastExpoOrd) {
		this.lastExpoOrd = lastExpoOrd;
	}

	public Integer getLastExpoOrdSub() {
		return lastExpoOrdSub;
	}

	public void setLastExpoOrdSub(Integer lastExpoOrdSub) {
		this.lastExpoOrdSub = lastExpoOrdSub;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
