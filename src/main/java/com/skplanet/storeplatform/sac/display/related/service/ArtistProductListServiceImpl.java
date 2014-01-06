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
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductListRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;

/**
 * 특정 작가별 Product Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class ArtistProductListServiceImpl implements ArtistProductListService {

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
	public RelatedProductListRes searchArtistProductList(RelatedProductReq requestVO)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		int totalCount = 1;

		RelatedProductListRes responseVO = null;
		CommonResponse commonResponse = null;

		if (true) {

			// Response VO를 만들기위한 생성자
			List<Product> productList = new ArrayList<Product>();
			List<Menu> menuList = new ArrayList<Menu>();
			List<Source> sourceList = new ArrayList<Source>();
			List<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service> serviceList = new ArrayList<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service>();

			for (int i = 1; i <= totalCount; i++) {
				Product product = new Product();

				Identifier identifier = new Identifier();
				Title title = new Title();
				Menu menu = new Menu();
				Source source = new Source();
				Accrual accrual = new Accrual();
				Rights rights = new Rights();
				Contributor contributor = new Contributor();

				Music music = new Music();
				com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("H001557945");
				identifier.setText("episode:H001557946");

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				menu.setId("MN05");
				menu.setName("뮤직");
				menu.setType("topClass");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("MN05001");
				menu.setName("뮤직>가요");
				menu.setType("");
				menuList.add(menu);

				/*
				 * TITLE
				 */
				title.setText("금요일에 만나요 (Feat. 장이정 Of HISTORY)");

				/*
				 * source mediaType, size, type, url
				 */
				source.setMediaType("image/png");
				source.setSize("128");
				source.setType("thumbNail");
				source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PMUSIC/201312/29/0002074441/10/0003894669/10/10_0002074441_200_200_1701_200x200_R130x130.PNG");
				sourceList.add(source);

				/*
				 * Accrual changeRank 변동 순위, 하락은 음수로 표현한다.
				 */
				accrual.setChangeRank("1");

				contributor.setName("아이유");
				contributor.setAlbum("Modern Times - Epilogue");

				/*
				 * music
				 */
				service.setName("mp3");
				service.setType("support");
				serviceList.add(service);
				service.setName("bell");
				service.setType("support");
				serviceList.add(service);
				service.setName("ring");
				service.setType("support");
				serviceList.add(service);
				music.setServiceList(serviceList);

				product = new Product();
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setAccrual(accrual);
				product.setTitle(title);
				product.setRights(rights);
				product.setSourceList(sourceList);
				product.setContributor(contributor);
				product.setMusic(music);

				productList.add(product);

			}

			responseVO = new RelatedProductListRes();
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
