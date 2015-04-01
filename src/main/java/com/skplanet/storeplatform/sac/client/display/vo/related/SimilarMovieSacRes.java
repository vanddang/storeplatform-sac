package com.skplanet.storeplatform.sac.client.display.vo.related;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

public class SimilarMovieSacRes {
	private Integer count;
	private String hasNext;
	private List<Product> productList;

	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getHasNext() {
		return hasNext;
	}
	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
