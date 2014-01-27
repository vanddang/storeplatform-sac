/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
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
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.best.vo.BestDownload;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 20. Updated by : 이석희, SK 플래닛.
 */

@Service
@Transactional
public class BestDownloadServiceImpl implements BestDownloadService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.BestDownloadService#BestDownloadService(com.skplanet
	 * .storeplatform.sac.client.product.vo.BestDownloadRequestVO)
	 */
	@Override
	public BestDownloadRes searchBestDownloadList(SacRequestHeader requestheader, BestDownloadReq bestDownloadReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		this.log.debug("########################################################");
		this.log.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.log.debug("tenantHeader.getLangCd()	:	" + tenantHeader.getLangCd());
		this.log.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.log.debug("########################################################");

		bestDownloadReq.setTenantId(tenantHeader.getTenantId());
		bestDownloadReq.setLangCd(tenantHeader.getLangCd());
		bestDownloadReq.setDeviceModelCd(deviceHeader.getModel());

		BestDownloadRes response = new BestDownloadRes();

		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		if (bestDownloadReq.getListId() == null || "".equals(bestDownloadReq.getListId())) {
			this.log.error("필수 파라미터(listId)값이 없음");
			commonResponse.setTotalCount(0);
			response.setCommonResponse(commonResponse);
			response.setProductList(productList);
			return response;

		}

		int offset = 1; // default
		int count = 20; // default

		if (bestDownloadReq.getOffset() != null) {
			offset = bestDownloadReq.getOffset();
		}
		bestDownloadReq.setOffset(offset);

		if (bestDownloadReq.getCount() != null) {
			count = bestDownloadReq.getCount();
		}
		count = offset + count - 1;
		bestDownloadReq.setCount(count);

		String stdDt = this.commonService.getBatchStandardDateString(bestDownloadReq.getTenantId(),
				bestDownloadReq.getListId());
		bestDownloadReq.setStdDt(stdDt);

		// BEST 다운로드 상품 조회
		List<BestDownload> bestList = null;

		if (bestDownloadReq.getDummy() == null) { // dummy 호출이 아닐때
			if ("DP13".equals(bestDownloadReq.getTopMenuId()) || "DP14".equals(bestDownloadReq.getTopMenuId())
					|| "DP17".equals(bestDownloadReq.getTopMenuId()) || "DP18".equals(bestDownloadReq.getTopMenuId())) { // 멀티미디어_상품
				bestList = this.commonDAO.queryForList("BestDownload.selectBestDownloadMMList", bestDownloadReq,
						BestDownload.class);

				if (bestList.size() > 0) {
					Iterator<BestDownload> iterator = bestList.iterator();
					while (iterator.hasNext()) {
						BestDownload mapperVO = iterator.next();
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
						identifier.setType("episode");
						identifier.setText(mapperVO.getProdId());

						/*
						 * VOD - HD 지원여부, DOLBY 지원여부
						 */
						List<Support> supportList = new ArrayList<Support>();
						support.setType("hd");
						support.setText(mapperVO.getHdvYn());
						supportList.add(support);
						support = new Support();
						support.setType("dolby");
						support.setText(mapperVO.getDolbySprtYn());
						supportList.add(support);

						/*
						 * Menu(메뉴정보) Id, Name, Type
						 */
						ArrayList<Menu> menuList = new ArrayList<Menu>();
						Menu menu = new Menu();
						menu.setId(mapperVO.getTopMenuId());
						menu.setName(mapperVO.getTopMenuNm());
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

						if ("DP13".equals(bestDownloadReq.getTopMenuId())) { // 이북
							contributor.setName(mapperVO.getArtist1Nm());
							contributor.setPublisher(mapperVO.getChnlCompNm());
							Date date = new Date();
							date.setText(mapperVO.getIssueDay());
							contributor.setDate(date);
						} else if ("DP14".equals(bestDownloadReq.getTopMenuId())) { // 코믹
							contributor.setName(mapperVO.getArtist1Nm());
							contributor.setPainter(mapperVO.getArtist2Nm());
							contributor.setPublisher(mapperVO.getChnlCompNm());
						} else if ("DP17".equals(bestDownloadReq.getTopMenuId())) { // 영화
							contributor.setDirector(mapperVO.getArtist2Nm());
							contributor.setArtist(mapperVO.getArtist1Nm());
							Date date = new Date();
							date.setText(mapperVO.getIssueDay());
							contributor.setDate(date);
						} else if ("DP18".equals(bestDownloadReq.getTopMenuId())) { // 방송
							contributor.setArtist(mapperVO.getArtist1Nm());
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
						ArrayList<Source> sourceList = new ArrayList<Source>();
						source.setType("thumbnail");
						source.setUrl(mapperVO.getImgPath());
						sourceList.add(source);

						/*
						 * Price text
						 */
						price.setText(mapperVO.getProdAmt());

						/*
						 * 이북, 코믹 일 경우에만
						 */
						if ("DP13".equals(bestDownloadReq.getTopMenuId())
								|| "DP14".equals(bestDownloadReq.getTopMenuId())) {
							ArrayList<Support> supportList2 = new ArrayList<Support>();
							book.setStatus(mapperVO.getBookStatus());
							book.setType(mapperVO.getBookType());
							book.setTotalCount(mapperVO.getBookCount());

							support = new Support();
							support.setType("store");
							support.setText(mapperVO.getSupportStore());
							supportList2.add(support);
							support = new Support();
							support.setType("play");
							support.setText(mapperVO.getSupportPlay());
							supportList2.add(support);

							book.setSupportList(supportList2);
						}

						product = new Product();
						product.setIdentifier(identifier);
						/*
						 * 영화, 방송 일 경우에만
						 */
						if ("DP17".equals(bestDownloadReq.getTopMenuId())
								|| "DP18".equals(bestDownloadReq.getTopMenuId())) {
							product.setSupportList(supportList);
						}
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
					commonResponse.setTotalCount(0);
					response.setCommonResponse(commonResponse);
					response.setProductList(productList);
				}

			} else { // App 상품
				bestList = this.commonDAO.queryForList("BestDownload.selectBestDownloadAppList", bestDownloadReq,
						BestDownload.class);

				if (bestList.size() > 0) {
					Iterator<BestDownload> iterator = bestList.iterator();
					while (iterator.hasNext()) {
						Product product = new Product();
						Identifier identifier = new Identifier();
						App app = new App();
						Accrual accrual = new Accrual();
						Rights rights = new Rights();
						Source source = new Source();
						Price price = new Price();
						Title title = new Title();
						Support support = new Support();
						Menu menu = new Menu();

						BestDownload mapperVO = iterator.next();

						int totalCount = mapperVO.getTotalCount();
						commonResponse.setTotalCount(totalCount);

						identifier.setType("episode");
						identifier.setText(mapperVO.getProdId());

						List<Support> supportList = new ArrayList<Support>();
						support.setType("drm");
						support.setText(mapperVO.getDrmYn());
						supportList.add(support);
						support = new Support();
						support.setType("iab");
						if (mapperVO.getPartParentClsfCd() == null || "".equals(mapperVO.getPartParentClsfCd())) {
							support.setText("");
						} else {
							support.setText(mapperVO.getPartParentClsfCd());
						}
						supportList.add(support);

						List<Menu> menuList = new ArrayList<Menu>();
						menu.setId(mapperVO.getTopMenuId());
						menu.setName(mapperVO.getTopMenuNm());
						menu.setType("topClass");
						menuList.add(menu);
						menu = new Menu();
						menu.setId(mapperVO.getMenuId());
						menu.setName(mapperVO.getMenuNm());
						menuList.add(menu);

						app.setAid(mapperVO.getAid());
						app.setPackageName(mapperVO.getApkPkgNm());
						app.setVersionCode(mapperVO.getApkVerCd());
						app.setVersion(mapperVO.getApkVer());

						accrual.setVoterCount(mapperVO.getPaticpersCnt());
						accrual.setDownloadCount(mapperVO.getDwldCnt());
						accrual.setScore(mapperVO.getAvgEvluScore());

						rights.setGrade(mapperVO.getProdGrdCd());

						title.setText(mapperVO.getProdNm());

						List<Source> sourceList = new ArrayList<Source>();
						source.setType("thumbnail");
						source.setUrl(mapperVO.getImgPath());
						sourceList.add(source);

						price.setText(mapperVO.getProdAmt());

						product.setIdentifier(identifier);
						product.setSupportList(supportList);
						product.setMenuList(menuList);
						product.setApp(app);
						product.setAccrual(accrual);
						product.setRights(rights);
						product.setTitle(title);
						product.setSourceList(sourceList);
						product.setProductExplain(mapperVO.getProdBaseDesc());
						product.setPrice(price);

						productList.add(product);
					}
				} else {
					commonResponse.setTotalCount(0);
					response.setCommonResponse(commonResponse);
					response.setProductList(productList);
				}
			}

		} else {
			// dummy data를 호출할때
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
				Menu menu = new Menu();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("episode");
				identifier.setText("0000643818");

				List<Support> supportList = new ArrayList<Support>();
				support.setType("PD012301");
				support.setText("PD012301");
				supportList.add(support);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				List<Menu> menuList = new ArrayList<Menu>();
				menu.setId("DP000501");
				menu.setName("게임");
				menu.setType("topClass");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("DP01004");
				menu.setName("RPG");
				menuList.add(menu);

				/*
				 * App aid, packagename, versioncode, version 상품이 앱일 경우 데이터 존재 앱이 아닐 경우 없음
				 */
				app.setAid("OA00643818");
				app.setPackageName("proj.syjt.tstore");
				app.setVersionCode("11000");
				app.setVersion("1.1");

				/*
				 * Accrual voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
				 */
				accrual.setVoterCount(14305);
				accrual.setDownloadCount(513434);
				accrual.setScore(4.8);

				/*
				 * Rights grade
				 */
				rights.setGrade("0");

				title.setText("워밸리 온라인");

				/*
				 * source mediaType, size, type, url
				 */
				List<Source> sourceList = new ArrayList<Source>();
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

				commonResponse.setTotalCount(10);
			}
		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);

		return response;
	}
}
