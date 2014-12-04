/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPriceSoldOutReq;

/**
 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록
 * 
 * Updated on : 2014. 12. 04. Updated by : 김형식
 */
@Service
public class UpdateSpecialPriceSoldOutServiceImpl implements UpdateSpecialPriceSoldOutService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * 
	 * <pre>
	 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록 한다.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return
	 */
	@Override
	public void updateSpecialPriceSoldOut(SpecialPriceSoldOutReq req) {

		this.log.debug("----------------------------------------------------------");
		this.log.debug("tenantId : {}", req.getTenantId());
		this.log.debug("productId : {}", req.getProductId());
		this.log.debug("lang : {}", req.getLang());
		this.log.debug("----------------------------------------------------------");

		Map<String, String> map = new HashMap<String, String>();
		int prodCnt = 0;
		map.put("tenantId", req.getTenantId());
		map.put("productId", req.getProductId());

		// TB_DP_SPRC_PROD 판매중인 상품 존재 유무 확인
		prodCnt = (Integer) this.commonDAO.queryForObject("SpecialPriceSoldOut.getSpecialPriceSoldOut", map);

		if (prodCnt < 1) {
			throw new StorePlatformException("SAC_DSP_0005", req.getProductId());
		} else {
			this.commonDAO.update("SpecialPriceSoldOut.updateSpecialPriceSoldOut", map);
		}

	}

}
