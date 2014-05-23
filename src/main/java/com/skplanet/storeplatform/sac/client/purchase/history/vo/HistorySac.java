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
	private String useDeviceId;
	private String prchsDt;
	private String totAmt;
	private String sendUserKey;
	private String sendDeviceKey;
	private String sendDeviceId;
	private String recvDt;
	private String recvConfPathCd;
	private String tenantProdGrpCd;
	private String prodId;
	private String prodAmt;
	private String prodQty;
	private String spcCpnAmt;
	private String statusCd;
	private String useStartDt;
	private String useExprDt;
	private String prchsReqPathCd;
	private String hidingYn;
	private String cancelReqPathCd;
	private String cancelDt;
	private String prchsProdType;
	private String prchsCaseCd;
	private String dwldStartDt;
	private String dwldExprDt;
	private String cpnPublishCd;
	private String cpnDlvUrl;
	private String cpnAddinfo;
	private String cpnBizProdSeq;
	private String cpnBizOrderNo;
	private String useFixrateProdId;
	private String drmYn;
	private String alarmYn;
	private String currencyCd;
	private String tid;
	private String txId;
	private String parentProdId;
	private String partChrgVer;
	private String partChrgProdNm;
	private String rnBillCd;
	private String infoUseFee;
	private String cid;
	private String contentsClsf;
	private String contentsType;
	private String prchsType;
	private String timbreClsf;
	private String timbreSctn;
	private String menuId;
	private String genreClsfCd;
	private String permitDeviceYn;
	private String paymentStartDt;
	private String paymentEndDt;
	private String afterPaymentDt;
	private String autoPaymentStatusCd;
	private String closedDt;
	private String closedReasonCd;
	private String closedReqPathCd;
	private String prchsTme;
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
	 * @return the useDeviceId
	 */
	public String getUseDeviceId() {
		return this.useDeviceId;
	}

	/**
	 * @param useDeviceId
	 *            the useDeviceId to set
	 */
	public void setUseDeviceId(String useDeviceId) {
		this.useDeviceId = useDeviceId;
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
	 * @return the sendDeviceId
	 */
	public String getSendDeviceId() {
		return this.sendDeviceId;
	}

	/**
	 * @param sendDeviceId
	 *            the sendDeviceId to set
	 */
	public void setSendDeviceId(String sendDeviceId) {
		this.sendDeviceId = sendDeviceId;
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
	 * @return the currencyCd
	 */
	public String getCurrencyCd() {
		return this.currencyCd;
	}

	/**
	 * @param currencyCd
	 *            the currencyCd to set
	 */
	public void setCurrencyCd(String currencyCd) {
		this.currencyCd = currencyCd;
	}

	/**
	 * @return the tid
	 */
	public String getTid() {
		return this.tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * @return the txId
	 */
	public String getTxId() {
		return this.txId;
	}

	/**
	 * @param txId
	 *            the txId to set
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}

	/**
	 * @return the parentProdId
	 */
	public String getParentProdId() {
		return this.parentProdId;
	}

	/**
	 * @param parentProdId
	 *            the parentProdId to set
	 */
	public void setParentProdId(String parentProdId) {
		this.parentProdId = parentProdId;
	}

	/**
	 * @return the partChrgVer
	 */
	public String getPartChrgVer() {
		return this.partChrgVer;
	}

	/**
	 * @param partChrgVer
	 *            the partChrgVer to set
	 */
	public void setPartChrgVer(String partChrgVer) {
		this.partChrgVer = partChrgVer;
	}

	/**
	 * @return the partChrgProdNm
	 */
	public String getPartChrgProdNm() {
		return this.partChrgProdNm;
	}

	/**
	 * @param partChrgProdNm
	 *            the partChrgProdNm to set
	 */
	public void setPartChrgProdNm(String partChrgProdNm) {
		this.partChrgProdNm = partChrgProdNm;
	}

	/**
	 * @return the rnBillCd
	 */
	public String getRnBillCd() {
		return this.rnBillCd;
	}

	/**
	 * @param rnBillCd
	 *            the rnBillCd to set
	 */
	public void setRnBillCd(String rnBillCd) {
		this.rnBillCd = rnBillCd;
	}

	/**
	 * @return the infoUseFee
	 */
	public String getInfoUseFee() {
		return this.infoUseFee;
	}

	/**
	 * @param infoUseFee
	 *            the infoUseFee to set
	 */
	public void setInfoUseFee(String infoUseFee) {
		this.infoUseFee = infoUseFee;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return this.cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the contentsClsf
	 */
	public String getContentsClsf() {
		return this.contentsClsf;
	}

	/**
	 * @param contentsClsf
	 *            the contentsClsf to set
	 */
	public void setContentsClsf(String contentsClsf) {
		this.contentsClsf = contentsClsf;
	}

	/**
	 * @return the contentsType
	 */
	public String getContentsType() {
		return this.contentsType;
	}

	/**
	 * @param contentsType
	 *            the contentsType to set
	 */
	public void setContentsType(String contentsType) {
		this.contentsType = contentsType;
	}

	/**
	 * @return the prchsType
	 */
	public String getPrchsType() {
		return this.prchsType;
	}

	/**
	 * @param prchsType
	 *            the prchsType to set
	 */
	public void setPrchsType(String prchsType) {
		this.prchsType = prchsType;
	}

	/**
	 * @return the timbreClsf
	 */
	public String getTimbreClsf() {
		return this.timbreClsf;
	}

	/**
	 * @param timbreClsf
	 *            the timbreClsf to set
	 */
	public void setTimbreClsf(String timbreClsf) {
		this.timbreClsf = timbreClsf;
	}

	/**
	 * @return the timbreSctn
	 */
	public String getTimbreSctn() {
		return this.timbreSctn;
	}

	/**
	 * @param timbreSctn
	 *            the timbreSctn to set
	 */
	public void setTimbreSctn(String timbreSctn) {
		this.timbreSctn = timbreSctn;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the genreClsfCd
	 */
	public String getGenreClsfCd() {
		return this.genreClsfCd;
	}

	/**
	 * @param genreClsfCd
	 *            the genreClsfCd to set
	 */
	public void setGenreClsfCd(String genreClsfCd) {
		this.genreClsfCd = genreClsfCd;
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
	 * @return the autoPaymentStatusCd
	 */
	public String getAutoPaymentStatusCd() {
		return this.autoPaymentStatusCd;
	}

	/**
	 * @param autoPaymentStatusCd
	 *            the autoPaymentStatusCd to set
	 */
	public void setAutoPaymentStatusCd(String autoPaymentStatusCd) {
		this.autoPaymentStatusCd = autoPaymentStatusCd;
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
	 * @return the spcCpnAmt
	 */
	public String getSpcCpnAmt() {
		return this.spcCpnAmt;
	}

	/**
	 * @param spcCpnAmt
	 *            the spcCpnAmt to set
	 */
	public void setSpcCpnAmt(String spcCpnAmt) {
		this.spcCpnAmt = spcCpnAmt;
	}

}
