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
	private Long size; // 파일 사이즈
	/*
	 * Resource 타입 (product : 상품, thumbnail : 썸네일, movie : 동영상, screenshot : 스크린샷, screenshot/large : 스크린샷(큰사이즈), music
	 * : 음악, barcode : 바코드이미지, 음악의 경우 audio/mp3-192, audio/mp3-128로 구분한다. preview : vod 미리보기, DLM : 다운로드 메타 이미지)
	 */
	private String type;
	private String url; // Resource URL
	private String expoOrd; // 노출 순서
	private String imageCode; // 이미지 코드
    private Integer width;  // 폭
    private Integer height; // 높이
    private String orientation; // 가로 세로 형태
    private String ratio; // 이미지 비율

	/**
	 * @return the mediaType
	 */
	public String getMediaType() {
		return this.mediaType;
	}

	/**
	 * @param mediaType
	 *            the mediaType to set
	 */
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return this.size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

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
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the expoOrd
	 */
	public String getExpoOrd() {
		return this.expoOrd;
	}

	/**
	 * @param expoOrd
	 *            the expoOrd to set
	 */
	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	/**
	 * @return the imageCode
	 */
	public String getImageCode() {
		return this.imageCode;
	}

	/**
	 * @param imageCode
	 *            the imageCode to set
	 */
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}


}
