/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.stat.service;

import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberReq;
import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * <p>
 * StatMemberService
 * </p>
 * Updated on : 2014. 10. 30 Updated by : 서대영, SK 플래닛.
 */
public interface StatMemberService {

	ListByMemberRes listByMember(ListByMemberReq req, SacRequestHeader header);
	
}
