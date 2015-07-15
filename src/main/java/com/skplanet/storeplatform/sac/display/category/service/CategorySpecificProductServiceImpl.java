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
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.GetProductBaseInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
import com.skplanet.storeplatform.sac.display.category.vo.SearchProductListParam;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.service.ProductSubInfoManager;
import com.skplanet.storeplatform.sac.display.meta.vo.CidPrice;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

import static com.skplanet.storeplatform.sac.display.common.ProductType.*;
import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.*;

/**
 * <p>
 * CategorySpecificProductServiceImpl
 * </p>
 * Updated on : 2015. 07. 01 Updated by : 정희원, SK 플래닛.
 */
@Service
public class CategorySpecificProductServiceImpl implements CategorySpecificProductService {

    @Autowired
    private CachedExtraInfoManager extraInfoManager;

    @Autowired
    private DisplayCommonService displayCommonService;

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private MemberBenefitService memberBenefitService;

    @Autowired
    private ProductSubInfoManager subInfoManager;

    @Autowired
    private ResponseInfoGenerateFacade responseGen;

    @Autowired
    private CategorySpecificMusicService specificMusicService;

    @Override
    public CategorySpecificSacRes searchProductList(SearchProductListParam param) {

        ArrayList<Product> prodList = new ArrayList<Product>();

        // 파라메터 작성. 특정 상품 조회는 판매상태를 참조하지 않음
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("deviceModelCd", param.getDeviceModelCd());  // App만 쓰임

        SpecificProductServiceContext ctx = new SpecificProductServiceContext(param.getDeviceModelCd());

        for (String prodId : param.getProdIdList()) {

            ProductBaseInfo baseInfo = extraInfoManager.getProductBaseInfo(new GetProductBaseInfoParam(prodId));

            if(baseInfo == null)
                continue;

            MetaInfo metaInfo;

            reqMap.put("svcGrpCd", baseInfo.getSvcGrpCd());
            reqMap.put("prodId", prodId);
            reqMap.put("contentsTypeCd", baseInfo.getContentsTypeCd());
            reqMap.put("topMenuId", baseInfo.getTopMenuId());

            ProductType type = baseInfo.getProductType();

            switch (type) {
                case App:
                    reqMap.put("imageCd", DP_APP_REPRESENT_IMAGE_CD);
                    metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getAppMetaInfo", reqMap, MetaInfo.class);
                    break;
                case InApp:
                    reqMap.put("imageCd", DP_APP_REPRESENT_IMAGE_CD);
                    metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getInAppMetaInfo", reqMap, MetaInfo.class);
                    break;
                case Vod: case VodTv: case VodMovie:
                    reqMap.put("imageCd", DP_VOD_REPRESENT_IMAGE_CD);
                    metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getVODMetaInfo", reqMap, MetaInfo.class);
                    break;
                case EbookComic: case Ebook: case Comic:
                    reqMap.put("imageCd", DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
                    metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getEbookComicMetaInfo", reqMap, MetaInfo.class);
                    break;
                case Webtoon:
                    reqMap.put("imageCd", DP_WEBTOON_REPRESENT_IMAGE_CD);
                    metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getWebtoonMetaInfo", reqMap, MetaInfo.class);
                    break;
                case Music:
                    reqMap.put("imageCd", DP_MUSIC_REPRESENT_IMAGE_CD);
                    metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getMusicMetaInfo", reqMap, MetaInfo.class);
                    break;
                case Shopping:
                    if(!ctx.isShoppingAvailable())
                        continue;

                    reqMap.put("imageCd", DP_SHOPPING_REPRESENT_IMAGE_CD);
                    reqMap.put("catalogId", baseInfo.getCatId());
                    reqMap.put("sellingOnly", false);
                    reqMap.put("filterApplyDt", true);

                    // 특가상품으로 요청한 경우 처리
                    if(baseInfo.getContentsTypeCd().equals(DP_EPISODE_CONTENT_TYPE_CD))
                        reqMap.put("specialProdId", prodId);

                    metaInfo = commonDAO.queryForObject("Shopping.searchSpecificShoppingDetail", reqMap, MetaInfo.class);
                    break;
                default:
                    throw new StorePlatformException("SAC_DSP_0032", prodId);
            }

            if(metaInfo == null)
                continue;

            // 멀티미디어 상품의 가격 처리
            if(type == Vod || type == VodTv || type == VodMovie|| type == EbookComic || type == Ebook || type == Comic) {
                CidPrice cidPrice = subInfoManager.getCidPrice(param.getLangCd(), param.getTenantId(), metaInfo.getEpsdCid());
                if (cidPrice != null) {
                    metaInfo.setUnlmtAmt(cidPrice.getProdAmt());
                    metaInfo.setPeriodAmt(cidPrice.getRentProdAmt());
                }
            }

            // 마일리지 조회시 항상 channel 기준이어야 함
            metaInfo.setMileageInfo(memberBenefitService.getMileageInfo(param.getTenantId(), baseInfo.getTopMenuId(), baseInfo.getChnlId(), null));

            switch(type) {
                case App:
                case InApp:
                    prodList.add(responseGen.generateSpecificAppProduct(metaInfo)); break;
                case VodMovie:
                    prodList.add(responseGen.generateSpecificMovieProduct(metaInfo)); break;
                case VodTv:
                    prodList.add(responseGen.generateSpecificBroadcastProduct(metaInfo)); break;
                case Ebook:
                    prodList.add(responseGen.generateSpecificEbookProduct(metaInfo)); break;
                case Comic:
                    prodList.add(responseGen.generateSpecificComicProduct(metaInfo)); break;
                case Webtoon:
                    prodList.add(responseGen.generateSpecificWebtoonProduct(metaInfo)); break;
                case Music:
                    Product product = responseGen.generateSpecificMusicProduct(metaInfo);
                    specificMusicService.mapgRingbell(param.getTenantId(), baseInfo.getChnlId(), product.getMusic());
                    prodList.add(product);
                    break;
                case Shopping:
                    Product shopProd = responseGen.generateSpecificShoppingProduct(metaInfo);
                    // 특가 상품으로 조회시 처리
                    if(baseInfo.getContentsTypeCd().equals(DP_EPISODE_CONTENT_TYPE_CD)) {
                        shopProd.setSpecialCouponId(metaInfo.getSpecialCouponId());
                        shopProd.setSpecialProdYn(metaInfo.getSpecialSale());
                        shopProd.setSpecialTypeCd(metaInfo.getSpecialTypeCd());
                    }
                    prodList.add(shopProd);
                    break;
                default:
                    throw new RuntimeException("아직 구현되지 않았습니다.");
            }
        }

        return new CategorySpecificSacRes(new CommonResponse(prodList.size()), prodList);
    }

    private class SpecificProductServiceContext {

        private Boolean shoppingAvailable = null;
        private String deviceModelCd;

        public SpecificProductServiceContext(String deviceModelCd) {
            this.deviceModelCd = deviceModelCd;
        }

        public Boolean isShoppingAvailable() {
            if(shoppingAvailable == null) {
                SupportDevice supportDevice = displayCommonService.getSupportDeviceInfo(deviceModelCd);
                if(supportDevice == null)
                    shoppingAvailable = false;
                else
                    shoppingAvailable = "Y".equals(supportDevice.getSclShpgSprtYn());
            }

            return shoppingAvailable;
        }
    }
}
