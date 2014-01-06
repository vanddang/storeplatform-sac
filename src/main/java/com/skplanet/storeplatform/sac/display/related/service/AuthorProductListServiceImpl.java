/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.related.service;

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
import com.skplanet.storeplatform.sac.client.display.vo.related.AuthorProductListRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductReq;
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
	public AuthorProductListRes searchAuthorProductList(RelatedProductReq requestVO)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		int totalCount = 1;

		AuthorProductListRes responseVO = null;
		CommonResponse commonResponse = null;

		if (true) {

			// Response VO를 만들기위한 생성자
			List<Product> productList = new ArrayList<Product>();
			List<Menu> menuList = new ArrayList<Menu>();
			List<Source> sourceList = new ArrayList<Source>();
			Contributor contributor = new Contributor();

			contributor.setName("야설록");
			contributor.setPublisher("미스터블루");

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
				identifier.setText("H900066271");

				/*
				 * TITLE
				 */
				title.setText("[야설록] 칠십이파검 1회");

				price.setText(0);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				menu.setId("DP14");
				menu.setName("Comic");
				menu.setType("topCategory");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("DP14001");
				menu.setName("Comic>무협");
				menu.setType("");
				menuList.add(menu);

				/*
				 * source mediaType, size, type, url
				 */
				source.setUrl("/data/SMILE_DATA/PCOMICS/201211/12/0000109537/1/0000119388/1/1009089888_vol_dvc_cont_img_medium_214x214.jpg");
				sourceList.add(source);

				/*
				 * Accrual changeRank 변동 순위, 하락은 음수로 표현한다.
				 */
				accrual.setVoterCount("1");
				accrual.setDownloadCount("3");
				accrual.setScore(4.0);

				rights.setGrade("1");

				product = new Product();
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setAccrual(accrual);
				product.setTitle(title);
				product.setPrice(price);
				product.setRights(rights);
				product.setSourceList(sourceList);
				product.setProductExplain("[1권 무료]\n"
						+ "사천 제일포쾌 은오리 이군악! 당대 최고 비도 곽영! 철검장 후계자의 자리를 놓고,\n"
						+ "사제와 겨뤄야 했던 군악, 의를 위해 현실의 안락을 버리고 무림을 떠나 포쾌를 자처했으나...사제였던 철검장주의 돌연한 죽음에 그는 다시 무림으로 돌아오게 되니...\n"
						+ "군악 앞에 나타나는 기연의 연속들...\n" + "청성파의 감춰진 최고 무학, 칠십이파검의 정수를 깨달으며, 파헤쳐 나가는 진실들...\n"
						+ "은호리 이군악과 그의 벗 곽영,잊지 못할 마음 속의 설지의 젊은 무림 이야기...");

				productList.add(product);

			}

			responseVO = new AuthorProductListRes();
			commonResponse = new CommonResponse();
			responseVO.setProductList(productList);
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonRes(commonResponse);
			responseVO.setContributor(contributor);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
			String json = objectMapper.writeValueAsString(responseVO);

			this.log.debug("test json : {}", json);
			// System.out.println(json);

		}
		return responseVO;
	}

}
