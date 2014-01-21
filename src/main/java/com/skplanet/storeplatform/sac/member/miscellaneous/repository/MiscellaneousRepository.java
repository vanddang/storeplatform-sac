package com.skplanet.storeplatform.sac.member.miscellaneous.repository;

import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuth;

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
	 *            String
	 * @return String
	 */
	public String getUaCode(String deviceModelNo);

	/**
	 * <pre>
	 * 휴대폰 SMS 인증 코드 및 Signature 일치 여부 확인.
	 * </pre>
	 * 
	 * @param serviceAuthInfo
	 *            ServiceAuthDTO
	 * @return ServiceAuthDTO
	 */
	public ServiceAuth getPhoneAuthYn(ServiceAuth serviceAuthInfo);

	/**
	 * <pre>
	 * 휴대폰 SMS 인증 코드 정보 저장.
	 * </pre>
	 * 
	 * @param serviceAuthInfo
	 *            ServiceAuthDTO
	 */
	public void insertPhoneAuthCode(ServiceAuth serviceAuthInfo);

	/**
	 * <pre>
	 * 휴대폰 SMS 인증 여부 업데이트(인증성공).
	 * </pre>
	 * 
	 * @param authSeq
	 *            String
	 */
	public void updatePhoneAuthYn(String authSeq);

	/**
	 * <pre>
	 * 회원 Key값으로 이메일 인증 여부 조회.
	 * </pre>
	 * 
	 * @param mbrNo
	 *            String
	 * @return String
	 */
	public String getEmailAuthYn(String mbrNo);
}
