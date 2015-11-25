/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.membership.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.membership.vo.SearchSaveExpectTotalAmtScReq;
import com.skplanet.storeplatform.purchase.client.membership.vo.SearchSaveExpectTotalAmtScRes;

/**
 * 
 * 마일리지 적립 처리 인터페이스
 * 
 * Updated on : 2014. 8. 8. Updated by : 이승택, nTels.
 */
@SCI
public interface MembershipReserveSCI {

	/**
	 * 
	 * <pre>
	 * 마일리지 적립 예상 총 금액 조회.
	 * </pre>
	 * 
	 * @param searchSaveExpectTotalAmtScReq
	 *            마일리지 적립 예상 총 금액 조회 요청 VO
	 * @return 마일리지 적립 예상 총 금액 조회 응답 VO
	 */
	public SearchSaveExpectTotalAmtScRes searchSaveExpectTotalAmt(
			SearchSaveExpectTotalAmtScReq searchSaveExpectTotalAmtScReq);

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
	 * 마일리지 처리상태 업데이트.
	 * </pre>
	 * 
	 * @param membershipReserve
	 *            멤버쉽 적립 예약 CommonVo
	 * @return int
	 */
	public int updateProcStatus(MembershipReserve membershipReserve);
}
