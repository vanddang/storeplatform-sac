/**
 * 
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 10. 14.
 * Updated by : 1002177
 */
public class AlbumMeta extends CommonInfo{
	private static final long serialVersionUID = -2254851812206897629L;

	// From TB_DP_PROD
	private String prodId;
	private String prodGrdCd;
	private String regDt;
	
	// From TB_DP_PROD_IMG
	private String imgFilePath;
	private String imgFileNm;

	// From TB_DP_ALBUM_PROD
	private String albumTypeNm;
	private String issueDay;
	private String chnlCompNm;
	private String agencyNm;
	
	// From TB_DP_PROD_DESC
	private String prodNm;
	private String prodDtlDesc;
	private String artist1Nm;
	private String artist1Id;
	
	// From TB_DP_MENU_CATEGORY_DESC
	private String topMenuId;
	private String topMenuNm;
	private String topMenuDesc;
	private String menuId;
	private String menuNm;
	private String menuDesc;
	
	private String likeYn;
	
	// From TB_DP_ARTIST
	private String artistImgFilePath;

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdGrdCd() {
		return prodGrdCd;
	}

	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getImgFilePath() {
		return imgFilePath;
	}

	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath = imgFilePath;
	}

	public String getImgFileNm() {
		return imgFileNm;
	}

	public void setImgFileNm(String imgFileNm) {
		this.imgFileNm = imgFileNm;
	}

	public String getAlbumTypeNm() {
		return albumTypeNm;
	}

	public void setAlbumTypeNm(String albumTypeNm) {
		this.albumTypeNm = albumTypeNm;
	}

	public String getIssueDay() {
		return issueDay;
	}

	public void setIssueDay(String issueDay) {
		this.issueDay = issueDay;
	}

	public String getChnlCompNm() {
		return chnlCompNm;
	}

	public void setChnlCompNm(String chnlCompNm) {
		this.chnlCompNm = chnlCompNm;
	}

	public String getAgencyNm() {
		return agencyNm;
	}

	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getProdDtlDesc() {
		return prodDtlDesc;
	}

	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
	}

	public String getArtist1Nm() {
		return artist1Nm;
	}

	public void setArtist1Nm(String artist1Nm) {
		this.artist1Nm = artist1Nm;
	}

	public String getArtist1Id() {
		return artist1Id;
	}

	public void setArtist1Id(String artist1Id) {
		this.artist1Id = artist1Id;
	}

	public String getTopMenuId() {
		return topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getTopMenuNm() {
		return topMenuNm;
	}

	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	public String getTopMenuDesc() {
		return topMenuDesc;
	}

	public void setTopMenuDesc(String topMenuDesc) {
		this.topMenuDesc = topMenuDesc;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getLikeYn() {
		return likeYn;
	}

	public void setLikeYn(String likeYn) {
		this.likeYn = likeYn;
	}

	public String getArtistImgFilePath() {
		return artistImgFilePath;
	}

	public void setArtistImgFilePath(String artistImgFilePath) {
		this.artistImgFilePath = artistImgFilePath;
	}
	
}
