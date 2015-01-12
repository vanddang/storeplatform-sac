/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppListReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppListRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * App 목록 요청 인터페이스(CoreStoreBusiness) 구현체.
 * 
 * Updated on : 2014. 3. 6. Updated by : 오승민, 인크로스.
 */
@Service
public class SellerAppListServiceImpl implements SellerAppListService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private AppInfoGenerator appGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.product.service.DeviceProfileService#searchDeviceProfile(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SellerAppListRes searchSellerAppList(SellerAppListReq req, SacRequestHeader header) {
		SellerAppListRes res = new SellerAppListRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();
		TenantHeader tenantHeader = header.getTenantHeader();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantHeader", tenantHeader);
		paramMap.put("req", req);
		paramMap.put("partClsfCd", DisplayConstants.DP_PART_PARENT_CLSF_CD);

		List<MetaInfo> appList = this.commonDAO.queryForList("OpenApi.getAppList", paramMap, MetaInfo.class);

		for (MetaInfo metaInfo : appList) {
			Product product = new Product();
			product.setTitle(this.commonGenerator.generateTitle(metaInfo));
			product.setDate(this.commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, metaInfo.getRegDt()));
			product.setSalesStatus(metaInfo.getProdStatusCd());
			product.setApp(this.appGenerator.generateApp(metaInfo));
			productList.add(product);
		}
		commonResponse.setTotalCount(productList.size());
		res.setProductList(productList);
		res.setCommonResponse(commonResponse);
		return res;
	}
}
