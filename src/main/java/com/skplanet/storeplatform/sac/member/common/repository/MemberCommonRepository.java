package com.skplanet.storeplatform.sac.member.common.repository;

import java.util.List;

import com.skplanet.storeplatform.sac.common.vo.Device;
import com.skplanet.storeplatform.sac.member.common.vo.ClauseDTO;

/**
 * 공통 관련 인터페이스
 * 
 * Updated on : 2014. 1. 9. Updated by : 심대진, 다모아 솔루션.
 */

public interface MemberCommonRepository {

	/**
	 * <pre>
	 * 필수 약관 동의 목록 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 아이디
	 * @return List<ClauseDTO>
	 * @throws Exception
	 *             Exception
	 */
	public List<ClauseDTO> getMandAgreeList(String tenantId) throws Exception;

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            단말 모델 코드
	 * @return Device
	 * @throws Exception
	 *             Exception
	 */
	public Device getPhoneInfo(String deviceModelCd) throws Exception;

}
