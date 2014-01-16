package com.skplanet.storeplatform.sac.member.miscellaneous.repository;

import java.util.Map;

/**
 * 
 * 기타 기능 관련 인터페이스
 * 
 * Updated on : 2014. 1. 9. Updated by : 김다슬, 인크로스.
 */
public interface MiscellaneousRepository {

	/**
	 * <pre>
	 * UA 코드 조회.
	 * </pre>
	 * 
	 * @param deviceModelNo
	 * @return String
	 */
	public String getUaCode(String deviceModelNo);

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송 - 휴대폰 SMS 재인증 여부 확인.
	 * </pre>
	 * 
	 * @param userKey
	 * @return String
	 */
	public Map<String, String> getPhoneAuthYn(String userKey);

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송 - 휴대폰 SMS 인증 코드 저장 / 삭제 예정 - 아래 merge method로 대체  예정.
	 * </pre>
	 * 
	 * @param phoneAuthCodeInfo
	 */
	public void insertPhoneAuthCode(Map<String, String> phoneAuthCodeInfo);

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송 - 휴대폰 SMS 인증 코드 저장 / 삭제 예정 - 아래 merge method로 대체 예정.
	 * </pre>
	 * 
	 * @param phoneAuthCodeInfo
	 */
	public void updatePhoneAuthCode(Map<String, String> phoneAuthCodeInfo);

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송 - 휴대폰 SMS 인증 코드 정보 Merge Into.
	 * </pre>
	 * 
	 * @param phoneAuthCodeInfo
	 */
	public void mergeIntoPhoneAuthCode(Map<String, String> phoneAuthCodeInfo);

	/**
	 * <pre>
	 * 휴대폰 인증 코드 정보 확인.
	 * </pre>
	 * 
	 * @param userKey
	 * @return Map
	 */
	public Map<String, String> confirmPhoneAuthCode(String userKey);

	/**
	 * <pre>
	 * 휴대폰 인증 코드 확인 - 인증 여부 Update.
	 * </pre>
	 * 
	 * @param userKey
	 */
	public void updatePhoneAuthYn(String userKey);
}
