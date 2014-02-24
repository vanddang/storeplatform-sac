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
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 웹툰 회차별 목록 조회.
 * 
 * Updated on : 2014. 2. 6. Updated by : 이승훈, 엔텔스.
 */
@Service
public class CategoryWebtoonSeriesServiceImpl implements CategoryWebtoonSeriesService {

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
	 * com.skplanet.storeplatform.sac.display.category.service.CategorySpecificVodService#CategoryWebtoonSeriesSacRes
	 * (com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSeriesSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public CategoryWebtoonSeriesSacRes getCategoryWebtoonSeriesList(CategoryWebtoonSeriesSacReq req,
			SacRequestHeader header) {

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

		CategoryWebtoonSeriesSacRes res = new CategoryWebtoonSeriesSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Product product = null;

		List<Product> productList = new ArrayList<Product>();

		if (req.getDummy() == null) {

			// 필수 파라미터 체크 channelId
			if (StringUtils.isEmpty(req.getChannelId())) {
				throw new StorePlatformException("SAC_DSP_0002", "channelId", req.getChannelId());
			}
			// 필수 파라미터 체크 menuId
			if (StringUtils.isEmpty(req.getMenuId())) {
				throw new StorePlatformException("SAC_DSP_0002", "menuId", req.getMenuId());
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

			// 웹툰 Top Menu ID.
			req.setTopMenuId(DisplayConstants.DP_WEBTOON_TOP_MENU_ID);

			// 웹툰 회차별 List 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
					"Webtoon.selectWebtoonSeriesList", req, ProductBasicInfo.class);

			if (!productBasicInfoList.isEmpty()) {
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					reqMap.put("imageCd", DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
					MetaInfo retMetaInfo = this.metaInfoService.getWebtoonMetaInfo(reqMap);

					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateWebtoonProduct(retMetaInfo);
						productList.add(product);
					}
				}
				commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
				res.setProductList(productList);
				res.setCommonResponse(commonResponse);
			} else {
				// 조회 결과 없음
				commonResponse.setTotalCount(0);
				res.setProductList(productList);
				res.setCommonResponse(commonResponse);
			}
			return res;
		} else {
			return this.generateDummy();
		}
	}

	/**
	 * <pre>
	 * 더미 데이터 생성.
	 * </pre>
	 * 
	 * @return CategoryWebtoonSeriesSacRes
	 */
	private CategoryWebtoonSeriesSacRes generateDummy() {
		Identifier identifier = null;
		List<Identifier> identifierList;
		Support support = null;
		Menu menu = null;
		Contributor contributor = null;
		Accrual accrual = null;
		Rights rights = null;
		Title title = null;
		Source source = null;
		Price price = null;
		Distributor distributor = null;
		Book book = null;
		Chapter chapter = null;
		Date date = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;
		Product product = null;
		List<Product> productList;
		CommonResponse commonResponse = new CommonResponse();
		CategoryWebtoonSeriesSacRes res = new CategoryWebtoonSeriesSacRes();

		productList = new ArrayList<Product>();
		menuList = new ArrayList<Menu>();
		sourceList = new ArrayList<Source>();
		supportList = new ArrayList<Support>();

		identifier = new Identifier();
		accrual = new Accrual();
		rights = new Rights();
		source = new Source();
		price = new Price();
		title = new Title();
		support = new Support();
		distributor = new Distributor();
		book = new Book();
		chapter = new Chapter();
		contributor = new Contributor();
		date = new Date();

		// Identifier 설정
		identifierList = new ArrayList<Identifier>();
		identifier = new Identifier();
		identifier.setType("episodeId");
		identifier.setText("H090123977");
		identifierList.add(identifier);
		identifier = new Identifier();
		identifier.setType("channel");
		identifier.setText("H090121356");
		identifierList.add(identifier);

		// support (지원구분) 설정
		support = new Support();
		support.setType("play");
		support.setText("N");
		supportList.add(support);
		support = new Support();
		support.setType("store");
		support.setText("Y");
		supportList.add(support);

		// mene 설정
		menu = new Menu();
		menu.setId("DP26");
		menu.setName("웹툰");
		menu.setType("topClass");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("DP26001");
		menu.setName("코믹");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("CT27");
		menu.setType("metaClass");
		menuList.add(menu);

		// accrual 설정
		accrual.setVoterCount(3);
		accrual.setDownloadCount(2);
		accrual.setScore(10.0);

		// rights 설정
		rights.setGrade("PD004401");

		// title 설정
		title.setPrefix("61회");
		title.setText("[QA] 웹툰 등록테스트");

		// source 설정
		source.setMediaType("image/jpeg");
		source.setType("thumbnail");
		source.setSize(659069);
		source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
		sourceList.add(source);

		// price 설정
		price.setText(0);

		// distributor 설정
		distributor.setName("ubivelox");
		distributor.setTel("0211112222");
		distributor.setEmail("signtest@yopmail.com");

		// contributor 설정
		contributor.setName("김기백");
		contributor.setPublisher("코믹플러스");

		// book 설정
		chapter.setUnit("4");
		book.setChapter(chapter);
		book.setSupportList(supportList);

		// date 설정
		date.setType("date/update");
		date.setText("20130820190000");

		product = new Product();
		product.setIdentifierList(identifierList);
		/*
		 * product.setSupportList(supportList); product.setMenuList(menuList); product.setApp(app);
		 * product.setProductExplain("[QA] 웹툰 등록테스트"); product.setPrice(price); product.setDistributor(distributor);
		 */

		product.setContributor(contributor);
		product.setAccrual(accrual);
		product.setTitle(title);
		product.setSourceList(sourceList);
		product.setRights(rights);
		product.setDate(date);

		productList.add(product);

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setProductList(productList);
		return res;
	}
}
