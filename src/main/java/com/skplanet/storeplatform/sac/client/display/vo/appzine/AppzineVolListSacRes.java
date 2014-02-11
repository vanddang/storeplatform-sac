/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.appzine;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Appzine;

/**
 * Appzine 회차별 목록 조회 Value Object.
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AppzineVolListSacRes extends CommonInfo {

	private static final long serialVersionUID = 11123123126L;

	private CommonResponse commonResponse;
	private List<Appzine> appzineVolList;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public List<Appzine> getAppzineVolList() {
		return this.appzineVolList;
	}

	public void setAppzineVolList(List<Appzine> appzineVolList) {
		this.appzineVolList = appzineVolList;
	}

}
