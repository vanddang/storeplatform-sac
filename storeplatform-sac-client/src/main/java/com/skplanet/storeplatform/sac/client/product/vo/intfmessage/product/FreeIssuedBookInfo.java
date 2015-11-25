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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message FreeIssuedBookInfo Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FreeIssuedBookInfo extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Identifier identifier; // 무료 발행호 상품 ID
	private String scid; // 무료 발행호 Subn Content ID
	private String version; // 무료 발행호 버전
	/*
	 * 무료 발행호 상태 ( payment : 구매, nonPayment : 비구매, noProduct : 상품 미등록)
	 */
	private String status;
	private Title title; // 무료 발행호 상품명
	private String freeIssuedBookExplain; // 무료 발행호 설명
	private Date date; // 예상 정기구독 시작 ~ 종료일
	private Source source; // 무료 발행호 이미지

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
	public String getScid() {
		return this.scid;
	}

	/**
	 * @param scid
	 *            scid
	 */
	public void setScid(String scid) {
		this.scid = scid;
	}

	/**
	 * @return String
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param version
	 *            version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Title
	 */
	public Title getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            title
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * @return String
	 */
	public String getFreeIssuedBookExplain() {
		return this.freeIssuedBookExplain;
	}

	/**
	 * @param freeIssuedBookExplain
	 *            freeIssuedBookExplain
	 */
	public void setFreeIssuedBookExplain(String freeIssuedBookExplain) {
		this.freeIssuedBookExplain = freeIssuedBookExplain;
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

}
