package com.skplanet.storeplatform.sac.client.rest;

import com.skplanet.storeplatform.sac.client.rest.apache.RestMethod;
import com.skplanet.storeplatform.sac.client.rest.apache.RestTemplateApahe;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

/**
 * SAC의 Rest API를 호출하기 위한 클라이언트 클래스
 * (Apache HttpClient를 사용하여 구현)
 *
 * Updated on : 2014. 3. 6.
 * Updated by : 서대영, SK플래닛.
 */
public class SacRestClientApache implements SacRestClient {

	private final RestTemplateApahe processor;

	/**
	 * 생성자
	 *
	 * ##### SAC 환경별 Host 정보 (2014-03-07 기준) #####
	 * DEV => devspweb1.sungsu.skplanet.com/sp_sac
	 * QA => qa-store.sungsu.store.skplanet.com/sp_sac
	 * REAL => store.sungsu.store.skplanet.com
	 * ################################################
	 *
	 * @param host
	 * 		SAC의 개발/QA/운영 host의 Domain Name
	 * @param authKey
	 * 		인증키 (인증키 발급 모듈이 현재 개발 중이어서 아무거나 넣어도 작동함)
	 * @param systemId
	 * 		시스템 색별자 (참조https://project.itopping.co.kr:82/projects/api_center/wiki/TenantSystem)
	 */
	public SacRestClientApache(String host, String authKey, String secret, String systemId) {
		this.processor = new RestTemplateApahe(host, authKey, secret, systemId);
	}

	/**
	 * 테스트키용 생성자
	 *
	 * ##### SAC 환경별 Host 정보 (2014-03-28 기준) #####
	 * DEV => devspweb1.sungsu.skplanet.com
	 * QA => qa-store.sungsu.store.skplanet.com
	 * REAL => store.sungsu.store.skplanet.com
	 * ################################################
	 *
	 * @param host
	 * 		SAC의 개발/QA/운영 host의 Domain Name
	 * @param authKey
	 * 		인증키 (인증키 발급 모듈이 현재 개발 중이어서 아무거나 넣어도 작동함)
	 * @param systemId
	 * 		시스템 색별자 (참조https://project.itopping.co.kr:82/projects/api_center/wiki/TenantSystem)
	 */
	public SacRestClientApache(String host, String authKey, String systemId) {
		this.processor = new RestTemplateApahe(host, authKey, systemId);
	}

	@Override
	public <T> T get(String interfaceId, String path, Class<T> responseType, Object param) throws SacRestClientException {
		return this.processor.process(RestMethod.GET, interfaceId, path, responseType, param, null);
	}

	@Override
	public <T> T post(String interfaceId, String path, Class<T> responseType, Object body) throws SacRestClientException {
		return this.processor.process(RestMethod.POST, interfaceId, path, responseType, null, body);
	}

	@Override
	public <T> T post(String interfaceId, String path, Class<T> responseType, Object param, Object body) throws SacRestClientException {
		return this.processor.process(RestMethod.POST, interfaceId, path, responseType, param, body);
	}

}
