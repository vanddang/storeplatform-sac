package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CheckPurchaseRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private List<CheckPurchase> checkPurchaseList;

	public List<CheckPurchase> getCheckPurchaseList() {
		return this.checkPurchaseList;
	}

	public void setCheckPurchaseList(List<CheckPurchase> checkPurchaseList) {
		this.checkPurchaseList = checkPurchaseList;
	}

}
