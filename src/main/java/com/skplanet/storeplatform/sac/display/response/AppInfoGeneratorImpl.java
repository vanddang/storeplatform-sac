/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Install;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * App 상품 전용 정보 Generator 구현체.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Component
public class AppInfoGeneratorImpl implements AppInfoGenerator {

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.AppInfoGenerator#generateSupportList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Support> generateSupportList(MetaInfo metaInfo) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = this.commonGenerator.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM, metaInfo.getDrmYn());
		supportList.add(support);
		support = this.commonGenerator.generateSupport(DisplayConstants.DP_IN_APP_SUPPORT_NM,
				metaInfo.getPartParentClsfCd());
		supportList.add(support);
		return supportList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.AppInfoGenerator#generateApp(com.skplanet.storeplatform.sac.display
	 * .meta.vo.MetaInfo)
	 */
	@Override
	public App generateApp(MetaInfo metaInfo) {
		return this.generateApp(metaInfo.getAid(), metaInfo.getApkPkgNm(), metaInfo.getApkVer(), metaInfo.getProdVer(),
				metaInfo.getFileSize(), metaInfo.getSupportedOs(), metaInfo.getSubContentsId(), metaInfo.getFilePath());
	}

	@Override
	public App generateApp(String aid, String packageNm, String apkVer, String prodVer, Integer fileSize,
			String supportOs, String scid, String filePath) {
		App app = new App();
		app.setAid(aid);
		app.setPackageName(packageNm);
		app.setVersionCode(apkVer);
		app.setVersion(prodVer);
		app.setSize(fileSize);
		app.setSupportedOs(supportOs);
		app.setScId(scid);
		app.setFilePath(filePath);

		return app;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.AppInfoGenerator#generateMenuList(com.skplanet.storeplatform.
	 * sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Menu> generateMenuList(MetaInfo metaInfo) {
		Menu menu = new Menu();
		List<Menu> menuList = new ArrayList<Menu>();

		menu.setId(metaInfo.getMenuId());
		menu.setName(metaInfo.getMenuNm());
		menu.setDesc(metaInfo.getMenuDesc());
		menuList.add(menu);

		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(metaInfo.getTopMenuId());
		menu.setName(metaInfo.getTopMenuNm());
		menuList.add(menu);
		return menuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.AppInfoGenerator#generateMenuList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Menu> generateMenuList(String topMenuId, String topMenuNm, String menuId, String menuNm) {
		Menu menu = new Menu();
		List<Menu> menuList = new ArrayList<Menu>();

		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(topMenuId);
		menu.setName(topMenuNm);
		menuList.add(menu);

		menu = new Menu();
		menu.setId(menuId);
		menu.setName(menuNm);
		menuList.add(menu);
		return menuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.AppInfoGenerator#generateAppIdentifierList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Identifier> generateIdentifierList(MetaInfo metaInfo) {
		Identifier identifier = new Identifier();
		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
		identifier.setText(metaInfo.getPartProdId());
		identifierList.add(identifier);
		identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
		identifier.setText(metaInfo.getProdId());
		identifierList.add(identifier);
		return identifierList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.AppInfoGenerator#generateIdentifierList(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Identifier> generateIdentifierList(String type, String text) {
		Identifier identifier = new Identifier();
		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifier.setType(type);
		identifier.setText(text);
		identifierList.add(identifier);
		return identifierList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.AppInfoGenerator#generateAppIdentifierList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Identifier> generateComponentIdentifierList(MetaInfo metaInfo) {
		Identifier identifier = new Identifier();
		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
		identifier.setText(metaInfo.getSeedProductId());
		identifierList.add(identifier);
		identifier = new Identifier();
		identifier.setType(DisplayConstants.DP_GAMECENTER_IDENTIFIER_CD);
		identifier.setText(metaInfo.getGameCentrId());
		identifierList.add(identifier);
		return identifierList;
	}

	@Override
	public Update generateUpdate(Date date, String updateExplain) {
		Update update = new Update();
		update.setUpdateExplain(updateExplain);
		update.setDate(date);

		return update;
	}

	@Override
	public Install generateInstall(String caller, String upgrade) {
		Install install = new Install();
		install.setCaller(caller);
		install.setUpgrade(upgrade);
		return install;
	}
}
