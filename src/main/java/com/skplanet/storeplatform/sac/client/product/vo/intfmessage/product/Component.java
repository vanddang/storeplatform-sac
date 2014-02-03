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
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message Component Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Component extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * 상품 ID
	 */
	private Identifier identifier;
	private List<Identifier> identifierList;

	/**
	 * identifier 추가 기술.
	 */
	private String id;

	/**
	 * 지원 매체.
	 */
	private String support;

	/**
	 * 지원 언어.
	 */
	private String lang;
	/**
	 * 
	 */
	private String status;

	/**
	 * title Message(상품명).
	 */
	private Title title;

	/**
	 * 가격정보.
	 */
	private Price price;
	/**
	 * > Code : 대분류, 소분류 카테고리코드 > name : shoppingStore > type(option) : 대분류 카테고리시 topClass > categoryExplian : 카테고리 설명.
	 */
	private Menu menuList;
	/**
	 * 패킷과금 타입 > paid : 일반 과금상품(PD000601) > library : 라이브러리(PD000602) > vm : VM(PD000603) > free : 일반 비과금상품(PD000604) >.
	 * paid-rss : RSS 과금상품(PD000605) > free-rss : RSS 비과금상품(PD000606).
	 */
	private String packetFee;
	/**
	 * resource 정보.
	 */
	private Source source;
	/**
	 * > short : 한 줄 설명 > long : 상세 설명.
	 */
	private String requestType;

	/**
	 * 목차정보(ebook일 경우).
	 */
	private String tableOfContents;

	/**
	 * 작가소개(ebook일 경우).
	 */
	private String aboutWriter;
	/**
	 * Accrual.
	 */
	private Accrual accrual;
	/**
	 * 권한 정보.
	 */
	private Rights rights;

	/**
	 * 앱 정보.
	 */
	private App app;

	/**
	 * 음악 부가 정보.
	 */
	private Music music;

	/**
	 * VOD 부가 정보.
	 */
	private Vod vod;

	/**
	 * VOD일경우 VOD 부가정보.
	 */
	private VideoInfo videoInfo;

	/**
	 * ebook,Comic일 경우 부가정보.
	 */
	private Book book;
	/**
	 * 이벤트 상품 정보.
	 */
	private Promotion promotion;

	/**
	 * Contributor message.
	 */
	private Contributor contributor;

	/**
	 * distributor message.
	 */
	private Distributor distributor;

	/**
	 * 기본URL.
	 */
	private String base;

	/**
	 * 사용여부 (for download Seed App 정보).
	 */
	private String useYn; //

	/**
	 * 유형참조코드(for download Seed App 정보).
	 */
	private String caseRefCd; //

	private Component component;

	/**
	 * 게임센터 버전코드.
	 */
	private String gameCenterVerCd;

	/**
	 * 
	 * <pre>
	 * 사용여부 (for download Seed App 정보).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUseYn() {
		return this.useYn;
	}

	/**
	 * 
	 * <pre>
	 * 사용여부 (for download Seed App 정보).
	 * </pre>
	 * 
	 * @param useYn
	 *            useYn
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * 
	 * <pre>
	 * 유형참조코드(for download Seed App 정보).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getCaseRefCd() {
		return this.caseRefCd;
	}

	/**
	 * @return Identifier
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}

	/**
	 * @param identifier
	 *            identifier
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String
	 */
	public String getSupport() {
		return this.support;
	}

	/**
	 * @param support
	 *            support
	 */
	public void setSupport(String support) {
		this.support = support;
	}

	/**
	 * @return String
	 */
	public String getLang() {
		return this.lang;
	}

	/**
	 * @param lang
	 *            lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Title
	 */
	public Title getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            title
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * @return Price
	 */
	public Price getPrice() {
		return this.price;
	}

	/**
	 * @param price
	 *            price
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * @return Menu
	 */
	public Menu getMenuList() {
		return this.menuList;
	}

	/**
	 * @param menuList
	 *            menuList
	 */
	public void setMenuList(Menu menuList) {
		this.menuList = menuList;
	}

	/**
	 * @return String
	 */
	public String getPacketFee() {
		return this.packetFee;
	}

	/**
	 * @param packetFee
	 *            packetFee
	 */
	public void setPacketFee(String packetFee) {
		this.packetFee = packetFee;
	}

	/**
	 * @return Source
	 */
	public Source getSource() {
		return this.source;
	}

	/**
	 * @param source
	 *            source
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * @return String
	 */
	public String getRequestType() {
		return this.requestType;
	}

	/**
	 * @param requestType
	 *            requestType
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return String
	 */
	public String getTableOfContents() {
		return this.tableOfContents;
	}

	/**
	 * @param tableOfContents
	 *            tableOfContents
	 */
	public void setTableOfContents(String tableOfContents) {
		this.tableOfContents = tableOfContents;
	}

	/**
	 * @return String
	 */
	public String getAboutWriter() {
		return this.aboutWriter;
	}

	/**
	 * @param aboutWriter
	 *            aboutWriter
	 */
	public void setAboutWriter(String aboutWriter) {
		this.aboutWriter = aboutWriter;
	}

	/**
	 * @return Accrual
	 */
	public Accrual getAccrual() {
		return this.accrual;
	}

	/**
	 * @param accrual
	 *            accrual
	 */
	public void setAccrual(Accrual accrual) {
		this.accrual = accrual;
	}

	/**
	 * @return Rights
	 */
	public Rights getRights() {
		return this.rights;
	}

	/**
	 * @param rights
	 *            rights
	 */
	public void setRights(Rights rights) {
		this.rights = rights;
	}

	/**
	 * @return App
	 */
	public App getApp() {
		return this.app;
	}

	/**
	 * @param app
	 *            app
	 */
	public void setApp(App app) {
		this.app = app;
	}

	/**
	 * @return Music
	 */
	public Music getMusic() {
		return this.music;
	}

	/**
	 * @param music
	 *            music
	 */
	public void setMusic(Music music) {
		this.music = music;
	}

	/**
	 * @return Vod
	 */
	public Vod getVod() {
		return this.vod;
	}

	/**
	 * @param vod
	 *            vod
	 */
	public void setVod(Vod vod) {
		this.vod = vod;
	}

	/**
	 * @return VideoInfo
	 */
	public VideoInfo getVideoInfo() {
		return this.videoInfo;
	}

	/**
	 * @param videoInfo
	 *            videoInfo
	 */
	public void setVideoInfo(VideoInfo videoInfo) {
		this.videoInfo = videoInfo;
	}

	/**
	 * @return Book
	 */
	public Book getBook() {
		return this.book;
	}

	/**
	 * @param book
	 *            book
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * @return Promotion
	 */
	public Promotion getPromotion() {
		return this.promotion;
	}

	/**
	 * @param promotion
	 *            promotion
	 */
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	/**
	 * @return Contributor
	 */
	public Contributor getContributor() {
		return this.contributor;
	}

	/**
	 * @param contributor
	 *            contributor
	 */
	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}

	/**
	 * @return Distributor
	 */
	public Distributor getDistributor() {
		return this.distributor;
	}

	/**
	 * @param distributor
	 *            distributor
	 */
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	/**
	 * @return String
	 */
	public String getBase() {
		return this.base;
	}

	/**
	 * @param base
	 *            base
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return Component
	 */
	public Component getComponent() {
		return this.component;
	}

	/**
	 * @param component
	 *            component
	 */
	public void setComponent(Component component) {
		this.component = component;
	}

	/**
	 * 
	 * <pre>
	 * 유형참조코드(for download Seed App 정보).
	 * </pre>
	 * 
	 * @param caseRefCd
	 *            caseRefCd
	 */
	public void setCaseRefCd(String caseRefCd) {
		this.caseRefCd = caseRefCd;
	}

	/**
	 * @return the identifierList
	 */
	public List<Identifier> getIdentifierList() {
		return this.identifierList;
	}

	/**
	 * @param identifierList
	 *            the identifierList to set
	 */
	public void setIdentifierList(List<Identifier> identifierList) {
		this.identifierList = identifierList;
	}

	/**
	 * @return the gameCenterVerCd
	 */
	public String getGameCenterVerCd() {
		return this.gameCenterVerCd;
	}

	/**
	 * @param gameCenterVerCd
	 *            the gameCenterVerCd to set
	 */
	public void setGameCenterVerCd(String gameCenterVerCd) {
		this.gameCenterVerCd = gameCenterVerCd;
	}
}
