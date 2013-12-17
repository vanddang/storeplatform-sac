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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.MusicProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.IdentifierVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.SourceVO;

/**
 * Interface Message Music Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(MusicProto.Music.class)
public class MusicVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private IdentifierVO identifier; // 다운로드 ID
	/*
	 * 제공 음질 및 사이즈 정보 제공(음악의 경우 type을 audio/mp3-192, audio/mp3-128로 구분한다.)
	 */
	private List<SourceVO> sourceList;
	private List<ServiceVO> serviceList;
	private BellVO bell; // 컬러링/벨소리 부가 정보

	public IdentifierVO getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(IdentifierVO identifier) {
		this.identifier = identifier;
	}

	public List<SourceVO> getSourceList() {
		return this.sourceList;
	}

	public void setSourceList(List<SourceVO> sourceList) {
		this.sourceList = sourceList;
	}

	public List<ServiceVO> getServiceList() {
		return this.serviceList;
	}

	public void setServiceList(List<ServiceVO> serviceList) {
		this.serviceList = serviceList;
	}

	public BellVO getBell() {
		return this.bell;
	}

	public void setBell(BellVO bell) {
		this.bell = bell;
	}
}
