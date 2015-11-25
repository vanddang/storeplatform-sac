package com.skplanet.storeplatform.sac.client.purchase.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매내역 조회 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class PurchaseHeaderSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	// tenant
	private String tenantId;
	private String systemId;
	private String langCd;

	// device
	private String model;
	private String dpi;
	private String resolution;
	private String os;
	private String pkg;
	private String svc;

	// network
	private String type;
	private String operator;
	private String simOperator;

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
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the dpi
	 */
	public String getDpi() {
		return this.dpi;
	}

	/**
	 * @param dpi
	 *            the dpi to set
	 */
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	/**
	 * @return the resolution
	 */
	public String getResolution() {
		return this.resolution;
	}

	/**
	 * @param resolution
	 *            the resolution to set
	 */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return this.os;
	}

	/**
	 * @param os
	 *            the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the pkg
	 */
	public String getPkg() {
		return this.pkg;
	}

	/**
	 * @param pkg
	 *            the pkg to set
	 */
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	/**
	 * @return the svc
	 */
	public String getSvc() {
		return this.svc;
	}

	/**
	 * @param svc
	 *            the svc to set
	 */
	public void setSvc(String svc) {
		this.svc = svc;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return this.operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the simOperator
	 */
	public String getSimOperator() {
		return this.simOperator;
	}

	/**
	 * @param simOperator
	 *            the simOperator to set
	 */
	public void setSimOperator(String simOperator) {
		this.simOperator = simOperator;
	}

}
