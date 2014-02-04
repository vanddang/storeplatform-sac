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
	 * 사용후기 번호.
	 */
	private int notiSeq;
	/**
	 * 판매자 댓글 제목.
	 */
	private String sellerRespTitle;
	/**
	 * 판매자 댓글 내용.
	 */
	private String sellerRespOpin;

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
