/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.vo;

import java.util.List;

import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonSacParam;

/**
 * 구매 취소(사용자) 요청 VO.
 * 
 * Updated on : 2014. 2. 12. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelSacParam extends PurchaseCommonSacParam {

	private static final long serialVersionUID = 1L;

	private String reqUserId;

	private Integer prchsCancelByType; // 구매 취소 요청자 구분(사용자 / 운영자).
	private Integer prchsCancelServiceType = 0; // 기본 : 0, TCASH : 1
	private String cancelReqPathCd;
	private String shoppingForceCancelYn;
	private String sktLimitUserCancelYn;

	private Boolean ignorePayment = false;
	private Boolean ignoreCouponCms = false;

	private List<PurchaseCancelDetailSacParam> prchsCancelList;

	/**
	 * @return the ignoreCouponCms
	 */
	public Boolean getIgnoreCouponCms() {
		return this.ignoreCouponCms;
	}

	/**
	 * @param ignoreCouponCms
	 *            the ignoreCouponCms to set
	 */
	public void setIgnoreCouponCms(Boolean ignoreCouponCms) {
		this.ignoreCouponCms = ignoreCouponCms;
	}

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

	public Boolean getIgnorePayment() {
		return this.ignorePayment;
	}

	public void setIgnorePayment(Boolean ignorePayment) {
		this.ignorePayment = ignorePayment;
	}

	/**
	 * @return the prchsCancelByType
	 */
	public Integer getPrchsCancelByType() {
		return this.prchsCancelByType;
	}

	/**
	 * @param prchsCancelByType
	 *            the prchsCancelByType to set
	 */
	public void setPrchsCancelByType(Integer prchsCancelByType) {
		this.prchsCancelByType = prchsCancelByType;
	}

	/**
	 * @return the prchsCancelServiceType
	 */
	public Integer getPrchsCancelServiceType() {
		return this.prchsCancelServiceType;
	}

	/**
	 * @param prchsCancelServiceType
	 *            the prchsCancelServiceType to set
	 */
	public void setPrchsCancelServiceType(Integer prchsCancelServiceType) {
		this.prchsCancelServiceType = prchsCancelServiceType;
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
	public List<PurchaseCancelDetailSacParam> getPrchsCancelList() {
		return this.prchsCancelList;
	}

	/**
	 * @param prchsCancelList
	 *            the prchsCancelList to set
	 */
	public void setPrchsCancelList(List<PurchaseCancelDetailSacParam> prchsCancelList) {
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
