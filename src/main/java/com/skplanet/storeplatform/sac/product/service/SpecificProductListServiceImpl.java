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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SpecificProductList;

/**
 * SpecificProductList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, Incross
 */
@Component
public class SpecificProductListServiceImpl implements SpecificProductListService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * <pre>
	 * 특정 상품 조회 API
	 * </pre>
	 * 
	 * @param list
	 * @param imageSizeCd
	 * @return 특정 상품 List
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Override
	public SpecificProductList getSpecificProductList(String list, String imageSizeCd) throws JsonGenerationException,
			JsonMappingException, IOException {
		String[] prodArr = StringUtils.split(list, "+");

		this.log.debug("prodArr : {}", prodArr);
		this.log.debug("imageSizeCd : {}", imageSizeCd);

		// dummy data 생성
		SpecificProductList specificProductListVO = new SpecificProductList();
		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();

		for (int i = 0; i < 2; i++) {
			Menu menuVO = new Menu();
			Source sourceVO = new Source();
			menuVO.setId("id" + i);
			menuVO.setName("name" + i);
			menuVO.setType("type" + i);
			sourceVO.setMediaType("mediaType" + i);
			sourceVO.setSize("size" + i);
			sourceVO.setType("type" + i);
			sourceVO.setUrl("url" + i);

			menuList.add(menuVO);
			sourceList.add(sourceVO);
		}

		for (int i = 0; i < 5; i++) {
			Product productVO = new Product();
			Identifier identifierVO = new Identifier();
			Rights rightsVO = new Rights();
			Title titleVO = new Title();

			identifierVO.setText("text" + i);
			identifierVO.setType("type" + i);
			rightsVO.setGrade("grade" + i);
			titleVO.setText("text" + i);

			productVO.setIdentifier(identifierVO);
			productVO.setMenuList(menuList);
			productVO.setRights(rightsVO);
			productVO.setTitle(titleVO);
			productVO.setSourceList(sourceList);
			// App 상품
			// TODO. 각각의 조건에 맞게 수정 필요
			if (i == 0) {
				App appVO = new App();
				Price priceVO = new Price();
				Accrual accrualVO = new Accrual();
				appVO.setAid("aid");
				appVO.setPackageName("packageName");
				appVO.setVersionCode("versionCode");
				appVO.setVersion("version");
				priceVO.setText(0);
				productVO.setPrice(priceVO);
				accrualVO.setVoterCount("count" + i);
				accrualVO.setDownloadCount("downloadCount" + i);
				accrualVO.setScore(i);
				productVO.setApp(appVO);
				productVO.setAccrual(accrualVO);
				productVO.setProductExplain("productExplain");

			}
			// 컨텐츠 상품
			else if (i == 1) {
				Price priceVO = new Price();
				Accrual accrualVO = new Accrual();
				priceVO.setText(0);
				productVO.setPrice(priceVO);
				productVO.setProductExplain("productExplain");

				// 영화
				Contributor contributorVO = new Contributor();
				Date dateVO = new Date();
				dateVO.setText("text");
				contributorVO.setDirector("director");
				contributorVO.setArtist("artist");
				contributorVO.setDate(dateVO);
				accrualVO.setVoterCount("count" + i);
				accrualVO.setDownloadCount("downloadCount" + i);
				accrualVO.setScore(i);

				productVO.setPrice(priceVO);
				productVO.setAccrual(accrualVO);
				productVO.setContributor(contributorVO);
				// TV
				// eBook
				// 만화
			}
			// 쇼핑 상품
			else if (i == 2) {
				Price priceVO = new Price();
				Contributor contributorVO = new Contributor();
				Identifier priceIdentifierVO = new Identifier();
				Accrual accrualVO = new Accrual();
				priceIdentifierVO.setText("text");
				priceIdentifierVO.setType("type");
				priceVO.setFixedPrice("fixedPrice");
				priceVO.setDiscountRate("discountRate");
				priceVO.setDiscountPrice("discountPrice");
				priceVO.setText(0);
				contributorVO.setIdentifier(priceIdentifierVO);
				accrualVO.setDownloadCount("downloadCount" + i);
				// 배송 상품일 경우
				SalesOption salesOptionVO = new SalesOption();
				salesOptionVO.setType("type");

				productVO.setPrice(priceVO);
				productVO.setContributor(contributorVO);
				productVO.setIdentifier(priceIdentifierVO);
				productVO.setAccrual(accrualVO);
				productVO.setSalesOption(salesOptionVO);
			}
			// 음원 상품
			else {
				List<Service> serviceList = new ArrayList<Service>();
				Contributor contributorVO = new Contributor();
				Music musicVO = new Music();
				Service serviceInfo = new Service();
				Accrual accrualVO = new Accrual();
				contributorVO.setName("name");
				contributorVO.setAlbum("album");
				serviceInfo.setName("name");
				serviceInfo.setType("type");
				serviceList.add(serviceInfo);
				musicVO.setServiceList(serviceList);
				accrualVO.setChangeRank("changeRank");
				productVO.setContributor(contributorVO);
				productVO.setMusic(musicVO);
				productVO.setAccrual(accrualVO);
			}

			productList.add(productVO);
		}
		specificProductListVO.setProductList(productList);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
		String json = objectMapper.writeValueAsString(specificProductListVO);

		this.log.debug("test json : {}", json);
		System.out.println(json);
		return specificProductListVO;
	}
}
