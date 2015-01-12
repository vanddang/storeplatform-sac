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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
	 * <pre>
	 * Header 정보를 셋팅한다.
	 * </pre>
	 * 
	 * @param purchaseHeaderSacReq
	 * @param sacRequestHeader
	 */
	public void setHeader(PurchaseHeaderSacReq purchaseHeaderSacReq, SacRequestHeader sacRequestHeader) {
		purchaseHeaderSacReq.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		purchaseHeaderSacReq.setSystemId(sacRequestHeader.getTenantHeader().getSystemId());
		purchaseHeaderSacReq.setLangCd(sacRequestHeader.getTenantHeader().getLangCd());

		purchaseHeaderSacReq.setModel(sacRequestHeader.getDeviceHeader().getModel());
		purchaseHeaderSacReq.setDpi(sacRequestHeader.getDeviceHeader().getDpi());
		purchaseHeaderSacReq.setResolution(sacRequestHeader.getDeviceHeader().getResolution());
		purchaseHeaderSacReq.setOs(sacRequestHeader.getDeviceHeader().getOs());
		purchaseHeaderSacReq.setPkg(sacRequestHeader.getDeviceHeader().getPkg());
		purchaseHeaderSacReq.setSvc(sacRequestHeader.getDeviceHeader().getSvc());

		purchaseHeaderSacReq.setType(sacRequestHeader.getNetworkHeader().getType());
		purchaseHeaderSacReq.setOperator(sacRequestHeader.getNetworkHeader().getOperator());
		purchaseHeaderSacReq.setSimOperator(sacRequestHeader.getNetworkHeader().getSimOperator());
	}
}
