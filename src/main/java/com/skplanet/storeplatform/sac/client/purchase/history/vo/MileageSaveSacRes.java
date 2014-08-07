package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * T마일리지 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class MileageSaveSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tMileageAvailMtd;
	private String tMileageLimitAmt;
	private List<MileageSave> tMileageReseveList;

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
	public List<MileageSave> gettMileageReseveList() {
		return this.tMileageReseveList;
	}

	/**
	 * @param tMileageReseveList
	 *            the tMileageReseveList to set
	 */
	public void settMileageReseveList(List<MileageSave> tMileageReseveList) {
		this.tMileageReseveList = tMileageReseveList;
	}

}
