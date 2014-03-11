/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.interworking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.interworking.sci.InterworkingSCI;
import com.skplanet.storeplatform.purchase.client.interworking.vo.InterworkingScReq;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.Interworking;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSacReq;

/**
 * 인터파크 연동 및 씨네21 저장.
 * 
 * Updated on : 2014. 2. 5. Updated by : 조용진, NTELS.
 */
@Service
public class InterworkingSacServiceImpl implements InterworkingSacService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// @Autowired
	// private InterparkSCI interparkSCI;
	@Autowired
	private InterworkingSCI interworkingSCI;

	/**
	 * 구매후처리 (인터파크,씨네21).
	 * 
	 * @param interworkingSacReq
	 *            요청정보
	 * @return
	 */
	@Override
	public void createInterworking(InterworkingSacReq interworkingSacReq) {

		InterworkingScReq req = new InterworkingScReq();

		req.setTenantId(interworkingSacReq.getTenantId());
		req.setSystemId(interworkingSacReq.getSystemId());
		req.setPrchsId(interworkingSacReq.getPrchsId());
		req.setInsdUsermbrNo(interworkingSacReq.getUserKey());
		req.setInsdDeviceId(interworkingSacReq.getDeviceKey());
		req.setPrchsDt(interworkingSacReq.getPrchsDt());
		req.setPrchsCancelDt(interworkingSacReq.getPrchsCancelDt());

		for (Interworking interworkingSac : interworkingSacReq.getInterworkingList()) {

			req.setProdId(interworkingSac.getProdId());
			req.setProdAmt(interworkingSac.getProdAmt());
			req.setCompContentsId(interworkingSac.getCompContentsId());

			/*
			 * 인터파크는 maillCd로 구분하며, cine21는 sellermbrNo로 구분한다 (인터파크 2014-03-10 실시간연동 주석처리)
			 */
			int updateCount = 0;
			// if (interworkingSac.getMallCd().equals("interpark")) {
			// // 상점코드를 기준으로 처리
			// req.setTransClasCd(PurchaseConstants.TRANS_CLAS_MALL_CD);
			// req.setTransClasValue(interworkingSac.getMallCd());
			//
			// this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
			// this.logger.debug("@@@@@@@@@@@@ interpark @@@@@@@@@@@@ ");
			// this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
			// // 전송테이블에 저장
			// updateCount = this.interworkingSCI.createInterworking(req);
			//
			// this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			// this.logger.debug("@@@@@@@@@@@@ interpark updateCount @@@@@@@@@@@@ {}", updateCount);
			// this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			// if (updateCount > 0) {
			// try {
			// CreateOrderReq createOrderReq = new CreateOrderReq();
			//
			// createOrderReq.setRevOrdNo(req.getPrchsId());
			// createOrderReq.setOrdDts(req.getPrchsDt());
			// createOrderReq.setPrdNo(req.getCompContentsId());
			// createOrderReq.setItemNo(req.getProdId());
			// createOrderReq.setPrice(req.getProdAmt());
			// createOrderReq.setQty(1);
			// createOrderReq.setFlag("01");
			//
			// // interpark 실시간 연동처리
			// // CreateOrderRes res =
			// // this.resConvert(this.interparkSCI.createOrder(this.reqConvert(createOrderReq)));
			// } catch (Exception ex) {
			// // 공통에러코드 필요
			// // throw new StorePlatformException("", ex);
			// }
			// }
			// }
			if (interworkingSac.getSellermbrNo().equals("cine21-SellermbrNo1")
					|| interworkingSac.getSellermbrNo().equals("cine21-SellermbrNo2")) {
				// 판매재회원번호 기준으로 처리
				req.setTransClasCd(PurchaseConstants.TRANS_CLAS_SELLER_MBR_NO);
				req.setTransClasValue(interworkingSac.getSellermbrNo());

				this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
				this.logger.debug("@@@@@@@@@@@@ cine21 @@@@@@@@@@@@ ");
				this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
				// 전송테이블에 저장
				updateCount = this.interworkingSCI.createInterworking(req);
				this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				this.logger.debug("@@@@@@@@@@@@ cine21 updateCount @@@@@@@@@@@@ {}", updateCount);
				this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

			}
		}
	}

	// 인터파크 2014-03-10 실시간연동 주석처리)
	// /**
	// * reqConvert.
	// *
	// * @param createOrderReq
	// * 요청정보
	// * @return CreateOrderEcReq
	// */
	// private CreateOrderEcReq reqConvert(CreateOrderReq createOrderReq) {
	//
	// CreateOrderEcReq req = new CreateOrderEcReq();
	//
	// req.setRevOrdNo(createOrderReq.getRevOrdNo());
	// req.setOrdDts(createOrderReq.getOrdDts());
	// req.setPrdNo(createOrderReq.getPrdNo());
	// req.setItemNo(createOrderReq.getItemNo());
	// req.setPrice(String.valueOf(createOrderReq.getPrice()));
	// req.setFlag("01");
	// req.setQty("1");
	//
	// return req;
	// }
	//
	// /**
	// * resConvert.
	// *
	// * @param createOrderEcRes
	// * 요청정보
	// * @return CreateOrderSacRes
	// */
	// private CreateOrderRes resConvert(CreateOrderEcRes createOrderEcRes) {
	// CreateOrderRes res = new CreateOrderRes();
	// this.logger.debug("@@@@@@resConvert@@@@@@@");
	// res.setResultStatus(createOrderEcRes.getResultStatus());
	//
	// return res;
	// }

}
