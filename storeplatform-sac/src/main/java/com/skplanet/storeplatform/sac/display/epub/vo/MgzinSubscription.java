/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.epub.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 잡지 정기구독 정보 제공 Value Object
 *
 * Updated on : 2014. 2. 2. Updated by : 임근대, SK플래닛.
 */
public class MgzinSubscription extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String mgzinSubscripCd;
	private String usePeriodUnitCd;
	private Integer chnlPeriodAmt;
	private String dcAmt;
	public String getMgzinSubscripCd() {
		return this.mgzinSubscripCd;
	}
	public void setMgzinSubscripCd(String mgzinSubscripCd) {
		this.mgzinSubscripCd = mgzinSubscripCd;
	}
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}
	public Integer getChnlPeriodAmt() {
		return this.chnlPeriodAmt;
	}
	public void setChnlPeriodAmt(Integer chnlPeriodAmt) {
		this.chnlPeriodAmt = chnlPeriodAmt;
	}
	public String getDcAmt() {
		return this.dcAmt;
	}
	public void setDcAmt(String dcAmt) {
		this.dcAmt = dcAmt;
	}




}
