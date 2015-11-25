/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.service;

import java.util.List;

import com.skplanet.storeplatform.sac.client.display.vo.card.PreferredCategoryRes;
import com.skplanet.storeplatform.sac.client.display.vo.card.SegmentRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacRes;
import com.skplanet.storeplatform.sac.display.card.vo.MemberSegment;

/**
 * <p>
 * MemberSegmentTypeService
 * </p>
 * Updated on : 2014. 11. 13 Updated by : 서대영, SK 플래닛.
 */
public interface MemberSegmentTypeService {

	void bindFromSci(SegmentRes segmentRes, SearchUserSegmentSacRes segmentFromSci);

	void bindFromDb(SegmentRes segmentRes, MemberSegment segmentFromDb);

	List<PreferredCategoryRes> fromMemberSegmentToPreferredCategoryRes(MemberSegment memberSegment);	
	
}
