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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.device.vo.DeviceProfile;

/**
 * DeviceProfileService Service 인터페이스(CoreStoreBusiness) 구현체.
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, 인크로스
 */
@Service
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
	public DeviceProfileRes searchDeviceProfile(DeviceProfileReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub

		CommonResponse commonResponse = new CommonResponse();
		DeviceProfileRes deviceProfileResponse = new DeviceProfileRes();
		DeviceProfile deviceProfile = this.commonDAO.queryForObject("DeviceProfile.selectDeviceProfile", requestVO,
				DeviceProfile.class);

		// DeviceHeader Profile 조회
		Device device = new Device();
		if (deviceProfile != null) {
			Map<String, Object> supportedHardwareMap = deviceProfile.getSupportedHardwareMap();
			String dpi = header.getDeviceHeader().getDpi();
			// dpi 세팅
			supportedHardwareMap.put("dpi", dpi);

			String sComp = deviceProfile.getCmntCompCd();
			StringBuilder sb = new StringBuilder();

			sb.append(deviceProfile.getMakeCompNm());
			sb.append("/");
			sb.append(deviceProfile.getDeviceModelCd());

			device.setIdentifier(sb.toString());
			// OMD 여부
			device.setModel(deviceProfile.getDeviceModelCd());
			device.setType(DisplayConstants.DP_OMD_TYPE_CD.equals(sComp) ? DisplayConstants.DP_OMD_TYPE_NM : DisplayConstants.DP_OMD_NORMAL_NM);
			device.setManufacturer(deviceProfile.getMakeCompNm());
			device.setPlatform(deviceProfile.getVmTypeNm());
			device.setUaCd(deviceProfile.getUaCd());

			device.setServices(deviceProfile.getServicesMap());
			device.setSupportedHardware(supportedHardwareMap);
			device.setModelExplain(deviceProfile.getModelNm());
			device.setPhoneType(DisplayConstants.DP_PHONE_DEVICE_TYPE_CD.equals(deviceProfile.getDeviceTypeCd()) ? DisplayConstants.DP_PHONE_DEVICE_TYPE_NM : DisplayConstants.DP_TABLET_DEVICE_TYPE_NM);

			commonResponse.setTotalCount(1);
		} else { // 미지원단말
			device.setIdentifier(DisplayConstants.DP_ANDROID_STANDARD_NM);
			device.setType("restrict");
			commonResponse.setTotalCount(0);
		}

		deviceProfileResponse.setDevice(device);
		deviceProfileResponse.setCommonResponse(commonResponse);
		return deviceProfileResponse;
	}
}
