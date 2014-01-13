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

	private String endDate;
	private String regDate;
	private String regID;
	private String rightProfileCode;
	private String sellerKey;
	private String sellerRate;
	private String startDate;
	private String tenantID;
	private String tenantRate;
	private String updateDate;
	private String updateID;

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegID() {
		return this.regID;
	}

	public void setRegID(String regID) {
		this.regID = regID;
	}

	public String getRightProfileCode() {
		return this.rightProfileCode;
	}

	public void setRightProfileCode(String rightProfileCode) {
		this.rightProfileCode = rightProfileCode;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getSellerRate() {
		return this.sellerRate;
	}

	public void setSellerRate(String sellerRate) {
		this.sellerRate = sellerRate;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTenantID() {
		return this.tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getTenantRate() {
		return this.tenantRate;
	}

	public void setTenantRate(String tenantRate) {
		this.tenantRate = tenantRate;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateID() {
		return this.updateID;
	}

	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}

}
