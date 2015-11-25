package com.skplanet.storeplatform.sac.client.rest.lib;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacRes;

public class RestTemplateTest {

	private final RestTemplate restTemplate = new RestTemplate();

	@Test
	public void testGet() {
		// String authKey = "9Coum1NDRACh2v7eoYxfaA";
		// String secret = "9Coum1NDRACh2v7eoYxfaA";
		// String interfaceId = "I04000013";

		String scheme = "http";
		String host = "devspweb1.sungsu.skplanet.com/sp_sac";
		String path = "/other/feedback/listScorePaticpers/v1?prodId={prodId}";

		String url = scheme + "://" + host + path;

		HttpMethod method = HttpMethod.GET;

		ListScorePaticpersSacReq req = new ListScorePaticpersSacReq();
		req.setProdId("0000647637");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

		Class<ListScorePaticpersSacRes> responseType = ListScorePaticpersSacRes.class;

		ResponseEntity<ListScorePaticpersSacRes> responseEntitiy = this.restTemplate.exchange(url, method, requestEntity, responseType, req);

		ListScorePaticpersSacRes res = responseEntitiy.getBody();

		System.out.println(res);
	}

	@Test
	public void testPost() {
		// String authKey = "9Coum1NDRACh2v7eoYxfaA";
		// String secret = "9Coum1NDRACh2v7eoYxfaA";
		// String interfaceId = "I04000013";

		String scheme = "http";
		String host = "devspweb1.sungsu.skplanet.com/sp_sac";
		String path = "/other/feedback/modify/v1";

		String url = scheme + "://" + host + path;

		HttpMethod method = HttpMethod.POST;

		ModifyFeedbackSacReq req = new ModifyFeedbackSacReq();
		req.setProdId("0000059641");
		req.setUserKey("IW1023350238820110701120455");
		req.setUserId("shop_7842");
		req.setAvgScore("2");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<ModifyFeedbackSacReq> requestEntity = new HttpEntity<ModifyFeedbackSacReq>(req, headers);

		/*
		Class<SacRestClientError> responseType = SacRestClientError.class;

		ResponseEntity<SacRestClientError> responseEntitiy = this.restTemplate.exchange(url, method, requestEntity, responseType);

		SacRestClientError res = responseEntitiy.getBody();
		*/

		Class<ModifyFeedbackSacRes> responseType = ModifyFeedbackSacRes.class;

		ResponseEntity<ModifyFeedbackSacRes> responseEntitiy = this.restTemplate.exchange(url, method, requestEntity, responseType, req);

		ModifyFeedbackSacRes res = responseEntitiy.getBody();

		System.out.println(res);
	}


}
