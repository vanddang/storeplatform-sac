package com.skplanet.storeplatform.sac.display.feature.best.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.best.vo.BestContents;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */
@Service
@Transactional
public class BestContentsServiceImpl implements BestContentsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.BestContentsService#BestContentsService(com.skplanet
	 * .storeplatform.sac.client.product.vo.bestContentsReqVO)
	 */
	@Override
	public BestContentsRes searchBestContentsList(SacRequestHeader requestheader, BestContentsReq bestContentsReq) {
		TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		bestContentsReq.setTenantId(tanantHeader.getTenantId());
		bestContentsReq.setDeviceModelCd(deviceHeader.getModel());

		BestContentsRes response = new BestContentsRes();
		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();
		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;

		if (bestContentsReq.getListId() == null || "".equals(bestContentsReq.getListId())) {
			this.log.error("필수 파라미터(listId)값이 없음");
			commonResponse.setTotalCount(0);
			response.setCommonResponse(commonResponse);
			response.setProductList(productList);
			return response;

		}
		if (bestContentsReq.getFilteredBy() == null || "".equals(bestContentsReq.getFilteredBy())) {
			bestContentsReq.setFilteredBy("movie+broadcast");
		}

		int count = 0;
		count = bestContentsReq.getOffset() + bestContentsReq.getCount() - 1;
		bestContentsReq.setCount(count);

		String stdDt = this.commonService.getBatchStandardDateString("S01", bestContentsReq.getListId());
		bestContentsReq.setStdDt(stdDt);

		if (bestContentsReq.getDummy() == null) {
			// dummy 호출이 아닐때
			// BEST 컨텐츠 상품 조회
			List<BestContents> contentsList = null;

			this.log.debug("########################################################");
			this.log.debug("bestContentsReq.getFilteredBy()	:	" + bestContentsReq.getFilteredBy());
			this.log.debug("########################################################");
			if ("movie".equals(bestContentsReq.getFilteredBy()) || "boardcast".equals(bestContentsReq.getFilteredBy())
					|| "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
				contentsList = this.commonDAO.queryForList("BestContents.selectBestContentsVodList", bestContentsReq,
						BestContents.class);
			} else {
				contentsList = this.commonDAO.queryForList("BestContents.selectBestContentsBookList", bestContentsReq,
						BestContents.class);
			}

			if (contentsList.size() != 0) {
				Iterator<BestContents> iterator = contentsList.iterator();
				while (iterator.hasNext()) {
					BestContents mapperVO = iterator.next();
					Product product = new Product();
					Identifier identifier = new Identifier();
					Contributor contributor = new Contributor();
					Accrual accrual = new Accrual();
					Rights rights = new Rights();
					Title title = new Title();
					Source source = new Source();
					Price price = new Price();
					Support support = new Support();
					Book book = new Book();

					// 상품ID
					identifier = new Identifier();
					identifier.setType("channel");
					identifier.setText(mapperVO.getProdId());

					supportList = new ArrayList<Support>();
					support.setType("hd");
					support.setText(mapperVO.getHdvYn());
					supportList.add(support);

					/*
					 * Menu(메뉴정보) Id, Name, Type
					 */
					menuList = new ArrayList<Menu>();
					Menu menu = new Menu();
					menu.setId(mapperVO.getTopMenuId());
					menu.setName(mapperVO.getUpMenuNm());
					menu.setType("topClass");
					menuList.add(menu);
					menu = new Menu();
					menu.setId(mapperVO.getMenuId());
					menu.setName(mapperVO.getMenuNm());
					menuList.add(menu);
					menu = new Menu();
					menu.setId(mapperVO.getMetaClsfCd());
					menu.setType("metaClass");
					menuList.add(menu);

					if ("movie".equals(bestContentsReq.getFilteredBy())
							|| "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
						contributor.setDirector(mapperVO.getArtist2Nm());
						contributor.setArtist(mapperVO.getArtist1Nm());
						Date date = new Date();
						date.setText(mapperVO.getIssueDay());
						contributor.setDate(date);
					} else if ("broadcast".equals(bestContentsReq.getFilteredBy())) {
						contributor.setArtist(mapperVO.getArtist1Nm());
					} else if ("ebook".equals(bestContentsReq.getFilteredBy())) {
						contributor.setName(mapperVO.getArtist1Nm());
						contributor.setPublisher(mapperVO.getChnlCompNm());
						Date date = new Date();
						date.setText(mapperVO.getIssueDay());
						contributor.setDate(date);
					} else if ("comic".equals(bestContentsReq.getFilteredBy())
							|| "ebook+comic".equals(bestContentsReq.getFilteredBy())) {
						contributor.setName(mapperVO.getArtist1Nm());
						contributor.setPainter(mapperVO.getArtist2Nm());
						contributor.setPublisher(mapperVO.getChnlCompNm());
					}

					/*
					 * Accrual - voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
					 */
					accrual.setVoterCount(mapperVO.getPaticpersCnt());
					accrual.setDownloadCount(mapperVO.getDwldCnt());
					accrual.setScore(mapperVO.getAvgEvluScore());

					/*
					 * Rights - grade
					 */
					rights.setGrade(mapperVO.getProdGrdCd());

					title.setPrefix(mapperVO.getVodTitlNm());
					title.setText(mapperVO.getProdNm());
					title.setPostfix(mapperVO.getChapter());
					// title.setText(mapperVO.getConcatProdNm());

					/*
					 * source mediaType - url
					 */
					sourceList = new ArrayList<Source>();
					source.setUrl(mapperVO.getImgPath());
					sourceList.add(source);

					/*
					 * Price text
					 */
					price.setText(mapperVO.getProdAmt());

					if (!"movie".equals(bestContentsReq.getFilteredBy())
							&& !"boardcast".equals(bestContentsReq.getFilteredBy())
							&& !"movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
						supportList = new ArrayList<Support>();
						book.setStatus(mapperVO.getBookStatus());
						book.setType(mapperVO.getBookType());
						book.setTotalCount(mapperVO.getBookCount());
						support = new Support();
						support.setType("store");
						if (!"".equals(mapperVO.getSupportStore()) && mapperVO.getSupportStore() != null) {
							support.setText(mapperVO.getSupportStore());
						} else {
							support.setText("");
						}
						support = new Support();
						support.setType("play");
						if (!"".equals(mapperVO.getSupportPlay()) && mapperVO.getSupportPlay() != null) {
							support.setText(mapperVO.getSupportPlay());
						} else {
							support.setText("");
						}
						supportList.add(support);
						book.setSupportList(supportList);
					}

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
					product.setProductExplain(mapperVO.getProdBaseDesc());
					product.setPrice(price);
					product.setBook(book);

					productList.add(product);

					commonResponse = new CommonResponse();
					commonResponse.setTotalCount(mapperVO.getTotalCount());
				}
			} else {
				commonResponse = new CommonResponse();
				commonResponse.setTotalCount(0);
				this.log.debug("조회된 결과가 없습니다.");
			}
		} else {
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

			supportList = new ArrayList<Support>();
			support.setType("hd");
			support.setText("Y");
			supportList.add(support);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menuList = new ArrayList<Menu>();
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

			if ("movie".equals(bestContentsReq.getFilteredBy())
					|| "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
				contributor.setDirector("곽경택");
				contributor.setArtist("유오성,주진모,김우빈,박아인,강한나,한수아");
				Date date = new Date();
				date.setText("20131114");
				contributor.setDate(date);
			} else if ("broadcast".equals(bestContentsReq.getFilteredBy())) {
				contributor.setArtist("유재석,지석진,김종국,하하,개리,이광수");
			} else if ("ebook".equals(bestContentsReq.getFilteredBy())) {
				contributor.setName("정현웅");
				contributor.setPublisher("L&amp;B BOOKS");
				Date date = new Date();
				date.setText("20130322");
				contributor.setDate(date);
			} else if ("comic".equals(bestContentsReq.getFilteredBy())
					|| "ebook+comic".equals(bestContentsReq.getFilteredBy())) {
				contributor.setName("황성");
				contributor.setPainter("황성");
				contributor.setPublisher("미스터블루");
			}

			/*
			 * Accrual - voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
			 */
			accrual.setVoterCount(51);
			accrual.setDownloadCount(5932);
			accrual.setScore(3.8);

			/*
			 * Rights - grade
			 */
			rights.setGrade("4");

			title.setPrefix("[20%할인]");
			title.setText("친구");
			title.setPostfix("2");
			// title.setText("[20%할인]친구 2");

			/*
			 * source mediaType - url
			 */
			sourceList = new ArrayList<Source>();
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

		response.setCommonResponse(commonResponse);
		response.setProductList(productList);

		return response;
	}
}
