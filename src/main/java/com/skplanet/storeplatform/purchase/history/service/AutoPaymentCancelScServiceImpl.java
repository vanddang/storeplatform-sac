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

import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;

/**
 * 구매 서비스 인터페이스 구현체
 * 
 * Updated on : 2013-12-10 Updated by : 조용진, 엔텔스.
 */
@Service
public class AutoPaymentCancelScServiceImpl implements AutoPaymentCancelScService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 자동결제 해지예약/해지예약취소/해지 요청.
	 * 
	 * @param autoPaymentCancelScReq
	 *            요청정보
	 * @return AutoPaymentCancelScRes
	 */
	@Override
	public AutoPaymentCancelScRes updateReservation(AutoPaymentCancelScReq autoPaymentCancelScReq) {
		AutoPaymentCancelScRes autoPaymentCancelScRes = new AutoPaymentCancelScRes();
		int count = 0;

		// 부분유료화 자동결제 해지 업데이트 처리
		if (autoPaymentCancelScReq.getAutoPaymentStatusCd().equals(PurchaseCDConstants.AUTO_PRCHS_STATUS_IAP_CLOSED)) {

			// 구매ID가 GXG로 시작 시 구매ID가 다르기 때문에 7자리부터 잘라서 구매ID를 조회하여 사용한다.
			if (StringUtils.startsWith(autoPaymentCancelScReq.getPrchsId(), PurchaseCDConstants.PRCHS_ID_IAP_GXG)) {
				String tid = autoPaymentCancelScReq.getPrchsId();
				autoPaymentCancelScReq.setPrchsId(this.commonDAO.queryForObject(
						"PurchaseAutoPaymentCancel.searchUniqueTid", tid.substring(7), String.class));
			}

			if (!StringUtils.isBlank(autoPaymentCancelScReq.getPrchsId())) {
				count = this.commonDAO.update("PurchaseAutoPaymentCancel.updateInAppClose", autoPaymentCancelScReq);
			} else {
				count = 0;
			}

		} else {
			// 자동결제 상품 확인 (자동결제 TABLE의 카운트로 확인)
			int checkCount = this.commonDAO.queryForInt("PurchaseAutoPaymentCancel.getCheckAutoPaymentCancel",
					autoPaymentCancelScReq);
			this.logger.debug("PRCHS,AutoPaymentCancelScService,SC,REQ,{}", checkCount);

			if (checkCount > 0) {
				// 자동결제코드OR020101,OR020102,OR020103가 아닌겨우는 Exception 처리
				if (autoPaymentCancelScReq.getAutoPaymentStatusCd().equals(PurchaseCDConstants.AUTO_PRCHS_STATUS_AUTO)
						|| autoPaymentCancelScReq.getAutoPaymentStatusCd().equals(
								PurchaseCDConstants.AUTO_PRCHS_STATUS_CLOSE_RESERVATION)
						|| autoPaymentCancelScReq.getAutoPaymentStatusCd().equals(
								PurchaseCDConstants.AUTO_PRCHS_STATUS_CLOSED)) {

					// 업데이트
					count = this.commonDAO
							.update("PurchaseAutoPaymentCancel.modifyReservation", autoPaymentCancelScReq);
					this.logger.debug("#############count#########################", count);
				} else {

					// 자동결제 해지예약코드가 아닌경우(에러처리)
					throw new StorePlatformException("SC_PUR_4003", autoPaymentCancelScReq.getAutoPaymentStatusCd());
				}
			} else {
				// 자동결제 상품이 아님(에러처리)
				throw new StorePlatformException("SC_PUR_4001");
			}
		}

		// 성공실패에 대한 셋팅 (성공:Y, 실패:N)
		if (count > 0) {
			autoPaymentCancelScRes = this.commonDAO.queryForObject("PurchaseAutoPaymentCancel.searchPurchaseDate",
					autoPaymentCancelScReq, AutoPaymentCancelScRes.class);

			autoPaymentCancelScRes.setPrchsId(autoPaymentCancelScReq.getPrchsId());
			// 성공시 구매일시 조회하여 리턴값에 셋팅한다.
			// autoPaymentCancelScRes.setPrchsDt(prchsDt);
			autoPaymentCancelScRes.setResultYn("Y");
		} else {
			autoPaymentCancelScRes.setPrchsId(autoPaymentCancelScReq.getPrchsId());
			autoPaymentCancelScRes.setResultYn("N");
		}
		return autoPaymentCancelScRes;
	}

}
