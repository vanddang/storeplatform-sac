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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.payplanet.sci.PaymentMethodPointSearchSCI;
import com.skplanet.storeplatform.external.client.payplanet.vo.CultureEcReq;
import com.skplanet.storeplatform.external.client.payplanet.vo.CultureEcRes;
import com.skplanet.storeplatform.external.client.payplanet.vo.DotoriEcReq;
import com.skplanet.storeplatform.external.client.payplanet.vo.DotoriEcRes;
import com.skplanet.storeplatform.external.client.payplanet.vo.OkCashBagEcReq;
import com.skplanet.storeplatform.external.client.payplanet.vo.OkCashBagEcRes;
import com.skplanet.storeplatform.external.client.payplanet.vo.TMemberShipEcReq;
import com.skplanet.storeplatform.external.client.payplanet.vo.TMemberShipEcRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.CultureSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.CultureSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.DotoriSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.DotoriSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.OkCashBagSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.OkCashBagSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.TMemberShipSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.payplanet.TMemberShipSacRes;

/**
 * Culture Service 구현.
 * 
 * Updated on : 2014. 3. 4. Updated by : 조용진, NTELS.
 */
@Service
public class PaymentMethodPointSearchSacServiceImpl implements PaymentMethodPointSearchSacService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentMethodPointSearchSacServiceImpl.class);

	@Autowired
	private PaymentMethodPointSearchSCI paymentMethodPointSearchSCI;

	/**
	 * 
	 * <pre>
	 * TMemberShip point 조회.
	 * </pre>
	 * 
	 * @param request
	 *            요청
	 * @return TMemberShipSacRes
	 */
	@Override
	public TMemberShipSacRes postTMemberShip(TMemberShipSacReq request) {

		TMemberShipEcReq req = new TMemberShipEcReq();

		req.setToken(request.getToken());
		req.setTimeReq(request.getTimeReq());
		req.setMid(request.getMid());
		req.setNoTmsCard(request.getNoTmsCard());
		req.setDtBirth(request.getDtBirth());

		TMemberShipEcRes tMemberShipEcRes = this.paymentMethodPointSearchSCI.postTMemberShip(req);

		TMemberShipSacRes res = new TMemberShipSacRes();
		res.setCdResult(tMemberShipEcRes.getCode());
		res.setMsgResult(tMemberShipEcRes.getMsg());
		res.setTmsPoint(tMemberShipEcRes.getTmsPoint());
		this.logger.debug("PRCHS,TMEMBERSHIP,SAC,RES,{}", res);
		return res;
	}

	/**
	 * 
	 * <pre>
	 * okcashbag point 조회.
	 * </pre>
	 * 
	 * @param request
	 *            요청
	 * @return OkCashBagSacRes
	 */
	@Override
	public OkCashBagSacRes postOkCashBag(OkCashBagSacReq request) {

		OkCashBagEcReq req = new OkCashBagEcReq();

		req.setToken(request.getToken());
		req.setTimeReq(request.getTimeReq());
		req.setMid(request.getMid());
		req.setValueOcbAuth(request.getValueOcbAuth());
		req.setOcbPwd(request.getOcbPwd());

		OkCashBagEcRes okCashBagEcRes = this.paymentMethodPointSearchSCI.postOkCashBag(req);

		OkCashBagSacRes res = new OkCashBagSacRes();
		res.setCdResult(okCashBagEcRes.getCode());
		res.setMsgResult(okCashBagEcRes.getMsg());
		res.setOcbPoint(okCashBagEcRes.getOcbPoint());
		this.logger.debug("PRCHS,OKCASHBAG,SAC,RES,{}", res);
		return res;
	}

	/**
	 * 
	 * <pre>
	 * 도토리 조회.
	 * </pre>
	 * 
	 * @param request
	 *            요청
	 * @return DotoriSacRes
	 */
	@Override
	public DotoriSacRes postDotori(DotoriSacReq request) {

		DotoriEcReq req = new DotoriEcReq();

		req.setToken(request.getToken());
		req.setTimeReq(request.getTimeReq());
		req.setMid(request.getMid());
		req.setMdn(request.getMdn());

		DotoriEcRes dotoriEcRes = this.paymentMethodPointSearchSCI.postDotori(req);

		DotoriSacRes res = new DotoriSacRes();
		res.setCdResult(dotoriEcRes.getCode());
		res.setMsgResult(dotoriEcRes.getMsg());
		res.setCntDotori(dotoriEcRes.getCntDotori());
		this.logger.debug("PRCHS,DOTORI,SAC,RES,{}", res);
		return res;
	}

	/**
	 * 
	 * <pre>
	 * 문화상품권 조회.
	 * </pre>
	 * 
	 * @param request
	 *            요청
	 * @return CultureSacRes
	 */
	@Override
	public CultureSacRes postCulture(CultureSacReq request) {
		CultureEcReq req = new CultureEcReq();

		req.setToken(request.getToken());
		req.setTimeReq(request.getTimeReq());
		req.setMid(request.getMid());
		req.setCultureId(request.getCultureId());
		req.setCulturePwd(request.getCulturePwd());

		CultureEcRes cultureEcRes = this.paymentMethodPointSearchSCI.postCulture(req);

		CultureSacRes res = new CultureSacRes();
		res.setCdResult(cultureEcRes.getCode());
		res.setMsgResult(cultureEcRes.getMsg());
		res.setCulturePoint(cultureEcRes.getCulturePoint());
		this.logger.debug("PRCHS,CULTURE,SAC,RES,{}", res);
		return res;
	}

}
