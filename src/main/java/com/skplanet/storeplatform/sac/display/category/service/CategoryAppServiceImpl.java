/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * CategoryApp Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@Service
public class CategoryAppServiceImpl implements CategoryAppService {
	private transient Logger logger = LoggerFactory.getLogger(CategoryAppServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.category.service.CategoryAppService#searchCategoryAppList(com.skplanet
	 * .storeplatform.sac.client.display.vo.category.CategoryAppSacReq)
	 */
	@Override
	public CategoryAppSacRes searchAppList(CategoryAppSacReq req, SacRequestHeader header)
			throws StorePlatformException {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchAppList Service started!!");
		this.logger.debug("----------------------------------------------------------------");

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		this.logger.debug("########################################################");
		this.logger.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.logger.debug("tenantHeader.getLangCd()		:	" + tenantHeader.getLangCd());
		this.logger.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.logger.debug("########################################################");

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

		CategoryAppSacRes appRes = new CategoryAppSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		String prodCharge = req.getProdCharge();
		String menuId = req.getMenuId();
		String orderedBy = req.getOrderedBy();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(prodCharge)) {
			throw new StorePlatformException("SAC_DSP_0002", "prodCharge", prodCharge);
		}
		if (StringUtils.isEmpty(menuId)) {
			throw new StorePlatformException("SAC_DSP_0002", "menuId", menuId);
		}
		if (StringUtils.isEmpty(orderedBy)) {
			throw new StorePlatformException("SAC_DSP_0002", "orderedBy", orderedBy);
		}

		// 파라미터 유효값 체크
		if (!"A".equals(prodCharge) && (!"Y".equals(prodCharge) && !"N".equals(prodCharge))) {
			throw new StorePlatformException("SAC_DSP_0003", "prodCharge", prodCharge);
		}
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.logger.debug("----------------------------------------------------------------");
						this.logger.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.logger.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}
		if (!"download".equals(orderedBy)) {
			throw new StorePlatformException("SAC_DSP_0003", "orderedBy", orderedBy);
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

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}

		if (req.getDummy() == null) { // Dummy 가 아닐 경우
			// 일반 카테고리 앱 상품 조회
			List<ProductBasicInfo> appList = this.commonDAO.queryForList("Category.selectCategoryAppList", req,
					ProductBasicInfo.class);

			if (!appList.isEmpty()) {
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				for (ProductBasicInfo productBasicInfo : appList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					MetaInfo retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);

					if (retMetaInfo != null) {
						Product product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
						productList.add(product);
					}
				}
				commonResponse.setTotalCount(appList.get(0).getTotalCount());
				appRes.setProductList(productList);
				appRes.setCommonResponse(commonResponse);
			} else {
				// 조회 결과 없음
				commonResponse.setTotalCount(0);
				appRes.setProductList(productList);
				appRes.setCommonResponse(commonResponse);
			}
		} else {
			// Dummy 일 경우
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			ProductBasicInfo productBasicInfo = new ProductBasicInfo();
			productBasicInfo.setProdId("0000142301");

			reqMap.put("productBasicInfo", productBasicInfo);
			reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
			MetaInfo retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);

			if (retMetaInfo != null) {
				Product product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
				productList.add(product);
			}
			commonResponse.setTotalCount(1);
			appRes.setProductList(productList);
			appRes.setCommonResponse(commonResponse);
		}

		return appRes;
	}
}
