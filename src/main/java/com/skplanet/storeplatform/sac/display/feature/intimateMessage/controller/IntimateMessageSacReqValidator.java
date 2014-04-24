/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.intimateMessage.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacReq;

public class IntimateMessageSacReqValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return IntimateMessageSacReq.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		IntimateMessageSacReq vo = (IntimateMessageSacReq) o;

		if (StringUtils.isEmpty(vo.getMsgType())) {
			errors.rejectValue("msgType", "NotEmpty", new Object[] { "msgType" }, "msgType");
		}

		if (("all").equals(vo.getMsgType())) {
			if (StringUtils.isEmpty(vo.getUserKey())) {
				errors.rejectValue("userKey", "NotEmpty", new Object[] { "userKey" }, "userKey");
			} else if (StringUtils.isEmpty(vo.getDeviceKey())) {
				errors.rejectValue("deviceKey", "NotEmpty", new Object[] { "deviceKey" }, "deviceKey");
			}
		}
	}
}
