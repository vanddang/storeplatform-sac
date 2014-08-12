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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.history.sci.MileageSaveSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveSc;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveScRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.MileageSave;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.MileageSaveSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.MileageSaveSacRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.PaymethodUtil;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;

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

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/
		for (MileageSaveSc obj : scResponse.gettMileageReseveList()) {

			tstoreMileageSac = new MileageSave();

			tstoreMileageSac.setSaveAmt(obj.getSaveAmt());
			tstoreMileageSac.setSaveDt(obj.getSaveDt());

			sacMileageSaveList.add(tstoreMileageSac);

		}
		/*************************************************
		 * SC -> SAC Response Setting Start
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

		// 적립한도 조회
		response.settMileageLimitAmt(PurchaseConstants.TMEMBERSHIP_SAVE_LIMIT + "");

		response.settMileageReseveList(sacMileageSaveList);

		return response;
	}

}
