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
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 인가 서비스 구현체
 *
 * Updated on : 2014. 2. 10.
 * Updated by : 정희원, SK 플래닛
 */
@Service
public class RequestAuthorizeServiceImpl implements RequestAuthorizeService {

    @Autowired
    private AclDataAccessService dbAccessService;

    @Override
    public void authorize(HttpHeaders httpHeaders) {

        String interfaceStatus = this.dbAccessService.selectUsableInterface(httpHeaders.getAuthKey(), httpHeaders.getInterfaceId());

        if(StringUtils.isEmpty(interfaceStatus))
            throw new StorePlatformException("SAC_CMN_0061");

        if(!StringUtil.equals(interfaceStatus, CommonConstants.INTERFACE_STAT_OK))
            throw new StorePlatformException("SAC_CMN_0062");
    }
}
