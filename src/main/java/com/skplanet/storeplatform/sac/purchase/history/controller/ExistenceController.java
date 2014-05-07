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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
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
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;

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

	/**
	 * 기구매 체크 SAC.
	 * 
	 * @param existenceSacReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return ExistenceListSacRes 응답정보
	 */
	@RequestMapping(value = "/history/existence/search/v1", method = RequestMethod.POST)
	@ResponseBody
	public ExistenceListSacRes searchExistenceList(@RequestBody @Validated ExistenceSacReq existenceSacReq,
			SacRequestHeader requestHeader) {

		TenantHeader header = requestHeader.getTenantHeader();
		// final String systemId = header.getSystemId();

		// new TLogUtil().set(new ShuttleSetter() {
		// @Override
		// public void customize(TLogSentinelShuttle shuttle) {
		// shuttle.log_id("TL_SAC_PUR_0002").system_id(systemId).insd_device_id(existenceSacReq.getDeviceKey())
		// .insd_usermbr_no(existenceSacReq.getUserKey());
		// ;
		// }
		// });

		this.logger.debug("PRCHS,ExistenceController,SAC,REQ,{},{}", existenceSacReq, requestHeader);

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

		if (StringUtils.isEmpty(existenceSacReq.getPrchsId())) {
			if (StringUtils.isEmpty(existenceSacReq.getUserKey())) {
				throw new StorePlatformException("SAC_PUR_0001", "UserKey");
			}
			if (StringUtils.isEmpty(existenceSacReq.getDeviceKey())) {
				throw new StorePlatformException("SAC_PUR_0001", "DeviceKey");
			}
		}
		req.setTenantId(header.getTenantId());
		req.setSystemId(header.getSystemId());
		req.setUserKey(existenceSacReq.getUserKey());
		req.setDeviceKey(existenceSacReq.getDeviceKey());
		req.setPrchsId(existenceSacReq.getPrchsId());
		req.setCheckValue(false);

		// 상품리스트가 없을시 제외
		if (existenceSacReq.getProductList() != null) {
			for (ExistenceItemSac existenceItemSac : existenceSacReq.getProductList()) {
				if (StringUtils.isEmpty(existenceItemSac.getProdId())) {
					throw new StorePlatformException("SAC_PUR_0001", "ProdId");
				}
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
		// this.logger.debug("PRCHS,ExistenceController,SAC,RES,{}", res);
		return res;
	}

}
