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
import org.springframework.beans.factory.annotation.Value;
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

	@Value("#{config['interwork.cine21.sellermbrno']}")
	private String sellerMbrNo;
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
		// proterties에서 cine21 sellermbrno 리스트를 가저온다
		String[] cine21MbrNo = this.sellerMbrNo.split(",");
		InterworkingScReq req = new InterworkingScReq();
		// 조건 셋팅
		req.setTenantId(interworkingSacReq.getTenantId());
		req.setSystemId(interworkingSacReq.getSystemId());
		req.setPrchsId(interworkingSacReq.getPrchsId());
		req.setInsdUsermbrNo(interworkingSacReq.getUserKey());
		req.setInsdDeviceId(interworkingSacReq.getDeviceKey());
		req.setPrchsDt(interworkingSacReq.getPrchsDt());
		req.setPrchsCancelDt(interworkingSacReq.getPrchsCancelDt());

		int updateCount = 0;
		for (Interworking interworkingSac : interworkingSacReq.getInterworkingList()) {
			// 상품리스트에 대한 조건 셋팅
			req.setProdId(interworkingSac.getProdId());
			req.setProdAmt(interworkingSac.getProdAmt());
			req.setCompContentsId(interworkingSac.getCompContentsId());
			for (int i = 0; i < cine21MbrNo.length; i++) {
				// cine21는 sellermbrNo로 구분한다 (인터파크 2014-03-10 실시간연동 제거)
				if (interworkingSac.getSellermbrNo().equals(cine21MbrNo[i])) {
					this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					this.logger.debug("@@@@@@@@@@@@ cine21 Start @@@@@@@@@@@@");
					this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					// 판매재회원번호 기준으로 처리
					req.setTransClasCd(PurchaseConstants.TRANS_CLAS_SELLER_MBR_NO);
					req.setTransClasValue(interworkingSac.getSellermbrNo());
					// 전송테이블에 저장
					updateCount += this.interworkingSCI.createInterworking(req);
				}
			}
		}
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		this.logger.debug("@@@@@@@@@@@@ End updateCount @@@@@@@@@@@@:{}", updateCount);
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}
}
