package com.skplanet.storeplatform.sac.client.display.vo.related;

public class SimilarMovieSacReq {
	private String prodId;
	private String prodGradeCd;
	private Integer count;

	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getProdGradeCd() {
		return prodGradeCd;
	}
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
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
