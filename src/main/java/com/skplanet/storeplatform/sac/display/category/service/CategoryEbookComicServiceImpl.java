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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEbookComicSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CategoryEbookComic Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 01. 06. Updated by : 이태희, SK 플래닛.
 */
@Service
public class CategoryEbookComicServiceImpl implements CategoryEbookComicService {
	private transient Logger logger = LoggerFactory.getLogger(CategoryEbookComicServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
    private MemberBenefitService memberBenefitService;
	
	/**
	 * 
	 * <pre>
	 * 일반 카테고리 ebook/만화 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @param header
	 *            공통헤더
	 * @return 일반 카테고리 ebook/만화 상품 리스트
	 */
	@Override
	public CategoryEbookComicSacRes searchEbookComicList(CategoryEbookComicSacReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchEbookComicList Service started!!");
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

		CategoryEbookComicSacRes caegoryEbookComicRes = new CategoryEbookComicSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		// 파라미터 유효값 체크
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

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset);

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count);

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}

		// 일반 카테고리 이북/코믹 상품 조회
		List<ProductBasicInfo> ebookComicList = this.commonDAO.queryForList("Category.selectCategoryEbookComicList",
				req, ProductBasicInfo.class);

		if (!ebookComicList.isEmpty()) {
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
			for (ProductBasicInfo productBasicInfo : ebookComicList) {
				reqMap.put("productBasicInfo", productBasicInfo);
				reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
				MetaInfo retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);

				if (retMetaInfo != null) {
					// Tstore멤버십 적립율 정보
					retMetaInfo.setMileageInfo(memberBenefitService.getMileageInfo(header.getTenantHeader().getTenantId(), retMetaInfo.getTopMenuId(), retMetaInfo.getProdId(), retMetaInfo.getProdAmt()));
                	
					if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
						Product product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
						productList.add(product);
					} else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
						Product product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
						productList.add(product);
					}
				}
			}
			commonResponse.setTotalCount(ebookComicList.get(0).getTotalCount());
			caegoryEbookComicRes.setProductList(productList);
			caegoryEbookComicRes.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			caegoryEbookComicRes.setProductList(productList);
			caegoryEbookComicRes.setCommonResponse(commonResponse);
		}

		return caegoryEbookComicRes;
	}
}
