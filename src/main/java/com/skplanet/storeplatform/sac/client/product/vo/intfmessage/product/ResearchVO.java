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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.ResearchProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;

/**
 * Interface Message Research Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(ResearchProto.Research.class)
public class ResearchVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name; // boxOffice : 영화 집계
	private DateVO date; // 영화 집계 기간

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateVO getDate() {
		return this.date;
	}

	public void setDate(DateVO date) {
		this.date = date;
	}
}
