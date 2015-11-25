/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.membership.service;

import java.util.List;

import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;

/**
 * 
 * 마일리지 적립 처리 서비스
 * 
 * Updated on : 2014. 8. 8. Updated by : 이승택, nTels.
 */
public interface MembershipReserveSCService {

	/**
	 * 
	 * <pre>
	 * 마일리지 적립 예상 총 금액 조회.
	 * </pre>
	 * 
	 * @param membershipReserve
	 *            마일리지 적립 이력 생성 VO
	 * @return 마일리지 적립 이력 생성 건수
	 */
	public int insertMembershipReserve(MembershipReserve membershipReserve);

	/**
	 * 
	 * <pre>
	 * 마일리지 적립 예상 총 금액 조회.
	 * </pre>
	 * 
	 * @param membershipReserveList
	 *            마일리지 적립 이력 생성 VO 목록
	 * @return 마일리지 적립 이력 생성 건수
	 */
	public int insertMembershipReserveList(List<MembershipReserve> membershipReserveList);

	/**
	 * 
	 * <pre>
	 * 마일리지 적립 예상 총 금액 조회.
	 * </pre>
	 * 
	 * @param membershipReserve
	 *            마일리지 적립 예상 총 금액 조회 VO
	 * @return 마일리지 적립 예상 총 금액
	 */
	public int searchSaveExpectTotalAmt(MembershipReserve membershipReserve);

	/**
	 * 
	 * <pre>
	 * 마일리지 적립 정보 조회.
	 * </pre>
	 * 
	 * @param membershipReserve
	 *            멤버쉽 적립 예약 CommonVo
	 * @return 멤버쉽 적립 예약 CommonVo
	 */
	public MembershipReserve getSaveInfo(MembershipReserve membershipReserve);

	/**
	 * 
	 * <pre>
	 * 마일리지 적립 정보 조회.
	 * </pre>
	 * 
	 * @param membershipReserve
	 *            멤버쉽 적립 예약 CommonVo
	 * @return 멤버쉽 적립 예약 CommonVo
	 */
	public int updateProcStatus(MembershipReserve membershipReserve);
}
