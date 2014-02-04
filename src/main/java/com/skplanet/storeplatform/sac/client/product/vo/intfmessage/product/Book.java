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

	/**
	 * 업데이트 주기.
	 */
	private String updateCycle;
	/**
	 * 전체 페이지수.
	 */
	private String totalPages;
	/**
	 * 버전.
	 */
	private String bookVersion;
	/**
	 * Sub Contents ID.
	 */
	private String scid;
	/**
	 * 용량 (byte 단위).
	 */
	private Integer size;
	/**
	 * 회차 정보.
	 */
	private Chapter chapter;
	/**
	 * 판매일.
	 */
	private String saleDate;
	/**
	 * 정기 구독수.
	 */
	private Count subscriptionCount;
	/**
	 * 채널에 속해 있는 전체 책 권수.
	 */
	private Count channelCount;
	/**
	 * 상태 > continue.
	 */
	private String status;

	/**
	 * 지원 >play >store.
	 */
	private List<Support> supportList;

	private Integer totalCount;

	/**
	 * 도서구분코드.
	 */
	private String bookClsfCd;

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the updateCycle
	 */
	public String getUpdateCycle() {
		return this.updateCycle;
	}

	/**
	 * @param updateCycle
	 *            the updateCycle to set
	 */
	public void setUpdateCycle(String updateCycle) {
		this.updateCycle = updateCycle;
	}

	/**
	 * @return the totalPages
	 */
	public String getTotalPages() {
		return this.totalPages;
	}

	/**
	 * @param totalPages
	 *            the totalPages to set
	 */
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the bookVersion
	 */
	public String getBookVersion() {
		return this.bookVersion;
	}

	/**
	 * @param bookVersion
	 *            the bookVersion to set
	 */
	public void setBookVersion(String bookVersion) {
		this.bookVersion = bookVersion;
	}

	/**
	 * @return the scid
	 */
	public String getScid() {
		return this.scid;
	}

	/**
	 * @param scid
	 *            the scid to set
	 */
	public void setScid(String scid) {
		this.scid = scid;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return this.size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the chapter
	 */
	public Chapter getChapter() {
		return this.chapter;
	}

	/**
	 * @param chapter
	 *            the chapter to set
	 */
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	/**
	 * @return the saleDate
	 */
	public String getSaleDate() {
		return this.saleDate;
	}

	/**
	 * @param saleDate
	 *            the saleDate to set
	 */
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	/**
	 * @return the subscriptionCount
	 */
	public Count getSubscriptionCount() {
		return this.subscriptionCount;
	}

	/**
	 * @param subscriptionCount
	 *            the subscriptionCount to set
	 */
	public void setSubscriptionCount(Count subscriptionCount) {
		this.subscriptionCount = subscriptionCount;
	}

	/**
	 * @return the channelCount
	 */
	public Count getChannelCount() {
		return this.channelCount;
	}

	/**
	 * @param channelCount
	 *            the channelCount to set
	 */
	public void setChannelCount(Count channelCount) {
		this.channelCount = channelCount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the supportList
	 */
	public List<Support> getSupportList() {
		return this.supportList;
	}

	/**
	 * @param supportList
	 *            the supportList to set
	 */
	public void setSupportList(List<Support> supportList) {
		this.supportList = supportList;
	}

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the bookClsfCd
	 */
	public String getBookClsfCd() {
		return this.bookClsfCd;
	}

	/**
	 * @param bookClsfCd
	 *            the bookClsfCd to set
	 */
	public void setBookClsfCd(String bookClsfCd) {
		this.bookClsfCd = bookClsfCd;
	}
}
