/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.theme.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeThemeZoneSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.feature.theme.vo.ThemeThemeZoneInfo;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.*;
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
 * Updated on : 2014. 2. 21. Updated by : 이승훈, 엔텔스.
 */

@Service
public class ThemeThemeZoneServiceImpl implements ThemeThemeZoneService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private MusicInfoGenerator musicGenerator;

	@Autowired
	private VodGenerator vodGenerator;

	@Autowired
	private EbookComicGenerator ebookComicGenerator;

	@Autowired
	private ShoppingInfoGenerator shoppingInfoGenerator;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

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
	public ThemeThemeZoneListSacRes searchThemeThemeZoneList(ThemeThemeZoneListSacReq req, SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		ThemeThemeZoneListSacRes res = new ThemeThemeZoneListSacRes();

        // 테마존 ID 에외처리 AR로 시작되면 TAR0로 replace처리함
        // periodStart = periodStart.replace("T", "");
        String themeZoneId = req.getThemeZoneId();
        String themeZoneIdFirst = themeZoneId.substring(0, 2);

        if (themeZoneIdFirst.equals("AR")) {
            themeZoneId = themeZoneId.replace("AR", "TAR0");
            req.setThemeZoneId(themeZoneId);
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

        String prodCharge = req.getProdCharge();
        String b2bProd = req.getB2bProd();

        // 상품의 유료/무료 구분 기본 설정
        if (StringUtils.isEmpty(prodCharge)) {
            req.setProdCharge("A");
        }

        // B2B 상품 구분 기본 설정
        if (StringUtils.isEmpty(b2bProd)) {
            req.setB2bProd("A");
        }

        if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
            String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
            for (int i = 0; i < arrayProdGradeCd.length; i++) {
                if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
                    if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
                            && !"PD004403".equals(arrayProdGradeCd[i])) {
                        throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
                                arrayProdGradeCd[i]);
                    }
                }
            }
        }

        // '+'로 연결 된 상품등급코드를 배열로 전달
        if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
            String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
            req.setArrayProdGradeCd(arrayProdGradeCd);
        }

        CommonResponse commonResponse = new CommonResponse();
        List<Product> productList = new ArrayList<Product>();

        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("tenantHeader", tenantHeader);
        reqMap.put("deviceHeader", deviceHeader);
        reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
        List<ThemeThemeZoneInfo> themeThemeZoneInfoMeta = null;
        // 테마 조회
        themeThemeZoneInfoMeta = this.commonDAO.queryForList("ThemeThemeZone.selectThemeThemeZoneInfo", req,
                ThemeThemeZoneInfo.class);

        // Rights rights = null;
        Layout layout = new Layout();

        ThemeThemeZoneInfo themeThemeZoneInfo = null;
        // layout 설정
        if (!themeThemeZoneInfoMeta.isEmpty()) {

            Menu menu = null;
            List<Menu> menuList = new ArrayList<Menu>();
            Title title = null;
            themeThemeZoneInfo = themeThemeZoneInfoMeta.get(0);
            layout = new Layout();

            title = new Title();
            title.setText(themeThemeZoneInfo.getBnrNm());
            layout.setTitle(title);

            // 메뉴 정보
            menu = new Menu(); // 메뉴
            menu.setId(themeThemeZoneInfo.getBnrMenuId());
            menu.setName(themeThemeZoneInfo.getBnrMenuNm());
            menuList.add(menu);
            layout.setMenuList(menuList);

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
            req.setShpgSprtYn(supportDevice.getSclShpgSprtYn());
        }
        else {
            req.setEbookSprtYn("N");
            req.setComicSprtYn("N");
            req.setMusicSprtYn("N");
            req.setVideoDrmSprtYn("N");
            req.setSdVideoSprtYn("N");
            req.setShpgSprtYn("N");
        }

        // 테마상품 조회
        List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
                "ThemeThemeZone.selectThemeThemeZoneList", req, ProductBasicInfo.class);

        if (!productBasicInfoList.isEmpty()) {

            Product product = null;
            // Identifier 설정
            for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
                String topMenuId = productBasicInfo.getTopMenuId();
                String svcGrpCd = productBasicInfo.getSvcGrpCd();

                reqMap.put("productBasicInfo", productBasicInfo);
                reqMap.put("req", req);
                reqMap.put("tenantHeader", tenantHeader);
                reqMap.put("deviceHeader", deviceHeader);
                reqMap.put("lang", tenantHeader.getLangCd());
                reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

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
                    if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
                            || DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
                        reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
                        retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
                        // 영화용 Contributor 설정
							/*
							 * if (retMetaInfo != null) { Contributor contributor =
							 * this.vodGenerator.generateMovieContributor(retMetaInfo);
							 * product.setContributor(contributor); }
							 */
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
                        reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
                        retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
                        // Ebook용 Contributor 설정
							/*
							 * if (retMetaInfo != null) { Contributor contributor = this.ebookComicGenerator
							 * .generateEbookContributor(retMetaInfo); product.setContributor(contributor); }
							 */
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
                        reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
                        retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap);
                        // Music용 Contributor 설정
							/*
							 * if (retMetaInfo != null) { Contributor contributor =
							 * this.musicGenerator.generateContributor(retMetaInfo);
							 * product.setContributor(contributor); }
							 */
                        if (retMetaInfo != null) {
                            // product = this.responseInfoGenerateFacade.generateSpecificMusicProduct(metaInfo);
                            product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
                            productList.add(product);
                        }
                    } else if (DisplayConstants.DP_WEBTOON_TOP_MENU_ID.equals(topMenuId)) { // WEBTOON 상품의 경우
                        reqMap.put("imageCd", DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
                        retMetaInfo = this.metaInfoService.getWebtoonMetaInfo(reqMap);
                        // Comic용 Contributor 설정
							/*
							 * if (retMetaInfo != null) { Contributor contributor = this.ebookComicGenerator
							 * .generateComicContributor(retMetaInfo); product.setContributor(contributor); }
							 */
                        if (retMetaInfo != null) {
                            product = this.responseInfoGenerateFacade.generateWebtoonProduct(retMetaInfo);
                            productList.add(product);
                        }
                    }
                } else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
                    reqMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
                    retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);// shopping용 Contributor 설정
						/*
						 * if (retMetaInfo != null) { Contributor contributor =
						 * this.shoppingInfoGenerator.generateContributor(retMetaInfo);
						 * product.setContributor(contributor); }
						 */
                    if (retMetaInfo != null) {
                        product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
                        productList.add(product);
                    }
                }
					/*
					 * if (retMetaInfo != null) { // title 정보 title = new Title();
					 * title.setText(retMetaInfo.getProdNm()); product.setTitle(title);
					 *
					 * // right 정보 rights = new Rights(); rights.setGrade(retMetaInfo.getProdGrdCd());
					 * product.setRights(rights);
					 *
					 * // 메뉴 정보 menu = new Menu(); // 메뉴 menuList = new ArrayList<Menu>(); // 메뉴 리스트
					 * menu.setId(retMetaInfo.getMenuId()); menu.setName(retMetaInfo.getMenuNm()); menuList.add(menu);
					 * product.setMenuList(menuList); productList.add(product); }
					 */

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

	@Override
	public ThemeThemeZoneSacRes searchThemeThemeZone(ThemeThemeZoneSacReq req, SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		ThemeThemeZoneSacRes res = new ThemeThemeZoneSacRes();
        // return this.generateDummy1();
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

        CommonResponse commonResponse = new CommonResponse();
        List<Product> productList = new ArrayList<Product>();
        // Device 정보 조회
        // List<ThemeThemeZoneInfo> ThemeThemeZoneDevice = this.commonDAO.queryForList(
        // "ThemeThemeZone.ThemeThemeZoneDevice", req, ThemeThemeZoneInfo.class);

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
        // 테마존 테마 조회
        List<ThemeThemeZoneInfo> ThemeThemeZoneList = this.commonDAO.queryForList(
                "ThemeThemeZone.selectThemeThemeZone", req, ThemeThemeZoneInfo.class);

        if (!ThemeThemeZoneList.isEmpty()) {

            Product product = null;

            // Identifier 설정
            Identifier identifier = null;
            List<Identifier> identifierList = null;
            Title title = null;

            // List<Source> sourceList = null;
            // Source source = null;

            ThemeThemeZoneInfo ThemeThemeZoneInfo = null;
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("tenantHeader", tenantHeader);
            reqMap.put("deviceHeader", deviceHeader);
            reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

            for (int i = 0; i < ThemeThemeZoneList.size(); i++) {
                ThemeThemeZoneInfo = ThemeThemeZoneList.get(i);

                product = new Product(); // 결과물

                // identifier 정보
                identifier = new Identifier();
                identifierList = new ArrayList<Identifier>();

                identifier.setType("theme");
                identifier.setText(ThemeThemeZoneInfo.getListId());
                identifierList.add(identifier);
                product.setIdentifierList(identifierList);

                // title 정보
                title = new Title();
                title.setText(ThemeThemeZoneInfo.getListNm());
                product.setTitle(title);

                // source 정보
					/*
					 * source = new Source(); sourceList = new ArrayList<Source>();
					 * source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
					 * source.setUrl(ThemeThemeZoneInfo.getImgPath()); sourceList.add(source);
					 * product.setSourceList(sourceList);
					 */

                // 데이터 매핑
                productList.add(i, product);

            }
            commonResponse.setTotalCount(ThemeThemeZoneList.get(0).getTotalCount());
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

}
