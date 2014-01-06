/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;

/**
 * 
 * 
 * Updated on : 2013. 12. 19. Updated by : 서영배, GTSOFT.
 */
@org.springframework.stereotype.Service
@Transactional
public class RecommendAdminServiceImpl implements RecommendAdminService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public RecommendAdminRes searchAdminList(RecommendAdminReq requestVO) {
		// TODO Auto-generated method stub
		//공통 응답 변수 선언
		int totalCount = 0;
		RecommendAdminRes responseVO = null;
		CommonResponse commonResponse = null;

		//List<MenuDetailMapperVO> resultList = this.commonDAO.queryForList("Feature.selectTotalRecommendList", requestVO,
		//		MenuDetailMapperVO.class);

		// List<ProductCategoryMapperVO> resultList = this.commonDAO.queryForList("ProductCategory.selectCategoryList",

		//RecommendProductVO featureProductVO = null;
		List<Product> listVO = new ArrayList<Product>();

		for (int i = 0; i < 5; i++) {
			Product product = new Product();
			Identifier identifier = new Identifier();
			Title title = new Title();
			App app = new App();
			Book book = new Book();
			Music music = new Music();
			Vod vod = new Vod();
			VideoInfo videoInfo = new VideoInfo();
			Accrual accrual = new Accrual();
			Rights rights = new Rights();
			Source source = new Source();
			Price price = new Price();
			Support support = new Support();
			
			// Response VO를 만들기위한 생성자
			List<Product> productList = new ArrayList<Product>();
			List<Menu> menuList = new ArrayList<Menu>();
			List<Source> sourceList = new ArrayList<Source>();
			List<Support> supportList = new ArrayList<Support>();

			// 상품ID
			identifier = new Identifier();
			//identifier.setType("product" + i);
			identifier.setText("H900101222" + i);
			
			title = new Title();
			title.setText("Test용 더미 데이터");

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			Menu topMenu = new Menu();
			topMenu.setId("dummyMenuId0");
			topMenu.setName("dummyMenuName0");
			topMenu.setType("dummyMenuType0");
			menuList.add(topMenu);
			Menu menu = new Menu();
			menu.setId("dummyMenuId1");
			menu.setName("dummyMenuName1");
			menu.setType("dummyMenuType1");
			menuList.add(menu);

			if (i == 0){
			/*
			 * App aid, packagename, versioncode, version
			 */
				app.setAid("A090101222_" + i);
				app.setPackageName("app dummy package" + i);
				app.setVersionCode("dummy version Code" + i);
				app.setVersion("1.1");
				product.setApp(app);
			}
			else if (i == 1) {
			/*
			 * App aid, packagename, versioncode, version
			 */
				book.setType("");
				book.setTotalPages("100");
				book.setStatus("");
				support.setText("");
				supportList.add(support);
				book.setSupportList(supportList);
				product.setBook(book);
			}
			else if (i == 2) {
				List<Service> serviceList = new ArrayList();
				Service service = new Service();

				service.setType("support");
				service.setName("mp3");
				serviceList.add(service);
				music.setServiceList(serviceList);
				product.setMusic(music);
			}
			else if (i == 3) {
				videoInfo.setType("hd");
				vod.setVideoInfo(videoInfo);
				product.setVod(vod);
			}

			/*
			 * Accrual voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
			 */
			accrual.setVoterCount("1234");
			accrual.setDownloadCount("800");
			accrual.setScore(3.3);

			/*
			 * Rights grade
			 */
			rights.setGrade("1");

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("media_" + i);
			source.setSize("1024_" + i);
			source.setType("thumbNail");
			source.setUrl("http://www.naver.com");
			sourceList.add(source);

			/*
			 * Price text
			 */
			price.setText(0);

			product = new Product();
			product.setIdentifier(identifier);
			product.setTitle(title);
			support.setText("y|iab");
			supportList.clear();
			supportList.add(support);
			product.setSupportList(supportList);
			product.setMenuList(menuList);

			product.setAccrual(accrual);
			product.setRights(rights);
			product.setProductExplain("베스트 앱_" + i);
			product.setSourceList(sourceList);
			product.setPrice(price);

			//featureProductVO = new RecommendProductVO();
			//featureProductVO.setProduct(product);
			listVO.add(product);

		}
		responseVO = new RecommendAdminRes();
		responseVO.setFeatureProductList(listVO);
		return responseVO;
	}

}
