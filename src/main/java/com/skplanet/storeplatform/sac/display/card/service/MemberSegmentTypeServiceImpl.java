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

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.display.vo.card.PreferredCategoryRes;
import com.skplanet.storeplatform.sac.client.display.vo.card.SegmentRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacRes;
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
	public void bindFromSci(SegmentRes segmentRes, SearchUserSegmentSacRes segmentFromSci) {
		String sex = segmentFromSci.getUserSex();
		String deviceChgYn = segmentFromSci.getIsChanged();
		String newEntryDay = segmentFromSci.getEntryDay();
		String mnoClsfCd = segmentFromSci.getUserTelecom();
		
		segmentRes.setSex(sex);
		segmentRes.setDeviceChgYn(deviceChgYn);
		String newEntryYn = isRecentlyRegistered(newEntryDay) ? "Y" : "N";
		segmentRes.setNewEntryYn(newEntryYn);
		segmentRes.setMnoClsfCd(mnoClsfCd);
	}

	@Override
	public void bindFromDb(SegmentRes segmentRes, MemberSegment segmentFromDb) {
		String outsdMbrGrdCd = segmentFromDb.getOutsdMbrLevelCd();
		String insdMbrGrdCd = segmentFromDb.getInsdMbrLevelCd();
		String sex = segmentFromDb.getSex();
		String ageClsfCd = segmentFromDb.getAgeClsfCd();
		String deviceChgYn = segmentFromDb.getDeviceChgYn();
		String newEntryYn = segmentFromDb.getNewEntryYn();
		String mnoClsfCd = segmentFromDb.getMnoCd();
		List<String> categoryBest = Arrays.asList(segmentFromDb.getCategoryBest());

		segmentRes.setOutsdMbrGrdCd(outsdMbrGrdCd);
		segmentRes.setInsdMbrGrdCd(insdMbrGrdCd);
		segmentRes.setSex(sex);
		segmentRes.setAgeClsfCd(ageClsfCd);
		segmentRes.setDeviceChgYn(deviceChgYn);
		segmentRes.setNewEntryYn(newEntryYn);
		segmentRes.setMnoClsfCd(mnoClsfCd);
		segmentRes.setCategoryBest(categoryBest);
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
	
	public static boolean isRecentlyRegistered(String dateStr) {
		Date date; 
		try {
			date = DateUtils.parseDate(dateStr, "yyyyMMdd");
		} catch (ParseException e) {
			return false;
		}
		LocalDate givenDate = new LocalDate(date);
		LocalDate currentDate = new LocalDate();
		
		int daysBetween = Days.daysBetween(givenDate, currentDate).getDays();
		if (daysBetween >= 0 && daysBetween <= 7) {
			return true;
		} else {
			return false;
		}
	}
	
}
