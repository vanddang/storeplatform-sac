/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.flow.exception;

import com.skplanet.storeplatform.framework.core.exception.ErrorMessage;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
public class AclException extends StorePlatformException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param errorMessage
	 *            errorMessage
	 * @param tr
	 *            tr
	 */
	public AclException(ErrorMessage errorMessage, Throwable tr) {
		super(errorMessage, tr);
	}

	/**
	 * 
	 * @param errorMessage
	 *            errorMessage
	 */
	public AclException(ErrorMessage errorMessage) {
		super(errorMessage);
	}
}
