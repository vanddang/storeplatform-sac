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
	private Long size;
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
     * 도서 구분명
     */
    private String bookType;

	/**
	 * 물리파일경로.
	 */
	private String filePath;

	/**
	 * 인터파크복호화키.
	 */
	private String bpJoinFileNo;

	/**
	 * 국제표준도서번호.
	 */
	private String isbn;

	/**
	 * 잡지표지여부.
	 */
	private String mgzinCoverYn;

	private String mallCd;

	/** 
	 * freeItem 정보를 위한 건수 조회 
	 */
	private Integer bookCount;
	private Integer bookFreeCount;
	private Integer serialCount;
	private Integer serialFreeCount;
	private Integer magazineCount;
	private Integer magazineFreeCount;
	
	/**
	 * 최종 챕터 정보
	 */
	private Integer bookLastChapter;
	private Integer serialLastChapter;
	private Integer magazineLastChapter;
	
	
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
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

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the bpJoinFileNo
	 */
	public String getBpJoinFileNo() {
		return this.bpJoinFileNo;
	}

	/**
	 * @param bpJoinFileNo
	 *            the bpJoinFileNo to set
	 */
	public void setBpJoinFileNo(String bpJoinFileNo) {
		this.bpJoinFileNo = bpJoinFileNo;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return this.isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the mgzinCoverYn
	 */
	public String getMgzinCoverYn() {
		return this.mgzinCoverYn;
	}

	/**
	 * @param mgzinCoverYn
	 *            the mgzinCoverYn to set
	 */
	public void setMgzinCoverYn(String mgzinCoverYn) {
		this.mgzinCoverYn = mgzinCoverYn;
	}

	/**
	 * @return the mallCd
	 */
	public String getMallCd() {
		return this.mallCd;
	}

	/**
	 * @param mallCd
	 *            the mallCd to set
	 */
	public void setMallCd(String mallCd) {
		this.mallCd = mallCd;
	}

	/**
	 * @return the bookCount
	 */
	public Integer getBookCount() {
		return bookCount;
	}

	/**
	 * @param bookCount the bookCount to set
	 */
	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}

	/**
	 * @return the bookFreeCount
	 */
	public Integer getBookFreeCount() {
		return bookFreeCount;
	}

	/**
	 * @param bookFreeCount the bookFreeCount to set
	 */
	public void setBookFreeCount(Integer bookFreeCount) {
		this.bookFreeCount = bookFreeCount;
	}

	/**
	 * @return the serialCount
	 */
	public Integer getSerialCount() {
		return serialCount;
	}

	/**
	 * @param serialCount the serialCount to set
	 */
	public void setSerialCount(Integer serialCount) {
		this.serialCount = serialCount;
	}

	/**
	 * @return the serialFreeCount
	 */
	public Integer getSerialFreeCount() {
		return serialFreeCount;
	}

	/**
	 * @param serialFreeCount the serialFreeCount to set
	 */
	public void setSerialFreeCount(Integer serialFreeCount) {
		this.serialFreeCount = serialFreeCount;
	}

	/**
	 * @return the magazineCount
	 */
	public Integer getMagazineCount() {
		return magazineCount;
	}

	/**
	 * @param magazineCount the magazineCount to set
	 */
	public void setMagazineCount(Integer magazineCount) {
		this.magazineCount = magazineCount;
	}

	/**
	 * @return the magazineFreeCount
	 */
	public Integer getMagazineFreeCount() {
		return magazineFreeCount;
	}

	/**
	 * @param magazineFreeCount the magazineFreeCount to set
	 */
	public void setMagazineFreeCount(Integer magazineFreeCount) {
		this.magazineFreeCount = magazineFreeCount;
	}

	public Integer getBookLastChapter() {
		return bookLastChapter;
	}

	public void setBookLastChapter(Integer bookLastChapter) {
		this.bookLastChapter = bookLastChapter;
	}

	public Integer getSerialLastChapter() {
		return serialLastChapter;
	}

	public void setSerialLastChapter(Integer serialLastChapter) {
		this.serialLastChapter = serialLastChapter;
	}

	public Integer getMagazineLastChapter() {
		return magazineLastChapter;
	}

	public void setMagazineLastChapter(Integer magazineLastChapter) {
		this.magazineLastChapter = magazineLastChapter;
	}

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }
}
