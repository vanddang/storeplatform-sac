/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.display.app.vo.*;
import com.skplanet.storeplatform.sac.display.app.vo.AppDetailParam;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.History;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;

/**
 * 앱 상품 상세조회
 * <p/>
 * Updated on : 2014. 01. 06. Updated by : 정희원, SK 플래닛.
 */
@Service
public class AppServiceImpl implements AppService {

	private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

	private static final Set<String> SOURCE_LIST_SCREENSHOT_ORIGINAL;
	private static final String[] SOURCE_REQUEST;
	static {
		SOURCE_LIST_SCREENSHOT_ORIGINAL = new HashSet<String>(Arrays.asList("DP000103", "DP000104", "DP000105",
				"DP000106", "DP0001C1", "DP0001C2", "DP0001C3", "DP0001C4"));
		List<String> reqList = new ArrayList<String>();
		reqList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD); // for Thumbnail original
		reqList.addAll(SOURCE_LIST_SCREENSHOT_ORIGINAL);
		SOURCE_REQUEST = reqList.toArray(new String[reqList.size()]);
	}

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	@Override
	public AppDetailRes getAppDetail(AppDetailParam request) {

		// TODO Provisioning - 단말기, 운영체제 버전
		AppDetail appDetail = this.commonDAO.queryForObject("AppDetail.getAppDetail", request, AppDetail.class);
		if (appDetail == null)
			return null;
		AppDetailRes res = new AppDetailRes();

        // Product Basic info
        Product product = new Product();
        product.setIdentifier(new Identifier("episode", request.getChannelId()));
        product.setPacketFee(appDetail.getProdGbn());

		product.setTitle(new Title(appDetail.getWapProdNm()));
		Price price = new Price();
		price.setFixedPrice(appDetail.getProdAmt());
		product.setPrice(price);

        // TODO 구매 메소드 호출하여 판매상태 반영
        product.setSalesStatus("PD000403");

        // Menu
        List<MenuItem> menuList = commonService.getMenuItemList(request.getChannelId(), request.getLangCd());
        product.setMenuList(new ArrayList<Menu>());
        for (MenuItem mi : menuList) {
            Menu menu = new Menu();
            menu.setId(mi.getMenuId());
            menu.setName(mi.getMenuNm());
            if(mi.isInfrMenu())
                menu.setType("topClass");

			product.getMenuList().add(menu);
		}

        // Source
        List<ImageSource> imageSourceList = commonDAO.queryForList("AppDetail.getSourceList", new ImageSourceReq(request.getChannelId(), SOURCE_REQUEST, request.getLangCd()), ImageSource.class);
        List<Source> sourceList = new ArrayList<Source>();
        for (ImageSource imgSrc : imageSourceList) {
            Source source = new Source();
            source.setMediaType(DisplayCommonUtil.getMimeType(imgSrc.getFileNm()));
            source.setUrl(imgSrc.getFilePath());

			if (SOURCE_LIST_SCREENSHOT_ORIGINAL.contains(imgSrc.getImgCd()))
				source.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
			else
				source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);

			sourceList.add(source);
		}
		product.setSourceList(sourceList);

		Accrual accrual = new Accrual();
		accrual.setVoterCount(appDetail.getPaticpersCnt());
		accrual.setDownloadCount(appDetail.getDwldCnt());
		accrual.setScore(appDetail.getAvgEvluScore());
		product.setAccrual(accrual);

		Rights rights = new Rights();
		rights.setGrade(appDetail.getProdGrd());
		product.setRights(rights);

		// App
		App app = new App();
		product.setApp(app);
		app.setAid(appDetail.getAid());
		app.setSupportedOs("Android " + appDetail.getVmVer());
		app.setPackageName(appDetail.getApkPkgNm());
		app.setVersionCode(appDetail.getApkVer());
		app.setVersion(appDetail.getApkVerNm());
		app.setSize(appDetail.getFileSize());

		// Update History
		History history = new History();
		List<UpdateHistory> updateHistoryList = this.commonDAO.queryForList("AppDetail.getUpdateHistoryList", request,
				UpdateHistory.class);
		List<Update> updateList = new ArrayList<Update>();
		for (UpdateHistory uh : updateHistoryList) {
			Update update = new Update();

			update.setUpdateExplain(uh.getUpdtText());
			update.setDate(new Date(DisplayConstants.DP_DATE_REG, uh.getProdUpdDt()));

			updateList.add(update);
		}
		app.setHistory(history);
		history.setUpdate(updateList);

		// Distributor
		Distributor distributor = new Distributor();
		distributor.setSellerKey(appDetail.getSellerMbrNo());
		distributor.setName(appDetail.getExpoSellerNm());
		distributor.setTel(appDetail.getExpoSellerTelno());
		distributor.setEmail(appDetail.getExpoSellerEmail());
		product.setDistributor(distributor);

		res.setProduct(product);
		return res;
	}

}
