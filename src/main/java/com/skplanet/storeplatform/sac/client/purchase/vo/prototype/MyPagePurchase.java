package com.skplanet.storeplatform.sac.client.purchase.vo.prototype;

import org.springframework.format.annotation.DateTimeFormat;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class MyPagePurchase extends CommonInfo {
	private static final long serialVersionUID = 201311131L;
	private String prchsId;
	private String prchsDtlId;
	private String useTenantId;
	private String useMbrNo;
	private String useDeviceNo;
	private String sendTenantId;
	private String sendMbrNo;
	private String sendDeviceNo;

	@DateTimeFormat(pattern = "yyyyMMddHHmmss")
	private String prchsDt;
	private int totAmt;
	private int prodAmt;
	private String prodGrpCd;
	private String statusCd;

	@DateTimeFormat(pattern = "yyyyMMddHHmmss")
	private String useStartDt;

	@DateTimeFormat(pattern = "yyyyMMddHHmmss")
	private String useExprDt;

	@DateTimeFormat(pattern = "yyyyMMddHHmmss")
	private String dwldStartDt;

	@DateTimeFormat(pattern = "yyyyMMddHHmmss")
	private String dwldExprDt;

	@DateTimeFormat(pattern = "yyyyMMddHHmmss")
	private String cancelDt;

	@DateTimeFormat(pattern = "yyyyMMddHHmmss")
	private String recvDt;
	private String rePrchsPmtYn;
	private String hidingYn;
	private String expiredYn;

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

	public String getUseMbrNo() {
		return this.useMbrNo;
	}

	public void setUseMbrNo(String useMbrNo) {
		this.useMbrNo = useMbrNo;
	}

	public String getUseDeviceNo() {
		return this.useDeviceNo;
	}

	public void setUseDeviceNo(String useDeviceNo) {
		this.useDeviceNo = useDeviceNo;
	}

	public String getSendTenantId() {
		return this.sendTenantId;
	}

	public void setSendTenantId(String sendTenantId) {
		this.sendTenantId = sendTenantId;
	}

	public String getSendMbrNo() {
		return this.sendMbrNo;
	}

	public void setSendMbrNo(String sendMbrNo) {
		this.sendMbrNo = sendMbrNo;
	}

	public String getSendDeviceNo() {
		return this.sendDeviceNo;
	}

	public void setSendDeviceNo(String sendDeviceNo) {
		this.sendDeviceNo = sendDeviceNo;
	}

	public String getPrchsDt() {
		return this.prchsDt;
	}

	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	public int getTotAmt() {
		return this.totAmt;
	}

	public void setTotAmt(int totAmt) {
		this.totAmt = totAmt;
	}

	public int getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(int prodAmt) {
		this.prodAmt = prodAmt;
	}

	public String getProdGrpCd() {
		return this.prodGrpCd;
	}

	public void setProdGrpCd(String prodGrpCd) {
		this.prodGrpCd = prodGrpCd;
	}

	public String getStatusCd() {
		return this.statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
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

	public String getCancelDt() {
		return this.cancelDt;
	}

	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
	}

	public String getRecvDt() {
		return this.recvDt;
	}

	public void setRecvDt(String recvDt) {
		this.recvDt = recvDt;
	}

	public String getRePrchsPmtYn() {
		return this.rePrchsPmtYn;
	}

	public void setRePrchsPmtYn(String rePrchsPmtYn) {
		this.rePrchsPmtYn = rePrchsPmtYn;
	}

	public String getHidingYn() {
		return this.hidingYn;
	}

	public void setHidingYn(String hidingYn) {
		this.hidingYn = hidingYn;
	}

	public String getExpiredYn() {
		return this.expiredYn;
	}

	public void setExpiredYn(String expiredYn) {
		this.expiredYn = expiredYn;
	}
}
