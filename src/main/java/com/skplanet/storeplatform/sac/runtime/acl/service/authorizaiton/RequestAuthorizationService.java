/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.authorizaiton;

import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

/**
 *
 * 인가 서비스
 *
 * Updated on : 2014. 2. 10.
 * Updated by : 정희원, SK 플래닛
 */
public interface RequestAuthorizationService {

    /**
     * systemId, interfaceId에 해당하는 인가 정보를 확인하여 액세스가 가능한지 확인한다.
     * @param httpHeaders
     */
    public void authorization(HttpHeaders httpHeaders);
}
