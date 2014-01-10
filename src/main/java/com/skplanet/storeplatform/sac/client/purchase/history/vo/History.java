package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매내역 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class History extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String prchsId;
	private String prchsDtlId;
	private String useTenantId;
	private String useInsdUsermbrNo;
	private String useInsdDeviceId;
	private String prchsDt;
	private String totAmt;
	private String sendInsdUsermbrNo;
	private String sendInsdDeviceId;
	private String recvDt;
	private String prodId;
	private String prodAmt;
	private String prodQty;
	private String prodGrpCd;
	private String prchsStatusCd;
	private String useStartDt;
	private String useExprDt;
	private String hidingYn;
	private String cancelReqPathCd;
	private String cancelDt;
	private String cpnPublishCd;
	private String cpnDlvUrl;
	private String prchsCaseCd;
	private String rePrchsPmtYn;
	private String dwldStartDt;
	private String dwldExprDt;
	private String prodNm;
	private String prodImgUrl;

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

	public String getUseTenantId() {
		return this.useTenantId;
	}

	public void setUseTenantId(String useTenantId) {
		this.useTenantId = useTenantId;
	}

	public String getUseInsdUsermbrNo() {
		return this.useInsdUsermbrNo;
	}

	public void setUseInsdUsermbrNo(String useInsdUsermbrNo) {
		this.useInsdUsermbrNo = useInsdUsermbrNo;
	}

	public String getUseInsdDeviceId() {
		return this.useInsdDeviceId;
	}

	public void setUseInsdDeviceId(String useInsdDeviceId) {
		this.useInsdDeviceId = useInsdDeviceId;
	}

	public String getPrchsDt() {
		return this.prchsDt;
	}

	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	public String getTotAmt() {
		return this.totAmt;
	}

	public void setTotAmt(String totAmt) {
		this.totAmt = totAmt;
	}

	public String getSendInsdUsermbrNo() {
		return this.sendInsdUsermbrNo;
	}

	public void setSendInsdUsermbrNo(String sendInsdUsermbrNo) {
		this.sendInsdUsermbrNo = sendInsdUsermbrNo;
	}

	public String getSendInsdDeviceId() {
		return this.sendInsdDeviceId;
	}

	public void setSendInsdDeviceId(String sendInsdDeviceId) {
		this.sendInsdDeviceId = sendInsdDeviceId;
	}

	public String getRecvDt() {
		return this.recvDt;
	}

	public void setRecvDt(String recvDt) {
		this.recvDt = recvDt;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}

	public String getProdQty() {
		return this.prodQty;
	}

	public void setProdQty(String prodQty) {
		this.prodQty = prodQty;
	}

	public String getProdGrpCd() {
		return this.prodGrpCd;
	}

	public void setProdGrpCd(String prodGrpCd) {
		this.prodGrpCd = prodGrpCd;
	}

	public String getPrchsStatusCd() {
		return this.prchsStatusCd;
	}

	public void setPrchsStatusCd(String prchsStatusCd) {
		this.prchsStatusCd = prchsStatusCd;
	}

	public String getUseStartDt() {
		return this.useStartDt;
	}

	public void setUseStartDt(String useStartDt) {
		this.useStartDt = useStartDt;
	}

	public String getUseExprDt() {
		return this.useExprDt;
	}

	public void setUseExprDt(String useExprDt) {
		this.useExprDt = useExprDt;
	}

	public String getHidingYn() {
		return this.hidingYn;
	}

	public void setHidingYn(String hidingYn) {
		this.hidingYn = hidingYn;
	}

	public String getCancelReqPathCd() {
		return this.cancelReqPathCd;
	}

	public void setCancelReqPathCd(String cancelReqPathCd) {
		this.cancelReqPathCd = cancelReqPathCd;
	}

	public String getCancelDt() {
		return this.cancelDt;
	}

	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
	}

	public String getCpnPublishCd() {
		return this.cpnPublishCd;
	}

	public void setCpnPublishCd(String cpnPublishCd) {
		this.cpnPublishCd = cpnPublishCd;
	}

	public String getCpnDlvUrl() {
		return this.cpnDlvUrl;
	}

	public void setCpnDlvUrl(String cpnDlvUrl) {
		this.cpnDlvUrl = cpnDlvUrl;
	}

	public String getPrchsCaseCd() {
		return this.prchsCaseCd;
	}

	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
	}

	public String getRePrchsPmtYn() {
		return this.rePrchsPmtYn;
	}

	public void setRePrchsPmtYn(String rePrchsPmtYn) {
		this.rePrchsPmtYn = rePrchsPmtYn;
	}

	public String getDwldStartDt() {
		return this.dwldStartDt;
	}

	public void setDwldStartDt(String dwldStartDt) {
		this.dwldStartDt = dwldStartDt;
	}

	public String getDwldExprDt() {
		return this.dwldExprDt;
	}

	public void setDwldExprDt(String dwldExprDt) {
		this.dwldExprDt = dwldExprDt;
	}

	public String getProdNm() {
		return this.prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getProdImgUrl() {
		return this.prodImgUrl;
	}

	public void setProdImgUrl(String prodImgUrl) {
		this.prodImgUrl = prodImgUrl;
	}

}
