package com.skplanet.storeplatform.sac.display.feature.best.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestContentsReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestContentsRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */
@Service
public class BestContentsServiceImpl implements BestContentsService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.BestContentsService#BestContentsService(com.skplanet
	 * .storeplatform.sac.client.product.vo.BestContentsRequestVO)
	 */
	@Override
	public BestContentsRes searchBestContentsList(BestContentsReq bestContentsRequest) {
		BestContentsRes response = new BestContentsRes();

		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();
		List<Support> supportList = new ArrayList<Support>();

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(10);

		if (!"shopping".equals(bestContentsRequest.getFiteredBy())) {

			for (int i = 1; i <= 1; i++) {
				Product product = new Product();
				Identifier identifier = new Identifier();
				Contributor contributor = new Contributor();
				Accrual accrual = new Accrual();
				Rights rights = new Rights();
				Title title = new Title();
				Source source = new Source();
				Price price = new Price();
				Support support = new Support();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("channelId");
				identifier.setText("H001540562");

				support.setType("hd");
				support.setText("Y");
				supportList.add(support);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				Menu menu = new Menu();
				menu.setId("DP000517");
				menu.setName("영화");
				menu.setType("topClass");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("DP17002");
				menu.setName("액션");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("CT13");
				menu.setName("movie");
				menu.setType("metaClass");
				menuList.add(menu);

				// fiteredBy = ebook
				// fiteredBy = comic
				// fiteredBy = ebook+comic

				if ("movie".equals(bestContentsRequest.getFiteredBy())
						|| "movie+broadcast".equals(bestContentsRequest.getFiteredBy())) {
					contributor.setDirector("곽경택");
					contributor.setArtist("유오성,주진모,김우빈,박아인,강한나,한수아");
					Date date = new Date();
					date.setText("20131114");
					contributor.setDate(date);
				} else if ("broadcast".equals(bestContentsRequest.getFiteredBy())) {
					contributor.setArtist("유재석,지석진,김종국,하하,개리,이광수");
				} else if ("ebook".equals(bestContentsRequest.getFiteredBy())) {
					contributor.setName("정현웅");
					contributor.setPublisher("L&amp;B BOOKS");
					Date date = new Date();
					date.setText("20130322");
					contributor.setDate(date);
				} else if ("comic".equals(bestContentsRequest.getFiteredBy())
						|| "ebook+comic".equals(bestContentsRequest.getFiteredBy())) {
					contributor.setName("황성");
					contributor.setPainter("황성");
					contributor.setPublisher("미스터블루");
				}

				/*
				 * Accrual - voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
				 */
				accrual.setVoterCount("51");
				accrual.setDownloadCount("5932");
				accrual.setScore(3.8);

				/*
				 * Rights - grade
				 */
				rights.setGrade("4");

				title.setText("[20%할인]친구 2");

				/*
				 * source mediaType - url
				 */
				source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PVOD/201401/02/0002057676/3/0003876930/3/RT1_02000024893_1_0921_182x261_130x186.PNG");
				sourceList.add(source);

				/*
				 * Price text
				 */
				price.setText(3200);

				product = new Product();
				product.setIdentifier(identifier);
				product.setSupportList(supportList);
				// product.setSupport("hd");
				product.setMenuList(menuList);
				product.setContributor(contributor);
				product.setAccrual(accrual);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setProductExplain("니 내랑 부산 접수할래? ...");
				product.setPrice(price);

				// BestContentsVO = new BestContentsVO();
				// BestContentsVO.setProduct(product);
				// listVO.add(BestContentsVO);

				productList.add(product);

			}
			// } else {
			//
			// for (int i = 1; i <= 1; i++) {
			// Product product = new Product();
			// Identifier identifier = new Identifier();
			// Contributor contributor = new Contributor();
			// Accrual accrual = new Accrual();
			// Rights rights = new Rights();
			// Title title = new Title();
			// Source source = new Source();
			// Price price = new Price();
			// SalesOption salesOption = new SalesOption();
			//
			// // 상품ID
			// identifier = new Identifier();
			// identifier.setType("product" + i);
			// identifier.setText("H090101222_" + i);
			//
			// /*
			// * Menu(메뉴정보) Id, Name, Type
			// */
			// Menu menu = new Menu();
			// menu.setName("recommand");
			// menu.setType("recommandShopping");
			// menuList.add(menu);
			// menu = new Menu();
			// menu.setId("dummyMenuId1");
			// menu.setName("dummyMenuName1");
			// menu.setType("dummyMenuType1");
			// menuList.add(menu);
			// menu = new Menu();
			// menu.setId("dummyMenuId2");
			// menu.setName("dummyMenuName2");
			// menu.setType("dummyMenuType2");
			// menuList.add(menu);
			//
			// title.setText("뜨레쥬르가 쏜다!");
			//
			// identifier = new Identifier();
			// identifier.setType("BRAND");
			// identifier.setText("B123455");
			// contributor.setIdentifier(identifier);
			// contributor.setBrand("뚜레주르");
			//
			// /*
			// * source mediaType - url, type, size, meidaType
			// */
			// source.setUrl("http://../image.jpg");
			// source.setType("thmubnail"); // 하드코딩
			// source.setSize("128");
			// source.setMediaType("image/jpeg");
			// sourceList.add(source);
			//
			// Date date = new Date();
			// date.setType("duration/usagePeriod");
			// date.setText("20121202T125122+0900/20131202T235959+0900");
			// rights.setDate(date);
			//
			// /*
			// * Accrual - DownloadCount (다운로드 수)
			// */
			// accrual.setDownloadCount("800");
			//
			// /*
			// * Price - fixedPrice(정가), discountRate(할인률), text(가격)
			// */
			// price.setFixedPrice("5000");
			// price.setDiscountRate("50");
			// price.setText(2500);
			//
			// product = new Product();
			// product.setIdentifier(identifier);
			// product.setMenuList(menuList);
			// product.setTitle(title);
			// product.setContributor(contributor);
			// product.setProductExplain("2012년 상반기 슈크림을 시~원하게 쏩니다.");
			// product.setSourceList(sourceList);
			// product.setRights(rights);
			// product.setAccrual(accrual);
			// product.setPrice(price);
			// if ("배송상품 일 경우".equals("배송상품 조회 데이터")) {
			// salesOption.setType("delivery");
			// product.setSalesOption(salesOption);
			// }
			//
			// productList.add(product);
			//
			// }
		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);

		return response;
	}
}
