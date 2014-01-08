package com.skplanet.storeplatform.sac.member.idp.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

/**
 * IDP에서 전달되는 Provisioning 및 Rx 처리를 위한 인터페이스
 * 
 * Updated on : 2014. 1. 8. Updated by : 임재호, 인크로스.
 */
@Service
public class IdpServiceImple implements IdpService {
	/**
	 * 
	 * <pre>
	 * 통합회원전환생성정보를사이트에배포 
	 * - CMD : RXCreateUserIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return HashMap
	 */
	@Override
	public HashMap RXCreateUserIDP(HashMap map) {
		// Service Method이름은 Provisioning 및 Rx 기능의 'cmd' 값과 동일 해야 함.
		return null;
	}
}
