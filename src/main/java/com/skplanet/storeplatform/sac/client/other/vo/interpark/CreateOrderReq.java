package com.skplanet.storeplatform.sac.client.other.vo.interpark;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * CreateOrderReq Value Object
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
public class CreateOrderReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String revOrdNo;
	private String ordDts;
	private String prdNo;
	private String itemNo;
	private String price;
	private String qty;
	private String flag;

	/**
	 * @return String
	 */
	public String getRevOrdNo() {
		return this.revOrdNo;
	}

	/**
	 * @param revOrdNo
	 *            revOrdNo
	 */
	public void setRevOrdNo(String revOrdNo) {
		this.revOrdNo = revOrdNo;
	}

	/**
	 * @return String
	 */
	public String getOrdDts() {
		return this.ordDts;
	}

	/**
	 * @param ordDts
	 *            ordDts
	 */
	public void setOrdDts(String ordDts) {
		this.ordDts = ordDts;
	}

	/**
	 * @return String
	 */
	public String getPrdNo() {
		return this.prdNo;
	}

	/**
	 * @param prdNo
	 *            prdNo
	 */
	public void setPrdNo(String prdNo) {
		this.prdNo = prdNo;
	}

	/**
	 * @return String
	 */
	public String getItemNo() {
		return this.itemNo;
	}

	/**
	 * @param itemNo
	 *            itemNo
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * @return String
	 */
	public String getPrice() {
		return this.price;
	}

	/**
	 * @param price
	 *            price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return String
	 */
	public String getQty() {
		return this.qty;
	}

	/**
	 * @param qty
	 *            qty
	 */
	public void setQty(String qty) {
		this.qty = qty;
	}

	/**
	 * @return String
	 */
	public String getFlag() {
		return this.flag;
	}

	/**
	 * @param flag
	 *            flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
