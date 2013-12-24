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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.related.AuthorProductListResponse;
import com.skplanet.storeplatform.sac.client.product.vo.related.RelatedProductRequest;

/**
 * 특정 작가별 Product Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AuthorProductListServiceImpl implements AuthorProductListService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.SimilarProductListService#searchSimilarProductList(
	 * RelatedProductRequest requestVO)
	 */
	@Override
	public AuthorProductListResponse searchAuthorProductList(RelatedProductRequest requestVO)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		int totalCount = 5;

		AuthorProductListResponse responseVO = null;
		CommonResponse commonResponse = null;

		if (true) {

			// Response VO를 만들기위한 생성자
			List<Product> productList = new ArrayList<Product>();
			List<Menu> menuList = new ArrayList<Menu>();
			List<Source> sourceList = new ArrayList<Source>();
			Contributor contributor = new Contributor();

			contributor.setName("김진명");
			contributor.setPublisher("시공사");

			for (int i = 1; i <= totalCount; i++) {
				Product product = new Product();

				Identifier identifier = new Identifier();
				Title title = new Title();
				Price price = new Price();
				Menu menu = new Menu();
				Source source = new Source();
				Accrual accrual = new Accrual();
				Rights rights = new Rights();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product" + i);
				identifier.setText("H90005501_" + i);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				menu.setId("MenuId10" + i);
				menu.setName("dummyMenuName10" + i);
				menu.setType("dummyMenuType10" + i);
				menuList.add(menu);
				menu = new Menu();
				menu.setId("dummyMenuId101" + i);
				menu.setName("dummyMenuName101" + i);
				menu.setType("dummyMenuType101" + i);
				menuList.add(menu);

				/*
				 * TITLE
				 */
				title.setText("특정 작가 상품 리스트");

				price.setText(1000000);

				/*
				 * source mediaType, size, type, url
				 */
				for (int j = 1; j < 4; j++) {
					source.setMediaType("media_" + (i + j));
					source.setSize("1024_" + (i + j));
					source.setType("thumbNail");
					source.setUrl("http://./4_182_261_130x186.PNG");
					sourceList.add(source);
				}

				/*
				 * Accrual changeRank 변동 순위, 하락은 음수로 표현한다.
				 */
				accrual.setVoterCount("100");
				accrual.setDownloadCount("100");
				accrual.setScore(3.3 * i);

				product = new Product();
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setAccrual(accrual);
				product.setTitle(title);
				product.setRights(rights);
				product.setSourceList(sourceList);
				product.setProductExplain("특정작가(김진명) 상품 리스트");

				productList.add(product);

			}

			responseVO = new AuthorProductListResponse();
			commonResponse = new CommonResponse();
			responseVO.setProductList(productList);
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonRes(commonResponse);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
			String json = objectMapper.writeValueAsString(responseVO);

			this.log.debug("test json : {}", json);
			// System.out.println(json);

		}
		return responseVO;
	}

}
