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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;

/**
 * Interface Message Music Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Music extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Identifier identifier; // 다운로드 ID
	private List<Identifier> identifierList; // 다운로드 ID List
	/*
	 * 제공 음질 및 사이즈 정보 제공(음악의 경우 type을 audio/mp3-192, audio/mp3-128로 구분한다.)
	 */
	private List<Source> sourceList;
	private List<Service> serviceList;
	private Bell bell; // 컬러링/벨소리 부가 정보

	/**
	 * @return the identifier
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the identifierList
	 */
	public List<Identifier> getIdentifierList() {
		return this.identifierList;
	}

	/**
	 * @param identifierList
	 *            the identifierList to set
	 */
	public void setIdentifierList(List<Identifier> identifierList) {
		this.identifierList = identifierList;
	}

	/**
	 * @return List<Source>
	 */
	public List<Source> getSourceList() {
		return this.sourceList;
	}

	/**
	 * @param sourceList
	 *            sourceList
	 */
	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	/**
	 * @return List<Service>
	 */
	public List<Service> getServiceList() {
		return this.serviceList;
	}

	/**
	 * @param serviceList
	 *            serviceList
	 */
	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

	/**
	 * @return Bell
	 */
	public Bell getBell() {
		return this.bell;
	}

	/**
	 * @param bell
	 *            bell
	 */
	public void setBell(Bell bell) {
		this.bell = bell;
	}

}
