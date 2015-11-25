/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.membership.sci;

import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.membership.sci.MembershipReserveSCI;
import com.skplanet.storeplatform.purchase.client.membership.vo.SearchSaveExpectTotalAmtScReq;
import com.skplanet.storeplatform.purchase.client.membership.vo.SearchSaveExpectTotalAmtScRes;
import com.skplanet.storeplatform.purchase.membership.service.MembershipReserveSCService;

/**
 * 
 * 마일리지 적립 처리 컨트롤러
 * 
 * Updated on : 2014. 8. 8. Updated by : 이승택, nTels.
 */
@LocalSCI
public class MembershipReserveSCIController implements MembershipReserveSCI {
	@Autowired
	private MembershipReserveSCService membershipReserveService;

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
	@Override
	public SearchSaveExpectTotalAmtScRes searchSaveExpectTotalAmt(
			SearchSaveExpectTotalAmtScReq searchSaveExpectTotalAmtScReq) {
		MembershipReserve membershipReserve = new MembershipReserve();
		membershipReserve.setTenantId(searchSaveExpectTotalAmtScReq.getTenantId());
		membershipReserve.setInsdUsermbrNo(searchSaveExpectTotalAmtScReq.getInsdUsermbrNo());
		membershipReserve.setProcStatusCd(PurchaseCDConstants.MEMBERSHIP_PROC_STATUS_RESERVE);
		membershipReserve.setPromId(searchSaveExpectTotalAmtScReq.getPromId());

		int amt = this.membershipReserveService.searchSaveExpectTotalAmt(membershipReserve);

		return new SearchSaveExpectTotalAmtScRes(amt);
	}

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
	@Override
	public MembershipReserve getSaveInfo(MembershipReserve membershipReserve) {
		return this.membershipReserveService.getSaveInfo(membershipReserve);
	}

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
	@Override
	public int updateProcStatus(MembershipReserve membershipReserve) {
		return this.membershipReserveService.updateProcStatus(membershipReserve);
	}

}
