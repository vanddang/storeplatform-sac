package com.skplanet.storeplatform.sac.member.idp.service;

import java.util.HashMap;

import com.skplanet.storeplatform.sac.member.idp.vo.ImResult;

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
	 * @return One ID Rx 처리 결과
	 */
	public ImResult rXCreateUserIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 회선 변경 정보 Provisioning (무선, 통합 회원)
	 * - CMD : changeMobileNumber
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String changeMobileNumber(HashMap map);

}
