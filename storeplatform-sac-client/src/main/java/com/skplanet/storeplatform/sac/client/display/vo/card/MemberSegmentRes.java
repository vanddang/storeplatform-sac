/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.card;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * UserSegmentRes
 * </p>
 * Updated on : 2014. 10. 30 Updated by : 서대영, SK 플래닛.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MemberSegmentRes extends CommonInfo {

    private static final long serialVersionUID = 1L;
    
    private SegmentRes segment;
    
    private List<PreferredCategoryRes> preferredCategoryList;

	public SegmentRes getSegment() {
		return segment;
	}

	public void setSegment(SegmentRes segment) {
		this.segment = segment;
	}

	public List<PreferredCategoryRes> getPreferredCategoryList() {
		return preferredCategoryList;
	}

	public void setPreferredCategoryList(List<PreferredCategoryRes> preferredCategoryList) {
		this.preferredCategoryList = preferredCategoryList;
	}
	
}
