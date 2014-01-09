package com.skplanet.storeplatform.sac.member.miscellaneous.repository;

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
	 * @return
	 */
	public String getUaCode(String deviceModelNo);
}
