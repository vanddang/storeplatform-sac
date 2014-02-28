package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.DcdSupportProductRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfo;

/**
 * 
 * DCD 지원 상품 조회.
 * 
 * Updated on : 2014. 2. 27. Updated by : 이석희, 아이에스플러스
 */
@Service
public class SearchDcdSupportProductServiceImpl implements SearchDcdSupportProductService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.localsci.sci.service.ProductInfoService#getProductList(com.skplanet.
	 * storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq)
	 */
	@Override
	public DcdSupportProductRes getDcdSupportProductList() {
		DcdSupportProductRes res = new DcdSupportProductRes();
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
		ProductInfo productInfo = null;
		List<Map> productList = this.commonDAO.queryForList("LocalSci.getDcdSupportProduct", "", Map.class);

		if (productList.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0009");
		} else {
			Map<String, Object> mapProduct = null;
			String productId = "";
			String dcdSprtCd = "";
			for (int i = 0; i < productList.size(); i++) {
				productInfo = new ProductInfo();
				mapProduct = productList.get(i);
				productId = ObjectUtils.toString(mapProduct.get("PROD_ID"));
				dcdSprtCd = ObjectUtils.toString(mapProduct.get("DCD_SPRT_CD"));

				productInfo.setProdId(productId);
				productInfo.setDcdSprtCd(dcdSprtCd);
				productInfoList.add(productInfo);
			}
		}
		res.setProductList(productInfoList);
		return res;
	}
}
