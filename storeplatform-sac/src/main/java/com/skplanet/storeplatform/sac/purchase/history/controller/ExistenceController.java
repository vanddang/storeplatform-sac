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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceItemSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacReqV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacResV2;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
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
	 * 기구매 체크 SAC. V1
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

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		NetworkHeader networkHeader = requestHeader.getNetworkHeader();
		this.logger.info("PRCHS,ExistenceController,SAC,REQ,{},{}", existenceSacReq, requestHeader);

		ExistenceListSacRes existenceListSacRes = new ExistenceListSacRes();

		existenceListSacRes.setExistenceList(this.resConvert(this.existenceSacService.searchExistenceList(
				this.reqConvert(existenceSacReq, tenantHeader), networkHeader.getType())));
		this.logger.info("PRCHS,ExistenceController,SAC,existenceListSacRes,{}", existenceListSacRes);
		return existenceListSacRes;
	}

	/**
	 * 기구매 체크 SAC. V2
	 * 
	 * @param existenceSacReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return ExistenceListSacRes 응답정보
	 */
	@RequestMapping(value = "/history/existence/search/v2", method = RequestMethod.POST)
	@ResponseBody
	public ExistenceSacResV2 searchExistenceListV2(@RequestBody @Validated ExistenceSacReqV2 existenceSacReqV2,
			SacRequestHeader requestHeader) {

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		NetworkHeader networkHeader = requestHeader.getNetworkHeader();

		this.logger.info("PRCHS,ExistenceController V2,SAC,REQ,{},{}", existenceSacReqV2, requestHeader);

		existenceSacReqV2.setSystemId(tenantHeader.getSystemId());
		ExistenceSacResV2 existenceSacResV2 = this.existenceSacService.searchExistenceListV2(existenceSacReqV2,
				networkHeader.getType());

		this.logger.info("PRCHS,ExistenceController,SAC,existenceListSacRes,{}", existenceSacResV2);
		return existenceSacResV2;
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

		if (StringUtils.isBlank(existenceSacReq.getPrchsId())) {
			if (StringUtils.isBlank(existenceSacReq.getUserKey())) {
				throw new StorePlatformException("SAC_PUR_0001", "UserKey");
			}
			if (StringUtils.isBlank(existenceSacReq.getDeviceKey())) {
				throw new StorePlatformException("SAC_PUR_0001", "DeviceKey");
			}
			if (existenceSacReq.getProductList() == null) {
				throw new StorePlatformException("SAC_PUR_0001", "ProductList");
			} else if (existenceSacReq.getProductList().size() < 1) {
				throw new StorePlatformException("SAC_PUR_0001", "ProdId");
			}
		}
		req.setTenantId(header.getTenantId());
		req.setSystemId(header.getSystemId());
		req.setUserKey(existenceSacReq.getUserKey());
		req.setDeviceKey(existenceSacReq.getDeviceKey());
		req.setPrchsId(existenceSacReq.getPrchsId());
		req.setCheckValue(false);

		// 해당값이 Y일 경우 무조건 디바이스 기반구매내역 조회( Y일 경우 deviceKey 필수체크)
		if (StringUtils.equals("Y", existenceSacReq.getDeviceHistoryYn())) {
			if (StringUtils.isBlank(existenceSacReq.getDeviceKey())) {
				throw new StorePlatformException("SAC_PUR_0001", "DeviceKey");
			}
			req.setCheckValue(true);
		}

		// 상품리스트가 없을시 제외
		if (existenceSacReq.getProductList() != null) {
			for (ExistenceItemSac existenceItemSac : existenceSacReq.getProductList()) {
				if (StringUtils.isBlank(existenceItemSac.getProdId())) {
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
	 * reqConvert.
	 * 
	 * @param existenceSacReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return ExistenceScReq
	 */
	// private ExistenceSacReqV2 reqConvertV2(ExistenceSacReqV2 existenceSacReqV2, TenantHeader header) {
	//
	// this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	// this.logger.debug("@@@@@@ Start reqConvert @@@@@@");
	// this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//
	// for (ExistenceSacReq userList : existenceSacReqV2.getUserList()) {
	//
	// if (StringUtils.isBlank(userList.getPrchsId())) {
	// if (StringUtils.isBlank(userList.getUserKey())) {
	// throw new StorePlatformException("SAC_PUR_0001", "UserKey");
	// }
	// if (StringUtils.isBlank(userList.getDeviceKey())) {
	// throw new StorePlatformException("SAC_PUR_0001", "DeviceKey");
	// }
	// if (userList.getProductList() == null) {
	// throw new StorePlatformException("SAC_PUR_0001", "ProductList");
	// } else if (userList.getProductList().size() < 1) {
	// throw new StorePlatformException("SAC_PUR_0001", "ProdId");
	// }
	// }
	// existenceSacReqV2.setTenantId(header.getTenantId());
	// existenceSacReqV2.setSystemId(header.getSystemId());
	//
	// // 상품리스트가 없을시 제외
	// if (userList.getProductList() != null) {
	// for (ExistenceItemSac existenceItemSac : userList.getProductList()) {
	// if (StringUtils.isBlank(existenceItemSac.getProdId())) {
	// throw new StorePlatformException("SAC_PUR_0001", "ProdId");
	// }
	// }
	// }
	// }
	//
	// return existenceSacReqV2;
	// }

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
		// this.logger.info("PRCHS,ExistenceController,SAC,RES,{}", res);
		return res;
	}

	/**
	 * resConvert.
	 * 
	 * @param existenceListScRes
	 *            요청정보
	 * @return List<ExistenceSacRes>
	 */
	// private List<ExistenceSacRes> resConvertV2(List<ExistenceScRes> existenceListScRes) {
	//
	// this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	// this.logger.debug("@@@@@@ Start resConvert @@@@@@");
	// this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//
	// List<ExistenceSacRes> res = new ArrayList<ExistenceSacRes>();
	// for (ExistenceScRes existenceScRes : existenceListScRes) {
	// ExistenceSacRes existenceSacRes = new ExistenceSacRes();
	// existenceSacRes.setPrchsId(existenceScRes.getPrchsId());
	// existenceSacRes.setProdId(existenceScRes.getProdId());
	//
	// res.add(existenceSacRes);
	// }
	// // this.logger.info("PRCHS,ExistenceController,SAC,RES,{}", res);
	// return res;
	// }

	/**
	 * 기구매 체크 SAC. V2
	 * 
	 * @param existenceSacReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return ExistenceListSacRes 응답정보
	 */
	// @RequestMapping(value = "/history/existence/search/v2", method = RequestMethod.POST)
	// @ResponseBody
	// public ExistenceSacResV2 searchExistenceListV2(@RequestBody @Validated ExistenceSacReqV2 existenceSacReqV2,
	// SacRequestHeader requestHeader) {
	//
	// TenantHeader tenantHeader = requestHeader.getTenantHeader();
	// NetworkHeader networkHeader = requestHeader.getNetworkHeader();
	// this.logger.info("PRCHS,ExistenceController V2,SAC,REQ,{},{}", existenceSacReqV2, requestHeader);
	//
	// existenceSacReqV2 = this.reqConvertV2(existenceSacReqV2, tenantHeader);
	// ExistenceSacResV2 existenceSacResV2 = this.existenceSacService.searchExistenceListV2(existenceSacReqV2,
	// networkHeader.getType());
	//
	// this.logger.info("PRCHS,ExistenceController,SAC,existenceListSacRes,{}", existenceSacResV2);
	// return existenceSacResV2;
	// }
}
