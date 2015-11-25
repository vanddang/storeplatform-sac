package com.skplanet.storeplatform.purchase.client.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * T마일리지 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class MileageSaveGetScRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String procStatusCd;
	private String saveDt;
	private String saveResultAmt;
	private String typeCd; // 마일리지타입

	/**
	 * @return the procStatusCd
	 */
	public String getProcStatusCd() {
		return this.procStatusCd;
	}

	/**
	 * @param procStatusCd
	 *            the procStatusCd to set
	 */
	public void setProcStatusCd(String procStatusCd) {
		this.procStatusCd = procStatusCd;
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

	/**
	 * @return the saveResultAmt
	 */
	public String getSaveResultAmt() {
		return this.saveResultAmt;
	}

	/**
	 * @param saveResultAmt
	 *            the saveResultAmt to set
	 */
	public void setSaveResultAmt(String saveResultAmt) {
		this.saveResultAmt = saveResultAmt;
	}

	/**
	 * Gets type cd.
	 *
	 * @return the type cd
	 */
	public String getTypeCd() {
		return typeCd;
	}

	/**
	 * Sets type cd.
	 *
	 * @param typeCd
	 *            the type cd
	 */
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
}
