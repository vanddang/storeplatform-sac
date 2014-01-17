/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message Source Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Source extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * Contents의 MimeType (image/png : PNG 이미지, image/jpeg : JPG 이미지)
	 */
	private String mediaType;
	private Integer size; // 파일 사이즈
	/*
	 * Resource 타입 (product : 상품, thumbnail : 썸네일, movie : 동영상, screenshot : 스크린샷, screenshot/large : 스크린샷(큰사이즈), music
	 * : 음악, barcode : 바코드이미지, 음악의 경우 audio/mp3-192, audio/mp3-128로 구분한다.)
	 */
	private String type;
	private String url; // Resource URL

	public String getMediaType() {
		return this.mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
