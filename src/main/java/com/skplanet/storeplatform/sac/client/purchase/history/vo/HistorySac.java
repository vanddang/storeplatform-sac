package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매내역 조회 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class HistorySac extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 구매정보
	private String tenantId;
	private String systemId;
	private String prchsId;
	private String prchsDtlId;
	private String useTenantId;
	private String useInsdUsermbrNo;
	private String useInsdDeviceId;
	private String recvTenantId;
	private String recvInsdUsermbrNo;
	private String recvInsdDeviceId;
	private String prchsDt;
	private String totAmt;
	private String sendInsdUsermbrNo;
	private String sendInsdDeviceId;
	private String recvDt;
	private String recvConfPathCd;
	private String tenantProdGrpCd;
	private String prodId;
	private String prodAmt;
	private String prodQty;
	private String statusCd;
	private String useStartDt;
	private String useExprDt;
	private String hidingYn;
	private String cancelReqPathCd;
	private String cancelDt;
	private String prchsCaseCd;
	private String rePrchsPmtYn;
	private String dwldStartDt;
	private String dwldExprDt;
	private String cpnPublishCd;
	private String cpnDlvUrl;
	private String etcSeq;
	private String fixrateProdId;
	private String prchsProdType;

	// 자동결제정보
	private String paymentStartDt; // 결제시작일시
	private String paymentEndDt; // 결제종료일시
	private String afterPaymentDt; // 이후결제일시
	private String prchsTme; // 회차
	private String closedCd; // 해지코드
	private String closedDt; // 해지일시
	private String closedReasonCd; // 해지사유코드
	private String closedReqPathCd; // 해지요청경로

	// 상품정보
	// private Product product;

	private String prodNm; // 상품명
	// private String prodImgUrl;
	//
	// private List<Menu> menuList;
	// private List<Source> sourceList;
	//
	//
	// private String menuId; // 메뉴ID
	// private String menuName; // 메뉴명
	//
	private String grade; // 이용등급

	//
	// // 앱정보
	// private String packageName; // 패키지명
	// private String versionCode; // 패키지 버전 코드

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @return the prchsTme
	 */
	public String getPrchsTme() {
		return this.prchsTme;
	}

	/**
	 * @param prchsTme
	 *            the prchsTme to set
	 */
	public void setPrchsTme(String prchsTme) {
		this.prchsTme = prchsTme;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the prchsDtlId
	 */
	public String getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(String prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}

	/**
	 * @return the useTenantId
	 */
	public String getUseTenantId() {
		return this.useTenantId;
	}

	/**
	 * @param useTenantId
	 *            the useTenantId to set
	 */
	public void setUseTenantId(String useTenantId) {
		this.useTenantId = useTenantId;
	}

	/**
	 * @return the useInsdUsermbrNo
	 */
	public String getUseInsdUsermbrNo() {
		return this.useInsdUsermbrNo;
	}

	/**
	 * @param useInsdUsermbrNo
	 *            the useInsdUsermbrNo to set
	 */
	public void setUseInsdUsermbrNo(String useInsdUsermbrNo) {
		this.useInsdUsermbrNo = useInsdUsermbrNo;
	}

	/**
	 * @return the useInsdDeviceId
	 */
	public String getUseInsdDeviceId() {
		return this.useInsdDeviceId;
	}

	/**
	 * @param useInsdDeviceId
	 *            the useInsdDeviceId to set
	 */
	public void setUseInsdDeviceId(String useInsdDeviceId) {
		this.useInsdDeviceId = useInsdDeviceId;
	}

	/**
	 * @return the recvTenantId
	 */
	public String getRecvTenantId() {
		return this.recvTenantId;
	}

	/**
	 * @param recvTenantId
	 *            the recvTenantId to set
	 */
	public void setRecvTenantId(String recvTenantId) {
		this.recvTenantId = recvTenantId;
	}

	/**
	 * @return the recvInsdUsermbrNo
	 */
	public String getRecvInsdUsermbrNo() {
		return this.recvInsdUsermbrNo;
	}

	/**
	 * @param recvInsdUsermbrNo
	 *            the recvInsdUsermbrNo to set
	 */
	public void setRecvInsdUsermbrNo(String recvInsdUsermbrNo) {
		this.recvInsdUsermbrNo = recvInsdUsermbrNo;
	}

	/**
	 * @return the recvInsdDeviceId
	 */
	public String getRecvInsdDeviceId() {
		return this.recvInsdDeviceId;
	}

	/**
	 * @param recvInsdDeviceId
	 *            the recvInsdDeviceId to set
	 */
	public void setRecvInsdDeviceId(String recvInsdDeviceId) {
		this.recvInsdDeviceId = recvInsdDeviceId;
	}

	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the totAmt
	 */
	public String getTotAmt() {
		return this.totAmt;
	}

	/**
	 * @param totAmt
	 *            the totAmt to set
	 */
	public void setTotAmt(String totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the sendInsdUsermbrNo
	 */
	public String getSendInsdUsermbrNo() {
		return this.sendInsdUsermbrNo;
	}

	/**
	 * @param sendInsdUsermbrNo
	 *            the sendInsdUsermbrNo to set
	 */
	public void setSendInsdUsermbrNo(String sendInsdUsermbrNo) {
		this.sendInsdUsermbrNo = sendInsdUsermbrNo;
	}

	/**
	 * @return the sendInsdDeviceId
	 */
	public String getSendInsdDeviceId() {
		return this.sendInsdDeviceId;
	}

	/**
	 * @param sendInsdDeviceId
	 *            the sendInsdDeviceId to set
	 */
	public void setSendInsdDeviceId(String sendInsdDeviceId) {
		this.sendInsdDeviceId = sendInsdDeviceId;
	}

	/**
	 * @return the recvDt
	 */
	public String getRecvDt() {
		return this.recvDt;
	}

	/**
	 * @param recvDt
	 *            the recvDt to set
	 */
	public void setRecvDt(String recvDt) {
		this.recvDt = recvDt;
	}

	/**
	 * @return the recvConfPathCd
	 */
	public String getRecvConfPathCd() {
		return this.recvConfPathCd;
	}

	/**
	 * @param recvConfPathCd
	 *            the recvConfPathCd to set
	 */
	public void setRecvConfPathCd(String recvConfPathCd) {
		this.recvConfPathCd = recvConfPathCd;
	}

	/**
	 * @return the tenantProdGrpCd
	 */
	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	/**
	 * @param tenantProdGrpCd
	 *            the tenantProdGrpCd to set
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

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
	 * @return the prodAmt
	 */
	public String getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the prodQty
	 */
	public String getProdQty() {
		return this.prodQty;
	}

	/**
	 * @param prodQty
	 *            the prodQty to set
	 */
	public void setProdQty(String prodQty) {
		this.prodQty = prodQty;
	}

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the useStartDt
	 */
	public String getUseStartDt() {
		return this.useStartDt;
	}

	/**
	 * @param useStartDt
	 *            the useStartDt to set
	 */
	public void setUseStartDt(String useStartDt) {
		this.useStartDt = useStartDt;
	}

	/**
	 * @return the useExprDt
	 */
	public String getUseExprDt() {
		return this.useExprDt;
	}

	/**
	 * @param useExprDt
	 *            the useExprDt to set
	 */
	public void setUseExprDt(String useExprDt) {
		this.useExprDt = useExprDt;
	}

	/**
	 * @return the hidingYn
	 */
	public String getHidingYn() {
		return this.hidingYn;
	}

	/**
	 * @param hidingYn
	 *            the hidingYn to set
	 */
	public void setHidingYn(String hidingYn) {
		this.hidingYn = hidingYn;
	}

	/**
	 * @return the cancelReqPathCd
	 */
	public String getCancelReqPathCd() {
		return this.cancelReqPathCd;
	}

	/**
	 * @param cancelReqPathCd
	 *            the cancelReqPathCd to set
	 */
	public void setCancelReqPathCd(String cancelReqPathCd) {
		this.cancelReqPathCd = cancelReqPathCd;
	}

	/**
	 * @return the cancelDt
	 */
	public String getCancelDt() {
		return this.cancelDt;
	}

	/**
	 * @param cancelDt
	 *            the cancelDt to set
	 */
	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
	}

	/**
	 * @return the prchsCaseCd
	 */
	public String getPrchsCaseCd() {
		return this.prchsCaseCd;
	}

	/**
	 * @param prchsCaseCd
	 *            the prchsCaseCd to set
	 */
	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
	}

	/**
	 * @return the rePrchsPmtYn
	 */
	public String getRePrchsPmtYn() {
		return this.rePrchsPmtYn;
	}

	/**
	 * @param rePrchsPmtYn
	 *            the rePrchsPmtYn to set
	 */
	public void setRePrchsPmtYn(String rePrchsPmtYn) {
		this.rePrchsPmtYn = rePrchsPmtYn;
	}

	/**
	 * @return the dwldStartDt
	 */
	public String getDwldStartDt() {
		return this.dwldStartDt;
	}

	/**
	 * @param dwldStartDt
	 *            the dwldStartDt to set
	 */
	public void setDwldStartDt(String dwldStartDt) {
		this.dwldStartDt = dwldStartDt;
	}

	/**
	 * @return the dwldExprDt
	 */
	public String getDwldExprDt() {
		return this.dwldExprDt;
	}

	/**
	 * @param dwldExprDt
	 *            the dwldExprDt to set
	 */
	public void setDwldExprDt(String dwldExprDt) {
		this.dwldExprDt = dwldExprDt;
	}

	/**
	 * @return the cpnPublishCd
	 */
	public String getCpnPublishCd() {
		return this.cpnPublishCd;
	}

	/**
	 * @param cpnPublishCd
	 *            the cpnPublishCd to set
	 */
	public void setCpnPublishCd(String cpnPublishCd) {
		this.cpnPublishCd = cpnPublishCd;
	}

	/**
	 * @return the cpnDlvUrl
	 */
	public String getCpnDlvUrl() {
		return this.cpnDlvUrl;
	}

	/**
	 * @param cpnDlvUrl
	 *            the cpnDlvUrl to set
	 */
	public void setCpnDlvUrl(String cpnDlvUrl) {
		this.cpnDlvUrl = cpnDlvUrl;
	}

	/**
	 * @return the etcSeq
	 */
	public String getEtcSeq() {
		return this.etcSeq;
	}

	/**
	 * @param etcSeq
	 *            the etcSeq to set
	 */
	public void setEtcSeq(String etcSeq) {
		this.etcSeq = etcSeq;
	}

	/**
	 * @return the fixrateProdId
	 */
	public String getFixrateProdId() {
		return this.fixrateProdId;
	}

	/**
	 * @param fixrateProdId
	 *            the fixrateProdId to set
	 */
	public void setFixrateProdId(String fixrateProdId) {
		this.fixrateProdId = fixrateProdId;
	}

	/**
	 * @return the prodNm
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * @param prodNm
	 *            the prodNm to set
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return this.grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @return the paymentStartDt
	 */
	public String getPaymentStartDt() {
		return this.paymentStartDt;
	}

	/**
	 * @param paymentStartDt
	 *            the paymentStartDt to set
	 */
	public void setPaymentStartDt(String paymentStartDt) {
		this.paymentStartDt = paymentStartDt;
	}

	/**
	 * @return the paymentEndDt
	 */
	public String getPaymentEndDt() {
		return this.paymentEndDt;
	}

	/**
	 * @param paymentEndDt
	 *            the paymentEndDt to set
	 */
	public void setPaymentEndDt(String paymentEndDt) {
		this.paymentEndDt = paymentEndDt;
	}

	/**
	 * @return the afterPaymentDt
	 */
	public String getAfterPaymentDt() {
		return this.afterPaymentDt;
	}

	/**
	 * @param afterPaymentDt
	 *            the afterPaymentDt to set
	 */
	public void setAfterPaymentDt(String afterPaymentDt) {
		this.afterPaymentDt = afterPaymentDt;
	}

	/**
	 * @return the closedCd
	 */
	public String getClosedCd() {
		return this.closedCd;
	}

	/**
	 * @param closedCd
	 *            the closedCd to set
	 */
	public void setClosedCd(String closedCd) {
		this.closedCd = closedCd;
	}

	/**
	 * @return the closedDt
	 */
	public String getClosedDt() {
		return this.closedDt;
	}

	/**
	 * @param closedDt
	 *            the closedDt to set
	 */
	public void setClosedDt(String closedDt) {
		this.closedDt = closedDt;
	}

	/**
	 * @return the closedReasonCd
	 */
	public String getClosedReasonCd() {
		return this.closedReasonCd;
	}

	/**
	 * @param closedReasonCd
	 *            the closedReasonCd to set
	 */
	public void setClosedReasonCd(String closedReasonCd) {
		this.closedReasonCd = closedReasonCd;
	}

	/**
	 * @return the closedReqPathCd
	 */
	public String getClosedReqPathCd() {
		return this.closedReqPathCd;
	}

	/**
	 * @param closedReqPathCd
	 *            the closedReqPathCd to set
	 */
	public void setClosedReqPathCd(String closedReqPathCd) {
		this.closedReqPathCd = closedReqPathCd;
	}

	/**
	 * @return the prchsProdType
	 */
	public String getPrchsProdType() {
		return this.prchsProdType;
	}

	/**
	 * @param prchsProdType
	 *            the prchsProdType to set
	 */
	public void setPrchsProdType(String prchsProdType) {
		this.prchsProdType = prchsProdType;
	}

}
