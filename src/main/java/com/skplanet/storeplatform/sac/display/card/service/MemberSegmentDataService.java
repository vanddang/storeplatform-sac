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

import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacRes;
import com.skplanet.storeplatform.sac.display.card.vo.MemberSegment;

/**
 * <p>
 * MemberSegmentDataService
 * </p>
 * Updated on : 2014. 11. 13 Updated by : 서대영, SK 플래닛.
 */
public interface MemberSegmentDataService {
	
	SearchUserSegmentSacRes searchUserSegment(String userKey, String deviceKey);

	MemberSegment selectMemberSegment(String tenantId, String userKey);
	
}
