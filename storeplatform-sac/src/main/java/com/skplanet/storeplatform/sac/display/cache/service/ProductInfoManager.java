/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

import java.util.List;

import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.EbookComicMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.EbookComicMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.FreepassMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.FreepassMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.MusicMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.MusicMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductStats;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductStatsParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ShoppingMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.ShoppingMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.SubContent;
import com.skplanet.storeplatform.sac.display.cache.vo.SubContentParam;
import com.skplanet.storeplatform.sac.display.cache.vo.VodMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.VodMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.VoucherMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.VoucherMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.WebtoonMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.WebtoonMetaParam;

/**
 * <p>
 * 캐쉬용 메타 데이터 조회 서비스
 * </p>
 * Updated on : 2014. 03. 03 Updated by : 정희원, SK 플래닛.
 */
public interface ProductInfoManager {

	/**
	 * 앱 메타정보 조회
	 * 
	 * @param param
	 * @return
	 */
	AppMeta getAppMeta(AppMetaParam param);

	List<AppMeta> getAppMetaList(String langCd, String tenantId, List<String> prodIdList, String deviceModelCd);

	/**
	 * 음악 메타 정보 조회
	 * @param param
	 * @return
	 */
	MusicMeta getMusicMeta(MusicMetaParam param);

	List<MusicMeta> getMusicMetaList(String langCd, String tenantId, String chartClsfCd, String stdDt, List<String> prodIdList);

	VodMeta getVodMeta(VodMetaParam param);

	EbookComicMeta getEbookComicMeta(EbookComicMetaParam param);

	AlbumMeta getAlbumMeta(AlbumMetaParam param, boolean useCache);

	/**
	 * 쇼핑상품 조회
	 * @param param
	 * @return
	 */
	ShoppingMeta getShoppingMeta(ShoppingMetaParam param);

	/**
	 * 정액권 상품 조회
	 * @param param
	 * @return
	 */
	FreepassMeta getFreepassMeta(FreepassMetaParam param);

	/**
	 * 웹툰 상품 조회
	 * @param param
	 * @return
	 */
	WebtoonMeta getWebtoonMeta(WebtoonMetaParam param);

	/**
	 * 서브컨텐트 정보를 조회한다. APP상품에서 이용한다.
	 * @param param
	 * @return
	 */
	SubContent getSubContent(SubContentParam param);

	/**
	 * 메뉴 정보를 조회한다.
	 * @param param
	 * @return
	 */
	MenuInfo getMenuInfo(MenuInfoParam param);

	/**
	 * 상품 통계(참여자 수, 구매건수, 평점)을 조회한다.
	 * @param param
	 * @return
	 */
	ProductStats getProductStats(ProductStatsParam param);

	/**
	 * 이용권 상품 조회
	 * 
	 * @param param
	 * @return
	 */
	VoucherMeta getVoucherMeta(VoucherMetaParam param);

}
