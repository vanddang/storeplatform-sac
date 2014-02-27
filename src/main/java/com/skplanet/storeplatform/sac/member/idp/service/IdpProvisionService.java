package com.skplanet.storeplatform.sac.member.idp.service;

import java.util.HashMap;

/**
 * IDP에서 전달되는 Provisioning 처리를 위한 인터페이스.
 * 
 * Updated on : 2014. 2. 27. Updated by : 반범진, 지티소프트.
 */
public interface IdpProvisionService {
	/**
	 * 
	 * <pre>
	 * 회선 변경 정보 Provisioning (번호변경).
	 * - CMD : changeMobileNumber
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String executeChangeMobileNumber(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 회선 변경 정보 Provisioning (기기변경).
	 * - CMD : changeMobileID
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String executeChangeMobileID(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 회선 변경 정보 Provisioning (번호해지).
	 * - CMD : secedeMobileNumber
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String executeSecedeMobileNumber(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 가입 승인 만료 정보 Provisioning (유선, 통합 회원).
	 * - CMD : joinComplete
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String executeJoinComplete(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 프로파일 변경 Provisioning (유선, 통합 회원).
	 * - CMD : adjustWiredProfile
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String executeAdjustWiredProfile(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 부가서비스 가입 Provisioning.
	 * - CMD : ecgJoinedTStore
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String executeEcgJoinedTStore(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 부가서비스 해지 Provisioning.
	 * - CMD : ecgScededTStore
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String executeEcgScededTStore(HashMap<String, String> map);

}
