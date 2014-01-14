package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class HistoryCountRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int totalCnt;

	public int getTotalCnt() {
		return this.totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

}
