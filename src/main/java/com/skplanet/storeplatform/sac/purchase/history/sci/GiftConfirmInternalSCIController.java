/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.sci;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.GiftConfirmInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.GiftConfirmSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.GiftConfirmSacInRes;
import com.skplanet.storeplatform.sac.purchase.history.service.GiftSacService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@LocalSCI
public class GiftConfirmInternalSCIController implements GiftConfirmInternalSCI {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftSacService giftService;

	/**
	 * 선물수신 처리.
	 * 
	 * @param GiftConfirmSacInReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return GiftConfirmSacInRes
	 */
	@Override
	public GiftConfirmSacInRes modifyGiftConfirm(GiftConfirmSacInReq giftConfirmSacInReq) {

		if (StringUtils.isBlank(giftConfirmSacInReq.getTenantId())) {
			throw new StorePlatformException("SAC_PUR_0001", "tenantId");
		} else if (StringUtils.isBlank(giftConfirmSacInReq.getSystemId())) {
			throw new StorePlatformException("SAC_PUR_0001", "systemId");
		} else if (StringUtils.isBlank(giftConfirmSacInReq.getUserKey())) {
			throw new StorePlatformException("SAC_PUR_0001", "userKey");
		} else if (StringUtils.isBlank(giftConfirmSacInReq.getDeviceKey())) {
			throw new StorePlatformException("SAC_PUR_0001", "deviceKey");
		} else if (StringUtils.isBlank(giftConfirmSacInReq.getProdId())) {
			throw new StorePlatformException("SAC_PUR_0001", "prodId");
		} else if (StringUtils.isBlank(giftConfirmSacInReq.getPrchsId())) {
			throw new StorePlatformException("SAC_PUR_0001", "prchsId");
		} else if (StringUtils.isBlank(giftConfirmSacInReq.getRecvDt())) {
			throw new StorePlatformException("SAC_PUR_0001", "recvDt");
		} else if (StringUtils.isBlank(giftConfirmSacInReq.getRecvConfPathCd())) {
			throw new StorePlatformException("SAC_PUR_0001", "recvConfPathCd");
		}

		final String deviceModel = "";
		final String deviceOs = "";
		final String networkType = "";
		final String systemId = giftConfirmSacInReq.getSystemId();
		final String userKey = giftConfirmSacInReq.getUserKey();
		final String deviceKey = giftConfirmSacInReq.getDeviceKey();
		final String prchsId = giftConfirmSacInReq.getPrchsId();
		final String recvConfPathCd = giftConfirmSacInReq.getRecvConfPathCd();

		// TLog
		// 상품아이디
		final List<String> prodIdList = new ArrayList<String>();
		// tlog 상품아이디 셋팅
		prodIdList.add(giftConfirmSacInReq.getProdId());

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_PUR_0004").system_id(systemId).usermbr_no(userKey).phone_model(deviceModel)
						.os_version(deviceOs).insd_device_id(deviceKey).insd_usermbr_no(userKey)
						.network_type(networkType).purchase_id(prchsId).product_id(prodIdList)
						.recv_confirm_class(recvConfPathCd);
			}
		});

		this.logger.info("PRCHS,GiftController,SAC,GiftConfirmSacInReq,{}", giftConfirmSacInReq);

		// reqConvert처리 -> SC선물수신처리 -> resConvert 처리후 리턴
		return this.resConfirmConvert(this.giftService.updateGiftConfirm(this.reqConfirmConvert(giftConfirmSacInReq)));
	}

	/**
	 * 선물수신처리 reqConfirmConvert.
	 * 
	 * @param GiftConfirmSacInReq
	 *            선물수신처리 요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return GiftConfirmScReq
	 */
	private GiftConfirmScReq reqConfirmConvert(GiftConfirmSacInReq giftConfirmSacInReq) {
		GiftConfirmScReq req = new GiftConfirmScReq();

		req.setTenantId(giftConfirmSacInReq.getTenantId());
		req.setSystemId(giftConfirmSacInReq.getSystemId());
		req.setUserKey(giftConfirmSacInReq.getUserKey());
		req.setDeviceKey(giftConfirmSacInReq.getDeviceKey());
		req.setPrchsId(giftConfirmSacInReq.getPrchsId());
		req.setRecvDt(giftConfirmSacInReq.getRecvDt());
		req.setRecvConfPathCd(giftConfirmSacInReq.getRecvConfPathCd());
		req.setProdId(giftConfirmSacInReq.getProdId());

		return req;
	}

	/**
	 * 선물수신처리 resConfirmConvert.
	 * 
	 * @param giftComfirmScRes
	 *            선물수신처리 응답정보
	 * @return GiftConfirmSacInRes
	 */
	private GiftConfirmSacInRes resConfirmConvert(GiftConfirmScRes giftComfirmScRes) {
		GiftConfirmSacInRes res = new GiftConfirmSacInRes();
		res.setPrchsId(giftComfirmScRes.getPrchsId());
		res.setProdId(giftComfirmScRes.getProdId());
		res.setResultYn(giftComfirmScRes.getResultYn());
		this.logger.info("PRCHS,GiftController,SAC,GiftConfirmSacInRes,{}", res);
		return res;
	}

}
