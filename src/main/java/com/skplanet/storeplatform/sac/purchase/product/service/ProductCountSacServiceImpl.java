/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.product.sci.ProductCountSCI;
import com.skplanet.storeplatform.purchase.client.product.vo.GetProductCountScReq;
import com.skplanet.storeplatform.purchase.client.product.vo.GetProductCountScRes;
import com.skplanet.storeplatform.sac.purchase.product.vo.GetProductCountSacReq;
import com.skplanet.storeplatform.sac.purchase.product.vo.GetProductCountSacRes;
import com.skplanet.storeplatform.sac.purchase.product.vo.SetProductCountSacReq;
import com.skplanet.storeplatform.sac.purchase.product.vo.SetProductCountSacRes;

/**
 * 상품 구매가능 갯수 GET, SET 처리
 * 
 * Updated on : 2014. 2. 13. Updated by : 조용진, NTELS.
 */
@Service
@Transactional
public class ProductCountSacServiceImpl implements ProductCountSacService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductCountSCI productCountSCI;

	/**
	 * 상품 구매가능 갯수 GET처리 .
	 * 
	 * @param getProductCountSacReq
	 *            요청정보
	 * @return GetProductCountSacRes
	 */
	@Override
	public GetProductCountSacRes searchProductCount(GetProductCountSacReq getProductCountSacReq) {
		GetProductCountSacRes getProductCountSacRes = new GetProductCountSacRes();

		getProductCountSacRes = this.resConvert(this.productCountSCI.searchProductCount(this
				.reqConvert(getProductCountSacReq)));

		return getProductCountSacRes;
	}

	/**
	 * 상품 구매가능 갯수 SET처리 .
	 * 
	 * @param setProductCountSacReq
	 *            요청정보
	 * @return SetProductCountSacRes
	 */
	@Override
	public SetProductCountSacRes createProductCount(SetProductCountSacReq setProductCountSacReq) {

		GetProductCountSacRes getProductCountSacRes = new GetProductCountSacRes();
		// getProductCountSacRes = productCountSCI.createProductCount(setProductCountScReq);
		return null;
	}

	/**
	 * 상품 구매가능 갯수 GET처리 reqConvert.
	 * 
	 * @param getProductCountSacReq
	 *            요청정보
	 * @return GetProductCountScReq
	 */
	private GetProductCountScReq reqConvert(GetProductCountSacReq getProductCountSacReq) {
		GetProductCountScReq req = new GetProductCountScReq();

		req.setProdId(getProductCountSacReq.getProdId());
		req.setPrchsDt(getProductCountSacReq.getPrchsDt());
		return req;
	}

	/**
	 * 상품 구매가능 갯수 GET처리 resConvert.
	 * 
	 * @param getProductCountScRes
	 *            응답정보
	 * @return GetProductCountSacRes
	 */
	private GetProductCountSacRes resConvert(GetProductCountScRes getProductCountScRes) {
		GetProductCountSacRes res = new GetProductCountSacRes();
		res.setPrchsAmt(getProductCountScRes.getPrchsAmt());
		res.setPrchsCnt(getProductCountScRes.getPrchsCnt());
		res.setPrchsDay(getProductCountScRes.getPrchsDay());
		res.setPrchsDayAmt(getProductCountScRes.getPrchsDayAmt());
		res.setPrchsDayCnt(getProductCountScRes.getPrchsDayCnt());
		res.setPrchsMn(getProductCountScRes.getPrchsMn());
		res.setPrchsMnAmt(getProductCountScRes.getPrchsMnAmt());
		res.setPrchsMnCnt(getProductCountScRes.getPrchsMnCnt());
		res.setProdId(getProductCountScRes.getProdId());

		return res;
	}
}
