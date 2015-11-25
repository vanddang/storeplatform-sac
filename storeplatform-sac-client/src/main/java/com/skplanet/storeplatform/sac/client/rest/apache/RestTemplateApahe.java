package com.skplanet.storeplatform.sac.client.rest.apache;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestMethod;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestRequest;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestResponse;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestScheme;

public class RestTemplateApahe {

	private final String host;

	private final String authKey;

	private final String secret;

	private final String tenantId;

	private final String systemId;

	/**
	 * 상용키용 생성자
	 * @param host 호스트 (Domain/IP)
	 * @param authKey 인증키
	 * @param secret 비밀키
	 * @param tenantId 테넌트 식별자
	 * @param systemId 시스템 식별자
	 */
	public RestTemplateApahe(String host, String authKey, String secret, String tenantId, String systemId) {
		this.host = host;
		this.authKey = authKey;
		this.secret = secret;
		this.tenantId = tenantId;
		this.systemId = systemId;
	}

	public <T> T process(SacRestScheme scheme, SacRestMethod method, String interfaceId, String path, Class<T> responseType, Object param, Object body) throws SacRestClientException {
		// 1) Format the URL.
		String url = UrlFormatter.formatUrl(scheme, this.host, path, param);

		// 2) Prepare a request.
		HttpUriRequest request = RequestComposer.prepareRequest(method, body, url);

		// 3) Set request headers.
		RequestComposer.injectHeaders(request, this.authKey, this.secret, this.tenantId, this.systemId, interfaceId);

		// 4) Call the SAC API.
		HttpResponse response = RestInvoker.invoke(request);

		// 5) Extract the response data.
		T resObj = ResponseExtractor.extractData(response, responseType);

		return resObj;
	}

	public <T> SacRestResponse<T> process(SacRestRequest sacReq, Class<T> responseType) throws SacRestClientException {
		// 0)Use the default settings if they are empty.
		this.setDefaultSettings(sacReq);

		// 1) Format the URL.
		String url = UrlFormatter.formatUrl(sacReq);

		// 2) Prepare a request.
		HttpUriRequest request = RequestComposer.prepareRequest(sacReq.getMethod(), sacReq.getBody(), url);

		// 3) Set request headers.
		RequestComposer.injectHeaders(request, sacReq);

		// 4) Call the SAC API.
		HttpResponse response = RestInvoker.invoke(request);

		// 5) Extract the response data.
		SacRestResponse<T> sacRes = ResponseExtractor.extractResponse(response, responseType);

		return sacRes;
	}

	private void setDefaultSettings(SacRestRequest request) {
		if (StringUtils.isBlank(request.getHost())) {
			request.setHost(this.host);
		}
		if (StringUtils.isBlank(request.getAuthKey())) {
			request.setAuthKey(this.authKey);
		}
		if (StringUtils.isBlank(request.getSecret())) {
			request.setSecret(this.secret);
		}
		if (StringUtils.isBlank(request.getTenantId())) {
			request.setTenantId(this.tenantId);
		}
		if (StringUtils.isBlank(request.getSystemId())) {
			request.setSystemId(this.systemId);
		}
	}

}