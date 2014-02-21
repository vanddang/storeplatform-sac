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

/**
 * 
 * 구매 처리 시, UAPS 연동
 * 
 * Updated on : 2014. 2. 13. Updated by : 이승택, nTels.
 */
public interface PurchaseUapsRespository {

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
	public boolean searchUapsAuthorizeInfoByMdn(String corpNum, String mdn);

	/**
	 * 
	 * <pre>
	 * UAPS MDN Mapping 정보 조회.
	 * </pre>
	 * 
	 * @param mdn
	 *            SKT 시험폰 여부를 조회할 MDN
	 * @return 시험폰 여부: true-시험폰
	 */
	public String searchUapsMappingInfoByMdn(String mdn);
}
