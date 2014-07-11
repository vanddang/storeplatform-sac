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
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPackageListReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherPackageListRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
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
	 * .skplanet.storeplatform.sac.client.display.vo.other.OtherPackageListReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, java.util.List)
	 */
	@Override
	public OtherPackageListRes searchProductListByPackageNm(OtherPackageListReq req, SacRequestHeader header,
			List<String> packageInfoList) {
		OtherPackageListRes res = new OtherPackageListRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		TenantHeader tenantHeader = header.getTenantHeader();
		String osVersion = DisplayCommonUtil.extractOsVer(deviceHeader.getOs());
		this.log.debug("#### osVerion : {}", osVersion);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("req", req);
		paramMap.put("PKG_LIST", packageInfoList);
		paramMap.put("osVersion", osVersion);
		paramMap.put("deviceHeader", deviceHeader);
		paramMap.put("tenantHeader", tenantHeader);
		paramMap.put("rshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		paramMap.put("partParentClsfCd", DisplayConstants.DP_PART_CHILD_CLSF_CD);

		List<MetaInfo> appList = this.commonDAO.queryForList("OtherPackageList.searchProdListByPackageNm", paramMap,
				MetaInfo.class);

		for (MetaInfo metaInfo : appList) {
			Product product = new Product();
			product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
			product.setApp(this.appGenerator.generateApp(metaInfo));
			product.setPrice(this.commonGenerator.generatePrice(metaInfo));
			product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
			product.setRights(this.commonGenerator.generateRights(metaInfo));
			productList.add(product);
		}
		commonResponse.setTotalCount(productList.size());
		res.setProductList(productList);
		res.setCommonResponse(commonResponse);
		return res;
	}
}
