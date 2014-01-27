/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.download.vo.DownloadMusic;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
@Transactional
public class DownloadMusicServiceImpl implements DownloadMusicService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadAppService#DownloadAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.DownloadAppReqVO)
	 */
	@Override
	public DownloadMusicRes searchDownloadMusic(SacRequestHeader requestheader, DownloadMusicReq downloadMusicReq) {
		TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		downloadMusicReq.setTenantId(tanantHeader.getTenantId());
		downloadMusicReq.setDeviceModelCd(deviceHeader.getModel());

		DownloadMusicRes response = new DownloadMusicRes();
		CommonResponse commonResponse = new CommonResponse();
		int totalCount = 0;

		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Support> supportList = null;
		List<Source> sourceList = null;

		// 다운로드 Music 상품 조회
		DownloadMusic downloadMusicInfo = null;

		Product product = null;
		Identifier identifier = null;
		Rights rights = null;
		Source source = null;
		Title title = null;
		Menu menu = null;
		Purchase purchase = null;
		Contributor contributor = null;
		Music music = null;

		if (downloadMusicReq.getDummy() == null) {
			this.log.debug("##################################################");
			this.log.debug("dummy 호출 아님");
			this.log.debug("##################################################");
			// dummy 호출이 아닐때

			// if (!"ADM000000001".equals(DownloadAppReq.getListId())) {
			// // 추천, 인기(매출), 인기신규 상품 조회
			// appList = this.commonDAO.queryForList("DownloadApp.selectDownloadAppList", DownloadAppReq,
			// DownloadApp.class);
			// } else {
			// // 신규 상품조회
			// appList = this.commonDAO.queryForList("DownloadApp.selectNewDownloadAppList", DownloadAppReq,
			// DownloadApp.class);
			// }

			// product = new Product();
			// identifier = new Identifier();
			// app = new App();
			// accrual = new Accrual();
			// rights = new Rights();
			// source = new Source();
			// price = new Price();
			// title = new Title();
			// support = new Support();
			//
			// DownloadApp mapperVO = iterator.next();
			//
			// totalCount = mapperVO.getTotalCount();
			// commonResponse.setTotalCount(totalCount);
			//
			// identifier.setType("episodeId");
			// identifier.setText(mapperVO.getProdId());
			//
			// supportList = new ArrayList<Support>();
			// support = new Support();
			// support.setType("drm");
			// support.setText(mapperVO.getDrmYn());
			// supportList.add(support);
			// support = new Support();
			// support.setType("iab");
			// if (mapperVO.getPartParentClsfCd() == null || "".equals(mapperVO.getPartParentClsfCd())) {
			// support.setText("");
			// } else {
			// support.setText(mapperVO.getPartParentClsfCd());
			// }
			// supportList.add(support);
			//
			// menuList = new ArrayList<Menu>();
			//
			// menu = new Menu();
			// menu.setId(mapperVO.getTopMenuId());
			// menu.setName(mapperVO.getUpMenuNm());
			// menu.setType("topClass");
			// menuList.add(menu);
			// menu = new Menu();
			// menu.setId(mapperVO.getMenuId());
			// menu.setName(mapperVO.getMenuNm());
			// menuList.add(menu);
			//
			// app.setAid(mapperVO.getAid());
			// app.setPackageName(mapperVO.getApkPkgNm());
			// app.setVersionCode(mapperVO.getApkVerCd());
			// app.setVersion(mapperVO.getApkVer());
			//
			// accrual.setVoterCount(mapperVO.getPaticpersCnt());
			// accrual.setDownloadCount(mapperVO.getDwldCnt());
			// accrual.setScore(mapperVO.getAvgEvluScore());
			//
			// rights.setGrade(mapperVO.getProdGrdCd());
			//
			// title.setText(mapperVO.getProdNm());
			//
			// sourceList = new ArrayList<Source>();
			// source.setType("thumbnail");
			// source.setUrl(mapperVO.getImgPath());
			// sourceList.add(source);
			//
			// price.setText(mapperVO.getProdAmt());
			//
			// product = new Product();
			// product.setIdentifier(identifier);
			// product.setSupportList(supportList);
			// product.setMenuList(menuList);
			// product.setApp(app);
			// product.setAccrual(accrual);
			// product.setRights(rights);
			// product.setTitle(title);
			// product.setSourceList(sourceList);
			// product.setProductExplain(mapperVO.getProdBaseDesc());
			// product.setPrice(price);
			//
			// productList.add(product);

		} else {
			// dummy data를 호출할때
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();

			product = new Product();
			identifier = new Identifier();
			rights = new Rights();
			source = new Source();
			title = new Title();
			purchase = new Purchase();
			contributor = new Contributor();
			music = new Music();

			// 상품ID
			identifier = new Identifier();
			identifier.setType("episode");
			identifier.setText("H001601609");
			identifierList.add(identifier);
			identifier = new Identifier();
			identifier.setType("isPartOf");
			identifier.setText("H001601608");
			identifierList.add(identifier);

			title.setText("조금 이따 샤워해 (Feat. Crush)");

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("image/png");
			source.setType("thumbnail");
			source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PMUSIC/201401/20/0002112826/6/0003940962/6/10_0002112826_200_200_1709_200x200_R130x130.PNG");
			sourceList.add(source);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menu = new Menu();
			menu.setId("DP000516");
			menu.setName("뮤직");
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("DP16002002");
			menu.setName("가요");
			menu = new Menu();
			menu.setId("CT25");
			menu.setName("mp3");
			menu.setType("metaClass");
			menuList.add(menu);

			contributor.setName("Gary");
			contributor.setAlbum("MR.GAE");

			identifier = new Identifier();
			identifier.setType("downloadId");
			identifier.setText("4397969");
			sourceList = new ArrayList<Source>();
			source = new Source();
			source.setSize(3842715);
			source.setType("audio/mp3-128");
			sourceList.add(source);
			source = new Source();
			source.setSize(5764074);
			source.setType("audio/mp3-192");
			sourceList.add(source);
			music.setIdentifier(identifier);
			music.setSourceList(sourceList);

			/*
			 * Rights grade
			 */
			rights.setGrade("PD004401");

			identifier = new Identifier();
			identifier.setType("purchase");
			identifier.setText("GI100000000265812187");
			purchase.setIdentifier(identifier);
			purchase.setState("gift");
			purchase.setToken("863353467024782ffce534613bac6450048a87fb0dcdbb4e30184f2a88418a037b90c3a16eb87d6eef34b18f320114e9d9cc965fb055db");

			product = new Product();
			product.setIdentifierList(identifierList);
			product.setTitle(title);
			product.setSourceList(sourceList);
			product.setMenuList(menuList);
			product.setContributor(contributor);
			product.setMusic(music);
			product.setRights(rights);
			product.setPurchase(purchase);

			commonResponse.setTotalCount(1);
		}
		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}
}
