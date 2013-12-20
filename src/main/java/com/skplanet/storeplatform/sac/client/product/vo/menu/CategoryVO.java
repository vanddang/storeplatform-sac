/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.menu;

/**
 * 카테고리 조회 Default Value Object.
 * 
 * Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
public class CategoryVO {

	private String code = "";

	private String name = "";

	private String type = "";

	private String text = "";

	/*
	 * Contents의 MimeType (image/png : PNG 이미지, image/jpeg : JPG 이미지)
	 */
	private String mediaType;

	private String size; // 파일 사이즈
	/*
	 * Resource 타입 (product : 상품, thumbnail : 썸네일, movie : 동영상, screenshot : 스크린샷, screenshot/large : 스크린샷(큰사이즈), music
	 * : 음악, barcode : 바코드이미지, 음악의 경우 audio/mp3-192, audio/mp3-128로 구분한다.)
	 */
	private String sourceType;

	private String url; // Resource URL

	public String getMediaType() {
		return this.mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
