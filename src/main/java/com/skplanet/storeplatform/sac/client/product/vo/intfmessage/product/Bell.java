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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;

/**
 * Interface Message Bell Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
public class Bell extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * colorRing or bell (colorRing : 컬러링, bell : 벨소리)
	 */
	private String name;
	/*
	 * 품질 (normal : 컬러링 일반(40초), 벨소리 일반 (40초), long : 컬러링 롱 (60초), 벨소리 고음질 (60초))
	 */
	private String quality;
	/*
	 * 컬러링, 벨소리 타입 (default : Default (벨소리 구매 시 유효), basic : 기본 컬러링, caller : 발신자 컬러링, time : 시간대 컬러링, grou : 그룹별 컬러링)
	 */
	private String type;
	private String rnPid; // rnPid ex) Z3000Z2000 Z3000Z4000 Z3000Z1000
	private String rsIsuAmtAdd; // rsIsuAmtAdd ex) “+0100A” or “-0100A”
	private Source source; // Image URL

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuality() {
		return this.quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRnPid() {
		return this.rnPid;
	}

	public void setRnPid(String rnPid) {
		this.rnPid = rnPid;
	}

	public String getRsIsuAmtAdd() {
		return this.rsIsuAmtAdd;
	}

	public void setRsIsuAmtAdd(String rsIsuAmtAdd) {
		this.rsIsuAmtAdd = rsIsuAmtAdd;
	}

	public Source getSource() {
		return this.source;
	}

	public void setSource(Source source) {
		this.source = source;
	}
}
