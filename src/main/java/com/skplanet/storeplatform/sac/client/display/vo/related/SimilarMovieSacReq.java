package com.skplanet.storeplatform.sac.client.display.vo.related;

public class SimilarMovieSacReq {
	private String productId;
	private String prodGradeCd;
	private String exceptId;
	private Integer count;

	public String getProductId() {
		return productId;
	}
	public void setProductId(String prodId) {
		this.productId = prodId;
	}
	public String getProdGradeCd() {
		return prodGradeCd;
	}
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}
	public String getExceptId() {
		return exceptId;
	}
	public void setExceptId(String exceptId) {
		this.exceptId = exceptId;
	}
	public Integer getCount() {
		if(count==null)
			return 20;
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
