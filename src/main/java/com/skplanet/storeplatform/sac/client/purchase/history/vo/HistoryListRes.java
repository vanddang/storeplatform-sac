package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class HistoryListRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int totalCnt;
	private List<History> historyList;

	public int getTotalCnt() {
		return this.totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public List<History> getHistoryList() {
		return this.historyList;
	}

	public void setHistoryList(List<History> historyList) {
		this.historyList = historyList;
	}

}
