package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자 부가정보,멀티미디어정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ExtraRight extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 등록 ID.
	 */
	private String regID;

	/**
	 * 멀티미디어권한 코드.
	 */
	private String rightProfileCode;

	/**
	 * 테넌트 ID.
	 */
	private String tenantID;

	/**
	 * 테넌트 정산률.
	 */
	private String tenantRate;

	/**
	 * 수정일시.
	 */
	private String updateDate;

	/**
	 * 수정 ID.
	 */
	private String updateID;

	private String sellerKey;
	private String sellerRate;
	private String regDate;
	private String startDate;
	private String endDate;

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * @param regDate
	 *            the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regID
	 */
	public String getRegID() {
		return this.regID;
	}

	/**
	 * @param regID
	 *            the regID to set
	 */
	public void setRegID(String regID) {
		this.regID = regID;
	}

	/**
	 * @return the rightProfileCode
	 */
	public String getRightProfileCode() {
		return this.rightProfileCode;
	}

	/**
	 * @param rightProfileCode
	 *            the rightProfileCode to set
	 */
	public void setRightProfileCode(String rightProfileCode) {
		this.rightProfileCode = rightProfileCode;
	}

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return the sellerRate
	 */
	public String getSellerRate() {
		return this.sellerRate;
	}

	/**
	 * @param sellerRate
	 *            the sellerRate to set
	 */
	public void setSellerRate(String sellerRate) {
		this.sellerRate = sellerRate;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the tenantID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * @param tenantID
	 *            the tenantID to set
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * @return the tenantRate
	 */
	public String getTenantRate() {
		return this.tenantRate;
	}

	/**
	 * @param tenantRate
	 *            the tenantRate to set
	 */
	public void setTenantRate(String tenantRate) {
		this.tenantRate = tenantRate;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateID
	 */
	public String getUpdateID() {
		return this.updateID;
	}

	/**
	 * @param updateID
	 *            the updateID to set
	 */
	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}

}
