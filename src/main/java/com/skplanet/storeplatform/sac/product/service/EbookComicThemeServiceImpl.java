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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.theme.EbookComicThemeRequest;
import com.skplanet.storeplatform.sac.client.product.vo.theme.EbookComicThemeResponse;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

@Service
public class EbookComicThemeServiceImpl implements EbookComicThemeService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.EbookComicThemeService#EbookComicThemeService(com.skplanet
	 * .storeplatform.sac.client.product.vo.EbookComicThemeRequestVO)
	 */
	@Override
	public EbookComicThemeResponse searchEbookComicThemeList(EbookComicThemeRequest ebookComicThemeRequest) {
		EbookComicThemeResponse response = new EbookComicThemeResponse();

		List<Product> productList = new ArrayList<Product>();
		List<Source> sourceList = new ArrayList<Source>();

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(10);

		if ("ebook".equals(ebookComicThemeRequest.getFilteredBy())) {
			for (int i = 1; i <= 1; i++) {
				Product product = new Product();
				Identifier identifier = new Identifier();
				Source source = new Source();
				Title title = new Title();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product" + i);
				identifier.setText("H090101222_" + i);

				title.setText("추리, 심리, 미스터리 모음 테마 이북 모음전");

				/*
				 * source mediaType, size, type, url
				 */
				source.setMediaType("image/jpeg");
				source.setSize("128");
				source.setType("thmubnail");
				source.setUrl("http://<<BASE>>/image/bb.jpg");
				sourceList.add(source);

				product = new Product();
				product.setIdentifier(identifier);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setProductExplain("치열한 두뇌싸움을 좋아하는 분들에게 Tstore가 추천");

				productList.add(product);

			}

		} else {
			for (int i = 1; i <= 1; i++) {
				Product product = new Product();
				Identifier identifier = new Identifier();
				Source source = new Source();
				Title title = new Title();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product" + i);
				identifier.setText("H090101222_" + i);

				title.setText("로맨스, 유머 모음 테마 코믹 모음전");

				/*
				 * source mediaType, size, type, url
				 */
				source.setMediaType("image/jpeg");
				source.setSize("128");
				source.setType("thmubnail");
				source.setUrl("http://<<BASE>>/image/bb.jpg");
				sourceList.add(source);

				product = new Product();
				product.setIdentifier(identifier);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setProductExplain("재미와 로맨스를 원하시는 분들에게 Tstore가 추천");

				productList.add(product);

			}
		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
