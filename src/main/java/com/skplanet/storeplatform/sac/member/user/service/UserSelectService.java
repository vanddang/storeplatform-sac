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

import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;

/**
 * 회원 조회 서비스 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
public interface UserSelectService {

	/**
	 * <pre>
	 * 회원 가입 여부 조회
	 * </pre>
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 */
	public ExistRes exist(HeaderVo headerVo, ExistReq req);

}
