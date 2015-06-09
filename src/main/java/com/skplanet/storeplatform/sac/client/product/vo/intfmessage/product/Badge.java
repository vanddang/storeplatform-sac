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
 * Interface Message Feedback.Badge Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Badge extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name; // Facebook과 연동한 댓글인 경우 표기 > facebook
	private String badgeCd; // Badge Code
	private String badgeText; // Badge Text

	/**
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return long
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the badgeCd
	 */
	public String getBadgeCd() {
		return this.badgeCd;
	}

	/**
	 * @param badgeCd
	 *            the badgeCd to set
	 */
	public void setBadgeCd(String badgeCd) {
		this.badgeCd = badgeCd;
	}

	/**
	 * @return the badgeText
	 */
	public String getBadgeText() {
		return this.badgeText;
	}

	/**
	 * @param badgeText
	 *            the badgeText to set
	 */
	public void setBadgeText(String badgeText) {
		this.badgeText = badgeText;
	}

}
