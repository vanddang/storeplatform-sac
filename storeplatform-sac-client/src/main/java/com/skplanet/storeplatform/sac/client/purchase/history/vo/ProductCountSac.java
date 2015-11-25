package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매내역 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class ProductCountSac extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String prodId;
	private String prodCount;

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
	 * @return the prodCount
	 */
	public String getProdCount() {
		return this.prodCount;
	}

	/**
	 * @param prodCount
	 *            the prodCount to set
	 */
	public void setProdCount(String prodCount) {
		this.prodCount = prodCount;
	}

}
