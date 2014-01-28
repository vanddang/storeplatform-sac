package com.skplanet.storeplatform.sac.display.feature.best.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
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
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		bestContentsReq.setTenantId(tenantHeader.getTenantId());
		bestContentsReq.setDeviceModelCd(deviceHeader.getModel());

		BestContentsRes response = new BestContentsRes();
		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();
		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;

		int offset = 1;
		int count = 20;

		if (StringUtils.isEmpty(bestContentsReq.getListId())) {
			this.log.error("필수 파라미터(listId)값이 없음");
			commonResponse.setTotalCount(0);
			response.setCommonResponse(commonResponse);
			response.setProductList(productList);
			return response;

		}
		if (StringUtils.isEmpty(bestContentsReq.getFilteredBy())) {
			bestContentsReq.setFilteredBy("movie+broadcast");
		}

		if (bestContentsReq.getOffset() != null) {
			offset = bestContentsReq.getOffset();
		} else {
			bestContentsReq.setOffset(offset);
		}

		if (bestContentsReq.getCount() != null) {
			count = bestContentsReq.getCount();
		} else {
			bestContentsReq.setCount(count);
		}

		// 2014.01.28 이석희 추가
		if ("movie".equals(bestContentsReq.getFilteredBy()) || "boardcast".equals(bestContentsReq.getFilteredBy())
				|| "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
			bestContentsReq.setImgCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		} else {
			bestContentsReq.setImgCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
		}
		// 2014.01.28 이석희 추가 끝

		count = offset + count - 1;
		bestContentsReq.setCount(count);

		String stdDt = this.commonService.getBatchStandardDateString(tenantHeader.getTenantId(),
				bestContentsReq.getListId());
		bestContentsReq.setStdDt(stdDt);// 2014.01.28 이석희 수정 S01 하드코딩에서 헤더에서 get 한 TenantId

		if (bestContentsReq.getDummy() == null) {
			// dummy 호출이 아닐때
			// BEST 컨텐츠 상품 조회
			List<BestContents> contentsList = null;

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
					identifierList = new ArrayList<Identifier>();
					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);// 2014.01.28 이석희 common 상수 변경
					identifier.setText(mapperVO.getProdId());
					identifierList.add(identifier);

					supportList = new ArrayList<Support>();
					support = new Support();
					support.setType(DisplayConstants.DP_VOD_HD_SUPPORT_NM);// 2014.01.28 이석희 common 상수 변경
					support.setText(mapperVO.getHdvYn());
					supportList.add(support);
					support = new Support();
					support.setType(DisplayConstants.DP_VOD_DOLBY_SUPPORT_NM);// 2014.01.28 이석희 common 상수 변경
					support.setText(mapperVO.getDolbySprtYn());
					supportList.add(support);

					/*
					 * Menu(메뉴정보) Id, Name, Type
					 */
					menuList = new ArrayList<Menu>();
					Menu menu = new Menu();
					menu.setId(mapperVO.getTopMenuId());
					menu.setName(mapperVO.getUpMenuNm());
					menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);// 2014.01.28 이석희 common 상수 변경
					menuList.add(menu);
					menu = new Menu();
					menu.setId(mapperVO.getMenuId());
					menu.setName(mapperVO.getMenuNm());
					menuList.add(menu);
					menu = new Menu();
					menu.setId(mapperVO.getMetaClsfCd());
					menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);// 2014.01.28 이석희 common 상수 변경
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
					// 2014.01.28 이석희 추가
					source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getImgPath()));
					source.setSize(mapperVO.getImgSize());
					source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
					// 2014.01.28 이석희 추가 끝
					source.setUrl(mapperVO.getImgPath());
					sourceList.add(source);

					/*
					 * Price text
					 */
					price.setText(mapperVO.getProdAmt());

					if (!"movie".equals(bestContentsReq.getFilteredBy())
							&& !"boardcast".equals(bestContentsReq.getFilteredBy())
							&& !"movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
						List<Support> bookSupportList = new ArrayList<Support>();
						if (mapperVO.getBookStatus() == null) {
							book.setStatus("");
						} else {
							book.setStatus(mapperVO.getBookStatus());
						}
						if (mapperVO.getBookType() == null) {
							book.setType("");
						} else {
							book.setType(mapperVO.getBookType());
						}
						book.setTotalCount(mapperVO.getBookCount());
						support = new Support();
						support.setType(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM); // 2014.01.28 이석희 common 상수 변경
						support.setText(mapperVO.getSupportStore());
						bookSupportList.add(support);
						support = new Support();
						support.setType(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM); // 2014.01.28 이석희 common 상수 변경
						support.setText(mapperVO.getSupportPlay());
						bookSupportList.add(support);
						book.setSupportList(bookSupportList);
					}

					product = new Product();
					product.setIdentifierList(identifierList);
					if ("movie".equals(bestContentsReq.getFilteredBy())
							|| "boardcast".equals(bestContentsReq.getFilteredBy())
							|| "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
						product.setSupportList(supportList);
					}

					if (!"movie".equals(bestContentsReq.getFilteredBy())
							&& !"boardcast".equals(bestContentsReq.getFilteredBy())
							&& !"movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
						product.setBook(book); // 2014.01.28 이북 /코믹일때만 Book(소장대여 정보) set
					}

					// product.setSupport("hd");
					product.setMenuList(menuList);
					product.setContributor(contributor);
					product.setAccrual(accrual);
					product.setRights(rights);
					product.setTitle(title);
					product.setSourceList(sourceList);
					product.setProductExplain(mapperVO.getProdBaseDesc());
					product.setPrice(price);

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
			// 2014.01.28 이석희 추가
			source.setMediaType("image/png");
			source.setSize(4325);
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			// 2014.01.28 이석희 추가 끝
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
