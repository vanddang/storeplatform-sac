package com.skplanet.storeplatform.sac.client.product.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class ProductCommonResponse extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String errorCode; // 에러코드

	private String menuId; // 메뉴ID

	private String menuNm; // 메뉴명

	private String menuDesc; // 메뉴설명

	private String upMenuId; // 상위메뉴ID

	private String upMenuNm; // 상위메뉴명

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuNm() {
		return this.menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getMenuDesc() {
		return this.menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getUpMenuId() {
		return this.upMenuId;
	}

	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}

	public String getUpMenuNm() {
		return this.upMenuNm;
	}

	public void setUpMenuNm(String upMenuNm) {
		this.upMenuNm = upMenuNm;
	}
}
