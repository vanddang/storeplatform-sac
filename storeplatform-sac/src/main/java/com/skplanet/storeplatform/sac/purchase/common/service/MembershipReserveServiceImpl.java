/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.membership.sci.MembershipReserveSCI;
import com.skplanet.storeplatform.purchase.client.membership.vo.SearchSaveExpectTotalAmtScReq;
import com.skplanet.storeplatform.purchase.client.membership.vo.SearchSaveExpectTotalAmtScRes;

/**
 * 
 * 마일리지 적립 처리 서비스
 * 
 * Updated on : 2014. 8. 8. Updated by : 이승택, nTels.
 */
@Service
public class MembershipReserveServiceImpl implements MembershipReserveService {

	@Autowired
	MembershipReserveSCI membershipReserveSCI;

	/**
	 * 
	 * <pre>
	 * 마일리지 적립 예상 총 금액 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param insdUsermbrNo
	 *            내부회원번호
	 * @param targetDt
	 *            적립예정일의 기준일
	 * @param saveDt
	 *            적립예정일
	 * @return 마일리지 적립 예상 총 금액
	 */
	@Override
	public int searchSaveExpectTotalAmt(String tenantId, String insdUsermbrNo, String targetDt, String saveDt) {
		SearchSaveExpectTotalAmtScReq searchSaveExpectTotalAmtScReq = new SearchSaveExpectTotalAmtScReq();
		searchSaveExpectTotalAmtScReq.setTenantId(tenantId);
		searchSaveExpectTotalAmtScReq.setInsdUsermbrNo(insdUsermbrNo);
		searchSaveExpectTotalAmtScReq.setTargetDt(targetDt);
		searchSaveExpectTotalAmtScReq.setSaveDt(saveDt);

		SearchSaveExpectTotalAmtScRes searchSaveExpectTotalAmtScRes = this.membershipReserveSCI
				.searchSaveExpectTotalAmt(searchSaveExpectTotalAmtScReq);
		return searchSaveExpectTotalAmtScRes.getAmt();
	}
}
