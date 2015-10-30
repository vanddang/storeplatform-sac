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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingListSc;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScRes;
import com.skplanet.storeplatform.purchase.history.vo.Hiding;

/**
 * 구매 서비스 인터페이스 구현체
 * 
 * Updated on : 2013-12-10 Updated by : 조용진, 엔텔스.
 */
@Service
public class HidingScServiceImpl implements HidingScService {
	private static Logger logger = LoggerFactory.getLogger(HidingScServiceImpl.class);

	/*
	 * 데이터 베이스에서 데이터를 조회할 별도 DAO 객체를 생성하지 않고, CommonDAO 를 사용한다.
	 */
	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 구매내역 숨김처리.
	 * 
	 * @param hidingScReq
	 *            요청정보
	 * @return List<HidingScRes>
	 */
	@Override
	public List<HidingScRes> updateHiding(HidingScReq hidingScReq) {

		// return List<data>
		List<HidingScRes> hidingListScRes = new ArrayList<HidingScRes>();
		// return data
		// sql 조회 셋팅을 위한 객체
		Hiding hiding = new Hiding();

		// 유저정보 셋팅
		hiding.setTenantId(hidingScReq.getTenantId());
		hiding.setSystemId(hidingScReq.getSystemId());
		hiding.setUserKey(hidingScReq.getUserKey());
		hiding.setDeviceKey(hidingScReq.getDeviceKey());
		hiding.setSendYn(hidingScReq.getSendYn());
		// Req prod list size만큼 loop
		for (HidingListSc hidingListSc : hidingScReq.getHidingList()) {
			int count = 0;
			hiding.setPrchsDtlId(hidingListSc.getPrchsDtlId());
			hiding.setPrchsId(hidingListSc.getPrchsId());
			hiding.setHidingYn(hidingListSc.getHidingYn());

			if (hidingScReq.getSendYn().equals("Y")) {
				count = this.commonDAO.update("PurchaseHiding.hidingSendY", hiding);

			} else {
				count = this.commonDAO.update("PurchaseHiding.hidingSendN", hiding);
			}

			this.logger.debug("=count========== {}:", count);
			HidingScRes hidingScRes = new HidingScRes();
			if (count > 0) {
				hidingScRes.setPrchsId(hidingListSc.getPrchsId());
				hidingScRes.setPrchsDtlId(hidingListSc.getPrchsDtlId());
				hidingScRes.setResultYn("Y");
			} else {
				hidingScRes.setPrchsId(hidingListSc.getPrchsId());
				hidingScRes.setPrchsDtlId(hidingListSc.getPrchsDtlId());
				hidingScRes.setResultYn("N");

			}
			hidingListScRes.add(hidingScRes);
		}

		return hidingListScRes;
	}

}
