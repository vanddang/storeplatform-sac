/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacReq;

/**
 * <p>
 * DownloadReqValidator
 * </p>
 * Updated on : 2014. 04. 23 Updated by : 백승현, 인크로스.
 */

public class DownloadAppSacReqValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return DownloadAppSacReq.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		DownloadAppSacReq vo = (DownloadAppSacReq) o;

		if (StringUtils.isEmpty(vo.getFilteredBy()))
			errors.rejectValue("filteredBy", "NotEmpty", new Object[] { "filteredBy" }, "filteredBy");

		if (("id").equals(vo.getFilteredBy())) {
			if (StringUtils.isEmpty(vo.getProductId()))
				errors.rejectValue("productId", "NotEmpty", new Object[] { "productId" }, "productId");
		} else if (("package").equals(vo.getFilteredBy())) {
			if (StringUtils.isEmpty(vo.getPackageName()))
				errors.rejectValue("packageName", "NotEmpty", new Object[] { "packageName" }, "packageName");
		}
	}
}
