/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.integration.api.test.signature;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 김현일, 인크로스.
 */
@SuppressWarnings("serial")
public class SignatureException extends RuntimeException {

	/**
	 * 
	 * @param msg
	 *            msg
	 */
	public SignatureException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * @param throwable
	 *            throwable
	 */
	public SignatureException(Throwable throwable) {
		super(throwable);
	}
}
