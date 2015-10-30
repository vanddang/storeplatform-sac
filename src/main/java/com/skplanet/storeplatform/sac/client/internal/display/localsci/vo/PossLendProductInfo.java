package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * PossLendProductInfo Value Object
 * 
 * 소장/대여 상품 정보 조회 VO
 * 
 * Updated on : 2014. 4. 15. Updated by : 홍지호, 엔텔스
 */
public class PossLendProductInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	// ////////////////////////// 소장상품 변수 ////////////////////////////
	private String possProdId;
	private String possProdNm;
	private Double possProdAmt;
	private String chapter;
	private String bookClsfCd;
	private String chapterText;
	private String chapterUnit;
	private String chnlProdNm;

	// ////////////////////////// 대여상품 변수 ////////////////////////////
	private String lendProdId;
	private String lendProdNm;
	private Double lendProdAmt;
	private String usePeriodUnitCd;
	private Integer usePeriod;
	private String downPossiblePeriodUnitCd;
	private Integer downPossiblePeriod;

	/**
	 * @return the possProdId
	 */
	public String getPossProdId() {
		return this.possProdId;
	}

	/**
	 * @param possProdId
	 *            the possProdId to set
	 */
	public void setPossProdId(String possProdId) {
		this.possProdId = possProdId;
	}

	/**
	 * @return the possProdNm
	 */
	public String getPossProdNm() {
		return this.possProdNm;
	}

	/**
	 * @param possProdNm
	 *            the possProdNm to set
	 */
	public void setPossProdNm(String possProdNm) {
		this.possProdNm = possProdNm;
	}

	/**
	 * @return the possProdAmt
	 */
	public Double getPossProdAmt() {
		return this.possProdAmt;
	}

	/**
	 * @param possProdAmt
	 *            the possProdAmt to set
	 */
	public void setPossProdAmt(Double possProdAmt) {
		this.possProdAmt = possProdAmt;
	}

	/**
	 * @return the chapter
	 */
	public String getChapter() {
		return this.chapter;
	}

	/**
	 * @param chapter
	 *            the chapter to set
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	/**
	 * @return the bookClsfCd
	 */
	public String getBookClsfCd() {
		return this.bookClsfCd;
	}

	/**
	 * @param bookClsfCd
	 *            the bookClsfCd to set
	 */
	public void setBookClsfCd(String bookClsfCd) {
		this.bookClsfCd = bookClsfCd;
	}

	/**
	 * @return the chapterText
	 */
	public String getChapterText() {
		return this.chapterText;
	}

	/**
	 * @param chapterText
	 *            the chapterText to set
	 */
	public void setChapterText(String chapterText) {
		this.chapterText = chapterText;
	}

	/**
	 * @return the chapterUnit
	 */
	public String getChapterUnit() {
		return this.chapterUnit;
	}

	/**
	 * @param chapterUnit
	 *            the chapterUnit to set
	 */
	public void setChapterUnit(String chapterUnit) {
		this.chapterUnit = chapterUnit;
	}

	/**
	 * @return the chnlProdNm
	 */
	public String getChnlProdNm() {
		return this.chnlProdNm;
	}

	/**
	 * @param chnlProdNm
	 *            the chnlProdNm to set
	 */
	public void setChnlProdNm(String chnlProdNm) {
		this.chnlProdNm = chnlProdNm;
	}

	/**
	 * @return the lendProdId
	 */
	public String getLendProdId() {
		return this.lendProdId;
	}

	/**
	 * @param lendProdId
	 *            the lendProdId to set
	 */
	public void setLendProdId(String lendProdId) {
		this.lendProdId = lendProdId;
	}

	/**
	 * @return the lendProdNm
	 */
	public String getLendProdNm() {
		return this.lendProdNm;
	}

	/**
	 * @param lendProdNm
	 *            the lendProdNm to set
	 */
	public void setLendProdNm(String lendProdNm) {
		this.lendProdNm = lendProdNm;
	}

	/**
	 * @return the lendProdAmt
	 */
	public Double getLendProdAmt() {
		return this.lendProdAmt;
	}

	/**
	 * @param lendProdAmt
	 *            the lendProdAmt to set
	 */
	public void setLendProdAmt(Double lendProdAmt) {
		this.lendProdAmt = lendProdAmt;
	}

	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * @param usePeriodUnitCd
	 *            the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * @return the usePeriod
	 */
	public Integer getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(Integer usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * @return the downPossiblePeriodUnitCd
	 */
	public String getDownPossiblePeriodUnitCd() {
		return this.downPossiblePeriodUnitCd;
	}

	/**
	 * @param downPossiblePeriodUnitCd
	 *            the downPossiblePeriodUnitCd to set
	 */
	public void setDownPossiblePeriodUnitCd(String downPossiblePeriodUnitCd) {
		this.downPossiblePeriodUnitCd = downPossiblePeriodUnitCd;
	}

	/**
	 * @return the downPossiblePeriod
	 */
	public Integer getDownPossiblePeriod() {
		return this.downPossiblePeriod;
	}

	/**
	 * @param downPossiblePeriod
	 *            the downPossiblePeriod to set
	 */
	public void setDownPossiblePeriod(Integer downPossiblePeriod) {
		this.downPossiblePeriod = downPossiblePeriod;
	}

}
