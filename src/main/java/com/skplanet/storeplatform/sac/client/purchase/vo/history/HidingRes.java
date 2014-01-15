/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역숨김처리 컴포넌트 응답.
 * 
 * Updated on : 2014. 12. 20. Updated by : 조용진, 엔텔스.
 */
public class HidingRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String prchsId; // 구매아이디
	private Integer prchsDtlId; // 상품 아이디
	private String resultYn; // update 성공여부

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the prchsDtlId
	 */
	public Integer getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(Integer prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}

	/**
	 * @return the resultYn
	 */
	public String getResultYn() {
		return this.resultYn;
	}

	/**
	 * @param resultYn
	 *            the resultYn to set
	 */
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}

}
