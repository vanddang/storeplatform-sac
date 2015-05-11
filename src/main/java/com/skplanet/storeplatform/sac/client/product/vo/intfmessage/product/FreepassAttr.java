/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * FreepassAttr Value Object
 * 
 * 이용권 속성정보 req VO
 * 
 * Updated on : 2015. 05. 07. Updated by : 이태균, IS PLUS.
 */
public class FreepassAttr extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/*
	 * 이용권 그룹코드 ("PD013301" : VOD 정액제 그룹 / "PD013302" : 도서(일반) 정액제 그룹 / "PD013303" : 만화 정액제 그룹 / "PD013304" : 장르 정액제 그룹
	 * "PD013305" : 연재 정액제 그룹)
	 */
	private String couponGroup;

	/*
	 * 소장,대여 구분코드 (“DP010601” : 소장 / “DP010602” : 대여 / “DP010603” : 소장/대여 모두 가능)
	 */
	private String possLend;

	/*
	 * 이북,코믹 연재/단행 구분코드 (“DP004301” : 단행 / “DP004302” : 연재)
	 */
	private String serialBook;

	public FreepassAttr() {
	}

	public FreepassAttr(String couponGroup, String possLend, String serialBook) {
		super();
		this.couponGroup = couponGroup;
		this.possLend = possLend;
		this.serialBook = serialBook;
	}

	public String getCouponGroup() {
		return this.couponGroup;
	}

	public void setCouponGroup(String couponGroup) {
		this.couponGroup = couponGroup;
	}

	public String getPossLend() {
		return this.possLend;
	}

	public void setPossLend(String possLend) {
		this.possLend = possLend;
	}

	public String getSerialBook() {
		return this.serialBook;
	}

	public void setSerialBook(String serialBook) {
		this.serialBook = serialBook;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
