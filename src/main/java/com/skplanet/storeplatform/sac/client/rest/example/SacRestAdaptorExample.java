package com.skplanet.storeplatform.sac.client.rest.example;

import com.skplanet.storeplatform.sac.client.rest.SacRestClient;
import com.skplanet.storeplatform.sac.client.rest.SacRestClientApache;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

/**
 * 아래와 같이 어댑터 패턴을 사용하여 SAC 호출 부분을 모듈화하면,
 * 추후 SacRestClient이 변경되었을 때 영향을 최소화 시킬수 있다.
 *
 * Updated on : 2014. 3. 7.
 * Updated by : 서대영, SK 플래닛.
 */
public class SacRestAdaptorExample {

	// 추후 구현체 바꿀 수 있도록 인터페이스 사용 권장
	private final SacRestClient restClient;

	public SacRestAdaptorExample() {
		/*
		 ##### SAC 환경별 Host 정보 (2014-03-07 기준) #####
		 DEV => devspweb1.sungsu.skplanet.com/sp_sac
		 QA => qa-store.sungsu.store.skplanet.com/sp_sac
	     REAL => store.sungsu.store.skplanet.com
	     ################################################
		 */
		String host = "devspweb1.sungsu.skplanet.com/sp_sac";
		// 테스트키 (유효기간 : 2014-03-24 ~ 2014-04-23)
		String authKey = "S010c629d2e2fb303ce5664c1ab3bc40a2e";
		// 인증키 (유효 기간 : 2014-03-24 ~ 2015-03-23)
		// String authKey = "S01464ce656b142258a5f7441c133fed9ca";
		// 비밀키
		// String secret = "c3b578a3d086e45dec6bb1d1cf970e45";
		// 시스템 아이디 참조 (https://project.itopping.co.kr:82/projects/api_center/wiki/TenantSystem)
		String systemId = "S01-06001";
		// 호스트 및 인증 정보 입력하여 템플릿 생성
		this.restClient= new SacRestClientApache(host, authKey, systemId);
		// this.restClient= new SacRestClientApache(host, authKey, secret, systemId);
	}

	public <T> T get(String interfaceId, String path, Class<T> responseType, Object param) throws SacRestClientException {
		return this.restClient.get(interfaceId, path, responseType, param);
	}

	public <T> T post(String interfaceId, String path, Class<T> responseType, Object body) throws SacRestClientException {
		return this.restClient.post(interfaceId, path, responseType, body);
	}

}
