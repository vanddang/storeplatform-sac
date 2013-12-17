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

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.VideoInfoProto;

/**
 * Interface Message VideoInfo Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(VideoInfoProto.VideoInfo.class)
public class VideoInfoVO extends CommonVO implements Serializable {
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

	public String getScid() {
		return this.scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPixel() {
		return this.pixel;
	}

	public void setPixel(String pixel) {
		this.pixel = pixel;
	}

	public String getPictureSize() {
		return this.pictureSize;
	}

	public void setPictureSize(String pictureSize) {
		this.pictureSize = pictureSize;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBtvcid() {
		return this.btvcid;
	}

	public void setBtvcid(String btvcid) {
		this.btvcid = btvcid;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}
