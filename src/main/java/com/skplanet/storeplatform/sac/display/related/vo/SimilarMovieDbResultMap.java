/**
 *
 */
package com.skplanet.storeplatform.sac.display.related.vo;

/**
 * 영화 유사 상품 API에서 사용하는 mybatis resultmap class
 *
 * Updated on : 2015. 3. 18.
 * Updated by : 문동선M
 */
public class SimilarMovieDbResultMap {
	private String prodId;
	private String simProdIdList;

	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getSimProdIdList() {
		return simProdIdList;
	}
	public void setSimProdIdList(String simProdIdList) {
		this.simProdIdList = simProdIdList;
	}
}
