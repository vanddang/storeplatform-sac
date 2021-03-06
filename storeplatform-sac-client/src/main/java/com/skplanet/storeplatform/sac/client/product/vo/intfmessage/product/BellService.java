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
 * Interface Message BellService Value Object.
 * 
 * Updated on : 2014. 3. 6. Updated by : 이승훈, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BellService extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Service().
	 */
	public BellService() {
	}

	/**
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            type
	 */
	public BellService(String name, String type) {
		this.name = name;
		this.type = type;
	}

	private String name; // 서비스 정보 (bell | ring | mp3)
	private String type; // 서비스 지원 or 제한 여부 (support, restict)

	/**
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
