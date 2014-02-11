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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message App Value Object.
 * 
 * Updated on : 2014.02. 10. Updated by : 유시혁, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Appzine extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String appzineNumber; // 앱진_번호
	private String appzineVol; // 앱진_호수
	private List<Date> dateList; // 발행일, 등록일
	private Title title; // 제목
	private String backgroundImagePath; // 배경_이미지_경로
	private String expoYn; // 노출_여부
	private String popularTitleImage480; // 인기_타이틀_이미지_480(인기 TOP 10)
	private String popularTitleImage800; // 인기_타이틀_이미지_800(인기 TOP 10)
	private String newTitleImage480; // NEW_TITL_IMG_480
	private String newTitleImage800; // NEW_TITL_IMG_800
	private String eventName; // 이벤트_명
	private String eventUrl; // 이벤트_URL
	private String eventImage480; // 이벤트_이미지_480
	private String eventImage880; // 이벤트_이미지_800
	private String titleImage; // 제목_이미지
	private String themeUpImage; // 테마_상단_이미지
	private String seq; // 앱_순서
	private String appType; // 앱 TYPE(인기:001, 신규:002, 테마:003)
	private String appOrder; // 앱_순서
	private String contentsTitle; // 컨텐츠_타이틀
	private String appProductId; // 앱_PID
	private String appUrl; // 앱_URL
	private String image480; // 이미지_480
	private String image800; // 이미지_800

	public String getAppzineNumber() {
		return this.appzineNumber;
	}

	public void setAppzineNumber(String appzineNumber) {
		this.appzineNumber = appzineNumber;
	}

	public String getAppzineVol() {
		return this.appzineVol;
	}

	public void setAppzineVol(String appzineVol) {
		this.appzineVol = appzineVol;
	}

	public List<Date> getDateList() {
		return this.dateList;
	}

	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getBackgroundImagePath() {
		return this.backgroundImagePath;
	}

	public void setBackgroundImagePath(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
	}

	public String getExpoYn() {
		return this.expoYn;
	}

	public void setExpoYn(String expoYn) {
		this.expoYn = expoYn;
	}

	public String getPopularTitleImage480() {
		return this.popularTitleImage480;
	}

	public void setPopularTitleImage480(String popularTitleImage480) {
		this.popularTitleImage480 = popularTitleImage480;
	}

	public String getPopularTitleImage800() {
		return this.popularTitleImage800;
	}

	public void setPopularTitleImage800(String popularTitleImage800) {
		this.popularTitleImage800 = popularTitleImage800;
	}

	public String getNewTitleImage480() {
		return this.newTitleImage480;
	}

	public void setNewTitleImage480(String newTitleImage480) {
		this.newTitleImage480 = newTitleImage480;
	}

	public String getNewTitleImage800() {
		return this.newTitleImage800;
	}

	public void setNewTitleImage800(String newTitleImage800) {
		this.newTitleImage800 = newTitleImage800;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventUrl() {
		return this.eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	public String getEventImage480() {
		return this.eventImage480;
	}

	public void setEventImage480(String eventImage480) {
		this.eventImage480 = eventImage480;
	}

	public String getEventImage880() {
		return this.eventImage880;
	}

	public void setEventImage880(String eventImage880) {
		this.eventImage880 = eventImage880;
	}

	public String getTitleImage() {
		return this.titleImage;
	}

	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}

	public String getThemeUpImage() {
		return this.themeUpImage;
	}

	public void setThemeUpImage(String themeUpImage) {
		this.themeUpImage = themeUpImage;
	}

	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppOrder() {
		return this.appOrder;
	}

	public void setAppOrder(String appOrder) {
		this.appOrder = appOrder;
	}

	public String getContentsTitle() {
		return this.contentsTitle;
	}

	public void setContentsTitle(String contentsTitle) {
		this.contentsTitle = contentsTitle;
	}

	public String getAppProductId() {
		return this.appProductId;
	}

	public void setAppProductId(String appProductId) {
		this.appProductId = appProductId;
	}

	public String getAppUrl() {
		return this.appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getImage480() {
		return this.image480;
	}

	public void setImage480(String image480) {
		this.image480 = image480;
	}

	public String getImage800() {
		return this.image800;
	}

	public void setImage800(String image800) {
		this.image800 = image800;
	}

}
