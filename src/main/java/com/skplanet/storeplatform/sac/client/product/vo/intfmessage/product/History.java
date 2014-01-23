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
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message History Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class History extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Update> update;

	/**
	 * @return List<Update>
	 */
	public List<Update> getUpdate() {
		return this.update;
	}

	/**
	 * @param update
	 *            update
	 */
	public void setUpdate(List<Update> update) {
		this.update = update;
	}

}
