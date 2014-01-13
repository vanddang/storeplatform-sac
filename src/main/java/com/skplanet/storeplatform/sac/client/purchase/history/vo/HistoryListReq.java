package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class HistoryListReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String insdUsermbrNo;
	private String insdDeviceId;
	private String startDt;
	private String endDt;
	private String tenantProdGrpCd;
	private String prodId;
	private String prchsProdType;
	private String hidingYn;
	private String prchsStatusCd;
	private String frProdId;

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
	}

	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
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

	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getPrchsProdType() {
		return this.prchsProdType;
	}

	public void setPrchsProdType(String prchsProdType) {
		this.prchsProdType = prchsProdType;
	}

	public String getHidingYn() {
		return this.hidingYn;
	}

	public void setHidingYn(String hidingYn) {
		this.hidingYn = hidingYn;
	}

	public String getPrchsStatusCd() {
		return this.prchsStatusCd;
	}

	public void setPrchsStatusCd(String prchsStatusCd) {
		this.prchsStatusCd = prchsStatusCd;
	}

	public String getFrProdId() {
		return this.frProdId;
	}

	public void setFrProdId(String frProdId) {
		this.frProdId = frProdId;
	}

}
