package com.skplanet.storeplatform.sac.display.card.service;

import java.util.List;

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

}
