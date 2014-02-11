package com.skplanet.storeplatform.sac.display.feature.theme.recommend.vo;

public class ThemeRecommend {

	private int totalCount;

	private String prodId;
	private String prodNm;
	private String filePos;

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the prodNm
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * @param prodNm
	 *            the prodNm to set
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * @return the filePos
	 */
	public String getFilePos() {
		return this.filePos;
	}

	/**
	 * @param filePos
	 *            the filePos to set
	 */
	public void setFilePos(String filePos) {
		this.filePos = filePos;
	}

}
