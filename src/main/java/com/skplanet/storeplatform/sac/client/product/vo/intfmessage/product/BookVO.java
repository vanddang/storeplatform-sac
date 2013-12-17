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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.BookProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CountVO;

/**
 * Interface Message Book Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(BookProto.Book.class)
public class BookVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 타입(연재물일 경우만 정의한다. > serial: 연재물
	 */
	private String type;

	/**
	 * 업데이트 주기
	 */
	private String updateCycle;
	/**
	 * 전체 페이지수
	 */
	private String totalPages;
	/**
	 * 버전
	 */
	private String bookVersion;
	/**
	 * Sub Contents ID
	 */
	private String scid;
	/**
	 * 용량 (byte 단위)
	 */
	private int size;
	/**
	 * 회차 정보
	 */
	private ChapterVO chapter;
	/**
	 * 
	 */
	private String saleDate;
	/**
	 * 정기 구독수
	 */
	private CountVO subscriptionCount;
	/**
	 * 채널에 속해 있는 전체 책 권수
	 */
	private CountVO channelCount;
	/**
	 * 상태 > continue
	 */
	private String status;

	/**
	 * 지원 >play >store
	 */
	private String support;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdateCycle() {
		return this.updateCycle;
	}

	public void setUpdateCycle(String updateCycle) {
		this.updateCycle = updateCycle;
	}

	public String getTotalPages() {
		return this.totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public String getBookVersion() {
		return this.bookVersion;
	}

	public void setBookVersion(String bookVersion) {
		this.bookVersion = bookVersion;
	}

	public String getScid() {
		return this.scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ChapterVO getChapter() {
		return this.chapter;
	}

	public void setChapter(ChapterVO chapter) {
		this.chapter = chapter;
	}

	public String getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public CountVO getSubscriptionCount() {
		return this.subscriptionCount;
	}

	public void setSubscriptionCount(CountVO subscriptionCount) {
		this.subscriptionCount = subscriptionCount;
	}

	public CountVO getChannelCount() {
		return this.channelCount;
	}

	public void setChannelCount(CountVO channelCount) {
		this.channelCount = channelCount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSupport() {
		return this.support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

}
