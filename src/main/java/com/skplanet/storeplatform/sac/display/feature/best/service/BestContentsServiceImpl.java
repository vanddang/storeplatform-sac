package com.skplanet.storeplatform.sac.display.feature.best.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
		List<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service> serviceList = new ArrayList<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service>();

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(10);

		if (bestContentsRequest.getDummy() != null) {
			// dummy 호출이 아닐때
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

		} else {
			// dummy data 호출
		}

		response.setCommonResponse(commonResponse);
		response.setProductList(productList);

		return response;
	}
}
