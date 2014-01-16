package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CheckPurchase extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodId;
	private String prchsId;

	public CheckPurchase() {
	}

	public CheckPurchase(String prodId, String prchsId) {
		this.prodId = prodId;
		this.prchsId = prchsId;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getPrchsId() {
		return this.prchsId;
	}

	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}
}
