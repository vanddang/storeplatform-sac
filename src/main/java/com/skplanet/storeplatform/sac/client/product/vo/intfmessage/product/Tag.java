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
 * Interface Message Tag Value Object.
 * 
 * Updated on : 2014. 2. 6. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Tag extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 태그 코드.
	 */
	private String tagCd;
	/**
	 * 태그 타입 코드.
	 */
	private String tagTypeCd;
	/**
	 * 태그 명.
	 */
	private String text;

	/**
	 * @return the tagCd
	 */
	public String getTagCd() {
		return this.tagCd;
	}

	/**
	 * @param tagCd
	 *            the tagCd to set
	 */
	public void setTagCd(String tagCd) {
		this.tagCd = tagCd;
	}

	/**
	 * @return the tagTypeCd
	 */
	public String getTagTypeCd() {
		return this.tagTypeCd;
	}

	/**
	 * @param tagTypeCd
	 *            the tagTypeCd to set
	 */
	public void setTagTypeCd(String tagTypeCd) {
		this.tagTypeCd = tagTypeCd;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

}
