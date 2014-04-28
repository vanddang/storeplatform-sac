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

import org.apache.commons.lang3.StringUtils;
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

	@Value("#{propertiesForSac['interwork.cine21.sellermbrno']}")
	private String sellerMbrNo;
	@Value("#{propertiesForSac['interwork.interpark.maillcd']}")
	private String mallCd;
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

		// 씨네21 판매자 회원번호를 properties에서 가저온다.
		String[] cine21MbrNo = this.sellerMbrNo.split(",");
		this.logger.debug("PRCHS,InterworkingSacService,SAC,REQ,{}", interworkingSacReq);
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
		// cine21는 sellermbrNo로 구분한다 (인터파크 2014-03-10 실시간연동 제거(전송테이블저장 추가 2014-03-13))
		for (Interworking interworkingSac : interworkingSacReq.getInterworkingList()) {
			// 상품리스트에 대한 조건 셋팅
			req.setProdId(interworkingSac.getProdId());
			req.setProdAmt(interworkingSac.getProdAmt());
			req.setCompCid(interworkingSac.getCompCid());
			// 인터파크 상품인지 확인후 전송테이블에 저장
			if (StringUtils.equals(this.mallCd, interworkingSac.getMallCd())) {
				this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				this.logger.debug("@@@@@@@@@@@@ interpark Start @@@@@@@@@@@@");
				this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				req.setTransClasCd(PurchaseConstants.TRANS_CLAS_MALL_CD);
				req.setTransClasValue(interworkingSac.getMallCd());
				updateCount += this.interworkingSCI.createInterworking(req);
			}
			// cine21 상품인지 확인(구매상품이의 판매자회원번호가 cine21이면 전송테이블에 저장)
			for (int i = 0; i < cine21MbrNo.length; i++) {
				if (StringUtils.equals(cine21MbrNo[i], interworkingSac.getSellermbrNo())) {
					this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					this.logger.debug("@@@@@@@@@@@@ cine21 Start @@@@@@@@@@@@");
					this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					// 판매재회원번호 기준으로 처리
					req.setTransClasCd(PurchaseConstants.TRANS_CLAS_SELLER_MBR_NO);
					req.setTransClasValue(interworkingSac.getSellermbrNo());
					// 전송테이블에 저장
					updateCount += this.interworkingSCI.createInterworking(req);
					updateCount += this.interworkingSCI.createInterworking(req);
				}
			}
		}
		this.logger.debug("PRCHS,InterworkingSacService,SAC,REQ,{}", updateCount);
	}
}
