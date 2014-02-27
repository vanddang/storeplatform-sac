package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.ProductInfoService;

/**
 * 
 * ProductInfoSCIController Controller
 * 
 * 구매 내역 조회 시 필요한 상품 메타 정보 조회
 * 
 * Updated on : 2014. 2. 24. Updated by : 오승민, 인크로스
 */
@LocalSCI
public class ProductInfoSCIController implements ProductInfoSCI {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductInfoService productInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ProductInfoSCI#getProductList(com.skplanet
	 * .storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq)
	 */
	@Override
	public ProductInfoSacRes getProductList(@Validated ProductInfoSacReq req) {
		if (req.getList().size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "list",
					DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT);
		}
		return this.productInfoService.getProductList(req);
	}

}
