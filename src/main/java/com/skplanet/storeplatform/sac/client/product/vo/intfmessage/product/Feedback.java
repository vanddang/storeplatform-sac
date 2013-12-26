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
import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.FeedbackProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Count;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message Feedback Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(FeedbackProto.Feedback.class)
public class Feedback extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 사용후기ID > feedback : 사용후기ID
	 */
	private Identifier identifier;

	/**
	 * > seller : 판매자일 경우 > mine : 자신이 등록한 댓글 일 경우 > seller|mine : 모두 해당될때 > 그 외에는 whose를 넣지 않는다
	 */
	private String whose;

	/**
	 * 추천구분 > yes : 내가 추천한 feedback인 경우 > no : 내가 추천하지 않은 feedback인 경우
	 */
	private String recommend;

	/**
	 * 추천구분 Count type recommend : 추천수
	 */
	private Count count;

	/**
	 * 평점
	 */
	private double score;

	/**
	 * 별명
	 */
	private String feedbackNickname;

	/**
	 * distributor message > company
	 */
	private Distributor distributor;

	/**
	 * title Message > text
	 */
	private Title title;

	/**
	 * Feedback내용
	 */
	private String feedBackExplain;

	/**
	 * Date Message > type (date/reg) > text 등록일
	 */
	private Date date;

	/**
	 * Badge Message
	 */
	private Badge badge;

	/**
	 * reply Message
	 */
	private Reply reply;

	/**
	 * Base URL
	 */
	private String base;

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public String getWhose() {
		return this.whose;
	}

	public void setWhose(String whose) {
		this.whose = whose;
	}

	public String getRecommend() {
		return this.recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public Count getCount() {
		return this.count;
	}

	public void setCount(Count count) {
		this.count = count;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getFeedbackNickname() {
		return this.feedbackNickname;
	}

	public void setFeedbackNickname(String feedbackNickname) {
		this.feedbackNickname = feedbackNickname;
	}

	public Distributor getDistributor() {
		return this.distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getFeedBackExplain() {
		return this.feedBackExplain;
	}

	public void setFeedBackExplain(String feedBackExplain) {
		this.feedBackExplain = feedBackExplain;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Badge getBadge() {
		return this.badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	public Reply getReply() {
		return this.reply;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}

}
