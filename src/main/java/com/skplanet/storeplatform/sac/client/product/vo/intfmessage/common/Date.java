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
import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message Date Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Date extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ISO8601 Basic 형태의 포매팅을 지원하는 SimpleDateForm인스턴스. 차후 상속으로 인한 확장을 고려하여 protected로 선언함.
	 */
	protected static final SimpleDateFormat DATE_FORMAT_ISO8601BASIC = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");

	/*
	 * 날짜타입 (date/reg : 등록일, date/saleReg : 판매등록 날짜, date/purchase : 구매날짜, date/update : 변경날짜, duration/usagePeriod :
	 * 이용기간, uint/usagePeriod : 이용기간, date/auth : 인증 날짜, duration/salePeriod : 판매기간, date/access : 접속일자, date/cancel :
	 * 해지일자 (자동결제 상품에서 사용), date/update : 인기검색어 업데이트 시간, duration/eventPeriod : 이벤트 기간, date/broadcast : 방송날짜)
	 * date/duration : 특정기간, chart/duration : 음원순위기간, date/publish : 출판일, date/issue : 발행일)
	 */
	private String type;
	/*
	 * 날짜 ISO 8601 Basic 형식으로 표기 20120913T195630+0900/20121013T195630+0900 ; 시작/끝 날짜를 지정하는 경우, /20121013T195630+0900 ;
	 * 시작 날짜 없이 끝 날짜만 지정하는 경우, 20120913T195630+0900/ ; 끝 날짜 없이 시작 날짜만 지정하는 경우
	 */
	private String text;

	public Date() {
	}

	public Date(String type, String text) {
		super();
		this.type = type;
		this.text = text;
	}

	/**
	 * 특정 일시를 표현하는 생성자. ISO8601Basic으로 변환함.
	 * 
	 * @param type
	 *            유형
	 * @param date
	 *            일시
	 */
	public Date(String type, java.util.Date date) {
		super();
		this.type = type;
		this.setText(date);
	}

	/**
	 * 기간을 표현하는 생성자. ISO8601Basic으로 변환함.
	 * 
	 * @param type
	 *            유형
	 * @param fromDt
	 *            시작일시. null일 경우 지정되지 않음
	 * @param toDt
	 *            종료일시. null일 경우 지정되지 않음
	 */
	public Date(String type, java.util.Date fromDt, java.util.Date toDt) {
		super();
		this.type = type;
		this.setText(fromDt, toDt);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * ISO8601 Basic형태의 일시를 지정합니다.
	 * 
	 * @param dt
	 *            일시
	 */
	public void setText(java.util.Date dt) {
		this.text = DATE_FORMAT_ISO8601BASIC.format(dt);
	}

	/**
	 * ISO8601 Basic형태의 기간 일시를 지정합니다.
	 * 
	 * @param fromDt
	 *            시작일시. null일 경우 지정되지 않음
	 * @param toDt
	 *            종료일시. null일 경우 지정되지 않음
	 */
	public void setText(java.util.Date fromDt, java.util.Date toDt) {
		StringBuilder sb = new StringBuilder();
		if (fromDt != null)
			sb.append(DATE_FORMAT_ISO8601BASIC.format(fromDt));
		sb.append("/");
		if (toDt != null)
			sb.append(DATE_FORMAT_ISO8601BASIC.format(toDt));

		this.text = sb.toString();
	}
}
