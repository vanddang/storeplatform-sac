package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * T마일리지 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class MileageSave extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private double saveAmt; // 적립예정금액
	private String saveDt; // 적립예정일

	/**
	 * @return the saveAmt
	 */
	public double getSaveAmt() {
		return this.saveAmt;
	}

	/**
	 * @param saveAmt
	 *            the saveAmt to set
	 */
	public void setSaveAmt(double saveAmt) {
		this.saveAmt = saveAmt;
	}

	/**
	 * @return the saveDt
	 */
	public String getSaveDt() {
		return this.saveDt;
	}

	/**
	 * @param saveDt
	 *            the saveDt to set
	 */
	public void setSaveDt(String saveDt) {
		this.saveDt = saveDt;
	}

}
