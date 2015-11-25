package com.skplanet.storeplatform.sac.runtime.bypass.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import com.skplanet.storeplatform.framework.core.context.RuntimeContextConstants;

/**
 * API Bypassing 시 요청/응답 헤더 정보를 유지해주기 위한 클래스
 * (기존에서 사용하던 Spring Integration 의 HttpRequestExecutingMessageHandler 와 HeaderMapper 를 참조하여 재구성) 
 * 
 * Updated on : 2015. 7. 22.
 * Updated by : 서대영
 */
public class BypassHeaderMapper {
	
	private static final String SAC_CUSTOM_HEADER_PREFIX = "x-sac-";
	
	private static final String ACCEPT = "Accept";

	private static final String ACCEPT_CHARSET = "Accept-Charset";

	private static final String ACCEPT_ENCODING = "Accept-Encoding";

	private static final String ACCEPT_LANGUAGE = "Accept-Language";

	private static final String ACCEPT_RANGES = "Accept-Ranges";

	private static final String AGE = "Age";

	private static final String ALLOW = "Allow";

	private static final String AUTHORIZATION = "Authorization";

	private static final String CACHE_CONTROL = "Cache-Control";

	private static final String CONNECTION = "Connection";

	private static final String CONTENT_ENCODING = "Content-Encoding";

	private static final String CONTENT_LANGUAGE = "Content-Language";

	private static final String CONTENT_LENGTH = "Content-Length";

	private static final String CONTENT_LOCATION = "Content-Location";

	private static final String CONTENT_MD5 = "Content-MD5";

	private static final String CONTENT_RANGE = "Content-Range";

	private static final String CONTENT_TYPE = "Content-Type";

	public  static final String COOKIE = "Cookie";

	private static final String DATE = "Date";

	private static final String ETAG = "ETag";

	private static final String EXPECT = "Expect";

	private static final String EXPIRES = "Expires";

	private static final String FROM = "From";

	private static final String HOST = "Host";

	private static final String IF_MATCH = "If-Match";

	private static final String IF_MODIFIED_SINCE = "If-Modified-Since";

	private static final String IF_NONE_MATCH = "If-None-Match";

	private static final String IF_RANGE = "If-Range";

	private static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

	private static final String LAST_MODIFIED = "Last-Modified";

	private static final String LOCATION = "Location";

	private static final String MAX_FORWARDS = "Max-Forwards";

	private static final String PRAGMA = "Pragma";

	private static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";

	private static final String PROXY_AUTHORIZATION = "Proxy-Authorization";

	private static final String RANGE = "Range";

	private static final String REFERER = "Referer";

	private static final String REFRESH = "Refresh";

	private static final String RETRY_AFTER = "Retry-After";

	private static final String SERVER = "Server";

	public  static final String SET_COOKIE = "Set-Cookie";

	private static final String TE = "TE";

	private static final String TRAILER = "Trailer";

	private static final String UPGRADE = "Upgrade";

	private static final String USER_AGENT = "User-Agent";

	private static final String VARY = "Vary";

	private static final String VIA = "Via";

	private static final String WARNING = "Warning";

	private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
	
	private static final String[] HTTP_REQUEST_HEADER_NAMES = new String[] {
		ACCEPT,
		ACCEPT_CHARSET,
		ACCEPT_ENCODING,
		ACCEPT_LANGUAGE,
		ACCEPT_RANGES,
		AUTHORIZATION,
		CACHE_CONTROL,
		CONNECTION,
		CONTENT_LENGTH,
		CONTENT_TYPE,
		COOKIE,
		DATE,
		EXPECT,
		FROM,
		HOST,
		IF_MATCH,
		IF_MODIFIED_SINCE,
		IF_NONE_MATCH,
		IF_RANGE,
		IF_UNMODIFIED_SINCE,
		MAX_FORWARDS,
		PRAGMA,
		PROXY_AUTHORIZATION,
		RANGE,
		REFERER,
		TE,
		UPGRADE,
		USER_AGENT,
		VIA,
		WARNING
	};

	private static String[] HTTP_RESPONSE_HEADER_NAMES = new String[] {
		ACCEPT_RANGES,
		AGE,
		ALLOW,
		CACHE_CONTROL,
		CONTENT_ENCODING,
		CONTENT_LANGUAGE,
		CONTENT_LENGTH,
		CONTENT_LOCATION,
		CONTENT_MD5,
		CONTENT_RANGE,
		CONTENT_TYPE,
		DATE,
		ETAG,
		EXPIRES,
		LAST_MODIFIED,
		LOCATION,
		PRAGMA,
		PROXY_AUTHENTICATE,
		REFRESH,
		RETRY_AFTER,
		SERVER,
		SET_COOKIE,
		TRAILER,
		VARY,
		VIA,
		WARNING,
		WWW_AUTHENTICATE
	};
	
	/**
	 * SAC 요청헤더를 Bypass 응답헤더에 전달한다.
	 */
	public HttpHeaders mapForRequest(HttpHeaders source) {
		HttpHeaders target = new HttpHeaders();
		for (String name : source.keySet()) {
			if (!subjectToMap(name, HTTP_REQUEST_HEADER_NAMES)) {
				continue;
			}

			List<String> value = source.get(name);
			target.put(name, value);
		}
		return target;
	}
	
	/**
	 * Bypass 응답헤더를 SAC 응답헤더에 전달한다.
	 */
	public void mapForResponse(HttpHeaders source, HttpServletResponse target) {
		for (String name : source.keySet()) {
			if (!subjectToMap(name, HTTP_RESPONSE_HEADER_NAMES)) {
				continue;
			}
			
			// x-sac-guid 또는 x-sac-result-code 의 경우, 기존에 Filter나 Interceptor에서 세팅한 값을 덮어쓴다.
			if (subjectToOverwirte(name)) {
				target.setHeader(name, source.getFirst(name));
			} else {
				for (String value : source.get(name)) {
					target.addHeader(name, value);
				}
			}
		}
	}
	
	private boolean subjectToMap(String headerName, String[] headerNames) {
		if (StringUtils.startsWithIgnoreCase(headerName, SAC_CUSTOM_HEADER_PREFIX)) {
			return true;
		}
		for (String each : headerNames) {
			if (each.equalsIgnoreCase(headerName)){
				return true;
			}
		}
		return false;
	}

	private boolean subjectToOverwirte(String headerName) {
		if (RuntimeContextConstants.X_SAC_GUID.equals(headerName)) {
			return true;
		}
		if (RuntimeContextConstants.X_SAC_RESULT_CODE.equals(headerName)) {
			return true;
		}
		return false;
	}
	
}
