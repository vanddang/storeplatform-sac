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
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.cache.service.CategorySpecificProductInfoManager;
import com.skplanet.storeplatform.sac.display.meta.service.ProductSubInfoManager;
import com.skplanet.storeplatform.sac.display.meta.vo.CidPrice;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
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
 * 특정 상품 ebook/코믹 조회 Service 구현체
 * 
 * Created on : 2014. 2. 3. Created by : 이승훈, 엔텔스.
 * Updated on : 2014. 2. 3. Updated by : 서대영, SK플래닛. : 메타에 캐시 적용
 */
@Service
public class CategorySpecificEbookServiceImpl implements CategorySpecificEbookService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private DisplayCommonService displayCommonService;

    @Autowired
    private ProductSubInfoManager productSubInfoManager;

	@Autowired
	private CategorySpecificProductInfoManager metaInfoService;

	@Autowired
    private MemberBenefitService memberBenefitService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.category.service.CategorySpecificEbookService#getSpecificEbookList
	 * (com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public CategorySpecificSacRes getSpecificEbookList(CategorySpecificSacReq req, SacRequestHeader header) {
        String tenantId = header.getTenantHeader().getTenantId();
        CategorySpecificSacRes res = new CategorySpecificSacRes();
        CommonResponse commonResponse = new CommonResponse();
        Product product = null;
        MetaInfo metaInfo = null;
        List<Product> productList = new ArrayList<Product>();

        List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
        if (prodIdList.size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_EBOOK_PARAMETER_LIMIT) {
            throw new StorePlatformException("SAC_DSP_0004", "list",
                    DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_EBOOK_PARAMETER_LIMIT);
        }

        // 상품 기본 정보 List 조회
        List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
                "CategorySpecificProduct.selectProductInfoList", prodIdList, ProductBasicInfo.class);

        if (productBasicInfoList != null) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("tenantHeader", header.getTenantHeader());
            paramMap.put("deviceHeader", header.getDeviceHeader());
            paramMap.put("lang", "ko");

            for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
                String topMenuId = productBasicInfo.getTopMenuId();
                String svcGrpCd = productBasicInfo.getSvcGrpCd();
                paramMap.put("productBasicInfo", productBasicInfo);

                this.log.debug("##### Top Menu Id : {}", topMenuId);
                this.log.debug("##### Service Group Cd : {}", svcGrpCd);

                // 상품 SVC_GRP_CD 조회
                // DP000203 : 멀티미디어
                // DP000206 : Tstore 쇼핑
                // DP000205 : 소셜쇼핑
                // DP000204 : 폰꾸미기
                // DP000201 : 애플리캐이션

                // ebook/코믹 상품의 경우
                if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
                    if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
                            || DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) {
                        // Ebook / Comic 상품의 경우

                        paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
                        metaInfo = this.metaInfoService.getEbookComicMeta(paramMap);
                        if(metaInfo == null)
                            continue;

                        CidPrice cidPrice = productSubInfoManager.getCidPrice(tenantId, metaInfo.getCid());
                        if (cidPrice != null) {
                            metaInfo.setUnlmtAmt(cidPrice.getProdAmt());
                            metaInfo.setPeriodAmt(cidPrice.getRentProdAmt());
                        }
                        
                        /*
                        if(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(productBasicInfo.getContentsTypeCd())) {
	                        //[CPS] 이북/코믹 메타 추가
	                        //단행/연재/잡지 전체 건수/최종 회차
	                        MetaInfo ebookComicEpisodeCount = this.metaInfoService.getEbookComicEpisodeCount(paramMap);
	                        metaInfo.setBookCount(ebookComicEpisodeCount.getBookCount());
	                        metaInfo.setSerialCount(ebookComicEpisodeCount.getSerialCount());
	                        metaInfo.setMagazineCount(ebookComicEpisodeCount.getMagazineCount());
	                        metaInfo.setBookLastChapter(ebookComicEpisodeCount.getBookLastChapter());
	                        metaInfo.setSerialLastChapter(ebookComicEpisodeCount.getSerialLastChapter());
	                        metaInfo.setMagazineLastChapter(ebookComicEpisodeCount.getMagazineLastChapter());
	                        //try { BeanUtils.copyProperties(ebookComicEpisodeCount, metaInfo); } catch (Exception e) { }
                        }
                        */

                        // Tstore멤버십 적립율 정보
                        metaInfo.setMileageInfo(memberBenefitService.getMileageInfo(header.getTenantHeader().getTenantId(), metaInfo.getTopMenuId(), metaInfo.getProdId(), metaInfo.getProdAmt()));

                        if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
                            product = this.responseInfoGenerateFacade.generateSpecificEbookProduct(metaInfo);
                        } else {
                            product = this.responseInfoGenerateFacade.generateSpecificComicProduct(metaInfo);
                        }

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
