package com.skplanet.storeplatform.sac.client.display.vo.feature.product;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

public class ProductListSacRes {
	private String startKey;
	private String hasNext; // Y/N
	private Integer count;
	private Date  date;
	private List<Product> productList;

	public ProductListSacRes() {
		this.productList = new ArrayList<Product>();
	}

	public String getStartKey() {
		return this.startKey;
	}
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}
	public List<Product> getProductList() {
		return this.productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public String getHasNext() {
		return this.hasNext;
	}
	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}
	public Integer getCount() {
		return this.count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
