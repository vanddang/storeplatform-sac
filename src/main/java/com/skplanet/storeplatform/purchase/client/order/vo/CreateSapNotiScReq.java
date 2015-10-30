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

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.purchase.client.common.vo.SapNoti;

/**
 * 
 * SAP 결제완료Noti 이력 생성 요청 VO
 * 
 * Updated on : 2014. 11. 20. Updated by : 이승택, nTels.
 */
public class CreateSapNotiScReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private List<SapNoti> sapNotiList;

	public CreateSapNotiScReq() {
	}

	public CreateSapNotiScReq(List<SapNoti> sapNotiList) {
		this.sapNotiList = sapNotiList;
	}

	/**
	 * @return the sapNotiList
	 */
	public List<SapNoti> getSapNotiList() {
		return this.sapNotiList;
	}

	/**
	 * @param sapNotiList
	 *            the sapNotiList to set
	 */
	public void setSapNotiList(List<SapNoti> sapNotiList) {
		this.sapNotiList = sapNotiList;
	}

}
