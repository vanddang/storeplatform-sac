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

import com.skplanet.storeplatform.sac.client.user.vo.UserSearchMdnRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.UserSearchMdnResponseVO;

/**
 * 회원 가입 여부 조회 Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 20. Updated by : 한서구, 부르칸.
 */
public interface UserSearchService {

	UserSearchMdnResponseVO searchMdn(UserSearchMdnRequestVO value);

}
