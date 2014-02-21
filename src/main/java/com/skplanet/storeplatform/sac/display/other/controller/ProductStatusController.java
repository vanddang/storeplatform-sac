/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.other.ParentStatusRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.other.service.ProductStatusService;
import com.skplanet.storeplatform.sac.display.other.vo.ParentAppInfo;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 02. 18 Updated by : 정희원, SK 플래닛.
 */
@Controller
@RequestMapping("/display")
public class ProductStatusController {

	@Autowired
	private ProductStatusService productStatusService;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param partChannelId
	 *            partChannelId
	 * @param header
	 *            header
	 * @return ParentStatusRes
	 */
	@RequestMapping(value = "/other/parentStatus/get/v1", method = RequestMethod.GET)
	@ResponseBody
	public ParentStatusRes parentStatus(@RequestParam String partChannelId, SacRequestHeader header) {
		ParentAppInfo parentAppInfo = this.productStatusService.selectParentInfo(
				header.getTenantHeader().getTenantId(), partChannelId);

		if (parentAppInfo == null)
			throw new StorePlatformException("SAC_DSP_0009");

		ParentStatusRes parentStatusRes = new ParentStatusRes();
		parentStatusRes.setParentStatus(parentAppInfo.getParentStatus());
		parentStatusRes.setParentChannelId(parentAppInfo.getParentChannelId());

		return parentStatusRes;
	}

}
