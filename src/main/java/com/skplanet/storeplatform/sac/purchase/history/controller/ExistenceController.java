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
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
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
public class ExistenceController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSacService existenceSacService;

	/**
	 * 기구매 체크 SAC.
	 * 
	 * @param existenceSacReq
	 *            요청정보
	 * @param bindingResult
	 *            Validated Result
	 * @param requestHeader
	 *            헤더정보
	 * @return List<ExistenceRes> 응답정보
	 */
	@RequestMapping(value = "/history/existence/search/v1", method = RequestMethod.POST)
	@ResponseBody
	public ExistenceListSacRes searchExistenceList(@RequestBody @Validated ExistenceSacReq existenceSacReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {

		TenantHeader header = requestHeader.getTenantHeader();

		// 필수값 체크
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				throw new StorePlatformException("SAC_PUR_0001", error.getField());
			}
		}

		List<ExistenceSacRes> res = new ArrayList<ExistenceSacRes>();

		ExistenceScReq req = this.reqConvert(existenceSacReq, header);
		res = this.resConvert(this.existenceSacService.searchExistenceList(req));

		ExistenceListSacRes existenceListRes = new ExistenceListSacRes();
		existenceListRes.setExistenceListRes(res);

		return existenceListRes;
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
		List<ExistenceItemSc> existenceItemListSc = new ArrayList<ExistenceItemSc>();

		req.setTenantId(header.getTenantId());
		req.setInsdUsermbrNo(existenceSacReq.getInsdUsermbrNo());
		req.setInsdDeviceId(existenceSacReq.getInsdDeviceId());
		req.setPrchsId(existenceSacReq.getPrchsId());
		// 상품리스트가 없을시 제외
		if (existenceSacReq.getExistenceItemSac() != null) {
			int size = existenceSacReq.getExistenceItemSac().size();
			for (int i = 0; i < size; i++) {
				ExistenceItemSc existenceItemSc = new ExistenceItemSc();
				existenceItemSc.setProdId(existenceSacReq.getExistenceItemSac().get(i).getProdId());
				existenceItemSc.setTenantProdGrpCd(existenceSacReq.getExistenceItemSac().get(i).getTenantProdGrpCd());
				existenceItemListSc.add(existenceItemSc);
			}
		}
		req.setExistenceItemSc(existenceItemListSc);

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
		int size = existenceListScRes.size();
		for (int i = 0; i < size; i++) {
			ExistenceSacRes existenceSacRes = new ExistenceSacRes();
			existenceSacRes.setPrchsId(existenceListScRes.get(i).getPrchsId());
			existenceSacRes.setProdId(existenceListScRes.get(i).getProdId());

			res.add(existenceSacRes);
		}

		return res;
	}

}
