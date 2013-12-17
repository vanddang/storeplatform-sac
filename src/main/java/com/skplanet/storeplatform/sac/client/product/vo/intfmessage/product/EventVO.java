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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.EventProto;

/**
 * Interface Message Event Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(EventProto.Event.class)
public class EventVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 이벤트 ID
	 */
	private String eventId;
	/**
	 * 이벤트 설명
	 */
	private String eventExplain;

	public String getEventId() {
		return this.eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventExplain() {
		return this.eventExplain;
	}

	public void setEventExplain(String eventExplain) {
		this.eventExplain = eventExplain;
	}

}
