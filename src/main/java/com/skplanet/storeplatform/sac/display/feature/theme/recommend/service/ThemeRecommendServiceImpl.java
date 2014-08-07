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

import org.apache.commons.lang.ObjectUtils;
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
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
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

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private IsfEcInvokerImpl invoker;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Override
	public ThemeRecommendSacRes searchThemeRecommendList(ThemeRecommendSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException {

		Map<String, Object> mapReq = new HashMap<String, Object>();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);
		mapReq.put("ver", requestVO.getVer());

		String userKey = requestVO.getUserKey();
		String deviceIdType = requestVO.getDeviceIdType();
		String deviceId = requestVO.getDeviceId();
		String ver = requestVO.getVer();

		this.log.debug("----------------------------------------------------------------");
		this.log.debug("[searchThemeRecommendList] userKey : {}", userKey);
		this.log.debug("[searchThemeRecommendList] deviceIdType : {}", deviceIdType);
		this.log.debug("[searchThemeRecommendList] deviceId : {}", deviceId);
		this.log.debug("[searchThemeRecommendList] ver : {}", ver);
		this.log.debug("----------------------------------------------------------------");

		ISFRes response = new ISFRes();
		try {
			// ISF 연동
			response = this.invoker.invoke(this.makeRequest(requestVO));
		} catch (StorePlatformException e) {
			// throw e;
		}

		// 추천 사유
		String reason = "";
		if (null != response && null != response.getProps()) {
			/*
			 * try { JAXBContext jc = JAXBContext.newInstance(ISFRes.class); Marshaller m1 = jc.createMarshaller();
			 * m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); m1.marshal(response, System.out); } catch
			 * (Exception e) {}
			 */

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
				mapReq.put("multiValueId", listProd);
			}

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
		}

		List<ThemeRecommend> listThemeRecommend = new ArrayList<ThemeRecommend>();
		if (StringUtils.equalsIgnoreCase(requestVO.getFilteredBy(), "short")) {

			// 단말 지원정보 조회
			SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(deviceHeader.getModel());
			if (supportDevice != null) {
				mapReq.put("ebookSprtYn", supportDevice.getEbookSprtYn());
				mapReq.put("comicSprtYn", supportDevice.getComicSprtYn());
				mapReq.put("musicSprtYn", supportDevice.getMusicSprtYn());
				mapReq.put("videoDrmSprtYn", supportDevice.getVideoDrmSprtYn());
				mapReq.put("sdVideoSprtYn", supportDevice.getSdVideoSprtYn());
				mapReq.put("sclShpgSprtYn", supportDevice.getSclShpgSprtYn());

				List<String> imageCodeList = new ArrayList<String>();
				imageCodeList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
				imageCodeList.add(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
				imageCodeList.add(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
				imageCodeList.add(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
				imageCodeList.add(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
				mapReq.put("imageCdList", imageCodeList);

				// 테마추천 v2 (맞춤테마)
				if (StringUtils.equalsIgnoreCase(requestVO.getVer(), "v2")) {
					// 2단, 3단 노출건수 조회
					int themeCnt = this.commonDAO.queryForInt("Isf.ThemeRecommend.getRecommendViewCount", null);

					// ISF 연동 결과가 있는 경우
					if (StringUtils.isNotEmpty(ObjectUtils.toString(mapReq.get("multiValueId")))) {
						mapReq.put("END_ROW", "1"); // 최 상단의 테마는 사용자에 맞는 추천
						listThemeRecommend = this.commonDAO.queryForList("Isf.ThemeRecommend.getRecomendPkgMainList",
								mapReq, ThemeRecommend.class);

						// 최 상단에 노출되는 테마는 2번째, 3번째 조회 시 제외 처리
						if (!listThemeRecommend.isEmpty()) {
							ThemeRecommend themeRecommend = listThemeRecommend.get(0);
							mapReq.put("excludePkgId", themeRecommend.getPkgId());
							mapReq.put("END_ROW", themeCnt - listThemeRecommend.size());
						}

						mapReq.remove("multiValueId");

						// 2번째와 3번째 테마는 DB 우선 노출 항목 노출
						listThemeRecommend.addAll(this.commonDAO.queryForList(
								"Isf.ThemeRecommend.getRecomendPkgMainList", mapReq, ThemeRecommend.class));
						mapReq.put("END_ROW", themeCnt);
					} else {
						mapReq.put("END_ROW", themeCnt);
						listThemeRecommend = this.commonDAO.queryForList("Isf.ThemeRecommend.getRecomendPkgMainList",
								mapReq, ThemeRecommend.class);
					}
				} else { // 기존 테마추천 (4개 고정)
					listThemeRecommend = this.commonDAO.queryForList("Isf.ThemeRecommend.getRecomendPkgMainList",
							mapReq, ThemeRecommend.class);
				}
			}
		} else if (StringUtils.equalsIgnoreCase(requestVO.getFilteredBy(), "long")) {

			int start = 1;
			int end = 20;

			if (requestVO.getOffset() > 0) {
				start = requestVO.getOffset();
			}
			if (requestVO.getCount() > 0 && (requestVO.getOffset() + requestVO.getCount() - 1) >= start) {
				end = requestVO.getOffset() + requestVO.getCount() - 1;
			}
			mapReq.put("START_ROW", start);
			mapReq.put("END_ROW", end);

			listThemeRecommend = this.commonDAO.queryForList("Isf.ThemeRecommend.getRecomendPkgList", mapReq,
					ThemeRecommend.class);
		}

		if (listThemeRecommend.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		return this.makeThemeRecommendResult(listThemeRecommend, reason, requestVO.getFilteredBy(), requestVO.getVer());
	}

	private ThemeRecommendSacRes makeThemeRecommendResult(List<ThemeRecommend> resultList, String reason,
			String filteredBy, String ver) {
		ThemeRecommendSacRes response = new ThemeRecommendSacRes();

		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();

		// layout 생성
		Layout layout = new Layout();
		if (StringUtils.isNotEmpty(reason)) {
			layout.setName(reason);
			response.setLayout(layout);
		}

		commonResponse.setTotalCount(resultList.get(0).getTotalCount());

		for (ThemeRecommend mapper : resultList) {

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

			if (StringUtils.isNotEmpty(ver) && StringUtils.equalsIgnoreCase(ver, "v2")) {
				if (StringUtils.isNotEmpty(mapper.getSetImg1())) {
					source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
					source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getSetImg1()));
					source.setUrl(mapper.getSetImg1());
					source.setExpoOrd("1");
					sourceList.add(source);
				}
				if (StringUtils.isNotEmpty(mapper.getSetImg2())) {
					source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
					source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getSetImg2()));
					source.setUrl(mapper.getSetImg2());
					source.setExpoOrd("2");
					sourceList.add(source);
				}
				if (StringUtils.isNotEmpty(mapper.getSetImg3())) {
					source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
					source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getSetImg3()));
					source.setUrl(mapper.getSetImg3());
					source.setExpoOrd("3");
					sourceList.add(source);
				}
			} else {
				source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
				source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getPkgImgPos()));
				source.setUrl(mapper.getPkgImgPos());
				sourceList.add(source);
			}

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

		response.setCommonRes(commonResponse);
		response.setProductList(productList);

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
