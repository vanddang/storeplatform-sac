/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.feature;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.FeatureVodProductProto;

/**
 * Feature 상품 (영화, 방송) 카테고리 조회 Request Value Object.
 * 
 * Updated on : 2013. 12. 23. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(FeatureVodProductProto.FeatureVodProductRequest.class)
public class FeatureVodProductRequest extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodGradeCd; // 상품등급코드

	private String drm; // DRM 지원 구분

	private String hdv; // HDV 지원 구분

	private String imageCd; // 이미지코드

	private String menuId; // 메뉴ID

	private String listId; // 리스트ID

	private String orderedBy; // 정렬순서

	private String tenantId; // 테넌트ID

	private String systemId; // 시스템ID

	private int offset; // offset

	private int count; // count

	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	public String getDrm() {
		return this.drm;
	}

	public void setDrm(String drm) {
		this.drm = drm;
	}

	public String getHdv() {
		return this.hdv;
	}

	public void setHdv(String hdv) {
		this.hdv = hdv;
	}

	public String getImageCd() {
		return this.imageCd;
	}

	public void setImageCd(String imageCd) {
		this.imageCd = imageCd;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getOrderedBy() {
		return this.orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
