/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.recommend.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonSacReq;

/**
 * <p>
 * RecommendWebtoonSacReqValidator
 * </p>
 * Updated on : 2014. 04. 28 Updated by : 조준일, nTels.
 */

public class RecommendWebtoonSacReqValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return RecommendWebtoonSacReq.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		RecommendWebtoonSacReq vo = (RecommendWebtoonSacReq) o;

		if (("ADM000000067").equals(vo.getListId())) {
			if (StringUtils.isEmpty(vo.getMenuId()))
				errors.rejectValue("menuId", "NotEmpty", new Object[] { "menuId" }, "menuId");
		}

	}
}
