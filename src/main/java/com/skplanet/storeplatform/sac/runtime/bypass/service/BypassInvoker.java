package com.skplanet.storeplatform.sac.runtime.bypass.service;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BypassInvoker {
	
	private static final String DEFAULT_CONTENT_TYPE = "application/json; charset=UTF-8";
	
	private BypassHeaderMapper headerMapper = new BypassHeaderMapper();
	
	private RestTemplate restTemplate;

	@Qualifier("bypassStoreplatformRestTemplate")
	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ResponseEntity<String> invoke(URI uri, HttpMethod method, HttpHeaders requestHeaders, String requestBody, HttpServletResponse response) {
		HttpEntity<String> bypassRequestEntity = handleRequest(requestHeaders, requestBody);
		ResponseEntity<String> bypassResponseEntity = restTemplate.exchange(uri, method, bypassRequestEntity, String.class);
		ResponseEntity<String> responseEntity = handleResponse(bypassResponseEntity, response);
		return responseEntity;
	}
	
	private HttpEntity<String> handleRequest(HttpHeaders requestHeaders, String requestBody) {
		HttpHeaders bypassRequestHeaders = headerMapper.mapForRequest(requestHeaders);
		if (!bypassRequestHeaders.containsKey("Content-Type")) {
			bypassRequestHeaders.set("Content-Type", DEFAULT_CONTENT_TYPE);
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestBody, bypassRequestHeaders);
		return requestEntity;
	}
	
	private ResponseEntity<String> handleResponse(ResponseEntity<String> bypassResponseEntity, HttpServletResponse response) {
		HttpHeaders bypassResponseHeaders = bypassResponseEntity.getHeaders();
		headerMapper.mapForResponse(bypassResponseHeaders, response);
		ResponseEntity<String> newResponseEntity = new ResponseEntity<String>(bypassResponseEntity.getBody(), HttpStatus.OK);
		return newResponseEntity;
	}

}
