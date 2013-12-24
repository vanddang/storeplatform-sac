/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.vod;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;

/**
 * Vod 보관함 조회 Input Value Object.
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
public class VodBoxRequest extends CommonVO implements Serializable {

	private static final long serialVersionUID = 11123123143L;

	private String filteredBy; // 차트 구분 코드

	private String imageSizeCd; // 이미지 사이즈 코드

	private String offset; // 시작점 ROW

	private String count; // 페이지당 노출 ROW 수

	private String duration;

	private String chapter;

	private String regdate;

	public String getFilteredBy() {
		return this.filteredBy;
	}

	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	public String getImageSizeCd() {
		return this.imageSizeCd;
	}

	public void setImageSizeCd(String imageSizeCd) {
		this.imageSizeCd = imageSizeCd;
	}

	public String getOffset() {
		return this.offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getCount() {
		return this.count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getChapter() {
		return this.chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getRegdate() {
		return this.regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

}
