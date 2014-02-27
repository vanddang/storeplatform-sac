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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.interpark.sci.InterparkSCI;
import com.skplanet.storeplatform.external.client.interpark.vo.CreateOrderEcReq;
import com.skplanet.storeplatform.external.client.interpark.vo.CreateOrderEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.interworking.sci.InterworkingSCI;
import com.skplanet.storeplatform.purchase.client.interworking.vo.InterworkingSc;
import com.skplanet.storeplatform.purchase.client.interworking.vo.InterworkingScReq;
import com.skplanet.storeplatform.purchase.client.interworking.vo.InterworkingScRes;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.CreateOrderReq;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.CreateOrderRes;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSac;
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
	private InterworkingSCI interworkingSCI;

	/**
	 * 구매후처리 (인터파크,씨네21).
	 * 
	 * @param interworkingSacReq
	 *            요청정보
	 * @return
	 */
	@Override
	public void createInterworking(InterworkingSacReq interworkingSacReq, String temp) {

		/**
		 * 상품ID로 전시호출후 응답값이 (인터파크,씨네21)일 경우 배치를 위해 생성될 table에 insert후 인터파크일 경우에는 실시간 연동처리를 한다. 전시쪽에서 조회가 어떻게 될지 몰라 현재
		 * 구현을 위해 test변수 temp을 사용한다.
		 */
		// value interpark, cine21
		// String temp = "interpark";

		if (temp.equals("interpark") || temp.equals("cine21")) {
			this.logger.debug("@@@@@@@@@@@@ Interworking @@@@@@@@@@@@ {}", interworkingSacReq.getPrchsId());

			InterworkingScRes interworkingScRes = new InterworkingScRes();
			// 구매내역전송을 위한 실시간 누적

			interworkingScRes = this.interworkingSCI.createInterworking(this.reqConvert(interworkingSacReq));

			this.logger.debug("@@@@@@@@@@@@ getResult @@@@@@@@@@@@ {}", interworkingScRes.getResult());
			if (temp.equals("interpark") && interworkingScRes.getResult() > 0) {
				try {
					this.logger.debug("@@@@@@@@@@@@ interpark @@@@@@@@@@@@ ");
					CreateOrderReq createOrderReq = new CreateOrderReq();

					createOrderReq.setRevOrdNo(interworkingSacReq.getPrchsId());
					createOrderReq.setOrdDts(interworkingSacReq.getPrchsDt());

					for (InterworkingSac interworkingSac : interworkingSacReq.getInterworkingListSac()) {
						createOrderReq.setPrdNo(interworkingSac.getCompContentsId());
						createOrderReq.setItemNo(interworkingSac.getProdId());
						createOrderReq.setPrice(interworkingSac.getProdAmt());
						createOrderReq.setQty(1);
						createOrderReq.setFlag("01");

						// interpark 실시간 연동처리
						// this.createOrder(createOrderReq);
					}

				} catch (StorePlatformException ex) {
					if (ex.getErrorInfo().getCode().equals("EC_INTERPARK_9996")) {
						throw new StorePlatformException("SAC_PUR_3000");
					}
				}
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
		req.setInsdUsermbrNo(interworkingSacReq.getUserKey());
		req.setInsdDeviceId(interworkingSacReq.getDeviceKey());
		req.setPrchsId(interworkingSacReq.getPrchsId());
		req.setPrchsDt(interworkingSacReq.getPrchsDt());
		// req.setPrchsCancelDt(interworkingSacReq.getPrchsDt());
		req.setFileMakeYn(interworkingSacReq.getFileMakeYn());
		List<InterworkingSc> list = new ArrayList<InterworkingSc>();
		for (InterworkingSac interworkingSac : interworkingSacReq.getInterworkingListSac()) {
			InterworkingSc interworkingSc = new InterworkingSc();

			interworkingSc.setMallCd(interworkingSac.getMallCd());
			interworkingSc.setProdId(interworkingSac.getProdId());
			interworkingSc.setSellermbrNo(interworkingSac.getSellermbrNo());
			interworkingSc.setProdAmt(interworkingSac.getProdAmt());
			interworkingSc.setCompContentsId(interworkingSac.getCompContentsId());
			list.add(interworkingSc);
		}
		req.setInterworkingListSc(list);

		return req;
	}

}
