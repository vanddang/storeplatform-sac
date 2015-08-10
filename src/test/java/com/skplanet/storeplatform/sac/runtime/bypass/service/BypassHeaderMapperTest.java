package com.skplanet.storeplatform.sac.runtime.bypass.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNull;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;

public class BypassHeaderMapperTest {

	BypassHeaderMapper mapper = new BypassHeaderMapper();
	
	@Test
	public void mapForRequestWithNoHeaders() {
		HttpHeaders sourceHeaders = new HttpHeaders();
		System.out.println("# sourceHeaders : " + sourceHeaders);
		
		HttpHeaders targetHeaders = mapper.mapForRequest(sourceHeaders);
		System.out.println("# targetHeaders : " + targetHeaders);
		
		assertThat(targetHeaders.isEmpty(), is(true));
	}

	@Test
	public void mapForRequestWithStandardHeaders() {
		HttpHeaders sourceHeaders = new HttpHeaders();
		sourceHeaders.set("Accept", "*/*");
		sourceHeaders.set("Content-Type", "application/json;charset=UTF-8");
		sourceHeaders.set("Cache-Control", "no-cache");
		sourceHeaders.set("accept-language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
		sourceHeaders.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36");
		System.out.println("# sourceHeaders : " + sourceHeaders);
		
		HttpHeaders targetHeaders = mapper.mapForRequest(sourceHeaders);
		System.out.println("# targetHeaders : " + targetHeaders);
		
		assertThat(targetHeaders.size(), is(sourceHeaders.size()));
		for (String name : sourceHeaders.keySet()) {
			assertThat(targetHeaders.getFirst(name), is(sourceHeaders.getFirst(name)));
		}
	}
	
	@Test
	public void mapForRequestWithCustomHeaders() {
		HttpHeaders sourceHeaders = new HttpHeaders();
		sourceHeaders.set("x-sac-auth-key", "S016bb3cef385cd5e438188a97c77aa234c");
		sourceHeaders.set("x-sac-tenant-id", "S01");
		sourceHeaders.set("x-sac-system-id", "S01-01002");
		sourceHeaders.set("x-sac-interface-id", "I99000004");
		System.out.println("# sourceHeaders : " + sourceHeaders);
		
		HttpHeaders targetHeaders = mapper.mapForRequest(sourceHeaders);
		System.out.println("# targetHeaders : " + targetHeaders);
		
		assertThat(targetHeaders.size(), is(sourceHeaders.size()));
		for (String name : sourceHeaders.keySet()) {
			assertThat(targetHeaders.getFirst(name), is(sourceHeaders.getFirst(name)));
		}
	}
	
	@Test
	public void mapForRequestWithUnknownHeaders() {
		HttpHeaders sourceHeaders = new HttpHeaders();
		sourceHeaders.set("xxx", "abc");
		sourceHeaders.set("yyy", "def");
		System.out.println("# sourceHeaders : " + sourceHeaders);
		
		HttpHeaders targetHeaders = mapper.mapForRequest(sourceHeaders);
		System.out.println("# targetHeaders : " + targetHeaders);
		
		assertThat(targetHeaders.isEmpty(), is(true));
	}
	
	@Test
	public void mapForResponseWithNoHeaders() {
		HttpHeaders sourceHeaders = new HttpHeaders();
		HttpServletResponse targetResposne = new MockHttpServletResponse();
		System.out.println("# sourceHeaders : " + sourceHeaders);
		
		mapper.mapForResponse(sourceHeaders, targetResposne);
		System.out.println("# targetHeaders : " + targetResposne);
		
		assertThat(targetResposne.getContentType(), nullValue());
	}
	
	@Test
	public void mapForResponseWithStandardHeaders() {
		HttpHeaders sourceHeaders = new HttpHeaders();
		sourceHeaders.set("Date", new Date().toString());
		sourceHeaders.set("Server", "Apache/2.2.24 (Unix) mod_ssl/2.2.24 OpenSSL/1.0.0-fips mod_jk/1.2.37");
		sourceHeaders.set("Content-Type", "application/json;charset=UTF-8");
		sourceHeaders.set("Content-Language", "ko");
		sourceHeaders.set("Content-Length", "5269");
		System.out.println("# sourceHeaders : " + sourceHeaders);

		HttpServletResponse targetResposne = new MockHttpServletResponse();
		mapper.mapForResponse(sourceHeaders, targetResposne);
		System.out.println("# targetHeaders : " + targetResposne);
		
		assertThat(targetResposne.getContentType(), is("application/json;charset=UTF-8"));
	}
	
	@Test
	public void mapForResponseWithCustomHeaders() {
		HttpHeaders sourceHeaders = new HttpHeaders();
		sourceHeaders.set("x-sac-auth-key", "S016bb3cef385cd5e438188a97c77aa234c");
		sourceHeaders.set("x-sac-tenant-id", "S01");
		sourceHeaders.set("x-sac-system-id", "S01-01002");
		sourceHeaders.set("x-sac-interface-id", "I99000004");
		sourceHeaders.set("x-sac-guid", "1437530063049-5655cb1e-2046-46af-ae8c-2cb2875382eb");
		sourceHeaders.set("x-sac-result-code", "SUCC");
		System.out.println("# sourceHeaders : " + sourceHeaders);

		HttpServletResponse targetResposne = new MockHttpServletResponse();
		mapper.mapForResponse(sourceHeaders, targetResposne);
		System.out.println("# targetHeaders : " + targetResposne);
	}
	
	@Test
	public void mapForResponseWithUnknownHeaders() {
		HttpHeaders sourceHeaders = new HttpHeaders();
		sourceHeaders.set("x-ec-auth-key", "S016bb3cef385cd5e438188a97c77aa234c");
		sourceHeaders.set("x-ec-tenant-id", "S01");
		sourceHeaders.set("x-ec-system-id", "S01-01002");
		sourceHeaders.set("x-ec-interface-id", "I99000004");
		sourceHeaders.set("x-ec-guid", "1437530063049-5655cb1e-2046-46af-ae8c-2cb2875382eb");
		sourceHeaders.set("x-ec-result-code", "SUCC");
		System.out.println("# sourceHeaders : " + sourceHeaders);
		
		HttpServletResponse targetResposne = new MockHttpServletResponse();
		mapper.mapForResponse(sourceHeaders, targetResposne);
		System.out.println("# targetHeaders : " + targetResposne);
	}

}
