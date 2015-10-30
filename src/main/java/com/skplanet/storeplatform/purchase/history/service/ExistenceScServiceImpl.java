/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScV2Req;
import com.skplanet.storeplatform.purchase.history.vo.Exist;

/**
 * 구매 서비스 인터페이스 구현체
 * 
 * Updated on : 2013-12-10 Updated by : 조용진, 엔텔스.
 */
@Service
public class ExistenceScServiceImpl implements ExistenceScService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 기구매 체크.
	 * 
	 * @param existenceScReq
	 *            요청정보
	 * @return List<ExistenceScRes>
	 */
	@Override
	public List<ExistenceScRes> searchExistenceList(ExistenceScReq existenceScReq) {

		// return List<data>
		List<ExistenceScRes> existenceListScRes = new ArrayList<ExistenceScRes>();
		// sql 조회 셋팅을 위한 객체
		Exist exist = new Exist();

		// 유저정보 셋팅
		exist.setTenantId(existenceScReq.getTenantId());
		exist.setUserKey(existenceScReq.getUserKey());
		exist.setDeviceKey(existenceScReq.getDeviceKey());
		exist.setPrchsId(existenceScReq.getPrchsId());
		exist.setCheckValue(existenceScReq.isCheckValue());
		// Req prod list size만큼 loop
		int size = 0;
		if (existenceScReq.getProductList() != null) {
			size = existenceScReq.getProductList().size();
		}
		this.logger.debug("=size========== {}:", size);
		if (size > 0) {

			for (int i = 0; i < size; i++) {

				// 상품정보 셋팅
				exist.setProdId(existenceScReq.getProductList().get(i).getProdId());
				exist.setTenantProdGrpCd(existenceScReq.getProductList().get(i).getTenantProdGrpCd());

				List<ExistenceScRes> resultList = (List<ExistenceScRes>) this.commonDAO.queryForList(
						"PurchaseExistence.purchaseExist", exist);
				this.logger.debug("=====resultList.size====== {}:", resultList.size());
				// size만큼의 상품 조회후 add
				for (ExistenceScRes existenceScRes : resultList) {
					existenceListScRes.add(existenceScRes);
				}
			}
		} else {
			existenceListScRes = (List<ExistenceScRes>) this.commonDAO.queryForList("PurchaseExistence.purchaseExist",
					exist);
		}
		return existenceListScRes;
	}

	/**
	 * 기구매 체크.
	 * 
	 * @param existenceScReq
	 *            요청정보
	 * @return List<ExistenceScRes>
	 */
	@Override
	public List<ExistenceScRes> searchExistenceListV2(ExistenceScV2Req existenceScV2Req) {

		this.logger.info("####################### {} ", existenceScV2Req);

		return this.commonDAO.queryForList("PurchaseExistence.purchaseExistV2", existenceScV2Req, ExistenceScRes.class);
	}

}
