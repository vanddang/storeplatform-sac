/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.interworking.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 2. 5. Updated by : 조용진, NTELS.
 */
public class CreateOrderReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 제휴사 주문번호.
	 */
	private String revOrdNo; // PRCHS_ID
	/**
	 * 결제일시.
	 */
	private String ordDts; // PRCHS_DTM
	/**
	 * 상품번호.
	 */
	private String prdNo; // INTERPARK_PROD_ID
	/**
	 * 제휴사 상품번호.
	 */
	private String itemNo; // PROD_ID
	/**
	 * 상품 판매가.
	 */
	private Integer price; // PROD_AMT
	/**
	 * 수량.
	 */
	private String qty;
	/**
	 * 플래그.
	 */
	private String flag;

	/**
	 * 
	 * @return String
	 */
	public String getRevOrdNo() {
		return this.revOrdNo;
	}

	/**
	 * 
	 * @param revOrdNo
	 *            revOrdNo
	 */
	public void setRevOrdNo(String revOrdNo) {
		this.revOrdNo = revOrdNo;
	}

	/**
	 * 
	 * @return String
	 */
	public String getOrdDts() {
		return this.ordDts;
	}

	/**
	 * 
	 * @param ordDts
	 *            ordDts
	 */
	public void setOrdDts(String ordDts) {
		this.ordDts = ordDts;
	}

	/**
	 * 
	 * @return String
	 */
	public String getPrdNo() {
		return this.prdNo;
	}

	/**
	 * 
	 * @param prdNo
	 *            prdNo
	 */
	public void setPrdNo(String prdNo) {
		this.prdNo = prdNo;
	}

	/**
	 * 
	 * @return String
	 */
	public String getItemNo() {
		return this.itemNo;
	}

	/**
	 * 
	 * @param itemNo
	 *            itemNo
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * 
	 * @return String
	 */
	public Integer getPrice() {
		return this.price;
	}

	/**
	 * 
	 * @param price
	 *            price
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * 
	 * @return String
	 */
	public String getQty() {
		return this.qty;
	}

	/**
	 * 
	 * @param qty
	 *            qty
	 */
	public void setQty(String qty) {
		this.qty = qty;
	}

	/**
	 * 
	 * @return String
	 */
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 
	 * @param flag
	 *            flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
