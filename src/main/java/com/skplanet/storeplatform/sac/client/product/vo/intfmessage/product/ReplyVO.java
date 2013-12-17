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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.FeedbackProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TitleVO;

/**
 * Interface Message Identifier Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@SuppressWarnings("serial")
@ProtobufMapping(FeedbackProto.Feedback.Reply.class)
public class ReplyVO extends CommonVO implements Serializable {
	/**
	 * Date Message > type (date/reg) > text
	 */
	private DateVO date;
	/**
	 * Title Message > text
	 */
	private TitleVO title;
	/**
	 * 댓글내용
	 */
	private String replayContent;
	/**
	 * 댓글 작성자이름
	 */
	private String replyName;

	public DateVO getDate() {
		return this.date;
	}

	public void setDate(DateVO date) {
		this.date = date;
	}

	public TitleVO getTitle() {
		return this.title;
	}

	public void setTitle(TitleVO title) {
		this.title = title;
	}

	public String getReplayContent() {
		return this.replayContent;
	}

	public void setReplayContent(String replayContent) {
		this.replayContent = replayContent;
	}

	public String getReplyName() {
		return this.replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

}
