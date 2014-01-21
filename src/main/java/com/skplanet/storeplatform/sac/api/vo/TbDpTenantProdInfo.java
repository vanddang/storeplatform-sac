/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.vo;
/**
 * <pre>
 * 전처리 상품 테넌트 상품 Value Object
 * </pre>
 *
 * Created on : 2014-01-02
 * Created by : 김형식, SK플래닛
 * Last Updated on : 2014-01-02
 * Last Updated by : 김형식, SK플래닛
 */
public class TbDpTenantProdInfo {
	private String prodId; //
	private String tenantId; //
	private String prodStatusCd; //
	private String expoYn; //
	private long expoOrd; //
	private String regId; //
	private String regDt; //
	private String updId; //
	private String updDt; //

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	public String getExpoYn() {
		return this.expoYn;
	}

	public void setExpoYn(String expoYn) {
		this.expoYn = expoYn;
	}

	public long getExpoOrd() {
		return this.expoOrd;
	}

	public void setExpoOrd(long expoOrd) {
		this.expoOrd = expoOrd;
	}

	public String getRegId() {
		return this.regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return this.regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getUpdId() {
		return this.updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getUpdDt() {
		return this.updDt;
	}

	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

}
