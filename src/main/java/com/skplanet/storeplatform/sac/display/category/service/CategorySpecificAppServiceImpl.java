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

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 특정 상품 APP 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 이승훈, 엔텔스.
 */
@Service
public class CategorySpecificAppServiceImpl implements CategorySpecificAppService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;
	
//	@Autowired
//    private MemberBenefitService memberBenefitService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.category.service.CategorySpecificAppService#getSpecificAppList
	 * (com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public CategorySpecificSacRes getSpecificAppList(CategorySpecificSacReq req, SacRequestHeader header) {

		CategorySpecificSacRes res = new CategorySpecificSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Product product = null;
		MetaInfo metaInfo = null;
		List<Product> productList = new ArrayList<Product>();

        List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
        if (prodIdList.size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT) {
            throw new StorePlatformException("SAC_DSP_0004", "list",
                    DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT);
        }

        // 상품 기본 정보 List 조회
        List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
                "CategorySpecificProduct.selectProductInfoList", prodIdList, ProductBasicInfo.class);

        if (productBasicInfoList != null) {

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("tenantId", header.getTenantHeader().getTenantId());
            paramMap.put("langCd", header.getTenantHeader().getLangCd());
            paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
            paramMap.put("svcGrpCd", DisplayConstants.DP_APP_PROD_SVC_GRP_CD);
            paramMap.put("deviceModelCd", Strings.nullToEmpty(req.getIgnoreProvisionYn()).equals("N") ?
                                            header.getDeviceHeader().getModel() : DisplayConstants.DP_ANY_PHONE_4APP);

            for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
                String svcGrpCd = productBasicInfo.getSvcGrpCd();
                paramMap.put("prodId", productBasicInfo.getProdId());

                this.log.debug("##### Service Group Cd : {}", svcGrpCd);

                // 상품 SVC_GRP_CD 조회
                // DP000203 : 멀티미디어
                // DP000206 : Tstore 쇼핑
                // DP000205 : 소셜쇼핑
                // DP000204 : 폰꾸미기
                // DP000201 : 애플리캐이션

                // APP 상품의 경우
                if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
                    if(productBasicInfo.getPartParentClsfCd() == null || productBasicInfo.getPartParentClsfCd().equals("PD012301")) {

                        // metaInfo = this.metaInfoService.getAppMetaInfo(paramMap);
                        metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getAppMetaInfo", paramMap,
                                MetaInfo.class);
                    }
                    else if(productBasicInfo.getPartParentClsfCd().equals("PD012302")) {
                        metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getInAppMetaInfo", paramMap, MetaInfo.class);
                    }

                    if (metaInfo != null) {
                    	
                    	// Tstore멤버십 적립율 정보
//                    	metaInfo.setMileageInfo(memberBenefitService.getMileageInfo(header.getTenantHeader().getTenantId(), metaInfo.getMenuId(), metaInfo.getProdId(), metaInfo.getProdAmt()));
                    	
                        product = this.responseInfoGenerateFacade.generateSpecificAppProduct(metaInfo);
                        productList.add(product);
                    }
                }
            }
        }
        commonResponse.setTotalCount(productList.size());
        res.setCommonResponse(commonResponse);
        res.setProductList(productList);
        return res;
	}
}
