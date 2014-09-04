/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.brandshop.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.brandshop.BrandshopSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.feature.brandshop.vo.BrandshopInfo;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * Updated on : 2014. 2. 25. Updated by : 이승훈, 엔텔스.
 */

@Service
public class BrandshopServiceImpl implements BrandshopService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private DisplayCommonService displayCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.EbookComicThemeService#EbookComicThemeService(com.skplanet
	 * .storeplatform.sac.client.product.vo.EbookComicThemeRequestVO)
	 */
	@Override
	public BrandshopSacRes searchBrandshop(BrandshopSacReq req, SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		BrandshopSacRes res = new BrandshopSacRes();

        // 필수 파라미터 체크 channelId
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

        // 단말 지원정보 조회
        SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(header.getDeviceHeader()
                .getModel());

        if (supportDevice != null) {
            req.setEbookSprtYn(supportDevice.getEbookSprtYn());
            req.setComicSprtYn(supportDevice.getComicSprtYn());
            req.setMusicSprtYn(supportDevice.getMusicSprtYn());
            req.setVideoDrmSprtYn(supportDevice.getVideoDrmSprtYn());
            req.setSdVideoSprtYn(supportDevice.getSdVideoSprtYn());
        }
        CommonResponse commonResponse = new CommonResponse();
        List<Product> productList = new ArrayList<Product>();
        // 브렌드샵 테마 조회
        List<BrandshopInfo> brandshopList = this.commonDAO.queryForList("Brandshop.selectBrandshop", req,
                BrandshopInfo.class);

        if (!brandshopList.isEmpty()) {

            Product product = null;

            // Identifier 설정
            Identifier identifier = null;
            List<Identifier> identifierList = null;
            Menu menu = null;
            List<Menu> menuList = null;
            Title title = null;

            List<Source> sourceList = null;
            Source source = null;

            BrandshopInfo brandshopInfo = null;
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("tenantHeader", tenantHeader);
            reqMap.put("deviceHeader", deviceHeader);
            reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

            for (int i = 0; i < brandshopList.size(); i++) {
                brandshopInfo = brandshopList.get(i);

                product = new Product(); // 결과물

                // identifier 정보
                identifier = new Identifier();
                identifierList = new ArrayList<Identifier>();

                identifier.setType("brand");
                identifier.setText(brandshopInfo.getBrandId());
                identifierList.add(identifier);
                product.setIdentifierList(identifierList);

                // 메뉴 정보
                menu = new Menu(); // 메뉴
                menuList = new ArrayList<Menu>(); // 메뉴 리스트
                menu.setId(brandshopInfo.getCategoryNo());
                menu.setName(brandshopInfo.getMenuNm());
                menu.setType("topClass");
                menuList.add(menu);
                product.setMenuList(menuList);

                // title 정보
                title = new Title();
                title.setText(brandshopInfo.getBrandShopNm());
                product.setTitle(title);

                // source 정보
                source = new Source();
                sourceList = new ArrayList<Source>();
                source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
                source.setUrl(brandshopInfo.getLogImgPos());
                source.setSize(brandshopInfo.getBnrImgSize());
                source.setMediaType(DisplayCommonUtil.getMimeType(brandshopInfo.getBnrImgNm()));
                sourceList.add(source);
                product.setSourceList(sourceList);

                // 데이터 매핑
                productList.add(i, product);

            }
            commonResponse.setTotalCount(brandshopList.get(0).getTotalCount());
            res.setProductList(productList);
            res.setCommonResponse(commonResponse);
        } else {
            // 조회 결과 없음
            commonResponse.setTotalCount(0);
            res.setProductList(productList);
            res.setCommonResponse(commonResponse);
        }
        return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.EbookComicThemeService#EbookComicThemeService(com.skplanet
	 * .storeplatform.sac.client.product.vo.EbookComicThemeRequestVO)
	 */
	@Override
	public BrandshopListSacRes searchBrandshopList(BrandshopListSacReq req, SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		BrandshopListSacRes res = new BrandshopListSacRes();

        // 필수 파라미터 체크 channelId
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

        if (req.getOrderedBy() == null || StringUtils.isEmpty(req.getOrderedBy())) {
            req.setOrderedBy("DP000701");
        }

        // 단말 지원정보 조회
        SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(header.getDeviceHeader()
                .getModel());

        if (supportDevice != null) {
            req.setEbookSprtYn(supportDevice.getEbookSprtYn());
            req.setComicSprtYn(supportDevice.getComicSprtYn());
            req.setMusicSprtYn(supportDevice.getMusicSprtYn());
            req.setVideoDrmSprtYn(supportDevice.getVideoDrmSprtYn());
            req.setSdVideoSprtYn(supportDevice.getSdVideoSprtYn());
        }
        CommonResponse commonResponse = new CommonResponse();
        List<Product> productList = new ArrayList<Product>();
        // 브렌드샵 테마 조회
        List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("Brandshop.selectBrandshopList",
                req, ProductBasicInfo.class);

        // Meta DB 조회 파라미터 생성
        Map<String, Object> reqMap = new HashMap<String, Object>();

        reqMap.put("req", req);
        reqMap.put("tenantHeader", tenantHeader);
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("lang", tenantHeader.getLangCd());

        // 브렌드샵 테마 조회
        List<BrandshopInfo> brandshopLayout = this.commonDAO.queryForList("Brandshop.selectBrandshopInfo", req,
                BrandshopInfo.class);

        Layout layout = new Layout();
        // layout 설정
        if (!brandshopLayout.isEmpty()) {

            BrandshopInfo brandshopLayoutInfo = null;

            Menu menu = null;
            List<Menu> menuList = null;
            // List<Menu> menuList = null;
            Title title = null;
            brandshopLayoutInfo = brandshopLayout.get(0);
            layout = new Layout();

            title = new Title();
            // title.setText(brandshopLayoutInfo.getBnrNm());
            layout.setTitle(title);

            // 메뉴 정보
            menu = new Menu(); // 메뉴
            menuList = new ArrayList<Menu>(); // 메뉴 리스트
            menu.setId(brandshopLayoutInfo.getCategoryNo());
            menu.setName(brandshopLayoutInfo.getMenuNm());
            menu.setType("topClass");
            menuList.add(menu);
            layout.setMenuList(menuList);

            // title 정보
            title = new Title();
            title.setText(brandshopLayoutInfo.getBrandShopNm());
            layout.setTitle(title);

            // source 정보
            List<Source> sourceList = null;
            Source source = null;
            source = new Source();
            sourceList = new ArrayList<Source>();
            source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
            source.setUrl(brandshopLayoutInfo.getLogImgPos());
            source.setSize(brandshopLayoutInfo.getBnrImgSize());
            source.setMediaType(DisplayCommonUtil.getMimeType(brandshopLayoutInfo.getBnrImgNm()));
            sourceList.add(source);
            layout.setSourceList(sourceList);

        }
        if (!productBasicInfoList.isEmpty()) {

            Product product = null;

            for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

                String topMenuId = productBasicInfo.getTopMenuId();
                String svcGrpCd = productBasicInfo.getSvcGrpCd();

                reqMap.put("productBasicInfo", productBasicInfo);
                reqMap.put("req", req);
                reqMap.put("tenantHeader", tenantHeader);
                reqMap.put("deviceHeader", deviceHeader);
                reqMap.put("lang", tenantHeader.getLangCd());

                product = new Product(); // 결과물

                MetaInfo retMetaInfo = null;

                // APP 상품의 경우
                if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
                    reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
                    retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);
                    if (retMetaInfo != null) {
                        product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
                        productList.add(product);
                    }

                } else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우
                    // 영화/방송 상품의 경우
                    reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
                    if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
                            || DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
                        retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);

                        if (retMetaInfo != null) {
                            if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
                                product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
                            } else {
                                product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
                            }
                            productList.add(product);
                        }
                    } else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
                            || DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의
                        // 경우
                        retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
                        if (retMetaInfo != null) {
                            if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
                                product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
                                productList.add(product);
                            } else {
                                product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
                                productList.add(product);
                            }
                        }

                    } else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우
                        retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap);
                        if (retMetaInfo != null) {
                            // product = this.responseInfoGenerateFacade.generateSpecificMusicProduct(metaInfo);
                            product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
                            productList.add(product);
                        }

                    } else if (DisplayConstants.DP_WEBTOON_TOP_MENU_ID.equals(topMenuId)) { // WEBTOON 상품의 경우
                        retMetaInfo = this.metaInfoService.getWebtoonMetaInfo(reqMap);
                        if (retMetaInfo != null) {
                            product = this.responseInfoGenerateFacade.generateWebtoonProduct(retMetaInfo);
                            productList.add(product);
                        }

                    }
                } else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
                    retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
                    if (retMetaInfo != null) {
                        product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
                        productList.add(product);
                    }
                }
            }
            commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
            res.setLayout(layout);
            res.setProductList(productList);
            res.setCommonResponse(commonResponse);
        } else {
            // 조회 결과 없음
            commonResponse.setTotalCount(0);
            res.setLayout(layout);
            res.setProductList(productList);
            res.setCommonResponse(commonResponse);
        }
        return res;
	}


}
