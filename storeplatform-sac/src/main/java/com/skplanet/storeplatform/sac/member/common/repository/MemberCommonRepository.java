package com.skplanet.storeplatform.sac.member.common.repository;

import java.util.List;

import com.skplanet.storeplatform.sac.member.common.vo.Clause;
import com.skplanet.storeplatform.sac.member.common.vo.CommonCode;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

/**
 * 공통 관련 인터페이스
 * 
 * Updated on : 2014. 1. 9. Updated by : 심대진, 다모아 솔루션.
 */

public interface MemberCommonRepository {

	/**
	 * <pre>
	 * 약관 목록 조회.
	 * </pre>
	 * 
	 * @return List<ClauseDTO>
	 */
	public List<Clause> searchClauseList();

	/**
	 * <pre>
	 * 약관 목록 상세 조회.
	 * </pre>
	 * 
	 * @param clauseId
	 * @return List<Clause>
	 */
	public List<Clause> searchClauseDetail(String clauseId);

	/**
	 * <pre>
	 * 테넌트 약관 코드 조회
	 * </pre>
	 * 
	 * @param clauseId
	 * @return Clause
	 */
	public Clause searchTenantClauseCode(String clauseId);

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            단말 모델 코드
	 * @return DeviceHeader
	 */
	public Device searchPhoneInfo(String deviceModelCd);

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param uacd
	 *            단말 모델 코드
	 * @return DeviceHeader
	 */
	public Device searchPhoneInfoByUacd(String uacd);

	/**
	 * <pre>
	 * OMD 단말여부 확인.
	 * </pre>
	 * 
	 * @param uacd
	 *            String
	 * @return int
	 */
	public int searchOmdCount(String uacd);

	/**
	 * <pre>
	 * 공통 약관 코드 정보 조회.
	 * </pre>
	 * 
	 * @param clauseId
	 *            약관 코드
	 * @return Clause
	 */
	public Clause getClauseItemInfo(String clauseId);

	/**
	 * <pre>
	 * 공통코드 조회.
	 * </pre>
	 * 
	 * @param grpCdId
	 *            그룹코드
	 * @return CommonCode 공통 코드
	 */
	public List<CommonCode> getCommonCode(String grpCdId);
}
