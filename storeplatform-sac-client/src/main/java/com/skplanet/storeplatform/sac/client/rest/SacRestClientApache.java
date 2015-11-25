package com.skplanet.storeplatform.sac.client.rest;

import org.apache.commons.lang.StringUtils;

import com.skplanet.storeplatform.sac.client.rest.apache.RestTemplateApahe;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestMethod;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestRequest;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestResponse;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestScheme;

/**
 * <pre>
 * SAC의 Rest API를 호출하기 위한 클라이언트 클래스
 * (Apache HttpClient를 사용하여 구현)
 * ##### SAC 환경별 Host 정보 (2014-04-08 기준) #####
 * DEV : http://dev-store.sungsu.skplanet.com
 * STAG : http://qa-store.store.sungsu.skplanet.com
 * PRD : http://store.sungsu.skplanet.com
 * ################################################
 * </pre>
 * Updated on : 2014. 3. 6.
 * Updated by : 서대영, SK플래닛.
 */
public class SacRestClientApache implements SacRestClient {

	private final RestTemplateApahe processor;

	/**
	 * 생성자
	 * @param host 호스트 (Domain/IP)
	 * @param authKey 인증키
	 * @param secret 비밀키
	 * @param tenantId 테넌트 식별자
	 * @param systemId 시스템 식별자
	 */
	public SacRestClientApache(String host, String authKey, String secret, String tenantId, String systemId) {
		this.processor = new RestTemplateApahe(host, authKey, secret, tenantId, systemId);
	}

	/**
	 * 생성자
	 *
	 *
	 * @param host
	 * 		SAC의 개발/QA/운영 host의 Domain Name
	 * @param authKey
	 * 		인증키
	 * @param systemId
	 * 		시스템 색별자 (참조https://project.itopping.co.kr:82/projects/api_center/wiki/TenantSystem)
	 */
	public SacRestClientApache(String host, String authKey, String secret, String systemId) {
		String tenantId = StringUtils.substring(authKey, 0, 3);
		this.processor = new RestTemplateApahe(host, authKey, secret, tenantId, systemId);
	}

	/**
	 * 테스트키용 생성자
	 *
	 * @param host
	 * 		SAC의 개발/QA/운영 host의 Domain Name
	 * @param authKey
	 * 		인증키 (인증키)
	 * @param systemId
	 * 		시스템 색별자 (참조https://project.itopping.co.kr:82/projects/api_center/wiki/TenantSystem)
	 */
	public SacRestClientApache(String host, String authKey, String systemId) {
		String tenantId = StringUtils.substring(authKey, 0, 3);
		String secret = null;
		this.processor = new RestTemplateApahe(host, authKey, secret, tenantId, systemId);
	}

	@Override
	public <T> T get(String interfaceId, String path, Class<T> responseType, Object param) throws SacRestClientException {
		return this.processor.process(SacRestScheme.http, SacRestMethod.GET, interfaceId, path, responseType, param, null);
	}

	@Override
	public <T> T post(String interfaceId, String path, Class<T> responseType, Object body) throws SacRestClientException {
		return this.processor.process(SacRestScheme.http, SacRestMethod.POST, interfaceId, path, responseType, null, body);
	}

	@Override
	public <T> T post(String interfaceId, String path, Class<T> responseType, Object param, Object body) throws SacRestClientException {
		return this.processor.process(SacRestScheme.http, SacRestMethod.POST, interfaceId, path, responseType, param, body);
	}

	@Override
	public <T> SacRestResponse<T> exchange(SacRestRequest request, Class<T> responseType) throws SacRestClientException {
		return this.processor.process(request, responseType);
	}

}
