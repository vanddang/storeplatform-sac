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
 * Interface Message Distributor Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Distributor extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 배포자 ID
	 */
	private String identifier;
	/*
	 * 배포자 타입 > individual: 개인 (US000401) > privateOperator: 개인사업자 (US000402) > corporation: 법인사업자 (US000403) >
	 * foreigner: 해외 개발자
	 */
	private String type;
	/**
	 * 이름 또는 회사명(SK Planet)
	 */
	private String name;
	/**
	 * 닉네임 또는 회사명 별칭(SK 플래닛)
	 */
	private String nickName;
	/**
	 * 전화번호
	 */
	private String tel;
	/**
	 * 이메일
	 */
	private String email;
	/**
	 * 국가명
	 */
	private String country;
	/**
	 * 주소
	 */
	private String address;
	/**
	 * 판매자 등록 번호
	 */
	private String regNo;
	/**
	 * 판매처
	 */
	private String company;

	/**
	 * @return String
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 * @param identifier
	 *            identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
	 * @return String
	 */
	public String getNickName() {
		return this.nickName;
	}

	/**
	 * @param nickName
	 *            nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return String
	 */
	public String getTel() {
		return this.tel;
	}

	/**
	 * @param tel
	 *            tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return String
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 *            email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return String
	 */
	public String getCountry() {
		return this.country;
	}

	/**
	 * @param country
	 *            country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return String
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * @param address
	 *            address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return String
	 */
	public String getRegNo() {
		return this.regNo;
	}

	/**
	 * @param regNo
	 *            regNo
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * @return String
	 */
	public String getCompany() {
		return this.company;
	}

	/**
	 * @param company
	 *            company
	 */
	public void setCompany(String company) {
		this.company = company;
	}

}
