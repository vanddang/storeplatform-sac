/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.theme.recommend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.external.client.isf.vo.MultiValueType;
import com.skplanet.storeplatform.external.client.isf.vo.MultiValuesType;
import com.skplanet.storeplatform.external.client.isf.vo.SingleValueType;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.feature.isf.invoker.IsfEcInvokerImpl;
import com.skplanet.storeplatform.sac.display.feature.isf.invoker.vo.IsfEcReq;
import com.skplanet.storeplatform.sac.display.feature.theme.recommend.vo.ThemeRecommend;

/**
 * 
 * 
 * Updated on : 2014. 02. 05. Updated by : 윤주영, GTSOFT.
 */
@Service
public class ThemeRecommendServiceImpl implements ThemeRecommendService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private int totalCount = 0;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private IsfEcInvokerImpl invoker;

	@Override
	public ThemeRecommendSacRes searchThemeRecommendList(ThemeRecommendSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException {

		Map<String, Object> mapReq = new HashMap<String, Object>();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

		String userKey = requestVO.getUserKey();
		String deviceIdType = requestVO.getDeviceIdType();
		String deviceId = requestVO.getDeviceId();

		this.log.debug("----------------------------------------------------------------");
		this.log.debug("[searchThemeRecommendList] userKey : {}", userKey);
		this.log.debug("[searchThemeRecommendList] deviceIdType : {}", deviceIdType);
		this.log.debug("[searchThemeRecommendList] deviceId : {}", deviceId);
		this.log.debug("----------------------------------------------------------------");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(userKey)) {
			throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey);
		}
		if (StringUtils.isEmpty(deviceIdType)) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceIdType", deviceIdType);
		}
		if (StringUtils.isEmpty(deviceId)) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceId", deviceId);
		}
		// 기기ID유형 유효값 체크
		if (!"msisdn".equals(deviceIdType)) {
			throw new StorePlatformException("SAC_DSP_0003", "deviceIdType", deviceIdType);
		}

		ISFRes response = new ISFRes();
		try {
			// ISF 연동
			response = this.invoker.invoke(this.makeRequest(requestVO));
		} catch (StorePlatformException e) {
			throw e;
		}

		try {
			JAXBContext jc = JAXBContext.newInstance(ISFRes.class);
			Marshaller m1 = jc.createMarshaller();
			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m1.marshal(response, System.out);
		} catch (Exception e) {

		}

		List<Map<String, Object>> listProd = new ArrayList<Map<String, Object>>();
		int multiCount = response.getProps().getMultiValues().getCount();
		this.log.debug("multiCount {}", multiCount);
		if (multiCount > 0) {
			Map<String, Object> mapIsf = null;

			MultiValuesType multis = response.getProps().getMultiValues();
			for (MultiValueType multi : multis.getMultiValue()) {
				mapIsf = new HashMap<String, Object>();

				this.log.debug("id:{}", multi.getId());
				this.log.debug("order:{}", multi.getOrder());

				mapIsf.put("id", multi.getId());
				mapIsf.put("order", multi.getOrder() == null ? 0 : multi.getOrder());

				listProd.add(mapIsf);
			}
			mapReq.put("multiValue_id", listProd);
		}

		// 추천 사유
		String reason = "";
		int singleCount = response.getProps().getSingleValues().getCount();
		if (singleCount > 0) {
			for (SingleValueType single : response.getProps().getSingleValues().getSingleValue()) {
				this.log.debug("name:{}", single.getName());
				this.log.debug("value:{}", single.getValue());
				if ("reason".equals(single.getName())) {
					reason = single.getValue();
					break;
				}
			}
		}
		this.log.debug("reason:{}", reason);

		List<ThemeRecommend> listThemeRecommend = new ArrayList<ThemeRecommend>();
		if (StringUtils.equalsIgnoreCase(requestVO.getFilteredBy(), "short")) {

			List<String> imageCodeList = new ArrayList<String>();
			imageCodeList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
			imageCodeList.add(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			imageCodeList.add(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			imageCodeList.add(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
			imageCodeList.add(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
			mapReq.put("imageCdList", imageCodeList);

			listThemeRecommend = this.commonDAO.queryForList("Isf.ThemeRecommend.getRecomendPkgMainList", mapReq,
					ThemeRecommend.class);
		} else if (StringUtils.equalsIgnoreCase(requestVO.getFilteredBy(), "long")) {

			mapReq.put("START_ROW", requestVO.getOffset());
			mapReq.put("END_ROW", requestVO.getOffset() + requestVO.getCount() - 1);

			listThemeRecommend = this.commonDAO.queryForList("Isf.ThemeRecommend.getRecomendPkgList", mapReq,
					ThemeRecommend.class);
		}

		return this.makeThemeRecommendResult(listThemeRecommend, reason, requestVO.getFilteredBy());
	}

	private ThemeRecommendSacRes makeThemeRecommendResult(List<ThemeRecommend> resultList, String reason,
			String filteredBy) {
		ThemeRecommendSacRes response = new ThemeRecommendSacRes();

		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();

		// layout 생성
		Layout layout = null;
		if (StringUtils.isNotEmpty(reason)) {
			layout = new Layout();
			layout.setName(reason);
		}

		for (ThemeRecommend mapper : resultList) {

			if (this.totalCount == 0)
				this.totalCount = mapper.getTotalCount();

			Product packageProduct;
			Product subProduct;
			Identifier identifier;
			Title title;
			Source source;
			Menu menu;

			// Response VO를 만들기위한 생성자
			List<Menu> menuList;
			List<Source> sourceList;
			List<Identifier> identifierList;

			packageProduct = new Product();
			identifier = new Identifier();
			title = new Title();
			source = new Source();

			// Response VO를 만들기위한 생성자
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();

			identifier.setType(DisplayConstants.DP_THEME_IDENTIFIER_CD);
			identifier.setText(mapper.getPkgId());
			identifierList.add(identifier);

			title.setText(mapper.getPkgNm());

			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getPkgImgPos()));
			source.setUrl(mapper.getPkgImgPos());
			sourceList.add(source);

			packageProduct.setTitle(title);
			packageProduct.setIdentifierList(identifierList);
			packageProduct.setSourceList(sourceList);

			if (StringUtils.equalsIgnoreCase("short", filteredBy)) { // 테마 추천 메인 : 테마 추천 4개 노출

				List<Product> subProductList = new ArrayList<Product>();

				subProduct = new Product();

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
				identifierList = new ArrayList<Identifier>();

				title = new Title();
				source = new Source();

				// 상품ID
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
				identifier.setText(mapper.getProdId1());
				identifierList.add(identifier);

				menu = new Menu();
				menu.setId(mapper.getTopMenuId1());
				menu.setName(mapper.getTopMenuNm1());
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menuList.add(menu);
				menu = new Menu();
				menu.setId(mapper.getMenuId1());
				menu.setName(mapper.getMenuNm1());
				menuList.add(menu);

				title.setText(mapper.getProdNm1());

				source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getProdImgPos1()));
				source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
				source.setUrl(mapper.getProdImgPos1());
				sourceList.add(source);

				subProduct.setTitle(title);
				subProduct.setMenuList(menuList);
				subProduct.setSourceList(sourceList);
				subProduct.setIdentifierList(identifierList);

				subProductList.add(subProduct);

				subProduct = new Product();

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
				identifierList = new ArrayList<Identifier>();

				title = new Title();
				source = new Source();

				// 상품ID
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
				identifier.setText(mapper.getProdId2());
				identifierList.add(identifier);

				menu = new Menu();
				menu.setId(mapper.getTopMenuId2());
				menu.setName(mapper.getTopMenuNm2());
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menuList.add(menu);
				menu = new Menu();
				menu.setId(mapper.getMenuId2());
				menu.setName(mapper.getMenuNm2());
				menuList.add(menu);

				title.setText(mapper.getProdNm2());

				source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getProdImgPos2()));
				source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
				source.setUrl(mapper.getProdImgPos2());
				sourceList.add(source);

				subProduct.setTitle(title);
				subProduct.setMenuList(menuList);
				subProduct.setSourceList(sourceList);
				subProduct.setIdentifierList(identifierList);

				subProductList.add(subProduct);

				packageProduct.setSubProductList(subProductList);

			}

			productList.add(packageProduct);

		} // end of while

		if (layout != null)
			response.setLayout(layout);

		commonResponse.setTotalCount(this.totalCount);
		response.setCommonRes(commonResponse);
		response.setProductList(productList);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public ThemeRecommendSacRes searchDummyThemeRecommendList(ThemeRecommendSacReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub

		ThemeRecommendSacRes response = new ThemeRecommendSacRes();
		CommonResponse commonResponse = null;
		List<Product> listVO = new ArrayList<Product>();
		List<Product> subListVO = new ArrayList<Product>();

		Product packageProduct;
		Product subProduct;
		Layout layout;
		Identifier identifier;
		Title title;
		Source source;
		Menu menu;

		// Response VO를 만들기위한 생성자
		List<Menu> menuList;
		List<Source> sourceList;
		List<Identifier> identifierList;

		packageProduct = new Product();
		layout = new Layout();
		identifier = new Identifier();
		title = new Title();
		source = new Source();

		// Response VO를 만들기위한 생성자
		sourceList = new ArrayList<Source>();
		identifierList = new ArrayList<Identifier>();

		// 추천 패키지 추천 사유
		layout.setName("추천사유");

		identifier.setType(DisplayConstants.DP_THEME_IDENTIFIER_CD);
		identifier.setText("1280");

		title.setText("나도 이젠 프로게이머");

		source.setMediaType("image/jpeg");
		source.setSize(128);
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setUrl("http://wap.tstore.co.kr/android6/201312/04/IF1423502835320131125163752/0000648339/img/thumbnail/bb.jpg");
		sourceList.add(source);

		identifierList.add(identifier);
		packageProduct.setTitle(title);
		packageProduct.setIdentifierList(identifierList);
		packageProduct.setSourceList(sourceList);

		// sub product list
		for (int i = 0; i < 2; i++) {
			subProduct = new Product();
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();

			title = new Title();
			source = new Source();

			// 상품ID
			identifier = new Identifier();

			identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			identifier.setText("0000648339");
			identifierList.add(identifier);

			menu = new Menu();
			menu.setId("DP01");
			menu.setName("GAME");
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("DP01004");
			menu.setName("RPG");
			menuList.add(menu);

			title.setText("뮤 더 제네시스 for Kakao " + (i + 1));

			source.setMediaType("image/png");
			source.setSize(0);
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setUrl("http://wap.tstore.co.kr/android6/201312/04/IF1423502835320131125163752/0000648339/img/thumbnail/0000648339_130_130_0_91_20131204195212.PNG");
			sourceList.add(source);

			subProduct.setTitle(title);
			subProduct.setMenuList(menuList);
			subProduct.setSourceList(sourceList);
			subProduct.setIdentifierList(identifierList);

			subListVO.add(subProduct);
		}
		packageProduct.setSubProductList(subListVO);

		listVO.add(packageProduct);

		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(listVO.size());

		response.setCommonRes(commonResponse);
		response.setLayout(layout);
		response.setProductList(listVO);

		return response;
	}

	private IsfEcReq makeRequest(ThemeRecommendSacReq requestVO) {

		IsfEcReq request = new IsfEcReq();

		if ("long".equals(requestVO.getFilteredBy()))
			request.setId("SVC_MAIN_0002");
		else
			request.setId("SVC_MAIN_0004");

		request.setChCode("M");
		request.setMbn(requestVO.getUserKey());
		request.setMdn(requestVO.getDeviceId());
		request.setType(requestVO.getFilteredBy());

		this.log.debug(request.toString());

		return request;
	}
}
