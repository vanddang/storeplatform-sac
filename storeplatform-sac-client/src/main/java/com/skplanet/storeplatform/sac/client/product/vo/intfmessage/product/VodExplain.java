/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message Vod.VodExplain Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class VodExplain extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String saleDateInfo; // BP가 직접 등록하는 방송일/개봉년도 정보
	private String text; // 날짜

	/**
	 * @return String
	 */
	public String getSaleDateInfo() {
		return this.saleDateInfo;
	}

	/**
	 * @param saleDateInfo
	 *            saleDateInfo
	 */
	public void setSaleDateInfo(String saleDateInfo) {
		this.saleDateInfo = saleDateInfo;
	}

	/**
	 * @return String
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * @param text
	 *            text
	 */
	public void setText(String text) {
		this.text = text;
	}

}
