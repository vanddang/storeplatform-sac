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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.related.RelatedProductListResponse;
import com.skplanet.storeplatform.sac.client.product.vo.related.RelatedProductRequest;

/**
 * Genre Product Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class GenreProductListServiceImpl implements GenreProductListService {

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
	public RelatedProductListResponse searchGenreProductList(RelatedProductRequest requestVO)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		int totalCount = 1;

		RelatedProductListResponse responseVO = null;
		CommonResponse commonResponse = null;

		if (true) {

			// Response VO를 만들기위한 생성자
			List<Product> productList = new ArrayList<Product>();
			List<Menu> menuList = new ArrayList<Menu>();
			List<Source> sourceList = new ArrayList<Source>();

			for (int i = 1; i <= totalCount; i++) {
				Product product = new Product();

				Identifier identifier = new Identifier();
				Title title = new Title();
				Price price = new Price();
				Menu menu = new Menu();
				Source source = new Source();
				Accrual accrual = new Accrual();
				Rights rights = new Rights();
				App app = new App();

				// 상품ID
				identifier = new Identifier();
				identifier.setText("0000299542");

				/*
				 * TITLE
				 */
				title.setText("[필수어플]스마트주소록");

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				menu.setId("MN000504");
				menu.setName("생활/위치");
				menu.setType("topClass");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("MN04003");
				menu.setName("유틸리티");
				menu.setType("");
				menuList.add(menu);

				price.setText(0);

				// App
				app.setAid("OA00299542");
				app.setPackageName("com.skmnc.smartaddr");
				app.setSize("4092866");
				app.setVersionCode("14");
				app.setVersion("1.8");

				/*
				 * source mediaType, size, type, url
				 */
				source.setMediaType("image/png");
				source.setType("thumbNail");
				source.setUrl("http://wap.tstore.co.kr/android6/201208/14/IF142815912220090812130133/0000299542/img/thumbnail/0000299542_130_130_0_91.PNG");
				sourceList.add(source);

				/*
				 * Accrual changeRank 변동 순위, 하락은 음수로 표현한다.
				 */
				accrual.setDownloadCount("38694");
				accrual.setVoterCount("120");
				accrual.setScore(4.6);

				rights.setGrade("0");

				product = new Product();
				product.setIdentifier(identifier);
				product.setProductExplain("스마트다이얼과 연동되는 스마트주소록 입니다");
				product.setPrice(price);
				product.setApp(app);
				product.setMenuList(menuList);
				product.setAccrual(accrual);
				product.setTitle(title);
				product.setRights(rights);
				product.setSourceList(sourceList);

				productList.add(product);

			}

			responseVO = new RelatedProductListResponse();
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
