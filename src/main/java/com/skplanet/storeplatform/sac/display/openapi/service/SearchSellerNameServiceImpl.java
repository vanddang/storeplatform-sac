/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchSellerNameSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchSellerNameSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 21. Updated by : 이석희, 인크로스.
 */
@Service
public class SearchSellerNameServiceImpl implements SearchSellerNameService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private AppInfoGenerator appInfoGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.SearchAppNameService#SearchAppNameService(com.skplanet
	 * .storeplatform.sac.client.product.vo.SearchAppNameSacReqVO)
	 */
	@Override
	public SearchSellerNameSacRes searchSellerNameList(SacRequestHeader requestheader,
			SearchSellerNameSacReq searchSellerNameSacReq) {

		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		searchSellerNameSacReq.setTenantId(tenantHeader.getTenantId());
		searchSellerNameSacReq.setLangCd(tenantHeader.getLangCd());
		searchSellerNameSacReq.setDeviceModelCd(deviceHeader.getModel());
		searchSellerNameSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		SearchSellerNameSacRes response = new SearchSellerNameSacRes();
		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();

		Product product = null;
		String searchKeyword = null;
		String topMenuId = searchSellerNameSacReq.getTopMenuId();
		String orderedBy = searchSellerNameSacReq.getOrderedBy();

		try {
			// 한글 키워드 처리
			// searchKeyword = new String(searchSellerNameSacReq.getSearchKeyword().getBytes("8859_1"), "UTF-8");
			searchKeyword = URLDecoder.decode(searchSellerNameSacReq.getSearchKeyword(), "utf-8");
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0014");
		}
		searchSellerNameSacReq.setSearchType(DisplayConstants.DP_OPENAPI_PRODUCT_SEARCHTYPE_SELLERNM); // 상품명
		searchSellerNameSacReq.setSearchKeyword(searchKeyword); // 검색어

		int offset = 1; // default
		int count = 20; // default

		if (searchSellerNameSacReq.getOffset() != null) {
			offset = searchSellerNameSacReq.getOffset();
		}
		searchSellerNameSacReq.setOffset(offset); // set offset

		if (searchSellerNameSacReq.getCount() != null) {
			count = searchSellerNameSacReq.getCount();
		}
		count = offset + count - 1;
		searchSellerNameSacReq.setCount(count); // set count

		List<MetaInfo> searchProductList = null;
		List<MetaInfo> bestDownloadAppList = null;

		// OpenApi 상품 검색 요청 조회(App) -- 판매자명
		// App전체(DPAPP), 게임(DP01), FUN(DP03), 생활/위치(DP04), 어학/교육 (DP08)
		if (DisplayConstants.DP_APPALL_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId)) {

			if (StringUtils.isNotEmpty(orderedBy)) {
				if (!DisplayConstants.DP_ORDEREDBY_TYPE_RECENT.equals(orderedBy)
						&& !DisplayConstants.DP_ORDEREDBY_TYPE_POPULAR.equals(orderedBy)) {
					throw new StorePlatformException("SAC_DSP_0015", "recent|popular", orderedBy);
				}
			}

			/*
			 * 앱 상품 조회 Parameter Set
			 */
			BestDownloadAppSacReq bestDownloadAppSacReq = new BestDownloadAppSacReq();
			bestDownloadAppSacReq.setTenantId(tenantHeader.getTenantId());
			bestDownloadAppSacReq.setLangCd(tenantHeader.getLangCd());
			bestDownloadAppSacReq.setDeviceModelCd(deviceHeader.getModel());
			bestDownloadAppSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
			bestDownloadAppSacReq.setTopMenuId(searchSellerNameSacReq.getTopMenuId());
			bestDownloadAppSacReq.setSearchType(searchSellerNameSacReq.getSearchType());
			bestDownloadAppSacReq.setSearchKeyword(searchKeyword);
			bestDownloadAppSacReq.setOrderedBy(orderedBy);
			bestDownloadAppSacReq.setOffset(searchSellerNameSacReq.getOffset());
			bestDownloadAppSacReq.setCount(searchSellerNameSacReq.getCount());

			if (DisplayConstants.DP_ORDEREDBY_TYPE_RECENT.equals(orderedBy)
					|| DisplayConstants.DP_ORDEREDBY_TYPE_POPULAR.equals(orderedBy)) {
				bestDownloadAppList = this.commonDAO.queryForList("OpenApi.searchBestDownloadAppListByOrder",
						bestDownloadAppSacReq, MetaInfo.class);
			} else {
				bestDownloadAppList = this.commonDAO.queryForList("OpenApi.searchBestDownloadAppListByListId",
						bestDownloadAppSacReq, MetaInfo.class);
			}

			// 앱 상품 조회 결과 SET
			if (bestDownloadAppList.size() != 0) {

				Iterator<MetaInfo> iterator = bestDownloadAppList.iterator();
				while (iterator.hasNext()) {

					MetaInfo metaInfo = iterator.next();

					product = new Product();

					List<Identifier> identifierList = new ArrayList<Identifier>();
					Identifier identifier = this.commonGenerator.generateIdentifier(
							DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getProdId());
					identifierList.add(identifier);
					product.setIdentifierList(identifierList); // 상품 ID
					product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
					product.setSourceList(this.commonGenerator.generateSourceList(metaInfo)); // 상품 이미지
					product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격
					product.setAccrual(this.commonGenerator.generateAccrual(metaInfo)); // 참여자 정보
					product.setApp(this.appInfoGenerator.generateApp(metaInfo)); // App 상세정보
					product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명
					product.setProductExplain(metaInfo.getProdBaseDesc()); // 상품 설명
					product.setRights(this.commonGenerator.generateRights(metaInfo)); // 상품 이용 등급

					productList.add(product);

					commonResponse.setTotalCount(metaInfo.getTotalCount());
				}

			}

		} else {
			// OpenApi 상품 검색 요청 조회(멀티미디어) -- 판매자명
			// 멀티미디어 상품 조회
			searchProductList = this.commonDAO.queryForList("OpenApi.searchProductList", searchSellerNameSacReq,
					MetaInfo.class);

			// 멀티미디어 상품 조회 결과 SET
			if (searchProductList.size() != 0) {

				Iterator<MetaInfo> iterator = searchProductList.iterator();
				while (iterator.hasNext()) {

					MetaInfo metaInfo = iterator.next();

					product = new Product();

					List<Identifier> identifierList = new ArrayList<Identifier>();
					Identifier identifier = this.commonGenerator.generateIdentifier(
							DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
					identifierList.add(identifier);
					// identifier = new Identifier();
					// identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
					// metaInfo.getPartProdId());
					// identifierList.add(identifier);
					product.setIdentifierList(identifierList); // 상품 ID
					product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
					product.setSourceList(this.commonGenerator.generateSourceList(metaInfo)); // 상품 이미지
					product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격
					product.setAccrual(this.commonGenerator.generateAccrual(metaInfo)); // 참여자 정보
					product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명
					product.setProductExplain(metaInfo.getProdBaseDesc()); // 상품 설명
					product.setRights(this.commonGenerator.generateRights(metaInfo)); // 상품 이용 등급

					productList.add(product);

					commonResponse.setTotalCount(metaInfo.getTotalCount());
				}
			}
		}

		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
