/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseHeaderSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 공통 기능을 임시로 정의해서 사용한다.
 * 
 * Updated on : 2014. 1. 7. Updated by : 양주원, 엔텔스.
 */
@Component
public class PurchaseCommonUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseCommonUtils.class);

	/**
	 * BindingValidation
	 * 
	 * @param bindingResult
	 *            바인딩결과
	 * @return void
	 */
	public void getBindingValid(BindingResult bindingResult) {

		// 필수값 체크
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {

				LOGGER.debug(error.getCode());

				if (error.getCode().equals("NotBlank") || error.getCode().equals("NotEmpty")
						|| error.getCode().equals("NotNull")) {
					throw new StorePlatformException("SAC_PUR_0001", error.getField());

				} else if (error.getCode().equals("Pattern")) {
					// @Pattern 처리시 pattern의 message 셋팅
					throw new StorePlatformException("SAC_PUR_0003", error.getField(), error.getRejectedValue(),
							error.getDefaultMessage());

				} else if (error.getCode().equals("Max")) {
					throw new StorePlatformException("SAC_PUR_0004", error.getField(), error.getRejectedValue(), 100);

				} else if (error.getCode().equals("Min")) {
					throw new StorePlatformException("SAC_PUR_0005", error.getField(), error.getRejectedValue(), 1);

				} else {
					throw new StorePlatformException("SAC_PUR_9999");
				}

			}
		}
	}

	public void setHeader(PurchaseHeaderSacReq purchaseHeaderSacReq, SacRequestHeader sacRequestHeader) {
		purchaseHeaderSacReq.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		purchaseHeaderSacReq.setSystemId(sacRequestHeader.getTenantHeader().getSystemId());
	}
}
