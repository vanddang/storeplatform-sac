package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 징계정보
 * 
 * Updated on : 2014. 2. 3. Updated by : 강신완, 부르칸.
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class UserMbrPnsh extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 사용자 징계처리 여부 Y/N */
	private String isRestricted;

	/* 신고 게시물 수 */
	private String restrictCount;

	/* 사용자 징계 종료일 */
	private String restrictEndDate;

	/* 징계구분 코드 */
	private String restrictId;

	/* 징계 등록자 */
	private String restrictOwner;

	/* 징계 등록일 */
	private String restrictRegisterDate;

	/* 사용자 징계 시작일 */
	private String restrictStartDate;

	/* 테넌트 ID */
	private String tenantId;

	/* 사용자 키 */
	private String userKey;

	public String getIsRestricted() {
		return this.isRestricted;
	}

	public void setIsRestricted(String isRestricted) {
		this.isRestricted = isRestricted;
	}

	public String getRestrictCount() {
		return this.restrictCount;
	}

	public void setRestrictCount(String restrictCount) {
		this.restrictCount = restrictCount;
	}

	public String getRestrictEndDate() {
		return this.restrictEndDate;
	}

	public void setRestrictEndDate(String restrictEndDate) {
		this.restrictEndDate = restrictEndDate;
	}

	public String getRestrictId() {
		return this.restrictId;
	}

	public void setRestrictId(String restrictId) {
		this.restrictId = restrictId;
	}

	public String getRestrictOwner() {
		return this.restrictOwner;
	}

	public void setRestrictOwner(String restrictOwner) {
		this.restrictOwner = restrictOwner;
	}

	public String getRestrictRegisterDate() {
		return this.restrictRegisterDate;
	}

	public void setRestrictRegisterDate(String restrictRegisterDate) {
		this.restrictRegisterDate = restrictRegisterDate;
	}

	public String getRestrictStartDate() {
		return this.restrictStartDate;
	}

	public void setRestrictStartDate(String restrictStartDate) {
		this.restrictStartDate = restrictStartDate;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
