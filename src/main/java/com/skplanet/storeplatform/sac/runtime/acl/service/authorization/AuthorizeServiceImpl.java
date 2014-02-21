/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.authorization;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.acl.vo.InterfaceStatus;

/**
 *
 * 인가 서비스 구현체
 *
 * Updated on : 2014. 2. 10.
 * Updated by : 정희원, SK 플래닛
 */
@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Autowired
    private AclDataAccessService service;

    @Override
    public void checkInterface(HttpHeaders header) {
		String interfaceId = header.getInterfaceId();
		String requestUrl = header.getRequestUrl();

		Interface intf = this.service.selectInterfaceById(interfaceId);

		if (intf == null) {
			throw new StorePlatformException("SAC_CMN_0061");
		}

		if (intf.getStatus() != InterfaceStatus.AVAILABLE) {
			throw new StorePlatformException("SAC_CMN_0062");
		}

		String urlFromDb = intf.getUrl();
		if (!StringUtils.equalsIgnoreCase(requestUrl, urlFromDb)) {
			throw new StorePlatformException("SAC_CMN_0063");
		}

	}

    @Override
    public void checkMapping(HttpHeaders header) {
		String interefaceId = this.service.selectUsableInterface(header.getAuthKey(), header.getInterfaceId());

        if(StringUtils.isEmpty(interefaceId))
            throw new StorePlatformException("SAC_CMN_0064");
	}

}
