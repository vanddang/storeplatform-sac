package com.skplanet.storeplatform.sac.client.display.vo.feature.outproduct.list;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.OutProduct;

public class OutProductListSacRes {
	private String listId;
	private String listNm;
	private String startKey;
	private String hasNext; // Y/N
	private Integer count;

	private List<OutProduct> productList;

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getListNm() {
		return listNm;
	}

	public void setListNm(String listNm) {
		this.listNm = listNm;
	}

	public String getStartKey() {
		return startKey;
	}

	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}

	public String getHasNext() {
		return hasNext;
	}

	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<OutProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<OutProduct> productList) {
		this.productList = productList;
	}
}
