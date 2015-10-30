package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 이용권에 등록된 상품 정보 조회 CmpxProductInfoList VO.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식 , 지티소프트
 */
public class CmpxProductInfoList extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodId;
	private String prodNm;
	private Double prodAmt;
	private String prodStatusCd;
	private String prodGrdCd;
	private String prodSprtYn;
	private String drmYn;
	private String usePeriodUnitCd;
	private Integer usePeriod;
	private String cid;
	private String chapterText;
	private String chapterUnit;
	private String possLendClsfCd;
	private String downStrmClsfCd;
	private String topMenuId;
	private String usePeriodSetCd; 
	private String episodeProdStatusCd;
	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return prodId;
	}
	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	/**
	 * @return the prodNm
	 */
	public String getProdNm() {
		return prodNm;
	}
	/**
	 * @param prodNm the prodNm to set
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	/**
	 * @return the prodAmt
	 */
	public Double getProdAmt() {
		return prodAmt;
	}
	/**
	 * @param prodAmt the prodAmt to set
	 */
	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}
	/**
	 * @return the prodStatusCd
	 */
	public String getProdStatusCd() {
		return prodStatusCd;
	}
	/**
	 * @param prodStatusCd the prodStatusCd to set
	 */
	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}
	/**
	 * @return the prodGrdCd
	 */
	public String getProdGrdCd() {
		return prodGrdCd;
	}
	/**
	 * @param prodGrdCd the prodGrdCd to set
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}
	/**
	 * @return the prodSprtYn
	 */
	public String getProdSprtYn() {
		return prodSprtYn;
	}
	/**
	 * @param prodSprtYn the prodSprtYn to set
	 */
	public void setProdSprtYn(String prodSprtYn) {
		this.prodSprtYn = prodSprtYn;
	}
	/**
	 * @return the drmYn
	 */
	public String getDrmYn() {
		return drmYn;
	}
	/**
	 * @param drmYn the drmYn to set
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}
	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return usePeriodUnitCd;
	}
	/**
	 * @param usePeriodUnitCd the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}
	/**
	 * @return the usePeriod
	 */
	public Integer getUsePeriod() {
		return usePeriod;
	}
	/**
	 * @param usePeriod the usePeriod to set
	 */
	public void setUsePeriod(Integer usePeriod) {
		this.usePeriod = usePeriod;
	}
	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}
	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**
	 * @return the chapterText
	 */
	public String getChapterText() {
		return chapterText;
	}
	/**
	 * @param chapterText the chapterText to set
	 */
	public void setChapterText(String chapterText) {
		this.chapterText = chapterText;
	}
	/**
	 * @return the chapterUnit
	 */
	public String getChapterUnit() {
		return chapterUnit;
	}
	/**
	 * @param chapterUnit the chapterUnit to set
	 */
	public void setChapterUnit(String chapterUnit) {
		this.chapterUnit = chapterUnit;
	}
	/**
	 * @return the possLendClsfCd
	 */
	public String getPossLendClsfCd() {
		return possLendClsfCd;
	}
	/**
	 * @param possLendClsfCd the possLendClsfCd to set
	 */
	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
	}
	/**
	 * @return the downStrmClsfCd
	 */
	public String getDownStrmClsfCd() {
		return downStrmClsfCd;
	}
	/**
	 * @param downStrmClsfCd the downStrmClsfCd to set
	 */
	public void setDownStrmClsfCd(String downStrmClsfCd) {
		this.downStrmClsfCd = downStrmClsfCd;
	}
	
	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return topMenuId;
	}
	/**
	 * @param topMenuId the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}
	/**
	 * @return the usePeriodSetCd
	 */
	public String getUsePeriodSetCd() {
		return usePeriodSetCd;
	}
	/**
	 * @param usePeriodSetCd the usePeriodSetCd to set
	 */
	public void setUsePeriodSetCd(String usePeriodSetCd) {
		this.usePeriodSetCd = usePeriodSetCd;
	}
	/**
	 * @return the episodeProdStatusCd
	 */
	public String getEpisodeProdStatusCd() {
		return episodeProdStatusCd;
	}
	/**
	 * @param episodeProdStatusCd the episodeProdStatusCd to set
	 */
	public void setEpisodeProdStatusCd(String episodeProdStatusCd) {
		this.episodeProdStatusCd = episodeProdStatusCd;
	}


}
