package com.skplanet.storeplatform.purchase.client.cancel.vo;

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScReq;

/**
 * 즉시이용정지 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class ImmediatelyUseStopScRes extends PurchaseCommonScReq {

	private static final long serialVersionUID = 1L;

	private String prchsId;

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

}
