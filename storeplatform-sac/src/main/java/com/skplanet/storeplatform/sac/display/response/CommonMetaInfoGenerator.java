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
import java.util.Map;

import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistorySacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Authority;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Badge;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 공통 Meta 정보 Generator.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
public interface CommonMetaInfoGenerator {

	/**
	 * <pre>
	 * 공통 Identifier 객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param text
	 *            text
	 * @return Identifier
	 */
	public Identifier generateIdentifier(String type, String text);

	/**
	 * <pre>
	 * 공통 Identifier 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Identifier
	 */
	public Identifier generateIdentifier(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Menu List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Menu>
	 */
	public List<Menu> generateMenuList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Source 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Source
	 */
	public Source generateSource(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Source 객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param url
	 *            url
	 * @return Source
	 */
	public Source generateSource(String type, String url);

	/**
	 * <pre>
	 * 공통 Source 객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param url
	 *            url
	 * @param size
	 * @return Source
	 */
	public Source generateSource(String type, String url, Long size);

	/**
	 * <pre>
	 * 공통 Source 객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param url
	 *            url
	 * @param ratio
	 *            ratio
	 * @return Source
	 */
	public Source generateSource(String type, String url, String ratio);

	/**
	 * <pre>
	 * 공통 Source 객체 생성.
	 * </pre>
	 * 
	 * @param path
	 *            path
	 * @return Source
	 */
	public Source generateSource(String path);

	/**
	 * <pre>
	 * 공통 Source List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Source>
	 */
	public List<Source> generateSourceList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 VOD Source List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Source>
	 */
	public List<Source> generateVodSourceList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * for download Source List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Source>
	 */
	public List<Source> generateDownloadSourceList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 배너 Source List 객체 생성.
	 * </pre>
	 * 
	 * @param path
	 *            path
	 * @param size
	 *            size
	 * @return List<Source>
	 */
	public List<Source> generateBannerSourceList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Source List 생성.
	 * </pre>
	 * 
	 * @param mediaType
	 *            mediaType
	 * @param type
	 *            type
	 * @param url
	 *            url
	 * @return List<Source>
	 */
	public List<Source> generateSourceList(String mediaType, String type, String url);

	/**
	 * <pre>
	 * 공통 Rights 객체 생성(멀티미디어 상품은 별도).
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Rights
	 */
	public Rights generateRights(MetaInfo metaInfo);
	
	/**
	 * <pre>
	 * 공통 Authority 객체 생성(VOD 상품만 적용).
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Rights
	 */
	public Authority generateAuthority(MetaInfo metaInfo);	

	/**
	 * <pre>
	 * 공통 Support 객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param text
	 *            text
	 * @return Support
	 */
	public Support generateSupport(String type, String text);

	/**
	 * <pre>
	 * 공통 Support Map 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Map<String, Object>
	 */
	public Map<String, Object> generateSupportList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Price 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Price
	 */
	public Price generatePrice(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Price 객체 생성.
	 * </pre>
	 * 
	 * @param text
	 *            text
	 * @param fixedPrice
	 *            fixedPrice
	 * @return Price
	 */
	public Price generatePrice(Integer text, Integer fixedPrice);

	/**
	 * <pre>
	 * 공통 Accrual 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Accrual
	 */
	public Accrual generateAccrual(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Title 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Title
	 */
	public Title generateTitle(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Identifier List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Identifier>
	 */
	public List<Identifier> generateIdentifierList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Distributor 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Distributor
	 */
	public Distributor generateDistributor(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Webtoon 상품 전용 Date 객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param text
	 *            text
	 * @return Date
	 */
	public Date generateDate(String type, String text);

	/**
	 * <pre>
	 * VOD, 이북, 코믹 상품 Contributor Date객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param text
	 *            text
	 * @return Date
	 */
	public Date generateDateString(String type, String text);

	/**
	 * <pre>
	 * 소장 상품 Store 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Store
	 */
	public Store generateStore(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 대여 상품 Play 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Store
	 */
	public Play generatePlay(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 구매 정보 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Purchase
	 */
	public Purchase generatePurchase(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 구매 정보 객체 생성.
	 * </pre>
	 * 
	 * @param prchId
	 *            prchId
	 * @param prodId
	 *            prodId
	 * @param prchState
	 *            prchState
	 * @param prchDt
	 *            prchDt
	 * @param dwldExprDt
	 *            dwldExprDt
	 * @return Purchase
	 */
	public Purchase generatePurchase(String prchId, String prodId, String prchState, String prchDt, String dwldExprDt);

	/**
	 *
	 * @param prchState
	 * @param historySacIn
	 * @return
	 */
	public Purchase generatePurchase(String prchState, HistorySacIn historySacIn);

	/**
	 * <pre>
	 * 미리보기 Source List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Source>
	 */
	public Source generatePreviewSourceList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Url 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Url>
	 */
	public Url generateUrl(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Url 객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param text
	 *            text
	 * @return Url
	 */
	public Url generateUrl(String type, String text);

	/**
	 * <pre>
	 * 공통 Url 객체 생성.
	 * </pre>
	 * 
	 * @param text
	 *            text
	 * @return Url
	 */
	public Url generateUrl(String text);

	/**
	 * <pre>
	 * 공통 Url 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return Url
	 */
	public List<Url> generateUrlList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 공통 Url 객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param text
	 *            text
	 * @return Url
	 */
	public List<Url> generateUrlList(String type, String text);

	/**
	 * <pre>
	 * 공통 tmembership 포인트.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Point
	 */
	public List<Point> generatePoint(TmembershipDcInfo metaInfo);

	/**
	 * 마일리지 객체 생성
	 * 
	 * @param mileageInfo
	 * @return
	 */
	public List<Point> generateMileage(MileageInfo mileageInfo);

	/**
	 * 회원 등급별 마일리지 객체 생성
	 * 
	 * @param mileageInfo
	 * @param userGrade
	 * @return
	 */
	public List<Point> generateMileage(MileageInfo mileageInfo, String userGrade);

	/**
	 * Badge 정보
	 * 
	 * @param metaInfo
	 * @return
	 */
	public Badge generateBadge(MetaInfo metaInfo);

	/**
	 * Date타입에 날짜 기간 데이터를 정의.
	 * 
	 * @param tp
	 * @param period
	 * @param unit
	 * @return
	 */
	public Date generateDateUsagePeriod(String tp, String period, String unit);

}
