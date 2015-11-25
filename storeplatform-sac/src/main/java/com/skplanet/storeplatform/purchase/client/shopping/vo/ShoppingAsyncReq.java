/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.shopping.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 쿠폰 발급 비동기 응답
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : nTels_yjw, nTels.
 */
public class ShoppingAsyncReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private List<ShoppingAsyncListSc> publish;

	/**
	 * @return the publish
	 */
	public List<ShoppingAsyncListSc> getPublish() {
		return this.publish;
	}

	/**
	 * @param publish
	 *            the publish to set
	 */
	public void setPublish(List<ShoppingAsyncListSc> publish) {
		this.publish = publish;
	}

}
