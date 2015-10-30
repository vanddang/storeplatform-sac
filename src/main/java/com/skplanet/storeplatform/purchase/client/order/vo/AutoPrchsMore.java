/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.vo;

import com.skplanet.storeplatform.purchase.client.common.vo.AutoPrchs;

/**
 * 
 * 자동결제 CommonVo 확장 VO
 * 
 * Updated on : 2014. 11. 20. Updated by : 이승택, nTels.
 */
public class AutoPrchsMore extends AutoPrchs {
	private static final long serialVersionUID = 1L;

	private Integer autoPrchsLastPeriod;

	/**
	 * @return the autoPrchsLastPeriod
	 */
	public Integer getAutoPrchsLastPeriod() {
		return this.autoPrchsLastPeriod;
	}

	/**
	 * @param autoPrchsLastPeriod
	 *            the autoPrchsLastPeriod to set
	 */
	public void setAutoPrchsLastPeriod(Integer autoPrchsLastPeriod) {
		this.autoPrchsLastPeriod = autoPrchsLastPeriod;
	}

}
