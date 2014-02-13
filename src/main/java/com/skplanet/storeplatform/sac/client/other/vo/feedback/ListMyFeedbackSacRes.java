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

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * ListMyFeedbackRes Value Object
 * 
 * Updated on : 2014. 1. 23. Updated by : 김현일, 인크로스.
 */
public class ListMyFeedbackSacRes extends CommonInfo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String notiTot;
	/**
	 * 사용후기 리스트.
	 */
	private List<FeedbackMy> notiList;

	/**
	 * @return String
	 */
	public String getNotiTot() {
		return this.notiTot;
	}

	/**
	 * @param notiTot
	 *            notiTot
	 */
	public void setNotiTot(String notiTot) {
		this.notiTot = notiTot;
	}

	/**
	 * @return List<FeedbackMy>
	 */
	public List<FeedbackMy> getNotiList() {
		return this.notiList;
	}

	/**
	 * @param notiList
	 *            notiList
	 */
	public void setNotiList(List<FeedbackMy> notiList) {
		this.notiList = notiList;
	}

}
