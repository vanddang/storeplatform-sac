package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매내역 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class HistoryListSacV2Res extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String startKey;
	private List<HistorySacV2> historyList;

	/**
	 * @return the startKey
	 */
	public String getStartKey() {
		return this.startKey;
	}

	/**
	 * @param startKey
	 *            the startKey to set
	 */
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}

	/**
	 * @return the historyList
	 */
	public List<HistorySacV2> getHistoryList() {
		return this.historyList;
	}

	/**
	 * @param historyList
	 *            the historyList to set
	 */
	public void setHistoryList(List<HistorySacV2> historyList) {
		this.historyList = historyList;
	}

}
