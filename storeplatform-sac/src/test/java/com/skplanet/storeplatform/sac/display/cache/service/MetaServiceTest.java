/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.cache.vo.ShoppingMetaParam;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
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
 * Updated on : 2014. 04. 11 Updated by : 정희원, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class MetaServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(MetaServiceTest.class);

    private static final String DEVICE_MODEL_CD = "SHW-M110S";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;

    @Autowired
    private MetaInfoService metaInfoService;

    @Autowired
    private CacheEvictManager cacheEvictManager;

    @Autowired
    private DisplayCommonService displayCommonService;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void appTest() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
        productBasicInfo.setProdId("0000653363");
        productBasicInfo.setSvcGrpCd(DisplayConstants.DP_APP_PROD_SVC_GRP_CD);


        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getAppMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        cacheEvictManager.evictAllAppMeta();
        MetaInfo meta2 = metaInfoService.getAppMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    @Test
    public void musicTest() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
        productBasicInfo.setProdId("H001164297");
        productBasicInfo.setSvcGrpCd("DP000203");
        productBasicInfo.setContentsTypeCd("PD002501");
        productBasicInfo.setTopMenuId("DP16");
        String dateString = displayCommonService.getBatchStandardDateString("S01", "MELON_DP004901");
        reqMap.put("stdDt", dateString.substring(0, 8));
        reqMap.put("chartClsfCd", "DP004901");

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getMusicMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        cacheEvictManager.evictAllMusicMeta();
        MetaInfo meta2 = metaInfoService.getMusicMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    @Test
    public void freepassTest() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
        reqMap.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
        reqMap.put("ebookThumbnailImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
        productBasicInfo.setProdId("F901000867");
        productBasicInfo.setSvcGrpCd(DisplayConstants.DP_TSTORE_FREEPASS_PROD_SVC_GRP_CD);


        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getFreepassMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        cacheEvictManager.evictAllFreepassMeta();
        MetaInfo meta2 = metaInfoService.getFreepassMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    @Test
    public void vodTestByChannel() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
        productBasicInfo.setProdId("H000043401");
        productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getVODMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        this.cacheEvictManager.evictAllVodMeta();
        MetaInfo meta2 = metaInfoService.getVODMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    @Test
    public void vodTestByEpisode() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
        productBasicInfo.setPartProdId("H000043402");
        productBasicInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getVODMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        this.cacheEvictManager.evictAllVodMeta();
        MetaInfo meta2 = metaInfoService.getVODMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    @Test
    public void ebookComicTestByChannel() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
        productBasicInfo.setProdId("H900036398");
        productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getEbookComicMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        this.cacheEvictManager.evictAllEbookComicMeta();
        MetaInfo meta2 = metaInfoService.getEbookComicMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    @Test
    public void ebookComicTestByEpisode() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
        productBasicInfo.setPartProdId("H001444114");
        productBasicInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);


        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getEbookComicMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        this.cacheEvictManager.evictAllEbookComicMeta();
        MetaInfo meta2 = metaInfoService.getEbookComicMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    @Test
    public void webtoonTestByChannel() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("imageCd", DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
        productBasicInfo.setProdId("H002621946");
        productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);


        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getWebtoonMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        this.cacheEvictManager.evictAllWebtoonMeta();
        MetaInfo meta2 = metaInfoService.getWebtoonMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    @Test
    public void webtoonTestByEpisode() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("imageCd", DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
        productBasicInfo.setPartProdId("H001562778");
        productBasicInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getWebtoonMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        this.cacheEvictManager.evictAllWebtoonMeta();
        MetaInfo meta2 = metaInfoService.getWebtoonMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    @Test
    public void shoppingTest() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        DeviceHeader deviceHeader = new DeviceHeader();
        TenantHeader tenantHeader = new TenantHeader();
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("tenantHeader", tenantHeader);
        deviceHeader.setModel(DEVICE_MODEL_CD);
        tenantHeader.setLangCd("ko");
        tenantHeader.setTenantId("S01");
        reqMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

        reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        reqMap.put("productBasicInfo", productBasicInfo);
//        productBasicInfo.setCatalogId("CL00007545");
        productBasicInfo.setCatalogId("CL00000404");
//        productBasicInfo.setCatalogId("CL00000851");
//        productBasicInfo.setCatalogId("CL00030277");
        productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta1 = metaInfoService.getShoppingMetaInfo(reqMap);
        logger.info("Meta1={}", meta1);

        cacheEvictManager.evictAllShoppingMeta();
//        ShoppingMetaParam param = new ShoppingMetaParam();
//        param.setTenantId(tenantHeader.getTenantId());
//        param.setLangCd(tenantHeader.getLangCd());
//        param.setCatalogId(productBasicInfo.getCatalogId());
//        cacheEvictManager.evictShoppingMeta(param);
        RequestContextHolder.currentRequestAttributes().setAttribute("useCache", true, RequestAttributes.SCOPE_REQUEST);
        MetaInfo meta2 = metaInfoService.getShoppingMetaInfo(reqMap);
        logger.info("Meta2={}", meta2);

        beanDiff(meta1, meta2);
    }

    private void beanDiff(Object obj1, Object obj2) {
        if(obj1 == null ^ obj2 == null)
            throw new RuntimeException("> Object different (oldObj="+(obj1 == null ? "null" : "exist")+", newObj="+(obj2 == null ? "null" : "exist")+")");

        BeanMap map = new BeanMap(obj1);
        PropertyUtilsBean propUtils = new PropertyUtilsBean();
        List<String> errMsg = new ArrayList<String>();

        try
        {
            for (Object propNameObject : map.keySet()) {
                String propertyName = (String) propNameObject;
                Object property1 = propUtils.getProperty(obj1, propertyName);
                Object property2 = propUtils.getProperty(obj2, propertyName);
                if(property1 == null ^ property2 == null) {
                    errMsg.add("> " + propertyName + " is different (oldValue=\"" + property1 + "\", newValue=\"" + property2 + "\")");
                }
                else if (property1 != null && property2 != null) {
                    if(!property1.equals(property2))
                        errMsg.add("> " + propertyName + " is different (oldValue=\"" + property1 + "\", newValue=\"" + property2 + "\")");
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(errMsg.size() > 0) {
            String conMsg = StringUtils.join(errMsg, "\n");
            throw new RuntimeException(conMsg);
        }
    }

}
