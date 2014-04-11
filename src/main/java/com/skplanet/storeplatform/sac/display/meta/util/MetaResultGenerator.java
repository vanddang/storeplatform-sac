/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta.util;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.cache.service.ProductInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.MusicMeta;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaFetchParam;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 04. 08 Updated by : 정희원, SK 플래닛.
 */
@Component
public class MetaResultGenerator {
    private static final Logger logger = LoggerFactory.getLogger(MetaResultGenerator.class);

    private static MetaInfoService metaInfoService;
    private static ProductInfoManager productInfoManager;

    @Autowired
    public MetaResultGenerator(MetaInfoService metaInfoService, ProductInfoManager productInfoManager) {
        MetaResultGenerator.metaInfoService = metaInfoService;
        MetaResultGenerator.productInfoManager = productInfoManager;
    }

    public static List<Product> fetch(ProductType prodTp, MetaFetchParam param, List<ProductBasicInfo> prodList, MetaMapper handler) {
        List<Product> productList = new ArrayList<Product>();

        // 파라메터 체크
        if(prodTp == null || param == null || prodList == null || prodList.size() == 0 || handler == null)
            return productList;

        Boolean useCache = (Boolean) RequestContextHolder.currentRequestAttributes().getAttribute("useCache", RequestAttributes.SCOPE_REQUEST);

        if(useCache) {
            if(prodTp == ProductType.App) {
                Map<String, Object> reqMap = new HashMap<String, Object>();
                DeviceHeader deviceHeader = new DeviceHeader();
                TenantHeader tenantHeader = new TenantHeader();
                reqMap.put("deviceHeader", deviceHeader);
                reqMap.put("tenantHeader", tenantHeader);
                deviceHeader.setModel(param.getDeviceModelCd());
                tenantHeader.setLangCd(param.getLangCd());
                tenantHeader.setTenantId(param.getTenantId());

                for (ProductBasicInfo productBasicInfo : prodList) {
                    reqMap.put("productBasicInfo", productBasicInfo);
                    reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
                    MetaInfo metaInfo = metaInfoService.getAppMetaInfo(reqMap);
                    productList.add(handler.processRow(metaInfo));
                }
            }
            else if(prodTp == ProductType.Music) {
                Map<String, Object> reqMap = new HashMap<String, Object>();
                DeviceHeader deviceHeader = new DeviceHeader();
                TenantHeader tenantHeader = new TenantHeader();
                reqMap.put("deviceHeader", deviceHeader);
                reqMap.put("tenantHeader", tenantHeader);
                deviceHeader.setModel(param.getDeviceModelCd());
                tenantHeader.setLangCd(param.getLangCd());
                tenantHeader.setTenantId(param.getTenantId());
                reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

                if(StringUtils.isNotEmpty(param.getChartClsfCd()) && StringUtils.isNotEmpty(param.getStdDt())) {
                    reqMap.put("chartClsfCd", param.getChartClsfCd());
                    reqMap.put("stdDt", param.getStdDt());
                }

                for (ProductBasicInfo productBasicInfo : prodList) {
                    reqMap.put("productBasicInfo", productBasicInfo);
                    MetaInfo metaInfo = metaInfoService.getMusicMetaInfo(reqMap);
                    productList.add(handler.processRow(metaInfo));
                }
            }
        }
        else {
            // 상품 목록 작성
            List<String> prodIdList = new ArrayList<String>();
            for(ProductBasicInfo prodInfo : prodList) {
                prodIdList.add(prodInfo.getProdId());
            }

            List<MetaInfo> metaList;
            if(prodTp == ProductType.App) {
                metaList = metaInfoService.getAppMetaInfoList(prodIdList, param.getLangCd(), param.getTenantId(), param.getDeviceModelCd());
            }
            else if(prodTp == ProductType.Music) {
                List<MusicMeta> musicMetaList;
                metaList = new ArrayList<MetaInfo>();

                if(prodIdList.size() > 1000) {
                    musicMetaList = new ArrayList<MusicMeta>();
                    List<String> partitionList = new ArrayList<String>();
                    for (int i = 1; i <= prodIdList.size(); ++i) {
                        partitionList.add(prodIdList.get(i-1));
                        if (i % 1000 == 0) {
                            musicMetaList.addAll(productInfoManager.getMusicMetaList(param.getLangCd(), param.getTenantId(), param.getChartClsfCd(), param.getStdDt(), partitionList));
                            partitionList.clear();
                        }
                    }
                } else {
                    musicMetaList = productInfoManager.getMusicMetaList(param.getLangCd(), param.getTenantId(), param.getChartClsfCd(), param.getStdDt(), prodIdList);
                }

                Map<String, MusicMeta> musicMetaMap = new HashMap<String, MusicMeta>();
                for (MusicMeta musicMeta : musicMetaList) {
                    musicMetaMap.put(musicMeta.getProdId(), musicMeta);
                }

                for (String prodId : prodIdList) {
                    MusicMeta app = musicMetaMap.get(prodId);
                    if (app == null) {
                        logger.warn("메타데이터를 읽을 수 없습니다 - Music#{}", prodId);
                    } else {
                        MetaInfo me = new MetaInfo();
                        MetaBeanUtils.setProperties(app, me);

                        metaList.add(me);
                    }
                }
            }
            else
                throw new RuntimeException("");

            for(MetaInfo meta : metaList) {
                productList.add(handler.processRow(meta));
            }
        }

        return productList;
    }
}
