package com.skplanet.storeplatform.sac.client.rest;

import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

public interface SacRestClient {

	<T> T get(String interfaceId, String path, Class<T> responseType, Object param) throws SacRestClientException;

	<T> T post(String interfaceId, String path, Class<T> responseType, Object body) throws SacRestClientException;

	<T> T post(String interfaceId, String path, Class<T> responseType, Object param, Object body) throws SacRestClientException;

}
