/**
 *
 */
package com.skplanet.storeplatform.sac.display.related.vo;

/**
 * 유사 영화 DB 조회 조건
 *
 * Updated on : 2015. 3. 18.
 * Updated by : 문동선M
 */
public class SimilarMovieCriteria {
	private String tenantId;
	private String prodId;
	private String stdDt;

	public SimilarMovieCriteria(String tenantId, String prodId, String stdDt) {
		super();
		this.tenantId = tenantId;
		this.prodId = prodId;
		this.stdDt = stdDt;
	}

	public String getTenantId() {
		return tenantId;
	}
	public String getProdId() {
		return prodId;
	}
	public String getStdDt() {
		return stdDt;
	}
}
