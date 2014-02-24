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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
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

		if (req.getDummy() == null) {

			// 필수 파라미터 체크 channelId
			if (StringUtils.isEmpty(req.getFilteredBy())) {
				throw new StorePlatformException("SAC_DSP_0002", "filteredBy", req.getFilteredBy());
			}
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
			} else {
				throw new StorePlatformException("SAC_DSP_0002", "filteredBy", req.getFilteredBy());
			}

			try {

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
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_0001", "");
			}
		} else {
			return this.generateDummy();
		}
	}

	/**
	 * <pre>
	 * 더미 데이터 생성.
	 * </pre>
	 * 
	 * @return CategorySpecificSacRes
	 */
	private ThemeEpubSacRes generateDummy() {
		Identifier identifier = null;
		List<Identifier> identifierList;
		Title title = null;
		Source source = null;

		List<Source> sourceList = null;
		Product product = null;
		List<Product> productList = new ArrayList<Product>();
		CommonResponse commonResponse = new CommonResponse();
		ThemeEpubSacRes res = new ThemeEpubSacRes();

		sourceList = new ArrayList<Source>();
		source = new Source();
		title = new Title();

		// Identifier 설정
		identifierList = new ArrayList<Identifier>();
		identifier = new Identifier();
		identifier.setType("episodeId");
		identifier.setText("H900063306");
		identifierList.add(identifier);

		// title 설정
		title.setText("추리, 심리, 미스터리 모음 테마 eBook 모음전");

		// source 설정
		source.setMediaType("image/jpeg");
		source.setType("thumbnail");
		source.setSize(659069);
		source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
		sourceList.add(source);

		product = new Product();
		product.setIdentifierList(identifierList);
		product.setTitle(title);
		product.setSourceList(sourceList);
		product.setProductExplain("치열한 두뇌싸움을 좋아하는 분들에게 Tstore가 추천");

		productList.add(product);

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setProductList(productList);
		return res;
	}
}
