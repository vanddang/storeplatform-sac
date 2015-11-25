package com.skplanet.storeplatform.sac.client.rest;

import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestRequest;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestResponse;

// @Component
public class SacRestClientSpring implements SacRestClient {

	@Override
	public <T> T get(String interfaceId, String path, Class<T> responseType, Object param) throws SacRestClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T post(String interfaceId, String path, Class<T> responseType, Object body) throws SacRestClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T post(String interfaceId, String path, Class<T> responseType, Object param, Object body) throws SacRestClientException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> SacRestResponse<T> exchange(SacRestRequest request, Class<T> responseType) throws SacRestClientException {
		throw new UnsupportedOperationException();
	}

}
