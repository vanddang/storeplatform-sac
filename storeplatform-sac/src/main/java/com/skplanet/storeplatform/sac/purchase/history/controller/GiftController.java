/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftConfirmSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftConfirmSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.history.service.GiftSacService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
public class GiftController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftSacService giftService;

	/**
	 * 선물수신 처리.
	 * 
	 * @param giftConfirmSacReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return GiftConfirmSacRes
	 */
	@RequestMapping(value = "/history/gift/update/v1", method = RequestMethod.POST)
	@ResponseBody
	public GiftConfirmSacRes modifyGiftConfirm(@RequestBody @Validated final GiftConfirmSacReq giftConfirmSacReq,
			SacRequestHeader requestHeader) {

		// 헤더 정보
		TenantHeader header = requestHeader.getTenantHeader();
		final NetworkHeader networkHeader = requestHeader.getNetworkHeader();
		final DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		// 시스템아이디
		final String systemId = header.getSystemId();
		// usembr_no
		final String userKey = giftConfirmSacReq.getUserKey();
		// TLog
		// 상품아이디
		final List<String> prodIdList = new ArrayList<String>();
		// tlog 상품아이디 셋팅
		prodIdList.add(giftConfirmSacReq.getProdId());

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_PUR_0004").system_id(systemId).usermbr_no(userKey)
						.phone_model(deviceHeader.getModel()).os_version(deviceHeader.getOs())
						.insd_device_id(giftConfirmSacReq.getDeviceKey())
						.insd_usermbr_no(giftConfirmSacReq.getUserKey()).network_type(networkHeader.getType())
						.purchase_id(giftConfirmSacReq.getPrchsId()).product_id(prodIdList)
						.recv_confirm_class(giftConfirmSacReq.getRecvConfPathCd());
			}
		});

		this.logger.info("PRCHS,GiftController,SAC,giftConfirmSacReq,{}", giftConfirmSacReq);

		// reqConvert처리 -> SC선물수신처리 -> resConvert 처리후 리턴
		return this.resConfirmConvert(this.giftService.updateGiftConfirm(this.reqConfirmConvert(giftConfirmSacReq,
				header)));
	}

	/**
	 * 선물수신처리 reqConfirmConvert.
	 * 
	 * @param giftConfirmSacReq
	 *            선물수신처리 요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return GiftConfirmScReq
	 */
	private GiftConfirmScReq reqConfirmConvert(GiftConfirmSacReq giftConfirmSacReq, TenantHeader header) {
		GiftConfirmScReq req = new GiftConfirmScReq();

		req.setTenantId(header.getTenantId());
		req.setSystemId(header.getSystemId());
		req.setUserKey(giftConfirmSacReq.getUserKey());
		req.setDeviceKey(giftConfirmSacReq.getDeviceKey());
		req.setPrchsId(giftConfirmSacReq.getPrchsId());
		req.setRecvDt(giftConfirmSacReq.getRecvDt());
		req.setRecvConfPathCd(giftConfirmSacReq.getRecvConfPathCd());
		req.setProdId(giftConfirmSacReq.getProdId());

		return req;
	}

	/**
	 * 선물수신처리 resConfirmConvert.
	 * 
	 * @param giftComfirmScRes
	 *            선물수신처리 응답정보
	 * @return GiftConfirmSacRes
	 */
	private GiftConfirmSacRes resConfirmConvert(GiftConfirmScRes giftComfirmScRes) {
		GiftConfirmSacRes res = new GiftConfirmSacRes();
		res.setPrchsId(giftComfirmScRes.getPrchsId());
		res.setProdId(giftComfirmScRes.getProdId());
		res.setResultYn(giftComfirmScRes.getResultYn());
		this.logger.info("PRCHS,GiftController,SAC,GiftConfirmSacRes,{}", res);
		return res;
	}

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// @@@ 선물수신확인은 사용 안하지만 일감등록후 개발은 진행함 @@@
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	/**
	 * 선물수신확인 체크 SAC.
	 * 
	 * @param giftReceiveSacReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return GiftReceiveSacRes
	 */
	@RequestMapping(value = "/history/gift/search/v1", method = RequestMethod.POST)
	@ResponseBody
	public GiftReceiveSacRes searchGiftReceive(@RequestBody @Validated GiftReceiveSacReq giftReceiveSacReq,
			SacRequestHeader requestHeader) {
		TenantHeader header = requestHeader.getTenantHeader();

		return this.resReceiveConvert(this.giftService.searchGiftReceive(this.reqReceiveConvert(giftReceiveSacReq,
				header)));
	}

	/**
	 * reqReceiveConvert.
	 * 
	 * @param giftReceiveSacReq
	 *            선물수신확인 체크 요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return giftReceiveSacReq
	 */
	private GiftReceiveScReq reqReceiveConvert(GiftReceiveSacReq giftReceiveSacReq, TenantHeader header) {
		GiftReceiveScReq req = new GiftReceiveScReq();

		req.setTenantId(header.getTenantId());
		req.setUserKey(giftReceiveSacReq.getUserKey());
		req.setDeviceKey(giftReceiveSacReq.getDeviceKey());
		req.setPrchsId(giftReceiveSacReq.getPrchsId());
		req.setProdId(giftReceiveSacReq.getProdId());

		return req;
	}

	/**
	 * resReceiveConvert.
	 * 
	 * @param giftReceiveScRes
	 *            요청정보
	 * @return GiftReceiveSacRes
	 */
	private GiftReceiveSacRes resReceiveConvert(GiftReceiveScRes giftReceiveScRes) {
		GiftReceiveSacRes res = new GiftReceiveSacRes();
		res.setRecvDt(giftReceiveScRes.getRecvDt());

		return res;
	}
}
