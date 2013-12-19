package com.skplanet.storeplatform.sac.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.best.BestContentsResponseVO;
import com.skplanet.storeplatform.sac.client.product.vo.best.BestContentsVO;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;

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
	public BestContentsResponseVO searchBestContentsList(String listId, String imageSizeCd, String fiteredBy,
			String b2bprod, String hdv, String drm, String prodGradeCd, String offset, String count) {
		BestContentsResponseVO responseVO = null;

		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();

		BestContentsVO BestContentsVO = null;
		List<BestContentsVO> listVO = new ArrayList<BestContentsVO>();
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(10);

		System.out.println("##################################");
		System.out.println("fiteredBy	:	" + fiteredBy);
		System.out.println("##################################");

		if (!"shopping".equals(fiteredBy)) {

			for (int i = 1; i <= 2; i++) {
				Product product = new Product();
				Identifier identifier = new Identifier();
				Contributor contributor = new Contributor();
				Accrual accrual = new Accrual();
				Rights rights = new Rights();
				Title title = new Title();
				Source source = new Source();
				Price price = new Price();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product" + i);
				identifier.setText("H090101222_" + i);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				Menu menu = new Menu();
				menu.setId("dummyMenuId1");
				menu.setName("dummyMenuName1");
				menu.setType("dummyMenuType1");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("dummyMenuId2");
				menu.setName("dummyMenuName2");
				menu.setType("dummyMenuType2");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("CT13");
				menu.setName("movie");
				menu.setType("metaClass");
				menuList.add(menu);

				// fiteredBy = ebook
				// fiteredBy = comic
				// fiteredBy = ebook+comic

				if ("movie".equals(fiteredBy) || "movie+broadcast".equals(fiteredBy)) {
					contributor.setDirector("임상윤_" + i);
					contributor.setArtist("소지섭, 이미연_" + i);
					Date date = new Date();
					date.setText("2012");
					contributor.setDate(date);
				} else if ("broadcast".equals(fiteredBy)) {
					contributor.setArtist("유재석" + i);
				} else if ("ebook".equals(fiteredBy)) {
					contributor.setName("살아 있는 것은 다 행복하라" + i);
					contributor.setPublisher("조화로운삶");
					Date date = new Date();
					date.setText("2013");
					contributor.setDate(date);
				} else if ("comic".equals(fiteredBy) || "ebook+comic".equals(fiteredBy)) {
					contributor.setName("열혈강호" + i);
					contributor.setPainter("양재현");
					contributor.setPublisher("대원씨아이");
				}

				/*
				 * Accrual - voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
				 */
				accrual.setVoterCount("1234");
				accrual.setDownloadCount("800");
				accrual.setScore(3.3);

				/*
				 * Rights - grade
				 */
				rights.setGrade("1");

				title.setText("회사원_" + i);

				/*
				 * source mediaType - url
				 */
				source.setUrl("http://./4_182_261_130x186.PNG");
				sourceList.add(source);

				/*
				 * Price text
				 */
				price.setText(0);

				product = new Product();
				product.setIdentifier(identifier);
				product.setSupport("hd");
				product.setMenuList(menuList);
				product.setContributorList(contributor);
				product.setAccrual(accrual);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setProductExplain("겉으론 평범한 금속 제조 회사지만" + i);
				product.setPrice(price);

				BestContentsVO = new BestContentsVO();
				BestContentsVO.setProduct(product);
				listVO.add(BestContentsVO);

			}
		} else {

			for (int i = 1; i <= 2; i++) {
				Product product = new Product();
				Identifier identifier = new Identifier();
				Contributor contributor = new Contributor();
				Accrual accrual = new Accrual();
				Rights rights = new Rights();
				Title title = new Title();
				Source source = new Source();
				Price price = new Price();
				SalesOption salesOption = new SalesOption();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product" + i);
				identifier.setText("H090101222_" + i);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				Menu menu = new Menu();
				menu.setName("recommand");
				menu.setType("recommandShopping");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("dummyMenuId1");
				menu.setName("dummyMenuName1");
				menu.setType("dummyMenuType1");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("dummyMenuId2");
				menu.setName("dummyMenuName2");
				menu.setType("dummyMenuType2");
				menuList.add(menu);

				title.setText("뜨레쥬르가 쏜다!");

				identifier = new Identifier();
				identifier.setType("BRAND");
				identifier.setText("B123455");
				contributor.setIdentifier(identifier);
				contributor.setBrand("뚜레주르");

				/*
				 * source mediaType - url, type, size, meidaType
				 */
				source.setUrl("http://../image.jpg");
				source.setType("thmubnail"); // 하드코딩
				source.setSize("128");
				source.setMediaType("image/jpeg");
				sourceList.add(source);

				Date date = new Date();
				date.setType("duration/usagePeriod");
				date.setText("20121202T125122+0900/20131202T235959+0900");
				rights.setDate(date);

				/*
				 * Accrual - DownloadCount (다운로드 수)
				 */
				accrual.setDownloadCount("800");

				/*
				 * Price - fixedPrice(정가), discountRate(할인률), text(가격)
				 */
				price.setFixedPrice("5000");
				price.setDiscountRate("50");
				price.setText(2500);

				product = new Product();
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setContributorList(contributor);
				product.setProductExplain("2012년 상반기 슈크림을 시~원하게 쏩니다.");
				product.setSourceList(sourceList);
				product.setRights(rights);
				product.setAccrual(accrual);
				product.setPrice(price);
				if ("배송상품 일 경우".equals("배송상품 조회 데이터")) {
					salesOption.setType("delivery");
					product.setSalesOption(salesOption);
				}

				BestContentsVO = new BestContentsVO();
				BestContentsVO.setProduct(product);
				listVO.add(BestContentsVO);

			}
		}
		responseVO = new BestContentsResponseVO();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setBestContentsList(listVO);

		return responseVO;
	}
}
