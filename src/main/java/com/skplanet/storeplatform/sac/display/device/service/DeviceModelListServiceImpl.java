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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceModelListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceModelListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.net.URLDecoder;
import java.util.*;

/**
 * DeviceModelList Service 인터페이스 구현체.
 * 
 */
@Service
public class DeviceModelListServiceImpl implements DeviceModelListService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public DeviceModelListSacRes searchDeviceList(@Validated DeviceModelListSacReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub

		CommonResponse commonResponse = new CommonResponse();
		DeviceModelListSacRes res = new DeviceModelListSacRes();
		TenantHeader tenantHeader = header.getTenantHeader();
		List<MetaInfo> deviceModelList = null;
		List<Device> deviceList = new ArrayList<Device>();
		
		// request Parameter가 없을 경우
		if(StringUtils.isEmpty(req.getCmntCompCd()) && StringUtils.isEmpty(req.getMnftCompCd()) && 
				StringUtils.isEmpty(req.getDeviceModelCd()) && StringUtils.isEmpty(req.getDeviceModelNm())) {
			commonResponse.setTotalCount(0);
			res.setDeviceList(deviceList);
			res.setCommonResponse(commonResponse);
			return res;
		}
			
		req.setLangCd(tenantHeader.getLangCd());
		String modelNm = "";

		if (StringUtils.isNotEmpty(req.getDeviceModelNm())) {
			try {
				// 한글 모델명 처리
				modelNm = URLDecoder.decode(req.getDeviceModelNm(), "utf-8"); 
				//modelNm = new String(req.getDeviceModelNm().getBytes("8859_1"), "utf-8");
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_0014");
			}
			req.setDeviceModelNm(modelNm);// Device 모델명
		}

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset); // set offset

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count); // set count

		deviceModelList = this.commonDAO.queryForList("DeviceModelList.selectDeviceModelList", req,
						MetaInfo.class);
		
		if (deviceModelList != null) {
			Iterator<MetaInfo> iterator = deviceModelList.iterator();
			while (iterator.hasNext()) {
				MetaInfo metaInfo = iterator.next();
				Device device = new Device();
				
				commonResponse.setTotalCount(metaInfo.getTotalCount());
				device.setDeviceModelCd(metaInfo.getDeviceModelCd());
				device.setDeviceModelNm(metaInfo.getDeviceModelNm());	
				device.setCmntCompCd(metaInfo.getCmntCompCd());
				device.setCmntCompNm(metaInfo.getCmntCompNm());
				device.setMnftCompCd(metaInfo.getMnftCompCd());
				device.setMnftCompNm(metaInfo.getMnftCompNm());
				deviceList.add(device);
			}
		} else {
			commonResponse.setTotalCount(0);
		}
		res.setDeviceList(deviceList);
		res.setCommonResponse(commonResponse);
		return res;
	}

}
