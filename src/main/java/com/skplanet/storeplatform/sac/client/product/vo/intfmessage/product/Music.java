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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;

/**
 * Interface Message Music Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
public class Music extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Identifier identifier; // 다운로드 ID
	/*
	 * 제공 음질 및 사이즈 정보 제공(음악의 경우 type을 audio/mp3-192, audio/mp3-128로 구분한다.)
	 */
	private List<Source> sourceList;
	private List<Service> serviceList;
	private Bell bell; // 컬러링/벨소리 부가 정보

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public List<Source> getSourceList() {
		return this.sourceList;
	}

	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	public List<Service> getServiceList() {
		return this.serviceList;
	}

	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

	public Bell getBell() {
		return this.bell;
	}

	public void setBell(Bell bell) {
		this.bell = bell;
	}
}
