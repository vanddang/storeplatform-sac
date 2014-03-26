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
		String authKey = "S010c629d2e2fb303ce5664c1ab3bc40a2e"; // TestKey (2014-03-24 ~ 2014-04-23)
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

	/**
	 * 한글이 깨지는 현상 재현을 위한 테스트
	 */
	@Test
	@SuppressWarnings("rawtypes")
	public void testForSms() {
		String interfaceId = "I04000019";
		String path = "/other/message/sms/send/v1";
		Class<Map> responseType = Map.class;

		Map<String, String> map = new HashMap<String, String>();
		map.put("campaignId", "");
		map.put("resvDtTm", "20140310180505");
		map.put("callbackUrl", "");
		map.put("interfaceId", "");
		map.put("recvMdn", "01085226051");
		map.put("teleSvcId", "0");
		map.put("carrier", "SKT");
		map.put("srcId", "OR00401");
		map.put("sendMdn", "01085226051");
		map.put("msg", "문자 메시지 전송 테스트");
		map.put("sendOrder", "");

		try {
			Map<?, ?> resObject = this.client.post(interfaceId, path, responseType, map);
			System.out.println("# PostForMap Data : \n" + resObject);
			assertTrue(StringUtils.isNotEmpty((String) resObject.get("resultStatus")));
		} catch (SacRestClientException e) {
			SacRestClientError error = e.getError();
			System.out.println("# PostForMap Error : \n" + error);
			assertTrue(StringUtils.isNotEmpty(error.getCode()));
		}

	}

}
