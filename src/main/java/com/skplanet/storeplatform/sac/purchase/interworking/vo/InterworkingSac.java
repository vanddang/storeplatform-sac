/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.interworking.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매후처리 연동(인터파크,씨네21).
 * 
 * Updated on : 2014. 2. 26. Updated by : 조용진, NTELS.
 */
public class InterworkingSac extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String mallCd;
	private String sellermbrNo;
	private String prodId;
	private Double prodAmt;
	private String compContentsId;

	/**
	 * @return the mallCd
	 */
	public String getMallCd() {
		return this.mallCd;
	}

	/**
	 * @param mallCd
	 *            the mallCd to set
	 */
	public void setMallCd(String mallCd) {
		this.mallCd = mallCd;
	}

	/**
	 * @return the sellermbrNo
	 */
	public String getSellermbrNo() {
		return this.sellermbrNo;
	}

	/**
	 * @param sellermbrNo
	 *            the sellermbrNo to set
	 */
	public void setSellermbrNo(String sellermbrNo) {
		this.sellermbrNo = sellermbrNo;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the prodAmt
	 */
	public Double getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the compContentsId
	 */
	public String getCompContentsId() {
		return this.compContentsId;
	}

	/**
	 * @param compContentsId
	 *            the compContentsId to set
	 */
	public void setCompContentsId(String compContentsId) {
		this.compContentsId = compContentsId;
	}

}
