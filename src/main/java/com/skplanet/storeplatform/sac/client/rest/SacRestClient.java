package com.skplanet.storeplatform.sac.client.rest;

import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

/**
 * SAC의 Rest API를 호출하기 위한 클라이언트 인터페이스
 *
 * Updated on : 2014. 3. 6.
 * Updated by : 서대영, SK플래닛.
 */
public interface SacRestClient {

	/**
	 * GET 방식으로 SAC API를 호출한다.
	 *
	 * @param interfaceId
	 * 		인터페이스 색별자 (규격서 참조)
	 * @param path
	 * 		호스트 이하 경로 (규격서 참조)
	 * @param responseType
	 * 		응답 데이터 타입
	 * @param param
	 * 		RequestParameter (Object, Map 모두 가능)
	 * @return
	 * 		응답 데이터 객체
	 */
	<T> T get(String interfaceId, String path, Class<T> responseType, Object param) throws SacRestClientException;

	/**
	 * POST 방식으로 SAC API를 호출한다.
	 *
	 * @param interfaceId
	 * 		인터페이스 색별자 (규격서 참조)
	 * @param path
	 * 		호스트 이하 경로 (규격서 참조)
	 * @param responseType
	 * 		응답 데이터 타입
	 * @param body
	 * 		RequestBody (Object, Map 모두 가능)
	 * @return
	 * 		응답 데이터 객체
	 */
	<T> T post(String interfaceId, String path, Class<T> responseType, Object body) throws SacRestClientException;

	/**
	 * POST 방식으로 SAC API를 호출한다.
	 *
	 * @param interfaceId
	 * 		인터페이스 색별자 (규격서 참조)
	 * @param path
	 * 		호스트 이하 경로 (규격서 참조)
	 * @param responseType
	 * 		응답 데이터 타입
	 * @param param
	 * 		RequestParameter (Object, Map 모두 가능)
	 * @param body
	 * 		RequestBody (Object, Map 모두 가능)
	 */
	<T> T post(String interfaceId, String path, Class<T> responseType, Object param, Object body) throws SacRestClientException;

}
