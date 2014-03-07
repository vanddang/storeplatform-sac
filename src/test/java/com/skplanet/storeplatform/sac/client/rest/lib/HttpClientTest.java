package com.skplanet.storeplatform.sac.client.rest.lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacRes;

public class HttpClientTest {

	private final HttpClient client = HttpClientBuilder.create().build();

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testGet() throws ClientProtocolException, IOException {
		String scheme = "http";
		String host = "devspweb1.sungsu.skplanet.com/sp_sac";
		String path = "/other/feedback/listScorePaticpers/v1";

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("prodId", "0000647637"));

		String query = URLEncodedUtils.format(urlParameters, "UTF-8");
		String url = scheme + "://" + host + path + "?" + query;

		HttpGet request = new HttpGet(url);

		request.addHeader("Content-Type", "application/json;charset=UTF-8");
		request.addHeader("Accept", "application/json;charset=UTF-8");

		HttpResponse response = this.client.execute(request);

		if (this.hasError(response)) {
			System.out.println("has Error");
			InputStream in = response.getEntity().getContent();

			ErrorInfo resObj = this.mapper.readValue(in, ErrorInfo.class);
			System.out.println(resObj);
		} else {
			System.out.println("no Error");
			InputStream in = response.getEntity().getContent();
			// String resTxt = IOUtils.toString(in);
			// System.out.println(resTxt);

			ListScorePaticpersSacRes resObj = this.mapper.readValue(in, ListScorePaticpersSacRes.class);
			System.out.println(resObj);
		}

	}

	@Test
	public void testPost() throws ClientProtocolException, IOException {
		String scheme = "http";
		String host = "devspweb1.sungsu.skplanet.com/sp_sac";
		String path = "/other/feedback/modify/v1";

		String url = scheme + "://" + host + path;

		HttpPost request = new HttpPost(url);

		request.addHeader("Content-Type", "application/json;charset=UTF-8");
		request.addHeader("Accept", "application/json;charset=UTF-8");

		ModifyFeedbackSacReq req = new ModifyFeedbackSacReq();
		req.setProdId("0000059641");
		req.setUserKey("IW1023350238820110701120455");
		req.setUserId("shop_7842");
		req.setAvgScore("2");

		String reqTxt = this.mapper.writeValueAsString(req);
		System.out.println("# reqTxt : " + reqTxt);

		request.setEntity(new StringEntity(reqTxt));

		HttpResponse response = this.client.execute(request);

		if (this.hasError(response)) {
			System.out.println("has Error");
			InputStream in = response.getEntity().getContent();

			ErrorInfo resObj = this.mapper.readValue(in, ErrorInfo.class);
			System.out.println(resObj);
		} else {
			System.out.println("no Error");
			InputStream in = response.getEntity().getContent();
			// String resTxt = IOUtils.toString(in);
			// System.out.println(resTxt);

			ModifyFeedbackSacRes resObj = this.mapper.readValue(in, ModifyFeedbackSacRes.class);
			System.out.println(resObj);
		}

	}

	private boolean hasError(HttpResponse response) {
		try {
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode);

			if (statusCode >= 300) {
				return true;
			}

			Header resultHeader = response.getHeaders("x-sac-result-code")[0];
			System.out.println(resultHeader.getValue());

			if (!StringUtils.equalsIgnoreCase(resultHeader.getValue(), "SUCC")) {
				return true;
			}
		} catch (Exception e) {
			return true;
		}

		return false;
	}



}
