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
	 * private final int productCount = 0; // 카테고리 상품수
	 * 
	 * 
	 * Contents의 MimeType > image/png : PNG 이미지 > image/jpeg : JPG 이미지
	 * 
	 * private final String contentsMediaType = ""; private final int fileSize = 0; // 파일 사이즈
	 * 
	 * 
	 * Resource 타입 > product : 상품 > thumbnail : 썸네일 > movie : 동영상 > screenshot : 스크린샷 > screenshot/large : 스크린샷(큰사이즈) >
	 * music : 음악 > barcode : 바코드이미지 > 음악의 경우 audio/mp3-192, audio/mp3-128로 구분한다.
	 * 
	 * private String resourceType = ""; private final String resouceUrl = ""; // Resource URL
	 * 
	 * public String getResourceType() { return this.resourceType; }
	 * 
	 * public void setResourceType(String resourceType) { this.resourceType = resourceType; }
	 */
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
