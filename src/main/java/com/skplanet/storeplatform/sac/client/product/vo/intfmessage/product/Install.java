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

/**
 * Interface Message App.Install Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(AppProto.App.Install.class)
public class Install extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * > Installer를 호출한 package name 정의 > Installer가 없을 경우 정의 하지 않음
	 */
	private String caller;
	/*
	 * >사용자 설정 상태 > auto : 자동 upgrade 사용
	 */
	private String upgrade;

	public String getCaller() {
		return this.caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getUpgrade() {
		return this.upgrade;
	}

	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}

}
