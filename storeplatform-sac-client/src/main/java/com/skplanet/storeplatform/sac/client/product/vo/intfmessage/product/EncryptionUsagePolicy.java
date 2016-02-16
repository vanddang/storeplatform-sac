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
 * EncryptionUsagePolicy Value Object.
 *
 * Updated on : 2014. 02. 11. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EncryptionUsagePolicy extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String applyDrm; // DRM 지원여부

	private String expirationDate; // DRM 만료일

    //	"dl": 다운로드 Only
    //	"strm": 스트리밍 Only
    //	"both": 다운로드 & 스트리밍 모두 지원
	private String dlStrmCd;

	//
	private String player;

	private String drmKey; // 컨텐츠 DRM Key

	public String getDrmKey() {
		return drmKey;
	}

	public void setDrmKey(String drmKey) {
		this.drmKey = drmKey;
	}
	/**
	 * @return the applyDrm
	 */
	public String getApplyDrm() {
		return this.applyDrm;
	}

	/**
	 * @param applyDrm
	 *            the applyDrm to set
	 */
	public void setApplyDrm(String applyDrm) {
		this.applyDrm = applyDrm;
	}

	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return this.expirationDate;
	}

	/**
	 * @param expirationDate
	 *            the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getDlStrmCd() {
		return dlStrmCd;
	}

	public void setDlStrmCd(String dlStrmCd) {
		this.dlStrmCd = dlStrmCd;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}
}
