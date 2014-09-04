/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.controller;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadMMSacReq;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <p>
 * BestDownloadMMSacReqValidator
 * </p>
 * Updated on : 2014. 04. 24 Updated by : 이석희, 아이에스 플러스.
 */

public class BestDownloadMMSacReqValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return BestDownloadMMSacReq.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		BestDownloadMMSacReq vo = (BestDownloadMMSacReq) o;

		if (StringUtils.isEmpty(vo.getListId()) && StringUtils.isEmpty(vo.getOrderedBy())) {
			errors.rejectValue("listId", "NotEmpty", new Object[] { "listId" }, "listId");
			errors.rejectValue("orderedBy", "NotEmpty", new Object[] { "orderedBy" }, "orderedBy");
		}
	}
}
