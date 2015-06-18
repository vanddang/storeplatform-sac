/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.sci.PurchaseDrmInfoSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoSc;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 구매DRM정보 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class PurchaseDrmInfoServiceImpl implements PurchaseDrmInfoService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseDrmInfoSCI purchaseDrmInfoSCI;

	/**
	 * 다운로드 여부에 따른 DRM 정보 수정.
	 * 
	 * @param request
	 *            DRM정보
	 * @return PurchaseDrmInfoSacInRes
	 */
	@Override
	public PurchaseDrmInfoSacInRes updatePrchaseDrm(PurchaseDrmInfoSacInReq request) {

		// SC request/response VO
		PurchaseDrmInfoScReq scRequest = new PurchaseDrmInfoScReq();
		PurchaseDrmInfoScRes scResponse = new PurchaseDrmInfoScRes();

		// SAC Response VO
		PurchaseDrmInfoSacInRes response = new PurchaseDrmInfoSacInRes();

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setSystemId(request.getSystemId());
		scRequest.setPrchsId(request.getPrchsId());
		scRequest.setUserKey(request.getUserKey());
		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		PurchaseDrmInfoSc purchaseDrmInfoSc = this.purchaseDrmInfoSCI.selectPrchsDtl(scRequest);

		this.logger.info("PurchaseDrmInfoServiceImpl.updatePrchaseDrm.purchaseDrmInfoSc {}", purchaseDrmInfoSc);

		// 구매내역이 존재하며 쇼핑상품이 아닌경우
		if (purchaseDrmInfoSc != null
				&& !StringUtils.startsWith(purchaseDrmInfoSc.getTenantProdGrpCd(),
						PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {

			// 처리대기 상태인 경우만 처리
			if (StringUtils.equals(PurchaseConstants.PROCESSING_STATUS_STANDBY,
					purchaseDrmInfoSc.getUsePeriodRedateCd())) {

				// 선물건이고 수신확인이 된 경우에는 처리안함
				if (StringUtils.equals(PurchaseConstants.PRCHS_CASE_GIFT_CD, purchaseDrmInfoSc.getPrchsCaseCd())
						&& !StringUtils.isBlank(purchaseDrmInfoSc.getRecvDt())) {

					// OR021101 : 처리대기
					// OR021102 : 처리완료

				} else {
					/**
					 * SC Call
					 */
					scResponse = this.purchaseDrmInfoSCI.updatePrchaseDrm(scRequest);
				}
			}
		}

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/
		response.setPrchsId(scResponse.getPrchsId());
		response.setResultYn(!StringUtils.equals("Y", scResponse.getResultYn()) ? "N" : scResponse.getResultYn());
		/*************************************************
		 * SC -> SAC Response Setting End
		 *************************************************/

		return response;
	}

}
