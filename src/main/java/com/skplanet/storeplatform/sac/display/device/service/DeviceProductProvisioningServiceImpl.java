/**
 * 
 */
package com.skplanet.storeplatform.sac.display.device.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 상품 ID에 대한 단말 Provisioning 조회 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
@Service
public class DeviceProductProvisioningServiceImpl implements DeviceProductProvisioningService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.device.service.DeviceProductProvisioningService#searchProductProvisioning
	 * (com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProductProvisioningRes,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public DeviceProductProvisioningRes searchProductProvisioning(DeviceProductProvisioningReq req,
			SacRequestHeader header) {
		CommonResponse commonResponse = new CommonResponse();
		DeviceProductProvisioningRes res = new DeviceProductProvisioningRes();

		return res;
	}

}
