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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacRes;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

/**
 * 특가 상품 구매수 업데이트
 * 
 * Updated on : 2015. 03. 26. Updated by : 김형식
 */
@Service
public class UpdateSpecialPurchaseCountServiceImpl implements UpdateSpecialPurchaseCountService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * 입력받은 상품의 구매수를 업데이트 한다
	 * 
	 * @param req
	 *            req
	 * @return SpecialPurchaseCountSacRes  상품 정보 .            
	 */
	@Override
	public SpecialPurchaseCountSacRes updateSpecialPurchaseCount(SpecialPurchaseCountSacReq req) {
		SpecialPurchaseCountSacRes res = new SpecialPurchaseCountSacRes();

		if (StringUtils.isEmpty(req.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}

		if (StringUtils.isEmpty(req.getPurchaseId())) {
			throw new StorePlatformException("SAC_DSP_0002", "purchaseId", req.getPurchaseId());
		}

		if (StringUtils.isEmpty(req.getProductId())) {
			throw new StorePlatformException("SAC_DSP_0002", "productId", req.getProductId());
		}

		if (StringUtils.isEmpty(req.getPurchaseStatusCd())) {
			throw new StorePlatformException("SAC_DSP_0002", "purchaseStatusCd", req.getPurchaseStatusCd());
		}else{
			if (!"OR000301".equals(req.getPurchaseStatusCd()) && !"OR000302".equals(req.getPurchaseStatusCd())) {
				throw new StorePlatformException("SAC_DSP_0003", "purchaseStatusCd", req.getPurchaseStatusCd());
			}			
		}

		if (req.getPurchaseCount() == null) {
			throw new StorePlatformException("SAC_DSP_0002", "purchaseCount", req.getPurchaseCount());
		}else{
			if(req.getPurchaseStatusCd().equals("OR000301")){
				if(req.getPurchaseCount() <=0){	// 무조건 양수 (음수로 올경우 에러)
					throw new StorePlatformException("SAC_DSP_0003", "purchaseCount", req.getPurchaseCount());
				}
			}else{
				if(req.getPurchaseCount() >=0){	// 무조건 음수 (양수로 올경우 에러)
					throw new StorePlatformException("SAC_DSP_0003", "purchaseCount", req.getPurchaseCount());
				}
			}
		}

		if (StringUtils.isEmpty(req.getPurchaseDate())) {
			throw new StorePlatformException("SAC_DSP_0002", "purchaseDate", req.getPurchaseDate());
		}
		
		if(req.getPurchaseStatusCd().equals("OR000302")){
			if (StringUtils.isEmpty(req.getPurchaseCancelDate())) {
				throw new StorePlatformException("SAC_DSP_0002", "purchaseCancelDate", req.getPurchaseCancelDate());
			}
			
		}
		

		boolean successFlag = true;
		Map<String, String> map = new HashMap<String, String>();

		map.put("tenantId", req.getTenantId());
		map.put("purchaseId", req.getPurchaseId());
		map.put("productId", req.getProductId());
		map.put("purchaseStatusCd", req.getPurchaseStatusCd());
		map.put("purchaseDate", req.getPurchaseDate());
		map.put("purchaseCancelDate", req.getPurchaseCancelDate());

		this.log.info("----------------------------------------------------------");
		this.log.info("tenantId : {}", req.getTenantId());
		this.log.info("purchaseId : {}", req.getPurchaseId());
		this.log.info("productId : {}", req.getProductId());
		this.log.info("purchaseStatusCd : {}", req.getPurchaseStatusCd());
		this.log.info("purchaseCount : {}", req.getPurchaseCount());
		this.log.info("purchaseDate : {}", req.getPurchaseDate());
		this.log.info("purchaseCancelDate : {}", req.getPurchaseCancelDate());
		this.log.info("----------------------------------------------------------");		

		int specialProdSize = 3;

		// 특가 상품 여부 조회
		if(req.getPurchaseStatusCd().equals("OR000301")){
			Integer specialProductCount = this.commonDAO.queryForObject("SpecialPurchaseCount.getSpecialProductCount", map,
					Integer.class);
			if(specialProductCount <=0 ){
				throw new StorePlatformException("SAC_DSP_0005", "특가");
			}
		}
		
		

		// 상품 기본 정보(상품군) 조회
		ProductBasicInfo productBasicInfo = this.commonDAO.queryForObject("SpecialPurchaseCount.getProductInfo", map,
				ProductBasicInfo.class);

		if (productBasicInfo != null) {
			this.log.debug("----------------------------------------------------------");
			this.log.debug("partProdId : {}", productBasicInfo.getPartProdId());
			this.log.debug("catalogId : {}", productBasicInfo.getCatalogId());
			this.log.debug("prodId : {}", productBasicInfo.getProdId());
			this.log.debug("----------------------------------------------------------");
			if (StringUtils.isEmpty(productBasicInfo.getPartProdId())) {
				throw new StorePlatformException("SAC_DSP_0002", "partProdId", productBasicInfo.getPartProdId());
			}
			if (StringUtils.isEmpty(productBasicInfo.getProdId())) {
				throw new StorePlatformException("SAC_DSP_0002", "prodId", productBasicInfo.getProdId());
			}
			if (StringUtils.isEmpty(productBasicInfo.getCatalogId())) {
				throw new StorePlatformException("SAC_DSP_0002", "catalogId", productBasicInfo.getCatalogId());
			}

			for (int kk = 0; kk < specialProdSize; kk++) {
				if (kk == 0) {
					map.put("productId", productBasicInfo.getPartProdId()); // 특가 상품 에피소드
				} else if (kk == 1) {
					map.put("productId", productBasicInfo.getProdId()); // 특가 상품 채널
				} else if (kk == 2) {
					map.put("productId", productBasicInfo.getCatalogId()); // 특가 상품 카탈로그
				}
				if(successFlag){
//					successFlag = this.updateSpecialPurchaseCount(map, req.getPurchaseCount()); // 특가상품 Update 로직 실행
				}
			}
		}else{
			throw new StorePlatformException("SAC_DSP_0003", "productId", req.getProductId());
		}

		res.setProductId(req.getProductId());
		res.setPurchaseId(req.getPurchaseId());

		if(successFlag){
			res.setPurchaseCountStatus("PD003700");
		}else{
			res.setPurchaseCountStatus("PD003701");
		}
		return res;
	}

	/**
	 * 특가 상품 구매 건수 업데이트.
	 * 
	 * @param map
	 *            map
	 * @param reqPurchsCnt
	 *            reqPurchsCnt
	 */
	private boolean updateSpecialPurchaseCount(Map<String, String> map, int reqPurchsCnt) {
		boolean flag = true;
		try {
			int prodCnt = 0;
			int prchsCnt = 0;
			int beforeProdCnt =0;
			int beforePrchsCnt = 0;
			
			
			// (기존 데이터 00:00:00) - 취소인 경우  변경전 데이터  취소함  S
			if(map.get("purchaseStatusCd").equals("OR000302")){
				beforeProdCnt = (Integer) this.commonDAO.queryForObject("SpecialPurchaseCount.getSpecialProdBeforeCount", map);
				
				if (beforeProdCnt > 0) {
					map.put("purchaseCount", Integer.toString(reqPurchsCnt));
					beforePrchsCnt = (Integer) this.commonDAO.queryForObject("SpecialPurchaseCount.getSpecialPurchaseBeforeCount", map);
					// 해당상품의 구매수 + 업데이트 구매수가 0보다 작으면 현재 상품의 구매 수만큼 -해서 상품구매수를 0으로 되게 SET
					if (beforePrchsCnt + reqPurchsCnt < 0) {
						Integer resetCount = 0 - beforePrchsCnt; // 상품의 현재 구매수만큼 -해주기 위한 변수
						map.put("purchaseCount", resetCount.toString());
					}

					// channel 및 catalog Id에 대한 update 실행
					this.commonDAO.update("SpecialPurchaseCount.updateSpecialPurchaseBeforeCount", map);
					return flag;
				}
			}
			// (기존 데이터 00:00:00) - 취소인 경우  변경전 데이터  취소함  E			
			
			// TB_DP_SPRC_PROD_PRCHS_MANG 상품 존재 유무 확인
			prodCnt = (Integer) this.commonDAO.queryForObject("SpecialPurchaseCount.getSpecialProdCount", map);

			if (prodCnt < 1) {
				// 대상 상품이 없으면 해당 상품 insert 실행 Insert 시 구매수는 Default 0으로 Set
				map.put("purchaseCount", "0");
				this.commonDAO.update("SpecialPurchaseCount.insertSpecialPurchaseProd", map);
			}

			map.put("purchaseCount", Integer.toString(reqPurchsCnt));

			prchsCnt = (Integer) this.commonDAO.queryForObject("SpecialPurchaseCount.getSpecialPurchaseCount", map);

			// 해당상품의 구매수 + 업데이트 구매수가 0보다 작으면 현재 상품의 구매 수만큼 -해서 상품구매수를 0으로 되게 SET
			if (prchsCnt + reqPurchsCnt < 0) {
				Integer resetCount = 0 - prchsCnt; // 상품의 현재 구매수만큼 -해주기 위한 변수
				map.put("purchaseCount", resetCount.toString());
			}

			// channel 및 catalog Id에 대한 update 실행
			this.commonDAO.update("SpecialPurchaseCount.updateSpecialPurchaseCount", map);
		}catch (Exception e){
			flag =false;
		}
		return flag;
	}


}
