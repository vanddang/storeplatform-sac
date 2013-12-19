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
import com.skplanet.storeplatform.sac.client.intfmessage.device.vo.DeviceProto;

/**
 * Interface Message SupportedHardware Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(DeviceProto.Device.SupportedHardware.class)
public class SupportedHardware extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 해상도(720*1280)
	 */
	private String resolution;
	/**
	 * DPI
	 */
	private String dpi;
	/**
	 * 지원가능 (","로 구분) > hdmi,hdcp,lte,dolby > HDMI,HDCP,LTE,돌비
	 */
	private String support;

	/**
	 * 지원불가 (","로 구분) > hdmi,hdcp,lte,dolby > HDMI,HDCP,LTE,돌비
	 */
	private String restrict;

	public String getResolution() {
		return this.resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getDpi() {
		return this.dpi;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	public String getSupport() {
		return this.support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getRestrict() {
		return this.restrict;
	}

	public void setRestrict(String restrict) {
		this.restrict = restrict;
	}

}
