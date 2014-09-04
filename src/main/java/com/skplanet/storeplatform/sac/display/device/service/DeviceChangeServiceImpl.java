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
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceChangeSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceUserAgentSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceUserAgentSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * DeviceChange Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 03. 11. Updated by : 이태희.
 */
@Service
public class DeviceChangeServiceImpl implements DeviceChangeService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.device.service.DeviceChangeService#searchDeviceChangeModelList()
	 */
	@Override
	public DeviceChangeSacRes searchDeviceChangeModelList() {
		DeviceChangeSacRes deviceChangeSacRes = new DeviceChangeSacRes();
		CommonResponse commonResponse = new CommonResponse();
		MetaInfo metaInfo = new MetaInfo();

		Device device = null;
		List<Device> deviceList = new ArrayList<Device>();

		// 단말 모델 정보 조회 (운영자 관리)
		List<MetaInfo> resultList = this.commonDAO.queryForList("DeviceModel.searchDeviceChangeModelList", null,
				MetaInfo.class);

		if (resultList != null && !resultList.isEmpty()) {
			for (int i = 0; i < resultList.size(); i++) {
				metaInfo = resultList.get(i);
				device = new Device();

				device.setDeviceModelCd(metaInfo.getDeviceModelCd());
				device.setDeviceModelNm(metaInfo.getDeviceModelNm());
				deviceList.add(device);
			}

			commonResponse.setTotalCount(metaInfo.getTotalCount());
		} else {
			commonResponse.setTotalCount(0);
		}

		deviceChangeSacRes.setDeviceList(deviceList);
		deviceChangeSacRes.setCommonResponse(commonResponse);
		return deviceChangeSacRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.device.service.DeviceChangeService#searchDeviceUserAgentList(com.skplanet
	 * .storeplatform.sac.client.display.vo.device.DeviceUserAgentSacReq)
	 */
	@Override
	public DeviceUserAgentSacRes searchDeviceUserAgentList(DeviceUserAgentSacReq deviceReq) {
		DeviceUserAgentSacRes deviceUserAgentSacRes = new DeviceUserAgentSacRes();
		CommonResponse commonResponse = new CommonResponse();
		MetaInfo metaInfo = new MetaInfo();

		Device device = null;
		List<Device> deviceList = new ArrayList<Device>();

		// 단말 모델 정보 조회 (by UserAgent)
		List<MetaInfo> resultList = this.commonDAO.queryForList("DeviceModel.searchDeviceUserAgentList", deviceReq,
				MetaInfo.class);

		if (resultList != null && !resultList.isEmpty()) {
			for (int i = 0; i < resultList.size(); i++) {
				metaInfo = resultList.get(i);
				device = new Device();

				device.setDeviceModelCd(metaInfo.getDeviceModelCd());
				device.setUaCd(metaInfo.getUaCd());
				deviceList.add(device);
			}

			commonResponse.setTotalCount(metaInfo.getTotalCount());
		} else {
			commonResponse.setTotalCount(0);
		}

		deviceUserAgentSacRes.setDeviceList(deviceList);
		deviceUserAgentSacRes.setCommonResponse(commonResponse);
		return deviceUserAgentSacRes;
	}
}
