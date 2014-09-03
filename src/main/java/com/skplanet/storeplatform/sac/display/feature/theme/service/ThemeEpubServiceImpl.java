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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeEpubSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.ThemeEpubSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.feature.theme.vo.ThemeEpubInfo;

/**
 * 
 * 
 * Updated on : 2014. 2. 7. Updated by : 이승훈, 엔텔스.
 */

@Service
public class ThemeEpubServiceImpl implements ThemeEpubService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.EbookComicThemeService#EbookComicThemeService(com.skplanet
	 * .storeplatform.sac.client.product.vo.EbookComicThemeRequestVO)
	 */
	@Override
	public ThemeEpubSacRes searchThemeEpubList(ThemeEpubSacReq req, SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

		ThemeEpubSacRes res = new ThemeEpubSacRes();

        String filteredBy = req.getFilteredBy();
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

        if ("ebook".equals(filteredBy)) {
            req.setBnrMenuId("DP010913"); // DP010913 ebook 테마 배너
        } else if ("comic".equals(filteredBy)) {
            req.setBnrMenuId("DP010914"); // DP010914 코믹 테마 배너
        }

        CommonResponse commonResponse = new CommonResponse();
        List<Product> productList = new ArrayList<Product>();
        // EBOOK/코믹 테마상품 조회
        List<ThemeEpubInfo> themeEpubList = this.commonDAO.queryForList("ThemeEpub.selectThemeEpubList", req,
                ThemeEpubInfo.class);

        if (!themeEpubList.isEmpty()) {

            Product product = null;

            // Identifier 설정
            Identifier identifier = null;
            List<Identifier> identifierList;
            Menu menu = null;
            List<Menu> menuList;
            Title title = null;

            List<Source> sourceList;
            Source source = null;

            ThemeEpubInfo ThemeEpubInfo = null;
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("tenantHeader", tenantHeader);
            reqMap.put("deviceHeader", deviceHeader);
            reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

            for (int i = 0; i < themeEpubList.size(); i++) {
                ThemeEpubInfo = themeEpubList.get(i);

                product = new Product(); // 결과물

                // identifier 정보
                identifier = new Identifier();
                identifierList = new ArrayList<Identifier>();

                identifier.setType("theme");
                identifier.setText(ThemeEpubInfo.getBnrSeq());
                identifierList.add(identifier);
                product.setIdentifierList(identifierList);

                // 메뉴 정보
                menu = new Menu(); // 메뉴
                menuList = new ArrayList<Menu>(); // 메뉴 리스트
                if ("ebook".equals(filteredBy)) {
                    menu.setId("DP000513");
                    menu.setName("이북");
                } else if ("comic".equals(filteredBy)) {
                    menu.setId("DP000514");
                    menu.setName("만화");
                }
                menu.setType("topClass");
                menuList.add(menu);
                product.setMenuList(menuList);

                // title 정보
                title = new Title();
                title.setText(ThemeEpubInfo.getBnrNm());
                product.setTitle(title);

                // productExplain
                product.setProductExplain(ThemeEpubInfo.getBnrDesc());

                // source 정보
                source = new Source();
                sourceList = new ArrayList<Source>();
                source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
                source.setUrl(ThemeEpubInfo.getImgPath());
                sourceList.add(source);
                product.setSourceList(sourceList);

                // 데이터 매핑
                productList.add(i, product);

            }
            commonResponse.setTotalCount(themeEpubList.get(0).getTotalCount());
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
