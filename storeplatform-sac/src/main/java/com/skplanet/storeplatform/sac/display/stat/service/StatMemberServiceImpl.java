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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberReq;
import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.stat.vo.StatLike;

/**
 * <p>
 * MemberSegmentServiceImpl
 * </p>
 * Updated on : 2014. 10. 30 Updated by : 서대영, SK 플래닛.
 */
@Service
public class StatMemberServiceImpl implements StatMemberService {
	
	@Autowired
	private StatMemberDataService dataService;
	
	@Autowired
	private StatMemberTypeService typeService;

	@Override
	public ListByMemberRes listByMember(ListByMemberReq req, SacRequestHeader header) {
		StatLike statLike = typeService.fromReqToVo(req, header);
		List<StatLike> voList = dataService.selectStatLikeList(statLike);
		ListByMemberRes res = typeService.fromVotoRes(voList, req, header);
		return res;
	}

}
