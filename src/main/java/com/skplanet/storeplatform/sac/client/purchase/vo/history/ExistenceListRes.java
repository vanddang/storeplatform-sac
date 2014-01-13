/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class ExistenceListRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ExistenceRes> existenceRes;

	/**
	 * @return the existenceRes
	 */
	public List<ExistenceRes> getExistenceRes() {
		return this.existenceRes;
	}

	/**
	 * @param existenceRes
	 *            the existenceRes to set
	 */
	public void setExistenceRes(List<ExistenceRes> existenceRes) {
		this.existenceRes = existenceRes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
