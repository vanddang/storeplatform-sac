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
 * Interface Message Event Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Event extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 이벤트 ID.
	 */
	private String eventId;
	/**
	 * 이벤트 설명.
	 */
	private String eventExplain;

	/**
	 * @return String
	 */
	public String getEventId() {
		return this.eventId;
	}

	/**
	 * @param eventId
	 *            eventId
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return String
	 */
	public String getEventExplain() {
		return this.eventExplain;
	}

	/**
	 * @param eventExplain
	 *            eventExplain
	 */
	public void setEventExplain(String eventExplain) {
		this.eventExplain = eventExplain;
	}

}
