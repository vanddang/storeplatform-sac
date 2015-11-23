package com.skplanet.storeplatform.purchase.client.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.io.Serializable;

/**
 * 
 * 선물수신처리 컴포넌트 응답.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class GiftConfirmScRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String recvDt; // 선물수신일시
	private String prodId; // 상품 아이디
	private String prchsId; // 구매ID
	private String resultYn; // update 성공여부
	private int prchsDtlId; // 구매ID
	private String prchsReqPathCd; // 구매상태
	private String prchsCaseCd; // 구매상태
	private int prodAmt; // 구매상태
	private String useStartDt; // 이용 시작 일시
	private String useExprDt; // 이용 만료 일시
	private String dwldStartDt; // 다운로드 시작 일시
	private String dwldExprDt; // 다운로드 만료 일시
	private String cpnPublishCd; // 구매상태
	private int prodQty; // 구매상태
	private String recvConfPathCd; // 구매상태
	private String sendInsdUsermbrNo; // 구매상태
	private String sendInsdDeviceId; // 구매상태
	private String useInsdDeviceId;
	private String tenantProdGrpCd; // 테넌트 그룹 코드

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
	 * @return the resultYn
	 */
	public String getResultYn() {
		return this.resultYn;
	}

	/**
	 * @param resultYn
	 *            the resultYn to set
	 */
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}

	/**
	 * @return the prchsDtlId
	 */
	public int getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(int prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
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
	 * @return the prodAmt
	 */
	public int getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(int prodAmt) {
		this.prodAmt = prodAmt;
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
	 * @return the prodQty
	 */
	public int getProdQty() {
		return this.prodQty;
	}

	/**
	 * @param prodQty
	 *            the prodQty to set
	 */
	public void setProdQty(int prodQty) {
		this.prodQty = prodQty;
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
	 * Gets dwld start dt.
	 *
	 * @return the dwld start dt
	 */
	public String getDwldStartDt() {
		return dwldStartDt;
	}

	/**
	 * Sets dwld start dt.
	 *
	 * @param dwldStartDt
	 *            the dwld start dt
	 */
	public void setDwldStartDt(String dwldStartDt) {
		this.dwldStartDt = dwldStartDt;
	}

	/**
	 * Gets tenant prod grp cd.
	 *
	 * @return the tenant prod grp cd
	 */
	public String getTenantProdGrpCd() {
		return tenantProdGrpCd;
	}

	/**
	 * Sets tenant prod grp cd.
	 *
	 * @param tenantProdGrpCd
	 *            the tenant prod grp cd
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}
}
