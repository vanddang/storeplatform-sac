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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.AutoPayProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;

/**
 * Interface Message Identifier Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@SuppressWarnings("serial")
@ProtobufMapping(AutoPayProto.AutoPay.class)
public class AutoPayVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 상품결제타입 > normal : 일반결제 > auto : 자동결제
	 */
	private String type;
	/**
	 * 자동결제여부 > enabled : 자동결제 > reservedCancellation : 해지예약 > disabled : 해지완료
	 */
	private String status;
	/**
	 * 일시
	 */
	private DateVO date;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DateVO getDate() {
		return this.date;
	}

	public void setDate(DateVO date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
