/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.common.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

/**
 * 공통으로 사용되는 응답 Value Object.
 * 
 * Updated on : 2013. 12. 16. Updated by : wisestone_mikepark
 */
public class CommonResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 결과 코드. */
	private String resultCode;

	/** 결과 메시지. */
	private String resultMessage;

	/**
	 * 결과 코드를 리턴한다.
	 * 
	 * @return resultCode - 결과 코드
	 */
	public String getResultCode() {
		return this.resultCode;
	}

	/**
	 * 결과 코드를 설정한다.
	 * 
	 * @param resultCode
	 *            결과 코드
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * 결과 메시지를 리턴한다.
	 * 
	 * @return resultMessage - 결과 메시지
	 */
	public String getResultMessage() {
		return this.resultMessage;
	}

	/**
	 * 결과 메시지를 설정한다.
	 * 
	 * @param resultMessage
	 *            결과 메시지
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * To string.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

}
