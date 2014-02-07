/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.feedback;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Feedback Value Object
 * 
 * Updated on : 2014. 1. 27. Updated by : 김현일, 인크로스.
 */
public class Feedback extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용후기 번호.
	 */
	private String notiSeq;
	/**
	 * 회원 Key.
	 */
	private String userKey;
	/**
	 * 판매자 Key.
	 */
	private String sellerKey;
	/**
	 * 사용후기 제목.
	 */
	private String notiTitle;
	/**
	 * 사용후기 내용.
	 */
	private String notiDscr;
	/**
	 * 사용후기 점수.
	 */
	private String notiScore;
	/**
	 * 등록 ID.
	 */
	private String regId;
	/**
	 * 등록 날짜.
	 */
	private String regDt;
	/**
	 * 판매자 댓글 제목.
	 */
	private String sellerRespTitle;
	/**
	 * 판매자 댓글 내용.
	 */
	private String sellerRespOpin;
	/**
	 * 판매자 댓글 등록 날짜.
	 */
	private String sellerRespRegDt;
	/**
	 * CID.
	 */
	private String cid;
	/**
	 * 본인여부.
	 */
	private String selfYn;
	/**
	 * 판매자여부.
	 */
	private String saleYn;
	/**
	 * 본인/판매자 여부.
	 */
	private String whose;
	/**
	 * 본인추천여부.
	 */
	private String selfRecomYn;
	/**
	 * 평점.
	 */
	private String avgScore;
	/**
	 * 닉네임.
	 */
	private String nickNm;
	/**
	 * 페이스북 전송 여부.
	 */
	private String fbPostYn;
	/**
	 * 상품ID.
	 */
	private String prodId;
	/**
	 * 업체명.
	 */
	private String compNm;

	/**
	 * @return String
	 */
	public String getNotiSeq() {
		return this.notiSeq;
	}

	/**
	 * @param notiSeq
	 *            notiSeq
	 */
	public void setNotiSeq(String notiSeq) {
		this.notiSeq = notiSeq;
	}

	/**
	 * @return String
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            userKey
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return String
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            sellerKey
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return String
	 */
	public String getNotiTitle() {
		return this.notiTitle;
	}

	/**
	 * @param notiTitle
	 *            notiTitle
	 */
	public void setNotiTitle(String notiTitle) {
		this.notiTitle = notiTitle;
	}

	/**
	 * @return String
	 */
	public String getNotiDscr() {
		return this.notiDscr;
	}

	/**
	 * @param notiDscr
	 *            notiDscr
	 */
	public void setNotiDscr(String notiDscr) {
		this.notiDscr = notiDscr;
	}

	/**
	 * @return String
	 */
	public String getNotiScore() {
		return this.notiScore;
	}

	/**
	 * @param notiScore
	 *            notiScore
	 */
	public void setNotiScore(String notiScore) {
		this.notiScore = notiScore;
	}

	/**
	 * @return String
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            regId
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return String
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            regDt
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return String
	 */
	public String getSellerRespTitle() {
		return this.sellerRespTitle;
	}

	/**
	 * @param sellerRespTitle
	 *            sellerRespTitle
	 */
	public void setSellerRespTitle(String sellerRespTitle) {
		this.sellerRespTitle = sellerRespTitle;
	}

	/**
	 * @return String
	 */
	public String getSellerRespOpin() {
		return this.sellerRespOpin;
	}

	/**
	 * @param sellerRespOpin
	 *            sellerRespOpin
	 */
	public void setSellerRespOpin(String sellerRespOpin) {
		this.sellerRespOpin = sellerRespOpin;
	}

	/**
	 * @return String
	 */
	public String getSellerRespRegDt() {
		return this.sellerRespRegDt;
	}

	/**
	 * @param sellerRespRegDt
	 *            sellerRespRegDt
	 */
	public void setSellerRespRegDt(String sellerRespRegDt) {
		this.sellerRespRegDt = sellerRespRegDt;
	}

	/**
	 * @return String
	 */
	public String getCid() {
		return this.cid;
	}

	/**
	 * @param cid
	 *            cid
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return String
	 */
	public String getSelfYn() {
		return this.selfYn;
	}

	/**
	 * @param selfYn
	 *            selfYn
	 */
	public void setSelfYn(String selfYn) {
		this.selfYn = selfYn;
	}

	/**
	 * @return String
	 */
	public String getSaleYn() {
		return this.saleYn;
	}

	/**
	 * @param saleYn
	 *            saleYn
	 */
	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}

	/**
	 * @return String
	 */
	public String getWhose() {
		return this.whose;
	}

	/**
	 * @param whose
	 *            whose
	 */
	public void setWhose(String whose) {
		this.whose = whose;
	}

	/**
	 * @return String
	 */
	public String getSelfRecomYn() {
		return this.selfRecomYn;
	}

	/**
	 * @param selfRecomYn
	 *            selfRecomYn
	 */
	public void setSelfRecomYn(String selfRecomYn) {
		this.selfRecomYn = selfRecomYn;
	}

	/**
	 * @return String
	 */
	public String getAvgScore() {
		return this.avgScore;
	}

	/**
	 * @param avgScore
	 *            avgScore
	 */
	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}

	/**
	 * @return String
	 */
	public String getNickNm() {
		return this.nickNm;
	}

	/**
	 * @param nickNm
	 *            nickNm
	 */
	public void setNickNm(String nickNm) {
		this.nickNm = nickNm;
	}

	/**
	 * @return String
	 */
	public String getFbPostYn() {
		return this.fbPostYn;
	}

	/**
	 * @param fbPostYn
	 *            fbPostYn
	 */
	public void setFbPostYn(String fbPostYn) {
		this.fbPostYn = fbPostYn;
	}

	/**
	 * @return String
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return String
	 */
	public String getCompNm() {
		return this.compNm;
	}

	/**
	 * @param compNm
	 *            compNm
	 */
	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}

}
