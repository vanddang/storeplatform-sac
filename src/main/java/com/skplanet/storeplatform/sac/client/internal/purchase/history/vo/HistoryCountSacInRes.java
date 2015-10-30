package com.skplanet.storeplatform.sac.client.internal.purchase.history.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매내역건수 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
public class HistoryCountSacInRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int totalCnt;

	private List<ProductCountSacIn> cntList;

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
	public List<ProductCountSacIn> getCntList() {
		return this.cntList;
	}

	/**
	 * @param cntList
	 *            the cntList to set
	 */
	public void setCntList(List<ProductCountSacIn> cntList) {
		this.cntList = cntList;
	}

}
