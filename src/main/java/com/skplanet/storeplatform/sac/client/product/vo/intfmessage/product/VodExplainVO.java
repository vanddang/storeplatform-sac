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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.VodProto;

/**
 * Interface Message Vod.VodExplain Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(VodProto.Vod.VodExplain.class)
public class VodExplainVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String saleDateInfo; // BP가 직접 등록하는 방송일/개봉년도 정보
	private String text; // 날짜

	public String getSaleDateInfo() {
		return this.saleDateInfo;
	}

	public void setSaleDateInfo(String saleDateInfo) {
		this.saleDateInfo = saleDateInfo;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
