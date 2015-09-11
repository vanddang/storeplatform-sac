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

import com.skplanet.storeplatform.purchase.client.history.sci.MileageSaveSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.*;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PromotionEventSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventItem;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PromotionEventRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.*;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.PaymethodUtil;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * T마일리지 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class MileageSaveServiceImpl implements MileageSaveService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MileageSaveSCI mileageSaveSci;

	@Autowired
	private PurchaseOrderPolicyService purchaseOrderPolicyService;

	@Autowired
	private PromotionEventSCI promotionEventSCI;

	/**
	 * T마일리지 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            T마일리지요청
	 * @return MileageSaveSacRes
	 */
	@Override
	public MileageSaveSacRes searchMileageSave(MileageSaveSacReq request) {
		this.logger.info("MileageSaveSac Request Param : {}", request);

		// SC request/response VO
		MileageSaveScReq scRequest = new MileageSaveScReq();
		MileageSaveScRes scResponse = null;

		// SAC Response VO
		MileageSaveSacRes response = new MileageSaveSacRes();
		List<MileageSave> sacMileageSaveList = new ArrayList<MileageSave>();
		MileageSave tstoreMileageSac = null;

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setUserKey(request.getUserKey());
		scRequest.setProcStatusCd(PurchaseConstants.MEMBERSHIP_PROC_STATUS_RESERVE); // 적립예정 상태
		scRequest.setStandardDt(request.getStandardDt());
		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		/**
		 * Purchase SC Call
		 */
		this.logger.debug("##### MileageSave SC Call Start");
		scResponse = this.mileageSaveSci.searchMileageSave(scRequest);
		this.logger.debug("##### MileageSave SC Call End");

		List<Integer> promIdList = new ArrayList<Integer>();

		for (MileageSaveSc obj : scResponse.gettMileageReseveList()) {
			if (obj.getPromId() != null) {
				promIdList.add(obj.getPromId());
			}
		}

		PromotionEventReq promotionEventReq = new PromotionEventReq();
		promotionEventReq.setTenantId(request.getTenantId());
		promotionEventReq.setPromIdList(promIdList);

		PromotionEventRes promotionEventRes = this.promotionEventSCI.getPromotionEvent(promotionEventReq);
		Map<Integer, PromotionEventItem> promotionMap = promotionEventRes.getPromotionEventMap();

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/
		for (MileageSaveSc obj : scResponse.gettMileageReseveList()) {

			tstoreMileageSac = new MileageSave();

			if (promotionMap != null && obj.getPromId() != null && promotionMap.get(obj.getPromId()) != null) {
				tstoreMileageSac.setPrivateAcmlLimit(promotionMap.get(obj.getPromId()).getPrivateAcmlLimit());
			}

			tstoreMileageSac.setPromId(obj.getPromId());
			tstoreMileageSac.setSaveAmt(obj.getSaveAmt());
			tstoreMileageSac.setSaveDt(obj.getSaveDt());

			sacMileageSaveList.add(tstoreMileageSac);

			promIdList.add(obj.getPromId());

		}
		/*************************************************
		 * SC -> SAC Response Setting End
		 *************************************************/

		// 적립가능 결제수단 조회
		String availMtd = this.purchaseOrderPolicyService.searchtMileageSavePaymentMethod(request.getTenantId(), null);

		if (StringUtils.startsWith(request.getSystemId(), "S01")) { // 테넌트는 테넌트용 결제수단으로 응답
			if (StringUtils.isNotBlank(availMtd)) {
				StringBuffer sbAvailMtd = new StringBuffer();
				for (String mtd : availMtd.split(";")) {
					if (sbAvailMtd.length() > 0) {
						sbAvailMtd.append(";");
					}
					sbAvailMtd.append(PaymethodUtil.convert2StoreCode(mtd));
				}
				availMtd = sbAvailMtd.toString();
			}
		}
		response.settMileageAvailMtd(availMtd);

		// // 적립한도 조회
		// response.settMileageLimitAmt(this.purchaseOrderPolicyService.searchtMileageSaveLimit(request.getTenantId(),
		// null) + "");

		response.settMileageReseveList(sacMileageSaveList);

		return response;
	}

	/**
	 * T마일리지 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            T마일리지요청
	 * @return MileageSaveSacRes
	 */
	@Override
	public MileageSaveGetSacRes getMileageSave(MileageSaveGetSacReq request) {
		this.logger.info("MileageSaveSac Request Param : {}", request);

		// SC request/response VO
		MileageSaveGetScReq scRequest = new MileageSaveGetScReq();
		MileageSaveGetScRes scResponse = null;

		// SAC Response VO
		MileageSaveGetSacRes response = new MileageSaveGetSacRes();

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setPrchsId(request.getPrchsId());
		scRequest.setTypeCd(request.getTypeCd());
		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		/**
		 * Purchase SC Call
		 */
		this.logger.debug("##### MileageSave SC Call Start");
		scResponse = this.mileageSaveSci.getMileageSave(scRequest);
		this.logger.debug("##### MileageSave SC Call End");

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/
		if (scResponse != null) {
			response.setProcStatusCd(scResponse.getProcStatusCd());
			response.setTypeCd(scResponse.getTypeCd());
			response.setSaveDt(scResponse.getSaveDt());
			response.setSaveResultAmt(scResponse.getSaveResultAmt());
		}
		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/

		return response;
	}

}
