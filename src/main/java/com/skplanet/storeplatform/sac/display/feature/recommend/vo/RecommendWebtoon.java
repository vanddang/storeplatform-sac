/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.recommend.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 웹툰 DTO Default Value Object.
 * 
 * Updated on : 2013. 12. 22. Updated by : 김형식, SK 플래닛.
 */
public class RecommendWebtoon extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int totalCount;
	private String upMenuId;
	private String upMenuName;
	private String menuId;
	private String menuNm;
	private String channelId;
	private String prodId;
	private String prodNm;
	private String prodGrdCd;
	private String avgScore;
	private String prodAmt;
	private String partCnt;
	private String seriallyWkdy;
	private String iconYn;
	private String updDt;
	private String comptYn;
	private String chapter;
	private String preFix;
	private String artist1Nm;
	private String artist2Nm;
	private String artist3Nm;
	private String filePos;

	/**
	 * <pre>
	 * 총 리스트수.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * <pre>
	 * 총 리스트수.
	 * </pre>
	 * 
	 * @param totalCount
	 *            totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * <pre>
	 * 상위 메뉴 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUpMenuId() {
		return this.upMenuId;
	}

	/**
	 * <pre>
	 * 상위 메뉴 아이디.
	 * </pre>
	 * 
	 * @param upMenuId
	 *            upMenuId
	 */
	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}

	/**
	 * <pre>
	 * 상위 메뉴명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUpMenuName() {
		return this.upMenuName;
	}

	/**
	 * <pre>
	 * 상위 메뉴명.
	 * </pre>
	 * 
	 * @param upMenuName
	 *            upMenuName
	 */
	public void setUpMenuName(String upMenuName) {
		this.upMenuName = upMenuName;
	}

	/**
	 * <pre>
	 * 메뉴 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * <pre>
	 * 메뉴 아이디.
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
	 * 채널 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getChannelId() {
		return this.channelId;
	}

	/**
	 * <pre>
	 * 채널 아이디.
	 * </pre>
	 * 
	 * @param channelId
	 *            channelId
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * <pre>
	 * 상품 아이디.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * <pre>
	 * 상품 아이디.
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
	 * 상품명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * <pre>
	 * 상품명.
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
	 * 상품등급.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	/**
	 * <pre>
	 * 상품등급.
	 * </pre>
	 * 
	 * @param prodGrdCd
	 *            prodGrdCd
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAvgScore() {
		return this.avgScore;
	}

	/**
	 * <pre>
	 * 평점.
	 * </pre>
	 * 
	 * @param avgScore
	 *            avgScore
	 */
	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}

	/**
	 * <pre>
	 * 상품금액.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * <pre>
	 * 상품금액.
	 * </pre>
	 * 
	 * @param prodAmt
	 *            prodAmt
	 */
	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * <pre>
	 * partCnt.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPartCnt() {
		return this.partCnt;
	}

	/**
	 * <pre>
	 * partCnt.
	 * </pre>
	 * 
	 * @param partCnt
	 *            partCnt
	 */
	public void setPartCnt(String partCnt) {
		this.partCnt = partCnt;
	}

	/**
	 * <pre>
	 * seriallyWkdy.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSeriallyWkdy() {
		return this.seriallyWkdy;
	}

	/**
	 * <pre>
	 * seriallyWkdy.
	 * </pre>
	 * 
	 * @param seriallyWkdy
	 *            seriallyWkdy
	 */
	public void setSeriallyWkdy(String seriallyWkdy) {
		this.seriallyWkdy = seriallyWkdy;
	}

	/**
	 * <pre>
	 * iconYn.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getIconYn() {
		return this.iconYn;
	}

	/**
	 * <pre>
	 * iconYn.
	 * </pre>
	 * 
	 * @param iconYn
	 *            iconYn
	 */
	public void setIconYn(String iconYn) {
		this.iconYn = iconYn;
	}

	/**
	 * <pre>
	 * comptYn.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getComptYn() {
		return this.comptYn;
	}

	/**
	 * <pre>
	 * comptYn.
	 * </pre>
	 * 
	 * @param comptYn
	 *            comptYn
	 */
	public void setComptYn(String comptYn) {
		this.comptYn = comptYn;
	}

	/**
	 * <pre>
	 * 업데이트 일자.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * <pre>
	 * 업데이트 일자.
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
	 * preFix.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPreFix() {
		return this.preFix;
	}

	/**
	 * <pre>
	 * preFix.
	 * </pre>
	 * 
	 * @param preFix
	 *            preFix
	 */
	public void setPreFix(String preFix) {
		this.preFix = preFix;
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
	 * artist1Nm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist1Nm() {
		return this.artist1Nm;
	}

	/**
	 * <pre>
	 * artist1Nm.
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
	 * artist2Nm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist2Nm() {
		return this.artist2Nm;
	}

	/**
	 * <pre>
	 * artist2Nm.
	 * </pre>
	 * 
	 * @param artist2Nm
	 *            artist2Nm
	 */
	public void setArtist2Nm(String artist2Nm) {
		this.artist2Nm = artist2Nm;
	}

	/**
	 * <pre>
	 * artist3Nm.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getArtist3Nm() {
		return this.artist3Nm;
	}

	/**
	 * <pre>
	 * artist3Nm.
	 * </pre>
	 * 
	 * @param artist3Nm
	 *            artist3Nm
	 */
	public void setArtist3Nm(String artist3Nm) {
		this.artist3Nm = artist3Nm;
	}

	/**
	 * <pre>
	 * filePos.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilePos() {
		return this.filePos;
	}

	/**
	 * <pre>
	 * filePos.
	 * </pre>
	 * 
	 * @param filePos
	 *            filePos
	 */
	public void setFilePos(String filePos) {
		this.filePos = filePos;
	}
}
