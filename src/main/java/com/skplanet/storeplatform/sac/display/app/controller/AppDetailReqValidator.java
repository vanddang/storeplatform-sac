/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.app.controller;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <p>
 * AppDetailReqValidator
 * </p>
 * Updated on : 2014. 02. 25 Updated by : 정희원, SK 플래닛.
 */

public class AppDetailReqValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return AppDetailReq.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AppDetailReq vo = (AppDetailReq)o;
        if(StringUtils.isEmpty(vo.getChannelId()))
            errors.rejectValue("channelId", "NotEmpty", new Object[]{"channelId"}, "channelId가 지정되지 않았습니다.");

        if (StringUtils.isEmpty(vo.getDeviceKey()) ^ StringUtils.isEmpty(vo.getUserKey())) {
            if(StringUtils.isEmpty(vo.getDeviceKey()))
                errors.rejectValue("userKey", "NotEmpty", new Object[]{"userKey"}, "deviceKey, userKey가 함께 지정되어야 합니다.");
            if(StringUtils.isEmpty(vo.getUserKey()))
                errors.rejectValue("deviceKey", "NotEmpty", new Object[]{"deviceKey"}, "deviceKey, userKey가 함께 지정되어야 합니다.");
        }
    }
}
