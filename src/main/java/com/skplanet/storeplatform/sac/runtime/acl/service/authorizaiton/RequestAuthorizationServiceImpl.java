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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDbAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

/**
 *
 * 인가 서비스 구현체
 *
 * Updated on : 2014. 2. 10.
 * Updated by : 정희원, SK 플래닛
 */
public class RequestAuthorizationServiceImpl implements RequestAuthorizationService {

//    @Autowired
    private AclDbAccessService dbAccessService;

    @Override
    public void authorization(HttpHeaders httpHeaders) {
        Tenant tenant = this.dbAccessService.selectTenantByAuthKey(httpHeaders.getAuthKey());
        String tenantId = tenant.getTenantId();

        String interfaceStatus = this.dbAccessService.selectInterfaceStatus(tenantId, httpHeaders.getInterfaceId());

        if(interfaceStatus == null)
            throw new StorePlatformException("SAC_CMN_0061");

        if(!StringUtil.equals(interfaceStatus, CommonConstants.INTERFACE_STAT_OK))
            throw new StorePlatformException("SAC_CMN_0062");
    }
}
