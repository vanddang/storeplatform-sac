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

	private String reqUserId;

	@NotBlank
	private String cancelReqPathCd;

	@NotBlank
	@Pattern(regexp = "^Y|^N|^F")
	private String shoppingForceCancelYn;

	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String sktLimitUserCancelYn;

	@NotEmpty
	@Valid
	private List<PurchaseCancelDetailSacReq> prchsCancelList;

	/**
	 * @return the reqUserId
	 */
	public String getReqUserId() {
		return this.reqUserId;
	}

	/**
	 * @param reqUserId
	 *            the reqUserId to set
	 */
	public void setReqUserId(String reqUserId) {
		this.reqUserId = reqUserId;
	}

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

	public String getShoppingForceCancelYn() {
		return this.shoppingForceCancelYn;
	}

	public void setShoppingForceCancelYn(String shoppingForceCancelYn) {
		this.shoppingForceCancelYn = shoppingForceCancelYn;
	}

	/**
	 * @return the prchsCancelList
	 */
	public List<PurchaseCancelDetailSacReq> getPrchsCancelList() {
		return this.prchsCancelList;
	}

	/**
	 * @param prchsCancelList
	 *            the prchsCancelList to set
	 */
	public void setPrchsCancelList(List<PurchaseCancelDetailSacReq> prchsCancelList) {
		this.prchsCancelList = prchsCancelList;
	}

	/**
	 * @return the sktLimitUserCancelYn
	 */
	public String getSktLimitUserCancelYn() {
		return this.sktLimitUserCancelYn;
	}

	/**
	 * @param sktLimitUserCancelYn
	 *            the sktLimitUserCancelYn to set
	 */
	public void setSktLimitUserCancelYn(String sktLimitUserCancelYn) {
		this.sktLimitUserCancelYn = sktLimitUserCancelYn;
	}

}
