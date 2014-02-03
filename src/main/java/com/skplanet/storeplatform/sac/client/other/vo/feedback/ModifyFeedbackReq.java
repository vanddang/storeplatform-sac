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
 * ModifyFeedbackReq Value Object
 * 
 * Updated on : 2014. 1. 27. Updated by : 김현일, 인크로스
 */
public class ModifyFeedbackReq extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 상품 ID.
	 */
	private String prodId;
	/**
	 * 사용자 Key.
	 */
	private String userKey;
	/**
	 * 사용후기 번호.
	 */
	private int notiSeq;
	/**
	 * 채널 ID.
	 */
	private String chnlId;
	/**
	 * 사용후기 제목.
	 */
	private String notiTitle;
	/**
	 * 사용후기 내용.
	 */
	private String notiDesc;
	/**
	 * 평점.
	 */
	private int score;
	/**
	 * 패키지 버전 코드.
	 */
	private String pkgVerCd;

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
	public String getNotiDesc() {
		return this.notiDesc;
	}

	/**
	 * @param notiDesc
	 *            notiDesc
	 */
	public void setNotiDesc(String notiDesc) {
		this.notiDesc = notiDesc;
	}

	/**
	 * @return int
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * @param score
	 *            score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return String
	 */
	public String getPkgVerCd() {
		return this.pkgVerCd;
	}

	/**
	 * @param pkgVerCd
	 *            pkgVerCd
	 */
	public void setPkgVerCd(String pkgVerCd) {
		this.pkgVerCd = pkgVerCd;
	}

}
