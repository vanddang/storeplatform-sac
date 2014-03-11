/**
 * 
 */
package com.skplanet.storeplatform.sac.display.other.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPakcageListReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPakcageListRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGeneratorImpl;

/**
 * Package 정보로 상품 ID 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
@Service
public class OtherPackageListServiceImpl implements OtherPackageListService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGeneratorImpl commonGenerator;

	@Autowired
	private AppInfoGenerator appGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.other.service.OtherPackageListService#searchProductListByPackageNm(com
	 * .skplanet.storeplatform.sac.client.display.vo.other.OtherPakcageListReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public OtherPakcageListRes searchProductListByPackageNm(OtherPakcageListReq req, SacRequestHeader header,
			List<String> prodIdList) {
		OtherPakcageListRes res = new OtherPakcageListRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		String osVersion = DisplayCommonUtil.getOsVer(deviceHeader.getOs());
		this.log.debug("#### osVerion : {}", osVersion);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("req", req);
		paramMap.put("PKG_LIST", prodIdList);
		paramMap.put("osVersion", osVersion);
		paramMap.put("deviceHeader", deviceHeader);

		List<MetaInfo> appList = this.commonDAO.queryForList("OtherPackageList.searchProdListByPackageNm", paramMap,
				MetaInfo.class);

		for (MetaInfo metaInfo : appList) {
			Product product = new Product();
			product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
			product.setApp(this.appGenerator.generateApp(metaInfo));
			productList.add(product);
		}
		commonResponse.setTotalCount(productList.size());
		res.setProductList(productList);
		res.setCommonResponse(commonResponse);
		return res;
	}
}
