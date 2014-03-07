package com.skplanet.storeplatform.sac.client.rest;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientError;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

public class SacRestClientApacheTest {

	private SacRestClientApache client;

	@Before
	public void setUp() {
		String host = "devspweb1.sungsu.skplanet.com/sp_sac";
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		String systemId = "S01-06001";
		this.client = new SacRestClientApache(host, authKey, systemId);
	}

	@Test
	public void testGetForObject() {
		String interfaceId = "I04000013";
		String path = "/other/feedback/listScorePaticpers/v1";

		ListScorePaticpersSacReq param = new ListScorePaticpersSacReq();
		param.setProdId("0000647637");

		try {
			ListScorePaticpersSacRes resObject = this.client.get(interfaceId, path, ListScorePaticpersSacRes.class, param);
			System.out.println("# GetForObject Data : \n" + resObject);
			assertTrue(CollectionUtils.isNotEmpty(resObject.getAvgScoreList()));
		} catch (SacRestClientException e) {
			SacRestClientError error = e.getError();
			System.out.println("# GetForObject Error : \n" + error);
			assertTrue(StringUtils.isNotEmpty(error.getCode()));
		}
	}

	@Test
	public void testPostForObject() {
		String interfaceId = "I04000003";
		String path = "/other/feedback/modify/v1";

		ModifyFeedbackSacReq body = new ModifyFeedbackSacReq();
		body.setProdId("0000059641");
		body.setUserKey("IW1023350238820110701120455");
		body.setUserId("shop_7842");
		body.setAvgScore("2");

		try {
			ModifyFeedbackSacRes resObject = this.client.post(interfaceId, path, ModifyFeedbackSacRes.class, body);
			System.out.println("# PostForObject Data : \n" + resObject);
			assertTrue(StringUtils.isNotEmpty(resObject.getProdId()));
		} catch (SacRestClientException e) {
			SacRestClientError error = e.getError();
			System.out.println("# PostForObject Error : \n" + error);
			assertTrue(StringUtils.isNotEmpty(error.getCode()));
		}

	}

	@Test
	public void testGetForMap() {
		String interfaceId = "I04000013";
		String path = "/other/feedback/listScorePaticpers/v1";

		Map<String, String> map = new HashMap<String, String>();
		map.put("prodId", "0000647637");

		try {
			ListScorePaticpersSacRes resObject = this.client.get(interfaceId, path, ListScorePaticpersSacRes.class, map);
			System.out.println("# GetForMap Data : \n" + resObject);
			assertTrue(CollectionUtils.isNotEmpty(resObject.getAvgScoreList()));
		} catch (SacRestClientException e) {
			SacRestClientError error = e.getError();
			System.out.println("# GetForMap Error : \n" + error);
			assertTrue(StringUtils.isNotEmpty(error.getCode()));
		}
	}

	@Test
	public void testPostForMap() {
		String interfaceId = "I04000003";
		String path = "/other/feedback/modify/v1";

		Map<String, String> map = new HashMap<String, String>();
		map.put("prodId", "0000059641");
		map.put("userKey", "IW1023350238820110701120455");
		map.put("userId", "shop_7842");
		map.put("avgScore", "2");

		try {
			Map<?, ?> resObject = this.client.post(interfaceId, path, Map.class, map);
			System.out.println("# PostForMap Data : \n" + resObject);
			assertTrue(StringUtils.isNotEmpty((String) resObject.get("prodId")));
		} catch (SacRestClientException e) {
			SacRestClientError error = e.getError();
			System.out.println("# PostForMap Error : \n" + error);
			assertTrue(StringUtils.isNotEmpty(error.getCode()));
		}

	}

}
