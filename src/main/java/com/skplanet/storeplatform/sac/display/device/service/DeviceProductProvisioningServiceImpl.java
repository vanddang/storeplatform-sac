/**
 * 
 */
package com.skplanet.storeplatform.sac.display.device.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
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
 * 상품 ID에 대한 단말 Provisioning 조회 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
@Service
public class DeviceProductProvisioningServiceImpl implements DeviceProductProvisioningService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private DisplayCommonService displayCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.device.service.DeviceProductProvisioningService#searchProductProvisioning
	 * (com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, java.util.List)
	 */
	@Override
	public DeviceProductProvisioningRes searchProductProvisioning(DeviceProductProvisioningReq req,
			SacRequestHeader header, List<String> prodIdList) {
		CommonResponse commonResponse = new CommonResponse();
		DeviceProductProvisioningRes res = new DeviceProductProvisioningRes();
		List<Product> productList = new ArrayList<Product>();
		TenantHeader tenantHeader = header.getTenantHeader();
		Product product = null;

		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelNo());

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("virtualDeviceModelNo", DisplayConstants.DP_ANY_PHONE_4MM);
		paramMap.put("contentTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);
		paramMap.put("list", prodIdList);
		paramMap.put("req", req);
		paramMap.put("tenantHeader", tenantHeader);
		paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		paramMap.put("rshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("supportDevice", supportDevice);

		if (supportDevice != null) {
			List<MetaInfo> metaInfoList = this.commonDAO.queryForList(
					"DeviceProductProvisioning.searchProductProvisioning", paramMap, MetaInfo.class);
			if (metaInfoList != null) {
				for (MetaInfo metaInfo : metaInfoList) {
					product = new Product();
					product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
					product.setRights(this.commonGenerator.generateRights(metaInfo));
					productList.add(product);
				}
			}
		}
		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setProductList(productList);
		return res;
	}

}
