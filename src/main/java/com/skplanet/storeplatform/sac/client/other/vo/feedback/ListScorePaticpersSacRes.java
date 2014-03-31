/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.feedback;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ListScorePaticpersSacRes Value Object
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListScorePaticpersSacRes extends CommonInfo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 평점별 리스트.
	 */
	private List<AvgScore> avgScoreList;

	/**
	 * @return List<AvgScore>
	 */
	public List<AvgScore> getAvgScoreList() {
		return this.avgScoreList;
	}

	/**
	 * @param avgScoreList
	 *            avgScoreList
	 */
	public void setAvgScoreList(List<AvgScore> avgScoreList) {
		this.avgScoreList = avgScoreList;
	}

}
