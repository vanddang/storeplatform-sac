package com.skplanet.storeplatform.sac.member.idp.service;

import java.util.HashMap;

/**
 * IDP에서 전달되는 Provisioning 및 Rx 처리를 위한 인터페이스
 * 
 * Updated on : 2014. 1. 8. Updated by : 임재호, 인크로스.
 */
public interface IdpService {
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
	public HashMap RXCreateUserIDP(HashMap map);

}
