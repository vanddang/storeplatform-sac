package com.skplanet.storeplatform.sac.client.rest.apache;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import com.skplanet.storeplatform.sac.client.rest.util.SacRestConvertingUtils;

public class RequestComposer {

	public static HttpUriRequest prepareRequest(RestMethod method, Object body, String url) {
		HttpUriRequest request;
		if (method == RestMethod.POST) {
			HttpPost postRequest = new HttpPost(url);
			// In case of POST, set the request body.
			RequestComposer.injectBody(postRequest, body);
			request = postRequest;
		} else {
			HttpGet getRequest = new HttpGet(url);
			request = getRequest;
		}
		return request;
	}

	public static void injectHeaders(HttpUriRequest request, String authKey, String systemId, String interfaceId) {
		request.addHeader("Content-Type", "application/json;charset=UTF-8");
		request.addHeader("Accept", "application/json;charset=UTF-8");

		request.addHeader("x-sac-auth-key", authKey);
		request.addHeader("x-sac-system-id", systemId);
		request.addHeader("x-sac-interface-id", interfaceId);
		// TODO: request.addHeader("x-sac-guid", guid);
	}

	public static void injectBody(HttpPost request, Object body) {
		String json = SacRestConvertingUtils.convertToJson(body);

		try {
			request.setEntity(new StringEntity(json));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("HttpClient Error", e);
		}
	}

}
