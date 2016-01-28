/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.service;

import java.util.List;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.cancel.sci.ImmediatelyUseStopSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.ImmediatelyUseStopSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.ImmediatelyUseStopSacRes;

/**
 * 즉시이용정지 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class ImmediatelyUseStopServiceImpl implements ImmediatelyUseStopService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ImmediatelyUseStopSCI immediatelyUseStopSci;

	/**
	 * 즉시이용정지 기능을 제공한다.
	 * 
	 * @param request
	 *            즉시이용정지요청
	 * @return ImmediatelyUseStopSacRes
	 */
	@Override
	public ImmediatelyUseStopSacRes updateUseStop(ImmediatelyUseStopSacReq request) {

		this.logger.info("ImmediatelyUseStop RequestParam {}" + request);

		// SC VO
		ImmediatelyUseStopScReq scRequest = new ImmediatelyUseStopScReq();

		scRequest.setTenantId(request.getTenantId());
		scRequest.setSystemId(request.getSystemId());

		scRequest.setPrchsId(request.getPrchsId());
		scRequest.setAdminId(request.getAdminId());
		scRequest.setReqPathCd(request.getReqPathCd());
		scRequest.setDrawbackAmt(request.getDrawbackAmt());
		scRequest.setUserKey(request.getUserKey());
        scRequest.setReasonCd(request.getReasonCd());
        scRequest.setReasonMsg(request.getReasonMsg());

		// 정액권 상품정보 조회
		List<PrchsDtl> prchsDtlList = this.immediatelyUseStopSci.searchPrchsDtl(scRequest);

		// 구매정보가 없는 경우 Exception
		if (CollectionUtils.isEmpty(prchsDtlList)) {
			throw new StorePlatformException("SAC_PUR_8100"); // 구매정보 없음
		}

        /*
         * VOD, 이북코믹의 정액권패스는 지원
         * 시리즈패스(전권 포함)는 지원하지 않음..
         *
         * 기존 전권상품의 경우 하기 권한상품 체크에서 오류 발생..
         * VOD 시리즈 패스는 적용 가능..???
         */
        PrchsDtl fixrateProdPrchsDtl = null;
		for (PrchsDtl prchsDtl : prchsDtlList) {
            // 권한상품이 아닌경우 상태가 아닌 경우 Exception
            if(PurchaseCDConstants.PRCHS_PROD_TYPE_AUTH.equals(prchsDtl.getPrchsProdType())){
                fixrateProdPrchsDtl = prchsDtl;
                break;
            }
		}

        if (fixrateProdPrchsDtl == null) {
            throw new StorePlatformException("SAC_PUR_8130"); // 정액권상품 아님
        }

        // 구매완료 상태가 아닌 경우 Exception
        if (!StringUtils.equals(fixrateProdPrchsDtl.getStatusCd(), PurchaseCDConstants.PRCHS_STATUS_COMPT)) {
            throw new StorePlatformException("SAC_PUR_8101"); // 구매완료상태 아님
        }

        // 환불 금액이 결제금액보다 큰 경우..
        if (Double.parseDouble(scRequest.getDrawbackAmt()) > fixrateProdPrchsDtl.getTotAmt() ) {
            throw new StorePlatformException("SAC_PUR_8133", scRequest.getDrawbackAmt(), fixrateProdPrchsDtl.getTotAmt()); // 환불 금액 오류
        }

        // 이용내역 조회를 위해 이용 내역 발생 가능 구간 설정
        scRequest.setDwldStartDt(fixrateProdPrchsDtl.getDwldStartDt());
        scRequest.setDwldExprDt(fixrateProdPrchsDtl.getDwldExprDt());
        scRequest.setUserKey(fixrateProdPrchsDtl.getUseInsdUsermbrNo());

		// 즉시이용정지 UPDATE
		ImmediatelyUseStopScRes scResponse = this.immediatelyUseStopSci.updateUseStop(scRequest);

		// SAC VO
		ImmediatelyUseStopSacRes sacResponse = new ImmediatelyUseStopSacRes();
		sacResponse.setPrchsId(scResponse.getPrchsId());

		return sacResponse;

	}

}
