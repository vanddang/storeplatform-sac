package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 사용자별 정책 공통 VO
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class IndividualPolicyInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/**
	 * 정책 코드.
	 */
	private String policyCode;
	/**
	 * 확인할 정책의 key값.
	 */
	private String key;
	/**
	 * key값에 대한 value.
	 */
	private String value;

	/** 정책_적용_값. */
	private String limitAmount; // LIMT_AMT

	/** 이전_정책_적용_값. */
	private String preLimitAmount; // PRE_LIMT_AMT

	/** 정책_적용_값. */
	private String permissionType; // PMT_TYPE

	/** 사용여부 (Y/N). */
	private String isUsed; // USE_YN

	/**
	 * @return the policyCode
	 */
	public String getPolicyCode() {
		return this.policyCode;
	}

	/**
	 * @param policyCode
	 *            the policyCode to set
	 */
	public void setPolicyCode(String policyCode) {
		this.policyCode = policyCode;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the limitAmount
	 */
	public String getLimitAmount() {
		return this.limitAmount;
	}

	/**
	 * @param limitAmount
	 *            the limitAmount to set
	 */
	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}

	/**
	 * @return the preLimitAmount
	 */
	public String getPreLimitAmount() {
		return this.preLimitAmount;
	}

	/**
	 * @param preLimitAmount
	 *            the preLimitAmount to set
	 */
	public void setPreLimitAmount(String preLimitAmount) {
		this.preLimitAmount = preLimitAmount;
	}

	/**
	 * @return the permissionType
	 */
	public String getPermissionType() {
		return this.permissionType;
	}

	/**
	 * @param permissionType
	 *            the permissionType to set
	 */
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	/**
	 * @return the isUsed
	 */
	public String getIsUsed() {
		return this.isUsed;
	}

	/**
	 * @param isUsed
	 *            the isUsed to set
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

}
