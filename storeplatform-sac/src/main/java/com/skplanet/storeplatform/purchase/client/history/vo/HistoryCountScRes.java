package com.skplanet.storeplatform.purchase.client.history.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매내역 조회 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class HistoryCountScRes extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int totalCnt;

	private List<ProductCountSc> cntList;

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
	 * @return the cntList
	 */
	public List<ProductCountSc> getCntList() {
		return this.cntList;
	}

	/**
	 * @param cntList
	 *            the cntList to set
	 */
	public void setCntList(List<ProductCountSc> cntList) {
		this.cntList = cntList;
	}

}
