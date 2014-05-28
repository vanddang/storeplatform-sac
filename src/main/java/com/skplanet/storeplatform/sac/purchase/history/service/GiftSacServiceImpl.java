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

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.purchase.client.history.sci.GiftSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;

/**
 * 선물확인 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
public class GiftSacServiceImpl implements GiftSacService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftSCI giftSCI;

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param giftReceiveScReq
	 *            요청정보
	 * @return GiftReceiveScRes
	 */
	@Override
	public GiftReceiveScRes searchGiftReceive(GiftReceiveScReq giftReceiveScReq) {

		return this.giftSCI.searchGiftReceive(giftReceiveScReq);
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

		final GiftConfirmScRes giftConfirmScRes = this.giftSCI.updateGiftConfirm(giftConfirmScReq);
		// TLog
		// 상품아이디
		final List<String> prodIdList = new ArrayList<String>();
		// 상품금액
		final List<Long> prodAmtList = new ArrayList<Long>();
		// tlog 상품아이디 셋팅
		prodIdList.add(giftConfirmScReq.getProdId());
		// tlog 상품금액 셋팅
		prodAmtList.add((long) giftConfirmScRes.getProdAmt());

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.purchase_channel(giftConfirmScRes.getPrchsReqPathCd())
						.purchase_inflow_channel(giftConfirmScRes.getPrchsCaseCd()).product_id(prodIdList)
						.product_price(prodAmtList).purchase_id(giftConfirmScRes.getPrchsId())
						.purchase_prod_num(String.valueOf(giftConfirmScRes.getPrchsDtlId()))
						.purchase_id_send(giftConfirmScRes.getPrchsId())
						.purchase_prod_num_send(String.valueOf(giftConfirmScRes.getPrchsDtlId()))
						.use_start_time(giftConfirmScRes.getUseStartDt()).use_end_time(giftConfirmScRes.getUseExprDt())
						.download_expired_time(giftConfirmScRes.getDwldExprDt())
						.product_qty((long) giftConfirmScRes.getProdQty())
						.coupon_publish_code(giftConfirmScRes.getCpnPublishCd())
						.recv_confirm_class(giftConfirmScRes.getRecvConfPathCd())
						.insd_usermbr_no(giftConfirmScRes.getSendInsdUsermbrNo());
			}
		});

		if (StringUtils.isEmpty(giftConfirmScRes.getRecvDt())) {
			// 이미 수신처리가 되었을 경우 에러처리? 아니면 실패로 리턴?
			throw new StorePlatformException("SAC_PUR_7104");
		}

		return giftConfirmScRes;
	}
}
