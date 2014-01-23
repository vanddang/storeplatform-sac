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
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message SupportedHardware Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SupportedHardware extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 해상도(720*1280)
	 */
	private String resolution;
	/**
	 * DPI
	 */
	private String dpi;

	private List<Service> serviceList;

	/**
	 * @return String
	 */
	public String getResolution() {
		return this.resolution;
	}

	/**
	 * @param resolution
	 *            resolution
	 */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	/**
	 * @return String
	 */
	public String getDpi() {
		return this.dpi;
	}

	/**
	 * @param dpi
	 *            dpi
	 */
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	/**
	 * @return List<Service>
	 */
	public List<Service> getServiceList() {
		return this.serviceList;
	}

	/**
	 * @param serviceList
	 *            serviceList
	 */
	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

}
