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

import com.skplanet.storeplatform.sac.client.display.vo.card.MemberSegmentReq;
import com.skplanet.storeplatform.sac.client.display.vo.card.MemberSegmentRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * <p>
 * MemberSegmentService
 * </p>
 * Updated on : 2014. 11. 13 Updated by : 서대영, SK 플래닛.
 */
public interface MemberSegmentService {

	MemberSegmentRes findMemberSegment(MemberSegmentReq req, SacRequestHeader header);
	
}
