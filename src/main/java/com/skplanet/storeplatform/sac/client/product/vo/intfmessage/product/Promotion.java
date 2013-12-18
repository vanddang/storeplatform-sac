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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.PromotionProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;

/**
 * Interface Message Promotion Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(PromotionProto.Promotion.class)
public class Promotion extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * 이벤트명 (clover: 네잎 클로버, offering: 결제완료 오퍼링, search: 검색어 입력 창에 노출되는 문구)
	 */
	private String name;
	private Identifier identifier; // 오퍼링ID
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
	private Source source; // promotion resource 정보
	private Url url; // externalUrl

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOffering() {
		return this.offering;
	}

	public void setOffering(String offering) {
		this.offering = offering;
	}

	public String getRef() {
		return this.ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getOfferingExplain() {
		return this.offeringExplain;
	}

	public void setOfferingExplain(String offeringExplain) {
		this.offeringExplain = offeringExplain;
	}

	public String getGuideEvent() {
		return this.guideEvent;
	}

	public void setGuideEvent(String guideEvent) {
		this.guideEvent = guideEvent;
	}

	public String getGuideUsage() {
		return this.guideUsage;
	}

	public void setGuideUsage(String guideUsage) {
		this.guideUsage = guideUsage;
	}

	public String getPromotionExplain() {
		return this.promotionExplain;
	}

	public void setPromotionExplain(String promotionExplain) {
		this.promotionExplain = promotionExplain;
	}

	public String getUsagePeriod() {
		return this.usagePeriod;
	}

	public void setUsagePeriod(String usagePeriod) {
		this.usagePeriod = usagePeriod;
	}

	public String getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getGiveaway() {
		return this.giveaway;
	}

	public void setGiveaway(String giveaway) {
		this.giveaway = giveaway;
	}

	public Source getSource() {
		return this.source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Url getUrl() {
		return this.url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}
}
