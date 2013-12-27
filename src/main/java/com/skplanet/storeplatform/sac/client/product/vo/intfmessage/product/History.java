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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message History Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
public class History extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Update update;

	public Update getUpdate() {
		return this.update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
