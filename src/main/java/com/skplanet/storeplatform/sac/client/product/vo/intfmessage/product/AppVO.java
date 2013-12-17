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

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.AppProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;

/**
 * Interface Message Identifier Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@SuppressWarnings("serial")
@ProtobufMapping(AppProto.App.class)
public class AppVO extends CommonVO implements Serializable {
	/**
	 * Applicaton ID
	 */
	private String aid;
	/**
	 * 지원 OS
	 */
	private String supportedOs;
	/**
	 * 패키지명
	 */
	private String packageName;
	/**
	 * 패키지 버전 코드
	 */
	private String versionCode;
	/**
	 * App 상품 버전
	 */
	private String version;
	/**
	 * 용량 (byte단위)
	 */
	private String size;
	/**
	 * 상품 등록일자
	 */
	private DateVO date;
	/**
	 * Debug 정보
	 */
	private AppDebugVO appDebug;
	/**
	 * update history
	 */
	private HistoryVO history;
	/**
	 * Install 정보
	 */
	private InstallVO install;
}
