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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.best.vo.BestApp;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 19. Updated by : 이석희, SK 플래닛.
 */
@Service
@Transactional
public class BestAppServiceImpl implements BestAppService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.BestAppService#BestAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.BestAppReqVO)
	 */
	@Override
	public BestAppRes searchBestAppList(SacRequestHeader requestheader, BestAppReq bestAppReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		this.log.debug("########################################################");
		this.log.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.log.debug("tenantHeader.getLangCd()	:	" + tenantHeader.getLangCd());
		this.log.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.log.debug("########################################################");

		bestAppReq.setTenantId(tenantHeader.getTenantId());
		bestAppReq.setLangCd(tenantHeader.getLangCd());
		bestAppReq.setDeviceModelCd(deviceHeader.getModel());

		BestAppRes response = new BestAppRes();
		CommonResponse commonResponse = new CommonResponse();
		int totalCount = 0;
		int offset = 1;
		int count = 20;

		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = null;
		List<Support> supportList = null;
		List<Source> sourceList = null;
		List<Identifier> identifierList = null;

		if (StringUtils.isEmpty(bestAppReq.getListId())) {
			this.log.error("필수 파라미터(listId)값이 없음");
			commonResponse.setTotalCount(0);
			response.setCommonResponse(commonResponse);
			response.setProductList(productList);
			return response;
		}

		if (bestAppReq.getOffset() != null) {
			offset = bestAppReq.getOffset();
		} else {
			bestAppReq.setOffset(offset);
		}

		if (bestAppReq.getCount() != null) {
			count = bestAppReq.getCount();
		} else {
			bestAppReq.setCount(count);
		}

		count = offset + count - 1;
		bestAppReq.setCount(count);

		String stdDt = this.commonService
				.getBatchStandardDateString(tenantHeader.getTenantId(), bestAppReq.getListId());
		bestAppReq.setStdDt(stdDt); // 2014.01.28 이석희 수정 S01 하드코딩에서 헤더에서 get 한 TenantId

		// BEST 앱 상품 조회
		List<BestApp> appList = null;

		Product product = null;
		Identifier identifier = null;
		App app = null;
		Accrual accrual = null;
		Rights rights = null;
		Source source = null;
		Price price = null;
		Title title = null;
		Support support = null;
		Menu menu = null;

		if (bestAppReq.getDummy() == null) {
			// dummy 호출이 아닐때

			if (!"ADM000000001".equals(bestAppReq.getListId())) {
				// 추천, 인기(매출), 인기신규 상품 조회
				appList = this.commonDAO.queryForList("BestApp.selectBestAppList", bestAppReq, BestApp.class);
			} else {
				// 신규 상품조회
				appList = this.commonDAO.queryForList("BestApp.selectNewBestAppList", bestAppReq, BestApp.class);
			}

			if (appList.size() > 0) {
				Iterator<BestApp> iterator = appList.iterator();
				while (iterator.hasNext()) {
					product = new Product();
					identifier = new Identifier();
					app = new App();
					accrual = new Accrual();
					rights = new Rights();
					source = new Source();
					price = new Price();
					title = new Title();
					support = new Support();

					BestApp mapperVO = iterator.next();

					totalCount = mapperVO.getTotalCount();
					commonResponse.setTotalCount(totalCount);

					/*
					 * Identifier 객체 List로 변경
					 */
					identifierList = new ArrayList<Identifier>();
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD); // 2014.01.28 이석희 common 상수 변경
					identifier.setText(mapperVO.getProdId());
					identifierList.add(identifier);

					supportList = new ArrayList<Support>();
					support = new Support();
					support.setType(DisplayConstants.DP_DRM_SUPPORT_NM); // 2014.01.28 이석희 common 상수 변경
					support.setText(mapperVO.getDrmYn());
					supportList.add(support);
					support = new Support();
					support.setType(DisplayConstants.DP_IN_APP_SUPPORT_NM); // 2014.01.28 이석희 common 상수 변경
					if (mapperVO.getPartParentClsfCd() == null || "".equals(mapperVO.getPartParentClsfCd())) {
						support.setText("");
					} else {
						support.setText(mapperVO.getPartParentClsfCd());
					}
					supportList.add(support);

					menuList = new ArrayList<Menu>();

					menu = new Menu();
					menu.setId(mapperVO.getTopMenuId());
					menu.setName(mapperVO.getTopMenuNm());
					menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
					menuList.add(menu);
					menu = new Menu();
					menu.setId(mapperVO.getMenuId());
					menu.setName(mapperVO.getMenuNm());
					menuList.add(menu);

					app.setAid(mapperVO.getAid());
					app.setPackageName(mapperVO.getApkPkgNm());
					app.setVersionCode(mapperVO.getApkVerCd());
					app.setVersion(mapperVO.getApkVer());
					app.setSize(mapperVO.getFileSize());

					accrual.setVoterCount(mapperVO.getPaticpersCnt());
					accrual.setDownloadCount(mapperVO.getDwldCnt());
					accrual.setScore(mapperVO.getAvgEvluScore());

					rights.setGrade(mapperVO.getProdGrdCd());

					title.setText(mapperVO.getProdNm());

					sourceList = new ArrayList<Source>();
					source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getImgPath()));
					source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL); // 2014.01.28 이석희 common 상수 변경
					source.setUrl(mapperVO.getImgPath());
					source.setSize(mapperVO.getImgSize());
					sourceList.add(source);

					price.setText(mapperVO.getProdAmt());

					product = new Product();
					product.setIdentifierList(identifierList); // 2014.01.28 이석희 List 형태로 변경
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
			}

		} else {
			// dummy data를 호출할때
			menuList = new ArrayList<Menu>();
			supportList = new ArrayList<Support>();
			sourceList = new ArrayList<Source>();

			product = new Product();
			identifier = new Identifier();
			app = new App();
			accrual = new Accrual();
			rights = new Rights();
			source = new Source();
			price = new Price();
			title = new Title();
			support = new Support();

			// 상품ID
			identifierList = new ArrayList<Identifier>();
			identifier = new Identifier();
			identifier.setType("episode");
			identifier.setText("0000643818");
			identifierList.add(identifier);

			support.setType("Y");
			support.setText("iab");
			supportList.add(support);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menu = new Menu();
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
			app.setSize(5908912);

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
			source.setMediaType("image/png");
			source.setSize(1234);
			source.setType("thumbnail");
			source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
			sourceList.add(source);

			/*
			 * Price text
			 */
			price.setText(0);

			product = new Product();
			product.setIdentifierList(identifierList);
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
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
