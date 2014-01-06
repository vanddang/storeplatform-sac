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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;

/**
 * Interface Message BindingInfo Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BindingInfo extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 단말 OS Platform에 대한 Version 정보
	 */
	private String versionCode;

	/**
	 * 이동통신사 정보 > {MCC/MNC} > 예) 450/05
	 */
	private String carrier; // 단말에 대한 가입 이동통신사 정보
	/**
	 * 해당 단말기 등록 일시
	 */
	private Date date;
	/**
	 * 대표단말 여부 정보 (default no) > yes : 대표단말 지정함 > no : 대표단말 지정 안함
	 */
	private String mark;

	public String getVersionCode() {
		return this.versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getCarrier() {
		return this.carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
