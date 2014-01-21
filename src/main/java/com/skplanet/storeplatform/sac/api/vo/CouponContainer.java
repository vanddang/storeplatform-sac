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
/**
 * <pre>
 * 전처리 쿠폰 아이템 Value Object
 * </pre>
 *
 * Created on : 2014-01-02
 * Created by : 김형식, SK플래닛
 * Last Updated on : 2014-01-02
 * Last Updated by : 김형식, SK플래닛
 */
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
