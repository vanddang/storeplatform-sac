/**
 * 
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * 상품 ID에 대한 단말 Provisioning 조회 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
@Service
public class SellerAppDetailServiceImpl implements SellerAppDetailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.device.service.DeviceProductProvisioningService#searchProductProvisioning
	 * (com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningRes,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public SellerAppDetailRes searchDeviceProfile(SellerAppDetailReq req, SacRequestHeader header) {
		CommonResponse commonResponse = new CommonResponse();
		SellerAppDetailRes res = new SellerAppDetailRes();
		return res;
	}

}
