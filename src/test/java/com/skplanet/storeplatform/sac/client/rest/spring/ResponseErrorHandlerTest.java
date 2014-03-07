package com.skplanet.storeplatform.sac.client.rest.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;
import com.skplanet.storeplatform.sac.client.rest.spring.ResponseErrorHandler;


public class ResponseErrorHandlerTest {

	private ResponseErrorHandler errorHandler;

	private HttpHeaders headers;

	private ClientHttpResponse mockRes;

	@Before
	public void setUp() {
		this.errorHandler = new ResponseErrorHandler();
		this.headers = new HttpHeaders();
		this.mockRes = mock(ClientHttpResponse.class);
	}

	@Test
	public void testHasErrorWhenResultCodeIsSucc() throws IOException {
		this.headers.set("x-sac-result-code", "SUCC");

		when(this.mockRes.getHeaders()).thenReturn(this.headers);
		when(this.mockRes.getStatusCode()).thenReturn(HttpStatus.OK);

		assertFalse(this.errorHandler.hasError(this.mockRes));
		verify(this.mockRes).getHeaders();
		verify(this.mockRes).getStatusCode();
	}

	@Test
	public void testHasErrorWhenResultCodeIsFail() throws IOException {
		this.headers.set("x-sac-result-code", "FAIL");

		when(this.mockRes.getHeaders()).thenReturn(this.headers);

		assertTrue(this.errorHandler.hasError(this.mockRes));
		verify(this.mockRes).getHeaders();
	}

	@Test
	public void testHasErrorWhenStatusCodeIsNotOk() throws IOException {
		when(this.mockRes.getHeaders()).thenReturn(this.headers);
		when(this.mockRes.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

		assertTrue(this.errorHandler.hasError(this.mockRes));
		verify(this.mockRes).getHeaders();
		verify(this.mockRes).getStatusCode();
	}

	@Test(expected=SacRestClientException.class)
	public void testHandleError() throws IOException {
		this.headers.setContentType(MediaType.APPLICATION_JSON);

		when(this.mockRes.getHeaders()).thenReturn(this.headers);

		String code = "\"code\": \"SAC_SYS_ERROR\"";
		String message = "\"message\": \"System Error.\"";
		String error = "{" + code + ", " + message + "}";
		System.out.println("# error : " + error);
		InputStream body = new ByteArrayInputStream(error.getBytes());

		when(this.mockRes.getBody()).thenReturn(body);

		try {
			this.errorHandler.handleError(this.mockRes);
		} catch (SacRestClientException e) {
			System.out.println(e);
			assertEquals(e.getCode(), "SAC_SYS_ERROR");
			throw e;
		}

		verify(this.mockRes).getHeaders();
		verify(this.mockRes).getBody();
	}

}
