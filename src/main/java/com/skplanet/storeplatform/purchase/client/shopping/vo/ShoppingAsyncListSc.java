/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.shopping.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 쿠폰 발급 비동기 응답
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : nTels_yjw, nTels.
 */
public class ShoppingAsyncListSc extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String prchsId;
	private String mdn;
	private String mdn2;
	private String avail_startdate;
	private String avail_enddate;

	private List<ShoppingAsyncItemSc> items;

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
	 * @return the mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return the mdn2
	 */
	public String getMdn2() {
		return this.mdn2;
	}

	/**
	 * @param mdn2
	 *            the mdn2 to set
	 */
	public void setMdn2(String mdn2) {
		this.mdn2 = mdn2;
	}

	/**
	 * @return the avail_startdate
	 */
	public String getAvail_startdate() {
		return this.avail_startdate;
	}

	/**
	 * @param avail_startdate
	 *            the avail_startdate to set
	 */
	public void setAvail_startdate(String avail_startdate) {
		this.avail_startdate = avail_startdate;
	}

	/**
	 * @return the avail_enddate
	 */
	public String getAvail_enddate() {
		return this.avail_enddate;
	}

	/**
	 * @param avail_enddate
	 *            the avail_enddate to set
	 */
	public void setAvail_enddate(String avail_enddate) {
		this.avail_enddate = avail_enddate;
	}

	/**
	 * @return the items
	 */
	public List<ShoppingAsyncItemSc> getItems() {
		return this.items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<ShoppingAsyncItemSc> items) {
		this.items = items;
	}

}
