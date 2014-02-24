/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceItemSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;

//import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacReq;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
@LocalSCI
public class ExistenceController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSacService existenceSacService;
	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * 기구매 체크 SAC.
	 * 
	 * @param existenceSacReq
	 *            요청정보
	 * @param bindingResult
	 *            Validated Result
	 * @param requestHeader
	 *            헤더정보
	 * @return ExistenceListSacRes 응답정보
	 */
	@RequestMapping(value = "/history/existence/search/v1", method = RequestMethod.POST)
	@ResponseBody
	public ExistenceListSacRes searchExistenceList(@RequestBody @Validated ExistenceSacReq existenceSacReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {

		TenantHeader header = requestHeader.getTenantHeader();

		// 필수값 체크
		// this.purchaseCommonUtils.getBindingValid(bindingResult);
		ExistenceListSacRes existenceListSacRes = new ExistenceListSacRes();

		existenceListSacRes.setExistenceList(this.resConvert(this.existenceSacService.searchExistenceList(this
				.reqConvert(existenceSacReq, header))));
		return existenceListSacRes;
	}

	/**
	 * reqConvert.
	 * 
	 * @param existenceSacReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return ExistenceScReq
	 */
	private ExistenceScReq reqConvert(ExistenceSacReq existenceSacReq, TenantHeader header) {

		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		this.logger.debug("@@@@@@ Start reqConvert @@@@@@");
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		ExistenceScReq req = new ExistenceScReq();
		List<ExistenceItemSc> productList = new ArrayList<ExistenceItemSc>();

		req.setTenantId(header.getTenantId());
		req.setUserKey(existenceSacReq.getUserKey());
		req.setDeviceKey(existenceSacReq.getDeviceKey());
		req.setPrchsId(existenceSacReq.getPrchsId());
		// 상품리스트가 없을시 제외
		if (existenceSacReq.getProductList() != null) {
			for (ExistenceItemSac existenceItemSac : existenceSacReq.getProductList()) {
				ExistenceItemSc existenceItemSc = new ExistenceItemSc();
				existenceItemSc.setProdId(existenceItemSac.getProdId());
				existenceItemSc.setTenantProdGrpCd(existenceItemSac.getTenantProdGrpCd());
				productList.add(existenceItemSc);
			}
		}
		req.setProductList(productList);

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param existenceListScRes
	 *            요청정보
	 * @return List<ExistenceSacRes>
	 */
	private List<ExistenceSacRes> resConvert(List<ExistenceScRes> existenceListScRes) {

		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		this.logger.debug("@@@@@@ Start resConvert @@@@@@");
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		List<ExistenceSacRes> res = new ArrayList<ExistenceSacRes>();
		for (ExistenceScRes existenceScRes : existenceListScRes) {
			ExistenceSacRes existenceSacRes = new ExistenceSacRes();
			existenceSacRes.setPrchsId(existenceScRes.getPrchsId());
			existenceSacRes.setProdId(existenceScRes.getProdId());

			res.add(existenceSacRes);
		}

		return res;
	}

}
