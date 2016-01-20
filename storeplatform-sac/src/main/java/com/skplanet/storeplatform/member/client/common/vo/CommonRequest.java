/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
/**
 * 판매자 공통 
 */
package com.skplanet.storeplatform.member.client.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

/**
 * 공통으로 사용되는 요청 Value Object.
 * 
 * Updated on : 2013. 12. 16. Updated by : wisestone_mikepark
 */
public class CommonRequest extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** Tenant ID. */
	private String tenantID;

	/** System ID. */
	private String systemID;

    public CommonRequest() {}

    public CommonRequest(String tenantID, String systemID) {
        this.tenantID = tenantID;
        this.systemID = systemID;
    }

    /**
	 * Tenant ID를 리턴한다.
	 * 
	 * @return tenantID - Tenant ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * Tenant ID를 설정한다.
	 * 
	 * @param tenantID
	 *            Tenant ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * System ID를 리턴한다.
	 * 
	 * @return systemID - System ID
	 */
	public String getSystemID() {
		return this.systemID;
	}

	/**
	 * System ID를 설정한다.
	 * 
	 * @param systemID
	 *            System ID
	 */
	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	/**
	 * To string.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

    public static CommonRequest convert(SacRequestHeader sacRequestHeader) {
        if(sacRequestHeader == null)
            return new CommonRequest();

        TenantHeader tenantHeader = sacRequestHeader.getTenantHeader();
        return new CommonRequest(tenantHeader.getTenantId(), tenantHeader.getSystemId());
    }
}
