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

import com.skplanet.storeplatform.external.client.shopping.vo.DpCouponInfo;
import com.skplanet.storeplatform.external.client.shopping.vo.DpItemInfo;

public class CouponContainer {
	private DpCouponInfo dpCouponInfo;
	private List<DpItemInfo> dpItemlist;

	public DpCouponInfo getDpCouponInfo() {
		return this.dpCouponInfo;
	}

	public void setDpCouponInfo(DpCouponInfo dpCouponInfo) {
		this.dpCouponInfo = dpCouponInfo;
	}

	public List<DpItemInfo> getDpItemlist() {
		return this.dpItemlist;
	}

	public void setDpItemlist(List<DpItemInfo> dpItemlist) {
		this.dpItemlist = dpItemlist;
	}

}
