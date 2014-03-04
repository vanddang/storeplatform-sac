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

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Install;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * App 상품 전용 정보 Generator
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
public interface AppInfoGenerator {
	/**
	 * <pre>
	 * App 상품 전용 Menu List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Menu>
	 */
	public List<Menu> generateMenuList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * App 상품 전용 Menu List 생성.
	 * </pre>
	 * 
	 * @param topMenuId
	 *            topMenuId
	 * @param topMenuNm
	 *            topMenuNm
	 * @param menuId
	 *            menuId
	 * @param menuNm
	 *            menuNm
	 * @return
	 */
	public List<Menu> generateMenuList(String topMenuId, String topMenuNm, String menuId, String menuNm);

	/**
	 * <pre>
	 * App 상품 전용 Support List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Support>
	 */
	public List<Support> generateSupportList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * App 상품 전용 App 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return App
	 */
	public App generateApp(MetaInfo metaInfo);

	/**
	 * <pre>
	 * App 상품 전용 App 객체 생성.
	 * </pre>
	 * 
	 * @param aid
	 *            aid
	 * @param packageNm
	 *            packageNm
	 * @param apkVer
	 *            apkVer
	 * @param prodVer
	 *            prodVer
	 * @param fileSize
	 *            fileSize
	 * @param supportOs
	 *            supportOs
	 * @param scid
	 *            scid
	 * @param filePath
	 *            filePath
	 * @return App
	 */
	public App generateApp(String aid, String packageNm, String apkVer, String prodVer, Integer fileSize,
			String supportOs, String scid, String filePath);

	/**
	 * <pre>
	 * App 상품 전용 Identifier 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Identifier>
	 */
	public List<Identifier> generateIdentifierList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * App 특정 상품 전용 Identifier 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Identifier>
	 */
	public List<Identifier> generateSpecificIdentifierList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * App 상품 전용 Identifier List 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param text
	 *            text
	 * @return List<Identifier>
	 */
	public List<Identifier> generateIdentifierList(String type, String text);

	/**
	 * <pre>
	 * Component 전용 Identifier 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Identifier>
	 */
	public List<Identifier> generateComponentIdentifierList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Update 객체 생성.
	 * </pre>
	 * 
	 * @param date
	 *            date
	 * @param updateExplain
	 *            updateExplain
	 * @return Update
	 */
	public Update generateUpdate(Date date, String updateExplain);

	/**
	 * <pre>
	 * Isntall 객체 생성.
	 * </pre>
	 * 
	 * @param caller
	 *            caller
	 * @param upgrade
	 *            upgrade
	 * @return Install
	 */
	public Install generateInstall(String caller, String upgrade);
}
