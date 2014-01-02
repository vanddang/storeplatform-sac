/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.vo;

import java.util.List;

public class CouponContainer {

	private DpCouponInfo dpCouponInfo;
	private List<DpItemInfo> dpItemlist;

	public DpCouponInfo getDpCouponInfo() {
		return this.dpCouponInfo;
	}

	public void setDpCouponInfo(DpCouponInfo dpCouponInfo) {
		this.dpCouponInfo = dpCouponInfo;
	}

	public List<DpItemInfo> getDpItemInfo() {
		return this.dpItemlist;
	}

	public void setDpItemInfo(List<DpItemInfo> dpItemInfo) {
		this.dpItemlist = dpItemInfo;
	}

}
