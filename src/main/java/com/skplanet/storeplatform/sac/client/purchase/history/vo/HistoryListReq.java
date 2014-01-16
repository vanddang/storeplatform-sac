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

	private int offset;
	private int count;

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
	 * @return the insdUsermbrNo
	 */
	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	/**
	 * @param insdUsermbrNo
	 *            the insdUsermbrNo to set
	 */
	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
	}

	/**
	 * @return the insdDeviceId
	 */
	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	/**
	 * @param insdDeviceId
	 *            the insdDeviceId to set
	 */
	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
	}

	/**
	 * @return the startDt
	 */
	public String getStartDt() {
		return this.startDt;
	}

	/**
	 * @param startDt
	 *            the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return this.endDt;
	}

	/**
	 * @param endDt
	 *            the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
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
	 * @return the prchsStatusCd
	 */
	public String getPrchsStatusCd() {
		return this.prchsStatusCd;
	}

	/**
	 * @param prchsStatusCd
	 *            the prchsStatusCd to set
	 */
	public void setPrchsStatusCd(String prchsStatusCd) {
		this.prchsStatusCd = prchsStatusCd;
	}

	/**
	 * @return the frProdId
	 */
	public String getFrProdId() {
		return this.frProdId;
	}

	/**
	 * @param frProdId
	 *            the frProdId to set
	 */
	public void setFrProdId(String frProdId) {
		this.frProdId = frProdId;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
