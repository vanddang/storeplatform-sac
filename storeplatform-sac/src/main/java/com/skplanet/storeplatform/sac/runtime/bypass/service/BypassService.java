package com.skplanet.storeplatform.sac.runtime.bypass.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


public interface BypassService {

	/**
     * API가 바이패스인지 여부를 판단한다
	 * @param interfaceId 인터페이스 아이디
	 * @return 바이패스 여부
	 */
	boolean isBypass(String interfaceId);
	
	/**
	 * API를 바이패스 시킨다.
	 * @param interfaceId 인터페이스 아이디
	 * @param tenantId 테넌트 아이디
	 * @param method HTTP 메소드 문자열
	 * @param queryString 쿼리 스트릥 문자열
	 * @param requestHeaders 요청 헤
	 * @param requestBody 요청 전문 문자열
	 * @param response HttpServletResposne
	 * @return
	 */
	ResponseEntity<String> bypass(String method, String queryString, HttpHeaders requestHeaders, String requestBody, HttpServletResponse response);
	
}
