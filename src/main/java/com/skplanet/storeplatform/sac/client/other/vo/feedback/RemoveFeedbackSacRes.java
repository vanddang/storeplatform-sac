/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.feedback;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * RemoveFeedbackRes Value Object
 * 
 * Updated on : 2014. 1. 27. Updated by : 김현일, 인크로스.
 */
public class RemoveFeedbackSacRes extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용후기 번호.
	 */
	private int notiSeq;

	/**
	 * @return int
	 */
	public int getNotiSeq() {
		return this.notiSeq;
	}

	/**
	 * @param notiSeq
	 *            notiSeq
	 */
	public void setNotiSeq(int notiSeq) {
		this.notiSeq = notiSeq;
	}

}
