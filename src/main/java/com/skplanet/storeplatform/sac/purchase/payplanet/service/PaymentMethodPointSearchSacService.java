/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.payplanet.service;

import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.CultureSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.CultureSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.DotoriSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.DotoriSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.OkCashBagSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.OkCashBagSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.TMemberShipSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.TMemberShipSacRes;

/**
 * Culture Service interface.
 * 
 * Updated on : 2014. 3. 4. Updated by : 조용진, NTELS.
 */
public interface PaymentMethodPointSearchSacService {

	/**
	 * 
	 * <pre>
	 * 문화상품권 조회.
	 * </pre>
	 * 
	 * @param cultureSacReq
	 *            요청
	 * @return CultureSacRes
	 */
	public CultureSacRes postCulture(CultureSacReq cultureSacReq);

	/**
	 * 
	 * <pre>
	 * 도토리 조회.
	 * </pre>
	 * 
	 * @param dotoriSacReq
	 *            요청
	 * @return DotoriSacRes
	 */
	public DotoriSacRes postDotori(DotoriSacReq dotoriSacReq);

	/**
	 * 
	 * <pre>
	 * okcashbag point 조회.
	 * </pre>
	 * 
	 * @param okCashBagSacReq
	 *            요청
	 * @return OkCashBagSacRes
	 */
	public OkCashBagSacRes postOkCashBag(OkCashBagSacReq okCashBagSacReq);

	/**
	 * 
	 * <pre>
	 * TMemberShip point 조회.
	 * </pre>
	 * 
	 * @param tMemberShipSacReq
	 *            요청
	 * @return TMemberShipSacRes
	 */
	public TMemberShipSacRes postTMemberShip(TMemberShipSacReq tMemberShipSacReq);
}
