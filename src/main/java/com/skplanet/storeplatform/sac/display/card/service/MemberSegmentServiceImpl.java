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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.display.vo.card.MemberSegmentReq;
import com.skplanet.storeplatform.sac.client.display.vo.card.MemberSegmentRes;
import com.skplanet.storeplatform.sac.client.display.vo.card.PreferredCategoryRes;
import com.skplanet.storeplatform.sac.client.display.vo.card.SegmentRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.card.vo.MemberSegment;

/**
 * <p>
 * MemberSegmentServiceImpl
 * </p>
 * Updated on : 2014. 11. 13 Updated by : 서대영, SK 플래닛.
 */
@Service
public class MemberSegmentServiceImpl implements MemberSegmentService {

	@Autowired
	private MemberSegmentDataService dataService;
	@Autowired
	private MemberSegmentTypeService typeService;
	
	@Override
	public MemberSegmentRes findMemberSegment(MemberSegmentReq req, SacRequestHeader header) {
		String tenantId = header.getTenantHeader().getTenantId();
		String userKey = req.getUserKey();
		String deviceKey = req.getDeviceKey();
		
		MemberSegmentRes res = new MemberSegmentRes();
		
		SegmentRes segmentRes = new SegmentRes();
		SearchUserSegmentSacRes segmentFromSci = dataService.searchUserSegment(userKey, deviceKey);
		typeService.bindFromSci(segmentRes, segmentFromSci);
		
		MemberSegment segmentFromDb = dataService.selectMemberSegment(tenantId, userKey);
		if (segmentFromDb != null) {
			typeService.bindFromDb(segmentRes, segmentFromDb);
			List<PreferredCategoryRes> preferredCategoryList = typeService.fromMemberSegmentToPreferredCategoryRes(segmentFromDb);	
			res.setPreferredCategoryList(preferredCategoryList);
		}
		
		if (StringUtils.isBlank(segmentRes.getOutsdMbrGrdCd())) {
			segmentRes.setOutsdMbrGrdCd("2"); // Default 외부회원레벨 = "gold"
		}
		
		res.setSegment(segmentRes);
		return res;
	}

}
