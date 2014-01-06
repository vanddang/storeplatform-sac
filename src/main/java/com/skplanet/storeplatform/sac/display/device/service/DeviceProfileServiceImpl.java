/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.device.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;
import com.skplanet.storeplatform.sac.display.device.vo.DeviceProfileDTO;

/**
 * DeviceProfileService Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, Incross
 */
@Service
@Transactional
public class DeviceProfileServiceImpl implements DeviceProfileService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.product.service.DeviceProfileService#searchDeviceProfile(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public DeviceProfileRes searchDeviceProfile(DeviceProfileReq requestVO) {
		// TODO Auto-generated method stub

		CommonResponse commonRes = new CommonResponse();
		DeviceProfileRes deviceProfileResponse = new DeviceProfileRes();
		DeviceProfileDTO deviceProfileDTO = this.commonDAO.queryForObject("DeviceProfile.selectDeviceProfile",
				requestVO, DeviceProfileDTO.class);

		// Device Profile 조회
		Device device = new Device();
		if (deviceProfileDTO != null) {
			String sComp = deviceProfileDTO.getCmntCompCd();
			StringBuilder sb = new StringBuilder();

			sb.append(deviceProfileDTO.getDeviceModelCd());
			sb.append("/");
			sb.append(deviceProfileDTO.getMakeCompNm());

			device.setIdentifier(sb.toString());
			// OMD 여부
			device.setModel(deviceProfileDTO.getDeviceModelCd());
			device.setType("US001204".equals(sComp) ? "omd" : "normal");
			device.setManufacturer(deviceProfileDTO.getMakeCompNm());
			device.setPlatform(deviceProfileDTO.getVmTypeNm());

			device.setServices(deviceProfileDTO.getServicesMap());
			device.setSupportedHardware(deviceProfileDTO.getSupportedHardwareMap());
			device.setModelExplain(deviceProfileDTO.getModelNm());
			// TODO osm1021 DPI 넣어주는 처리가 필요
		} else { // 미지원단말
			device.setIdentifier("android_standard");
			device.setType("restrict");
		}

		deviceProfileResponse.setDevice(device);
		deviceProfileResponse.setCommonRes(commonRes);
		return deviceProfileResponse;
	}
}
