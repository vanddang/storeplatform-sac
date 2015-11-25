package com.skplanet.storeplatform.sac.client.rest.apache;

import java.security.SignatureException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import com.skplanet.storeplatform.framework.core.idgen.GuidGenerator;
import com.skplanet.storeplatform.framework.core.idgen.GuidGeneratorFactory;
import com.skplanet.storeplatform.sac.client.rest.constant.SacRestClientConstants;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;
import com.skplanet.storeplatform.sac.client.rest.util.SacRestConvertingUtils;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestMethod;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestRequest;
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

	public static HttpUriRequest prepareRequest(SacRestMethod method, Object body, String url) {
		HttpUriRequest request;
		if (method == SacRestMethod.POST) {
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

	public static void injectHeaders(HttpUriRequest request, SacRestRequest sacReq) {
		injectHeaders(request, sacReq.getAuthKey(), sacReq.getSecret(), sacReq.getTenantId(), sacReq.getSystemId(), sacReq.getInterfaceId());

		if (sacReq.getDevice() != null) {
			request.addHeader(SacRestClientConstants.HEADER_DEVICE, sacReq.getDevice());
		}

		if (sacReq.getNetwork() != null) {
			request.addHeader(SacRestClientConstants.HEADER_NETWORK, sacReq.getNetwork());
		}
	}

	public static void injectHeaders(HttpUriRequest request, String authKey, String secret, String tenantId, String systemId, String interfaceId) {
		request.addHeader("Content-Type", "application/json;charset=UTF-8");
		request.addHeader("Accept", "application/json;charset=UTF-8");

		request.addHeader(SacRestClientConstants.HEADER_AUTH_KEY, authKey);
		request.addHeader(SacRestClientConstants.HEADER_TENANT_ID, tenantId);
		request.addHeader(SacRestClientConstants.HEADER_SYSTEM_ID, systemId);
		request.addHeader(SacRestClientConstants.HEADER_INTERFACE_ID, interfaceId);

		String guid = guidGenerator.getId();
		request.addHeader(SacRestClientConstants.HEADER_GUID, guid);

		String timestamp = SacAuthUtil.getTimestamp();
		request.addHeader(SacRestClientConstants.HEADER_AUTH_TIMESTAMP, timestamp);

		String nonce = SacAuthUtil.getNonce();
		request.addHeader(SacRestClientConstants.HEADER_AUTH_NONCE, nonce);

		if (StringUtils.isNotBlank(secret)) {
			String baseUrl = request.getURI().toString();
			String signature = getSignature(baseUrl, authKey, secret, timestamp, nonce);
			request.addHeader(SacRestClientConstants.HEADER_AUTH_SIGNATURE, signature);
		}
	}

	private static String getSignature(String baseUrl, String authKey, String secret, String timestamp, String nonce) {
		// 인증을 위한 URL (BaseUrl + authKey + timestamp + nonce)
		String urlForAuth = SacAuthUtil.getMessageForAuth(baseUrl, authKey, timestamp, nonce);
		// Signature 생성
		String signature;
		try {
			signature = HmacSha1Util.getSignature(urlForAuth, secret);
		} catch (SignatureException e) {
			throw new SacRestClientException("Failed to generate the signature for " + urlForAuth + ".");
		}
		return signature;
	}

	public static void injectBody(HttpPost request, Object body) {
		String json = SacRestConvertingUtils.convertToJson(body);
		request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
	}

}
