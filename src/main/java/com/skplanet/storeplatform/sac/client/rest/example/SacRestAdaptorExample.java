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
	private final SacRestClient template;

	public SacRestAdaptorExample() {
		/*
		 ##### SAC 환경별 Host 정보 (2014-03-07 기준) #####
		 DEV => devspweb1.sungsu.skplanet.com/sp_sac
		 QA => qa-store.sungsu.store.skplanet.com/sp_sac
	     REAL => store.sungsu.store.skplanet.com
	     ################################################
		 */
		String host = "devspweb1.sungsu.skplanet.com/sp_sac";
		// 인증키는 생성 모듈은 현재 개발 중이어서 아무 값이나 넣어둠
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		// 시스템 아이디 참조 (https://project.itopping.co.kr:82/projects/api_center/wiki/TenantSystem)
		String systemId = "S01-06001";
		// 호스트 및 인증 정보 입력하여 템플릿 생성
		this.template= new SacRestClientApache(host, authKey, systemId);
	}

	public <T> T get(String interfaceId, String path, Class<T> responseType, Object param) throws SacRestClientException {
		return this.template.get(interfaceId, path, responseType, param);
	}

	public <T> T post(String interfaceId, String path, Class<T> responseType, Object body) throws SacRestClientException {
		return this.template.post(interfaceId, path, responseType, body);
	}


}
