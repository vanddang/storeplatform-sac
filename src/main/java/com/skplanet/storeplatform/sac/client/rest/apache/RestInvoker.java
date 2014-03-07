package com.skplanet.storeplatform.sac.client.rest.apache;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestInvoker {

	private final static HttpClient client = HttpClientBuilder.create().build();

	public static HttpResponse invoke(HttpUriRequest request) {
		HttpResponse response;
		try {
			response = client.execute(request);
		} catch (Exception e) {
			throw new RuntimeException("Failed to execute HttpClient.", e);
		}
		return response;
	}

}
