/**
 * 
 */
package com.skplanet.storeplatform.sac.display.webtoon.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 웹툰 상품 상세 조회
 * 
 * Updated on : 2014. 2. 19. Updated by : 조준일, nTels.
 */
public class WebtoonDetail extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodId; // 채널ID
	private String partProdId; // 에피소드ID
	private String nextProdId; // 다음회ID
	private String preProdId; // 이전회ID
	private String prodNm; // 에피소드명
	private String chnlProdNm; // 채널명
	private String topMenuId; // 탑메뉴ID
	private String topMenuNm; // 탑메뉴명
	private String menuId; // 메뉴ID
	private String menuNm; // 메뉴명
	private String chapter; // 회차
	private String bookPages; // 페이지 수
	private String filePath; // 파일경로
	private String mainFilePath; // 메인 이미지 경로
	private Double avgEvluScore; // 평점
	private Integer partCnt; // 참여자수
	private String artistNm; // 작가명
	private String updDt; // 업데이트일자
	private String mbrNo; // 회원번호
	private String prodDesc; // 상품설명
	private String prodDtlDesc; // 상품상세설명

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getPartProdId() {
		return this.partProdId;
	}

	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

	public String getNextProdId() {
		return this.nextProdId;
	}

	public void setNextProdId(String nextProdId) {
		this.nextProdId = nextProdId;
	}

	public String getPreProdId() {
		return this.preProdId;
	}

	public void setPreProdId(String preProdId) {
		this.preProdId = preProdId;
	}

	public String getProdNm() {
		return this.prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getChnlProdNm() {
		return this.chnlProdNm;
	}

	public void setChnlProdNm(String chnlProdNm) {
		this.chnlProdNm = chnlProdNm;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getChapter() {
		return this.chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getBookPages() {
		return this.bookPages;
	}

	public void setBookPages(String bookPages) {
		this.bookPages = bookPages;
	}

	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	public Integer getPartCnt() {
		return this.partCnt;
	}

	public void setPartCnt(Integer partCnt) {
		this.partCnt = partCnt;
	}

	public String getArtistNm() {
		return this.artistNm;
	}

	public void setArtistNm(String artistNm) {
		this.artistNm = artistNm;
	}

	public String getUpdDt() {
		return this.updDt;
	}

	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	public String getMbrNo() {
		return this.mbrNo;
	}

	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	public String getProdDesc() {
		return this.prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	public String getProdDtlDesc() {
		return this.prodDtlDesc;
	}

	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	public String getMenuNm() {
		return this.menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMainFilePath() {
		return this.mainFilePath;
	}

	public void setMainFilePath(String mainFilePath) {
		this.mainFilePath = mainFilePath;
	}

}
