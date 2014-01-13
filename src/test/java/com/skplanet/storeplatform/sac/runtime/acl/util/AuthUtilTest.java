package com.skplanet.storeplatform.sac.runtime.acl.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class AuthUtilTest {

	@Test
	public void getTimeStamp() {
		String timestamp = AuthUtil.getTimestamp();
		System.out.println("timestamp="+timestamp);
		assertNotNull(timestamp);
	}

	@Test
	public void getNonce() {
		String nonce = AuthUtil.getNonce();
		System.out.println("nonce="+nonce);
		assertNotNull(nonce);
	}

	/**
	 * addParameter - 파라미터가 있는 경우
	 * 정상 : URL&param=value
	 */
	@Test
	public void addParameter1() {
		String url = "http://store.skplanet.com/sac?dummy=1";
		String name = "auth";
		String value = "test";
		String result = AuthUtil.addParameter(url, name, value);
		System.out.println("addParameter1 result="+result);
		assertThat(result, is(url+"&"+name+"="+value));
	}

	/**
	 * addParameter - 파라미터가 없는 경우
	 * 정상 : URL?param=value
	 */
	@Test
	public void addParameter2() {
		String url = "http://store.skplanet.com/sac";
		String name = "auth";
		String value = "test";
		String result = AuthUtil.addParameter(url, name, value);
		System.out.println("addParameter2 result="+result);
		assertThat(result, is(url+"?"+name+"="+value));
	}

	@Test
	public void encodeUrl() {
		String data = "테스트";

		String encodeResult = AuthUtil.encodeUrl(data);

		String decodeResult = AuthUtil.decodeUrl(encodeResult);
		System.out.println("encodeResult="+encodeResult+", decodeResult="+decodeResult);

		assertThat(encodeResult, is("%ED%85%8C%EC%8A%A4%ED%8A%B8"));
		assertThat(decodeResult, is(data));
	}


	@Test
	public void decodeUrl() {
		String data = "%ED%85%8C%EC%8A%A4%ED%8A%B8";

		String decodeResult = AuthUtil.decodeUrl(data);
		System.out.println("decodeResult="+decodeResult);

		assertThat(decodeResult, is("테스트"));
	}


	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}



	@Test
	public void getUrlForAuth1_no_param() {
		String baseUrl = "http://store.skplanet.com/sac";
		String tenantKey = "9Coum1NDRACh2v7eoYxfaA";
		this.getUrlForAuth1(baseUrl, tenantKey);
	}

	@Test
	public void getUrlForAuth1_param() {
		String baseUrl = "http://store.skplanet.com/sac?dummy=test";
		String tenantKey = "9Coum1NDRACh2v7eoYxfaA";
		this.getUrlForAuth1(baseUrl, tenantKey);
	}


	private void getUrlForAuth1(String baseUrl, String tenantKey) {

		String urlForAuth = AuthUtil.getUrlForAuth(baseUrl, tenantKey);

		System.out.println("<getUrlForAuth1> urlForAuth="+urlForAuth);
		//timestamp, nonce 가 계속 바뀌기 때문에 성공여부 확인 불가
		//Parameter 를 parsing 하여 비교 한다.
		//urlForAuth
		//assertThat(urlForAuth, is("http://store.skplanet.com/sac?x-sac-auth-tenant-key=9Coum1NDRACh2v7eoYxfaA&x-sac-auth-timestamp=1389633599&x-sac-auth-nonce=8851706618849"));

		URL url;
		try {
			url = new URL(urlForAuth);
			System.out.println("<getUrlForAuth1> url="+url);
			assertThat(url.getHost(), is("store.skplanet.com"));

			Map<String, String> query_pairs = splitQuery(url);

			//Tenant Key 비교
			String urlTenantKey = query_pairs.get(AuthUtil.CUSTOM_HEADER_KEY_TENANTKEY);
			assertThat(urlTenantKey, is(tenantKey));

			//Timestamp, nonce 값 null 비교
			String urlTimestamp = query_pairs.get(AuthUtil.CUSTOM_HEADER_KEY_TIMESTAMP);
			assertNotNull(urlTimestamp);
			String urlNonce = query_pairs.get(AuthUtil.CUSTOM_HEADER_KEY_NONCE);
			assertNotNull(urlNonce);



		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void getUrlForAuth2_no_param() {
		String baseUrl = "http://store.skplanet.com/sac";
		String tenantKey = "9Coum1NDRACh2v7eoYxfaA";
		String timestamp = AuthUtil.getTimestamp();
		String nonce = AuthUtil.getNonce();
		this.getUrlForAuth2(baseUrl, tenantKey, timestamp, nonce);
	}

	@Test
	public void getUrlForAuth2_param() {
		String baseUrl = "http://store.skplanet.com/sac?dummy=test";
		String tenantKey = "9Coum1NDRACh2v7eoYxfaA";
		String timestamp = AuthUtil.getTimestamp();
		String nonce = AuthUtil.getNonce();
		this.getUrlForAuth2(baseUrl, tenantKey, timestamp, nonce);
	}


	private void getUrlForAuth2(String baseUrl, String tenantKey, String timestamp, String nonce) {

		String urlForAuth = AuthUtil.getUrlForAuth(baseUrl, tenantKey, timestamp, nonce);

		System.out.println("<getUrlForAuth2> urlForAuth="+urlForAuth);
		//timestamp, nonce 가 계속 바뀌기 때문에 성공여부 확인 불가
		//Parameter 를 parsing 하여 비교 한다.
		//urlForAuth
		//assertThat(urlForAuth, is("http://store.skplanet.com/sac?x-sac-auth-tenant-key=9Coum1NDRACh2v7eoYxfaA&x-sac-auth-timestamp=1389633599&x-sac-auth-nonce=8851706618849"));

		URL url;
		try {
			url = new URL(urlForAuth);
			System.out.println("<getUrlForAuth2> url="+url);
			assertThat(url.getHost(), is("store.skplanet.com"));

			Map<String, String> query_pairs = splitQuery(url);

			//Tenant Key 비교
			String urlTenantKey = query_pairs.get(AuthUtil.CUSTOM_HEADER_KEY_TENANTKEY);
			assertThat(urlTenantKey, is(tenantKey));

			//Timestamp, nonce 값 null 비교
			String urlTimestamp = query_pairs.get(AuthUtil.CUSTOM_HEADER_KEY_TIMESTAMP);
			assertThat(urlTimestamp, is(timestamp));
			String urlNonce = query_pairs.get(AuthUtil.CUSTOM_HEADER_KEY_NONCE);
			assertThat(urlNonce, is(nonce));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
