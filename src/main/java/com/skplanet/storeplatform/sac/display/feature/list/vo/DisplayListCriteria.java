package com.skplanet.storeplatform.sac.display.feature.list.vo;


import com.skplanet.storeplatform.sac.client.display.vo.feature.list.DisplayListSacReq;

public class DisplayListCriteria {

	private static final int DEFAULT_LIST_COUNT = 20;
	private static final int MIN_EXPO_ORD = -99999999;
	
	private String tenantId;
	private String listGrpCd;
	private String listId;
	private Integer lastExpoOrd;
	private Integer lastExpoOrdSub;
	private Integer count;
	
	public DisplayListCriteria(DisplayListSacReq requestVO, String tenantId) {
		this.tenantId = tenantId;
		listGrpCd = requestVO.getListGrpCd();
		listId = requestVO.getListId();
		
		setupOrders(requestVO);
		setCount(requestVO);
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

	public String getListGrpCd() {
		return listGrpCd;
	}

	public String getListId() {
		return listId;
	}

	public Integer getLastExpoOrd() {
		return lastExpoOrd;
	}

	public Integer getLastExpoOrdSub() {
		return lastExpoOrdSub;
	}

	public Integer getCount() {
		return count;
	}
}
