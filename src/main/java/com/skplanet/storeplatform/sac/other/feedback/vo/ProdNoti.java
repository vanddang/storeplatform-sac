/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ProdNoti Value Object
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
public class ProdNoti extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tenantId;
	private int notiSeq;
	private String notiTypeCd;
	private String mbrNo;
	private String prodId;
	private String title;
	private String notiDscr;
	private String trackbackAddr;
	private String reqIp;
	private String postId;
	private String url;
	private String blogNm;
	private String spamYn;
	private String badNotiYn;
	private String mbrTelno;
	private String notiReason;
	private String notiOpin;
	private int notiScore;
	private String sellerMbrNm;
	private String sellerRespRegDt;
	private String sellerRespTitle;
	private String sellerRespOpin;
	private String recomYn;
	private String fbPostYn;
	private String oprpersRespYn;
	private String respOprpersId;
	private String respOprpersNm;
	private String respOprpersProcDt;
	private String confCd;
	private String catalogId;
	private String regLoc;
	private String deviceModelCd;
	private String pkgVer;
	private String regId;
	private String regDt;
	private String updDt;
	private String delDt;
	private String delYn;

	// 검색 조건 추가.
	private String chnlId;

	/**
	 * @return String
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return int
	 */
	public int getNotiSeq() {
		return this.notiSeq;
	}

	/**
	 * @param notiSeq
	 *            notiSeq
	 */
	public void setNotiSeq(int notiSeq) {
		this.notiSeq = notiSeq;
	}

	/**
	 * @return String
	 */
	public String getNotiTypeCd() {
		return this.notiTypeCd;
	}

	/**
	 * @param notiTypeCd
	 *            notiTypeCd
	 */
	public void setNotiTypeCd(String notiTypeCd) {
		this.notiTypeCd = notiTypeCd;
	}

	/**
	 * @return String
	 */
	public String getMbrNo() {
		return this.mbrNo;
	}

	/**
	 * @param mbrNo
	 *            mbrNo
	 */
	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
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
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	public String getTrackbackAddr() {
		return this.trackbackAddr;
	}

	/**
	 * @param trackbackAddr
	 *            trackbackAddr
	 */
	public void setTrackbackAddr(String trackbackAddr) {
		this.trackbackAddr = trackbackAddr;
	}

	/**
	 * @return String
	 */
	public String getReqIp() {
		return this.reqIp;
	}

	/**
	 * @param reqIp
	 *            reqIp
	 */
	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

	/**
	 * @return String
	 */
	public String getPostId() {
		return this.postId;
	}

	/**
	 * @param postId
	 *            postId
	 */
	public void setPostId(String postId) {
		this.postId = postId;
	}

	/**
	 * @return String
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 *            url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return String
	 */
	public String getBlogNm() {
		return this.blogNm;
	}

	/**
	 * @param blogNm
	 *            blogNm
	 */
	public void setBlogNm(String blogNm) {
		this.blogNm = blogNm;
	}

	/**
	 * @return String
	 */
	public String getSpamYn() {
		return this.spamYn;
	}

	/**
	 * @param spamYn
	 *            spamYn
	 */
	public void setSpamYn(String spamYn) {
		this.spamYn = spamYn;
	}

	/**
	 * @return String
	 */
	public String getBadNotiYn() {
		return this.badNotiYn;
	}

	/**
	 * @param badNotiYn
	 *            badNotiYn
	 */
	public void setBadNotiYn(String badNotiYn) {
		this.badNotiYn = badNotiYn;
	}

	/**
	 * @return String
	 */
	public String getMbrTelno() {
		return this.mbrTelno;
	}

	/**
	 * @param mbrTelno
	 *            mbrTelno
	 */
	public void setMbrTelno(String mbrTelno) {
		this.mbrTelno = mbrTelno;
	}

	/**
	 * @return String
	 */
	public String getNotiReason() {
		return this.notiReason;
	}

	/**
	 * @param notiReason
	 *            notiReason
	 */
	public void setNotiReason(String notiReason) {
		this.notiReason = notiReason;
	}

	/**
	 * @return String
	 */
	public String getNotiOpin() {
		return this.notiOpin;
	}

	/**
	 * @param notiOpin
	 *            notiOpin
	 */
	public void setNotiOpin(String notiOpin) {
		this.notiOpin = notiOpin;
	}

	/**
	 * @return int
	 */
	public int getNotiScore() {
		return this.notiScore;
	}

	/**
	 * @param notiScore
	 *            notiScore
	 */
	public void setNotiScore(int notiScore) {
		this.notiScore = notiScore;
	}

	/**
	 * @return String
	 */
	public String getSellerMbrNm() {
		return this.sellerMbrNm;
	}

	/**
	 * @param sellerMbrNm
	 *            sellerMbrNm
	 */
	public void setSellerMbrNm(String sellerMbrNm) {
		this.sellerMbrNm = sellerMbrNm;
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
	public String getRecomYn() {
		return this.recomYn;
	}

	/**
	 * @param recomYn
	 *            recomYn
	 */
	public void setRecomYn(String recomYn) {
		this.recomYn = recomYn;
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
	public String getOprpersRespYn() {
		return this.oprpersRespYn;
	}

	/**
	 * @param oprpersRespYn
	 *            oprpersRespYn
	 */
	public void setOprpersRespYn(String oprpersRespYn) {
		this.oprpersRespYn = oprpersRespYn;
	}

	/**
	 * @return String
	 */
	public String getRespOprpersId() {
		return this.respOprpersId;
	}

	/**
	 * @param respOprpersId
	 *            respOprpersId
	 */
	public void setRespOprpersId(String respOprpersId) {
		this.respOprpersId = respOprpersId;
	}

	/**
	 * @return String
	 */
	public String getRespOprpersNm() {
		return this.respOprpersNm;
	}

	/**
	 * @param respOprpersNm
	 *            respOprpersNm
	 */
	public void setRespOprpersNm(String respOprpersNm) {
		this.respOprpersNm = respOprpersNm;
	}

	/**
	 * @return String
	 */
	public String getRespOprpersProcDt() {
		return this.respOprpersProcDt;
	}

	/**
	 * @param respOprpersProcDt
	 *            respOprpersProcDt
	 */
	public void setRespOprpersProcDt(String respOprpersProcDt) {
		this.respOprpersProcDt = respOprpersProcDt;
	}

	/**
	 * @return String
	 */
	public String getConfCd() {
		return this.confCd;
	}

	/**
	 * @param confCd
	 *            confCd
	 */
	public void setConfCd(String confCd) {
		this.confCd = confCd;
	}

	/**
	 * @return String
	 */
	public String getCatalogId() {
		return this.catalogId;
	}

	/**
	 * @param catalogId
	 *            catalogId
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * @return String
	 */
	public String getRegLoc() {
		return this.regLoc;
	}

	/**
	 * @param regLoc
	 *            regLoc
	 */
	public void setRegLoc(String regLoc) {
		this.regLoc = regLoc;
	}

	/**
	 * @return String
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            deviceModelCd
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return String
	 */
	public String getPkgVer() {
		return this.pkgVer;
	}

	/**
	 * @param pkgVer
	 *            pkgVer
	 */
	public void setPkgVer(String pkgVer) {
		this.pkgVer = pkgVer;
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
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            updDt
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	/**
	 * @return String
	 */
	public String getDelDt() {
		return this.delDt;
	}

	/**
	 * @param delDt
	 *            delDt
	 */
	public void setDelDt(String delDt) {
		this.delDt = delDt;
	}

	/**
	 * @return String
	 */
	public String getDelYn() {
		return this.delYn;
	}

	/**
	 * @param delYn
	 *            delYn
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	/**
	 * @return String
	 */
	public String getChnlId() {
		return this.chnlId;
	}

	/**
	 * @param chnlId
	 *            chnlId
	 */
	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}

}
