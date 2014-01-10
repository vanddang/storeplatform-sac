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
	private String prchsReqPathCd;
	private String prodGrpCd;
	private String prchsCaseCd;
	private String hidingYn;
	private String prchsStatusCd;
	private String freepassProdId;

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

	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	public String getProdGrpCd() {
		return this.prodGrpCd;
	}

	public void setProdGrpCd(String prodGrpCd) {
		this.prodGrpCd = prodGrpCd;
	}

	public String getPrchsCaseCd() {
		return this.prchsCaseCd;
	}

	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
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

	public String getFreepassProdId() {
		return this.freepassProdId;
	}

	public void setFreepassProdId(String freepassProdId) {
		this.freepassProdId = freepassProdId;
	}

}
