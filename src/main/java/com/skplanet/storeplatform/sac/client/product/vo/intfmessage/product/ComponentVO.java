/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.ComponentProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.IdentifierVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.MenuVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.PriceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.SourceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TitleVO;

/**
 * Interface Message Component Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(ComponentProto.Component.class)
public class ComponentVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 상품 ID
	 */
	private IdentifierVO identifier;
	/**
	 * identifier 추가 기술
	 */
	private String id;
	/**
	 * 지원 매체
	 */
	private String support;
	/**
	 * 지원 언어
	 */
	private String lang;
	/**
	 * 
	 */
	private String status;
	/**
	 * title Message(상품명)
	 */
	private TitleVO title;
	/**
	 * 가격정보
	 */
	private PriceVO price;
	/**
	 * > Code : 대분류, 소분류 카테고리코드 > name : shoppingStore > type(option) : 대분류 카테고리시 topClass > categoryExplian : 카테고리 설명
	 */
	private MenuVO menuList;
	/**
	 * 패킷과금 타입 > paid : 일반 과금상품(PD000601) > library : 라이브러리(PD000602) > vm : VM(PD000603) > free : 일반 비과금상품(PD000604) >
	 * paid-rss : RSS 과금상품(PD000605) > free-rss : RSS 비과금상품(PD000606)
	 */
	private String packetFee;
	/**
	 * resource 정보
	 */
	private SourceVO source;
	/**
	 * > short : 한 줄 설명 > long : 상세 설명
	 */
	private String requestType;
	/**
	 * 목차정보(ebook일 경우)
	 */
	private String tableOfContents;
	/**
	 * 작가소개(ebook일 경우)
	 */
	private String aboutWriter;
	/**
	 * Accrual
	 */
	private AccrualVO accrual;
	/**
	 * 권한 정보
	 */
	private RightsVO rights;
	/**
	 * 앱 정보
	 */
	private AppVO app;
	/**
	 * 음악 부가 정보
	 */
	private MusicVO music;
	/**
	 * VOD 부가 정보
	 */
	private VodVO vod;
	/**
	 * VOD일경우 VOD 부가정보
	 */
	private VideoInfoVO videoInfo;

	/**
	 * ebook,Comic일 경우 부가정보
	 */
	private BookVO book;
	/**
	 * 이벤트 상품 정보
	 */
	private PromotionVO promotion;
	/**
	 * Contributor message
	 */
	private ContributorVO contributor;

	/**
	 * distributor message
	 */
	private DistributorVO distributor;

	/**
	 * 기본URL
	 */
	private String base;

	public IdentifierVO getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(IdentifierVO identifier) {
		this.identifier = identifier;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupport() {
		return this.support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TitleVO getTitle() {
		return this.title;
	}

	public void setTitle(TitleVO title) {
		this.title = title;
	}

	public PriceVO getPrice() {
		return this.price;
	}

	public void setPrice(PriceVO price) {
		this.price = price;
	}

	public MenuVO getMenuList() {
		return this.menuList;
	}

	public void setMenuList(MenuVO menuList) {
		this.menuList = menuList;
	}

	public String getPacketFee() {
		return this.packetFee;
	}

	public void setPacketFee(String packetFee) {
		this.packetFee = packetFee;
	}

	public SourceVO getSource() {
		return this.source;
	}

	public void setSource(SourceVO source) {
		this.source = source;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getTableOfContents() {
		return this.tableOfContents;
	}

	public void setTableOfContents(String tableOfContents) {
		this.tableOfContents = tableOfContents;
	}

	public String getAboutWriter() {
		return this.aboutWriter;
	}

	public void setAboutWriter(String aboutWriter) {
		this.aboutWriter = aboutWriter;
	}

	public AccrualVO getAccrual() {
		return this.accrual;
	}

	public void setAccrual(AccrualVO accrual) {
		this.accrual = accrual;
	}

	public RightsVO getRights() {
		return this.rights;
	}

	public void setRights(RightsVO rights) {
		this.rights = rights;
	}

	public AppVO getApp() {
		return this.app;
	}

	public void setApp(AppVO app) {
		this.app = app;
	}

	public MusicVO getMusic() {
		return this.music;
	}

	public void setMusic(MusicVO music) {
		this.music = music;
	}

	public VodVO getVod() {
		return this.vod;
	}

	public void setVod(VodVO vod) {
		this.vod = vod;
	}

	public VideoInfoVO getVideoInfo() {
		return this.videoInfo;
	}

	public void setVideoInfo(VideoInfoVO videoInfo) {
		this.videoInfo = videoInfo;
	}

	public BookVO getBook() {
		return this.book;
	}

	public void setBook(BookVO book) {
		this.book = book;
	}

	public PromotionVO getPromotion() {
		return this.promotion;
	}

	public void setPromotion(PromotionVO promotion) {
		this.promotion = promotion;
	}

	public ContributorVO getContributor() {
		return this.contributor;
	}

	public void setContributor(ContributorVO contributor) {
		this.contributor = contributor;
	}

	public DistributorVO getDistributor() {
		return this.distributor;
	}

	public void setDistributor(DistributorVO distributor) {
		this.distributor = distributor;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}
}
