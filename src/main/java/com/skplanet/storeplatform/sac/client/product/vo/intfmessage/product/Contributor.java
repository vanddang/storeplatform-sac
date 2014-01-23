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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;

/**
 * Interface Message Contributor Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Contributor extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 가수, 저자, 판매자, 브랜드 ID Identifier "type"을 아래와 같이 지정 > individual : 판매회원의 개발자ID > privateOperator : 개인사업자의 개발자ID >
	 * corporation : 법인사업자의 개발자ID > foreigner : 외국인 개발자ID > brand : 브랜드ID
	 */
	private Identifier identifier;
	/**
	 * 제작자 또는 저자 이름
	 */
	private String name;
	/**
	 * 브랜드 이름
	 */
	private String brand;
	/**
	 * 그림작가
	 */
	private String painter;
	/**
	 * 번역자
	 */
	private String translator;
	/**
	 * 일반적인 회사
	 */
	private String company;
	/**
	 * 출판사, 앨범 발매회사
	 */
	private String publisher;
	/**
	 * 소속사
	 */
	private String agency;
	/**
	 * 방송 채널
	 */
	private String channel;
	/**
	 * 감독
	 */
	private String director;
	/**
	 * 출연자
	 */
	private String artist;

	/**
	 * 앨범명 private String album; /** 국가명
	 */
	private String country;
	/*
	 * 이미지url
	 */private Source source;
	/**
	 * 앨범 출시 일 등 날짜를 기입할 경우 사용한다.
	 */
	private Date date;

	/**
	 * @return Identifier
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}

	/**
	 * @param identifier
	 *            identifier
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
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
	public String getBrand() {
		return this.brand;
	}

	/**
	 * @param brand
	 *            brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return String
	 */
	public String getPainter() {
		return this.painter;
	}

	/**
	 * @param painter
	 *            painter
	 */
	public void setPainter(String painter) {
		this.painter = painter;
	}

	/**
	 * @return String
	 */
	public String getTranslator() {
		return this.translator;
	}

	/**
	 * @param translator
	 *            translator
	 */
	public void setTranslator(String translator) {
		this.translator = translator;
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

	/**
	 * @return String
	 */
	public String getPublisher() {
		return this.publisher;
	}

	/**
	 * @param publisher
	 *            publisher
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return String
	 */
	public String getAgency() {
		return this.agency;
	}

	/**
	 * @param agency
	 *            agency
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/**
	 * @return String
	 */
	public String getChannel() {
		return this.channel;
	}

	/**
	 * @param channel
	 *            channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return String
	 */
	public String getDirector() {
		return this.director;
	}

	/**
	 * @param director
	 *            director
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @return String
	 */
	public String getArtist() {
		return this.artist;
	}

	/**
	 * @param artist
	 *            artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
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
	 * @return Source
	 */
	public Source getSource() {
		return this.source;
	}

	/**
	 * @param source
	 *            source
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * @return Date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date
	 *            date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
