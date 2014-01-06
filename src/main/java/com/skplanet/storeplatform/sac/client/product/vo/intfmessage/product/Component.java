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
	/**
	 * 상품 ID
	 */
	private Identifier identifier;
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
	private Title title;
	/**
	 * 가격정보
	 */
	private Price price;
	/**
	 * > Code : 대분류, 소분류 카테고리코드 > name : shoppingStore > type(option) : 대분류 카테고리시 topClass > categoryExplian : 카테고리 설명
	 */
	private Menu menuList;
	/**
	 * 패킷과금 타입 > paid : 일반 과금상품(PD000601) > library : 라이브러리(PD000602) > vm : VM(PD000603) > free : 일반 비과금상품(PD000604) >
	 * paid-rss : RSS 과금상품(PD000605) > free-rss : RSS 비과금상품(PD000606)
	 */
	private String packetFee;
	/**
	 * resource 정보
	 */
	private Source source;
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
	private Accrual accrual;
	/**
	 * 권한 정보
	 */
	private Rights rights;
	/**
	 * 앱 정보
	 */
	private App app;
	/**
	 * 음악 부가 정보
	 */
	private Music music;
	/**
	 * VOD 부가 정보
	 */
	private Vod vod;
	/**
	 * VOD일경우 VOD 부가정보
	 */
	private VideoInfo videoInfo;

	/**
	 * ebook,Comic일 경우 부가정보
	 */
	private Book book;
	/**
	 * 이벤트 상품 정보
	 */
	private Promotion promotion;
	/**
	 * Contributor message
	 */
	private Contributor contributor;

	/**
	 * distributor message
	 */
	private Distributor distributor;

	/**
	 * 기본URL
	 */
	private String base;

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Identifier identifier) {
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

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Menu getMenuList() {
		return this.menuList;
	}

	public void setMenuList(Menu menuList) {
		this.menuList = menuList;
	}

	public String getPacketFee() {
		return this.packetFee;
	}

	public void setPacketFee(String packetFee) {
		this.packetFee = packetFee;
	}

	public Source getSource() {
		return this.source;
	}

	public void setSource(Source source) {
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

	public Accrual getAccrual() {
		return this.accrual;
	}

	public void setAccrual(Accrual accrual) {
		this.accrual = accrual;
	}

	public Rights getRights() {
		return this.rights;
	}

	public void setRights(Rights rights) {
		this.rights = rights;
	}

	public App getApp() {
		return this.app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Music getMusic() {
		return this.music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public Vod getVod() {
		return this.vod;
	}

	public void setVod(Vod vod) {
		this.vod = vod;
	}

	public VideoInfo getVideoInfo() {
		return this.videoInfo;
	}

	public void setVideoInfo(VideoInfo videoInfo) {
		this.videoInfo = videoInfo;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Promotion getPromotion() {
		return this.promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public Contributor getContributor() {
		return this.contributor;
	}

	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}

	public Distributor getDistributor() {
		return this.distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}
}
