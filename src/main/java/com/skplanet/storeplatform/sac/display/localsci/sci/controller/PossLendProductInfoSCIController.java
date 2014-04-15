package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PossLendProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacRes;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.PossLendProductInfoService;

/**
 * 
 * PossLendProductInfoSCIController Controller
 * 
 * 소장/대여 상품 정보 조회
 * 
 * Updated on : 2014. 4. 15. Updated by : 홍지호, 엔텔스
 */
@LocalSCI
public class PossLendProductInfoSCIController implements PossLendProductInfoSCI {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PossLendProductInfoService possLendProductInfoService;

	/**
	 * <pre>
	 * 소장/대여 상품 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PossLendProductInfoSacRes 소장/대여 상품 정보 리스트
	 */
	@Override
	public PossLendProductInfoSacRes searchPossLendProductInfo(PossLendProductInfoSacReq req) {
		return this.possLendProductInfoService.searchPossLendProductInfo(req);
	}

}
