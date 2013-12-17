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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.ContributorProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.IdentifierVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.SourceVO;

/**
 * Interface Message Identifier Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@SuppressWarnings("serial")
@ProtobufMapping(ContributorProto.Contributor.class)
public class ContributorVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 가수, 저자, 판매자, 브랜드 ID Identifier "type"을 아래와 같이 지정 > individual : 판매회원의 개발자ID > privateOperator : 개인사업자의 개발자ID >
	 * corporation : 법인사업자의 개발자ID > foreigner : 외국인 개발자ID > brand : 브랜드ID
	 */
	private IdentifierVO identifier;
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
	 * 앨범명
	 */
	private String album;
	/**
	 * 국가명
	 */
	private String country;
	/**
	 * 이미지url
	 */
	private SourceVO source;
	/**
	 * 앨범 출시 일 등 날짜를 기입할 경우 사용한다.
	 */
	private DateVO date;

	public IdentifierVO getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(IdentifierVO identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPainter() {
		return this.painter;
	}

	public void setPainter(String painter) {
		this.painter = painter;
	}

	public String getTranslator() {
		return this.translator;
	}

	public void setTranslator(String translator) {
		this.translator = translator;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAgency() {
		return this.agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getArtist() {
		return this.artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return this.album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public SourceVO getSource() {
		return this.source;
	}

	public void setSource(SourceVO source) {
		this.source = source;
	}

	public DateVO getDate() {
		return this.date;
	}

	public void setDate(DateVO date) {
		this.date = date;
	}

}
