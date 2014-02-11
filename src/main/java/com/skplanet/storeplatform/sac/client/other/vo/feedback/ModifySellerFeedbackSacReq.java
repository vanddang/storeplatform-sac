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

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ModifySellerFeedbackReq Value Object
 * 
 * Updated on : 2014. 1. 27. Updated by : 김현일, 인크로스.
 */
public class ModifySellerFeedbackSacReq extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 상품ID.
	 */
	@NotBlank
	private String prodId;
	/**
	 * 회원Key(판매자).
	 */
	@NotBlank
	private String sellerKey;
	/**
	 * 사용후기 번호.
	 */
	@NotBlank
	private String notiSeq;
	/**
	 * 판매자 댓글 제목.
	 */
	@NotBlank
	private String sellerRespTitle;
	/**
	 * 판매자 댓글 내용.
	 */
	@NotBlank
	private String sellerRespOpin;

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
}
