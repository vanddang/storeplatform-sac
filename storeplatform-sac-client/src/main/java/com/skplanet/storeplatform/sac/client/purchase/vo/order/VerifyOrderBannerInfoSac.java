/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매인증 시, 배너 정보
 * 
 * Updated on : 2014. 11. 13. Updated by : 이승택, nTels.
 */
public class VerifyOrderBannerInfoSac extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String title; // 배너명
	private String imagePath; // 배너 이미지 Path
	private String linkUrl; // 배너 클릭 시 이동 URL
	private String backColorCd; // 배너 배경 색상 코드

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return this.imagePath;
	}

	/**
	 * @param imagePath
	 *            the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the linkUrl
	 */
	public String getLinkUrl() {
		return this.linkUrl;
	}

	/**
	 * @param linkUrl
	 *            the linkUrl to set
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	/**
	 * @return the backColorCd
	 */
	public String getBackColorCd() {
		return this.backColorCd;
	}

	/**
	 * @param backColorCd
	 *            the backColorCd to set
	 */
	public void setBackColorCd(String backColorCd) {
		this.backColorCd = backColorCd;
	}

}
