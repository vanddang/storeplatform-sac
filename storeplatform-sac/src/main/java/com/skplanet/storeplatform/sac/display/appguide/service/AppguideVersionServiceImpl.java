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
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideVersionSacReq;
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

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * 
	 * <pre>
	 * 앱 가이드 버전정보 조회.
	 * 패키지명과 OS Version으로 App 상품의 버전을 조회한다.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppguideVersionSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppguideSacRes
	 */
	@Override
	public AppguideSacRes searchVersion(AppguideVersionSacReq requestVO, SacRequestHeader requestHeader) {

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
		String osVersion = requestVO.getOsVersion();
		if (this.log.isDebugEnabled()) {
			this.log.debug("[{}] packageName : {}", className, packageName);
			this.log.debug("[{}] osVersion : {}", className, osVersion);
		}

		mapReq.put("packageName", packageName);
		mapReq.put("osVersion", osVersion);

		// 패키지명과 os version으로 앱 정보를 조회한다.
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

		commonResponse.setTotalCount(1);
		responseVO.setCommonResponse(commonResponse);
		responseVO.setProductList(productList);
		return responseVO;
	}

}
