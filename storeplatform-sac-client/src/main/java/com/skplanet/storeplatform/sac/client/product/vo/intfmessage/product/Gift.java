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
 * Interface Message Gift Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Gift extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * 선물수신상태 (received : 선물 수신이 완료됐을 경우, sending : 수신자가 “수신 확인” 전까지의 상태)
	 */
	private String status;
	private String sender; // 발신자 전화번호
	private String receiver; // 수신자 전화번호

	/**
	 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return String
	 */
	public String getSender() {
		return this.sender;
	}

	/**
	 * @param sender
	 *            sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return String
	 */
	public String getReceiver() {
		return this.receiver;
	}

	/**
	 * @param receiver
	 *            receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}
