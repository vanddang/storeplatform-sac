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
	 * 통합회원전환생성정보를사이트에배포. 
	 * - CMD : RXCreateUserIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request 받은 Parameter Map
	 * @return One ID Rx 처리 결과
	 */
	public ImResult rXCreateUserIDP(HashMap<String, String> map);

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
	public String changeMobileNumber(HashMap<String, String> map);

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
	public String changeMobileID(HashMap<String, String> map);

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
	public String secedeMobileNumber(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 휴대폰소유변경정보배포.
	 * - CMD : RXInvalidUserTelNoIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return ImResult
	 */
	public ImResult rXInvalidUserTelNoIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 로그인 상태정보배포.
	 * - CMD : RXSetLoginConditionIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return ImResult
	 */
	public ImResult rXSetLoginConditionIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 신규가입정보를 미동의 사이트에 배포.
	 * - CMD : RXCreateUserIdIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return ImResult
	 */
	public ImResult rXCreateUserIdIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 직권중지 상태정보 배포.
	 * - CMD : RXSetSuspendUserIdIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return ImResult
	 */

	public ImResult rXSetSuspendUserIdIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 실명변경 정보 배포.
	 * - CMD : RXUpdateUserNameIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return ImResult
	 */

	public ImResult rXUpdateUserNameIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 법정대리인 동의정보 변경 배포.
	 * - CMD : RXUpdateGuardianInfoIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return ImResult
	 */

	public ImResult rXUpdateGuardianInfoIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 활성화된 통합 ID 가입자 상태정보 배포.
	 * </pre>
	 * 
	 * @param map
	 *            Request 받은 Parameter Map
	 * @return
	 */
	public ImResult rXActivateUserIdIDP(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 전체 서비스 사이트 해지 배포.
	 * </pre>
	 * 
	 * @param map
	 *            Request 받은 Parameter Map
	 * @return
	 */
	public ImResult rXDeleteUserIdIDP(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 가입 승인 만료 정보 Provisioning (유선, 통합 회원)
	 * - CMD : joinComplete
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String joinComplete(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 프로파일 변경 Provisioning (유선, 통합 회원)
	 * - CMD : adjustWiredProfile
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String adjustWiredProfile(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 부가서비스 가입 Provisioning
	 * - CMD : ecgJoinedTStore
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String ecgJoinedTStore(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 부가서비스 해지 Provisioning
	 * - CMD : ecgScededTStore
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	public String ecgScededTStore(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 탈퇴가 가능한 통합회원 확인요청 - CMD : RXPreCheckDeleteUserIDP .
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	public ImResult rXPreCheckDeleteUserIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 이용해지가 가능한 통합회원 확인요청 - CMD : RXPreCheckDisagreeUserIDP .
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	public ImResult rXPreCheckDisagreeUserIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * OCB 해지 및 철회 요청 설정 배포 - CMD : RXSetOCBDisagreeIDP .
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */

	public ImResult rXSetOCBDisagreeIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 이용해지변경 프로파일 배포.
	 * </pre>
	 * 
	 * @param map
	 * @return
	 */
	public ImResult rXUpdateDisagreeUserIDP(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * Retry 완료 알림 배포 - CMD : RXTerminateRetryIDP .
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	public ImResult rXTerminateRetryIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 비밀번호 변경 배포 - oneID 회원 - CMD : RXUpdateUserPwdIDP .
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	public ImResult rXUpdateUserPwdIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 비밀번호 변경 알림 배포 -기존 IDP 회원 - CMD : RXChangePWDIDP .
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	public ImResult rXChangePWDIDP(HashMap map);

	/**
	 * 
	 * <pre>
	 * 변경된 공통 프로파일 배포.
	 * </pre>
	 * 
	 * @param map
	 *            Request 받은 Parameter Map
	 * @return
	 */
	public ImResult rXUpdateUserInfoIDP(HashMap<String, String> map);

	/**
	 * 
	 * <pre>
	 * 통합 ID 변경 배포.
	 * </pre>
	 * 
	 * @param map
	 *            Request 받은 Parameter Map
	 * @return
	 */
	public ImResult rXChangeUserIdIDP(HashMap<String, String> map);

	/**
	 * 이용동의 변경사이트 목록 배포.
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param map
	 *            Request 받은 Parameter Map
	 * @return
	 */
	public ImResult rXUpdateAgreeUserIDP(HashMap<String, String> map);
}
