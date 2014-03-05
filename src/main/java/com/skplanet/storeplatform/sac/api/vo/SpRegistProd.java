package com.skplanet.storeplatform.sac.api.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
/**
 * <pre>
 * 정산 처리 프로시저 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class SpRegistProd extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String prodId; // 상품_ID
	private String settlRt; // 정산율
	private String saleMbrNo; // 판매자번호
	private String saleStdDt; // 판매시작일
	private String saleEndDt; // 판매완료일
	private String regId; // 등록자아이디
	private String rtn;
	private String cudType; // CUD

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the settlRt
	 */
	public String getSettlRt() {
		return this.settlRt;
	}

	/**
	 * @param settlRt
	 *            the settlRt to set
	 */
	public void setSettlRt(String settlRt) {
		this.settlRt = settlRt;
	}

	/**
	 * @return the saleMbrNo
	 */
	public String getSaleMbrNo() {
		return this.saleMbrNo;
	}

	/**
	 * @param saleMbrNo
	 *            the saleMbrNo to set
	 */
	public void setSaleMbrNo(String saleMbrNo) {
		this.saleMbrNo = saleMbrNo;
	}

	/**
	 * @return the saleStdDt
	 */
	public String getSaleStdDt() {
		return this.saleStdDt;
	}

	/**
	 * @param saleStdDt
	 *            the saleStdDt to set
	 */
	public void setSaleStdDt(String saleStdDt) {
		this.saleStdDt = saleStdDt;
	}

	/**
	 * @return the saleEndDt
	 */
	public String getSaleEndDt() {
		return this.saleEndDt;
	}

	/**
	 * @param saleEndDt
	 *            the saleEndDt to set
	 */
	public void setSaleEndDt(String saleEndDt) {
		this.saleEndDt = saleEndDt;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the rtn
	 */
	public String getRtn() {
		return this.rtn;
	}

	/**
	 * @param rtn
	 *            the rtn to set
	 */
	public void setRtn(String rtn) {
		this.rtn = rtn;
	}

	/**
	 * @return the cudType
	 */
	public String getCudType() {
		return this.cudType;
	}

	/**
	 * @param cudType
	 *            the cudType to set
	 */
	public void setCudType(String cudType) {
		this.cudType = cudType;
	}

}
