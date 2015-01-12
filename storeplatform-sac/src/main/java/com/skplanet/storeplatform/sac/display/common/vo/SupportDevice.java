/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 단말 지원정보 조회 VO
 * 
 * Updated on : 2014. 4. 1. Updated by : 이태희, 부르칸.
 */
public class SupportDevice extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String ebookSprtYn; // eBook 상품 지원여부

	private String comicSprtYn; // Comic 상품 지원여부

	private String musicSprtYn; // 음악 상품 지원여부

	private String videoDrmSprtYn; // VOD 상품 DRM 지원여부

	private String sdVideoSprtYn; // VOD 상품 SD 지원여부

	private String sclShpgSprtYn; // Tstore 쇼핑 상품 지원여부

	private String vodFixisttSprtYn; // Tstore 정액권 상품 지원여부

	/**
	 * @return the ebookSprtYn
	 */
	public String getEbookSprtYn() {
		return this.ebookSprtYn;
	}

	/**
	 * @param ebookSprtYn
	 *            the ebookSprtYn to set
	 */
	public void setEbookSprtYn(String ebookSprtYn) {
		this.ebookSprtYn = ebookSprtYn;
	}

	/**
	 * @return the comicSprtYn
	 */
	public String getComicSprtYn() {
		return this.comicSprtYn;
	}

	/**
	 * @param comicSprtYn
	 *            the comicSprtYn to set
	 */
	public void setComicSprtYn(String comicSprtYn) {
		this.comicSprtYn = comicSprtYn;
	}

	/**
	 * @return the musicSprtYn
	 */
	public String getMusicSprtYn() {
		return this.musicSprtYn;
	}

	/**
	 * @param musicSprtYn
	 *            the musicSprtYn to set
	 */
	public void setMusicSprtYn(String musicSprtYn) {
		this.musicSprtYn = musicSprtYn;
	}

	/**
	 * @return the videoDrmSprtYn
	 */
	public String getVideoDrmSprtYn() {
		return this.videoDrmSprtYn;
	}

	/**
	 * @param videoDrmSprtYn
	 *            the videoDrmSprtYn to set
	 */
	public void setVideoDrmSprtYn(String videoDrmSprtYn) {
		this.videoDrmSprtYn = videoDrmSprtYn;
	}

	/**
	 * @return the sdVideoSprtYn
	 */
	public String getSdVideoSprtYn() {
		return this.sdVideoSprtYn;
	}

	/**
	 * @param sdVideoSprtYn
	 *            the sdVideoSprtYn to set
	 */
	public void setSdVideoSprtYn(String sdVideoSprtYn) {
		this.sdVideoSprtYn = sdVideoSprtYn;
	}

	/**
	 * @return the sclShpgSprtYn
	 */
	public String getSclShpgSprtYn() {
		return this.sclShpgSprtYn;
	}

	/**
	 * @param sclShpgSprtYn
	 *            the sclShpgSprtYn to set
	 */
	public void setSclShpgSprtYn(String sclShpgSprtYn) {
		this.sclShpgSprtYn = sclShpgSprtYn;
	}

	/**
	 * @return the vodFixisttSprtYn
	 */
	public String getVodFixisttSprtYn() {
		return this.vodFixisttSprtYn;
	}

	/**
	 * @param vodFixisttSprtYn
	 *            the vodFixisttSprtYn to set
	 */
	public void setVodFixisttSprtYn(String vodFixisttSprtYn) {
		this.vodFixisttSprtYn = vodFixisttSprtYn;
	}

}
