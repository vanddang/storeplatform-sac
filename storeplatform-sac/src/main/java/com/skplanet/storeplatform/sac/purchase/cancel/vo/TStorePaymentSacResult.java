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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 18. Updated by : nTels_cswoo81, nTels.
 */
public class TStorePaymentSacResult extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prchsId;

	private String payCls;
	private String payCancelResultCd;
	private String payCancelResultMsg;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the payCls
	 */
	public String getPayCls() {
		return this.payCls;
	}

	/**
	 * @param payCls
	 *            the payCls to set
	 */
	public void setPayCls(String payCls) {
		this.payCls = payCls;
	}

	/**
	 * @return the payCancelResultCd
	 */
	public String getPayCancelResultCd() {
		return this.payCancelResultCd;
	}

	/**
	 * @param payCancelResultCd
	 *            the payCancelResultCd to set
	 */
	public void setPayCancelResultCd(String payCancelResultCd) {
		this.payCancelResultCd = payCancelResultCd;
	}

	/**
	 * @return the payCancelResultMsg
	 */
	public String getPayCancelResultMsg() {
		return this.payCancelResultMsg;
	}

	/**
	 * @param payCancelResultMsg
	 *            the payCancelResultMsg to set
	 */
	public void setPayCancelResultMsg(String payCancelResultMsg) {
		this.payCancelResultMsg = payCancelResultMsg;
	}

}
