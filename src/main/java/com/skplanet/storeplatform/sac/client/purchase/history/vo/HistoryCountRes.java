package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class HistoryCountRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int totalCnt;

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

}
