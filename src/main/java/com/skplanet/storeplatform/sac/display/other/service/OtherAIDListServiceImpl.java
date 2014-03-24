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
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherAIDListReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherAIDListRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGeneratorImpl;

/**
 * AID로 상품 ID 조회 Service
 * 
 * Updated on : 2014. 3. 14. Updated by : 백승현, 인크로스.
 */
@Service
public class OtherAIDListServiceImpl implements OtherAIDListService {

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
	 * @see com.skplanet.storeplatform.sac.display.other.service.OtherAIDListService#searchProductListByAID(com
	 * .skplanet.storeplatform.sac.client.display.vo.other.OtherAIDListReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public OtherAIDListRes searchProductListByAID(OtherAIDListReq req, SacRequestHeader header, List<String> aIdList) {

		TenantHeader tenantHeader = header.getTenantHeader();

		OtherAIDListRes res = new OtherAIDListRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		String tenantId = tenantHeader.getTenantId();
		String langCd = tenantHeader.getLangCd();
		String deviceModelNo = req.getDeviceModelNo();
		String rshpCd = DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD;
		String partParentClsfCd = DisplayConstants.DP_PART_CHILD_CLSF_CD;

		this.log.debug("########### tenantId : " + tenantId);
		this.log.debug("########### langCd : " + langCd);
		this.log.debug("########### aIdList : " + aIdList);
		this.log.debug("########### deviceModelNo : " + deviceModelNo);
		this.log.debug("########### rshpCd : " + rshpCd);
		this.log.debug("########### partParentClsfCd : " + partParentClsfCd);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("req", req);
		paramMap.put("tenantId", tenantId);
		paramMap.put("langCd", langCd);
		paramMap.put("deviceModelNo", deviceModelNo);
		paramMap.put("arrayAId", aIdList);
		paramMap.put("rshpCd", rshpCd);
		paramMap.put("partParentClsfCd", partParentClsfCd);

		List<MetaInfo> appList = this.commonDAO.queryForList("OtherPackageList.searchProdListByAid", paramMap,
				MetaInfo.class);

		for (MetaInfo metaInfo : appList) {

			Product product = new Product();
			product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
			product.setApp(this.appGenerator.generateApp(metaInfo));

			// 상품에 대한 단말 지원 여부
			Support support = new Support();
			support.setType("device");
			support.setText(metaInfo.getDeviceSupport());
			product.setSupport(support);

			// 상품 판매 상태 코드
			product.setSalesStatus(metaInfo.getProdStatusCd());

			productList.add(product);
		}
		commonResponse.setTotalCount(productList.size());
		res.setProductList(productList);
		res.setCommonResponse(commonResponse);
		return res;
	}
}
