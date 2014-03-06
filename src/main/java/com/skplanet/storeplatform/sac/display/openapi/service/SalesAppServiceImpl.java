/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * OpenApi SalesApp Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 03. 06. Updated by : 이태희.
 */
@Service
public class SalesAppServiceImpl implements SalesAppService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public SalesAppSacRes searchSalesAppList(SacRequestHeader header, SalesAppSacReq salesAppSacReq) {
		SalesAppSacRes salesAppSacRes = new SalesAppSacRes();
		CommonResponse commonResponse = new CommonResponse();

		// OS VERSION 가공
		String[] headerOsVer = header.getDeviceHeader().getOs().trim().split("/");

		String osVersion = headerOsVer[1];
		String osVersionOrginal = osVersion;
		String[] osVersionTemp = StringUtils.split(osVersionOrginal, ".");
		if (osVersionTemp.length == 3) {
			osVersion = osVersionTemp[0] + "." + osVersionTemp[1];
		}

		salesAppSacReq.setTenantId(header.getTenantHeader().getTenantId());
		salesAppSacReq.setOsVersion(osVersion);

		MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForList("OpenApi.searchSalesAppList", salesAppSacReq);
		this.logger.debug("{}", metaInfo);

		return salesAppSacRes;
	}
}
