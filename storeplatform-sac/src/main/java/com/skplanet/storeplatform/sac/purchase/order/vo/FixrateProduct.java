/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 정액제 상품 정보
 * 
 * Updated on : 2014. 3. 4. Updated by : 이승택, nTels.
 */
public class FixrateProduct extends CommonInfo {
	private static final long serialVersionUID = 201403041L;

	private String fixrateProdId; // 정액제 상품 ID
	private String drmYn; // DRM 여부 (에피소드 상품의 대여/소장 기준)
	private String usePeriodUnitCd; // 이용기간 단위 (에피소드 상품의 대여/소장 기준)
	private String usePeriod; // 이용기간 값 (에피소드 상품의 대여/소장 기준)
	private String cmpxProdClsfCd; // 정액제 상품 분류 코드: OR004301-정액권, OR004302-시리즈패스, OR004303-전권소장, OR004304-전권대여

	/**
	 * @return the fixrateProdId
	 */
	public String getFixrateProdId() {
		return this.fixrateProdId;
	}

	/**
	 * @param fixrateProdId
	 *            the fixrateProdId to set
	 */
	public void setFixrateProdId(String fixrateProdId) {
		this.fixrateProdId = fixrateProdId;
	}

	/**
	 * @return the drmYn
	 */
	public String getDrmYn() {
		return this.drmYn;
	}

	/**
	 * @param drmYn
	 *            the drmYn to set
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * @param usePeriodUnitCd
	 *            the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * @return the usePeriod
	 */
	public String getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * @return the cmpxProdClsfCd
	 */
	public String getCmpxProdClsfCd() {
		return this.cmpxProdClsfCd;
	}

	/**
	 * @param cmpxProdClsfCd
	 *            the cmpxProdClsfCd to set
	 */
	public void setCmpxProdClsfCd(String cmpxProdClsfCd) {
		this.cmpxProdClsfCd = cmpxProdClsfCd;
	}

}
