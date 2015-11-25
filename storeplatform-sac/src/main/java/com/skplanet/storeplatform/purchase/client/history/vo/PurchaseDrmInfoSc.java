package com.skplanet.storeplatform.purchase.client.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매DRM정보 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class PurchaseDrmInfoSc extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prchsId;
	private String useInsdUsermbrNo;
	private String recvDt;
	private String statusCd;
	private String tenantProdGrpCd;
	private String prchsCaseCd;
	private String usePeriodSetCd;
	private String usePeriodRedateCd;
	private String usePeriodUnitCd;
	private String usePeriod;

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
	 * @return the usePeriodSetCd
	 */
	public String getUsePeriodSetCd() {
		return this.usePeriodSetCd;
	}

	/**
	 * @param usePeriodSetCd
	 *            the usePeriodSetCd to set
	 */
	public void setUsePeriodSetCd(String usePeriodSetCd) {
		this.usePeriodSetCd = usePeriodSetCd;
	}

	/**
	 * @return the usePeriodRedateCd
	 */
	public String getUsePeriodRedateCd() {
		return this.usePeriodRedateCd;
	}

	/**
	 * @param usePeriodRedateCd
	 *            the usePeriodRedateCd to set
	 */
	public void setUsePeriodRedateCd(String usePeriodRedateCd) {
		this.usePeriodRedateCd = usePeriodRedateCd;
	}

	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * @param usePeriodUnitCd
	 *            the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * @return the usePeriod
	 */
	public String getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
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

}
