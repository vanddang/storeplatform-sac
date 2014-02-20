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
	private String prodNm; // 에피소드명
	private String chnlProdNm; // 채널명
	private String topMenuId; // 탑메뉴ID
	private String topMenuNm; // 탑메뉴명
	private String menuId; // 메뉴ID
	private String menuNm; // 메뉴명
	private String chapter; // 회차
	private String bookPageCnt; // 페이지 수
	private String filePath; // 파일경로
	private String mainFilePath; // 메인 이미지 경로
	private Double avgEvluScore; // 평점
	private Integer paticpersCnt; // 참여자수
	private String artist1Nm; // 작가명
	private String updDt; // 업데이트일자
	private String prodBaseDesc; // 상품설명
	private String prodDtlDesc; // 상품상세설명
	private String sellerMbrNo; // 회원번호
	private int totalCount;

	/**
	 * <pre>
	 * 채널ID.
	 * </pre>
	 * 
	 * @return String String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * <pre>
	 * 채널ID.
	 * </pre>
	 * 
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * <pre>
	 * 에피소드ID.
	 * </pre>
	 * 
	 * @return String String
	 */
	public String getPartProdId() {
		return this.partProdId;
	}

	/**
	 * <pre>
	 * 에피소드ID.
	 * </pre>
	 * 
	 * @param partProdId
	 *            partProdId
	 */
	public void setPartProdId(String partProdId) {
		this.partProdId = partProdId;
	}

	/**
	 * <pre>
	 * 에피소드명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * <pre>
	 * 에피소드명.
	 * </pre>
	 * 
	 * @param prodNm
	 *            prodNm
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * <pre>
	 * 채널명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChnlProdNm() {
		return this.chnlProdNm;
	}

	/**
	 * <pre>
	 * 채널명.
	 * </pre>
	 * 
	 * @param chnlProdNm
	 *            chnlProdNm
	 */
	public void setChnlProdNm(String chnlProdNm) {
		this.chnlProdNm = chnlProdNm;
	}

	/**
	 * <pre>
	 * 탑메뉴ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * <pre>
	 * 탑메뉴ID.
	 * </pre>
	 * 
	 * @param topMenuId
	 *            topMenuId
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * <pre>
	 * 메뉴ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * <pre>
	 * 메뉴ID.
	 * </pre>
	 * 
	 * @param menuId
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * <pre>
	 * 회차.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChapter() {
		return this.chapter;
	}

	/**
	 * <pre>
	 * 회차.
	 * </pre>
	 * 
	 * @param chapter
	 *            chapter
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	/**
	 * <pre>
	 * 페이지 수.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBookPageCnt() {
		return this.bookPageCnt;
	}

	/**
	 * <pre>
	 * 페이지 수.
	 * </pre>
	 * 
	 * @param bookPageCnt
	 *            bookPageCnt
	 */
	public void setBookPageCnt(String bookPageCnt) {
		this.bookPageCnt = bookPageCnt;
	}

	/**
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @return String
	 */
	public Double getAvgEvluScore() {
		return this.avgEvluScore;
	}

	/**
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @param avgEvluScore
	 *            avgEvluScore
	 */
	public void setAvgEvluScore(Double avgEvluScore) {
		this.avgEvluScore = avgEvluScore;
	}

	/**
	 * <pre>
	 * 참여자수.
	 * </pre>
	 * 
	 * @return String
	 */
	public Integer getPaticpersCnt() {
		return this.paticpersCnt;
	}

	/**
	 * <pre>
	 * 참여자수.
	 * </pre>
	 * 
	 * @param paticpersCnt
	 *            paticpersCnt
	 */
	public void setPaticpersCnt(Integer paticpersCnt) {
		this.paticpersCnt = paticpersCnt;
	}

	/**
	 * <pre>
	 * 작가명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist1Nm() {
		return this.artist1Nm;
	}

	/**
	 * <pre>
	 * 작가명.
	 * </pre>
	 * 
	 * @param artist1Nm
	 *            artist1Nm
	 */
	public void setArtist1Nm(String artist1Nm) {
		this.artist1Nm = artist1Nm;
	}

	/**
	 * <pre>
	 * 업데이트일자.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * <pre>
	 * 업데이트일자.
	 * </pre>
	 * 
	 * @param updDt
	 *            updDt
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	/**
	 * <pre>
	 * 상품설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	/**
	 * <pre>
	 * 상품설명.
	 * </pre>
	 * 
	 * @param prodBaseDesc
	 *            prodBaseDesc
	 */
	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	/**
	 * <pre>
	 * 상품상세설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdDtlDesc() {
		return this.prodDtlDesc;
	}

	/**
	 * <pre>
	 * 상품상세설명.
	 * </pre>
	 * 
	 * @param prodDtlDesc
	 *            prodDtlDesc
	 */
	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
	}

	/**
	 * <pre>
	 * serialVersionUID.
	 * </pre>
	 * 
	 * @return String
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * <pre>
	 * 탑메뉴명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	/**
	 * <pre>
	 * 탑메뉴명.
	 * </pre>
	 * 
	 * @param topMenuNm
	 *            topMenuNm
	 */
	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	/**
	 * <pre>
	 * 메뉴명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuNm() {
		return this.menuNm;
	}

	/**
	 * <pre>
	 * 메뉴명.
	 * </pre>
	 * 
	 * @param menuNm
	 *            menuNm
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	/**
	 * <pre>
	 * 파일경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * <pre>
	 * 파일경로.
	 * </pre>
	 * 
	 * @param filePath
	 *            filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * <pre>
	 * 메인 이미지 경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMainFilePath() {
		return this.mainFilePath;
	}

	/**
	 * <pre>
	 * 메인 이미지 경로.
	 * </pre>
	 * 
	 * @param mainFilePath
	 *            mainFilePath
	 */
	public void setMainFilePath(String mainFilePath) {
		this.mainFilePath = mainFilePath;
	}

	/**
	 * <pre>
	 * 회원번호.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	/**
	 * <pre>
	 * 회원번호.
	 * </pre>
	 * 
	 * @param sellerMbrNo
	 *            sellerMbrNo
	 */
	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}

	/**
	 * <pre>
	 * totalCount.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * <pre>
	 * totalCount.
	 * </pre>
	 * 
	 * @param totalCount
	 *            totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
