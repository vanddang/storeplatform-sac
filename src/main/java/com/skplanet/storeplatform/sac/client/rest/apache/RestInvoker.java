package com.skplanet.storeplatform.sac.client.rest.apache;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class RestInvoker {
	
	private static HttpClient client;

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();		 
		cm.setMaxTotal(200); // connections in total 
		cm.setDefaultMaxPerRoute(200); // concurrent connections per given route
		
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(20 * 1000) // socket timeout = 20sec
				.setConnectTimeout(2 * 1000) // connect timeout = 5sec
			    .build();
		
		client = HttpClients.custom()
				.setConnectionManager(cm)
				.setDefaultRequestConfig(config)
				.build();
	}

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
