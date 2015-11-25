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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;

/**
 * Interface Message Vod Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Vod extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Time runningTime; // Play시간
	private Chapter chapter; // 회차
	private VideoInfo videoInfo; // 화질별 video 정보
	private List<VideoInfo> videoInfoList; // 화질별 video 정보 List
	private VodExplain vodExplain; // vod 정보 (삭제 예정)
	private Date date; // 날짜 정보
	private List<Identifier> identifierList; // 상품의 에피소드 ID 리스트
	/**
	 * 지원 >play >store.
	 */
	private List<Support> supportList;
	
	/**
	 * Vod().
	 */
	public Vod() {
		super();
	}

	/**
	 * 
	 * @param runningTime
	 *            runningTime
	 * @param videoInfo
	 *            videoInfo
	 */
	public Vod(Time runningTime, VideoInfo videoInfo) {
		super();
		this.runningTime = runningTime;
		this.videoInfo = videoInfo;
	}

	/**
	 * 
	 * @param runningTime
	 *            runningTime
	 * @param videoInfoList
	 *            videoInfoList
	 */
	public Vod(Time runningTime, List<VideoInfo> videoInfoList) {
		super();
		this.runningTime = runningTime;
		this.videoInfoList = videoInfoList;
	}

	/**
	 * @return Time
	 */
	public Time getRunningTime() {
		return this.runningTime;
	}

	/**
	 * @param runningTime
	 *            runningTime
	 */
	public void setRunningTime(Time runningTime) {
		this.runningTime = runningTime;
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
	 * 
	 * <pre>
	 * 화질별 video 정보 List.
	 * </pre>
	 * 
	 * @return VideoInfoList
	 */
	public List<VideoInfo> getVideoInfoList() {
		return this.videoInfoList;
	}

	/**
	 * 
	 * <pre>
	 * 화질별 video 정보 List.
	 * </pre>
	 * 
	 * @param videoInfoList
	 *            videoInfoList
	 */
	public void setVideoInfoList(List<VideoInfo> videoInfoList) {
		this.videoInfoList = videoInfoList;
	}

	/**
	 * 
	 * <pre>
	 * vod 정보.
	 * </pre>
	 * 
	 * @return VodExplain
	 */
	public VodExplain getVodExplain() {
		return this.vodExplain;
	}

	/**
	 * 
	 * <pre>
	 * vod 정보.
	 * </pre>
	 * 
	 * @param vodExplain
	 *            vodExplain
	 */
	public void setVodExplain(VodExplain vodExplain) {
		this.vodExplain = vodExplain;
	}

	/**
	 * 
	 * <pre>
	 * 날짜 정보.
	 * </pre>
	 * 
	 * @return Date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * 
	 * <pre>
	 * 날짜 정보.
	 * </pre>
	 * 
	 * @param date
	 *            Date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public List<Support> getSupportList() {
		return supportList;
	}

	public void setSupportList(List<Support> supportList) {
		this.supportList = supportList;
	}

	/**
	 * @return the identifierList
	 */
	public List<Identifier> getIdentifierList() {
		return identifierList;
	}

	/**
	 * @param identifierList the identifierList to set
	 */
	public void setIdentifierList(List<Identifier> identifierList) {
		this.identifierList = identifierList;
	}
	
}
