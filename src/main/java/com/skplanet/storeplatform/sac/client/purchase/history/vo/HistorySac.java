package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import java.io.Serializable;
import java.util.HashMap;

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
	private String prchsId;
	private String prchsDtlId;
	private String useTenantId;
	private String useUserKey;
	private String useDeviceKey;
	private String recvTenantId;
	private String recvUserKey;
	private String recvDeviceKey;
	private String prchsDt;
	private String totAmt;
	private String sendUserKey;
	private String sendDeviceKey;
	private String recvDt;
	private String recvConfPathCd;
	private String prchsReqPathCd;
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
	private String dwldStartDt;
	private String dwldExprDt;
	private String cpnPublishCd;
	private String cpnDlvUrl;
	private String useFixrateProdId;
	private String prchsProdType;
	private String drmYn;
	private String alarmYn;
	private String permitDeviceYn;
	private String cpnAddinfo; // 쇼핑쿠폰_추가정보
	private String cpnBizProdSeq; // 쇼핑쿠폰_biz상품_순번
	private String cpnBizOrderNo; // 쇼핑쿠폰_biz상품_주문번호

	// 자동결제정보
	private String paymentStartDt; // 결제시작일시
	private String paymentEndDt; // 결제종료일시
	private String afterPaymentDt; // 이후결제일시
	private String prchsTme; // 회차
	private String closedCd; // 해지코드
	private String closedDt; // 해지일시
	private String closedReasonCd; // 해지사유코드
	private String closedReqPathCd; // 해지요청경로

	// 예비컬럼
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;

	private HashMap<String, Object> productInfo;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @return the prchsReqPathCd
	 */
	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	/**
	 * @param prchsReqPathCd
	 *            the prchsReqPathCd to set
	 */
	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
	 * @return the useUserKey
	 */
	public String getUseUserKey() {
		return this.useUserKey;
	}

	/**
	 * @param useUserKey
	 *            the useUserKey to set
	 */
	public void setUseUserKey(String useUserKey) {
		this.useUserKey = useUserKey;
	}

	/**
	 * @return the useDeviceKey
	 */
	public String getUseDeviceKey() {
		return this.useDeviceKey;
	}

	/**
	 * @param useDeviceKey
	 *            the useDeviceKey to set
	 */
	public void setUseDeviceKey(String useDeviceKey) {
		this.useDeviceKey = useDeviceKey;
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
	 * @return the recvUserKey
	 */
	public String getRecvUserKey() {
		return this.recvUserKey;
	}

	/**
	 * @param recvUserKey
	 *            the recvUserKey to set
	 */
	public void setRecvUserKey(String recvUserKey) {
		this.recvUserKey = recvUserKey;
	}

	/**
	 * @return the recvDeviceKey
	 */
	public String getRecvDeviceKey() {
		return this.recvDeviceKey;
	}

	/**
	 * @param recvDeviceKey
	 *            the recvDeviceKey to set
	 */
	public void setRecvDeviceKey(String recvDeviceKey) {
		this.recvDeviceKey = recvDeviceKey;
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
	 * @return the sendUserKey
	 */
	public String getSendUserKey() {
		return this.sendUserKey;
	}

	/**
	 * @param sendUserKey
	 *            the sendUserKey to set
	 */
	public void setSendUserKey(String sendUserKey) {
		this.sendUserKey = sendUserKey;
	}

	/**
	 * @return the sendDeviceKey
	 */
	public String getSendDeviceKey() {
		return this.sendDeviceKey;
	}

	/**
	 * @param sendDeviceKey
	 *            the sendDeviceKey to set
	 */
	public void setSendDeviceKey(String sendDeviceKey) {
		this.sendDeviceKey = sendDeviceKey;
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
	 * @return the useFixrateProdId
	 */
	public String getUseFixrateProdId() {
		return this.useFixrateProdId;
	}

	/**
	 * @param useFixrateProdId
	 *            the useFixrateProdId to set
	 */
	public void setUseFixrateProdId(String useFixrateProdId) {
		this.useFixrateProdId = useFixrateProdId;
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

	/**
	 * @return the resvCol01
	 */
	public String getResvCol01() {
		return this.resvCol01;
	}

	/**
	 * @param resvCol01
	 *            the resvCol01 to set
	 */
	public void setResvCol01(String resvCol01) {
		this.resvCol01 = resvCol01;
	}

	/**
	 * @return the resvCol02
	 */
	public String getResvCol02() {
		return this.resvCol02;
	}

	/**
	 * @param resvCol02
	 *            the resvCol02 to set
	 */
	public void setResvCol02(String resvCol02) {
		this.resvCol02 = resvCol02;
	}

	/**
	 * @return the resvCol03
	 */
	public String getResvCol03() {
		return this.resvCol03;
	}

	/**
	 * @param resvCol03
	 *            the resvCol03 to set
	 */
	public void setResvCol03(String resvCol03) {
		this.resvCol03 = resvCol03;
	}

	/**
	 * @return the resvCol04
	 */
	public String getResvCol04() {
		return this.resvCol04;
	}

	/**
	 * @param resvCol04
	 *            the resvCol04 to set
	 */
	public void setResvCol04(String resvCol04) {
		this.resvCol04 = resvCol04;
	}

	/**
	 * @return the resvCol05
	 */
	public String getResvCol05() {
		return this.resvCol05;
	}

	/**
	 * @param resvCol05
	 *            the resvCol05 to set
	 */
	public void setResvCol05(String resvCol05) {
		this.resvCol05 = resvCol05;
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
	 * @return the drmYn
	 */
	public String getDrmYn() {
		return this.drmYn;
	}

	/**
	 * @param drmYn
	 *            the drmYn to set
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	/**
	 * @return the alarmYn
	 */
	public String getAlarmYn() {
		return this.alarmYn;
	}

	/**
	 * @param alarmYn
	 *            the alarmYn to set
	 */
	public void setAlarmYn(String alarmYn) {
		this.alarmYn = alarmYn;
	}

	/**
	 * @return the permitDeviceYn
	 */
	public String getPermitDeviceYn() {
		return this.permitDeviceYn;
	}

	/**
	 * @param permitDeviceYn
	 *            the permitDeviceYn to set
	 */
	public void setPermitDeviceYn(String permitDeviceYn) {
		this.permitDeviceYn = permitDeviceYn;
	}

	/**
	 * @return the productInfo
	 */
	public HashMap<String, Object> getProductInfo() {
		return this.productInfo;
	}

	/**
	 * @param productInfo
	 *            the productInfo to set
	 */
	public void setProductInfo(HashMap<String, Object> productInfo) {
		this.productInfo = productInfo;
	}

	/**
	 * @return the cpnAddinfo
	 */
	public String getCpnAddinfo() {
		return this.cpnAddinfo;
	}

	/**
	 * @param cpnAddinfo
	 *            the cpnAddinfo to set
	 */
	public void setCpnAddinfo(String cpnAddinfo) {
		this.cpnAddinfo = cpnAddinfo;
	}

	/**
	 * @return the cpnBizProdSeq
	 */
	public String getCpnBizProdSeq() {
		return this.cpnBizProdSeq;
	}

	/**
	 * @param cpnBizProdSeq
	 *            the cpnBizProdSeq to set
	 */
	public void setCpnBizProdSeq(String cpnBizProdSeq) {
		this.cpnBizProdSeq = cpnBizProdSeq;
	}

	/**
	 * @return the cpnBizOrderNo
	 */
	public String getCpnBizOrderNo() {
		return this.cpnBizOrderNo;
	}

	/**
	 * @param cpnBizOrderNo
	 *            the cpnBizOrderNo to set
	 */
	public void setCpnBizOrderNo(String cpnBizOrderNo) {
		this.cpnBizOrderNo = cpnBizOrderNo;
	}

}
