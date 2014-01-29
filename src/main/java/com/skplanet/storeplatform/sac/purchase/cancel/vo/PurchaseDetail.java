/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매 취소를 위한 구매 상세 정보 VO.
 * 
 * Updated on : 2014. 1. 20. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseDetail extends CommonInfo {

	private static final long serialVersionUID = 1L;
	
	private String tenantId;
	private String prchsId;
	private Integer prchsDtlId;
	private String useTenantId;
	private String useInsdUsermbrNo;
	private String useInsdDeviceId;
	private String prchsDt;
	private Double totAmt;
	private String prchsReqPathCd;
	private String clientIp;
	private String sendInsdUsermbrNo;
	private String sendInsdDeviceId;
	private String recvDt;
	private String recvConfPathCd;
	private String prodId;
	private Double prodAmt;
	private Integer prodQty;
	private String prodGrpCd;
	private String statusCd;
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
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;
	private String resvCol01;
	private String resvCol02;
	private String resvCol03;
	private String resvCol04;
	private String resvCol05;
	private String prchsProdType;
	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return prchsId;
	}
	/**
	 * @param prchsId the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}
	/**
	 * @return the prchsDtlId
	 */
	public Integer getPrchsDtlId() {
		return prchsDtlId;
	}
	/**
	 * @param prchsDtlId the prchsDtlId to set
	 */
	public void setPrchsDtlId(Integer prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}
	/**
	 * @return the useTenantId
	 */
	public String getUseTenantId() {
		return useTenantId;
	}
	/**
	 * @param useTenantId the useTenantId to set
	 */
	public void setUseTenantId(String useTenantId) {
		this.useTenantId = useTenantId;
	}
	/**
	 * @return the useInsdUsermbrNo
	 */
	public String getUseInsdUsermbrNo() {
		return useInsdUsermbrNo;
	}
	/**
	 * @param useInsdUsermbrNo the useInsdUsermbrNo to set
	 */
	public void setUseInsdUsermbrNo(String useInsdUsermbrNo) {
		this.useInsdUsermbrNo = useInsdUsermbrNo;
	}
	/**
	 * @return the useInsdDeviceId
	 */
	public String getUseInsdDeviceId() {
		return useInsdDeviceId;
	}
	/**
	 * @param useInsdDeviceId the useInsdDeviceId to set
	 */
	public void setUseInsdDeviceId(String useInsdDeviceId) {
		this.useInsdDeviceId = useInsdDeviceId;
	}
	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return prchsDt;
	}
	/**
	 * @param prchsDt the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}
	/**
	 * @return the totAmt
	 */
	public Double getTotAmt() {
		return totAmt;
	}
	/**
	 * @param totAmt the totAmt to set
	 */
	public void setTotAmt(Double totAmt) {
		this.totAmt = totAmt;
	}
	/**
	 * @return the prchsReqPathCd
	 */
	public String getPrchsReqPathCd() {
		return prchsReqPathCd;
	}
	/**
	 * @param prchsReqPathCd the prchsReqPathCd to set
	 */
	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}
	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}
	/**
	 * @param clientIp the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	/**
	 * @return the sendInsdUsermbrNo
	 */
	public String getSendInsdUsermbrNo() {
		return sendInsdUsermbrNo;
	}
	/**
	 * @param sendInsdUsermbrNo the sendInsdUsermbrNo to set
	 */
	public void setSendInsdUsermbrNo(String sendInsdUsermbrNo) {
		this.sendInsdUsermbrNo = sendInsdUsermbrNo;
	}
	/**
	 * @return the sendInsdDeviceId
	 */
	public String getSendInsdDeviceId() {
		return sendInsdDeviceId;
	}
	/**
	 * @param sendInsdDeviceId the sendInsdDeviceId to set
	 */
	public void setSendInsdDeviceId(String sendInsdDeviceId) {
		this.sendInsdDeviceId = sendInsdDeviceId;
	}
	/**
	 * @return the recvDt
	 */
	public String getRecvDt() {
		return recvDt;
	}
	/**
	 * @param recvDt the recvDt to set
	 */
	public void setRecvDt(String recvDt) {
		this.recvDt = recvDt;
	}
	/**
	 * @return the recvConfPathCd
	 */
	public String getRecvConfPathCd() {
		return recvConfPathCd;
	}
	/**
	 * @param recvConfPathCd the recvConfPathCd to set
	 */
	public void setRecvConfPathCd(String recvConfPathCd) {
		this.recvConfPathCd = recvConfPathCd;
	}
	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return prodId;
	}
	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	/**
	 * @return the prodAmt
	 */
	public Double getProdAmt() {
		return prodAmt;
	}
	/**
	 * @param prodAmt the prodAmt to set
	 */
	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}
	/**
	 * @return the prodQty
	 */
	public Integer getProdQty() {
		return prodQty;
	}
	/**
	 * @param prodQty the prodQty to set
	 */
	public void setProdQty(Integer prodQty) {
		this.prodQty = prodQty;
	}
	/**
	 * @return the prodGrpCd
	 */
	public String getProdGrpCd() {
		return prodGrpCd;
	}
	/**
	 * @param prodGrpCd the prodGrpCd to set
	 */
	public void setProdGrpCd(String prodGrpCd) {
		this.prodGrpCd = prodGrpCd;
	}
	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return statusCd;
	}
	/**
	 * @param statusCd the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	/**
	 * @return the useStartDt
	 */
	public String getUseStartDt() {
		return useStartDt;
	}
	/**
	 * @param useStartDt the useStartDt to set
	 */
	public void setUseStartDt(String useStartDt) {
		this.useStartDt = useStartDt;
	}
	/**
	 * @return the useExprDt
	 */
	public String getUseExprDt() {
		return useExprDt;
	}
	/**
	 * @param useExprDt the useExprDt to set
	 */
	public void setUseExprDt(String useExprDt) {
		this.useExprDt = useExprDt;
	}
	/**
	 * @return the hidingYn
	 */
	public String getHidingYn() {
		return hidingYn;
	}
	/**
	 * @param hidingYn the hidingYn to set
	 */
	public void setHidingYn(String hidingYn) {
		this.hidingYn = hidingYn;
	}
	/**
	 * @return the cancelReqPathCd
	 */
	public String getCancelReqPathCd() {
		return cancelReqPathCd;
	}
	/**
	 * @param cancelReqPathCd the cancelReqPathCd to set
	 */
	public void setCancelReqPathCd(String cancelReqPathCd) {
		this.cancelReqPathCd = cancelReqPathCd;
	}
	/**
	 * @return the cancelDt
	 */
	public String getCancelDt() {
		return cancelDt;
	}
	/**
	 * @param cancelDt the cancelDt to set
	 */
	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
	}
	/**
	 * @return the cpnPublishCd
	 */
	public String getCpnPublishCd() {
		return cpnPublishCd;
	}
	/**
	 * @param cpnPublishCd the cpnPublishCd to set
	 */
	public void setCpnPublishCd(String cpnPublishCd) {
		this.cpnPublishCd = cpnPublishCd;
	}
	/**
	 * @return the cpnDlvUrl
	 */
	public String getCpnDlvUrl() {
		return cpnDlvUrl;
	}
	/**
	 * @param cpnDlvUrl the cpnDlvUrl to set
	 */
	public void setCpnDlvUrl(String cpnDlvUrl) {
		this.cpnDlvUrl = cpnDlvUrl;
	}
	/**
	 * @return the prchsCaseCd
	 */
	public String getPrchsCaseCd() {
		return prchsCaseCd;
	}
	/**
	 * @param prchsCaseCd the prchsCaseCd to set
	 */
	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
	}
	/**
	 * @return the rePrchsPmtYn
	 */
	public String getRePrchsPmtYn() {
		return rePrchsPmtYn;
	}
	/**
	 * @param rePrchsPmtYn the rePrchsPmtYn to set
	 */
	public void setRePrchsPmtYn(String rePrchsPmtYn) {
		this.rePrchsPmtYn = rePrchsPmtYn;
	}
	/**
	 * @return the dwldStartDt
	 */
	public String getDwldStartDt() {
		return dwldStartDt;
	}
	/**
	 * @param dwldStartDt the dwldStartDt to set
	 */
	public void setDwldStartDt(String dwldStartDt) {
		this.dwldStartDt = dwldStartDt;
	}
	/**
	 * @return the dwldExprDt
	 */
	public String getDwldExprDt() {
		return dwldExprDt;
	}
	/**
	 * @param dwldExprDt the dwldExprDt to set
	 */
	public void setDwldExprDt(String dwldExprDt) {
		this.dwldExprDt = dwldExprDt;
	}
	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}
	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return regDt;
	}
	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	/**
	 * @return the updId
	 */
	public String getUpdId() {
		return updId;
	}
	/**
	 * @param updId the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	/**
	 * @return the updDt
	 */
	public String getUpdDt() {
		return updDt;
	}
	/**
	 * @param updDt the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}
	/**
	 * @return the resvCol01
	 */
	public String getResvCol01() {
		return resvCol01;
	}
	/**
	 * @param resvCol01 the resvCol01 to set
	 */
	public void setResvCol01(String resvCol01) {
		this.resvCol01 = resvCol01;
	}
	/**
	 * @return the resvCol02
	 */
	public String getResvCol02() {
		return resvCol02;
	}
	/**
	 * @param resvCol02 the resvCol02 to set
	 */
	public void setResvCol02(String resvCol02) {
		this.resvCol02 = resvCol02;
	}
	/**
	 * @return the resvCol03
	 */
	public String getResvCol03() {
		return resvCol03;
	}
	/**
	 * @param resvCol03 the resvCol03 to set
	 */
	public void setResvCol03(String resvCol03) {
		this.resvCol03 = resvCol03;
	}
	/**
	 * @return the resvCol04
	 */
	public String getResvCol04() {
		return resvCol04;
	}
	/**
	 * @param resvCol04 the resvCol04 to set
	 */
	public void setResvCol04(String resvCol04) {
		this.resvCol04 = resvCol04;
	}
	/**
	 * @return the resvCol05
	 */
	public String getResvCol05() {
		return resvCol05;
	}
	/**
	 * @param resvCol05 the resvCol05 to set
	 */
	public void setResvCol05(String resvCol05) {
		this.resvCol05 = resvCol05;
	}
	/**
	 * @return the prchsProdType
	 */
	public String getPrchsProdType() {
		return prchsProdType;
	}
	/**
	 * @param prchsProdType the prchsProdType to set
	 */
	public void setPrchsProdType(String prchsProdType) {
		this.prchsProdType = prchsProdType;
	}
	
	

}
