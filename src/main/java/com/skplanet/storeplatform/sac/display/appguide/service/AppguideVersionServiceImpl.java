/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.appguide.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.appguide.vo.Appguide;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * App guide Version Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 02. 27. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppguideVersionServiceImpl implements AppguideVersionService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private int totalCount = 0;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.AppguideVersionServiceImpl#searchVersion(AppguideSacReq
	 * requestVO, SacRequestHeader requestHeader)
	 */
	@Override
	public AppguideSacRes searchVersion(AppguideSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException {

		AppguideSacRes responseVO = new AppguideSacRes();

		List<Product> productList = new ArrayList<Product>();

		CommonResponse commonResponse = new CommonResponse();

		String className = this.getClass().getName();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		Map<String, Object> mapReq = new HashMap<String, Object>();
		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

		String packageName = requestVO.getPackageName();
		/*
		 * String userKey = requestVO.getUserKey(); String deviceIdType = requestVO.getDeviceIdType(); String deviceId =
		 * requestVO.getDeviceId();
		 */
		String osVersion = requestVO.getOsVersion();
		if (this.log.isDebugEnabled()) {
			this.log.debug("[{}] packageName : {}", className, packageName);
			this.log.debug("[{}] osVersion : {}", className, osVersion);
		}

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(packageName)) {
			throw new StorePlatformException("SAC_DSP_0002", "packageName", packageName);
		}
		/*
		 * if (StringUtils.isEmpty(userKey)) { throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey); }
		 * if (StringUtils.isEmpty(deviceIdType)) { throw new StorePlatformException("SAC_DSP_0002", "deviceIdType",
		 * deviceIdType); } if (StringUtils.isEmpty(deviceId)) { throw new StorePlatformException("SAC_DSP_0002",
		 * "deviceId", deviceId); }
		 */
		if (StringUtils.isEmpty(osVersion)) {
			throw new StorePlatformException("SAC_DSP_0002", "osVersion", osVersion);
		}
		mapReq.put("packageName", packageName);
		mapReq.put("osVersion", osVersion);

		// 기기ID유형 유효값 체크
		/*
		 * if (!StringUtils.equalsIgnoreCase(DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN, deviceIdType)) { throw new
		 * StorePlatformException("SAC_DSP_0003", "deviceIdType", deviceIdType); }
		 */
		Appguide appguide = this.commonDAO.queryForObject("Appguide.getVersion", mapReq, Appguide.class);
		if (appguide == null) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		Product product = new Product();
		App app = new App();
		app.setPackageName(appguide.getApkPkg());
		app.setVersionCode(appguide.getApkVerCd());
		product.setApp(app);
		productList.add(product);

		this.totalCount = 1;

		commonResponse.setTotalCount(this.totalCount);
		responseVO.setCommonRes(commonResponse);
		responseVO.setProductList(productList);
		return responseVO;
	}

}
