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

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * CreateFeedbackReq Value Object
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
public class CreateFeedbackSacReq extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 상품 ID.
	 */
	@NotBlank
	private String prodId;
	/**
	 * 사용자 Key.
	 */
	@NotBlank
	private String userKey;
	/**
	 * 사용자 ID.
	 */
	@NotBlank
	private String userId;
	/**
	 * 기기 ID.
	 */
	private String deviceId;
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
	private String notiDscr;
	/**
	 * 페이스북 전송 여부.
	 */
	@Pattern(regexp = "^$|^Y|^N")
	private String fbPostYn;
	/**
	 * 패키지 버전 코드.
	 */
	private String pkgVer;
	/**
	 * 평점.
	 */
	private String avgScore;

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
	 * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return String
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            deviceId
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

}
