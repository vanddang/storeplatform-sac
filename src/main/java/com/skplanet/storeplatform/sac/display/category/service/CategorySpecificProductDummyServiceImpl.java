package com.skplanet.storeplatform.sac.display.category.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;

@Service
@Transactional
public class CategorySpecificProductDummyServiceImpl implements CategorySpecificProductDummyService {

	@Override
	public CategorySpecificRes getSpecificProductList(CategorySpecificReq req) {

		CategorySpecificRes res = new CategorySpecificRes();
		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();
		List<Support> supportList = new ArrayList<Support>();
		for (int i = 1; i <= 1; i++) {
			Product product = new Product();
			Identifier identifier = new Identifier();
			App app = new App();
			Accrual accrual = new Accrual();
			Rights rights = new Rights();
			Source source = new Source();
			Price price = new Price();
			Title title = new Title();
			Support support = new Support();

			// 상품ID
			identifier = new Identifier();
			identifier.setType("episodeId");
			identifier.setText("0000643818");

			support.setType("iab");
			support.setText("PD012301");
			supportList.add(support);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			Menu menu = new Menu();
			menu.setId("DP000501");
			menu.setName("게임");
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("DP01004");
			menu.setName("RPG");
			menuList.add(menu);

			/*
			 * App aid, packagename, versioncode, version
			 */
			app.setAid("OA00643818");
			app.setPackageName("proj.syjt.tstore");
			app.setVersionCode("11000");
			app.setVersion("1.1");

			/*
			 * Accrual voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
			 */
			accrual.setVoterCount("14305");
			accrual.setDownloadCount("513434");
			accrual.setScore(4.8);

			/*
			 * Rights grade
			 */
			rights.setGrade("0");

			title.setText("워밸리 온라인");

			/*
			 * source mediaType, size, type, url
			 */
			source.setType("thumbnail");
			source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
			sourceList.add(source);

			/*
			 * Price text
			 */
			price.setText(0);

			product = new Product();
			product.setIdentifier(identifier);
			// product.setSupport("y|iab");
			product.setSupportList(supportList);
			product.setMenuList(menuList);
			product.setApp(app);
			product.setAccrual(accrual);
			product.setRights(rights);
			product.setTitle(title);
			product.setSourceList(sourceList);
			product.setProductExplain("★이벤트★세상에 없던 모바일 MMORPG!");
			product.setPrice(price);

			productList.add(product);
		}

		for (int i = 1; i <= 1; i++) {

			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();

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

			// fiteredBy = ebook
			// fiteredBy = comic
			// fiteredBy = ebook+comic

			// contributor.setDirector("곽경택");
			// contributor.setArtist("유오성,주진모,김우빈,박아인,강한나,한수아");
			// Date date = new Date();
			// date.setText("20131114");
			// contributor.setDate(date);
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
			// product.setContributor(contributor);
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

		for (int i = 1; i <= 1; i++) {

			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();

			Product product = null;
			Identifier identifier = null;
			Menu menu = null;
			Rights rights = null;
			Title title = null;
			Source source = null;
			Accrual accural = null;
			Price price = null;
			Contributor contributor = null;
			Accrual accrual = null;
			Date date = null;
			SalesOption saleoption = null;

			// 상품 정보 (상품ID)
			product = new Product();
			identifier = new Identifier();
			identifier.setType("catagoryId");
			identifier.setText("1234");

			// 메뉴 정보
			menuList = new ArrayList<Menu>();
			menu = new Menu();
			menu.setType("topClass");
			menu.setId("DP000528");
			menu.setName("쇼핑");
			menuList.add(menu);

			menu = new Menu();
			menu.setType("topClass");
			menu.setId("DP28013");
			menu.setName("버거/치킨/피자");
			menuList.add(menu);

			// 상품 정보 (상품명)
			title = new Title();
			title.setText("커피/음료/아이스크림");

			// 상품 정보 (상품가격)
			price = new Price();
			price.setFixedPrice("17500");
			price.setDiscountRate("10");
			price.setText(15750);

			// 이미지 정보
			sourceList = new ArrayList<Source>();
			source = new Source();
			source.setType("thumbnail");
			source.setUrl("inst_thumbnail_20111216154840.jpg");
			sourceList.add(source);

			// 다운로드 수
			accrual = new Accrual();
			accrual.setDownloadCount("6229");

			// 이용권한 정보
			rights = new Rights();
			date = new Date();
			date.setText("20130820190000/20131231235959");
			rights.setGrade("PD004401");
			rights.setDate(date);

			// contributor
			contributor = new Contributor();
			identifier = new Identifier();
			identifier.setType("brandId");
			identifier.setText("세븐일레븐 바이더웨이");
			contributor.setIdentifier(identifier);

			// saleoption
			saleoption = new SalesOption();

			// 데이터 매핑
			product.setIdentifier(identifier);
			product.setMenuList(menuList);
			product.setTitle(title);
			product.setPrice(price);
			product.setSourceList(sourceList);
			product.setAccrual(accrual);
			product.setRights(rights);
			product.setContributor(contributor);
			productList.add(product);
		}

		res.setProductList(productList);
		return res;
	}

}
