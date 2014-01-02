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
 * Similar Product Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class SimilarProductListServiceImpl implements SimilarProductListService {

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
	public RelatedProductListResponse searchSimilarProductList(RelatedProductRequest requestVO)
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
				identifier.setText("0000254239");

				// App
				app.setAid("OA00254239");
				app.setPackageName("net.hwado.paid.PDFGenerator");
				app.setSize("1280765");
				app.setVersionCode("1");
				app.setVersion("1.0");

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

				/*
				 * TITLE
				 */
				title.setText("PDF 생성기");

				price.setText(1000);

				/*
				 * source mediaType, size, type, url
				 */
				source.setMediaType("image/png");
				source.setSize("0");
				source.setType("thumbNail");
				source.setUrl("http://wap.tstore.co.kr/images/IF1423022570420091207142717/0000254239/thumbnail/0000254239_130_130_0_91.PNG");
				sourceList.add(source);

				/*
				 * Accrual changeRank 변동 순위, 하락은 음수로 표현한다.
				 */
				accrual.setDownloadCount("246");
				accrual.setVoterCount("2");
				accrual.setScore(3);

				rights.setGrade("0");

				product = new Product();
				product.setProductExplain("PDF 생성기,PDF Generator,HWADO,hwado,www.hwado.net,HWADOTECH");
				product.setIdentifier(identifier);
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
