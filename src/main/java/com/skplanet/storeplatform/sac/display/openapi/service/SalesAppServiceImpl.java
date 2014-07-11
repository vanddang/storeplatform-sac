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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppInfoSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppInfoSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * OpenApi SalesApp Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 03. 06. Updated by : 이태희.
 */
@Service
public class SalesAppServiceImpl implements SalesAppService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	CommonMetaInfoGenerator commonMetaInfoGenerator;

	@Autowired
	AppInfoGenerator appInfoGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.openapi.service.SalesAppService#searchSalesAppList(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader, com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppSacReq)
	 */
	@Override
	public SalesAppSacRes searchSalesAppList(SacRequestHeader header, SalesAppSacReq salesAppReq) {
		String salesStatus = salesAppReq.getSalesStatus();

		// 판매구분 유효값 체크
		if (!"PD000403".equals(salesStatus) && !"PD000404".equals(salesStatus)) {
			throw new StorePlatformException("SAC_DSP_0003", "salesStatus", salesStatus);
		}

		/*
		 * Parameter로 넘겨받은 OS Version 가공 추가 2014.07.11 Updated By : 이석희
		 */
		salesAppReq.setOsVersion(DisplayCommonUtil.extractOsVer(salesAppReq.getOsVersion()));

		// 헤더 세팅
		salesAppReq.setTenantId(header.getTenantHeader().getTenantId());

		SalesAppSacRes salesAppSacRes = new SalesAppSacRes();
		CommonResponse commonResponse = new CommonResponse();
		MetaInfo metaInfo = new MetaInfo();

		Product product = null;
		List<Product> productList = new ArrayList<Product>();

		// PKG Name 기반 상품 리스트 조회
		List<MetaInfo> resultList = this.commonDAO.queryForList("OpenApi.searchSalesAppList", salesAppReq,
				MetaInfo.class);

		if (resultList != null && !resultList.isEmpty()) {
			for (int i = 0; i < resultList.size(); i++) {
				metaInfo = resultList.get(i);
				product = new Product();

				// 상품ID 정보
				metaInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
				product.setIdentifierList(this.commonMetaInfoGenerator.generateIdentifierList(metaInfo));

				// APP 정보
				product.setApp(this.appInfoGenerator.generateApp(metaInfo));

				// 판매상태 정보
				product.setSalesStatus(metaInfo.getProdStatusCd());

				productList.add(product);
			}

			commonResponse.setTotalCount(metaInfo.getTotalCount());
		} else {
			commonResponse.setTotalCount(0);
		}

		salesAppSacRes.setProductList(productList);
		salesAppSacRes.setCommonResponse(commonResponse);
		return salesAppSacRes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.openapi.service.SalesAppService#getSalesAppInfo(com.skplanet.storeplatform
	 * .sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppInfoSacReq)
	 */
	@Override
	public SalesAppInfoSacRes getSalesAppInfo(SacRequestHeader header, SalesAppInfoSacReq salesAppInfoReq) {
		SalesAppInfoSacRes salesAppInfoSacRes = new SalesAppInfoSacRes();
		CommonResponse commonResponse = new CommonResponse();

		// PKG Name 기반 상품 정보 조회
		MetaInfo metaInfo = this.commonDAO.queryForObject("OpenApi.getSalesAppInfo", salesAppInfoReq, MetaInfo.class);

		if (metaInfo != null) {
			Product product = new Product();

			// 상품ID 정보
			metaInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
			product.setIdentifierList(this.commonMetaInfoGenerator.generateIdentifierList(metaInfo));

			// APP 정보
			product.setApp(this.appInfoGenerator.generateApp(metaInfo));

			salesAppInfoSacRes.setProduct(product);
			commonResponse.setTotalCount(1);
		} else {
			commonResponse.setTotalCount(0);
		}

		salesAppInfoSacRes.setCommonResponse(commonResponse);
		return salesAppInfoSacRes;
	}
}
