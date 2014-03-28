package com.skplanet.storeplatform.sac.client.rest.apache;

import java.security.SignatureException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import com.skplanet.storeplatform.framework.core.idgen.GuidGenerator;
import com.skplanet.storeplatform.framework.core.idgen.GuidGeneratorFactory;
import com.skplanet.storeplatform.sac.client.rest.util.SacRestConvertingUtils;
import com.skplanet.storeplatform.sac.runtime.acl.util.HmacSha1Util;
import com.skplanet.storeplatform.sac.runtime.acl.util.SacAuthUtil;

/**
 * HttpClient의 Request에 헤더와 바디를 삽입해주는 클래스
 *
 * Updated on : 2014. 3. 6.
 * Updated by : 서대영, SK플래닛.
 */
public class RequestComposer {

	private static GuidGenerator guidGenerator = GuidGeneratorFactory.getDefaultInstance();

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

		String guid = guidGenerator.getId();
		String timestamp = SacAuthUtil.getTimestamp();
		// String nonce = SacAuthUtil.getNonce();
		// String signature = this.getSignature(baseUrl, authKey, timestamp, nonce, secret);

		request.addHeader("x-sac-guid", guid);
		request.addHeader("x-sac-auth-timestamp", timestamp);
		// request.addHeader("x-sac-auth-nonce", nonce);
		// request.addHeader("x-sac-auth-signature", signature);

		request.addHeader("x-sac-auth-key", authKey);
		request.addHeader("x-sac-system-id", systemId);
		request.addHeader("x-sac-interface-id", interfaceId);
		// TODO: request.addHeader("x-sac-guid", guid);
	}

	private String getSignature(String baseUrl, String authKey, String timestamp, String nonce, String secret) throws SignatureException {
		//인증을 위한 URL (BaseUrl + authKey + timestamp + nonce)
		String urlForAuth = SacAuthUtil.getMessageForAuth(baseUrl, authKey, timestamp, nonce);
		// Signature 생성
		String signature = HmacSha1Util.getSignature(urlForAuth, secret);
		return signature;
	}

	public static void injectBody(HttpPost request, Object body) {
		String json = SacRestConvertingUtils.convertToJson(body);
		request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
	}

}
