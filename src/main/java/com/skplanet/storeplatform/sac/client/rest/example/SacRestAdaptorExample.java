package com.skplanet.storeplatform.sac.client.rest.example;

import com.skplanet.storeplatform.sac.client.rest.SacRestClient;
import com.skplanet.storeplatform.sac.client.rest.SacRestClientApache;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

/**
 * 아래와 같이 어댑터 패턴을 사용하여 SAC 호출 부분을 모듈화하면,
 * 추후 SacRestClient이 변경되었을 때 영향을 최소화 시킬수 있다.
 *
 * Created on 2014. 3. 7. Created by 서대영, SK 플래닛.
 * Updated on 2014. 5. 5. Updated by 서대영, SK 플래닛. : 상용키로 변경
 */
/*
##### SAC 환경별 Host 정보 (2014-05-06 기준) #####
DEV => dev-store.sungsu.skplanet.com
QA => qa-store.store.sungsu.skplanet.com
REAL => store.sungsu.skplanet.com
################################################
*/
public class SacRestAdaptorExample {

	// 추후 구현체 바꿀 수 있도록 인터페이스 사용 권장
	private final SacRestClient restClient;

	public SacRestAdaptorExample() {
		// 호스트 (프로토콜, 포트 제거할 것!)
		String host = "localhost:8010";
		// 인증키 (SAC에 발급 신청, 유효 기간 확인)
		String authKey = "S018aff2d1ada07fbc284cd3e0b243af437";
		// 비밀키 (SAC에 발급 신청, 유효 기간 확인)
		String secret = "6d121baf2a4ae3b74e2b61a3b888c250";
		// 테넌트 아이디 (https://project.itopping.co.kr:82/projects/api_center/wiki/TenantSystem)
		String tenantId = "S01";
		// 시스템 아이디 (https://project.itopping.co.kr:82/projects/api_center/wiki/TenantSystem)
		String systemId = "S01-06001";
		// 호스트 및 인증 정보 입력하여 템플릿 생성
		this.restClient= new SacRestClientApache(host, authKey, secret, tenantId, systemId);
	}

	public <T> T get(String interfaceId, String path, Class<T> responseType, Object param) throws SacRestClientException {
		return this.restClient.get(interfaceId, path, responseType, param);
	}

	public <T> T post(String interfaceId, String path, Class<T> responseType, Object body) throws SacRestClientException {
		return this.restClient.post(interfaceId, path, responseType, body);
	}

}
