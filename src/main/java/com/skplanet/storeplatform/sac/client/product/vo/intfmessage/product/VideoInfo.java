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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message VideoInfo Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class VideoInfo extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * 타입 ( normal: normal, sd: sd, hd: hd)
	 */
	private String scid;
	private String type; // 타입
	private String pixel; // 픽셀수
	private String pictureSize; // 화면비
	private String source; // 파일정보
	private String version; // 버전
	private String btvcid; // BTV CID
	private String size; // 파일사이즈

	/**
	 * @return String
	 */
	public String getScid() {
		return this.scid;
	}

	/**
	 * @param scid
	 *            scid
	 */
	public void setScid(String scid) {
		this.scid = scid;
	}

	/**
	 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return String
	 */
	public String getPixel() {
		return this.pixel;
	}

	/**
	 * @param pixel
	 *            pixel
	 */
	public void setPixel(String pixel) {
		this.pixel = pixel;
	}

	/**
	 * @return String
	 */
	public String getPictureSize() {
		return this.pictureSize;
	}

	/**
	 * @param pictureSize
	 *            pictureSize
	 */
	public void setPictureSize(String pictureSize) {
		this.pictureSize = pictureSize;
	}

	/**
	 * @return String
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * @param source
	 *            source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return String
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param version
	 *            version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return String
	 */
	public String getBtvcid() {
		return this.btvcid;
	}

	/**
	 * @param btvcid
	 *            btvcid
	 */
	public void setBtvcid(String btvcid) {
		this.btvcid = btvcid;
	}

	/**
	 * @return String
	 */
	public String getSize() {
		return this.size;
	}

	/**
	 * @param size
	 *            size
	 */
	public void setSize(String size) {
		this.size = size;
	}

}
