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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.device.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * DeviceModelCode Service 인터페이스(CoreStoreBusiness) 구현체
 */
@Service
public class DeviceModelCodeServiceImpl implements DeviceModelCodeService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public DeviceUaCdSacRes searchDeviceModelCode(DeviceUaCdSacReq req) {
		DeviceUaCdSacRes deviceUaCdSacRes = new DeviceUaCdSacRes();

		// 단말 모델 정보 조회 (by UaCd)
		String param = req.getUaCd();
		String deviceModelCd = this.commonDAO.queryForObject("DeviceModel.searchDeviceModelCode", param, String.class);
		deviceUaCdSacRes.setDeviceModelNo(deviceModelCd);
		return deviceUaCdSacRes;
	}
}
