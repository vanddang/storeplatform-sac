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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacRes;
import com.skplanet.storeplatform.sac.display.card.vo.MemberSegment;

/**
 * <p>
 * MemberSegmentDataServiceImpl
 * </p>
 * Created on 2014. 11. 13 by 서대영, SK 플래닛.
 * Updated on 2014. 12. 03 by 서대영, SK 플래닛 : 회원 LocalSCI 연동 추가
 */
@Service
public class MemberSegmentDataServiceImpl implements MemberSegmentDataService {
	
	@Autowired
	private SearchUserSCI sci;
	
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	@Override
	public SearchUserSegmentSacRes searchUserSegment(String userKey, String deviceKey) {
		SearchUserSegmentSacReq req = new SearchUserSegmentSacReq();
		req.setUserKey(userKey);
		req.setDeviceKey(deviceKey);
		return sci.searchUserSegment(req);
	}
	
	@Override
	public MemberSegment selectMemberSegment(String tenantId, String userKey) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tenantId", tenantId);
        param.put("userKey", userKey);
		
		return commonDAO.queryForObject("MemberSegment.selectMemberSegment", param, MemberSegment.class);
	}
	
}
