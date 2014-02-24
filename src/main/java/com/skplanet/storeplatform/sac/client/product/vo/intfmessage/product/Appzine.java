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
 * Updated on : 2014.02. 10. Updated by : 유시혁.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Appzine extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String appzineNumber;
	private String appzineVol;
	private List<Date> dateList;
	private Title title;
	private String backgroundImagePath;
	private String themeHtml;
	private String expoYn;
	private String popularTitleImage480;
	private String popularTitleImage800;
	private String newTitleImage480;
	private String newTitleImage800;
	private String eventName;
	private String eventUrl;
	private String eventImage480;
	private String eventImage800;
	private String titleImage;
	private String themeUpImage;
	private String seq;
	private String appType;
	private String appOrder;
	private String contentsTitle;
	private String appProductId;
	private String appUrl;
	private String image480;
	private String image800;

	/**
	 * 
	 * <pre>
	 * 앱진_번호.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppzineNumber() {
		return this.appzineNumber;
	}

	/**
	 * 
	 * <pre>
	 * 앱진_번호.
	 * </pre>
	 * 
	 * @param appzineNumber
	 *            String
	 */
	public void setAppzineNumber(String appzineNumber) {
		this.appzineNumber = appzineNumber;
	}

	/**
	 * 
	 * <pre>
	 * 앱진_호수.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppzineVol() {
		return this.appzineVol;
	}

	/**
	 * 
	 * <pre>
	 * 앱진_호수.
	 * </pre>
	 * 
	 * @param appzineVol
	 *            String
	 */
	public void setAppzineVol(String appzineVol) {
		this.appzineVol = appzineVol;
	}

	/**
	 * 
	 * <pre>
	 * 발행일, 등록일.
	 * </pre>
	 * 
	 * @return List<Date>
	 */
	public List<Date> getDateList() {
		return this.dateList;
	}

	/**
	 * 
	 * <pre>
	 * 발행일, 등록일.
	 * </pre>
	 * 
	 * @param dateList
	 *            List<Date>
	 */
	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	/**
	 * 
	 * <pre>
	 * 제목.
	 * </pre>
	 * 
	 * @return Title
	 */
	public Title getTitle() {
		return this.title;
	}

	/**
	 * 
	 * <pre>
	 * 제목.
	 * </pre>
	 * 
	 * @param title
	 *            Title
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * 
	 * <pre>
	 * 배경_이미지_경로.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBackgroundImagePath() {
		return this.backgroundImagePath;
	}

	/**
	 * 
	 * <pre>
	 * 배경_이미지_경로.
	 * </pre>
	 * 
	 * @param backgroundImagePath
	 *            String
	 */
	public void setBackgroundImagePath(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
	}

	/**
	 * 
	 * <pre>
	 * 테마_HTML.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getThemeHtml() {
		return this.themeHtml;
	}

	/**
	 * 
	 * <pre>
	 * 테마_HTML.
	 * </pre>
	 * 
	 * @param themeHtml
	 *            String
	 */
	public void setThemeHtml(String themeHtml) {
		this.themeHtml = themeHtml;
	}

	/**
	 * 
	 * <pre>
	 * 노출_여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getExpoYn() {
		return this.expoYn;
	}

	/**
	 * 
	 * <pre>
	 * 노출_여부.
	 * </pre>
	 * 
	 * @param expoYn
	 *            String
	 */
	public void setExpoYn(String expoYn) {
		this.expoYn = expoYn;
	}

	/**
	 * 
	 * <pre>
	 * 인기_타이틀_이미지_480(인기 TOP 10).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPopularTitleImage480() {
		return this.popularTitleImage480;
	}

	/**
	 * 
	 * <pre>
	 * 인기_타이틀_이미지_480(인기 TOP 10).
	 * </pre>
	 * 
	 * @param popularTitleImage480
	 *            String
	 */
	public void setPopularTitleImage480(String popularTitleImage480) {
		this.popularTitleImage480 = popularTitleImage480;
	}

	/**
	 * 
	 * <pre>
	 * 인기_타이틀_이미지_800(인기 TOP 10).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPopularTitleImage800() {
		return this.popularTitleImage800;
	}

	/**
	 * 
	 * <pre>
	 * 인기_타이틀_이미지_800(인기 TOP 10).
	 * </pre>
	 * 
	 * @param popularTitleImage800
	 *            String
	 */
	public void setPopularTitleImage800(String popularTitleImage800) {
		this.popularTitleImage800 = popularTitleImage800;
	}

	/**
	 * 
	 * <pre>
	 * NEW_TITL_IMG_480.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNewTitleImage480() {
		return this.newTitleImage480;
	}

	/**
	 * 
	 * <pre>
	 * NEW_TITL_IMG_480.
	 * </pre>
	 * 
	 * @param newTitleImage480
	 *            String
	 */
	public void setNewTitleImage480(String newTitleImage480) {
		this.newTitleImage480 = newTitleImage480;
	}

	/**
	 * 
	 * <pre>
	 * NEW_TITL_IMG_800.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getNewTitleImage800() {
		return this.newTitleImage800;
	}

	/**
	 * 
	 * <pre>
	 * NEW_TITL_IMG_800.
	 * </pre>
	 * 
	 * @param newTitleImage800
	 *            String
	 */
	public void setNewTitleImage800(String newTitleImage800) {
		this.newTitleImage800 = newTitleImage800;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEventName() {
		return this.eventName;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_명.
	 * </pre>
	 * 
	 * @param eventName
	 *            String
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_URL.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEventUrl() {
		return this.eventUrl;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_URL.
	 * </pre>
	 * 
	 * @param eventUrl
	 *            String
	 */
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_이미지_480.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEventImage480() {
		return this.eventImage480;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_이미지_480.
	 * </pre>
	 * 
	 * @param eventImage480
	 *            String
	 */
	public void setEventImage480(String eventImage480) {
		this.eventImage480 = eventImage480;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_이미지_800.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getEventImage800() {
		return this.eventImage800;
	}

	/**
	 * 
	 * <pre>
	 * 이벤트_이미지_800.
	 * </pre>
	 * 
	 * @param eventImage800
	 *            String
	 */
	public void setEventImage800(String eventImage800) {
		this.eventImage800 = eventImage800;
	}

	/**
	 * 
	 * <pre>
	 * 제목_이미지.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getTitleImage() {
		return this.titleImage;
	}

	/**
	 * 
	 * <pre>
	 * 제목_이미지.
	 * </pre>
	 * 
	 * @param titleImage
	 *            String
	 */
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}

	/**
	 * 
	 * <pre>
	 * 테마_상단_이미지.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getThemeUpImage() {
		return this.themeUpImage;
	}

	/**
	 * 
	 * <pre>
	 * 테마_상단_이미지.
	 * </pre>
	 * 
	 * @param themeUpImage
	 *            String
	 */
	public void setThemeUpImage(String themeUpImage) {
		this.themeUpImage = themeUpImage;
	}

	/**
	 * 
	 * <pre>
	 * 앱_순서.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getSeq() {
		return this.seq;
	}

	/**
	 * 
	 * <pre>
	 * 앱_순서.
	 * </pre>
	 * 
	 * @param seq
	 *            String
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	/**
	 * 
	 * <pre>
	 * 앱 TYPE(인기:001, 신규:002, 테마:003).
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppType() {
		return this.appType;
	}

	/**
	 * 
	 * <pre>
	 * 앱 TYPE(인기:001, 신규:002, 테마:003).
	 * </pre>
	 * 
	 * @param appType
	 *            String
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * 
	 * <pre>
	 * 앱_순서.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppOrder() {
		return this.appOrder;
	}

	/**
	 * 
	 * <pre>
	 * 앱_순서.
	 * </pre>
	 * 
	 * @param appOrder
	 *            String
	 */
	public void setAppOrder(String appOrder) {
		this.appOrder = appOrder;
	}

	/**
	 * 
	 * <pre>
	 * 컨텐츠_타이틀.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getContentsTitle() {
		return this.contentsTitle;
	}

	/**
	 * 
	 * <pre>
	 * 컨텐츠_타이틀.
	 * </pre>
	 * 
	 * @param contentsTitle
	 *            String
	 */
	public void setContentsTitle(String contentsTitle) {
		this.contentsTitle = contentsTitle;
	}

	/**
	 * 
	 * <pre>
	 * 앱_PID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppProductId() {
		return this.appProductId;
	}

	/**
	 * 
	 * <pre>
	 * 앱_PID.
	 * </pre>
	 * 
	 * @param appProductId
	 *            String
	 */
	public void setAppProductId(String appProductId) {
		this.appProductId = appProductId;
	}

	/**
	 * 
	 * <pre>
	 * 앱_URL.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getAppUrl() {
		return this.appUrl;
	}

	/**
	 * 
	 * <pre>
	 * 앱_URL.
	 * </pre>
	 * 
	 * @param appUrl
	 *            String
	 */
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	/**
	 * 
	 * <pre>
	 * 이미지_480.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImage480() {
		return this.image480;
	}

	/**
	 * 
	 * <pre>
	 * 이미지_480.
	 * </pre>
	 * 
	 * @param image480
	 *            String
	 */
	public void setImage480(String image480) {
		this.image480 = image480;
	}

	/**
	 * 
	 * <pre>
	 * 이미지_800.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImage800() {
		return this.image800;
	}

	/**
	 * 
	 * <pre>
	 * 이미지_800.
	 * </pre>
	 * 
	 * @param image800
	 *            String
	 */
	public void setImage800(String image800) {
		this.image800 = image800;
	}

}
