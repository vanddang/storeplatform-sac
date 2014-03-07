package com.skplanet.storeplatform.sac.client.rest;

import com.skplanet.storeplatform.sac.client.rest.apache.RestMethod;
import com.skplanet.storeplatform.sac.client.rest.apache.RestTemplateApahe;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

public class SacRestClientApache implements SacRestClient {

	private final RestTemplateApahe processor;

	public SacRestClientApache(String host, String authKey, String systemId) {
		this.processor = new RestTemplateApahe(host, authKey, systemId);
	}

	@Override
	public <T> T get(String interfaceId, String path, Class<T> responseType, Object param) throws SacRestClientException {
		return this.processor.process(RestMethod.GET, interfaceId, path, responseType, param, null);
	}

	@Override
	public <T> T post(String interfaceId, String path, Class<T> responseType, Object body) throws SacRestClientException {
		return this.processor.process(RestMethod.POST, interfaceId, path, responseType, null, body);
	}

	@Override
	public <T> T post(String interfaceId, String path, Class<T> responseType, Object param, Object body) throws SacRestClientException {
		return this.processor.process(RestMethod.POST, interfaceId, path, responseType, param, body);
	}

}
