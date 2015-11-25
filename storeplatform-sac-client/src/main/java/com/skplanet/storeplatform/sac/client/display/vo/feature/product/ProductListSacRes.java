package com.skplanet.storeplatform.sac.client.display.vo.feature.product;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonRawValue;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

public class ProductListSacRes {
	private String listId;
	private String listNm;
	private String startKey;
	private String hasNext; // Y/N
	private Integer count;
	private Date  date;
	@JsonRawValue
	private String etcProp;
	private List<Product> productList;

	public ProductListSacRes() {
		productList = new ArrayList<Product>();
	}
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
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getEtcProp() {
		return etcProp;
	}

	public void setEtcProp(String etcProp) {
		this.etcProp = etcProp;
	}
}
