/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.theme.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.theme.EbookComicThemeReq;
import com.skplanet.storeplatform.sac.client.display.vo.theme.EbookComicThemeRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */

@Service
public class EbookComicThemeServiceImpl implements EbookComicThemeService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
	public EbookComicThemeRes searchEbookComicThemeList(EbookComicThemeReq ebookComicThemeRequest) {
		EbookComicThemeRes response = new EbookComicThemeRes();

		Layout layout = new Layout();
		List<Banner> bannerList = null;
		List<Identifier> identifierList = null;
		List<Source> sourceList = null;
		Banner banner = null;
		Identifier identifier = null;
		Title title = null;
		Source source = null;

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(10);

		if ("ebook".equals(ebookComicThemeRequest.getFilteredBy())) {
			for (int i = 1; i <= 1; i++) {

				banner = new Banner();
				identifier = new Identifier();
				title = new Title();
				source = new Source();

				identifierList = new ArrayList<Identifier>();
				identifier.setType("channelId");
				identifier.setText("2866");
				identifierList.add(identifier);

				title.setText("최고의 로맨스소설 50프로 할인 이벤트");

				Title titleName = new Title();
				titleName.setName("theme");
				titleName.setText("최고의 로맨스소설 50프로 할인이벤트");

				sourceList = new ArrayList<Source>();
				source.setMediaType("image/png");
				source.setUrl("http://wap.tstore.co.kr/images/tstore30/banner/ebook/ebook_xhdpi_290_191_20140106144054878.png");
				sourceList.add(source);

				bannerList = new ArrayList<Banner>();
				banner.setBase("/category/themeZone");
				banner.setIdentifier(identifierList);
				banner.setSizeType("A");
				banner.setType("themeZone");
				banner.setTitle(title);
				banner.setBannerId("AR00001359");
				banner.setTitleName(titleName);
				banner.setThemeInfo("베스트 로맨스 도서 37종 50프로 할인 이벤트");
				banner.setSourceList(sourceList);
				bannerList.add(banner);

			}
			layout.setBannerList(bannerList);
		} else if ("cartoon".equals(ebookComicThemeRequest.getFilteredBy())) {
			for (int i = 1; i <= 1; i++) {

				banner = new Banner();
				identifier = new Identifier();
				title = new Title();
				source = new Source();

				identifierList = new ArrayList<Identifier>();
				identifier.setType("channelId");
				identifier.setText("2866");
				identifierList.add(identifier);

				title.setText("황성");

				Title titleName = new Title();
				titleName.setName("theme");
				titleName.setText("무협 황성 작가관");

				sourceList = new ArrayList<Source>();
				source.setMediaType("image/png");
				source.setUrl("http://wap.tstore.co.kr/images/tstore30/banner/comic/comic_xhdpi_290_191_20140110143611883.png");
				sourceList.add(source);

				bannerList = new ArrayList<Banner>();
				banner.setBase("/category/themeZone");
				banner.setIdentifier(identifierList);
				banner.setSizeType("A");
				banner.setType("themeZone");
				banner.setTitle(title);
				banner.setBannerId("AR00001386");
				banner.setTitleName(titleName);
				banner.setThemeInfo("반전에 반전을 거듭하는 묘미~황성 작품들만 모아보기");
				banner.setSourceList(sourceList);
				bannerList.add(banner);

			}

			layout.setBannerList(bannerList);
		} else {
			this.log.debug("해당 요청이 없습니다.");
		}
		response.setCommonResponse(commonResponse);
		response.setLayout(layout);
		// response.setProductList(productList);
		return response;
	}
}
