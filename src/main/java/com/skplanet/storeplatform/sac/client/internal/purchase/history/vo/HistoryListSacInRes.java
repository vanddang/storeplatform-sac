package com.skplanet.storeplatform.sac.client.internal.purchase.history.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매내역 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class HistoryListSacInRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int totalCnt;
	private List<HistorySacIn> historyList;

	/**
	 * @return the totalCnt
	 */
	public int getTotalCnt() {
		return this.totalCnt;
	}

	/**
	 * @param totalCnt
	 *            the totalCnt to set
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	/**
	 * @return the historyList
	 */
	public List<HistorySacIn> getHistoryList() {
		return this.historyList;
	}

	/**
	 * @param historyList
	 *            the historyList to set
	 */
	public void setHistoryList(List<HistorySacIn> historyList) {
		this.historyList = historyList;
	}

}
