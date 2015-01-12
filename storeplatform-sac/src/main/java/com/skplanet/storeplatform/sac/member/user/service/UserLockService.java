/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.LockAccountSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.LockAccountSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 계정 잠금 서비스 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 10. Updated by : 심대진, 다모아 솔루션.
 */
public interface UserLockService {

	/**
	 * <pre>
	 * 회원 계정 잠금.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 * 
	 */
	public LockAccountSacRes lockAccount(SacRequestHeader sacHeader, LockAccountSacReq req);

}
