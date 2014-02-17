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
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.external.client.interpark.sci.InterparkSCI;
import com.skplanet.storeplatform.external.client.interpark.vo.CreateOrderEcReq;
import com.skplanet.storeplatform.external.client.interpark.vo.CreateOrderEcRes;
import com.skplanet.storeplatform.purchase.client.interworking.sci.InterworkingSCI;
import com.skplanet.storeplatform.purchase.client.interworking.vo.InterworkingScReq;
import com.skplanet.storeplatform.purchase.client.interworking.vo.InterworkingScRes;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.CreateOrderReq;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.CreateOrderRes;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSacReq;

/**
 * 인터파크 연동 및 씨네21 저장.
 * 
 * Updated on : 2014. 2. 5. Updated by : 조용진, NTELS.
 */
@Service
public class InterworkingSacServiceImpl implements InterworkingSacService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InterparkSCI interparkSCI;
	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;
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
	public void createInterworking(@Validated InterworkingSacReq interworkingSacReq) {

		// DataBinder dataBinder = new DataBinder(interworkingSacReq);
		// BindingResult result = dataBinder.getBindingResult();
		//
		// this.validator.validate(interworkingSacReq, result, null);
		// if (result.hasErrors()) {
		// throw new StorePlatformException("SAC_PUR_0001", result.getErrorCount());
		// }
		/**
		 * 상품ID로 전시호출후 응답값이 (인터파크,씨네21)일 경우 배치를 위해 생성될 table에 insert후 인터파크일 경우에는 실시간 연동처리를 한다. 전시쪽에서 조회가 어떻게 될지 몰라 현재
		 * 구현을 위해 test변수 temp을 사용한다.
		 */
		// value interpark, cine21
		String temp = "interpark";

		if (temp.equals("interpark") || temp.equals("cine21")) {
			this.logger.debug("@@@@@@@@@@@@ Interworking @@@@@@@@@@@@ {}", interworkingSacReq.getPrchsId());

			InterworkingScRes interworkingScRes = new InterworkingScRes();
			// 구매내역전송을 위한 실시간 누적

			interworkingScRes = this.interworkingSCI.createInterworking(this.reqConvert(interworkingSacReq));

			this.logger.debug("@@@@@@@@@@@@ getResult @@@@@@@@@@@@ {}", interworkingScRes.getResult());
			if (temp.equals("interpark") && interworkingScRes.getResult() > 0) {
				this.logger.debug("@@@@@@@@@@@@ interpark @@@@@@@@@@@@ ");
				CreateOrderReq createOrderReq = new CreateOrderReq();

				createOrderReq.setRevOrdNo(interworkingSacReq.getPrchsId());
				createOrderReq.setOrdDts(interworkingSacReq.getPrchsDt());
				createOrderReq.setPrdNo(interworkingSacReq.getCompContentsId());
				createOrderReq.setItemNo(interworkingSacReq.getProdId());
				createOrderReq.setPrice(interworkingSacReq.getProdAmt());

				// try {
				// // interpark 실시간 연동처리
				// this.createOrder(createOrderReq);
				// } catch (Exception ex) {
				// throw new StorePlatformException("SAC_PUR_3000", ex.getMessage());
				// }
			}

		}

	}

	/**
	 * interpark 연동.
	 * 
	 * @param createOrderReq
	 *            요청정보
	 * @return CreateOrderSacRes
	 */
	public CreateOrderRes createOrder(CreateOrderReq createOrderReq) {
		CreateOrderRes res = this.resConvert(this.interparkSCI.createOrder(this.reqConvert(createOrderReq)));

		return res;
	}

	/**
	 * reqConvert.
	 * 
	 * @param createOrderReq
	 *            요청정보
	 * @return CreateOrderEcReq
	 */
	private CreateOrderEcReq reqConvert(CreateOrderReq createOrderReq) {

		CreateOrderEcReq req = new CreateOrderEcReq();

		req.setRevOrdNo(createOrderReq.getRevOrdNo());
		req.setOrdDts(createOrderReq.getOrdDts());
		req.setPrdNo(createOrderReq.getPrdNo());
		req.setItemNo(createOrderReq.getItemNo());
		req.setPrice(String.valueOf(createOrderReq.getPrice()));
		req.setFlag("01");
		req.setQty("1");

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param createOrderEcRes
	 *            요청정보
	 * @return CreateOrderSacRes
	 */
	private CreateOrderRes resConvert(CreateOrderEcRes createOrderEcRes) {
		CreateOrderRes res = new CreateOrderRes();
		this.logger.debug("@@@@@@resConvert@@@@@@@");
		res.setResultStatus(createOrderEcRes.getResultStatus());

		return res;
	}

	/**
	 * reqConvert.
	 * 
	 * @param interworkingSacReq
	 *            요청정보
	 * @return InterworkingScReq
	 */
	private InterworkingScReq reqConvert(InterworkingSacReq interworkingSacReq) {

		InterworkingScReq req = new InterworkingScReq();

		req.setTenantId(interworkingSacReq.getTenantId());
		req.setSystemId(interworkingSacReq.getSystemId());
		req.setPrchsId(interworkingSacReq.getPrchsId());
		req.setInsdUsermbrNo(interworkingSacReq.getUserKey());
		req.setInsdDeviceId(interworkingSacReq.getDeviceKey());
		req.setCompContentsId(interworkingSacReq.getCompContentsId());
		req.setFileMakeYn(interworkingSacReq.getFileMakeYn());
		req.setPrchsCancelDt(interworkingSacReq.getPrchsDt());
		req.setPrchsDt(interworkingSacReq.getPrchsDt());
		req.setProdAmt(interworkingSacReq.getProdAmt());
		req.setProdId(interworkingSacReq.getProdId());
		req.setSellermbrNo(interworkingSacReq.getSellermbrNo());

		return req;
	}

}
