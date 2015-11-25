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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;

/**
 * Interface Message Preview Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Preview extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Source> sourceList;

	/**
	 * @return the sourceList
	 */
	public List<Source> getSourceList() {
		return this.sourceList;
	}

	/**
	 * @param sourceList
	 *            the sourceList to set
	 */
	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}
}
