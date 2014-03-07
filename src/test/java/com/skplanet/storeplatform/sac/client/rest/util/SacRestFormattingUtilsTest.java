package com.skplanet.storeplatform.sac.client.rest.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;

public class SacRestFormattingUtilsTest {

	@Test
	public void testFormatUrl() {
		String scheme = "http";
		String host = "devspweb1.sungsu.skplanet.com/sp_sac";
		String path = "/other/feedback/listScorePaticpers/v1";
		String query = "prodId=0000059641&userId=shop_7842";

		String expectedUrl1 = "http://devspweb1.sungsu.skplanet.com/sp_sac/other/feedback/listScorePaticpers/v1?prodId=0000059641&userId=shop_7842";
		String actualUrl1 = SacRestFormattingUtils.formatUrl(scheme, host, path, query);
		System.out.println("# Url1 : " + actualUrl1);
		assertEquals(expectedUrl1, actualUrl1);

		String expectedUrl2 = "http://devspweb1.sungsu.skplanet.com/sp_sac/other/feedback/listScorePaticpers/v1";
		String actualUrl2 = SacRestFormattingUtils.formatUrl(scheme, host, path);
		System.out.println("# Url2 : " + actualUrl2);
		assertEquals(expectedUrl2, actualUrl2);
	}

	@Test
	public void testFormatQueryForObj() {
		ModifyFeedbackSacReq obj = new ModifyFeedbackSacReq();
		obj.setProdId("0000059641");
		obj.setUserKey("IW1023350238820110701120455");
		obj.setUserId("shop_7842");
		obj.setAvgScore("2");

		String expectedQuery = "prodId=0000059641&userKey=IW1023350238820110701120455&userId=shop_7842&avgScore=2";
		String actualQuery = SacRestFormattingUtils.formatQuery(obj);
		System.out.println("# QueryStringFromObj : " + actualQuery);
		assertEquals(expectedQuery, actualQuery);
	}

	@Test
	public void testFormatQueryForMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("prodId", "0000059641");
		map.put("userKey", "IW1023350238820110701120455");
		map.put("userId", "shop_7842");
		map.put("avgScore", "2");

		String actualQuery = SacRestFormattingUtils.formatQuery(map);
		System.out.println("# QueryStringFromMap : " + actualQuery);
		assertTrue(actualQuery.contains("prodId=0000059641"));
		// The order of parameters varies for maps.
	}

}
