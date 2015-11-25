package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.CmpxInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxBasicInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxBasicInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductListRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductSacReq;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.CmpxInfoService;

/**
 * 
 * CmpxInfoSCIController Controller
 * 
 * 이용권 상품 정보 조회.
 * 
 * Updated on : 2015. 5. 7. Updated by : 김형식 , 지티소프트
 */
@LocalSCI
public class CmpxInfoSCIController implements CmpxInfoSCI {
	@Autowired
	private CmpxInfoService cmpxInfoService;

	/**
	 * <pre>
	 * 이용권 기본 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxBasicInfoSacRes 상품 메타 정보 리스트
	 */
	@Override
	public CmpxBasicInfoSacRes searchCmpxBasicInfoList(@Validated CmpxBasicInfoSacReq req) {
		if (req.getList().size() > DisplayConstants.DP_PRODUCT_INFO_PARAMETER_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "list", DisplayConstants.DP_PRODUCT_INFO_PARAMETER_LIMIT);
		}
		return this.cmpxInfoService.searchCmpxBasicInfoList(req);
	}

	/**
	 * <pre>
	 * 이용권에 등록된 상품 정보 조회 .
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxProductSacReq 상품 메타 정보 리스트
	 */
	@Override
	public CmpxProductListRes searchCmpxProductList(@Validated CmpxProductSacReq req) {
		return this.cmpxInfoService.searchCmpxProductList(req);
	}

	/**
	 * <pre>
	 * 이용권 및 에피소드 상품 정보 조회 .
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxProductInfoSacReq 상품 메타 정보 리스트
	 */
	@Override
	public CmpxProductInfo searchCmpxProductInfo(@Validated CmpxProductInfoSacReq req) {
		return this.cmpxInfoService.searchCmpxProductInfo(req);
	}

}
