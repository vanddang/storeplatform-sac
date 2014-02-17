package com.skplanet.storeplatform.sac.client.display.vo.category;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CategoryWebtoonSeriesSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "필수 파라미터 입니다.")
	private String channelId;
	private String menuId;
	private String orderedBy;
	private Integer offset = 1; // offset
	private Integer count = 20; // count
	private String tenantId; // 테넌트ID
	private String langCd; // 언어코드
	private String deviceModelCd; // 디바이스 모델 코드
	private String topMenuId; // TOP 메뉴 아이디

	// TODO osm1021 dummy data가 필요없어지면 삭제할것
	private String dummy;

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getOffset() {
		return this.offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

}
