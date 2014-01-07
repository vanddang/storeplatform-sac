/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.example.ec.controller;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 회원 경력 Value Object
 * 
 * Updated on : 2013-09-26 Updated by : 서대영, SK플래닛.
 */
@XmlRootElement
public class UserCareerSearch implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String description;

	/**
	 * 아이디 Getter.
	 * 
	 * @return 아이디
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 아이디 Setter.
	 * 
	 * @param id
	 *            아이디
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 성명 Getter.
	 * 
	 * @return 성명
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 성명 Setter.
	 * 
	 * @param name
	 *            성명
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 설명 Getter.
	 * 
	 * @return 설명
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 설명 Setter.
	 * 
	 * @param description
	 *            설명
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
