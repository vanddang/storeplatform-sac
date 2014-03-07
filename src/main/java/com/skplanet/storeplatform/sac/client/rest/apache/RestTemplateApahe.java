package com.skplanet.storeplatform.sac.client.rest.apache;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

public class RestTemplateApahe {

	private final String scheme = "http";

	private final String host;

	private final String authKey;

	private final String systemId;

	public RestTemplateApahe(String host, String authKey, String systemId) {
		this.host = host;
		this.authKey = authKey;
		this.systemId = systemId;
	}

	public <T> T process(RestMethod method, String interfaceId, String path, Class<T> responseType, Object param, Object body) throws SacRestClientException {
		// 1) Format the URL.
		String url = UrlFormatter.formatUrl(this.scheme, this.host, path, param);

		// 2) Prepare a request.
		HttpUriRequest request = RequestComposer.prepareRequest(method, body, url);

		// 3) Set request headers.
		RequestComposer.injectHeaders(request, this.authKey, this.systemId, interfaceId);

		// 4) Call the SAC API.
		HttpResponse response = RestInvoker.invoke(request);

		// 5) Extract the response data.
		T resObj = ResponseExtractor.extractData(response, responseType);

		return resObj;
	}

}