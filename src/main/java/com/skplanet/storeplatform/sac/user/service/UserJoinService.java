/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.user.service;

import com.skplanet.storeplatform.sac.client.user.vo.SellerSecedeResonListResponseVO;
import com.skplanet.storeplatform.sac.client.user.vo.UserJoinMdnRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.UserJoinMdnResponseVO;

/**
 * 회원 가입 Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 19. Updated by : 심대진, 다모아 솔루션.
 */
public interface UserJoinService {

	UserJoinMdnResponseVO joinMdn(UserJoinMdnRequestVO value);

	UserJoinMdnResponseVO joinIdPw(UserJoinMdnRequestVO value);

	SellerSecedeResonListResponseVO sellerSecedeResonList();
}
