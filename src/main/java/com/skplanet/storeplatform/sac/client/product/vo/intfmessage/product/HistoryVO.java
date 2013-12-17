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
import com.skplanet.storeplatform.sac.clinet.intfmessage.product.vo.HistoryProto;

/**
 * Interface Message Identifier Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@SuppressWarnings("serial")
@ProtobufMapping(HistoryProto.History.class)
public class HistoryVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private UpdateVO update;

	public UpdateVO getUpdate() {
		return this.update;
	}

	public void setUpdate(UpdateVO update) {
		this.update = update;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
