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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
		String deviceChgYn = segmentFromSci.getIsChanged(); // deviceKey가 들어왔을 때만 내려줌 
		String newEntryDay = segmentFromSci.getEntryDay();
		String userBirthDay = segmentFromSci.getUserBirthDay();
		
		segmentRes.setSex(sex);
		segmentRes.setDeviceChgYn(deviceChgYn);
		String newEntryYn = isRecentlyRegistered(newEntryDay) ? "Y" : "N";
		segmentRes.setNewEntryYn(newEntryYn);
		String ageClsfCd = getAgeClsfCd(userBirthDay);
		segmentRes.setAgeClsfCd(ageClsfCd);
	}

	@Override
	public void bindFromDb(SegmentRes segmentRes, MemberSegment segmentFromDb) {
		String outsdMbrGrdCd = segmentFromDb.getOutsdMbrLevelCd();
		String insdMbrGrdCd = segmentFromDb.getInsdMbrLevelCd();
		List<String> categoryBest = Arrays.asList(segmentFromDb.getCategoryBest());

		segmentRes.setOutsdMbrGrdCd(outsdMbrGrdCd);
		segmentRes.setInsdMbrGrdCd(insdMbrGrdCd);
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
	
	/*	
		유형
		코드
		만 14세 미만
		14
		만 14세 ~ 18세
		18
		만 19세 ~ 24세
		24
		만 25세 ~ 29세
		29
		만 30세 ~ 39세
		39
		만 40세 ~ 49세
		49
		만 50세 이상
		50
	 */
	public String getAgeClsfCd(String userBirthDay) {
		if (StringUtils.isBlank(userBirthDay)) {
			return null;
		}
		
		int age = getAge(userBirthDay);
		if (age < 14)				return "13";
		if (age >= 14 && age <= 18)	return "18";
		if (age >= 19 && age <= 24) return "24";
		if (age >= 25 && age <= 29) return "29";
		if (age >= 30 && age <= 39)	return "39";
		if (age >= 40 && age <= 49) return "49";
		if (age > 50)				return "50";
		return null;
	}
	
	public static int getAge(String yyyyMMdd) {
		LocalDate birthDay;
		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
			birthDay = LocalDate.parse(yyyyMMdd, formatter);
		} catch (IllegalArgumentException e) {
			return -1;
		}
		
		LocalDate today = LocalDate.now();
		Years years = Years.yearsBetween(birthDay, today);
		return years.getYears();
	}
	
}
