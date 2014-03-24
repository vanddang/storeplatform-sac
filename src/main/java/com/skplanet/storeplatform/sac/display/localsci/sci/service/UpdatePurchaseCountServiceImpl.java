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
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

/**
 * UpdatePurchaseCount Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 2. 14. Updated by : 이석희, 아이에스플러스.
 */
@Service
public class UpdatePurchaseCountServiceImpl implements UpdatePurchaseCountService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.display.localsci.sci.service.UpdatePurchaseCountService#UpdatePurchaseCountService
	 * (UpdatePurchaseCountSacReq req)
	 */
	@Override
	public void updatePurchaseCount(List<UpdatePurchaseCountSacReq> reqList) {
		Map<String, String> map = null;
		List<Map> productList = null;
		int prchsCnt = 0;
		int prodCnt = 0;

		for (int i = 0; i < reqList.size(); i++) {
			map = new HashMap<String, String>();
			if (i > 9) {
				throw new StorePlatformException("SAC_DSP_0007", "10");
			}

			map.put("tenantId", reqList.get(i).getTenantId());
			map.put("productId", reqList.get(i).getProductId());

			// 상품 기본 정보(상품군) 조회
			ProductBasicInfo productBasicInfo = this.commonDAO.queryForObject("LocalSci.selectProductInfo", map,
					ProductBasicInfo.class);

			if (productBasicInfo != null) {
				this.log.debug("----------------------------------------------------------");
				this.log.debug("svcGrpCd : {}", productBasicInfo.getSvcGrpCd());
				this.log.debug("getContentsTypeCd : {}", productBasicInfo.getContentsTypeCd());
				this.log.debug("partProdId : {}", productBasicInfo.getPartProdId());
				this.log.debug("catalogId : {}", productBasicInfo.getCatalogId());
				this.log.debug("prodId : {}", productBasicInfo.getProdId());
				this.log.debug("----------------------------------------------------------");

				map.put("productId", productBasicInfo.getPartProdId());

				// TB_DP_TENANT_PROD_STATS 상품 존재 유무 확인
				prodCnt = (Integer) this.commonDAO.queryForObject("LocalSci.selectTenantProdStats", map);

				if (prodCnt < 1) {
					// 대상 상품이 없으면 해당 상품 insert 실행 Insert 시 구매수는 Default 0으로 Set
					map.put("purchaseCount", "0");
					this.commonDAO.update("LocalSci.insertPurchaseProd", map);
				}

				map.put("purchaseCount", reqList.get(i).getPurchaseCount().toString());

				prchsCnt = (Integer) this.commonDAO.queryForObject("LocalSci.getPurchaseCount", map);

				// 해당상품의 구매수 + 업데이트 구매수가 0보다 작으면 현재 상품의 구매 수만큼 -해서 상품구매수를 0으로 되게 SET
				if (prchsCnt + reqList.get(i).getPurchaseCount() < 0) {
					Integer resetCount = 0 - prchsCnt; // 상품의 현재 구매수만큼 -해주기 위한 변수
					map.put("purchaseCount", resetCount.toString());
				}

				// episode 상품에 대한 update 실행
				this.commonDAO.update("LocalSci.updatePurchaseCount", map);

				// APP 상품은 Episode 한번만 update
				if (!DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals((productBasicInfo.getSvcGrpCd()))) {
					if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals((productBasicInfo.getSvcGrpCd()))) {
						map.put("productId", productBasicInfo.getProdId()); // 멀티미디어 상품일 때 채널 ID를 SET
					} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals((productBasicInfo
							.getSvcGrpCd()))) {
						map.put("productId", productBasicInfo.getCatalogId());// 쇼핑 상품 일때 카탈로그 ID를 SET
					}

					// TB_DP_TENANT_PROD_STATS 상품 존재 유무 확인
					prodCnt = (Integer) this.commonDAO.queryForObject("LocalSci.selectTenantProdStats", map);

					if (prodCnt < 1) {
						// 대상 상품이 없으면 해당 상품 insert 실행 Insert 시 구매수는 Default 0으로 Set
						map.put("purchaseCount", "0");
						this.commonDAO.update("LocalSci.insertPurchaseProd", map);
					}

					map.put("purchaseCount", reqList.get(i).getPurchaseCount().toString());

					prchsCnt = (Integer) this.commonDAO.queryForObject("LocalSci.getPurchaseCount", map);

					// 해당상품의 구매수 + 업데이트 구매수가 0보다 작으면 현재 상품의 구매 수만큼 -해서 상품구매수를 0으로 되게 SET
					if (prchsCnt + reqList.get(i).getPurchaseCount() < 0) {
						Integer resetCount = 0 - prchsCnt; // 상품의 현재 구매수만큼 -해주기 위한 변수
						map.put("purchaseCount", resetCount.toString());
					}

					// channel 및 catalog Id에 대한 update 실행
					this.commonDAO.update("LocalSci.updatePurchaseCount", map);

				}

			}

		}
	}
}
