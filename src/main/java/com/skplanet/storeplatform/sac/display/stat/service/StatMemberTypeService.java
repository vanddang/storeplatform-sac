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

import java.util.List;

import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberReq;
import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.stat.vo.StatLike;

/**
 * <p>
 * StatMemberTypeService
 * </p>
 * Updated on : 2014. 11. 04 Updated by : 서대영, SK 플래닛.
 */
public interface StatMemberTypeService {
	
	StatLike fromReqToVo(ListByMemberReq req, SacRequestHeader header);

	ListByMemberRes fromVotoRes(List<StatLike> voList, ListByMemberReq req, SacRequestHeader header);
	
}
