/* Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.SegmentInfo;

/**
 * <p>
 * SegmentInfo
 * </p>
 * Updated on : 2014. 10. 30 Updated by : 서대영, SK 플래닛.
 */
public class SegmentAndPreferredCategoryList extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private SegmentInfo segment;
	
	private List<PreferredCategory> preferredCategoryList;

	public SegmentInfo getSegment() {
		return segment;
	}

	public void setSegment(SegmentInfo segment) {
		this.segment = segment;
	}

	public List<PreferredCategory> getPreferredCategoryList() {
		return preferredCategoryList;
	}

	public void setPreferredCategoryList(List<PreferredCategory> preferredCategoryList) {
		this.preferredCategoryList = preferredCategoryList;
	}
	
}
