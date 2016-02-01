/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.migration;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.purchase.migration.vo.PurchaseMigList;

import java.util.List;

/**
 * 
 * 구매이관정보 조회 요청 응답 VO.
 * 
 * Updated on : 2016. 1. 13. Updated by : 황민규, SK플래닛.
 */
public class PurchaseMigInformationSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private int totalCount;
	private List<PurchaseMigList> migList;

	/**
	 * Gets total count.
	 *
	 * @return the total count
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * Sets total count.
	 *
	 * @param totalCount
	 *            the total count
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * Gets mig list.
	 *
	 * @return the mig list
	 */
	public List<PurchaseMigList> getMigList() {
		return migList;
	}

	/**
	 * Sets mig list.
	 *
	 * @param migList
	 *            the mig list
	 */
	public void setMigList(List<PurchaseMigList> migList) {
		this.migList = migList;
	}
}
