package com.skplanet.storeplatform.sac.client.display.vo.related;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

public class SimilarMovieSacRes {
	private List<Product> productList;

	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
