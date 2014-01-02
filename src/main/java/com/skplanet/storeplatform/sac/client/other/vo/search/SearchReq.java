/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.search;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SearchReq extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deviceId;
	private String userKey;
	private String deviceModelNo;
	private String category;
	private String orderedBy;
	private String prodType;
	private String newShoppingYN;
	private String keywd;
	private String googleLinkYN;
	private String adultYN;
	private String relSearchYN;
	private String dtType;
	private String spellCorrectYN;
	private int offset;
	private int count;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getProdType() {
		return this.prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public String getNewShoppingYN() {
		return this.newShoppingYN;
	}

	public void setNewShoppingYN(String newShoppingYN) {
		this.newShoppingYN = newShoppingYN;
	}

	public String getKeywd() {
		return this.keywd;
	}

	public void setKeywd(String keywd) {
		this.keywd = keywd;
	}

	public String getGoogleLinkYN() {
		return this.googleLinkYN;
	}

	public void setGoogleLinkYN(String googleLinkYN) {
		this.googleLinkYN = googleLinkYN;
	}

	public String getAdultYN() {
		return this.adultYN;
	}

	public void setAdultYN(String adultYN) {
		this.adultYN = adultYN;
	}

	public String getRelSearchYN() {
		return this.relSearchYN;
	}

	public void setRelSearchYN(String relSearchYN) {
		this.relSearchYN = relSearchYN;
	}

	public String getDtType() {
		return this.dtType;
	}

	public void setDtType(String dtType) {
		this.dtType = dtType;
	}

	public String getSpellCorrectYN() {
		return this.spellCorrectYN;
	}

	public void setSpellCorrectYN(String spellCorrectYN) {
		this.spellCorrectYN = spellCorrectYN;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
