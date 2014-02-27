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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.display.app.vo.*;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

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

		AppDetail appDetail = this.commonDAO.queryForObject("AppDetail.getAppDetail", request, AppDetail.class);
		if (appDetail == null)
			return null;

		AppDetailRes res = new AppDetailRes();
        Product product = new Product();
        res.setProduct(product);

        if (!appDetail.getProdStatusCd().equals(DisplayConstants.DP_SALE_STAT_ING)) {
            // 04, 09, 10의 경우 구매이력이 없으면 상품 없음을 표시한다.
            if (DisplayConstants.DP_SALE_STAT_PAUSED.equals(appDetail.getProdStatusCd()) ||
                    DisplayConstants.DP_SALE_STAT_RESTRIC_DN.equals(appDetail.getProdStatusCd()) ||
                    DisplayConstants.DP_SALE_STAT_DROP_REQ_DN.equals(appDetail.getProdStatusCd())) {
                if (!StringUtils.isEmpty(request.getUserKey()) && !StringUtils.isEmpty(request.getDeviceKey()) &&
                        !commonService.checkPurchase(request.getTenantId(), request.getUserKey(), request.getDeviceKey(), request.getChannelId())) {
                    return null;
                }
                else
                    res.getProduct().setSalesStatus("restricted");
            }
            else
                res.getProduct().setSalesStatus("restricted");
        }

        // Product Basic info
        product.setIdentifier(new Identifier("episode", request.getChannelId()));
        product.setPacketFee(appDetail.getProdGbn());

		product.setTitle(new Title(appDetail.getProdNm()));
		Price price = new Price();
		price.setFixedPrice(appDetail.getProdAmt());
		product.setPrice(price);

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
		rights.setGrade(appDetail.getProdGrdCd());
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
