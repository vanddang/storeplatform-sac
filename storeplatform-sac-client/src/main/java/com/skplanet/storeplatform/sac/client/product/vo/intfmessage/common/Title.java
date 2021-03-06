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
 * Interface Message Title Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Title extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String color; // 폰트 색상을 정의 (RGB 8:8:8 형태이며 Alpha channel은 사용하지 않는다.)
	private String prefix; // 제목 앞에 붙는 추가 노출 문구
	private String postfix; // 제목 뒤에 붙는 추가 노출 문구. 예) 열혈강호 59권
	private String text; // 제목
	private String name; // title의 분류명
	private String alias; // 별명

	public Title() {
	}

	public Title(String text) {
		this.text = text;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPostfix() {
		return this.postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
