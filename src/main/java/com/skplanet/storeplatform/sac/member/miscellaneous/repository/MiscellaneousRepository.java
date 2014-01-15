package com.skplanet.storeplatform.sac.member.miscellaneous.repository;

import java.util.HashMap;

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
	 * @param String
	 *            deviceModelNo
	 * @return String
	 */
	public String getUaCode(String deviceModelNo);

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송 - 휴대폰 SMS 재인증 여부 확인.
	 * </pre>
	 * 
	 * @param map
	 * @return String
	 */
	public HashMap<String, String> getPhoneAuthYn(String userKey);

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송 - 휴대폰 SMS 인증 코드 저장 / 삭제 예정 - 아래 method로 대체.
	 * </pre>
	 * 
	 * @param request
	 */
	public void insertPhoneAuthCode(HashMap<String, String> phoneAuthCodeInfo);

	/**
	 * <pre>
	 * 휴대폰 인증 SMS 발송 - 휴대폰 SMS 인증 코드 정보 Merge Into.
	 * </pre>
	 * 
	 * @param request
	 */
	public void mergeIntoPhoneAuthCode(HashMap<String, String> phoneAuthCodeInfo);
}
