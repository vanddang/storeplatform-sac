package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원탈퇴 관련 인터페이스
 * 
 * Updated on : 2014. 1. 16. Updated by : 강신완, 부르칸.
 */
public interface UserWithdrawService {

	/**
	 * 회원탈퇴
	 * 
	 * @param headerVo
	 * @param WithdrawReq
	 * @return
	 * @throws Exception
	 */
	public WithdrawRes withdraw(SacRequestHeader requestHeader, WithdrawReq req) throws Exception;
}
