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

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.common.vo.SourceProto.Source;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.MusicProto;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.ServiceProto.Service;
import com.skplanet.storeplatform.sac.client.intfmessage.user.vo.BellProto.Bell;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.IdentifierVO;

/**
 * Interface Message Identifier Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@SuppressWarnings("serial")
@ProtobufMapping(MusicProto.Music.class)
public class MusicVO extends CommonVO implements Serializable {
	private IdentifierVO identifier;
	/**
	 * 제공 음질 및 사이즈 정보 제공 음악의 경우 type을 audio/mp3-192, audio/mp3-128로 구분한다. 파일 size 정보 Remote 정보
	 */
	private List<Source> sourceList;

	private List<Service> serviceList;

	private Bell bell;

	public IdentifierVO getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(IdentifierVO identifier) {
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
