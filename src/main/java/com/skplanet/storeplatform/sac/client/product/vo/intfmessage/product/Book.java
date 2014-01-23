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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Count;

/**
 * Interface Message Book Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Book extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 타입(연재물일 경우만 정의한다. > serial: 연재물
	 */
	private String type;

	/*
	 * 업데이트 주기
	 */
	private String updateCycle;

	/*
	 * 전체 페이지수
	 */
	private String totalPages;

	/*
	 * 버전
	 */
	private String bookVersion;

	/*
	 * Sub Contents ID
	 */
	private String scid;

	/*
	 * 용량 (byte 단위)
	 */
	private int size;

	/*
	 * 회차 정보
	 */
	private Chapter chapter;
	/**
	 *
	 */
	private String saleDate;

	/*
	 * 정기 구독수
	 */
	private Count subscriptionCount;

	/*
	 * 채널에 속해 있는 전체 책 권수
	 */
	private Count channelCount;

	/*
	 * 상태 > continue
	 */
	private String status;

	/*
	 * 지원 >play >store
	 */
	private List<Support> supportList;

	private int totalCount;

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
	public String getUpdateCycle() {
		return this.updateCycle;
	}

	/**
	 * @param updateCycle
	 *            updateCycle
	 */
	public void setUpdateCycle(String updateCycle) {
		this.updateCycle = updateCycle;
	}

	/**
	 * @return String
	 */
	public String getTotalPages() {
		return this.totalPages;
	}

	/**
	 * @param totalPages
	 *            totalPages
	 */
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return String
	 */
	public String getBookVersion() {
		return this.bookVersion;
	}

	/**
	 * @param bookVersion
	 *            bookVersion
	 */
	public void setBookVersion(String bookVersion) {
		this.bookVersion = bookVersion;
	}

	/**
	 * @return String
	 */
	public String getScid() {
		return this.scid;
	}

	/**
	 * @param scid
	 *            scid
	 */
	public void setScid(String scid) {
		this.scid = scid;
	}

	/**
	 * @return int
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * @param size
	 *            size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return Chapter
	 */
	public Chapter getChapter() {
		return this.chapter;
	}

	/**
	 * @param chapter
	 *            chapter
	 */
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	/**
	 * @return String
	 */
	public String getSaleDate() {
		return this.saleDate;
	}

	/**
	 * @param saleDate
	 *            saleDate
	 */
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	/**
	 * @return Count
	 */
	public Count getSubscriptionCount() {
		return this.subscriptionCount;
	}

	/**
	 * @param subscriptionCount
	 *            subscriptionCount
	 */
	public void setSubscriptionCount(Count subscriptionCount) {
		this.subscriptionCount = subscriptionCount;
	}

	/**
	 * @return Count
	 */
	public Count getChannelCount() {
		return this.channelCount;
	}

	/**
	 * @param channelCount
	 *            channelCount
	 */
	public void setChannelCount(Count channelCount) {
		this.channelCount = channelCount;
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
	 * @return List<Support>
	 */
	public List<Support> getSupportList() {
		return this.supportList;
	}

	/**
	 * @param supportList
	 *            supportList
	 */
	public void setSupportList(List<Support> supportList) {
		this.supportList = supportList;
	}

	/**
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount
	 *            totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
