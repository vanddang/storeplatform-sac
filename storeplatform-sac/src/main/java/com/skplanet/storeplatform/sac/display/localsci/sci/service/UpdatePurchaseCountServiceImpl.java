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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		for (int i = 0; i < reqList.size(); i++) {
			map = new HashMap<String, String>();
			if (i > 9) {
				throw new StorePlatformException("SAC_DSP_0007", "10");
			}
			if (!StringUtils.equals(reqList.get(i).getSpcYn(), "Y")) { // 특가 상품 여부 가 아닌경우
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
					this.updatePurchaseCount(map, reqList.get(i).getPurchaseCount()); // Episode에 대한 Update 로직 실행

					// APP 상품은 Episode 한번만 update
					if (!DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals((productBasicInfo.getSvcGrpCd()))) {
						if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals((productBasicInfo.getSvcGrpCd()))) {
							map.put("productId", productBasicInfo.getProdId()); // 멀티미디어 상품일 때 채널 ID를 SET
							this.updatePurchaseCount(map, reqList.get(i).getPurchaseCount()); // 채널에 대한 Update 로직 실행
						} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals((productBasicInfo
								.getSvcGrpCd()))) {
							map.put("productId", productBasicInfo.getProdId()); // 쇼핑 상품 일때 Channel ID를 SET
							this.updatePurchaseCount(map, reqList.get(i).getPurchaseCount()); // 채널에 대한 Update 로직 실행
							map.put("productId", productBasicInfo.getCatalogId());// 쇼핑 상품 일때 카탈로그 ID를 SET
							this.updatePurchaseCount(map, reqList.get(i).getPurchaseCount()); // 카탈로그에 대한 Update 로직 실행
						}
					}

				}
			} else { // 특가 상품 여부 가 아닌경우
				map.put("tenantId", reqList.get(i).getTenantId());
				map.put("productId", reqList.get(i).getProductId());
				map.put("purchaseDate", reqList.get(i).getPurchaseDate());
				int specialProdSize = 3;
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
						this.updateSpecialPurchaseCount(map, reqList.get(i).getPurchaseCount()); // 특가상품 Update 로직 실행
					}
				}
			}
		}
	}

	/**
	 * 상품 구매 건수 업데이트.
	 * 
	 * @param map
	 *            map
	 * @param reqPurchsCnt
	 *            reqPurchsCnt
	 */
	public void updatePurchaseCount(Map<String, String> map, int reqPurchsCnt) {
		int prodCnt = 0;
		int prchsCnt = 0;
		// TB_DP_TENANT_PROD_STATS 상품 존재 유무 확인
		prodCnt = (Integer) this.commonDAO.queryForObject("LocalSci.selectTenantProdStats", map);

		if (prodCnt < 1) {
			// 대상 상품이 없으면 해당 상품 insert 실행 Insert 시 구매수는 Default 0으로 Set
			map.put("purchaseCount", "0");
			this.commonDAO.update("LocalSci.createPurchaseProd", map);
		}

		map.put("purchaseCount", Integer.toString(reqPurchsCnt));

		prchsCnt = (Integer) this.commonDAO.queryForObject("LocalSci.getPurchaseCount", map);

		// 해당상품의 구매수 + 업데이트 구매수가 0보다 작으면 현재 상품의 구매 수만큼 -해서 상품구매수를 0으로 되게 SET
		if (prchsCnt + reqPurchsCnt < 0) {
			Integer resetCount = 0 - prchsCnt; // 상품의 현재 구매수만큼 -해주기 위한 변수
			map.put("purchaseCount", resetCount.toString());
		}

		// channel 및 catalog Id에 대한 update 실행
		this.commonDAO.update("LocalSci.updatePurchaseCount", map);
	}

	/**
	 * 특가 상품 구매 건수 업데이트.
	 * 
	 * @param map
	 *            map
	 * @param reqPurchsCnt
	 *            reqPurchsCnt
	 */
	private void updateSpecialPurchaseCount(Map<String, String> map, int reqPurchsCnt) {
		int prodCnt = 0;
		int prchsCnt = 0;
		// TB_DP_TENANT_PROD_STATS 상품 존재 유무 확인
		prodCnt = (Integer) this.commonDAO.queryForObject("LocalSci.selectSpecialProdCount", map);

		if (prodCnt < 1) {
			// 대상 상품이 없으면 해당 상품 insert 실행 Insert 시 구매수는 Default 0으로 Set
			map.put("purchaseCount", "0");
			this.commonDAO.update("LocalSci.createSpecialPurchaseProd", map);
		}

		map.put("purchaseCount", Integer.toString(reqPurchsCnt));

		prchsCnt = (Integer) this.commonDAO.queryForObject("LocalSci.getSpecialPurchaseCount", map);

		// 해당상품의 구매수 + 업데이트 구매수가 0보다 작으면 현재 상품의 구매 수만큼 -해서 상품구매수를 0으로 되게 SET
		if (prchsCnt + reqPurchsCnt < 0) {
			Integer resetCount = 0 - prchsCnt; // 상품의 현재 구매수만큼 -해주기 위한 변수
			map.put("purchaseCount", resetCount.toString());
		}

		// channel 및 catalog Id에 대한 update 실행
		this.commonDAO.update("LocalSci.updateSpecialPurchaseCount", map);
	}
}
