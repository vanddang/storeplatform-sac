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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;

/**
 * Interface Message Promotion Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Promotion extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * 이벤트명 (clover: 네잎 클로버, offering: 결제완료 오퍼링, search: 검색어 입력 창에 노출되는 문구)
	 */
	private String name;
	private Identifier identifier; // 오퍼링ID

	private List<Identifier> identifierList;
	/*
	 * 본인/타인 구분 (self:본인, others:타인)
	 */
	private String receiver;
	/*
	 * 프로모션 타입 (free:무료, discount:할인, tcash:T store 캐쉬)
	 */
	private String type;
	/*
	 * 오퍼링 분류 (product:상품, tcash:T store 캐쉬)
	 */
	private String offering;
	/*
	 * 오퍼링 적용구분 (“APP”, “음악”, “ebook단품”, “VOD단품”인 경우 필요, “VOD”,”이북”,”코믹 시리즈”인 경우 불필요)
	 */
	private String ref;
	private Title title; // Promotion 제목 (Title name이 ”search”일 경우 검색어 입력 창에 문구가 정의된다.)
	private String offeringExplain; // 오퍼링내용
	private String guideEvent; // 이벤트 안내
	private String guideUsage; // 이용안내
	private String promotionExplain; // promotion 설명
	private String usagePeriod; // 이용기간
	private String releaseDate; // 종료일자
	private String giveaway; // 경품
	private List<Source> sourceList;
	private Source source; // promotion resource 정보
	private Url url; // externalUrl

	/**
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return String
	 */
	public String getReceiver() {
		return this.receiver;
	}

	/**
	 * @param receiver
	 *            receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return String
	 */
	public String getOffering() {
		return this.offering;
	}

	/**
	 * @param offering
	 *            offering
	 */
	public void setOffering(String offering) {
		this.offering = offering;
	}

	/**
	 * @return String
	 */
	public String getRef() {
		return this.ref;
	}

	/**
	 * @param ref
	 *            ref
	 */
	public void setRef(String ref) {
		this.ref = ref;
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
	 * @return String
	 */
	public String getOfferingExplain() {
		return this.offeringExplain;
	}

	/**
	 * @param offeringExplain
	 *            offeringExplain
	 */
	public void setOfferingExplain(String offeringExplain) {
		this.offeringExplain = offeringExplain;
	}

	/**
	 * @return String
	 */
	public String getGuideEvent() {
		return this.guideEvent;
	}

	/**
	 * @param guideEvent
	 *            guideEvent
	 */
	public void setGuideEvent(String guideEvent) {
		this.guideEvent = guideEvent;
	}

	/**
	 * @return String
	 */
	public String getGuideUsage() {
		return this.guideUsage;
	}

	/**
	 * @param guideUsage
	 *            guideUsage
	 */
	public void setGuideUsage(String guideUsage) {
		this.guideUsage = guideUsage;
	}

	/**
	 * @return String
	 */
	public String getPromotionExplain() {
		return this.promotionExplain;
	}

	/**
	 * @param promotionExplain
	 *            promotionExplain
	 */
	public void setPromotionExplain(String promotionExplain) {
		this.promotionExplain = promotionExplain;
	}

	/**
	 * @return String
	 */
	public String getUsagePeriod() {
		return this.usagePeriod;
	}

	/**
	 * @param usagePeriod
	 *            usagePeriod
	 */
	public void setUsagePeriod(String usagePeriod) {
		this.usagePeriod = usagePeriod;
	}

	/**
	 * @return String
	 */
	public String getReleaseDate() {
		return this.releaseDate;
	}

	/**
	 * @param releaseDate
	 *            releaseDate
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return String
	 */
	public String getGiveaway() {
		return this.giveaway;
	}

	/**
	 * @param giveaway
	 *            giveaway
	 */
	public void setGiveaway(String giveaway) {
		this.giveaway = giveaway;
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
	 * @return the sourceList
	 */
	public List<Source> getSourceList() {
		return this.sourceList;
	}

	/**
	 * @param sourceList
	 *            the sourceList to set
	 */
	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	/**
	 * @return Url
	 */
	public Url getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 *            url
	 */
	public void setUrl(Url url) {
		this.url = url;
	}

}
