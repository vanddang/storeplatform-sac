/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.category.SpecificProductDTO;
import com.skplanet.storeplatform.sac.client.product.vo.category.SpecificProductRequest;
import com.skplanet.storeplatform.sac.client.product.vo.category.SpecificProductResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.common.util.DisplayCommonUtil;

/**
 * SpecificProductList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, Incross
 */
@Component
public class SpecificProductListServiceImpl implements SpecificProductListService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.SpecificProductListService#getSpecificProductList(com.skplanet
	 * .storeplatform.sac.client.product.vo.category.SpecificProductRequest)
	 */
	@Override
	public SpecificProductResponse getSpecificProductList(SpecificProductRequest request) {

		SpecificProductResponse response = new SpecificProductResponse();
		List<Product> productList = new ArrayList<Product>();
		List<String> prodIdList = Arrays.asList(StringUtils.split(request.getList(), "+"));
		if (prodIdList == null || prodIdList.size() == 0) {
			// TODO osm1021 에러 처리
		}

		List<SpecificProductDTO> svcGrpCdList = this.commonDAO.queryForList("SpecificProduct.selectSvcGrpCd",
				prodIdList, SpecificProductDTO.class);

		for (SpecificProductDTO svcGtpCdDto : svcGrpCdList) {
			String prodId = svcGtpCdDto.getProdId();
			String svcGrpCd = svcGtpCdDto.getSvcGrpCd();

			// TODO osm1021 더미 데이터 꼭 삭제할것
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("deviceModelCd", "SHV-E210S");
			paramMap.put("prodId", prodId);
			paramMap.put("tenantId", "S01");
			paramMap.put("imageCd", "DP000101");

			SpecificProductDTO retDto = null;
			if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
				retDto = this.commonDAO.queryForObject("SpecificProduct.selectApp", paramMap, SpecificProductDTO.class);
			} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) {

			} else if (DisplayConstants.DP_SOCIAL_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
				// 구쇼핑 SAC는 지원하지 않으므로 아무 처리 안함
			} else if (DisplayConstants.DP_PHONE_DECO_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
				// 폰 데코는 현재 사용하지 않기 때문에 SAC에서는 지원하지 않음
			} else if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {

				retDto = this.commonDAO.queryForObject("SpecificProduct.selectApp", paramMap, SpecificProductDTO.class);

				Product product = new Product();
				Identifier identifierVO = new Identifier();
				Rights rightsVO = new Rights();
				Title titleVO = new Title();
				List<Source> sourceList = new ArrayList<Source>();
				Source source = new Source();

				identifierVO.setText(prodId);
				titleVO.setText(retDto.getProdNm());

				App appVO = new App();
				Price priceVO = new Price();
				Accrual accrualVO = new Accrual();
				appVO.setAid(retDto.getAid());
				appVO.setPackageName(retDto.getApkPkgNm());
				appVO.setVersionCode(retDto.getApkVer());
				appVO.setVersion(retDto.getProdVer());

				priceVO.setText(retDto.getProdAmt());
				product.setPrice(priceVO);
				accrualVO.setVoterCount(retDto.getPaticpersCnt());
				accrualVO.setDownloadCount(retDto.getPrchsCnt());
				accrualVO.setScore(retDto.getAvgEvluScore());

				source.setMediaType(DisplayCommonUtil.getMimeType(retDto.getImgFilePath()));
				source.setUrl(retDto.getImgFilePath());
				source.setType("thumbnail");
				sourceList.add(source);

				product.setApp(appVO);
				product.setAccrual(accrualVO);
				product.setProductExplain(retDto.getProdBaseDesc());

				product.setIdentifier(identifierVO);
				// productVO.setMenuList(menuList);
				product.setRights(rightsVO);
				product.setTitle(titleVO);
				product.setSourceList(sourceList);
				productList.add(product);
			}
		}
		response.setProductList(productList);
		// 상품 type 조회
		// DP000203 : 멀티미디어
		// DP000206 : Tstore 쇼핑
		// DP000205 : 소셜쇼핑
		// DP000204 : 폰꾸미기
		// DP000201 : 애플리캐이션

		// APP

		// 컨텐츠
		// ----------------
		// VOD(영화,TV)
		// 음원
		// 코믹/ebook
		// 웹툰
		// ----------------
		// 쇼핑 상품

		// dummy data 생성
		// SpecificProductList specificProductListVO = new SpecificProductList();
		// List<Product> productList = new ArrayList<Product>();
		// List<Menu> menuList = new ArrayList<Menu>();
		// List<Source> sourceList = new ArrayList<Source>();
		//
		// for (int i = 0; i < 2; i++) {
		// Menu menuVO = new Menu();
		// Source sourceVO = new Source();
		// menuVO.setId("id" + i);
		// menuVO.setName("name" + i);
		// menuVO.setType("type" + i);
		// sourceVO.setMediaType("mediaType" + i);
		// sourceVO.setSize("size" + i);
		// sourceVO.setType("type" + i);
		// sourceVO.setUrl("url" + i);
		//
		// menuList.add(menuVO);
		// sourceList.add(sourceVO);
		// }
		//
		// for (int i = 0; i < 5; i++) {
		// Product productVO = new Product();
		// Identifier identifierVO = new Identifier();
		// Rights rightsVO = new Rights();
		// Title titleVO = new Title();
		//
		// identifierVO.setText("text" + i);
		// identifierVO.setType("type" + i);
		// rightsVO.setGrade("grade" + i);
		// titleVO.setText("text" + i);
		//
		// productVO.setIdentifier(identifierVO);
		// productVO.setMenuList(menuList);
		// productVO.setRights(rightsVO);
		// productVO.setTitle(titleVO);
		// productVO.setSourceList(sourceList);
		// // App 상품
		// // TODO. 각각의 조건에 맞게 수정 필요
		// if (i == 0) {
		// App appVO = new App();
		// Price priceVO = new Price();
		// Accrual accrualVO = new Accrual();
		// appVO.setAid("aid");
		// appVO.setPackageName("packageName");
		// appVO.setVersionCode("versionCode");
		// appVO.setVersion("version");
		// priceVO.setText(0);
		// productVO.setPrice(priceVO);
		// accrualVO.setVoterCount("count" + i);
		// accrualVO.setDownloadCount("downloadCount" + i);
		// accrualVO.setScore(i);
		// productVO.setApp(appVO);
		// productVO.setAccrual(accrualVO);
		// productVO.setProductExplain("productExplain");
		//
		// }
		// // 컨텐츠 상품
		// else if (i == 1) {
		// Price priceVO = new Price();
		// Accrual accrualVO = new Accrual();
		// priceVO.setText(0);
		// productVO.setPrice(priceVO);
		// productVO.setProductExplain("productExplain");
		//
		// // 영화
		// Contributor contributorVO = new Contributor();
		// Date dateVO = new Date();
		// dateVO.setText("text");
		// contributorVO.setDirector("director");
		// contributorVO.setArtist("artist");
		// contributorVO.setDate(dateVO);
		// accrualVO.setVoterCount("count" + i);
		// accrualVO.setDownloadCount("downloadCount" + i);
		// accrualVO.setScore(i);
		//
		// productVO.setPrice(priceVO);
		// productVO.setAccrual(accrualVO);
		// productVO.setContributor(contributorVO);
		// // TV
		// // eBook
		// // 만화
		// }
		// // 쇼핑 상품
		// else if (i == 2) {
		// Price priceVO = new Price();
		// Contributor contributorVO = new Contributor();
		// Identifier priceIdentifierVO = new Identifier();
		// Accrual accrualVO = new Accrual();
		// priceIdentifierVO.setText("text");
		// priceIdentifierVO.setType("type");
		// priceVO.setFixedPrice("fixedPrice");
		// priceVO.setDiscountRate("discountRate");
		// priceVO.setDiscountPrice("discountPrice");
		// priceVO.setText(0);
		// contributorVO.setIdentifier(priceIdentifierVO);
		// accrualVO.setDownloadCount("downloadCount" + i);
		// // 배송 상품일 경우
		// SalesOption salesOptionVO = new SalesOption();
		// salesOptionVO.setType("type");
		//
		// productVO.setPrice(priceVO);
		// productVO.setContributor(contributorVO);
		// productVO.setIdentifier(priceIdentifierVO);
		// productVO.setAccrual(accrualVO);
		// productVO.setSalesOption(salesOptionVO);
		// }
		// // 음원 상품
		// else {
		// List<Service> serviceList = new ArrayList<Service>();
		// Contributor contributorVO = new Contributor();
		// Music musicVO = new Music();
		// Service serviceVO = new Service();
		// Accrual accrualVO = new Accrual();
		// contributorVO.setName("name");
		// contributorVO.setAlbum("album");
		// serviceVO.setName("name");
		// serviceVO.setType("type");
		// serviceList.add(serviceVO);
		// musicVO.setServiceList(serviceList);
		// accrualVO.setChangeRank("changeRank");
		// productVO.setContributor(contributorVO);
		// productVO.setMusic(musicVO);
		// productVO.setAccrual(accrualVO);
		// }
		//
		// productList.add(productVO);
		// }
		// specificProductListVO.setProductList(productList);

		// ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		// String json = objectMapper.writeValueAsString(specificProductListVO);
		//
		// this.log.debug("test json : {}", json);
		// System.out.println(json);
		return response;
	}
}
