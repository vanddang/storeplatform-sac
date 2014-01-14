package com.skplanet.storeplatform.sac.purchase.purchase.dummy.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class DummyMember extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId; // 테넌트 ID
	private String insdUsermbrNo; // 내부 회원 번호
	private String insdDeviceId; // 내부 디바이스 ID
	private String mdn; // MDN
	private Integer age; // 연령
	private Boolean bAvailableMember; // 정상상태 회원 여부
	private Boolean bLogin; // 로그인 여부

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
	}

	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
	}

	public String getMdn() {
		return this.mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getbAvailableMember() {
		return this.bAvailableMember;
	}

	public void setbAvailableMember(Boolean bAvailableMember) {
		this.bAvailableMember = bAvailableMember;
	}

	public Boolean getbLogin() {
		return this.bLogin;
	}

	public void setbLogin(Boolean bLogin) {
		this.bLogin = bLogin;
	}
}
