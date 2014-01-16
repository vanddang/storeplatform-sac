package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class MyPagePurchaseHistoryReq extends CommonInfo {
	private static final long serialVersionUID = 201311131L;

	private String tenantId;
	private String mbrNo;
	private String deviceNo;
	private String prchsId;
	private String prchsDtlId;
	private String prodId;
	private String prodOwnType;
	private String startDt;
	private String endDt;
	private int startRow;
	private int endRow;
	private String prodGrpCd;
	private String target;
	private String prchsStatus;
	private String hiding;

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getMbrNo() {
		return this.mbrNo;
	}

	public void setMbrNo(String mbrNo) {
		this.mbrNo = mbrNo;
	}

	public String getDeviceNo() {
		return this.deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getPrchsId() {
		return this.prchsId;
	}

	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	public String getPrchsDtlId() {
		return this.prchsDtlId;
	}

	public void setPrchsDtlId(String prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdOwnType() {
		return this.prodOwnType;
	}

	public void setProdOwnType(String prodOwnType) {
		this.prodOwnType = prodOwnType;
	}

	public String getStartDt() {
		return this.startDt;
	}

	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	public String getEndDt() {
		return this.endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public int getStartRow() {
		return this.startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return this.endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getProdGrpCd() {
		return this.prodGrpCd;
	}

	public void setProdGrpCd(String prodGrpCd) {
		this.prodGrpCd = prodGrpCd;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPrchsStatus() {
		return this.prchsStatus;
	}

	public void setPrchsStatus(String prchsStatus) {
		this.prchsStatus = prchsStatus;
	}

	public String getHiding() {
		return this.hiding;
	}

	public void setHiding(String hiding) {
		this.hiding = hiding;
	}

}
