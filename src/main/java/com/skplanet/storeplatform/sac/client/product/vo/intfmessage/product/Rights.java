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
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;

/**
 * Interface Message Rights Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Rights extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String allow; // 이용권한 (freepass : 자유이용권 지원, domestic : 국내만 지원, feedback : 사용후기 가능)
	private String deny; // 이용금지 권한 (multiAccess 여러 개의 단말에서 이용 금지, access : 이 단말에서 사용 금지)
	private String grade; // 이용등급 (0 : 전체이용가, 1 : 12세 이용가, 2 : 15세 이용가, 4 : 청소년사용불가)
	private Integer ageAllowedFrom; // 이용 가능 연령
	private Preview preview; // 미리보기 정보를 정의
	private Play play; // 스트리밍을 지원할 경우 정의한다.
	private Store store; // 다운로드를 지원할 경우 정의한다.
	private Date date; // 사용가능 시작/만료 일자
	private String additionalUsagePeriod; // 전시를 목적으로 이용 기간을 표기할 경우 사용한다.
	private String durationUsagePeriodView; // 전시를 목적으로 추가 이용 기간을 표기할 경우 사용한다.
	private String subscription; // 정기구독정보
	private List<Date> dateList; // 사용가능 시작/만료 일자 LIST
	private String gradeExtra; // 19+ 상품여부

	/**
	 * @return String
	 */
	public String getAllow() {
		return this.allow;
	}

	/**
	 * @param allow
	 *            allow
	 */
	public void setAllow(String allow) {
		this.allow = allow;
	}

	/**
	 * @return String
	 */
	public String getDeny() {
		return this.deny;
	}

	/**
	 * @param deny
	 *            deny
	 */
	public void setDeny(String deny) {
		this.deny = deny;
	}

	/**
	 * @return String
	 */
	public String getGrade() {
		return this.grade;
	}

	/**
	 * @param grade
	 *            grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getAgeAllowedFrom() {
		return this.ageAllowedFrom;
	}

	public void setAgeAllowedFrom(Integer ageAllowedFrom) {
		this.ageAllowedFrom = ageAllowedFrom;
	}

	/**
	 * @return Preview
	 */
	public Preview getPreview() {
		return this.preview;
	}

	/**
	 * @param preview
	 *            preview
	 */
	public void setPreview(Preview preview) {
		this.preview = preview;
	}

	/**
	 * @return Play
	 */
	public Play getPlay() {
		return this.play;
	}

	/**
	 * @param play
	 *            play
	 */
	public void setPlay(Play play) {
		this.play = play;
	}

	/**
	 * @return Store
	 */
	public Store getStore() {
		return this.store;
	}

	/**
	 * @param store
	 *            store
	 */
	public void setStore(Store store) {
		this.store = store;
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
	 * @return String
	 */
	public String getAdditionalUsagePeriod() {
		return this.additionalUsagePeriod;
	}

	/**
	 * @param additionalUsagePeriod
	 *            additionalUsagePeriod
	 */
	public void setAdditionalUsagePeriod(String additionalUsagePeriod) {
		this.additionalUsagePeriod = additionalUsagePeriod;
	}

	/**
	 * @return String
	 */
	public String getDurationUsagePeriodView() {
		return this.durationUsagePeriodView;
	}

	/**
	 * @param durationUsagePeriodView
	 *            durationUsagePeriodView
	 */
	public void setDurationUsagePeriodView(String durationUsagePeriodView) {
		this.durationUsagePeriodView = durationUsagePeriodView;
	}

	/**
	 * @return String
	 */
	public String getSubscription() {
		return this.subscription;
	}

	/**
	 * @param subscription
	 *            subscription
	 */
	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	/**
	 * @return the dateList
	 */
	public List<Date> getDateList() {
		return this.dateList;
	}

	/**
	 * @param dateList
	 *            the dateList to set
	 */
	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	/**
	 * @return String
	 */
	public String getGradeExtra() {
		return this.gradeExtra;
	}

	/**
	 * @param gradeExtra
	 *            gradeExtra
	 */
	public void setGradeExtra(String gradeExtra) {
		this.gradeExtra = gradeExtra;
	}

}
