package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class MyPagePurchaseProduct extends CommonInfo {
	private static final long serialVersionUID = 201311131L;
	private MyPageProduct product;
	private MyPagePurchase purchase;

	public MyPagePurchaseProduct() {
	}

	public MyPagePurchaseProduct(MyPagePurchase purchase, MyPageProduct product) {
		this.purchase = purchase;
		this.product = product;
	}

	public MyPageProduct getProduct() {
		return this.product;
	}

	public void setProduct(MyPageProduct product) {
		this.product = product;
	}

	public MyPagePurchase getPurchase() {
		return this.purchase;
	}

	public void setPurchase(MyPagePurchase purchase) {
		this.purchase = purchase;
	}
}
