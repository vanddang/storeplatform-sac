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

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;
import com.skplanet.storeplatform.purchase.client.policy.sci.PurchaseTenantPolicySCI;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 구매 서비스 인터페이스 구현체
 * 
 * Updated on : 2013-12-10 Updated by : 조용진, 엔텔스.
 */
@Service
public class GiftScServiceImpl implements GiftScService {
	private static Logger logger = LoggerFactory.getLogger(GiftScServiceImpl.class);

	/*
	 * 데이터 베이스에서 데이터를 조회할 별도 DAO 객체를 생성하지 않고, CommonDAO 를 사용한다.
	 */
	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	@Autowired
	private PurchaseTenantPolicySCI purchaseTenantPolicySCI;

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param giftReceiveScReq
	 *            요청정보
	 * @return GiftReceiveScRes
	 */
	@Override
	public GiftReceiveScRes searchGiftReceive(GiftReceiveScReq giftReceiveScReq) {
		logger.debug("@@@@@@@@@@@@@@ searchGiftReceive @@@@@@@@@@@@@@@@");
		return (GiftReceiveScRes) this.commonDAO.queryForObject("PurchaseGift.searchGiftReceive", giftReceiveScReq);
	}

	/**
	 * 선물수신 저장.
	 * 
	 * @param giftConfirmScReq
	 *            요청정보
	 * @return GiftConfirmScRes
	 */
	@Override
	public GiftConfirmScRes updateGiftConfirm(GiftConfirmScReq giftConfirmScReq) {
		logger.debug("@@@@@@@@@@@@@@ updateGiftConfirm @@@@@@@@@@@@@@@@");
		// GiftConfirmScRes giftConfirmScRes = new GiftConfirmScRes();
		// 2014-03-19 테스트를 위하여 임시로 수신처리확인 주석처리
		GiftConfirmScRes giftConfirmScRes = (GiftConfirmScRes) this.commonDAO.queryForObject(
				"PurchaseGift.getGiftConfirm", giftConfirmScReq);
		if (giftConfirmScRes == null) {
			// 선물 수신 내역이 존재하지않으면 Exception 처리
			throw new StorePlatformException("SC_PUR_4002");
		} else {
			if (!StringUtils.equals(giftConfirmScReq.getDeviceKey(), giftConfirmScRes.getUseInsdDeviceId())) {
				// 다른 기기에서 수신한 경우 device기반 정책이 등록되어 있는지 확인하고, 정책이 등록되어 있다면 선물발송_디바이스!=수신_디바이스=>에러처리
				if (CollectionUtils.isNotEmpty(this.purchaseTenantPolicySCI.searchTenantSalePolicyList(
						giftConfirmScReq.getTenantId(), giftConfirmScRes.getTenantProdGrpCd(),
						PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, null, false)))
					throw new StorePlatformException("SAC_PUR_4111"); // 선물 받은 휴대기기에서 수신 확인 후 이용 가능합니다.
			}
			this.logger.debug("PRCHS,GiftScService,SC,REQ,{}", giftConfirmScRes.getRecvDt());
			if (StringUtils.isEmpty(giftConfirmScRes.getRecvDt())
					&& StringUtils.isNotEmpty(giftConfirmScRes.getPrchsId())) {
				int count = 0;
				// 1. 구매상세 업데이트
				count = this.commonDAO.update("PurchaseGift.updateGiftConfirm", giftConfirmScReq);

				if (count > 0) {
					// giftConfirmScRes.setPrchsId(giftConfirmScReq.getPrchsId());
					// giftConfirmScRes.setProdId(giftConfirmScReq.getProdId());
					giftConfirmScRes = (GiftConfirmScRes) this.commonDAO.queryForObject(
							"PurchaseGift.retrieveUpdatedGiftDtl", giftConfirmScReq);
					giftConfirmScRes.setResultYn("Y");
				} else {
					// giftConfirmScRes.setPrchsId(giftConfirmScReq.getPrchsId());
					// giftConfirmScRes.setProdId(giftConfirmScReq.getProdId());
					giftConfirmScRes.setResultYn("N");
				}
			}
		}
		return giftConfirmScRes;
	}
}
