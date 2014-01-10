package com.skplanet.storeplatform.sac.client.other.vo.interpark;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CreateOrderReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String revOrdNo;
	private String ordDts;
	private String prdNo;
	private String itemNo;
	private String price;
	private String qty;
	private String flag;

	public String getRevOrdNo() {
		return this.revOrdNo;
	}
	public void setRevOrdNo(String revOrdNo) {
		this.revOrdNo = revOrdNo;
	}
	public String getOrdDts() {
		return this.ordDts;
	}
	public void setOrdDts(String ordDts) {
		this.ordDts = ordDts;
	}
	public String getPrdNo() {
		return this.prdNo;
	}
	public void setPrdNo(String prdNo) {
		this.prdNo = prdNo;
	}
	public String getItemNo() {
		return this.itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getPrice() {
		return this.price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQty() {
		return this.qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getFlag() {
		return this.flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
