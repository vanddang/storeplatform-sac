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
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.best.vo.BestAppDTO;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 19. Updated by : 이석희, SK 플래닛.
 */
@Service
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
	public BestAppRes searchBestAppList(BestAppReq bestAppReq) {
		BestAppRes response = new BestAppRes();
		int totalCount = 0;

		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = null;
		List<Support> supportList = null;
		List<Source> sourceList = null;

		String stdDt = this.commonService.getBatchStandardDateString("S01", bestAppReq.getListId());

		bestAppReq.setStdDt(stdDt);

		// BEST 앱 상품 조회
		List<BestAppDTO> appList = null;

		if (!"ADM000000001".equals(bestAppReq.getListId())) {
			// 추천, 인기(매출), 인기신규 상품 조회
			appList = this.commonDAO.queryForList("BestApp.selectBestAppList", bestAppReq, BestAppDTO.class);
		} else {
			// 신규 상품조회
			appList = this.commonDAO.queryForList("BestApp.selectNewBestAppList", bestAppReq, BestAppDTO.class);
		}

		CommonResponse commonResponse = new CommonResponse();

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

		if (appList != null) {
			Iterator<BestAppDTO> iterator = appList.iterator();
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

				BestAppDTO mapperVO = iterator.next();

				totalCount = mapperVO.getTotalCount();
				commonResponse.setTotalCount(totalCount);

				identifier.setType("episodeId");
				identifier.setText(mapperVO.getProdId());

				supportList = new ArrayList<Support>();
				support = new Support();
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

				menuList = new ArrayList<Menu>();

				menu = new Menu();
				menu.setId(mapperVO.getUpMenuid());
				menu.setName(mapperVO.getUpMenuNm());
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

				sourceList = new ArrayList<Source>();
				source.setType("thumbnail");
				source.setUrl(mapperVO.getImgPath());
				sourceList.add(source);

				price.setText(mapperVO.getProdAmt());

				product = new Product();
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
			response.setCommonResponse(commonResponse);
			response.setProductList(productList);
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			response.setCommonResponse(commonResponse);
		}
		return response;
	}
}
