/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.sci;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceInternalSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceRes;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@Controller
@LocalSCI
public class ExistenceInternalSCIController implements ExistenceInternalSacSCI {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSacService existenceSacService;

	/**
	 * 기구매 체크 SAC.
	 * 
	 * @param existenceReq
	 *            요청정보
	 * @return ExistenceListRes 응답정보
	 */
	@Override
	@ResponseBody
	public ExistenceListRes searchExistenceList(ExistenceReq existenceReq) {

		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		this.logger.debug("@@@@@@ Start Internal searchExistenceList @@@@@@");
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		// 필수값 체크
		if (existenceReq.getTenantId() == null || existenceReq.getTenantId().equals("")) {
			throw new StorePlatformException("SAC_PUR_0001", "TenantId");
		}

		List<ExistenceRes> res = new ArrayList<ExistenceRes>();

		ExistenceScReq req = this.reqConvert(existenceReq);
		res = this.resConvert(this.existenceSacService.searchExistenceList(req));

		ExistenceListRes existenceListRes = new ExistenceListRes();
		existenceListRes.setExistenceListRes(res);

		return existenceListRes;
	}

	/**
	 * reqConvert.
	 * 
	 * @param existenceReq
	 *            요청정보
	 * @return ExistenceScReq
	 */
	private ExistenceScReq reqConvert(ExistenceReq existenceReq) {

		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		this.logger.debug("@@@@@@ Start reqConvert @@@@@@");
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		ExistenceScReq req = new ExistenceScReq();
		List<ExistenceItemSc> existenceItemListSc = new ArrayList<ExistenceItemSc>();

		req.setTenantId(existenceReq.getTenantId());
		req.setUserKey(existenceReq.getUserKey());
		req.setDeviceKey(existenceReq.getDeviceKey());
		req.setPrchsId(existenceReq.getPrchsId());

		// 상품리스트가 없을시 제외
		if (existenceReq.getExistenceItem() != null) {
			int size = existenceReq.getExistenceItem().size();
			for (int i = 0; i < size; i++) {
				ExistenceItemSc existenceItemSc = new ExistenceItemSc();
				existenceItemSc.setProdId(existenceReq.getExistenceItem().get(i).getProdId());
				existenceItemSc.setTenantProdGrpCd(existenceReq.getExistenceItem().get(i).getTenantProdGrpCd());
				existenceItemListSc.add(existenceItemSc);
			}
		}
		req.setProductList(existenceItemListSc);

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param existenceListScRes
	 *            요청정보
	 * @return List<ExistenceRes>
	 */
	private List<ExistenceRes> resConvert(List<ExistenceScRes> existenceListScRes) {

		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		this.logger.debug("@@@@@@ Start resConvert @@@@@@");
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		List<ExistenceRes> res = new ArrayList<ExistenceRes>();
		int size = existenceListScRes.size();
		for (int i = 0; i < size; i++) {
			ExistenceRes existenceRes = new ExistenceRes();
			existenceRes.setPrchsId(existenceListScRes.get(i).getPrchsId());
			existenceRes.setProdId(existenceListScRes.get(i).getProdId());

			res.add(existenceRes);
		}

		return res;
	}

}
