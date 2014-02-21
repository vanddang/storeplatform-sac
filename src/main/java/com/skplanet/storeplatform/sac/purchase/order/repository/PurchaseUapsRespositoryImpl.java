/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsEcReq;
import com.skplanet.storeplatform.external.client.uaps.vo.UserEcRes;

/**
 * 
 * 구매 처리 시, UAPS 연동
 * 
 * Updated on : 2014. 2. 13. Updated by : 이승택, nTels.
 */
@Component
public class PurchaseUapsRespositoryImpl implements PurchaseUapsRespository {

	@Autowired
	private UapsSCI uapsSCI;

	/**
	 * 
	 * <pre>
	 * UAPS 명의자인증 조회.
	 * </pre>
	 * 
	 * @param corpNum
	 *            법인번호
	 * @param mdn
	 *            해당 법인번호에 대한 법인폰 여부를 조회할 MDN
	 * @return 법인폰 여부: true-법인폰
	 */
	@Override
	public boolean searchUapsAuthorizeInfoByMdn(String corpNum, String mdn) {
		UapsEcReq uapsEcReq = new UapsEcReq();
		uapsEcReq.setCustId(corpNum);
		uapsEcReq.setDeviceId(mdn);
		uapsEcReq.setType("mdn");

		this.uapsSCI.getAuthorizeInfo(uapsEcReq);

		return true;
	}

	/**
	 * 
	 * <pre>
	 * 고객정보 조회.
	 * </pre>
	 * 
	 * @param mdn
	 *            SKT 시험폰 여부를 조회할 MDN
	 * @return 시험폰 여부: true-시험폰
	 */
	@Override
	public String searchUapsMappingInfoByMdn(String mdn) {
		UapsEcReq uapsEcReq = new UapsEcReq();
		uapsEcReq.setDeviceId(mdn);
		uapsEcReq.setType("mdn");

		UserEcRes userEcRes = this.uapsSCI.getMappingInfo(uapsEcReq);
		return userEcRes.getSvcTP();
	}

}
