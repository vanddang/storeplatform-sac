package com.skplanet.storeplatform.sac.display.card.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;

import com.skplanet.storeplatform.sac.client.display.vo.card.PreferredCategoryRes;
import com.skplanet.storeplatform.sac.display.card.vo.MemberSegment;

public class MemberSegmentTypeServiceImplTest {

	private MemberSegmentTypeServiceImpl service;
	
	@Before
	public void setUp() {
		service = new MemberSegmentTypeServiceImpl();
	}
	
	@Test
	public void testFromUserSegmentToPreferredCategoryRes() {
		String jsonArray = "[{\"menuId\":\"DP03\",\"menuNm\":\"FUN\"},{\"menuId\":\"DP01\",\"menuNm\":\"게임\"},{\"menuId\":\"DP13\",\"menuNm\":\"eBook\"},{\"menuId\":\"DP14\",\"menuNm\":\"만화\"},{\"menuId\":\"DP17\",\"menuNm\":\"영화\"}]";
		System.out.println("jsonArray : \n" + jsonArray);
		MemberSegment userSegment = new MemberSegment();
		userSegment.setCategoryPrefer(jsonArray);
		List<PreferredCategoryRes> list = service.fromMemberSegmentToPreferredCategoryRes(userSegment);
		System.out.println("list : \n" + list);
	}
	
	@Test
	public void isRecentlyRegistered() {
		boolean[] expecteds = {false, true, true, true, true, true, true, true, true, false};
		
		for (int i = 0; i < expecteds.length; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, i - 8);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			String dateStr = DateFormatUtils.format(calendar, "yyyyMMdd");
			
			boolean isWithinPastWeek = MemberSegmentTypeServiceImpl.isRecentlyRegistered(dateStr);
			if (expecteds[i]) {
				assertTrue(isWithinPastWeek);
			} else {
				assertFalse(isWithinPastWeek);
			}
			System.out.println(dateStr + "=>"+ isWithinPastWeek);
		}
	}

}
