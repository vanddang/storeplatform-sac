package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 부가 정보 등록/수정/삭제/조회 인터페이스
 * 
 * Updated on : 2014. 1. 20. Updated by : 강신완, 부르칸.
 */
public interface UserExtraInfoService {

	/**
	 * 등록/수정
	 * 
	 * @param headerVo
	 * @param WithdrawReq
	 * @return
	 * @throws Exception
	 */
	public UserExtraInfoRes withdraw(SacRequestHeader requestHeader, UserExtraInfoReq req) throws Exception;
}
