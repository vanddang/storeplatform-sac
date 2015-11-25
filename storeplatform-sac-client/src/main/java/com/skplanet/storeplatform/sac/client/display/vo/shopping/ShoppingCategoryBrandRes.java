/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.shopping;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.GroupLayout;

/**
 * <pre>
 * 쇼핑 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class ShoppingCategoryBrandRes extends CommonInfo {
	/**
	 * 
	 */
	private CommonResponse commonResponse;
	private static final long serialVersionUID = 1L;
	private List<GroupLayout> groupLayoutList;

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}
	/**
	 * @return the groupLayoutList
	 */
	public List<GroupLayout> getGroupLayoutList() {
		return groupLayoutList;
	}

	/**
	 * @param groupLayoutList the groupLayoutList to set
	 */
	public void setGroupLayoutList(List<GroupLayout> groupLayoutList) {
		this.groupLayoutList = groupLayoutList;
	}

	

}
