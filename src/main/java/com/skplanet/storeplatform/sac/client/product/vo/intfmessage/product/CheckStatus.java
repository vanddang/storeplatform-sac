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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.CheckStatusProto;

/**
 * Interface Message CheckStatus Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(CheckStatusProto.CheckStatus.class)
public class CheckStatus extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String productId;
	/**
	 * 상품 상태 - 상품 선물 발신 여부 조회인 경우 Y : 가능 N : 불가능 - 상품 추천 가능 여부 조회인 경우 Y : 가능 N : 불가능
	 */
	private String status;

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
