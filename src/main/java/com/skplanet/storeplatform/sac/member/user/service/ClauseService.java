package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.ListClauseSacRes;

/**
 * 약관목록 관련 인터페이스.
 * 
 * Updated on : 2014. 2. 12. Updated by : 강신완, 부르칸.
 */
public interface ClauseService {

	/**
	 * 약관목록 조회.
	 * 
	 * @return ListClauseSacRes
	 */
	public ListClauseSacRes listClause();

}
