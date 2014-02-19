package com.skplanet.storeplatform.sac.client.purchase.admin.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 상품별 구매내역 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class AdminHistorySacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int totalCnt;
	private List<AdminHistorySac> historyList;

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
	public List<AdminHistorySac> getHistoryList() {
		return this.historyList;
	}

	/**
	 * @param historyList
	 *            the historyList to set
	 */
	public void setHistoryList(List<AdminHistorySac> historyList) {
		this.historyList = historyList;
	}

}
