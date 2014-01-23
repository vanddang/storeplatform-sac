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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message Feedback.Reply Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Reply extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Date Message > type (date/reg) > text
	 */
	private Date date;
	/**
	 * Title Message > text
	 */
	private Title title;
	/**
	 * 댓글내용
	 */
	private String replayContent;
	/**
	 * 댓글 작성자이름
	 */
	private String replyName;

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
	public String getReplayContent() {
		return this.replayContent;
	}

	/**
	 * @param replayContent
	 *            replayContent
	 */
	public void setReplayContent(String replayContent) {
		this.replayContent = replayContent;
	}

	/**
	 * @return String
	 */
	public String getReplyName() {
		return this.replyName;
	}

	/**
	 * @param replyName
	 *            replyName
	 */
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

}
