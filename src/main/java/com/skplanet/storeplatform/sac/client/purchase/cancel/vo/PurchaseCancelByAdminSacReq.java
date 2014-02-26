/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.cancel.vo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;

/**
 * 구매 취소(사용자) 요청 VO.
 * 
 * Updated on : 2014. 2. 12. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelByAdminSacReq extends PurchaseCommonSacReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String cancelReqPathCd;

	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String forceCancelYn;

	@NotEmpty
	@Valid
	private List<PurchaseCancelByAdminDetailSacReq> prchsCancelList;

	/**
	 * @return the cancelReqPathCd
	 */
	public String getCancelReqPathCd() {
		return this.cancelReqPathCd;
	}

	/**
	 * @param cancelReqPathCd
	 *            the cancelReqPathCd to set
	 */
	public void setCancelReqPathCd(String cancelReqPathCd) {
		this.cancelReqPathCd = cancelReqPathCd;
	}

	/**
	 * @return the forceCancelYn
	 */
	public String getForceCancelYn() {
		return this.forceCancelYn;
	}

	/**
	 * @param forceCancelYn
	 *            the forceCancelYn to set
	 */
	public void setForceCancelYn(String forceCancelYn) {
		this.forceCancelYn = forceCancelYn;
	}

	/**
	 * @return the prchsCancelList
	 */
	public List<PurchaseCancelByAdminDetailSacReq> getPrchsCancelList() {
		return this.prchsCancelList;
	}

	/**
	 * @param prchsCancelList
	 *            the prchsCancelList to set
	 */
	public void setPrchsCancelList(List<PurchaseCancelByAdminDetailSacReq> prchsCancelList) {
		this.prchsCancelList = prchsCancelList;
	}

}
