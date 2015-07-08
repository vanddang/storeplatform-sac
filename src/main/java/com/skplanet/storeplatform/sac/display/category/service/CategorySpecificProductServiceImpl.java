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
import com.skplanet.storeplatform.sac.display.common.EbookComicType;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.VodType;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.ProductTypeInfo;
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
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("deviceModelCd", param.getDeviceModelCd());  // App만 쓰임
        //reqMap.put("prodStatusCd", "PD000403");   // FIXME

        for (String prodId : param.getProdIdList()) {
            ProductBaseInfo baseInfo = extraInfoManager.getProductBaseInfo(new GetProductBaseInfoParam(prodId));

            if(baseInfo == null)
                continue;

            MetaInfo metaInfo = null;

            reqMap.put("svcGrpCd", baseInfo.getSvcGrpCd());
            reqMap.put("prodId", prodId);
            reqMap.put("contentsTypeCd", baseInfo.getContentsTypeCd());
            reqMap.put("topMenuId", baseInfo.getTopMenuId());

            ProductTypeInfo prodTp = displayCommonService.getProductTypeInfo(baseInfo.getSvcGrpCd(), baseInfo.getSvcTpCd(), baseInfo.getMetaClsfCd(), baseInfo.getTopMenuId());
            ProductType type = prodTp.getProductType();

            if(type == App) {
                reqMap.put("imageCd", DP_APP_REPRESENT_IMAGE_CD);
                if (!baseInfo.isIapProduct()) {
                    metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getAppMetaInfo", reqMap, MetaInfo.class);
                }
                else {
                    metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getInAppMetaInfo", reqMap, MetaInfo.class);
                }

            }
            else if(type == Vod) {
                reqMap.put("imageCd", DP_VOD_REPRESENT_IMAGE_CD);
                metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getVODMetaInfo", reqMap, MetaInfo.class);
            }
            else if(type == EbookComic) {
                reqMap.put("imageCd", DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
                metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getEbookComicMetaInfo", reqMap, MetaInfo.class);
            }
            else if(type == Webtoon) {
                reqMap.put("imageCd", DP_WEBTOON_REPRESENT_IMAGE_CD);
                metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getWebtoonMetaInfo", reqMap, MetaInfo.class);
            }
            else if(type == Music) {
                reqMap.put("imageCd", DP_MUSIC_REPRESENT_IMAGE_CD);
                metaInfo = commonDAO.queryForObject("CategorySpecificProduct.getMusicMetaInfo", reqMap, MetaInfo.class);
            }
            else
                throw new StorePlatformException("SAC_DSP_0032", prodId);

            if(metaInfo == null)
                continue;

            // 멀티미디어 상품의 가격 처리
            if(type == Vod || type == EbookComic) {
                CidPrice cidPrice = subInfoManager.getCidPrice(param.getLangCd(), param.getTenantId(), metaInfo.getEpsdCid());
                if (cidPrice != null) {
                    metaInfo.setUnlmtAmt(cidPrice.getProdAmt());
                    metaInfo.setPeriodAmt(cidPrice.getRentProdAmt());
                }
            }

            // 마일리지 조회시 항상 channel 기준이어야 함
            // FIXME 멀티미디어 중 cidPrice 검색 결과로 응답해야 하는 경우 마일리지 정보 반영이 늦게 되어야 함
            metaInfo.setMileageInfo(memberBenefitService.getMileageInfo(param.getTenantId(), baseInfo.getTopMenuId(), baseInfo.getChnlId(), metaInfo.getProdAmt()));

            switch(type) {
                case App:
                    prodList.add(responseGen.generateSpecificAppProduct(metaInfo)); break;
                case Vod:
                    if(prodTp.getVodType() == VodType.Movie)
                        prodList.add(responseGen.generateSpecificMovieProduct(metaInfo));
                    else
                        prodList.add(responseGen.generateSpecificBroadcastProduct(metaInfo));
                    break;
                case EbookComic:
                    if(prodTp.getEbookComicType() == EbookComicType.Ebook)
                        prodList.add(responseGen.generateSpecificEbookProduct(metaInfo));
                    else
                        prodList.add(responseGen.generateSpecificComicProduct(metaInfo));
                    break;
                case Webtoon:
                    prodList.add(responseGen.generateSpecificWebtoonProduct(metaInfo)); break;
                case Music:
                    Product product = responseGen.generateSpecificMusicProduct(metaInfo);
                    specificMusicService.mapgRingbell(param.getTenantId(), baseInfo.getChnlId(), product.getMusic());
                    prodList.add(product);
                    break;
                default:
                    throw new RuntimeException("아직 구현되지 않았습니다.");
            }
        }

        return new CategorySpecificSacRes(new CommonResponse(prodList.size()), prodList);
    }
}
