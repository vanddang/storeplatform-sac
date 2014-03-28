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

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.device.UseableDeviceSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.device.UseableDeviceSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * DeviceProfileService Service 인터페이스(CoreStoreBusiness) 구현체.
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, 인크로스
 */
@Service
public class UseableDeviceServiceImpl implements UseableDeviceService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.product.service.UseableDeviceService#searchUseableDevice(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public UseableDeviceSacRes searchUseableDeviceList(@Validated UseableDeviceSacReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub

		CommonResponse commonResponse = new CommonResponse();
		UseableDeviceSacRes res = new UseableDeviceSacRes();
		TenantHeader tenantHeader = header.getTenantHeader();
		List<MetaInfo> usableDeviceList = null;

		req.setLangCd(tenantHeader.getLangCd());
		String modelNm = "";
		if (StringUtils.isNotEmpty(req.getDeviceModelNm())) {
			try {
				// 한글 모델명 처리
				modelNm = URLDecoder.decode(req.getDeviceModelNm(), "utf-8");
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

		// 서비스 그룹 코드가 어플리케이션(DP000201) 일때
		if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(req.getSvcGrpCd())) {

			if (StringUtils.isEmpty(req.getProductId())) {
				throw new StorePlatformException("SAC_DSP_0002", "productId", req.getProductId());
			}

			usableDeviceList = this.commonDAO.queryForList("UseableDevice.selectUseableDeviceForApp", req,
					MetaInfo.class);
		} else if (DisplayConstants.DP_SOCIAL_SHOPPING_PROD_SVC_GRP_CD.equals(req.getSvcGrpCd())) { // 서비스 그룹코드
			usableDeviceList = this.commonDAO.queryForList("UseableDevice.selectUseableDeviceForShopping", req,
					MetaInfo.class);
		} else {

			if (StringUtils.isEmpty(req.getProductId())) {
				throw new StorePlatformException("SAC_DSP_0002", "productId", req.getProductId());
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("productId", req.getProductId());

			ProductBasicInfo productBasicInfo = this.commonDAO.queryForObject("UseableDevice.searchProductBasicInfo",
					map, ProductBasicInfo.class);

			if (productBasicInfo != null) {
				String topMenuId = productBasicInfo.getTopMenuId();
				req.setTopMenuId(topMenuId);
				usableDeviceList = this.commonDAO.queryForList("UseableDevice.selectUseableDeviceForOther", req,
						MetaInfo.class);
			} else {
				commonResponse.setTotalCount(0);
			}
		}

		List<Device> deviceList = new ArrayList<Device>();
		if (usableDeviceList != null) {
			Iterator<MetaInfo> iterator = usableDeviceList.iterator();
			while (iterator.hasNext()) {
				MetaInfo metaInfo = iterator.next();
				Device device = new Device();
				// List<Identifier> identifierList = new ArrayList<Identifier>();
				// Identifier identifier = this.commonGenerator.generateIdentifier(
				// DisplayConstants.DP_PRODUCT_IDENTIFIER_CD, metaInfo.getProdId());
				// identifierList.add(identifier);
				// device.setIdentifierList(identifierList);
				commonResponse.setTotalCount(metaInfo.getTotalCount());
				device.setDeviceModelCd(metaInfo.getDeviceModelCd());
				device.setDeviceModelNm(metaInfo.getDeviceModelNm());
				device.setMnftCompCd(metaInfo.getMnftCompCd());
				device.setMnftCompNm(metaInfo.getMnftCompNm());
				device.setSvcGrpCd(req.getSvcGrpCd());
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
