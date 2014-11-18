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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.display.vo.card.PreferredCategoryRes;
import com.skplanet.storeplatform.sac.client.display.vo.card.SegmentRes;
import com.skplanet.storeplatform.sac.display.card.vo.MemberSegment;

/**
 * <p>
 * MemberSegmentTypeServiceImpl
 * </p>
 * Updated on : 2014. 11. 13 Updated by : 서대영, SK 플래닛.
 */
@Service
public class MemberSegmentTypeServiceImpl implements MemberSegmentTypeService {

	private ObjectMapper mapper = new ObjectMapper();
	
	public MemberSegmentTypeServiceImpl() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@Override
	public SegmentRes fromMemberSegmentToSegmetRes(MemberSegment vo) {
		String outsdMbrGrdCd = vo.getOutsdMbrLevelCd();
		String insdMbrGrdCd = vo.getInsdMbrLevelCd();
		String sex = vo.getSex();
		String ageClsfCd = vo.getAgeClsfCd();
		String deviceChgYn = vo.getDeviceChgYn();
		String newEntryYn = vo.getNewEntryYn();
		String mnoClsfCd = vo.getMnoCd();
		List<String> categoryBest = Arrays.asList(vo.getCategoryBest());
		
		SegmentRes res = new SegmentRes();
		res.setOutsdMbrGrdCd(outsdMbrGrdCd);
		res.setInsdMbrGrdCd(insdMbrGrdCd);
		res.setSex(sex);
		res.setAgeClsfCd(ageClsfCd);
		res.setDeviceChgYn(deviceChgYn);
		res.setNewEntryYn(newEntryYn);
		res.setMnoClsfCd(mnoClsfCd);
		res.setCategoryBest(categoryBest);
		return res;
	}

	@Override
	public List<PreferredCategoryRes> fromMemberSegmentToPreferredCategoryRes(MemberSegment memberSegment) {
		String jsonArray = memberSegment.getCategoryPrefer();
		List<PreferredCategoryRes> list;
		try {
			list = mapper.readValue(jsonArray, TypeFactory.defaultInstance().constructCollectionType(List.class, PreferredCategoryRes.class));
		} catch (Exception e) {
			return Collections.emptyList();
		}
		return list;
	}
	
}
