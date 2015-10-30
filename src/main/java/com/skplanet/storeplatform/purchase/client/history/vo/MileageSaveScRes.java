package com.skplanet.storeplatform.purchase.client.history.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * T마일리지 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class MileageSaveScRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tMileageAvailMtd;
	private String tMileageLimitAmt;
	private List<MileageSaveSc> tMileageReseveList;

	/**
	 * @return the tMileageAvailMtd
	 */
	public String gettMileageAvailMtd() {
		return this.tMileageAvailMtd;
	}

	/**
	 * @param tMileageAvailMtd
	 *            the tMileageAvailMtd to set
	 */
	public void settMileageAvailMtd(String tMileageAvailMtd) {
		this.tMileageAvailMtd = tMileageAvailMtd;
	}

	/**
	 * @return the tMileageLimitAmt
	 */
	public String gettMileageLimitAmt() {
		return this.tMileageLimitAmt;
	}

	/**
	 * @param tMileageLimitAmt
	 *            the tMileageLimitAmt to set
	 */
	public void settMileageLimitAmt(String tMileageLimitAmt) {
		this.tMileageLimitAmt = tMileageLimitAmt;
	}

	/**
	 * @return the tMileageReseveList
	 */
	public List<MileageSaveSc> gettMileageReseveList() {
		return this.tMileageReseveList;
	}

	/**
	 * @param tMileageReseveList
	 *            the tMileageReseveList to set
	 */
	public void settMileageReseveList(List<MileageSaveSc> tMileageReseveList) {
		this.tMileageReseveList = tMileageReseveList;
	}

}
